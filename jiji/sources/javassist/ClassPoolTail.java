package javassist;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javassist.bytecode.ClassFile;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class ClassPoolTail {
    protected ClassPathList pathList = null;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[class path: ");
        for (ClassPathList classPathList = this.pathList; classPathList != null; classPathList = classPathList.next) {
            stringBuffer.append(classPathList.path.toString());
            stringBuffer.append(File.pathSeparatorChar);
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public synchronized ClassPath insertClassPath(ClassPath classPath) {
        this.pathList = new ClassPathList(classPath, this.pathList);
        return classPath;
    }

    public synchronized ClassPath appendClassPath(ClassPath classPath) {
        ClassPathList classPathList = new ClassPathList(classPath, null);
        ClassPathList classPathList2 = this.pathList;
        if (classPathList2 == null) {
            this.pathList = classPathList;
        } else {
            while (classPathList2.next != null) {
                classPathList2 = classPathList2.next;
            }
            classPathList2.next = classPathList;
        }
        return classPath;
    }

    public synchronized void removeClassPath(ClassPath classPath) {
        ClassPathList classPathList = this.pathList;
        if (classPathList != null) {
            if (classPathList.path == classPath) {
                this.pathList = classPathList.next;
            } else {
                while (classPathList.next != null) {
                    if (classPathList.next.path == classPath) {
                        classPathList.next = classPathList.next.next;
                    } else {
                        classPathList = classPathList.next;
                    }
                }
            }
        }
    }

    public ClassPath appendSystemPath() {
        if (ClassFile.MAJOR_VERSION < 53) {
            return appendClassPath(new ClassClassPath());
        }
        return appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
    }

    public ClassPath insertClassPath(String str) throws NotFoundException {
        return insertClassPath(makePathObject(str));
    }

    public ClassPath appendClassPath(String str) throws NotFoundException {
        return appendClassPath(makePathObject(str));
    }

    private static ClassPath makePathObject(String str) throws NotFoundException {
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(".jar") || lowerCase.endsWith(".zip")) {
            return new JarClassPath(str);
        }
        int length = str.length();
        if (length > 2 && str.charAt(length - 1) == '*') {
            int i = length - 2;
            if (str.charAt(i) == '/' || str.charAt(i) == File.separatorChar) {
                return new JarDirClassPath(str.substring(0, i));
            }
        }
        return new DirClassPath(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeClassfile(String str, OutputStream outputStream) throws NotFoundException, IOException, CannotCompileException {
        InputStream openClassfile = openClassfile(str);
        if (openClassfile == null) {
            throw new NotFoundException(str);
        }
        try {
            copyStream(openClassfile, outputStream);
        } finally {
            openClassfile.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public InputStream openClassfile(String str) throws NotFoundException {
        NotFoundException notFoundException = null;
        InputStream inputStream = null;
        for (ClassPathList classPathList = this.pathList; classPathList != null; classPathList = classPathList.next) {
            try {
                inputStream = classPathList.path.openClassfile(str);
            } catch (NotFoundException e) {
                if (notFoundException == null) {
                    notFoundException = e;
                }
            }
            if (inputStream != null) {
                return inputStream;
            }
        }
        if (notFoundException == null) {
            return null;
        }
        throw notFoundException;
    }

    public URL find(String str) {
        for (ClassPathList classPathList = this.pathList; classPathList != null; classPathList = classPathList.next) {
            URL find = classPathList.path.find(str);
            if (find != null) {
                return find;
            }
        }
        return null;
    }

    public static byte[] readStream(InputStream inputStream) throws IOException {
        byte[][] bArr = new byte[8];
        int i = 4096;
        for (int i2 = 0; i2 < 8; i2++) {
            bArr[i2] = new byte[i];
            int i3 = 0;
            do {
                int read = inputStream.read(bArr[i2], i3, i - i3);
                if (read < 0) {
                    byte[] bArr2 = new byte[(i - 4096) + i3];
                    int i4 = 0;
                    for (int i5 = 0; i5 < i2; i5++) {
                        System.arraycopy(bArr[i5], 0, bArr2, i4, i4 + 4096);
                        i4 = i4 + i4 + 4096;
                    }
                    System.arraycopy(bArr[i2], 0, bArr2, i4, i3);
                    return bArr2;
                }
                i3 += read;
            } while (i3 < i);
            i *= 2;
        }
        throw new IOException("too much data");
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        int i = 4096;
        byte[] bArr = null;
        for (int i2 = 0; i2 < 64; i2++) {
            if (i2 < 8) {
                i *= 2;
                bArr = new byte[i];
            }
            int i3 = 0;
            do {
                int read = inputStream.read(bArr, i3, i - i3);
                if (read < 0) {
                    outputStream.write(bArr, 0, i3);
                    return;
                }
                i3 += read;
            } while (i3 < i);
            outputStream.write(bArr);
        }
        throw new IOException("too much data");
    }
}
