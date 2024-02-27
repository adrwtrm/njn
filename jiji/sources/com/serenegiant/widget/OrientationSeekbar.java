package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.serenegiant.common.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class OrientationSeekbar extends AppCompatSeekBar {
    private static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    private static final String TAG = "OrientationSeekbar";
    public static final int VERTICAL = 1;
    private int lastProgress;
    private Paint mDebugPaint;
    private int mOrientation;
    private SeekBar.OnSeekBarChangeListener onChangeListener;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Orientation {
    }

    public OrientationSeekbar(Context context) {
        this(context, null, 0);
    }

    public OrientationSeekbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OrientationSeekbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOrientation = 0;
        this.lastProgress = 0;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.OrientationSeekbar, i, 0);
        this.mOrientation = obtainStyledAttributes.getInt(R.styleable.OrientationSeekbar_android_orientation, this.mOrientation);
        obtainStyledAttributes.recycle();
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
        requestLayout();
    }

    @Override // android.widget.SeekBar
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.onChangeListener = onSeekBarChangeListener;
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (this.mOrientation == 0) {
            super.onSizeChanged(i, i2, i3, i4);
        } else {
            super.onSizeChanged(i2, i, i4, i3);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        boolean z = this.mOrientation == 0;
        int i3 = z ? i : i2;
        if (z) {
            i = i2;
        }
        super.onMeasure(i3, i);
        setMeasuredDimension(z ? getMeasuredWidth() : getMeasuredHeight(), z ? getMeasuredHeight() : getMeasuredWidth());
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z = this.mOrientation == 0;
        float width = z ? getWidth() : getHeight();
        if (z) {
            getHeight();
        } else {
            getWidth();
        }
        if (!z) {
            canvas.rotate(270.0f);
            canvas.translate(-width, 0.0f);
        }
        super.draw(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            if (this.mOrientation == 0) {
                return super.onTouchEvent(motionEvent);
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                this.onChangeListener.onStartTrackingTouch(this);
                setPressed(true);
                setSelected(true);
            } else if (action == 1) {
                this.onChangeListener.onStopTrackingTouch(this);
                setPressed(false);
                setSelected(false);
            } else if (action == 2) {
                super.onTouchEvent(motionEvent);
                int max = getMax() - ((int) ((getMax() * motionEvent.getY()) / getHeight()));
                if (max < 0) {
                    max = 0;
                }
                if (max > getMax()) {
                    max = getMax();
                }
                setProgress(max);
                if (max != this.lastProgress) {
                    this.lastProgress = max;
                    this.onChangeListener.onProgressChanged(this, max, true);
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                this.onChangeListener.onProgressChanged(this, (int) ((getMax() * motionEvent.getY()) / getHeight()), true);
                setPressed(true);
                setSelected(true);
            } else if (action == 3) {
                super.onTouchEvent(motionEvent);
                setPressed(false);
                setSelected(false);
            }
            return true;
        }
        return false;
    }
}
