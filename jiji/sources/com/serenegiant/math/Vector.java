package com.serenegiant.math;

import android.opengl.Matrix;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes2.dex */
public class Vector implements Parcelable, Serializable, Cloneable {
    public static final double TO_DEGREE = 57.29577951308232d;
    public static final double TO_RADIAN = 0.017453292519943295d;
    private static final long serialVersionUID = 1620440892067002860L;
    public float x;
    public float y;
    public float z;
    public static final Vector zeroVector = new Vector();
    public static final Vector normVector = new Vector(1.0f, 1.0f, 1.0f).normalize();
    private static final float[] matrix = new float[16];
    private static final float[] inVec = new float[4];
    private static final float[] outVec = new float[4];
    public static final Parcelable.Creator<Vector> CREATOR = new Parcelable.Creator<Vector>() { // from class: com.serenegiant.math.Vector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vector createFromParcel(Parcel parcel) {
            return new Vector(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vector[] newArray(int i) {
            return new Vector[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vector() {
    }

    public Vector(float f, float f2) {
        this(f, f2, 0.0f);
    }

    public Vector(Vector vector) {
        this(vector.x, vector.y, vector.z);
    }

    public Vector(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    protected Vector(Parcel parcel) {
        this.x = parcel.readFloat();
        this.y = parcel.readFloat();
        this.z = parcel.readFloat();
    }

    /* renamed from: clone */
    public Vector m273clone() throws CloneNotSupportedException {
        return (Vector) super.clone();
    }

    public Vector clear(float f) {
        this.z = f;
        this.y = f;
        this.x = f;
        return this;
    }

    public Vector set(float f, float f2) {
        return set(f, f2, 0.0f);
    }

    public Vector set(Vector vector) {
        if (this != vector) {
            set(vector.x, vector.y, vector.z);
        }
        return this;
    }

    public Vector set(Vector vector, float f) {
        return set(vector.x, vector.y, vector.z, f);
    }

    public Vector set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        return this;
    }

    public Vector set(float f, float f2, float f3, float f4) {
        this.x = f * f4;
        this.y = f2 * f4;
        this.z = f3 * f4;
        return this;
    }

    public float x() {
        return this.x;
    }

    public Vector x(float f) {
        this.x = f;
        return this;
    }

    public float y() {
        return this.y;
    }

    public Vector y(float f) {
        this.y = f;
        return this;
    }

    public float z() {
        return this.z;
    }

    public Vector z(float f) {
        this.z = f;
        return this;
    }

    public Vector add(float f, float f2) {
        return add(f, f2, 0.0d);
    }

    public Vector add(double d, double d2, double d3) {
        this.x = (float) (this.x + d);
        this.y = (float) (this.y + d2);
        this.z = (float) (this.z + d3);
        return this;
    }

    public Vector add(double d, double d2, double d3, double d4) {
        this.x = (float) (this.x + (d * d4));
        this.y = (float) (this.y + (d2 * d4));
        this.z = (float) (this.z + (d3 * d4));
        return this;
    }

    public Vector add(Vector vector) {
        return add(vector.x, vector.y, vector.z);
    }

    public Vector add(Vector vector, float f) {
        return add(vector.x, vector.y, vector.z, f);
    }

    public static Vector add(Vector vector, Vector vector2, Vector vector3) {
        return (vector != null ? vector.set(vector2) : new Vector(vector2)).add(vector3);
    }

    public Vector sub(float f, float f2) {
        return add(-f, -f2, 0.0d);
    }

    public Vector sub(double d, double d2) {
        return add(-d, -d2, 0.0d);
    }

    public Vector sub(Vector vector) {
        return add(-vector.x, -vector.y, -vector.z);
    }

    public Vector sub(Vector vector, float f) {
        return add(-vector.x, -vector.y, -vector.z, f);
    }

    public Vector sub(float f, float f2, float f3) {
        return add(-f, -f2, -f3);
    }

    public Vector sub(double d, double d2, double d3) {
        return add(-d, -d2, -d3);
    }

    public Vector sub(float f, float f2, float f3, float f4) {
        return add(-f, -f2, -f3, f4);
    }

    public Vector sub(double d, double d2, double d3, double d4) {
        return add(-d, -d2, -d3, d4);
    }

    public static Vector sub(Vector vector, Vector vector2, Vector vector3) {
        return (vector != null ? vector.set(vector2) : new Vector(vector2)).sub(vector3);
    }

    public Vector mult(Vector vector) {
        this.x *= vector.x;
        this.y *= vector.y;
        this.z *= vector.z;
        return this;
    }

    public Vector mult(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        return this;
    }

    public Vector mult(double d) {
        this.x = (float) (this.x * d);
        this.y = (float) (this.y * d);
        this.z = (float) (this.z * d);
        return this;
    }

    public Vector mult(float f, float f2) {
        this.x *= f;
        this.y *= f2;
        return this;
    }

    public Vector mult(double d, double d2) {
        this.x = (float) (this.x * d);
        this.y = (float) (this.y * d2);
        return this;
    }

    public Vector mult(float f, float f2, float f3) {
        this.x *= f;
        this.y *= f2;
        this.z *= f3;
        return this;
    }

    public Vector mult(double d, double d2, double d3) {
        this.x = (float) (this.x * d);
        this.y = (float) (this.y * d2);
        this.z = (float) (this.z * d3);
        return this;
    }

    public Vector div(Vector vector) {
        this.x /= vector.x;
        this.y /= vector.y;
        this.z /= vector.z;
        return this;
    }

    public Vector div(float f) {
        this.x /= f;
        this.y /= f;
        this.z /= f;
        return this;
    }

    public Vector div(double d) {
        this.x = (float) (this.x / d);
        this.y = (float) (this.y / d);
        this.z = (float) (this.z / d);
        return this;
    }

    public Vector div(float f, float f2) {
        this.x /= f;
        this.y /= f2;
        return this;
    }

    public Vector div(double d, double d2) {
        this.x = (float) (this.x / d);
        this.y = (float) (this.y / d2);
        return this;
    }

    public Vector div(float f, float f2, float f3) {
        this.x /= f;
        this.y /= f2;
        this.z /= f3;
        return this;
    }

    public Vector div(double d, double d2, double d3) {
        this.x = (float) (this.x / d);
        this.y = (float) (this.y / d2);
        this.z = (float) (this.z / d3);
        return this;
    }

    public Vector mod(float f) {
        this.x %= f;
        this.y %= f;
        this.z %= f;
        return this;
    }

    public Vector toRadian() {
        return mult(0.017453292519943295d);
    }

    public Vector toDegree() {
        return mult(57.29577951308232d);
    }

    public Vector limit(float f) {
        float f2;
        float abs = Math.abs(f);
        if (abs != 0.0f) {
            while (true) {
                float f3 = this.x;
                if (f3 < abs) {
                    break;
                }
                this.x = f3 - abs;
            }
            while (true) {
                float f4 = this.x;
                f2 = -abs;
                if (f4 > f2) {
                    break;
                }
                this.x = f4 + abs;
            }
            while (true) {
                float f5 = this.y;
                if (f5 < abs) {
                    break;
                }
                this.y = f5 - abs;
            }
            while (true) {
                float f6 = this.y;
                if (f6 > f2) {
                    break;
                }
                this.y = f6 + abs;
            }
            while (true) {
                float f7 = this.z;
                if (f7 < abs) {
                    break;
                }
                this.z = f7 - abs;
            }
            while (true) {
                float f8 = this.z;
                if (f8 > f2) {
                    break;
                }
                this.z = f8 + abs;
            }
        } else {
            this.z = 0.0f;
            this.y = 0.0f;
            this.x = 0.0f;
        }
        return this;
    }

    public Vector limit(float f, float f2) {
        float min = Math.min(f, f2);
        float max = Math.max(f, f2);
        if (max != min) {
            while (true) {
                float f3 = this.x;
                if (f3 < max) {
                    break;
                }
                this.x = f3 - max;
            }
            while (true) {
                float f4 = this.x;
                if (f4 > min) {
                    break;
                }
                this.x = f4 - min;
            }
            while (true) {
                float f5 = this.y;
                if (f5 < max) {
                    break;
                }
                this.y = f5 - max;
            }
            while (true) {
                float f6 = this.y;
                if (f6 > min) {
                    break;
                }
                this.y = f6 - min;
            }
            while (true) {
                float f7 = this.z;
                if (f7 < max) {
                    break;
                }
                this.z = f7 - max;
            }
            while (true) {
                float f8 = this.z;
                if (f8 > min) {
                    break;
                }
                this.z = f8 - min;
            }
        } else {
            this.z = min;
            this.y = min;
            this.x = min;
        }
        return this;
    }

    public Vector saturate(float f) {
        float abs = Math.abs(f);
        float f2 = this.x;
        this.x = f2 >= abs ? abs : Math.max(f2, -abs);
        float f3 = this.y;
        this.y = f3 >= abs ? abs : Math.max(f3, -abs);
        float f4 = this.z;
        if (f4 < abs) {
            abs = Math.max(f4, -abs);
        }
        this.z = abs;
        return this;
    }

    public Vector saturate(float f, float f2) {
        float min = Math.min(f, f2);
        float max = Math.max(f, f2);
        if (max != min) {
            float f3 = this.x;
            this.x = f3 >= max ? max : Math.max(f3, min);
            float f4 = this.y;
            this.y = f4 >= max ? max : Math.max(f4, min);
            float f5 = this.z;
            if (f5 < max) {
                max = Math.max(f5, min);
            }
            this.z = max;
        } else {
            this.y = min;
            this.z = min;
        }
        return this;
    }

    public float len2D() {
        return (float) Math.hypot(this.x, this.y);
    }

    public float len() {
        return (float) Math.sqrt(lenSquared());
    }

    public Vector len(float f) {
        double sqrt = Math.sqrt(lenSquared());
        if (sqrt != 0.0d && f != 0.0f) {
            return mult(f / sqrt);
        }
        return clear(0.0f);
    }

    public Vector len(double d) {
        double sqrt = Math.sqrt(lenSquared());
        if (sqrt != 0.0d && d != 0.0d) {
            return mult(d / sqrt);
        }
        return clear(0.0f);
    }

    public float lenSquared() {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.z;
        return (float) ((f * f) + (f2 * f2) + (f3 * f3));
    }

    public Vector normalize() {
        double sqrt = Math.sqrt(lenSquared());
        if (sqrt != 0.0d) {
            this.x = (float) (this.x / sqrt);
            this.y = (float) (this.y / sqrt);
            this.z = (float) (this.z / sqrt);
        }
        return this;
    }

    public float dot2D(Vector vector) {
        return (float) ((this.x * vector.x) + (this.y * vector.y));
    }

    public float dotProduct2D(Vector vector) {
        return (float) ((this.x * vector.x) + (this.y * vector.y));
    }

    public float dot2D(float f, float f2, float f3) {
        return (float) ((this.x * f) + (this.y * f2));
    }

    public float dotProduct2D(float f, float f2, float f3) {
        return (float) ((this.x * f) + (this.y * f2));
    }

    public float dot(Vector vector) {
        return dot(this, vector);
    }

    public float dotProduct(Vector vector) {
        return dot(this, vector);
    }

    public float dot(float f, float f2, float f3) {
        return (float) ((this.x * f) + (this.y * f2) + (this.z * f3));
    }

    public float dotProduct(float f, float f2, float f3) {
        return (float) ((this.x * f) + (this.y * f2) + (this.z * f3));
    }

    public static float dot(Vector vector, Vector vector2) {
        return (float) ((vector.x * vector2.x) + (vector.y * vector2.y) + (vector.z * vector2.z));
    }

    public static double dotDouble(Vector vector, Vector vector2) {
        return (vector.x * vector2.x) + (vector.y * vector2.y) + (vector.z * vector2.z);
    }

    public float cross2D(Vector vector) {
        return (float) ((this.x * vector.y) - (vector.x * this.y));
    }

    public float crossProduct2D(Vector vector) {
        return (float) ((this.x * vector.y) - (vector.x * this.y));
    }

    public Vector cross(Vector vector) {
        return crossProduct(this, this, vector);
    }

    public Vector crossProduct(Vector vector) {
        return crossProduct(this, this, vector);
    }

    public static Vector cross(Vector vector, Vector vector2, Vector vector3) {
        return crossProduct(vector, vector2, vector3);
    }

    public static Vector crossProduct(Vector vector, Vector vector2, Vector vector3) {
        float f = vector2.y;
        float f2 = vector3.z;
        float f3 = vector2.z;
        float f4 = vector3.y;
        float f5 = (float) ((f * f2) - (f3 * f4));
        float f6 = vector3.x;
        float f7 = vector2.x;
        float f8 = (float) ((f3 * f6) - (f7 * f2));
        float f9 = (float) ((f7 * f4) - (f * f6));
        return vector != null ? vector.set(f5, f8, f9) : new Vector(f5, f8, f9);
    }

    public float angleXY() {
        float atan2 = (float) (Math.atan2(this.y, this.x) * 57.29577951308232d);
        return atan2 < 0.0f ? atan2 + 360.0f : atan2;
    }

    public float angleXZ() {
        float atan2 = (float) (Math.atan2(this.z, this.x) * 57.29577951308232d);
        return atan2 < 0.0f ? atan2 + 360.0f : atan2;
    }

    public float angleYZ() {
        float atan2 = (float) (Math.atan2(this.z, this.y) * 57.29577951308232d);
        return atan2 < 0.0f ? atan2 + 360.0f : atan2;
    }

    public float angle(Vector vector) {
        return (float) (Math.acos(dotDouble(this, vector) / (Math.sqrt(lenSquared()) * Math.sqrt(vector.lenSquared()))) * 57.29577951308232d);
    }

    public Vector rotateXY(float f) {
        double d = f * 0.017453292519943295d;
        double cos = Math.cos(d);
        double sin = Math.sin(d);
        float f2 = this.x;
        float f3 = this.y;
        this.x = (float) ((f2 * cos) - (f3 * sin));
        this.y = (float) ((f2 * sin) + (f3 * cos));
        return this;
    }

    public Vector rotateXZ(float f) {
        double d = f * 0.017453292519943295d;
        double cos = Math.cos(d);
        double sin = Math.sin(d);
        float f2 = this.x;
        float f3 = this.z;
        this.x = (float) ((f2 * cos) - (f3 * sin));
        this.z = (float) ((f2 * sin) + (f3 * cos));
        return this;
    }

    public Vector rotateYZ(float f) {
        double d = f * 0.017453292519943295d;
        double cos = Math.cos(d);
        double sin = Math.sin(d);
        float f2 = this.y;
        float f3 = this.z;
        this.y = (float) ((f2 * cos) - (f3 * sin));
        this.z = (float) ((f2 * sin) + (f3 * cos));
        return this;
    }

    public Vector rotate(float f, float f2, float f3, float f4) {
        float[] fArr = inVec;
        fArr[0] = this.x;
        fArr[1] = this.y;
        fArr[2] = this.z;
        fArr[3] = 1.0f;
        float[] fArr2 = matrix;
        Matrix.setIdentityM(fArr2, 0);
        Matrix.rotateM(fArr2, 0, f, f2, f3, f4);
        float[] fArr3 = outVec;
        Matrix.multiplyMV(fArr3, 0, fArr2, 0, fArr, 0);
        this.x = fArr3[0];
        this.y = fArr3[1];
        this.z = fArr3[2];
        return this;
    }

    public Vector rotate(float f, float f2, float f3) {
        return rotate(this, f, f2, f3);
    }

    public static Vector rotate(Vector vector, float f, float f2, float f3) {
        float[] fArr = inVec;
        fArr[0] = vector.x;
        fArr[1] = vector.y;
        fArr[2] = vector.z;
        fArr[3] = 1.0f;
        float[] fArr2 = matrix;
        Matrix.setIdentityM(fArr2, 0);
        if (f != 0.0f) {
            Matrix.rotateM(fArr2, 0, f, 1.0f, 0.0f, 0.0f);
        }
        if (f2 != 0.0f) {
            Matrix.rotateM(fArr2, 0, f2, 0.0f, 1.0f, 0.0f);
        }
        if (f3 != 0.0f) {
            Matrix.rotateM(fArr2, 0, f3, 0.0f, 0.0f, 1.0f);
        }
        float[] fArr3 = outVec;
        Matrix.multiplyMV(fArr3, 0, fArr2, 0, fArr, 0);
        vector.x = fArr3[0];
        vector.y = fArr3[1];
        vector.z = fArr3[2];
        return vector;
    }

    public Vector rotate(Vector vector, float f) {
        return rotate(this, vector.x * f, vector.y * f, vector.z * f);
    }

    public Vector rotate(Vector vector) {
        return rotate(this, vector.x, vector.y, vector.z);
    }

    public float[] getQuat(float[] fArr) {
        if (fArr == null) {
            fArr = new float[4];
        }
        fArr[0] = this.x;
        fArr[1] = this.y;
        fArr[2] = this.z;
        fArr[3] = 1.0f;
        return fArr;
    }

    public Vector setQuat(float[] fArr) {
        this.x = fArr[0];
        this.y = fArr[1];
        this.z = fArr[2];
        return this;
    }

    public float distance(Vector vector) {
        return distance(vector.x, vector.y, vector.z);
    }

    public float distance(float f, float f2) {
        return distance(f, f2, this.z);
    }

    public float distance(float f, float f2, float f3) {
        return (float) Math.sqrt(distSquaredDouble(f, f2, f3));
    }

    public float distSquared(Vector vector) {
        return distSquared(vector.x, vector.y, vector.z);
    }

    public float distSquared(float f, float f2) {
        return distSquared(f, f2, this.z);
    }

    public float distSquared(float f, float f2, float f3) {
        double d = this.x - f;
        double d2 = this.y - f2;
        double d3 = this.z - f3;
        return (float) ((d * d) + (d2 * d2) + (d3 * d3));
    }

    public double distSquaredDouble(float f, float f2, float f3) {
        double d = this.x - f;
        double d2 = this.y - f2;
        double d3 = this.z - f3;
        return (d * d) + (d2 * d2) + (d3 * d3);
    }

    public Vector swap(Vector vector) {
        float f = this.x;
        this.x = vector.x;
        vector.x = f;
        float f2 = this.y;
        this.y = vector.y;
        vector.y = f2;
        float f3 = this.z;
        this.z = vector.z;
        vector.z = f3;
        return this;
    }

    public Vector swapXY() {
        float f = this.x;
        this.x = this.y;
        this.y = f;
        return this;
    }

    public float slope2D(Vector vector) {
        float f = vector.x;
        float f2 = this.x;
        if (f != f2) {
            return (float) ((vector.y - this.y) / (f - f2));
        }
        return vector.y - this.y >= 0.0f ? Float.MAX_VALUE : Float.MIN_VALUE;
    }

    public float interceptY2D(Vector vector) {
        float f = vector.x;
        float f2 = this.x;
        if (f != f2) {
            double d = vector.y;
            float f3 = this.y;
            return (float) (f3 - (((d - f3) / (f - f2)) * f2));
        }
        return Float.POSITIVE_INFINITY;
    }

    public float slope2D() {
        float f = this.x;
        if (f != 0.0f) {
            return (float) (this.y / f);
        }
        return this.y >= 0.0f ? Float.MAX_VALUE : Float.MIN_VALUE;
    }

    public Vector sign() {
        this.x = Math.signum(this.x);
        this.y = Math.signum(this.y);
        this.z = Math.signum(this.z);
        return this;
    }

    public Vector mid(Vector vector) {
        return mid(null, this, vector);
    }

    public static Vector mid(Vector vector, Vector vector2, Vector vector3) {
        if (vector == null) {
            vector = new Vector();
        }
        return add(vector, vector2, vector3).div(2.0f);
    }

    public static Vector normalVector(Vector vector, Vector vector2, Vector vector3, Vector vector4) {
        Vector sub = sub(vector, vector3, vector2);
        return sub.mult(sub.dot(new Vector(vector4).sub(vector2)) / sub.lenSquared()).add(vector2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector) {
            Vector vector = (Vector) obj;
            return Float.compare(vector.x, this.x) == 0 && Float.compare(vector.y, this.y) == 0 && Float.compare(vector.z, this.z) == 0;
        }
        return false;
    }

    public int hashCode() {
        return hasCode(Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z));
    }

    private static int hasCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public String toString() {
        return String.format(Locale.US, "(%f,%f,%f)", Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z));
    }

    public String toString(String str) {
        return String.format(Locale.US, str, Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
        parcel.writeFloat(this.z);
    }
}
