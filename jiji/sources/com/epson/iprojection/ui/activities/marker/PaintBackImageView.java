package com.epson.iprojection.ui.activities.marker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.ui.common.ResRect;

/* loaded from: classes.dex */
public class PaintBackImageView extends View {
    private Bitmap _bmp;
    private final Paint _paint;

    public PaintBackImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this._paint = paint;
        Bitmap bitmap = BmpHolder.ins().get();
        this._bmp = bitmap;
        if (bitmap == null) {
            Lg.e("PaintBackImageが読み込めませんでした");
        } else {
            paint.setFilterBitmap(true);
        }
    }

    public void destroy() {
        BmpHolder.ins().destroy();
        this._bmp = null;
    }

    public Bitmap getImageBitmap() {
        return this._bmp;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Bitmap bitmap;
        Rect drawRect = getDrawRect();
        if (canvas != null && drawRect != null && (bitmap = this._bmp) != null) {
            canvas.drawBitmap(bitmap, (Rect) null, drawRect, this._paint);
        } else {
            Lg.e("_bmp is null. skip onDraw.");
        }
    }

    private Rect getCenteredRectInView(Bitmap bitmap) {
        ResRect resRect = new ResRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        FitResolution.getRectFitWithIn(resRect, new ResRect(0, 0, getWidth(), getHeight()));
        return new Rect(resRect.x, resRect.y, resRect.x + resRect.w, resRect.y + resRect.h);
    }

    public Rect getDrawRect() {
        Bitmap bitmap = this._bmp;
        if (bitmap != null) {
            return getCenteredRectInView(bitmap);
        }
        return null;
    }
}
