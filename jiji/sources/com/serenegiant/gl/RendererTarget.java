package com.serenegiant.gl;

import android.opengl.GLES20;
import android.opengl.Matrix;
import androidx.core.view.ViewCompat;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.system.Time;

/* loaded from: classes2.dex */
public class RendererTarget implements IMirror {
    private volatile boolean mEnable = true;
    private int mMirror = 0;
    private final float[] mMvpMatrix;
    private final boolean mOwnSurface;
    private Object mSurface;
    private ISurface mTargetSurface;

    public static RendererTarget newInstance(EGLBase eGLBase, Object obj, float f) {
        if (f > 0.0f) {
            return new RendererTargetHasWait(eGLBase, obj, f);
        }
        return new RendererTarget(eGLBase, obj);
    }

    protected RendererTarget(EGLBase eGLBase, Object obj) {
        float[] fArr = new float[16];
        this.mMvpMatrix = fArr;
        this.mSurface = obj;
        if (obj instanceof ISurface) {
            this.mTargetSurface = (ISurface) obj;
            this.mOwnSurface = false;
        } else if (obj instanceof TextureWrapper) {
            TextureWrapper textureWrapper = (TextureWrapper) obj;
            this.mTargetSurface = GLSurface.wrap(eGLBase.isGLES3(), textureWrapper.texTarget, textureWrapper.texUnit, textureWrapper.texId, textureWrapper.width, textureWrapper.height, false);
            this.mOwnSurface = true;
        } else if (GLUtils.isSupportedSurface(obj)) {
            this.mTargetSurface = eGLBase.createFromSurface(obj);
            this.mOwnSurface = true;
        } else {
            throw new IllegalArgumentException("Unsupported surface," + obj);
        }
        Matrix.setIdentityM(fArr, 0);
    }

    public void release() {
        ISurface iSurface;
        if (this.mOwnSurface && (iSurface = this.mTargetSurface) != null) {
            iSurface.release();
        }
        this.mTargetSurface = null;
        this.mSurface = null;
    }

    public Object getSurface() {
        return this.mSurface;
    }

    public int getId() {
        Object obj = this.mSurface;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public boolean isValid() {
        ISurface iSurface = this.mTargetSurface;
        return iSurface != null && iSurface.isValid();
    }

    public boolean isEnabled() {
        return this.mEnable;
    }

    public void setEnabled(boolean z) {
        this.mEnable = z;
    }

    public boolean canDraw() {
        ISurface iSurface;
        return this.mEnable && (iSurface = this.mTargetSurface) != null && iSurface.isValid();
    }

    public float[] getMvpMatrix() {
        return this.mMvpMatrix;
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(int i) {
        int i2 = i % 4;
        if (i2 != this.mMirror) {
            this.mMirror = i2;
            GLUtils.setMirror(this.mMvpMatrix, i2);
        }
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        return this.mMirror;
    }

    public int width() {
        ISurface iSurface = this.mTargetSurface;
        if (iSurface != null) {
            return iSurface.getWidth();
        }
        return 0;
    }

    public int height() {
        ISurface iSurface = this.mTargetSurface;
        if (iSurface != null) {
            return iSurface.getHeight();
        }
        return 0;
    }

    public void draw(GLDrawer2D gLDrawer2D, int i, int i2, float[] fArr) {
        ISurface iSurface = this.mTargetSurface;
        if (iSurface != null) {
            iSurface.makeCurrent();
            ISurface iSurface2 = this.mTargetSurface;
            iSurface2.setViewPort(0, 0, iSurface2.getWidth(), this.mTargetSurface.getHeight());
            GLES20.glClear(16384);
            doDraw(gLDrawer2D, i, i2, fArr, this.mMvpMatrix);
            this.mTargetSurface.swap();
        }
    }

    protected static void doDraw(GLDrawer2D gLDrawer2D, int i, int i2, float[] fArr, float[] fArr2) {
        gLDrawer2D.setMvpMatrix(fArr2, 0);
        gLDrawer2D.draw(i, i2, fArr, 0);
    }

    public void clear(int i) {
        ISurface iSurface = this.mTargetSurface;
        if (iSurface != null) {
            iSurface.makeCurrent();
            ISurface iSurface2 = this.mTargetSurface;
            iSurface2.setViewPort(0, 0, iSurface2.getWidth(), this.mTargetSurface.getHeight());
            GLES20.glClearColor(((16711680 & i) >>> 16) / 255.0f, ((65280 & i) >>> 8) / 255.0f, (i & 255) / 255.0f, ((i & ViewCompat.MEASURED_STATE_MASK) >>> 24) / 255.0f);
            GLES20.glClear(16384);
            this.mTargetSurface.swap();
        }
    }

    public void makeCurrent() throws IllegalStateException {
        check();
        this.mTargetSurface.makeCurrent();
        ISurface iSurface = this.mTargetSurface;
        iSurface.setViewPort(0, 0, iSurface.getWidth(), this.mTargetSurface.getHeight());
    }

    public void swap() throws IllegalStateException {
        check();
        this.mTargetSurface.swap();
    }

    private void check() throws IllegalStateException {
        ISurface iSurface = this.mTargetSurface;
        if (iSurface == null || !iSurface.isValid()) {
            throw new IllegalStateException("already released");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class RendererTargetHasWait extends RendererTarget {
        private final long mIntervalsDeltaNs;
        private final long mIntervalsNs;
        private long mNextDraw;

        protected RendererTargetHasWait(EGLBase eGLBase, Object obj, float f) {
            super(eGLBase, obj);
            long round = Math.round(1.0E9d / f);
            this.mIntervalsNs = round;
            this.mIntervalsDeltaNs = -Math.round(round * 0.03d);
            this.mNextDraw = Time.nanoTime() + round;
        }

        @Override // com.serenegiant.gl.RendererTarget
        public boolean canDraw() {
            return super.canDraw() && Time.nanoTime() - this.mNextDraw > this.mIntervalsDeltaNs;
        }

        @Override // com.serenegiant.gl.RendererTarget
        public void draw(GLDrawer2D gLDrawer2D, int i, int i2, float[] fArr) {
            this.mNextDraw = Time.nanoTime() + this.mIntervalsNs;
            super.draw(gLDrawer2D, i, i2, fArr);
        }
    }
}
