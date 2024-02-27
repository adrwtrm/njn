package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewModel;

/* loaded from: classes.dex */
public abstract class ToolbarCameraBinding extends ViewDataBinding {
    public final ImageButton btnProjection;
    public final ImageButton cameraBtnExchange;
    public final ImageButton cameraBtnLight;
    public final ImageButton cameraBtnPaint;
    public final ImageButton cameraBtnSave;
    public final View cameraDummySpace;
    public final Guideline leftGuideline;
    @Bindable
    protected CameraViewModel mViewmodel;
    public final Toolbar toolbarCamera;
    public final TextView txtTitlebarFilename;

    public abstract void setViewmodel(CameraViewModel cameraViewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarCameraBinding(Object obj, View view, int i, ImageButton imageButton, ImageButton imageButton2, ImageButton imageButton3, ImageButton imageButton4, ImageButton imageButton5, View view2, Guideline guideline, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnProjection = imageButton;
        this.cameraBtnExchange = imageButton2;
        this.cameraBtnLight = imageButton3;
        this.cameraBtnPaint = imageButton4;
        this.cameraBtnSave = imageButton5;
        this.cameraDummySpace = view2;
        this.leftGuideline = guideline;
        this.toolbarCamera = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public CameraViewModel getViewmodel() {
        return this.mViewmodel;
    }

    public static ToolbarCameraBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarCameraBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarCameraBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_camera, viewGroup, z, obj);
    }

    public static ToolbarCameraBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarCameraBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarCameraBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_camera, null, false, obj);
    }

    public static ToolbarCameraBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarCameraBinding bind(View view, Object obj) {
        return (ToolbarCameraBinding) bind(obj, view, R.layout.toolbar_camera);
    }
}
