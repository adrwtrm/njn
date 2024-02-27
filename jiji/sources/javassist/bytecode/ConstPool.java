package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javassist.CtClass;

/* loaded from: classes2.dex */
public final class ConstPool {
    public static final int CONST_Class = 7;
    public static final int CONST_Double = 6;
    public static final int CONST_Dynamic = 17;
    public static final int CONST_Fieldref = 9;
    public static final int CONST_Float = 4;
    public static final int CONST_Integer = 3;
    public static final int CONST_InterfaceMethodref = 11;
    public static final int CONST_InvokeDynamic = 18;
    public static final int CONST_Long = 5;
    public static final int CONST_MethodHandle = 15;
    public static final int CONST_MethodType = 16;
    public static final int CONST_Methodref = 10;
    public static final int CONST_Module = 19;
    public static final int CONST_NameAndType = 12;
    public static final int CONST_Package = 20;
    public static final int CONST_String = 8;
    public static final int CONST_Utf8 = 1;
    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_invokeInterface = 9;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;
    public static final CtClass THIS = null;
    LongVector items;
    Map<ConstInfo, ConstInfo> itemsCache;
    int numOfItems;
    int thisClassInfo;

    public ConstPool(String str) {
        this.items = new LongVector();
        this.itemsCache = null;
        this.numOfItems = 0;
        addItem0(null);
        this.thisClassInfo = addClassInfo(str);
    }

    public ConstPool(DataInputStream dataInputStream) throws IOException {
        this.itemsCache = null;
        this.thisClassInfo = 0;
        read(dataInputStream);
    }

    void prune() {
        this.itemsCache = null;
    }

    public int getSize() {
        return this.numOfItems;
    }

    public String getClassName() {
        return getClassInfo(this.thisClassInfo);
    }

