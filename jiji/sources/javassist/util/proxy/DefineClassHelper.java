package javassist.util.proxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.List;
import javassist.CannotCompileException;
import javassist.bytecode.ClassFile;
import javassist.util.proxy.SecurityActions;

/* loaded from: classes2.dex */
public class DefineClassHelper {
    private static final Helper privileged;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class Helper {
        abstract Class<?> defineClass(String str, byte[] bArr, int i, int i2, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException;

        private Helper() {
        }
    }

    /* loaded from: classes2.dex */
    private static class Java11 extends JavaOther {
        private Java11() {
            super();
        }

        @Override // javassist.util.proxy.DefineClassHelper.JavaOther, javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String str, byte[] bArr, int i, int i2, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
            if (cls != null) {
                return DefineClassHelper.toClass(cls, bArr);
            }
            return super.defineClass(str, bArr, i, i2, cls, classLoader, protectionDomain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Java9 extends Helper {
        private final Method getCallerClass;
        private final Object stack;
        private final ReferencedUnsafe sunMiscUnsafe;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public final class ReferencedUnsafe {
            private final MethodHandle defineClass;
            private final SecurityActions.TheUnsafe sunMiscUnsafeTheUnsafe;

            ReferencedUnsafe(SecurityActions.TheUnsafe theUnsafe, MethodHandle methodHandle) {
                this.sunMiscUnsafeTheUnsafe = theUnsafe;
                this.defineClass = methodHandle;
            }

            Class<?> defineClass(String str, byte[] bArr, int i, int i2, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError {
                try {
                    if (Java9.this.getCallerClass.invoke(Java9.this.stack, new Object[0]) != Java9.class) {
                        throw new IllegalAccessError("Access denied for caller.");
                    }
                    try {
                        return (Class) this.defineClass.invokeWithArguments(this.sunMiscUnsafeTheUnsafe.theUnsafe, str, bArr, Integer.valueOf(i), Integer.valueOf(i2), classLoader, protectionDomain);
                    } catch (Throwable th) {
                        if (th instanceof RuntimeException) {
                            throw ((RuntimeException) th);
                        }
                        if (th instanceof ClassFormatError) {
                            throw th;
                        }
                        throw new ClassFormatError(th.getMessage());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("cannot initialize", e);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        Java9() {
            super();
            Class<?> cls;
            this.sunMiscUnsafe = getReferencedUnsafe();
            try {
                cls = Class.forName("java.lang.StackWalker");
            } catch (ClassNotFoundException unused) {
                cls = null;
            }
            if (cls != null) {
                try {
                    Class<?> cls2 = Class.forName("java.lang.StackWalker$Option");
                    this.stack = cls.getMethod("getInstance", cls2).invoke(null, cls2.getEnumConstants()[0]);
                    this.getCallerClass = cls.getMethod("getCallerClass", new Class[0]);
                    return;
                } catch (Throwable th) {
                    throw new RuntimeException("cannot initialize", th);
                }
            }
            this.stack = null;
            this.getCallerClass = null;
        }

        private final ReferencedUnsafe getReferencedUnsafe() {
            try {
                if (DefineClassHelper.privileged != null && this.getCallerClass.invoke(this.stack, new Object[0]) != getClass()) {
                    throw new IllegalAccessError("Access denied for caller.");
                }
                try {
                    SecurityActions.TheUnsafe sunMiscUnsafeAnonymously = SecurityActions.getSunMiscUnsafeAnonymously();
                    List<Method> list = sunMiscUnsafeAnonymously.methods.get("defineClass");
                    if (list == null) {
                        return null;
                    }
                    return new ReferencedUnsafe(sunMiscUnsafeAnonymously, MethodHandles.lookup().unreflect(list.get(0)));
                } finally {
                    RuntimeException runtimeException = new RuntimeException("cannot initialize", th);
                }
            } catch (Exception th) {
                throw new RuntimeException(r0, th);
            }
        }

        @Override // javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String str, byte[] bArr, int i, int i2, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError {
            try {
                if (this.getCallerClass.invoke(this.stack, new Object[0]) != DefineClassHelper.class) {
                    throw new IllegalAccessError("Access denied for caller.");
                }
                return this.sunMiscUnsafe.defineClass(str, bArr, i, i2, classLoader, protectionDomain);
            } catch (Exception e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class Java7 extends Helper {
        private final MethodHandle defineClass;
        private final SecurityActions stack;

        private Java7() {
            super();
            this.stack = SecurityActions.stack;
            this.defineClass = getDefineClassMethodHandle();
        }

        private final MethodHandle getDefineClassMethodHandle() {
            if (DefineClassHelper.privileged != null && this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getMethodHandle(ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String str, byte[] bArr, int i, int i2, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError {
            if (this.stack.getCallerClass() != DefineClassHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return (Class) this.defineClass.invokeWithArguments(classLoader, str, bArr, Integer.valueOf(i), Integer.valueOf(i2), protectionDomain);
            } catch (Throwable th) {
                if (th instanceof RuntimeException) {
                    throw ((RuntimeException) th);
                }
                if (th instanceof ClassFormatError) {
                    throw th;
                }
                throw new ClassFormatError(th.getMessage());
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class JavaOther extends Helper {
        private final Method defineClass;
        private final SecurityActions stack;

        private JavaOther() {
            super();
            this.defineClass = getDefineClassMethod();
            this.stack = SecurityActions.stack;
        }

        private final Method getDefineClassMethod() {
            if (DefineClassHelper.privileged != null && this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getDeclaredMethod(ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String str, byte[] bArr, int i, int i2, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
            Class<?> callerClass = this.stack.getCallerClass();
            if (callerClass != DefineClassHelper.class && callerClass != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                SecurityActions.setAccessible(this.defineClass, true);
                return (Class) this.defineClass.invoke(classLoader, str, bArr, Integer.valueOf(i), Integer.valueOf(i2), protectionDomain);
            } catch (Throwable th) {
                if (th instanceof ClassFormatError) {
                    throw ((ClassFormatError) th);
                }
                if (th instanceof RuntimeException) {
                    throw th;
                }
                throw new CannotCompileException(th);
            }
        }
    }

    static {
        Helper java7;
        if (ClassFile.MAJOR_VERSION > 54) {
            java7 = new Java11();
        } else if (ClassFile.MAJOR_VERSION >= 53) {
            java7 = new Java9();
        } else {
            java7 = ClassFile.MAJOR_VERSION >= 51 ? new Java7() : new JavaOther();
        }
        privileged = java7;
    }

    public static Class<?> toClass(String str, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain, byte[] bArr) throws CannotCompileException {
        try {
            return privileged.defineClass(str, bArr, 0, bArr.length, cls, classLoader, protectionDomain);
        } catch (ClassFormatError e) {
            e = e;
            Throwable cause = e.getCause();
            if (cause != null) {
                e = cause;
            }
            throw new CannotCompileException(e);
        } catch (RuntimeException e2) {
            throw e2;
        } catch (CannotCompileException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new CannotCompileException(e4);
        }
    }

    public static Class<?> toClass(Class<?> cls, byte[] bArr) throws CannotCompileException {
        try {
            DefineClassHelper.class.getModule().addReads(cls.getModule());
            return MethodHandles.privateLookupIn(cls, MethodHandles.lookup()).defineClass(bArr);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new CannotCompileException(e.getMessage() + ": " + cls.getName() + " has no permission to define the class");
        }
    }

    public static Class<?> toClass(MethodHandles.Lookup lookup, byte[] bArr) throws CannotCompileException {
        try {
            return lookup.defineClass(bArr);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new CannotCompileException(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Class<?> toPublicClass(String str, byte[] bArr) throws CannotCompileException {
        try {
            return MethodHandles.lookup().dropLookupMode(2).defineClass(bArr);
        } catch (Throwable th) {
            throw new CannotCompileException(th);
        }
    }

    private DefineClassHelper() {
    }
}
