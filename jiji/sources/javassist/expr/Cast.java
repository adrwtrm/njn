package javassist.expr;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;
import javassist.compiler.JvstCodeGen;
import javassist.compiler.JvstTypeChecker;
import javassist.compiler.ProceedHandler;
import javassist.compiler.ast.ASTList;

/* loaded from: classes2.dex */
public class Cast extends Expr {
    /* JADX INFO: Access modifiers changed from: protected */
    public Cast(int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo) {
        super(i, codeIterator, ctClass, methodInfo);
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

    public CtClass getType() throws NotFoundException {
        return this.thisClass.getClassPool().getCtClass(getConstPool().getClassInfo(this.iterator.u16bitAt(this.currentPos + 1)));
    }

    @Override // javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    @Override // javassist.expr.Expr
    public void replace(String str) throws CannotCompileException {
        this.thisClass.getClassFile();
        getConstPool();
        int i = this.currentPos;
        int u16bitAt = this.iterator.u16bitAt(i + 1);
        Javac javac = new Javac(this.thisClass);
        ClassPool classPool = this.thisClass.getClassPool();
        CodeAttribute codeAttribute = this.iterator.get();
        try {
            CtClass[] ctClassArr = {classPool.get("java.lang.Object")};
            CtClass type = getType();
            int maxLocals = codeAttribute.getMaxLocals();
            javac.recordParams("java.lang.Object", ctClassArr, true, maxLocals, withinStatic());
            int recordReturnType = javac.recordReturnType(type, true);
            javac.recordProceed(new ProceedForCast(u16bitAt, type));
            checkResultValue(type, str);
            Bytecode bytecode = javac.getBytecode();
            storeStack(ctClassArr, true, maxLocals, bytecode);
            javac.recordLocalVariables(codeAttribute, i);
            bytecode.addConstZero(type);
            bytecode.addStore(recordReturnType, type);
            javac.compileStmnt(str);
            bytecode.addLoad(recordReturnType, type);
            replace0(i, bytecode, 3);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode unused) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e2) {
            throw new CannotCompileException(e2);
        }
    }

    /* loaded from: classes2.dex */
    static class ProceedForCast implements ProceedHandler {
        int index;
        CtClass retType;

        ProceedForCast(int i, CtClass ctClass) {
            this.index = i;
            this.retType = ctClass;
        }

        @Override // javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
            if (jvstCodeGen.getMethodArgsLength(aSTList) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for cast");
            }
            jvstCodeGen.atMethodArgs(aSTList, new int[1], new int[1], new String[1]);
            bytecode.addOpcode(192);
            bytecode.addIndex(this.index);
            jvstCodeGen.setType(this.retType);
        }

        @Override // javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
            jvstTypeChecker.atMethodArgs(aSTList, new int[1], new int[1], new String[1]);
            jvstTypeChecker.setType(this.retType);
        }
    }
}