    public int getThisClassInfo() {
        return this.thisClassInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setThisClassInfo(int i) {
        this.thisClassInfo = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConstInfo getItem(int i) {
        return this.items.elementAt(i);
    }

    public int getTag(int i) {
        return getItem(i).getTag();
    }

    public String getClassInfo(int i) {
        ClassInfo classInfo = (ClassInfo) getItem(i);
        if (classInfo == null) {
            return null;
        }
        return Descriptor.toJavaName(getUtf8Info(classInfo.name));
    }

    public String getClassInfoByDescriptor(int i) {
        ClassInfo classInfo = (ClassInfo) getItem(i);
        if (classInfo == null) {
            return null;
        }
        String utf8Info = getUtf8Info(classInfo.name);
        return utf8Info.charAt(0) == '[' ? utf8Info : Descriptor.of(utf8Info);
    }

    public int getNameAndTypeName(int i) {
        return ((NameAndTypeInfo) getItem(i)).memberName;
    }

    public int getNameAndTypeDescriptor(int i) {
        return ((NameAndTypeInfo) getItem(i)).typeDescriptor;
    }

    public int getMemberClass(int i) {
        return ((MemberrefInfo) getItem(i)).classIndex;
    }

    public int getMemberNameAndType(int i) {
        return ((MemberrefInfo) getItem(i)).nameAndTypeIndex;
    }

    public int getFieldrefClass(int i) {
        return ((FieldrefInfo) getItem(i)).classIndex;
    }

    public String getFieldrefClassName(int i) {
        FieldrefInfo fieldrefInfo = (FieldrefInfo) getItem(i);
        if (fieldrefInfo == null) {
            return null;
        }
        return getClassInfo(fieldrefInfo.classIndex);
    }

    public int getFieldrefNameAndType(int i) {
        return ((FieldrefInfo) getItem(i)).nameAndTypeIndex;
    }

    public String getFieldrefName(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        FieldrefInfo fieldrefInfo = (FieldrefInfo) getItem(i);
        if (fieldrefInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(fieldrefInfo.nameAndTypeIndex)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.memberName);
    }

    public String getFieldrefType(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        FieldrefInfo fieldrefInfo = (FieldrefInfo) getItem(i);
        if (fieldrefInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(fieldrefInfo.nameAndTypeIndex)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }

    public int getMethodrefClass(int i) {
        return ((MemberrefInfo) getItem(i)).classIndex;
    }

    public String getMethodrefClassName(int i) {
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        if (memberrefInfo == null) {
            return null;
        }
        return getClassInfo(memberrefInfo.classIndex);
    }

    public int getMethodrefNameAndType(int i) {
        return ((MemberrefInfo) getItem(i)).nameAndTypeIndex;
    }

    public String getMethodrefName(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        if (memberrefInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(memberrefInfo.nameAndTypeIndex)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.memberName);
    }

    public String getMethodrefType(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        if (memberrefInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(memberrefInfo.nameAndTypeIndex)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }

    public int getInterfaceMethodrefClass(int i) {
        return ((MemberrefInfo) getItem(i)).classIndex;
    }

    public String getInterfaceMethodrefClassName(int i) {
        return getClassInfo(((MemberrefInfo) getItem(i)).classIndex);
    }

    public int getInterfaceMethodrefNameAndType(int i) {
        return ((MemberrefInfo) getItem(i)).nameAndTypeIndex;
    }

    public String getInterfaceMethodrefName(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        if (memberrefInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(memberrefInfo.nameAndTypeIndex)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.memberName);
    }

    public String getInterfaceMethodrefType(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        if (memberrefInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(memberrefInfo.nameAndTypeIndex)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }

    public Object getLdcValue(int i) {
        ConstInfo item = getItem(i);
        if (item instanceof StringInfo) {
            return getStringInfo(i);
        }
        if (item instanceof FloatInfo) {
            return Float.valueOf(getFloatInfo(i));
        }
        if (item instanceof IntegerInfo) {
            return Integer.valueOf(getIntegerInfo(i));
        }
        if (item instanceof LongInfo) {
            return Long.valueOf(getLongInfo(i));
        }
        if (item instanceof DoubleInfo) {
            return Double.valueOf(getDoubleInfo(i));
        }
        return null;
    }

    public int getIntegerInfo(int i) {
        return ((IntegerInfo) getItem(i)).value;
    }

    public float getFloatInfo(int i) {
        return ((FloatInfo) getItem(i)).value;
    }

    public long getLongInfo(int i) {
        return ((LongInfo) getItem(i)).value;
    }

    public double getDoubleInfo(int i) {
        return ((DoubleInfo) getItem(i)).value;
    }

    public String getStringInfo(int i) {
        return getUtf8Info(((StringInfo) getItem(i)).string);
    }

    public String getUtf8Info(int i) {
        return ((Utf8Info) getItem(i)).string;
    }

    public int getMethodHandleKind(int i) {
        return ((MethodHandleInfo) getItem(i)).refKind;
    }

    public int getMethodHandleIndex(int i) {
        return ((MethodHandleInfo) getItem(i)).refIndex;
    }

    public int getMethodTypeInfo(int i) {
        return ((MethodTypeInfo) getItem(i)).descriptor;
    }

    public int getInvokeDynamicBootstrap(int i) {
        return ((InvokeDynamicInfo) getItem(i)).bootstrap;
    }

    public int getInvokeDynamicNameAndType(int i) {
        return ((InvokeDynamicInfo) getItem(i)).nameAndType;
    }

    public String getInvokeDynamicType(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        InvokeDynamicInfo invokeDynamicInfo = (InvokeDynamicInfo) getItem(i);
        if (invokeDynamicInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(invokeDynamicInfo.nameAndType)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }

    public int getDynamicBootstrap(int i) {
        return ((DynamicInfo) getItem(i)).bootstrap;
    }

    public int getDynamicNameAndType(int i) {
        return ((DynamicInfo) getItem(i)).nameAndType;
    }

    public String getDynamicType(int i) {
        NameAndTypeInfo nameAndTypeInfo;
        DynamicInfo dynamicInfo = (DynamicInfo) getItem(i);
        if (dynamicInfo == null || (nameAndTypeInfo = (NameAndTypeInfo) getItem(dynamicInfo.nameAndType)) == null) {
            return null;
        }
        return getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }

    public String getModuleInfo(int i) {
        return getUtf8Info(((ModuleInfo) getItem(i)).name);
    }

    public String getPackageInfo(int i) {
        return getUtf8Info(((PackageInfo) getItem(i)).name);
    }

    public int isConstructor(String str, int i) {
        return isMember(str, "<init>", i);
    }

    public int isMember(String str, String str2, int i) {
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        if (getClassInfo(memberrefInfo.classIndex).equals(str)) {
            NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo) getItem(memberrefInfo.nameAndTypeIndex);
            if (getUtf8Info(nameAndTypeInfo.memberName).equals(str2)) {
                return nameAndTypeInfo.typeDescriptor;
            }
            return 0;
        }
        return 0;
    }

    public String eqMember(String str, String str2, int i) {
        MemberrefInfo memberrefInfo = (MemberrefInfo) getItem(i);
        NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo) getItem(memberrefInfo.nameAndTypeIndex);
        if (getUtf8Info(nameAndTypeInfo.memberName).equals(str) && getUtf8Info(nameAndTypeInfo.typeDescriptor).equals(str2)) {
            return getClassInfo(memberrefInfo.classIndex);
        }
        return null;
    }

    private int addItem0(ConstInfo constInfo) {
        this.items.addElement(constInfo);
        int i = this.numOfItems;
        this.numOfItems = i + 1;
        return i;
    }

    private int addItem(ConstInfo constInfo) {
        if (this.itemsCache == null) {
            this.itemsCache = makeItemsCache(this.items);
        }
        ConstInfo constInfo2 = this.itemsCache.get(constInfo);
        if (constInfo2 != null) {
            return constInfo2.index;
        }
        this.items.addElement(constInfo);
        this.itemsCache.put(constInfo, constInfo);
        int i = this.numOfItems;
        this.numOfItems = i + 1;
        return i;
    }

    public int copy(int i, ConstPool constPool, Map<String, String> map) {
        if (i == 0) {
            return 0;
        }
        return getItem(i).copy(this, constPool, map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int addConstInfoPadding() {
        return addItem0(new ConstInfoPadding(this.numOfItems));
    }

    public int addClassInfo(CtClass ctClass) {
        if (ctClass == THIS) {
            return this.thisClassInfo;
        }
        if (!ctClass.isArray()) {
            return addClassInfo(ctClass.getName());
        }
        return addClassInfo(Descriptor.toJvmName(ctClass));
    }

    public int addClassInfo(String str) {
        return addItem(new ClassInfo(addUtf8Info(Descriptor.toJvmName(str)), this.numOfItems));
    }

    public int addNameAndTypeInfo(String str, String str2) {
        return addNameAndTypeInfo(addUtf8Info(str), addUtf8Info(str2));
    }

    public int addNameAndTypeInfo(int i, int i2) {
        return addItem(new NameAndTypeInfo(i, i2, this.numOfItems));
    }

    public int addFieldrefInfo(int i, String str, String str2) {
        return addFieldrefInfo(i, addNameAndTypeInfo(str, str2));
    }

    public int addFieldrefInfo(int i, int i2) {
        return addItem(new FieldrefInfo(i, i2, this.numOfItems));
    }

    public int addMethodrefInfo(int i, String str, String str2) {
        return addMethodrefInfo(i, addNameAndTypeInfo(str, str2));
    }

    public int addMethodrefInfo(int i, int i2) {
        return addItem(new MethodrefInfo(i, i2, this.numOfItems));
    }

    public int addInterfaceMethodrefInfo(int i, String str, String str2) {
        return addInterfaceMethodrefInfo(i, addNameAndTypeInfo(str, str2));
    }

    public int addInterfaceMethodrefInfo(int i, int i2) {
        return addItem(new InterfaceMethodrefInfo(i, i2, this.numOfItems));
    }

    public int addStringInfo(String str) {
        return addItem(new StringInfo(addUtf8Info(str), this.numOfItems));
    }

    public int addIntegerInfo(int i) {
        return addItem(new IntegerInfo(i, this.numOfItems));
    }

    public int addFloatInfo(float f) {
        return addItem(new FloatInfo(f, this.numOfItems));
    }

    public int addLongInfo(long j) {
        int addItem = addItem(new LongInfo(j, this.numOfItems));
        if (addItem == this.numOfItems - 1) {
            addConstInfoPadding();
        }
        return addItem;
    }

    public int addDoubleInfo(double d) {
        int addItem = addItem(new DoubleInfo(d, this.numOfItems));
        if (addItem == this.numOfItems - 1) {
            addConstInfoPadding();
        }
        return addItem;
    }

    public int addUtf8Info(String str) {
        return addItem(new Utf8Info(str, this.numOfItems));
    }

    public int addMethodHandleInfo(int i, int i2) {
        return addItem(new MethodHandleInfo(i, i2, this.numOfItems));
    }

    public int addMethodTypeInfo(int i) {
        return addItem(new MethodTypeInfo(i, this.numOfItems));
    }

    public int addInvokeDynamicInfo(int i, int i2) {
        return addItem(new InvokeDynamicInfo(i, i2, this.numOfItems));
    }

    public int addDynamicInfo(int i, int i2) {
        return addItem(new DynamicInfo(i, i2, this.numOfItems));
    }

    public int addModuleInfo(int i) {
        return addItem(new ModuleInfo(i, this.numOfItems));
    }

    public int addPackageInfo(int i) {
        return addItem(new PackageInfo(i, this.numOfItems));
    }

    public Set<String> getClassNames() {
        HashSet hashSet = new HashSet();
        LongVector longVector = this.items;
        int i = this.numOfItems;
        for (int i2 = 1; i2 < i; i2++) {
            String className = longVector.elementAt(i2).getClassName(this);
            if (className != null) {
                hashSet.add(className);
            }
        }
        return hashSet;
    }

    public void renameClass(String str, String str2) {
        LongVector longVector = this.items;
        int i = this.numOfItems;
        for (int i2 = 1; i2 < i; i2++) {
            longVector.elementAt(i2).renameClass(this, str, str2, this.itemsCache);
        }
    }

    public void renameClass(Map<String, String> map) {
        LongVector longVector = this.items;
        int i = this.numOfItems;
        for (int i2 = 1; i2 < i; i2++) {
            longVector.elementAt(i2).renameClass(this, map, this.itemsCache);
        }
    }

    private void read(DataInputStream dataInputStream) throws IOException {
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        this.items = new LongVector(readUnsignedShort);
        this.numOfItems = 0;
        addItem0(null);
        while (true) {
            readUnsignedShort--;
            if (readUnsignedShort <= 0) {
                return;
            }
            int readOne = readOne(dataInputStream);
            if (readOne == 5 || readOne == 6) {
                addConstInfoPadding();
                readUnsignedShort--;
            }
        }
    }

    private static Map<ConstInfo, ConstInfo> makeItemsCache(LongVector longVector) {
        HashMap hashMap = new HashMap();
        int i = 1;
        while (true) {
            int i2 = i + 1;
            ConstInfo elementAt = longVector.elementAt(i);
            if (elementAt == null) {
                return hashMap;
            }
            hashMap.put(elementAt, elementAt);
            i = i2;
        }
    }

    private int readOne(DataInputStream dataInputStream) throws IOException {
        ConstInfo utf8Info;
        int readUnsignedByte = dataInputStream.readUnsignedByte();
        switch (readUnsignedByte) {
            case 1:
                utf8Info = new Utf8Info(dataInputStream, this.numOfItems);
                break;
            case 2:
            case 13:
            case 14:
            default:
                throw new IOException("invalid constant type: " + readUnsignedByte + " at " + this.numOfItems);
            case 3:
                utf8Info = new IntegerInfo(dataInputStream, this.numOfItems);
                break;
            case 4:
                utf8Info = new FloatInfo(dataInputStream, this.numOfItems);
                break;
            case 5:
                utf8Info = new LongInfo(dataInputStream, this.numOfItems);
                break;
            case 6:
                utf8Info = new DoubleInfo(dataInputStream, this.numOfItems);
                break;
            case 7:
                utf8Info = new ClassInfo(dataInputStream, this.numOfItems);
                break;
            case 8:
                utf8Info = new StringInfo(dataInputStream, this.numOfItems);
                break;
            case 9:
                utf8Info = new FieldrefInfo(dataInputStream, this.numOfItems);
                break;
            case 10:
                utf8Info = new MethodrefInfo(dataInputStream, this.numOfItems);
                break;
            case 11:
                utf8Info = new InterfaceMethodrefInfo(dataInputStream, this.numOfItems);
                break;
            case 12:
                utf8Info = new NameAndTypeInfo(dataInputStream, this.numOfItems);
                break;
            case 15:
                utf8Info = new MethodHandleInfo(dataInputStream, this.numOfItems);
                break;
            case 16:
                utf8Info = new MethodTypeInfo(dataInputStream, this.numOfItems);
                break;
            case 17:
                utf8Info = new DynamicInfo(dataInputStream, this.numOfItems);
                break;
            case 18:
                utf8Info = new InvokeDynamicInfo(dataInputStream, this.numOfItems);
                break;
            case 19:
                utf8Info = new ModuleInfo(dataInputStream, this.numOfItems);
                break;
            case 20:
                utf8Info = new PackageInfo(dataInputStream, this.numOfItems);
                break;
        }
        addItem0(utf8Info);
        return readUnsignedByte;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.numOfItems);
        LongVector longVector = this.items;
        int i = this.numOfItems;
        for (int i2 = 1; i2 < i; i2++) {
            longVector.elementAt(i2).write(dataOutputStream);
        }
    }

    public void print() {
        print(new PrintWriter((OutputStream) System.out, true));
    }

    public void print(PrintWriter printWriter) {
        int i = this.numOfItems;
        for (int i2 = 1; i2 < i; i2++) {
            printWriter.print(i2);
            printWriter.print(" ");
            this.items.elementAt(i2).print(printWriter);
        }
    }
}
