package com.serenegiant.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class Pool<T> {
    private int mCreatedObjects;
    private final int mInitNum;
    private final int mLimitNum;
    private final int mMaxNumInPool;
    private final List<T> mPool;

    protected abstract T createObject(Object... objArr);

    public Pool(int i, int i2, Object... objArr) {
        this(i, i2, i2, objArr);
    }

    public Pool(int i, int i2, int i3, Object... objArr) {
        this.mPool = new ArrayList();
        this.mInitNum = i;
        this.mMaxNumInPool = Math.min(i2, i3);
        this.mLimitNum = i3;
        init(objArr);
    }

    public void init(Object... objArr) {
        synchronized (this.mPool) {
            this.mPool.clear();
            this.mCreatedObjects = 0;
            for (int i = 0; i < this.mInitNum && i < this.mMaxNumInPool; i++) {
                this.mPool.add(createObject(objArr));
                this.mCreatedObjects++;
            }
        }
    }

    public T obtain(Object... objArr) {
        T t;
        List<T> list;
        synchronized (this.mPool) {
            if (this.mPool.isEmpty()) {
                t = null;
            } else {
                t = this.mPool.remove(list.size() - 1);
            }
            if (t == null && this.mCreatedObjects < this.mLimitNum) {
                t = createObject(objArr);
                this.mCreatedObjects++;
            }
        }
        return t;
    }

    public boolean recycle(T t) {
        synchronized (this.mPool) {
            if (this.mPool.size() < this.mMaxNumInPool) {
                return this.mPool.add(t);
            }
            this.mCreatedObjects--;
            return false;
        }
    }

    public void recycle(Collection<T> collection) {
        for (T t : collection) {
            if (t != null) {
                recycle((Pool<T>) t);
            }
        }
    }

    public void recycle(T[] tArr) {
        for (T t : tArr) {
            if (t != null) {
                recycle((Pool<T>) t);
            }
        }
    }

    public void release(T t) {
        synchronized (this.mPool) {
            int i = this.mCreatedObjects;
            if (i > 0) {
                this.mCreatedObjects = i - 1;
            }
        }
    }

    public void clear() {
        synchronized (this.mPool) {
            this.mPool.clear();
            this.mCreatedObjects = 0;
        }
    }
}
