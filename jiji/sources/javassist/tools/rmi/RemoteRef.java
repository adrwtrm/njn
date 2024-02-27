package javassist.tools.rmi;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class RemoteRef implements Serializable {
    private static final long serialVersionUID = 1;
    public String classname;
    public int oid;

    public RemoteRef(int i) {
        this.oid = i;
        this.classname = null;
    }

    public RemoteRef(int i, String str) {
        this.oid = i;
        this.classname = str;
    }
}
