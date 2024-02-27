package javassist.tools.rmi;

/* loaded from: classes2.dex */
public class RemoteException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public RemoteException(String str) {
        super(str);
    }

    public RemoteException(Exception exc) {
        super("by " + exc.toString());
    }
}
