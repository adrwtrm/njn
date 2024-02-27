package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javassist.ClassPool;
import javassist.bytecode.stackmap.MapMaker;

/* loaded from: classes2.dex */
public class MethodInfo {
    public static boolean doPreverify = false;
    public static final String nameClinit = "<clinit>";
    public static final String nameInit = "<init>";
    int accessFlags;
    List<AttributeInfo> attribute;
    String cachedName;
    ConstPool constPool;
    int descriptor;
    int name;

    private MethodInfo(ConstPool constPool) {
        this.constPool = constPool;
        this.attribute = null;
    }

    public MethodInfo(ConstPool constPool, String str, String str2) {
        this(constPool);
        this.accessFlags = 0;
        this.name = constPool.addUtf8Info(str);
        this.cachedName = str;
        this.descriptor = this.constPool.addUtf8Info(str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MethodInfo(ConstPool constPool, DataInputStream dataInputStream) throws IOException {
        this(constPool);
        read(dataInputStream);
    }

    public MethodInfo(ConstPool constPool, String str, MethodInfo methodInfo, Map<String, String> map) throws BadBytecode {
        this(constPool);
        read(methodInfo, str, map);
    }

    public String toString() {
        return getName() + " " + getDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void compact(ConstPool constPool) {
        this.name = constPool.addUtf8Info(getName());
        this.descriptor = constPool.addUtf8Info(getDescriptor());
        this.attribute = AttributeInfo.copyAll(this.attribute, constPool);
        this.constPool = constPool;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void prune(ConstPool constPool) {
        ArrayList arrayList = new ArrayList();
        AttributeInfo attribute = getAttribute(AnnotationsAttribute.invisibleTag);
        if (attribute != null) {
            arrayList.add(attribute.copy(constPool, null));
        }
        AttributeInfo attribute2 = getAttribute(AnnotationsAttribute.visibleTag);
        if (attribute2 != null) {
            arrayList.add(attribute2.copy(constPool, null));
        }
        AttributeInfo attribute3 = getAttribute(ParameterAnnotationsAttribute.invisibleTag);
        if (attribute3 != null) {
            arrayList.add(attribute3.copy(constPool, null));
        }
        AttributeInfo attribute4 = getAttribute(ParameterAnnotationsAttribute.visibleTag);
        if (attribute4 != null) {
            arrayList.add(attribute4.copy(constPool, null));
        }
        AnnotationDefaultAttribute annotationDefaultAttribute = (AnnotationDefaultAttribute) getAttribute(AnnotationDefaultAttribute.tag);
        if (annotationDefaultAttribute != null) {
            arrayList.add(annotationDefaultAttribute);
        }
        ExceptionsAttribute exceptionsAttribute = getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            arrayList.add(exceptionsAttribute);
        }
        AttributeInfo attribute5 = getAttribute(SignatureAttribute.tag);
        if (attribute5 != null) {
            arrayList.add(attribute5.copy(constPool, null));
        }
        this.attribute = arrayList;
        this.name = constPool.addUtf8Info(getName());
        this.descriptor = constPool.addUtf8Info(getDescriptor());
        this.constPool = constPool;
    }

    public String getName() {
        if (this.cachedName == null) {
            this.cachedName = this.constPool.getUtf8Info(this.name);
        }
        return this.cachedName;
    }

    public void setName(String str) {
        this.name = this.constPool.addUtf8Info(str);
        this.cachedName = str;
    }

    public boolean isMethod() {
        String name = getName();
        return (name.equals("<init>") || name.equals(nameClinit)) ? false : true;
    }

    public ConstPool getConstPool() {
        return this.constPool;
    }

    public boolean isConstructor() {
        return getName().equals("<init>");
    }

    public boolean isStaticInitializer() {
        return getName().equals(nameClinit);
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int i) {
        this.accessFlags = i;
    }

    public String getDescriptor() {
        return this.constPool.getUtf8Info(this.descriptor);
    }

    public void setDescriptor(String str) {
        if (str.equals(getDescriptor())) {
            return;
        }
        this.descriptor = this.constPool.addUtf8Info(str);
    }

    public List<AttributeInfo> getAttributes() {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        return this.attribute;
    }

    public AttributeInfo getAttribute(String str) {
        return AttributeInfo.lookup(this.attribute, str);
    }

    public AttributeInfo removeAttribute(String str) {
        return AttributeInfo.remove(this.attribute, str);
    }

    public void addAttribute(AttributeInfo attributeInfo) {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        AttributeInfo.remove(this.attribute, attributeInfo.getName());
        this.attribute.add(attributeInfo);
    }

    public ExceptionsAttribute getExceptionsAttribute() {
        return (ExceptionsAttribute) AttributeInfo.lookup(this.attribute, ExceptionsAttribute.tag);
    }

    public CodeAttribute getCodeAttribute() {
        return (CodeAttribute) AttributeInfo.lookup(this.attribute, CodeAttribute.tag);
    }

    public void removeExceptionsAttribute() {
        AttributeInfo.remove(this.attribute, ExceptionsAttribute.tag);
    }

    public void setExceptionsAttribute(ExceptionsAttribute exceptionsAttribute) {
        removeExceptionsAttribute();
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        this.attribute.add(exceptionsAttribute);
    }

    public void removeCodeAttribute() {
        AttributeInfo.remove(this.attribute, CodeAttribute.tag);
    }

    public void setCodeAttribute(CodeAttribute codeAttribute) {
        removeCodeAttribute();
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        this.attribute.add(codeAttribute);
    }

    public void rebuildStackMapIf6(ClassPool classPool, ClassFile classFile) throws BadBytecode {
        if (classFile.getMajorVersion() >= 50) {
            rebuildStackMap(classPool);
        }
        if (doPreverify) {
            rebuildStackMapForME(classPool);
        }
    }

    public void rebuildStackMap(ClassPool classPool) throws BadBytecode {
        CodeAttribute codeAttribute = getCodeAttribute();
        if (codeAttribute != null) {
            codeAttribute.setAttribute(MapMaker.make(classPool, this));
        }
    }

    public void rebuildStackMapForME(ClassPool classPool) throws BadBytecode {
        CodeAttribute codeAttribute = getCodeAttribute();
        if (codeAttribute != null) {
            codeAttribute.setAttribute(MapMaker.make2(classPool, this));
        }
    }

    public int getLineNumber(int i) {
        LineNumberAttribute lineNumberAttribute;
        CodeAttribute codeAttribute = getCodeAttribute();
        if (codeAttribute == null || (lineNumberAttribute = (LineNumberAttribute) codeAttribute.getAttribute(LineNumberAttribute.tag)) == null) {
            return -1;
        }
        return lineNumberAttribute.toLineNumber(i);
    }

    public void setSuperclass(String str) throws BadBytecode {
        if (isConstructor()) {
            CodeAttribute codeAttribute = getCodeAttribute();
            byte[] code = codeAttribute.getCode();
            int skipSuperConstructor = codeAttribute.iterator().skipSuperConstructor();
            if (skipSuperConstructor >= 0) {
                ConstPool constPool = this.constPool;
                int i = skipSuperConstructor + 1;
                ByteArray.write16bit(constPool.addMethodrefInfo(constPool.addClassInfo(str), constPool.getMethodrefNameAndType(ByteArray.readU16bit(code, i))), code, i);
            }
        }
    }

    private void read(MethodInfo methodInfo, String str, Map<String, String> map) {
        ConstPool constPool = this.constPool;
        this.accessFlags = methodInfo.accessFlags;
        this.name = constPool.addUtf8Info(str);
        this.cachedName = str;
        this.descriptor = constPool.addUtf8Info(Descriptor.rename(methodInfo.constPool.getUtf8Info(methodInfo.descriptor), map));
        this.attribute = new ArrayList();
        ExceptionsAttribute exceptionsAttribute = methodInfo.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            this.attribute.add(exceptionsAttribute.copy(constPool, map));
        }
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            this.attribute.add(codeAttribute.copy(constPool, map));
        }
    }

    private void read(DataInputStream dataInputStream) throws IOException {
        this.accessFlags = dataInputStream.readUnsignedShort();
        this.name = dataInputStream.readUnsignedShort();
        this.descriptor = dataInputStream.readUnsignedShort();
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        this.attribute = new ArrayList();
        for (int i = 0; i < readUnsignedShort; i++) {
            this.attribute.add(AttributeInfo.read(this.constPool, dataInputStream));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.accessFlags);
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeShort(this.descriptor);
        List<AttributeInfo> list = this.attribute;
        if (list == null) {
            dataOutputStream.writeShort(0);
            return;
        }
        dataOutputStream.writeShort(list.size());
        AttributeInfo.writeAll(this.attribute, dataOutputStream);
    }
}
