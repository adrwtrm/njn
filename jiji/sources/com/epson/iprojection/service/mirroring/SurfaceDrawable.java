package com.epson.iprojection.service.mirroring;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.egl.EglTask;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.MessageTask;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class SurfaceDrawable extends Drawable {
    private static final boolean DEBUG = false;
    private static final int REQUEST_DRAW = 1;
    private static final int REQUEST_RECREATE_MASTER_SURFACE = 5;
    private static final int REQUEST_UPDATE_SIZE = 2;
    private static final String TAG = "SurfaceDrawable";
    private int drawCnt;
    private final Bitmap mBitmap;
    private final Callback mCallback;
    private GLDrawer2D mDrawer;
    private final EglTask mEglTask;
    private int mHeight;
    private int mImageHeight;
    private int mImageWidth;
    private Surface mInputSurface;
    private SurfaceTexture mInputTexture;
    private final Runnable mInvalidateSelfOnUITask;
    private final SurfaceTexture.OnFrameAvailableListener mOnFrameAvailableListener;
    private final Paint mPaint;
    private final Object mSync;
    private int mTexId;
    final float[] mTexMatrix;
    private final Matrix mTransform;
    private final Handler mUIHandler;
    private int mWidth;
    private ByteBuffer mWorkBuffer;

    /* loaded from: classes.dex */
    public interface Callback {
        void onCreateSurface(Surface surface);

        void onDestroySurface();

        void onSurfaceChanged(Bitmap bitmap);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public SurfaceDrawable(int i, int i2, Callback callback) {
        this(i, i2, GLUtils.getSupportedGLVersion(), callback);
    }

    public SurfaceDrawable(int i, int i2, int i3, Callback callback) {
        this.mSync = new Object();
        this.mUIHandler = new Handler(Looper.getMainLooper());
        this.mTransform = new Matrix();
        this.mPaint = new Paint();
        this.mTexMatrix = new float[16];
        this.mInvalidateSelfOnUITask = new Runnable() { // from class: com.epson.iprojection.service.mirroring.SurfaceDrawable.2
            @Override // java.lang.Runnable
            public void run() {
                SurfaceDrawable.this.invalidateSelf();
            }
        };
        this.mOnFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.epson.iprojection.service.mirroring.SurfaceDrawable.3
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                SurfaceDrawable.this.mEglTask.offer(1);
            }
        };
        this.mImageWidth = i;
        this.mWidth = i;
        this.mImageHeight = i2;
        this.mHeight = i2;
        this.mCallback = callback;
        EglTask eglTask = new EglTask(i3, null, 0, i, i2) { // from class: com.epson.iprojection.service.mirroring.SurfaceDrawable.1
            @Override // com.serenegiant.utils.MessageTask
            protected void onStart() {
                SurfaceDrawable.this.handleOnStart();
            }

            @Override // com.serenegiant.utils.MessageTask
            protected void onStop() {
                SurfaceDrawable.this.handleOnStop();
            }

            @Override // com.serenegiant.utils.MessageTask
            protected Object processRequest(int i4, int i5, int i6, Object obj) throws MessageTask.TaskBreak {
                return SurfaceDrawable.this.handleRequest(i4, i5, i6, obj);
            }
        };
        this.mEglTask = eglTask;
        this.mBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.mWorkBuffer = ByteBuffer.allocateDirect(i * i2 * 4);
        new Thread(eglTask, TAG).start();
        eglTask.offer(5);
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void release() {
        this.mEglTask.release();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        synchronized (this.mBitmap) {
            canvas.drawBitmap(this.mBitmap, this.mTransform, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    public boolean isSurfaceValid() {
        Surface surface = this.mInputSurface;
        return surface != null && surface.isValid();
    }

    public Surface getSurface() {
        Surface surface;
        synchronized (this.mSync) {
            surface = this.mInputSurface;
        }
        return surface;
    }

    public SurfaceTexture getSurfaceTexture() {
        SurfaceTexture surfaceTexture;
        synchronized (this.mSync) {
            surfaceTexture = this.mInputTexture;
        }
        return surfaceTexture;
    }

    public void resize(int i, int i2) {
        if (i == this.mImageWidth && i2 == this.mImageHeight) {
            return;
        }
        this.mEglTask.offer(2, i, i2);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateTransformMatrix();
    }

    protected EGLBase getEgl() {
        return this.mEglTask.getEgl();
    }

    protected EGLBase.IContext<?> getContext() {
        return this.mEglTask.getContext();
    }

    protected boolean isGLES3() {
        return this.mEglTask.isGLES3();
    }

    protected boolean isOES3Supported() {
        return this.mEglTask.isOES3Supported();
    }

    protected int getTexId() {
        return this.mTexId;
    }

    protected float[] getTexMatrix() {
        return this.mTexMatrix;
    }

    protected final void handleOnStart() {
        this.mDrawer = GLDrawer2D.create(isOES3Supported(), true);
    }

    protected final void handleOnStop() {
        GLDrawer2D gLDrawer2D = this.mDrawer;
        if (gLDrawer2D != null) {
            gLDrawer2D.release();
            this.mDrawer = null;
        }
        handleReleaseInputSurface();
    }

    protected Object handleRequest(int i, int i2, int i3, Object obj) {
        if (i == 1) {
            handleDraw();
            return null;
        } else if (i == 2) {
            handleResize(i2, i3);
            return null;
        } else if (i != 5) {
            return null;
        } else {
            handleReCreateInputSurface();
            return null;
        }
    }

    protected void handleDraw() {
        this.mEglTask.removeRequest(1);
        try {
            this.mEglTask.makeCurrent();
            this.mInputTexture.updateTexImage();
            this.mInputTexture.getTransformMatrix(this.mTexMatrix);
            this.mDrawer.draw(33984, this.mTexId, this.mTexMatrix, 0);
            this.mWorkBuffer.clear();
            GLES20.glReadPixels(0, 0, this.mImageWidth, this.mImageHeight, 6408, 5121, this.mWorkBuffer);
            this.mWorkBuffer.clear();
            synchronized (this.mBitmap) {
                this.mBitmap.copyPixelsFromBuffer(this.mWorkBuffer);
                this.mCallback.onSurfaceChanged(this.mBitmap);
            }
            this.mUIHandler.removeCallbacks(this.mInvalidateSelfOnUITask);
            this.mUIHandler.post(this.mInvalidateSelfOnUITask);
        } catch (Exception e) {
            Log.e(TAG, "handleDraw:thread id =" + Thread.currentThread().getId(), e);
        }
    }

    protected void handleResize(int i, int i2) {
        SurfaceTexture surfaceTexture;
        if (this.mImageWidth == i && this.mImageHeight == i2) {
            return;
        }
        this.mBitmap.reconfigure(i, i2, Bitmap.Config.ARGB_8888);
        this.mWorkBuffer = ByteBuffer.allocateDirect(i * i2 * 4);
        this.mImageWidth = i;
        this.mImageHeight = i2;
        updateTransformMatrix();
        if (!BuildCheck.isAndroid4_1() || (surfaceTexture = this.mInputTexture) == null) {
            return;
        }
        surfaceTexture.setDefaultBufferSize(getIntrinsicWidth(), getIntrinsicHeight());
    }

    protected void handleReCreateInputSurface() {
        synchronized (this.mSync) {
            this.mEglTask.makeCurrent();
            handleReleaseInputSurface();
            this.mEglTask.makeCurrent();
            this.mTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33984, 9728);
            this.mInputTexture = new SurfaceTexture(this.mTexId);
            this.mInputSurface = new Surface(this.mInputTexture);
            if (BuildCheck.isAndroid4_1()) {
                this.mInputTexture.setDefaultBufferSize(getIntrinsicWidth(), getIntrinsicHeight());
            }
            this.mInputTexture.setOnFrameAvailableListener(this.mOnFrameAvailableListener);
        }
        onCreateSurface(this.mInputSurface);
    }

    protected void handleReleaseInputSurface() {
        synchronized (this.mSync) {
            Surface surface = this.mInputSurface;
            if (surface != null) {
                try {
                    surface.release();
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
                this.mInputSurface = null;
                onDestroySurface();
            }
            SurfaceTexture surfaceTexture = this.mInputTexture;
            if (surfaceTexture != null) {
                try {
                    surfaceTexture.release();
                } catch (Exception e2) {
                    Log.w(TAG, e2);
                }
                this.mInputTexture = null;
            }
            int i = this.mTexId;
            if (i != 0) {
                GLUtils.deleteTex(i);
                this.mTexId = 0;
            }
        }
    }

    private void updateTransformMatrix() {
        Rect bounds = getBounds();
        this.mWidth = bounds.width();
        this.mHeight = bounds.height();
        float width = this.mWidth / this.mBitmap.getWidth();
        float height = this.mHeight / this.mBitmap.getHeight();
        this.mTransform.reset();
        this.mTransform.postScale(width, height);
    }

    private void onCreateSurface(Surface surface) {
        this.mCallback.onCreateSurface(surface);
    }

    private void onDestroySurface() {
        this.mCallback.onDestroySurface();
    }
}
