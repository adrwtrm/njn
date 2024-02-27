package com.google.common.base;

@FunctionalInterface
@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public interface Supplier<T> extends java.util.function.Supplier<T> {
    @Override // java.util.function.Supplier
    @ParametricNullness
    T get();
}
