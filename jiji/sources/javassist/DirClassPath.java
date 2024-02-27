package javassist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/* compiled from: ClassPoolTail.java */
/* loaded from: classes2.dex */
final class DirClassPath implements ClassPath {
    String directory;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DirClassPath(String str) {
        this.directory = str;
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) {
        try {
            char c = File.separatorChar;
            return new FileInputStream((this.directory + c + str.replace('.', c) + ".class").toString());
        } catch (FileNotFoundException | SecurityException unused) {
            return null;
        }
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        char c = File.separatorChar;
        File file = new File(this.directory + c + str.replace('.', c) + ".class");
        if (file.exists()) {
            try {
                return file.getCanonicalFile().toURI().toURL();
            } catch (MalformedURLException | IOException unused) {
                return null;
            }
        }
        return null;
    }

    public String toString() {
        return this.directory;
    }
}
