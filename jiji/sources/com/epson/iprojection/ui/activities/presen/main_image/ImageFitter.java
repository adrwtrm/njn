package com.epson.iprojection.ui.activities.presen.main_image;

import android.view.View;
import com.epson.iprojection.ui.common.ResRect;

/* loaded from: classes.dex */
public class ImageFitter {
    private final View _view;
    private int _imgW = 0;
    private int _imgH = 0;
    private int _viewW = 0;
    private int _viewH = 0;

    public ImageFitter(View view) {
        this._view = view;
    }

    public void centering(ResRect resRect) {
        this._view.scrollTo(-((this._viewW - resRect.w) / 2), -((this._viewH - resRect.h) / 2));
        this._imgW = resRect.w;
        this._imgH = resRect.h;
    }

    public int getSubW() {
        return (this._viewW - this._imgW) / 2;
    }

    public int getSubH() {
        return (this._viewH - this._imgH) / 2;
    }

    public void setViewSize(int i, int i2) {
        this._viewW = i;
        this._viewH = i2;
    }

    public void setImageSize(int i, int i2) {
        this._imgW = i;
        this._imgH = i2;
    }

    public boolean isSizeChanged() {
        return (this._imgW == this._viewW && this._imgH == this._viewH) ? false : true;
    }
}
