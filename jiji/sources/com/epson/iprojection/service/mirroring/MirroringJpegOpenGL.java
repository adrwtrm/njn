package com.epson.iprojection.service.mirroring;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.epson.iprojection.R;
import com.epson.iprojection.service.mirroring.SurfaceDrawable;
import com.epson.iprojection.service.mirroring.floatingview.BlinkPixelView;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringUtils;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringJpegOpenGL.kt */
@Metadata(d1 = {"\u0000G\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0014\u001a\u00020\u000fH\u0002J\u0018\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\b\u0010\u001a\u001a\u00020\u000fH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\r¨\u0006\u001b"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringJpegOpenGL;", "Lcom/epson/iprojection/service/mirroring/MirroringJpeg;", "()V", "_pixelView", "Lcom/epson/iprojection/service/mirroring/floatingview/BlinkPixelView;", "_pixelViewGroup", "Landroid/view/ViewGroup;", "_resolution", "Landroid/util/Size;", "_surfaceDrawable", "Lcom/epson/iprojection/service/mirroring/SurfaceDrawable;", "_surfaceDrawableCallback", "com/epson/iprojection/service/mirroring/MirroringJpegOpenGL$_surfaceDrawableCallback$1", "Lcom/epson/iprojection/service/mirroring/MirroringJpegOpenGL$_surfaceDrawableCallback$1;", "addBlinkPixelView", "", "context", "Landroid/content/Context;", "isDeviceSupported", "", "removeBlinkPixelView", "start", "captureIntent", "Landroid/content/Intent;", "startImage", "stop", "stopImage", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringJpegOpenGL extends MirroringJpeg {
    private BlinkPixelView _pixelView;
    private ViewGroup _pixelViewGroup;
    private Size _resolution;
    private SurfaceDrawable _surfaceDrawable;
    private final MirroringJpegOpenGL$_surfaceDrawableCallback$1 _surfaceDrawableCallback = new SurfaceDrawable.Callback() { // from class: com.epson.iprojection.service.mirroring.MirroringJpegOpenGL$_surfaceDrawableCallback$1
        @Override // com.epson.iprojection.service.mirroring.SurfaceDrawable.Callback
        public void onDestroySurface() {
        }

        @Override // com.epson.iprojection.service.mirroring.SurfaceDrawable.Callback
        public void onCreateSurface(Surface surface) {
            Size size;
            Size size2;
            SurfaceDrawable surfaceDrawable;
            Intrinsics.checkNotNullParameter(surface, "surface");
            if (MirroringJpegOpenGL.this.get_isStopped() || MirroringJpegOpenGL.this.get_context() == null) {
                return;
            }
            size = MirroringJpegOpenGL.this._resolution;
            SurfaceDrawable surfaceDrawable2 = null;
            if (size == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_resolution");
                size = null;
            }
            int width = size.getWidth();
            size2 = MirroringJpegOpenGL.this._resolution;
            if (size2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_resolution");
                size2 = null;
            }
            int height = size2.getHeight();
            MirroringJpegOpenGL mirroringJpegOpenGL = MirroringJpegOpenGL.this;
            MediaProjection mediaProjection = mirroringJpegOpenGL.get_mediaProjection();
            Intrinsics.checkNotNull(mediaProjection);
            Context context = MirroringJpegOpenGL.this.get_context();
            Intrinsics.checkNotNull(context);
            int i = context.getResources().getDisplayMetrics().densityDpi;
            surfaceDrawable = MirroringJpegOpenGL.this._surfaceDrawable;
            if (surfaceDrawable == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_surfaceDrawable");
            } else {
                surfaceDrawable2 = surfaceDrawable;
            }
            mirroringJpegOpenGL.set_virtualDisplay(mediaProjection.createVirtualDisplay("Capturing Display", width, height, i, 16, surfaceDrawable2.getSurface(), null, null));
        }

        @Override // com.epson.iprojection.service.mirroring.SurfaceDrawable.Callback
        public void onSurfaceChanged(Bitmap bitmap) {
            if (MirroringJpegOpenGL.this.get_isAVMuting() || MirroringJpegOpenGL.this.get_isStopped() || MirroringJpegOpenGL.this.get_isRotating() || bitmap == null) {
                return;
            }
            Pj.getIns().sendMirroringImage(bitmap);
        }
    };

    @Override // com.epson.iprojection.service.mirroring.MirroringJpeg, com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void start(Context context, Intent captureIntent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        super.start(context, captureIntent);
        addBlinkPixelView(context);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringJpeg
    public void startImage(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isDeviceSupported(context)) {
            notifyContentRect();
            this._resolution = MirroringUtils.Companion.getVirtualDisplayResolution(context, new Size(Pj.getIns().getPjResWidth(), Pj.getIns().getPjResHeight()));
            Size size = this._resolution;
            Size size2 = null;
            if (size == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_resolution");
                size = null;
            }
            int width = size.getWidth();
            Size size3 = this._resolution;
            if (size3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_resolution");
            } else {
                size2 = size3;
            }
            this._surfaceDrawable = new SurfaceDrawable(width, size2.getHeight(), this._surfaceDrawableCallback);
        }
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringJpeg, com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void stop() {
        super.stop();
        BlinkPixelView blinkPixelView = this._pixelView;
        if (blinkPixelView != null) {
            blinkPixelView.stop();
        }
        removeBlinkPixelView();
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringJpeg
    public void stopImage() {
        SurfaceDrawable surfaceDrawable = this._surfaceDrawable;
        if (surfaceDrawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_surfaceDrawable");
            surfaceDrawable = null;
        }
        surfaceDrawable.release();
        if (get_virtualDisplay() != null) {
            VirtualDisplay virtualDisplay = get_virtualDisplay();
            Intrinsics.checkNotNull(virtualDisplay);
            virtualDisplay.release();
            set_virtualDisplay(null);
        }
    }

    private final boolean isDeviceSupported(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        Intrinsics.checkNotNull(activityManager);
        ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
        Intrinsics.checkNotNullExpressionValue(deviceConfigurationInfo, "activityManager!!.deviceConfigurationInfo");
        return deviceConfigurationInfo.reqGlEsVersion >= 131072;
    }

    private final void addBlinkPixelView(Context context) {
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        Object systemService2 = context.getSystemService("layout_inflater");
        Intrinsics.checkNotNull(systemService2, "null cannot be cast to non-null type android.view.LayoutInflater");
        View inflate = ((LayoutInflater) systemService2).inflate(R.layout.floating_pixelview, (ViewGroup) null);
        Intrinsics.checkNotNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) inflate;
        this._pixelViewGroup = viewGroup;
        Intrinsics.checkNotNull(viewGroup);
        this._pixelView = (BlinkPixelView) viewGroup.findViewById(R.id.button_pixelview);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 56, -3);
        layoutParams.gravity = 5;
        layoutParams.x = 0;
        layoutParams.y = 0;
        ((WindowManager) systemService).addView(this._pixelViewGroup, layoutParams);
        BlinkPixelView blinkPixelView = this._pixelView;
        Intrinsics.checkNotNull(blinkPixelView);
        blinkPixelView.start();
    }

    private final void removeBlinkPixelView() {
        if (this._pixelViewGroup == null || get_context() == null) {
            return;
        }
        Context context = get_context();
        Intrinsics.checkNotNull(context);
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        ((WindowManager) systemService).removeView(this._pixelViewGroup);
        this._pixelViewGroup = null;
    }
}
