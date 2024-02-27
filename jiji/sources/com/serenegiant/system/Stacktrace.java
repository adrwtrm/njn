package com.serenegiant.system;

import android.util.Log;

/* loaded from: classes2.dex */
public class Stacktrace {
    private static final String TAG = "Stacktrace";

    private Stacktrace() {
    }

    public static void print() {
        Throwable th = new Throwable();
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null) {
            boolean z = true;
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (z || stackTraceElement == null) {
                    z = false;
                } else {
                    sb.append(stackTraceElement.toString()).append("\n");
                }
            }
        }
        Log.i(TAG, sb.toString());
    }

    public static String asString() {
        return asString(new Throwable());
    }

    public static String asString(Throwable th) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null) {
            boolean z = true;
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (z || stackTraceElement == null) {
                    z = false;
                } else {
                    sb.append(stackTraceElement.toString()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public static String callFrom() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace != null) {
            int i = 0;
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (i > 1 && stackTraceElement != null) {
                    return stackTraceElement.toString();
                }
                i++;
            }
            return "unknown method";
        }
        return "unknown method";
    }
}
