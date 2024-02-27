package com.google.common.util.concurrent;

@ElementTypesAreNonnullByDefault
@FunctionalInterface
/* loaded from: classes2.dex */
public interface AsyncFunction<I, O> {
    ListenableFuture<O> apply(@ParametricNullness I i) throws Exception;
}
