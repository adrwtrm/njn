package com.epson.iprojection.ui.activities.presen.main_image;

import android.graphics.Rect;

/* loaded from: classes.dex */
public class D_ExtendInfo {
    public double scale;
    public Rect srcClip = new Rect(0, 0, 0, 0);
    public Rect dstClip = new Rect(0, 0, 0, 0);

    public D_ExtendInfo(Rect rect, Rect rect2, double d) {
        this.srcClip.set(rect);
        this.dstClip.set(rect2);
        this.scale = d;
    }
}
