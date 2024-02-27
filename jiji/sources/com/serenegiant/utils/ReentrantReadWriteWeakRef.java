package com.serenegiant.utils;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class ReentrantReadWriteWeakRef<T> {
    private final Lock mReadLock;
    private final ReentrantReadWriteLock mSensorLock;
    private WeakReference<T> mWeakRef;
    private final Lock mWriteLock;

    public ReentrantReadWriteWeakRef() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mSensorLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
    }

    public ReentrantReadWriteWeakRef(T t) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mSensorLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
        set((ReentrantReadWriteWeakRef<T>) t);
    }

    public ReentrantReadWriteWeakRef(WeakReference<T> weakReference) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mSensorLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
        set((WeakReference) weakReference);
    }

    public ReentrantReadWriteWeakRef(ReentrantReadWriteWeakRef<T> reentrantReadWriteWeakRef) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mSensorLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
        if (reentrantReadWriteWeakRef != null) {
            set((ReentrantReadWriteWeakRef<T>) reentrantReadWriteWeakRef.get());
        }
    }

    public T get() {
        this.mReadLock.lock();
        try {
            WeakReference<T> weakReference = this.mWeakRef;
            return weakReference != null ? weakReference.get() : null;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public T tryGet() {
        if (this.mReadLock.tryLock()) {
            try {
                WeakReference<T> weakReference = this.mWeakRef;
                return weakReference != null ? weakReference.get() : null;
            } finally {
                this.mReadLock.unlock();
            }
        }
        return null;
    }

    public T set(T t) {
        this.mWriteLock.lock();
        try {
            WeakReference<T> weakReference = this.mWeakRef;
            T t2 = weakReference != null ? weakReference.get() : null;
            if (t != null) {
                this.mWeakRef = new WeakReference<>(t);
            } else {
                this.mWeakRef = null;
            }
            return t2;
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public T set(WeakReference<T> weakReference) {
        T t = weakReference != null ? weakReference.get() : null;
        this.mWriteLock.lock();
        try {
            WeakReference<T> weakReference2 = this.mWeakRef;
            T t2 = weakReference2 != null ? weakReference2.get() : null;
            if (t != null) {
                this.mWeakRef = new WeakReference<>(t);
            } else {
                this.mWeakRef = null;
            }
            return t2;
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public T set(ReentrantReadWriteWeakRef<T> reentrantReadWriteWeakRef) {
        return set((ReentrantReadWriteWeakRef<T>) (reentrantReadWriteWeakRef != null ? reentrantReadWriteWeakRef.get() : null));
    }

    public T clear() {
        return set((ReentrantReadWriteWeakRef<T>) null);
    }

    public T swap(ReentrantReadWriteWeakRef<T> reentrantReadWriteWeakRef) {
        return set((ReentrantReadWriteWeakRef) reentrantReadWriteWeakRef);
    }

    public boolean isEmpty() {
        return tryGet() == null;
    }

    public void readLock() {
        this.mReadLock.lock();
    }

    public void readUnlock() {
        this.mReadLock.unlock();
    }

    public void writeLock() {
        this.mWriteLock.lock();
    }

    public void writeUnlock() {
        this.mWriteLock.unlock();
    }
}
