package javassist;

import javassist.CtMethod;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;

/* loaded from: classes2.dex */
public class CtNewMethod {
    public static CtMethod make(String str, CtClass ctClass) throws CannotCompileException {
        return make(str, ctClass, null, null);
    }

    public static CtMethod make(String str, CtClass ctClass, String str2, String str3) throws CannotCompileException {
        Javac javac = new Javac(ctClass);
        if (str3 != null) {
            try {
                javac.recordProceed(str2, str3);
            } catch (CompileError e) {
                throw new CannotCompileException(e);
            }
        }
        CtMember compile = javac.compile(str);
        if (compile instanceof CtMethod) {
            return (CtMethod) compile;
        }
        throw new CannotCompileException("not a method");
    }

    public static CtMethod make(CtClass ctClass, String str, CtClass[] ctClassArr, CtClass[] ctClassArr2, String str2, CtClass ctClass2) throws CannotCompileException {
        return make(1, ctClass, str, ctClassArr, ctClassArr2, str2, ctClass2);
    }

    public static CtMethod make(int i, CtClass ctClass, String str, CtClass[] ctClassArr, CtClass[] ctClassArr2, String str2, CtClass ctClass2) throws CannotCompileException {
        try {
            CtMethod ctMethod = new CtMethod(ctClass, str, ctClassArr, ctClass2);
            ctMethod.setModifiers(i);
            ctMethod.setExceptionTypes(ctClassArr2);
            ctMethod.setBody(str2);
            return ctMethod;
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtMethod copy(CtMethod ctMethod, CtClass ctClass, ClassMap classMap) throws CannotCompileException {
        return new CtMethod(ctMethod, ctClass, classMap);
    }

    public static CtMethod copy(CtMethod ctMethod, String str, CtClass ctClass, ClassMap classMap) throws CannotCompileException {
        CtMethod ctMethod2 = new CtMethod(ctMethod, ctClass, classMap);
        ctMethod2.setName(str);
        return ctMethod2;
    }

    public static CtMethod abstractMethod(CtClass ctClass, String str, CtClass[] ctClassArr, CtClass[] ctClassArr2, CtClass ctClass2) throws NotFoundException {
        CtMethod ctMethod = new CtMethod(ctClass, str, ctClassArr, ctClass2);
        ctMethod.setExceptionTypes(ctClassArr2);
        return ctMethod;
    }

    public static CtMethod getter(String str, CtField ctField) throws CannotCompileException {
        FieldInfo fieldInfo2 = ctField.getFieldInfo2();
        String descriptor = fieldInfo2.getDescriptor();
        ConstPool constPool = fieldInfo2.getConstPool();
        MethodInfo methodInfo = new MethodInfo(constPool, str, "()" + descriptor);
        methodInfo.setAccessFlags(1);
        Bytecode bytecode = new Bytecode(constPool, 2, 1);
        try {
            String name = fieldInfo2.getName();
            if ((fieldInfo2.getAccessFlags() & 8) == 0) {
                bytecode.addAload(0);
                bytecode.addGetfield(Bytecode.THIS, name, descriptor);
            } else {
                bytecode.addGetstatic(Bytecode.THIS, name, descriptor);
            }
            bytecode.addReturn(ctField.getType());
            methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
            return new CtMethod(methodInfo, ctField.getDeclaringClass());
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtMethod setter(String str, CtField ctField) throws CannotCompileException {
        FieldInfo fieldInfo2 = ctField.getFieldInfo2();
        String descriptor = fieldInfo2.getDescriptor();
        ConstPool constPool = fieldInfo2.getConstPool();
        MethodInfo methodInfo = new MethodInfo(constPool, str, "(" + descriptor + ")V");
        methodInfo.setAccessFlags(1);
        Bytecode bytecode = new Bytecode(constPool, 3, 3);
        try {
            String name = fieldInfo2.getName();
            if ((fieldInfo2.getAccessFlags() & 8) == 0) {
                bytecode.addAload(0);
                bytecode.addLoad(1, ctField.getType());
                bytecode.addPutfield(Bytecode.THIS, name, descriptor);
            } else {
                bytecode.addLoad(1, ctField.getType());
                bytecode.addPutstatic(Bytecode.THIS, name, descriptor);
            }
            bytecode.addReturn(null);
            methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
            return new CtMethod(methodInfo, ctField.getDeclaringClass());
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtMethod delegator(CtMethod ctMethod, CtClass ctClass) throws CannotCompileException {
        try {
            return delegator0(ctMethod, ctClass);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    private static CtMethod delegator0(CtMethod ctMethod, CtClass ctClass) throws CannotCompileException, NotFoundException {
        int addLoadParameters;
        MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        String name = methodInfo2.getName();
        String descriptor = methodInfo2.getDescriptor();
        ConstPool constPool = ctClass.getClassFile2().getConstPool();
        MethodInfo methodInfo = new MethodInfo(constPool, name, descriptor);
        methodInfo.setAccessFlags(methodInfo2.getAccessFlags());
        ExceptionsAttribute exceptionsAttribute = methodInfo2.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            methodInfo.setExceptionsAttribute((ExceptionsAttribute) exceptionsAttribute.copy(constPool, null));
        }
        Bytecode bytecode = new Bytecode(constPool, 0, 0);
        boolean isStatic = Modifier.isStatic(ctMethod.getModifiers());
        CtClass declaringClass = ctMethod.getDeclaringClass();
        CtClass[] parameterTypes = ctMethod.getParameterTypes();
        if (isStatic) {
            addLoadParameters = bytecode.addLoadParameters(parameterTypes, 0);
            bytecode.addInvokestatic(declaringClass, name, descriptor);
        } else {
            bytecode.addLoad(0, declaringClass);
            addLoadParameters = bytecode.addLoadParameters(parameterTypes, 1);
            bytecode.addInvokespecial(declaringClass, name, descriptor);
        }
        bytecode.addReturn(ctMethod.getReturnType());
        int i = addLoadParameters + 1;
        bytecode.setMaxLocals(i);
        if (i < 2) {
            i = 2;
        }
        bytecode.setMaxStack(i);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return new CtMethod(methodInfo, ctClass);
    }

    public static CtMethod wrapped(CtClass ctClass, String str, CtClass[] ctClassArr, CtClass[] ctClassArr2, CtMethod ctMethod, CtMethod.ConstParameter constParameter, CtClass ctClass2) throws CannotCompileException {
        return CtNewWrappedMethod.wrapped(ctClass, str, ctClassArr, ctClassArr2, ctMethod, constParameter, ctClass2);
    }
}
