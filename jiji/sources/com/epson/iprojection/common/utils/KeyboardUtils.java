package com.epson.iprojection.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class KeyboardUtils {
    private static final float KEYBOARD_DETECT_BOTTOM_THRESHOLD_DP = 100.0f;

    public static boolean isShowing(Activity activity) {
        Context context;
        View rootView;
        View childAt = ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
        if (childAt == null || (context = childAt.getContext()) == null || (rootView = childAt.getRootView()) == null) {
            return false;
        }
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        return Math.abs(((float) rect.bottom) - ((float) context.getResources().getDisplayMetrics().heightPixels)) > context.getResources().getDisplayMetrics().density * KEYBOARD_DETECT_BOTTOM_THRESHOLD_DP;
    }
}
