package javassist.tools.rmi;

/* loaded from: classes2.dex */
public class ObjectNotFoundException extends Exception {
    private static final long serialVersionUID = 1;

    public ObjectNotFoundException(String str) {
        super(str + " is not exported");
    }

    public ObjectNotFoundException(String str, Exception exc) {
        super(str + " because of " + exc.toString());
    }
}
