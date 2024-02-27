package com.serenegiant.widget;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLContext;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.ISurface;
import com.serenegiant.widget.GLView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GLView.kt */
@Metadata(d1 = {"\u0000s\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\n*\u0001\u000b\b\u0016\u0018\u0000 ;2\u00020\u00012\u00020\u0002:\u0002;<B'\b\u0007\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0012\u0010\u001c\u001a\u00020\u001d2\b\b\u0001\u0010\u001e\u001a\u00020\u0018H\u0005J\b\u0010\u001f\u001a\u00020\u001dH\u0005J\b\u0010 \u001a\u00020\u000eH\u0007J\b\u0010!\u001a\u00020\u0012H\u0007J\u0014\u0010\"\u001a\u00020\u00182\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u0018H\u0017J\b\u0010#\u001a\u00020$H\u0017J\b\u0010%\u001a\u00020\u0016H\u0007J\b\u0010&\u001a\u00020\u0016H\u0007J\b\u0010'\u001a\u00020\u001dH\u0005J\b\u0010(\u001a\u00020\u001dH\u0014J \u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\bH\u0005J\b\u0010-\u001a\u00020\u001dH\u0005J\b\u0010.\u001a\u00020\u001dH\u0005J\u0010\u0010/\u001a\u00020\u001d2\u0006\u00100\u001a\u000201H\u0007J\u0018\u0010/\u001a\u00020\u001d2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020\u001d2\u0006\u00100\u001a\u000201H\u0007J\u0012\u00105\u001a\u00020\u001d2\b\u00106\u001a\u0004\u0018\u00010\u0014H\u0007J\u0014\u00107\u001a\u00020\u001d2\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u0018H\u0017J(\u00108\u001a\u00020\u001d2\u0006\u00109\u001a\u00020\b2\u0006\u0010:\u001a\u00020\b2\u0006\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\bH\u0007R\u0010\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lcom/serenegiant/widget/GLView;", "Landroid/view/SurfaceView;", "Lcom/serenegiant/widget/IGLTransformView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mChoreographerCallback", "com/serenegiant/widget/GLView$mChoreographerCallback$1", "Lcom/serenegiant/widget/GLView$mChoreographerCallback$1;", "mGLContext", "Lcom/serenegiant/gl/GLContext;", "mGLHandler", "Landroid/os/Handler;", "mGLManager", "Lcom/serenegiant/gl/GLManager;", "mGLRenderer", "Lcom/serenegiant/widget/GLView$GLRenderer;", "mHasSurface", "", "mMatrix", "", "mMatrixChanged", "mTarget", "Lcom/serenegiant/gl/ISurface;", "applyTransformMatrix", "", "transform", "drawFrame", "getGLContext", "getGLManager", "getTransform", "getView", "Landroid/view/View;", "isGLES3", "isOES3Supported", "makeDefault", "onDetachedFromWindow", "onSurfaceChanged", "format", "width", "height", "onSurfaceCreated", "onSurfaceDestroyed", "queueEvent", "task", "Ljava/lang/Runnable;", "delayMs", "", "removeEvent", "setRenderer", "renderer", "setTransform", "setViewport", "x", "y", "Companion", "GLRenderer", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class GLView extends SurfaceView implements IGLTransformView {
    private static final boolean DEBUG = false;
    private GLView$mChoreographerCallback$1 mChoreographerCallback;
    private final GLContext mGLContext;
    private final Handler mGLHandler;
    private final GLManager mGLManager;
    private GLRenderer mGLRenderer;
    private volatile boolean mHasSurface;
    private final float[] mMatrix;
    private boolean mMatrixChanged;
    private ISurface mTarget;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "GLView";

    /* compiled from: GLView.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H'J\b\u0010\u0006\u001a\u00020\u0003H'J \u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH'J\b\u0010\f\u001a\u00020\u0003H'J\b\u0010\r\u001a\u00020\u0003H'¨\u0006\u000e"}, d2 = {"Lcom/serenegiant/widget/GLView$GLRenderer;", "", "applyTransformMatrix", "", "transform", "", "drawFrame", "onSurfaceChanged", "format", "", "width", "height", "onSurfaceCreated", "onSurfaceDestroyed", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public interface GLRenderer {
        void applyTransformMatrix(float[] fArr);

        void drawFrame();

        void onSurfaceChanged(int i, int i2, int i3);

        void onSurfaceCreated();

        void onSurfaceDestroyed();
    }

    public static /* synthetic */ void $r8$lambda$0x2_QbTHfhB01LPNvirSvRS9HmA(GLView gLView, GLRenderer gLRenderer) {
        m281setRenderer$lambda0(gLView, gLRenderer);
    }

    public static /* synthetic */ void $r8$lambda$UvMBDy1jFmx_hXIJ6OjpkqCOffA(GLView gLView, int i, int i2, int i3, int i4) {
        m282setViewport$lambda1(gLView, i, i2, i3, i4);
    }

    public GLView(Context context) {
        this(context, null, 0, 6, null);
    }

    public GLView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ GLView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public static final /* synthetic */ GLManager access$getMGLManager$p(GLView gLView) {
        return gLView.mGLManager;
    }

    public static final /* synthetic */ boolean access$getMHasSurface$p(GLView gLView) {
        return gLView.mHasSurface;
    }

    public static final /* synthetic */ float[] access$getMMatrix$p(GLView gLView) {
        return gLView.mMatrix;
    }

    public static final /* synthetic */ boolean access$getMMatrixChanged$p(GLView gLView) {
        return gLView.mMatrixChanged;
    }

    public static final /* synthetic */ ISurface access$getMTarget$p(GLView gLView) {
        return gLView.mTarget;
    }

    public static final /* synthetic */ void access$setMMatrixChanged$p(GLView gLView, boolean z) {
        gLView.mMatrixChanged = z;
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.serenegiant.widget.GLView$mChoreographerCallback$1] */
    public GLView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        float[] fArr = new float[16];
        this.mMatrix = fArr;
        GLManager gLManager = new GLManager();
        this.mGLManager = gLManager;
        GLContext gLContext = gLManager.getGLContext();
        Intrinsics.checkNotNullExpressionValue(gLContext, "mGLManager.glContext");
        this.mGLContext = gLContext;
        Handler gLHandler = gLManager.getGLHandler();
        Intrinsics.checkNotNullExpressionValue(gLHandler, "mGLManager.glHandler");
        this.mGLHandler = gLHandler;
        Matrix.setIdentityM(fArr, 0);
        getHolder().addCallback(new AnonymousClass1());
        this.mChoreographerCallback = new Choreographer.FrameCallback() { // from class: com.serenegiant.widget.GLView$mChoreographerCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                if (GLView.access$getMHasSurface$p(GLView.this)) {
                    GLView.access$getMGLManager$p(GLView.this).postFrameCallbackDelayed(this, 0L);
                    GLView.this.makeDefault();
                    float[] access$getMMatrix$p = GLView.access$getMMatrix$p(GLView.this);
                    GLView gLView = GLView.this;
                    synchronized (access$getMMatrix$p) {
                        if (GLView.access$getMMatrixChanged$p(gLView)) {
                            gLView.applyTransformMatrix(GLView.access$getMMatrix$p(gLView));
                            GLView.access$setMMatrixChanged$p(gLView, false);
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    GLView.this.drawFrame();
                    ISurface access$getMTarget$p = GLView.access$getMTarget$p(GLView.this);
                    if (access$getMTarget$p != null) {
                        access$getMTarget$p.swap();
                    }
                }
            }
        };
    }

    /* compiled from: GLView.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\f"}, d2 = {"com/serenegiant/widget/GLView$1", "Landroid/view/SurfaceHolder$Callback;", "surfaceChanged", "", "holder", "Landroid/view/SurfaceHolder;", "format", "", "width", "height", "surfaceCreated", "surfaceDestroyed", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* renamed from: com.serenegiant.widget.GLView$1 */
    /* loaded from: classes2.dex */
    public static final class AnonymousClass1 implements SurfaceHolder.Callback {
        public static /* synthetic */ void $r8$lambda$1iM63LsapP8bR2FToF3sIP7fmWU(GLView gLView, int i, int i2, int i3) {
            m284surfaceChanged$lambda1(gLView, i, i2, i3);
        }

        public static /* synthetic */ void $r8$lambda$nGaORhOYmAfJmZkUyQrLWr_hzxU(GLView gLView) {
            m285surfaceCreated$lambda0(gLView);
        }

        /* renamed from: $r8$lambda$oOzYvY-yPvrW3C1neI0TGpIkmdY */
        public static /* synthetic */ void m283$r8$lambda$oOzYvYyPvrW3C1neI0TGpIkmdY(GLView gLView) {
            m286surfaceDestroyed$lambda2(gLView);
        }

        AnonymousClass1() {
            GLView.this = r1;
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder holder) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            if (GLView.this.getWidth() <= 0 || GLView.this.getHeight() <= 0) {
                return;
            }
            GLView.this.mHasSurface = true;
            GLView.this.mMatrixChanged = true;
            final GLView gLView = GLView.this;
            gLView.queueEvent(new Runnable() { // from class: com.serenegiant.widget.GLView$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    GLView.AnonymousClass1.$r8$lambda$nGaORhOYmAfJmZkUyQrLWr_hzxU(GLView.this);
                }
            });
        }

        /* renamed from: surfaceCreated$lambda-0 */
        public static final void m285surfaceCreated$lambda0(GLView this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.onSurfaceCreated();
        }

        /* renamed from: surfaceChanged$lambda-1 */
        public static final void m284surfaceChanged$lambda1(GLView this$0, int i, int i2, int i3) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.onSurfaceChanged(i, i2, i3);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, final int i, final int i2, final int i3) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            final GLView gLView = GLView.this;
            gLView.queueEvent(new Runnable() { // from class: com.serenegiant.widget.GLView$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    GLView.AnonymousClass1.$r8$lambda$1iM63LsapP8bR2FToF3sIP7fmWU(GLView.this, i, i2, i3);
                }
            });
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            GLView.this.mHasSurface = false;
            final GLView gLView = GLView.this;
            gLView.queueEvent(new Runnable() { // from class: com.serenegiant.widget.GLView$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GLView.AnonymousClass1.m283$r8$lambda$oOzYvYyPvrW3C1neI0TGpIkmdY(GLView.this);
                }
            });
        }

        /* renamed from: surfaceDestroyed$lambda-2 */
        public static final void m286surfaceDestroyed$lambda2(GLView this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.onSurfaceDestroyed();
        }
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        this.mGLManager.release();
        super.onDetachedFromWindow();
    }

    public final boolean isGLES3() {
        return this.mGLContext.isGLES3();
    }

    public final boolean isOES3Supported() {
        return this.mGLContext.isOES3Supported();
    }

    public final GLManager getGLManager() {
        return this.mGLManager;
    }

    public final GLContext getGLContext() {
        return this.mGLContext;
    }

    public final void setRenderer(final GLRenderer gLRenderer) {
        queueEvent(new Runnable() { // from class: com.serenegiant.widget.GLView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GLView.$r8$lambda$0x2_QbTHfhB01LPNvirSvRS9HmA(GLView.this, gLRenderer);
            }
        });
    }

    /* renamed from: setRenderer$lambda-0 */
    public static final void m281setRenderer$lambda0(GLView this$0, GLRenderer gLRenderer) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mGLRenderer = gLRenderer;
    }

    public final void setViewport(final int i, final int i2, final int i3, final int i4) {
        queueEvent(new Runnable() { // from class: com.serenegiant.widget.GLView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GLView.$r8$lambda$UvMBDy1jFmx_hXIJ6OjpkqCOffA(GLView.this, i, i2, i3, i4);
            }
        });
    }

    /* renamed from: setViewport$lambda-1 */
    public static final void m282setViewport$lambda1(GLView this$0, int i, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ISurface iSurface = this$0.mTarget;
        if (iSurface != null) {
            Intrinsics.checkNotNull(iSurface);
            iSurface.setViewPort(i, i2, i3, i4);
        }
    }

    public final void queueEvent(Runnable task) {
        Intrinsics.checkNotNullParameter(task, "task");
        this.mGLHandler.post(task);
    }

    public final void queueEvent(Runnable task, long j) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (j > 0) {
            this.mGLHandler.postDelayed(task, j);
        } else {
            this.mGLHandler.post(task);
        }
    }

    public final void removeEvent(Runnable task) {
        Intrinsics.checkNotNullParameter(task, "task");
        this.mGLHandler.removeCallbacks(task);
    }

    @Override // com.serenegiant.widget.IGLTransformView
    public void setTransform(float[] fArr) {
        synchronized (this.mMatrix) {
            if (fArr != null) {
                System.arraycopy(fArr, 0, this.mMatrix, 0, 16);
            } else {
                Matrix.setIdentityM(this.mMatrix, 0);
            }
            this.mMatrixChanged = true;
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.serenegiant.widget.IGLTransformView
    public float[] getTransform(float[] fArr) {
        if (fArr == null) {
            fArr = new float[16];
        }
        synchronized (this.mMatrix) {
            System.arraycopy(this.mMatrix, 0, fArr, 0, 16);
            Unit unit = Unit.INSTANCE;
        }
        return fArr;
    }

    @Override // com.serenegiant.widget.IGLTransformView
    public View getView() {
        return this;
    }

    public final void makeDefault() {
        ISurface iSurface = this.mTarget;
        if (iSurface != null) {
            Intrinsics.checkNotNull(iSurface);
            iSurface.makeCurrent();
            return;
        }
        this.mGLContext.makeDefault();
    }

    protected final void onSurfaceCreated() {
        EGLBase.IEglSurface createFromSurface = this.mGLContext.getEgl().createFromSurface(getHolder().getSurface());
        this.mTarget = createFromSurface;
        if (createFromSurface != null) {
            createFromSurface.setViewPort(0, 0, getWidth(), getHeight());
        }
        this.mGLManager.postFrameCallbackDelayed(this.mChoreographerCallback, 0L);
        GLRenderer gLRenderer = this.mGLRenderer;
        if (gLRenderer != null) {
            gLRenderer.onSurfaceCreated();
        }
    }

    protected final void onSurfaceChanged(int i, int i2, int i3) {
        ISurface iSurface = this.mTarget;
        if (iSurface != null) {
            iSurface.setViewPort(0, 0, i2, i3);
        }
        GLRenderer gLRenderer = this.mGLRenderer;
        if (gLRenderer != null) {
            gLRenderer.onSurfaceChanged(i, i2, i3);
        }
    }

    public final void drawFrame() {
        if (this.mHasSurface) {
            GLES20.glClear(16384);
            GLRenderer gLRenderer = this.mGLRenderer;
            if (gLRenderer != null) {
                gLRenderer.drawFrame();
            }
        }
    }

    protected final void onSurfaceDestroyed() {
        if (this.mGLManager.isValid()) {
            this.mGLManager.removeFrameCallback(this.mChoreographerCallback);
            this.mGLHandler.removeCallbacksAndMessages(null);
        }
        GLRenderer gLRenderer = this.mGLRenderer;
        if (gLRenderer != null) {
            gLRenderer.onSurfaceDestroyed();
        }
        ISurface iSurface = this.mTarget;
        if (iSurface != null) {
            Intrinsics.checkNotNull(iSurface);
            iSurface.release();
            this.mTarget = null;
        }
    }

    public final void applyTransformMatrix(float[] transform) {
        Intrinsics.checkNotNullParameter(transform, "transform");
        GLRenderer gLRenderer = this.mGLRenderer;
        if (gLRenderer != null) {
            gLRenderer.applyTransformMatrix(transform);
        }
    }

    /* compiled from: GLView.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/serenegiant/widget/GLView$Companion;", "", "()V", "DEBUG", "", "TAG", "", "kotlin.jvm.PlatformType", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
