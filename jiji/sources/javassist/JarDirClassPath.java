package javassist;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;

/* compiled from: ClassPoolTail.java */
/* loaded from: classes2.dex */
final class JarDirClassPath implements ClassPath {
    JarClassPath[] jars;

    /* JADX INFO: Access modifiers changed from: package-private */
    public JarDirClassPath(String str) throws NotFoundException {
        File[] listFiles = new File(str).listFiles(new FilenameFilter() { // from class: javassist.JarDirClassPath.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str2) {
                String lowerCase = str2.toLowerCase();
                return lowerCase.endsWith(".jar") || lowerCase.endsWith(".zip");
            }
        });
        if (listFiles != null) {
            this.jars = new JarClassPath[listFiles.length];
            for (int i = 0; i < listFiles.length; i++) {
                this.jars[i] = new JarClassPath(listFiles[i].getPath());
            }
        }
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) throws NotFoundException {
        if (this.jars == null) {
            return null;
        }
        int i = 0;
        while (true) {
            JarClassPath[] jarClassPathArr = this.jars;
            if (i >= jarClassPathArr.length) {
                return null;
            }
            InputStream openClassfile = jarClassPathArr[i].openClassfile(str);
            if (openClassfile != null) {
                return openClassfile;
            }
            i++;
        }
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        if (this.jars == null) {
            return null;
        }
        int i = 0;
        while (true) {
            JarClassPath[] jarClassPathArr = this.jars;
            if (i >= jarClassPathArr.length) {
                return null;
            }
            URL find = jarClassPathArr[i].find(str);
            if (find != null) {
                return find;
            }
            i++;
        }
    }
}
