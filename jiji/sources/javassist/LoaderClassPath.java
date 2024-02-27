package javassist;

import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;

/* loaded from: classes2.dex */
public class LoaderClassPath implements ClassPath {
    private Reference<ClassLoader> clref;

    public LoaderClassPath(ClassLoader classLoader) {
        this.clref = new WeakReference(classLoader);
    }

    public String toString() {
        return this.clref.get() == null ? "<null>" : this.clref.get().toString();
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) throws NotFoundException {
        String str2 = str.replace('.', '/') + ".class";
        ClassLoader classLoader = this.clref.get();
        if (classLoader == null) {
            return null;
        }
        return classLoader.getResourceAsStream(str2);
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        String str2 = str.replace('.', '/') + ".class";
        ClassLoader classLoader = this.clref.get();
        if (classLoader == null) {
            return null;
        }
        return classLoader.getResource(str2);
    }
}
