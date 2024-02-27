package com.serenegiant.math;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public class Fraction implements Parcelable {
    public static final Parcelable.Creator<Fraction> CREATOR;
    private static final double DEFAULT_EPS = 1.0E-5d;
    public static final Fraction ONE_FIFTH;
    public static final Fraction ONE_FOURTH;
    public static final Fraction ONE_QUARTER;
    public static final Fraction THREE_FOURTH;
    public static final Fraction THREE_QUARTER;
    public static final Fraction TWO_THIRD;
    private int mDenominator;
    private int mNumerator;
    public static final Fraction ZERO = unmodifiableFraction(0, 1);
    public static final Fraction ONE = unmodifiableFraction(1, 1);
    public static final Fraction MINUS = unmodifiableFraction(-1, 1);
    public static final Fraction TWO = unmodifiableFraction(2, 1);
    public static final Fraction THREE = unmodifiableFraction(3, 1);
    public static final Fraction FOUR = unmodifiableFraction(4, 1);
    public static final Fraction FIVE = unmodifiableFraction(5, 1);
    public static final Fraction TEN = unmodifiableFraction(10, 1);
    public static final Fraction ONE_HALF = unmodifiableFraction(1, 2);
    public static final Fraction ONE_THIRD = unmodifiableFraction(1, 3);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        Fraction unmodifiableFraction = unmodifiableFraction(1, 4);
        ONE_QUARTER = unmodifiableFraction;
        ONE_FOURTH = unmodifiableFraction;
        ONE_FIFTH = unmodifiableFraction(1, 5);
        TWO_THIRD = unmodifiableFraction(2, 3);
        Fraction unmodifiableFraction2 = unmodifiableFraction(3, 4);
        THREE_QUARTER = unmodifiableFraction2;
        THREE_FOURTH = unmodifiableFraction2;
        CREATOR = new Parcelable.Creator<Fraction>() { // from class: com.serenegiant.math.Fraction.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Fraction createFromParcel(Parcel parcel) {
                return new Fraction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Fraction[] newArray(int i) {
                return new Fraction[i];
            }
        };
    }

    public Fraction() {
        this.mDenominator = 1;
    }

    public Fraction(int i) {
        this.mNumerator = i;
        this.mDenominator = 1;
    }

    public Fraction(double d) {
        this(d, 1.0E-5d, Integer.MAX_VALUE, 100);
    }

    public Fraction(double d, double d2) {
        this(d, d2, Integer.MAX_VALUE, 100);
    }

    private Fraction(double d, double d2, int i, int i2) {
        long j;
        long j2;
        long j3;
        long floor = (long) Math.floor(d);
        if (Math.abs(floor) > 2147483647L) {
            throw new IllegalArgumentException(String.format("Failed to create Fraction, v=%f,%d,%d)", Double.valueOf(d), Long.valueOf(floor), 1L));
        }
        int i3 = 1;
        if (Math.abs(floor - d) < d2) {
            this.mNumerator = (int) floor;
            this.mDenominator = 1;
        }
        int i4 = 0;
        double d3 = d;
        long j4 = 1;
        long j5 = 0;
        boolean z = false;
        long j6 = 1;
        long j7 = floor;
        while (true) {
            i4 += i3;
            boolean z2 = z;
            double d4 = 1.0d / (d3 - floor);
            long floor2 = (long) Math.floor(d4);
            j = (floor2 * j7) + j6;
            long j8 = floor;
            j2 = (floor2 * j4) + j5;
            if (Math.abs(j) > 2147483647L || Math.abs(j2) > 2147483647L) {
                break;
            }
            long j9 = j7;
            double d5 = j / j2;
            if (i4 >= i2 || Math.abs(d5 - d) <= d2 || j2 >= i) {
                j7 = j9;
                z = true;
            } else {
                j7 = j;
                j5 = j4;
                z = z2;
                d3 = d4;
                j6 = j9;
                j8 = floor2;
                j4 = j2;
            }
            if (z) {
                j3 = j4;
                break;
            } else {
                floor = j8;
                i3 = 1;
            }
        }
        long j10 = j7;
        if (d2 != 0.0d || Math.abs(j4) >= i) {
            throw new IllegalArgumentException(String.format("Failed to create Fraction, v=%f,%d,%d)", Double.valueOf(d), Long.valueOf(j), Long.valueOf(j2)));
        }
        j3 = j4;
        j7 = j10;
        if (i4 >= i2) {
            throw new IllegalArgumentException(String.format("Failed to create Fraction, v=%f,%d,%d)", Double.valueOf(d), Long.valueOf(j), Long.valueOf(j2)));
        }
        if (j2 < i) {
            this.mNumerator = (int) j;
            this.mDenominator = (int) j2;
            return;
        }
        this.mNumerator = (int) j7;
        this.mDenominator = (int) j3;
    }

    public Fraction(int i, int i2) throws IllegalArgumentException {
        if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("numerator/denominator should not MIN_VALUE.");
        }
        if (i2 < 0) {
            this.mNumerator = -i;
            this.mDenominator = -i2;
        } else if (i2 > 0) {
            this.mNumerator = i;
            this.mDenominator = i2;
        } else {
            throw new IllegalArgumentException("denominator should not zero/MIN_VALUE.");
        }
    }

    public Fraction(Fraction fraction) {
        if (fraction == null) {
            this.mNumerator = 0;
            this.mDenominator = 1;
            return;
        }
        this.mNumerator = fraction.mNumerator;
        this.mDenominator = fraction.mDenominator;
    }

    protected Fraction(Parcel parcel) {
        this.mNumerator = parcel.readInt();
        this.mDenominator = parcel.readInt();
    }

    public int numerator() {
        return this.mNumerator;
    }

    public int denominator() {
        return this.mDenominator;
    }

    public float asFloat() {
        return this.mNumerator / this.mDenominator;
    }

    public double asDouble() {
        return this.mNumerator / this.mDenominator;
    }

    public String asString() {
        return this.mNumerator + "/" + this.mDenominator;
    }

    public Fraction dup() {
        return new Fraction(this);
    }

    public int sign() throws IllegalArgumentException {
        return Integer.signum(this.mNumerator) * Integer.signum(this.mDenominator);
    }

    public Fraction reset() {
        this.mNumerator = 0;
        this.mDenominator = 1;
        return this;
    }

    public Fraction reduced() {
        int gcd = gcd(this.mNumerator, this.mDenominator);
        int i = this.mNumerator / gcd;
        this.mNumerator = i;
        int i2 = this.mDenominator / gcd;
        this.mDenominator = i2;
        if (i2 < 0) {
            this.mNumerator = -i;
            this.mDenominator = -i2;
        }
        return this;
    }

    public Fraction abs() {
        this.mNumerator = Math.abs(this.mNumerator);
        this.mDenominator = Math.abs(this.mDenominator);
        return reduced();
    }

    public Fraction invert() {
        int i = this.mNumerator;
        int i2 = ((i * this.mDenominator) > 0L ? 1 : ((i * this.mDenominator) == 0L ? 0 : -1));
        if (i2 == 0) {
            this.mNumerator = 0;
        } else if (i2 == 0) {
            this.mNumerator = 0;
        } else if (i2 > 0) {
            this.mNumerator = -Math.abs(i);
        } else {
            this.mNumerator = Math.abs(i);
        }
        this.mDenominator = Math.abs(this.mDenominator);
        return reduced();
    }

    public Fraction add(Fraction fraction) {
        int i = fraction.mDenominator;
        int i2 = fraction.mNumerator;
        int i3 = this.mDenominator;
        this.mNumerator = (this.mNumerator * i) + (i2 * i3);
        this.mDenominator = i3 * i;
        return reduced();
    }

    public Fraction sub(Fraction fraction) {
        int i = fraction.mDenominator;
        int i2 = fraction.mNumerator;
        int i3 = this.mDenominator;
        this.mNumerator = (this.mNumerator * i) - (i2 * i3);
        this.mDenominator = i3 * i;
        return reduced();
    }

    public Fraction multiply(Fraction fraction) {
        this.mNumerator *= fraction.mNumerator;
        this.mDenominator *= fraction.mDenominator;
        return reduced();
    }

    public Fraction multiply(int i) {
        this.mNumerator *= i;
        return reduced();
    }

    public Fraction div(Fraction fraction) {
        this.mNumerator *= fraction.mDenominator;
        this.mDenominator *= fraction.mNumerator;
        return reduced();
    }

    public Fraction div(int i) {
        this.mDenominator *= i;
        return reduced();
    }

    public Fraction absFraction() {
        return reducedFraction(Math.abs(this.mNumerator), Math.abs(this.mDenominator));
    }

    public Fraction invertFraction() {
        int i = this.mNumerator;
        int i2 = ((i * this.mDenominator) > 0L ? 1 : ((i * this.mDenominator) == 0L ? 0 : -1));
        int i3 = 0;
        if (i2 != 0 && i2 != 0) {
            if (i2 > 0) {
                i3 = -Math.abs(i);
            } else {
                i3 = Math.abs(i);
            }
        }
        return reducedFraction(i3, Math.abs(this.mDenominator));
    }

    public Fraction reducedFraction() {
        return reducedFraction(this.mNumerator, this.mDenominator);
    }

    public Fraction addFraction(Fraction fraction) {
        int i = fraction.mDenominator;
        int i2 = fraction.mNumerator;
        int i3 = this.mDenominator;
        return reducedFraction((this.mNumerator * i) + (i2 * i3), i3 * i);
    }

    public Fraction subFraction(Fraction fraction) {
        int i = fraction.mDenominator;
        int i2 = fraction.mNumerator;
        int i3 = this.mDenominator;
        return reducedFraction((this.mNumerator * i) - (i2 * i3), i3 * i);
    }

    public Fraction multiplyFraction(Fraction fraction) {
        return reducedFraction(this.mNumerator * fraction.mNumerator, this.mDenominator * fraction.mDenominator);
    }

    public Fraction multiplyFraction(int i) {
        return reducedFraction(this.mNumerator * i, this.mDenominator);
    }

    public Fraction divFraction(Fraction fraction) {
        return reducedFraction(this.mNumerator * fraction.mDenominator, this.mDenominator * fraction.mNumerator);
    }

    public Fraction divFraction(int i) {
        return reducedFraction(this.mNumerator, this.mDenominator * i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Fraction) {
            Fraction fraction = (Fraction) obj;
            return this.mNumerator == fraction.mNumerator && this.mDenominator == fraction.mDenominator;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mNumerator), Integer.valueOf(this.mDenominator));
    }

    public String toString() {
        return "Fraction{numerator=" + this.mNumerator + ", denominator=" + this.mDenominator + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mNumerator);
        parcel.writeInt(this.mDenominator);
    }

    public static Fraction unmodifiableFraction(Fraction fraction) {
        return unmodifiableFraction(fraction.mNumerator, fraction.mDenominator);
    }

    public static Fraction unmodifiableFraction(int i, int i2) {
        return new Fraction(i, i2) { // from class: com.serenegiant.math.Fraction.1
            @Override // com.serenegiant.math.Fraction
            public Fraction reset() {
                throw new UnsupportedOperationException("Can't modify this Fraction instance.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction abs() {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #absFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction invert() {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #invertFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction reduced() {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #reducedFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction add(Fraction fraction) {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #addFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction sub(Fraction fraction) {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #subFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction multiply(Fraction fraction) {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #multiplyFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction multiply(int i3) {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #multiplyFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction div(Fraction fraction) {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #divFraction instead.");
            }

            @Override // com.serenegiant.math.Fraction
            public Fraction div(int i3) {
                throw new UnsupportedOperationException("Can't modify this Fraction instance, use #divFraction instead.");
            }
        };
    }

    private static Fraction reducedFraction(int i, int i2) throws IllegalArgumentException {
        if (i2 != 0) {
            if (i == 0) {
                return new Fraction(ZERO);
            }
            int gcd = gcd(i, i2);
            return new Fraction(i / gcd, i2 / gcd);
        }
        throw new IllegalArgumentException("denominator should not zero");
    }

    private static int gcd(int i, int i2) {
        int abs = Math.abs(i);
        int abs2 = Math.abs(i2);
        while (true) {
            int i3 = abs2;
            int i4 = abs;
            abs = i3;
            if (abs <= 0) {
                return i4;
            }
            abs2 = i4 % abs;
        }
    }
}
