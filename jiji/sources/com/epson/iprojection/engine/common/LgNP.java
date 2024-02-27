package com.epson.iprojection.engine.common;

import android.util.Log;

/* loaded from: classes.dex */
public final class LgNP {
    public static final int LvDebug = 0;
    public static final int LvError = 3;
    public static final int LvInfo = 1;
    public static final int LvNoLog = 4;
    public static final int LvWarning = 2;
    public static final String TAG = "NpEngine";
    private static int _logLevel = 4;

    public static void SetLogLevel(int i) {
        _logLevel = i;
    }

    public static void e(String str) {
        if (_logLevel > 3) {
            return;
        }
        Log.e(TAG, getShowStr(str));
    }

    public static void w(String str) {
        if (_logLevel > 2) {
            return;
        }
        Log.w(TAG, getShowStr(str));
    }

    public static void i(String str) {
        if (_logLevel > 1) {
            return;
        }
        Log.i(TAG, getShowStr(str));
    }

    public static void d(String str) {
        if (_logLevel > 0) {
            return;
        }
        Log.d(TAG, getShowStr(str));
    }

    private static String getShowStr(String str) {
        return String.format("%s | %s(%04d) %s()", str, Thread.currentThread().getStackTrace()[4].getFileName(), Integer.valueOf(Thread.currentThread().getStackTrace()[4].getLineNumber()), Thread.currentThread().getStackTrace()[4].getMethodName());
    }

    private LgNP() {
    }
}
