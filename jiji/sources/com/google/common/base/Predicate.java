package com.google.common.base;

import javax.annotation.CheckForNull;

@FunctionalInterface
@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public interface Predicate<T> extends java.util.function.Predicate<T> {
    boolean apply(@ParametricNullness T t);

    boolean equals(@CheckForNull Object obj);

    @Override // java.util.function.Predicate
    default boolean test(@ParametricNullness T t) {
        return apply(t);
    }
}
