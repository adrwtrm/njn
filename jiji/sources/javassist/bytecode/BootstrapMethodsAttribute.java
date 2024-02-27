package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class BootstrapMethodsAttribute extends AttributeInfo {
    public static final String tag = "BootstrapMethods";

    /* loaded from: classes2.dex */
    public static class BootstrapMethod {
        public int[] arguments;
        public int methodRef;

        public BootstrapMethod(int i, int[] iArr) {
            this.methodRef = i;
            this.arguments = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BootstrapMethodsAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public BootstrapMethodsAttribute(ConstPool constPool, BootstrapMethod[] bootstrapMethodArr) {
        super(constPool, tag);
        int i = 2;
        int i2 = 2;
        for (BootstrapMethod bootstrapMethod : bootstrapMethodArr) {
            i2 += (bootstrapMethod.arguments.length * 2) + 4;
        }
        byte[] bArr = new byte[i2];
        ByteArray.write16bit(bootstrapMethodArr.length, bArr, 0);
        for (int i3 = 0; i3 < bootstrapMethodArr.length; i3++) {
            ByteArray.write16bit(bootstrapMethodArr[i3].methodRef, bArr, i);
            ByteArray.write16bit(bootstrapMethodArr[i3].arguments.length, bArr, i + 2);
            i += 4;
            for (int i4 : bootstrapMethodArr[i3].arguments) {
                ByteArray.write16bit(i4, bArr, i);
                i += 2;
            }
        }
        set(bArr);
    }

    public BootstrapMethod[] getMethods() {
        byte[] bArr = get();
        int readU16bit = ByteArray.readU16bit(bArr, 0);
        BootstrapMethod[] bootstrapMethodArr = new BootstrapMethod[readU16bit];
        int i = 2;
        for (int i2 = 0; i2 < readU16bit; i2++) {
            int readU16bit2 = ByteArray.readU16bit(bArr, i);
            int readU16bit3 = ByteArray.readU16bit(bArr, i + 2);
            int[] iArr = new int[readU16bit3];
            i += 4;
            for (int i3 = 0; i3 < readU16bit3; i3++) {
                iArr[i3] = ByteArray.readU16bit(bArr, i);
                i += 2;
            }
            bootstrapMethodArr[i2] = new BootstrapMethod(readU16bit2, iArr);
        }
        return bootstrapMethodArr;
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        BootstrapMethod[] methods = getMethods();
        ConstPool constPool2 = getConstPool();
        for (BootstrapMethod bootstrapMethod : methods) {
            bootstrapMethod.methodRef = constPool2.copy(bootstrapMethod.methodRef, constPool, map);
            for (int i = 0; i < bootstrapMethod.arguments.length; i++) {
                bootstrapMethod.arguments[i] = constPool2.copy(bootstrapMethod.arguments[i], constPool, map);
            }
        }
        return new BootstrapMethodsAttribute(constPool, methods);
    }
}
