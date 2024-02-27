package javassist;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/* compiled from: ClassPoolTail.java */
/* loaded from: classes2.dex */
final class JarClassPath implements ClassPath {
    List<String> jarfileEntries;
    String jarfileURL;

    /* JADX INFO: Access modifiers changed from: package-private */
    public JarClassPath(String str) throws NotFoundException {
        JarFile jarFile = null;
        try {
            JarFile jarFile2 = new JarFile(str);
            try {
                this.jarfileEntries = new ArrayList();
                Iterator it = Collections.list(jarFile2.entries()).iterator();
                while (it.hasNext()) {
                    JarEntry jarEntry = (JarEntry) it.next();
                    if (jarEntry.getName().endsWith(".class")) {
                        this.jarfileEntries.add(jarEntry.getName());
                    }
                }
                this.jarfileURL = new File(str).getCanonicalFile().toURI().toURL().toString();
                try {
                    jarFile2.close();
                } catch (IOException unused) {
                }
            } catch (IOException unused2) {
                jarFile = jarFile2;
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (IOException unused3) {
                    }
                }
                throw new NotFoundException(str);
            } catch (Throwable th) {
                th = th;
                jarFile = jarFile2;
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (IOException unused5) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) throws NotFoundException {
        URL find = find(str);
        if (find != null) {
            try {
                if (ClassPool.cacheOpenedJarFile) {
                    return find.openConnection().getInputStream();
                }
                URLConnection openConnection = find.openConnection();
                openConnection.setUseCaches(false);
                return openConnection.getInputStream();
            } catch (IOException unused) {
                throw new NotFoundException("broken jar file?: " + str);
            }
        }
        return null;
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        String str2 = str.replace('.', '/') + ".class";
        if (this.jarfileEntries.contains(str2)) {
            try {
                return new URL(String.format("jar:%s!/%s", this.jarfileURL, str2));
            } catch (MalformedURLException unused) {
                return null;
            }
        }
        return null;
    }

    public String toString() {
        String str = this.jarfileURL;
        return str == null ? "<null>" : str.toString();
    }
}
