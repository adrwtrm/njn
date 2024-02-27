package javassist.scopedpool;

import javassist.ClassPool;

/* loaded from: classes2.dex */
public class ScopedClassPoolFactoryImpl implements ScopedClassPoolFactory {
    @Override // javassist.scopedpool.ScopedClassPoolFactory
    public ScopedClassPool create(ClassLoader classLoader, ClassPool classPool, ScopedClassPoolRepository scopedClassPoolRepository) {
        return new ScopedClassPool(classLoader, classPool, scopedClassPoolRepository, false);
    }

    @Override // javassist.scopedpool.ScopedClassPoolFactory
    public ScopedClassPool create(ClassPool classPool, ScopedClassPoolRepository scopedClassPoolRepository) {
        return new ScopedClassPool(null, classPool, scopedClassPoolRepository, true);
    }
}
