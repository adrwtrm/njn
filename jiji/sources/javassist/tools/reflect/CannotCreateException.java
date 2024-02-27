package javassist.tools.reflect;

/* loaded from: classes2.dex */
public class CannotCreateException extends Exception {
    private static final long serialVersionUID = 1;

    public CannotCreateException(String str) {
        super(str);
    }

    public CannotCreateException(Exception exc) {
        super("by " + exc.toString());
    }
}
