package com.google.common.collect;

import javax.annotation.CheckForNull;

/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public class ImmutableMapEntry<K, V> extends ImmutableEntry<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @CheckForNull
    public ImmutableMapEntry<K, V> getNextInKeyBucket() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @CheckForNull
    public ImmutableMapEntry<K, V> getNextInValueBucket() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isReusable() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> ImmutableMapEntry<K, V>[] createEntryArray(int i) {
        return new ImmutableMapEntry[i];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmutableMapEntry(K k, V v) {
        super(k, v);
        CollectPreconditions.checkEntryNotNull(k, v);
    }

    ImmutableMapEntry(ImmutableMapEntry<K, V> immutableMapEntry) {
        super(immutableMapEntry.getKey(), immutableMapEntry.getValue());
    }

    /* loaded from: classes2.dex */
    static class NonTerminalImmutableMapEntry<K, V> extends ImmutableMapEntry<K, V> {
        @CheckForNull
        private final transient ImmutableMapEntry<K, V> nextInKeyBucket;

        @Override // com.google.common.collect.ImmutableMapEntry
        final boolean isReusable() {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public NonTerminalImmutableMapEntry(K k, V v, @CheckForNull ImmutableMapEntry<K, V> immutableMapEntry) {
            super(k, v);
            this.nextInKeyBucket = immutableMapEntry;
        }

        @Override // com.google.common.collect.ImmutableMapEntry
        @CheckForNull
        final ImmutableMapEntry<K, V> getNextInKeyBucket() {
            return this.nextInKeyBucket;
        }
    }

    /* loaded from: classes2.dex */
    static final class NonTerminalImmutableBiMapEntry<K, V> extends NonTerminalImmutableMapEntry<K, V> {
        @CheckForNull
        private final transient ImmutableMapEntry<K, V> nextInValueBucket;

        /* JADX INFO: Access modifiers changed from: package-private */
        public NonTerminalImmutableBiMapEntry(K k, V v, @CheckForNull ImmutableMapEntry<K, V> immutableMapEntry, @CheckForNull ImmutableMapEntry<K, V> immutableMapEntry2) {
            super(k, v, immutableMapEntry);
            this.nextInValueBucket = immutableMapEntry2;
        }

        @Override // com.google.common.collect.ImmutableMapEntry
        @CheckForNull
        ImmutableMapEntry<K, V> getNextInValueBucket() {
            return this.nextInValueBucket;
        }
    }
}
