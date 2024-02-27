package javassist.util.proxy;

import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/* loaded from: classes2.dex */
class SerializedProxy implements Serializable {
    private static final long serialVersionUID = 1;
    private byte[] filterSignature;
    private MethodHandler handler;
    private String[] interfaces;
    private String superClass;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SerializedProxy(Class<?> cls, byte[] bArr, MethodHandler methodHandler) {
        this.filterSignature = bArr;
        this.handler = methodHandler;
        this.superClass = cls.getSuperclass().getName();
        Class<?>[] interfaces = cls.getInterfaces();
        int length = interfaces.length;
        this.interfaces = new String[length - 1];
        String name = ProxyObject.class.getName();
        String name2 = Proxy.class.getName();
        for (int i = 0; i < length; i++) {
            String name3 = interfaces[i].getName();
            if (!name3.equals(name) && !name3.equals(name2)) {
                this.interfaces[i] = name3;
            }
        }
    }

    protected Class<?> loadClass(final String str) throws ClassNotFoundException {
        try {
            return (Class) AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>() { // from class: javassist.util.proxy.SerializedProxy.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedExceptionAction
                public Class<?> run() throws Exception {
                    return Class.forName(str, true, Thread.currentThread().getContextClassLoader());
                }
            });
        } catch (PrivilegedActionException e) {
            throw new RuntimeException("cannot load the class: " + str, e.getException());
        }
    }

    Object readResolve() throws ObjectStreamException {
        try {
            int length = this.interfaces.length;
            Class<?>[] clsArr = new Class[length];
            for (int i = 0; i < length; i++) {
                clsArr[i] = loadClass(this.interfaces[i]);
            }
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setSuperclass(loadClass(this.superClass));
            proxyFactory.setInterfaces(clsArr);
            Proxy proxy = (Proxy) proxyFactory.createClass(this.filterSignature).getConstructor(new Class[0]).newInstance(new Object[0]);
            proxy.setHandler(this.handler);
            return proxy;
        } catch (ClassNotFoundException e) {
            throw new InvalidClassException(e.getMessage());
        } catch (IllegalAccessException e2) {
            throw new InvalidClassException(e2.getMessage());
        } catch (InstantiationException e3) {
            throw new InvalidObjectException(e3.getMessage());
        } catch (NoSuchMethodException e4) {
            throw new InvalidClassException(e4.getMessage());
        } catch (InvocationTargetException e5) {
            throw new InvalidClassException(e5.getMessage());
        }
    }
}
