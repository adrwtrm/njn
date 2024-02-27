package com.google.common.cache;

@ElementTypesAreNonnullByDefault
@FunctionalInterface
/* loaded from: classes2.dex */
public interface Weigher<K, V> {
    int weigh(K k, V v);
}
