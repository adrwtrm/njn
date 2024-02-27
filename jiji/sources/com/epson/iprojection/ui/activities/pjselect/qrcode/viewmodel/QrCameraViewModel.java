package com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.SeekBar;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import com.epson.iprojection.databinding.MainQrcodeCameraBinding;
import com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract;

/* loaded from: classes.dex */
public class QrCameraViewModel extends BaseObservable implements QrcodeContract.IViewModel, View.OnTouchListener {
    private static final int INTENT_INFO = 0;
    private final MainQrcodeCameraBinding _binding;
    private final QrcodeContract.IPresenter _contractPresenter;
    private final QrcodeContract.IView _contractView;
    private int _orgProgress;
    private float _orgSpan;
    private final ScaleGestureDetector _scaleGestureDetector;
    public final ObservableField<Boolean> isEnableZoom = new ObservableField<>();
    public final ObservableField<Boolean> isEnableSeekBar = new ObservableField<>();
    public final ObservableField<Integer> currentProgress = new ObservableField<>(0);

    public QrCameraViewModel(QrcodeContract.IView iView, MainQrcodeCameraBinding mainQrcodeCameraBinding) {
        this._contractView = iView;
        this._binding = mainQrcodeCameraBinding;
        this._contractPresenter = new QrCameraViewPresenter(iView, mainQrcodeCameraBinding);
        this._scaleGestureDetector = new ScaleGestureDetector(iView.getActivityForIntent(), new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewModel.1
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                if (QrCameraViewModel.this._contractPresenter.getPlayingState()) {
                    QrCameraViewModel qrCameraViewModel = QrCameraViewModel.this;
                    qrCameraViewModel._orgProgress = qrCameraViewModel.currentProgress.get().intValue();
                    QrCameraViewModel.this._orgSpan = scaleGestureDetector.getCurrentSpan();
                    return super.onScaleBegin(scaleGestureDetector);
                }
                return false;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                QrCameraViewModel.this._orgProgress = -1;
                super.onScaleEnd(scaleGestureDetector);
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                int i = 0;
                if (scaleGestureDetector == null || QrCameraViewModel.this._orgProgress == -1 || !QrCameraViewModel.this._contractPresenter.getPlayingState()) {
                    return false;
                }
                int currentSpan = QrCameraViewModel.this._orgProgress + ((int) (((scaleGestureDetector.getCurrentSpan() - QrCameraViewModel.this._orgSpan) / QrCameraViewModel.this._binding.seekZoom.getWidth()) * QrCameraViewModel.this._binding.seekZoom.getMax()));
                if (currentSpan >= 0) {
                    i = currentSpan > QrCameraViewModel.this._binding.seekZoom.getMax() ? QrCameraViewModel.this._binding.seekZoom.getMax() : currentSpan;
                }
                QrCameraViewModel.this._contractPresenter.changeZoomLevel(i);
                QrCameraViewModel.this.currentProgress.set(Integer.valueOf(i));
                return true;
            }
        });
        mainQrcodeCameraBinding.qrSurfaceview.setOnTouchListener(this);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IViewModel
    public void onResume() {
        initializeState(true);
        this._contractPresenter.onResume();
        setZoomVisibility();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IViewModel
    public void onPause() {
        this._contractPresenter.onPause();
    }

    private void initializeState(boolean z) {
        this.isEnableZoom.set(Boolean.valueOf(z));
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ScaleGestureDetector scaleGestureDetector;
        if (motionEvent == null || (scaleGestureDetector = this._scaleGestureDetector) == null) {
            return false;
        }
        return scaleGestureDetector.onTouchEvent(motionEvent);
    }

    public void onValueChanged(SeekBar seekBar, int i, boolean z) {
        this._contractPresenter.changeZoomLevel(i);
    }

    public void onClickInfo(View view) {
        this._contractView.showInformationDialog();
    }

    private void setZoomVisibility() {
        if (this._contractPresenter.isAvailableZoom()) {
            enableSeekBar();
        } else {
            disableSeekBar();
        }
    }

    private void enableSeekBar() {
        if (this.isEnableZoom.get().booleanValue()) {
            return;
        }
        this.isEnableSeekBar.set(true);
        this.isEnableZoom.set(true);
    }

    private void disableSeekBar() {
        if (this.isEnableZoom.get().booleanValue()) {
            this.isEnableSeekBar.set(false);
            this.isEnableZoom.set(false);
        }
    }
}
