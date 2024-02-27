package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public class BaseToolAdapter implements IBaseTool {
    protected static final int ERR = -1;
    protected final PointF _pt = new PointF();
    protected final Canvas _canvas = new Canvas();
    protected final Paint _paint = new Paint();

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public void onFingerDown(float f, float f2, Bitmap bitmap) {
    }

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public void onFingerMove(float f, float f2, Bitmap bitmap) {
    }

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public void setColor(int i) {
        this._paint.setColor(i);
    }

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public void setWidth(int i) {
        this._paint.setStrokeWidth(i);
    }

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public void setAlpha(int i) {
        Lg.d("alpha = " + i);
        this._paint.setAlpha(i);
    }

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public int getColor() {
        return this._paint.getColor();
    }

    @Override // com.epson.iprojection.ui.activities.marker.IBaseTool
    public void clear() {
        this._pt.x = -1.0f;
        this._pt.y = -1.0f;
    }
}
