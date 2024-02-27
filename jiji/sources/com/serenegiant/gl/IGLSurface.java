package com.serenegiant.gl;

import android.graphics.Bitmap;

/* loaded from: classes2.dex */
public interface IGLSurface extends ISurface, GLConst {
    void copyTexMatrix(float[] fArr, int i);

    float[] copyTexMatrix();

    int getTexHeight();

    int getTexId();

    float[] getTexMatrix();

    int getTexTarget();

    int getTexUnit();

    int getTexWidth();

    boolean isOES();

    void loadBitmap(Bitmap bitmap);
}
