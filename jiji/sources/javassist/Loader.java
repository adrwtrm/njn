package javassist;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javassist.bytecode.ClassFile;

/* loaded from: classes2.dex */
public class Loader extends ClassLoader {
    public boolean doDelegation;
    private ProtectionDomain domain;
    private HashMap<String, ClassLoader> notDefinedHere;
    private Vector<String> notDefinedPackages;
    private ClassPool source;
    private Translator translator;

    /* loaded from: classes2.dex */
    public static class Simple extends ClassLoader {
        public Simple() {
        }

        public Simple(ClassLoader classLoader) {
            super(classLoader);
        }

        public Class<?> invokeDefineClass(CtClass ctClass) throws IOException, CannotCompileException {
            byte[] bytecode = ctClass.toBytecode();
            return defineClass(ctClass.getName(), bytecode, 0, bytecode.length);
        }
    }

    public Loader() {
        this(null);
    }

    public Loader(ClassPool classPool) {
        this.doDelegation = true;
        init(classPool);
    }

    public Loader(ClassLoader classLoader, ClassPool classPool) {
        super(classLoader);
        this.doDelegation = true;
        init(classPool);
    }

    private void init(ClassPool classPool) {
        this.notDefinedHere = new HashMap<>();
        this.notDefinedPackages = new Vector<>();
        this.source = classPool;
        this.translator = null;
        this.domain = null;
        delegateLoadingOf("javassist.Loader");
    }

    public void delegateLoadingOf(String str) {
        if (str.endsWith(".")) {
            this.notDefinedPackages.addElement(str);
        } else {
            this.notDefinedHere.put(str, this);
        }
    }

    public void setDomain(ProtectionDomain protectionDomain) {
        this.domain = protectionDomain;
    }

    public void setClassPool(ClassPool classPool) {
        this.source = classPool;
    }

    public void addTranslator(ClassPool classPool, Translator translator) throws NotFoundException, CannotCompileException {
        this.source = classPool;
        this.translator = translator;
        translator.start(classPool);
    }

    public static void main(String[] strArr) throws Throwable {
        new Loader().run(strArr);
    }

    public void run(String[] strArr) throws Throwable {
        if (strArr.length >= 1) {
            run(strArr[0], (String[]) Arrays.copyOfRange(strArr, 1, strArr.length));
        }
    }

    public void run(String str, String[] strArr) throws Throwable {
        try {
            loadClass(str).getDeclaredMethod("main", String[].class).invoke(null, strArr);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    @Override // java.lang.ClassLoader
    protected Class<?> loadClass(String str, boolean z) throws ClassFormatError, ClassNotFoundException {
        Class<?> findLoadedClass;
        String intern = str.intern();
        synchronized (intern) {
            findLoadedClass = findLoadedClass(intern);
            if (findLoadedClass == null) {
                findLoadedClass = loadClassByDelegation(intern);
            }
            if (findLoadedClass == null) {
                findLoadedClass = findClass(intern);
            }
            if (findLoadedClass == null) {
                findLoadedClass = delegateToParent(intern);
            }
            if (z) {
                resolveClass(findLoadedClass);
            }
        }
        return findLoadedClass;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String str) throws ClassNotFoundException {
        byte[] readStream;
        try {
            ClassPool classPool = this.source;
            if (classPool != null) {
                Translator translator = this.translator;
                if (translator != null) {
                    translator.onLoad(classPool, str);
                }
                try {
                    readStream = this.source.get(str).toBytecode();
                } catch (NotFoundException unused) {
                    return null;
                }
            } else {
                InputStream resourceAsStream = getClass().getResourceAsStream("/" + str.replace('.', '/') + ".class");
                if (resourceAsStream == null) {
                    return null;
                }
                readStream = ClassPoolTail.readStream(resourceAsStream);
            }
            int lastIndexOf = str.lastIndexOf(46);
            if (lastIndexOf != -1) {
                String substring = str.substring(0, lastIndexOf);
                if (isDefinedPackage(substring)) {
                    try {
                        definePackage(substring, null, null, null, null, null, null, null);
                    } catch (IllegalArgumentException unused2) {
                    }
                }
            }
            ProtectionDomain protectionDomain = this.domain;
            if (protectionDomain == null) {
                return defineClass(str, readStream, 0, readStream.length);
            }
            return defineClass(str, readStream, 0, readStream.length, protectionDomain);
        } catch (Exception e) {
            throw new ClassNotFoundException("caught an exception while obtaining a class file for " + str, e);
        }
    }

    private boolean isDefinedPackage(String str) {
        return ClassFile.MAJOR_VERSION >= 53 ? getDefinedPackage(str) == null : getPackage(str) == null;
    }

    protected Class<?> loadClassByDelegation(String str) throws ClassNotFoundException {
        if (this.doDelegation && (str.startsWith("java.") || str.startsWith("javax.") || str.startsWith("sun.") || str.startsWith("com.sun.") || str.startsWith("org.w3c.") || str.startsWith("org.xml.") || notDelegated(str))) {
            return delegateToParent(str);
        }
        return null;
    }

    private boolean notDelegated(String str) {
        if (this.notDefinedHere.containsKey(str)) {
            return true;
        }
        Iterator<String> it = this.notDefinedPackages.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    protected Class<?> delegateToParent(String str) throws ClassNotFoundException {
        ClassLoader parent = getParent();
        if (parent != null) {
            return parent.loadClass(str);
        }
        return findSystemClass(str);
    }
}
