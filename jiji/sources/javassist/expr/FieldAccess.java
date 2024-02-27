package javassist.expr;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
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
public class FieldAccess extends Expr {
    int opcode;

    static boolean isStatic(int i) {
        return i == 178 || i == 179;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FieldAccess(int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo, int i2) {
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

    public boolean isStatic() {
        return isStatic(this.opcode);
    }

    public boolean isReader() {
        int i = this.opcode;
        return i == 180 || i == 178;
    }

    public boolean isWriter() {
        int i = this.opcode;
        return i == 181 || i == 179;
    }

    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(getClassName());
    }

    public String getClassName() {
        return getConstPool().getFieldrefClassName(this.iterator.u16bitAt(this.currentPos + 1));
    }

    public String getFieldName() {
        return getConstPool().getFieldrefName(this.iterator.u16bitAt(this.currentPos + 1));
    }

    public CtField getField() throws NotFoundException {
        CtClass ctClass = getCtClass();
        int u16bitAt = this.iterator.u16bitAt(this.currentPos + 1);
        ConstPool constPool = getConstPool();
        return ctClass.getField(constPool.getFieldrefName(u16bitAt), constPool.getFieldrefType(u16bitAt));
    }

    @Override // javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    public String getSignature() {
        return getConstPool().getFieldrefType(this.iterator.u16bitAt(this.currentPos + 1));
    }

    @Override // javassist.expr.Expr
    public void replace(String str) throws CannotCompileException {
        CtClass[] ctClassArr;
        CtClass ctClass;
        int i;
        this.thisClass.getClassFile();
        ConstPool constPool = getConstPool();
        int i2 = this.currentPos;
        int u16bitAt = this.iterator.u16bitAt(i2 + 1);
        Javac javac = new Javac(this.thisClass);
        CodeAttribute codeAttribute = this.iterator.get();
        try {
            CtClass ctClass2 = Descriptor.toCtClass(constPool.getFieldrefType(u16bitAt), this.thisClass.getClassPool());
            boolean isReader = isReader();
            if (isReader) {
                ctClassArr = new CtClass[0];
                ctClass = ctClass2;
            } else {
                ctClassArr = new CtClass[]{ctClass2};
                ctClass = CtClass.voidType;
            }
            int maxLocals = codeAttribute.getMaxLocals();
            CtClass ctClass3 = ctClass;
            CtClass[] ctClassArr2 = ctClassArr;
            javac.recordParams(constPool.getFieldrefClassName(u16bitAt), ctClassArr, true, maxLocals, withinStatic());
            boolean checkResultValue = checkResultValue(ctClass3, str);
            if (isReader) {
                checkResultValue = true;
            }
            int recordReturnType = javac.recordReturnType(ctClass3, checkResultValue);
            if (isReader) {
                i = maxLocals;
                javac.recordProceed(new ProceedForRead(ctClass3, this.opcode, u16bitAt, i));
            } else {
                i = maxLocals;
                javac.recordType(ctClass2);
                javac.recordProceed(new ProceedForWrite(ctClassArr2[0], this.opcode, u16bitAt, i));
            }
            Bytecode bytecode = javac.getBytecode();
            storeStack(ctClassArr2, isStatic(), i, bytecode);
            javac.recordLocalVariables(codeAttribute, i2);
            if (checkResultValue) {
                if (ctClass3 == CtClass.voidType) {
                    bytecode.addOpcode(1);
                    bytecode.addAstore(recordReturnType);
                } else {
                    bytecode.addConstZero(ctClass3);
                    bytecode.addStore(recordReturnType, ctClass3);
                }
            }
            javac.compileStmnt(str);
            if (isReader) {
                bytecode.addLoad(recordReturnType, ctClass3);
            }
            replace0(i2, bytecode, 3);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode unused) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e2) {
            throw new CannotCompileException(e2);
        }
    }

    /* loaded from: classes2.dex */
    static class ProceedForRead implements ProceedHandler {
        CtClass fieldType;
        int index;
        int opcode;
        int targetVar;

        ProceedForRead(CtClass ctClass, int i, int i2, int i3) {
            this.fieldType = ctClass;
            this.targetVar = i3;
            this.opcode = i;
            this.index = i2;
        }

        @Override // javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
            int i;
            if (aSTList != null && !jvstCodeGen.isParamListName(aSTList)) {
                throw new CompileError("$proceed() cannot take a parameter for field reading");
            }
            if (FieldAccess.isStatic(this.opcode)) {
                i = 0;
            } else {
                bytecode.addAload(this.targetVar);
                i = -1;
            }
            CtClass ctClass = this.fieldType;
            int dataSize = ctClass instanceof CtPrimitiveType ? i + ((CtPrimitiveType) ctClass).getDataSize() : i + 1;
            bytecode.add(this.opcode);
            bytecode.addIndex(this.index);
            bytecode.growStack(dataSize);
            jvstCodeGen.setType(this.fieldType);
        }

        @Override // javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
            jvstTypeChecker.setType(this.fieldType);
        }
    }

    /* loaded from: classes2.dex */
    static class ProceedForWrite implements ProceedHandler {
        CtClass fieldType;
        int index;
        int opcode;
        int targetVar;

        ProceedForWrite(CtClass ctClass, int i, int i2, int i3) {
            this.fieldType = ctClass;
            this.targetVar = i3;
            this.opcode = i;
            this.index = i2;
        }

        @Override // javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
            int i;
            if (jvstCodeGen.getMethodArgsLength(aSTList) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for field writing");
            }
            if (FieldAccess.isStatic(this.opcode)) {
                i = 0;
            } else {
                bytecode.addAload(this.targetVar);
                i = -1;
            }
            jvstCodeGen.atMethodArgs(aSTList, new int[1], new int[1], new String[1]);
            jvstCodeGen.doNumCast(this.fieldType);
            CtClass ctClass = this.fieldType;
            int dataSize = ctClass instanceof CtPrimitiveType ? i - ((CtPrimitiveType) ctClass).getDataSize() : i - 1;
            bytecode.add(this.opcode);
            bytecode.addIndex(this.index);
            bytecode.growStack(dataSize);
            jvstCodeGen.setType(CtClass.voidType);
            jvstCodeGen.addNullIfVoid();
        }

        @Override // javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
            jvstTypeChecker.atMethodArgs(aSTList, new int[1], new int[1], new String[1]);
            jvstTypeChecker.setType(CtClass.voidType);
            jvstTypeChecker.addNullIfVoid();
        }
    }
}
