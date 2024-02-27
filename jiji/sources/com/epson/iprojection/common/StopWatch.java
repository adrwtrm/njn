package com.epson.iprojection.common;

/* loaded from: classes.dex */
public class StopWatch {
    long mStartTime = System.currentTimeMillis();

    public void start() {
        this.mStartTime = System.currentTimeMillis();
    }

    public long getDiff() {
        return System.currentTimeMillis() - this.mStartTime;
    }
}
