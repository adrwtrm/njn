package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PorterDuff;

/* loaded from: classes.dex */
public class Eraser extends BaseToolAdapter {
    private static final float PI = 3.1415927f;
    private final Path _path = new Path();

    @Override // com.epson.iprojection.ui.activities.marker.BaseToolAdapter, com.epson.iprojection.ui.activities.marker.IBaseTool
    public void onFingerDown(float f, float f2, Bitmap bitmap) {
        DeleteLine(bitmap, f, f2, f, f2);
        this._pt.x = f;
        this._pt.y = f2;
    }

    @Override // com.epson.iprojection.ui.activities.marker.BaseToolAdapter, com.epson.iprojection.ui.activities.marker.IBaseTool
    public void onFingerMove(float f, float f2, Bitmap bitmap) {
        if (this._pt.x == -1.0f || this._pt.y == -1.0f) {
            return;
        }
        DeleteLine(bitmap, this._pt.x, this._pt.y, f, f2);
        this._pt.x = f;
        this._pt.y = f2;
    }

    private void DeleteLine(Bitmap bitmap, float f, float f2, float f3, float f4) {
        float strokeWidth = this._paint.getStrokeWidth() / 2.0f;
        this._path.reset();
        this._path.addCircle(f, f2, this._paint.getStrokeWidth() / 2.0f, Path.Direction.CW);
        if (f != f3 || f2 != f4) {
            this._path.addCircle(f3, f4, this._paint.getStrokeWidth() / 2.0f, Path.Direction.CW);
            double atan2 = Math.atan2(f4 - f2, f3 - f);
            double d = f;
            double d2 = atan2 + 1.5707963705062866d;
            double d3 = strokeWidth;
            double d4 = f2;
            double d5 = atan2 - 1.5707963705062866d;
            double d6 = f3;
            double d7 = f4;
            double[] dArr = {d + (Math.cos(d2) * d3), d + (Math.cos(d5) * d3), (Math.cos(d5) * d3) + d6, d6 + (Math.cos(d2) * d3)};
            double[] dArr2 = {d4 + (Math.sin(d2) * d3), d4 + (Math.sin(d5) * d3), (Math.sin(d5) * d3) + d7, d7 + (Math.sin(d2) * d3)};
            this._path.moveTo((float) dArr[0], (float) dArr2[0]);
            this._path.lineTo((float) dArr[1], (float) dArr2[1]);
            this._path.lineTo((float) dArr[2], (float) dArr2[2]);
            this._path.lineTo((float) dArr[3], (float) dArr2[3]);
        }
        this._canvas.setBitmap(bitmap);
        this._canvas.save();
        this._canvas.clipPath(this._path);
        this._canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        this._canvas.restore();
    }
}
