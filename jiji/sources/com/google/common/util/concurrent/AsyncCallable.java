package com.google.common.util.concurrent;

@ElementTypesAreNonnullByDefault
@FunctionalInterface
/* loaded from: classes2.dex */
public interface AsyncCallable<V> {
    ListenableFuture<V> call() throws Exception;
}
