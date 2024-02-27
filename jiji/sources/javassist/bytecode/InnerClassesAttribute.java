package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class InnerClassesAttribute extends AttributeInfo {
    public static final String tag = "InnerClasses";

    /* JADX INFO: Access modifiers changed from: package-private */
    public InnerClassesAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    private InnerClassesAttribute(ConstPool constPool, byte[] bArr) {
        super(constPool, tag, bArr);
    }

    public InnerClassesAttribute(ConstPool constPool) {
        super(constPool, tag, new byte[2]);
        ByteArray.write16bit(0, get(), 0);
    }

    public int tableLength() {
        return ByteArray.readU16bit(get(), 0);
    }

    public int innerClassIndex(int i) {
        return ByteArray.readU16bit(get(), (i * 8) + 2);
    }

    public String innerClass(int i) {
        int innerClassIndex = innerClassIndex(i);
        if (innerClassIndex == 0) {
            return null;
        }
        return this.constPool.getClassInfo(innerClassIndex);
    }

    public void setInnerClassIndex(int i, int i2) {
        ByteArray.write16bit(i2, get(), (i * 8) + 2);
    }

    public int outerClassIndex(int i) {
        return ByteArray.readU16bit(get(), (i * 8) + 4);
    }

    public String outerClass(int i) {
        int outerClassIndex = outerClassIndex(i);
        if (outerClassIndex == 0) {
            return null;
        }
        return this.constPool.getClassInfo(outerClassIndex);
    }

    public void setOuterClassIndex(int i, int i2) {
        ByteArray.write16bit(i2, get(), (i * 8) + 4);
    }

    public int innerNameIndex(int i) {
        return ByteArray.readU16bit(get(), (i * 8) + 6);
    }

    public String innerName(int i) {
        int innerNameIndex = innerNameIndex(i);
        if (innerNameIndex == 0) {
            return null;
        }
        return this.constPool.getUtf8Info(innerNameIndex);
    }

    public void setInnerNameIndex(int i, int i2) {
        ByteArray.write16bit(i2, get(), (i * 8) + 6);
    }

    public int accessFlags(int i) {
        return ByteArray.readU16bit(get(), (i * 8) + 8);
    }

    public void setAccessFlags(int i, int i2) {
        ByteArray.write16bit(i2, get(), (i * 8) + 8);
    }

    public int find(String str) {
        int tableLength = tableLength();
        for (int i = 0; i < tableLength; i++) {
            if (str.equals(innerClass(i))) {
                return i;
            }
        }
        return -1;
    }

    public void append(String str, String str2, String str3, int i) {
        append(this.constPool.addClassInfo(str), this.constPool.addClassInfo(str2), this.constPool.addUtf8Info(str3), i);
    }

    public void append(int i, int i2, int i3, int i4) {
        byte[] bArr = get();
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 8];
        for (int i5 = 2; i5 < length; i5++) {
            bArr2[i5] = bArr[i5];
        }
        ByteArray.write16bit(ByteArray.readU16bit(bArr, 0) + 1, bArr2, 0);
        ByteArray.write16bit(i, bArr2, length);
        ByteArray.write16bit(i2, bArr2, length + 2);
        ByteArray.write16bit(i3, bArr2, length + 4);
        ByteArray.write16bit(i4, bArr2, length + 6);
        set(bArr2);
    }

    public int remove(int i) {
        byte[] bArr = get();
        int length = bArr.length;
        if (length < 10) {
            return 0;
        }
        int readU16bit = ByteArray.readU16bit(bArr, 0);
        int i2 = 2;
        int i3 = (i * 8) + 2;
        if (readU16bit <= i) {
            return readU16bit;
        }
        byte[] bArr2 = new byte[length - 8];
        int i4 = readU16bit - 1;
        ByteArray.write16bit(i4, bArr2, 0);
        int i5 = 2;
        while (i2 < length) {
            if (i2 == i3) {
                i2 += 8;
            } else {
                bArr2[i5] = bArr[i2];
                i5++;
                i2++;
            }
        }
        set(bArr2);
        return i4;
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        byte[] bArr = get();
        byte[] bArr2 = new byte[bArr.length];
        ConstPool constPool2 = getConstPool();
        InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute(constPool, bArr2);
        int readU16bit = ByteArray.readU16bit(bArr, 0);
        ByteArray.write16bit(readU16bit, bArr2, 0);
        int i = 2;
        for (int i2 = 0; i2 < readU16bit; i2++) {
            int readU16bit2 = ByteArray.readU16bit(bArr, i);
            int i3 = i + 2;
            int readU16bit3 = ByteArray.readU16bit(bArr, i3);
            int i4 = i + 4;
            int readU16bit4 = ByteArray.readU16bit(bArr, i4);
            int i5 = i + 6;
            int readU16bit5 = ByteArray.readU16bit(bArr, i5);
            if (readU16bit2 != 0) {
                readU16bit2 = constPool2.copy(readU16bit2, constPool, map);
            }
            ByteArray.write16bit(readU16bit2, bArr2, i);
            if (readU16bit3 != 0) {
                readU16bit3 = constPool2.copy(readU16bit3, constPool, map);
            }
            ByteArray.write16bit(readU16bit3, bArr2, i3);
            if (readU16bit4 != 0) {
                readU16bit4 = constPool2.copy(readU16bit4, constPool, map);
            }
            ByteArray.write16bit(readU16bit4, bArr2, i4);
            ByteArray.write16bit(readU16bit5, bArr2, i5);
            i += 8;
        }
        return innerClassesAttribute;
    }
}
