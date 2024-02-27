package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.serenegiant.common.R;
import com.serenegiant.glutils.IMirror;
import com.serenegiant.view.ViewTransformDelegater;
import com.serenegiant.view.ViewUtils;

/* loaded from: classes2.dex */
public class ZoomAspectScaledTextureView extends AspectScaledTextureView implements IMirror {
    private static final boolean DEBUG = false;
    private static final String TAG = "ZoomAspectScaledTextureView";
    private final RectF mContentRect;
    private float mCurrentDegrees;
    protected final Matrix mDefaultMatrix;
    private int mHandleTouchEvent;
    protected boolean mImageMatrixChanged;
    private boolean mIsRotating;
    private final RectF mLimitRect;
    private final ViewUtils.LineSegment[] mLimitSegments;
    private float mManualRotate;
    private float mManualScale;
    protected final float[] mMatrixCache;
    protected final float mMaxScale;
    private float mMinScale;
    private int mMirrorMode;
    private float mPivotX;
    private float mPivotY;
    private int mPrimaryId;
    private float mPrimaryX;
    private float mPrimaryY;
    private final Matrix mSavedImageMatrix;
    private float mSecondX;
    private float mSecondY;
    private int mSecondaryId;
    private Runnable mStartCheckRotate;
    private int mState;
    private float mTouchDistance;
    private final float[] mTransCoords;
    private float mTransX;
    private float mTransY;
    private Runnable mWaitImageReset;

    protected RectF getContentBounds() {
        return null;
    }

    protected boolean handleOnTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    protected void onReset() {
    }

    public ZoomAspectScaledTextureView(Context context) {
        this(context, null, 0);
    }

