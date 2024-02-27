package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.serenegiant.common.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javassist.compiler.TokenId;

/* loaded from: classes2.dex */
public class ProgressView extends View implements IProgressView {
    private static final int CLIP_HORIZONTAL = 8;
    private static final int CLIP_VERTICAL = 128;
    private static final boolean DEBUG = false;
    public static final int DIRECTION_BOTTOM_TO_TOP = 90;
    @Deprecated
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    @Deprecated
    public static final int DIRECTION_RIGHT_TO_LEFT = 180;
    @Deprecated
    public static final int DIRECTION_TOP_TO_BOTTOM = 270;
    private static final String TAG = "ProgressView";
    private ClipDrawable mClipDrawable;
    private int mDirectionDegrees;
    private Drawable mDrawable;
    private int mMax;
    private int mMin;
    private int mProgress;
    private int mProgressColor;
    private float mScale;
    private final Object mSync;
    private final Runnable mUpdateProgressOnUITask;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Direction {
    }

    public ProgressView(Context context) {
        this(context, null, 0);
    }

    public ProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSync = new Object();
        this.mDirectionDegrees = 90;
        this.mMin = 0;
        this.mMax = 100;
        this.mScale = 100.0f;
        this.mProgress = 40;
        this.mProgressColor = -65536;
        this.mUpdateProgressOnUITask = new Runnable() { // from class: com.serenegiant.widget.ProgressView.1
            @Override // java.lang.Runnable
            public void run() {
                int i2;
                synchronized (ProgressView.this.mSync) {
                    i2 = ProgressView.this.mProgress;
                }
                if (ProgressView.this.mClipDrawable != null) {
                    int i3 = ((int) (i2 * ProgressView.this.mScale)) + ProgressView.this.mMin;
                    if (i3 < 0) {
                        i3 = 0;
                    }
                    if (i3 > 10000) {
                        i3 = 10000;
                    }
                    ProgressView.this.mClipDrawable.setLevel(i3);
                }
                ProgressView.this.invalidate();
            }
        };
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ProgressView, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ProgressView_drawableCompat, 0);
        if (resourceId != 0) {
            this.mDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resourceId));
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.ProgressView_backgroundCompat, 0);
        if (resourceId2 != 0) {
            Drawable wrap = DrawableCompat.wrap(ContextCompat.getDrawable(context, resourceId2));
            int color = obtainStyledAttributes.getColor(R.styleable.ProgressView_backgroundTintCompat, 0);
            if (color != 0) {
                DrawableCompat.setTint(wrap, color);
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_IN);
            }
            setBackground(wrap);
        }
        this.mProgressColor = obtainStyledAttributes.getColor(R.styleable.ProgressView_android_color, this.mProgressColor);
        this.mDirectionDegrees = checkDirection(obtainStyledAttributes.getInt(R.styleable.ProgressView_direction, this.mDirectionDegrees));
        this.mMin = obtainStyledAttributes.getInt(R.styleable.ProgressView_android_min, this.mMin);
        int i2 = obtainStyledAttributes.getInt(R.styleable.ProgressView_android_min, this.mMax);
        this.mMax = i2;
        int i3 = this.mMin;
        if (i3 == i2) {
            throw new IllegalArgumentException("min and max should be different");
        }
        if (i3 > i2) {
            this.mMin = i2;
            this.mMax = i3;
        }
        this.mProgress = obtainStyledAttributes.getInt(R.styleable.ProgressView_android_progress, this.mProgress);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ClipDrawable clipDrawable = this.mClipDrawable;
        if (clipDrawable != null) {
            clipDrawable.draw(canvas);
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        resize();
    }

    @Override // com.serenegiant.widget.IProgressView
    public void setMinMax(int i, int i2) {
        if ((this.mMin == i && this.mMax == i2) || i == i2) {
            return;
        }
        this.mMin = Math.min(i, i2);
        this.mMax = Math.max(i, i2);
        resize();
    }

    @Override // com.serenegiant.widget.IProgressView
    public void setProgress(int i) {
        synchronized (this.mSync) {
            if (this.mProgress != i) {
                this.mProgress = i;
                removeCallbacks(this.mUpdateProgressOnUITask);
                post(this.mUpdateProgressOnUITask);
            }
        }
    }

    @Override // com.serenegiant.widget.IProgressView
    public int getProgress() {
        int i;
        synchronized (this.mSync) {
            i = this.mProgress;
        }
        return i;
    }

    @Deprecated
    public void setDirection(int i) {
        int checkDirection = checkDirection(((i / 90) * 90) % TokenId.EXOR_E);
        if (this.mDirectionDegrees != checkDirection) {
            this.mDirectionDegrees = checkDirection;
            resize();
        }
    }

    public int getDirection() {
        return this.mDirectionDegrees;
    }

    public void setColor(int i) {
        if (this.mProgressColor != i) {
            this.mProgressColor = i;
            refreshDrawable(null);
        }
    }

    public void setDrawable(Drawable drawable) {
        if (this.mDrawable != drawable) {
            refreshDrawable(drawable);
        }
    }

    protected void resize() {
        synchronized (this.mSync) {
            float f = this.mProgress * this.mScale;
            int i = this.mMin;
            float f2 = 10000.0f / (this.mMax - i);
            this.mScale = f2;
            this.mProgress = (int) (((f + i) - i) / f2);
        }
        refreshDrawable(this.mDrawable);
    }

    protected void refreshDrawable(Drawable drawable) {
        int i;
        int i2;
        synchronized (this.mSync) {
            i = ((int) (this.mProgress * this.mScale)) + this.mMin;
        }
        Drawable wrap = drawable != null ? DrawableCompat.wrap(drawable) : null;
        this.mDrawable = wrap;
        int i3 = 1;
        if (wrap == null) {
            setLayerType(2, null);
            this.mDrawable = new ColorDrawable(this.mProgressColor);
        } else {
            if (this.mDrawable instanceof VectorDrawable) {
                setLayerType(1, null);
            }
            DrawableCompat.setTint(this.mDrawable, this.mProgressColor);
            DrawableCompat.setTintMode(this.mDrawable, PorterDuff.Mode.SRC_IN);
        }
        int i4 = this.mDirectionDegrees;
        if (i4 != 90) {
            if (i4 == 180) {
                i2 = 125;
            } else if (i4 != 270) {
                i2 = 123;
            } else {
                i2 = 183;
            }
            this.mClipDrawable = new ClipDrawable(this.mDrawable, i2, i3);
            Rect rect = new Rect();
            getDrawingRect(rect);
            this.mClipDrawable.setBounds(rect);
            this.mClipDrawable.setLevel(i);
            postInvalidate();
        }
        i2 = 215;
        i3 = 2;
        this.mClipDrawable = new ClipDrawable(this.mDrawable, i2, i3);
        Rect rect2 = new Rect();
        getDrawingRect(rect2);
        this.mClipDrawable.setBounds(rect2);
        this.mClipDrawable.setLevel(i);
        postInvalidate();
    }

    private static int checkDirection(int i) {
        while (i < 0) {
            i += TokenId.EXOR_E;
        }
        return i % TokenId.EXOR_E;
    }
}
