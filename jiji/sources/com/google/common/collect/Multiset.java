package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public interface Multiset<E> extends Collection<E> {

    /* loaded from: classes2.dex */
    public interface Entry<E> {
        boolean equals(@CheckForNull Object obj);

        int getCount();

        @ParametricNullness
        E getElement();

        int hashCode();

        String toString();
    }

    int add(@ParametricNullness E e, int i);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean add(@ParametricNullness E e);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean contains(@CheckForNull Object obj);

    @Override // java.util.Collection
    boolean containsAll(Collection<?> collection);

    int count(@CheckForNull Object obj);

    Set<E> elementSet();

    Set<Entry<E>> entrySet();

    @Override // com.google.common.collect.Multiset
    boolean equals(@CheckForNull Object obj);

    @Override // com.google.common.collect.Multiset
    int hashCode();

    @Override // java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    Iterator<E> iterator();

    int remove(@CheckForNull Object obj, int i);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean remove(@CheckForNull Object obj);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean removeAll(Collection<?> collection);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean retainAll(Collection<?> collection);

    int setCount(@ParametricNullness E e, int i);

    boolean setCount(@ParametricNullness E e, int i, int i2);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    int size();

    String toString();

    default void forEachEntry(final ObjIntConsumer<? super E> objIntConsumer) {
        Preconditions.checkNotNull(objIntConsumer);
        entrySet().forEach(new Consumer() { // from class: com.google.common.collect.Multiset$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Multiset.lambda$forEachEntry$0(objIntConsumer, (Multiset.Entry) obj);
            }
        });
    }

    static /* synthetic */ void lambda$forEachEntry$0(ObjIntConsumer objIntConsumer, Entry entry) {
        objIntConsumer.accept(entry.getElement(), entry.getCount());
    }

    @Override // com.google.common.collect.Multiset
    default void forEach(final Consumer<? super E> consumer) {
        Preconditions.checkNotNull(consumer);
        entrySet().forEach(new Consumer() { // from class: com.google.common.collect.Multiset$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Multiset.lambda$forEach$1(consumer, (Multiset.Entry) obj);
            }
        });
    }

    static /* synthetic */ void lambda$forEach$1(Consumer consumer, Entry entry) {
        Object element = entry.getElement();
        int count = entry.getCount();
        for (int i = 0; i < count; i++) {
            consumer.accept(element);
        }
    }

    @Override // java.lang.Iterable, com.google.common.collect.Multiset
    default Spliterator<E> spliterator() {
        return Multisets.spliteratorImpl(this);
    }
}
