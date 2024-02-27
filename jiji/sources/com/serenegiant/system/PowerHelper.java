package com.serenegiant.system;

import android.app.Activity;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;

/* loaded from: classes2.dex */
public class PowerHelper {
    private static final String TAG = "PowerHelper";

    private PowerHelper() {
    }

    public static void wake(Activity activity, boolean z, long j) {
        wake(activity, true, true, j);
    }

    public static void wake(Activity activity, boolean z, boolean z2, long j) {
        try {
            PowerManager.WakeLock newWakeLock = ((PowerManager) ContextUtils.requireSystemService(activity, PowerManager.class)).newWakeLock(805306394, "PowerHelper:disableLock");
            if (j > 0) {
                newWakeLock.acquire(j);
            } else {
                newWakeLock.acquire();
            }
            int i = 128;
            if (z) {
                Window window = activity.getWindow();
                if (!z2) {
                    i = 0;
                }
                window.addFlags(4718592 | i);
            } else if (z2) {
                activity.getWindow().addFlags(128);
            }
            newWakeLock.release();
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    public static void releaseWakeup(Activity activity) {
        activity.getWindow().clearFlags(4718720);
    }
}
