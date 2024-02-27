package com.serenegiant.gl;

import android.os.Handler;
import android.util.Log;
import android.view.Choreographer;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class GLManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "GLManager";
    private final GLContext mGLContext;
    private final Handler mGLHandler;
    private final long mHandlerThreadId;
    private boolean mInitialized;
    private boolean mReleased;

    public GLManager() {
        this(GLUtils.getSupportedGLVersion(), null, 0, null, 0, 0, null);
    }

    public GLManager(Handler.Callback callback) {
        this(GLUtils.getSupportedGLVersion(), null, 0, null, 0, 0, callback);
    }

    public GLManager(int i) {
        this(i, null, 0, null, 0, 0, null);
    }

    public GLManager(int i, Handler.Callback callback) {
        this(i, null, 0, null, 0, 0, callback);
    }

    public GLManager(GLManager gLManager, Handler.Callback callback) {
        this(gLManager.getGLContext().getMaxClientVersion(), gLManager.getGLContext().getContext(), gLManager.getGLContext().getFlags(), null, 0, 0, callback);
    }

    public GLManager(int i, EGLBase.IContext<?> iContext, int i2, Handler.Callback callback) throws IllegalArgumentException, IllegalStateException {
        this(i, iContext, i2, null, 0, 0, callback);
    }

    public GLManager(int i, EGLBase.IContext<?> iContext, int i2, final Object obj, final int i3, final int i4, Handler.Callback callback) throws IllegalArgumentException, IllegalStateException {
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("wrong type of masterSurface");
        }
        this.mGLContext = new GLContext(i, iContext, i2);
        HandlerThreadHandler createHandler = HandlerThreadHandler.createHandler(TAG, callback, true);
        this.mGLHandler = createHandler;
        this.mHandlerThreadId = createHandler.getId();
        final Semaphore semaphore = new Semaphore(0);
        createHandler.postAtFrontOfQueue(new Runnable() { // from class: com.serenegiant.gl.GLManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    GLManager.this.mGLContext.initialize(obj, i3, i4);
                    GLManager.this.mInitialized = true;
                } catch (Exception e) {
                    Log.w(GLManager.TAG, e);
                }
                semaphore.release();
            }
        });
        try {
            if (!semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS)) {
                this.mInitialized = false;
            }
        } catch (InterruptedException unused) {
        }
        if (!this.mInitialized) {
            throw new IllegalStateException("Failed to initialize GL context");
        }
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public synchronized void release() {
        if (!this.mReleased) {
            this.mReleased = true;
            this.mGLHandler.postAtFrontOfQueue(new Runnable() { // from class: com.serenegiant.gl.GLManager.2
                @Override // java.lang.Runnable
                public void run() {
                    GLManager.this.mGLContext.release();
                    GLManager.this.mGLHandler.removeCallbacksAndMessages(null);
                    HandlerUtils.NoThrowQuit(GLManager.this.mGLHandler);
                }
            });
        }
    }

    public void reInitialize(final Object obj, final int i, final int i2) {
        if (this.mInitialized) {
            this.mInitialized = false;
            final Semaphore semaphore = new Semaphore(0);
            this.mGLHandler.postAtFrontOfQueue(new Runnable() { // from class: com.serenegiant.gl.GLManager.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        GLManager.this.mGLContext.initialize(obj, i, i2);
                        GLManager.this.mInitialized = true;
                    } catch (Exception e) {
                        Log.w(GLManager.TAG, e);
                    }
                    semaphore.release();
                }
            });
            try {
                if (!semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS)) {
                    this.mInitialized = false;
                }
            } catch (InterruptedException unused) {
            }
            if (!this.mInitialized) {
                throw new IllegalStateException("Failed to initialize GL context");
            }
            return;
        }
        throw new IllegalStateException("Not initialized!");
    }

    public synchronized boolean isValid() {
        boolean z;
        if (this.mInitialized) {
            z = this.mReleased ? false : true;
        }
        return z;
    }

    public boolean isGLES3() {
        return this.mGLContext.isGLES3();
    }

    public boolean isGLThread() {
        return this.mHandlerThreadId == Thread.currentThread().getId();
    }

    public EGLBase getEgl() throws IllegalStateException {
        checkValid();
        return this.mGLContext.getEgl();
    }

    public int getMasterWidth() {
        return this.mGLContext.getMasterWidth();
    }

    public int getMasterHeight() {
        return this.mGLContext.getMasterHeight();
    }

    public synchronized GLManager createShared(Handler.Callback callback) throws RuntimeException {
        checkValid();
        return new GLManager(this.mGLContext.getMaxClientVersion(), this.mGLContext.getContext(), this.mGLContext.getFlags(), null, 0, 0, callback);
    }

    public synchronized Handler getGLHandler() throws IllegalStateException {
        checkValid();
        return this.mGLHandler;
    }

    public synchronized Handler createGLHandler(Handler.Callback callback) throws IllegalStateException {
        checkValid();
        return new Handler(this.mGLHandler.getLooper(), callback);
    }

    public synchronized GLContext getGLContext() throws IllegalStateException {
        checkValid();
        return this.mGLContext;
    }

    public void makeDefault() throws IllegalStateException {
        checkValid();
        this.mGLContext.makeDefault();
    }

    public void swap() throws IllegalStateException {
        checkValid();
        this.mGLContext.swap();
    }

    public synchronized void runOnGLThread(Runnable runnable) throws IllegalStateException {
        checkValid();
        if (isGLThread()) {
            runnable.run();
        } else {
            this.mGLHandler.post(runnable);
        }
    }

    public synchronized void runOnGLThread(Runnable runnable, long j) throws IllegalStateException {
        checkValid();
        if (j > 0) {
            this.mGLHandler.postDelayed(runnable, j);
        } else if (isGLThread()) {
            runnable.run();
        } else {
            this.mGLHandler.post(runnable);
        }
    }

    public synchronized void postFrameCallbackDelayed(final Choreographer.FrameCallback frameCallback, final long j) throws IllegalStateException {
        checkValid();
        if (isGLThread()) {
            Choreographer.getInstance().postFrameCallbackDelayed(frameCallback, j);
        } else {
            this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.gl.GLManager.4
                @Override // java.lang.Runnable
                public void run() {
                    Choreographer.getInstance().postFrameCallbackDelayed(frameCallback, j);
                }
            });
        }
    }

    public synchronized void removeFrameCallback(final Choreographer.FrameCallback frameCallback) throws IllegalStateException {
        checkValid();
        if (isGLThread()) {
            Choreographer.getInstance().removeFrameCallback(frameCallback);
        } else {
            this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.gl.GLManager.5
                @Override // java.lang.Runnable
                public void run() {
                    Choreographer.getInstance().removeFrameCallback(frameCallback);
                }
            });
        }
    }

    private void checkValid() throws IllegalStateException {
        if (!isValid()) {
            throw new IllegalStateException("already released");
        }
    }
}
