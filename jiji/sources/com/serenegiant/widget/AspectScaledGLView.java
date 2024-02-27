package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.Matrix;
import android.util.AttributeSet;
import com.serenegiant.common.R;
import com.serenegiant.view.MeasureSpecDelegater;

/* loaded from: classes2.dex */
public class AspectScaledGLView extends GLView implements IScaledView {
    private static final boolean DEBUG = false;
    private static final String TAG = "AspectScaledGLView";
    private boolean mNeedResizeToKeepAspect;
    private double mRequestedAspect;
    private int mScaleMode;
    private int prevHeight;
    private int prevWidth;

    protected void onResize(int i, int i2) {
    }

    public AspectScaledGLView(Context context) {
        this(context, null, 0);
    }

    public AspectScaledGLView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AspectScaledGLView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.prevWidth = -1;
        this.prevHeight = -1;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IScaledView, i, 0);
        try {
            this.mRequestedAspect = obtainStyledAttributes.getFloat(R.styleable.IScaledView_aspect_ratio, -1.0f);
            this.mScaleMode = obtainStyledAttributes.getInt(R.styleable.IScaledView_scale_mode, 0);
            this.mNeedResizeToKeepAspect = obtainStyledAttributes.getBoolean(R.styleable.IScaledView_resize_to_keep_aspect, true);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        MeasureSpecDelegater.MeasureSpec onMeasure = MeasureSpecDelegater.onMeasure(this, this.mRequestedAspect, this.mScaleMode, this.mNeedResizeToKeepAspect, i, i2);
        super.onMeasure(onMeasure.widthMeasureSpec, onMeasure.heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i3 - i;
        int i6 = i4 - i2;
        if (i5 == 0 || i6 == 0) {
            return;
        }
        if (this.prevWidth != i5 || this.prevHeight != i6) {
            this.prevWidth = i5;
            this.prevHeight = i6;
            onResize(i5, i6);
        }
        init();
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setAspectRatio(double d) {
        if (this.mRequestedAspect != d) {
            this.mRequestedAspect = d;
            requestLayout();
        }
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setAspectRatio(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        setAspectRatio(i / i2);
    }

    @Override // com.serenegiant.widget.IScaledView
    public double getAspectRatio() {
        return this.mRequestedAspect;
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setScaleMode(int i) {
        if (this.mScaleMode != i) {
            this.mScaleMode = i;
            requestLayout();
        }
    }

    @Override // com.serenegiant.widget.IScaledView
    public int getScaleMode() {
        return this.mScaleMode;
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setNeedResizeToKeepAspect(boolean z) {
        if (this.mNeedResizeToKeepAspect != z) {
            this.mNeedResizeToKeepAspect = z;
            requestLayout();
        }
    }

    protected void init() {
        double min;
        int width = getWidth();
        int height = getHeight();
        double d = this.mRequestedAspect;
        double d2 = d > 0.0d ? d * height : height;
        double d3 = height;
        float[] fArr = new float[16];
        Matrix.setIdentityM(fArr, 0);
        int i = this.mScaleMode;
        if (i == 0 || i == 2) {
            double d4 = width / d2;
            double d5 = d3 / d3;
            if (i == 2) {
                min = Math.max(d4, d5);
            } else {
                min = Math.min(d4, d5);
            }
            Matrix.scaleM(fArr, 0, ((float) (d2 * min)) / width, ((float) (min * d3)) / height, 1.0f);
        }
        setTransform(fArr);
    }
}
