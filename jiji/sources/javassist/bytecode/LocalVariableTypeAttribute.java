package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class LocalVariableTypeAttribute extends LocalVariableAttribute {
    public static final String tag = "LocalVariableTypeTable";

    public LocalVariableTypeAttribute(ConstPool constPool) {
        super(constPool, "LocalVariableTypeTable", new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalVariableTypeAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    private LocalVariableTypeAttribute(ConstPool constPool, byte[] bArr) {
        super(constPool, "LocalVariableTypeTable", bArr);
    }

    @Override // javassist.bytecode.LocalVariableAttribute
    String renameEntry(String str, String str2, String str3) {
        return SignatureAttribute.renameClass(str, str2, str3);
    }

    @Override // javassist.bytecode.LocalVariableAttribute
    String renameEntry(String str, Map<String, String> map) {
        return SignatureAttribute.renameClass(str, map);
    }

    @Override // javassist.bytecode.LocalVariableAttribute
    LocalVariableAttribute makeThisAttr(ConstPool constPool, byte[] bArr) {
        return new LocalVariableTypeAttribute(constPool, bArr);
    }
}
