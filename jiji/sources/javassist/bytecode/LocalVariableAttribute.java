package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class LocalVariableAttribute extends AttributeInfo {
    public static final String tag = "LocalVariableTable";
    public static final String typeTag = "LocalVariableTypeTable";

    public LocalVariableAttribute(ConstPool constPool) {
        super(constPool, tag, new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }

    @Deprecated
    public LocalVariableAttribute(ConstPool constPool, String str) {
        super(constPool, str, new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalVariableAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalVariableAttribute(ConstPool constPool, String str, byte[] bArr) {
        super(constPool, str, bArr);
    }

    public void addEntry(int i, int i2, int i3, int i4, int i5) {
        int length = this.info.length;
        byte[] bArr = new byte[length + 10];
        ByteArray.write16bit(tableLength() + 1, bArr, 0);
        for (int i6 = 2; i6 < length; i6++) {
            bArr[i6] = this.info[i6];
        }
        ByteArray.write16bit(i, bArr, length);
        ByteArray.write16bit(i2, bArr, length + 2);
        ByteArray.write16bit(i3, bArr, length + 4);
        ByteArray.write16bit(i4, bArr, length + 6);
        ByteArray.write16bit(i5, bArr, length + 8);
        this.info = bArr;
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(String str, String str2) {
        ConstPool constPool = getConstPool();
        int tableLength = tableLength();
        for (int i = 0; i < tableLength; i++) {
            int i2 = (i * 10) + 2 + 6;
            int readU16bit = ByteArray.readU16bit(this.info, i2);
            if (readU16bit != 0) {
                ByteArray.write16bit(constPool.addUtf8Info(renameEntry(constPool.getUtf8Info(readU16bit), str, str2)), this.info, i2);
            }
        }
    }

    String renameEntry(String str, String str2, String str3) {
        return Descriptor.rename(str, str2, str3);
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(Map<String, String> map) {
        ConstPool constPool = getConstPool();
        int tableLength = tableLength();
        for (int i = 0; i < tableLength; i++) {
            int i2 = (i * 10) + 2 + 6;
            int readU16bit = ByteArray.readU16bit(this.info, i2);
            if (readU16bit != 0) {
                ByteArray.write16bit(constPool.addUtf8Info(renameEntry(constPool.getUtf8Info(readU16bit), map)), this.info, i2);
            }
        }
    }

    String renameEntry(String str, Map<String, String> map) {
        return Descriptor.rename(str, map);
    }

    public void shiftIndex(int i, int i2) {
        int length = this.info.length;
        for (int i3 = 2; i3 < length; i3 += 10) {
            int i4 = i3 + 8;
            int readU16bit = ByteArray.readU16bit(this.info, i4);
            if (readU16bit >= i) {
                ByteArray.write16bit(readU16bit + i2, this.info, i4);
            }
        }
    }

    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }

    public int startPc(int i) {
        return ByteArray.readU16bit(this.info, (i * 10) + 2);
    }

    public int codeLength(int i) {
        return ByteArray.readU16bit(this.info, (i * 10) + 4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftPc(int i, int i2, boolean z) {
        int tableLength = tableLength();
        for (int i3 = 0; i3 < tableLength; i3++) {
            int i4 = (i3 * 10) + 2;
            int readU16bit = ByteArray.readU16bit(this.info, i4);
            int i5 = i4 + 2;
            int readU16bit2 = ByteArray.readU16bit(this.info, i5);
            if (readU16bit > i || (z && readU16bit == i && readU16bit != 0)) {
                ByteArray.write16bit(readU16bit + i2, this.info, i4);
            } else {
                int i6 = readU16bit + readU16bit2;
                if (i6 > i || (z && i6 == i)) {
                    ByteArray.write16bit(readU16bit2 + i2, this.info, i5);
                }
            }
        }
    }

    public int nameIndex(int i) {
        return ByteArray.readU16bit(this.info, (i * 10) + 6);
    }

    public String variableName(int i) {
        return getConstPool().getUtf8Info(nameIndex(i));
    }

    public int descriptorIndex(int i) {
        return ByteArray.readU16bit(this.info, (i * 10) + 8);
    }

    public int signatureIndex(int i) {
        return descriptorIndex(i);
    }

    public String descriptor(int i) {
        return getConstPool().getUtf8Info(descriptorIndex(i));
    }

    public String signature(int i) {
        return descriptor(i);
    }

    public int index(int i) {
        return ByteArray.readU16bit(this.info, (i * 10) + 10);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        byte[] bArr = get();
        byte[] bArr2 = new byte[bArr.length];
        ConstPool constPool2 = getConstPool();
        LocalVariableAttribute makeThisAttr = makeThisAttr(constPool, bArr2);
        int i = 0;
        int readU16bit = ByteArray.readU16bit(bArr, 0);
        ByteArray.write16bit(readU16bit, bArr2, 0);
        int i2 = 2;
        while (i < readU16bit) {
            int readU16bit2 = ByteArray.readU16bit(bArr, i2);
            int i3 = i2 + 2;
            int readU16bit3 = ByteArray.readU16bit(bArr, i3);
            int i4 = i2 + 4;
            int readU16bit4 = ByteArray.readU16bit(bArr, i4);
            int i5 = i2 + 6;
            int readU16bit5 = ByteArray.readU16bit(bArr, i5);
            int i6 = i2 + 8;
            int i7 = readU16bit;
            int readU16bit6 = ByteArray.readU16bit(bArr, i6);
            ByteArray.write16bit(readU16bit2, bArr2, i2);
            ByteArray.write16bit(readU16bit3, bArr2, i3);
            if (readU16bit4 != 0) {
                readU16bit4 = constPool2.copy(readU16bit4, constPool, null);
            }
            ByteArray.write16bit(readU16bit4, bArr2, i4);
            if (readU16bit5 != 0) {
                readU16bit5 = constPool.addUtf8Info(Descriptor.rename(constPool2.getUtf8Info(readU16bit5), map));
            }
            ByteArray.write16bit(readU16bit5, bArr2, i5);
            ByteArray.write16bit(readU16bit6, bArr2, i6);
            i2 += 10;
            i++;
            readU16bit = i7;
        }
        return makeThisAttr;
    }

    LocalVariableAttribute makeThisAttr(ConstPool constPool, byte[] bArr) {
        return new LocalVariableAttribute(constPool, tag, bArr);
    }
}
