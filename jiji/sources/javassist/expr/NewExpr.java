package javassist.expr;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
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
public class NewExpr extends Expr {
    int newPos;
    String newTypeName;

    /* JADX INFO: Access modifiers changed from: protected */
    public NewExpr(int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo, String str, int i2) {
        super(i, codeIterator, ctClass, methodInfo);
        this.newTypeName = str;
        this.newPos = i2;
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

    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.newTypeName);
    }

    public String getClassName() {
        return this.newTypeName;
    }

    public String getSignature() {
        return getConstPool().getMethodrefType(this.iterator.u16bitAt(this.currentPos + 1));
    }

    public CtConstructor getConstructor() throws NotFoundException {
        return getCtClass().getConstructor(getConstPool().getMethodrefType(this.iterator.u16bitAt(this.currentPos + 1)));
    }

    @Override // javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    private int canReplace() throws CannotCompileException {
        int byteAt = this.iterator.byteAt(this.newPos + 3);
        return byteAt == 89 ? (this.iterator.byteAt(this.newPos + 4) == 94 && this.iterator.byteAt(this.newPos + 5) == 88) ? 6 : 4 : (byteAt == 90 && this.iterator.byteAt(this.newPos + 4) == 95) ? 5 : 3;
    }

    @Override // javassist.expr.Expr
    public void replace(String str) throws CannotCompileException {
        CtClass[] parameterTypes;
        CtClass ctClass;
        int maxLocals;
        this.thisClass.getClassFile();
        int i = this.newPos;
        int u16bitAt = this.iterator.u16bitAt(i + 1);
        int canReplace = canReplace();
        int i2 = i + canReplace;
        while (i < i2) {
            this.iterator.writeByte(0, i);
            i++;
        }
        ConstPool constPool = getConstPool();
        int i3 = this.currentPos;
        int u16bitAt2 = this.iterator.u16bitAt(i3 + 1);
        String methodrefType = constPool.getMethodrefType(u16bitAt2);
        Javac javac = new Javac(this.thisClass);
        ClassPool classPool = this.thisClass.getClassPool();
        CodeAttribute codeAttribute = this.iterator.get();
        try {
            parameterTypes = Descriptor.getParameterTypes(methodrefType, classPool);
            ctClass = classPool.get(this.newTypeName);
            maxLocals = codeAttribute.getMaxLocals();
        } catch (NotFoundException e) {
            e = e;
        } catch (BadBytecode unused) {
        } catch (CompileError e2) {
            e = e2;
        }
        try {
            javac.recordParams(this.newTypeName, parameterTypes, true, maxLocals, withinStatic());
            int recordReturnType = javac.recordReturnType(ctClass, true);
            javac.recordProceed(new ProceedForNew(ctClass, u16bitAt, u16bitAt2));
            checkResultValue(ctClass, str);
            Bytecode bytecode = javac.getBytecode();
            storeStack(parameterTypes, true, maxLocals, bytecode);
            javac.recordLocalVariables(codeAttribute, i3);
            bytecode.addConstZero(ctClass);
            bytecode.addStore(recordReturnType, ctClass);
            javac.compileStmnt(str);
            if (canReplace > 3) {
                bytecode.addAload(recordReturnType);
            }
            replace0(i3, bytecode, 3);
        } catch (NotFoundException e3) {
            e = e3;
            throw new CannotCompileException(e);
        } catch (BadBytecode unused2) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e4) {
            e = e4;
            throw new CannotCompileException(e);
        }
    }

    /* loaded from: classes2.dex */
    static class ProceedForNew implements ProceedHandler {
        int methodIndex;
        int newIndex;
        CtClass newType;

        ProceedForNew(CtClass ctClass, int i, int i2) {
            this.newType = ctClass;
            this.newIndex = i;
            this.methodIndex = i2;
        }

        @Override // javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
            bytecode.addOpcode(187);
            bytecode.addIndex(this.newIndex);
            bytecode.addOpcode(89);
            jvstCodeGen.atMethodCallCore(this.newType, "<init>", aSTList, false, true, -1, null);
            jvstCodeGen.setType(this.newType);
        }

        @Override // javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
            jvstTypeChecker.atMethodCallCore(this.newType, "<init>", aSTList);
            jvstTypeChecker.setType(this.newType);
        }
    }
}
