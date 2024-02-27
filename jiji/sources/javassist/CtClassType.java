package javassist;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javassist.CtField;
import javassist.CtMember;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ConstantAttribute;
import javassist.bytecode.Descriptor;
import javassist.bytecode.EnclosingMethodAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.InnerClassesAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationImpl;
import javassist.compiler.AccessorMaker;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;
import javassist.expr.ExprEditor;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class CtClassType extends CtClass {
    private static final int GET_THRESHOLD = 2;
    private AccessorMaker accessors;
    ClassPool classPool;
    ClassFile classfile;
    private boolean doPruning;
    private FieldInitLink fieldInitializers;
    boolean gcConstPool;
    private int getCount;
    private Map<CtMethod, String> hiddenMethods;
    private Reference<CtMember.Cache> memberCache;
    byte[] rawClassfile;
    private int uniqueNumberSeed;
    boolean wasChanged;
    private boolean wasFrozen;
    boolean wasPruned;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtClassType(String str, ClassPool classPool) {
        super(str);
        this.doPruning = ClassPool.doPruning;
        this.classPool = classPool;
        this.gcConstPool = false;
        this.wasPruned = false;
        this.wasFrozen = false;
        this.wasChanged = false;
        this.classfile = null;
        this.rawClassfile = null;
        this.memberCache = null;
        this.accessors = null;
        this.fieldInitializers = null;
        this.hiddenMethods = null;
        this.uniqueNumberSeed = 0;
        this.getCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtClassType(InputStream inputStream, ClassPool classPool) throws IOException {
        this((String) null, classPool);
        ClassFile classFile = new ClassFile(new DataInputStream(inputStream));
        this.classfile = classFile;
        this.qualifiedName = classFile.getName();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtClassType(ClassFile classFile, ClassPool classPool) {
        this((String) null, classPool);
        this.classfile = classFile;
        this.qualifiedName = classFile.getName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // javassist.CtClass
    public void extendToString(StringBuffer stringBuffer) {
        if (this.wasChanged) {
            stringBuffer.append("changed ");
        }
        if (this.wasFrozen) {
            stringBuffer.append("frozen ");
        }
        if (this.wasPruned) {
            stringBuffer.append("pruned ");
        }
        stringBuffer.append(Modifier.toString(getModifiers()));
        stringBuffer.append(" class ");
        stringBuffer.append(getName());
        try {
            CtClass superclass = getSuperclass();
            if (superclass != null && !superclass.getName().equals("java.lang.Object")) {
                stringBuffer.append(" extends " + superclass.getName());
            }
        } catch (NotFoundException unused) {
            stringBuffer.append(" extends ??");
        }
        try {
            CtClass[] interfaces = getInterfaces();
            if (interfaces.length > 0) {
                stringBuffer.append(" implements ");
            }
            for (CtClass ctClass : interfaces) {
                stringBuffer.append(ctClass.getName());
                stringBuffer.append(", ");
            }
        } catch (NotFoundException unused2) {
            stringBuffer.append(" extends ??");
        }
        CtMember.Cache members = getMembers();
        exToString(stringBuffer, " fields=", members.fieldHead(), members.lastField());
        exToString(stringBuffer, " constructors=", members.consHead(), members.lastCons());
        exToString(stringBuffer, " methods=", members.methodHead(), members.lastMethod());
    }

    private void exToString(StringBuffer stringBuffer, String str, CtMember ctMember, CtMember ctMember2) {
        stringBuffer.append(str);
        while (ctMember != ctMember2) {
            ctMember = ctMember.next();
            stringBuffer.append(ctMember);
            stringBuffer.append(", ");
        }
    }

    @Override // javassist.CtClass
    public AccessorMaker getAccessorMaker() {
        if (this.accessors == null) {
            this.accessors = new AccessorMaker(this);
        }
        return this.accessors;
    }

    @Override // javassist.CtClass
    public ClassFile getClassFile2() {
        return getClassFile3(true);
    }

    public ClassFile getClassFile3(boolean z) {
        ClassFile classFile = this.classfile;
        if (classFile != null) {
            return classFile;
        }
        if (z) {
            this.classPool.compress();
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            if (this.rawClassfile != null) {
                try {
                    ClassFile classFile2 = new ClassFile(new DataInputStream(new ByteArrayInputStream(this.rawClassfile)));
                    this.rawClassfile = null;
                    this.getCount = 2;
                    return setClassFile(classFile2);
                } catch (IOException e) {
                    throw new RuntimeException(e.toString(), e);
                }
            }
            try {
                InputStream openClassfile = this.classPool.openClassfile(getName());
                if (openClassfile == null) {
                    throw new NotFoundException(getName());
                }
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(openClassfile);
                try {
                    ClassFile classFile3 = new ClassFile(new DataInputStream(bufferedInputStream2));
                    if (!classFile3.getName().equals(this.qualifiedName)) {
                        throw new RuntimeException("cannot find " + this.qualifiedName + ": " + classFile3.getName() + " found in " + this.qualifiedName.replace('.', '/') + ".class");
                    }
                    ClassFile classFile4 = setClassFile(classFile3);
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException unused) {
                    }
                    return classFile4;
                } catch (IOException e2) {
                    e = e2;
                    throw new RuntimeException(e.toString(), e);
                } catch (NotFoundException e3) {
                    e = e3;
                    throw new RuntimeException(e.toString(), e);
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
            } catch (NotFoundException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.CtClass
    public final void incGetCounter() {
        this.getCount++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.CtClass
    public void compress() {
        if (this.getCount < 2) {
            if (!isModified() && ClassPool.releaseUnmodifiedClassFile) {
                removeClassFile();
            } else if (isFrozen() && !this.wasPruned) {
                saveClassFile();
            }
        }
        this.getCount = 0;
    }

    private synchronized void saveClassFile() {
        if (this.classfile != null && hasMemberCache() == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                this.classfile.write(new DataOutputStream(byteArrayOutputStream));
                byteArrayOutputStream.close();
                this.rawClassfile = byteArrayOutputStream.toByteArray();
                this.classfile = null;
            } catch (IOException unused) {
            }
        }
    }

    private synchronized void removeClassFile() {
        if (this.classfile != null && !isModified() && hasMemberCache() == null) {
            this.classfile = null;
        }
    }

    private synchronized ClassFile setClassFile(ClassFile classFile) {
        if (this.classfile == null) {
            this.classfile = classFile;
        }
        return this.classfile;
    }

    @Override // javassist.CtClass
    public ClassPool getClassPool() {
        return this.classPool;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setClassPool(ClassPool classPool) {
        this.classPool = classPool;
    }

    @Override // javassist.CtClass
    public URL getURL() throws NotFoundException {
        URL find = this.classPool.find(getName());
        if (find != null) {
            return find;
        }
        throw new NotFoundException(getName());
    }

    @Override // javassist.CtClass
    public boolean isModified() {
        return this.wasChanged;
    }

    @Override // javassist.CtClass
    public boolean isFrozen() {
        return this.wasFrozen;
    }

    @Override // javassist.CtClass
    public void freeze() {
        this.wasFrozen = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.CtClass
    public void checkModify() throws RuntimeException {
        if (isFrozen()) {
            String str = getName() + " class is frozen";
            if (this.wasPruned) {
                str = str + " and pruned";
            }
            throw new RuntimeException(str);
        }
        this.wasChanged = true;
    }

    @Override // javassist.CtClass
    public void defrost() {
        checkPruned("defrost");
        this.wasFrozen = false;
    }

    @Override // javassist.CtClass
    public boolean subtypeOf(CtClass ctClass) throws NotFoundException {
        String name = ctClass.getName();
        if (this == ctClass || getName().equals(name)) {
            return true;
        }
        ClassFile classFile2 = getClassFile2();
        String superclass = classFile2.getSuperclass();
        if (superclass == null || !superclass.equals(name)) {
            String[] interfaces = classFile2.getInterfaces();
            for (String str : interfaces) {
                if (str.equals(name)) {
                    return true;
                }
            }
            if (superclass == null || !this.classPool.get(superclass).subtypeOf(ctClass)) {
                for (String str2 : interfaces) {
                    if (this.classPool.get(str2).subtypeOf(ctClass)) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    @Override // javassist.CtClass
    public void setName(String str) throws RuntimeException {
        String name = getName();
        if (str.equals(name)) {
            return;
        }
        this.classPool.checkNotFrozen(str);
        ClassFile classFile2 = getClassFile2();
        super.setName(str);
        classFile2.setName(str);
        nameReplaced();
        this.classPool.classNameChanged(name, this);
    }

    @Override // javassist.CtClass
    public String getGenericSignature() {
        SignatureAttribute signatureAttribute = (SignatureAttribute) getClassFile2().getAttribute(SignatureAttribute.tag);
        if (signatureAttribute == null) {
            return null;
        }
        return signatureAttribute.getSignature();
    }

    @Override // javassist.CtClass
    public void setGenericSignature(String str) {
        ClassFile classFile = getClassFile();
        classFile.addAttribute(new SignatureAttribute(classFile.getConstPool(), str));
    }

    @Override // javassist.CtClass
    public void replaceClassName(ClassMap classMap) throws RuntimeException {
        String name = getName();
        String str = classMap.get((Object) Descriptor.toJvmName(name));
        if (str != null) {
            str = Descriptor.toJavaName(str);
            this.classPool.checkNotFrozen(str);
        }
        super.replaceClassName(classMap);
        getClassFile2().renameClass(classMap);
        nameReplaced();
        if (str != null) {
            super.setName(str);
            this.classPool.classNameChanged(name, this);
        }
    }

    @Override // javassist.CtClass
    public void replaceClassName(String str, String str2) throws RuntimeException {
        if (getName().equals(str)) {
            setName(str2);
            return;
        }
        super.replaceClassName(str, str2);
        getClassFile2().renameClass(str, str2);
        nameReplaced();
    }

    @Override // javassist.CtClass
    public boolean isInterface() {
        return Modifier.isInterface(getModifiers());
    }

    @Override // javassist.CtClass
    public boolean isAnnotation() {
        return Modifier.isAnnotation(getModifiers());
    }

    @Override // javassist.CtClass
    public boolean isEnum() {
        return Modifier.isEnum(getModifiers());
    }

    @Override // javassist.CtClass
    public int getModifiers() {
        ClassFile classFile2 = getClassFile2();
        int clear = AccessFlag.clear(classFile2.getAccessFlags(), 32);
        int innerAccessFlags = classFile2.getInnerAccessFlags();
        if (innerAccessFlags != -1) {
            if ((innerAccessFlags & 8) != 0) {
                clear |= 8;
            }
            if ((innerAccessFlags & 1) != 0) {
                clear |= 1;
            } else {
                clear &= -2;
                if ((innerAccessFlags & 4) != 0) {
                    clear |= 4;
                } else if ((innerAccessFlags & 2) != 0) {
                    clear |= 2;
                }
            }
        }
        return AccessFlag.toModifier(clear);
    }

    @Override // javassist.CtClass
    public CtClass[] getNestedClasses() throws NotFoundException {
        ClassFile classFile2;
        InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) getClassFile2().getAttribute(InnerClassesAttribute.tag);
        if (innerClassesAttribute == null) {
            return new CtClass[0];
        }
        String str = classFile2.getName() + "$";
        int tableLength = innerClassesAttribute.tableLength();
        ArrayList arrayList = new ArrayList(tableLength);
        for (int i = 0; i < tableLength; i++) {
            String innerClass = innerClassesAttribute.innerClass(i);
            if (innerClass != null && innerClass.startsWith(str) && innerClass.lastIndexOf(36) < str.length()) {
                arrayList.add(this.classPool.get(innerClass));
            }
        }
        return (CtClass[]) arrayList.toArray(new CtClass[arrayList.size()]);
    }

    @Override // javassist.CtClass
    public void setModifiers(int i) {
        checkModify();
        updateInnerEntry(i, getName(), this, true);
        getClassFile2().setAccessFlags(AccessFlag.of(i & (-9)));
    }

    private static void updateInnerEntry(int i, String str, CtClass ctClass, boolean z) {
        int accessFlags;
        InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) ctClass.getClassFile2().getAttribute(InnerClassesAttribute.tag);
        if (innerClassesAttribute != null) {
            int i2 = i & (-9);
            int find = innerClassesAttribute.find(str);
            if (find >= 0 && ((accessFlags = innerClassesAttribute.accessFlags(find) & 8) != 0 || !Modifier.isStatic(i))) {
                ctClass.checkModify();
                innerClassesAttribute.setAccessFlags(find, AccessFlag.of(i2) | accessFlags);
                String outerClass = innerClassesAttribute.outerClass(find);
                if (outerClass == null || !z) {
                    return;
                }
                try {
                    updateInnerEntry(i2, str, ctClass.getClassPool().get(outerClass), false);
                    return;
                } catch (NotFoundException unused) {
                    throw new RuntimeException("cannot find the declaring class: " + outerClass);
                }
            }
        }
        if (Modifier.isStatic(i)) {
            throw new RuntimeException("cannot change " + Descriptor.toJavaName(str) + " into a static class");
        }
    }

    @Override // javassist.CtClass
    public boolean hasAnnotation(String str) {
        ClassFile classFile2 = getClassFile2();
        return hasAnnotationType(str, getClassPool(), (AnnotationsAttribute) classFile2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) classFile2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    @Deprecated
    static boolean hasAnnotationType(Class<?> cls, ClassPool classPool, AnnotationsAttribute annotationsAttribute, AnnotationsAttribute annotationsAttribute2) {
        return hasAnnotationType(cls.getName(), classPool, annotationsAttribute, annotationsAttribute2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean hasAnnotationType(String str, ClassPool classPool, AnnotationsAttribute annotationsAttribute, AnnotationsAttribute annotationsAttribute2) {
        Annotation[] annotations = annotationsAttribute == null ? null : annotationsAttribute.getAnnotations();
        Annotation[] annotations2 = annotationsAttribute2 != null ? annotationsAttribute2.getAnnotations() : null;
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (annotation.getTypeName().equals(str)) {
                    return true;
                }
            }
        }
        if (annotations2 != null) {
            for (Annotation annotation2 : annotations2) {
                if (annotation2.getTypeName().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // javassist.CtClass
    public Object getAnnotation(Class<?> cls) throws ClassNotFoundException {
        ClassFile classFile2 = getClassFile2();
        return getAnnotationType(cls, getClassPool(), (AnnotationsAttribute) classFile2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) classFile2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object getAnnotationType(Class<?> cls, ClassPool classPool, AnnotationsAttribute annotationsAttribute, AnnotationsAttribute annotationsAttribute2) throws ClassNotFoundException {
        Annotation[] annotations = annotationsAttribute == null ? null : annotationsAttribute.getAnnotations();
        Annotation[] annotations2 = annotationsAttribute2 == null ? null : annotationsAttribute2.getAnnotations();
        String name = cls.getName();
        if (annotations != null) {
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].getTypeName().equals(name)) {
                    return toAnnoType(annotations[i], classPool);
                }
            }
        }
        if (annotations2 != null) {
            for (int i2 = 0; i2 < annotations2.length; i2++) {
                if (annotations2[i2].getTypeName().equals(name)) {
                    return toAnnoType(annotations2[i2], classPool);
                }
            }
        }
        return null;
    }

    @Override // javassist.CtClass
    public Object[] getAnnotations() throws ClassNotFoundException {
        return getAnnotations(false);
    }

    @Override // javassist.CtClass
    public Object[] getAvailableAnnotations() {
        try {
            return getAnnotations(true);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected exception ", e);
        }
    }

    private Object[] getAnnotations(boolean z) throws ClassNotFoundException {
        ClassFile classFile2 = getClassFile2();
        return toAnnotationType(z, getClassPool(), (AnnotationsAttribute) classFile2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) classFile2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object[] toAnnotationType(boolean z, ClassPool classPool, AnnotationsAttribute annotationsAttribute, AnnotationsAttribute annotationsAttribute2) throws ClassNotFoundException {
        Annotation[] annotations;
        int length;
        int length2;
        Annotation[] annotationArr = null;
        int i = 0;
        if (annotationsAttribute == null) {
            annotations = null;
            length = 0;
        } else {
            annotations = annotationsAttribute.getAnnotations();
            length = annotations.length;
        }
        if (annotationsAttribute2 == null) {
            length2 = 0;
        } else {
            annotationArr = annotationsAttribute2.getAnnotations();
            length2 = annotationArr.length;
        }
        if (!z) {
            Object[] objArr = new Object[length + length2];
            for (int i2 = 0; i2 < length; i2++) {
                objArr[i2] = toAnnoType(annotations[i2], classPool);
            }
            while (i < length2) {
                objArr[i + length] = toAnnoType(annotationArr[i], classPool);
                i++;
            }
            return objArr;
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < length; i3++) {
            try {
                arrayList.add(toAnnoType(annotations[i3], classPool));
            } catch (ClassNotFoundException unused) {
            }
        }
        while (i < length2) {
            try {
                arrayList.add(toAnnoType(annotationArr[i], classPool));
            } catch (ClassNotFoundException unused2) {
            }
            i++;
        }
        return arrayList.toArray();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object[][] toAnnotationType(boolean z, ClassPool classPool, ParameterAnnotationsAttribute parameterAnnotationsAttribute, ParameterAnnotationsAttribute parameterAnnotationsAttribute2, MethodInfo methodInfo) throws ClassNotFoundException {
        int numOfParameters;
        Annotation[] annotationArr;
        int length;
        int length2;
        if (parameterAnnotationsAttribute != null) {
            numOfParameters = parameterAnnotationsAttribute.numParameters();
        } else if (parameterAnnotationsAttribute2 != null) {
            numOfParameters = parameterAnnotationsAttribute2.numParameters();
        } else {
            numOfParameters = Descriptor.numOfParameters(methodInfo.getDescriptor());
        }
        Object[][] objArr = new Object[numOfParameters];
        for (int i = 0; i < numOfParameters; i++) {
            Annotation[] annotationArr2 = null;
            if (parameterAnnotationsAttribute == null) {
                length = 0;
                annotationArr = null;
            } else {
                annotationArr = parameterAnnotationsAttribute.getAnnotations()[i];
                length = annotationArr.length;
            }
            if (parameterAnnotationsAttribute2 == null) {
                length2 = 0;
            } else {
                annotationArr2 = parameterAnnotationsAttribute2.getAnnotations()[i];
                length2 = annotationArr2.length;
            }
            if (!z) {
                objArr[i] = new Object[length + length2];
                for (int i2 = 0; i2 < length; i2++) {
                    objArr[i][i2] = toAnnoType(annotationArr[i2], classPool);
                }
                for (int i3 = 0; i3 < length2; i3++) {
                    objArr[i][i3 + length] = toAnnoType(annotationArr2[i3], classPool);
                }
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i4 = 0; i4 < length; i4++) {
                    try {
                        arrayList.add(toAnnoType(annotationArr[i4], classPool));
                    } catch (ClassNotFoundException unused) {
                    }
                }
                for (int i5 = 0; i5 < length2; i5++) {
                    try {
                        arrayList.add(toAnnoType(annotationArr2[i5], classPool));
                    } catch (ClassNotFoundException unused2) {
                    }
                }
                objArr[i] = arrayList.toArray();
            }
        }
        return objArr;
    }

    private static Object toAnnoType(Annotation annotation, ClassPool classPool) throws ClassNotFoundException {
        try {
            return annotation.toAnnotationType(classPool.getClassLoader(), classPool);
        } catch (ClassNotFoundException unused) {
            try {
                return annotation.toAnnotationType(classPool.getClass().getClassLoader(), classPool);
            } catch (ClassNotFoundException unused2) {
                Class<?> cls = classPool.get(annotation.getTypeName()).toClass();
                return AnnotationImpl.make(cls.getClassLoader(), cls, classPool, annotation);
            }
        }
    }

    @Override // javassist.CtClass
    public boolean subclassOf(CtClass ctClass) {
        if (ctClass == null) {
            return false;
        }
        String name = ctClass.getName();
        for (CtClass ctClass2 = this; ctClass2 != null; ctClass2 = ctClass2.getSuperclass()) {
            try {
                if (ctClass2.getName().equals(name)) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    @Override // javassist.CtClass
    public CtClass getSuperclass() throws NotFoundException {
        String superclass = getClassFile2().getSuperclass();
        if (superclass == null) {
            return null;
        }
        return this.classPool.get(superclass);
    }

    @Override // javassist.CtClass
    public void setSuperclass(CtClass ctClass) throws CannotCompileException {
        checkModify();
        if (isInterface()) {
            addInterface(ctClass);
        } else {
            getClassFile2().setSuperclass(ctClass.getName());
        }
    }

    @Override // javassist.CtClass
    public CtClass[] getInterfaces() throws NotFoundException {
        String[] interfaces = getClassFile2().getInterfaces();
        int length = interfaces.length;
        CtClass[] ctClassArr = new CtClass[length];
        for (int i = 0; i < length; i++) {
            ctClassArr[i] = this.classPool.get(interfaces[i]);
        }
        return ctClassArr;
    }

    @Override // javassist.CtClass
    public void setInterfaces(CtClass[] ctClassArr) {
        String[] strArr;
        checkModify();
        if (ctClassArr == null) {
            strArr = new String[0];
        } else {
            int length = ctClassArr.length;
            String[] strArr2 = new String[length];
            for (int i = 0; i < length; i++) {
                strArr2[i] = ctClassArr[i].getName();
            }
            strArr = strArr2;
        }
        getClassFile2().setInterfaces(strArr);
    }

    @Override // javassist.CtClass
    public void addInterface(CtClass ctClass) {
        checkModify();
        if (ctClass != null) {
            getClassFile2().addInterface(ctClass.getName());
        }
    }

    @Override // javassist.CtClass
    public CtClass getDeclaringClass() throws NotFoundException {
        ClassFile classFile2 = getClassFile2();
        InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) classFile2.getAttribute(InnerClassesAttribute.tag);
        if (innerClassesAttribute == null) {
            return null;
        }
        String name = getName();
        int tableLength = innerClassesAttribute.tableLength();
        for (int i = 0; i < tableLength; i++) {
            if (name.equals(innerClassesAttribute.innerClass(i))) {
                String outerClass = innerClassesAttribute.outerClass(i);
                if (outerClass != null) {
                    return this.classPool.get(outerClass);
                }
                EnclosingMethodAttribute enclosingMethodAttribute = (EnclosingMethodAttribute) classFile2.getAttribute(EnclosingMethodAttribute.tag);
                if (enclosingMethodAttribute != null) {
                    return this.classPool.get(enclosingMethodAttribute.className());
                }
            }
        }
        return null;
    }

    @Override // javassist.CtClass
    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        EnclosingMethodAttribute enclosingMethodAttribute = (EnclosingMethodAttribute) getClassFile2().getAttribute(EnclosingMethodAttribute.tag);
        if (enclosingMethodAttribute == null) {
            return null;
        }
        CtClass ctClass = this.classPool.get(enclosingMethodAttribute.className());
        String methodName = enclosingMethodAttribute.methodName();
        if ("<init>".equals(methodName)) {
            return ctClass.getConstructor(enclosingMethodAttribute.methodDescriptor());
        }
        if (MethodInfo.nameClinit.equals(methodName)) {
            return ctClass.getClassInitializer();
        }
        return ctClass.getMethod(methodName, enclosingMethodAttribute.methodDescriptor());
    }

    @Override // javassist.CtClass
    public CtClass makeNestedClass(String str, boolean z) {
        if (!z) {
            throw new RuntimeException("sorry, only nested static class is supported");
        }
        checkModify();
        CtClass makeNestedClass = this.classPool.makeNestedClass(getName() + "$" + str);
        ClassFile classFile2 = getClassFile2();
        ClassFile classFile22 = makeNestedClass.getClassFile2();
        InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) classFile2.getAttribute(InnerClassesAttribute.tag);
        if (innerClassesAttribute == null) {
            innerClassesAttribute = new InnerClassesAttribute(classFile2.getConstPool());
            classFile2.addAttribute(innerClassesAttribute);
        }
        innerClassesAttribute.append(makeNestedClass.getName(), getName(), str, (classFile22.getAccessFlags() & (-33)) | 8);
        classFile22.addAttribute(innerClassesAttribute.copy(classFile22.getConstPool(), null));
        return makeNestedClass;
    }

    private void nameReplaced() {
        CtMember.Cache hasMemberCache = hasMemberCache();
        if (hasMemberCache != null) {
            CtMember methodHead = hasMemberCache.methodHead();
            CtMember lastMethod = hasMemberCache.lastMethod();
            while (methodHead != lastMethod) {
                methodHead = methodHead.next();
                methodHead.nameReplaced();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CtMember.Cache hasMemberCache() {
        Reference<CtMember.Cache> reference = this.memberCache;
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    protected synchronized CtMember.Cache getMembers() {
        CtMember.Cache cache;
        Reference<CtMember.Cache> reference = this.memberCache;
        if (reference == null || (cache = reference.get()) == null) {
            cache = new CtMember.Cache(this);
            makeFieldCache(cache);
            makeBehaviorCache(cache);
            this.memberCache = new WeakReference(cache);
        }
        return cache;
    }

    private void makeFieldCache(CtMember.Cache cache) {
        for (FieldInfo fieldInfo : getClassFile3(false).getFields()) {
            cache.addField(new CtField(fieldInfo, this));
        }
    }

    private void makeBehaviorCache(CtMember.Cache cache) {
        for (MethodInfo methodInfo : getClassFile3(false).getMethods()) {
            if (methodInfo.isMethod()) {
                cache.addMethod(new CtMethod(methodInfo, this));
            } else {
                cache.addConstructor(new CtConstructor(methodInfo, this));
            }
        }
    }

    @Override // javassist.CtClass
    public CtField[] getFields() {
        ArrayList arrayList = new ArrayList();
        getFields(arrayList, this);
        return (CtField[]) arrayList.toArray(new CtField[arrayList.size()]);
    }

    private static void getFields(List<CtMember> list, CtClass ctClass) {
        if (ctClass == null) {
            return;
        }
        try {
            getFields(list, ctClass.getSuperclass());
        } catch (NotFoundException unused) {
        }
        try {
            for (CtClass ctClass2 : ctClass.getInterfaces()) {
                getFields(list, ctClass2);
            }
        } catch (NotFoundException unused2) {
        }
        CtMember.Cache members = ((CtClassType) ctClass).getMembers();
        CtMember fieldHead = members.fieldHead();
        CtMember lastField = members.lastField();
        while (fieldHead != lastField) {
            fieldHead = fieldHead.next();
            if (!Modifier.isPrivate(fieldHead.getModifiers())) {
                list.add(fieldHead);
            }
        }
    }

    @Override // javassist.CtClass
    public CtField getField(String str, String str2) throws NotFoundException {
        return checkGetField(getField2(str, str2), str, str2);
    }

    private CtField checkGetField(CtField ctField, String str, String str2) throws NotFoundException {
        if (ctField == null) {
            String str3 = "field: " + str;
            if (str2 != null) {
                str3 = str3 + " type " + str2;
            }
            throw new NotFoundException(str3 + " in " + getName());
        }
        return ctField;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.CtClass
    public CtField getField2(String str, String str2) {
        CtField declaredField2 = getDeclaredField2(str, str2);
        if (declaredField2 != null) {
            return declaredField2;
        }
        try {
            for (CtClass ctClass : getInterfaces()) {
                CtField field2 = ctClass.getField2(str, str2);
                if (field2 != null) {
                    return field2;
                }
            }
            CtClass superclass = getSuperclass();
            if (superclass != null) {
                return superclass.getField2(str, str2);
            }
            return null;
        } catch (NotFoundException unused) {
            return null;
        }
    }

    @Override // javassist.CtClass
    public CtField[] getDeclaredFields() {
        CtMember.Cache members = getMembers();
        CtMember fieldHead = members.fieldHead();
        CtMember lastField = members.lastField();
        CtField[] ctFieldArr = new CtField[CtMember.Cache.count(fieldHead, lastField)];
        int i = 0;
        while (fieldHead != lastField) {
            fieldHead = fieldHead.next();
            ctFieldArr[i] = (CtField) fieldHead;
            i++;
        }
        return ctFieldArr;
    }

    @Override // javassist.CtClass
    public CtField getDeclaredField(String str) throws NotFoundException {
        return getDeclaredField(str, null);
    }

    @Override // javassist.CtClass
    public CtField getDeclaredField(String str, String str2) throws NotFoundException {
        return checkGetField(getDeclaredField2(str, str2), str, str2);
    }

    private CtField getDeclaredField2(String str, String str2) {
        CtMember.Cache members = getMembers();
        CtMember fieldHead = members.fieldHead();
        CtMember lastField = members.lastField();
        while (fieldHead != lastField) {
            fieldHead = fieldHead.next();
            if (fieldHead.getName().equals(str) && (str2 == null || str2.equals(fieldHead.getSignature()))) {
                return (CtField) fieldHead;
            }
        }
        return null;
    }

    @Override // javassist.CtClass
    public CtBehavior[] getDeclaredBehaviors() {
        CtMember.Cache members = getMembers();
        CtMember consHead = members.consHead();
        CtMember lastCons = members.lastCons();
        int count = CtMember.Cache.count(consHead, lastCons);
        CtMember methodHead = members.methodHead();
        CtMember lastMethod = members.lastMethod();
        CtBehavior[] ctBehaviorArr = new CtBehavior[count + CtMember.Cache.count(methodHead, lastMethod)];
        int i = 0;
        while (consHead != lastCons) {
            consHead = consHead.next();
            ctBehaviorArr[i] = (CtBehavior) consHead;
            i++;
        }
        while (methodHead != lastMethod) {
            methodHead = methodHead.next();
            ctBehaviorArr[i] = (CtBehavior) methodHead;
            i++;
        }
        return ctBehaviorArr;
    }

    @Override // javassist.CtClass
    public CtConstructor[] getConstructors() {
        CtMember.Cache members = getMembers();
        CtMember consHead = members.consHead();
        CtMember lastCons = members.lastCons();
        int i = 0;
        CtMember ctMember = consHead;
        int i2 = 0;
        while (ctMember != lastCons) {
            ctMember = ctMember.next();
            if (isPubCons((CtConstructor) ctMember)) {
                i2++;
            }
        }
        CtConstructor[] ctConstructorArr = new CtConstructor[i2];
        while (consHead != lastCons) {
            consHead = consHead.next();
            CtConstructor ctConstructor = (CtConstructor) consHead;
            if (isPubCons(ctConstructor)) {
                ctConstructorArr[i] = ctConstructor;
                i++;
            }
        }
        return ctConstructorArr;
    }

    private static boolean isPubCons(CtConstructor ctConstructor) {
        return !Modifier.isPrivate(ctConstructor.getModifiers()) && ctConstructor.isConstructor();
    }

    @Override // javassist.CtClass
    public CtConstructor getConstructor(String str) throws NotFoundException {
        CtMember.Cache members = getMembers();
        CtMember consHead = members.consHead();
        CtMember lastCons = members.lastCons();
        while (consHead != lastCons) {
            consHead = consHead.next();
            CtConstructor ctConstructor = (CtConstructor) consHead;
            if (ctConstructor.getMethodInfo2().getDescriptor().equals(str) && ctConstructor.isConstructor()) {
                return ctConstructor;
            }
        }
        return super.getConstructor(str);
    }

    @Override // javassist.CtClass
    public CtConstructor[] getDeclaredConstructors() {
        CtMember.Cache members = getMembers();
        CtMember consHead = members.consHead();
        CtMember lastCons = members.lastCons();
        int i = 0;
        CtMember ctMember = consHead;
        int i2 = 0;
        while (ctMember != lastCons) {
            ctMember = ctMember.next();
            if (((CtConstructor) ctMember).isConstructor()) {
                i2++;
            }
        }
        CtConstructor[] ctConstructorArr = new CtConstructor[i2];
        while (consHead != lastCons) {
            consHead = consHead.next();
            CtConstructor ctConstructor = (CtConstructor) consHead;
            if (ctConstructor.isConstructor()) {
                ctConstructorArr[i] = ctConstructor;
                i++;
            }
        }
        return ctConstructorArr;
    }

    @Override // javassist.CtClass
    public CtConstructor getClassInitializer() {
        CtMember.Cache members = getMembers();
        CtMember consHead = members.consHead();
        CtMember lastCons = members.lastCons();
        while (consHead != lastCons) {
            consHead = consHead.next();
            CtConstructor ctConstructor = (CtConstructor) consHead;
            if (ctConstructor.isClassInitializer()) {
                return ctConstructor;
            }
        }
        return null;
    }

    @Override // javassist.CtClass
    public CtMethod[] getMethods() {
        HashMap hashMap = new HashMap();
        getMethods0(hashMap, this);
        return (CtMethod[]) hashMap.values().toArray(new CtMethod[hashMap.size()]);
    }

    private static void getMethods0(Map<String, CtMember> map, CtClass ctClass) {
        try {
            for (CtClass ctClass2 : ctClass.getInterfaces()) {
                getMethods0(map, ctClass2);
            }
        } catch (NotFoundException unused) {
        }
        try {
            CtClass superclass = ctClass.getSuperclass();
            if (superclass != null) {
                getMethods0(map, superclass);
            }
        } catch (NotFoundException unused2) {
        }
        if (ctClass instanceof CtClassType) {
            CtMember.Cache members = ((CtClassType) ctClass).getMembers();
            CtMember methodHead = members.methodHead();
            CtMember lastMethod = members.lastMethod();
            while (methodHead != lastMethod) {
                methodHead = methodHead.next();
                if (!Modifier.isPrivate(methodHead.getModifiers())) {
                    map.put(((CtMethod) methodHead).getStringRep(), methodHead);
                }
            }
        }
    }

    @Override // javassist.CtClass
    public CtMethod getMethod(String str, String str2) throws NotFoundException {
        CtMethod method0 = getMethod0(this, str, str2);
        if (method0 != null) {
            return method0;
        }
        throw new NotFoundException(str + "(..) is not found in " + getName());
    }

    private static CtMethod getMethod0(CtClass ctClass, String str, String str2) {
        if (ctClass instanceof CtClassType) {
            CtMember.Cache members = ((CtClassType) ctClass).getMembers();
            CtMember methodHead = members.methodHead();
            CtMember lastMethod = members.lastMethod();
            while (methodHead != lastMethod) {
                methodHead = methodHead.next();
                if (methodHead.getName().equals(str)) {
                    CtMethod ctMethod = (CtMethod) methodHead;
                    if (ctMethod.getMethodInfo2().getDescriptor().equals(str2)) {
                        return ctMethod;
                    }
                }
            }
        }
        try {
            CtClass superclass = ctClass.getSuperclass();
            if (superclass != null) {
                CtMethod method0 = getMethod0(superclass, str, str2);
                if (method0 != null) {
                    return method0;
                }
            }
        } catch (NotFoundException unused) {
        }
        try {
            for (CtClass ctClass2 : ctClass.getInterfaces()) {
                CtMethod method02 = getMethod0(ctClass2, str, str2);
                if (method02 != null) {
                    return method02;
                }
            }
            return null;
        } catch (NotFoundException unused2) {
            return null;
        }
    }

    @Override // javassist.CtClass
    public CtMethod[] getDeclaredMethods() {
        CtMember.Cache members = getMembers();
        CtMember methodHead = members.methodHead();
        CtMember lastMethod = members.lastMethod();
        ArrayList arrayList = new ArrayList();
        while (methodHead != lastMethod) {
            methodHead = methodHead.next();
            arrayList.add(methodHead);
        }
        return (CtMethod[]) arrayList.toArray(new CtMethod[arrayList.size()]);
    }

    @Override // javassist.CtClass
    public CtMethod[] getDeclaredMethods(String str) throws NotFoundException {
        CtMember.Cache members = getMembers();
        CtMember methodHead = members.methodHead();
        CtMember lastMethod = members.lastMethod();
        ArrayList arrayList = new ArrayList();
        while (methodHead != lastMethod) {
            methodHead = methodHead.next();
            if (methodHead.getName().equals(str)) {
                arrayList.add(methodHead);
            }
        }
        return (CtMethod[]) arrayList.toArray(new CtMethod[arrayList.size()]);
    }

    @Override // javassist.CtClass
    public CtMethod getDeclaredMethod(String str) throws NotFoundException {
        CtMember.Cache members = getMembers();
        CtMember methodHead = members.methodHead();
        CtMember lastMethod = members.lastMethod();
        while (methodHead != lastMethod) {
            methodHead = methodHead.next();
            if (methodHead.getName().equals(str)) {
                return (CtMethod) methodHead;
            }
        }
        throw new NotFoundException(str + "(..) is not found in " + getName());
    }

    @Override // javassist.CtClass
    public CtMethod getDeclaredMethod(String str, CtClass[] ctClassArr) throws NotFoundException {
        String ofParameters = Descriptor.ofParameters(ctClassArr);
        CtMember.Cache members = getMembers();
        CtMember methodHead = members.methodHead();
        CtMember lastMethod = members.lastMethod();
        while (methodHead != lastMethod) {
            methodHead = methodHead.next();
            if (methodHead.getName().equals(str)) {
                CtMethod ctMethod = (CtMethod) methodHead;
                if (ctMethod.getMethodInfo2().getDescriptor().startsWith(ofParameters)) {
                    return ctMethod;
                }
            }
        }
        throw new NotFoundException(str + "(..) is not found in " + getName());
    }

    @Override // javassist.CtClass
    public void addField(CtField ctField, String str) throws CannotCompileException {
        addField(ctField, CtField.Initializer.byExpr(str));
    }

    @Override // javassist.CtClass
    public void addField(CtField ctField, CtField.Initializer initializer) throws CannotCompileException {
        checkModify();
        if (ctField.getDeclaringClass() != this) {
            throw new CannotCompileException("cannot add");
        }
        if (initializer == null) {
            initializer = ctField.getInit();
        }
        if (initializer != null) {
            initializer.check(ctField.getSignature());
            int modifiers = ctField.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                try {
                    ConstPool constPool = getClassFile2().getConstPool();
                    int constantValue = initializer.getConstantValue(constPool, ctField.getType());
                    if (constantValue != 0) {
                        ctField.getFieldInfo2().addAttribute(new ConstantAttribute(constPool, constantValue));
                        initializer = null;
                    }
                } catch (NotFoundException unused) {
                }
            }
        }
        getMembers().addField(ctField);
        getClassFile2().addField(ctField.getFieldInfo2());
        if (initializer != null) {
            FieldInitLink fieldInitLink = new FieldInitLink(ctField, initializer);
            FieldInitLink fieldInitLink2 = this.fieldInitializers;
            if (fieldInitLink2 == null) {
                this.fieldInitializers = fieldInitLink;
                return;
            }
            while (fieldInitLink2.next != null) {
                fieldInitLink2 = fieldInitLink2.next;
            }
            fieldInitLink2.next = fieldInitLink;
        }
    }

    @Override // javassist.CtClass
    public void removeField(CtField ctField) throws NotFoundException {
        checkModify();
        if (getClassFile2().getFields().remove(ctField.getFieldInfo2())) {
            getMembers().remove(ctField);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(ctField.toString());
    }

    @Override // javassist.CtClass
    public CtConstructor makeClassInitializer() throws CannotCompileException {
        CtConstructor classInitializer = getClassInitializer();
        if (classInitializer != null) {
            return classInitializer;
        }
        checkModify();
        ClassFile classFile2 = getClassFile2();
        modifyClassConstructor(classFile2, new Bytecode(classFile2.getConstPool(), 0, 0), 0, 0);
        return getClassInitializer();
    }

    @Override // javassist.CtClass
    public void addConstructor(CtConstructor ctConstructor) throws CannotCompileException {
        checkModify();
        if (ctConstructor.getDeclaringClass() != this) {
            throw new CannotCompileException("cannot add");
        }
        getMembers().addConstructor(ctConstructor);
        getClassFile2().addMethod(ctConstructor.getMethodInfo2());
    }

    @Override // javassist.CtClass
    public void removeConstructor(CtConstructor ctConstructor) throws NotFoundException {
        checkModify();
        if (getClassFile2().getMethods().remove(ctConstructor.getMethodInfo2())) {
            getMembers().remove(ctConstructor);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(ctConstructor.toString());
    }

    @Override // javassist.CtClass
    public void addMethod(CtMethod ctMethod) throws CannotCompileException {
        checkModify();
        if (ctMethod.getDeclaringClass() != this) {
            throw new CannotCompileException("bad declaring class");
        }
        int modifiers = ctMethod.getModifiers();
        if ((getModifiers() & 512) != 0) {
            if (Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers)) {
                throw new CannotCompileException("an interface method must be public: " + ctMethod.toString());
            }
            ctMethod.setModifiers(modifiers | 1);
        }
        getMembers().addMethod(ctMethod);
        getClassFile2().addMethod(ctMethod.getMethodInfo2());
        if ((modifiers & 1024) != 0) {
            setModifiers(getModifiers() | 1024);
        }
    }

    @Override // javassist.CtClass
    public void removeMethod(CtMethod ctMethod) throws NotFoundException {
        checkModify();
        if (getClassFile2().getMethods().remove(ctMethod.getMethodInfo2())) {
            getMembers().remove(ctMethod);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(ctMethod.toString());
    }

    @Override // javassist.CtClass
    public byte[] getAttribute(String str) {
        AttributeInfo attribute = getClassFile2().getAttribute(str);
        if (attribute == null) {
            return null;
        }
        return attribute.get();
    }

    @Override // javassist.CtClass
    public void setAttribute(String str, byte[] bArr) {
        checkModify();
        ClassFile classFile2 = getClassFile2();
        classFile2.addAttribute(new AttributeInfo(classFile2.getConstPool(), str, bArr));
    }

    @Override // javassist.CtClass
    public void instrument(CodeConverter codeConverter) throws CannotCompileException {
        checkModify();
        ClassFile classFile2 = getClassFile2();
        ConstPool constPool = classFile2.getConstPool();
        List<MethodInfo> methods = classFile2.getMethods();
        for (MethodInfo methodInfo : (MethodInfo[]) methods.toArray(new MethodInfo[methods.size()])) {
            codeConverter.doit(this, methodInfo, constPool);
        }
    }

    @Override // javassist.CtClass
    public void instrument(ExprEditor exprEditor) throws CannotCompileException {
        checkModify();
        List<MethodInfo> methods = getClassFile2().getMethods();
        for (MethodInfo methodInfo : (MethodInfo[]) methods.toArray(new MethodInfo[methods.size()])) {
            exprEditor.doit(this, methodInfo);
        }
    }

    @Override // javassist.CtClass
    public void prune() {
        if (this.wasPruned) {
            return;
        }
        this.wasFrozen = true;
        this.wasPruned = true;
        getClassFile2().prune();
    }

    @Override // javassist.CtClass
    public void rebuildClassFile() {
        this.gcConstPool = true;
    }

    @Override // javassist.CtClass
    public void toBytecode(DataOutputStream dataOutputStream) throws CannotCompileException, IOException {
        try {
            if (isModified()) {
                checkPruned("toBytecode");
                ClassFile classFile2 = getClassFile2();
                if (this.gcConstPool) {
                    classFile2.compact();
                    this.gcConstPool = false;
                }
                modifyClassConstructor(classFile2);
                modifyConstructors(classFile2);
                if (debugDump != null) {
                    dumpClassFile(classFile2);
                }
                classFile2.write(dataOutputStream);
                dataOutputStream.flush();
                this.fieldInitializers = null;
                if (this.doPruning) {
                    classFile2.prune();
                    this.wasPruned = true;
                }
            } else {
                this.classPool.writeClassfile(getName(), dataOutputStream);
            }
            this.getCount = 0;
            this.wasFrozen = true;
        } catch (IOException e) {
            throw new CannotCompileException(e);
        } catch (NotFoundException e2) {
            throw new CannotCompileException(e2);
        }
    }

    private void dumpClassFile(ClassFile classFile) throws IOException {
        DataOutputStream makeFileOutput = makeFileOutput(debugDump);
        try {
            classFile.write(makeFileOutput);
        } finally {
            makeFileOutput.close();
        }
    }

    private void checkPruned(String str) {
        if (this.wasPruned) {
            throw new RuntimeException(str + "(): " + getName() + " was pruned.");
        }
    }

    @Override // javassist.CtClass
    public boolean stopPruning(boolean z) {
        boolean z2 = !this.doPruning;
        this.doPruning = !z;
        return z2;
    }

    private void modifyClassConstructor(ClassFile classFile) throws CannotCompileException, NotFoundException {
        if (this.fieldInitializers == null) {
            return;
        }
        Bytecode bytecode = new Bytecode(classFile.getConstPool(), 0, 0);
        Javac javac = new Javac(bytecode, this);
        boolean z = false;
        int i = 0;
        for (FieldInitLink fieldInitLink = this.fieldInitializers; fieldInitLink != null; fieldInitLink = fieldInitLink.next) {
            CtField ctField = fieldInitLink.field;
            if (Modifier.isStatic(ctField.getModifiers())) {
                int compileIfStatic = fieldInitLink.init.compileIfStatic(ctField.getType(), ctField.getName(), bytecode, javac);
                if (i < compileIfStatic) {
                    i = compileIfStatic;
                }
                z = true;
            }
        }
        if (z) {
            modifyClassConstructor(classFile, bytecode, i, 0);
        }
    }

    private void modifyClassConstructor(ClassFile classFile, Bytecode bytecode, int i, int i2) throws CannotCompileException {
        MethodInfo staticInitializer = classFile.getStaticInitializer();
        if (staticInitializer == null) {
            bytecode.add(177);
            bytecode.setMaxStack(i);
            bytecode.setMaxLocals(i2);
            staticInitializer = new MethodInfo(classFile.getConstPool(), MethodInfo.nameClinit, "()V");
            staticInitializer.setAccessFlags(8);
            staticInitializer.setCodeAttribute(bytecode.toCodeAttribute());
            classFile.addMethod(staticInitializer);
            CtMember.Cache hasMemberCache = hasMemberCache();
            if (hasMemberCache != null) {
                hasMemberCache.addConstructor(new CtConstructor(staticInitializer, this));
            }
        } else {
            CodeAttribute codeAttribute = staticInitializer.getCodeAttribute();
            if (codeAttribute == null) {
                throw new CannotCompileException("empty <clinit>");
            }
            try {
                CodeIterator it = codeAttribute.iterator();
                it.insert(bytecode.getExceptionTable(), it.insertEx(bytecode.get()));
                if (codeAttribute.getMaxStack() < i) {
                    codeAttribute.setMaxStack(i);
                }
                if (codeAttribute.getMaxLocals() < i2) {
                    codeAttribute.setMaxLocals(i2);
                }
            } catch (BadBytecode e) {
                throw new CannotCompileException(e);
            }
        }
        try {
            staticInitializer.rebuildStackMapIf6(this.classPool, classFile);
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2);
        }
    }

    private void modifyConstructors(ClassFile classFile) throws CannotCompileException, NotFoundException {
        CodeAttribute codeAttribute;
        if (this.fieldInitializers == null) {
            return;
        }
        ConstPool constPool = classFile.getConstPool();
        for (MethodInfo methodInfo : classFile.getMethods()) {
            if (methodInfo.isConstructor() && (codeAttribute = methodInfo.getCodeAttribute()) != null) {
                try {
                    Bytecode bytecode = new Bytecode(constPool, 0, codeAttribute.getMaxLocals());
                    insertAuxInitializer(codeAttribute, bytecode, makeFieldInitializer(bytecode, Descriptor.getParameterTypes(methodInfo.getDescriptor(), this.classPool)));
                    methodInfo.rebuildStackMapIf6(this.classPool, classFile);
                } catch (BadBytecode e) {
                    throw new CannotCompileException(e);
                }
            }
        }
    }

    private static void insertAuxInitializer(CodeAttribute codeAttribute, Bytecode bytecode, int i) throws BadBytecode {
        CodeIterator it = codeAttribute.iterator();
        if (it.skipSuperConstructor() >= 0 || it.skipThisConstructor() < 0) {
            it.insert(bytecode.getExceptionTable(), it.insertEx(bytecode.get()));
            if (codeAttribute.getMaxStack() < i) {
                codeAttribute.setMaxStack(i);
            }
        }
    }

    private int makeFieldInitializer(Bytecode bytecode, CtClass[] ctClassArr) throws CannotCompileException, NotFoundException {
        int compile;
        Javac javac = new Javac(bytecode, this);
        try {
            javac.recordParams(ctClassArr, false);
            int i = 0;
            for (FieldInitLink fieldInitLink = this.fieldInitializers; fieldInitLink != null; fieldInitLink = fieldInitLink.next) {
                CtField ctField = fieldInitLink.field;
                if (!Modifier.isStatic(ctField.getModifiers()) && i < (compile = fieldInitLink.init.compile(ctField.getType(), ctField.getName(), bytecode, ctClassArr, javac))) {
                    i = compile;
                }
            }
            return i;
        } catch (CompileError e) {
            throw new CannotCompileException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<CtMethod, String> getHiddenMethods() {
        if (this.hiddenMethods == null) {
            this.hiddenMethods = new Hashtable();
        }
        return this.hiddenMethods;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getUniqueNumber() {
        int i = this.uniqueNumberSeed;
        this.uniqueNumberSeed = i + 1;
        return i;
    }

    @Override // javassist.CtClass
    public String makeUniqueName(String str) {
        HashMap hashMap = new HashMap();
        makeMemberList(hashMap);
        Set<Object> keySet = hashMap.keySet();
        String[] strArr = new String[keySet.size()];
        keySet.toArray(strArr);
        if (notFindInArray(str, strArr)) {
            return str;
        }
        int i = 100;
        while (i <= 999) {
            int i2 = i + 1;
            String str2 = str + i;
            if (notFindInArray(str2, strArr)) {
                return str2;
            }
            i = i2;
        }
        throw new RuntimeException("too many unique name");
    }

    private static boolean notFindInArray(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str2.startsWith(str)) {
                return false;
            }
        }
        return true;
    }

    private void makeMemberList(Map<Object, CtClassType> map) {
        CtClass[] interfaces;
        int modifiers = getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
            try {
                for (CtClass ctClass : getInterfaces()) {
                    if (ctClass != null && (ctClass instanceof CtClassType)) {
                        ((CtClassType) ctClass).makeMemberList(map);
                    }
                }
            } catch (NotFoundException unused) {
            }
        }
        try {
            CtClass superclass = getSuperclass();
            if (superclass != null && (superclass instanceof CtClassType)) {
                ((CtClassType) superclass).makeMemberList(map);
            }
        } catch (NotFoundException unused2) {
        }
        for (MethodInfo methodInfo : getClassFile2().getMethods()) {
            map.put(methodInfo.getName(), this);
        }
        for (FieldInfo fieldInfo : getClassFile2().getFields()) {
            map.put(fieldInfo.getName(), this);
        }
    }
}
