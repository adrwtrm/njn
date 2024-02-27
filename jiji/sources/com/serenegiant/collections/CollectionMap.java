package com.serenegiant.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class CollectionMap<K, V> implements Map<K, Collection<V>> {
    private final Map<K, Collection<V>> contents = createContentsMap();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((CollectionMap<K, V>) obj, (Collection) ((Collection) obj2));
    }

    @Override // java.util.Map
    public void clear() {
        this.contents.clear();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.contents.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.contents.containsValue(obj);
    }

    public boolean containsInValue(V v) {
        for (Collection<V> collection : this.contents.values()) {
            if (collection.contains(v)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, Collection<V>>> entrySet() {
        return this.contents.entrySet();
    }

    @Override // java.util.Map
    public Collection<V> get(Object obj) {
        return internalGet(obj);
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.contents.isEmpty();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.contents.keySet();
    }

    public Collection<V> put(K k, Collection<V> collection) {
        return this.contents.put(k, collection);
    }

    public boolean add(K k, V v) {
        Collection<V> collection = get((Object) k);
        if (collection == null) {
            collection = createCollection();
            this.contents.put(k, collection);
        }
        return collection.add(v);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends Collection<V>> map) {
        this.contents.putAll(map);
    }

    public void addAll(Map<? extends K, ? extends Collection<V>> map) {
        for (Map.Entry<? extends K, ? extends Collection<V>> entry : map.entrySet()) {
            addAll(entry.getKey(), (Collection) ((Collection<V>) entry.getValue()));
        }
    }

    public boolean addAll(K k, Collection<? extends V> collection) {
        Collection<V> internalGet = internalGet(k);
        if (internalGet == null) {
            internalGet = createCollection();
            this.contents.put(k, internalGet);
        }
        return internalGet.addAll(collection);
    }

    @Override // java.util.Map
    public Collection<V> remove(Object obj) {
        return this.contents.remove(obj);
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        Collection<V> internalGet = internalGet(obj);
        return internalGet != null && internalGet.remove(obj2);
    }

    @Override // java.util.Map
    public int size() {
        return this.contents.size();
    }

    public int size(K k) {
        Collection<V> internalGet = internalGet(k);
        if (internalGet != null) {
            return internalGet.size();
        }
        return 0;
    }

    @Override // java.util.Map
    public Collection<Collection<V>> values() {
        return this.contents.values();
    }

    public Collection<V> valuesAll() {
        Collection<V> createCollection = createCollection();
        for (Collection<V> collection : values()) {
            createCollection.addAll(collection);
        }
        return createCollection;
    }

    protected Map<K, Collection<V>> createContentsMap() {
        return new HashMap();
    }

    protected Collection<V> createCollection() {
        return new ArrayList();
    }

    private Collection<V> internalGet(Object obj) {
        if (this.contents.containsKey(obj)) {
            return this.contents.get(obj);
        }
        return null;
    }
}
