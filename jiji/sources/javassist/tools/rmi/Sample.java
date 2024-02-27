package javassist.tools.rmi;

/* loaded from: classes2.dex */
public class Sample {
    private ObjectImporter importer;
    private int objectId;

    public Object forward(Object[] objArr, int i) {
        return this.importer.call(this.objectId, i, objArr);
    }

    public static Object forwardStatic(Object[] objArr, int i) throws RemoteException {
        throw new RemoteException("cannot call a static method.");
    }
}
