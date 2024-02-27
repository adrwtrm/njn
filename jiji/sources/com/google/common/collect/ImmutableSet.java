package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    private static final int CUTOFF = 751619276;
    private static final double DESIRED_LOAD_FACTOR = 0.7d;
    static final int MAX_TABLE_SIZE = 1073741824;
    static final int SPLITERATOR_CHARACTERISTICS = 1297;

    boolean isHashCodeFast() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public abstract UnmodifiableIterator<E> iterator();

    public static <E> Collector<E, ?, ImmutableSet<E>> toImmutableSet() {
        return CollectCollectors.toImmutableSet();
    }

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.EMPTY;
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new SingletonImmutableSet(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2) {
        return construct(2, 2, e, e2);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3) {
        return construct(3, 3, e, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4) {
        return construct(4, 4, e, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return construct(5, 5, e, e2, e3, e4, e5);
    }

    @SafeVarargs
    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        Preconditions.checkArgument(eArr.length <= 2147483641, "the total number of elements must fit in an int");
        int length = eArr.length + 6;
        Object[] objArr = new Object[length];
        objArr[0] = e;
        objArr[1] = e2;
        objArr[2] = e3;
        objArr[3] = e4;
        objArr[4] = e5;
        objArr[5] = e6;
        System.arraycopy(eArr, 0, objArr, 6, eArr.length);
        return construct(length, length, objArr);
    }

    private static <E> ImmutableSet<E> constructUnknownDuplication(int i, Object... objArr) {
        return construct(i, Math.max(4, IntMath.sqrt(i, RoundingMode.CEILING)), objArr);
    }

    private static <E> ImmutableSet<E> construct(int i, int i2, Object... objArr) {
        if (i != 0) {
            int i3 = 0;
            if (i == 1) {
                return of(objArr[0]);
            }
            RegularSetBuilderImpl regularSetBuilderImpl = new RegularSetBuilderImpl(i2);
            while (i3 < i) {
                i3++;
                regularSetBuilderImpl = regularSetBuilderImpl.add(Preconditions.checkNotNull(objArr[i3]));
            }
            return regularSetBuilderImpl.review().build();
        }
        return of();
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof SortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection;
            if (!immutableSet.isPartialView()) {
                return immutableSet;
            }
        } else if (collection instanceof EnumSet) {
            return copyOfEnumSet((EnumSet) collection);
        }
        Object[] array = collection.toArray();
        if (collection instanceof Set) {
            return construct(array.length, array.length, array);
        }
        return constructUnknownDuplication(array.length, array);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection) iterable);
        }
        return copyOf(iterable.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        E next = it.next();
        if (!it.hasNext()) {
            return of((Object) next);
        }
        return new Builder().add((Builder) next).addAll((Iterator) it).build();
    }

    public static <E> ImmutableSet<E> copyOf(E[] eArr) {
        int length = eArr.length;
        if (length != 0) {
            if (length == 1) {
                return of((Object) eArr[0]);
            }
            return constructUnknownDuplication(eArr.length, (Object[]) eArr.clone());
        }
        return of();
    }

    private static ImmutableSet copyOfEnumSet(EnumSet enumSet) {
        return ImmutableEnumSet.asImmutable(EnumSet.copyOf(enumSet));
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof ImmutableSet) && isHashCodeFast() && ((ImmutableSet) obj).isHashCodeFast() && hashCode() != obj.hashCode()) {
            return false;
        }
        return Sets.equalsImpl(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class CachingAsList<E> extends ImmutableSet<E> {
        @CheckForNull
        @LazyInit
        private transient ImmutableList<E> asList;

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // com.google.common.collect.ImmutableCollection
        public ImmutableList<E> asList() {
            ImmutableList<E> immutableList = this.asList;
            if (immutableList == null) {
                ImmutableList<E> createAsList = createAsList();
                this.asList = createAsList;
                return createAsList;
            }
            return immutableList;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ImmutableList<E> createAsList() {
            return new RegularImmutableAsList(this, toArray());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class Indexed<E> extends CachingAsList<E> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract E get(int i);

        @Override // com.google.common.collect.ImmutableSet.CachingAsList, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<E> iterator() {
            return asList().iterator();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
        public Spliterator<E> spliterator() {
            return CollectSpliterators.indexed(size(), ImmutableSet.SPLITERATOR_CHARACTERISTICS, new IntFunction() { // from class: com.google.common.collect.ImmutableSet$Indexed$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return ImmutableSet.Indexed.this.get(i);
                }
            });
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super E> consumer) {
            Preconditions.checkNotNull(consumer);
            int size = size();
            for (int i = 0; i < size; i++) {
                consumer.accept(get(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public int copyIntoArray(Object[] objArr, int i) {
            return asList().copyIntoArray(objArr, i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableSet.CachingAsList
        public ImmutableList<E> createAsList() {
            return new ImmutableAsList<E>() { // from class: com.google.common.collect.ImmutableSet.Indexed.1
                @Override // java.util.List
                public E get(int i) {
                    return (E) Indexed.this.get(i);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.google.common.collect.ImmutableAsList
                public Indexed<E> delegateCollection() {
                    return Indexed.this;
                }
            };
        }
    }

    /* loaded from: classes2.dex */
    private static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] elements;

        SerializedForm(Object[] objArr) {
            this.elements = objArr;
        }

        Object readResolve() {
            return ImmutableSet.copyOf(this.elements);
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new SerializedForm(toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static <E> Builder<E> builderWithExpectedSize(int i) {
        CollectPreconditions.checkNonnegative(i, "expectedSize");
        return new Builder<>(i);
    }

    /* loaded from: classes2.dex */
    public static class Builder<E> extends ImmutableCollection.Builder<E> {
        boolean forceCopy;
        @CheckForNull
        private SetBuilderImpl<E> impl;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj);
        }

        public Builder() {
            this(0);
        }

        Builder(int i) {
            if (i > 0) {
                this.impl = new RegularSetBuilderImpl(i);
            } else {
                this.impl = EmptySetBuilderImpl.instance();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Builder(boolean z) {
            this.impl = null;
        }

        void forceJdk() {
            Objects.requireNonNull(this.impl);
            this.impl = new JdkBackedSetBuilderImpl(this.impl);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final void copyIfNecessary() {
            if (this.forceCopy) {
                copy();
                this.forceCopy = false;
            }
        }

        void copy() {
            Objects.requireNonNull(this.impl);
            this.impl = this.impl.copy();
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E e) {
            Objects.requireNonNull(this.impl);
            Preconditions.checkNotNull(e);
            copyIfNecessary();
            this.impl = this.impl.add(e);
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E... eArr) {
            super.add((Object[]) eArr);
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll((Iterable) iterable);
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Builder<E> combine(Builder<E> builder) {
            Objects.requireNonNull(this.impl);
            Objects.requireNonNull(builder.impl);
            copyIfNecessary();
            this.impl = this.impl.combine(builder.impl);
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public ImmutableSet<E> build() {
            Objects.requireNonNull(this.impl);
            this.forceCopy = true;
            SetBuilderImpl<E> review = this.impl.review();
            this.impl = review;
            return review.build();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class SetBuilderImpl<E> {
        E[] dedupedElements;
        int distinct;

        abstract SetBuilderImpl<E> add(E e);

        abstract ImmutableSet<E> build();

        abstract SetBuilderImpl<E> copy();

        SetBuilderImpl<E> review() {
            return this;
        }

        SetBuilderImpl(int i) {
            this.dedupedElements = (E[]) new Object[i];
            this.distinct = 0;
        }

        SetBuilderImpl(SetBuilderImpl<E> setBuilderImpl) {
            E[] eArr = setBuilderImpl.dedupedElements;
            this.dedupedElements = (E[]) Arrays.copyOf(eArr, eArr.length);
            this.distinct = setBuilderImpl.distinct;
        }

        private void ensureCapacity(int i) {
            E[] eArr = this.dedupedElements;
            if (i > eArr.length) {
                this.dedupedElements = (E[]) Arrays.copyOf(this.dedupedElements, ImmutableCollection.Builder.expandedCapacity(eArr.length, i));
            }
        }

        final void addDedupedElement(E e) {
            ensureCapacity(this.distinct + 1);
            E[] eArr = this.dedupedElements;
            int i = this.distinct;
            this.distinct = i + 1;
            eArr[i] = e;
        }

        final SetBuilderImpl<E> combine(SetBuilderImpl<E> setBuilderImpl) {
            SetBuilderImpl<E> setBuilderImpl2 = this;
            for (int i = 0; i < setBuilderImpl.distinct; i++) {
                setBuilderImpl2 = setBuilderImpl2.add((E) Objects.requireNonNull(setBuilderImpl.dedupedElements[i]));
            }
            return setBuilderImpl2;
        }
    }

    /* loaded from: classes2.dex */
    private static final class EmptySetBuilderImpl<E> extends SetBuilderImpl<E> {
        private static final EmptySetBuilderImpl<Object> INSTANCE = new EmptySetBuilderImpl<>();

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> copy() {
            return this;
        }

        static <E> SetBuilderImpl<E> instance() {
            return INSTANCE;
        }

        private EmptySetBuilderImpl() {
            super(0);
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> add(E e) {
            return new RegularSetBuilderImpl(4).add(e);
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        ImmutableSet<E> build() {
            return ImmutableSet.of();
        }
    }

    static int chooseTableSize(int i) {
        int max = Math.max(i, 2);
        if (max < CUTOFF) {
            int highestOneBit = Integer.highestOneBit(max - 1) << 1;
            while (highestOneBit * DESIRED_LOAD_FACTOR < max) {
                highestOneBit <<= 1;
            }
            return highestOneBit;
        }
        Preconditions.checkArgument(max < 1073741824, "collection too large");
        return 1073741824;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class RegularSetBuilderImpl<E> extends SetBuilderImpl<E> {
        static final int MAX_RUN_MULTIPLIER = 13;
        private int expandTableThreshold;
        private int hashCode;
        private Object[] hashTable;
        private int maxRunBeforeFallback;

        RegularSetBuilderImpl(int i) {
            super(i);
            this.hashTable = null;
            this.maxRunBeforeFallback = 0;
            this.expandTableThreshold = 0;
        }

        RegularSetBuilderImpl(RegularSetBuilderImpl<E> regularSetBuilderImpl) {
            super(regularSetBuilderImpl);
            Object[] objArr = regularSetBuilderImpl.hashTable;
            this.hashTable = objArr == null ? null : (Object[]) objArr.clone();
            this.maxRunBeforeFallback = regularSetBuilderImpl.maxRunBeforeFallback;
            this.expandTableThreshold = regularSetBuilderImpl.expandTableThreshold;
            this.hashCode = regularSetBuilderImpl.hashCode;
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> add(E e) {
            Preconditions.checkNotNull(e);
            if (this.hashTable == null) {
                if (this.distinct == 0) {
                    addDedupedElement(e);
                    return this;
                }
                ensureTableCapacity(this.dedupedElements.length);
                this.distinct--;
                return insertInHashTable(this.dedupedElements[0]).add(e);
            }
            return insertInHashTable(e);
        }

        private SetBuilderImpl<E> insertInHashTable(E e) {
            Objects.requireNonNull(this.hashTable);
            int hashCode = e.hashCode();
            int smear = Hashing.smear(hashCode);
            int length = this.hashTable.length - 1;
            for (int i = smear; i - smear < this.maxRunBeforeFallback; i++) {
                int i2 = i & length;
                Object obj = this.hashTable[i2];
                if (obj == null) {
                    addDedupedElement(e);
                    this.hashTable[i2] = e;
                    this.hashCode += hashCode;
                    ensureTableCapacity(this.distinct);
                    return this;
                } else if (obj.equals(e)) {
                    return this;
                }
            }
            return new JdkBackedSetBuilderImpl(this).add(e);
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> copy() {
            return new RegularSetBuilderImpl(this);
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> review() {
            if (this.hashTable == null) {
                return this;
            }
            int chooseTableSize = ImmutableSet.chooseTableSize(this.distinct);
            if (chooseTableSize * 2 < this.hashTable.length) {
                this.hashTable = rebuildHashTable(chooseTableSize, this.dedupedElements, this.distinct);
                this.maxRunBeforeFallback = maxRunBeforeFallback(chooseTableSize);
                this.expandTableThreshold = (int) (chooseTableSize * ImmutableSet.DESIRED_LOAD_FACTOR);
            }
            return hashFloodingDetected(this.hashTable) ? new JdkBackedSetBuilderImpl(this) : this;
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        ImmutableSet<E> build() {
            Object[] copyOf;
            int i = this.distinct;
            if (i != 0) {
                if (i == 1) {
                    return ImmutableSet.of(Objects.requireNonNull(this.dedupedElements[0]));
                }
                if (this.distinct == this.dedupedElements.length) {
                    copyOf = this.dedupedElements;
                } else {
                    copyOf = Arrays.copyOf(this.dedupedElements, this.distinct);
                }
                return new RegularImmutableSet(copyOf, this.hashCode, (Object[]) Objects.requireNonNull(this.hashTable), this.hashTable.length - 1);
            }
            return ImmutableSet.of();
        }

        static Object[] rebuildHashTable(int i, Object[] objArr, int i2) {
            int i3;
            Object[] objArr2 = new Object[i];
            int i4 = i - 1;
            for (int i5 = 0; i5 < i2; i5++) {
                Object requireNonNull = Objects.requireNonNull(objArr[i5]);
                int smear = Hashing.smear(requireNonNull.hashCode());
                while (true) {
                    i3 = smear & i4;
                    if (objArr2[i3] == null) {
                        break;
                    }
                    smear++;
                }
                objArr2[i3] = requireNonNull;
            }
            return objArr2;
        }

        void ensureTableCapacity(int i) {
            int length;
            Object[] objArr = this.hashTable;
            if (objArr == null) {
                length = ImmutableSet.chooseTableSize(i);
                this.hashTable = new Object[length];
            } else if (i <= this.expandTableThreshold || objArr.length >= 1073741824) {
                return;
            } else {
                length = objArr.length * 2;
                this.hashTable = rebuildHashTable(length, this.dedupedElements, this.distinct);
            }
            this.maxRunBeforeFallback = maxRunBeforeFallback(length);
            this.expandTableThreshold = (int) (length * ImmutableSet.DESIRED_LOAD_FACTOR);
        }

        static boolean hashFloodingDetected(Object[] objArr) {
            int maxRunBeforeFallback = maxRunBeforeFallback(objArr.length);
            int length = objArr.length - 1;
            int i = 0;
            int i2 = 0;
            while (i < objArr.length) {
                if (i != i2 || objArr[i] != null) {
                    int i3 = i + maxRunBeforeFallback;
                    for (int i4 = i3 - 1; i4 >= i2; i4--) {
                        if (objArr[i4 & length] == null) {
                            i2 = i3;
                            i = i4 + 1;
                        }
                    }
                    return true;
                }
                i2 = i + maxRunBeforeFallback;
                if (objArr[(i2 - 1) & length] != null) {
                    i2 = i + 1;
                }
                i = i2;
            }
            return false;
        }

        static int maxRunBeforeFallback(int i) {
            return IntMath.log2(i, RoundingMode.UNNECESSARY) * 13;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class JdkBackedSetBuilderImpl<E> extends SetBuilderImpl<E> {
        private final Set<Object> delegate;

        JdkBackedSetBuilderImpl(SetBuilderImpl<E> setBuilderImpl) {
            super(setBuilderImpl);
            this.delegate = Sets.newHashSetWithExpectedSize(this.distinct);
            for (int i = 0; i < this.distinct; i++) {
                this.delegate.add(Objects.requireNonNull(this.dedupedElements[i]));
            }
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> add(E e) {
            Preconditions.checkNotNull(e);
            if (this.delegate.add(e)) {
                addDedupedElement(e);
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl<E> copy() {
            return new JdkBackedSetBuilderImpl(this);
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        ImmutableSet<E> build() {
            int i = this.distinct;
            if (i != 0) {
                if (i == 1) {
                    return ImmutableSet.of(Objects.requireNonNull(this.dedupedElements[0]));
                }
                return new JdkBackedImmutableSet(this.delegate, ImmutableList.asImmutableList(this.dedupedElements, this.distinct));
            }
            return ImmutableSet.of();
        }
    }
}
