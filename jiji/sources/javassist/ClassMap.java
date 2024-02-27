package javassist;

import java.util.HashMap;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public class ClassMap extends HashMap<String, String> {
    private static final long serialVersionUID = 1;
    private ClassMap parent;

    public ClassMap() {
        this.parent = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ClassMap(ClassMap classMap) {
        this.parent = classMap;
    }

    public void put(CtClass ctClass, CtClass ctClass2) {
        put(ctClass.getName(), ctClass2.getName());
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public String put(String str, String str2) {
        if (str == str2) {
            return str;
        }
        String jvmName = toJvmName(str);
        String str3 = get((Object) jvmName);
        return (str3 == null || !str3.equals(jvmName)) ? (String) super.put((ClassMap) jvmName, toJvmName(str2)) : str3;
    }

    public void putIfNone(String str, String str2) {
        if (str == str2) {
            return;
        }
        String jvmName = toJvmName(str);
        if (get((Object) jvmName) == null) {
            super.put((ClassMap) jvmName, toJvmName(str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String put0(String str, String str2) {
        return (String) super.put((ClassMap) str, str2);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public String get(Object obj) {
        ClassMap classMap;
        String str = (String) super.get(obj);
        return (str != null || (classMap = this.parent) == null) ? str : classMap.get(obj);
    }

    public void fix(CtClass ctClass) {
        fix(ctClass.getName());
    }

    public void fix(String str) {
        String jvmName = toJvmName(str);
        super.put((ClassMap) jvmName, jvmName);
    }

    public static String toJvmName(String str) {
        return Descriptor.toJvmName(str);
    }

    public static String toJavaName(String str) {
        return Descriptor.toJavaName(str);
    }
}
