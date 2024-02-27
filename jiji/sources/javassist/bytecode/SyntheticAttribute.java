package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class SyntheticAttribute extends AttributeInfo {
    public static final String tag = "Synthetic";

    /* JADX INFO: Access modifiers changed from: package-private */
    public SyntheticAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public SyntheticAttribute(ConstPool constPool) {
        super(constPool, tag, new byte[0]);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        return new SyntheticAttribute(constPool);
    }
}
