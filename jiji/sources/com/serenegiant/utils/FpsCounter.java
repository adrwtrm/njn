package com.serenegiant.utils;

import com.serenegiant.system.Time;

/* loaded from: classes2.dex */
public class FpsCounter {
    private int cnt;
    private float fps;
    private int prevCnt;
    private long prevTime;
    private long startTime;
    private float totalFps;

    public FpsCounter() {
        reset();
    }

    public synchronized FpsCounter reset() {
        this.prevCnt = 0;
        this.cnt = 0;
        long nanoTime = Time.nanoTime() - 1;
        this.prevTime = nanoTime;
        this.startTime = nanoTime;
        return this;
    }

    public synchronized void count() {
        this.cnt++;
    }

    public synchronized FpsCounter update() {
        long nanoTime = Time.nanoTime();
        int i = this.cnt;
        this.fps = ((i - this.prevCnt) * 1.0E9f) / ((float) (nanoTime - this.prevTime));
        this.prevCnt = i;
        this.prevTime = nanoTime;
        this.totalFps = (i * 1.0E9f) / ((float) (nanoTime - this.startTime));
        return this;
    }

    public synchronized float getFps() {
        return this.fps;
    }

    public synchronized float getTotalFps() {
        return this.totalFps;
    }
}
