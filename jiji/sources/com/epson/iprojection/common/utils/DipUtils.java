package com.epson.iprojection.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;

/* loaded from: classes.dex */
public class DipUtils {
    public static float getDensity(Activity activity) {
        return activity.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int getWinW(Activity activity) {
        int width = DisplayInfoUtils.getWidth();
        int height = DisplayInfoUtils.getHeight();
        return width > height ? height : width;
    }

    public static int dp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }
}
