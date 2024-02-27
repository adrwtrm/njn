package javassist.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/* loaded from: classes2.dex */
public abstract class Callback {
    public static Map<String, Callback> callbacks = new HashMap();
    private final String sourceCode;

    public abstract void result(Object[] objArr);

    public Callback(String str) {
        String uuid = UUID.randomUUID().toString();
        callbacks.put(uuid, this);
        this.sourceCode = "((javassist.tools.Callback) javassist.tools.Callback.callbacks.get(\"" + uuid + "\")).result(new Object[]{" + str + "});";
    }

    public String toString() {
        return sourceCode();
    }

    public String sourceCode() {
        return this.sourceCode;
    }

    public static void insertBefore(CtBehavior ctBehavior, Callback callback) throws CannotCompileException {
        ctBehavior.insertBefore(callback.toString());
    }

    public static void insertAfter(CtBehavior ctBehavior, Callback callback) throws CannotCompileException {
        ctBehavior.insertAfter(callback.toString(), false);
    }

    public static void insertAfter(CtBehavior ctBehavior, Callback callback, boolean z) throws CannotCompileException {
        ctBehavior.insertAfter(callback.toString(), z);
    }

    public static int insertAt(CtBehavior ctBehavior, Callback callback, int i) throws CannotCompileException {
        return ctBehavior.insertAt(i, callback.toString());
    }
}
