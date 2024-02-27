package javassist.compiler;

import java.util.HashMap;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SyntheticAttribute;

/* loaded from: classes2.dex */
public class AccessorMaker {
    static final String lastParamType = "javassist.runtime.Inner";
    private CtClass clazz;
    private int uniqueNumber = 1;
    private Map<String, Object> accessors = new HashMap();

    public AccessorMaker(CtClass ctClass) {
        this.clazz = ctClass;
    }

    public String getConstructor(CtClass ctClass, String str, MethodInfo methodInfo) throws CompileError {
        String str2 = "<init>:" + str;
        String str3 = (String) this.accessors.get(str2);
        if (str3 != null) {
            return str3;
        }
        String appendParameter = Descriptor.appendParameter(lastParamType, str);
        ClassFile classFile = this.clazz.getClassFile();
        try {
            ConstPool constPool = classFile.getConstPool();
            ClassPool classPool = this.clazz.getClassPool();
            MethodInfo methodInfo2 = new MethodInfo(constPool, "<init>", appendParameter);
            methodInfo2.setAccessFlags(0);
            methodInfo2.addAttribute(new SyntheticAttribute(constPool));
            ExceptionsAttribute exceptionsAttribute = methodInfo.getExceptionsAttribute();
            if (exceptionsAttribute != null) {
                methodInfo2.addAttribute(exceptionsAttribute.copy(constPool, null));
            }
            CtClass[] parameterTypes = Descriptor.getParameterTypes(str, classPool);
            Bytecode bytecode = new Bytecode(constPool);
            bytecode.addAload(0);
            int i = 1;
            for (CtClass ctClass2 : parameterTypes) {
                i += bytecode.addLoad(i, ctClass2);
            }
            bytecode.setMaxLocals(i + 1);
            bytecode.addInvokespecial(this.clazz, "<init>", str);
            bytecode.addReturn(null);
            methodInfo2.setCodeAttribute(bytecode.toCodeAttribute());
            classFile.addMethod(methodInfo2);
            this.accessors.put(str2, appendParameter);
            return appendParameter;
        } catch (CannotCompileException e) {
            throw new CompileError(e);
        } catch (NotFoundException e2) {
            throw new CompileError(e2);
        }
    }

    public String getMethodAccessor(String str, String str2, String str3, MethodInfo methodInfo) throws CompileError {
        String str4 = str + ":" + str2;
        String str5 = (String) this.accessors.get(str4);
        if (str5 != null) {
            return str5;
        }
        ClassFile classFile = this.clazz.getClassFile();
        String findAccessorName = findAccessorName(classFile);
        try {
            ConstPool constPool = classFile.getConstPool();
            ClassPool classPool = this.clazz.getClassPool();
            MethodInfo methodInfo2 = new MethodInfo(constPool, findAccessorName, str3);
            methodInfo2.setAccessFlags(8);
            methodInfo2.addAttribute(new SyntheticAttribute(constPool));
            ExceptionsAttribute exceptionsAttribute = methodInfo.getExceptionsAttribute();
            if (exceptionsAttribute != null) {
                methodInfo2.addAttribute(exceptionsAttribute.copy(constPool, null));
            }
            CtClass[] parameterTypes = Descriptor.getParameterTypes(str3, classPool);
            Bytecode bytecode = new Bytecode(constPool);
            int i = 0;
            for (CtClass ctClass : parameterTypes) {
                i += bytecode.addLoad(i, ctClass);
            }
            bytecode.setMaxLocals(i);
            if (str2 == str3) {
                bytecode.addInvokestatic(this.clazz, str, str2);
            } else {
                bytecode.addInvokevirtual(this.clazz, str, str2);
            }
            bytecode.addReturn(Descriptor.getReturnType(str2, classPool));
            methodInfo2.setCodeAttribute(bytecode.toCodeAttribute());
            classFile.addMethod(methodInfo2);
            this.accessors.put(str4, findAccessorName);
            return findAccessorName;
        } catch (CannotCompileException e) {
            throw new CompileError(e);
        } catch (NotFoundException e2) {
            throw new CompileError(e2);
        }
    }

