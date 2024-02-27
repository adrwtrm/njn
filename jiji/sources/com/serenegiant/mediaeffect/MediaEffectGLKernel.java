package com.serenegiant.mediaeffect;

import com.serenegiant.gl.ShaderConst;

/* loaded from: classes2.dex */
public class MediaEffectGLKernel extends MediaEffectGLBase {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaEffectGLKernel";

    public MediaEffectGLKernel() {
        super(new MediaEffectKernel3x3Drawer(false, ShaderConst.VERTEX_SHADER_ES2, ShaderConst.FRAGMENT_SHADER_ES2));
    }

    public MediaEffectGLKernel(float[] fArr) {
        this();
        setParameter(fArr, 0.0f);
    }

    public MediaEffectGLKernel(float[] fArr, float f) {
        this();
        setParameter(fArr, f);
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public MediaEffectGLKernel resize(int i, int i2) {
        super.resize(i, i2);
        setTexSize(i, i2);
        return this;
    }

    public void setKernel(float[] fArr, float f) {
        ((MediaEffectKernel3x3Drawer) this.mDrawer).setKernel(fArr, f);
    }

    public void setColorAdjust(float f) {
        ((MediaEffectKernel3x3Drawer) this.mDrawer).setColorAdjust(f);
    }

    public void setTexSize(int i, int i2) {
        this.mDrawer.setTexSize(i, i2);
    }

    public MediaEffectGLKernel setParameter(float[] fArr, float f) {
        setKernel(fArr, f);
        return this;
    }
}
