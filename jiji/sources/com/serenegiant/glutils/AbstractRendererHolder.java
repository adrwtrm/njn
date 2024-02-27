package com.serenegiant.glutils;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;
import com.epson.iprojection.ui.activities.marker.ImageSaver;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.egl.EglTask;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLContext;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.ISurface;
import com.serenegiant.glutils.IRendererHolder;
import com.serenegiant.math.Fraction;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.MessageTask;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public abstract class AbstractRendererHolder implements IRendererHolder {
    private static final String CAPTURE_THREAD_NAME = "CaptureTask";
    private static final boolean DEBUG = false;
    private static final String RENDERER_THREAD_NAME = "RendererHolder";
    private static final String TAG = "AbstractRendererHolder";
    private volatile boolean isRunning;
    private final IRendererHolder.RenderHolderCallback mCallback;
    private int mCaptureFormat;
    private OutputStream mCaptureStream;
    private IRendererHolder.OnCapturedListener mOnCapturedListener;
    protected final BaseRendererTask mRendererTask;
    private final Object mSync = new Object();
    private int mCaptureCompression = 80;
    private final Runnable mCaptureTask = new Runnable() { // from class: com.serenegiant.glutils.AbstractRendererHolder.1
        private ISurface captureSurface;
        private GLDrawer2D drawer;
        private GLContext mContext;
        private final float[] mMvpMatrix = new float[16];

        @Override // java.lang.Runnable
        public void run() {
            synchronized (AbstractRendererHolder.this.mSync) {
                while (!AbstractRendererHolder.this.isRunning && !AbstractRendererHolder.this.mRendererTask.isFinished()) {
                    try {
                        AbstractRendererHolder.this.mSync.wait(1000L);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            if (AbstractRendererHolder.this.isRunning) {
                init();
                try {
                    try {
                        if (this.mContext.isOES3Supported()) {
                            captureLoopGLES3();
                        } else {
                            captureLoopGLES2();
                        }
                    } catch (Exception e) {
                        Log.w(AbstractRendererHolder.TAG, e);
                    }
                } finally {
                    release();
                }
            }
        }

        private final void init() {
            GLContext gLContext = new GLContext(AbstractRendererHolder.this.mRendererTask.getGLContext());
            this.mContext = gLContext;
            gLContext.initialize();
            this.captureSurface = this.mContext.getEgl().createOffscreen(AbstractRendererHolder.this.mRendererTask.width(), AbstractRendererHolder.this.mRendererTask.height());
            Matrix.setIdentityM(this.mMvpMatrix, 0);
            GLDrawer2D create = GLDrawer2D.create(this.mContext.isOES3Supported(), true);
            this.drawer = create;
            AbstractRendererHolder.this.setupCaptureDrawer(create);
        }

        private final void captureLoopGLES2() {
            int i = -1;
            ByteBuffer byteBuffer = null;
            int i2 = 80;
            int i3 = -1;
            while (AbstractRendererHolder.this.isRunning) {
                synchronized (AbstractRendererHolder.this.mSync) {
                    if (AbstractRendererHolder.this.mCaptureStream == null) {
                        try {
                            AbstractRendererHolder.this.mSync.wait();
                            if (AbstractRendererHolder.this.mCaptureStream != null) {
                                i2 = AbstractRendererHolder.this.mCaptureCompression;
                                if (i2 <= 0 || i2 >= 100) {
                                    i2 = 90;
                                }
                            }
                        } catch (InterruptedException unused) {
                        }
                    }
                    if (byteBuffer == null || i != AbstractRendererHolder.this.mRendererTask.width() || i3 != AbstractRendererHolder.this.mRendererTask.height()) {
                        i = AbstractRendererHolder.this.mRendererTask.width();
                        i3 = AbstractRendererHolder.this.mRendererTask.height();
                        byteBuffer = ByteBuffer.allocateDirect(i * i3 * 4);
                        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                        ISurface iSurface = this.captureSurface;
                        if (iSurface != null) {
                            iSurface.release();
                            this.captureSurface = null;
                        }
                        this.captureSurface = this.mContext.getEgl().createOffscreen(i, i3);
                    }
                    boolean z = false;
                    if (!AbstractRendererHolder.this.isRunning || i <= 0 || i3 <= 0) {
                        if (AbstractRendererHolder.this.isRunning) {
                            Log.w(AbstractRendererHolder.TAG, "#captureLoopGLES3:unexpectedly width/height is zero");
                        }
                    } else {
                        GLUtils.setMirror(this.mMvpMatrix, AbstractRendererHolder.this.mRendererTask.getMirror());
                        float[] fArr = this.mMvpMatrix;
                        fArr[5] = fArr[5] * (-1.0f);
                        this.drawer.setMvpMatrix(fArr, 0);
                        this.captureSurface.makeCurrent();
                        this.drawer.draw(33984, AbstractRendererHolder.this.mRendererTask.mTexId, AbstractRendererHolder.this.mRendererTask.mTexMatrix, 0);
                        this.captureSurface.swap();
                        byteBuffer.clear();
                        GLES20.glReadPixels(0, 0, i, i3, 6408, 5121, byteBuffer);
                        Bitmap.CompressFormat captureFormat = AbstractRendererHolder.getCaptureFormat(AbstractRendererHolder.this.mCaptureFormat);
                        try {
                            Bitmap createBitmap = Bitmap.createBitmap(i, i3, Bitmap.Config.ARGB_8888);
                            byteBuffer.clear();
                            createBitmap.copyPixelsFromBuffer(byteBuffer);
                            createBitmap.compress(captureFormat, i2, AbstractRendererHolder.this.mCaptureStream);
                            createBitmap.recycle();
                            AbstractRendererHolder.this.mCaptureStream.flush();
                            z = true;
                            try {
                                AbstractRendererHolder.this.mCaptureStream.close();
                            } catch (IOException e) {
                                Log.w(AbstractRendererHolder.TAG, "failed to save file", e);
                            }
                        } catch (Throwable th) {
                            AbstractRendererHolder.this.mCaptureStream.close();
                            throw th;
                            break;
                        }
                    }
                    AbstractRendererHolder.this.mCaptureStream = null;
                    if (AbstractRendererHolder.this.mOnCapturedListener != null) {
                        try {
                            AbstractRendererHolder.this.mOnCapturedListener.onCaptured(AbstractRendererHolder.this, z);
                        } catch (Exception unused2) {
                        }
                    }
                    AbstractRendererHolder.this.mOnCapturedListener = null;
                    AbstractRendererHolder.this.mSync.notifyAll();
                }
            }
            synchronized (AbstractRendererHolder.this.mSync) {
                AbstractRendererHolder.this.mSync.notifyAll();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:51:0x0146 A[Catch: all -> 0x017d, TryCatch #6 {, blocks: (B:7:0x0018, B:9:0x0020, B:10:0x0029, B:12:0x0031, B:17:0x003f, B:22:0x0046, B:24:0x0050, B:32:0x0092, B:36:0x009f, B:39:0x0118, B:53:0x0151, B:55:0x015e, B:56:0x0169, B:57:0x0177, B:48:0x0132, B:44:0x0125, B:45:0x012e, B:49:0x013c, B:51:0x0146, B:28:0x005f, B:30:0x0080, B:31:0x0085, B:19:0x0041), top: B:83:0x0018, inners: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x015e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private final void captureLoopGLES3() {
            /*
                Method dump skipped, instructions count: 405
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.glutils.AbstractRendererHolder.AnonymousClass1.captureLoopGLES3():void");
        }

        private final void release() {
            ISurface iSurface = this.captureSurface;
            if (iSurface != null) {
                iSurface.makeCurrent();
                this.captureSurface.release();
                this.captureSurface = null;
            }
            GLDrawer2D gLDrawer2D = this.drawer;
            if (gLDrawer2D != null) {
                gLDrawer2D.release();
                this.drawer = null;
            }
            GLContext gLContext = this.mContext;
            if (gLContext != null) {
                gLContext.release();
                this.mContext = null;
            }
        }
    };

    protected abstract BaseRendererTask createRendererTask(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4);

    protected void setupCaptureDrawer(GLDrawer2D gLDrawer2D) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractRendererHolder(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        this.mCallback = renderHolderCallback;
        BaseRendererTask createRendererTask = createRendererTask(i, i2, i3, iContext, i4);
        this.mRendererTask = createRendererTask;
        createRendererTask.start(RENDERER_THREAD_NAME);
        if (!createRendererTask.waitReady()) {
            throw new RuntimeException("failed to start renderer thread");
        }
        startCaptureTask();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void release() {
        this.mRendererTask.release();
        synchronized (this.mSync) {
            this.isRunning = false;
            this.mSync.notifyAll();
        }
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public EGLBase.IContext<?> getContext() {
        return this.mRendererTask.getContext();
    }

    public int getGlVersion() {
        return this.mRendererTask.getGlVersion();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public Surface getSurface() {
        return this.mRendererTask.getSurface();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public SurfaceTexture getSurfaceTexture() {
        return this.mRendererTask.getSurfaceTexture();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void reset() {
        this.mRendererTask.checkMasterSurface();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void resize(int i, int i2) throws IllegalStateException {
        this.mRendererTask.resize(i, i2);
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(int i) {
        this.mRendererTask.setMirror(i % 4);
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        return this.mRendererTask.getMirror();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void addSurface(int i, Object obj, boolean z) throws IllegalStateException, IllegalArgumentException {
        this.mRendererTask.addSurface(i, obj);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void addSurface(int i, Object obj, boolean z, Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        this.mRendererTask.addSurface(i, obj, fraction);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void removeSurface(int i) {
        this.mRendererTask.removeSurface(i);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void removeSurfaceAll() {
        this.mRendererTask.removeSurfaceAll();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void clearSurface(int i, int i2) {
        this.mRendererTask.clearSurface(i, i2);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void clearSurfaceAll(int i) {
        this.mRendererTask.clearSurfaceAll(i);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void setMvpMatrix(int i, int i2, float[] fArr) {
        this.mRendererTask.setMvpMatrix(i, i2, fArr);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public boolean isEnabled(int i) {
        return this.mRendererTask.isEnabled(i);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void setEnabled(int i, boolean z) {
        this.mRendererTask.setEnabled(i, z);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void requestFrame() {
        BaseRendererTask baseRendererTask = this.mRendererTask;
        baseRendererTask.requestFrame(true, baseRendererTask.mTexId, this.mRendererTask.mTexMatrix);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public int getCount() {
        return this.mRendererTask.getCount();
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void captureStill(String str, IRendererHolder.OnCapturedListener onCapturedListener) throws FileNotFoundException, IllegalStateException {
        captureStill(new BufferedOutputStream(new FileOutputStream(str)), getCaptureFormat(str), 80, onCapturedListener);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void captureStill(String str, int i, IRendererHolder.OnCapturedListener onCapturedListener) throws FileNotFoundException, IllegalStateException {
        captureStill(new BufferedOutputStream(new FileOutputStream(str)), getCaptureFormat(str), i, onCapturedListener);
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void captureStill(OutputStream outputStream, int i, int i2, IRendererHolder.OnCapturedListener onCapturedListener) throws IllegalStateException {
        synchronized (this.mSync) {
            if (!this.isRunning) {
                throw new IllegalStateException("already released?");
            }
            if (this.mCaptureStream != null) {
                throw new IllegalStateException("already run still capturing now");
            }
            this.mCaptureStream = outputStream;
            this.mCaptureFormat = i;
            this.mCaptureCompression = i2;
            this.mOnCapturedListener = onCapturedListener;
            this.mSync.notifyAll();
        }
    }

    @Override // com.serenegiant.glutils.IRendererHolder
    public void queueEvent(Runnable runnable) {
        this.mRendererTask.queueEvent(runnable);
    }

    private static int getCaptureFormat(String str) throws IllegalArgumentException {
        str.toLowerCase();
        if (str.endsWith(".jpg") || str.endsWith(".jpeg")) {
            return 0;
        }
        if (str.endsWith(ImageSaver.FILE_TYPE)) {
            return 1;
        }
        if (str.endsWith(".webp")) {
            return 2;
        }
        throw new IllegalArgumentException("unknown compress format(extension)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap.CompressFormat getCaptureFormat(int i) {
        if (i != 1) {
            if (i == 2) {
                return Bitmap.CompressFormat.WEBP;
            }
            return Bitmap.CompressFormat.JPEG;
        }
        return Bitmap.CompressFormat.PNG;
    }

    protected void startCaptureTask() {
        new Thread(this.mCaptureTask, CAPTURE_THREAD_NAME).start();
        synchronized (this.mSync) {
            if (!this.isRunning) {
                try {
                    this.mSync.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    protected void notifyCapture() {
        synchronized (this.mSync) {
            this.mSync.notify();
        }
    }

    protected void callOnCreate(Surface surface) {
        IRendererHolder.RenderHolderCallback renderHolderCallback = this.mCallback;
        if (renderHolderCallback != null) {
            try {
                renderHolderCallback.onCreate(surface);
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }

    protected void callOnFrameAvailable() {
        IRendererHolder.RenderHolderCallback renderHolderCallback = this.mCallback;
        if (renderHolderCallback != null) {
            try {
                renderHolderCallback.onFrameAvailable();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }

    protected void callOnDestroy() {
        IRendererHolder.RenderHolderCallback renderHolderCallback = this.mCallback;
        if (renderHolderCallback != null) {
            try {
                renderHolderCallback.onDestroy();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class BaseRendererTask extends AbstractDistributeTask implements SurfaceTexture.OnFrameAvailableListener {
        private final EglTask mEglTask;
        private Surface mInputSurface;
        private SurfaceTexture mInputTexture;
        private final AbstractRendererHolder mParent;
        private int mTexId;
        final float[] mTexMatrix;

        public BaseRendererTask(AbstractRendererHolder abstractRendererHolder, int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, GLDrawer2D.DrawerFactory drawerFactory) {
            super(i, i2, drawerFactory);
            this.mTexMatrix = new float[16];
            this.mParent = abstractRendererHolder;
            this.mEglTask = new EglTask(i3, iContext, i4) { // from class: com.serenegiant.glutils.AbstractRendererHolder.BaseRendererTask.1
                @Override // com.serenegiant.utils.MessageTask
                protected void onStart() {
                    BaseRendererTask.this.handleOnStart();
                }

                @Override // com.serenegiant.utils.MessageTask
                protected void onStop() {
                    BaseRendererTask.this.handleOnStop();
                }

                @Override // com.serenegiant.utils.MessageTask
                protected Object processRequest(int i5, int i6, int i7, Object obj) throws MessageTask.TaskBreak {
                    return BaseRendererTask.this.handleRequest(i5, i6, i7, obj);
                }
            };
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void release() {
            this.mEglTask.release();
            super.release();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void start(String str) {
            new Thread(this.mEglTask, str).start();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean waitReady() {
            return this.mEglTask.waitReady();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isRunning() {
            return this.mEglTask.isRunning();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isFinished() {
            return this.mEglTask.isFinished();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i) {
            return this.mEglTask.offer(i);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, Object obj) {
            return this.mEglTask.offer(i, obj);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, int i2) {
            return this.mEglTask.offer(i, i2);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, int i2, int i3) {
            return this.mEglTask.offer(i, i2, i3);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean offer(int i, int i2, int i3, Object obj) {
            return this.mEglTask.offer(i, i2, i3, obj);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void removeRequest(int i) {
            this.mEglTask.removeRequest(i);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public EGLBase getEgl() {
            return this.mEglTask.getEgl();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public GLContext getGLContext() {
            return this.mEglTask.getGLContext();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public EGLBase.IContext<?> getContext() {
            return this.mEglTask.getContext();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public int getGlVersion() {
            return this.mEglTask.getGlVersion();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void makeCurrent() {
            this.mEglTask.makeCurrent();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isGLES3() {
            return this.mEglTask.isGLES3();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isOES3Supported() {
            return this.mEglTask.isOES3Supported();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public boolean isMasterSurfaceValid() {
            Surface surface = this.mInputSurface;
            return surface != null && surface.isValid();
        }

        public int getTexId() {
            return this.mTexId;
        }

        public float[] getTexMatrix() {
            return this.mTexMatrix;
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void notifyParent(boolean z) {
            synchronized (this.mParent.mSync) {
                this.mParent.isRunning = z;
                this.mParent.mSync.notifyAll();
            }
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void callOnFrameAvailable() {
            this.mParent.callOnFrameAvailable();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void handleDrawTargets(boolean z, int i, float[] fArr) {
            super.handleDrawTargets(z, i, fArr);
            this.mParent.notifyCapture();
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        protected void handleReCreateInputSurface() {
            makeCurrent();
            handleReleaseInputSurface();
            makeCurrent();
            this.mTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33984, 9728);
            this.mInputTexture = new SurfaceTexture(this.mTexId);
            this.mInputSurface = new Surface(this.mInputTexture);
            if (BuildCheck.isAndroid4_1()) {
                this.mInputTexture.setDefaultBufferSize(width(), height());
            }
            this.mInputTexture.setOnFrameAvailableListener(this);
            this.mParent.callOnCreate(this.mInputSurface);
        }

        @Override // com.serenegiant.glutils.AbstractDistributeTask
        protected void handleReleaseInputSurface() {
            Surface surface = this.mInputSurface;
            if (surface != null) {
                try {
                    surface.release();
                } catch (Exception e) {
                    Log.w(AbstractRendererHolder.TAG, e);
                }
                this.mInputSurface = null;
                this.mParent.callOnDestroy();
            }
            SurfaceTexture surfaceTexture = this.mInputTexture;
            if (surfaceTexture != null) {
                try {
                    surfaceTexture.release();
                } catch (Exception e2) {
                    Log.w(AbstractRendererHolder.TAG, e2);
                }
                this.mInputTexture = null;
            }
            int i = this.mTexId;
            if (i != 0) {
                GLUtils.deleteTex(i);
                this.mTexId = 0;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void handleUpdateTexture() {
            this.mInputTexture.updateTexImage();
            this.mInputTexture.getTransformMatrix(this.mTexMatrix);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void handleResize(int i, int i2) {
            super.handleResize(i, i2);
            if (BuildCheck.isAndroid4_1()) {
                this.mInputTexture.setDefaultBufferSize(i, i2);
            }
        }

        public AbstractRendererHolder getParent() {
            return this.mParent;
        }

        public Surface getSurface() {
            checkMasterSurface();
            return this.mInputSurface;
        }

        public SurfaceTexture getSurfaceTexture() {
            checkMasterSurface();
            return this.mInputTexture;
        }

        public void reset() {
            checkMasterSurface();
        }

        public void checkMasterSurface() {
            checkFinished();
            Surface surface = this.mInputSurface;
            if (surface == null || !surface.isValid()) {
                Log.d(AbstractRendererHolder.TAG, "checkMasterSurface:invalid master surface");
                requestRecreateMasterSurface();
            }
        }

        public void queueEvent(Runnable runnable) {
            this.mEglTask.queueEvent(runnable);
        }

        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            requestFrame(true, this.mTexId, this.mTexMatrix);
        }
    }
}
