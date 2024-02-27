package javassist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.util.proxy.DefineClassHelper;
import javassist.util.proxy.DefinePackageHelper;

/* loaded from: classes2.dex */
public class ClassPool {
    private static final int COMPRESS_THRESHOLD = 100;
    private static final int INIT_HASH_SIZE = 191;
    public static boolean cacheOpenedJarFile = true;
    private static ClassPool defaultPool = null;
    public static boolean doPruning = false;
    public static boolean releaseUnmodifiedClassFile = true;
    private Hashtable cflow;
    public boolean childFirstLookup;
    protected Hashtable classes;
    private int compressCount;
    private ArrayList importedPackages;
    protected ClassPool parent;
    protected ClassPoolTail source;

    public void recordInvalidClassName(String str) {
    }

    public ClassPool() {
        this((ClassPool) null);
    }

    public ClassPool(boolean z) {
        this((ClassPool) null);
        if (z) {
            appendSystemPath();
        }
    }

    public ClassPool(ClassPool classPool) {
        this.childFirstLookup = false;
        this.cflow = null;
        this.classes = new Hashtable(191);
        this.source = new ClassPoolTail();
        this.parent = classPool;
        if (classPool == null) {
            CtClass[] ctClassArr = CtClass.primitiveTypes;
            for (int i = 0; i < ctClassArr.length; i++) {
                this.classes.put(ctClassArr[i].getName(), ctClassArr[i]);
            }
        }
        this.cflow = null;
        this.compressCount = 0;
        clearImportedPackages();
    }

    public static synchronized ClassPool getDefault() {
        ClassPool classPool;
        synchronized (ClassPool.class) {
            if (defaultPool == null) {
                ClassPool classPool2 = new ClassPool((ClassPool) null);
                defaultPool = classPool2;
                classPool2.appendSystemPath();
            }
            classPool = defaultPool;
        }
        return classPool;
    }

