package com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

/* loaded from: classes.dex */
public class QrTextureView extends TextureView {
    private int _ratioHeight;
    private int _ratioWidth;

    public QrTextureView(Context context) {
        this(context, null);
    }

    public QrTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QrTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._ratioWidth = 0;
        this._ratioHeight = 0;
    }

    public void setAspectRatio(int i, int i2) {
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        this._ratioWidth = i;
        this._ratioHeight = i2;
        requestLayout();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i4 = this._ratioWidth;
        if (i4 == 0 || (i3 = this._ratioHeight) == 0) {
            setMeasuredDimension(size, size2);
        } else if (size < (size2 * i4) / i3) {
            setMeasuredDimension(size, (i3 * size) / i4);
        } else {
            setMeasuredDimension((i4 * size2) / i3, size2);
        }
    }
}
