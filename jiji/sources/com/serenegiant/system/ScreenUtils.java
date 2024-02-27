package com.serenegiant.system;

import android.app.Activity;
import android.view.Window;
import com.serenegiant.utils.BrightnessHelper;

/* loaded from: classes2.dex */
public class ScreenUtils {
    private ScreenUtils() {
    }

    public static void setKeepScreenOn(final Activity activity, final boolean z) {
        activity.runOnUiThread(new Runnable() { // from class: com.serenegiant.system.ScreenUtils.1
            @Override // java.lang.Runnable
            public void run() {
                Window window = activity.getWindow();
                if (z) {
                    window.addFlags(128);
                    return;
                }
                window.clearFlags(128);
                BrightnessHelper.setBrightness(activity, -1.0f);
            }
        });
    }
}
