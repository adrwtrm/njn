package javassist.bytecode;

/* loaded from: classes2.dex */
public class BadBytecode extends Exception {
    private static final long serialVersionUID = 1;

    public BadBytecode(int i) {
        super("bytecode " + i);
    }

    public BadBytecode(String str) {
        super(str);
    }

    public BadBytecode(String str, Throwable th) {
        super(str, th);
    }

    public BadBytecode(MethodInfo methodInfo, Throwable th) {
        super(methodInfo.toString() + " in " + methodInfo.getConstPool().getClassName() + ": " + th.getMessage(), th);
    }
}
