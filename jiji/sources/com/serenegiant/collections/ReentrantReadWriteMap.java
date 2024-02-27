package com.serenegiant.collections;

import androidx.collection.ArraySet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class ReentrantReadWriteMap<K, V> implements Map<K, V> {
    private final Map<K, V> mMap;
    private final Lock mReadLock;
    private final ReentrantReadWriteLock mSensorLock;
    private final Lock mWriteLock;

    public ReentrantReadWriteMap() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mSensorLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
        this.mMap = new HashMap();
    }

    @Override // java.util.Map
    public V get(Object obj) {
        this.mReadLock.lock();
        try {
            return getLocked(obj, null);
        } finally {
            this.mReadLock.unlock();
        }
    }

    public V tryGet(K k) {
        if (this.mReadLock.tryLock()) {
            try {
                return getLocked(k, null);
            } finally {
                this.mReadLock.unlock();
            }
        }
        return null;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        this.mWriteLock.lock();
        try {
            V remove = this.mMap.remove(k);
            this.mMap.put(k, v);
            return remove;
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @Override // java.util.Map
    public V putIfAbsent(K k, V v) {
        this.mWriteLock.lock();
        try {
            V locked = getLocked(k, null);
            if (locked == null) {
                locked = this.mMap.put(k, v);
            }
            return locked;
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        this.mWriteLock.lock();
        try {
            this.mMap.putAll(map);
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        this.mWriteLock.lock();
        try {
            return this.mMap.remove(obj);
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        this.mWriteLock.lock();
        try {
            return this.mMap.remove(obj, obj2);
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public Collection<V> removeAll() {
        ArrayList arrayList = new ArrayList();
        this.mWriteLock.lock();
        try {
            arrayList.addAll(this.mMap.values());
            this.mMap.clear();
            return arrayList;
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @Override // java.util.Map
    public void clear() {
        this.mWriteLock.lock();
        try {
            this.mMap.clear();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @Override // java.util.Map
    public int size() {
        this.mReadLock.lock();
        try {
            return this.mMap.size();
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        this.mReadLock.lock();
        try {
            return containsKeyLocked(obj);
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        this.mReadLock.lock();
        try {
            return this.mMap.containsValue(obj);
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public V getOrDefault(Object obj, V v) {
        this.mReadLock.lock();
        try {
            return getLocked(obj, v);
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        this.mReadLock.lock();
        try {
            return this.mMap.isEmpty();
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        ArraySet arraySet = new ArraySet();
        this.mReadLock.lock();
        try {
            arraySet.addAll(this.mMap.keySet());
            return arraySet;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public Collection<K> keys() {
        ArrayList arrayList = new ArrayList();
        this.mReadLock.lock();
        try {
            arrayList.addAll(this.mMap.keySet());
            return arrayList;
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public Collection<V> values() {
        ArrayList arrayList = new ArrayList();
        this.mReadLock.lock();
        try {
            if (!this.mMap.isEmpty()) {
                arrayList.addAll(this.mMap.values());
            }
            return arrayList;
        } finally {
            this.mReadLock.unlock();
        }
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        HashSet hashSet = new HashSet();
        this.mReadLock.lock();
        try {
            hashSet.addAll(this.mMap.entrySet());
            return hashSet;
        } finally {
            this.mReadLock.unlock();
        }
    }

    private V getLocked(Object obj, V v) {
        return this.mMap.containsKey(obj) ? this.mMap.get(obj) : v;
    }

    private boolean containsKeyLocked(Object obj) {
        return this.mMap.containsKey(obj);
    }

    private static final boolean isEquals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    protected void readLock() {
        this.mReadLock.lock();
    }

    protected void readUnlock() {
        this.mReadLock.unlock();
    }

    protected void writeLock() {
        this.mWriteLock.lock();
    }

    protected void writeUnlock() {
        this.mWriteLock.unlock();
    }

    protected Collection<V> valuesLocked() {
        return this.mMap.values();
    }

    protected Set<K> keysLocked() {
        return this.mMap.keySet();
    }

    protected Map<K, V> mapLocked() {
        return this.mMap;
    }
}
