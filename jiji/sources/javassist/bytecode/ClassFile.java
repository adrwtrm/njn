package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javassist.CannotCompileException;

/* loaded from: classes2.dex */
public final class ClassFile {
    public static final int JAVA_1 = 45;
    public static final int JAVA_10 = 54;
    public static final int JAVA_11 = 55;
    public static final int JAVA_2 = 46;
    public static final int JAVA_3 = 47;
    public static final int JAVA_4 = 48;
    public static final int JAVA_5 = 49;
    public static final int JAVA_6 = 50;
    public static final int JAVA_7 = 51;
    public static final int JAVA_8 = 52;
    public static final int JAVA_9 = 53;
    public static final int MAJOR_VERSION;
    int accessFlags;
    List<AttributeInfo> attributes;
    String[] cachedInterfaces;
    String cachedSuperclass;
    ConstPool constPool;
    List<FieldInfo> fields;
    int[] interfaces;
    int major;
    List<MethodInfo> methods;
    int minor;
    int superClass;
    int thisClass;
    String thisclassname;

    static {
        int i = 47;
        try {
            Class.forName("java.lang.StringBuilder");
            Class.forName("java.util.zip.DeflaterInputStream");
            Class.forName("java.lang.invoke.CallSite", false, ClassLoader.getSystemClassLoader());
            Class.forName("java.util.function.Function");
            Class.forName("java.lang.Module");
            List.class.getMethod("copyOf", Collection.class);
            i = 54;
            Class.forName("java.util.Optional").getMethod("isEmpty", new Class[0]);
            i = 55;
        } catch (Throwable unused) {
        }
        MAJOR_VERSION = i;
    }

    public ClassFile(DataInputStream dataInputStream) throws IOException {
        read(dataInputStream);
    }

    public ClassFile(boolean z, String str, String str2) {
        this.major = MAJOR_VERSION;
        this.minor = 0;
        ConstPool constPool = new ConstPool(str);
        this.constPool = constPool;
        this.thisClass = constPool.getThisClassInfo();
        if (z) {
            this.accessFlags = 1536;
        } else {
            this.accessFlags = 32;
        }
        initSuperclass(str2);
        this.interfaces = null;
        this.fields = new ArrayList();
        this.methods = new ArrayList();
        this.thisclassname = str;
        ArrayList arrayList = new ArrayList();
        this.attributes = arrayList;
        arrayList.add(new SourceFileAttribute(this.constPool, getSourcefileName(this.thisclassname)));
    }

    private void initSuperclass(String str) {
        if (str != null) {
            this.superClass = this.constPool.addClassInfo(str);
            this.cachedSuperclass = str;
            return;
        }
        this.superClass = this.constPool.addClassInfo("java.lang.Object");
        this.cachedSuperclass = "java.lang.Object";
    }

    private static String getSourcefileName(String str) {
        return str.replaceAll("^.*\\.", "") + ".java";
    }

    public void compact() {
        ConstPool compact0 = compact0();
        for (MethodInfo methodInfo : this.methods) {
            methodInfo.compact(compact0);
        }
        for (FieldInfo fieldInfo : this.fields) {
            fieldInfo.compact(compact0);
        }
        this.attributes = AttributeInfo.copyAll(this.attributes, compact0);
        this.constPool = compact0;
    }

    private ConstPool compact0() {
        ConstPool constPool = new ConstPool(this.thisclassname);
        this.thisClass = constPool.getThisClassInfo();
        if (getSuperclass() != null) {
            this.superClass = constPool.addClassInfo(getSuperclass());
        }
        if (this.interfaces != null) {
            int i = 0;
            while (true) {
                int[] iArr = this.interfaces;
                if (i >= iArr.length) {
                    break;
                }
                iArr[i] = constPool.addClassInfo(this.constPool.getClassInfo(iArr[i]));
                i++;
            }
        }
        return constPool;
    }