    public ZoomAspectScaledTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomAspectScaledTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mManualScale = 1.0f;
        this.mManualRotate = Float.MAX_VALUE;
        this.mDefaultMatrix = new Matrix();
        this.mMatrixCache = new float[9];
        this.mSavedImageMatrix = new Matrix();
        this.mLimitRect = new RectF();
        this.mLimitSegments = new ViewUtils.LineSegment[4];
        this.mContentRect = new RectF();
        this.mTransCoords = new float[8];
        this.mMaxScale = 10.0f;
        this.mMinScale = 0.05f;
        this.mState = 0;
        this.mMirrorMode = 0;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ZoomAspectScaledTextureView, i, 0);
        try {
            try {
                this.mHandleTouchEvent = obtainStyledAttributes.getInteger(R.styleable.ZoomAspectScaledTextureView_handle_touch_event, 7);
            } catch (UnsupportedOperationException e) {
                String str = TAG;
                Log.d(str, str, e);
                this.mHandleTouchEvent = obtainStyledAttributes.getBoolean(R.styleable.ZoomAspectScaledTextureView_handle_touch_event, true) ? 7 : 0;
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0023, code lost:
        if (r0 != 6) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x002b, code lost:
        if (r0 != 2) goto L19;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.handleOnTouchEvent(r6)
            r1 = 1
            if (r0 == 0) goto L8
            return r1
        L8:
            int r0 = r5.mHandleTouchEvent
            if (r0 != 0) goto L11
            boolean r6 = super.onTouchEvent(r6)
            return r6
        L11:
            int r0 = r6.getActionMasked()
            if (r0 == 0) goto Lbb
            if (r0 == r1) goto L84
            r2 = 5
            r3 = 3
            r4 = 2
            if (r0 == r4) goto L3e
            if (r0 == r3) goto L84
            if (r0 == r2) goto L27
            r1 = 6
            if (r0 == r1) goto Lb2
            goto Lb6
        L27:
            int r0 = r5.mState
            if (r0 == r1) goto L2f
            if (r0 == r4) goto L34
            goto Lb6
        L2f:
            java.lang.Runnable r0 = r5.mWaitImageReset
            r5.removeCallbacks(r0)
        L34:
            int r0 = r6.getPointerCount()
            if (r0 <= r1) goto Lb6
            r5.startCheck(r6)
            return r1
        L3e:
            int r0 = r5.mState
            if (r0 == r1) goto L70
            if (r0 == r4) goto L69
            if (r0 == r3) goto L5a
            r3 = 4
            if (r0 == r3) goto L53
            if (r0 == r2) goto L4c
            goto Lb6
        L4c:
            boolean r0 = r5.processRotate(r6)
            if (r0 == 0) goto Lb6
            return r1
        L53:
            boolean r0 = r5.processZoom(r6)
            if (r0 == 0) goto Lb6
            return r1
        L5a:
            boolean r0 = r5.checkTouchMoved(r6)
            if (r0 == 0) goto Lb6
            int r0 = r5.mHandleTouchEvent
            r0 = r0 & r4
            if (r0 != r4) goto Lb6
            r5.startZoom(r6)
            return r1
        L69:
            boolean r0 = r5.processDrag(r6)
            if (r0 == 0) goto Lb6
            return r1
        L70:
            int r0 = r5.mHandleTouchEvent
            r0 = r0 & r1
            if (r0 != r1) goto Lb6
            boolean r0 = r5.checkTouchMoved(r6)
            if (r0 == 0) goto Lb6
            java.lang.Runnable r6 = r5.mWaitImageReset
            r5.removeCallbacks(r6)
            r5.setState(r4)
            return r1
        L84:
            java.lang.Runnable r2 = r5.mWaitImageReset
            r5.removeCallbacks(r2)
            java.lang.Runnable r2 = r5.mStartCheckRotate
            r5.removeCallbacks(r2)
            if (r0 != r1) goto Lb2
            int r0 = r5.mState
            if (r0 != r1) goto Lb2
            long r0 = android.os.SystemClock.uptimeMillis()
            long r2 = r6.getDownTime()
            long r0 = r0 - r2
            int r2 = com.serenegiant.view.ViewTransformDelegater.LONG_PRESS_TIMEOUT
            long r2 = (long) r2
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto La8
            r5.performLongClick()
            goto Lb2
        La8:
            int r2 = com.serenegiant.view.ViewTransformDelegater.TAP_TIMEOUT
            long r2 = (long) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto Lb2
            r5.performClick()
        Lb2:
            r0 = 0
            r5.setState(r0)
        Lb6:
            boolean r6 = super.onTouchEvent(r6)
            return r6
        Lbb:
            r5.startWaiting(r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.widget.ZoomAspectScaledTextureView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.serenegiant.widget.AspectScaledTextureView, android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        super.onSurfaceTextureAvailable(surfaceTexture, i, i2);
        setMirror(0);
    }

    @Override // com.serenegiant.widget.AspectScaledTextureView, android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        super.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
        applyMirrorMode();
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
        this.mHandleTouchEvent = i;
    }

    public void reset() {
        init();
        onReset();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.widget.AspectScaledTextureView
    public void init() {
        this.mState = -1;
        setState(0);
        this.mMinScale = 0.05f;
        this.mCurrentDegrees = 0.0f;
        this.mIsRotating = Math.abs((((float) ((int) (0.0f / 360.0f))) * 360.0f) - 0.0f) > 0.1f;
        Rect rect = new Rect();
        getDrawingRect(rect);
        this.mLimitRect.set(rect);
        RectF contentBounds = getContentBounds();
        if (contentBounds != null && !contentBounds.isEmpty()) {
            this.mContentRect.set(contentBounds);
        } else {
            this.mContentRect.set(this.mLimitRect);
        }
        this.mLimitRect.inset(getWidth() * 0.2f, getHeight() * 0.2f);
        this.mLimitSegments[0] = null;
        this.mTransY = 0.0f;
        this.mTransX = 0.0f;
        super.init();
        this.mDefaultMatrix.set(this.mImageMatrix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setState(int i) {
        if (this.mState != i) {
            this.mState = i;
            getTransform(this.mSavedImageMatrix);
            if (this.mImageMatrix.equals(this.mSavedImageMatrix)) {
                return;
            }
            this.mImageMatrix.set(this.mSavedImageMatrix);
            this.mImageMatrixChanged = true;
        }
    }

    private final void startWaiting(MotionEvent motionEvent) {
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
        postDelayed(this.mWaitImageReset, ViewTransformDelegater.CHECK_TIMEOUT);
        setState(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x019c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean processDrag(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 623
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.widget.ZoomAspectScaledTextureView.processDrag(android.view.MotionEvent):boolean");
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
                postDelayed(this.mStartCheckRotate, ViewTransformDelegater.CHECK_TIMEOUT);
            }
            setState(3);
        }
    }

    private final void startZoom(MotionEvent motionEvent) {
        removeCallbacks(this.mStartCheckRotate);
        setState(4);
    }

    private final boolean processZoom(MotionEvent motionEvent) {
        restoreMatrix();
        float matrixScale = getMatrixScale();
        float calcScale = calcScale(motionEvent);
        float f = matrixScale * calcScale;
        if (f >= this.mMinScale && f <= 10.0f) {
            if (this.mImageMatrix.postScale(calcScale, calcScale, this.mPivotX, this.mPivotY)) {
                this.mImageMatrixChanged = true;
                setTransform(this.mImageMatrix);
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
                this.mImageMatrixChanged = true;
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

    private final float getMatrixScale() {
        updateMatrixCache();
        float f = this.mMatrixCache[0];
        float min = Math.min(f, f);
        if (min <= 0.0f) {
            return 1.0f;
        }
        return min;
    }

    private final void restoreMatrix() {
        this.mImageMatrix.set(this.mSavedImageMatrix);
        this.mImageMatrixChanged = true;
    }

    private final boolean updateMatrixCache() {
        if (this.mImageMatrixChanged) {
            this.mImageMatrix.getValues(this.mMatrixCache);
            this.mImageMatrixChanged = false;
            return true;
        }
        return false;
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

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class WaitImageReset implements Runnable {
        private WaitImageReset() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ZoomAspectScaledTextureView.this.init();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class StartCheckRotate implements Runnable {
        private StartCheckRotate() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ZoomAspectScaledTextureView.this.mState == 3) {
                ZoomAspectScaledTextureView.this.setState(5);
            }
        }
    }

    public void setManualScale(float f) {
        setMatrix(this.mTransX, this.mTransY, f, this.mManualRotate);
    }

    public float getManualScale() {
        return this.mManualScale;
    }

    public PointF getTranslate(PointF pointF) {
        pointF.set(this.mManualScale, this.mTransX);
        return pointF;
    }

    public float getTranslateX() {
        return this.mTransX;
    }

    public float getTranslateY() {
        return this.mTransY;
    }

    public void setTranslate(float f, float f2) {
        setMatrix(f, f2, this.mManualScale, this.mManualRotate);
    }

    public void setRotate(float f) {
        setMatrix(this.mTransX, this.mTransY, this.mManualScale, f);
    }

    public float getRotate() {
        return this.mManualRotate;
    }

    public void setMatrix(float f, float f2, float f3) {
        setMatrix(f, f2, f3, Float.MAX_VALUE);
    }

    public void setMatrix(float f, float f2, float f3, float f4) {
        if (this.mTransX == f && this.mTransY == f2 && this.mManualScale == f3 && this.mCurrentDegrees == f4) {
            return;
        }
        if (f3 <= 0.0f) {
            f3 = this.mManualScale;
        }
        this.mManualScale = f3;
        this.mTransX = f;
        this.mTransY = f2;
        this.mManualRotate = f4;
        int i = (f4 > Float.MAX_VALUE ? 1 : (f4 == Float.MAX_VALUE ? 0 : -1));
        if (i != 0) {
            while (true) {
                float f5 = this.mManualRotate;
                if (f5 <= 360.0f) {
                    break;
                }
                this.mManualRotate = f5 - 360.0f;
            }
            while (true) {
                float f6 = this.mManualRotate;
                if (f6 >= -360.0f) {
                    break;
                }
                this.mManualRotate = f6 + 360.0f;
            }
        }
        float[] fArr = new float[9];
        this.mDefaultMatrix.getValues(fArr);
        this.mImageMatrix.reset();
        this.mImageMatrix.postTranslate(f, f2);
        Matrix matrix = this.mImageMatrix;
        float f7 = fArr[0];
        float f8 = this.mManualScale;
        float width = getWidth() >> 1;
        float height = getHeight() >> 1;
        matrix.postScale(f7 * f8, fArr[4] * f8, width, height);
        if (i != 0) {
            this.mImageMatrix.postRotate(this.mManualRotate, width, height);
        }
        this.mImageMatrixChanged = true;
        setTransform(this.mImageMatrix);
    }
}
