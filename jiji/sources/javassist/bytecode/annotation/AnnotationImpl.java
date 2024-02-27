package javassist.bytecode.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationDefaultAttribute;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public class AnnotationImpl implements InvocationHandler {
    private static final String JDK_ANNOTATION_CLASS_NAME = "java.lang.annotation.Annotation";
    private static Method JDK_ANNOTATION_TYPE_METHOD;
    private Annotation annotation;
    private transient Class<?> annotationType;
    private transient int cachedHashCode = Integer.MIN_VALUE;
    private ClassLoader classLoader;
    private ClassPool pool;

    static {
        try {
            Class[] clsArr = null;
            JDK_ANNOTATION_TYPE_METHOD = Class.forName(JDK_ANNOTATION_CLASS_NAME).getMethod("annotationType", null);
        } catch (Exception unused) {
        }
    }

    public static Object make(ClassLoader classLoader, Class<?> cls, ClassPool classPool, Annotation annotation) throws IllegalArgumentException {
        return Proxy.newProxyInstance(classLoader, new Class[]{cls}, new AnnotationImpl(annotation, classPool, classLoader));
    }

    private AnnotationImpl(Annotation annotation, ClassPool classPool, ClassLoader classLoader) {
        this.annotation = annotation;
        this.pool = classPool;
        this.classLoader = classLoader;
    }

    public String getTypeName() {
        return this.annotation.getTypeName();
    }

    private Class<?> getAnnotationType() {
        if (this.annotationType == null) {
            String typeName = this.annotation.getTypeName();
            try {
                this.annotationType = this.classLoader.loadClass(typeName);
            } catch (ClassNotFoundException e) {
                NoClassDefFoundError noClassDefFoundError = new NoClassDefFoundError("Error loading annotation class: " + typeName);
                noClassDefFoundError.setStackTrace(e.getStackTrace());
                throw noClassDefFoundError;
            }
        }
        return this.annotationType;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        String name = method.getName();
        if (Object.class == method.getDeclaringClass()) {
            if ("equals".equals(name)) {
                return Boolean.valueOf(checkEquals(objArr[0]));
            }
            if ("toString".equals(name)) {
                return this.annotation.toString();
            }
            if ("hashCode".equals(name)) {
                return Integer.valueOf(hashCode());
            }
        } else if ("annotationType".equals(name) && method.getParameterTypes().length == 0) {
            return getAnnotationType();
        }
        MemberValue memberValue = this.annotation.getMemberValue(name);
        if (memberValue == null) {
            return getDefault(name, method);
        }
        return memberValue.getValue(this.classLoader, this.pool, method);
    }

    private Object getDefault(String str, Method method) throws ClassNotFoundException, RuntimeException {
        AnnotationDefaultAttribute annotationDefaultAttribute;
        String typeName = this.annotation.getTypeName();
        ClassPool classPool = this.pool;
        if (classPool != null) {
            try {
                MethodInfo method2 = classPool.get(typeName).getClassFile2().getMethod(str);
                if (method2 != null && (annotationDefaultAttribute = (AnnotationDefaultAttribute) method2.getAttribute(AnnotationDefaultAttribute.tag)) != null) {
                    return annotationDefaultAttribute.getDefaultValue().getValue(this.classLoader, this.pool, method);
                }
            } catch (NotFoundException unused) {
                throw new RuntimeException("cannot find a class file: " + typeName);
            }
        }
        throw new RuntimeException("no default value: " + typeName + "." + str + "()");
    }

    public int hashCode() {
        Object value;
        int i;
        if (this.cachedHashCode == Integer.MIN_VALUE) {
            getAnnotationType();
            Method[] declaredMethods = this.annotationType.getDeclaredMethods();
            int i2 = 0;
            for (int i3 = 0; i3 < declaredMethods.length; i3++) {
                String name = declaredMethods[i3].getName();
                MemberValue memberValue = this.annotation.getMemberValue(name);
                if (memberValue != null) {
                    try {
                        value = memberValue.getValue(this.classLoader, this.pool, declaredMethods[i3]);
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new RuntimeException("Error retrieving value " + name + " for annotation " + this.annotation.getTypeName(), e2);
                    }
                } else {
                    value = null;
                }
                if (value == null) {
                    value = getDefault(name, declaredMethods[i3]);
                }
                if (value == null) {
                    i = 0;
                } else if (value.getClass().isArray()) {
                    i = arrayHashCode(value);
                } else {
                    i = value.hashCode();
                }
                i2 += (name.hashCode() * 127) ^ i;
            }
            this.cachedHashCode = i2;
        }
        return this.cachedHashCode;
    }

    private boolean checkEquals(Object obj) throws Exception {
        Object value;
        if (obj == null) {
            return false;
        }
        if (obj instanceof Proxy) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(obj);
            if (invocationHandler instanceof AnnotationImpl) {
                return this.annotation.equals(((AnnotationImpl) invocationHandler).annotation);
            }
        }
        if (getAnnotationType().equals((Class) JDK_ANNOTATION_TYPE_METHOD.invoke(obj, new Object[0]))) {
            Method[] declaredMethods = this.annotationType.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                String name = declaredMethods[i].getName();
                MemberValue memberValue = this.annotation.getMemberValue(name);
                if (memberValue != null) {
                    try {
                        value = memberValue.getValue(this.classLoader, this.pool, declaredMethods[i]);
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new RuntimeException("Error retrieving value " + name + " for annotation " + this.annotation.getTypeName(), e2);
                    }
                } else {
                    value = null;
                }
                if (value == null) {
                    value = getDefault(name, declaredMethods[i]);
                }
                Object invoke = declaredMethods[i].invoke(obj, new Object[0]);
                if (value == null && invoke != null) {
                    return false;
                }
                if (value != null && !value.equals(invoke)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static int arrayHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        Object[] objArr = (Object[]) obj;
        int i = 1;
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj2 = objArr[i2];
            i = (i * 31) + (obj2 != null ? obj2.hashCode() : 0);
        }
        return i;
    }
}
