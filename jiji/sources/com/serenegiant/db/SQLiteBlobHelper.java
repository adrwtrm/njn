package com.serenegiant.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.ArrayUtils;

/* loaded from: classes2.dex */
public class SQLiteBlobHelper {
    public static void bindBlobFloatArray(SQLiteStatement sQLiteStatement, int i, float[] fArr) {
        sQLiteStatement.bindBlob(i, ArrayUtils.floatArrayToByteArray(fArr, 0, fArr.length));
    }

    public static void bindBlobFloatArray(SQLiteStatement sQLiteStatement, int i, float[] fArr, int i2, int i3) {
        sQLiteStatement.bindBlob(i, ArrayUtils.floatArrayToByteArray(fArr, i2, i3));
    }

    public static void bindBlobDoubleArray(SQLiteStatement sQLiteStatement, int i, double[] dArr) {
        sQLiteStatement.bindBlob(i, ArrayUtils.doubleArrayToByteArray(dArr, 0, dArr.length));
    }

    public static void bindBlobDoubleArray(SQLiteStatement sQLiteStatement, int i, double[] dArr, int i2, int i3) {
        sQLiteStatement.bindBlob(i, ArrayUtils.doubleArrayToByteArray(dArr, i2, i3));
    }

    public static void bindBlobIntArray(SQLiteStatement sQLiteStatement, int i, int[] iArr) {
        sQLiteStatement.bindBlob(i, ArrayUtils.intArrayToByteArray(iArr, 0, iArr.length));
    }

    public static void bindBlobIntArray(SQLiteStatement sQLiteStatement, int i, int[] iArr, int i2, int i3) {
        sQLiteStatement.bindBlob(i, ArrayUtils.intArrayToByteArray(iArr, i2, i3));
    }

    public static void bindBlobShortArray(SQLiteStatement sQLiteStatement, int i, short[] sArr) {
        sQLiteStatement.bindBlob(i, ArrayUtils.shortArrayToByteArray(sArr, 0, sArr.length));
    }

    public static void bindBlobShortArray(SQLiteStatement sQLiteStatement, int i, short[] sArr, int i2, int i3) {
        sQLiteStatement.bindBlob(i, ArrayUtils.shortArrayToByteArray(sArr, i2, i3));
    }

    public static void bindBlobLongArray(SQLiteStatement sQLiteStatement, int i, long[] jArr) {
        sQLiteStatement.bindBlob(i, ArrayUtils.longArrayToByteArray(jArr, 0, jArr.length));
    }

    public static void bindBlobLongArray(SQLiteStatement sQLiteStatement, int i, long[] jArr, int i2, int i3) {
        sQLiteStatement.bindBlob(i, ArrayUtils.longArrayToByteArray(jArr, i2, i3));
    }

    public static void bindBlobBitmap(SQLiteStatement sQLiteStatement, int i, Bitmap bitmap) {
        sQLiteStatement.bindBlob(i, BitmapHelper.BitmapToByteArray(bitmap));
    }

    public static float[] getBlobFloatArray(Cursor cursor, int i) {
        return ArrayUtils.byteArrayToFloatArray(cursor.getBlob(i));
    }

    public static float[] getBlobFloatArray(Cursor cursor, String str, float[] fArr) {
        float[] byteArrayToFloatArray = ArrayUtils.byteArrayToFloatArray(getBlob(cursor, str, null));
        return byteArrayToFloatArray == null ? fArr : byteArrayToFloatArray;
    }

    public static double[] getBlobDoubleArray(Cursor cursor, int i) {
        return ArrayUtils.byteArrayToDoubleArray(cursor.getBlob(i));
    }

    public static double[] getBlobDoubleArray(Cursor cursor, String str, double[] dArr) {
        double[] byteArrayToDoubleArray = ArrayUtils.byteArrayToDoubleArray(getBlob(cursor, str, null));
        return byteArrayToDoubleArray == null ? dArr : byteArrayToDoubleArray;
    }

    public static byte[] getBlob(Cursor cursor, String str, byte[] bArr) {
        try {
            return cursor.getBlob(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return bArr;
        }
    }

    public static int[] getBlobIntArray(Cursor cursor, int i) {
        return ArrayUtils.byteArrayToIntArray(cursor.getBlob(i));
    }

    public static int[] getBlobIntArray(Cursor cursor, String str, int[] iArr) {
        int[] byteArrayToIntArray = ArrayUtils.byteArrayToIntArray(getBlob(cursor, str, null));
        return byteArrayToIntArray == null ? iArr : byteArrayToIntArray;
    }

    public static short[] getBlobShortArray(Cursor cursor, int i) {
        return ArrayUtils.byteArrayToShortArray(cursor.getBlob(i));
    }

    public static short[] getBlobShortArray(Cursor cursor, String str, short[] sArr) {
        short[] byteArrayToShortArray = ArrayUtils.byteArrayToShortArray(getBlob(cursor, str, null));
        return byteArrayToShortArray == null ? sArr : byteArrayToShortArray;
    }

    public static long[] getBlobLongArray(Cursor cursor, int i) {
        return ArrayUtils.byteArrayToLongArray(cursor.getBlob(i));
    }

    public static long[] getBlobLongArray(Cursor cursor, String str, long[] jArr) {
        long[] byteArrayToLongArray = ArrayUtils.byteArrayToLongArray(getBlob(cursor, str, null));
        return byteArrayToLongArray == null ? jArr : byteArrayToLongArray;
    }

    public static Bitmap getBlobBitmap(Cursor cursor, int i) {
        return BitmapHelper.asBitmap(cursor.getBlob(i));
    }

    public static Bitmap getBlobBitmap(Cursor cursor, String str) {
        return BitmapHelper.asBitmap(getBlob(cursor, str, null));
    }

    public static Bitmap getBlobBitmap(Cursor cursor, int i, int i2, int i3) {
        return BitmapHelper.asBitmap(cursor.getBlob(i), i2, i3);
    }

    public static Bitmap getBlobBitmap(Cursor cursor, String str, int i, int i2) {
        return BitmapHelper.asBitmap(getBlob(cursor, str, null), i, i2);
    }

    public static Bitmap getBlobBitmapStrictSize(Cursor cursor, int i, int i2, int i3) {
        return BitmapHelper.asBitmapStrictSize(cursor.getBlob(i), i2, i3);
    }

    public static Bitmap getBlobBitmapStrictSize(Cursor cursor, String str, int i, int i2) {
        return BitmapHelper.asBitmapStrictSize(getBlob(cursor, str, null), i, i2);
    }
}
