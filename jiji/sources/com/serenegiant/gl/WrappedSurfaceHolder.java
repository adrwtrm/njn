package com.serenegiant.gl;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceHolder;

/* loaded from: classes2.dex */
public class WrappedSurfaceHolder implements SurfaceHolder {
    private final Surface surface;

    @Override // android.view.SurfaceHolder
    public void addCallback(SurfaceHolder.Callback callback) {
    }

    @Override // android.view.SurfaceHolder
    public Rect getSurfaceFrame() {
        return null;
    }

    @Override // android.view.SurfaceHolder
    public boolean isCreating() {
        return false;
    }

    @Override // android.view.SurfaceHolder
    public Canvas lockCanvas() {
        return null;
    }

    @Override // android.view.SurfaceHolder
    public Canvas lockCanvas(Rect rect) {
        return null;
    }

    @Override // android.view.SurfaceHolder
    public void removeCallback(SurfaceHolder.Callback callback) {
    }

    @Override // android.view.SurfaceHolder
    public void setFixedSize(int i, int i2) {
    }

    @Override // android.view.SurfaceHolder
    public void setFormat(int i) {
    }

    @Override // android.view.SurfaceHolder
    public void setKeepScreenOn(boolean z) {
    }

    @Override // android.view.SurfaceHolder
    public void setSizeFromLayout() {
    }

    @Override // android.view.SurfaceHolder
    public void setType(int i) {
    }

    @Override // android.view.SurfaceHolder
    public void unlockCanvasAndPost(Canvas canvas) {
    }

    public WrappedSurfaceHolder(Surface surface) {
        this.surface = surface;
    }

    @Override // android.view.SurfaceHolder
    public Surface getSurface() {
        return this.surface;
    }
}
