package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewModel;
import com.epson.iprojection.ui.activities.camera.viewmodel.PreviewTextureView;

/* loaded from: classes.dex */
public abstract class MainCameraBinding extends ViewDataBinding {
    public final ImageButton btnPlayPause;
    public final PreviewTextureView camSurfaceview;
    public final ConstraintLayout cameraLayoutZoom;
    public final DrawerLayout drawerLayout;
    public final ImageView imgZoomin;
    public final ImageView imgZoomout;
    @Bindable
    protected CameraViewModel mViewmodel;
    public final AppCompatSeekBar seekZoom;
    public final ToolbarCameraBinding toolbarCamera;

    public abstract void setViewmodel(CameraViewModel cameraViewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public MainCameraBinding(Object obj, View view, int i, ImageButton imageButton, PreviewTextureView previewTextureView, ConstraintLayout constraintLayout, DrawerLayout drawerLayout, ImageView imageView, ImageView imageView2, AppCompatSeekBar appCompatSeekBar, ToolbarCameraBinding toolbarCameraBinding) {
        super(obj, view, i);
        this.btnPlayPause = imageButton;
        this.camSurfaceview = previewTextureView;
        this.cameraLayoutZoom = constraintLayout;
        this.drawerLayout = drawerLayout;
        this.imgZoomin = imageView;
        this.imgZoomout = imageView2;
        this.seekZoom = appCompatSeekBar;
        this.toolbarCamera = toolbarCameraBinding;
    }

    public CameraViewModel getViewmodel() {
        return this.mViewmodel;
    }

    public static MainCameraBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MainCameraBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (MainCameraBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.main_camera, viewGroup, z, obj);
    }

    public static MainCameraBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MainCameraBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (MainCameraBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.main_camera, null, false, obj);
    }

    public static MainCameraBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MainCameraBinding bind(View view, Object obj) {
        return (MainCameraBinding) bind(obj, view, R.layout.main_camera);
    }
}
