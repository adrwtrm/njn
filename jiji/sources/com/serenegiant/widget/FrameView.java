package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.serenegiant.common.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class FrameView extends View {
    private static final float DEFAULT_FRAME_WIDTH_DP = 3.0f;
    public static final int FRAME_TYPE_CIRCLE = 4;
    public static final int FRAME_TYPE_CIRCLE_2 = 6;
    public static final int FRAME_TYPE_CROSS_CIRCLE = 5;
    public static final int FRAME_TYPE_CROSS_CIRCLE2 = 7;
    public static final int FRAME_TYPE_CROSS_FULL = 2;
    public static final int FRAME_TYPE_CROSS_QUARTER = 3;
    public static final int FRAME_TYPE_FRAME = 1;
    public static final int FRAME_TYPE_NONE = 0;
    public static final int FRAME_TYPE_NUMS = 8;
    public static final float MAX_SCALE = 10.0f;
    public static final int SCALE_TYPE_INCH = 1;
    public static final int SCALE_TYPE_MM = 2;
    public static final int SCALE_TYPE_NONE = 0;
    public static final int SCALE_TYPE_NUMS = 3;
    private static final String TAG = "FrameView";
    private final float defaultFrameWidth;
    private final RectF mBoundsRect;
    private float mCenterX;
    private float mCenterY;
    private int mFrameColor;
    private int mFrameType;
    private float mFrameWidth;
    private float mHeight;
    private final Paint mPaint;
    private float mRadius;
    private float mRadius2;
    private float mRadius4;
    private float mRadiusQ;
    private float mRotation;
    private float mScale;
    private int mScaleColor;
    private int mScaleType;
    private float mScaleWidth;
    private int mTickColor;
    private float mWidth;
    private final DisplayMetrics metrics;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface FrameType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ScaleType {
    }

    public FrameView(Context context) {
        this(context, null, 0);
    }

    public FrameView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FrameView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mBoundsRect = new RectF();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.metrics = displayMetrics;
        float f = displayMetrics.density * DEFAULT_FRAME_WIDTH_DP;
        this.defaultFrameWidth = f;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.FrameView, i, 0);
        this.mFrameType = obtainStyledAttributes.getInt(R.styleable.FrameView_frame_type, 0);
        this.mFrameWidth = obtainStyledAttributes.getDimension(R.styleable.FrameView_frame_width, f);
        this.mFrameColor = obtainStyledAttributes.getColor(R.styleable.FrameView_frame_color, -5131855);
        this.mScaleType = obtainStyledAttributes.getInt(R.styleable.FrameView_scale_type, 0);
        this.mScaleWidth = obtainStyledAttributes.getDimension(R.styleable.FrameView_scale_width, this.mFrameWidth);
        this.mScaleColor = obtainStyledAttributes.getColor(R.styleable.FrameView_scale_color, this.mFrameColor);
        this.mTickColor = obtainStyledAttributes.getColor(R.styleable.FrameView_tick_color, this.mScaleColor);
        this.mRotation = obtainStyledAttributes.getFloat(R.styleable.FrameView_scale_rotation, 0.0f);
        this.mScale = obtainStyledAttributes.getFloat(R.styleable.FrameView_scale_scale, 1.0f);
        obtainStyledAttributes.recycle();
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        float f = this.mFrameWidth / 2.0f;
        this.mBoundsRect.set(getPaddingLeft() + f, getPaddingTop() + f, (getWidth() - getPaddingRight()) - f, (getHeight() - getPaddingBottom()) - f);
        this.mCenterX = this.mBoundsRect.centerX();
        this.mCenterY = this.mBoundsRect.centerY();
        this.mWidth = this.mBoundsRect.width();
        float height = this.mBoundsRect.height();
        this.mHeight = height;
        float min = Math.min(this.mWidth, height) * 0.9f;
        this.mRadius = min;
        this.mRadius2 = min / 2.0f;
        float f2 = min / 4.0f;
        this.mRadius4 = f2;
        this.mRadiusQ = (float) (f2 / Math.sqrt(2.0d));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mFrameType != 0) {
            this.mPaint.setStrokeWidth(this.mFrameWidth);
            this.mPaint.setColor(this.mFrameColor);
            canvas.drawRect(this.mBoundsRect, this.mPaint);
            this.mPaint.setStrokeWidth(this.mScaleWidth);
            this.mPaint.setColor(this.mScaleColor);
            float f = this.mCenterX;
            float f2 = this.mCenterY;
            float f3 = this.mRadius2;
            float f4 = this.mRadius4;
            int save = canvas.save();
            try {
                canvas.rotate(this.mRotation, f, f2);
                float f5 = this.mScale;
                canvas.scale(f5, f5, f, f2);
                switch (this.mFrameType) {
                    case 2:
                        int i = this.mScaleType;
                        if (i == 0) {
                            canvas.drawLine(f, this.mBoundsRect.top, f, this.mBoundsRect.bottom, this.mPaint);
                            canvas.drawLine(this.mBoundsRect.left, f2, this.mBoundsRect.right, f2, this.mPaint);
                            break;
                        } else if (i == 1) {
                            draw_scale_full(canvas, this.mWidth, this.mHeight, this.metrics.xdpi / 10.0f, this.metrics.ydpi / 10.0f, 10);
                            break;
                        } else if (i == 2) {
                            draw_scale_full(canvas, this.mWidth, this.mHeight, this.metrics.xdpi / 12.7f, this.metrics.ydpi / 12.7f, 5);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        int i2 = this.mScaleType;
                        if (i2 == 0) {
                            canvas.drawLine(f, f2 - f4, f, f2 + f4, this.mPaint);
                            canvas.drawLine(f - f4, f2, f + f4, f2, this.mPaint);
                            break;
                        } else if (i2 == 1) {
                            draw_scale_full(canvas, f3, f3, this.metrics.xdpi / 10.0f, this.metrics.ydpi / 10.0f, 10);
                            break;
                        } else if (i2 == 2) {
                            draw_scale_full(canvas, f3, f3, this.metrics.xdpi / 12.7f, this.metrics.ydpi / 12.7f, 5);
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        canvas.drawCircle(this.mCenterX, f2, f4, this.mPaint);
                        break;
                    case 5:
                        int i3 = this.mScaleType;
                        if (i3 == 0) {
                            canvas.drawLine(f, f2 - f4, f, f2 + f4, this.mPaint);
                            canvas.drawLine(f - f4, f2, f + f4, f2, this.mPaint);
                        } else if (i3 == 1) {
                            draw_scale_full(canvas, f3, f3, this.metrics.xdpi / 10.0f, this.metrics.ydpi / 10.0f, 10);
                        } else if (i3 == 2) {
                            draw_scale_full(canvas, f3, f3, this.metrics.xdpi / 12.7f, this.metrics.ydpi / 12.7f, 5);
                        }
                        canvas.drawCircle(f, f2, f4, this.mPaint);
                        break;
                    case 6:
                        canvas.drawCircle(f, f2, f4 / 2.0f, this.mPaint);
                        canvas.drawCircle(f, f2, f4, this.mPaint);
                        break;
                    case 7:
                        int i4 = this.mScaleType;
                        if (i4 == 0) {
                            canvas.drawLine(f, f2 - f4, f, f2 + f4, this.mPaint);
                            canvas.drawLine(f - f4, f2, f + f4, f2, this.mPaint);
                        } else if (i4 == 1) {
                            draw_scale_full(canvas, f3, f3, this.metrics.xdpi / 10.0f, this.metrics.ydpi / 10.0f, 10);
                        } else if (i4 == 2) {
                            draw_scale_full(canvas, f3, f3, this.metrics.xdpi / 12.7f, this.metrics.ydpi / 12.7f, 5);
                        }
                        canvas.drawCircle(f, f2, f4 / 2.0f, this.mPaint);
                        canvas.drawCircle(f, f2, f4, this.mPaint);
                        break;
                }
            } finally {
                canvas.restoreToCount(save);
            }
        }
    }

    private void draw_scale_full(Canvas canvas, float f, float f2, float f3, float f4, int i) {
        float f5 = this.mCenterX;
        float f6 = this.mCenterY;
        float f7 = this.mScaleWidth;
        float f8 = this.defaultFrameWidth;
        float f9 = f7 > f8 ? 4.0f * f7 : 4.0f * f8;
        float f10 = f7 > f8 ? f7 * 2.0f : f8 * 2.0f;
        float f11 = f / 2.0f;
        float f12 = f2 / 2.0f;
        int i2 = (int) (f11 / f3);
        int i3 = (int) (f12 / f4);
        canvas.drawLine(f5, f6 - f12, f5, f6 + f12, this.mPaint);
        canvas.drawLine(f5 - f11, f6, f5 + f11, f6, this.mPaint);
        this.mPaint.setColor(this.mTickColor);
        for (int i4 = 0; i4 < i2; i4++) {
            float f13 = i4 % i == 0 ? f9 : f10;
            float f14 = i4 * f3;
            float f15 = f5 + f14;
            float f16 = f6 - f13;
            float f17 = f6 + f13;
            canvas.drawLine(f15, f16, f15, f17, this.mPaint);
            float f18 = f5 - f14;
            canvas.drawLine(f18, f16, f18, f17, this.mPaint);
        }
        for (int i5 = 0; i5 < i3; i5++) {
            float f19 = i5 % i == 0 ? f9 : f10;
            float f20 = i5 * f4;
            float f21 = f6 + f20;
            float f22 = f5 - f19;
            float f23 = f5 + f19;
            canvas.drawLine(f22, f21, f23, f21, this.mPaint);
            float f24 = f6 - f20;
            canvas.drawLine(f22, f24, f23, f24, this.mPaint);
        }
        this.mPaint.setColor(this.mScaleColor);
    }

    public void setFrameType(int i) {
        if (this.mFrameType == i || i < 0 || i >= 8) {
            return;
        }
        this.mFrameType = i;
        postInvalidate();
    }

    public int getFrameType() {
        return this.mFrameType;
    }

    public void setFrameColor(int i) {
        int i2 = this.mFrameColor;
        if (i2 != i) {
            if (i2 == this.mScaleColor) {
                setScaleColor(i);
            }
            this.mFrameColor = i;
            postInvalidate();
        }
    }

    public int getFrameColor() {
        return this.mFrameColor;
    }

    public void setFrameWidth(float f) {
        if (f <= 1.0f) {
            f = 0.0f;
        }
        float f2 = this.mFrameWidth;
        if (f2 == f || f < 0.0f) {
            return;
        }
        if (f2 == this.mScaleWidth) {
            setScaleWidth(f);
        }
        this.mFrameWidth = f;
        postInvalidate();
    }

    public float getFrameWidth() {
        return this.mFrameWidth;
    }

    public void setScaleColor(int i) {
        int i2 = this.mScaleColor;
        if (i2 != i) {
            if (i2 == this.mTickColor) {
                setTickColor(i);
            }
            this.mScaleColor = i;
            postInvalidate();
        }
    }

    public int getScaleColor() {
        return this.mScaleColor;
    }

    public void setScaleType(int i) {
        if (this.mScaleType == i || i < 0 || i >= 3) {
            return;
        }
        this.mScaleType = i;
        postInvalidate();
    }

    public int getScaleType() {
        return this.mScaleType;
    }

    public void setScaleWidth(float f) {
        if (f <= 1.0f) {
            f = 0.0f;
        }
        if (this.mScaleWidth != f) {
            this.mScaleWidth = f;
            postInvalidate();
        }
    }

    public float getScaleWidth() {
        return this.mScaleWidth;
    }

    public void setTickColor(int i) {
        if (this.mTickColor != i) {
            this.mTickColor = i;
            postInvalidate();
        }
    }

    public int getTickColor() {
        return this.mTickColor;
    }

    @Override // android.view.View
    public void setRotation(float f) {
        while (f > 360.0f) {
            f -= 360.0f;
        }
        while (f < -360.0f) {
            f += 360.0f;
        }
        if (this.mRotation != f) {
            this.mRotation = f;
            postInvalidate();
        }
    }

    @Override // android.view.View
    public float getRotation() {
        return this.mRotation;
    }

    public void setScale(float f) {
        if (this.mScale == f || f <= 0.0f || f > 10.0f) {
            return;
        }
        this.mScale = f;
        postInvalidate();
    }

    public float getScale() {
        return this.mScale;
    }
}
