package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class ExceptionsAttribute extends AttributeInfo {
    public static final String tag = "Exceptions";

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExceptionsAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    private ExceptionsAttribute(ConstPool constPool, ExceptionsAttribute exceptionsAttribute, Map<String, String> map) {
        super(constPool, tag);
        copyFrom(exceptionsAttribute, map);
    }

    public ExceptionsAttribute(ConstPool constPool) {
        super(constPool, tag);
        this.info = new byte[]{0, 0};
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        return new ExceptionsAttribute(constPool, this, map);
    }

    private void copyFrom(ExceptionsAttribute exceptionsAttribute, Map<String, String> map) {
        ConstPool constPool = exceptionsAttribute.constPool;
        ConstPool constPool2 = this.constPool;
        byte[] bArr = exceptionsAttribute.info;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        bArr2[0] = bArr[0];
        bArr2[1] = bArr[1];
        for (int i = 2; i < length; i += 2) {
            ByteArray.write16bit(constPool.copy(ByteArray.readU16bit(bArr, i), constPool2, map), bArr2, i);
        }
        this.info = bArr2;
    }

    public int[] getExceptionIndexes() {
        byte[] bArr = this.info;
        int length = bArr.length;
        int i = 2;
        if (length <= 2) {
            return null;
        }
        int[] iArr = new int[(length / 2) - 1];
        int i2 = 0;
        while (i < length) {
            iArr[i2] = ((bArr[i] & 255) << 8) | (bArr[i + 1] & 255);
            i += 2;
            i2++;
        }
        return iArr;
    }

    public String[] getExceptions() {
        byte[] bArr = this.info;
        int length = bArr.length;
        int i = 2;
        if (length <= 2) {
            return null;
        }
        String[] strArr = new String[(length / 2) - 1];
        int i2 = 0;
        while (i < length) {
            strArr[i2] = this.constPool.getClassInfo(((bArr[i] & 255) << 8) | (bArr[i + 1] & 255));
            i += 2;
            i2++;
        }
        return strArr;
    }

    public void setExceptionIndexes(int[] iArr) {
        int length = iArr.length;
        byte[] bArr = new byte[(length * 2) + 2];
        ByteArray.write16bit(length, bArr, 0);
        for (int i = 0; i < length; i++) {
            ByteArray.write16bit(iArr[i], bArr, (i * 2) + 2);
        }
        this.info = bArr;
    }

    public void setExceptions(String[] strArr) {
        int length = strArr.length;
        byte[] bArr = new byte[(length * 2) + 2];
        ByteArray.write16bit(length, bArr, 0);
        for (int i = 0; i < length; i++) {
            ByteArray.write16bit(this.constPool.addClassInfo(strArr[i]), bArr, (i * 2) + 2);
        }
        this.info = bArr;
    }

    public int tableLength() {
        return (this.info.length / 2) - 1;
    }

    public int getException(int i) {
        int i2 = (i * 2) + 2;
        return (this.info[i2 + 1] & 255) | ((this.info[i2] & 255) << 8);
    }
}
