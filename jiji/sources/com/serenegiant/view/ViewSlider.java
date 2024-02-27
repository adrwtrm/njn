package com.serenegiant.view;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import com.serenegiant.common.R;
import com.serenegiant.view.animation.ResizeAnimation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class ViewSlider {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_DURATION_RESIZE_MS = 300;
    public static final int HORIZONTAL = 1;
    private static final String TAG = "ViewSlider";
    public static final int VERTICAL = 0;
    private final Animation.AnimationListener mAnimationListener;
    private final int mDurationResizeMs;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    private int mOrientation;
    private final View mParent;
    private final View mTarget;
    private int mTargetMaxHeight;
    private int mTargetMaxWidth;
    private int mTargetMinHeight;
    private int mTargetMinWidth;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Orientation {
    }

    public ViewSlider(View view, int i) throws IllegalArgumentException {
        this(view, i, 0, 300);
    }

    public ViewSlider(View view, int i, int i2) throws IllegalArgumentException {
        this(view, i, i2, 300);
    }

    public ViewSlider(View view, int i, int i2, int i3) throws IllegalArgumentException {
        this(view, view.findViewById(i), i2, i3);
    }

    public ViewSlider(View view, View view2, int i, int i2) throws IllegalArgumentException {
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.serenegiant.view.ViewSlider.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view3, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                if (view3 == ViewSlider.this.mTarget) {
                    if (ViewSlider.this.mTargetMaxWidth <= 0) {
                        ViewSlider.this.mTargetMaxWidth = view3.getWidth();
                    }
                    if (ViewSlider.this.mTargetMaxHeight <= 0) {
                        ViewSlider.this.mTargetMaxHeight = view3.getHeight();
                    }
                }
            }
        };
        this.mOnLayoutChangeListener = onLayoutChangeListener;
        this.mAnimationListener = new Animation.AnimationListener() { // from class: com.serenegiant.view.ViewSlider.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                Object tag = ViewSlider.this.mTarget.getTag(R.id.visibility);
                if (!(tag instanceof Integer) || ((Integer) tag).intValue() != 1) {
                    ViewSlider.this.mTarget.setVisibility(4);
                    return;
                }
                ViewSlider.this.mTarget.setTag(R.id.visibility, 0);
                Object tag2 = ViewSlider.this.mTarget.getTag(R.id.auto_hide_duration);
                long longValue = tag2 instanceof Long ? ((Long) tag2).longValue() : 0L;
                if (longValue > 0) {
                    ViewSlider.this.mTarget.postDelayed(new Runnable() { // from class: com.serenegiant.view.ViewSlider.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ViewSlider.this.hide(ViewSlider.this.mDurationResizeMs);
                        }
                    }, longValue);
                }
            }
        };
        this.mParent = view;
        if (view.getClass().getSimpleName().equals("ConstraintLayout")) {
            Log.w(TAG, "If parent is ConstraintLayout, ViewSlider will not work well.");
        }
        this.mTarget = view2;
        if (view2 == null) {
            throw new IllegalArgumentException("Target view not found");
        }
        this.mDurationResizeMs = i2 <= 0 ? 300 : i2;
        this.mOrientation = i;
        this.mTargetMaxWidth = view2.getWidth();
        this.mTargetMaxHeight = view2.getHeight();
        this.mTargetMinHeight = 0;
        this.mTargetMinWidth = 0;
        view.addOnLayoutChangeListener(onLayoutChangeListener);
        view2.addOnLayoutChangeListener(onLayoutChangeListener);
        if (view2.getVisibility() == 8) {
            view2.setVisibility(4);
        }
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void release() {
        this.mTarget.clearAnimation();
        this.mTarget.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mParent.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
    }

    public View getTargetView() {
        return this.mTarget;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setTargetWidth(int i) throws IllegalArgumentException {
        if (i >= 0) {
            this.mTargetMaxWidth = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setTargetHeight(int i) throws IllegalArgumentException {
        if (i >= 0) {
            this.mTargetMaxHeight = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void resetTargetSize() {
        setTargetSize(this.mTarget.getWidth(), this.mTarget.getHeight());
    }

    public void setTargetSize(int i, int i2) throws IllegalArgumentException {
        if (i >= 0 && i2 >= 0) {
            this.mTargetMaxWidth = i;
            this.mTargetMaxHeight = i2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setMinSize(int i, int i2) throws IllegalArgumentException {
        if (i >= 0 && i2 >= 0) {
            this.mTargetMinWidth = i;
            this.mTargetMinHeight = i2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public int getVisibility() {
        return this.mTarget.getVisibility();
    }

    public boolean isVisible() {
        return this.mTarget.getVisibility() == 0 && this.mTarget.getWidth() > 0 && this.mTarget.getHeight() > 0;
    }

    public void show(final long j) {
        this.mTarget.post(new Runnable() { // from class: com.serenegiant.view.ViewSlider.1
            @Override // java.lang.Runnable
            public void run() {
                final ResizeAnimation resizeAnimation;
                ViewSlider.this.mTarget.clearAnimation();
                if (ViewSlider.this.mOrientation == 0) {
                    resizeAnimation = new ResizeAnimation(ViewSlider.this.mTarget, ViewSlider.this.mTargetMaxWidth, ViewSlider.this.mTargetMinHeight, ViewSlider.this.mTargetMaxWidth, ViewSlider.this.mTargetMaxHeight);
                } else {
                    resizeAnimation = new ResizeAnimation(ViewSlider.this.mTarget, ViewSlider.this.mTargetMinWidth, ViewSlider.this.mTargetMaxHeight, ViewSlider.this.mTargetMaxWidth, ViewSlider.this.mTargetMaxHeight);
                }
                resizeAnimation.setDuration(ViewSlider.this.mDurationResizeMs);
                resizeAnimation.setAnimationListener(ViewSlider.this.mAnimationListener);
                ViewSlider.this.mTarget.setTag(R.id.visibility, 1);
                ViewSlider.this.mTarget.setTag(R.id.auto_hide_duration, Long.valueOf(j));
                ViewSlider.this.mTarget.postDelayed(new Runnable() { // from class: com.serenegiant.view.ViewSlider.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewSlider.this.mTarget.setVisibility(0);
                        ViewSlider.this.mTarget.startAnimation(resizeAnimation);
                    }
                }, 50L);
            }
        });
    }

    public void hide() {
        hide(this.mDurationResizeMs);
    }

    public void hide(final long j) {
        this.mTarget.post(new Runnable() { // from class: com.serenegiant.view.ViewSlider.2
            @Override // java.lang.Runnable
            public void run() {
                ResizeAnimation resizeAnimation;
                if (ViewSlider.this.mTarget.getVisibility() == 0) {
                    ViewSlider.this.mTarget.clearAnimation();
                    if (j > 0) {
                        if (ViewSlider.this.mOrientation == 0) {
                            resizeAnimation = new ResizeAnimation(ViewSlider.this.mTarget, ViewSlider.this.mTargetMaxWidth, ViewSlider.this.mTarget.getHeight(), ViewSlider.this.mTargetMaxWidth, ViewSlider.this.mTargetMinHeight);
                        } else {
                            resizeAnimation = new ResizeAnimation(ViewSlider.this.mTarget, ViewSlider.this.mTarget.getWidth(), ViewSlider.this.mTargetMaxHeight, ViewSlider.this.mTargetMinWidth, ViewSlider.this.mTargetMaxHeight);
                        }
                        resizeAnimation.setDuration(j);
                        resizeAnimation.setAnimationListener(ViewSlider.this.mAnimationListener);
                        ViewSlider.this.mTarget.setTag(R.id.visibility, 0);
                        ViewSlider.this.mTarget.startAnimation(resizeAnimation);
                        return;
                    }
                    if (ViewSlider.this.mOrientation == 0) {
                        ViewUtils.requestResize(ViewSlider.this.mTarget, ViewSlider.this.mTargetMaxWidth, ViewSlider.this.mTargetMinHeight);
                    } else {
                        ViewUtils.requestResize(ViewSlider.this.mTarget, ViewSlider.this.mTargetMinWidth, ViewSlider.this.mTargetMaxHeight);
                    }
                    ViewSlider.this.mTarget.setVisibility(4);
                }
            }
        });
    }
}
