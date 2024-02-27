package com.serenegiant.glpipeline;

import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class SurfaceRendererPipeline extends ProxyPipeline implements GLSurfacePipeline, IMirror {
    private static final boolean DEBUG = false;
    private static final String TAG = "SurfaceRendererPipeline";
    private int cnt;
    private GLDrawer2D mDrawer;
    private final GLManager mManager;
    private int mMirror;
    private RendererTarget mRendererTarget;
    private final Object mSync;

    public SurfaceRendererPipeline(GLManager gLManager) throws IllegalStateException, IllegalArgumentException {
        this(gLManager, null, null);
    }

    public SurfaceRendererPipeline(GLManager gLManager, final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        this.mSync = new Object();
        this.mMirror = 0;
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager = gLManager;
        gLManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceRendererPipeline.1
            @Override // java.lang.Runnable
            public void run() {
                SurfaceRendererPipeline.this.createTargetOnGL(obj, fraction);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        if (isValid()) {
            releaseTarget();
        }
        super.internalRelease();
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public void setSurface(Object obj) throws IllegalStateException, IllegalArgumentException {
        setSurface(obj, null);
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public void setSurface(final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        if (!isValid()) {
            throw new IllegalStateException("already released?");
        }
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceRendererPipeline.2
            @Override // java.lang.Runnable
            public void run() {
                SurfaceRendererPipeline.this.createTargetOnGL(obj, fraction);
            }
        });
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public boolean hasSurface() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mRendererTarget != null;
        }
        return z;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return super.isValid() && this.mManager.isValid();
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
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
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceRendererPipeline$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceRendererPipeline.this.m265x2f92d4b0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setMirror$0$com-serenegiant-glpipeline-SurfaceRendererPipeline  reason: not valid java name */
    public /* synthetic */ void m265x2f92d4b0(int i) {
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

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        GLDrawer2D gLDrawer2D;
        RendererTarget rendererTarget;
        super.onFrameAvailable(z, i, fArr);
        if (isValid()) {
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
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        super.refresh();
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceRendererPipeline.3
                @Override // java.lang.Runnable
                public void run() {
                    GLDrawer2D gLDrawer2D;
                    synchronized (SurfaceRendererPipeline.this.mSync) {
                        gLDrawer2D = SurfaceRendererPipeline.this.mDrawer;
                        SurfaceRendererPipeline.this.mDrawer = null;
                    }
                    if (gLDrawer2D != null) {
                        gLDrawer2D.release();
                    }
                }
            });
        }
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
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), obj, fraction != null ? fraction.asFloat() : 0.0f);
                }
                RendererTarget rendererTarget2 = this.mRendererTarget;
                if (rendererTarget2 != null) {
                    rendererTarget2.setMirror(IMirror.flipVertical(this.mMirror));
                }
            }
        }
    }

    private void releaseTarget() {
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
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.SurfaceRendererPipeline.4
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
