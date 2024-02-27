package com.serenegiant.glpipeline;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.view.Choreographer;
import android.view.Surface;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLTexture;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class ImageSourcePipeline extends ProxyPipeline implements GLPipelineSource {
    private static final boolean DEBUG = false;
    private static final String TAG = "ImageSourcePipeline";
    private int cnt;
    private volatile long mFrameIntervalNs;
    private GLTexture mImageSource;
    private final GLManager mManager;
    private final Object mSync = new Object();
    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() { // from class: com.serenegiant.glpipeline.ImageSourcePipeline.4
        private long prevFrameTimeNs;

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            if (ImageSourcePipeline.this.isValid()) {
                long j2 = (ImageSourcePipeline.this.mFrameIntervalNs - (j - this.prevFrameTimeNs)) / 1000000;
                this.prevFrameTimeNs = j;
                if (j2 <= 0) {
                    ImageSourcePipeline.this.mManager.postFrameCallbackDelayed(this, 0L);
                } else {
                    ImageSourcePipeline.this.mManager.postFrameCallbackDelayed(this, j2);
                }
                synchronized (ImageSourcePipeline.this.mSync) {
                    if (ImageSourcePipeline.this.mImageSource != null) {
                        ImageSourcePipeline imageSourcePipeline = ImageSourcePipeline.this;
                        imageSourcePipeline.onFrameAvailable(false, imageSourcePipeline.mImageSource.getTexId(), ImageSourcePipeline.this.mImageSource.getTexMatrix());
                    }
                }
            }
        }
    };

    public ImageSourcePipeline(GLManager gLManager, final Bitmap bitmap, final Fraction fraction) {
        this.mManager = gLManager;
        if (bitmap != null) {
            gLManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.ImageSourcePipeline.1
                @Override // java.lang.Runnable
                public void run() {
                    ImageSourcePipeline.this.createImageSource(bitmap, fraction);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.ImageSourcePipeline.2
                @Override // java.lang.Runnable
                public void run() {
                    ImageSourcePipeline.this.releaseImageSource();
                }
            });
        }
        super.internalRelease();
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public GLManager getGLManager() throws IllegalStateException {
        return this.mManager;
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public SurfaceTexture getInputSurfaceTexture() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("");
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public Surface getInputSurface() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("");
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public int getTexId() throws IllegalStateException {
        GLTexture gLTexture;
        int texId;
        synchronized (this.mSync) {
            if (!isValid() || (gLTexture = this.mImageSource) == null) {
                throw new IllegalStateException("already released or image not set yet.");
            }
            texId = gLTexture != null ? gLTexture.getTexId() : 0;
        }
        return texId;
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public float[] getTexMatrix() throws IllegalStateException {
        GLTexture gLTexture;
        float[] texMatrix;
        synchronized (this.mSync) {
            if (!isValid() || (gLTexture = this.mImageSource) == null) {
                throw new IllegalStateException("already released or image not set yet.");
            }
            texMatrix = gLTexture.getTexMatrix();
        }
        return texMatrix;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return super.isValid() && this.mManager.isValid();
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        synchronized (this.mSync) {
            if (isValid() && this.mImageSource != null) {
                super.onFrameAvailable(z, i, fArr);
            }
        }
    }

    public void setSource(final Bitmap bitmap, final Fraction fraction) {
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.ImageSourcePipeline.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (ImageSourcePipeline.this.mSync) {
                    Bitmap bitmap2 = bitmap;
                    if (bitmap2 == null) {
                        ImageSourcePipeline.this.releaseImageSource();
                    } else {
                        ImageSourcePipeline.this.createImageSource(bitmap2, fraction);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseImageSource() {
        this.mManager.removeFrameCallback(this.mFrameCallback);
        synchronized (this.mSync) {
            GLTexture gLTexture = this.mImageSource;
            if (gLTexture != null) {
                gLTexture.release();
                this.mImageSource = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createImageSource(Bitmap bitmap, Fraction fraction) {
        this.mManager.removeFrameCallback(this.mFrameCallback);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean z = (getWidth() == width && getHeight() == height) ? false : true;
        float asFloat = fraction != null ? fraction.asFloat() : 30.0f;
        synchronized (this.mSync) {
            if (this.mImageSource == null || z) {
                releaseImageSource();
                this.mImageSource = GLTexture.newInstance(33984, width, height);
                GLUtils.checkGlError("createImageSource");
            }
            this.mImageSource.loadBitmap(bitmap);
            this.mFrameIntervalNs = Math.round(1.0E9d / asFloat);
        }
        if (z) {
            resize(width, height);
        }
        this.mManager.postFrameCallbackDelayed(this.mFrameCallback, 0L);
    }
}
