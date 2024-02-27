package com.serenegiant.view;

import android.graphics.PointF;
import android.opengl.Matrix;
import com.serenegiant.widget.IGLTransformView;

/* loaded from: classes2.dex */
public class GLViewTransformer implements IGLViewTransformer {
    private static final boolean DEBUG = false;
    private static final String TAG = "GLViewTransformer";
    private float mCurrentRotate;
    private float mCurrentScaleX;
    private float mCurrentScaleY;
    private float mCurrentTransX;
    private float mCurrentTransY;
    private final IGLTransformView mTargetView;
    private final float[] mTransform = new float[16];
    private final float[] mDefaultTransform = new float[16];
    private final float[] work = new float[16];

    protected void calcValues(float[] fArr) {
    }

    public GLViewTransformer(IGLTransformView iGLTransformView) {
        this.mTargetView = iGLTransformView;
        updateTransform(true);
    }

    public IGLTransformView getTargetView() {
        return this.mTargetView;
    }

    @Override // com.serenegiant.view.IGLViewTransformer
    public GLViewTransformer updateTransform(boolean z) {
        internalGetTransform(this.mTransform);
        if (z) {
            System.arraycopy(this.mTransform, 0, this.mDefaultTransform, 0, 16);
            resetValues();
        } else {
            calcValues(this.mTransform);
        }
        return this;
    }

    @Override // com.serenegiant.view.IGLViewTransformer
    public final GLViewTransformer setTransform(float[] fArr) {
        if (fArr != null && fArr.length >= 16) {
            System.arraycopy(fArr, 0, this.mTransform, 0, 16);
        } else {
            Matrix.setIdentityM(this.mTransform, 0);
        }
        internalSetTransform(this.mTransform);
        calcValues(this.mTransform);
        return this;
    }

    @Override // com.serenegiant.view.IGLViewTransformer
    public float[] getTransform(float[] fArr) {
        if (fArr == null && fArr.length < 16) {
            fArr = new float[16];
        }
        System.arraycopy(this.mTransform, 0, fArr, 0, 16);
        return fArr;
    }

    @Override // com.serenegiant.view.IGLViewTransformer
    public GLViewTransformer setDefault(float[] fArr) {
        if (fArr == null || fArr.length < 16) {
            Matrix.setIdentityM(this.mDefaultTransform, 0);
        } else {
            System.arraycopy(fArr, 0, this.mDefaultTransform, 0, 16);
        }
        return this;
    }

    @Override // com.serenegiant.view.IGLViewTransformer
    public GLViewTransformer reset() {
        setTransform(this.mDefaultTransform);
        return this;
    }

    public GLViewTransformer setTranslate(float f, float f2) {
        return setTransform(f, f2, this.mCurrentScaleX, this.mCurrentScaleY, this.mCurrentRotate);
    }

    public GLViewTransformer translate(float f, float f2) {
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

    public GLViewTransformer setScale(float f, float f2) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, f, f2, this.mCurrentRotate);
    }

    public GLViewTransformer setScale(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, f, f, this.mCurrentRotate);
    }

    public GLViewTransformer scale(float f, float f2) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX * f, this.mCurrentScaleY * f2, this.mCurrentRotate);
    }

    public GLViewTransformer scale(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX * f, this.mCurrentScaleY * f, this.mCurrentRotate);
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

    public GLViewTransformer setRotate(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX, this.mCurrentScaleY, f);
    }

    public GLViewTransformer rotate(float f) {
        return setTransform(this.mCurrentTransX, this.mCurrentTransY, this.mCurrentScaleX, this.mCurrentScaleY, this.mCurrentRotate + f);
    }

    public float getRotation() {
        return this.mCurrentRotate;
    }

    public void mapPoints(float[] fArr) {
        throw new UnsupportedOperationException();
    }

    public void mapPoints(float[] fArr, float[] fArr2) {
        throw new UnsupportedOperationException();
    }

    protected void internalSetTransform(float[] fArr) {
        if (fArr != null) {
            Matrix.multiplyMM(this.work, 0, fArr, 0, this.mDefaultTransform, 0);
            this.mTargetView.setTransform(this.work);
            return;
        }
        this.mTargetView.setTransform(this.mDefaultTransform);
    }

    protected float[] internalGetTransform(float[] fArr) {
        return this.mTargetView.getTransform(fArr);
    }

    protected GLViewTransformer setTransform(float f, float f2, float f3, float f4, float f5) {
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
            this.mTargetView.getView().getWidth();
            this.mTargetView.getView().getHeight();
            Matrix.setIdentityM(this.mTransform, 0);
            float[] fArr = this.mTransform;
            Matrix.multiplyMM(fArr, 0, fArr, 0, this.mDefaultTransform, 0);
            Matrix.translateM(this.mTransform, 0, f, f2, 0.0f);
            Matrix.scaleM(this.mTransform, 0, f3, f4, 1.0f);
            if (i != 0) {
                Matrix.rotateM(this.mTransform, 0, this.mCurrentRotate, 0.0f, 0.0f, 1.0f);
            }
            internalSetTransform(this.mTransform);
        }
        return this;
    }

    protected void resetValues() {
        this.mCurrentTransY = 0.0f;
        this.mCurrentTransX = 0.0f;
        this.mCurrentScaleY = 1.0f;
        this.mCurrentScaleX = 1.0f;
        this.mCurrentRotate = 0.0f;
    }
}
