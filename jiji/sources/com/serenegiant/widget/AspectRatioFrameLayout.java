package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.serenegiant.common.R;
import com.serenegiant.view.MeasureSpecDelegater;

/* loaded from: classes2.dex */
public class AspectRatioFrameLayout extends FrameLayout implements IScaledView {
    private boolean mNeedResizeToKeepAspect;
    private double mRequestedAspect;
    private int mScaleMode;

    public AspectRatioFrameLayout(Context context) {
        this(context, null, 0);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mScaleMode = 0;
        this.mRequestedAspect = -1.0d;
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScaleMode = 0;
        this.mRequestedAspect = -1.0d;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IScaledView, i, 0);
        try {
            this.mRequestedAspect = obtainStyledAttributes.getFloat(R.styleable.IScaledView_aspect_ratio, -1.0f);
            this.mScaleMode = obtainStyledAttributes.getInt(R.styleable.IScaledView_scale_mode, 0);
            this.mNeedResizeToKeepAspect = obtainStyledAttributes.getBoolean(R.styleable.IScaledView_resize_to_keep_aspect, true);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        MeasureSpecDelegater.MeasureSpec onMeasure = MeasureSpecDelegater.onMeasure(this, this.mRequestedAspect, this.mScaleMode, this.mNeedResizeToKeepAspect, i, i2);
        super.onMeasure(onMeasure.widthMeasureSpec, onMeasure.heightMeasureSpec);
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
    public void setAspectRatio(double d) {
        if (this.mRequestedAspect != d) {
            this.mRequestedAspect = d;
            requestLayout();
        }
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setAspectRatio(int i, int i2) {
        setAspectRatio(i / i2);
    }

    @Override // com.serenegiant.widget.IScaledView
    public double getAspectRatio() {
        return this.mRequestedAspect;
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setNeedResizeToKeepAspect(boolean z) {
        if (this.mNeedResizeToKeepAspect != z) {
            this.mNeedResizeToKeepAspect = z;
            requestLayout();
        }
    }
}
