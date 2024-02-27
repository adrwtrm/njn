package javassist.runtime;

/* loaded from: classes2.dex */
public class DotClass {
    public static NoClassDefFoundError fail(ClassNotFoundException classNotFoundException) {
        return new NoClassDefFoundError(classNotFoundException.getMessage());
    }
}
