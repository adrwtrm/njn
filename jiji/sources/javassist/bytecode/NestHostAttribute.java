package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class NestHostAttribute extends AttributeInfo {
    public static final String tag = "NestHost";

    /* JADX INFO: Access modifiers changed from: package-private */
    public NestHostAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    private NestHostAttribute(ConstPool constPool, int i) {
        super(constPool, tag, new byte[2]);
        ByteArray.write16bit(i, get(), 0);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        return new NestHostAttribute(constPool, getConstPool().copy(ByteArray.readU16bit(get(), 0), constPool, map));
    }

    public int hostClassIndex() {
        return ByteArray.readU16bit(this.info, 0);
    }
}
