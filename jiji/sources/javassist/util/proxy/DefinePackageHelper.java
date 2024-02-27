package javassist.util.proxy;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import javassist.CannotCompileException;
import javassist.bytecode.ClassFile;

/* loaded from: classes2.dex */
public class DefinePackageHelper {
    private static final Helper privileged;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class Helper {
        abstract Package definePackage(ClassLoader classLoader, String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) throws IllegalArgumentException;

        private Helper() {
        }
    }

    /* loaded from: classes2.dex */
    private static class Java9 extends Helper {
        private Java9() {
            super();
        }

        @Override // javassist.util.proxy.DefinePackageHelper.Helper
        Package definePackage(ClassLoader classLoader, String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) throws IllegalArgumentException {
            throw new RuntimeException("define package has been disabled for jigsaw");
        }
    }

    /* loaded from: classes2.dex */
    private static class Java7 extends Helper {
        private final MethodHandle definePackage;
        private final SecurityActions stack;

        private Java7() {
            super();
            this.stack = SecurityActions.stack;
            this.definePackage = getDefinePackageMethodHandle();
        }

        private MethodHandle getDefinePackageMethodHandle() {
            if (this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getMethodHandle(ClassLoader.class, "definePackage", new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // javassist.util.proxy.DefinePackageHelper.Helper
        Package definePackage(ClassLoader classLoader, String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) throws IllegalArgumentException {
            if (this.stack.getCallerClass() != DefinePackageHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return (Package) this.definePackage.invokeWithArguments(classLoader, str, str2, str3, str4, str5, str6, str7, url);
            } catch (Throwable th) {
                if (th instanceof IllegalArgumentException) {
                    throw th;
                }
                if (th instanceof RuntimeException) {
                    throw th;
                }
                return null;
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class JavaOther extends Helper {
        private final Method definePackage;
        private final SecurityActions stack;

        private JavaOther() {
            super();
            this.stack = SecurityActions.stack;
            this.definePackage = getDefinePackageMethod();
        }

        private Method getDefinePackageMethod() {
            if (this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getDeclaredMethod(ClassLoader.class, "definePackage", new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // javassist.util.proxy.DefinePackageHelper.Helper
        Package definePackage(ClassLoader classLoader, String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) throws IllegalArgumentException {
            if (this.stack.getCallerClass() != DefinePackageHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                this.definePackage.setAccessible(true);
                return (Package) this.definePackage.invoke(classLoader, str, str2, str3, str4, str5, str6, str7, url);
            } catch (Throwable th) {
                if (th instanceof InvocationTargetException) {
                    Throwable targetException = th.getTargetException();
                    if (targetException instanceof IllegalArgumentException) {
                        throw ((IllegalArgumentException) targetException);
                    }
                }
                if (th instanceof RuntimeException) {
                    throw ((RuntimeException) th);
                }
                return null;
            }
        }
    }

    static {
        Helper java7;
        if (ClassFile.MAJOR_VERSION >= 53) {
            java7 = new Java9();
        } else {
            java7 = ClassFile.MAJOR_VERSION >= 51 ? new Java7() : new JavaOther();
        }
        privileged = java7;
    }

    public static void definePackage(String str, ClassLoader classLoader) throws CannotCompileException {
        try {
            privileged.definePackage(classLoader, str, null, null, null, null, null, null, null);
        } catch (IllegalArgumentException unused) {
        } catch (Exception e) {
            throw new CannotCompileException(e);
        }
    }

    private DefinePackageHelper() {
    }
}
