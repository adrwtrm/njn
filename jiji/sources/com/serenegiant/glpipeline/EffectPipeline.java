package com.serenegiant.glpipeline;

import com.serenegiant.gl.EffectDrawer2D;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLSurface;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class EffectPipeline extends ProxyPipeline implements GLSurfacePipeline, IMirror {
    private static final boolean DEBUG = false;
    private static final String TAG = "EffectPipeline";
    private int cnt;
    private EffectDrawer2D mDrawer;
    private int mEffect;
    final EffectDrawer2D.EffectListener mEffectListener;
    private volatile boolean mEffectOnly;
    private final GLManager mManager;
    private int mMirror;
    private RendererTarget mRendererTarget;
    private final Object mSync;
    private GLSurface work;

    protected boolean onChangeEffect(int i, GLDrawer2D gLDrawer2D) {
        return false;
    }

    public EffectPipeline(GLManager gLManager) throws IllegalStateException, IllegalArgumentException {
        this(gLManager, null, null);
    }

    public EffectPipeline(GLManager gLManager, final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        this.mSync = new Object();
        this.mEffect = 0;
        this.mMirror = 0;
        this.mEffectListener = new EffectDrawer2D.EffectListener() { // from class: com.serenegiant.glpipeline.EffectPipeline.6
            @Override // com.serenegiant.gl.EffectDrawer2D.EffectListener
            public boolean onChangeEffect(int i, GLDrawer2D gLDrawer2D) {
                return EffectPipeline.this.onChangeEffect(i, gLDrawer2D);
            }
        };
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager = gLManager;
        gLManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline.1
            @Override // java.lang.Runnable
            public void run() {
                EffectPipeline.this.createTargetOnGL(obj, fraction);
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
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline.2
            @Override // java.lang.Runnable
            public void run() {
                EffectPipeline.this.createTargetOnGL(obj, fraction);
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

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public int getId() {
        int id;
        synchronized (this.mSync) {
            RendererTarget rendererTarget = this.mRendererTarget;
            id = rendererTarget != null ? rendererTarget.getId() : 0;
        }
        return id;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return super.isValid() && this.mManager.isValid();
    }

    public boolean isEffectOnly() {
        return this.mEffectOnly;
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(final int i) {
        synchronized (this.mSync) {
            if (this.mMirror != i) {
                this.mMirror = i;
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EffectPipeline.this.m264lambda$setMirror$0$comserenegiantglpipelineEffectPipeline(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setMirror$0$com-serenegiant-glpipeline-EffectPipeline  reason: not valid java name */
    public /* synthetic */ void m264lambda$setMirror$0$comserenegiantglpipelineEffectPipeline(int i) {
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
        RendererTarget rendererTarget;
        GLSurface gLSurface;
        if (isValid()) {
            EffectDrawer2D effectDrawer2D = this.mDrawer;
            if (effectDrawer2D == null || z != effectDrawer2D.isOES()) {
                EffectDrawer2D effectDrawer2D2 = this.mDrawer;
                if (effectDrawer2D2 != null) {
                    effectDrawer2D2.release();
                }
                EffectDrawer2D effectDrawer2D3 = new EffectDrawer2D(this.mManager.isGLES3(), z, this.mEffectListener);
                this.mDrawer = effectDrawer2D3;
                effectDrawer2D3.setEffect(this.mEffect);
            }
            EffectDrawer2D effectDrawer2D4 = this.mDrawer;
            synchronized (this.mSync) {
                rendererTarget = this.mRendererTarget;
            }
            if (rendererTarget != null && rendererTarget.canDraw()) {
                rendererTarget.draw(effectDrawer2D4, 33984, i, fArr);
            }
            if (this.mEffectOnly && (gLSurface = this.work) != null) {
                super.onFrameAvailable(gLSurface.isOES(), this.work.getTexId(), this.work.getTexMatrix());
            } else {
                super.onFrameAvailable(z, i, fArr);
            }
        }
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        super.refresh();
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline.3
                @Override // java.lang.Runnable
                public void run() {
                    EffectDrawer2D effectDrawer2D = EffectPipeline.this.mDrawer;
                    EffectPipeline.this.mDrawer = null;
                    if (effectDrawer2D != null) {
                        synchronized (EffectPipeline.this.mSync) {
                            EffectPipeline.this.mEffect = effectDrawer2D.getCurrentEffect();
                        }
                        effectDrawer2D.release();
                    }
                }
            });
        }
    }

    public void resetEffect() throws IllegalStateException {
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline.4
                @Override // java.lang.Runnable
                public void run() {
                    if (EffectPipeline.this.mDrawer != null) {
                        EffectPipeline.this.mDrawer.resetEffect();
                        synchronized (EffectPipeline.this.mSync) {
                            EffectPipeline effectPipeline = EffectPipeline.this;
                            effectPipeline.mEffect = effectPipeline.mDrawer.getCurrentEffect();
                        }
                    }
                }
            });
            return;
        }
        throw new IllegalStateException("already released!");
    }

    public void setEffect(final int i) throws IllegalStateException {
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline.5
                @Override // java.lang.Runnable
                public void run() {
                    if (EffectPipeline.this.mDrawer != null) {
                        EffectPipeline.this.mDrawer.setEffect(i);
                        synchronized (EffectPipeline.this.mSync) {
                            EffectPipeline effectPipeline = EffectPipeline.this;
                            effectPipeline.mEffect = effectPipeline.mDrawer.getCurrentEffect();
                        }
                    }
                }
            });
            return;
        }
        throw new IllegalStateException("already released!");
    }

    public int getCurrentEffect() {
        int i;
        synchronized (this.mSync) {
            i = this.mEffect;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTargetOnGL(Object obj, Fraction fraction) {
        synchronized (this.mSync) {
            RendererTarget rendererTarget = this.mRendererTarget;
            if (rendererTarget == null || rendererTarget.getSurface() != obj) {
                RendererTarget rendererTarget2 = this.mRendererTarget;
                if (rendererTarget2 != null) {
                    rendererTarget2.release();
                    this.mRendererTarget = null;
                }
                GLSurface gLSurface = this.work;
                if (gLSurface != null) {
                    gLSurface.release();
                    this.work = null;
                }
                if (GLUtils.isSupportedSurface(obj)) {
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), obj, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mEffectOnly = false;
                } else {
                    this.work = GLSurface.newInstance(this.mManager.isGLES3(), getWidth(), getHeight());
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), this.work, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mEffectOnly = true;
                }
                this.mRendererTarget.setMirror(IMirror.flipVertical(this.mMirror));
            }
        }
    }

    private void releaseTarget() {
        final RendererTarget rendererTarget;
        final GLSurface gLSurface;
        final EffectDrawer2D effectDrawer2D = this.mDrawer;
        this.mDrawer = null;
        synchronized (this.mSync) {
            rendererTarget = this.mRendererTarget;
            this.mRendererTarget = null;
            gLSurface = this.work;
            this.work = null;
        }
        if (!(effectDrawer2D == null && rendererTarget == null) && this.mManager.isValid()) {
            try {
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EffectPipeline.7
                    @Override // java.lang.Runnable
                    public void run() {
                        EffectDrawer2D effectDrawer2D2 = effectDrawer2D;
                        if (effectDrawer2D2 != null) {
                            effectDrawer2D2.release();
                        }
                        RendererTarget rendererTarget2 = rendererTarget;
                        if (rendererTarget2 != null) {
                            rendererTarget2.release();
                        }
                        GLSurface gLSurface2 = gLSurface;
                        if (gLSurface2 != null) {
                            gLSurface2.release();
                        }
                    }
                });
            } catch (Exception unused) {
            }
        }
    }
}
