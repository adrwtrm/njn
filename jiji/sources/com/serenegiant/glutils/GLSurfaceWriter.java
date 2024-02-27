package com.serenegiant.glutils;

import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.math.Fraction;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class GLSurfaceWriter implements IMirror {
    private static final boolean DEBUG = false;
    private static final String TAG = "GLSurfaceWriter";
    private int cnt;
    private GLDrawer2D mDrawer;
    private final GLManager mManager;
    private final boolean mOwnManager;
    private RendererTarget mRendererTarget;
    private final Object mSync = new Object();
    private volatile boolean mReleased = false;
    private int mMirror = 0;

    public GLSurfaceWriter(GLManager gLManager, boolean z) {
        this.mOwnManager = z;
        if (z) {
            this.mManager = gLManager.createShared(null);
        } else {
            this.mManager = gLManager;
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
        if (this.mManager.isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.GLSurfaceWriter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    GLSurfaceWriter.this.m266lambda$internalRelease$0$comserenegiantglutilsGLSurfaceWriter();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$internalRelease$0$com-serenegiant-glutils-GLSurfaceWriter  reason: not valid java name */
    public /* synthetic */ void m266lambda$internalRelease$0$comserenegiantglutilsGLSurfaceWriter() {
        releaseTargetOnGL();
        if (this.mOwnManager) {
            this.mManager.release();
        }
    }

    public boolean isValid() {
        return !this.mReleased && this.mManager.isValid();
    }

    public boolean hasSurface() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mRendererTarget != null;
        }
        return z;
    }

    public int getId() {
        int id;
        synchronized (this.mSync) {
            RendererTarget rendererTarget = this.mRendererTarget;
            id = rendererTarget != null ? rendererTarget.getId() : 0;
        }
        return id;
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(final int i) {
        synchronized (this.mSync) {
            if (this.mMirror != i) {
                this.mMirror = i;
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.GLSurfaceWriter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        GLSurfaceWriter.this.m268lambda$setMirror$1$comserenegiantglutilsGLSurfaceWriter(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setMirror$1$com-serenegiant-glutils-GLSurfaceWriter  reason: not valid java name */
    public /* synthetic */ void m268lambda$setMirror$1$comserenegiantglutilsGLSurfaceWriter(int i) {
        RendererTarget rendererTarget = this.mRendererTarget;
        if (rendererTarget != null) {
            rendererTarget.setMirror(IMirror.flipVertical(i));
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

    public void setSurface(Object obj) throws IllegalStateException, IllegalArgumentException {
        setSurface(obj, null);
    }

    public void setSurface(final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        if (!isValid()) {
            throw new IllegalStateException("already released?");
        }
        if (obj == null || GLUtils.isSupportedSurface(obj)) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.GLSurfaceWriter$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    GLSurfaceWriter.this.m269lambda$setSurface$2$comserenegiantglutilsGLSurfaceWriter(obj, fraction);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Unsupported surface type!," + obj);
    }

    public void onFrameAvailable(final boolean z, final int i, final float[] fArr) {
        if (isValid()) {
            if (this.mManager.isGLThread()) {
                renderTargetOnGL(z, i, fArr);
                return;
            }
            final Semaphore semaphore = new Semaphore(0);
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.GLSurfaceWriter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GLSurfaceWriter.this.m267xbc818e2d(z, i, fArr, semaphore);
                }
            });
            try {
                semaphore.tryAcquire(50L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onFrameAvailable$3$com-serenegiant-glutils-GLSurfaceWriter  reason: not valid java name */
    public /* synthetic */ void m267xbc818e2d(boolean z, int i, float[] fArr, Semaphore semaphore) {
        renderTargetOnGL(z, i, fArr);
        semaphore.release();
    }

    private void renderTargetOnGL(boolean z, int i, float[] fArr) {
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
    public void m269lambda$setSurface$2$comserenegiantglutilsGLSurfaceWriter(Object obj, Fraction fraction) {
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

    private void releaseTargetOnGL() {
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
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glutils.GLSurfaceWriter.1
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
