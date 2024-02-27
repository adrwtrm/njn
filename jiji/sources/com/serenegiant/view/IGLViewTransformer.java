package com.serenegiant.view;

/* loaded from: classes2.dex */
public interface IGLViewTransformer extends IContentTransformer {
    float[] getTransform(float[] fArr);

    IGLViewTransformer reset();

    IGLViewTransformer setDefault(float[] fArr);

    IGLViewTransformer setTransform(float[] fArr);

    IGLViewTransformer updateTransform(boolean z);
}
