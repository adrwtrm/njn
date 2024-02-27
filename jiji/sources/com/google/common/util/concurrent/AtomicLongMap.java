package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public final class AtomicLongMap<K> implements Serializable {
    @CheckForNull
    private transient Map<K, Long> asMap;
    private final ConcurrentHashMap<K, Long> map;

    public static /* synthetic */ long $r8$lambda$uAjrY3NI4r9F1o9CaCMzj_QnUcY(Long l) {
        return l.longValue();
    }

    public static /* synthetic */ long lambda$put$4(long j, long j2) {
        return j;
    }

    private AtomicLongMap(ConcurrentHashMap<K, Long> concurrentHashMap) {
        this.map = (ConcurrentHashMap) Preconditions.checkNotNull(concurrentHashMap);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap<>(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> map) {
        AtomicLongMap<K> create = create();
        create.putAll(map);
        return create;
    }

    public long get(K k) {
        return this.map.getOrDefault(k, 0L).longValue();
    }

    public long incrementAndGet(K k) {
        return addAndGet(k, 1L);
    }

    public long decrementAndGet(K k) {
        return addAndGet(k, -1L);
    }

    public long addAndGet(K k, long j) {
        return accumulateAndGet(k, j, new AtomicLongMap$$ExternalSyntheticLambda4());
    }

    public long getAndIncrement(K k) {
        return getAndAdd(k, 1L);
    }

    public long getAndDecrement(K k) {
        return getAndAdd(k, -1L);
    }

    public long getAndAdd(K k, long j) {
        return getAndAccumulate(k, j, new AtomicLongMap$$ExternalSyntheticLambda4());
    }

    public long updateAndGet(K k, final LongUnaryOperator longUnaryOperator) {
        Preconditions.checkNotNull(longUnaryOperator);
        return this.map.compute(k, new BiFunction() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AtomicLongMap.lambda$updateAndGet$0(longUnaryOperator, obj, (Long) obj2);
            }
        }).longValue();
    }

    public static /* synthetic */ Long lambda$updateAndGet$0(LongUnaryOperator longUnaryOperator, Object obj, Long l) {
        return Long.valueOf(longUnaryOperator.applyAsLong(l == null ? 0L : l.longValue()));
    }

    public long getAndUpdate(K k, final LongUnaryOperator longUnaryOperator) {
        Preconditions.checkNotNull(longUnaryOperator);
        final AtomicLong atomicLong = new AtomicLong();
        this.map.compute(k, new BiFunction() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AtomicLongMap.lambda$getAndUpdate$1(atomicLong, longUnaryOperator, obj, (Long) obj2);
            }
        });
        return atomicLong.get();
    }

    public static /* synthetic */ Long lambda$getAndUpdate$1(AtomicLong atomicLong, LongUnaryOperator longUnaryOperator, Object obj, Long l) {
        long longValue = l == null ? 0L : l.longValue();
        atomicLong.set(longValue);
        return Long.valueOf(longUnaryOperator.applyAsLong(longValue));
    }

    public long accumulateAndGet(K k, final long j, final LongBinaryOperator longBinaryOperator) {
        Preconditions.checkNotNull(longBinaryOperator);
        return updateAndGet(k, new LongUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda6
            @Override // java.util.function.LongUnaryOperator
            public final long applyAsLong(long j2) {
                return AtomicLongMap.lambda$accumulateAndGet$2(longBinaryOperator, j, j2);
            }
        });
    }

    public static /* synthetic */ long lambda$accumulateAndGet$2(LongBinaryOperator longBinaryOperator, long j, long j2) {
        return longBinaryOperator.applyAsLong(j2, j);
    }

    public long getAndAccumulate(K k, final long j, final LongBinaryOperator longBinaryOperator) {
        Preconditions.checkNotNull(longBinaryOperator);
        return getAndUpdate(k, new LongUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda9
            @Override // java.util.function.LongUnaryOperator
            public final long applyAsLong(long j2) {
                return AtomicLongMap.lambda$getAndAccumulate$3(longBinaryOperator, j, j2);
            }
        });
    }

    public static /* synthetic */ long lambda$getAndAccumulate$3(LongBinaryOperator longBinaryOperator, long j, long j2) {
        return longBinaryOperator.applyAsLong(j2, j);
    }

    public long put(K k, final long j) {
        return getAndUpdate(k, new LongUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda0
            @Override // java.util.function.LongUnaryOperator
            public final long applyAsLong(long j2) {
                return AtomicLongMap.lambda$put$4(j, j2);
            }
        });
    }

    public void putAll(Map<? extends K, ? extends Long> map) {
        map.forEach(new BiConsumer() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AtomicLongMap.this.put(obj, ((Long) obj2).longValue());
            }
        });
    }

    public long remove(K k) {
        Long remove = this.map.remove(k);
        if (remove == null) {
            return 0L;
        }
        return remove.longValue();
    }

    boolean remove(K k, long j) {
        return this.map.remove(k, Long.valueOf(j));
    }

    public boolean removeIfZero(K k) {
        return remove(k, 0L);
    }

    public static /* synthetic */ boolean lambda$removeAllZeros$5(Long l) {
        return l.longValue() == 0;
    }

    public void removeAllZeros() {
        this.map.values().removeIf(new Predicate() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AtomicLongMap.lambda$removeAllZeros$5((Long) obj);
            }
        });
    }

    public long sum() {
        return this.map.values().stream().mapToLong(new ToLongFunction() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda5
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return AtomicLongMap.$r8$lambda$uAjrY3NI4r9F1o9CaCMzj_QnUcY((Long) obj);
            }
        }).sum();
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map = this.asMap;
        if (map == null) {
            Map<K, Long> createAsMap = createAsMap();
            this.asMap = createAsMap;
            return createAsMap;
        }
        return map;
    }

    private Map<K, Long> createAsMap() {
        return Collections.unmodifiableMap(this.map);
    }

    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }

    long putIfAbsent(K k, final long j) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Long compute = this.map.compute(k, new BiFunction() { // from class: com.google.common.util.concurrent.AtomicLongMap$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AtomicLongMap.lambda$putIfAbsent$6(atomicBoolean, j, obj, (Long) obj2);
            }
        });
        if (atomicBoolean.get()) {
            return 0L;
        }
        return compute.longValue();
    }

    public static /* synthetic */ Long lambda$putIfAbsent$6(AtomicBoolean atomicBoolean, long j, Object obj, Long l) {
        if (l == null || l.longValue() == 0) {
            atomicBoolean.set(true);
            return Long.valueOf(j);
        }
        return l;
    }

    boolean replace(K k, long j, long j2) {
        if (j == 0) {
            return putIfAbsent(k, j2) == 0;
        }
        return this.map.replace(k, Long.valueOf(j), Long.valueOf(j2));
    }
}
