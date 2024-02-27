package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public class AlphaLayer {
    private Bitmap _bitmap = null;
    private final Paint _paint = new Paint();
    private int _w = 0;
    private int _h = 0;

    public void destroy() {
        delete();
    }

    public void setAlpha(int i) throws BitmapMemoryException {
        this._paint.setAlpha(i);
        if (isEnabled()) {
            if (this._bitmap == null) {
                create();
            }
        } else if (this._bitmap != null) {
            delete();
        }
    }

    public boolean isEnabled() {
        int alpha = this._paint.getAlpha();
        return (alpha == 255 || alpha == 0) ? false : true;
    }

    public final Bitmap getBitmap() {
        return this._bitmap;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this._bitmap;
        if (bitmap == null) {
            return;
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this._paint);
    }

    public void draw(Bitmap bitmap) {
        if (this._bitmap == null) {
            return;
        }
        new Canvas(bitmap).drawBitmap(this._bitmap, 0.0f, 0.0f, this._paint);
    }

    public void clear() {
        Bitmap bitmap = this._bitmap;
        if (bitmap != null) {
            bitmap.eraseColor(Color.argb(0, 0, 0, 0));
        }
    }

    public void changeSize(int i, int i2) throws BitmapMemoryException {
        this._bitmap = null;
        this._w = i;
        this._h = i2;
        if (isEnabled()) {
            create();
        }
    }

    private void create() throws BitmapMemoryException {
        int i;
        int i2 = this._w;
        if (i2 == 0 || (i = this._h) == 0) {
            return;
        }
        this._bitmap = BitmapUtils.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
    }

    private void delete() {
        this._bitmap = null;
    }
}
