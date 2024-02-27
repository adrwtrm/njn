package javassist.scopedpool;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class SoftValueHashMap<K, V> implements Map<K, V> {
    private Map<K, SoftValueRef<K, V>> hash;
    private ReferenceQueue<V> queue;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SoftValueRef<K, V> extends SoftReference<V> {
        public K key;

        private SoftValueRef(K k, V v, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            this.key = k;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <K, V> SoftValueRef<K, V> create(K k, V v, ReferenceQueue<V> referenceQueue) {
            if (v == null) {
                return null;
            }
            return new SoftValueRef<>(k, v, referenceQueue);
        }
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        processQueue();
        HashSet hashSet = new HashSet();
        for (Map.Entry<K, SoftValueRef<K, V>> entry : this.hash.entrySet()) {
            hashSet.add(new AbstractMap.SimpleImmutableEntry(entry.getKey(), entry.getValue().get()));
        }
        return hashSet;
    }

    private void processQueue() {
        if (this.hash.isEmpty()) {
            return;
        }
        while (true) {
            Reference<? extends V> poll = this.queue.poll();
            if (poll == null) {
                return;
            }
            if (poll instanceof SoftValueRef) {
                SoftValueRef softValueRef = (SoftValueRef) poll;
                if (poll == this.hash.get(softValueRef.key)) {
                    this.hash.remove(softValueRef.key);
                }
            }
        }
    }

    public SoftValueHashMap(int i, float f) {
        this.queue = new ReferenceQueue<>();
        this.hash = new ConcurrentHashMap(i, f);
    }

    public SoftValueHashMap(int i) {
        this.queue = new ReferenceQueue<>();
        this.hash = new ConcurrentHashMap(i);
    }

    public SoftValueHashMap() {
        this.queue = new ReferenceQueue<>();
        this.hash = new ConcurrentHashMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SoftValueHashMap(Map<K, V> map) {
        this(Math.max(map.size() * 2, 11), 0.75f);
        putAll(map);
    }

    @Override // java.util.Map
    public int size() {
        processQueue();
        return this.hash.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        processQueue();
        return this.hash.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        processQueue();
        return this.hash.containsKey(obj);
    }

    @Override // java.util.Map
    public V get(Object obj) {
        processQueue();
        return valueOrNull(this.hash.get(obj));
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        processQueue();
        return valueOrNull(this.hash.put(k, SoftValueRef.create(k, v, this.queue)));
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        processQueue();
        return valueOrNull(this.hash.remove(obj));
    }

    @Override // java.util.Map
    public void clear() {
        processQueue();
        this.hash.clear();
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        processQueue();
        if (obj == null) {
            return false;
        }
        for (SoftValueRef<K, V> softValueRef : this.hash.values()) {
            if (softValueRef != null && obj.equals(softValueRef.get())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        processQueue();
        return this.hash.keySet();
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        processQueue();
        for (K k : map.keySet()) {
            put(k, map.get(k));
        }
    }

    @Override // java.util.Map
    public Collection<V> values() {
        processQueue();
        ArrayList arrayList = new ArrayList();
        for (SoftValueRef<K, V> softValueRef : this.hash.values()) {
            arrayList.add(softValueRef.get());
        }
        return arrayList;
    }

    private V valueOrNull(SoftValueRef<K, V> softValueRef) {
        if (softValueRef == null) {
            return null;
        }
        return softValueRef.get();
    }
}
