package javassist;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Collection;
import javassist.CtField;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.compiler.AccessorMaker;
import javassist.expr.ExprEditor;

/* loaded from: classes2.dex */
public abstract class CtClass {
    public static CtClass booleanType = null;
    public static CtClass byteType = null;
    public static CtClass charType = null;
    public static String debugDump = null;
    public static CtClass doubleType = null;
    public static CtClass floatType = null;
    public static CtClass intType = null;
    static final String javaLangObject = "java.lang.Object";
    public static CtClass longType = null;
    static CtClass[] primitiveTypes = new CtClass[9];
    public static CtClass shortType = null;
    public static final String version = "3.26.0-GA";
    public static CtClass voidType;
    protected String qualifiedName;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void compress() {
    }

    public void freeze() {
    }

    public AccessorMaker getAccessorMaker() {
        return null;
    }

    public Object getAnnotation(Class<?> cls) throws ClassNotFoundException {
        return null;
    }

    public Object[] getAnnotations() throws ClassNotFoundException {
        return new Object[0];
    }

    public byte[] getAttribute(String str) {
        return null;
    }

    public Object[] getAvailableAnnotations() {
        return new Object[0];
    }

    public ClassFile getClassFile2() {
        return null;
    }

    public CtConstructor getClassInitializer() {
        return null;
    }

    public ClassPool getClassPool() {
        return null;
    }

    public CtClass getComponentType() throws NotFoundException {
        return null;
    }

    public CtConstructor[] getConstructors() {
        return new CtConstructor[0];
    }

    public CtBehavior[] getDeclaredBehaviors() {
        return new CtBehavior[0];
    }

    public CtConstructor[] getDeclaredConstructors() {
        return new CtConstructor[0];
    }

    public CtField[] getDeclaredFields() {
        return new CtField[0];
    }

    public CtMethod[] getDeclaredMethods() {
        return new CtMethod[0];
    }

    public CtClass getDeclaringClass() throws NotFoundException {
        return null;
    }

    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtField getField2(String str, String str2) {
        return null;
    }

    public CtField[] getFields() {
        return new CtField[0];
    }

    public String getGenericSignature() {
        return null;
    }

    public CtClass[] getInterfaces() throws NotFoundException {
        return new CtClass[0];
    }

    public CtMethod[] getMethods() {
        return new CtMethod[0];
    }

    public int getModifiers() {
        return 0;
    }

    public CtClass[] getNestedClasses() throws NotFoundException {
        return new CtClass[0];
    }

    public CtClass getSuperclass() throws NotFoundException {
        return null;
    }

    public boolean hasAnnotation(String str) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void incGetCounter() {
    }

    public boolean isAnnotation() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isEnum() {
        return false;
    }

