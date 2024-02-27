package com.serenegiant.glpipeline;

import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLSurface;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class DrawerPipeline extends ProxyPipeline implements GLSurfacePipeline, IMirror {
    private static final boolean DEBUG = false;
    public static Callback DEFAULT_CALLBACK = new Callback() { // from class: com.serenegiant.glpipeline.DrawerPipeline.1
        @Override // com.serenegiant.glpipeline.DrawerPipeline.Callback
        public GLDrawer2D createDrawer(GLManager gLManager, boolean z) {
            return GLDrawer2D.create(gLManager.isGLES3(), z);
        }

        @Override // com.serenegiant.glpipeline.DrawerPipeline.Callback
        public void releaseDrawer(GLManager gLManager, GLDrawer2D gLDrawer2D) {
            gLDrawer2D.release();
        }

        @Override // com.serenegiant.glpipeline.DrawerPipeline.Callback
        public GLDrawer2D onResize(GLManager gLManager, GLDrawer2D gLDrawer2D, int i, int i2) {
            if (gLDrawer2D != null) {
                gLDrawer2D.release();
                return null;
            }
            return null;
        }
    };
    private static final String TAG = "DrawerPipeline";
    private int cnt;
    private final Callback mCallback;
    private volatile boolean mDrawOnly;
    private GLDrawer2D mDrawer;
    private final GLManager mManager;
    private int mMirror;
    private RendererTarget mRendererTarget;
    private final Object mSync;
    private GLSurface offscreenSurface;

    /* loaded from: classes2.dex */
    public interface Callback {
        GLDrawer2D createDrawer(GLManager gLManager, boolean z);

        GLDrawer2D onResize(GLManager gLManager, GLDrawer2D gLDrawer2D, int i, int i2);

        void releaseDrawer(GLManager gLManager, GLDrawer2D gLDrawer2D);
    }

    public DrawerPipeline(GLManager gLManager, Callback callback) throws IllegalStateException, IllegalArgumentException {
        this(gLManager, callback, null, null);
    }

    public DrawerPipeline(GLManager gLManager, Callback callback, final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        this.mSync = new Object();
        this.mMirror = 0;
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager = gLManager;
        this.mCallback = callback;
        gLManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.DrawerPipeline.2
            @Override // java.lang.Runnable
            public void run() {
                DrawerPipeline.this.createTargetOnGL(obj, fraction);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        if (isValid()) {
            releaseAll();
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
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.DrawerPipeline.3
            @Override // java.lang.Runnable
            public void run() {
                DrawerPipeline.this.createTargetOnGL(obj, fraction);
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

    public boolean isDrawOnly() {
        return this.mDrawOnly;
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(final int i) {
        synchronized (this.mSync) {
            if (this.mMirror != i) {
                this.mMirror = i;
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.DrawerPipeline$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrawerPipeline.this.m263lambda$setMirror$0$comserenegiantglpipelineDrawerPipeline(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setMirror$0$com-serenegiant-glpipeline-DrawerPipeline  reason: not valid java name */
    public /* synthetic */ void m263lambda$setMirror$0$comserenegiantglpipelineDrawerPipeline(int i) {
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
        GLDrawer2D gLDrawer2D = this.mDrawer;
        if (gLDrawer2D == null || z != gLDrawer2D.isOES()) {
            GLDrawer2D gLDrawer2D2 = this.mDrawer;
            if (gLDrawer2D2 != null) {
                this.mCallback.releaseDrawer(this.mManager, gLDrawer2D2);
            }
            this.mDrawer = this.mCallback.createDrawer(this.mManager, z);
        }
        synchronized (this.mSync) {
            rendererTarget = this.mRendererTarget;
        }
        if (rendererTarget != null && rendererTarget.canDraw()) {
            rendererTarget.draw(this.mDrawer, 33984, i, fArr);
        }
        if (this.mDrawOnly && (gLSurface = this.offscreenSurface) != null) {
            super.onFrameAvailable(gLSurface.isOES(), this.offscreenSurface.getTexId(), this.offscreenSurface.getTexMatrix());
        } else {
            super.onFrameAvailable(z, i, fArr);
        }
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        super.refresh();
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.DrawerPipeline.4
                @Override // java.lang.Runnable
                public void run() {
                    if (DrawerPipeline.this.mDrawer != null) {
                        DrawerPipeline.this.mCallback.releaseDrawer(DrawerPipeline.this.mManager, DrawerPipeline.this.mDrawer);
                        DrawerPipeline.this.mDrawer = null;
                    }
                }
            });
        }
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void resize(final int i, final int i2) throws IllegalStateException {
        super.resize(i, i2);
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.DrawerPipeline.5
            @Override // java.lang.Runnable
            public void run() {
                DrawerPipeline drawerPipeline = DrawerPipeline.this;
                drawerPipeline.mDrawer = drawerPipeline.mCallback.onResize(DrawerPipeline.this.mManager, DrawerPipeline.this.mDrawer, i, i2);
            }
        });
    }

    private void releaseAll() {
        if (this.mManager.isValid()) {
            try {
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.DrawerPipeline.6
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (DrawerPipeline.this.mSync) {
                            if (DrawerPipeline.this.mRendererTarget != null) {
                                DrawerPipeline.this.mRendererTarget.release();
                                DrawerPipeline.this.mRendererTarget = null;
                            }
                            if (DrawerPipeline.this.offscreenSurface != null) {
                                DrawerPipeline.this.offscreenSurface.release();
                                DrawerPipeline.this.offscreenSurface = null;
                            }
                        }
                        if (DrawerPipeline.this.mDrawer != null) {
                            DrawerPipeline.this.mCallback.releaseDrawer(DrawerPipeline.this.mManager, DrawerPipeline.this.mDrawer);
                        }
                        DrawerPipeline.this.mDrawer = null;
                    }
                });
            } catch (Exception unused) {
            }
        }
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
                GLSurface gLSurface = this.offscreenSurface;
                if (gLSurface != null) {
                    gLSurface.release();
                    this.offscreenSurface = null;
                }
                if (GLUtils.isSupportedSurface(obj)) {
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), obj, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mDrawOnly = false;
                } else if (isValid()) {
                    this.offscreenSurface = GLSurface.newInstance(this.mManager.isGLES3(), getWidth(), getHeight());
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), this.offscreenSurface, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mDrawOnly = true;
                }
                RendererTarget rendererTarget3 = this.mRendererTarget;
                if (rendererTarget3 != null) {
                    rendererTarget3.setMirror(IMirror.flipVertical(this.mMirror));
                }
            }
        }
    }
}
