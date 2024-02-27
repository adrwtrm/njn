package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class ConstantAttribute extends AttributeInfo {
    public static final String tag = "ConstantValue";

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConstantAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public ConstantAttribute(ConstPool constPool, int i) {
        super(constPool, tag);
        set(new byte[]{(byte) (i >>> 8), (byte) i});
    }

    public int getConstantValue() {
        return ByteArray.readU16bit(get(), 0);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        return new ConstantAttribute(constPool, getConstPool().copy(getConstantValue(), constPool, map));
    }
}
