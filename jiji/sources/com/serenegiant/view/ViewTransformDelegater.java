package com.serenegiant.view;

import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.serenegiant.view.ViewUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public abstract class ViewTransformDelegater extends ViewTransformer {
    private static final boolean DEBUG = false;
    public static final float DEFAULT_MAX_SCALE = 10.0f;
    public static final float DEFAULT_MIN_SCALE = 0.05f;
    public static final float DEFAULT_SCALE = 1.0f;
    public static final float MIN_DISTANCE = 15.0f;
    public static final float MIN_DISTANCE_SQUARE = 225.0f;
    public static final float MOVE_LIMIT_RATE = 0.2f;
    public static final int STATE_CHECKING = 3;
    public static final int STATE_DRAGGING = 2;
    public static final int STATE_NON = 0;
    public static final int STATE_RESET = -1;
    public static final int STATE_ROTATING = 5;
    public static final int STATE_WAITING = 1;
    public static final int STATE_ZOOMING = 4;
    private static final String TAG = "ViewTransformDelegater";
    public static final int TOUCH_DISABLED = 0;
    public static final int TOUCH_ENABLED_ALL = 7;
    public static final int TOUCH_ENABLED_MOVE = 1;
    public static final int TOUCH_ENABLED_ROTATE = 4;
    public static final int TOUCH_ENABLED_ZOOM = 2;
    private final RectF mContentRect;
    private float mCurrentDegrees;
    private int mHandleTouchEvent;
    protected final Matrix mImageMatrix;
    private boolean mIsRestored;
    private boolean mIsRotating;
    private final RectF mLimitRect;
    private final ViewUtils.LineSegment[] mLimitSegments;
    private float mMaxScale;
    private float mMinScale;
    private boolean mNeedResizeToKeepAspect;
    private float mPivotX;
    private float mPivotY;
    private int mPrimaryId;
    private float mPrimaryX;
    private float mPrimaryY;
    private final Matrix mSavedImageMatrix;
    private int mScaleMode;
    private float mSecondX;
    private float mSecondY;
    private int mSecondaryId;
    private Runnable mStartCheckRotate;
    private int mState;
    private float mTouchDistance;
    private final float[] mTransCoords;
    private ViewTransformListener mViewTransformListener;
    private Runnable mWaitImageReset;
    private Runnable mWaitReverseReset;
    public static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout() * 2;
    public static final int LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    public static final int CHECK_TIMEOUT = ViewConfiguration.getTapTimeout() + ViewConfiguration.getLongPressTimeout();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface State {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface TouchMode {
    }

    /* loaded from: classes2.dex */
    public interface ViewTransformListener {
        void onStateChanged(View view, int i);

        void onTransformed(View view, Matrix matrix);
    }

    protected abstract RectF getContentBounds();

    protected abstract void onInit();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class WaitImageReset implements Runnable {
        private WaitImageReset() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewTransformDelegater.this.getTargetView().requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class StartCheckRotate implements Runnable {
        private StartCheckRotate() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ViewTransformDelegater.this.mState == 3) {
                ViewTransformDelegater.this.setState(5);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.serenegiant.view.ViewTransformDelegater.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private float mCurrentDegrees;
        private int mHandleTouchEvent;
        private final float[] mMatrixCache;
        private float mMaxScale;
        private float mMinScale;
        private int mState;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mMatrixCache = new float[9];
            readFromParcel(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
            this.mMatrixCache = new float[9];
        }

        private void readFromParcel(Parcel parcel) {
            this.mState = parcel.readInt();
            this.mHandleTouchEvent = parcel.readInt();
            this.mMinScale = parcel.readFloat();
            this.mMaxScale = parcel.readFloat();
            this.mCurrentDegrees = parcel.readFloat();
            parcel.readFloatArray(this.mMatrixCache);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mState);
            parcel.writeInt(this.mHandleTouchEvent);
            parcel.writeFloat(this.mMinScale);
            parcel.writeFloat(this.mMaxScale);
            parcel.writeFloat(this.mCurrentDegrees);
            parcel.writeFloatArray(this.mMatrixCache);
        }
    }

    public ViewTransformDelegater(View view) {
        super(view);
        this.mHandleTouchEvent = 7;
        this.mImageMatrix = new Matrix();
        this.mSavedImageMatrix = new Matrix();
        this.mLimitRect = new RectF();
        this.mLimitSegments = new ViewUtils.LineSegment[4];
        this.mContentRect = new RectF();
        this.mTransCoords = new float[8];
        this.mMaxScale = 10.0f;
        this.mMinScale = 0.05f;
        this.mState = 0;
        if (view instanceof ViewTransformListener) {
            this.mViewTransformListener = (ViewTransformListener) view;
        }
    }

    @Override // com.serenegiant.view.ViewTransformer, com.serenegiant.view.IViewTransformer
    public ViewTransformer setTransform(Matrix matrix) {
        super.setTransform(matrix);
        ViewTransformListener viewTransformListener = this.mViewTransformListener;
        if (viewTransformListener != null) {
            viewTransformListener.onTransformed(getTargetView(), matrix);
        }
        return this;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mIsRestored = true;
            this.mImageMatrix.setValues(savedState.mMatrixCache);
            this.mState = savedState.mState;
            this.mHandleTouchEvent = savedState.mHandleTouchEvent;
            this.mMinScale = savedState.mMinScale;
            this.mMaxScale = savedState.mMaxScale;
            this.mCurrentDegrees = savedState.mCurrentDegrees;
        }
    }

    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        SavedState savedState = new SavedState(parcelable);
        savedState.mState = this.mState;
        savedState.mHandleTouchEvent = this.mHandleTouchEvent;
        savedState.mMinScale = this.mMinScale;
        savedState.mMaxScale = this.mMaxScale;
        savedState.mCurrentDegrees = this.mCurrentDegrees;
        this.mImageMatrix.getValues(savedState.mMatrixCache);
        return savedState;
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.mIsRestored = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001d, code lost:
        if (r2 != 6) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0025, code lost:
        if (r2 != 2) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            int r0 = r7.mHandleTouchEvent
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            android.view.View r0 = r7.getTargetView()
            int r2 = r8.getActionMasked()
            r3 = 1
            if (r2 == 0) goto Lb0
            if (r2 == r3) goto L7e
            r4 = 5
            r5 = 3
            r6 = 2
            if (r2 == r6) goto L38
            if (r2 == r5) goto L7e
            if (r2 == r4) goto L21
            r8 = 6
            if (r2 == r8) goto Lac
            goto Laf
        L21:
            int r2 = r7.mState
            if (r2 == r3) goto L29
            if (r2 == r6) goto L2e
            goto Laf
        L29:
            java.lang.Runnable r2 = r7.mWaitImageReset
            r0.removeCallbacks(r2)
        L2e:
            int r0 = r8.getPointerCount()
            if (r0 <= r3) goto Laf
            r7.startCheck(r8)
            return r3
        L38:
            int r2 = r7.mState
            if (r2 == r3) goto L6a
            if (r2 == r6) goto L63
            if (r2 == r5) goto L54
            r0 = 4
            if (r2 == r0) goto L4d
            if (r2 == r4) goto L46
            goto Laf
        L46:
            boolean r8 = r7.processRotate(r8)
            if (r8 == 0) goto Laf
            return r3
        L4d:
            boolean r8 = r7.processZoom(r8)
            if (r8 == 0) goto Laf
            return r3
        L54:
            boolean r0 = r7.checkTouchMoved(r8)
            if (r0 == 0) goto Laf
            int r0 = r7.mHandleTouchEvent
            r0 = r0 & r6
            if (r0 != r6) goto Laf
            r7.startZoom(r8)
            return r3
        L63:
            boolean r8 = r7.processDrag(r8)
            if (r8 == 0) goto Laf
            return r3
        L6a:
            int r2 = r7.mHandleTouchEvent
            r2 = r2 & r3
            if (r2 != r3) goto Laf
            boolean r8 = r7.checkTouchMoved(r8)
            if (r8 == 0) goto Laf
            java.lang.Runnable r8 = r7.mWaitImageReset
            r0.removeCallbacks(r8)
            r7.setState(r6)
            return r3
        L7e:
            java.lang.Runnable r4 = r7.mWaitImageReset
            r0.removeCallbacks(r4)
            java.lang.Runnable r4 = r7.mStartCheckRotate
            r0.removeCallbacks(r4)
            if (r2 != r3) goto Lac
            int r2 = r7.mState
            if (r2 != r3) goto Lac
            long r2 = android.os.SystemClock.uptimeMillis()
            long r4 = r8.getDownTime()
            long r2 = r2 - r4
            int r8 = com.serenegiant.view.ViewTransformDelegater.LONG_PRESS_TIMEOUT
            long r4 = (long) r8
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 <= 0) goto La2
            r0.performLongClick()
            goto Lac
        La2:
            int r8 = com.serenegiant.view.ViewTransformDelegater.TAP_TIMEOUT
            long r4 = (long) r8
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 >= 0) goto Lac
            r0.performClick()
        Lac:
            r7.setState(r1)
        Laf:
            return r1
        Lb0:
            r7.startWaiting(r8)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.view.ViewTransformDelegater.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setViewTransformListener(ViewTransformListener viewTransformListener) {
        this.mViewTransformListener = viewTransformListener;
    }

    public ViewTransformListener getViewTransformListener() {
        return this.mViewTransformListener;
    }

    public void setEnableHandleTouchEvent(int i) {
        this.mHandleTouchEvent = i;
    }

    public int getEnableHandleTouchEvent() {
        return this.mHandleTouchEvent;
    }

    public void setMaxScale(float f) throws IllegalArgumentException {
        if (this.mMinScale > f || f <= 0.0f) {
            throw new IllegalArgumentException();
        }
        if (this.mMaxScale != f) {
            this.mMaxScale = f;
            checkScale();
        }
    }

    public float getMaxScale() {
        return this.mMaxScale;
    }

    public void setMinScale(float f) throws IllegalArgumentException {
        if (this.mMaxScale < f || f <= 0.0f) {
            throw new IllegalArgumentException();
        }
        if (this.mMinScale != f) {
            this.mMinScale = f;
            checkScale();
        }
    }

    public float getMinScale() {
        return this.mMinScale;
    }

    public ViewTransformDelegater setScaleRelative(float f) {
        getTransform(this.mImageMatrix);
        float scale = getScale() * f;
        if (scale >= this.mMinScale && scale <= this.mMaxScale && this.mImageMatrix.postScale(f, f, getViewWidth() / 2.0f, getViewHeight() / 2.0f)) {
            setTransform(this.mImageMatrix);
        }
        return this;
    }

    public void setScaleMode(int i) {
        if (this.mScaleMode != i) {
            this.mScaleMode = i;
            getTargetView().requestLayout();
        }
    }

    public int getScaleMode() {
        return this.mScaleMode;
    }

    public void setKeepAspect(boolean z) {
        if (this.mNeedResizeToKeepAspect != z) {
            this.mNeedResizeToKeepAspect = z;
            getTargetView().requestLayout();
        }
    }

    public void init() {
        this.mState = -1;
        clearPendingTasks();
        if (!this.mIsRestored) {
            onInit();
            updateTransform(true);
            this.mCurrentDegrees = 0.0f;
        }
        this.mIsRestored = false;
        float f = this.mCurrentDegrees;
        this.mIsRotating = Math.abs((((float) ((int) (f / 360.0f))) * 360.0f) - f) > 0.1f;
        this.mLimitRect.set(getDrawingRect());
        RectF contentBounds = getContentBounds();
        if (contentBounds != null && !contentBounds.isEmpty()) {
            this.mContentRect.set(contentBounds);
        } else {
            this.mContentRect.set(this.mLimitRect);
        }
        this.mLimitRect.inset(getViewWidth() * 0.2f, getViewHeight() * 0.2f);
        this.mLimitSegments[0] = null;
        setupDefaultTransform();
        setState(0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0027, code lost:
        if (r6 != 2) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setupDefaultTransform() {
        /*
            r9 = this;
            int r0 = r9.getViewWidth()
            int r1 = r9.getViewHeight()
            android.graphics.RectF r2 = r9.mContentRect
            float r2 = r2.width()
            android.graphics.RectF r3 = r9.mContentRect
            float r3 = r3.height()
            float r0 = (float) r0
            float r4 = r0 / r2
            float r1 = (float) r1
            float r5 = r1 / r3
            android.graphics.Matrix r6 = r9.mImageMatrix
            r6.reset()
            int r6 = r9.mScaleMode
            r7 = 2
            if (r6 == 0) goto L30
            r8 = 1
            if (r6 == r8) goto L2a
            if (r6 == r7) goto L30
            goto L57
        L2a:
            android.graphics.Matrix r0 = r9.mImageMatrix
            r0.setScale(r4, r5)
            goto L57
        L30:
            if (r6 != r7) goto L37
            float r4 = java.lang.Math.max(r4, r5)
            goto L3b
        L37:
            float r4 = java.lang.Math.min(r4, r5)
        L3b:
            float r2 = r2 * r4
            float r0 = r0 - r2
            r2 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 * r2
            int r0 = java.lang.Math.round(r0)
            float r0 = (float) r0
            float r3 = r3 * r4
            float r1 = r1 - r3
            float r1 = r1 * r2
            int r1 = java.lang.Math.round(r1)
            float r1 = (float) r1
            android.graphics.Matrix r2 = r9.mImageMatrix
            r2.setScale(r4, r4)
            android.graphics.Matrix r2 = r9.mImageMatrix
            r2.postTranslate(r0, r1)
        L57:
            android.graphics.Matrix r0 = r9.mImageMatrix
            r9.setTransform(r0)
            android.graphics.Matrix r0 = r9.mImageMatrix
            r9.setDefault(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.view.ViewTransformDelegater.setupDefaultTransform():void");
    }

    public void clearPendingTasks() {
        View targetView = getTargetView();
        Runnable runnable = this.mWaitImageReset;
        if (runnable != null) {
            targetView.removeCallbacks(runnable);
        }
        Runnable runnable2 = this.mStartCheckRotate;
        if (runnable2 != null) {
            targetView.removeCallbacks(runnable2);
        }
        Runnable runnable3 = this.mWaitReverseReset;
        if (runnable3 != null) {
            targetView.removeCallbacks(runnable3);
        }
    }

    private void checkScale() {
        float scale = getScale();
        float f = this.mMinScale;
        if (scale < f) {
            getTransform(this.mImageMatrix);
            this.mImageMatrix.setScale(f, f);
            setTransform(this.mImageMatrix);
            return;
        }
        float f2 = this.mMaxScale;
        if (scale > f2) {
            getTransform(this.mImageMatrix);
            this.mImageMatrix.setScale(f2, f2);
            setTransform(this.mImageMatrix);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int i) {
        if (this.mState != i) {
            this.mState = i;
            getTransform(this.mSavedImageMatrix);
            this.mImageMatrix.set(this.mSavedImageMatrix);
            ViewTransformListener viewTransformListener = this.mViewTransformListener;
            if (viewTransformListener != null) {
                viewTransformListener.onStateChanged(getTargetView(), i);
            }
        }
    }

    private void startWaiting(MotionEvent motionEvent) {
        this.mPrimaryId = 0;
        this.mSecondaryId = -1;
        float x = motionEvent.getX();
        this.mSecondX = x;
        this.mPrimaryX = x;
        float y = motionEvent.getY();
        this.mSecondY = y;
        this.mPrimaryY = y;
        if (this.mWaitImageReset == null) {
            this.mWaitImageReset = new WaitImageReset();
        }
        getTargetView().postDelayed(this.mWaitImageReset, CHECK_TIMEOUT);
        setState(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x019c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean processDrag(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 611
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.view.ViewTransformDelegater.processDrag(android.view.MotionEvent):boolean");
    }

    private final void startCheck(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            this.mPrimaryId = motionEvent.getPointerId(0);
            this.mPrimaryX = motionEvent.getX(0);
            this.mPrimaryY = motionEvent.getY(0);
            this.mSecondaryId = motionEvent.getPointerId(1);
            this.mSecondX = motionEvent.getX(1);
            float y = motionEvent.getY(1);
            this.mSecondY = y;
            float hypot = (float) Math.hypot(this.mSecondX - this.mPrimaryX, y - this.mPrimaryY);
            if (hypot < 15.0f) {
                return;
            }
            this.mTouchDistance = hypot;
            this.mPivotX = (this.mPrimaryX + this.mSecondX) / 2.0f;
            this.mPivotY = (this.mPrimaryY + this.mSecondY) / 2.0f;
            if ((this.mHandleTouchEvent & 4) == 4) {
                if (this.mStartCheckRotate == null) {
                    this.mStartCheckRotate = new StartCheckRotate();
                }
                getTargetView().postDelayed(this.mStartCheckRotate, CHECK_TIMEOUT);
            }
            setState(3);
        }
    }

    private final void startZoom(MotionEvent motionEvent) {
        getTargetView().removeCallbacks(this.mStartCheckRotate);
        setState(4);
    }

    private final boolean processZoom(MotionEvent motionEvent) {
        restoreMatrix();
        float calcScale = calcScale(motionEvent);
        float scale = getScale() * calcScale;
        if (scale >= this.mMinScale && scale <= this.mMaxScale) {
            if (this.mImageMatrix.postScale(calcScale, calcScale, this.mPivotX, this.mPivotY)) {
                setTransform(this.mImageMatrix);
                return true;
            }
            return true;
        }
        return false;
    }

    private final float calcScale(MotionEvent motionEvent) {
        return ((float) Math.hypot(motionEvent.getX(0) - motionEvent.getX(1), motionEvent.getY(0) - motionEvent.getY(1))) / this.mTouchDistance;
    }

    private final boolean checkTouchMoved(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mPrimaryId);
        int findPointerIndex2 = motionEvent.findPointerIndex(this.mSecondaryId);
        if (findPointerIndex >= 0) {
            float x = motionEvent.getX(findPointerIndex) - this.mPrimaryX;
            float y = motionEvent.getY(findPointerIndex) - this.mPrimaryY;
            if ((x * x) + (y * y) < 225.0f) {
                if (findPointerIndex2 >= 0) {
                    float x2 = motionEvent.getX(findPointerIndex2) - this.mSecondX;
                    float y2 = motionEvent.getY(findPointerIndex2) - this.mSecondY;
                    if ((x2 * x2) + (y2 * y2) >= 225.0f) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private final boolean processRotate(MotionEvent motionEvent) {
        if (checkTouchMoved(motionEvent)) {
            restoreMatrix();
            float calcAngle = calcAngle(motionEvent);
            this.mCurrentDegrees = calcAngle;
            boolean z = Math.abs((((float) ((int) (calcAngle / 360.0f))) * 360.0f) - calcAngle) > 0.1f;
            this.mIsRotating = z;
            if (z && this.mImageMatrix.postRotate(this.mCurrentDegrees, this.mPivotX, this.mPivotY)) {
                setTransform(this.mImageMatrix);
                return true;
            }
        }
        return false;
    }

    private final float calcAngle(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mPrimaryId);
        int findPointerIndex2 = motionEvent.findPointerIndex(this.mSecondaryId);
        if (findPointerIndex < 0 || findPointerIndex2 < 0) {
            return 0.0f;
        }
        float f = this.mSecondX - this.mPrimaryX;
        float f2 = this.mSecondY - this.mPrimaryY;
        float x = motionEvent.getX(findPointerIndex2) - motionEvent.getX(findPointerIndex);
        float y = motionEvent.getY(findPointerIndex2) - motionEvent.getY(findPointerIndex);
        return ((float) Math.acos(ViewUtils.dotProduct(f, f2, x, y) / Math.sqrt(((f * f) + (f2 * f2)) * ((x * x) + (y * y))))) * 57.29578f * Math.signum(ViewUtils.crossProduct(f, f2, x, y));
    }

    protected void restoreMatrix() {
        this.mImageMatrix.set(this.mSavedImageMatrix);
    }

    private Rect getDrawingRect() {
        Rect rect = new Rect();
        getTargetView().getDrawingRect(rect);
        return rect;
    }

    private int getViewWidth() {
        return getTargetView().getWidth();
    }

    private int getViewHeight() {
        return getTargetView().getHeight();
    }
}
