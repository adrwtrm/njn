package com.epson.iprojection.common.utils;

import android.graphics.PointF;

/* loaded from: classes.dex */
public final class MathUtils {
    public static float getDist(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (float) Math.sqrt((f5 * f5) + (f6 * f6));
    }

    public static PointF getMidPoint(float f, float f2, float f3, float f4) {
        return new PointF((f + f3) / 2.0f, (f2 + f4) / 2.0f);
    }

    private MathUtils() {
    }
}
