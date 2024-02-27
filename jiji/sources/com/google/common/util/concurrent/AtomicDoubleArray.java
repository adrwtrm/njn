package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.primitives.ImmutableLongArray;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public class AtomicDoubleArray implements Serializable {
    private static final long serialVersionUID = 0;
    private transient AtomicLongArray longs;

    public AtomicDoubleArray(int i) {
        this.longs = new AtomicLongArray(i);
    }

    public AtomicDoubleArray(double[] dArr) {
        int length = dArr.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = Double.doubleToRawLongBits(dArr[i]);
        }
        this.longs = new AtomicLongArray(jArr);
    }

    public final int length() {
        return this.longs.length();
    }

    public final double get(int i) {
        return Double.longBitsToDouble(this.longs.get(i));
    }

    public final void set(int i, double d) {
        this.longs.set(i, Double.doubleToRawLongBits(d));
    }

    public final void lazySet(int i, double d) {
        this.longs.lazySet(i, Double.doubleToRawLongBits(d));
    }

    public final double getAndSet(int i, double d) {
        return Double.longBitsToDouble(this.longs.getAndSet(i, Double.doubleToRawLongBits(d)));
    }

    public final boolean compareAndSet(int i, double d, double d2) {
        return this.longs.compareAndSet(i, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(int i, double d, double d2) {
        return this.longs.weakCompareAndSet(i, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final double getAndAdd(int i, double d) {
        return getAndAccumulate(i, d, new AtomicDoubleArray$$ExternalSyntheticLambda1());
    }

    public double addAndGet(int i, double d) {
        return accumulateAndGet(i, d, new AtomicDoubleArray$$ExternalSyntheticLambda1());
    }

    public final double getAndAccumulate(int i, final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        Preconditions.checkNotNull(doubleBinaryOperator);
        return getAndUpdate(i, new DoubleUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicDoubleArray$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return AtomicDoubleArray.lambda$getAndAccumulate$0(doubleBinaryOperator, d, d2);
            }
        });
    }

    public static /* synthetic */ double lambda$getAndAccumulate$0(DoubleBinaryOperator doubleBinaryOperator, double d, double d2) {
        return doubleBinaryOperator.applyAsDouble(d2, d);
    }

    public final double accumulateAndGet(int i, final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        Preconditions.checkNotNull(doubleBinaryOperator);
        return updateAndGet(i, new DoubleUnaryOperator() { // from class: com.google.common.util.concurrent.AtomicDoubleArray$$ExternalSyntheticLambda2
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                double applyAsDouble;
                applyAsDouble = doubleBinaryOperator.applyAsDouble(d2, d);
                return applyAsDouble;
            }
        });
    }

    public final double getAndUpdate(int i, DoubleUnaryOperator doubleUnaryOperator) {
        long j;
        double longBitsToDouble;
        do {
            j = this.longs.get(i);
            longBitsToDouble = Double.longBitsToDouble(j);
        } while (!this.longs.compareAndSet(i, j, Double.doubleToRawLongBits(doubleUnaryOperator.applyAsDouble(longBitsToDouble))));
        return longBitsToDouble;
    }

    public final double updateAndGet(int i, DoubleUnaryOperator doubleUnaryOperator) {
        long j;
        double applyAsDouble;
        do {
            j = this.longs.get(i);
            applyAsDouble = doubleUnaryOperator.applyAsDouble(Double.longBitsToDouble(j));
        } while (!this.longs.compareAndSet(i, j, Double.doubleToRawLongBits(applyAsDouble)));
        return applyAsDouble;
    }

    public String toString() {
        int length = length() - 1;
        if (length == -1) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder((length + 1) * 19);
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(Double.longBitsToDouble(this.longs.get(i)));
            if (i == length) {
                return sb.append(']').toString();
            }
            sb.append(", ");
            i++;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int length = length();
        objectOutputStream.writeInt(length);
        for (int i = 0; i < length; i++) {
            objectOutputStream.writeDouble(get(i));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        ImmutableLongArray.Builder builder = ImmutableLongArray.builder();
        for (int i = 0; i < readInt; i++) {
            builder.add(Double.doubleToRawLongBits(objectInputStream.readDouble()));
        }
        this.longs = new AtomicLongArray(builder.build().toArray());
    }
}
