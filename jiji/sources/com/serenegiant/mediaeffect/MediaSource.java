package com.serenegiant.mediaeffect;

import android.util.Log;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLSurface;

/* loaded from: classes2.dex */
public class MediaSource implements ISource {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaSource";
    protected int mHeight;
    protected GLSurface mOutputScreen;
    protected GLSurface mSourceScreen;
    protected int[] mSrcTexIds;
    protected int mWidth;
    protected boolean needSwap;

    public MediaSource() {
        this.mSrcTexIds = new int[1];
        resize(1, 1);
    }

    public MediaSource(int i, int i2) {
        this.mSrcTexIds = new int[1];
        resize(i, i2);
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public ISource reset() {
        this.needSwap = false;
        this.mSrcTexIds[0] = this.mSourceScreen.getTexId();
        return this;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public ISource resize(int i, int i2) {
        if (this.mWidth != i || this.mHeight != i2) {
            GLSurface gLSurface = this.mSourceScreen;
            if (gLSurface != null) {
                gLSurface.release();
                this.mSourceScreen = null;
            }
            GLSurface gLSurface2 = this.mOutputScreen;
            if (gLSurface2 != null) {
                gLSurface2.release();
                this.mOutputScreen = null;
            }
            if (i > 0 && i2 > 0) {
                this.mSourceScreen = GLSurface.newInstance(false, i, i2, false, false);
                this.mOutputScreen = GLSurface.newInstance(false, i, i2, false, false);
                this.mWidth = i;
                this.mHeight = i2;
                this.mSrcTexIds[0] = this.mSourceScreen.getTexId();
            }
        }
        this.needSwap = false;
        return this;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public ISource apply(IEffect iEffect) {
        GLSurface gLSurface = this.mSourceScreen;
        if (gLSurface != null) {
            if (this.needSwap) {
                GLSurface gLSurface2 = this.mOutputScreen;
                this.mSourceScreen = gLSurface2;
                this.mOutputScreen = gLSurface;
                this.mSrcTexIds[0] = gLSurface2.getTexId();
            }
            this.needSwap = !this.needSwap;
            iEffect.apply(this);
        }
        return this;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public int getWidth() {
        return this.mWidth;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public int getHeight() {
        return this.mHeight;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public int[] getSourceTexId() {
        return this.mSrcTexIds;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public int getOutputTexId() {
        return (this.needSwap ? this.mOutputScreen : this.mSourceScreen).getTexId();
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public float[] getTexMatrix() {
        return (this.needSwap ? this.mOutputScreen : this.mSourceScreen).copyTexMatrix();
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public GLSurface getOutputTexture() {
        return this.needSwap ? this.mOutputScreen : this.mSourceScreen;
    }

    @Override // com.serenegiant.mediaeffect.ISource
    public void release() {
        this.mSrcTexIds[0] = -1;
        GLSurface gLSurface = this.mSourceScreen;
        if (gLSurface != null) {
            gLSurface.release();
            this.mSourceScreen = null;
        }
        GLSurface gLSurface2 = this.mOutputScreen;
        if (gLSurface2 != null) {
            gLSurface2.release();
            this.mOutputScreen = null;
        }
    }

    public MediaSource bind() {
        this.mSourceScreen.makeCurrent();
        return this;
    }

    public MediaSource unbind() {
        this.mSourceScreen.swap();
        reset();
        return this;
    }

    public MediaSource setSource(GLDrawer2D gLDrawer2D, int i, float[] fArr) {
        this.mSourceScreen.makeCurrent();
        try {
            try {
                gLDrawer2D.draw(33984, i, fArr, 0);
            } catch (RuntimeException e) {
                Log.w(TAG, e);
            }
            reset();
            return this;
        } finally {
            this.mSourceScreen.swap();
        }
    }
}
