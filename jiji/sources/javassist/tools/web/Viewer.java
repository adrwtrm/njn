package javassist.tools.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes2.dex */
public class Viewer extends ClassLoader {
    private int port;
    private String server;

    public static void main(String[] strArr) throws Throwable {
        if (strArr.length >= 3) {
            Viewer viewer = new Viewer(strArr[0], Integer.parseInt(strArr[1]));
            String[] strArr2 = new String[strArr.length - 3];
            System.arraycopy(strArr, 3, strArr2, 0, strArr.length - 3);
            viewer.run(strArr[2], strArr2);
            return;
        }
        System.err.println("Usage: java javassist.tools.web.Viewer <host> <port> class [args ...]");
    }

    public Viewer(String str, int i) {
        this.server = str;
        this.port = i;
    }

    public String getServer() {
        return this.server;
    }

    public int getPort() {
        return this.port;
    }

    public void run(String str, String[] strArr) throws Throwable {
        try {
            loadClass(str).getDeclaredMethod("main", String[].class).invoke(null, strArr);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    @Override // java.lang.ClassLoader
    protected synchronized Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        Class<?> findLoadedClass;
        findLoadedClass = findLoadedClass(str);
        if (findLoadedClass == null) {
            findLoadedClass = findClass(str);
        }
        if (findLoadedClass == null) {
            throw new ClassNotFoundException(str);
        }
        if (z) {
            resolveClass(findLoadedClass);
        }
        return findLoadedClass;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> findSystemClass = (str.startsWith("java.") || str.startsWith("javax.") || str.equals("javassist.tools.web.Viewer")) ? findSystemClass(str) : null;
        if (findSystemClass == null) {
            try {
                byte[] fetchClass = fetchClass(str);
                return fetchClass != null ? defineClass(str, fetchClass, 0, fetchClass.length) : findSystemClass;
            } catch (Exception unused) {
                return findSystemClass;
            }
        }
        return findSystemClass;
    }

    protected byte[] fetchClass(String str) throws Exception {
        byte[] bArr;
        URLConnection openConnection = new URL("http", this.server, this.port, "/" + str.replace('.', '/') + ".class").openConnection();
        openConnection.connect();
        int contentLength = openConnection.getContentLength();
        InputStream inputStream = openConnection.getInputStream();
        if (contentLength <= 0) {
            bArr = readStream(inputStream);
        } else {
            byte[] bArr2 = new byte[contentLength];
            int i = 0;
            do {
                int read = inputStream.read(bArr2, i, contentLength - i);
                if (read < 0) {
                    inputStream.close();
                    throw new IOException("the stream was closed: " + str);
                }
                i += read;
            } while (i < contentLength);
            bArr = bArr2;
        }
        inputStream.close();
        return bArr;
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4096];
        int i = 0;
        int i2 = 0;
        do {
            i += i2;
            if (bArr.length - i <= 0) {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
            i2 = inputStream.read(bArr, i, bArr.length - i);
        } while (i2 >= 0);
        byte[] bArr3 = new byte[i];
        System.arraycopy(bArr, 0, bArr3, 0, i);
        return bArr3;
    }
}
