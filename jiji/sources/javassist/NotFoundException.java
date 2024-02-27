package javassist;

/* loaded from: classes2.dex */
public class NotFoundException extends Exception {
    private static final long serialVersionUID = 1;

    public NotFoundException(String str) {
        super(str);
    }

    public NotFoundException(String str, Exception exc) {
        super(str + " because of " + exc.toString());
    }
}
