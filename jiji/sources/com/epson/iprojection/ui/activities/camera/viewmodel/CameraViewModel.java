package com.epson.iprojection.ui.activities.camera.viewmodel;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import com.epson.iprojection.R;
import com.epson.iprojection.databinding.MainCameraBinding;
import com.epson.iprojection.ui.activities.camera.models.CameraContract;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class CameraViewModel extends BaseObservable implements CameraContract.IViewModel, View.OnTouchListener {
    private final MainCameraBinding _binding;
    private final CameraContract.IPresenter _contractPresenter;
    private final CameraContract.IView _contractView;
    private boolean _hasFlash;
    private int _orgProgress;
    private float _orgSpan;
    private final ScaleGestureDetector _scaleGestureDetector;
    public final ObservableField<Boolean> isPreviewMode = new ObservableField<>();
    public final ObservableField<Boolean> isFlashAvailable = new ObservableField<>();
    public final ObservableField<Boolean> isCameraExchangeAvailable = new ObservableField<>(true);
    public final ObservableField<Boolean> isEnableZoom = new ObservableField<>();
    public final ObservableField<Boolean> isEnableSeekBar = new ObservableField<>();
    public final ObservableField<Boolean> isConnected = new ObservableField<>();
    public final ObservableField<Integer> currentProgress = new ObservableField<>(0);

    public CameraViewModel(CameraContract.IView iView, MainCameraBinding mainCameraBinding, int i, String str, boolean z) {
        this._contractView = iView;
        this._binding = mainCameraBinding;
        this._contractPresenter = new CameraViewPresenter(iView, mainCameraBinding, str);
        this._scaleGestureDetector = new ScaleGestureDetector(iView.getActivityForIntent(), new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewModel.1
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                if (CameraViewModel.this._contractPresenter.getPlayingState()) {
                    CameraViewModel cameraViewModel = CameraViewModel.this;
                    cameraViewModel._orgProgress = cameraViewModel.currentProgress.get().intValue();
                    CameraViewModel.this._orgSpan = scaleGestureDetector.getCurrentSpan();
                    return super.onScaleBegin(scaleGestureDetector);
                }
                return false;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                CameraViewModel.this._orgProgress = -1;
                super.onScaleEnd(scaleGestureDetector);
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                int i2 = 0;
                if (scaleGestureDetector == null || CameraViewModel.this._orgProgress == -1 || !CameraViewModel.this._contractPresenter.getPlayingState()) {
                    return false;
                }
                int currentSpan = CameraViewModel.this._orgProgress + ((int) (((scaleGestureDetector.getCurrentSpan() - CameraViewModel.this._orgSpan) * CameraViewModel.this._binding.seekZoom.getMax()) / CameraViewModel.this._binding.seekZoom.getWidth()));
                if (currentSpan >= 0) {
                    i2 = currentSpan > CameraViewModel.this._binding.seekZoom.getMax() ? CameraViewModel.this._binding.seekZoom.getMax() : currentSpan;
                }
                CameraViewModel.this._contractPresenter.changeZoomLevel(i2);
                CameraViewModel.this.currentProgress.set(Integer.valueOf(i2));
                return true;
            }
        });
        mainCameraBinding.camSurfaceview.setOnTouchListener(this);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void onResume() {
        initializeState(true);
        this._contractPresenter.onResume();
        resetIconVisibility();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void onPause() {
        initializeState(false);
        this._contractPresenter.onPause();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void onDestroy() {
        this._contractPresenter.onDestroy();
    }

    private void initializeState(boolean z) {
        this.isPreviewMode.set(Boolean.valueOf(z));
        notifyPropertyChanged(1);
        this.isEnableZoom.set(Boolean.valueOf(z));
        this.isEnableSeekBar.set(Boolean.valueOf(z));
        this.isFlashAvailable.set(Boolean.valueOf(z));
        this.isCameraExchangeAvailable.set(Boolean.valueOf(z));
        updateToolBar();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void updateToolBar() {
        this.isConnected.set(Boolean.valueOf(Pj.getIns().isConnected()));
    }

    public void onClickActionBar(View view) {
        this._contractPresenter.onClickEvent(view);
        resetIconVisibility();
    }

    public void onClickExchange(View view) {
        this._contractPresenter.onClickEvent(view);
        this.isPreviewMode.set(true);
        this.currentProgress.set(0);
        this._orgProgress = 0;
        resetIconVisibility();
    }

    public void onClickSavePhoto(View view) {
        setSaveButtonEnable(false, 76);
        onClickActionBar(view);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void setSaveButtonEnable(boolean z, int i) {
        ImageButton imageButton = this._binding.toolbarCamera.cameraBtnSave;
        imageButton.setImageAlpha(i);
        imageButton.setClickable(z);
        imageButton.setEnabled(z);
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
        this.currentProgress.set(Integer.valueOf(i));
    }

    public void onClickPause(View view) {
        this._contractPresenter.onClickEvent(view);
        notifyPropertyChanged(1);
        ObservableField<Boolean> observableField = this.isPreviewMode;
        observableField.set(Boolean.valueOf(true ^ observableField.get().booleanValue()));
        resetIconVisibility();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public int getLastOrientationNum() {
        return this._contractPresenter.getLastOrientationNum();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public String getLastCameraId() {
        return this._contractPresenter.getLastCameraId();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public boolean getPlayingState() {
        return this._contractPresenter.getPlayingState();
    }

    @Bindable
    public Drawable getImageRes() {
        if (this.isPreviewMode.get().booleanValue()) {
            return this._contractView.getDrawableById(R.drawable.camera_pause);
        }
        return this._contractView.getDrawableById(R.drawable.camera_play);
    }

    private void resetIconVisibility() {
        setFlashVisibility();
        setCameraExchangeVisibility();
        setZoomVisibility();
    }

    private void setFlashVisibility() {
        if (this._contractPresenter.isNowCameraFacingBack() && this._contractPresenter.hasFlash() && this.isPreviewMode.get().booleanValue()) {
            this.isFlashAvailable.set(true);
        } else {
            this.isFlashAvailable.set(false);
        }
    }

    private void setZoomVisibility() {
        if (this.isPreviewMode.get().booleanValue() && this._contractPresenter.isAvailableZoom()) {
            enableSeekBar();
        } else {
            disableSeekBar();
        }
    }

    private void setCameraExchangeVisibility() {
        if (2 > this._contractPresenter.hasSomeCameras()) {
            this.isCameraExchangeAvailable.set(false);
        } else if (!this.isPreviewMode.get().booleanValue()) {
            this.isCameraExchangeAvailable.set(false);
        } else {
            this.isCameraExchangeAvailable.set(true);
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

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void save() {
        this._contractPresenter.save();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IViewModel
    public void showSaveFailed() {
        this._contractPresenter.showSaveFailed();
    }
}
