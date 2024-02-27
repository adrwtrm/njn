package com.epson.iprojection.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.generated.callback.OnClickListener;
import com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewModel;
import com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrTextureView;

/* loaded from: classes.dex */
public class MainQrcodeCameraBindingImpl extends MainQrcodeCameraBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback6;
    private long mDirtyFlags;
    private OnProgressChangedImpl mViewmodelOnValueChangedAndroidxDatabindingAdaptersSeekBarBindingAdapterOnProgressChanged;
    private final FrameLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.qr_surfaceview, 5);
        sparseIntArray.put(R.id.camera_layout_zoom, 6);
        sparseIntArray.put(R.id.img_home_qr_infoback, 7);
    }

    public MainQrcodeCameraBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 8, sIncludes, sViewsWithIds));
    }

    private MainQrcodeCameraBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 3, (ConstraintLayout) objArr[6], (ImageView) objArr[4], (ImageView) objArr[7], (ImageView) objArr[3], (ImageView) objArr[1], (QrTextureView) objArr[5], (AppCompatSeekBar) objArr[2]);
        this.mDirtyFlags = -1L;
        this.imgHomeQrInfo.setTag(null);
        this.imgZoomin.setTag(null);
        this.imgZoomout.setTag(null);
        FrameLayout frameLayout = (FrameLayout) objArr[0];
        this.mboundView0 = frameLayout;
        frameLayout.setTag(null);
        this.seekZoom.setTag(null);
        setRootTag(view);
        this.mCallback6 = new OnClickListener(this, 1);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (2 == i) {
            setViewmodel((QrCameraViewModel) obj);
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.databinding.MainQrcodeCameraBinding
    public void setViewmodel(QrCameraViewModel qrCameraViewModel) {
        updateRegistration(1, qrCameraViewModel);
        this.mViewmodel = qrCameraViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
                return onChangeViewmodelCurrentProgress((ObservableField) obj, i2);
            }
            return onChangeViewmodel((QrCameraViewModel) obj, i2);
        }
        return onChangeViewmodelIsEnableZoom((ObservableField) obj, i2);
    }

    private boolean onChangeViewmodelIsEnableZoom(ObservableField<Boolean> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 1;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodel(QrCameraViewModel qrCameraViewModel, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 2;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelCurrentProgress(ObservableField<Integer> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 4;
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0068  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 201
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.databinding.MainQrcodeCameraBindingImpl.executeBindings():void");
    }

    /* loaded from: classes.dex */
    public static class OnProgressChangedImpl implements SeekBarBindingAdapter.OnProgressChanged {
        private QrCameraViewModel value;

        public OnProgressChangedImpl setValue(QrCameraViewModel qrCameraViewModel) {
            this.value = qrCameraViewModel;
            if (qrCameraViewModel == null) {
                return null;
            }
            return this;
        }

        @Override // androidx.databinding.adapters.SeekBarBindingAdapter.OnProgressChanged
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            this.value.onValueChanged(seekBar, i, z);
        }
    }

    @Override // com.epson.iprojection.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view) {
        QrCameraViewModel qrCameraViewModel = this.mViewmodel;
        if (qrCameraViewModel != null) {
            qrCameraViewModel.onClickInfo(view);
        }
    }
}
