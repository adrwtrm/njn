package com.serenegiant.camera;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;

/* loaded from: classes2.dex */
public interface ISurfacePreview extends IPreview {
    void setPreviewSurface(SurfaceTexture surfaceTexture);

    void setPreviewSurface(Surface surface);

    void setPreviewSurface(SurfaceHolder surfaceHolder);
}
