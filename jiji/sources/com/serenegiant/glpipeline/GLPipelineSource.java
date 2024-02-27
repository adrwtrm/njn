package com.serenegiant.glpipeline;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.serenegiant.gl.GLManager;

/* loaded from: classes2.dex */
public interface GLPipelineSource extends GLPipeline {

    /* loaded from: classes2.dex */
    public interface PipelineSourceCallback {
        void onCreate(Surface surface);

        void onDestroy();
    }

    GLManager getGLManager() throws IllegalStateException;

    Surface getInputSurface() throws IllegalStateException;

    SurfaceTexture getInputSurfaceTexture() throws IllegalStateException;

    int getTexId();

    float[] getTexMatrix();
}
