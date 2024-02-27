package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMapEntrySet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public final class JdkBackedImmutableMap<K, V> extends ImmutableMap<K, V> {
    private final transient Map<K, V> delegateMap;
    private final transient ImmutableList<Map.Entry<K, V>> entries;

    @Override // com.google.common.collect.ImmutableMap
    public boolean isPartialView() {
        return false;
    }

    public static <K, V> ImmutableMap<K, V> create(int i, Map.Entry<K, V>[] entryArr, boolean z) {
        HashMap newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(i);
        HashMap hashMap = null;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            entryArr[i3] = RegularImmutableMap.makeImmutable((Map.Entry) Objects.requireNonNull(entryArr[i3]));
            K key = entryArr[i3].getKey();
            V value = entryArr[i3].getValue();
            Object put = newHashMapWithExpectedSize.put(key, value);
            if (put != null) {
                if (z) {
                    Map.Entry<K, V> entry = entryArr[i3];
                    String valueOf = String.valueOf(entry.getKey());
                    String valueOf2 = String.valueOf(put);
                    throw conflictException("key", entry, new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length()).append(valueOf).append("=").append(valueOf2).toString());
                }
                if (hashMap == null) {
                    hashMap = new HashMap();
                }
                hashMap.put(key, value);
                i2++;
            }
        }
        if (hashMap != null) {
            Map.Entry<K, V>[] entryArr2 = new Map.Entry[i - i2];
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                Map.Entry<K, V> entry2 = (Map.Entry) Objects.requireNonNull(entryArr[i5]);
                K key2 = entry2.getKey();
                if (hashMap.containsKey(key2)) {
                    Object obj = hashMap.get(key2);
                    if (obj != null) {
                        ImmutableMapEntry immutableMapEntry = new ImmutableMapEntry(key2, obj);
                        hashMap.put(key2, null);
                        entry2 = immutableMapEntry;
                    }
                }
                entryArr2[i4] = entry2;
                i4++;
            }
            entryArr = entryArr2;
        }
        return new JdkBackedImmutableMap(newHashMapWithExpectedSize, ImmutableList.asImmutableList(entryArr, i));
    }

    JdkBackedImmutableMap(Map<K, V> map, ImmutableList<Map.Entry<K, V>> immutableList) {
        this.delegateMap = map;
        this.entries = immutableList;
    }

    @Override // java.util.Map
    public int size() {
        return this.entries.size();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        return this.delegateMap.get(obj);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet(this, this.entries);
    }

    @Override // java.util.Map
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        this.entries.forEach(new Consumer() { // from class: com.google.common.collect.JdkBackedImmutableMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                biConsumer.accept(r2.getKey(), ((Map.Entry) obj).getValue());
            }
        });
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> createKeySet() {
        return new ImmutableMapKeySet(this);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> createValues() {
        return new ImmutableMapValues(this);
    }
}
