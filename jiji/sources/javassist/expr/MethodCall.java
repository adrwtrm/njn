package javassist.expr;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;

/* loaded from: classes2.dex */
public class MethodCall extends Expr {
    /* JADX INFO: Access modifiers changed from: protected */
    public MethodCall(int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo) {
        super(i, codeIterator, ctClass, methodInfo);
    }

    private int getNameAndType(ConstPool constPool) {
        int i = this.currentPos;
        int byteAt = this.iterator.byteAt(i);
        int u16bitAt = this.iterator.u16bitAt(i + 1);
        if (byteAt == 185) {
            return constPool.getInterfaceMethodrefNameAndType(u16bitAt);
        }
        return constPool.getMethodrefNameAndType(u16bitAt);
    }

    @Override // javassist.expr.Expr
    public CtBehavior where() {
        return super.where();
    }

    @Override // javassist.expr.Expr
    public int getLineNumber() {
        return super.getLineNumber();
    }

    @Override // javassist.expr.Expr
    public String getFileName() {
        return super.getFileName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(getClassName());
    }

    public String getClassName() {
        String methodrefClassName;
        ConstPool constPool = getConstPool();
        int i = this.currentPos;
        int byteAt = this.iterator.byteAt(i);
        int u16bitAt = this.iterator.u16bitAt(i + 1);
        if (byteAt == 185) {
            methodrefClassName = constPool.getInterfaceMethodrefClassName(u16bitAt);
        } else {
            methodrefClassName = constPool.getMethodrefClassName(u16bitAt);
        }
        return methodrefClassName.charAt(0) == '[' ? Descriptor.toClassName(methodrefClassName) : methodrefClassName;
    }

    public String getMethodName() {
        ConstPool constPool = getConstPool();
        return constPool.getUtf8Info(constPool.getNameAndTypeName(getNameAndType(constPool)));
    }

    public CtMethod getMethod() throws NotFoundException {
        return getCtClass().getMethod(getMethodName(), getSignature());
    }

    public String getSignature() {
        ConstPool constPool = getConstPool();
        return constPool.getUtf8Info(constPool.getNameAndTypeDescriptor(getNameAndType(constPool)));
    }

    @Override // javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    public boolean isSuper() {
        return this.iterator.byteAt(this.currentPos) == 183 && !where().getDeclaringClass().getName().equals(getClassName());
    }

    @Override // javassist.expr.Expr
    public void replace(String str) throws CannotCompileException {
        String methodrefClassName;
        String methodrefName;
        String methodrefType;
        int i;
        CtClass ctClass;
        boolean z;
        int i2;
        CtClass[] ctClassArr;
        this.thisClass.getClassFile();
        ConstPool constPool = getConstPool();
        int i3 = this.currentPos;
        int u16bitAt = this.iterator.u16bitAt(i3 + 1);
        int byteAt = this.iterator.byteAt(i3);
        if (byteAt == 185) {
            methodrefClassName = constPool.getInterfaceMethodrefClassName(u16bitAt);
            methodrefName = constPool.getInterfaceMethodrefName(u16bitAt);
            methodrefType = constPool.getInterfaceMethodrefType(u16bitAt);
            i = 5;
        } else if (byteAt == 184 || byteAt == 183 || byteAt == 182) {
            methodrefClassName = constPool.getMethodrefClassName(u16bitAt);
            methodrefName = constPool.getMethodrefName(u16bitAt);
            methodrefType = constPool.getMethodrefType(u16bitAt);
            i = 3;
        } else {
            throw new CannotCompileException("not method invocation");
        }
        String str2 = methodrefType;
        String str3 = methodrefName;
        int i4 = i;
        String str4 = methodrefClassName;
        Javac javac = new Javac(this.thisClass);
        ClassPool classPool = this.thisClass.getClassPool();
        CodeAttribute codeAttribute = this.iterator.get();
        try {
            CtClass[] parameterTypes = Descriptor.getParameterTypes(str2, classPool);
            CtClass returnType = Descriptor.getReturnType(str2, classPool);
            int maxLocals = codeAttribute.getMaxLocals();
            javac.recordParams(str4, parameterTypes, true, maxLocals, withinStatic());
            int recordReturnType = javac.recordReturnType(returnType, true);
            if (byteAt == 184) {
                javac.recordStaticProceed(str4, str3);
                ctClass = returnType;
            } else if (byteAt == 183) {
                ctClass = returnType;
                javac.recordSpecialProceed(Javac.param0Name, str4, str3, str2, u16bitAt);
            } else {
                ctClass = returnType;
                javac.recordProceed(Javac.param0Name, str3);
            }
            checkResultValue(ctClass, str);
            Bytecode bytecode = javac.getBytecode();
            if (byteAt == 184) {
                i2 = maxLocals;
                ctClassArr = parameterTypes;
                z = true;
            } else {
                z = false;
                i2 = maxLocals;
                ctClassArr = parameterTypes;
            }
            storeStack(ctClassArr, z, i2, bytecode);
            javac.recordLocalVariables(codeAttribute, i3);
            if (ctClass != CtClass.voidType) {
                bytecode.addConstZero(ctClass);
                bytecode.addStore(recordReturnType, ctClass);
            }
            javac.compileStmnt(str);
            if (ctClass != CtClass.voidType) {
                bytecode.addLoad(recordReturnType, ctClass);
            }
            replace0(i3, bytecode, i4);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode unused) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e2) {
            throw new CannotCompileException(e2);
        }
    }
}
