package javassist.expr;

import java.util.LinkedList;
import java.util.List;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;
import javassist.compiler.Javac;
import javassist.expr.ExprEditor;

/* loaded from: classes2.dex */
public abstract class Expr implements Opcode {
    static final String javaLangObject = "java.lang.Object";
    int currentPos;
    boolean edited;
    CodeIterator iterator;
    int maxLocals;
    int maxStack;
    CtClass thisClass;
    MethodInfo thisMethod;

    public abstract void replace(String str) throws CannotCompileException;

    /* JADX INFO: Access modifiers changed from: protected */
    public Expr(int i, CodeIterator codeIterator, CtClass ctClass, MethodInfo methodInfo) {
        this.currentPos = i;
        this.iterator = codeIterator;
        this.thisClass = ctClass;
        this.thisMethod = methodInfo;
    }

    public CtClass getEnclosingClass() {
        return this.thisClass;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ConstPool getConstPool() {
        return this.thisMethod.getConstPool();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean edited() {
        return this.edited;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int locals() {
        return this.maxLocals;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int stack() {
        return this.maxStack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean withinStatic() {
        return (this.thisMethod.getAccessFlags() & 8) != 0;
    }

    public CtBehavior where() {
        MethodInfo methodInfo = this.thisMethod;
        CtBehavior[] declaredBehaviors = this.thisClass.getDeclaredBehaviors();
        for (int length = declaredBehaviors.length - 1; length >= 0; length--) {
            if (declaredBehaviors[length].getMethodInfo2() == methodInfo) {
                return declaredBehaviors[length];
            }
        }
        CtConstructor classInitializer = this.thisClass.getClassInitializer();
        if (classInitializer == null || classInitializer.getMethodInfo2() != methodInfo) {
            for (int length2 = declaredBehaviors.length - 1; length2 >= 0; length2--) {
                if (this.thisMethod.getName().equals(declaredBehaviors[length2].getMethodInfo2().getName()) && this.thisMethod.getDescriptor().equals(declaredBehaviors[length2].getMethodInfo2().getDescriptor())) {
                    return declaredBehaviors[length2];
                }
            }
            throw new RuntimeException("fatal: not found");
        }
        return classInitializer;
    }

    public CtClass[] mayThrow() {
        String[] exceptions;
        int catchType;
        ClassPool classPool = this.thisClass.getClassPool();
        ConstPool constPool = this.thisMethod.getConstPool();
        LinkedList linkedList = new LinkedList();
        try {
            ExceptionTable exceptionTable = this.thisMethod.getCodeAttribute().getExceptionTable();
            int i = this.currentPos;
            int size = exceptionTable.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (exceptionTable.startPc(i2) <= i && i < exceptionTable.endPc(i2) && (catchType = exceptionTable.catchType(i2)) > 0) {
                    try {
                        addClass(linkedList, classPool.get(constPool.getClassInfo(catchType)));
                    } catch (NotFoundException unused) {
                    }
                }
            }
        } catch (NullPointerException unused2) {
        }
        ExceptionsAttribute exceptionsAttribute = this.thisMethod.getExceptionsAttribute();
        if (exceptionsAttribute != null && (exceptions = exceptionsAttribute.getExceptions()) != null) {
            for (String str : exceptions) {
                try {
                    addClass(linkedList, classPool.get(str));
                } catch (NotFoundException unused3) {
                }
            }
        }
        return (CtClass[]) linkedList.toArray(new CtClass[linkedList.size()]);
    }

    private static void addClass(List<CtClass> list, CtClass ctClass) {
        if (list.contains(ctClass)) {
            return;
        }
        list.add(ctClass);
    }

    public int indexOfBytecode() {
        return this.currentPos;
    }

    public int getLineNumber() {
        return this.thisMethod.getLineNumber(this.currentPos);
    }

    public String getFileName() {
        ClassFile classFile2 = this.thisClass.getClassFile2();
        if (classFile2 == null) {
            return null;
        }
        return classFile2.getSourceFile();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final boolean checkResultValue(CtClass ctClass, String str) throws CannotCompileException {
        boolean z = str.indexOf(Javac.resultVarName) >= 0;
        if (z || ctClass == CtClass.voidType) {
            return z;
        }
        throw new CannotCompileException("the resulting value is not stored in $_");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void storeStack(CtClass[] ctClassArr, boolean z, int i, Bytecode bytecode) {
        storeStack0(0, ctClassArr.length, ctClassArr, i + 1, bytecode);
        if (z) {
            bytecode.addOpcode(1);
        }
        bytecode.addAstore(i);
    }

    private static void storeStack0(int i, int i2, CtClass[] ctClassArr, int i3, Bytecode bytecode) {
        if (i >= i2) {
            return;
        }
        CtClass ctClass = ctClassArr[i];
        storeStack0(i + 1, i2, ctClassArr, (ctClass instanceof CtPrimitiveType ? ((CtPrimitiveType) ctClass).getDataSize() : 1) + i3, bytecode);
        bytecode.addStore(i3, ctClass);
    }

    public void replace(String str, ExprEditor exprEditor) throws CannotCompileException {
        replace(str);
        if (exprEditor != null) {
            runEditor(exprEditor, this.iterator);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void replace0(int i, Bytecode bytecode, int i2) throws BadBytecode {
        byte[] bArr = bytecode.get();
        this.edited = true;
        int length = bArr.length - i2;
        for (int i3 = 0; i3 < i2; i3++) {
            this.iterator.writeByte(0, i + i3);
        }
        if (length > 0) {
            i = this.iterator.insertGapAt(i, length, false).position;
        }
        this.iterator.write(bArr, i);
        this.iterator.insert(bytecode.getExceptionTable(), i);
        this.maxLocals = bytecode.getMaxLocals();
        this.maxStack = bytecode.getMaxStack();
    }

    protected void runEditor(ExprEditor exprEditor, CodeIterator codeIterator) throws CannotCompileException {
        CodeAttribute codeAttribute = codeIterator.get();
        int maxLocals = codeAttribute.getMaxLocals();
        int maxStack = codeAttribute.getMaxStack();
        int locals = locals();
        codeAttribute.setMaxStack(stack());
        codeAttribute.setMaxLocals(locals);
        ExprEditor.LoopContext loopContext = new ExprEditor.LoopContext(locals);
        int codeLength = codeIterator.getCodeLength();
        int lookAhead = codeIterator.lookAhead();
        codeIterator.move(this.currentPos);
        if (exprEditor.doit(this.thisClass, this.thisMethod, loopContext, codeIterator, lookAhead)) {
            this.edited = true;
        }
        codeIterator.move((lookAhead + codeIterator.getCodeLength()) - codeLength);
        codeAttribute.setMaxLocals(maxLocals);
        codeAttribute.setMaxStack(maxStack);
        this.maxLocals = loopContext.maxLocals;
        this.maxStack += loopContext.maxStack;
    }
}
