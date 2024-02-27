package javassist;

/* loaded from: classes2.dex */
public class Modifier {
    public static final int ABSTRACT = 1024;
    public static final int ANNOTATION = 8192;
    public static final int ENUM = 16384;
    public static final int FINAL = 16;
    public static final int INTERFACE = 512;
    public static final int NATIVE = 256;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int PUBLIC = 1;
    public static final int STATIC = 8;
    public static final int STRICT = 2048;
    public static final int SYNCHRONIZED = 32;
    public static final int TRANSIENT = 128;
    public static final int VARARGS = 128;
    public static final int VOLATILE = 64;

    public static int clear(int i, int i2) {
        return i & (~i2);
    }

    public static boolean isAbstract(int i) {
        return (i & 1024) != 0;
    }

    public static boolean isAnnotation(int i) {
        return (i & 8192) != 0;
    }

    public static boolean isEnum(int i) {
        return (i & 16384) != 0;
    }

    public static boolean isFinal(int i) {
        return (i & 16) != 0;
    }

    public static boolean isInterface(int i) {
        return (i & 512) != 0;
    }

    public static boolean isNative(int i) {
        return (i & 256) != 0;
    }

    public static boolean isPackage(int i) {
        return (i & 7) == 0;
    }

    public static boolean isPrivate(int i) {
        return (i & 2) != 0;
    }

    public static boolean isProtected(int i) {
        return (i & 4) != 0;
    }

    public static boolean isPublic(int i) {
        return (i & 1) != 0;
    }

    public static boolean isStatic(int i) {
        return (i & 8) != 0;
    }

    public static boolean isStrict(int i) {
        return (i & 2048) != 0;
    }

    public static boolean isSynchronized(int i) {
        return (i & 32) != 0;
    }

    public static boolean isTransient(int i) {
        return (i & 128) != 0;
    }

    public static boolean isVarArgs(int i) {
        return (i & 128) != 0;
    }

    public static boolean isVolatile(int i) {
        return (i & 64) != 0;
    }

    public static int setPackage(int i) {
        return i & (-8);
    }

    public static int setPrivate(int i) {
        return (i & (-6)) | 2;
    }

    public static int setProtected(int i) {
        return (i & (-4)) | 4;
    }

    public static int setPublic(int i) {
        return (i & (-7)) | 1;
    }

    public static String toString(int i) {
        return java.lang.reflect.Modifier.toString(i);
    }
}
