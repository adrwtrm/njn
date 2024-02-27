package com.serenegiant.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import com.serenegiant.common.R;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.view.IViewTransformer;
import com.serenegiant.view.MeasureSpecDelegater;
import com.serenegiant.view.ViewTransformDelegater;

/* loaded from: classes2.dex */
public class ZoomAspectScaledTextureView2 extends TransformTextureView implements IMirror, TextureView.SurfaceTextureListener, ViewTransformDelegater.ViewTransformListener, IScaledView {
    private static final boolean DEBUG = false;
    private static final String TAG = "ZoomAspectScaledTextureView2";
    private volatile boolean mHasSurface;
    private TextureView.SurfaceTextureListener mListener;
    private int mMirrorMode;
    private boolean mNeedResizeToKeepAspect;
    private double mRequestedAspect;

    protected boolean handleOnTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.serenegiant.view.ViewTransformDelegater.ViewTransformListener
    public void onStateChanged(View view, int i) {
    }

    @Override // com.serenegiant.view.ViewTransformDelegater.ViewTransformListener
    public void onTransformed(View view, Matrix matrix) {
    }

    public ZoomAspectScaledTextureView2(Context context) {
        this(context, null, 0);
    }

    public ZoomAspectScaledTextureView2(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.content.res.TypedArray] */
    /* JADX WARN: Type inference failed for: r9v5, types: [com.serenegiant.widget.ZoomAspectScaledTextureView2$1, com.serenegiant.view.IViewTransformer, com.serenegiant.view.ViewTransformDelegater] */
    public ZoomAspectScaledTextureView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        int i3;
        TypedArray typedArray;
        this.mMirrorMode = 0;
        double d = -1.0d;
        this.mRequestedAspect = -1.0d;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IScaledView, i, 0);
        try {
            try {
                d = obtainStyledAttributes.getFloat(R.styleable.IScaledView_aspect_ratio, -1.0f);
                i2 = obtainStyledAttributes.getInt(R.styleable.IScaledView_scale_mode, 0);
                try {
                    this.mNeedResizeToKeepAspect = obtainStyledAttributes.getBoolean(R.styleable.IScaledView_resize_to_keep_aspect, true);
                } catch (UnsupportedOperationException e) {
                    e = e;
                    String str = TAG;
                    Log.d(str, str, e);
                    obtainStyledAttributes.recycle();
                    obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ZoomAspectScaledTextureView, i, 0);
                    try {
                        i3 = obtainStyledAttributes.getInteger(R.styleable.ZoomAspectScaledTextureView_handle_touch_event, 7);
                    } catch (UnsupportedOperationException e2) {
                        String str2 = TAG;
                        Log.d(str2, str2, e2);
                        int i4 = obtainStyledAttributes.getBoolean(R.styleable.ZoomAspectScaledTextureView_handle_touch_event, true) ? 7 : 0;
                        obtainStyledAttributes.recycle();
                        i3 = i4;
                    }
                    super.setSurfaceTextureListener(this);
                    typedArray = new ViewTransformDelegater(this) { // from class: com.serenegiant.widget.ZoomAspectScaledTextureView2.1
                        @Override // com.serenegiant.view.ViewTransformDelegater
                        public void onInit() {
                        }

                        @Override // com.serenegiant.view.ViewTransformer
                        protected void setTransform(View view, Matrix matrix) {
                            ZoomAspectScaledTextureView2.this.superSetTransform(matrix);
                        }

                        @Override // com.serenegiant.view.ViewTransformer
                        protected Matrix getTransform(View view, Matrix matrix) {
                            return ZoomAspectScaledTextureView2.this.superGetTransform(matrix);
                        }

                        @Override // com.serenegiant.view.ViewTransformDelegater
                        public RectF getContentBounds() {
                            return ZoomAspectScaledTextureView2.this.getContentBounds();
                        }
                    };
                    typedArray.setScaleMode(i2);
                    typedArray.setEnableHandleTouchEvent(i3);
                    setViewTransformer(typedArray);
                    setAspectRatio(d);
                }
            } finally {
                obtainStyledAttributes.recycle();
            }
        } catch (UnsupportedOperationException e3) {
            e = e3;
            i2 = 0;
        }
        obtainStyledAttributes.recycle();
        obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ZoomAspectScaledTextureView, i, 0);
        try {
            i3 = obtainStyledAttributes.getInteger(R.styleable.ZoomAspectScaledTextureView_handle_touch_event, 7);
            super.setSurfaceTextureListener(this);
            typedArray = new ViewTransformDelegater(this) { // from class: com.serenegiant.widget.ZoomAspectScaledTextureView2.1
                @Override // com.serenegiant.view.ViewTransformDelegater
                public void onInit() {
                }

                @Override // com.serenegiant.view.ViewTransformer
                protected void setTransform(View view, Matrix matrix) {
                    ZoomAspectScaledTextureView2.this.superSetTransform(matrix);
                }

                @Override // com.serenegiant.view.ViewTransformer
                protected Matrix getTransform(View view, Matrix matrix) {
                    return ZoomAspectScaledTextureView2.this.superGetTransform(matrix);
                }

                @Override // com.serenegiant.view.ViewTransformDelegater
                public RectF getContentBounds() {
                    return ZoomAspectScaledTextureView2.this.getContentBounds();
                }
            };
            typedArray.setScaleMode(i2);
            typedArray.setEnableHandleTouchEvent(i3);
            setViewTransformer(typedArray);
            setAspectRatio(d);
        } finally {
            typedArray.recycle();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        MeasureSpecDelegater.MeasureSpec onMeasure = MeasureSpecDelegater.onMeasure(this, getAspectRatio(), getScaleMode(), this.mNeedResizeToKeepAspect, i, i2);
        super.onMeasure(onMeasure.widthMeasureSpec, onMeasure.heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        init();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            ((ViewTransformDelegater) viewTransformer).clearPendingTasks();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            return ((ViewTransformDelegater) viewTransformer).onSaveInstanceState(super.onSaveInstanceState());
        }
        return super.onSaveInstanceState();
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(parcelable);
        if (parcelable instanceof AbsSavedState) {
            super.onRestoreInstanceState(((AbsSavedState) parcelable).getSuperState());
        }
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            ((ViewTransformDelegater) viewTransformer).onRestoreInstanceState(parcelable);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            ((ViewTransformDelegater) viewTransformer).onConfigurationChanged(configuration);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (handleOnTouchEvent(motionEvent)) {
            return true;
        }
        IViewTransformer viewTransformer = getViewTransformer();
        if ((viewTransformer instanceof ViewTransformDelegater) && ((ViewTransformDelegater) viewTransformer).onTouchEvent(motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean hasSurface() {
        return this.mHasSurface;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mHasSurface = true;
        setMirror(0);
        init();
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureAvailable(surfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        applyMirrorMode();
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

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(int i) {
        if (this.mMirrorMode != i) {
            this.mMirrorMode = i;
            applyMirrorMode();
        }
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        return this.mMirrorMode;
    }

    public void setEnableHandleTouchEvent(int i) {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            ((ViewTransformDelegater) viewTransformer).setEnableHandleTouchEvent(i);
        }
    }

    public int getEnableHandleTouchEvent() {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            return ((ViewTransformDelegater) viewTransformer).getEnableHandleTouchEvent();
        }
        return 7;
    }

    protected void init() {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            ((ViewTransformDelegater) viewTransformer).init();
        }
    }

    private void applyMirrorMode() {
        int i = this.mMirrorMode;
        if (i == 1) {
            setScaleX(-1.0f);
            setScaleY(1.0f);
        } else if (i == 2) {
            setScaleX(1.0f);
            setScaleY(-1.0f);
        } else if (i == 3) {
            setScaleX(-1.0f);
            setScaleY(-1.0f);
        } else {
            setScaleX(1.0f);
            setScaleY(1.0f);
        }
    }

    protected RectF getContentBounds() {
        float height = getHeight();
        double d = this.mRequestedAspect;
        return new RectF(0.0f, 0.0f, d > 0.0d ? (float) (d * height) : getWidth(), height);
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

    @Override // com.serenegiant.widget.IScaledView
    public void setScaleMode(int i) {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            ((ViewTransformDelegater) viewTransformer).setScaleMode(i);
        }
    }

    @Override // com.serenegiant.widget.IScaledView
    public int getScaleMode() {
        IViewTransformer viewTransformer = getViewTransformer();
        if (viewTransformer instanceof ViewTransformDelegater) {
            return ((ViewTransformDelegater) viewTransformer).getScaleMode();
        }
        return 1;
    }
}
