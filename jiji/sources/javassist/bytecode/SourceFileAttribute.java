package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class SourceFileAttribute extends AttributeInfo {
    public static final String tag = "SourceFile";

    /* JADX INFO: Access modifiers changed from: package-private */
    public SourceFileAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public SourceFileAttribute(ConstPool constPool, String str) {
        super(constPool, tag);
        int addUtf8Info = constPool.addUtf8Info(str);
        set(new byte[]{(byte) (addUtf8Info >>> 8), (byte) addUtf8Info});
    }

    public String getFileName() {
        return getConstPool().getUtf8Info(ByteArray.readU16bit(get(), 0));
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        return new SourceFileAttribute(constPool, getFileName());
    }
}
