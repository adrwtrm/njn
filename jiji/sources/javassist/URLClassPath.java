package javassist;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes2.dex */
public class URLClassPath implements ClassPath {
    protected String directory;
    protected String hostname;
    protected String packageName;
    protected int port;

    public URLClassPath(String str, int i, String str2, String str3) {
        this.hostname = str;
        this.port = i;
        this.directory = str2;
        this.packageName = str3;
    }

    public String toString() {
        return this.hostname + ":" + this.port + this.directory;
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) {
        try {
            URLConnection openClassfile0 = openClassfile0(str);
            if (openClassfile0 != null) {
                return openClassfile0.getInputStream();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    private URLConnection openClassfile0(String str) throws IOException {
        String str2 = this.packageName;
        if (str2 == null || str.startsWith(str2)) {
            return fetchClass0(this.hostname, this.port, this.directory + str.replace('.', '/') + ".class");
        }
        return null;
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        try {
            URLConnection openClassfile0 = openClassfile0(str);
            InputStream inputStream = openClassfile0.getInputStream();
            if (inputStream != null) {
                inputStream.close();
                return openClassfile0.getURL();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    public static byte[] fetchClass(String str, int i, String str2, String str3) throws IOException {
        byte[] bArr;
        URLConnection fetchClass0 = fetchClass0(str, i, str2 + str3.replace('.', '/') + ".class");
        int contentLength = fetchClass0.getContentLength();
        InputStream inputStream = fetchClass0.getInputStream();
        try {
            if (contentLength <= 0) {
                bArr = ClassPoolTail.readStream(inputStream);
            } else {
                byte[] bArr2 = new byte[contentLength];
                int i2 = 0;
                do {
                    int read = inputStream.read(bArr2, i2, contentLength - i2);
                    if (read < 0) {
                        throw new IOException("the stream was closed: " + str3);
                    }
                    i2 += read;
                } while (i2 < contentLength);
                bArr = bArr2;
            }
            return bArr;
        } finally {
            inputStream.close();
        }
    }

    private static URLConnection fetchClass0(String str, int i, String str2) throws IOException {
        try {
            URLConnection openConnection = new URL("http", str, i, str2).openConnection();
            openConnection.connect();
            return openConnection;
        } catch (MalformedURLException unused) {
            throw new IOException("invalid URL?");
        }
    }
}
