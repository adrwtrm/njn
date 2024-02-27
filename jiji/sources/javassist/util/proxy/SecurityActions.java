package javassist.util.proxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javassist.bytecode.ClassFile;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SecurityActions extends SecurityManager {
    public static final SecurityActions stack = new SecurityActions();

    SecurityActions() {
    }

    public Class<?> getCallerClass() {
        return getClassContext()[2];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Method[] getDeclaredMethods(final Class<?> cls) {
        if (System.getSecurityManager() == null) {
            return cls.getDeclaredMethods();
        }
        return (Method[]) AccessController.doPrivileged(new PrivilegedAction<Method[]>() { // from class: javassist.util.proxy.SecurityActions.1
            @Override // java.security.PrivilegedAction
            public Method[] run() {
                return cls.getDeclaredMethods();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Constructor<?>[] getDeclaredConstructors(final Class<?> cls) {
        if (System.getSecurityManager() == null) {
            return cls.getDeclaredConstructors();
        }
        return (Constructor[]) AccessController.doPrivileged(new PrivilegedAction<Constructor<?>[]>() { // from class: javassist.util.proxy.SecurityActions.2
            @Override // java.security.PrivilegedAction
            public Constructor<?>[] run() {
                return cls.getDeclaredConstructors();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MethodHandle getMethodHandle(final Class<?> cls, final String str, final Class<?>[] clsArr) throws NoSuchMethodException {
        try {
            return (MethodHandle) AccessController.doPrivileged(new PrivilegedExceptionAction<MethodHandle>() { // from class: javassist.util.proxy.SecurityActions.3
                @Override // java.security.PrivilegedExceptionAction
                public MethodHandle run() throws IllegalAccessException, NoSuchMethodException, SecurityException {
                    Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                    declaredMethod.setAccessible(true);
                    MethodHandle unreflect = MethodHandles.lookup().unreflect(declaredMethod);
                    declaredMethod.setAccessible(false);
                    return unreflect;
                }
            });
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw ((NoSuchMethodException) e.getCause());
            }
            throw new RuntimeException(e.getCause());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Method getDeclaredMethod(final Class<?> cls, final String str, final Class<?>[] clsArr) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return cls.getDeclaredMethod(str, clsArr);
        }
        try {
            return (Method) AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() { // from class: javassist.util.proxy.SecurityActions.4
                @Override // java.security.PrivilegedExceptionAction
                public Method run() throws Exception {
                    return cls.getDeclaredMethod(str, clsArr);
                }
            });
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw ((NoSuchMethodException) e.getCause());
            }
            throw new RuntimeException(e.getCause());
        }
    }

    static Constructor<?> getDeclaredConstructor(final Class<?> cls, final Class<?>[] clsArr) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return cls.getDeclaredConstructor(clsArr);
        }
        try {
            return (Constructor) AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>>() { // from class: javassist.util.proxy.SecurityActions.5
                @Override // java.security.PrivilegedExceptionAction
                public Constructor<?> run() throws Exception {
                    return cls.getDeclaredConstructor(clsArr);
                }
            });
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw ((NoSuchMethodException) e.getCause());
            }
            throw new RuntimeException(e.getCause());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAccessible(final AccessibleObject accessibleObject, final boolean z) {
        if (System.getSecurityManager() == null) {
            accessibleObject.setAccessible(z);
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: javassist.util.proxy.SecurityActions.6
                @Override // java.security.PrivilegedAction
                public Void run() {
                    accessibleObject.setAccessible(z);
                    return null;
                }
            });
        }
    }

    static void set(final Field field, final Object obj, final Object obj2) throws IllegalAccessException {
        if (System.getSecurityManager() == null) {
            field.set(obj, obj2);
            return;
        }
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() { // from class: javassist.util.proxy.SecurityActions.7
                @Override // java.security.PrivilegedExceptionAction
                public Void run() throws Exception {
                    field.set(obj, obj2);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw ((IllegalAccessException) e.getCause());
            }
            throw new RuntimeException(e.getCause());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TheUnsafe getSunMiscUnsafeAnonymously() throws ClassNotFoundException {
        try {
            return (TheUnsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<TheUnsafe>() { // from class: javassist.util.proxy.SecurityActions.8
                @Override // java.security.PrivilegedExceptionAction
                public TheUnsafe run() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
                    Class<?> cls = Class.forName("sun.misc.Unsafe");
                    Field declaredField = cls.getDeclaredField("theUnsafe");
                    declaredField.setAccessible(true);
                    SecurityActions securityActions = SecurityActions.stack;
                    Objects.requireNonNull(securityActions);
                    TheUnsafe theUnsafe = new TheUnsafe(cls, declaredField.get(null));
                    declaredField.setAccessible(false);
                    SecurityActions.disableWarning(theUnsafe);
                    return theUnsafe;
                }
            });
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof ClassNotFoundException) {
                throw ((ClassNotFoundException) e.getCause());
            }
            if (e.getCause() instanceof NoSuchFieldException) {
                throw new ClassNotFoundException("No such instance.", e.getCause());
            }
            if ((e.getCause() instanceof IllegalAccessException) || (e.getCause() instanceof IllegalAccessException) || (e.getCause() instanceof SecurityException)) {
                throw new ClassNotFoundException("Security denied access.", e.getCause());
            }
            throw new RuntimeException(e.getCause());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class TheUnsafe {
        final Map<String, List<Method>> methods = new HashMap();
        final Object theUnsafe;
        final Class<?> unsafe;

        TheUnsafe(Class<?> cls, Object obj) {
            Method[] declaredMethods;
            this.unsafe = cls;
            this.theUnsafe = obj;
            for (Method method : cls.getDeclaredMethods()) {
                if (!this.methods.containsKey(method.getName())) {
                    this.methods.put(method.getName(), Collections.singletonList(method));
                } else {
                    if (this.methods.get(method.getName()).size() == 1) {
                        this.methods.put(method.getName(), new ArrayList(this.methods.get(method.getName())));
                    }
                    this.methods.get(method.getName()).add(method);
                }
            }
        }

        private Method getM(String str, Object[] objArr) {
            return this.methods.get(str).get(0);
        }

        public Object call(String str, Object... objArr) {
            try {
                return getM(str, objArr).invoke(this.theUnsafe, objArr);
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }
    }

    static void disableWarning(TheUnsafe theUnsafe) {
        try {
            if (ClassFile.MAJOR_VERSION < 53) {
                return;
            }
            Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            theUnsafe.call("putObjectVolatile", cls, theUnsafe.call("staticFieldOffset", cls.getDeclaredField("logger")), null);
        } catch (Exception unused) {
        }
    }
}
