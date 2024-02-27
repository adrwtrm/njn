package com.epson.iprojection.common.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.epson.iprojection.ui.common.activity.ActivityGetter;

/* loaded from: classes.dex */
public class DisplayInfoUtils {
    /* JADX WARN: Code restructure failed: missing block: B:10:0x002b, code lost:
        if (r6 != 3) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0026, code lost:
        if (r6 != 2) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getOrientationType(android.app.Activity r6) {
        /*
            android.content.res.Resources r0 = r6.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            java.lang.String r1 = "window"
            java.lang.Object r6 = r6.getSystemService(r1)
            android.view.WindowManager r6 = (android.view.WindowManager) r6
            android.view.Display r6 = r6.getDefaultDisplay()
            int r6 = r6.getRotation()
            int r1 = r0.orientation
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == r4) goto L29
            if (r1 == r3) goto L24
            r1 = r5
            goto L31
        L24:
            if (r6 == 0) goto L30
            if (r6 != r3) goto L2e
            goto L30
        L29:
            if (r6 == r4) goto L30
            if (r6 != r2) goto L2e
            goto L30
        L2e:
            r1 = r4
            goto L31
        L30:
            r1 = r3
        L31:
            if (r1 == r4) goto L3b
            if (r1 == r3) goto L36
            goto L40
        L36:
            if (r6 == r4) goto L42
            if (r6 != r3) goto L40
            goto L42
        L3b:
            if (r6 == r3) goto L42
            if (r6 != r2) goto L40
            goto L42
        L40:
            r6 = r5
            goto L43
        L42:
            r6 = r4
        L43:
            int r0 = r0.orientation
            if (r0 == r4) goto L51
            if (r0 == r3) goto L4a
            goto L55
        L4a:
            if (r6 == 0) goto L4f
            r4 = 8
            goto L55
        L4f:
            r4 = r5
            goto L55
        L51:
            if (r6 == 0) goto L55
            r4 = 9
        L55:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.utils.DisplayInfoUtils.getOrientationType(android.app.Activity):int");
    }

    public static int getWidth() {
        WindowManager windowManager = (WindowManager) ActivityGetter.getIns().getFrontActivity().getSystemService("window");
        if (Build.VERSION.SDK_INT >= 30) {
            return windowManager.getCurrentWindowMetrics().getBounds().width();
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    public static int getHeight() {
        WindowManager windowManager = (WindowManager) ActivityGetter.getIns().getFrontActivity().getSystemService("window");
        if (Build.VERSION.SDK_INT >= 30) {
            return windowManager.getCurrentWindowMetrics().getBounds().height();
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    public static int getDeviceDefaultOrientation(Activity activity) {
        Configuration configuration = activity.getResources().getConfiguration();
        int rotation = ((WindowManager) activity.getSystemService("window")).getDefaultDisplay().getRotation();
        return (((rotation == 0 || rotation == 2) && configuration.orientation == 2) || ((rotation == 1 || rotation == 3) && configuration.orientation == 1)) ? 2 : 1;
    }

    private DisplayInfoUtils() {
    }
}
