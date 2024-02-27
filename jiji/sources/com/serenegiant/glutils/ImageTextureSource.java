package com.serenegiant.glutils;

import android.graphics.Bitmap;
import android.view.Choreographer;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLTexture;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class ImageTextureSource implements GLConst, IMirror {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_HEIGHT = 480;
    private static final int DEFAULT_WIDTH = 640;
    private static final String TAG = "ImageTextureSource";
    private GLDrawer2D mDrawer;
    private volatile long mFrameIntervalNs;
    private GLTexture mImageSource;
    private final GLManager mManager;
    private RendererTarget mRendererTarget;
    private final Object mSync = new Object();
    private volatile boolean mReleased = false;
    private int mMirror = 0;
    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() { // from class: com.serenegiant.glutils.ImageTextureSource.6
        private long prevFrameTimeNs;

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            if (ImageTextureSource.this.isValid()) {
                long j2 = (ImageTextureSource.this.mFrameIntervalNs - (j - this.prevFrameTimeNs)) / 1000000;
                this.prevFrameTimeNs = j;
                if (j2 <= 0) {
                    ImageTextureSource.this.mManager.postFrameCallbackDelayed(this, 0L);
                } else {
                    ImageTextureSource.this.mManager.postFrameCallbackDelayed(this, j2);
                }
                synchronized (ImageTextureSource.this.mSync) {
                    if (ImageTextureSource.this.mImageSource != null) {
                        ImageTextureSource imageTextureSource = ImageTextureSource.this;
                        imageTextureSource.onFrameAvailable(imageTextureSource.mImageSource.getTexId(), ImageTextureSource.this.mImageSource.getTexMatrix());
                    }
                }
            }
        }
    };
    private int mWidth = 640;
    private int mHeight = 480;

    public ImageTextureSource(GLManager gLManager, final Bitmap bitmap, final Fraction fraction) {
        this.mManager = gLManager;
        if (bitmap != null) {
            gLManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.ImageTextureSource.1
                @Override // java.lang.Runnable
                public void run() {
                    ImageTextureSource.this.createImageSource(bitmap, fraction);
                }
            });
        }
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public final void release() {
        if (this.mReleased) {
            return;
        }
        this.mReleased = true;
        internalRelease();
    }

    protected void internalRelease() {
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.ImageTextureSource.2
                @Override // java.lang.Runnable
                public void run() {
                    ImageTextureSource.this.releaseImageSource();
                    ImageTextureSource.this.releaseTarget();
                }
            });
        }
    }

    public GLManager getGLManager() throws IllegalStateException {
        return this.mManager;
    }

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

    public boolean isValid() {
        return !this.mReleased && this.mManager.isValid();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean hasSurface() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mRendererTarget != null;
        }
        return z;
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(final int i) {
        synchronized (this.mSync) {
            if (this.mMirror != i) {
                this.mMirror = i;
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.ImageTextureSource$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ImageTextureSource.this.m270lambda$setMirror$0$comserenegiantglutilsImageTextureSource(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setMirror$0$com-serenegiant-glutils-ImageTextureSource  reason: not valid java name */
    public /* synthetic */ void m270lambda$setMirror$0$comserenegiantglutilsImageTextureSource(int i) {
        RendererTarget rendererTarget = this.mRendererTarget;
        if (rendererTarget != null) {
            rendererTarget.setMirror(i);
        }
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        int i;
        synchronized (this.mSync) {
            i = this.mMirror;
        }
        return i;
    }

    public void setSource(final Bitmap bitmap, final Fraction fraction) {
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.ImageTextureSource.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (ImageTextureSource.this.mSync) {
                    Bitmap bitmap2 = bitmap;
                    if (bitmap2 == null) {
                        ImageTextureSource.this.releaseImageSource();
                    } else {
                        ImageTextureSource.this.createImageSource(bitmap2, fraction);
                    }
                }
            }
        });
    }

    public void setSurface(Object obj) throws IllegalStateException, IllegalArgumentException {
        setSurface(obj, null);
    }

    public void setSurface(final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        if (!isValid()) {
            throw new IllegalStateException("already released?");
        }
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.ImageTextureSource.4
            @Override // java.lang.Runnable
            public void run() {
                ImageTextureSource.this.createTargetOnGL(obj, fraction);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFrameAvailable(int i, float[] fArr) {
        GLDrawer2D gLDrawer2D;
        RendererTarget rendererTarget;
        if (isValid()) {
            synchronized (this.mSync) {
                gLDrawer2D = this.mDrawer;
                rendererTarget = this.mRendererTarget;
            }
            if (gLDrawer2D == null || rendererTarget == null || !rendererTarget.canDraw()) {
                return;
            }
            rendererTarget.draw(gLDrawer2D, 33984, i, fArr);
        }
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
            this.mWidth = width;
            this.mHeight = height;
        }
        this.mManager.postFrameCallbackDelayed(this.mFrameCallback, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTargetOnGL(Object obj, Fraction fraction) {
        synchronized (this.mSync) {
            synchronized (this.mSync) {
                RendererTarget rendererTarget = this.mRendererTarget;
                if (rendererTarget != null && rendererTarget.getSurface() != obj) {
                    this.mRendererTarget.release();
                    this.mRendererTarget = null;
                }
                if (this.mRendererTarget == null && obj != null) {
                    RendererTarget newInstance = RendererTarget.newInstance(this.mManager.getEgl(), obj, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mRendererTarget = newInstance;
                    newInstance.setMirror(this.mMirror);
                    if (this.mDrawer == null) {
                        this.mDrawer = GLDrawer2D.create(this.mManager.isGLES3(), false);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseTarget() {
        final GLDrawer2D gLDrawer2D;
        final RendererTarget rendererTarget;
        synchronized (this.mSync) {
            gLDrawer2D = this.mDrawer;
            this.mDrawer = null;
            rendererTarget = this.mRendererTarget;
            this.mRendererTarget = null;
        }
        if (!(gLDrawer2D == null && rendererTarget == null) && this.mManager.isValid()) {
            try {
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.ImageTextureSource.5
                    @Override // java.lang.Runnable
                    public void run() {
                        GLDrawer2D gLDrawer2D2 = gLDrawer2D;
                        if (gLDrawer2D2 != null) {
                            gLDrawer2D2.release();
                        }
                        RendererTarget rendererTarget2 = rendererTarget;
                        if (rendererTarget2 != null) {
                            rendererTarget2.release();
                        }
                    }
                });
            } catch (Exception unused) {
            }
        }
    }
}
