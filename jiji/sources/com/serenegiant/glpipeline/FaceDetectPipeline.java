package com.serenegiant.glpipeline;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.FaceDetector;
import android.opengl.GLES20;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.math.Fraction;
import com.serenegiant.utils.HandlerThreadHandler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public class FaceDetectPipeline extends ProxyPipeline {
    private static final boolean DEBUG = false;
    private static final float DEFAULT_MAX_FPS = 1.0f;
    private static final String TAG = "FaceDetectPipeline";
    private int cnt;
    private final FaceDetector.Face[] mDetected;
    private FaceDetector mDetector;
    private GLDrawer2D mDrawer;
    private final OnDetectedListener mListener;
    private final GLManager mManager;
    private final int mMaxDetectNum;
    private final float mMaxFps;
    private RendererTarget mRendererTarget;
    private Bitmap mWorkBitmap;
    private ByteBuffer mWorkBuffer;
    private EGLBase.IEglSurface offscreen;
    private final Object mSync = new Object();
    private final HandlerThreadHandler mAsyncHandler = HandlerThreadHandler.createHandler(TAG);
    private final Runnable mDetectTask = new Runnable() { // from class: com.serenegiant.glpipeline.FaceDetectPipeline.3
        private final Matrix m = new Matrix();

        @Override // java.lang.Runnable
        public void run() {
            synchronized (FaceDetectPipeline.this.mSync) {
                if (FaceDetectPipeline.this.mWorkBitmap != null) {
                    Bitmap copy = FaceDetectPipeline.this.mWorkBitmap.copy(Bitmap.Config.RGB_565, true);
                    int width = FaceDetectPipeline.this.getWidth();
                    int height = FaceDetectPipeline.this.getHeight();
                    this.m.preScale(1.0f, -1.0f);
                    Bitmap createBitmap = Bitmap.createBitmap(copy, 0, 0, width, height, this.m, true);
                    copy.recycle();
                    int findFaces = FaceDetectPipeline.this.mDetector.findFaces(createBitmap, FaceDetectPipeline.this.mDetected);
                    if (findFaces > 0) {
                        FaceDetectPipeline.this.mListener.onDetected(findFaces, FaceDetectPipeline.this.mDetected, width, height);
                    }
                    createBitmap.recycle();
                }
            }
        }
    };

    /* loaded from: classes2.dex */
    public interface OnDetectedListener {
        void onDetected(int i, FaceDetector.Face[] faceArr, int i2, int i3);
    }

    public FaceDetectPipeline(GLManager gLManager, Fraction fraction, int i, OnDetectedListener onDetectedListener) {
        this.mManager = gLManager;
        this.mMaxFps = fraction != null ? fraction.asFloat() : 1.0f;
        this.mMaxDetectNum = i;
        this.mListener = onDetectedListener;
        this.mDetected = new FaceDetector.Face[i];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        if (isValid()) {
            releaseTarget();
            this.mAsyncHandler.removeCallbacksAndMessages(null);
            this.mAsyncHandler.quit();
        }
        super.internalRelease();
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return super.isValid() && this.mManager.isValid();
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        int width;
        int height;
        GLDrawer2D gLDrawer2D;
        RendererTarget rendererTarget;
        super.onFrameAvailable(z, i, fArr);
        if (isActive()) {
            synchronized (this.mSync) {
                width = getWidth();
                height = getHeight();
                GLDrawer2D gLDrawer2D2 = this.mDrawer;
                if (gLDrawer2D2 == null || z != gLDrawer2D2.isOES()) {
                    GLDrawer2D gLDrawer2D3 = this.mDrawer;
                    if (gLDrawer2D3 != null) {
                        gLDrawer2D3.release();
                    }
                    this.mDrawer = GLDrawer2D.create(this.mManager.isGLES3(), z);
                }
                gLDrawer2D = this.mDrawer;
                RendererTarget rendererTarget2 = this.mRendererTarget;
                if (rendererTarget2 == null || rendererTarget2.width() != width || this.mRendererTarget.height() != height) {
                    createTarget();
                }
                rendererTarget = this.mRendererTarget;
            }
            if (rendererTarget == null || !rendererTarget.canDraw()) {
                return;
            }
            rendererTarget.draw(gLDrawer2D, 33984, i, fArr);
            this.offscreen.makeCurrent();
            this.mWorkBuffer.clear();
            GLES20.glReadPixels(0, 0, width, height, 6408, 5121, this.mWorkBuffer);
            this.mWorkBuffer.clear();
            this.mAsyncHandler.removeCallbacks(this.mDetectTask);
            synchronized (this.mSync) {
                Bitmap bitmap = this.mWorkBitmap;
                if (bitmap != null) {
                    bitmap.copyPixelsFromBuffer(this.mWorkBuffer);
                    this.mAsyncHandler.post(this.mDetectTask);
                }
            }
        }
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        super.refresh();
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.FaceDetectPipeline.1
                @Override // java.lang.Runnable
                public void run() {
                    GLDrawer2D gLDrawer2D;
                    synchronized (FaceDetectPipeline.this.mSync) {
                        gLDrawer2D = FaceDetectPipeline.this.mDrawer;
                        FaceDetectPipeline.this.mDrawer = null;
                    }
                    if (gLDrawer2D != null) {
                        gLDrawer2D.release();
                    }
                }
            });
        }
    }

    private void createTarget() {
        int width = getWidth();
        int height = getHeight();
        synchronized (this.mSync) {
            RendererTarget rendererTarget = this.mRendererTarget;
            if (rendererTarget != null) {
                rendererTarget.release();
                this.mRendererTarget = null;
            }
            EGLBase.IEglSurface iEglSurface = this.offscreen;
            if (iEglSurface != null) {
                iEglSurface.release();
                this.offscreen = null;
            }
            Bitmap bitmap = this.mWorkBitmap;
            if (bitmap != null) {
                bitmap.recycle();
                this.mWorkBitmap = null;
            }
            this.offscreen = this.mManager.getEgl().createOffscreen(width, height);
            EGLBase egl = this.mManager.getEgl();
            EGLBase.IEglSurface iEglSurface2 = this.offscreen;
            float f = this.mMaxFps;
            if (f <= 0.0f) {
                f = 1.0f;
            }
            this.mRendererTarget = RendererTarget.newInstance(egl, iEglSurface2, f);
            this.mWorkBuffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.LITTLE_ENDIAN);
            this.mWorkBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.mDetector = new FaceDetector(width, height, this.mMaxDetectNum);
        }
    }

    private void releaseTarget() {
        final GLDrawer2D gLDrawer2D;
        final RendererTarget rendererTarget;
        final EGLBase.IEglSurface iEglSurface;
        synchronized (this.mSync) {
            gLDrawer2D = this.mDrawer;
            this.mDrawer = null;
            rendererTarget = this.mRendererTarget;
            this.mRendererTarget = null;
            iEglSurface = this.offscreen;
            this.offscreen = null;
            Bitmap bitmap = this.mWorkBitmap;
            if (bitmap != null) {
                bitmap.recycle();
                this.mWorkBitmap = null;
            }
            this.mWorkBuffer = null;
            this.mDetector = null;
        }
        if (!(gLDrawer2D == null && rendererTarget == null) && this.mManager.isValid()) {
            try {
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.FaceDetectPipeline.2
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
                        EGLBase.IEglSurface iEglSurface2 = iEglSurface;
                        if (iEglSurface2 != null) {
                            iEglSurface2.release();
                        }
                    }
                });
            } catch (Exception unused) {
            }
        }
    }
}
