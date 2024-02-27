package com.serenegiant.utils;

/* loaded from: classes2.dex */
public class ThreadUtils {
    private ThreadUtils() {
    }

    public static void NoThrowSleep(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
        }
    }

    public static void NoThrowSleep(long j, int i) {
        try {
            Thread.sleep(j, i);
        } catch (InterruptedException unused) {
        }
    }
}
