package javassist.expr;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;

/* loaded from: classes2.dex */
public class Handler extends Expr {
    private static String EXCEPTION_NAME = "$1";
    private ExceptionTable etable;
    private int index;

    /* JADX INFO: Access modifiers changed from: protected */
    public Handler(ExceptionTable exceptionTable, int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo) {
        super(exceptionTable.handlerPc(i), codeIterator, ctClass, methodInfo);
        this.etable = exceptionTable;
        this.index = i;
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

    public CtClass getType() throws NotFoundException {
        int catchType = this.etable.catchType(this.index);
        if (catchType == 0) {
            return null;
        }
        return this.thisClass.getClassPool().getCtClass(getConstPool().getClassInfo(catchType));
    }

    public boolean isFinally() {
        return this.etable.catchType(this.index) == 0;
    }

    @Override // javassist.expr.Expr
    public void replace(String str) throws CannotCompileException {
        throw new RuntimeException("not implemented yet");
    }

    public void insertBefore(String str) throws CannotCompileException {
        this.edited = true;
        getConstPool();
        CodeAttribute codeAttribute = this.iterator.get();
        Javac javac = new Javac(this.thisClass);
        Bytecode bytecode = javac.getBytecode();
        bytecode.setStackDepth(1);
        bytecode.setMaxLocals(codeAttribute.getMaxLocals());
        try {
            CtClass type = getType();
            int recordVariable = javac.recordVariable(type, EXCEPTION_NAME);
            javac.recordReturnType(type, false);
            bytecode.addAstore(recordVariable);
            javac.compileStmnt(str);
            bytecode.addAload(recordVariable);
            int handlerPc = this.etable.handlerPc(this.index);
            bytecode.addOpcode(167);
            bytecode.addIndex(((handlerPc - this.iterator.getCodeLength()) - bytecode.currentPc()) + 1);
            this.maxStack = bytecode.getMaxStack();
            this.maxLocals = bytecode.getMaxLocals();
            int append = this.iterator.append(bytecode.get());
            this.iterator.append(bytecode.getExceptionTable(), append);
            this.etable.setHandlerPc(this.index, append);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (CompileError e2) {
            throw new CannotCompileException(e2);
        }
    }
}
