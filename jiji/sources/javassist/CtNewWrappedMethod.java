package javassist;

import java.util.Map;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SyntheticAttribute;
import javassist.compiler.JvstCodeGen;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class CtNewWrappedMethod {
    private static final String addedWrappedMethod = "_added_m$";

    public static CtMethod wrapped(CtClass ctClass, String str, CtClass[] ctClassArr, CtClass[] ctClassArr2, CtMethod ctMethod, CtMethod.ConstParameter constParameter, CtClass ctClass2) throws CannotCompileException {
        CtMethod ctMethod2 = new CtMethod(ctClass, str, ctClassArr, ctClass2);
        ctMethod2.setModifiers(ctMethod.getModifiers());
        try {
            ctMethod2.setExceptionTypes(ctClassArr2);
            ctMethod2.getMethodInfo2().setCodeAttribute(makeBody(ctClass2, ctClass2.getClassFile2(), ctMethod, ctClassArr, ctClass, constParameter).toCodeAttribute());
            return ctMethod2;
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static Bytecode makeBody(CtClass ctClass, ClassFile classFile, CtMethod ctMethod, CtClass[] ctClassArr, CtClass ctClass2, CtMethod.ConstParameter constParameter) throws CannotCompileException {
        boolean isStatic = Modifier.isStatic(ctMethod.getModifiers());
        Bytecode bytecode = new Bytecode(classFile.getConstPool(), 0, 0);
        bytecode.setMaxStack(makeBody0(ctClass, classFile, ctMethod, isStatic, ctClassArr, ctClass2, constParameter, bytecode));
        bytecode.setMaxLocals(isStatic, ctClassArr, 0);
        return bytecode;
    }

    public static int makeBody0(CtClass ctClass, ClassFile classFile, CtMethod ctMethod, boolean z, CtClass[] ctClassArr, CtClass ctClass2, CtMethod.ConstParameter constParameter, Bytecode bytecode) throws CannotCompileException {
        String descriptor;
        if (!(ctClass instanceof CtClassType)) {
            throw new CannotCompileException("bad declaring class" + ctClass.getName());
        }
        int i = 0;
        if (!z) {
            bytecode.addAload(0);
        }
        int compileParameterList = compileParameterList(bytecode, ctClassArr, !z ? 1 : 0);
        if (constParameter == null) {
            descriptor = CtMethod.ConstParameter.defaultDescriptor();
        } else {
            i = constParameter.compile(bytecode);
            descriptor = constParameter.descriptor();
        }
        checkSignature(ctMethod, descriptor);
        try {
            String addBodyMethod = addBodyMethod((CtClassType) ctClass, classFile, ctMethod);
            if (z) {
                bytecode.addInvokestatic(Bytecode.THIS, addBodyMethod, descriptor);
            } else {
                bytecode.addInvokespecial(Bytecode.THIS, addBodyMethod, descriptor);
            }
            compileReturn(bytecode, ctClass2);
            int i2 = i + 2;
            return compileParameterList < i2 ? i2 : compileParameterList;
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    private static void checkSignature(CtMethod ctMethod, String str) throws CannotCompileException {
        if (!str.equals(ctMethod.getMethodInfo2().getDescriptor())) {
            throw new CannotCompileException("wrapped method with a bad signature: " + ctMethod.getDeclaringClass().getName() + '.' + ctMethod.getName());
        }
    }

    private static String addBodyMethod(CtClassType ctClassType, ClassFile classFile, CtMethod ctMethod) throws BadBytecode, CannotCompileException {
        Map<CtMethod, String> hiddenMethods = ctClassType.getHiddenMethods();
        String str = hiddenMethods.get(ctMethod);
        if (str == null) {
            do {
                str = addedWrappedMethod + ctClassType.getUniqueNumber();
            } while (classFile.getMethod(str) != null);
            ClassMap classMap = new ClassMap();
            classMap.put(ctMethod.getDeclaringClass().getName(), ctClassType.getName());
            MethodInfo methodInfo = new MethodInfo(classFile.getConstPool(), str, ctMethod.getMethodInfo2(), classMap);
            methodInfo.setAccessFlags(AccessFlag.setPrivate(methodInfo.getAccessFlags()));
            methodInfo.addAttribute(new SyntheticAttribute(classFile.getConstPool()));
            classFile.addMethod(methodInfo);
            hiddenMethods.put(ctMethod, str);
            CtMember.Cache hasMemberCache = ctClassType.hasMemberCache();
            if (hasMemberCache != null) {
                hasMemberCache.addMethod(new CtMethod(methodInfo, ctClassType));
            }
        }
        return str;
    }

    public static int compileParameterList(Bytecode bytecode, CtClass[] ctClassArr, int i) {
        return JvstCodeGen.compileParameterList(bytecode, ctClassArr, i);
    }

    private static void compileReturn(Bytecode bytecode, CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) ctClass;
            if (ctPrimitiveType != CtClass.voidType) {
                String wrapperName = ctPrimitiveType.getWrapperName();
                bytecode.addCheckcast(wrapperName);
                bytecode.addInvokevirtual(wrapperName, ctPrimitiveType.getGetMethodName(), ctPrimitiveType.getGetMethodDescriptor());
            }
            bytecode.addOpcode(ctPrimitiveType.getReturnOp());
            return;
        }
        bytecode.addCheckcast(ctClass);
        bytecode.addOpcode(176);
    }
}
