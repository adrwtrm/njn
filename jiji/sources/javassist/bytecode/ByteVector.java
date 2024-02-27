package javassist.bytecode;

/* compiled from: Bytecode.java */
/* loaded from: classes2.dex */
class ByteVector implements Cloneable {
    private byte[] buffer = new byte[64];
    private int size = 0;

    public Object clone() throws CloneNotSupportedException {
        ByteVector byteVector = (ByteVector) super.clone();
        byteVector.buffer = (byte[]) this.buffer.clone();
        return byteVector;
    }

    public final int getSize() {
        return this.size;
    }

    public final byte[] copy() {
        int i = this.size;
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, 0, bArr, 0, i);
        return bArr;
    }

    public int read(int i) {
        if (i < 0 || this.size <= i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.buffer[i];
    }

    public void write(int i, int i2) {
        if (i < 0 || this.size <= i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.buffer[i] = (byte) i2;
    }

    public void add(int i) {
        addGap(1);
        this.buffer[this.size - 1] = (byte) i;
    }

    public void add(int i, int i2) {
        addGap(2);
        byte[] bArr = this.buffer;
        int i3 = this.size;
        bArr[i3 - 2] = (byte) i;
        bArr[i3 - 1] = (byte) i2;
    }

    public void add(int i, int i2, int i3, int i4) {
        addGap(4);
        byte[] bArr = this.buffer;
        int i5 = this.size;
        bArr[i5 - 4] = (byte) i;
        bArr[i5 - 3] = (byte) i2;
        bArr[i5 - 2] = (byte) i3;
        bArr[i5 - 1] = (byte) i4;
    }

    public void addGap(int i) {
        int i2 = this.size;
        int i3 = i2 + i;
        byte[] bArr = this.buffer;
        if (i3 > bArr.length) {
            int i4 = i2 << 1;
            if (i4 < i2 + i) {
                i4 = i2 + i;
            }
            byte[] bArr2 = new byte[i4];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            this.buffer = bArr2;
        }
        this.size += i;
    }
}
