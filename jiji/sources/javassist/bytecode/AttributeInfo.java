package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AttributeInfo {
    protected ConstPool constPool;
    byte[] info;
    int name;

    void getRefClasses(Map<String, String> map) {
    }

    void renameClass(String str, String str2) {
    }

    void renameClass(Map<String, String> map) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AttributeInfo(ConstPool constPool, int i, byte[] bArr) {
        this.constPool = constPool;
        this.name = i;
        this.info = bArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AttributeInfo(ConstPool constPool, String str) {
        this(constPool, str, (byte[]) null);
    }

    public AttributeInfo(ConstPool constPool, String str, byte[] bArr) {
        this(constPool, constPool.addUtf8Info(str), bArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AttributeInfo(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        this.constPool = constPool;
        this.name = i;
        int readInt = dataInputStream.readInt();
        byte[] bArr = new byte[readInt];
        this.info = bArr;
        if (readInt > 0) {
            dataInputStream.readFully(bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AttributeInfo read(ConstPool constPool, DataInputStream dataInputStream) throws IOException {
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        String utf8Info = constPool.getUtf8Info(readUnsignedShort);
        char charAt = utf8Info.charAt(0);
        if (charAt < 'E') {
            if (utf8Info.equals(AnnotationDefaultAttribute.tag)) {
                return new AnnotationDefaultAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(BootstrapMethodsAttribute.tag)) {
                return new BootstrapMethodsAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(CodeAttribute.tag)) {
                return new CodeAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(ConstantAttribute.tag)) {
                return new ConstantAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(DeprecatedAttribute.tag)) {
                return new DeprecatedAttribute(constPool, readUnsignedShort, dataInputStream);
            }
        }
        if (charAt < 'M') {
            if (utf8Info.equals(EnclosingMethodAttribute.tag)) {
                return new EnclosingMethodAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(ExceptionsAttribute.tag)) {
                return new ExceptionsAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(InnerClassesAttribute.tag)) {
                return new InnerClassesAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(LineNumberAttribute.tag)) {
                return new LineNumberAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(LocalVariableAttribute.tag)) {
                return new LocalVariableAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals("LocalVariableTypeTable")) {
                return new LocalVariableTypeAttribute(constPool, readUnsignedShort, dataInputStream);
            }
        }
        if (charAt < 'S') {
            if (utf8Info.equals(MethodParametersAttribute.tag)) {
                return new MethodParametersAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(NestHostAttribute.tag)) {
                return new NestHostAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(NestMembersAttribute.tag)) {
                return new NestMembersAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(AnnotationsAttribute.visibleTag) || utf8Info.equals(AnnotationsAttribute.invisibleTag)) {
                return new AnnotationsAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(ParameterAnnotationsAttribute.visibleTag) || utf8Info.equals(ParameterAnnotationsAttribute.invisibleTag)) {
                return new ParameterAnnotationsAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(TypeAnnotationsAttribute.visibleTag) || utf8Info.equals(TypeAnnotationsAttribute.invisibleTag)) {
                return new TypeAnnotationsAttribute(constPool, readUnsignedShort, dataInputStream);
            }
        }
        if (charAt >= 'S') {
            if (utf8Info.equals(SignatureAttribute.tag)) {
                return new SignatureAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(SourceFileAttribute.tag)) {
                return new SourceFileAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(SyntheticAttribute.tag)) {
                return new SyntheticAttribute(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(StackMap.tag)) {
                return new StackMap(constPool, readUnsignedShort, dataInputStream);
            }
            if (utf8Info.equals(StackMapTable.tag)) {
                return new StackMapTable(constPool, readUnsignedShort, dataInputStream);
            }
        }
        return new AttributeInfo(constPool, readUnsignedShort, dataInputStream);
    }

    public String getName() {
        return this.constPool.getUtf8Info(this.name);
    }

    public ConstPool getConstPool() {
        return this.constPool;
    }

    public int length() {
        return this.info.length + 6;
    }

    public byte[] get() {
        return this.info;
    }

    public void set(byte[] bArr) {
        this.info = bArr;
    }

    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        String name = getName();
        byte[] bArr = this.info;
        return new AttributeInfo(constPool, name, Arrays.copyOf(bArr, bArr.length));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeInt(this.info.length);
        byte[] bArr = this.info;
        if (bArr.length > 0) {
            dataOutputStream.write(bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getLength(List<AttributeInfo> list) {
        int i = 0;
        for (AttributeInfo attributeInfo : list) {
            i += attributeInfo.length();
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AttributeInfo lookup(List<AttributeInfo> list, String str) {
        if (list == null) {
            return null;
        }
        for (AttributeInfo attributeInfo : list) {
            if (attributeInfo.getName().equals(str)) {
                return attributeInfo;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized AttributeInfo remove(List<AttributeInfo> list, String str) {
        synchronized (AttributeInfo.class) {
            if (list == null) {
                return null;
            }
            for (AttributeInfo attributeInfo : list) {
                if (attributeInfo.getName().equals(str) && list.remove(attributeInfo)) {
                    return attributeInfo;
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeAll(List<AttributeInfo> list, DataOutputStream dataOutputStream) throws IOException {
        if (list == null) {
            return;
        }
        for (AttributeInfo attributeInfo : list) {
            attributeInfo.write(dataOutputStream);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<AttributeInfo> copyAll(List<AttributeInfo> list, ConstPool constPool) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (AttributeInfo attributeInfo : list) {
            arrayList.add(attributeInfo.copy(constPool, null));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void renameClass(List<AttributeInfo> list, String str, String str2) {
        if (list == null) {
            return;
        }
        for (AttributeInfo attributeInfo : list) {
            attributeInfo.renameClass(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void renameClass(List<AttributeInfo> list, Map<String, String> map) {
        if (list == null) {
            return;
        }
        for (AttributeInfo attributeInfo : list) {
            attributeInfo.renameClass(map);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void getRefClasses(List<AttributeInfo> list, Map<String, String> map) {
        if (list == null) {
            return;
        }
        for (AttributeInfo attributeInfo : list) {
            attributeInfo.getRefClasses(map);
        }
    }
}
