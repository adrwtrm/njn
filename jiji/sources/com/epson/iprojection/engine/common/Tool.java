package com.epson.iprojection.engine.common;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import java.util.Arrays;

/* loaded from: classes.dex */
public class Tool {
    public static final byte[] INVALID_IP_ADDRESS = {0, 0, 0, 0};
    public static final byte[] INVALID_IP_ADDRESS2 = {-1, -1, -1, -1};
    public static final byte[] INVALID_UNIQ_INFO = {0, 0, 0, 0, 0, 0};

    public static byte[] IntToBinarryArrayIPAddr(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static boolean IsEmptyIPAddr(byte[] bArr) {
        boolean equals = Arrays.equals(bArr, INVALID_IP_ADDRESS);
        if (Arrays.equals(bArr, INVALID_IP_ADDRESS2)) {
            return true;
        }
        return equals;
    }

    public static boolean IsEmptyUniqInfo(byte[] bArr) {
        return Arrays.equals(bArr, INVALID_UNIQ_INFO);
    }

    public static String BinarryArrayToStringUniqInfo(byte[] bArr) {
        return 6 <= bArr.length ? String.format("%02x.%02x.%02x.%02x.%02x.%02x", Byte.valueOf(bArr[0]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[3]), Byte.valueOf(bArr[4]), Byte.valueOf(bArr[5])) : "";
    }

    public static String BinarryArrayToStringIPAddr(byte[] bArr) {
        String format = 4 <= bArr.length ? String.format("%d.%d.%d.%d", Integer.valueOf(bArr[0] & 255), Integer.valueOf(bArr[1] & 255), Integer.valueOf(bArr[2] & 255), Integer.valueOf(bArr[3] & 255)) : null;
        return format == null ? "Unknown" : format;
    }

    public static byte[] IntToBinarryArray(int i) {
        byte[] bArr = {0, 0, 0, 0};
        bArr[0] = (byte) (i & 255);
        bArr[1] = (byte) ((i >> 8) & 255);
        bArr[2] = (byte) ((i >> 16) & 255);
        bArr[3] = (byte) ((i >> 24) & 255);
        return bArr;
    }

    public static byte[] SwapIntToBinarryArray(int i) {
        byte[] bArr = {0, 0, 0, 0};
        bArr[3] = (byte) (i & 255);
        bArr[2] = (byte) ((i >> 8) & 255);
        bArr[1] = (byte) ((i >> 16) & 255);
        bArr[0] = (byte) ((i >> 24) & 255);
        return bArr;
    }

    public static byte[] IntToBinarry3Array(int i) {
        byte[] bArr = {0, 0, 0};
        bArr[0] = (byte) ((i & 127) | 128);
        bArr[1] = (byte) (((i >> 7) & 127) | 128);
        bArr[2] = (byte) ((i >> 14) & 255);
        return bArr;
    }

    public static byte[] ShortToBinarryArray(short s) {
        byte[] bArr = {0, 0};
        bArr[0] = (byte) (s & 255);
        bArr[1] = (byte) ((s >> 8) & 255);
        return bArr;
    }

    public static byte[] SwapShortToBinarryArray(short s) {
        byte[] bArr = {0, 0};
        bArr[1] = (byte) (s & 255);
        bArr[0] = (byte) ((s >> 8) & 255);
        return bArr;
    }

    public static int BinarryArrayToInt(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = (bArr[i] & 255) | ((bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        return ((bArr[i3 + 1] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | i4 | ((bArr[i3] << Ascii.DLE) & 16711680);
    }

    public static Short BinarryArrayToShort(byte[] bArr, int i) {
        return Short.valueOf((short) (((short) ((bArr[i + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((short) (bArr[i] & 255))));
    }

    public static void Sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception unused) {
        }
    }
}
