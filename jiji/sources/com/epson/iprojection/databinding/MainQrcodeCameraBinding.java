package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewModel;
import com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrTextureView;

/* loaded from: classes.dex */
public abstract class MainQrcodeCameraBinding extends ViewDataBinding {
    public final ConstraintLayout cameraLayoutZoom;
    public final ImageView imgHomeQrInfo;
    public final ImageView imgHomeQrInfoback;
    public final ImageView imgZoomin;
    public final ImageView imgZoomout;
    @Bindable
    protected QrCameraViewModel mViewmodel;
    public final QrTextureView qrSurfaceview;
    public final AppCompatSeekBar seekZoom;

    public abstract void setViewmodel(QrCameraViewModel qrCameraViewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public MainQrcodeCameraBinding(Object obj, View view, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, QrTextureView qrTextureView, AppCompatSeekBar appCompatSeekBar) {
        super(obj, view, i);
        this.cameraLayoutZoom = constraintLayout;
        this.imgHomeQrInfo = imageView;
        this.imgHomeQrInfoback = imageView2;
        this.imgZoomin = imageView3;
        this.imgZoomout = imageView4;
        this.qrSurfaceview = qrTextureView;
        this.seekZoom = appCompatSeekBar;
    }

    public QrCameraViewModel getViewmodel() {
        return this.mViewmodel;
    }

    public static MainQrcodeCameraBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MainQrcodeCameraBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (MainQrcodeCameraBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.main_qrcode_camera, viewGroup, z, obj);
    }

    public static MainQrcodeCameraBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MainQrcodeCameraBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (MainQrcodeCameraBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.main_qrcode_camera, null, false, obj);
    }

    public static MainQrcodeCameraBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MainQrcodeCameraBinding bind(View view, Object obj) {
        return (MainQrcodeCameraBinding) bind(obj, view, R.layout.main_qrcode_camera);
    }
}
