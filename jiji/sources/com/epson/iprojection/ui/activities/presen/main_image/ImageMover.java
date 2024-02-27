package com.epson.iprojection.ui.activities.presen.main_image;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.common.utils.MathUtils;
import com.epson.iprojection.ui.activities.presen.main_image.EdgeInfo;
import com.epson.iprojection.ui.common.ResRect;

/* loaded from: classes.dex */
public class ImageMover {
    private static final float MAX_RATIO = 4.0f;
    private static final float MIN_RATIO = 1.0f;
    private final ImageFitter _fitter;
    private DblMatrix _matrix = new DblMatrix();
    private PointF _prePt = new PointF();
    private float _preDist = 0.0f;
    private float _curZoomRatio = 1.0f;
    private int _touchN = 0;
    private int _viewImgW = 0;
    private int _shadowImgW = 0;
    private int _shadowImgH = 0;
    private RectF _baseRect = null;
    private final EdgeInfo _edgeInfo = new EdgeInfo();

    public ImageMover(View view) {
        this._fitter = new ImageFitter(view);
    }

    public void initialize(ResRect resRect) {
        this._preDist = 0.0f;
        this._curZoomRatio = 1.0f;
        this._touchN = 0;
        this._matrix = new DblMatrix();
        centering(resRect);
        setViewImageSize(resRect.w, resRect.h);
    }

    private void centering(ResRect resRect) {
        this._fitter.centering(resRect);
        this._baseRect = new RectF(0.0f, 0.0f, resRect.w, resRect.h);
        this._matrix.scaleViewImage(resRect.w / this._shadowImgW);
    }

    public void setViewSize(int i, int i2) {
        this._fitter.setViewSize(i, i2);
    }

    private void setViewImageSize(int i, int i2) {
        this._fitter.setImageSize(i, i2);
        this._viewImgW = i;
    }

    public void setShadowImageSize(int i, int i2) {
        this._shadowImgW = i;
        this._shadowImgH = i2;
    }

    public Matrix getShadowImageMatrix() {
        return this._matrix.getBackMatrix();
    }

    public Matrix getViewImageMatrix() {
        return this._matrix.getFrontMatrix();
    }

