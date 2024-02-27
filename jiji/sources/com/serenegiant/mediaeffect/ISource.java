package com.serenegiant.mediaeffect;

import com.serenegiant.gl.GLSurface;

/* loaded from: classes2.dex */
public interface ISource {
    ISource apply(IEffect iEffect);

    int getHeight();

    int getOutputTexId();

    GLSurface getOutputTexture();

    int[] getSourceTexId();

    float[] getTexMatrix();

    int getWidth();

    void release();

    ISource reset();

    ISource resize(int i, int i2);
}
