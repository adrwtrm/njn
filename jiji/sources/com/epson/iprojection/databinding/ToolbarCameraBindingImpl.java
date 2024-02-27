package com.epson.iprojection.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;
import com.epson.iprojection.generated.callback.OnClickListener;
import com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewModel;

/* loaded from: classes.dex */
public class ToolbarCameraBindingImpl extends ToolbarCameraBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback1;
    private final View.OnClickListener mCallback2;
    private final View.OnClickListener mCallback3;
    private final View.OnClickListener mCallback4;
    private final View.OnClickListener mCallback5;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbarCamera, 7);
        sparseIntArray.put(R.id.left_guideline, 8);
        sparseIntArray.put(R.id.txt_titlebar_filename, 9);
    }

    public ToolbarCameraBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 10, sIncludes, sViewsWithIds));
    }

    private ToolbarCameraBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 5, (ImageButton) objArr[4], (ImageButton) objArr[2], (ImageButton) objArr[1], (ImageButton) objArr[5], (ImageButton) objArr[3], (View) objArr[6], (Guideline) objArr[8], (Toolbar) objArr[7], (TextView) objArr[9]);
        this.mDirtyFlags = -1L;
        this.btnProjection.setTag(null);
        this.cameraBtnExchange.setTag(null);
        this.cameraBtnLight.setTag(null);
        this.cameraBtnPaint.setTag(null);
        this.cameraBtnSave.setTag(null);
        this.cameraDummySpace.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(view);
        this.mCallback4 = new OnClickListener(this, 4);
        this.mCallback2 = new OnClickListener(this, 2);
        this.mCallback5 = new OnClickListener(this, 5);
        this.mCallback3 = new OnClickListener(this, 3);
        this.mCallback1 = new OnClickListener(this, 1);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
            setViewmodel((CameraViewModel) obj);
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.databinding.ToolbarCameraBinding
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
                    return onChangeViewmodelIsFlashAvailable((ObservableField) obj, i2);
                }
                return onChangeViewmodelIsCameraExchangeAvailable((ObservableField) obj, i2);
            }
            return onChangeViewmodelIsPreviewMode((ObservableField) obj, i2);
        }
        return onChangeViewmodelIsConnected((ObservableField) obj, i2);
    }

    private boolean onChangeViewmodelIsConnected(ObservableField<Boolean> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 1;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelIsPreviewMode(ObservableField<Boolean> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 2;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelIsCameraExchangeAvailable(ObservableField<Boolean> observableField, int i) {
        if (i == 0) {
            synchronized (this) {
                this.mDirtyFlags |= 4;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewmodelIsFlashAvailable(ObservableField<Boolean> observableField, int i) {
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
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00e5  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.databinding.ToolbarCameraBindingImpl.executeBindings():void");
    }

    @Override // com.epson.iprojection.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view) {
        if (i == 1) {
            CameraViewModel cameraViewModel = this.mViewmodel;
            if (cameraViewModel != null) {
                cameraViewModel.onClickActionBar(view);
            }
        } else if (i == 2) {
            CameraViewModel cameraViewModel2 = this.mViewmodel;
            if (cameraViewModel2 != null) {
                cameraViewModel2.onClickExchange(view);
            }
        } else if (i == 3) {
            CameraViewModel cameraViewModel3 = this.mViewmodel;
            if (cameraViewModel3 != null) {
                cameraViewModel3.onClickSavePhoto(view);
            }
        } else if (i == 4) {
            CameraViewModel cameraViewModel4 = this.mViewmodel;
            if (cameraViewModel4 != null) {
                cameraViewModel4.onClickActionBar(view);
            }
        } else if (i != 5) {
        } else {
            CameraViewModel cameraViewModel5 = this.mViewmodel;
            if (cameraViewModel5 != null) {
                cameraViewModel5.onClickActionBar(view);
            }
        }
    }
}
