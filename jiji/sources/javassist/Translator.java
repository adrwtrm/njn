package javassist;

/* loaded from: classes2.dex */
public interface Translator {
    void onLoad(ClassPool classPool, String str) throws NotFoundException, CannotCompileException;

    void start(ClassPool classPool) throws NotFoundException, CannotCompileException;
}
