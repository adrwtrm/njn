package com.epson.iprojection.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;
import com.epson.iprojection.R;
import com.epson.iprojection.generated.callback.OnClickListener;
import com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewModel;
import com.epson.iprojection.ui.activities.camera.viewmodel.PreviewTextureView;
import com.epson.iprojection.ui.activities.camera.views.adapters.DataBindingAdapters;

/* loaded from: classes.dex */
public class MainCameraBindingImpl extends MainCameraBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback7;
    private long mDirtyFlags;
    private OnProgressChangedImpl mViewmodelOnValueChangedAndroidxDatabindingAdaptersSeekBarBindingAdapterOnProgressChanged;
    private final FrameLayout mboundView1;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(9);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"toolbar_camera"}, new int[]{6}, new int[]{R.layout.toolbar_camera});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.cam_surfaceview, 7);
        sparseIntArray.put(R.id.camera_layout_zoom, 8);
    }

    public MainCameraBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 9, sIncludes, sViewsWithIds));
    }

    private MainCameraBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 5, (ImageButton) objArr[2], (PreviewTextureView) objArr[7], (ConstraintLayout) objArr[8], (DrawerLayout) objArr[0], (ImageView) objArr[5], (ImageView) objArr[3], (AppCompatSeekBar) objArr[4], (ToolbarCameraBinding) objArr[6]);
        this.mDirtyFlags = -1L;
        this.btnPlayPause.setTag(null);
        this.drawerLayout.setTag(null);
        this.imgZoomin.setTag(null);
        this.imgZoomout.setTag(null);
        FrameLayout frameLayout = (FrameLayout) objArr[1];
        this.mboundView1 = frameLayout;
        frameLayout.setTag(null);
        this.seekZoom.setTag(null);
        setContainedBinding(this.toolbarCamera);
        setRootTag(view);
        this.mCallback7 = new OnClickListener(this, 1);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
        }
        this.toolbarCamera.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.toolbarCamera.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (2 == i) {
            setViewmodel((CameraViewModel) obj);
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.databinding.MainCameraBinding
    public void setViewmodel(CameraViewModel cameraViewModel) {
        updateRegistration(4, cameraViewModel);
        this.mViewmodel = cameraViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.toolbarCamera.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            return false;
                        }
                        return onChangeViewmodel((CameraViewModel) obj, i2);
                    }
                    return onChangeViewmodelIsEnableZoom((ObservableField) obj, i2);
                }
                return onChangeViewmodelIsEnableSeekBar((ObservableField) obj, i2);
            }
            return onChangeViewmodelCurrentProgress((ObservableField) obj, i2);
        }
        return onChangeToolbarCamera((ToolbarCameraBinding) obj, i2);
    }

    private boolean onChangeToolbarCamera(ToolbarCameraBinding toolbarCameraBinding, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 1;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelCurrentProgress(ObservableField<Integer> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 2;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelIsEnableSeekBar(ObservableField<Boolean> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 4;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelIsEnableZoom(ObservableField<Boolean> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 8;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodel(CameraViewModel cameraViewModel, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 16;
            }
            return true;
        } else if (i == 1) {
            synchronized (this) {
                this.mDirtyFlags |= 32;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        boolean z;
        int i2;
        Drawable drawable;
        OnProgressChangedImpl onProgressChangedImpl;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        CameraViewModel cameraViewModel = this.mViewmodel;
        int i3 = 0;
        if ((126 & j) != 0) {
            drawable = ((j & 112) == 0 || cameraViewModel == null) ? null : cameraViewModel.getImageRes();
            if ((j & 82) != 0) {
                ObservableField<Integer> observableField = cameraViewModel != null ? cameraViewModel.currentProgress : null;
                updateRegistration(1, observableField);
                i2 = ViewDataBinding.safeUnbox(observableField != null ? observableField.get() : null);
            } else {
                i2 = 0;
            }
            if ((j & 80) == 0 || cameraViewModel == null) {
                onProgressChangedImpl = null;
            } else {
                OnProgressChangedImpl onProgressChangedImpl2 = this.mViewmodelOnValueChangedAndroidxDatabindingAdaptersSeekBarBindingAdapterOnProgressChanged;
                if (onProgressChangedImpl2 == null) {
                    onProgressChangedImpl2 = new OnProgressChangedImpl();
                    this.mViewmodelOnValueChangedAndroidxDatabindingAdaptersSeekBarBindingAdapterOnProgressChanged = onProgressChangedImpl2;
                }
                onProgressChangedImpl = onProgressChangedImpl2.setValue(cameraViewModel);
            }
            int i4 = ((j & 84) > 0L ? 1 : ((j & 84) == 0L ? 0 : -1));
            if (i4 != 0) {
                ObservableField<Boolean> observableField2 = cameraViewModel != null ? cameraViewModel.isEnableSeekBar : null;
                updateRegistration(2, observableField2);
                z = ViewDataBinding.safeUnbox(observableField2 != null ? observableField2.get() : null);
                if (i4 != 0) {
                    j |= z ? 1024L : 512L;
                }
            } else {
                z = false;
            }
            int i5 = ((j & 88) > 0L ? 1 : ((j & 88) == 0L ? 0 : -1));
            if (i5 != 0) {
                ObservableField<Boolean> observableField3 = cameraViewModel != null ? cameraViewModel.isEnableZoom : null;
                updateRegistration(3, observableField3);
                boolean safeUnbox = ViewDataBinding.safeUnbox(observableField3 != null ? observableField3.get() : null);
                if (i5 != 0) {
                    j |= safeUnbox ? 256L : 128L;
                }
                if (!safeUnbox) {
                    i3 = 8;
                }
            }
            i = i3;
        } else {
            i = 0;
            z = false;
            i2 = 0;
            drawable = null;
            onProgressChangedImpl = null;
        }
        if ((j & 64) != 0) {
            this.btnPlayPause.setOnClickListener(this.mCallback7);
        }
        if ((112 & j) != 0) {
            DataBindingAdapters.setImageDrawable(this.btnPlayPause, drawable);
        }
        if ((88 & j) != 0) {
            this.imgZoomin.setVisibility(i);
            this.imgZoomout.setVisibility(i);
            this.seekZoom.setVisibility(i);
        }
        if ((j & 82) != 0) {
            SeekBarBindingAdapter.setProgress(this.seekZoom, i2);
        }
        if ((84 & j) != 0) {
            this.seekZoom.setSaveEnabled(z);
        }
        if ((j & 80) != 0) {
            SeekBarBindingAdapter.setOnSeekBarChangeListener(this.seekZoom, null, null, onProgressChangedImpl, null);
            this.toolbarCamera.setViewmodel(cameraViewModel);
        }
        executeBindingsOn(this.toolbarCamera);
    }

    /* loaded from: classes.dex */
    public static class OnProgressChangedImpl implements SeekBarBindingAdapter.OnProgressChanged {
        private CameraViewModel value;

        public OnProgressChangedImpl setValue(CameraViewModel cameraViewModel) {
            this.value = cameraViewModel;
            if (cameraViewModel == null) {
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
        CameraViewModel cameraViewModel = this.mViewmodel;
        if (cameraViewModel != null) {
            cameraViewModel.onClickPause(view);
        }
    }
}
