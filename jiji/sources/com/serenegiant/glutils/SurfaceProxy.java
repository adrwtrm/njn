package com.serenegiant.glutils;

import android.graphics.SurfaceTexture;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.view.Surface;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.glutils.GLImageReceiver;
import com.serenegiant.glutils.SurfaceProxy;
import com.serenegiant.math.Fraction;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class SurfaceProxy implements GLConst, IMirror {
    private static final String TAG = "SurfaceProxy";
    private int mHeight;
    protected int mMirror;
    private volatile boolean mReleased;
    protected final Object mSync;
    private int mWidth;

    public abstract Surface getInputSurface();

    public abstract void setSurface(Object obj, Fraction fraction);

    public static SurfaceProxy newInstance(int i, int i2, boolean z) {
        if (z && BuildCheck.isAPI23()) {
            return new SurfaceProxyReaderWriter(i, i2);
        }
        return new SurfaceProxyGLES(i, i2);
    }

    private SurfaceProxy(int i, int i2) {
        this.mSync = new Object();
        this.mReleased = false;
        this.mMirror = 0;
        this.mWidth = Math.max(i, 1);
        this.mHeight = Math.max(i2, 1);
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
        this.mReleased = true;
    }

    public boolean isValid() {
        return !this.mReleased;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        int i;
        synchronized (this.mSync) {
            i = this.mMirror;
        }
        return i;
    }

    protected void resize(int i, int i2) {
        this.mWidth = Math.max(i, 1);
        this.mHeight = Math.max(i2, 1);
    }

    public void setSurface(Object obj) {
        setSurface(obj, null);
    }

    /* loaded from: classes2.dex */
    public static class SurfaceProxyReaderWriter extends SurfaceProxy {
        private static final boolean DEBUG = false;
        private static final int MAX_IMAGES = 2;
        private static final String TAG = "SurfaceProxyReaderWriter";
        private final Handler mAsyncHandler;
        private android.media.ImageReader mImageReader;
        private ImageWriter mImageWriter;

        private SurfaceProxyReaderWriter(int i, int i2) {
            super(i, i2);
            HandlerThreadHandler createHandler = HandlerThreadHandler.createHandler(TAG);
            this.mAsyncHandler = createHandler;
            android.media.ImageReader newInstance = android.media.ImageReader.newInstance(getWidth(), getHeight(), 1, 2);
            this.mImageReader = newInstance;
            newInstance.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { // from class: com.serenegiant.glutils.SurfaceProxy.SurfaceProxyReaderWriter.1
                private int cnt = 0;

                @Override // android.media.ImageReader.OnImageAvailableListener
                public void onImageAvailable(android.media.ImageReader imageReader) {
                    Image acquireLatestImage = imageReader.acquireLatestImage();
                    if (acquireLatestImage != null) {
                        synchronized (SurfaceProxyReaderWriter.this.mSync) {
                            if (SurfaceProxyReaderWriter.this.mImageWriter != null) {
                                SurfaceProxyReaderWriter.this.mImageWriter.queueInputImage(acquireLatestImage);
                            } else {
                                acquireLatestImage.close();
                            }
                        }
                    }
                }
            }, createHandler);
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        protected void internalRelease() {
            synchronized (this.mSync) {
                ImageWriter imageWriter = this.mImageWriter;
                if (imageWriter != null) {
                    imageWriter.close();
                    this.mImageWriter = null;
                }
                android.media.ImageReader imageReader = this.mImageReader;
                if (imageReader != null) {
                    imageReader.close();
                    this.mImageReader = null;
                }
            }
            HandlerUtils.NoThrowQuit(this.mAsyncHandler);
            super.internalRelease();
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        public Surface getInputSurface() {
            Surface surface;
            synchronized (this.mSync) {
                if (this.mImageReader != null && isValid()) {
                    surface = this.mImageReader.getSurface();
                } else {
                    throw new IllegalStateException("already released?");
                }
            }
            return surface;
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        public void setSurface(Object obj, Fraction fraction) {
            synchronized (this.mSync) {
                ImageWriter imageWriter = this.mImageWriter;
                if (imageWriter != null) {
                    imageWriter.close();
                    this.mImageWriter = null;
                }
                if (obj instanceof Surface) {
                    this.mImageWriter = ImageWriter.newInstance((Surface) obj, 2);
                }
            }
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        protected void resize(int i, int i2) {
            super.resize(i, i2);
            throw new UnsupportedOperationException("SurfaceProxyReaderWriter does not support #resize");
        }

        @Override // com.serenegiant.glutils.IMirror
        public void setMirror(int i) {
            throw new UnsupportedOperationException("SurfaceProxyReaderWriter does not support #setMirror");
        }
    }

    /* loaded from: classes2.dex */
    public static class SurfaceProxyGLES extends SurfaceProxy {
        private static final boolean DEBUG = false;
        private static final String TAG = "SurfaceProxyGLES";
        private int cnt;
        private GLDrawer2D mDrawer;
        private final GLManager mManager;
        private final GLImageReceiver mReceiver;
        private RendererTarget mRendererTarget;

        private SurfaceProxyGLES(int i, int i2) throws IllegalStateException {
            this(new GLManager(), i, i2, false);
        }

        public SurfaceProxyGLES(GLManager gLManager, int i, int i2, boolean z) throws IllegalStateException {
            super(i, i2);
            this.mDrawer = null;
            this.mRendererTarget = null;
            this.cnt = 0;
            final Semaphore semaphore = new Semaphore(0);
            this.mReceiver = new GLImageReceiver(gLManager, z, i, i2, new GLImageReceiver.Callback() { // from class: com.serenegiant.glutils.SurfaceProxy.SurfaceProxyGLES.1
                @Override // com.serenegiant.glutils.GLImageReceiver.Callback
                public void onInitialize(GLImageReceiver gLImageReceiver) {
                }

                @Override // com.serenegiant.glutils.GLImageReceiver.Callback
                public void onReleaseInputSurface(GLImageReceiver gLImageReceiver) {
                }

                @Override // com.serenegiant.glutils.GLImageReceiver.Callback
                public void onResize(int i3, int i4) {
                }

                @Override // com.serenegiant.glutils.GLImageReceiver.Callback
                public void onRelease() {
                    SurfaceProxyGLES.this.releaseTargetOnGL();
                }

                @Override // com.serenegiant.glutils.GLImageReceiver.Callback
                public void onCreateInputSurface(GLImageReceiver gLImageReceiver) {
                    semaphore.release();
                }

                @Override // com.serenegiant.glutils.GLImageReceiver.Callback
                public void onFrameAvailable(GLImageReceiver gLImageReceiver, boolean z2, int i3, float[] fArr) {
                    SurfaceProxyGLES.this.renderTargetOnGL(z2, i3, fArr);
                }
            });
            if (!semaphore.tryAcquire(1000L, TimeUnit.MILLISECONDS)) {
                throw new IllegalStateException();
            }
            this.mManager = this.mReceiver.getGLManager();
            getInputSurface();
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        protected void internalRelease() {
            if (isValid()) {
                this.mReceiver.release();
            }
            super.internalRelease();
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        public void resize(int i, int i2) throws IllegalStateException {
            super.resize(i, i2);
            checkValid();
            this.mReceiver.resize(i, i2);
        }

        @Override // com.serenegiant.glutils.IMirror
        public void setMirror(final int i) {
            synchronized (this.mSync) {
                if (this.mMirror != i) {
                    this.mMirror = i;
                    this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.SurfaceProxy$SurfaceProxyGLES$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceProxy.SurfaceProxyGLES.this.m271x979fe5b2(i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$setMirror$0$com-serenegiant-glutils-SurfaceProxy$SurfaceProxyGLES  reason: not valid java name */
        public /* synthetic */ void m271x979fe5b2(int i) {
            RendererTarget rendererTarget = this.mRendererTarget;
            if (rendererTarget != null) {
                rendererTarget.setMirror(IMirror.flipVertical(i));
            }
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        public boolean isValid() {
            return super.isValid() && this.mReceiver.isValid();
        }

        public SurfaceTexture getInputSurfaceTexture() throws IllegalStateException {
            checkValid();
            return this.mReceiver.getSurfaceTexture();
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        public Surface getInputSurface() throws IllegalStateException {
            checkValid();
            return this.mReceiver.getSurface();
        }

        public int getTexId() {
            checkValid();
            return this.mReceiver.getTexId();
        }

        public float[] getTexMatrix() {
            checkValid();
            return this.mReceiver.getTexMatrix();
        }

        @Override // com.serenegiant.glutils.SurfaceProxy
        public void setSurface(final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
            checkValid();
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.SurfaceProxy$SurfaceProxyGLES$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceProxy.SurfaceProxyGLES.this.m272x5bcedfb7(obj, fraction);
                }
            });
        }

        protected void makeDefault() {
            this.mManager.makeDefault();
        }

        protected void checkValid() throws IllegalStateException {
            if (!this.mReceiver.isValid()) {
                throw new IllegalStateException("Already released");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void renderTargetOnGL(boolean z, int i, float[] fArr) {
            GLDrawer2D gLDrawer2D;
            RendererTarget rendererTarget;
            synchronized (this.mSync) {
                GLDrawer2D gLDrawer2D2 = this.mDrawer;
                if (gLDrawer2D2 == null || z != gLDrawer2D2.isOES()) {
                    GLDrawer2D gLDrawer2D3 = this.mDrawer;
                    if (gLDrawer2D3 != null) {
                        gLDrawer2D3.release();
                    }
                    this.mDrawer = GLDrawer2D.create(this.mManager.isGLES3(), z);
                }
                gLDrawer2D = this.mDrawer;
                rendererTarget = this.mRendererTarget;
            }
            if (rendererTarget == null || !rendererTarget.canDraw()) {
                return;
            }
            rendererTarget.draw(gLDrawer2D, 33984, i, fArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: createTargetOnGL */
        public void m272x5bcedfb7(Object obj, Fraction fraction) {
            synchronized (this.mSync) {
                synchronized (this.mSync) {
                    RendererTarget rendererTarget = this.mRendererTarget;
                    if (rendererTarget != null && rendererTarget.getSurface() != obj) {
                        this.mRendererTarget.release();
                        this.mRendererTarget = null;
                    }
                    if (this.mRendererTarget == null && obj != null) {
                        this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), obj, fraction != null ? fraction.asFloat() : 0.0f);
                    }
                    RendererTarget rendererTarget2 = this.mRendererTarget;
                    if (rendererTarget2 != null) {
                        rendererTarget2.setMirror(IMirror.flipVertical(this.mMirror));
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseTargetOnGL() {
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
                    this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.SurfaceProxy.SurfaceProxyGLES.2
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
}
