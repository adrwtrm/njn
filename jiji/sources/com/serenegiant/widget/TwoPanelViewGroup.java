package com.serenegiant.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.serenegiant.common.R;
import com.serenegiant.system.BuildCheck;

/* loaded from: classes2.dex */
public class TwoPanelViewGroup extends FrameLayout {
    private static final int DEFAULT_CHILD_GRAVITY = 17;
    private static final int DEFAULT_HEIGHT = 200;
    private static final float DEFAULT_SUB_WINDOW_SCALE = 0.2f;
    private static final int DEFAULT_WIDTH = 200;
    public static final int HORIZONTAL = 0;
    public static final int MODE_SELECT_1 = 1;
    public static final int MODE_SELECT_2 = 2;
    public static final int MODE_SINGLE_1 = 3;
    public static final int MODE_SINGLE_2 = 4;
    public static final int MODE_SPLIT = 0;
    private static final String TAG = "TwoPanelViewGroup";
    public static final int VERTICAL = 1;
    private ObjectAnimator mAnimator;
    private final Animator.AnimatorListener mAnimatorListener;
    private View mChild1;
    private View mChild2;
    private final Rect mChildRect;
    private int mDisplayMode;
    private boolean mEnableSubWindow;
    private boolean mFlipChildPos;
    private int mOrientation;
    private float mSubWindowScale;
    private final Object mSync;

    public TwoPanelViewGroup(Context context) {
        this(context, null, 0);
    }

