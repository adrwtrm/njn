package javassist.tools.reflect;

/* loaded from: classes2.dex */
public class Sample {
    private static ClassMetaobject _classobject;
    private Metaobject _metaobject;

    public Object trap(Object[] objArr, int i) throws Throwable {
        Metaobject metaobject = this._metaobject;
        if (metaobject == null) {
            return ClassMetaobject.invoke(this, i, objArr);
        }
        return metaobject.trapMethodcall(i, objArr);
    }

    public static Object trapStatic(Object[] objArr, int i) throws Throwable {
        return _classobject.trapMethodcall(i, objArr);
    }

    public static Object trapRead(Object[] objArr, String str) {
        Object obj = objArr[0];
        if (obj == null) {
            return _classobject.trapFieldRead(str);
        }
        return ((Metalevel) obj)._getMetaobject().trapFieldRead(str);
    }

    public static Object trapWrite(Object[] objArr, String str) {
        Metalevel metalevel = (Metalevel) objArr[0];
        if (metalevel == null) {
            _classobject.trapFieldWrite(str, objArr[1]);
            return null;
        }
        metalevel._getMetaobject().trapFieldWrite(str, objArr[1]);
        return null;
    }
}
