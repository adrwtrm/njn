package javassist.bytecode.annotation;

/* loaded from: classes2.dex */
public class NoSuchClassError extends Error {
    private static final long serialVersionUID = 1;
    private String className;

    public NoSuchClassError(String str, Error error) {
        super(error.toString(), error);
        this.className = str;
    }

    public String getClassName() {
        return this.className;
    }
}
