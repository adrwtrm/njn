package com.serenegiant.glutils;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.system.BuildCheck;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class GLImageReceiver {
    private static final boolean DEBUG = false;
    private static final int REQUEST_RECREATE_MASTER_SURFACE = 5;
    private static final int REQUEST_UPDATE_SIZE = 2;
    private static final int REQUEST_UPDATE_TEXTURE = 1;
    private static final String TAG = "GLImageReceiver";
    private int drawCnt;
    private final Callback mCallback;
    private final Handler mGLHandler;
    private int mHeight;
    private Surface mInputSurface;
    private SurfaceTexture mInputTexture;
    private boolean mIsReaderValid;
    private final GLManager mManager;
    private final SurfaceTexture.OnFrameAvailableListener mOnFrameAvailableListener;
    private final boolean mOwnManager;
    private volatile boolean mReleased;
    private final Object mSync;
    private int mTexId;
    final float[] mTexMatrix;
    private int mWidth;

    /* loaded from: classes2.dex */
    public interface Callback {
        void onCreateInputSurface(GLImageReceiver gLImageReceiver);

        void onFrameAvailable(GLImageReceiver gLImageReceiver, boolean z, int i, float[] fArr);

        void onInitialize(GLImageReceiver gLImageReceiver);

        void onRelease();

        void onReleaseInputSurface(GLImageReceiver gLImageReceiver);

        void onResize(int i, int i2);
    }

    public GLImageReceiver(int i, int i2, Callback callback) {
        this(new GLManager(), false, i, i2, callback);
    }

    public GLImageReceiver(GLManager gLManager, boolean z, int i, int i2, Callback callback) {
        Surface surface;
        Object obj = new Object();
        this.mSync = obj;
        this.mReleased = false;
        this.mIsReaderValid = false;
        this.mTexMatrix = new float[16];
        this.mOnFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.serenegiant.glutils.GLImageReceiver.5
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                GLImageReceiver.this.mGLHandler.sendEmptyMessage(1);
            }
        };
        this.mOwnManager = z;
        Handler.Callback callback2 = new Handler.Callback() { // from class: com.serenegiant.glutils.GLImageReceiver.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                return GLImageReceiver.this.handleMessage(message);
            }
        };
        if (z) {
            GLManager createShared = gLManager.createShared(callback2);
            this.mManager = createShared;
            this.mGLHandler = createShared.getGLHandler();
        } else {
            this.mManager = gLManager;
            this.mGLHandler = gLManager.createGLHandler(callback2);
        }
        this.mWidth = i;
        this.mHeight = i2;
        this.mCallback = callback;
        final Semaphore semaphore = new Semaphore(0);
        this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.glutils.GLImageReceiver.2
            @Override // java.lang.Runnable
            public void run() {
                GLImageReceiver.this.handleOnStart();
                semaphore.release();
            }
        });
        try {
            if (!semaphore.tryAcquire(1000L, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("failed to init");
            }
            semaphore.release(semaphore.availablePermits());
            this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.glutils.GLImageReceiver.3
                @Override // java.lang.Runnable
                public void run() {
                    GLImageReceiver.this.handleReCreateInputSurface();
                    semaphore.release();
                }
            });
            try {
                synchronized (obj) {
                    surface = this.mInputSurface;
                }
                if (surface == null) {
                    if (semaphore.tryAcquire(1000L, TimeUnit.MILLISECONDS)) {
                        this.mIsReaderValid = true;
                        return;
                    }
                    throw new RuntimeException("failed to create surface");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e2) {
            throw new RuntimeException("failed to init", e2);
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
        this.mIsReaderValid = false;
        internalRelease();
    }

    protected void internalRelease() {
        if (this.mManager.isValid()) {
            this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.glutils.GLImageReceiver.4
                @Override // java.lang.Runnable
                public void run() {
                    GLImageReceiver.this.handleOnStop();
                    if (GLImageReceiver.this.mOwnManager) {
                        GLImageReceiver.this.mManager.release();
                    }
                }
            });
        }
    }

    public GLManager getGLManager() {
        return this.mManager;
    }

    public boolean isValid() {
        return !this.mReleased && this.mIsReaderValid && this.mManager.isValid();
    }

    public int getWidth() {
        int i;
        synchronized (this.mSync) {
            i = this.mWidth;
        }
        return i;
    }

    public int getHeight() {
        int i;
        synchronized (this.mSync) {
            i = this.mHeight;
        }
        return i;
    }

    public int getTexId() {
        int i;
        synchronized (this.mSync) {
            if (this.mInputSurface == null) {
                throw new IllegalStateException("surface not ready, already released?");
            }
            i = this.mTexId;
        }
        return i;
    }

    public float[] getTexMatrix() {
        float[] fArr;
        synchronized (this.mSync) {
            fArr = this.mTexMatrix;
        }
        return fArr;
    }

    public Surface getSurface() throws IllegalStateException {
        Surface surface;
        synchronized (this.mSync) {
            surface = this.mInputSurface;
            if (surface == null) {
                throw new IllegalStateException("surface not ready, already released?");
            }
        }
        return surface;
    }

    public SurfaceTexture getSurfaceTexture() throws IllegalStateException {
        SurfaceTexture surfaceTexture;
        synchronized (this.mSync) {
            surfaceTexture = this.mInputTexture;
            if (surfaceTexture == null) {
                throw new IllegalStateException("surface not ready, already released?");
            }
        }
        return surfaceTexture;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isGLES3() {
        return this.mManager.isGLES3();
    }

    public void resize(int i, int i2) {
        int max = Math.max(i, 1);
        int max2 = Math.max(i2, 1);
        synchronized (this.mSync) {
            if (this.mWidth != max || this.mHeight != max2) {
                Handler handler = this.mGLHandler;
                handler.sendMessage(handler.obtainMessage(2, max, max2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnStart() {
        this.mCallback.onInitialize(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnStop() {
        handleReleaseInputSurface();
        this.mCallback.onRelease();
    }

    protected boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            handleUpdateTexImage();
            return true;
        } else if (i == 2) {
            handleResize(message.arg1, message.arg2);
            return true;
        } else if (i != 5) {
            return false;
        } else {
            handleReCreateInputSurface();
            return true;
        }
    }

    private void handleUpdateTexImage() {
        try {
            this.mManager.makeDefault();
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
            this.mManager.swap();
            this.mInputTexture.updateTexImage();
            this.mInputTexture.getTransformMatrix(this.mTexMatrix);
            this.mCallback.onFrameAvailable(this, true, this.mTexId, this.mTexMatrix);
        } catch (Exception e) {
            Log.e(TAG, "handleDraw:thread id =" + Thread.currentThread().getId(), e);
        }
    }

    private void handleResize(int i, int i2) {
        SurfaceTexture surfaceTexture;
        synchronized (this.mSync) {
            this.mWidth = i;
            this.mHeight = i2;
        }
        if (BuildCheck.isAndroid4_1() && (surfaceTexture = this.mInputTexture) != null) {
            surfaceTexture.setDefaultBufferSize(i, i2);
        }
        this.mCallback.onResize(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReCreateInputSurface() {
        synchronized (this.mSync) {
            this.mManager.makeDefault();
            handleReleaseInputSurface();
            this.mManager.makeDefault();
            this.mTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33984, 9728);
            this.mInputTexture = new SurfaceTexture(this.mTexId);
            this.mInputSurface = new Surface(this.mInputTexture);
            if (BuildCheck.isAndroid4_1()) {
                this.mInputTexture.setDefaultBufferSize(this.mWidth, this.mHeight);
            }
            this.mCallback.onCreateInputSurface(this);
            this.mInputTexture.setOnFrameAvailableListener(this.mOnFrameAvailableListener);
        }
    }

    private void handleReleaseInputSurface() {
        this.mCallback.onReleaseInputSurface(this);
        synchronized (this.mSync) {
            Surface surface = this.mInputSurface;
            if (surface != null) {
                try {
                    surface.release();
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
                this.mInputSurface = null;
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
}
