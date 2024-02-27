package com.serenegiant.glpipeline;

import android.opengl.GLES20;
import android.os.Handler;
import android.os.Message;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLContext;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.glutils.AbstractDistributeTask;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class SurfaceDistributePipeline extends ProxyPipeline implements IMirror {
    private static final boolean DEBUG = false;
    private static final String RENDERER_THREAD_NAME = "SurfaceDistributePipeline";
    private static final String TAG = "SurfaceDistributePipeline";
    private volatile boolean isRunning;
    private final DistributeTask mDistributeTask;
    private final GLManager mManager;
    private final boolean mOwnManager;

    public SurfaceDistributePipeline(GLManager gLManager) {
        this(gLManager, false, null);
    }

    public SurfaceDistributePipeline(GLManager gLManager, boolean z, GLDrawer2D.DrawerFactory drawerFactory) {
        Handler createGLHandler;
        Handler.Callback callback = new Handler.Callback() { // from class: com.serenegiant.glpipeline.SurfaceDistributePipeline.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                SurfaceDistributePipeline.this.mDistributeTask.handleRequest(message.what, message.arg1, message.arg2, message.obj);
                return true;
            }
        };
        this.mOwnManager = z;
        if (z) {
            GLManager createShared = gLManager.createShared(callback);
            this.mManager = createShared;
            createGLHandler = createShared.getGLHandler();
        } else {
            this.mManager = gLManager;
            createGLHandler = gLManager.createGLHandler(callback);
        }
        DistributeTask distributeTask = new DistributeTask(this.mManager.getGLContext(), createGLHandler, getWidth(), getHeight(), drawerFactory);
        this.mDistributeTask = distributeTask;
        distributeTask.start("SurfaceDistributePipeline");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        if (isValid()) {
            this.mDistributeTask.release();
        }
        super.internalRelease();
    }

    public GLManager getGLManager() throws IllegalStateException {
        return this.mManager;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void resize(int i, int i2) throws IllegalStateException {
        super.resize(i, i2);
        if (i <= 0 || i2 <= 0) {
            return;
        }
        this.mDistributeTask.resize(i, i2);
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return super.isValid() && this.mDistributeTask.isRunning();
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        super.onFrameAvailable(z, i, fArr);
        this.mDistributeTask.requestFrame(z, i, fArr);
    }

    public void addSurface(int i, Object obj, boolean z) throws IllegalStateException, IllegalArgumentException {
        this.mDistributeTask.addSurface(i, obj);
    }

    public void addSurface(int i, Object obj, boolean z, Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        this.mDistributeTask.addSurface(i, obj, fraction);
    }

    public void removeSurface(int i) {
        this.mDistributeTask.removeSurface(i);
    }

    public void removeSurfaceAll() {
        this.mDistributeTask.removeSurfaceAll();
    }

    public void clearSurface(int i, int i2) {
        this.mDistributeTask.clearSurface(i, i2);
    }

    public void clearSurfaceAll(int i) {
        this.mDistributeTask.clearSurfaceAll(i);
    }

    public void setMvpMatrix(int i, int i2, float[] fArr) {
        this.mDistributeTask.setMvpMatrix(i, i2, fArr);
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(int i) {
        this.mDistributeTask.setMirror(i % 4);
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        return this.mDistributeTask.getMirror();
    }

    public boolean isEnabled(int i) {
        return this.mDistributeTask.isEnabled(i);
    }

    public void setEnabled(int i, boolean z) {
        this.mDistributeTask.setEnabled(i, z);
    }

    public int getCount() {
        return this.mDistributeTask.getCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class DistributeTask extends AbstractDistributeTask {
        private final boolean isGLES3;
        private volatile boolean isRunning;
        private final GLContext mGLContext;
        private final Handler mGLHandler;

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void callOnFrameAvailable() {
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        protected void handleReCreateInputSurface() {
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        protected void handleReleaseInputSurface() {
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        protected void handleUpdateTexture() {
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isMasterSurfaceValid() {
            return true;
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean waitReady() {
            return true;
        }

        public DistributeTask(GLContext gLContext, Handler handler, int i, int i2, GLDrawer2D.DrawerFactory drawerFactory) {
            super(i, i2, drawerFactory);
            this.mGLContext = gLContext;
            this.mGLHandler = handler;
            this.isGLES3 = gLContext.isGLES3();
            this.isRunning = true;
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void release() {
            if (this.isRunning) {
                this.isRunning = false;
                this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceDistributePipeline.DistributeTask.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DistributeTask.this.handleOnStop();
                        if (SurfaceDistributePipeline.this.mOwnManager && SurfaceDistributePipeline.this.isValid()) {
                            SurfaceDistributePipeline.this.mManager.release();
                        }
                    }
                });
            }
            super.release();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void start(String str) {
            this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceDistributePipeline.DistributeTask.2
                @Override // java.lang.Runnable
                public void run() {
                    DistributeTask.this.handleOnStart();
                }
            });
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isRunning() {
            return this.isRunning && SurfaceDistributePipeline.this.mManager.isValid();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isFinished() {
            return !isRunning();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i) {
            return this.mGLHandler.sendEmptyMessage(i);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, Object obj) {
            Handler handler = this.mGLHandler;
            return handler.sendMessage(handler.obtainMessage(i, obj));
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, int i2) {
            Handler handler = this.mGLHandler;
            return handler.sendMessage(handler.obtainMessage(i, Integer.valueOf(i2)));
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, int i2, int i3) {
            Handler handler = this.mGLHandler;
            return handler.sendMessage(handler.obtainMessage(i, i2, i3));
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, int i2, int i3, Object obj) {
            Handler handler = this.mGLHandler;
            return handler.sendMessage(handler.obtainMessage(i, i2, i3, obj));
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void removeRequest(int i) {
            this.mGLHandler.removeMessages(i);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public EGLBase getEgl() {
            return this.mGLContext.getEgl();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public GLContext getGLContext() {
            return this.mGLContext;
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public EGLBase.IContext<?> getContext() {
            return this.mGLContext.getContext();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public int getGlVersion() {
            return this.mGLContext.getGlVersion();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void makeCurrent() {
            this.mGLContext.makeDefault();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isGLES3() {
            return this.isGLES3;
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isOES3Supported() {
            return this.mGLContext.isOES3Supported();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void requestFrame(boolean z, int i, float[] fArr) {
            if (SurfaceDistributePipeline.this.mOwnManager || !isFirstFrameRendered()) {
                super.requestFrame(z, i, fArr);
                return;
            }
            super.handleDrawTargets(z, i, fArr);
            makeCurrent();
            GLES20.glClear(16384);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public Object handleRequest(int i, int i2, int i3, Object obj) {
            return super.handleRequest(i, i2, i3, obj);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void notifyParent(boolean z) {
            synchronized (SurfaceDistributePipeline.this) {
                SurfaceDistributePipeline.this.isRunning = z;
                SurfaceDistributePipeline.this.notifyAll();
            }
        }
    }
}
