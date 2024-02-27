package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class EnclosingMethodAttribute extends AttributeInfo {
    public static final String tag = "EnclosingMethod";

    /* JADX INFO: Access modifiers changed from: package-private */
    public EnclosingMethodAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public EnclosingMethodAttribute(ConstPool constPool, String str, String str2, String str3) {
        super(constPool, tag);
        int addClassInfo = constPool.addClassInfo(str);
        int addNameAndTypeInfo = constPool.addNameAndTypeInfo(str2, str3);
        set(new byte[]{(byte) (addClassInfo >>> 8), (byte) addClassInfo, (byte) (addNameAndTypeInfo >>> 8), (byte) addNameAndTypeInfo});
    }

    public EnclosingMethodAttribute(ConstPool constPool, String str) {
        super(constPool, tag);
        int addClassInfo = constPool.addClassInfo(str);
        set(new byte[]{(byte) (addClassInfo >>> 8), (byte) addClassInfo, (byte) 0, (byte) 0});
    }

    public int classIndex() {
        return ByteArray.readU16bit(get(), 0);
    }

    public int methodIndex() {
        return ByteArray.readU16bit(get(), 2);
    }

    public String className() {
        return getConstPool().getClassInfo(classIndex());
    }

    public String methodName() {
        ConstPool constPool = getConstPool();
        int methodIndex = methodIndex();
        return methodIndex == 0 ? MethodInfo.nameClinit : constPool.getUtf8Info(constPool.getNameAndTypeName(methodIndex));
    }

    public String methodDescriptor() {
        ConstPool constPool = getConstPool();
        return constPool.getUtf8Info(constPool.getNameAndTypeDescriptor(methodIndex()));
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        if (methodIndex() == 0) {
            return new EnclosingMethodAttribute(constPool, className());
        }
        return new EnclosingMethodAttribute(constPool, className(), methodName(), methodDescriptor());
    }
}
