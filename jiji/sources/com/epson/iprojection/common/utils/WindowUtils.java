package com.epson.iprojection.common.utils;

import android.app.Activity;
import android.graphics.Rect;

/* loaded from: classes.dex */
public class WindowUtils {
    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}