    public boolean isFrozen() {
        return true;
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isModified() {
        return false;
    }

    public boolean isPrimitive() {
        return false;
    }

    public void prune() {
    }

    public void rebuildClassFile() {
    }

    public boolean stopPruning(boolean z) {
        return true;
    }

    public boolean subclassOf(CtClass ctClass) {
        return false;
    }

    public static void main(String[] strArr) {
        System.out.println("Javassist version 3.26.0-GA");
        System.out.println("Copyright (C) 1999-2019 Shigeru Chiba. All Rights Reserved.");
    }

    static {
        CtPrimitiveType ctPrimitiveType = new CtPrimitiveType(TypedValues.Custom.S_BOOLEAN, 'Z', "java.lang.Boolean", "booleanValue", "()Z", 172, 4, 1);
        booleanType = ctPrimitiveType;
        primitiveTypes[0] = ctPrimitiveType;
        CtPrimitiveType ctPrimitiveType2 = new CtPrimitiveType("char", 'C', "java.lang.Character", "charValue", "()C", 172, 5, 1);
        charType = ctPrimitiveType2;
        primitiveTypes[1] = ctPrimitiveType2;
        CtPrimitiveType ctPrimitiveType3 = new CtPrimitiveType("byte", 'B', "java.lang.Byte", "byteValue", "()B", 172, 8, 1);
        byteType = ctPrimitiveType3;
        primitiveTypes[2] = ctPrimitiveType3;
        CtPrimitiveType ctPrimitiveType4 = new CtPrimitiveType("short", 'S', "java.lang.Short", "shortValue", "()S", 172, 9, 1);
        shortType = ctPrimitiveType4;
        primitiveTypes[3] = ctPrimitiveType4;
        CtPrimitiveType ctPrimitiveType5 = new CtPrimitiveType("int", 'I', "java.lang.Integer", "intValue", "()I", 172, 10, 1);
        intType = ctPrimitiveType5;
        primitiveTypes[4] = ctPrimitiveType5;
        CtPrimitiveType ctPrimitiveType6 = new CtPrimitiveType("long", 'J', "java.lang.Long", "longValue", "()J", 173, 11, 2);
        longType = ctPrimitiveType6;
        primitiveTypes[5] = ctPrimitiveType6;
        CtPrimitiveType ctPrimitiveType7 = new CtPrimitiveType(TypedValues.Custom.S_FLOAT, 'F', "java.lang.Float", "floatValue", "()F", 174, 6, 1);
        floatType = ctPrimitiveType7;
        primitiveTypes[6] = ctPrimitiveType7;
        CtPrimitiveType ctPrimitiveType8 = new CtPrimitiveType("double", 'D', "java.lang.Double", "doubleValue", "()D", 175, 7, 2);
        doubleType = ctPrimitiveType8;
        primitiveTypes[7] = ctPrimitiveType8;
        CtPrimitiveType ctPrimitiveType9 = new CtPrimitiveType("void", 'V', "java.lang.Void", null, null, 177, 0, 0);
        voidType = ctPrimitiveType9;
        primitiveTypes[8] = ctPrimitiveType9;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CtClass(String str) {
        this.qualifiedName = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(getClass().getName());
        stringBuffer.append("@");
        stringBuffer.append(Integer.toHexString(hashCode()));
        stringBuffer.append("[");
        extendToString(stringBuffer);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    protected void extendToString(StringBuffer stringBuffer) {
        stringBuffer.append(getName());
    }

    public ClassFile getClassFile() {
        checkModify();
        return getClassFile2();
    }

    public URL getURL() throws NotFoundException {
        throw new NotFoundException(getName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void checkModify() throws RuntimeException {
        if (isFrozen()) {
            throw new RuntimeException(getName() + " class is frozen");
        }
    }

    public void defrost() {
        throw new RuntimeException("cannot defrost " + getName());
    }

    public boolean isKotlin() {
        return hasAnnotation("kotlin.Metadata");
    }

    public boolean subtypeOf(CtClass ctClass) throws NotFoundException {
        return this == ctClass || getName().equals(ctClass.getName());
    }

    public String getName() {
        return this.qualifiedName;
    }

    public final String getSimpleName() {
        String str = this.qualifiedName;
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf < 0 ? str : str.substring(lastIndexOf + 1);
    }

    public final String getPackageName() {
        String str = this.qualifiedName;
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf < 0) {
            return null;
        }
        return str.substring(0, lastIndexOf);
    }

    public void setName(String str) {
        checkModify();
        if (str != null) {
            this.qualifiedName = str;
        }
    }

    public void setGenericSignature(String str) {
        checkModify();
    }

    public void replaceClassName(String str, String str2) {
        checkModify();
    }

    public void replaceClassName(ClassMap classMap) {
        checkModify();
    }

    public synchronized Collection<String> getRefClasses() {
        ClassFile classFile2 = getClassFile2();
        if (classFile2 != null) {
            ClassMap classMap = new ClassMap() { // from class: javassist.CtClass.1
                private static final long serialVersionUID = 1;

                @Override // javassist.ClassMap
                public void fix(String str) {
                }

                @Override // javassist.ClassMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
                public String put(String str, String str2) {
                    return put0(str, str2);
                }

                @Override // javassist.ClassMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
                public String get(Object obj) {
                    String javaName = toJavaName((String) obj);
                    put0(javaName, javaName);
                    return null;
                }
            };
            classFile2.getRefClasses(classMap);
            return classMap.values();
        }
        return null;
    }

    public boolean hasAnnotation(Class<?> cls) {
        return hasAnnotation(cls.getName());
    }

    public CtClass[] getDeclaredClasses() throws NotFoundException {
        return getNestedClasses();
    }

    public void setModifiers(int i) {
        checkModify();
    }

    public void setSuperclass(CtClass ctClass) throws CannotCompileException {
        checkModify();
    }

    public void setInterfaces(CtClass[] ctClassArr) {
        checkModify();
    }

    public void addInterface(CtClass ctClass) {
        checkModify();
    }

    @Deprecated
    public final CtMethod getEnclosingMethod() throws NotFoundException {
        CtBehavior enclosingBehavior = getEnclosingBehavior();
        if (enclosingBehavior == null) {
            return null;
        }
        if (enclosingBehavior instanceof CtMethod) {
            return (CtMethod) enclosingBehavior;
        }
        throw new NotFoundException(enclosingBehavior.getLongName() + " is enclosing " + getName());
    }

    public CtClass makeNestedClass(String str, boolean z) {
        throw new RuntimeException(getName() + " is not a class");
    }

    public CtField getField(String str) throws NotFoundException {
        return getField(str, null);
    }

    public CtField getField(String str, String str2) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtField getDeclaredField(String str) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtField getDeclaredField(String str, String str2) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtConstructor getConstructor(String str) throws NotFoundException {
        throw new NotFoundException("no such constructor");
    }

    public CtConstructor getDeclaredConstructor(CtClass[] ctClassArr) throws NotFoundException {
        return getConstructor(Descriptor.ofConstructor(ctClassArr));
    }

    public CtMethod getMethod(String str, String str2) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtMethod getDeclaredMethod(String str, CtClass[] ctClassArr) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtMethod[] getDeclaredMethods(String str) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtMethod getDeclaredMethod(String str) throws NotFoundException {
        throw new NotFoundException(str);
    }

    public CtConstructor makeClassInitializer() throws CannotCompileException {
        throw new CannotCompileException("not a class");
    }

    public void addConstructor(CtConstructor ctConstructor) throws CannotCompileException {
        checkModify();
    }

    public void removeConstructor(CtConstructor ctConstructor) throws NotFoundException {
        checkModify();
    }

    public void addMethod(CtMethod ctMethod) throws CannotCompileException {
        checkModify();
    }

    public void removeMethod(CtMethod ctMethod) throws NotFoundException {
        checkModify();
    }

    public void addField(CtField ctField) throws CannotCompileException {
        addField(ctField, (CtField.Initializer) null);
    }

    public void addField(CtField ctField, String str) throws CannotCompileException {
        checkModify();
    }

    public void addField(CtField ctField, CtField.Initializer initializer) throws CannotCompileException {
        checkModify();
    }

    public void removeField(CtField ctField) throws NotFoundException {
        checkModify();
    }

    public void setAttribute(String str, byte[] bArr) {
        checkModify();
    }

    public void instrument(CodeConverter codeConverter) throws CannotCompileException {
        checkModify();
    }

    public void instrument(ExprEditor exprEditor) throws CannotCompileException {
        checkModify();
    }

    public Class<?> toClass() throws CannotCompileException {
        return getClassPool().toClass(this);
    }

    public Class<?> toClass(Class<?> cls) throws CannotCompileException {
        return getClassPool().toClass(this, cls);
    }

    public Class<?> toClass(MethodHandles.Lookup lookup) throws CannotCompileException {
        return getClassPool().toClass(this, lookup);
    }

    public Class<?> toClass(ClassLoader classLoader, ProtectionDomain protectionDomain) throws CannotCompileException {
        ClassPool classPool = getClassPool();
        if (classLoader == null) {
            classLoader = classPool.getClassLoader();
        }
        return classPool.toClass(this, null, classLoader, protectionDomain);
    }

    @Deprecated
    public final Class<?> toClass(ClassLoader classLoader) throws CannotCompileException {
        return getClassPool().toClass(this, null, classLoader, null);
    }

    public void detach() {
        ClassPool classPool = getClassPool();
        CtClass removeCached = classPool.removeCached(getName());
        if (removeCached != this) {
            classPool.cacheCtClass(getName(), removeCached, false);
        }
    }

    public byte[] toBytecode() throws IOException, CannotCompileException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            toBytecode(dataOutputStream);
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            dataOutputStream.close();
            throw th;
        }
    }

    public void writeFile() throws NotFoundException, IOException, CannotCompileException {
        writeFile(".");
    }

    public void writeFile(String str) throws CannotCompileException, IOException {
        DataOutputStream makeFileOutput = makeFileOutput(str);
        try {
            toBytecode(makeFileOutput);
        } finally {
            makeFileOutput.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DataOutputStream makeFileOutput(String str) {
        String str2 = str + File.separatorChar + getName().replace('.', File.separatorChar) + ".class";
        int lastIndexOf = str2.lastIndexOf(File.separatorChar);
        if (lastIndexOf > 0) {
            String substring = str2.substring(0, lastIndexOf);
            if (!substring.equals(".")) {
                new File(substring).mkdirs();
            }
        }
        return new DataOutputStream(new BufferedOutputStream(new DelayedFileOutputStream(str2)));
    }

    public void debugWriteFile() {
        debugWriteFile(".");
    }

    public void debugWriteFile(String str) {
        try {
            boolean stopPruning = stopPruning(true);
            writeFile(str);
            defrost();
            stopPruning(stopPruning);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class DelayedFileOutputStream extends OutputStream {
        private FileOutputStream file = null;
        private String filename;

        DelayedFileOutputStream(String str) {
            this.filename = str;
        }

        private void init() throws IOException {
            if (this.file == null) {
                this.file = new FileOutputStream(this.filename);
            }
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            init();
            this.file.write(i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            init();
            this.file.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            init();
            this.file.write(bArr, i, i2);
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            init();
            this.file.flush();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            init();
            this.file.close();
        }
    }

    public void toBytecode(DataOutputStream dataOutputStream) throws CannotCompileException, IOException {
        throw new CannotCompileException("not a class");
    }

    public String makeUniqueName(String str) {
        throw new RuntimeException("not available in " + getName());
    }
}
