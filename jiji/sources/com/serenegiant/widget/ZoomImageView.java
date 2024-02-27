package com.serenegiant.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.serenegiant.common.R;
import com.serenegiant.view.ViewTransformDelegater;

/* loaded from: classes2.dex */
public class ZoomImageView extends TransformImageView implements ViewTransformDelegater.ViewTransformListener, IScaledView {
    private static final boolean DEBUG = false;
    private static final float[] REVERSE = {-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final int REVERSING_TIMEOUT = 100;
    private static final String TAG = "ZoomImageView";
    private ColorFilter mColorReverseFilter;
    private final ViewTransformDelegater mDelegater;
    private double mRequestedAspect;
    private ColorFilter mSavedColorFilter;
    private ViewTransformDelegater.ViewTransformListener mViewTransformListener;
    private WaitReverseReset mWaitReverseReset;

    public ZoomImageView(Context context) {
        this(context, null, 0);
    }

    public ZoomImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        double d = -1.0d;
        this.mRequestedAspect = -1.0d;
        int i2 = 0;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IScaledView, i, 0);
        try {
            try {
                d = obtainStyledAttributes.getFloat(R.styleable.IScaledView_aspect_ratio, -1.0f);
                i2 = obtainStyledAttributes.getInt(R.styleable.IScaledView_scale_mode, 0);
            } catch (UnsupportedOperationException e) {
                String str = TAG;
                Log.d(str, str, e);
            }
            ViewTransformDelegater viewTransformDelegater = new ViewTransformDelegater(this) { // from class: com.serenegiant.widget.ZoomImageView.1
                @Override // com.serenegiant.view.ViewTransformer
                protected void setTransform(View view, Matrix matrix) {
                    ZoomImageView.this.superSetImageMatrix(matrix);
                }

                @Override // com.serenegiant.view.ViewTransformer
                protected Matrix getTransform(View view, Matrix matrix) {
                    return ZoomImageView.this.superGetImageMatrix(matrix);
                }

                @Override // com.serenegiant.view.ViewTransformDelegater
                public RectF getContentBounds() {
                    Drawable drawable = ZoomImageView.this.getDrawable();
                    if (drawable != null) {
                        return new RectF(drawable.getBounds());
                    }
                    return new RectF();
                }

                @Override // com.serenegiant.view.ViewTransformDelegater
                public void onInit() {
                    ZoomImageView.this.superSetScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    ZoomImageView zoomImageView = ZoomImageView.this;
                    zoomImageView.setFrame(zoomImageView.getLeft(), ZoomImageView.this.getTop(), ZoomImageView.this.getRight(), ZoomImageView.this.getBottom());
                }
            };
            this.mDelegater = viewTransformDelegater;
            viewTransformDelegater.setScaleMode(i2);
            setViewTransformer(viewTransformDelegater);
            setAspectRatio(d);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        this.mDelegater.clearPendingTasks();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        return this.mDelegater.onSaveInstanceState(super.onSaveInstanceState());
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(parcelable);
        if (parcelable instanceof AbsSavedState) {
            super.onRestoreInstanceState(((AbsSavedState) parcelable).getSuperState());
        }
        this.mDelegater.onRestoreInstanceState(parcelable);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegater.onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (getWidth() == 0 || getHeight() == 0 || !hasImage()) {
            return;
        }
        init();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (hasImage()) {
            if (this.mDelegater.onTouchEvent(motionEvent)) {
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(ImageView.ScaleType.MATRIX);
        Log.w(TAG, "setScaleType: ignore this parameter on ZoomImageView2, fixed to ScaleType.MATRIX.");
    }

    @Override // android.widget.ImageView
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        this.mSavedColorFilter = colorFilter;
    }

    public boolean hasImage() {
        return getDrawable() != null;
    }

    public Bitmap getCurrentImage() {
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setMatrix(super.getImageMatrix());
        super.getDrawable().draw(canvas);
        return createBitmap;
    }

    public Bitmap getCurrentImage(Rect rect) {
        Bitmap currentImage = getCurrentImage();
        if (rect == null || rect.isEmpty()) {
            return currentImage;
        }
        Bitmap createBitmap = Bitmap.createBitmap(currentImage, rect.left, rect.top, rect.width(), rect.height(), (Matrix) null, false);
        currentImage.recycle();
        return createBitmap;
    }

    public void setEnableHandleTouchEvent(int i) {
        this.mDelegater.setEnableHandleTouchEvent(i);
    }

    public int getEnableHandleTouchEvent() {
        return this.mDelegater.getEnableHandleTouchEvent();
    }

    public void setMaxScale(float f) throws IllegalArgumentException {
        this.mDelegater.setMaxScale(f);
    }

    public float getMaxScale() {
        return this.mDelegater.getMaxScale();
    }

    public void setMinScale(float f) throws IllegalArgumentException {
        this.mDelegater.setMinScale(f);
    }

    public float getMinScale() {
        return this.mDelegater.getMinScale();
    }

    public void setViewTransformListener(ViewTransformDelegater.ViewTransformListener viewTransformListener) {
        this.mViewTransformListener = viewTransformListener;
    }

    public ViewTransformDelegater.ViewTransformListener getViewTransformListener() {
        return this.mViewTransformListener;
    }

    public float getScale() {
        return this.mDelegater.getScale();
    }

    public void setScale(float f) {
        this.mDelegater.setScale(f);
    }

    public void setScaleRelative(float f) {
        this.mDelegater.setScaleRelative(f);
    }

    public PointF getTranslate(PointF pointF) {
        return this.mDelegater.getTranslate(pointF);
    }

    @Override // android.view.View
    public float getRotation() {
        return this.mDelegater.getRotation();
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
        this.mDelegater.setScaleMode(i);
    }

    @Override // com.serenegiant.widget.IScaledView
    public int getScaleMode() {
        return this.mDelegater.getScaleMode();
    }

    @Override // com.serenegiant.widget.IScaledView
    public void setNeedResizeToKeepAspect(boolean z) {
        this.mDelegater.setKeepAspect(z);
    }

    private void init() {
        this.mDelegater.init();
        superSetScaleType(ImageView.ScaleType.MATRIX);
        superSetImageMatrix(this.mDelegater.getTransform(null));
    }

    /* loaded from: classes2.dex */
    private final class WaitReverseReset implements Runnable {
        private WaitReverseReset() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ZoomImageView.this.resetColorFilter();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetColorFilter() {
        super.setColorFilter(this.mSavedColorFilter);
    }

    @Override // com.serenegiant.view.ViewTransformDelegater.ViewTransformListener
    public void onStateChanged(View view, int i) {
        if (i == 0) {
            resetColorFilter();
        } else if (i == 5) {
            if (this.mColorReverseFilter == null) {
                this.mColorReverseFilter = new ColorMatrixColorFilter(new ColorMatrix(REVERSE));
            }
            super.setColorFilter(this.mColorReverseFilter);
            if (this.mWaitReverseReset == null) {
                this.mWaitReverseReset = new WaitReverseReset();
            }
            postDelayed(this.mWaitReverseReset, 100L);
        }
        ViewTransformDelegater.ViewTransformListener viewTransformListener = this.mViewTransformListener;
        if (viewTransformListener != null) {
            viewTransformListener.onStateChanged(view, i);
        }
    }

    @Override // com.serenegiant.view.ViewTransformDelegater.ViewTransformListener
    public void onTransformed(View view, Matrix matrix) {
        ViewTransformDelegater.ViewTransformListener viewTransformListener = this.mViewTransformListener;
        if (viewTransformListener != null) {
            viewTransformListener.onTransformed(view, matrix);
        }
    }
}
