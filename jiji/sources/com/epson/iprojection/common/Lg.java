package com.epson.iprojection.common;

/* loaded from: classes.dex */
public final class Lg {
    public static final int LvDebug = 0;
    public static final int LvError = 3;
    public static final int LvInfo = 1;
    public static final int LvNoLog = 4;
    public static final int LvWarning = 2;
    public static final String TAG = "iProjection";
    private static final int _logLevel = 4;

    public static void d(String str) {
    }

    public static void e(String str) {
    }

    public static void i(String str) {
    }

    public static boolean isEnable() {
        return false;
    }

    public static void trace() {
    }

    public static void w(String str) {
    }

    private static String getShowStr(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return String.format("%s | %s(%04d) %s(), %03d'%03d", str, stackTrace[4].getFileName(), Integer.valueOf(stackTrace[4].getLineNumber()), stackTrace[4].getMethodName(), Long.valueOf((currentTimeMillis / 1000) % 1000), Long.valueOf(currentTimeMillis % 1000));
    }

    private Lg() {
    }
}
