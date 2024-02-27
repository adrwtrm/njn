package javassist.util.proxy;

import java.io.InvalidClassException;
import java.io.Serializable;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class RuntimeSupport {
    public static MethodHandler default_interceptor = new DefaultMethodHandler();

    /* loaded from: classes2.dex */
    static class DefaultMethodHandler implements MethodHandler, Serializable {
        private static final long serialVersionUID = 1;

        DefaultMethodHandler() {
        }

        @Override // javassist.util.proxy.MethodHandler
        public Object invoke(Object obj, Method method, Method method2, Object[] objArr) throws Exception {
            return method2.invoke(obj, objArr);
        }
    }

    public static void find2Methods(Class<?> cls, String str, String str2, int i, String str3, Method[] methodArr) {
        methodArr[i + 1] = str2 == null ? null : findMethod(cls, str2, str3);
        methodArr[i] = findSuperClassMethod(cls, str, str3);
    }

    @Deprecated
    public static void find2Methods(Object obj, String str, String str2, int i, String str3, Method[] methodArr) {
        methodArr[i + 1] = str2 == null ? null : findMethod(obj, str2, str3);
        methodArr[i] = findSuperMethod(obj, str, str3);
    }

    @Deprecated
    public static Method findMethod(Object obj, String str, String str2) {
        Method findMethod2 = findMethod2(obj.getClass(), str, str2);
        if (findMethod2 == null) {
            error(obj.getClass(), str, str2);
        }
        return findMethod2;
    }

    public static Method findMethod(Class<?> cls, String str, String str2) {
        Method findMethod2 = findMethod2(cls, str, str2);
        if (findMethod2 == null) {
            error(cls, str, str2);
        }
        return findMethod2;
    }

    public static Method findSuperMethod(Object obj, String str, String str2) {
        return findSuperClassMethod(obj.getClass(), str, str2);
    }

    public static Method findSuperClassMethod(Class<?> cls, String str, String str2) {
        Method findSuperMethod2 = findSuperMethod2(cls.getSuperclass(), str, str2);
        if (findSuperMethod2 == null) {
            findSuperMethod2 = searchInterfaces(cls, str, str2);
        }
        if (findSuperMethod2 == null) {
            error(cls, str, str2);
        }
        return findSuperMethod2;
    }

    private static void error(Class<?> cls, String str, String str2) {
        throw new RuntimeException("not found " + str + ":" + str2 + " in " + cls.getName());
    }

    private static Method findSuperMethod2(Class<?> cls, String str, String str2) {
        Method findSuperMethod2;
        Method findMethod2 = findMethod2(cls, str, str2);
        if (findMethod2 != null) {
            return findMethod2;
        }
        Class<? super Object> superclass = cls.getSuperclass();
        return (superclass == null || (findSuperMethod2 = findSuperMethod2(superclass, str, str2)) == null) ? searchInterfaces(cls, str, str2) : findSuperMethod2;
    }

    private static Method searchInterfaces(Class<?> cls, String str, String str2) {
        Method method = null;
        for (Class<?> cls2 : cls.getInterfaces()) {
            method = findSuperMethod2(cls2, str, str2);
            if (method != null) {
                return method;
            }
        }
        return method;
    }

    private static Method findMethod2(Class<?> cls, String str, String str2) {
        Method[] declaredMethods = SecurityActions.getDeclaredMethods(cls);
        int length = declaredMethods.length;
        for (int i = 0; i < length; i++) {
            if (declaredMethods[i].getName().equals(str) && makeDescriptor(declaredMethods[i]).equals(str2)) {
                return declaredMethods[i];
            }
        }
        return null;
    }

    public static String makeDescriptor(Method method) {
        return makeDescriptor(method.getParameterTypes(), method.getReturnType());
    }

    public static String makeDescriptor(Class<?>[] clsArr, Class<?> cls) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Class<?> cls2 : clsArr) {
            makeDesc(stringBuffer, cls2);
        }
        stringBuffer.append(')');
        if (cls != null) {
            makeDesc(stringBuffer, cls);
        }
        return stringBuffer.toString();
    }

    public static String makeDescriptor(String str, Class<?> cls) {
        StringBuffer stringBuffer = new StringBuffer(str);
        makeDesc(stringBuffer, cls);
        return stringBuffer.toString();
    }

    private static void makeDesc(StringBuffer stringBuffer, Class<?> cls) {
        if (cls.isArray()) {
            stringBuffer.append('[');
            makeDesc(stringBuffer, cls.getComponentType());
        } else if (cls.isPrimitive()) {
            if (cls == Void.TYPE) {
                stringBuffer.append('V');
            } else if (cls == Integer.TYPE) {
                stringBuffer.append('I');
            } else if (cls == Byte.TYPE) {
                stringBuffer.append('B');
            } else if (cls == Long.TYPE) {
                stringBuffer.append('J');
            } else if (cls == Double.TYPE) {
                stringBuffer.append('D');
            } else if (cls == Float.TYPE) {
                stringBuffer.append('F');
            } else if (cls == Character.TYPE) {
                stringBuffer.append('C');
            } else if (cls == Short.TYPE) {
                stringBuffer.append('S');
            } else if (cls == Boolean.TYPE) {
                stringBuffer.append('Z');
            } else {
                throw new RuntimeException("bad type: " + cls.getName());
            }
        } else {
            stringBuffer.append('L').append(cls.getName().replace('.', '/')).append(';');
        }
    }

    public static SerializedProxy makeSerializedProxy(Object obj) throws InvalidClassException {
        MethodHandler handler;
        Class<?> cls = obj.getClass();
        if (obj instanceof ProxyObject) {
            handler = ((ProxyObject) obj).getHandler();
        } else {
            handler = obj instanceof Proxy ? ProxyFactory.getHandler((Proxy) obj) : null;
        }
        return new SerializedProxy(cls, ProxyFactory.getFilterSignature(cls), handler);
    }
}
