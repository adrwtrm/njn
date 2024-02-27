package com.serenegiant.view;

import android.graphics.Matrix;

/* loaded from: classes2.dex */
public interface IViewTransformer extends IContentTransformer {
    Matrix getTransform(Matrix matrix);

    IViewTransformer reset();

    IViewTransformer setDefault(Matrix matrix);

    IViewTransformer setTransform(Matrix matrix);

    IViewTransformer updateTransform(boolean z);
}
