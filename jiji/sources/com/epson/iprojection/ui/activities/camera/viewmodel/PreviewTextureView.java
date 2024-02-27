package com.epson.iprojection.ui.activities.camera.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public class PreviewTextureView extends TextureView {
    private int _defaultRotation;
    private boolean _isActivated;
    private int _ratioHeight;
    private int _ratioWidth;
    private int _totalRotation;

    public PreviewTextureView(Context context) {
        this(context, null);
    }

    public PreviewTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreviewTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._ratioWidth = 0;
        this._ratioHeight = 0;
        this._isActivated = false;
        this._defaultRotation = 0;
    }

    public void onActivityCreated(Activity activity, int i) {
        this._defaultRotation = i;
        this._isActivated = true;
    }

    @Override // android.view.View
    public boolean isActivated() {
        return this._isActivated;
    }

    public synchronized void setDefaultOrientation(int i) {
        this._defaultRotation = i;
    }

    public Bitmap getPauseBitmap() {
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            try {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), getTransform(null), true);
            } catch (IllegalArgumentException e) {
                Lg.e("Bitmap create error:" + e);
                return null;
            }
        }
        return bitmap;
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
