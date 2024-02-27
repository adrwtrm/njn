package com.epson.iprojection.common.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.epson.iprojection.ui.common.activity.ActivityGetter;

/* loaded from: classes.dex */
public final class ColorUtils {
    private static final int MENU_FILTER_COLOR = -2130706433;

    public static PorterDuffColorFilter filter() {
        return new PorterDuffColorFilter(MENU_FILTER_COLOR, PorterDuff.Mode.DST_IN);
    }

    public static int get(int i) {
        return MethodUtil.compatGetColor(ActivityGetter.getIns().getFrontActivity().getApplicationContext(), i);
    }

    private ColorUtils() {
    }
}
