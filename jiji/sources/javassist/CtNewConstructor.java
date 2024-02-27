package javassist;

import javassist.CtMethod;
import javassist.bytecode.Bytecode;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;

/* loaded from: classes2.dex */
public class CtNewConstructor {
    public static final int PASS_ARRAY = 1;
    public static final int PASS_NONE = 0;
    public static final int PASS_PARAMS = 2;

    public static CtConstructor make(String str, CtClass ctClass) throws CannotCompileException {
        try {
            CtMember compile = new Javac(ctClass).compile(str);
            if (compile instanceof CtConstructor) {
                return (CtConstructor) compile;
            }
            throw new CannotCompileException("not a constructor");
        } catch (CompileError e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtConstructor make(CtClass[] ctClassArr, CtClass[] ctClassArr2, String str, CtClass ctClass) throws CannotCompileException {
        try {
            CtConstructor ctConstructor = new CtConstructor(ctClassArr, ctClass);
            ctConstructor.setExceptionTypes(ctClassArr2);
            ctConstructor.setBody(str);
            return ctConstructor;
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtConstructor copy(CtConstructor ctConstructor, CtClass ctClass, ClassMap classMap) throws CannotCompileException {
        return new CtConstructor(ctConstructor, ctClass, classMap);
    }

    public static CtConstructor defaultConstructor(CtClass ctClass) throws CannotCompileException {
        CtConstructor ctConstructor = new CtConstructor((CtClass[]) null, ctClass);
        Bytecode bytecode = new Bytecode(ctClass.getClassFile2().getConstPool(), 1, 1);
        bytecode.addAload(0);
        try {
            bytecode.addInvokespecial(ctClass.getSuperclass(), "<init>", "()V");
            bytecode.add(177);
            ctConstructor.getMethodInfo2().setCodeAttribute(bytecode.toCodeAttribute());
            return ctConstructor;
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtConstructor skeleton(CtClass[] ctClassArr, CtClass[] ctClassArr2, CtClass ctClass) throws CannotCompileException {
        return make(ctClassArr, ctClassArr2, 0, null, null, ctClass);
    }

    public static CtConstructor make(CtClass[] ctClassArr, CtClass[] ctClassArr2, CtClass ctClass) throws CannotCompileException {
        return make(ctClassArr, ctClassArr2, 2, null, null, ctClass);
    }

    public static CtConstructor make(CtClass[] ctClassArr, CtClass[] ctClassArr2, int i, CtMethod ctMethod, CtMethod.ConstParameter constParameter, CtClass ctClass) throws CannotCompileException {
        return CtNewWrappedConstructor.wrapped(ctClassArr, ctClassArr2, i, ctMethod, constParameter, ctClass);
    }
}
