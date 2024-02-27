package javassist.bytecode;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
final class ByteStream extends OutputStream {
    private byte[] buf;
    private int count;

    public ByteStream() {
        this(32);
    }

    public ByteStream(int i) {
        this.buf = new byte[i];
        this.count = 0;
    }

    public int getPos() {
        return this.count;
    }

    public int size() {
        return this.count;
    }

    public void writeBlank(int i) {
        enlarge(i);
        this.count += i;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        enlarge(i2);
        System.arraycopy(bArr, i, this.buf, this.count, i2);
        this.count += i2;
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        enlarge(1);
        int i2 = this.count;
        this.buf[i2] = (byte) i;
        this.count = i2 + 1;
    }

    public void writeShort(int i) {
        enlarge(2);
        int i2 = this.count;
        byte[] bArr = this.buf;
        bArr[i2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) i;
        this.count = i2 + 2;
    }

    public void writeInt(int i) {
        enlarge(4);
        int i2 = this.count;
        byte[] bArr = this.buf;
        bArr[i2] = (byte) (i >>> 24);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 3] = (byte) i;
        this.count = i2 + 4;
    }

    public void writeLong(long j) {
        enlarge(8);
        int i = this.count;
        byte[] bArr = this.buf;
        bArr[i] = (byte) (j >>> 56);
        bArr[i + 1] = (byte) (j >>> 48);
        bArr[i + 2] = (byte) (j >>> 40);
        bArr[i + 3] = (byte) (j >>> 32);
        bArr[i + 4] = (byte) (j >>> 24);
        bArr[i + 5] = (byte) (j >>> 16);
        bArr[i + 6] = (byte) (j >>> 8);
        bArr[i + 7] = (byte) j;
        this.count = i + 8;
    }

    public void writeFloat(float f) {
        writeInt(Float.floatToIntBits(f));
    }

    public void writeDouble(double d) {
        writeLong(Double.doubleToLongBits(d));
    }

    public void writeUTF(String str) {
        int length = str.length();
        int i = this.count;
        enlarge(length + 2);
        byte[] bArr = this.buf;
        int i2 = i + 1;
        bArr[i] = (byte) (length >>> 8);
        int i3 = i2 + 1;
        bArr[i2] = (byte) length;
        int i4 = 0;
        while (i4 < length) {
            char charAt = str.charAt(i4);
            if (1 <= charAt && charAt <= 127) {
                bArr[i3] = (byte) charAt;
                i4++;
                i3++;
            } else {
                writeUTF2(str, length, i4);
                return;
            }
        }
        this.count = i3;
    }

    private void writeUTF2(String str, int i, int i2) {
        int i3 = i;
        for (int i4 = i2; i4 < i; i4++) {
            char charAt = str.charAt(i4);
            if (charAt > 2047) {
                i3 += 2;
            } else if (charAt == 0 || charAt > 127) {
                i3++;
            }
        }
        if (i3 > 65535) {
            throw new RuntimeException("encoded string too long: " + i + i3 + " bytes");
        }
        enlarge(i3 + 2);
        int i5 = this.count;
        byte[] bArr = this.buf;
        bArr[i5] = (byte) (i3 >>> 8);
        bArr[i5 + 1] = (byte) i3;
        int i6 = i5 + i2 + 2;
        while (i2 < i) {
            char charAt2 = str.charAt(i2);
            if (1 <= charAt2 && charAt2 <= 127) {
                bArr[i6] = (byte) charAt2;
                i6++;
            } else if (charAt2 > 2047) {
                bArr[i6] = (byte) (((charAt2 >> '\f') & 15) | 224);
                bArr[i6 + 1] = (byte) (((charAt2 >> 6) & 63) | 128);
                bArr[i6 + 2] = (byte) ((charAt2 & '?') | 128);
                i6 += 3;
            } else {
                bArr[i6] = (byte) (((charAt2 >> 6) & 31) | 192);
                bArr[i6 + 1] = (byte) ((charAt2 & '?') | 128);
                i6 += 2;
            }
            i2++;
        }
        this.count = i6;
    }

    public void write(int i, int i2) {
        this.buf[i] = (byte) i2;
    }

    public void writeShort(int i, int i2) {
        byte[] bArr = this.buf;
        bArr[i] = (byte) (i2 >>> 8);
        bArr[i + 1] = (byte) i2;
    }

    public void writeInt(int i, int i2) {
        byte[] bArr = this.buf;
        bArr[i] = (byte) (i2 >>> 24);
        bArr[i + 1] = (byte) (i2 >>> 16);
        bArr[i + 2] = (byte) (i2 >>> 8);
        bArr[i + 3] = (byte) i2;
    }

    public byte[] toByteArray() {
        int i = this.count;
        byte[] bArr = new byte[i];
        System.arraycopy(this.buf, 0, bArr, 0, i);
        return bArr;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.buf, 0, this.count);
    }

    public void enlarge(int i) {
        int i2 = this.count;
        int i3 = i + i2;
        byte[] bArr = this.buf;
        if (i3 > bArr.length) {
            int length = bArr.length << 1;
            if (length > i3) {
                i3 = length;
            }
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            this.buf = bArr2;
        }
    }
}
