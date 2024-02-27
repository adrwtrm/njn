package com.epson.iprojection.common.utils;

import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public final class Sleeper {
    public static void sleep(long j) {
        if (j < 0) {
            Lg.w(j + "[ms]はsleep出来ません");
            return;
        }
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
            Lg.e("sleep error");
        }
    }

    private Sleeper() {
    }
}
