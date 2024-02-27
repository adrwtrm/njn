package javassist;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/* loaded from: classes2.dex */
public class ByteArrayClassPath implements ClassPath {
    protected byte[] classfile;
    protected String classname;

    public ByteArrayClassPath(String str, byte[] bArr) {
        this.classname = str;
        this.classfile = bArr;
    }

    public String toString() {
        return "byte[]:" + this.classname;
    }

    @Override // javassist.ClassPath
    public InputStream openClassfile(String str) {
        if (this.classname.equals(str)) {
            return new ByteArrayInputStream(this.classfile);
        }
        return null;
    }

    @Override // javassist.ClassPath
    public URL find(String str) {
        if (this.classname.equals(str)) {
            try {
                return new URL((URL) null, "file:/ByteArrayClassPath/" + (str.replace('.', '/') + ".class"), new BytecodeURLStreamHandler());
            } catch (MalformedURLException unused) {
            }
        }
        return null;
    }

    /* loaded from: classes2.dex */
    private class BytecodeURLStreamHandler extends URLStreamHandler {
        private BytecodeURLStreamHandler() {
        }

        @Override // java.net.URLStreamHandler
        protected URLConnection openConnection(URL url) {
            return new BytecodeURLConnection(url);
        }
    }

    /* loaded from: classes2.dex */
    private class BytecodeURLConnection extends URLConnection {
        @Override // java.net.URLConnection
        public void connect() throws IOException {
        }

        protected BytecodeURLConnection(URL url) {
            super(url);
        }

        @Override // java.net.URLConnection
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(ByteArrayClassPath.this.classfile);
        }

        @Override // java.net.URLConnection
        public int getContentLength() {
            return ByteArrayClassPath.this.classfile.length;
        }
    }
}
