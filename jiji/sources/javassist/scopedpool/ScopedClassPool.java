package javassist.scopedpool;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.security.ProtectionDomain;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/* loaded from: classes2.dex */
public class ScopedClassPool extends ClassPool {
    protected Reference<ClassLoader> classLoader;
    protected LoaderClassPath classPath;
    boolean isBootstrapCl;
    protected ScopedClassPoolRepository repository;
    protected Map<String, CtClass> softcache;

    public boolean isUnloadedClassLoader() {
        return false;
    }

    static {
        ClassPool.doPruning = false;
        ClassPool.releaseUnmodifiedClassFile = false;
    }

    protected ScopedClassPool(ClassLoader classLoader, ClassPool classPool, ScopedClassPoolRepository scopedClassPoolRepository) {
        this(classLoader, classPool, scopedClassPoolRepository, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ScopedClassPool(ClassLoader classLoader, ClassPool classPool, ScopedClassPoolRepository scopedClassPoolRepository, boolean z) {
        super(classPool);
        this.softcache = new SoftValueHashMap();
        this.isBootstrapCl = true;
        this.repository = scopedClassPoolRepository;
        this.classLoader = new WeakReference(classLoader);
        if (classLoader != null) {
            LoaderClassPath loaderClassPath = new LoaderClassPath(classLoader);
            this.classPath = loaderClassPath;
            insertClassPath(loaderClassPath);
        }
        this.childFirstLookup = true;
        if (z || classLoader != null) {
            return;
        }
        this.isBootstrapCl = true;
    }

    @Override // javassist.ClassPool
    public ClassLoader getClassLoader() {
        ClassLoader classLoader0 = getClassLoader0();
        if (classLoader0 != null || this.isBootstrapCl) {
            return classLoader0;
        }
        throw new IllegalStateException("ClassLoader has been garbage collected");
    }

    protected ClassLoader getClassLoader0() {
        return this.classLoader.get();
    }

    public void close() {
        removeClassPath(this.classPath);
        this.classes.clear();
        this.softcache.clear();
    }

    public synchronized void flushClass(String str) {
        this.classes.remove(str);
        this.softcache.remove(str);
    }

    public synchronized void soften(CtClass ctClass) {
        if (this.repository.isPrune()) {
            ctClass.prune();
        }
        this.classes.remove(ctClass.getName());
        this.softcache.put(ctClass.getName(), ctClass);
    }

    @Override // javassist.ClassPool
    protected CtClass getCached(String str) {
        int lastIndexOf;
        String str2;
        CtClass cachedLocally = getCachedLocally(str);
        if (cachedLocally == null) {
            ClassLoader classLoader0 = getClassLoader0();
            boolean z = false;
            if (classLoader0 != null) {
                if (str.lastIndexOf(36) < 0) {
                    str2 = str.replaceAll("[\\.]", "/") + ".class";
                } else {
                    str2 = str.substring(0, lastIndexOf).replaceAll("[\\.]", "/") + str.substring(lastIndexOf) + ".class";
                }
                if (classLoader0.getResource(str2) != null) {
                    z = true;
                }
            }
            if (!z) {
                Map<ClassLoader, ScopedClassPool> registeredCLs = this.repository.getRegisteredCLs();
                synchronized (registeredCLs) {
                    for (ScopedClassPool scopedClassPool : registeredCLs.values()) {
                        if (scopedClassPool.isUnloadedClassLoader()) {
                            this.repository.unregisterClassLoader(scopedClassPool.getClassLoader());
                        } else {
                            cachedLocally = scopedClassPool.getCachedLocally(str);
                            if (cachedLocally != null) {
                                return cachedLocally;
                            }
                        }
                    }
                }
            }
        }
        return cachedLocally;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // javassist.ClassPool
    public void cacheCtClass(String str, CtClass ctClass, boolean z) {
        if (z) {
            super.cacheCtClass(str, ctClass, z);
            return;
        }
        if (this.repository.isPrune()) {
            ctClass.prune();
        }
        this.softcache.put(str, ctClass);
    }

    public void lockInCache(CtClass ctClass) {
        super.cacheCtClass(ctClass.getName(), ctClass, false);
    }

    protected CtClass getCachedLocally(String str) {
        CtClass ctClass;
        CtClass ctClass2 = (CtClass) this.classes.get(str);
        if (ctClass2 != null) {
            return ctClass2;
        }
        synchronized (this.softcache) {
            ctClass = this.softcache.get(str);
        }
        return ctClass;
    }

    public synchronized CtClass getLocally(String str) throws NotFoundException {
        CtClass ctClass;
        this.softcache.remove(str);
        ctClass = (CtClass) this.classes.get(str);
        if (ctClass == null) {
            ctClass = createCtClass(str, true);
            if (ctClass == null) {
                throw new NotFoundException(str);
            }
            super.cacheCtClass(str, ctClass, false);
        }
        return ctClass;
    }

    @Override // javassist.ClassPool
    public Class<?> toClass(CtClass ctClass, ClassLoader classLoader, ProtectionDomain protectionDomain) throws CannotCompileException {
        lockInCache(ctClass);
        return super.toClass(ctClass, getClassLoader0(), protectionDomain);
    }
}