    public boolean isSizeChanged() {
        return this._fitter.isSizeChanged();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0055, code lost:
        if (r7 != 6) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r9, android.view.MotionEvent r10) {
        /*
            r8 = this;
            int r0 = r10.getPointerCount()
            r1 = 0
            r2 = 0
            r3 = 1
            if (r0 < r3) goto L22
            float r0 = r10.getX(r1)
            com.epson.iprojection.ui.activities.presen.main_image.ImageFitter r4 = r8._fitter
            int r4 = r4.getSubW()
            float r4 = (float) r4
            float r0 = r0 - r4
            float r4 = r10.getY(r1)
            com.epson.iprojection.ui.activities.presen.main_image.ImageFitter r5 = r8._fitter
            int r5 = r5.getSubH()
            float r5 = (float) r5
            float r4 = r4 - r5
            goto L24
        L22:
            r0 = r2
            r4 = r0
        L24:
            int r5 = r10.getPointerCount()
            r6 = 2
            if (r5 < r6) goto L44
            float r2 = r10.getX(r3)
            com.epson.iprojection.ui.activities.presen.main_image.ImageFitter r5 = r8._fitter
            int r5 = r5.getSubW()
            float r5 = (float) r5
            float r2 = r2 - r5
            float r5 = r10.getY(r3)
            com.epson.iprojection.ui.activities.presen.main_image.ImageFitter r7 = r8._fitter
            int r7 = r7.getSubH()
            float r7 = (float) r7
            float r5 = r5 - r7
            goto L45
        L44:
            r5 = r2
        L45:
            int r7 = r10.getAction()
            r7 = r7 & 255(0xff, float:3.57E-43)
            if (r7 == 0) goto La7
            if (r7 == r3) goto La4
            if (r7 == r6) goto L70
            r6 = 5
            if (r7 == r6) goto L58
            r10 = 6
            if (r7 == r10) goto La4
            goto Lae
        L58:
            android.graphics.PointF r1 = r8._prePt
            r1.set(r0, r4)
            float r1 = com.epson.iprojection.common.utils.MathUtils.getDist(r0, r4, r2, r5)
            r8._preDist = r1
            android.graphics.PointF r0 = com.epson.iprojection.common.utils.MathUtils.getMidPoint(r0, r4, r2, r5)
            r8._prePt = r0
            int r10 = r10.getPointerCount()
            r8._touchN = r10
            goto Lae
        L70:
            int r10 = r8._touchN
            if (r10 == r3) goto L7c
            if (r10 == r6) goto L77
            return r1
        L77:
            android.graphics.PointF r10 = com.epson.iprojection.common.utils.MathUtils.getMidPoint(r0, r4, r2, r5)
            goto L81
        L7c:
            android.graphics.PointF r10 = new android.graphics.PointF
            r10.<init>(r0, r4)
        L81:
            float r1 = r10.x
            android.graphics.PointF r3 = r8._prePt
            float r3 = r3.x
            float r1 = r1 - r3
            float r3 = r10.y
            android.graphics.PointF r7 = r8._prePt
            float r7 = r7.y
            float r3 = r3 - r7
            int r7 = r8._touchN
            if (r7 != r6) goto L96
            r8.Zoom(r0, r4, r2, r5)
        L96:
            boolean r3 = r8.moveImage(r9, r1, r3)
            android.graphics.PointF r0 = r8._prePt
            float r1 = r10.x
            float r10 = r10.y
            r0.set(r1, r10)
            goto Lae
        La4:
            r8._touchN = r1
            goto Lae
        La7:
            android.graphics.PointF r10 = r8._prePt
            r10.set(r0, r4)
            r8._touchN = r3
        Lae:
            r9.invalidate()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.presen.main_image.ImageMover.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public boolean isExcludedExtendRatio() {
        float[] fArr = new float[9];
        this._matrix.getBackMatrix().getValues(fArr);
        return fArr[0] < 1.1f && fArr[4] < 1.1f;
    }

    public D_ExtendInfo getExtendInfo(int i, int i2, float f) {
        ResRect resRect;
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        float[] fArr = new float[9];
        this._matrix.getBackMatrix().getValues(fArr);
        if (isExcludedExtendRatio()) {
            return null;
        }
        int round = Math.round(this._shadowImgW * f);
        int round2 = Math.round(this._shadowImgH * f);
        FitResolution.getRectFitWithIn(new ResRect(0, 0, i, i2), new ResRect(0, 0, round, round2));
        double d6 = fArr[0];
        double d7 = fArr[4];
        double d8 = fArr[2] * f;
        double d9 = fArr[5] * f;
        double abs = (Math.abs(round - resRect.w) * d6) / 2.0d;
        double abs2 = (Math.abs(round2 - resRect.h) * d7) / 2.0d;
        double abs3 = Math.abs(d8) - abs;
        double abs4 = Math.abs(d9) - abs2;
        double d10 = abs3 > 0.0d ? 0.0d : abs3;
        double d11 = abs4 > 0.0d ? 0.0d : abs4;
        double d12 = abs3 < 0.0d ? 0.0d : abs3;
        if (abs4 < 0.0d) {
            d3 = abs2;
            d = d6;
            d2 = 0.0d;
        } else {
            d = d6;
            d2 = abs4;
            d3 = abs2;
        }
        double d13 = round;
        double d14 = d13 + d10 + d12;
        double d15 = round2;
        double d16 = d15 + d11 + d2;
        Rect rect = new Rect(0, 0, 0, 0);
        rect.left = Math.max(0, (int) (d8 + abs));
        rect.top = Math.max(0, (int) (d9 + d3));
        if (d14 < resRect.w * d) {
            rect.right = round;
            d4 = d14;
        } else {
            d4 = (int) (resRect.w * d);
            rect.right = ((int) (d4 - d12)) + rect.left;
        }
        if (d16 < resRect.h * d7) {
            rect.bottom = round2;
            d5 = d16;
        } else {
            d5 = (int) (resRect.h * d7);
            rect.bottom = ((int) (d5 - d2)) + rect.top;
        }
        if (d4 - d12 < 1.0d || d5 - d2 < 1.0d) {
            return null;
        }
        return new D_ExtendInfo(new Rect((int) d12, (int) d2, (int) d4, (int) d5), rect, getMetterScale(d13, d15, i, i2, d, d7));
    }

    private double getMetterScale(double d, double d2, int i, int i2, double d3, double d4) {
        return Math.min((d3 * d) / i, (d4 * d2) / i2);
    }

    private boolean moveImage(View view, float f, float f2) {
        boolean z;
        try {
            float[] fArr = new float[9];
            this._matrix.getFrontMatrix().getValues(fArr);
            float f3 = fArr[2];
            float f4 = fArr[5];
            RectF rectF = new RectF(f3, f4, (this._shadowImgW * fArr[0]) + f3, (this._shadowImgH * fArr[4]) + f4);
            float f5 = rectF.left + f;
            float f6 = rectF.top + f2;
            float f7 = rectF.right + f;
            float f8 = rectF.bottom + f2;
            this._edgeInfo.reset();
            if (this._baseRect.left < f5) {
                f -= f5 - this._baseRect.left;
                this._edgeInfo.set(EdgeInfo.eDirection.eLeft);
                z = false;
            } else {
                z = true;
            }
            if (this._baseRect.top < f6) {
                f2 -= f6 - this._baseRect.top;
                this._edgeInfo.set(EdgeInfo.eDirection.eTop);
                z = false;
            }
            if (f7 < this._baseRect.right) {
                f += this._baseRect.right - f7;
                this._edgeInfo.set(EdgeInfo.eDirection.eRight);
                z = false;
            }
            if (f8 < this._baseRect.bottom) {
                f2 += this._baseRect.bottom - f8;
                this._edgeInfo.set(EdgeInfo.eDirection.eBottom);
                z = false;
            }
            this._matrix.postTranslate(f, f2, this._shadowImgW / this._viewImgW);
            return z;
        } catch (NullPointerException e) {
            Lg.e("NullPointerException" + e);
            return false;
        }
    }

    private void Zoom(float f, float f2, float f3, float f4) {
        if (this._preDist == 0.0f) {
            return;
        }
        float dist = MathUtils.getDist(f, f2, f3, f4);
        float f5 = dist / this._preDist;
        float f6 = this._curZoomRatio * f5;
        PointF midPoint = MathUtils.getMidPoint(f, f2, f3, f4);
        this._preDist = dist;
        if (f6 < 1.0f) {
            f5 = 1.0f / this._curZoomRatio;
            f6 = 1.0f;
        }
        if (f6 > MAX_RATIO) {
            f5 = MAX_RATIO / this._curZoomRatio;
            f6 = 4.0f;
        }
        float f7 = f5;
        this._curZoomRatio = f6;
        this._matrix.postScale(f7, f7, midPoint.x, midPoint.y, this._shadowImgW / this._viewImgW);
    }

    public EdgeInfo getEdgeInfo() {
        return this._edgeInfo;
    }

    public float getZoomRatio() {
        return this._curZoomRatio;
    }
}
