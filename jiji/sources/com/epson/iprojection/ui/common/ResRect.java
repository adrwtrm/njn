package com.epson.iprojection.ui.common;

import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;

/* loaded from: classes.dex */
public class ResRect {
    public int h;
    public int w;
    public int x;
    public int y;

    public ResRect() {
        this.h = 0;
        this.w = 0;
        this.y = 0;
        this.x = 0;
    }

    public ResRect(int i, int i2) {
        this.x = 0;
        this.y = 0;
        this.w = i;
        this.h = i2;
    }

    public ResRect(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.w = i3;
        this.h = i4;
    }

    public ResRect(ResRect resRect) {
        this.x = resRect.x;
        this.y = resRect.y;
        this.w = resRect.w;
        this.h = resRect.h;
    }

    public String toString() {
        return "ResRect{ (" + this.x + RemotePrefUtils.SEPARATOR + this.y + ")[" + this.w + "x" + this.h + "], aspect=" + (this.w / this.h) + " }";
    }

    /* renamed from: clone */
    public ResRect m191clone() {
        try {
            return (ResRect) super.clone();
        } catch (Exception unused) {
            return new ResRect(this.x, this.y, this.w, this.h);
        }
    }
}