    public TwoPanelViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoPanelViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSync = new Object();
        this.mChildRect = new Rect();
        this.mAnimatorListener = new Animator.AnimatorListener() { // from class: com.serenegiant.widget.TwoPanelViewGroup.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                synchronized (TwoPanelViewGroup.this.mSync) {
                    TwoPanelViewGroup.this.mAnimator = null;
                }
                TwoPanelViewGroup.this.requestLayout();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                synchronized (TwoPanelViewGroup.this.mSync) {
                    TwoPanelViewGroup.this.mAnimator = null;
                }
                TwoPanelViewGroup.this.requestLayout();
            }
        };
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TwoPanelViewGroup, i, 0);
        this.mOrientation = obtainStyledAttributes.getInt(R.styleable.TwoPanelViewGroup_orientation, 0);
        this.mDisplayMode = obtainStyledAttributes.getInt(R.styleable.TwoPanelViewGroup_displayMode, 0);
        this.mEnableSubWindow = obtainStyledAttributes.getBoolean(R.styleable.TwoPanelViewGroup_enableSubWindow, true);
        this.mFlipChildPos = obtainStyledAttributes.getBoolean(R.styleable.TwoPanelViewGroup_flipChildPos, false);
        float f = obtainStyledAttributes.getFloat(R.styleable.TwoPanelViewGroup_subWindowScale, 0.2f);
        this.mSubWindowScale = f;
        if (f <= 0.0f || f >= 1.0f) {
            this.mSubWindowScale = 0.2f;
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() >= 2) {
            throw new IllegalStateException("Can't add more than 2 views to a ViewSwitcher");
        }
        super.addView(view, i, layoutParams);
        int childCount = getChildCount();
        if (childCount > 0 && this.mChild1 == null) {
            this.mChild1 = getChildAt(0);
        }
        if (childCount <= 1 || this.mChild2 != null) {
            return;
        }
        this.mChild2 = getChildAt(1);
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view == this.mChild1) {
            this.mChild1 = null;
        } else if (view == this.mChild2) {
            this.mChild2 = null;
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(TwoPanelViewGroup.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(TwoPanelViewGroup.class.getName());
    }

    public void setOrientation(int i) {
        synchronized (this.mSync) {
            if (this.mOrientation != i % 2) {
                this.mOrientation = i % 2;
                startLayout();
            }
        }
    }

    public int getOrientation() {
        int i;
        synchronized (this.mSync) {
            i = this.mOrientation;
        }
        return i;
    }

    public void setEnableSubWindow(boolean z) {
        synchronized (this.mSync) {
            if (this.mEnableSubWindow != z) {
                this.mEnableSubWindow = z;
                startLayout();
            }
        }
    }

    public boolean getEnableSubWindow() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mEnableSubWindow;
        }
        return z;
    }

    public void setDisplayMode(int i) {
        synchronized (this.mSync) {
            if (this.mDisplayMode != i) {
                this.mDisplayMode = i;
                startLayout();
            }
        }
    }

    public int getDisplayMode() {
        int i;
        synchronized (this.mSync) {
            i = this.mDisplayMode;
        }
        return i;
    }

    public void setSubWindowScale(float f) {
        f = (f <= 0.0f || f >= 1.0f) ? 0.2f : 0.2f;
        synchronized (this.mSync) {
            if (f != this.mSubWindowScale) {
                this.mSubWindowScale = f;
                startLayout();
            }
        }
    }

    public float getSubWindowScale() {
        float f;
        synchronized (this.mSync) {
            f = this.mSubWindowScale;
        }
        return f;
    }

    public void setFlipChildPos(boolean z) {
        synchronized (this.mSync) {
            if (z != this.mFlipChildPos) {
                this.mFlipChildPos = z;
                startLayout();
            }
        }
    }

    public boolean getFlipChildPos() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mFlipChildPos;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0076, code lost:
        if (r3 != r20.mChild1) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007b, code lost:
        if (r0 != 3) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007f, code lost:
        if (r3 != r20.mChild2) goto L24;
     */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r21, int r22) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.widget.TwoPanelViewGroup.onMeasure(int, int):void");
    }

    private void onMeasureSplit(int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            onMeasureVertical(i, i2, i3, i4);
        } else {
            onMeasureHorizontal(i, i2, i3, i4);
        }
    }

    private void onMeasureSelect1(int i, int i2, int i3, int i4) {
        boolean z = this.mFlipChildPos;
        View view = z ? this.mChild2 : this.mChild1;
        View view2 = z ? this.mChild1 : this.mChild2;
        callChildMeasure(view, i, i2, i3, i4);
        if (this.mEnableSubWindow) {
            float f = this.mSubWindowScale;
            callChildMeasure(view2, (int) (i * f), (int) (i2 * f), i3, i4);
        }
    }

    private void onMeasureSelect2(int i, int i2, int i3, int i4) {
        boolean z = this.mFlipChildPos;
        View view = z ? this.mChild2 : this.mChild1;
        callChildMeasure(z ? this.mChild1 : this.mChild2, i, i2, i3, i4);
        if (this.mEnableSubWindow) {
            float f = this.mSubWindowScale;
            callChildMeasure(view, (int) (i * f), (int) (i2 * f), i3, i4);
        }
    }

    private void onMeasureHorizontal(int i, int i2, int i3, int i4) {
        boolean z = this.mFlipChildPos;
        View view = z ? this.mChild2 : this.mChild1;
        View view2 = z ? this.mChild1 : this.mChild2;
        int i5 = i >>> 1;
        callChildMeasure(view, i5, i2, i3, i4);
        callChildMeasure(view2, i5, i2, i3, i4);
    }

    private void onMeasureVertical(int i, int i2, int i3, int i4) {
        boolean z = this.mFlipChildPos;
        View view = z ? this.mChild2 : this.mChild1;
        View view2 = z ? this.mChild1 : this.mChild2;
        int i5 = i2 >>> 1;
        callChildMeasure(view, i, i5, i3, i4);
        callChildMeasure(view2, i, i5, i3, i4);
    }

    private void callChildMeasure(View view, int i, int i2, int i3, int i4) {
        int makeMeasureSpec;
        int makeMeasureSpec2;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (marginLayoutParams.width == -1) {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.min(i, (((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin), 1073741824);
        } else {
            int childMeasureSpec = getChildMeasureSpec(i3, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(childMeasureSpec), i), View.MeasureSpec.getMode(childMeasureSpec));
        }
        if (marginLayoutParams.height == -1) {
            makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min(i2, (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin), 1073741824);
        } else {
            int childMeasureSpec2 = getChildMeasureSpec(i4, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
            makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i2), View.MeasureSpec.getMode(childMeasureSpec2));
        }
        view.measure(makeMeasureSpec, makeMeasureSpec2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int paddingLeft = getPaddingLeft() + i;
        int paddingTop = i2 + getPaddingTop();
        int paddingRight = i3 - getPaddingRight();
        int paddingBottom = i4 - getPaddingBottom();
        int childCount = getChildCount();
        if (childCount == 1) {
            callChildLayout(this.mChild1, z, paddingLeft, paddingTop, paddingRight, paddingBottom);
        } else if (childCount > 0) {
            int i5 = this.mDisplayMode;
            if (i5 != 1) {
                if (i5 != 2) {
                    if (i5 != 3) {
                        if (i5 != 4) {
                            onLayoutSplit(z, paddingLeft, paddingTop, paddingRight, paddingBottom);
                            return;
                        }
                    }
                }
                onLayoutSelect2(z, paddingLeft, paddingTop, paddingRight, paddingBottom);
                return;
            }
            onLayoutSelect1(z, paddingLeft, paddingTop, paddingRight, paddingBottom);
        } else {
            super.onLayout(z, i, i2, i3, i4);
        }
    }

    private void onLayoutSplit(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            onLayoutVertical(z, i, i2, i3, i4);
        } else {
            onLayoutHorizontal(z, i, i2, i3, i4);
        }
    }

    private void onLayoutSelect1(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = this.mFlipChildPos;
        View view = z2 ? this.mChild2 : this.mChild1;
        View view2 = z2 ? this.mChild1 : this.mChild2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        callChildLayout(view, z, i - paddingLeft, i2 - paddingTop, i3 - paddingLeft, i4 - paddingTop);
        if (this.mEnableSubWindow) {
            int bottom = view.getBottom();
            int right = view.getRight();
            int measuredWidth = view2.getMeasuredWidth();
            int measuredHeight = view2.getMeasuredHeight();
            if (this.mOrientation == 1) {
                callChildLayout(view2, z, right - measuredWidth, bottom - measuredHeight, right, bottom);
            } else {
                callChildLayout(view2, z, right - measuredWidth, bottom - measuredHeight, right, bottom);
            }
        }
    }

    private void onLayoutSelect2(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = this.mFlipChildPos;
        View view = z2 ? this.mChild2 : this.mChild1;
        View view2 = z2 ? this.mChild1 : this.mChild2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        callChildLayout(view2, z, i - paddingLeft, i2 - paddingTop, i3 - paddingLeft, i4 - paddingTop);
        if (this.mEnableSubWindow) {
            int left = view2.getLeft();
            int top = view2.getTop();
            int right = view2.getRight();
            int bottom = view2.getBottom();
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            if (this.mOrientation == 1) {
                callChildLayout(view, z, right - measuredWidth, top, right, top + measuredHeight);
            } else {
                callChildLayout(view, z, left, bottom - measuredHeight, left + measuredWidth, bottom);
            }
        }
    }

    private void onLayoutHorizontal(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = this.mFlipChildPos;
        View view = z2 ? this.mChild2 : this.mChild1;
        View view2 = z2 ? this.mChild1 : this.mChild2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i5 = i - paddingLeft;
        int i6 = i2 - paddingTop;
        int i7 = i5 + ((i3 - i) >>> 1);
        int i8 = i4 - paddingTop;
        callChildLayout(view, z, i5, i6, i7, i8);
        callChildLayout(view2, z, i7, i6, i3 - paddingLeft, i8);
    }

    private void onLayoutVertical(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = this.mFlipChildPos;
        View view = z2 ? this.mChild2 : this.mChild1;
        View view2 = z2 ? this.mChild1 : this.mChild2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i5 = i - paddingLeft;
        int i6 = i2 - paddingTop;
        int i7 = i3 - paddingLeft;
        int i8 = i6 + ((i4 - i2) >>> 1);
        callChildLayout(view, z, i5, i6, i7, i8);
        callChildLayout(view2, z, i5, i8, i7, i4 - paddingTop);
    }

    private void callChildLayout(View view, boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i8 = layoutParams.gravity;
        if (i8 == -1) {
            i8 = 17;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i8, BuildCheck.isAndroid4_2() ? getLayoutDirection() : 0);
        int i9 = i8 & 112;
        int i10 = absoluteGravity & 7;
        if (i10 == 1) {
            i5 = ((i + (((i3 - i) - measuredWidth) / 2)) + layoutParams.leftMargin) - layoutParams.rightMargin;
        } else if (i10 == 5) {
            i5 = (i3 - measuredWidth) - layoutParams.rightMargin;
        } else {
            i5 = i + layoutParams.leftMargin;
        }
        if (i9 != 16) {
            if (i9 == 48) {
                i7 = layoutParams.topMargin;
            } else if (i9 == 80) {
                i6 = (i4 - measuredHeight) - layoutParams.bottomMargin;
            } else {
                i7 = layoutParams.topMargin;
            }
            i6 = i2 + i7;
        } else {
            i6 = ((i2 + (((i4 - i2) - measuredHeight) / 2)) + layoutParams.topMargin) - layoutParams.bottomMargin;
        }
        view.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
    }

    public void startLayout() {
        if (isInEditMode() || getChildCount() < 2) {
            requestLayout();
        }
        post(new Runnable() { // from class: com.serenegiant.widget.TwoPanelViewGroup.1
            @Override // java.lang.Runnable
            public void run() {
                TwoPanelViewGroup.this.startLayoutOnUI();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Finally extract failed */
    public void startLayoutOnUI() {
        boolean z = this.mFlipChildPos;
        View view = z ? this.mChild2 : this.mChild1;
        View view2 = z ? this.mChild1 : this.mChild2;
        try {
            int i = this.mDisplayMode;
            int i2 = 0;
            if (i != 0) {
                int i3 = 4;
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                            }
                        }
                    }
                    removeView(view2);
                    addView(view2, 0);
                    if (this.mEnableSubWindow && this.mDisplayMode != 4) {
                        i3 = 0;
                    }
                    view.setVisibility(i3);
                    view2.setVisibility(0);
                }
                removeView(view);
                addView(view, 0);
                view.setVisibility(0);
                if (!this.mEnableSubWindow || this.mDisplayMode == 3) {
                    i2 = 4;
                }
                view2.setVisibility(i2);
            } else {
                view.setVisibility(0);
                view2.setVisibility(0);
            }
            boolean z2 = this.mFlipChildPos;
            this.mChild1 = z2 ? view2 : view;
            if (!z2) {
                view = view2;
            }
            this.mChild2 = view;
            requestLayout();
        } catch (Throwable th) {
            boolean z3 = this.mFlipChildPos;
            this.mChild1 = z3 ? view2 : view;
            if (!z3) {
                view = view2;
            }
            this.mChild2 = view;
            throw th;
        }
    }

    private void cancelAnimation() {
        synchronized (this.mSync) {
            ObjectAnimator objectAnimator = this.mAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
                this.mAnimator = null;
            }
        }
    }
}