    protected CtClass getCached(String str) {
        return (CtClass) this.classes.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void cacheCtClass(String str, CtClass ctClass, boolean z) {
        this.classes.put(str, ctClass);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CtClass removeCached(String str) {
        return (CtClass) this.classes.remove(str);
    }

    public String toString() {
        return this.source.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void compress() {
        int i = this.compressCount;
        this.compressCount = i + 1;
        if (i > 100) {
            this.compressCount = 0;
            Enumeration elements = this.classes.elements();
            while (elements.hasMoreElements()) {
                ((CtClass) elements.nextElement()).compress();
            }
        }
    }

    public void importPackage(String str) {
        this.importedPackages.add(str);
    }

    public void clearImportedPackages() {
        ArrayList arrayList = new ArrayList();
        this.importedPackages = arrayList;
        arrayList.add("java.lang");
    }

    public Iterator<String> getImportedPackages() {
        return this.importedPackages.iterator();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void recordCflow(String str, String str2, String str3) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        this.cflow.put(str, new Object[]{str2, str3});
    }

    public Object[] lookupCflow(String str) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        return (Object[]) this.cflow.get(str);
    }

    public CtClass getAndRename(String str, String str2) throws NotFoundException {
        CtClass ctClass = get0(str, false);
        if (ctClass == null) {
            throw new NotFoundException(str);
        }
        if (ctClass instanceof CtClassType) {
            ((CtClassType) ctClass).setClassPool(this);
        }
        ctClass.setName(str2);
        return ctClass;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void classNameChanged(String str, CtClass ctClass) {
        if (getCached(str) == ctClass) {
            removeCached(str);
        }
        String name = ctClass.getName();
        checkNotFrozen(name);
        cacheCtClass(name, ctClass, false);
    }

    public CtClass get(String str) throws NotFoundException {
        CtClass ctClass = str == null ? null : get0(str, true);
        if (ctClass == null) {
            throw new NotFoundException(str);
        }
        ctClass.incGetCounter();
        return ctClass;
    }

    public CtClass getOrNull(String str) {
        CtClass ctClass = null;
        if (str != null) {
            try {
                ctClass = get0(str, true);
            } catch (NotFoundException unused) {
            }
        }
        if (ctClass != null) {
            ctClass.incGetCounter();
        }
        return ctClass;
    }

    public CtClass getCtClass(String str) throws NotFoundException {
        if (str.charAt(0) == '[') {
            return Descriptor.toCtClass(str, this);
        }
        return get(str);
    }

    protected synchronized CtClass get0(String str, boolean z) throws NotFoundException {
        ClassPool classPool;
        ClassPool classPool2;
        CtClass ctClass;
        if (z) {
            CtClass cached = getCached(str);
            if (cached != null) {
                return cached;
            }
        }
        if (this.childFirstLookup || (classPool2 = this.parent) == null || (ctClass = classPool2.get0(str, z)) == null) {
            CtClass createCtClass = createCtClass(str, z);
            if (createCtClass != null) {
                if (z) {
                    cacheCtClass(createCtClass.getName(), createCtClass, false);
                }
                return createCtClass;
            }
            if (this.childFirstLookup && (classPool = this.parent) != null) {
                createCtClass = classPool.get0(str, z);
            }
            return createCtClass;
        }
        return ctClass;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CtClass createCtClass(String str, boolean z) {
        if (str.charAt(0) == '[') {
            str = Descriptor.toClassName(str);
        }
        if (str.endsWith("[]")) {
            String substring = str.substring(0, str.indexOf(91));
            if ((!z || getCached(substring) == null) && find(substring) == null) {
                return null;
            }
            return new CtArray(str, this);
        } else if (find(str) == null) {
            return null;
        } else {
            return new CtClassType(str, this);
        }
    }

    public URL find(String str) {
        return this.source.find(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void checkNotFrozen(String str) throws RuntimeException {
        ClassPool classPool;
        CtClass cached = getCached(str);
        if (cached == null) {
            if (this.childFirstLookup || (classPool = this.parent) == null) {
                return;
            }
            try {
                cached = classPool.get0(str, true);
            } catch (NotFoundException unused) {
            }
            if (cached != null) {
                throw new RuntimeException(str + " is in a parent ClassPool.  Use the parent.");
            }
        } else if (cached.isFrozen()) {
            throw new RuntimeException(str + ": frozen class (cannot edit)");
        }
    }

    CtClass checkNotExists(String str) {
        ClassPool classPool;
        CtClass cached = getCached(str);
        if (cached != null || this.childFirstLookup || (classPool = this.parent) == null) {
            return cached;
        }
        try {
            return classPool.get0(str, true);
        } catch (NotFoundException unused) {
            return cached;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public InputStream openClassfile(String str) throws NotFoundException {
        return this.source.openClassfile(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeClassfile(String str, OutputStream outputStream) throws NotFoundException, IOException, CannotCompileException {
        this.source.writeClassfile(str, outputStream);
    }

    public CtClass[] get(String[] strArr) throws NotFoundException {
        if (strArr == null) {
            return new CtClass[0];
        }
        int length = strArr.length;
        CtClass[] ctClassArr = new CtClass[length];
        for (int i = 0; i < length; i++) {
            ctClassArr[i] = get(strArr[i]);
        }
        return ctClassArr;
    }

    public CtMethod getMethod(String str, String str2) throws NotFoundException {
        return get(str).getDeclaredMethod(str2);
    }

    public CtClass makeClass(InputStream inputStream) throws IOException, RuntimeException {
        return makeClass(inputStream, true);
    }

    public CtClass makeClass(InputStream inputStream, boolean z) throws IOException, RuntimeException {
        compress();
        CtClassType ctClassType = new CtClassType(new BufferedInputStream(inputStream), this);
        ctClassType.checkModify();
        String name = ctClassType.getName();
        if (z) {
            checkNotFrozen(name);
        }
        cacheCtClass(name, ctClassType, true);
        return ctClassType;
    }

    public CtClass makeClass(ClassFile classFile) throws RuntimeException {
        return makeClass(classFile, true);
    }

    public CtClass makeClass(ClassFile classFile, boolean z) throws RuntimeException {
        compress();
        CtClassType ctClassType = new CtClassType(classFile, this);
        ctClassType.checkModify();
        String name = ctClassType.getName();
        if (z) {
            checkNotFrozen(name);
        }
        cacheCtClass(name, ctClassType, true);
        return ctClassType;
    }

    public CtClass makeClassIfNew(InputStream inputStream) throws IOException, RuntimeException {
        compress();
        CtClassType ctClassType = new CtClassType(new BufferedInputStream(inputStream), this);
        ctClassType.checkModify();
        String name = ctClassType.getName();
        CtClass checkNotExists = checkNotExists(name);
        if (checkNotExists != null) {
            return checkNotExists;
        }
        cacheCtClass(name, ctClassType, true);
        return ctClassType;
    }

    public CtClass makeClass(String str) throws RuntimeException {
        return makeClass(str, (CtClass) null);
    }

    public synchronized CtClass makeClass(String str, CtClass ctClass) throws RuntimeException {
        CtNewClass ctNewClass;
        checkNotFrozen(str);
        ctNewClass = new CtNewClass(str, this, false, ctClass);
        cacheCtClass(str, ctNewClass, true);
        return ctNewClass;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized CtClass makeNestedClass(String str) {
        CtNewClass ctNewClass;
        checkNotFrozen(str);
        ctNewClass = new CtNewClass(str, this, false, null);
        cacheCtClass(str, ctNewClass, true);
        return ctNewClass;
    }

    public CtClass makeInterface(String str) throws RuntimeException {
        return makeInterface(str, null);
    }

    public synchronized CtClass makeInterface(String str, CtClass ctClass) throws RuntimeException {
        CtNewClass ctNewClass;
        checkNotFrozen(str);
        ctNewClass = new CtNewClass(str, this, true, ctClass);
        cacheCtClass(str, ctNewClass, true);
        return ctNewClass;
    }

    public CtClass makeAnnotation(String str) throws RuntimeException {
        try {
            CtClass makeInterface = makeInterface(str, get("java.lang.annotation.Annotation"));
            makeInterface.setModifiers(makeInterface.getModifiers() | 8192);
            return makeInterface;
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ClassPath appendSystemPath() {
        return this.source.appendSystemPath();
    }

    public ClassPath insertClassPath(ClassPath classPath) {
        return this.source.insertClassPath(classPath);
    }

    public ClassPath appendClassPath(ClassPath classPath) {
        return this.source.appendClassPath(classPath);
    }

    public ClassPath insertClassPath(String str) throws NotFoundException {
        return this.source.insertClassPath(str);
    }

    public ClassPath appendClassPath(String str) throws NotFoundException {
        return this.source.appendClassPath(str);
    }

    public void removeClassPath(ClassPath classPath) {
        this.source.removeClassPath(classPath);
    }

    public void appendPathList(String str) throws NotFoundException {
        char c = File.pathSeparatorChar;
        int i = 0;
        while (true) {
            int indexOf = str.indexOf(c, i);
            if (indexOf < 0) {
                appendClassPath(str.substring(i));
                return;
            } else {
                appendClassPath(str.substring(i, indexOf));
                i = indexOf + 1;
            }
        }
    }

    public Class toClass(CtClass ctClass) throws CannotCompileException {
        return toClass(ctClass, getClassLoader());
    }

    public ClassLoader getClassLoader() {
        return getContextClassLoader();
    }

    static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public Class toClass(CtClass ctClass, ClassLoader classLoader) throws CannotCompileException {
        return toClass(ctClass, null, classLoader, null);
    }

    public Class toClass(CtClass ctClass, ClassLoader classLoader, ProtectionDomain protectionDomain) throws CannotCompileException {
        return toClass(ctClass, null, classLoader, protectionDomain);
    }

    public Class<?> toClass(CtClass ctClass, Class<?> cls) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(cls, ctClass.toBytecode());
        } catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    public Class<?> toClass(CtClass ctClass, MethodHandles.Lookup lookup) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(lookup, ctClass.toBytecode());
        } catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    public Class toClass(CtClass ctClass, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(ctClass.getName(), cls, classLoader, protectionDomain, ctClass.toBytecode());
        } catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    public void makePackage(ClassLoader classLoader, String str) throws CannotCompileException {
        DefinePackageHelper.definePackage(str, classLoader);
    }
}
