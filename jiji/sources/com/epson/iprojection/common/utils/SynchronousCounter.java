package com.epson.iprojection.common.utils;

/* loaded from: classes.dex */
public class SynchronousCounter {
    protected int n;

    public synchronized void reset() {
        this.n = 0;
    }

    public synchronized void add() {
        this.n++;
    }

    public synchronized void subtract() {
        this.n--;
    }

    public synchronized int get() {
        return this.n;
    }
}
