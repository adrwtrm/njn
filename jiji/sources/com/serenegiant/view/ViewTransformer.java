package com.serenegiant.view;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.View;
import com.serenegiant.graphics.MatrixUtils;

/* loaded from: classes2.dex */
public abstract class ViewTransformer implements IViewTransformer {
    private static final boolean DEBUG = false;
    private static final String TAG = "ViewTransformer";
    private float mCurrentRotate;
    private float mCurrentScaleX;
    private float mCurrentScaleY;
    private float mCurrentTransX;
    private float mCurrentTransY;
    private final View mTargetView;
    protected final Matrix mDefaultTransform = new Matrix();
    protected final Matrix mTransform = new Matrix();
    private final Matrix mWork = new Matrix();
    private final float[] workArray = new float[9];

    protected abstract Matrix getTransform(View view, Matrix matrix);

    protected abstract void setTransform(View view, Matrix matrix);

    public ViewTransformer(View view) {
        this.mTargetView = view;
        updateTransform(true);
    }

    public View getTargetView() {
        return this.mTargetView;
    }

    @Override // com.serenegiant.view.IViewTransformer
    public ViewTransformer setTransform(Matrix matrix) {
        Matrix matrix2 = this.mTransform;
        if (matrix2 != matrix) {
            matrix2.set(matrix);
        }
        internalSetTransform(this.mTransform);
        calcValues(this.mTransform);
        return this;
    }

    @Override // com.serenegiant.view.IViewTransformer
    public Matrix getTransform(Matrix matrix) {
        if (matrix != null) {
            matrix.set(this.mTransform);
            return matrix;
        }
        return new Matrix(this.mTransform);
    }

    @Override // com.serenegiant.view.IViewTransformer
    public ViewTransformer updateTransform(boolean z) {
        internalGetTransform(this.mTransform);
        if (z) {
            setDefault(this.mTransform);
            resetValues();
        } else {
            calcValues(this.mTransform);
        }
        return this;
    }

    @Override // com.serenegiant.view.IViewTransformer
    public ViewTransformer setDefault(Matrix matrix) {
        Matrix matrix2 = this.mDefaultTransform;
        if (matrix2 != matrix) {
            matrix2.set(matrix);
        }
        return this;
    }

    @Override // com.serenegiant.view.IViewTransformer
    public ViewTransformer reset() {
        setTransform(this.mDefaultTransform);
        return this;
    }

    public ViewTransformer setTranslate(float f, float f2) {
        return setTransform(f, f2, this.mCurrentScaleX, this.mCurrentScaleY, this.mCurrentRotate);
    }

    public ViewTransformer translate(float f, float f2) {
        return setTransform(this.mCurrentTransX + f, this.mCurrentTransY + f2, this.mCurrentScaleX, this.mCurrentScaleY, this.mCurrentRotate);
    }

    public PointF getTranslate(PointF pointF) {
        if (pointF != null) {
            pointF.set(this.mCurrentTransX, this.mCurrentTransY);
            return pointF;
        }
        return new PointF(this.mCurrentTransX, this.mCurrentTransY);
    }

    public float getTranslateX() {
        return this.mCurrentTransX;
    }

    public float getTranslateY() {
        return this.mCurrentTransY;
    }

    public ViewTransformer setScale(float f, float f2) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, f, f2, this.mCurrentRotate);
    }

    public ViewTransformer setScale(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, f, f, this.mCurrentRotate);
    }

    public ViewTransformer scale(float f, float f2) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX * f, this.mCurrentScaleY * f2, this.mCurrentRotate);
    }

    public ViewTransformer scale(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX * f, this.mCurrentScaleY * f, this.mCurrentRotate);
    }

    public ViewTransformer scale(float f, float f2, float f3, float f4) {
        this.mTransform.postScale(f, f2, f3, f4);
        internalSetTransform(this.mTransform);
        calcValues(this.mTransform);
        return this;
    }

    public float getScaleX() {
        return this.mCurrentScaleX;
    }

    public float getScaleY() {
        return this.mCurrentScaleY;
    }

    public float getScale() {
        return Math.min(this.mCurrentScaleX, this.mCurrentScaleY);
    }

    public ViewTransformer setRotate(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX, this.mCurrentScaleY, f);
    }

    public ViewTransformer rotate(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX, this.mCurrentScaleY, this.mCurrentRotate + f);
    }

    public ViewTransformer rotate(float f, float f2, float f3) {
        this.mTransform.postRotate(f, f2, f3);
        internalSetTransform(this.mTransform);
        calcValues(this.mTransform);
        return this;
    }

    public float getRotation() {
        return this.mCurrentRotate;
    }

    public void mapPoints(float[] fArr) {
        this.mTransform.mapPoints(fArr);
    }

    public void mapPoints(float[] fArr, float[] fArr2) {
        this.mTransform.mapPoints(fArr, fArr2);
    }

    private void internalSetTransform(Matrix matrix) {
        setTransform(this.mTargetView, matrix);
    }

    private Matrix internalGetTransform(Matrix matrix) {
        Matrix transform = getTransform(this.mTargetView, matrix);
        if (transform != matrix && matrix != null) {
            matrix.set(transform);
        }
        return transform;
    }

    protected ViewTransformer setTransform(float f, float f2, float f3, float f4, float f5) {
        if (this.mCurrentTransX != f || this.mCurrentTransY != f2 || this.mCurrentScaleX != f3 || this.mCurrentScaleY != f4 || this.mCurrentRotate != f5) {
            this.mCurrentScaleX = f3;
            this.mCurrentScaleY = f4;
            this.mCurrentTransX = f;
            this.mCurrentTransY = f2;
            this.mCurrentRotate = f5;
            int i = (f5 > Float.MAX_VALUE ? 1 : (f5 == Float.MAX_VALUE ? 0 : -1));
            if (i != 0) {
                while (true) {
                    float f6 = this.mCurrentRotate;
                    if (f6 <= 360.0f) {
                        break;
                    }
                    this.mCurrentRotate = f6 - 360.0f;
                }
                while (true) {
                    float f7 = this.mCurrentRotate;
                    if (f7 >= -360.0f) {
                        break;
                    }
                    this.mCurrentRotate = f7 + 360.0f;
                }
            }
            this.mTransform.set(this.mDefaultTransform);
            this.mTransform.postTranslate(f, f2);
            float width = this.mTargetView.getWidth() >> 1;
            float height = this.mTargetView.getHeight() >> 1;
            this.mTransform.postScale(f3, f4, width, height);
            if (i != 0) {
                this.mTransform.postRotate(this.mCurrentRotate, width, height);
            }
            internalSetTransform(this.mTransform);
        }
        return this;
    }

    protected void calcValues(Matrix matrix) {
        this.mTransform.getValues(this.workArray);
        float[] fArr = this.workArray;
        this.mCurrentTransX = fArr[2];
        this.mCurrentTransY = fArr[5];
        this.mCurrentScaleX = fArr[0];
        this.mCurrentScaleY = MatrixUtils.getScale(fArr);
        this.mCurrentRotate = MatrixUtils.getRotate(this.workArray);
    }

    protected void resetValues() {
        this.mCurrentTransY = 0.0f;
        this.mCurrentTransX = 0.0f;
        this.mCurrentScaleY = 1.0f;
        this.mCurrentScaleX = 1.0f;
        this.mCurrentRotate = 0.0f;
    }
}
