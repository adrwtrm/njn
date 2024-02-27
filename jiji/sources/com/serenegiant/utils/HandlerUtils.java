package com.serenegiant.utils;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes2.dex */
public class HandlerUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "HandlerUtils";

    private HandlerUtils() {
    }

    public static void quitSafely(Handler handler) throws IllegalStateException {
        Looper looper = handler != null ? handler.getLooper() : null;
        if (looper != null) {
            looper.quitSafely();
            return;
        }
        throw new IllegalStateException("has no looper");
    }

    public static void NoThrowQuitSafely(Handler handler) {
        try {
            quitSafely(handler);
        } catch (Exception unused) {
        }
    }

    public static void quit(Handler handler) throws IllegalStateException {
        Looper looper = handler != null ? handler.getLooper() : null;
        if (looper != null) {
            looper.quit();
            return;
        }
        throw new IllegalStateException("has no looper");
    }

    public static void NoThrowQuit(Handler handler) {
        try {
            quit(handler);
        } catch (Exception unused) {
        }
    }

    public static boolean isActive(Handler handler) {
        Thread thread = handler != null ? handler.getLooper().getThread() : null;
        return handler != null && thread != null && thread.isAlive() && handler.sendEmptyMessage(0);
    }

    public static boolean isTerminated(Handler handler) {
        Thread thread = handler != null ? handler.getLooper().getThread() : null;
        return handler == null || thread == null || !thread.isAlive() || !handler.sendEmptyMessage(0);
    }
}
