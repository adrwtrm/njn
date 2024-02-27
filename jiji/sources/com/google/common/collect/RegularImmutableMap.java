package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMapEntry;
import com.google.common.collect.ImmutableMapEntrySet;
import java.io.Serializable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap(ImmutableMap.EMPTY_ENTRY_ARRAY, null, 0);
    static final double HASH_FLOODING_FPP = 0.001d;
    static final int MAX_HASH_BUCKET_LENGTH = 8;
    static final double MAX_LOAD_FACTOR = 1.2d;
    private static final long serialVersionUID = 0;
    final transient Map.Entry<K, V>[] entries;
    private final transient int mask;
    @CheckForNull
    private final transient ImmutableMapEntry<K, V>[] table;

    /* loaded from: classes2.dex */
    public static class BucketOverflowException extends Exception {
    }

    @Override // com.google.common.collect.ImmutableMap
    public boolean isPartialView() {
        return false;
    }

    public static <K, V> ImmutableMap<K, V> fromEntries(Map.Entry<K, V>... entryArr) {
        return fromEntryArray(entryArr.length, entryArr, true);
    }

    public static <K, V> ImmutableMap<K, V> fromEntryArray(int i, Map.Entry<K, V>[] entryArr, boolean z) {
        Preconditions.checkPositionIndex(i, entryArr.length);
        if (i == 0) {
            return (ImmutableMap<K, V>) EMPTY;
        }
        try {
            return fromEntryArrayCheckingBucketOverflow(i, entryArr, z);
        } catch (BucketOverflowException unused) {
            return JdkBackedImmutableMap.create(i, entryArr, z);
        }
    }

    private static <K, V> ImmutableMap<K, V> fromEntryArrayCheckingBucketOverflow(int i, Map.Entry<K, V>[] entryArr, boolean z) throws BucketOverflowException {
        Map.Entry<K, V>[] createEntryArray = i == entryArr.length ? entryArr : ImmutableMapEntry.createEntryArray(i);
        int closedTableSize = Hashing.closedTableSize(i, MAX_LOAD_FACTOR);
        ImmutableMapEntry[] createEntryArray2 = ImmutableMapEntry.createEntryArray(closedTableSize);
        int i2 = closedTableSize - 1;
        IdentityHashMap identityHashMap = null;
        int i3 = 0;
        for (int i4 = i - 1; i4 >= 0; i4--) {
            Map.Entry entry = (Map.Entry) Objects.requireNonNull(entryArr[i4]);
            Object key = entry.getKey();
            Object value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            int smear = Hashing.smear(key.hashCode()) & i2;
            ImmutableMapEntry immutableMapEntry = createEntryArray2[smear];
            ImmutableMapEntry checkNoConflictInKeyBucket = checkNoConflictInKeyBucket(key, value, immutableMapEntry, z);
            if (checkNoConflictInKeyBucket == null) {
                if (immutableMapEntry == null) {
                    checkNoConflictInKeyBucket = makeImmutable(entry, key, value);
                } else {
                    checkNoConflictInKeyBucket = new ImmutableMapEntry.NonTerminalImmutableMapEntry(key, value, immutableMapEntry);
                }
                createEntryArray2[smear] = checkNoConflictInKeyBucket;
            } else {
                if (identityHashMap == null) {
                    identityHashMap = new IdentityHashMap();
                }
                identityHashMap.put(checkNoConflictInKeyBucket, true);
                i3++;
                if (createEntryArray == entryArr) {
                    createEntryArray = (Map.Entry[]) createEntryArray.clone();
                }
            }
            createEntryArray[i4] = checkNoConflictInKeyBucket;
        }
        if (identityHashMap != null) {
            createEntryArray = removeDuplicates(createEntryArray, i, i - i3, identityHashMap);
            if (Hashing.closedTableSize(createEntryArray.length, MAX_LOAD_FACTOR) != closedTableSize) {
                return fromEntryArrayCheckingBucketOverflow(createEntryArray.length, createEntryArray, true);
            }
        }
        return new RegularImmutableMap(createEntryArray, createEntryArray2, i2);
    }

    static <K, V> Map.Entry<K, V>[] removeDuplicates(Map.Entry<K, V>[] entryArr, int i, int i2, IdentityHashMap<Map.Entry<K, V>, Boolean> identityHashMap) {
        ImmutableMapEntry[] createEntryArray = ImmutableMapEntry.createEntryArray(i2);
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            Map.Entry<K, V> entry = entryArr[i4];
            Boolean bool = identityHashMap.get(entry);
            if (bool != null) {
                if (bool.booleanValue()) {
                    identityHashMap.put(entry, false);
                }
            }
            createEntryArray[i3] = entry;
            i3++;
        }
        return createEntryArray;
    }

    public static <K, V> ImmutableMapEntry<K, V> makeImmutable(Map.Entry<K, V> entry, K k, V v) {
        return (entry instanceof ImmutableMapEntry) && ((ImmutableMapEntry) entry).isReusable() ? (ImmutableMapEntry) entry : new ImmutableMapEntry<>(k, v);
    }

    public static <K, V> ImmutableMapEntry<K, V> makeImmutable(Map.Entry<K, V> entry) {
        return makeImmutable(entry, entry.getKey(), entry.getValue());
    }

    private RegularImmutableMap(Map.Entry<K, V>[] entryArr, @CheckForNull ImmutableMapEntry<K, V>[] immutableMapEntryArr, int i) {
        this.entries = entryArr;
        this.table = immutableMapEntryArr;
        this.mask = i;
    }

    public static <K, V> ImmutableMapEntry<K, V> checkNoConflictInKeyBucket(Object obj, Object obj2, @CheckForNull ImmutableMapEntry<K, V> immutableMapEntry, boolean z) throws BucketOverflowException {
        int i = 0;
        while (immutableMapEntry != null) {
            if (immutableMapEntry.getKey().equals(obj)) {
                if (!z) {
                    return immutableMapEntry;
                }
                String valueOf = String.valueOf(obj);
                String valueOf2 = String.valueOf(obj2);
                checkNoConflict(false, "key", immutableMapEntry, new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length()).append(valueOf).append("=").append(valueOf2).toString());
            }
            i++;
            if (i <= 8) {
                immutableMapEntry = immutableMapEntry.getNextInKeyBucket();
            } else {
                throw new BucketOverflowException();
            }
        }
        return null;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        return (V) get(obj, this.table, this.mask);
    }

    @CheckForNull
    public static <V> V get(@CheckForNull Object obj, @CheckForNull ImmutableMapEntry<?, V>[] immutableMapEntryArr, int i) {
        if (obj != null && immutableMapEntryArr != null) {
            for (ImmutableMapEntry<?, V> immutableMapEntry = immutableMapEntryArr[i & Hashing.smear(obj.hashCode())]; immutableMapEntry != null; immutableMapEntry = immutableMapEntry.getNextInKeyBucket()) {
                if (obj.equals(immutableMapEntry.getKey())) {
                    return immutableMapEntry.getValue();
                }
            }
        }
        return null;
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Map.Entry<K, V>[] entryArr;
        Preconditions.checkNotNull(biConsumer);
        for (Map.Entry<K, V> entry : this.entries) {
            biConsumer.accept((K) entry.getKey(), (V) entry.getValue());
        }
    }

    @Override // java.util.Map
    public int size() {
        return this.entries.length;
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet(this, this.entries);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> createKeySet() {
        return new KeySet(this);
    }

    /* loaded from: classes2.dex */
    private static final class KeySet<K> extends IndexedImmutableSet<K> {
        private final RegularImmutableMap<K, ?> map;

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        KeySet(RegularImmutableMap<K, ?> regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override // com.google.common.collect.IndexedImmutableSet
        public K get(int i) {
            return this.map.entries[i].getKey();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        /* loaded from: classes2.dex */
        private static class SerializedForm<K> implements Serializable {
            private static final long serialVersionUID = 0;
            final ImmutableMap<K, ?> map;

            SerializedForm(ImmutableMap<K, ?> immutableMap) {
                this.map = immutableMap;
            }

            Object readResolve() {
                return this.map.keySet();
            }
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> createValues() {
        return new Values(this);
    }

    /* loaded from: classes2.dex */
    private static final class Values<K, V> extends ImmutableList<V> {
        final RegularImmutableMap<K, V> map;

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        Values(RegularImmutableMap<K, V> regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override // java.util.List
        public V get(int i) {
            return this.map.entries[i].getValue();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.map.size();
        }

        /* loaded from: classes2.dex */
        private static class SerializedForm<V> implements Serializable {
            private static final long serialVersionUID = 0;
            final ImmutableMap<?, V> map;

            SerializedForm(ImmutableMap<?, V> immutableMap) {
                this.map = immutableMap;
            }

            Object readResolve() {
                return this.map.values();
            }
        }
    }
}
