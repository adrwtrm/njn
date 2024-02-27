package javassist.scopedpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import javassist.ClassPool;
import javassist.LoaderClassPath;

/* loaded from: classes2.dex */
public class ScopedClassPoolRepositoryImpl implements ScopedClassPoolRepository {
    private static final ScopedClassPoolRepositoryImpl instance = new ScopedClassPoolRepositoryImpl();
    boolean pruneWhenCached;
    private boolean prune = true;
    protected Map<ClassLoader, ScopedClassPool> registeredCLs = Collections.synchronizedMap(new WeakHashMap());
    protected ScopedClassPoolFactory factory = new ScopedClassPoolFactoryImpl();
    protected ClassPool classpool = ClassPool.getDefault();

    public void insertDelegate(ScopedClassPoolRepository scopedClassPoolRepository) {
    }

    public static ScopedClassPoolRepository getInstance() {
        return instance;
    }

    private ScopedClassPoolRepositoryImpl() {
        this.classpool.insertClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public boolean isPrune() {
        return this.prune;
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public void setPrune(boolean z) {
        this.prune = z;
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public ScopedClassPool createScopedClassPool(ClassLoader classLoader, ClassPool classPool) {
        return this.factory.create(classLoader, classPool, this);
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public ClassPool findClassPool(ClassLoader classLoader) {
        if (classLoader == null) {
            return registerClassLoader(ClassLoader.getSystemClassLoader());
        }
        return registerClassLoader(classLoader);
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public ClassPool registerClassLoader(ClassLoader classLoader) {
        synchronized (this.registeredCLs) {
            if (this.registeredCLs.containsKey(classLoader)) {
                return this.registeredCLs.get(classLoader);
            }
            ScopedClassPool createScopedClassPool = createScopedClassPool(classLoader, this.classpool);
            this.registeredCLs.put(classLoader, createScopedClassPool);
            return createScopedClassPool;
        }
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public Map<ClassLoader, ScopedClassPool> getRegisteredCLs() {
        clearUnregisteredClassLoaders();
        return this.registeredCLs;
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public void clearUnregisteredClassLoaders() {
        synchronized (this.registeredCLs) {
            ArrayList<ClassLoader> arrayList = null;
            for (Map.Entry<ClassLoader, ScopedClassPool> entry : this.registeredCLs.entrySet()) {
                if (entry.getValue().isUnloadedClassLoader()) {
                    ClassLoader classLoader = entry.getValue().getClassLoader();
                    if (classLoader != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(classLoader);
                    }
                    this.registeredCLs.remove(entry.getKey());
                }
            }
            if (arrayList != null) {
                for (ClassLoader classLoader2 : arrayList) {
                    unregisterClassLoader(classLoader2);
                }
            }
        }
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public void unregisterClassLoader(ClassLoader classLoader) {
        synchronized (this.registeredCLs) {
            ScopedClassPool remove = this.registeredCLs.remove(classLoader);
            if (remove != null) {
                remove.close();
            }
        }
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public void setClassPoolFactory(ScopedClassPoolFactory scopedClassPoolFactory) {
        this.factory = scopedClassPoolFactory;
    }

    @Override // javassist.scopedpool.ScopedClassPoolRepository
    public ScopedClassPoolFactory getClassPoolFactory() {
        return this.factory;
    }
}
