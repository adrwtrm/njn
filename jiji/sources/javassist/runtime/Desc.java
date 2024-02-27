package javassist.runtime;

/* loaded from: classes2.dex */
public class Desc {
    private static final ThreadLocal<Boolean> USE_CONTEXT_CLASS_LOADER_LOCALLY = new ThreadLocal<Boolean>() { // from class: javassist.runtime.Desc.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Boolean initialValue() {
            return false;
        }
    };
    public static boolean useContextClassLoader = false;

    public static void setUseContextClassLoaderLocally() {
        USE_CONTEXT_CLASS_LOADER_LOCALLY.set(true);
    }

    public static void resetUseContextClassLoaderLocally() {
        USE_CONTEXT_CLASS_LOADER_LOCALLY.remove();
    }

    private static Class<?> getClassObject(String str) throws ClassNotFoundException {
        if (useContextClassLoader || USE_CONTEXT_CLASS_LOADER_LOCALLY.get().booleanValue()) {
            return Class.forName(str, true, Thread.currentThread().getContextClassLoader());
        }
        return Class.forName(str);
    }

    public static Class<?> getClazz(String str) {
        try {
            return getClassObject(str);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("$class: internal error, could not find class '" + str + "' (Desc.useContextClassLoader: " + Boolean.toString(useContextClassLoader) + ")", e);
        }
    }

    public static Class<?>[] getParams(String str) {
        if (str.charAt(0) != '(') {
            throw new RuntimeException("$sig: internal error");
        }
        return getType(str, str.length(), 1, 0);
    }

    public static Class<?> getType(String str) {
        Class<?>[] type = getType(str, str.length(), 0, 0);
        if (type == null || type.length != 1) {
            throw new RuntimeException("$type: internal error");
        }
        return type[0];
    }

    private static Class<?>[] getType(String str, int i, int i2, int i3) {
        Class<?> cls;
        if (i2 >= i) {
            return new Class[i3];
        }
        char charAt = str.charAt(i2);
        if (charAt == 'F') {
            cls = Float.TYPE;
        } else {
            if (charAt != 'L') {
                if (charAt == 'S') {
                    cls = Short.TYPE;
                } else if (charAt == 'V') {
                    cls = Void.TYPE;
                } else if (charAt == 'I') {
                    cls = Integer.TYPE;
                } else if (charAt == 'J') {
                    cls = Long.TYPE;
                } else if (charAt == 'Z') {
                    cls = Boolean.TYPE;
                } else if (charAt != '[') {
                    switch (charAt) {
                        case 'B':
                            cls = Byte.TYPE;
                            break;
                        case 'C':
                            cls = Character.TYPE;
                            break;
                        case 'D':
                            cls = Double.TYPE;
                            break;
                        default:
                            return new Class[i3];
                    }
                }
            }
            return getClassType(str, i, i2, i3);
        }
        Class<?>[] type = getType(str, i, i2 + 1, i3 + 1);
        type[i3] = cls;
        return type;
    }

    private static Class<?>[] getClassType(String str, int i, int i2, int i3) {
        String substring;
        int i4 = i2;
        while (str.charAt(i4) == '[') {
            i4++;
        }
        if (str.charAt(i4) == 'L' && (i4 = str.indexOf(59, i4)) < 0) {
            throw new IndexOutOfBoundsException("bad descriptor");
        }
        if (str.charAt(i2) == 'L') {
            substring = str.substring(i2 + 1, i4);
        } else {
            substring = str.substring(i2, i4 + 1);
        }
        Class<?>[] type = getType(str, i, i4 + 1, i3 + 1);
        try {
            type[i3] = getClassObject(substring.replace('/', '.'));
            return type;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
