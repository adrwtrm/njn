package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public final class HashBiMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final double LOAD_FACTOR = 1.0d;
    private static final long serialVersionUID = 0;
    @CheckForNull
    private transient BiEntry<K, V> firstInKeyInsertionOrder;
    private transient BiEntry<K, V>[] hashTableKToV;
    private transient BiEntry<K, V>[] hashTableVToK;
    @CheckForNull
    @LazyInit
    private transient BiMap<V, K> inverse;
    @CheckForNull
    private transient BiEntry<K, V> lastInKeyInsertionOrder;
    private transient int mask;
    private transient int modCount;
    private transient int size;

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int i) {
        return new HashBiMap<>(i);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> create = create(map.size());
        create.putAll(map);
        return create;
    }

    /* loaded from: classes2.dex */
    public static final class BiEntry<K, V> extends ImmutableEntry<K, V> {
        final int keyHash;
        @CheckForNull
        BiEntry<K, V> nextInKToVBucket;
        @CheckForNull
        BiEntry<K, V> nextInKeyInsertionOrder;
        @CheckForNull
        BiEntry<K, V> nextInVToKBucket;
        @CheckForNull
        BiEntry<K, V> prevInKeyInsertionOrder;
        final int valueHash;

        BiEntry(@ParametricNullness K k, int i, @ParametricNullness V v, int i2) {
            super(k, v);
            this.keyHash = i;
            this.valueHash = i2;
        }
    }

    private HashBiMap(int i) {
        init(i);
    }

    private void init(int i) {
        CollectPreconditions.checkNonnegative(i, "expectedSize");
        int closedTableSize = Hashing.closedTableSize(i, LOAD_FACTOR);
        this.hashTableKToV = createTable(closedTableSize);
        this.hashTableVToK = createTable(closedTableSize);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.size = 0;
        this.mask = closedTableSize - 1;
        this.modCount = 0;
    }

    public void delete(BiEntry<K, V> biEntry) {
        BiEntry<K, V> biEntry2;
        int i = biEntry.keyHash & this.mask;
        BiEntry<K, V> biEntry3 = null;
        BiEntry<K, V> biEntry4 = null;
        for (BiEntry<K, V> biEntry5 = this.hashTableKToV[i]; biEntry5 != biEntry; biEntry5 = biEntry5.nextInKToVBucket) {
            biEntry4 = biEntry5;
        }
        if (biEntry4 == null) {
            this.hashTableKToV[i] = biEntry.nextInKToVBucket;
        } else {
            biEntry4.nextInKToVBucket = biEntry.nextInKToVBucket;
        }
        int i2 = biEntry.valueHash & this.mask;
        BiEntry<K, V> biEntry6 = this.hashTableVToK[i2];
        while (true) {
            biEntry2 = biEntry3;
            biEntry3 = biEntry6;
            if (biEntry3 == biEntry) {
                break;
            }
            biEntry6 = biEntry3.nextInVToKBucket;
        }
        if (biEntry2 == null) {
            this.hashTableVToK[i2] = biEntry.nextInVToKBucket;
        } else {
            biEntry2.nextInVToKBucket = biEntry.nextInVToKBucket;
        }
        if (biEntry.prevInKeyInsertionOrder == null) {
            this.firstInKeyInsertionOrder = biEntry.nextInKeyInsertionOrder;
        } else {
            biEntry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry.nextInKeyInsertionOrder;
        }
        if (biEntry.nextInKeyInsertionOrder == null) {
            this.lastInKeyInsertionOrder = biEntry.prevInKeyInsertionOrder;
        } else {
            biEntry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = biEntry.prevInKeyInsertionOrder;
        }
        this.size--;
        this.modCount++;
    }

    public void insert(BiEntry<K, V> biEntry, @CheckForNull BiEntry<K, V> biEntry2) {
        int i = biEntry.keyHash & this.mask;
        biEntry.nextInKToVBucket = this.hashTableKToV[i];
        this.hashTableKToV[i] = biEntry;
        int i2 = biEntry.valueHash & this.mask;
        biEntry.nextInVToKBucket = this.hashTableVToK[i2];
        this.hashTableVToK[i2] = biEntry;
        if (biEntry2 == null) {
            biEntry.prevInKeyInsertionOrder = this.lastInKeyInsertionOrder;
            biEntry.nextInKeyInsertionOrder = null;
            BiEntry<K, V> biEntry3 = this.lastInKeyInsertionOrder;
            if (biEntry3 == null) {
                this.firstInKeyInsertionOrder = biEntry;
            } else {
                biEntry3.nextInKeyInsertionOrder = biEntry;
            }
            this.lastInKeyInsertionOrder = biEntry;
        } else {
            biEntry.prevInKeyInsertionOrder = biEntry2.prevInKeyInsertionOrder;
            if (biEntry.prevInKeyInsertionOrder == null) {
                this.firstInKeyInsertionOrder = biEntry;
            } else {
                biEntry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry;
            }
            biEntry.nextInKeyInsertionOrder = biEntry2.nextInKeyInsertionOrder;
            if (biEntry.nextInKeyInsertionOrder == null) {
                this.lastInKeyInsertionOrder = biEntry;
            } else {
                biEntry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = biEntry;
            }
        }
        this.size++;
        this.modCount++;
    }

    @CheckForNull
    public BiEntry<K, V> seekByKey(@CheckForNull Object obj, int i) {
        for (BiEntry<K, V> biEntry = this.hashTableKToV[this.mask & i]; biEntry != null; biEntry = biEntry.nextInKToVBucket) {
            if (i == biEntry.keyHash && Objects.equal(obj, biEntry.key)) {
                return biEntry;
            }
        }
        return null;
    }

    @CheckForNull
    public BiEntry<K, V> seekByValue(@CheckForNull Object obj, int i) {
        for (BiEntry<K, V> biEntry = this.hashTableVToK[this.mask & i]; biEntry != null; biEntry = biEntry.nextInVToKBucket) {
            if (i == biEntry.valueHash && Objects.equal(obj, biEntry.value)) {
                return biEntry;
            }
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        return seekByKey(obj, Hashing.smearedHash(obj)) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        return seekByValue(obj, Hashing.smearedHash(obj)) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        return (V) Maps.valueOrNull(seekByKey(obj, Hashing.smearedHash(obj)));
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CheckForNull
    public V put(@ParametricNullness K k, @ParametricNullness V v) {
        return put(k, v, false);
    }

    @CheckForNull
    private V put(@ParametricNullness K k, @ParametricNullness V v, boolean z) {
        int smearedHash = Hashing.smearedHash(k);
        int smearedHash2 = Hashing.smearedHash(v);
        BiEntry<K, V> seekByKey = seekByKey(k, smearedHash);
        if (seekByKey != null && smearedHash2 == seekByKey.valueHash && Objects.equal(v, seekByKey.value)) {
            return v;
        }
        BiEntry<K, V> seekByValue = seekByValue(v, smearedHash2);
        if (seekByValue != null) {
            if (z) {
                delete(seekByValue);
            } else {
                String valueOf = String.valueOf(v);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 23).append("value already present: ").append(valueOf).toString());
            }
        }
        BiEntry<K, V> biEntry = new BiEntry<>(k, smearedHash, v, smearedHash2);
        if (seekByKey != null) {
            delete(seekByKey);
            insert(biEntry, seekByKey);
            seekByKey.prevInKeyInsertionOrder = null;
            seekByKey.nextInKeyInsertionOrder = null;
            return seekByKey.value;
        }
        insert(biEntry, null);
        rehashIfNecessary();
        return null;
    }

    @Override // com.google.common.collect.BiMap
    @CheckForNull
    public V forcePut(@ParametricNullness K k, @ParametricNullness V v) {
        return put(k, v, true);
    }

    @CheckForNull
    public K putInverse(@ParametricNullness V v, @ParametricNullness K k, boolean z) {
        int smearedHash = Hashing.smearedHash(v);
        int smearedHash2 = Hashing.smearedHash(k);
        BiEntry<K, V> seekByValue = seekByValue(v, smearedHash);
        BiEntry<K, V> seekByKey = seekByKey(k, smearedHash2);
        if (seekByValue != null && smearedHash2 == seekByValue.keyHash && Objects.equal(k, seekByValue.key)) {
            return k;
        }
        if (seekByKey != null && !z) {
            String valueOf = String.valueOf(k);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 21).append("key already present: ").append(valueOf).toString());
        }
        if (seekByValue != null) {
            delete(seekByValue);
        }
        if (seekByKey != null) {
            delete(seekByKey);
        }
        insert(new BiEntry<>(k, smearedHash2, v, smearedHash), seekByKey);
        if (seekByKey != null) {
            seekByKey.prevInKeyInsertionOrder = null;
            seekByKey.nextInKeyInsertionOrder = null;
        }
        if (seekByValue != null) {
            seekByValue.prevInKeyInsertionOrder = null;
            seekByValue.nextInKeyInsertionOrder = null;
        }
        rehashIfNecessary();
        return (K) Maps.keyOrNull(seekByValue);
    }

    private void rehashIfNecessary() {
        BiEntry<K, V>[] biEntryArr = this.hashTableKToV;
        if (Hashing.needsResizing(this.size, biEntryArr.length, LOAD_FACTOR)) {
            int length = biEntryArr.length * 2;
            this.hashTableKToV = createTable(length);
            this.hashTableVToK = createTable(length);
            this.mask = length - 1;
            this.size = 0;
            for (BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder; biEntry != null; biEntry = biEntry.nextInKeyInsertionOrder) {
                insert(biEntry, biEntry);
            }
            this.modCount++;
        }
    }

    private BiEntry<K, V>[] createTable(int i) {
        return new BiEntry[i];
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        BiEntry<K, V> seekByKey = seekByKey(obj, Hashing.smearedHash(obj));
        if (seekByKey == null) {
            return null;
        }
        delete(seekByKey);
        seekByKey.prevInKeyInsertionOrder = null;
        seekByKey.nextInKeyInsertionOrder = null;
        return seekByKey.value;
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        this.size = 0;
        Arrays.fill(this.hashTableKToV, (Object) null);
        Arrays.fill(this.hashTableVToK, (Object) null);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.modCount++;
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public abstract class Itr<T> implements Iterator<T> {
        int expectedModCount;
        @CheckForNull
        BiEntry<K, V> next;
        int remaining;
        @CheckForNull
        BiEntry<K, V> toRemove = null;

        abstract T output(BiEntry<K, V> biEntry);

        Itr() {
            HashBiMap.this = r2;
            this.next = r2.firstInKeyInsertionOrder;
            this.expectedModCount = r2.modCount;
            this.remaining = r2.size();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (HashBiMap.this.modCount == this.expectedModCount) {
                return this.next != null && this.remaining > 0;
            }
            throw new ConcurrentModificationException();
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BiEntry<K, V> biEntry = (BiEntry) java.util.Objects.requireNonNull(this.next);
            this.next = biEntry.nextInKeyInsertionOrder;
            this.toRemove = biEntry;
            this.remaining--;
            return output(biEntry);
        }

        @Override // java.util.Iterator
        public void remove() {
            if (HashBiMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            BiEntry<K, V> biEntry = this.toRemove;
            if (biEntry != null) {
                HashBiMap.this.delete(biEntry);
                this.expectedModCount = HashBiMap.this.modCount;
                this.toRemove = null;
                return;
            }
            throw new IllegalStateException("no calls to next() since the last call to remove()");
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        return new KeySet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class KeySet extends Maps.KeySet<K, V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySet() {
            super(r1);
            HashBiMap.this = r1;
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new HashBiMap<K, V>.Itr<K>(this) { // from class: com.google.common.collect.HashBiMap.KeySet.1
                {
                    HashBiMap hashBiMap = HashBiMap.this;
                }

                @Override // com.google.common.collect.HashBiMap.Itr
                @ParametricNullness
                K output(BiEntry<K, V> biEntry) {
                    return biEntry.key;
                }
            };
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            BiEntry seekByKey = HashBiMap.this.seekByKey(obj, Hashing.smearedHash(obj));
            if (seekByKey == null) {
                return false;
            }
            HashBiMap.this.delete(seekByKey);
            seekByKey.prevInKeyInsertionOrder = null;
            seekByKey.nextInKeyInsertionOrder = null;
            return true;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    public Set<V> values() {
        return inverse().keySet();
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
    public Iterator<Map.Entry<K, V>> entryIterator() {
        return new HashBiMap<K, V>.Itr<Map.Entry<K, V>>() { // from class: com.google.common.collect.HashBiMap.1
            {
                HashBiMap.this = this;
            }

            @Override // com.google.common.collect.HashBiMap.Itr
            public Map.Entry<K, V> output(BiEntry<K, V> biEntry) {
                return new MapEntry(biEntry);
            }

            /* renamed from: com.google.common.collect.HashBiMap$1$MapEntry */
            /* loaded from: classes2.dex */
            public class MapEntry extends AbstractMapEntry<K, V> {
                BiEntry<K, V> delegate;

                MapEntry(BiEntry<K, V> biEntry) {
                    AnonymousClass1.this = r1;
                    this.delegate = biEntry;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                @ParametricNullness
                public K getKey() {
                    return this.delegate.key;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                @ParametricNullness
                public V getValue() {
                    return this.delegate.value;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                @ParametricNullness
                public V setValue(@ParametricNullness V v) {
                    V v2 = this.delegate.value;
                    int smearedHash = Hashing.smearedHash(v);
                    if (smearedHash == this.delegate.valueHash && Objects.equal(v, v2)) {
                        return v;
                    }
                    Preconditions.checkArgument(HashBiMap.this.seekByValue(v, smearedHash) == null, "value already present: %s", v);
                    HashBiMap.this.delete(this.delegate);
                    BiEntry<K, V> biEntry = new BiEntry<>(this.delegate.key, this.delegate.keyHash, v, smearedHash);
                    HashBiMap.this.insert(biEntry, this.delegate);
                    this.delegate.prevInKeyInsertionOrder = null;
                    this.delegate.nextInKeyInsertionOrder = null;
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    anonymousClass1.expectedModCount = HashBiMap.this.modCount;
                    if (AnonymousClass1.this.toRemove == this.delegate) {
                        AnonymousClass1.this.toRemove = biEntry;
                    }
                    this.delegate = biEntry;
                    return v2;
                }
            }
        };
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        for (BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder; biEntry != null; biEntry = biEntry.nextInKeyInsertionOrder) {
            biConsumer.accept((K) biEntry.key, (V) biEntry.value);
        }
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Preconditions.checkNotNull(biFunction);
        clear();
        for (BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder; biEntry != null; biEntry = biEntry.nextInKeyInsertionOrder) {
            put(biEntry.key, biFunction.apply((K) biEntry.key, (V) biEntry.value));
        }
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap == null) {
            Inverse inverse = new Inverse();
            this.inverse = inverse;
            return inverse;
        }
        return biMap;
    }

    /* loaded from: classes2.dex */
    public final class Inverse extends Maps.IteratorBasedAbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private Inverse() {
            HashBiMap.this = r1;
        }

        BiMap<K, V> forward() {
            return HashBiMap.this;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return HashBiMap.this.size;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            forward().clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return forward().containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public K get(@CheckForNull Object obj) {
            return (K) Maps.keyOrNull(HashBiMap.this.seekByValue(obj, Hashing.smearedHash(obj)));
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        @CheckForNull
        public K put(@ParametricNullness V v, @ParametricNullness K k) {
            return (K) HashBiMap.this.putInverse(v, k, false);
        }

        @Override // com.google.common.collect.BiMap
        @CheckForNull
        public K forcePut(@ParametricNullness V v, @ParametricNullness K k) {
            return (K) HashBiMap.this.putInverse(v, k, true);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public K remove(@CheckForNull Object obj) {
            BiEntry seekByValue = HashBiMap.this.seekByValue(obj, Hashing.smearedHash(obj));
            if (seekByValue == null) {
                return null;
            }
            HashBiMap.this.delete(seekByValue);
            seekByValue.prevInKeyInsertionOrder = null;
            seekByValue.nextInKeyInsertionOrder = null;
            return seekByValue.key;
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return forward();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return new InverseKeySet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public final class InverseKeySet extends Maps.KeySet<V, K> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            InverseKeySet() {
                super(r1);
                Inverse.this = r1;
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                BiEntry seekByValue = HashBiMap.this.seekByValue(obj, Hashing.smearedHash(obj));
                if (seekByValue == null) {
                    return false;
                }
                HashBiMap.this.delete(seekByValue);
                return true;
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<V> iterator() {
                return new HashBiMap<K, V>.Itr<V>(this) { // from class: com.google.common.collect.HashBiMap.Inverse.InverseKeySet.1
                    {
                        HashBiMap hashBiMap = HashBiMap.this;
                    }

                    @Override // com.google.common.collect.HashBiMap.Itr
                    @ParametricNullness
                    V output(BiEntry<K, V> biEntry) {
                        return biEntry.value;
                    }
                };
            }
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        public Set<K> values() {
            return forward().keySet();
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public Iterator<Map.Entry<V, K>> entryIterator() {
            return new HashBiMap<K, V>.Itr<Map.Entry<V, K>>() { // from class: com.google.common.collect.HashBiMap.Inverse.1
                {
                    Inverse.this = this;
                    HashBiMap hashBiMap = HashBiMap.this;
                }

                @Override // com.google.common.collect.HashBiMap.Itr
                public Map.Entry<V, K> output(BiEntry<K, V> biEntry) {
                    return new InverseEntry(biEntry);
                }

                /* renamed from: com.google.common.collect.HashBiMap$Inverse$1$InverseEntry */
                /* loaded from: classes2.dex */
                public class InverseEntry extends AbstractMapEntry<V, K> {
                    BiEntry<K, V> delegate;

                    InverseEntry(BiEntry<K, V> biEntry) {
                        AnonymousClass1.this = r1;
                        this.delegate = biEntry;
                    }

                    @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                    @ParametricNullness
                    public V getKey() {
                        return this.delegate.value;
                    }

                    @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                    @ParametricNullness
                    public K getValue() {
                        return this.delegate.key;
                    }

                    @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                    @ParametricNullness
                    public K setValue(@ParametricNullness K k) {
                        K k2 = this.delegate.key;
                        int smearedHash = Hashing.smearedHash(k);
                        if (smearedHash == this.delegate.keyHash && Objects.equal(k, k2)) {
                            return k;
                        }
                        Preconditions.checkArgument(HashBiMap.this.seekByKey(k, smearedHash) == null, "value already present: %s", k);
                        HashBiMap.this.delete(this.delegate);
                        BiEntry<K, V> biEntry = new BiEntry<>(k, smearedHash, this.delegate.value, this.delegate.valueHash);
                        this.delegate = biEntry;
                        HashBiMap.this.insert(biEntry, null);
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        anonymousClass1.expectedModCount = HashBiMap.this.modCount;
                        return k2;
                    }
                }
            };
        }

        @Override // java.util.Map
        public void forEach(final BiConsumer<? super V, ? super K> biConsumer) {
            Preconditions.checkNotNull(biConsumer);
            HashBiMap.this.forEach(new BiConsumer() { // from class: com.google.common.collect.HashBiMap$Inverse$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    HashBiMap.Inverse.lambda$forEach$0(biConsumer, obj, obj2);
                }
            });
        }

        public static /* synthetic */ void lambda$forEach$0(BiConsumer biConsumer, Object obj, Object obj2) {
            biConsumer.accept(obj2, obj);
        }

        @Override // java.util.Map
        public void replaceAll(BiFunction<? super V, ? super K, ? extends K> biFunction) {
            Preconditions.checkNotNull(biFunction);
            clear();
            for (BiEntry<K, V> biEntry = HashBiMap.this.firstInKeyInsertionOrder; biEntry != null; biEntry = biEntry.nextInKeyInsertionOrder) {
                put(biEntry.value, biFunction.apply((V) biEntry.value, (K) biEntry.key));
            }
        }

        Object writeReplace() {
            return new InverseSerializedForm(HashBiMap.this);
        }
    }

    /* loaded from: classes2.dex */
    private static final class InverseSerializedForm<K, V> implements Serializable {
        private final HashBiMap<K, V> bimap;

        InverseSerializedForm(HashBiMap<K, V> hashBiMap) {
            this.bimap = hashBiMap;
        }

        Object readResolve() {
            return this.bimap.inverse();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readCount = Serialization.readCount(objectInputStream);
        init(16);
        Serialization.populateMap(this, objectInputStream, readCount);
    }
}
