package javassist.expr;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public class ExprEditor {
    public void edit(Cast cast) throws CannotCompileException {
    }

    public void edit(ConstructorCall constructorCall) throws CannotCompileException {
    }

    public void edit(FieldAccess fieldAccess) throws CannotCompileException {
    }

    public void edit(Handler handler) throws CannotCompileException {
    }

    public void edit(Instanceof r1) throws CannotCompileException {
    }

    public void edit(MethodCall methodCall) throws CannotCompileException {
    }

    public void edit(NewArray newArray) throws CannotCompileException {
    }

    public void edit(NewExpr newExpr) throws CannotCompileException {
    }

    public boolean doit(CtClass ctClass, MethodInfo methodInfo) throws CannotCompileException {
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return false;
        }
        CodeIterator it = codeAttribute.iterator();
        LoopContext loopContext = new LoopContext(codeAttribute.getMaxLocals());
        boolean z = false;
        while (it.hasNext()) {
            if (loopBody(it, ctClass, methodInfo, loopContext)) {
                z = true;
            }
        }
        ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
        int size = exceptionTable.size();
        boolean z2 = z;
        for (int i = 0; i < size; i++) {
            Handler handler = new Handler(exceptionTable, i, it, ctClass, methodInfo);
            edit(handler);
            if (handler.edited()) {
                loopContext.updateMax(handler.locals(), handler.stack());
                z2 = true;
            }
        }
        if (codeAttribute.getMaxLocals() < loopContext.maxLocals) {
            codeAttribute.setMaxLocals(loopContext.maxLocals);
        }
        codeAttribute.setMaxStack(codeAttribute.getMaxStack() + loopContext.maxStack);
        if (z2) {
            try {
                methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
            } catch (BadBytecode e) {
                throw new CannotCompileException(e.getMessage(), e);
            }
        }
        return z2;
    }

    public boolean doit(CtClass ctClass, MethodInfo methodInfo, LoopContext loopContext, CodeIterator codeIterator, int i) throws CannotCompileException {
        boolean z = false;
        while (codeIterator.hasNext() && codeIterator.lookAhead() < i) {
            int codeLength = codeIterator.getCodeLength();
            if (loopBody(codeIterator, ctClass, methodInfo, loopContext)) {
                int codeLength2 = codeIterator.getCodeLength();
                if (codeLength != codeLength2) {
                    i += codeLength2 - codeLength;
                }
                z = true;
            }
        }
        return z;
    }

    /* loaded from: classes2.dex */
    public static final class NewOp {
        NewOp next;
        int pos;
        String type;

        NewOp(NewOp newOp, int i, String str) {
            this.next = newOp;
            this.pos = i;
            this.type = str;
        }
    }

    /* loaded from: classes2.dex */
    public static final class LoopContext {
        int maxLocals;
        int maxStack = 0;
        NewOp newList = null;

        public LoopContext(int i) {
            this.maxLocals = i;
        }

        void updateMax(int i, int i2) {
            if (this.maxLocals < i) {
                this.maxLocals = i;
            }
            if (this.maxStack < i2) {
                this.maxStack = i2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x00f9 A[Catch: BadBytecode -> 0x010e, TryCatch #0 {BadBytecode -> 0x010e, blocks: (B:68:0x0000, B:119:0x00f9, B:121:0x00ff, B:90:0x0034, B:93:0x0051, B:95:0x0055, B:97:0x0067, B:98:0x0080, B:100:0x0091, B:101:0x009b, B:102:0x009f, B:103:0x00af, B:112:0x00ca, B:115:0x00da, B:117:0x00e8), top: B:129:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x010c A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final boolean loopBody(javassist.bytecode.CodeIterator r10, javassist.CtClass r11, javassist.bytecode.MethodInfo r12, javassist.expr.ExprEditor.LoopContext r13) throws javassist.CannotCompileException {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.expr.ExprEditor.loopBody(javassist.bytecode.CodeIterator, javassist.CtClass, javassist.bytecode.MethodInfo, javassist.expr.ExprEditor$LoopContext):boolean");
    }
}
