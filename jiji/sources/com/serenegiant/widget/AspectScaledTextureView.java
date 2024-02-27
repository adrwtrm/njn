package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;
import com.serenegiant.common.R;
import com.serenegiant.view.MeasureSpecDelegater;

/* loaded from: classes2.dex */
public class AspectScaledTextureView extends TransformTextureView implements TextureView.SurfaceTextureListener, IScaledView {
    private static final boolean DEBUG = false;
    private static final String TAG = "AspectScaledTextureView";
    private volatile boolean mHasSurface;
    protected final Matrix mImageMatrix;
    private TextureView.SurfaceTextureListener mListener;
    private boolean mNeedResizeToKeepAspect;
    private double mRequestedAspect;
    private int mScaleMode;
    private int prevHeight;
    private int prevWidth;

    protected void onResize(int i, int i2) {
    }

    public AspectScaledTextureView(Context context) {
        this(context, null, 0);
    }

    public AspectScaledTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AspectScaledTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mImageMatrix = new Matrix();
        this.prevWidth = -1;
        this.prevHeight = -1;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IScaledView, i, 0);
        try {
            this.mRequestedAspect = obtainStyledAttributes.getFloat(R.styleable.IScaledView_aspect_ratio, -1.0f);
            this.mScaleMode = obtainStyledAttributes.getInt(R.styleable.IScaledView_scale_mode, 0);
            this.mNeedResizeToKeepAspect = obtainStyledAttributes.getBoolean(R.styleable.IScaledView_resize_to_keep_aspect, true);
            obtainStyledAttributes.recycle();
            super.setSurfaceTextureListener(this);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        MeasureSpecDelegater.MeasureSpec onMeasure = MeasureSpecDelegater.onMeasure(this, this.mRequestedAspect, this.mScaleMode, this.mNeedResizeToKeepAspect, i, i2);
        super.onMeasure(onMeasure.widthMeasureSpec, onMeasure.heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (this.prevWidth != getWidth() || this.prevHeight != getHeight()) {
            this.prevWidth = getWidth();
            int height = getHeight();
            this.prevHeight = height;
            onResize(this.prevWidth, height);
        }
        init();
    }

    @Override // android.view.TextureView
    public final void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        this.mListener = surfaceTextureListener;
    }

    @Override // android.view.TextureView
    public TextureView.SurfaceTextureListener getSurfaceTextureListener() {
        return this.mListener;
    }

    public boolean hasSurface() {
        return this.mHasSurface;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mHasSurface = true;
        init();
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureAvailable(surfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mHasSurface = false;
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureDestroyed(surfaceTexture);
        }
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureUpdated(surfaceTexture);
        }
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

    /* JADX INFO: Access modifiers changed from: protected */
    public void init() {
        double min;
        int width = getWidth();
        int height = getHeight();
        this.mImageMatrix.reset();
        int i = this.mScaleMode;
        if (i == 0 || i == 2) {
            double d = this.mRequestedAspect;
            double d2 = d > 0.0d ? d * height : height;
            double d3 = height;
            double d4 = width;
            double d5 = d4 / d2;
            double d6 = d3 / d3;
            if (i == 2) {
                min = Math.max(d5, d6);
            } else {
                min = Math.min(d5, d6);
            }
            this.mImageMatrix.postScale((float) ((d2 * min) / d4), (float) ((min * d3) / d3), width / 2.0f, height / 2.0f);
        }
        setTransform(this.mImageMatrix);
    }
}
