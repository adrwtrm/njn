package javassist.bytecode;

/* loaded from: classes2.dex */
public class AccessFlag {
    public static final int ABSTRACT = 1024;
    public static final int ANNOTATION = 8192;
    public static final int BRIDGE = 64;
    public static final int ENUM = 16384;
    public static final int FINAL = 16;
    public static final int INTERFACE = 512;
    public static final int MANDATED = 32768;
    public static final int MODULE = 32768;
    public static final int NATIVE = 256;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int PUBLIC = 1;
    public static final int STATIC = 8;
    public static final int STRICT = 2048;
    public static final int SUPER = 32;
    public static final int SYNCHRONIZED = 32;
    public static final int SYNTHETIC = 4096;
    public static final int TRANSIENT = 128;
    public static final int VARARGS = 128;
    public static final int VOLATILE = 64;

    public static int clear(int i, int i2) {
        return i & (~i2);
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

    public static int of(int i) {
        return i;
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

    public static int toModifier(int i) {
        return i;
    }
}
