package com.serenegiant.system;

import android.os.SystemClock;

/* loaded from: classes2.dex */
public class Time {
    public static boolean prohibitElapsedRealtimeNanos = true;
    private static Time sTime;

    static {
        reset();
    }

    public static long nanoTime() {
        return sTime.timeNs();
    }

    public static long milliTime() {
        return sTime.timeMs();
    }

    public static void reset() {
        if (!prohibitElapsedRealtimeNanos && BuildCheck.isJellyBeanMr1()) {
            sTime = new TimeJellyBeanMr1();
        } else {
            sTime = new Time();
        }
    }

    private Time() {
    }

    private long timeMs() {
        return timeNs() / 1000000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class TimeJellyBeanMr1 extends Time {
        private TimeJellyBeanMr1() {
            super();
        }

        @Override // com.serenegiant.system.Time
        public long timeNs() {
            return SystemClock.elapsedRealtimeNanos();
        }
    }

    protected long timeNs() {
        return System.nanoTime();
    }
}
