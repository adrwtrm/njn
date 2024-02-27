package com.epson.iprojection.ui.activities.presen.main_image;

import android.graphics.Matrix;

/* loaded from: classes.dex */
public class DblMatrix {
    private final Matrix _matrixViewImage = new Matrix();
    private final Matrix _matrixShadowImage = new Matrix();

    public void postScale(float f, float f2, float f3, float f4, float f5) {
        this._matrixViewImage.postScale(f, f2, f3, f4);
        this._matrixShadowImage.postScale(f, f2, f3 * f5, f4 * f5);
    }

    public void postTranslate(float f, float f2, float f3) {
        this._matrixViewImage.postTranslate(f, f2);
        this._matrixShadowImage.postTranslate(f * f3, f2 * f3);
    }

    public void postTranslateForCentering(float f, float f2, float f3) {
        this._matrixViewImage.postTranslate(f, f2);
    }

    public void scaleViewImage(float f) {
        this._matrixViewImage.postScale(f, f);
    }

    public final Matrix getFrontMatrix() {
        return this._matrixViewImage;
    }

    public final Matrix getBackMatrix() {
        return this._matrixShadowImage;
    }
}
