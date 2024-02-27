package javassist.tools.reflect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ClassMetaobject implements Serializable {
    static final String methodPrefix = "_m_";
    static final int methodPrefixLen = 3;
    private static final long serialVersionUID = 1;
    public static boolean useContextClassLoader = false;
    private Constructor<?>[] constructors;
    private Class<?> javaClass;
    private Method[] methods;

    public ClassMetaobject(String[] strArr) {
        try {
            Class<?> classObject = getClassObject(strArr[0]);
            this.javaClass = classObject;
            this.constructors = classObject.getConstructors();
            this.methods = null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("not found: " + strArr[0] + ", useContextClassLoader: " + Boolean.toString(useContextClassLoader), e);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this.javaClass.getName());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Class<?> classObject = getClassObject(objectInputStream.readUTF());
        this.javaClass = classObject;
        this.constructors = classObject.getConstructors();
        this.methods = null;
    }

    private Class<?> getClassObject(String str) throws ClassNotFoundException {
        if (useContextClassLoader) {
            return Thread.currentThread().getContextClassLoader().loadClass(str);
        }
        return Class.forName(str);
    }

    public final Class<?> getJavaClass() {
        return this.javaClass;
    }

    public final String getName() {
        return this.javaClass.getName();
    }

    public final boolean isInstance(Object obj) {
        return this.javaClass.isInstance(obj);
    }

    public final Object newInstance(Object[] objArr) throws CannotCreateException {
        for (int i = 0; i < this.constructors.length; i++) {
            try {
                return this.constructors[i].newInstance(objArr);
            } catch (IllegalAccessException e) {
                throw new CannotCreateException(e);
            } catch (IllegalArgumentException unused) {
            } catch (InstantiationException e2) {
                throw new CannotCreateException(e2);
            } catch (InvocationTargetException e3) {
                throw new CannotCreateException(e3);
            }
        }
        throw new CannotCreateException("no constructor matches");
    }

    public Object trapFieldRead(String str) {
        try {
            return getJavaClass().getField(str).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.toString());
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public void trapFieldWrite(String str, Object obj) {
        try {
            getJavaClass().getField(str).set(null, obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.toString());
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public static Object invoke(Object obj, int i, Object[] objArr) throws Throwable {
        Method[] methods = obj.getClass().getMethods();
        int length = methods.length;
        String str = methodPrefix + i;
        for (int i2 = 0; i2 < length; i2++) {
            if (methods[i2].getName().startsWith(str)) {
                try {
                    return methods[i2].invoke(obj, objArr);
                } catch (IllegalAccessException e) {
                    throw new CannotInvokeException(e);
                } catch (InvocationTargetException e2) {
                    throw e2.getTargetException();
                }
            }
        }
        throw new CannotInvokeException("cannot find a method");
    }

    public Object trapMethodcall(int i, Object[] objArr) throws Throwable {
        try {
            return getReflectiveMethods()[i].invoke(null, objArr);
        } catch (IllegalAccessException e) {
            throw new CannotInvokeException(e);
        } catch (InvocationTargetException e2) {
            throw e2.getTargetException();
        }
    }

    public final Method[] getReflectiveMethods() {
        Method[] methodArr = this.methods;
        if (methodArr != null) {
            return methodArr;
        }
        Method[] declaredMethods = getJavaClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int[] iArr = new int[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            String name = declaredMethods[i2].getName();
            if (name.startsWith(methodPrefix)) {
                int i3 = 3;
                int i4 = 0;
                while (true) {
                    char charAt = name.charAt(i3);
                    if ('0' > charAt || charAt > '9') {
                        break;
                    }
                    i4 = ((i4 * 10) + charAt) - 48;
                    i3++;
                }
                int i5 = i4 + 1;
                iArr[i2] = i5;
                if (i5 > i) {
                    i = i5;
                }
            }
        }
        this.methods = new Method[i];
        for (int i6 = 0; i6 < length; i6++) {
            int i7 = iArr[i6];
            if (i7 > 0) {
                this.methods[i7 - 1] = declaredMethods[i6];
            }
        }
        return this.methods;
    }

    public final Method getMethod(int i) {
        return getReflectiveMethods()[i];
    }

    public final String getMethodName(int i) {
        int i2;
        String name = getReflectiveMethods()[i].getName();
        int i3 = 3;
        while (true) {
            i2 = i3 + 1;
            char charAt = name.charAt(i3);
            if (charAt < '0' || '9' < charAt) {
                break;
            }
            i3 = i2;
        }
        return name.substring(i2);
    }

    public final Class<?>[] getParameterTypes(int i) {
        return getReflectiveMethods()[i].getParameterTypes();
    }

    public final Class<?> getReturnType(int i) {
        return getReflectiveMethods()[i].getReturnType();
    }

    public final int getMethodIndex(String str, Class<?>[] clsArr) throws NoSuchMethodException {
        Method[] reflectiveMethods = getReflectiveMethods();
        for (int i = 0; i < reflectiveMethods.length; i++) {
            if (reflectiveMethods[i] != null && getMethodName(i).equals(str) && Arrays.equals(clsArr, reflectiveMethods[i].getParameterTypes())) {
                return i;
            }
        }
        throw new NoSuchMethodException("Method " + str + " not found");
    }
}
