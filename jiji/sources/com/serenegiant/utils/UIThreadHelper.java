package com.serenegiant.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/* loaded from: classes2.dex */
public final class UIThreadHelper {
    private static final String TAG = "UIThreadHelper";
    private static final Handler sUIHandler;
    private static final Thread sUiThread;

    private UIThreadHelper() {
    }

    static {
        Handler handler = new Handler(Looper.getMainLooper());
        sUIHandler = handler;
        sUiThread = handler.getLooper().getThread();
    }

    public static Handler getHandler() {
        return sUIHandler;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != sUiThread) {
            sUIHandler.post(runnable);
            return;
        }
        try {
            runnable.run();
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    public static void runOnUiThread(Runnable runnable, long j) {
        if (j > 0 || Thread.currentThread() != sUiThread) {
            sUIHandler.postDelayed(runnable, j);
            return;
        }
        try {
            runnable.run();
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    public static void removeFromUiThread(Runnable runnable) {
        sUIHandler.removeCallbacks(runnable);
    }

    public static void removeAllFromUiThread() {
        sUIHandler.removeCallbacksAndMessages(null);
    }
}