    public void prune() {
        ConstPool compact0 = compact0();
        ArrayList arrayList = new ArrayList();
        AttributeInfo attribute = getAttribute(AnnotationsAttribute.invisibleTag);
        if (attribute != null) {
            arrayList.add(attribute.copy(compact0, null));
        }
        AttributeInfo attribute2 = getAttribute(AnnotationsAttribute.visibleTag);
        if (attribute2 != null) {
            arrayList.add(attribute2.copy(compact0, null));
        }
        AttributeInfo attribute3 = getAttribute(SignatureAttribute.tag);
        if (attribute3 != null) {
            arrayList.add(attribute3.copy(compact0, null));
        }
        for (MethodInfo methodInfo : this.methods) {
            methodInfo.prune(compact0);
        }
        for (FieldInfo fieldInfo : this.fields) {
            fieldInfo.prune(compact0);
        }
        this.attributes = arrayList;
        this.constPool = compact0;
    }

    public ConstPool getConstPool() {
        return this.constPool;
    }

    public boolean isInterface() {
        return (this.accessFlags & 512) != 0;
    }

    public boolean isFinal() {
        return (this.accessFlags & 16) != 0;
    }

    public boolean isAbstract() {
        return (this.accessFlags & 1024) != 0;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int i) {
        if ((i & 512) == 0) {
            i |= 32;
        }
        this.accessFlags = i;
    }

