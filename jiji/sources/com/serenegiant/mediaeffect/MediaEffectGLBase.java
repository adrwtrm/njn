package com.serenegiant.mediaeffect;

import com.serenegiant.gl.GLSurface;
import com.serenegiant.gl.ShaderConst;

/* loaded from: classes2.dex */
public class MediaEffectGLBase implements IEffect {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaEffectGLBase";
    protected final MediaEffectDrawer mDrawer;
    protected volatile boolean mEnabled;
    protected GLSurface mOutputOffscreen;

    public MediaEffectGLBase(int i, String str) {
        this(MediaEffectDrawer.newInstance(i, false, ShaderConst.VERTEX_SHADER_ES2, str));
    }

    public MediaEffectGLBase(int i, boolean z, String str) {
        this(MediaEffectDrawer.newInstance(i, z, ShaderConst.VERTEX_SHADER_ES2, str));
    }

    public MediaEffectGLBase(int i, boolean z, String str, String str2) {
        this(MediaEffectDrawer.newInstance(i, z, str, str2));
    }

    public MediaEffectGLBase(MediaEffectDrawer mediaEffectDrawer) {
        this.mEnabled = true;
        this.mDrawer = mediaEffectDrawer;
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public void release() {
        this.mDrawer.release();
        GLSurface gLSurface = this.mOutputOffscreen;
        if (gLSurface != null) {
            gLSurface.release();
            this.mOutputOffscreen = null;
        }
    }

    public float[] getMvpMatrix() {
        return this.mDrawer.getMvpMatrix();
    }

    public MediaEffectGLBase setMvpMatrix(float[] fArr, int i) {
        this.mDrawer.setMvpMatrix(fArr, i);
        return this;
    }

    public void getMvpMatrix(float[] fArr, int i) {
        this.mDrawer.getMvpMatrix(fArr, i);
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public MediaEffectGLBase resize(int i, int i2) {
        MediaEffectDrawer mediaEffectDrawer = this.mDrawer;
        if (mediaEffectDrawer != null) {
            mediaEffectDrawer.setTexSize(i, i2);
        }
        return this;
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public boolean enabled() {
        return this.mEnabled;
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public IEffect setEnable(boolean z) {
        this.mEnabled = z;
        return this;
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public void apply(int[] iArr, int i, int i2, int i3) {
        if (this.mEnabled) {
            if (this.mOutputOffscreen == null) {
                this.mOutputOffscreen = GLSurface.newInstance(false, i, i2, false);
            }
            if (i3 != this.mOutputOffscreen.getTexId() || i != this.mOutputOffscreen.getWidth() || i2 != this.mOutputOffscreen.getHeight()) {
                this.mOutputOffscreen.assignTexture(i3, i, i2);
            }
            this.mOutputOffscreen.makeCurrent();
            try {
                this.mDrawer.apply(iArr, this.mOutputOffscreen.copyTexMatrix(), 0);
            } finally {
                this.mOutputOffscreen.swap();
            }
        }
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public void apply(int[] iArr, GLSurface gLSurface) {
        if (this.mEnabled) {
            gLSurface.makeCurrent();
            try {
                this.mDrawer.apply(iArr, gLSurface.copyTexMatrix(), 0);
            } finally {
                gLSurface.swap();
            }
        }
    }

    @Override // com.serenegiant.mediaeffect.IEffect
    public void apply(ISource iSource) {
        if (this.mEnabled) {
            GLSurface outputTexture = iSource.getOutputTexture();
            int[] sourceTexId = iSource.getSourceTexId();
            outputTexture.makeCurrent();
            try {
                this.mDrawer.apply(sourceTexId, outputTexture.copyTexMatrix(), 0);
            } finally {
                outputTexture.swap();
            }
        }
    }

    protected int getProgram() {
        return this.mDrawer.getProgram();
    }
}
