package com.serenegiant.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static <T> boolean contains(T[] tArr, T t) {
        for (T t2 : tArr) {
            if (t2 != null && t2.equals(t)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(byte[] bArr, byte b) {
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(short[] sArr, short s) {
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(float[] fArr, float f) {
        for (float f2 : fArr) {
            if (Float.compare(f2, f) == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(double[] dArr, double d) {
        for (double d2 : dArr) {
            if (Double.compare(d2, d) == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T> void copy(T[] tArr, T[] tArr2) {
        System.arraycopy(tArr, 0, tArr2, 0, tArr.length);
    }

    public static <T> ArrayList<T> asList(T[] tArr) {
        return new ArrayList<>(Arrays.asList(tArr));
    }

    public static byte[] floatArrayToByteArray(float[] fArr, int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate((i2 * 32) / 8);
        allocate.order(ByteOrder.nativeOrder());
        int i3 = (i2 % 8) + i;
        int i4 = i2 + i;
        while (i < i3) {
            allocate.putFloat(fArr[i]);
            i++;
        }
        while (i3 < i4) {
            allocate.putFloat(fArr[i3]);
            allocate.putFloat(fArr[i3 + 1]);
            allocate.putFloat(fArr[i3 + 2]);
            allocate.putFloat(fArr[i3 + 3]);
            allocate.putFloat(fArr[i3 + 4]);
            allocate.putFloat(fArr[i3 + 5]);
            allocate.putFloat(fArr[i3 + 6]);
            allocate.putFloat(fArr[i3 + 7]);
            i3 += 8;
        }
        allocate.flip();
        return allocate.array();
    }

    public static float[] byteArrayToFloatArray(byte[] bArr) {
        if (bArr == null || bArr.length < 4) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.nativeOrder());
        int limit = wrap.limit() / 4;
        float[] fArr = new float[limit];
        int i = limit % 8;
        for (int i2 = 0; i2 < i; i2++) {
            fArr[i2] = wrap.getFloat();
        }
        while (i < limit) {
            fArr[i] = wrap.getFloat();
            fArr[i + 1] = wrap.getFloat();
            fArr[i + 2] = wrap.getFloat();
            fArr[i + 3] = wrap.getFloat();
            fArr[i + 4] = wrap.getFloat();
            fArr[i + 5] = wrap.getFloat();
            fArr[i + 6] = wrap.getFloat();
            fArr[i + 7] = wrap.getFloat();
            i += 8;
        }
        return fArr;
    }

    public static byte[] doubleArrayToByteArray(double[] dArr, int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate((i2 * 64) / 8);
        allocate.order(ByteOrder.nativeOrder());
        int i3 = (i2 % 8) + i;
        int i4 = i2 + i;
        while (i < i3) {
            allocate.putDouble(dArr[i]);
            i++;
        }
        while (i3 < i4) {
            allocate.putDouble(dArr[i3]);
            allocate.putDouble(dArr[i3 + 1]);
            allocate.putDouble(dArr[i3 + 2]);
            allocate.putDouble(dArr[i3 + 3]);
            allocate.putDouble(dArr[i3 + 4]);
            allocate.putDouble(dArr[i3 + 5]);
            allocate.putDouble(dArr[i3 + 6]);
            allocate.putDouble(dArr[i3 + 7]);
            i3 += 8;
        }
        allocate.flip();
        return allocate.array();
    }

    public static double[] byteArrayToDoubleArray(byte[] bArr) {
        if (bArr == null || bArr.length < 8) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.nativeOrder());
        int limit = wrap.limit() / 8;
        double[] dArr = new double[limit];
        int i = limit % 8;
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = wrap.getDouble();
        }
        while (i < limit) {
            dArr[i] = wrap.getDouble();
            dArr[i + 1] = wrap.getDouble();
            dArr[i + 2] = wrap.getDouble();
            dArr[i + 3] = wrap.getDouble();
            dArr[i + 4] = wrap.getDouble();
            dArr[i + 5] = wrap.getDouble();
            dArr[i + 6] = wrap.getDouble();
            dArr[i + 7] = wrap.getDouble();
            i += 8;
        }
        return dArr;
    }

    public static byte[] intArrayToByteArray(int[] iArr, int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate((i2 * 32) / 8);
        allocate.order(ByteOrder.nativeOrder());
        int i3 = (i2 % 8) + i;
        int i4 = i2 + i;
        while (i < i3) {
            allocate.putInt(iArr[i]);
            i++;
        }
        while (i3 < i4) {
            allocate.putInt(iArr[i3]);
            allocate.putInt(iArr[i3 + 1]);
            allocate.putInt(iArr[i3 + 2]);
            allocate.putInt(iArr[i3 + 3]);
            allocate.putInt(iArr[i3 + 4]);
            allocate.putInt(iArr[i3 + 5]);
            allocate.putInt(iArr[i3 + 6]);
            allocate.putInt(iArr[i3 + 7]);
            i3 += 8;
        }
        allocate.flip();
        return allocate.array();
    }

    public static int[] byteArrayToIntArray(byte[] bArr) {
        if (bArr == null || bArr.length < 4) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.nativeOrder());
        int limit = wrap.limit() / 4;
        int[] iArr = new int[limit];
        int i = limit % 8;
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = wrap.getInt();
        }
        while (i < limit) {
            iArr[i] = wrap.getInt();
            iArr[i + 1] = wrap.getInt();
            iArr[i + 2] = wrap.getInt();
            iArr[i + 3] = wrap.getInt();
            iArr[i + 4] = wrap.getInt();
            iArr[i + 5] = wrap.getInt();
            iArr[i + 6] = wrap.getInt();
            iArr[i + 7] = wrap.getInt();
            i += 8;
        }
        return iArr;
    }

    public static byte[] shortArrayToByteArray(short[] sArr, int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate((i2 * 16) / 8);
        allocate.order(ByteOrder.nativeOrder());
        int i3 = (i2 % 8) + i;
        int i4 = i2 + i;
        while (i < i3) {
            allocate.putShort(sArr[i]);
            i++;
        }
        while (i3 < i4) {
            allocate.putShort(sArr[i3]);
            allocate.putShort(sArr[i3 + 1]);
            allocate.putShort(sArr[i3 + 2]);
            allocate.putShort(sArr[i3 + 3]);
            allocate.putShort(sArr[i3 + 4]);
            allocate.putShort(sArr[i3 + 5]);
            allocate.putShort(sArr[i3 + 6]);
            allocate.putShort(sArr[i3 + 7]);
            i3 += 8;
        }
        allocate.flip();
        return allocate.array();
    }

    public static short[] byteArrayToShortArray(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.nativeOrder());
        int limit = wrap.limit() / 2;
        short[] sArr = new short[limit];
        int i = limit % 8;
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = wrap.getShort();
        }
        while (i < limit) {
            sArr[i] = wrap.getShort();
            sArr[i + 1] = wrap.getShort();
            sArr[i + 2] = wrap.getShort();
            sArr[i + 3] = wrap.getShort();
            sArr[i + 4] = wrap.getShort();
            sArr[i + 5] = wrap.getShort();
            sArr[i + 6] = wrap.getShort();
            sArr[i + 7] = wrap.getShort();
            i += 8;
        }
        return sArr;
    }

    public static byte[] longArrayToByteArray(long[] jArr, int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate((i2 * 64) / 8);
        allocate.order(ByteOrder.nativeOrder());
        int i3 = (i2 % 8) + i;
        int i4 = i2 + i;
        while (i < i3) {
            allocate.putLong(jArr[i]);
            i++;
        }
        while (i3 < i4) {
            allocate.putLong(jArr[i3]);
            allocate.putLong(jArr[i3 + 1]);
            allocate.putLong(jArr[i3 + 2]);
            allocate.putLong(jArr[i3 + 3]);
            allocate.putLong(jArr[i3 + 4]);
            allocate.putLong(jArr[i3 + 5]);
            allocate.putLong(jArr[i3 + 6]);
            allocate.putLong(jArr[i3 + 7]);
            i3 += 8;
        }
        allocate.flip();
        return allocate.array();
    }

    public static long[] byteArrayToLongArray(byte[] bArr) {
        if (bArr == null || bArr.length < 8) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.nativeOrder());
        int limit = wrap.limit() / 8;
        long[] jArr = new long[limit];
        int i = limit % 8;
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = wrap.getLong();
        }
        while (i < limit) {
            jArr[i] = wrap.getLong();
            jArr[i + 1] = wrap.getLong();
            jArr[i + 2] = wrap.getLong();
            jArr[i + 3] = wrap.getLong();
            jArr[i + 4] = wrap.getLong();
            jArr[i + 5] = wrap.getLong();
            jArr[i + 6] = wrap.getLong();
            jArr[i + 7] = wrap.getLong();
            i += 8;
        }
        return jArr;
    }
}
