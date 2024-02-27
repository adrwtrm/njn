package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public interface IBaseTool {
    void clear();

    int getColor();

    void onFingerDown(float f, float f2, Bitmap bitmap);

    void onFingerMove(float f, float f2, Bitmap bitmap);

    void setAlpha(int i);

    void setColor(int i);

    void setWidth(int i);
}
