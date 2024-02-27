package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public class AtomicDouble extends Number implements Serializable {
    private static final long serialVersionUID = 0;
    private static final AtomicLongFieldUpdater<AtomicDouble> updater = AtomicLongFieldUpdater.newUpdater(AtomicDouble.class, "value");
    private volatile transient long value;

    public AtomicDouble(double d) {
        this.value = Double.doubleToRawLongBits(d);
    }

    public AtomicDouble() {
    }

    public final double get() {
        return Double.longBitsToDouble(this.value);
    }

    public final void set(double d) {
        this.value = Double.doubleToRawLongBits(d);
    }

    public final void lazySet(double d) {
        updater.lazySet(this, Double.doubleToRawLongBits(d));
    }

    public final double getAndSet(double d) {
        return Double.longBitsToDouble(updater.getAndSet(this, Double.doubleToRawLongBits(d)));
    }

    public final boolean compareAndSet(double d, double d2) {
        return updater.compareAndSet(this, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(double d, double d2) {
        return updater.weakCompareAndSet(this, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final double getAndAdd(double d) {
        return getAndAccumulate(d, new AtomicDouble$$ExternalSyntheticLambda2());
    }

    public final double addAndGet(double d) {
        return accumulateAndGet(d, new AtomicDouble$$ExternalSyntheticLambda2());
    }

    public final double getAndAccumulate(final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        Preconditions.checkNotNull(doubleBinaryOperator);
        return getAndUpdate(new DoubleUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicDouble$$ExternalSyntheticLambda1
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return AtomicDouble.lambda$getAndAccumulate$0(doubleBinaryOperator, d, d2);
            }
        });
    }

    public static /* synthetic */ double lambda$getAndAccumulate$0(DoubleBinaryOperator doubleBinaryOperator, double d, double d2) {
        return doubleBinaryOperator.applyAsDouble(d2, d);
    }

    public final double accumulateAndGet(final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        Preconditions.checkNotNull(doubleBinaryOperator);
        return updateAndGet(new DoubleUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicDouble$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return AtomicDouble.lambda$accumulateAndGet$1(doubleBinaryOperator, d, d2);
            }
        });
    }

    public static /* synthetic */ double lambda$accumulateAndGet$1(DoubleBinaryOperator doubleBinaryOperator, double d, double d2) {
        return doubleBinaryOperator.applyAsDouble(d2, d);
    }

    public final double getAndUpdate(DoubleUnaryOperator doubleUnaryOperator) {
        long j;
        double longBitsToDouble;
        do {
            j = this.value;
            longBitsToDouble = Double.longBitsToDouble(j);
        } while (!updater.compareAndSet(this, j, Double.doubleToRawLongBits(doubleUnaryOperator.applyAsDouble(longBitsToDouble))));
        return longBitsToDouble;
    }

    public final double updateAndGet(DoubleUnaryOperator doubleUnaryOperator) {
        long j;
        double applyAsDouble;
        do {
            j = this.value;
            applyAsDouble = doubleUnaryOperator.applyAsDouble(Double.longBitsToDouble(j));
        } while (!updater.compareAndSet(this, j, Double.doubleToRawLongBits(applyAsDouble)));
        return applyAsDouble;
    }

    public String toString() {
        return Double.toString(get());
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) get();
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) get();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) get();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return get();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(get());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        set(objectInputStream.readDouble());
    }
}
