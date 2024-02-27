package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;
import android.graphics.Paint;

/* loaded from: classes.dex */
public class Pen extends BaseToolAdapter {
    public Pen() {
        this._paint.setColor(-65536);
        this._paint.setAntiAlias(true);
        this._paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // com.epson.iprojection.ui.activities.marker.BaseToolAdapter, com.epson.iprojection.ui.activities.marker.IBaseTool
    public void onFingerDown(float f, float f2, Bitmap bitmap) {
        this._canvas.setBitmap(bitmap);
        this._canvas.drawCircle(f, f2, this._paint.getStrokeWidth() / 2.0f, this._paint);
        this._pt.x = f;
        this._pt.y = f2;
    }

    @Override // com.epson.iprojection.ui.activities.marker.BaseToolAdapter, com.epson.iprojection.ui.activities.marker.IBaseTool
    public void onFingerMove(float f, float f2, Bitmap bitmap) {
        if (this._pt.x == -1.0f || this._pt.y == -1.0f) {
            return;
        }
        this._canvas.setBitmap(bitmap);
        this._canvas.drawLine(this._pt.x, this._pt.y, f, f2, this._paint);
        this._pt.x = f;
        this._pt.y = f2;
    }
}