    public MethodInfo getFieldGetter(FieldInfo fieldInfo, boolean z) throws CompileError {
        String str;
        String name = fieldInfo.getName();
        String str2 = name + ":getter";
        Object obj = this.accessors.get(str2);
        if (obj != null) {
            return (MethodInfo) obj;
        }
        ClassFile classFile = this.clazz.getClassFile();
        String findAccessorName = findAccessorName(classFile);
        try {
            ConstPool constPool = classFile.getConstPool();
            ClassPool classPool = this.clazz.getClassPool();
            String descriptor = fieldInfo.getDescriptor();
            if (z) {
                str = "()" + descriptor;
            } else {
                str = "(" + Descriptor.of(this.clazz) + ")" + descriptor;
            }
            MethodInfo methodInfo = new MethodInfo(constPool, findAccessorName, str);
            methodInfo.setAccessFlags(8);
            methodInfo.addAttribute(new SyntheticAttribute(constPool));
            Bytecode bytecode = new Bytecode(constPool);
            if (z) {
                bytecode.addGetstatic(Bytecode.THIS, name, descriptor);
            } else {
                bytecode.addAload(0);
                bytecode.addGetfield(Bytecode.THIS, name, descriptor);
                bytecode.setMaxLocals(1);
            }
            bytecode.addReturn(Descriptor.toCtClass(descriptor, classPool));
            methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
            classFile.addMethod(methodInfo);
            this.accessors.put(str2, methodInfo);
            return methodInfo;
        } catch (CannotCompileException e) {
            throw new CompileError(e);
        } catch (NotFoundException e2) {
            throw new CompileError(e2);
        }
    }

    public MethodInfo getFieldSetter(FieldInfo fieldInfo, boolean z) throws CompileError {
        String str;
        int addLoad;
        String name = fieldInfo.getName();
        String str2 = name + ":setter";
        Object obj = this.accessors.get(str2);
        if (obj != null) {
            return (MethodInfo) obj;
        }
        ClassFile classFile = this.clazz.getClassFile();
        String findAccessorName = findAccessorName(classFile);
        try {
            ConstPool constPool = classFile.getConstPool();
            ClassPool classPool = this.clazz.getClassPool();
            String descriptor = fieldInfo.getDescriptor();
            if (z) {
                str = "(" + descriptor + ")V";
            } else {
                str = "(" + Descriptor.of(this.clazz) + descriptor + ")V";
            }
            MethodInfo methodInfo = new MethodInfo(constPool, findAccessorName, str);
            methodInfo.setAccessFlags(8);
            methodInfo.addAttribute(new SyntheticAttribute(constPool));
            Bytecode bytecode = new Bytecode(constPool);
            if (z) {
                addLoad = bytecode.addLoad(0, Descriptor.toCtClass(descriptor, classPool));
                bytecode.addPutstatic(Bytecode.THIS, name, descriptor);
            } else {
                bytecode.addAload(0);
                addLoad = bytecode.addLoad(1, Descriptor.toCtClass(descriptor, classPool)) + 1;
                bytecode.addPutfield(Bytecode.THIS, name, descriptor);
            }
            bytecode.addReturn(null);
            bytecode.setMaxLocals(addLoad);
            methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
            classFile.addMethod(methodInfo);
            this.accessors.put(str2, methodInfo);
            return methodInfo;
        } catch (CannotCompileException e) {
            throw new CompileError(e);
        } catch (NotFoundException e2) {
            throw new CompileError(e2);
        }
    }

    private String findAccessorName(ClassFile classFile) {
        String sb;
        do {
            StringBuilder sb2 = new StringBuilder("access$");
            int i = this.uniqueNumber;
            this.uniqueNumber = i + 1;
            sb = sb2.append(i).toString();
        } while (classFile.getMethod(sb) != null);
        return sb;
    }
}
