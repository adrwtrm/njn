package com.epson.iprojection.common.utils;

import com.epson.iprojection.ui.common.ResRect;

/* loaded from: classes.dex */
public final class FitResolution {
    public static double get(ResRect resRect, ResRect resRect2) {
        double d;
        int i;
        if (resRect.w == 0 || resRect.h == 0 || resRect2.w == 0 || resRect2.h == 0) {
            return 0.0d;
        }
        if (resRect.w * resRect2.h > resRect2.w * resRect.h) {
            d = resRect2.w;
            i = resRect.w;
        } else {
            d = resRect2.h;
            i = resRect.h;
        }
        double d2 = d / i;
        resRect.w = (int) Math.round(resRect.w * d2);
        resRect.h = (int) Math.round(resRect.h * d2);
        if (resRect.w < 1.0d) {
            resRect.w = 1;
        }
        if (resRect.h < 1.0d) {
            resRect.h = 1;
        }
        return d2;
    }

    public static double getRectFitWithIn(ResRect resRect, ResRect resRect2) {
        double d = get(resRect, resRect2);
        resRect.x = (resRect2.w - resRect.w) / 2;
        resRect.y = (resRect2.h - resRect.h) / 2;
        return d;
    }

    private FitResolution() {
    }
}
