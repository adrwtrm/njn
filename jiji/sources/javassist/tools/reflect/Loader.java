package javassist.tools.reflect;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

/* loaded from: classes2.dex */
public class Loader extends javassist.Loader {
    protected Reflection reflection;

    public static void main(String[] strArr) throws Throwable {
        new Loader().run(strArr);
    }

    public Loader() throws CannotCompileException, NotFoundException {
        delegateLoadingOf("javassist.tools.reflect.Loader");
        this.reflection = new Reflection();
        addTranslator(ClassPool.getDefault(), this.reflection);
    }

    public boolean makeReflective(String str, String str2, String str3) throws CannotCompileException, NotFoundException {
        return this.reflection.makeReflective(str, str2, str3);
    }
}
