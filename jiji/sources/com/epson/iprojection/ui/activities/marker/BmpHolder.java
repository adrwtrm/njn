package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class BmpHolder {
    private static final BmpHolder _inst = new BmpHolder();
    private Bitmap _bmp = null;

    public void set(Bitmap bitmap) {
        this._bmp = bitmap;
    }

    public Bitmap get() {
        return this._bmp;
    }

    public void destroy() {
        this._bmp = null;
    }

    private BmpHolder() {
    }

    public static BmpHolder ins() {
        return _inst;
    }
}
