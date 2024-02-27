package com.serenegiant.glutils;

/* loaded from: classes2.dex */
public interface IEffectRendererHolder extends IRendererHolder {
    void changeEffect(int i);

    int getCurrentEffect();

    void setParams(int i, float[] fArr) throws IllegalArgumentException;

    void setParams(float[] fArr);
}
