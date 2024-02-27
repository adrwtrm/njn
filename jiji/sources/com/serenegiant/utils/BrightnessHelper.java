package com.serenegiant.utils;

import android.app.Activity;

/* loaded from: classes2.dex */
public class BrightnessHelper {
    private BrightnessHelper() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001b, code lost:
        if (r4 < (-1.0f)) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setBrightness(android.app.Activity r3, float r4) {
        /*
            boolean r0 = r3.isFinishing()
            if (r0 == 0) goto L7
            return
        L7:
            android.view.Window r3 = r3.getWindow()
            android.view.WindowManager$LayoutParams r0 = r3.getAttributes()
            r1 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r2 <= 0) goto L17
        L15:
            r4 = r1
            goto L1e
        L17:
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r2 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r2 >= 0) goto L1e
            goto L15
        L1e:
            r0.screenBrightness = r4
            r0.buttonBrightness = r4
            r3.setAttributes(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.utils.BrightnessHelper.setBrightness(android.app.Activity, float):void");
    }

    public static float getBrightness(Activity activity) {
        return activity.getWindow().getAttributes().screenBrightness;
    }
}
