package com.serenegiant.gl;

/* loaded from: classes2.dex */
public interface ISurface {
    int getHeight();

    int getWidth();

    boolean isValid();

    void makeCurrent();

    void release();

    void setViewPort(int i, int i2, int i3, int i4);

    void swap();
}
