package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class NestMembersAttribute extends AttributeInfo {
    public static final String tag = "NestMembers";

    /* JADX INFO: Access modifiers changed from: package-private */
    public NestMembersAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    private NestMembersAttribute(ConstPool constPool, byte[] bArr) {
        super(constPool, tag, bArr);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        byte[] bArr = get();
        byte[] bArr2 = new byte[bArr.length];
        ConstPool constPool2 = getConstPool();
        int i = 0;
        int readU16bit = ByteArray.readU16bit(bArr, 0);
        ByteArray.write16bit(readU16bit, bArr2, 0);
        int i2 = 2;
        while (i < readU16bit) {
            ByteArray.write16bit(constPool2.copy(ByteArray.readU16bit(bArr, i2), constPool, map), bArr2, i2);
            i++;
            i2 += 2;
        }
        return new NestMembersAttribute(constPool, bArr2);
    }

    public int numberOfClasses() {
        return ByteArray.readU16bit(this.info, 0);
    }

    public int memberClass(int i) {
        return ByteArray.readU16bit(this.info, (i * 2) + 2);
    }
}
