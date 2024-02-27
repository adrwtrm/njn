package com.serenegiant.utils;

import java.lang.AutoCloseable;

/* loaded from: classes2.dex */
public class RefCountedAutoCloseable<T extends AutoCloseable> implements AutoCloseable {
    private T mObject;
    private long mRefCount = 0;

    public RefCountedAutoCloseable(T t) {
        this.mObject = t;
    }

    public synchronized T getAndRetain() {
        long j = this.mRefCount;
        if (j < 0) {
            return null;
        }
        this.mRefCount = j + 1;
        return this.mObject;
    }

    public synchronized T get() {
        return this.mObject;
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() {
        long j = this.mRefCount;
        if (j >= 0) {
            long j2 = j - 1;
            this.mRefCount = j2;
            if (j2 < 0) {
                try {
                    this.mObject.close();
                    this.mObject = null;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
