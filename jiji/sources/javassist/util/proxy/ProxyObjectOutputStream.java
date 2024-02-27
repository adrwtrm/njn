package javassist.util.proxy;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class ProxyObjectOutputStream extends ObjectOutputStream {
    public ProxyObjectOutputStream(OutputStream outputStream) throws IOException {
        super(outputStream);
    }

    @Override // java.io.ObjectOutputStream
    protected void writeClassDescriptor(ObjectStreamClass objectStreamClass) throws IOException {
        Class<?> forClass = objectStreamClass.forClass();
        if (ProxyFactory.isProxyClass(forClass)) {
            writeBoolean(true);
            Class<? super Object> superclass = forClass.getSuperclass();
            Class<?>[] interfaces = forClass.getInterfaces();
            byte[] filterSignature = ProxyFactory.getFilterSignature(forClass);
            writeObject(superclass.getName());
            writeInt(interfaces.length - 1);
            for (Class<?> cls : interfaces) {
                if (cls != ProxyObject.class && cls != Proxy.class) {
                    writeObject(cls.getName());
                }
            }
            writeInt(filterSignature.length);
            write(filterSignature);
            return;
        }
        writeBoolean(false);
        super.writeClassDescriptor(objectStreamClass);
    }
}
