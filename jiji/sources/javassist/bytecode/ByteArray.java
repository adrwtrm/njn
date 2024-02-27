package javassist.bytecode;

import com.google.common.base.Ascii;

/* loaded from: classes2.dex */
public class ByteArray {
    public static int readU16bit(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    public static int readS16bit(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | (bArr[i] << 8);
    }

    public static void write16bit(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) i;
    }

    public static int read32bit(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | (bArr[i] << Ascii.CAN) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static void write32bit(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 24);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 3] = (byte) i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void copy32bit(byte[] bArr, int i, byte[] bArr2, int i2) {
        bArr2[i2] = bArr[i];
        bArr2[i2 + 1] = bArr[i + 1];
        bArr2[i2 + 2] = bArr[i + 2];
        bArr2[i2 + 3] = bArr[i + 3];
    }
}
