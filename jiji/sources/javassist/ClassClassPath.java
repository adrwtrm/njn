package javassist;

import java.io.InputStream;
import java.net.URL;

/* loaded from: classes2.dex */
public class ClassClassPath implements ClassPath {
    private Class<?> thisClass;

    public ClassClassPath(Class<?> cls) {
        this.thisClass = cls;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ClassClassPath() {
        this(Object.class);
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) throws NotFoundException {
        return this.thisClass.getResourceAsStream("/" + str.replace('.', '/') + ".class");
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        return this.thisClass.getResource("/" + str.replace('.', '/') + ".class");
    }

    public String toString() {
        return this.thisClass.getName() + ".class";
    }
}
