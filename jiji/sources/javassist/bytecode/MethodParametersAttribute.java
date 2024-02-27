package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class MethodParametersAttribute extends AttributeInfo {
    public static final String tag = "MethodParameters";

    /* JADX INFO: Access modifiers changed from: package-private */
    public MethodParametersAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public MethodParametersAttribute(ConstPool constPool, String[] strArr, int[] iArr) {
        super(constPool, tag);
        byte[] bArr = new byte[(strArr.length * 4) + 1];
        bArr[0] = (byte) strArr.length;
        for (int i = 0; i < strArr.length; i++) {
            int i2 = i * 4;
            ByteArray.write16bit(constPool.addUtf8Info(strArr[i]), bArr, i2 + 1);
            ByteArray.write16bit(iArr[i], bArr, i2 + 3);
        }
        set(bArr);
    }

    public int size() {
        return this.info[0] & 255;
    }

    public int name(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 1);
    }

    public int accessFlags(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 3);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        int size = size();
        ConstPool constPool2 = getConstPool();
        String[] strArr = new String[size];
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = constPool2.getUtf8Info(name(i));
            iArr[i] = accessFlags(i);
        }
        return new MethodParametersAttribute(constPool, strArr, iArr);
    }
}