    public int getInnerAccessFlags() {
        InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) getAttribute(InnerClassesAttribute.tag);
        if (innerClassesAttribute == null) {
            return -1;
        }
        String name = getName();
        int tableLength = innerClassesAttribute.tableLength();
        for (int i = 0; i < tableLength; i++) {
            if (name.equals(innerClassesAttribute.innerClass(i))) {
                return innerClassesAttribute.accessFlags(i);
            }
        }
        return -1;
    }

    public String getName() {
        return this.thisclassname;
    }

    public void setName(String str) {
        renameClass(this.thisclassname, str);
    }

    public String getSuperclass() {
        if (this.cachedSuperclass == null) {
            this.cachedSuperclass = this.constPool.getClassInfo(this.superClass);
        }
        return this.cachedSuperclass;
    }

    public int getSuperclassId() {
        return this.superClass;
    }

    public void setSuperclass(String str) throws CannotCompileException {
        if (str == null) {
            str = "java.lang.Object";
        }
        try {
            this.superClass = this.constPool.addClassInfo(str);
            for (MethodInfo methodInfo : this.methods) {
                methodInfo.setSuperclass(str);
            }
            this.cachedSuperclass = str;
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    public final void renameClass(String str, String str2) {
        if (str.equals(str2)) {
            return;
        }
        if (str.equals(this.thisclassname)) {
            this.thisclassname = str2;
        }
        String jvmName = Descriptor.toJvmName(str);
        String jvmName2 = Descriptor.toJvmName(str2);
        this.constPool.renameClass(jvmName, jvmName2);
        AttributeInfo.renameClass(this.attributes, jvmName, jvmName2);
        for (MethodInfo methodInfo : this.methods) {
            methodInfo.setDescriptor(Descriptor.rename(methodInfo.getDescriptor(), jvmName, jvmName2));
            AttributeInfo.renameClass(methodInfo.getAttributes(), jvmName, jvmName2);
        }
        for (FieldInfo fieldInfo : this.fields) {
            fieldInfo.setDescriptor(Descriptor.rename(fieldInfo.getDescriptor(), jvmName, jvmName2));
            AttributeInfo.renameClass(fieldInfo.getAttributes(), jvmName, jvmName2);
        }
    }

    public final void renameClass(Map<String, String> map) {
        String str = map.get(Descriptor.toJvmName(this.thisclassname));
        if (str != null) {
            this.thisclassname = Descriptor.toJavaName(str);
        }
        this.constPool.renameClass(map);
        AttributeInfo.renameClass(this.attributes, map);
        for (MethodInfo methodInfo : this.methods) {
            methodInfo.setDescriptor(Descriptor.rename(methodInfo.getDescriptor(), map));
            AttributeInfo.renameClass(methodInfo.getAttributes(), map);
        }
        for (FieldInfo fieldInfo : this.fields) {
            fieldInfo.setDescriptor(Descriptor.rename(fieldInfo.getDescriptor(), map));
            AttributeInfo.renameClass(fieldInfo.getAttributes(), map);
        }
    }

    public final void getRefClasses(Map<String, String> map) {
        this.constPool.renameClass(map);
        AttributeInfo.getRefClasses(this.attributes, map);
        for (MethodInfo methodInfo : this.methods) {
            Descriptor.rename(methodInfo.getDescriptor(), map);
            AttributeInfo.getRefClasses(methodInfo.getAttributes(), map);
        }
        for (FieldInfo fieldInfo : this.fields) {
            Descriptor.rename(fieldInfo.getDescriptor(), map);
            AttributeInfo.getRefClasses(fieldInfo.getAttributes(), map);
        }
    }

    public String[] getInterfaces() {
        String[] strArr;
        String[] strArr2 = this.cachedInterfaces;
        if (strArr2 != null) {
            return strArr2;
        }
        int[] iArr = this.interfaces;
        int i = 0;
        if (iArr != null) {
            strArr = new String[iArr.length];
            while (true) {
                int[] iArr2 = this.interfaces;
                if (i >= iArr2.length) {
                    break;
                }
                strArr[i] = this.constPool.getClassInfo(iArr2[i]);
                i++;
            }
        } else {
            strArr = new String[0];
        }
        this.cachedInterfaces = strArr;
        return strArr;
    }

    public void setInterfaces(String[] strArr) {
        this.cachedInterfaces = null;
        if (strArr != null) {
            this.interfaces = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                this.interfaces[i] = this.constPool.addClassInfo(strArr[i]);
            }
        }
    }

    public void addInterface(String str) {
        this.cachedInterfaces = null;
        int addClassInfo = this.constPool.addClassInfo(str);
        int[] iArr = this.interfaces;
        if (iArr == null) {
            this.interfaces = r0;
            int[] iArr2 = {addClassInfo};
            return;
        }
        int length = iArr.length;
        int[] iArr3 = new int[length + 1];
        System.arraycopy(iArr, 0, iArr3, 0, length);
        iArr3[length] = addClassInfo;
        this.interfaces = iArr3;
    }

    public List<FieldInfo> getFields() {
        return this.fields;
    }

    public void addField(FieldInfo fieldInfo) throws DuplicateMemberException {
        testExistingField(fieldInfo.getName(), fieldInfo.getDescriptor());
        this.fields.add(fieldInfo);
    }

    public final void addField2(FieldInfo fieldInfo) {
        this.fields.add(fieldInfo);
    }

    private void testExistingField(String str, String str2) throws DuplicateMemberException {
        for (FieldInfo fieldInfo : this.fields) {
            if (fieldInfo.getName().equals(str)) {
                throw new DuplicateMemberException("duplicate field: " + str);
            }
        }
    }

    public List<MethodInfo> getMethods() {
        return this.methods;
    }

    public MethodInfo getMethod(String str) {
        for (MethodInfo methodInfo : this.methods) {
            if (methodInfo.getName().equals(str)) {
                return methodInfo;
            }
        }
        return null;
    }

    public MethodInfo getStaticInitializer() {
        return getMethod(MethodInfo.nameClinit);
    }

    public void addMethod(MethodInfo methodInfo) throws DuplicateMemberException {
        testExistingMethod(methodInfo);
        this.methods.add(methodInfo);
    }

    public final void addMethod2(MethodInfo methodInfo) {
        this.methods.add(methodInfo);
    }

    private void testExistingMethod(MethodInfo methodInfo) throws DuplicateMemberException {
        String name = methodInfo.getName();
        String descriptor = methodInfo.getDescriptor();
        ListIterator<MethodInfo> listIterator = this.methods.listIterator(0);
        while (listIterator.hasNext()) {
            if (isDuplicated(methodInfo, name, descriptor, listIterator.next(), listIterator)) {
                throw new DuplicateMemberException("duplicate method: " + name + " in " + getName());
            }
        }
    }

    private static boolean isDuplicated(MethodInfo methodInfo, String str, String str2, MethodInfo methodInfo2, ListIterator<MethodInfo> listIterator) {
        if (methodInfo2.getName().equals(str)) {
            String descriptor = methodInfo2.getDescriptor();
            if (Descriptor.eqParamTypes(descriptor, str2) && descriptor.equals(str2)) {
                if (notBridgeMethod(methodInfo2)) {
                    return true;
                }
                listIterator.remove();
            }
            return false;
        }
        return false;
    }

    private static boolean notBridgeMethod(MethodInfo methodInfo) {
        return (methodInfo.getAccessFlags() & 64) == 0;
    }

    public List<AttributeInfo> getAttributes() {
        return this.attributes;
    }

    public AttributeInfo getAttribute(String str) {
        for (AttributeInfo attributeInfo : this.attributes) {
            if (attributeInfo.getName().equals(str)) {
                return attributeInfo;
            }
        }
        return null;
    }

    public AttributeInfo removeAttribute(String str) {
        return AttributeInfo.remove(this.attributes, str);
    }

    public void addAttribute(AttributeInfo attributeInfo) {
        AttributeInfo.remove(this.attributes, attributeInfo.getName());
        this.attributes.add(attributeInfo);
    }

    public String getSourceFile() {
        SourceFileAttribute sourceFileAttribute = (SourceFileAttribute) getAttribute(SourceFileAttribute.tag);
        if (sourceFileAttribute == null) {
            return null;
        }
        return sourceFileAttribute.getFileName();
    }

    private void read(DataInputStream dataInputStream) throws IOException {
        int readInt;
        if (dataInputStream.readInt() != -889275714) {
            throw new IOException("bad magic number: " + Integer.toHexString(readInt));
        }
        this.minor = dataInputStream.readUnsignedShort();
        this.major = dataInputStream.readUnsignedShort();
        this.constPool = new ConstPool(dataInputStream);
        this.accessFlags = dataInputStream.readUnsignedShort();
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        this.thisClass = readUnsignedShort;
        this.constPool.setThisClassInfo(readUnsignedShort);
        this.superClass = dataInputStream.readUnsignedShort();
        int readUnsignedShort2 = dataInputStream.readUnsignedShort();
        if (readUnsignedShort2 == 0) {
            this.interfaces = null;
        } else {
            this.interfaces = new int[readUnsignedShort2];
            for (int i = 0; i < readUnsignedShort2; i++) {
                this.interfaces[i] = dataInputStream.readUnsignedShort();
            }
        }
        ConstPool constPool = this.constPool;
        int readUnsignedShort3 = dataInputStream.readUnsignedShort();
        this.fields = new ArrayList();
        for (int i2 = 0; i2 < readUnsignedShort3; i2++) {
            addField2(new FieldInfo(constPool, dataInputStream));
        }
        int readUnsignedShort4 = dataInputStream.readUnsignedShort();
        this.methods = new ArrayList();
        for (int i3 = 0; i3 < readUnsignedShort4; i3++) {
            addMethod2(new MethodInfo(constPool, dataInputStream));
        }
        this.attributes = new ArrayList();
        int readUnsignedShort5 = dataInputStream.readUnsignedShort();
        for (int i4 = 0; i4 < readUnsignedShort5; i4++) {
            addAttribute(AttributeInfo.read(constPool, dataInputStream));
        }
        this.thisclassname = this.constPool.getClassInfo(this.thisClass);
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(-889275714);
        dataOutputStream.writeShort(this.minor);
        dataOutputStream.writeShort(this.major);
        this.constPool.write(dataOutputStream);
        dataOutputStream.writeShort(this.accessFlags);
        dataOutputStream.writeShort(this.thisClass);
        dataOutputStream.writeShort(this.superClass);
        int[] iArr = this.interfaces;
        int length = iArr == null ? 0 : iArr.length;
        dataOutputStream.writeShort(length);
        for (int i = 0; i < length; i++) {
            dataOutputStream.writeShort(this.interfaces[i]);
        }
        int size = this.fields.size();
        dataOutputStream.writeShort(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.fields.get(i2).write(dataOutputStream);
        }
        dataOutputStream.writeShort(this.methods.size());
        for (MethodInfo methodInfo : this.methods) {
            methodInfo.write(dataOutputStream);
        }
        dataOutputStream.writeShort(this.attributes.size());
        AttributeInfo.writeAll(this.attributes, dataOutputStream);
    }

    public int getMajorVersion() {
        return this.major;
    }

    public void setMajorVersion(int i) {
        this.major = i;
    }

    public int getMinorVersion() {
        return this.minor;
    }

    public void setMinorVersion(int i) {
        this.minor = i;
    }

    public void setVersionToJava5() {
        this.major = 49;
        this.minor = 0;
    }
}
