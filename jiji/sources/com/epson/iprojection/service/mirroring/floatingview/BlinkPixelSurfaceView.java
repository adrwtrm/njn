package com.epson.iprojection.service.mirroring.floatingview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlinkPixelSurfaceView.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0011\u001a\u00020\rJ(\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tH\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/BlinkPixelSurfaceView;", "Landroid/view/SurfaceView;", "Landroid/view/SurfaceHolder$Callback;", "context", "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "_counter", "", "_isRunning", "", "draw", "", "holder", "Landroid/view/SurfaceHolder;", "start", "stop", "surfaceChanged", "format", "width", "height", "surfaceCreated", "surfaceDestroyed", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class BlinkPixelSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private int _counter;
    private boolean _isRunning;

    /* renamed from: $r8$lambda$zMbRJC60X0v-jSt6y28qx-ANhqo */
    public static /* synthetic */ void m75$r8$lambda$zMbRJC60X0vjSt6y28qxANhqo(BlinkPixelSurfaceView blinkPixelSurfaceView, SurfaceHolder surfaceHolder) {
        surfaceCreated$lambda$0(blinkPixelSurfaceView, surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(holder, "holder");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlinkPixelSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
        this._isRunning = true;
        getHolder().addCallback(this);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(final SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        new Thread(new Runnable() { // from class: com.epson.iprojection.service.mirroring.floatingview.BlinkPixelSurfaceView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BlinkPixelSurfaceView.m75$r8$lambda$zMbRJC60X0vjSt6y28qxANhqo(BlinkPixelSurfaceView.this, holder);
            }
        }).start();
    }

    public static final void surfaceCreated$lambda$0(BlinkPixelSurfaceView this$0, SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        while (this$0._isRunning) {
            this$0.draw(holder);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this._isRunning = false;
    }

    public final void start() {
        this._isRunning = true;
    }

    public final void stop() {
        this._isRunning = false;
    }

    private final void draw(SurfaceHolder surfaceHolder) {
        Canvas lockCanvas = surfaceHolder.lockCanvas();
        if (lockCanvas == null) {
            return;
        }
        lockCanvas.drawARGB(1, 1, 0, 0);
        surfaceHolder.unlockCanvasAndPost(lockCanvas);
        this._counter++;
    }
}
