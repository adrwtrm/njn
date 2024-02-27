package javassist.expr;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtPrimitiveType;
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
import javassist.compiler.JvstCodeGen;
import javassist.compiler.JvstTypeChecker;
import javassist.compiler.ProceedHandler;
import javassist.compiler.ast.ASTList;

/* loaded from: classes2.dex */
public class NewArray extends Expr {
    int opcode;

    /* JADX INFO: Access modifiers changed from: protected */
    public NewArray(int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo, int i2) {
        super(i, codeIterator, ctClass, methodInfo);
        this.opcode = i2;
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

    @Override // javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    public CtClass getComponentType() throws NotFoundException {
        int i = this.opcode;
        if (i == 188) {
            return getPrimitiveType(this.iterator.byteAt(this.currentPos + 1));
        }
        if (i == 189 || i == 197) {
            String classInfo = getConstPool().getClassInfo(this.iterator.u16bitAt(this.currentPos + 1));
            return Descriptor.toCtClass(Descriptor.toArrayComponent(classInfo, Descriptor.arrayDimension(classInfo)), this.thisClass.getClassPool());
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }

    CtClass getPrimitiveType(int i) {
        switch (i) {
            case 4:
                return CtClass.booleanType;
            case 5:
                return CtClass.charType;
            case 6:
                return CtClass.floatType;
            case 7:
                return CtClass.doubleType;
            case 8:
                return CtClass.byteType;
            case 9:
                return CtClass.shortType;
            case 10:
                return CtClass.intType;
            case 11:
                return CtClass.longType;
            default:
                throw new RuntimeException("bad atype: " + i);
        }
    }

    public int getDimension() {
        int i = this.opcode;
        if (i == 188) {
            return 1;
        }
        if (i == 189 || i == 197) {
            return Descriptor.arrayDimension(getConstPool().getClassInfo(this.iterator.u16bitAt(this.currentPos + 1))) + (this.opcode != 189 ? 0 : 1);
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }

    public int getCreatedDimensions() {
        if (this.opcode == 197) {
            return this.iterator.byteAt(this.currentPos + 3);
        }
        return 1;
    }

    @Override // javassist.expr.Expr
    public void replace(String str) throws CannotCompileException {
        try {
            replace2(str);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode unused) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e2) {
            throw new CannotCompileException(e2);
        }
    }

    private void replace2(String str) throws CompileError, NotFoundException, BadBytecode, CannotCompileException {
        int u16bitAt;
        String classInfo;
        int byteAt;
        int i;
        String str2;
        int i2;
        this.thisClass.getClassFile();
        ConstPool constPool = getConstPool();
        int i3 = this.currentPos;
        int i4 = this.opcode;
        if (i4 == 188) {
            i2 = this.iterator.byteAt(this.currentPos + 1);
            str2 = "[" + ((CtPrimitiveType) getPrimitiveType(i2)).getDescriptor();
            i = 2;
            byteAt = 1;
        } else {
            if (i4 == 189) {
                u16bitAt = this.iterator.u16bitAt(i3 + 1);
                String classInfo2 = constPool.getClassInfo(u16bitAt);
                if (classInfo2.startsWith("[")) {
                    classInfo = "[" + classInfo2;
                } else {
                    classInfo = "[L" + classInfo2 + ";";
                }
                byteAt = 1;
                i = 3;
            } else if (i4 == 197) {
                u16bitAt = this.iterator.u16bitAt(this.currentPos + 1);
                classInfo = constPool.getClassInfo(u16bitAt);
                byteAt = this.iterator.byteAt(this.currentPos + 3);
                i = 4;
            } else {
                throw new RuntimeException("bad opcode: " + this.opcode);
            }
            int i5 = u16bitAt;
            str2 = classInfo;
            i2 = i5;
        }
        CtClass ctClass = Descriptor.toCtClass(str2, this.thisClass.getClassPool());
        Javac javac = new Javac(this.thisClass);
        CodeAttribute codeAttribute = this.iterator.get();
        CtClass[] ctClassArr = new CtClass[byteAt];
        for (int i6 = 0; i6 < byteAt; i6++) {
            ctClassArr[i6] = CtClass.intType;
        }
        int maxLocals = codeAttribute.getMaxLocals();
        javac.recordParams("java.lang.Object", ctClassArr, true, maxLocals, withinStatic());
        checkResultValue(ctClass, str);
        int recordReturnType = javac.recordReturnType(ctClass, true);
        javac.recordProceed(new ProceedForArray(ctClass, this.opcode, i2, byteAt));
        Bytecode bytecode = javac.getBytecode();
        storeStack(ctClassArr, true, maxLocals, bytecode);
        javac.recordLocalVariables(codeAttribute, i3);
        bytecode.addOpcode(1);
        bytecode.addAstore(recordReturnType);
        javac.compileStmnt(str);
        bytecode.addAload(recordReturnType);
        replace0(i3, bytecode, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class ProceedForArray implements ProceedHandler {
        CtClass arrayType;
        int dimension;
        int index;
        int opcode;

        ProceedForArray(CtClass ctClass, int i, int i2, int i3) {
            this.arrayType = ctClass;
            this.opcode = i;
            this.index = i2;
            this.dimension = i3;
        }

        @Override // javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
            int methodArgsLength = jvstCodeGen.getMethodArgsLength(aSTList);
            if (methodArgsLength != this.dimension) {
                throw new CompileError("$proceed() with a wrong number of parameters");
            }
            jvstCodeGen.atMethodArgs(aSTList, new int[methodArgsLength], new int[methodArgsLength], new String[methodArgsLength]);
            bytecode.addOpcode(this.opcode);
            int i = this.opcode;
            if (i == 189) {
                bytecode.addIndex(this.index);
            } else if (i == 188) {
                bytecode.add(this.index);
            } else {
                bytecode.addIndex(this.index);
                bytecode.add(this.dimension);
                bytecode.growStack(1 - this.dimension);
            }
            jvstCodeGen.setType(this.arrayType);
        }

        @Override // javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
            jvstTypeChecker.setType(this.arrayType);
        }
    }
}
