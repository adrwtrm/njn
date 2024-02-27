package javassist.util.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/* loaded from: classes2.dex */
public class ProxyObjectInputStream extends ObjectInputStream {
    private ClassLoader loader;

    public ProxyObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        this.loader = contextClassLoader;
        if (contextClassLoader == null) {
            this.loader = ClassLoader.getSystemClassLoader();
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        if (classLoader != null) {
            this.loader = classLoader;
        } else {
            ClassLoader.getSystemClassLoader();
        }
    }

    @Override // java.io.ObjectInputStream
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        if (readBoolean()) {
            Class<?> loadClass = this.loader.loadClass((String) readObject());
            int readInt = readInt();
            Class<?>[] clsArr = new Class[readInt];
            for (int i = 0; i < readInt; i++) {
                clsArr[i] = this.loader.loadClass((String) readObject());
            }
            byte[] bArr = new byte[readInt()];
            read(bArr);
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setUseCache(true);
            proxyFactory.setUseWriteReplace(false);
            proxyFactory.setSuperclass(loadClass);
            proxyFactory.setInterfaces(clsArr);
            return ObjectStreamClass.lookup(proxyFactory.createClass(bArr));
        }
        return super.readClassDescriptor();
    }
}
