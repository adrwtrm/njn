package javassist.compiler;

import java.util.ArrayList;
import java.util.List;
import javassist.bytecode.Bytecode;
import javassist.bytecode.Opcode;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.ArrayInit;
import javassist.compiler.ast.AssignExpr;
import javassist.compiler.ast.BinExpr;
import javassist.compiler.ast.CallExpr;
import javassist.compiler.ast.CastExpr;
import javassist.compiler.ast.CondExpr;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.DoubleConst;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.FieldDecl;
import javassist.compiler.ast.InstanceOfExpr;
import javassist.compiler.ast.IntConst;
import javassist.compiler.ast.Keyword;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.MethodDecl;
import javassist.compiler.ast.NewExpr;
import javassist.compiler.ast.Pair;
import javassist.compiler.ast.Stmnt;
import javassist.compiler.ast.StringL;
import javassist.compiler.ast.Symbol;
import javassist.compiler.ast.Variable;
import javassist.compiler.ast.Visitor;

/* loaded from: classes2.dex */
public abstract class CodeGen extends Visitor implements Opcode, TokenId {
    private static final int P_DOUBLE = 0;
    private static final int P_FLOAT = 1;
    private static final int P_INT = 3;
    private static final int P_LONG = 2;
    private static final int P_OTHER = -1;
    static final String javaLangObject = "java.lang.Object";
    static final String javaLangString = "java.lang.String";
    static final String jvmJavaLangObject = "java/lang/Object";
    static final String jvmJavaLangString = "java/lang/String";
    protected int arrayDim;
    protected Bytecode bytecode;
    protected String className;
    protected int exprType;
    static final int[] binOp = {43, 99, 98, 97, 96, 45, 103, 102, 101, 100, 42, 107, 106, 105, 104, 47, 111, 110, 109, 108, 37, 115, 114, 113, 112, 124, 0, 0, 129, 128, 94, 0, 0, 131, 130, 38, 0, 0, 127, 126, TokenId.LSHIFT, 0, 0, 121, 120, TokenId.RSHIFT, 0, 0, 123, 122, TokenId.ARSHIFT, 0, 0, 125, 124};
    private static final int[] ifOp = {TokenId.EQ, 159, 160, TokenId.NEQ, 160, 159, TokenId.LE, 164, 163, TokenId.GE, 162, 161, 60, 161, 162, 62, 163, 164};
    private static final int[] ifOp2 = {TokenId.EQ, 153, 154, TokenId.NEQ, 154, 153, TokenId.LE, 158, 157, TokenId.GE, 156, 155, 60, 155, 156, 62, 157, 158};
    private static final int[] castOp = {0, 144, 143, 142, 141, 0, 140, 139, 138, 137, 0, 136, 135, 134, 133, 0};
    private int tempVar = -1;
    TypeChecker typeChecker = null;
    protected boolean hasReturned = false;
    public boolean inStaticMethod = false;
    protected List<Integer> breakList = null;
    protected List<Integer> continueList = null;
    protected ReturnHook returnHooks = null;

    protected static int getArrayReadOp(int i, int i2) {
        if (i2 > 0) {
            return 50;
        }
        if (i == 301 || i == 303) {
            return 51;
        }
        if (i != 306) {
            if (i != 312) {
                if (i != 317) {
                    if (i != 324) {
                        if (i != 326) {
                            return i != 334 ? 50 : 53;
                        }
                        return 47;
                    }
                    return 46;
                }
                return 48;
            }
            return 49;
        }
        return 52;
    }

    public static int getArrayWriteOp(int i, int i2) {
        if (i2 > 0) {
            return 83;
        }
        if (i == 301 || i == 303) {
            return 84;
        }
        if (i != 306) {
            if (i != 312) {
                if (i != 317) {
                    if (i != 324) {
                        if (i != 326) {
                            return i != 334 ? 83 : 86;
                        }
                        return 80;
                    }
                    return 79;
                }
                return 81;
            }
            return 82;
        }
        return 85;
    }

    public static boolean is2word(int i, int i2) {
        return i2 == 0 && (i == 312 || i == 326);
    }

    public static boolean isRefType(int i) {
        return i == 307 || i == 412;
    }

    @Override // javassist.compiler.ast.Visitor
    public abstract void atArrayInit(ArrayInit arrayInit) throws CompileError;

    protected abstract void atArrayVariableAssign(ArrayInit arrayInit, int i, int i2, String str) throws CompileError;

    @Override // javassist.compiler.ast.Visitor
    public abstract void atCallExpr(CallExpr callExpr) throws CompileError;

    protected abstract void atFieldAssign(Expr expr, int i, ASTree aSTree, ASTree aSTree2, boolean z) throws CompileError;

    protected abstract void atFieldPlusPlus(int i, boolean z, ASTree aSTree, Expr expr, boolean z2) throws CompileError;

    protected abstract void atFieldRead(ASTree aSTree) throws CompileError;

    @Override // javassist.compiler.ast.Visitor
    public abstract void atMember(Member member) throws CompileError;

    @Override // javassist.compiler.ast.Visitor
    public abstract void atNewExpr(NewExpr newExpr) throws CompileError;

    protected abstract String getSuperName() throws CompileError;

    protected abstract String getThisName();

    protected abstract void insertDefaultSuperCall() throws CompileError;

    protected abstract String resolveClassName(String str) throws CompileError;

    protected abstract String resolveClassName(ASTList aSTList) throws CompileError;

    /* loaded from: classes2.dex */
    public static abstract class ReturnHook {
        ReturnHook next;

        protected abstract boolean doit(Bytecode bytecode, int i);

        public ReturnHook(CodeGen codeGen) {
            this.next = codeGen.returnHooks;
            codeGen.returnHooks = this;
        }

        public void remove(CodeGen codeGen) {
            codeGen.returnHooks = this.next;
        }
    }

    public CodeGen(Bytecode bytecode) {
        this.bytecode = bytecode;
    }

    public void setTypeChecker(TypeChecker typeChecker) {
        this.typeChecker = typeChecker;
    }

    public static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }

    public int getMaxLocals() {
        return this.bytecode.getMaxLocals();
    }

    public void setMaxLocals(int i) {
        this.bytecode.setMaxLocals(i);
    }

    public void incMaxLocals(int i) {
        this.bytecode.incMaxLocals(i);
    }

    protected int getTempVar() {
        if (this.tempVar < 0) {
            this.tempVar = getMaxLocals();
            incMaxLocals(2);
        }
        return this.tempVar;
    }

    protected int getLocalVar(Declarator declarator) {
        int localVar = declarator.getLocalVar();
        if (localVar < 0) {
            int maxLocals = getMaxLocals();
            declarator.setLocalVar(maxLocals);
            incMaxLocals(1);
            return maxLocals;
        }
        return localVar;
    }

    public static String toJvmArrayName(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i == 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                stringBuffer.append('[');
                i = i2;
            } else {
                stringBuffer.append('L');
                stringBuffer.append(str);
                stringBuffer.append(';');
                return stringBuffer.toString();
            }
        }
    }

    public static String toJvmTypeName(int i, int i2) {
        char c;
        if (i == 301) {
            c = 'Z';
        } else if (i == 303) {
            c = 'B';
        } else if (i == 306) {
            c = 'C';
        } else if (i == 312) {
            c = 'D';
        } else if (i != 317) {
            c = 'I';
            if (i != 324) {
                if (i == 326) {
                    c = 'J';
                } else if (i == 334) {
                    c = 'S';
                } else if (i == 344) {
                    c = 'V';
                }
            }
        } else {
            c = 'F';
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                stringBuffer.append('[');
                i2 = i3;
            } else {
                stringBuffer.append(c);
                return stringBuffer.toString();
            }
        }
    }

    public void compileExpr(ASTree aSTree) throws CompileError {
        doTypeCheck(aSTree);
        aSTree.accept(this);
    }

    public boolean compileBooleanExpr(boolean z, ASTree aSTree) throws CompileError {
        doTypeCheck(aSTree);
        return booleanExpr(z, aSTree);
    }

    public void doTypeCheck(ASTree aSTree) throws CompileError {
        TypeChecker typeChecker = this.typeChecker;
        if (typeChecker != null) {
            aSTree.accept(typeChecker);
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atASTList(ASTList aSTList) throws CompileError {
        fatal();
    }

    @Override // javassist.compiler.ast.Visitor
    public void atPair(Pair pair) throws CompileError {
        fatal();
    }

    @Override // javassist.compiler.ast.Visitor
    public void atSymbol(Symbol symbol) throws CompileError {
        fatal();
    }

    @Override // javassist.compiler.ast.Visitor
    public void atFieldDecl(FieldDecl fieldDecl) throws CompileError {
        fieldDecl.getInit().accept(this);
    }

    @Override // javassist.compiler.ast.Visitor
    public void atMethodDecl(MethodDecl methodDecl) throws CompileError {
        ASTList modifiers = methodDecl.getModifiers();
        setMaxLocals(1);
        while (modifiers != null) {
            modifiers = modifiers.tail();
            if (((Keyword) modifiers.head()).get() == 335) {
                setMaxLocals(0);
                this.inStaticMethod = true;
            }
        }
        for (ASTList params = methodDecl.getParams(); params != null; params = params.tail()) {
            atDeclarator((Declarator) params.head());
        }
        atMethodBody(methodDecl.getBody(), methodDecl.isConstructor(), methodDecl.getReturn().getType() == 344);
    }

    public void atMethodBody(Stmnt stmnt, boolean z, boolean z2) throws CompileError {
        if (stmnt == null) {
            return;
        }
        if (z && needsSuperCall(stmnt)) {
            insertDefaultSuperCall();
        }
        this.hasReturned = false;
        stmnt.accept(this);
        if (this.hasReturned) {
            return;
        }
        if (z2) {
            this.bytecode.addOpcode(177);
            this.hasReturned = true;
            return;
        }
        throw new CompileError("no return statement");
    }

    private boolean needsSuperCall(Stmnt stmnt) throws CompileError {
        ASTree head;
        if (stmnt.getOperator() == 66) {
            stmnt = (Stmnt) stmnt.head();
        }
        if (stmnt == null || stmnt.getOperator() != 69 || (head = stmnt.head()) == null || !(head instanceof Expr)) {
            return true;
        }
        Expr expr = (Expr) head;
        if (expr.getOperator() == 67) {
            ASTree head2 = expr.head();
            if (head2 instanceof Keyword) {
                int i = ((Keyword) head2).get();
                return (i == 339 || i == 336) ? false : true;
            }
            return true;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [javassist.compiler.ast.Stmnt] */
    /* JADX WARN: Type inference failed for: r6v1, types: [javassist.compiler.ast.ASTList] */
    /* JADX WARN: Type inference failed for: r6v2, types: [javassist.compiler.ast.ASTList] */
    @Override // javassist.compiler.ast.Visitor
    public void atStmnt(Stmnt stmnt) throws CompileError {
        if (stmnt == 0) {
            return;
        }
        int operator = stmnt.getOperator();
        if (operator == 69) {
            ASTree left = stmnt.getLeft();
            doTypeCheck(left);
            if (left instanceof AssignExpr) {
                atAssignExpr((AssignExpr) left, false);
            } else if (isPlusPlusExpr(left)) {
                Expr expr = (Expr) left;
                atPlusPlus(expr.getOperator(), expr.oprand1(), expr, false);
            } else {
                left.accept(this);
                if (is2word(this.exprType, this.arrayDim)) {
                    this.bytecode.addOpcode(88);
                } else if (this.exprType != 344) {
                    this.bytecode.addOpcode(87);
                }
            }
        } else if (operator == 68 || operator == 66) {
            while (stmnt != 0) {
                ASTree head = stmnt.head();
                stmnt = stmnt.tail();
                if (head != null) {
                    head.accept(this);
                }
            }
        } else if (operator == 320) {
            atIfStmnt(stmnt);
        } else if (operator == 346 || operator == 311) {
            atWhileStmnt(stmnt, operator == 346);
        } else if (operator == 318) {
            atForStmnt(stmnt);
        } else if (operator == 302 || operator == 309) {
            atBreakStmnt(stmnt, operator == 302);
        } else if (operator == 333) {
            atReturnStmnt(stmnt);
        } else if (operator == 340) {
            atThrowStmnt(stmnt);
        } else if (operator == 343) {
            atTryStmnt(stmnt);
        } else if (operator == 337) {
            atSwitchStmnt(stmnt);
        } else if (operator == 338) {
            atSyncStmnt(stmnt);
        } else {
            this.hasReturned = false;
            throw new CompileError("sorry, not supported statement: TokenId " + operator);
        }
    }

    private void atIfStmnt(Stmnt stmnt) throws CompileError {
        int i;
        ASTree head = stmnt.head();
        Stmnt stmnt2 = (Stmnt) stmnt.tail().head();
        Stmnt stmnt3 = (Stmnt) stmnt.tail().tail().head();
        boolean z = false;
        if (compileBooleanExpr(false, head)) {
            this.hasReturned = false;
            if (stmnt3 != null) {
                stmnt3.accept(this);
                return;
            }
            return;
        }
        int currentPc = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        this.hasReturned = false;
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        boolean z2 = this.hasReturned;
        this.hasReturned = false;
        if (stmnt3 == null || z2) {
            i = 0;
        } else {
            this.bytecode.addOpcode(167);
            i = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        Bytecode bytecode = this.bytecode;
        bytecode.write16bit(currentPc, (bytecode.currentPc() - currentPc) + 1);
        if (stmnt3 != null) {
            stmnt3.accept(this);
            if (!z2) {
                Bytecode bytecode2 = this.bytecode;
                bytecode2.write16bit(i, (bytecode2.currentPc() - i) + 1);
            }
            if (z2 && this.hasReturned) {
                z = true;
            }
            this.hasReturned = z;
        }
    }

    private void atWhileStmnt(Stmnt stmnt, boolean z) throws CompileError {
        int i;
        List<Integer> list = this.breakList;
        List<Integer> list2 = this.continueList;
        this.breakList = new ArrayList();
        this.continueList = new ArrayList();
        ASTree head = stmnt.head();
        Stmnt stmnt2 = (Stmnt) stmnt.tail();
        if (z) {
            this.bytecode.addOpcode(167);
            i = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        } else {
            i = 0;
        }
        int currentPc = this.bytecode.currentPc();
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        int currentPc2 = this.bytecode.currentPc();
        if (z) {
            this.bytecode.write16bit(i, (currentPc2 - i) + 1);
        }
        boolean compileBooleanExpr = compileBooleanExpr(true, head);
        if (compileBooleanExpr) {
            this.bytecode.addOpcode(167);
            compileBooleanExpr = this.breakList.size() == 0;
        }
        Bytecode bytecode = this.bytecode;
        bytecode.addIndex((currentPc - bytecode.currentPc()) + 1);
        patchGoto(this.breakList, this.bytecode.currentPc());
        patchGoto(this.continueList, currentPc2);
        this.continueList = list2;
        this.breakList = list;
        this.hasReturned = compileBooleanExpr;
    }

    public void patchGoto(List<Integer> list, int i) {
        for (Integer num : list) {
            int intValue = num.intValue();
            this.bytecode.write16bit(intValue, (i - intValue) + 1);
        }
    }

    private void atForStmnt(Stmnt stmnt) throws CompileError {
        int i;
        List<Integer> list = this.breakList;
        List<Integer> list2 = this.continueList;
        this.breakList = new ArrayList();
        this.continueList = new ArrayList();
        Stmnt stmnt2 = (Stmnt) stmnt.head();
        ASTList tail = stmnt.tail();
        ASTree head = tail.head();
        ASTList tail2 = tail.tail();
        Stmnt stmnt3 = (Stmnt) tail2.head();
        Stmnt stmnt4 = (Stmnt) tail2.tail();
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        int currentPc = this.bytecode.currentPc();
        if (head == null) {
            i = 0;
        } else if (compileBooleanExpr(false, head)) {
            this.continueList = list2;
            this.breakList = list;
            this.hasReturned = false;
            return;
        } else {
            i = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        if (stmnt4 != null) {
            stmnt4.accept(this);
        }
        int currentPc2 = this.bytecode.currentPc();
        if (stmnt3 != null) {
            stmnt3.accept(this);
        }
        this.bytecode.addOpcode(167);
        Bytecode bytecode = this.bytecode;
        bytecode.addIndex((currentPc - bytecode.currentPc()) + 1);
        int currentPc3 = this.bytecode.currentPc();
        if (head != null) {
            this.bytecode.write16bit(i, (currentPc3 - i) + 1);
        }
        patchGoto(this.breakList, currentPc3);
        patchGoto(this.continueList, currentPc2);
        this.continueList = list2;
        this.breakList = list;
        this.hasReturned = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0128 A[LOOP:3: B:110:0x0126->B:111:0x0128, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0167 A[LOOP:4: B:120:0x0161->B:122:0x0167, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0077 A[EDGE_INSN: B:125:0x0077->B:86:0x0077 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0096 A[EDGE_INSN: B:126:0x0096->B:93:0x0096 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0070 A[LOOP:0: B:83:0x006c->B:85:0x0070, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void atSwitchStmnt(javassist.compiler.ast.Stmnt r22) throws javassist.compiler.CompileError {
        /*
            Method dump skipped, instructions count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.compiler.CodeGen.atSwitchStmnt(javassist.compiler.ast.Stmnt):void");
    }

    private int computeLabel(ASTree aSTree) throws CompileError {
        doTypeCheck(aSTree);
        ASTree stripPlusExpr = TypeChecker.stripPlusExpr(aSTree);
        if (stripPlusExpr instanceof IntConst) {
            return (int) ((IntConst) stripPlusExpr).get();
        }
        throw new CompileError("bad case label");
    }

    private int computeStringLabel(ASTree aSTree, int i, List<Integer> list) throws CompileError {
        doTypeCheck(aSTree);
        ASTree stripPlusExpr = TypeChecker.stripPlusExpr(aSTree);
        if (stripPlusExpr instanceof StringL) {
            String str = ((StringL) stripPlusExpr).get();
            this.bytecode.addAload(i);
            this.bytecode.addLdc(str);
            this.bytecode.addInvokevirtual(jvmJavaLangString, "equals", "(Ljava/lang/Object;)Z");
            this.bytecode.addOpcode(153);
            Integer valueOf = Integer.valueOf(this.bytecode.currentPc());
            this.bytecode.addIndex(0);
            list.add(valueOf);
            return str.hashCode();
        }
        throw new CompileError("bad case label");
    }

    private void atBreakStmnt(Stmnt stmnt, boolean z) throws CompileError {
        if (stmnt.head() != null) {
            throw new CompileError("sorry, not support labeled break or continue");
        }
        this.bytecode.addOpcode(167);
        Integer valueOf = Integer.valueOf(this.bytecode.currentPc());
        this.bytecode.addIndex(0);
        if (z) {
            this.breakList.add(valueOf);
        } else {
            this.continueList.add(valueOf);
        }
    }

    protected void atReturnStmnt(Stmnt stmnt) throws CompileError {
        atReturnStmnt2(stmnt.getLeft());
    }

    public final void atReturnStmnt2(ASTree aSTree) throws CompileError {
        int i;
        if (aSTree == null) {
            i = 177;
        } else {
            compileExpr(aSTree);
            if (this.arrayDim <= 0) {
                int i2 = this.exprType;
                if (i2 == 312) {
                    i = 175;
                } else if (i2 == 317) {
                    i = 174;
                } else if (i2 == 326) {
                    i = 173;
                } else if (!isRefType(i2)) {
                    i = 172;
                }
            }
            i = 176;
        }
        for (ReturnHook returnHook = this.returnHooks; returnHook != null; returnHook = returnHook.next) {
            if (returnHook.doit(this.bytecode, i)) {
                this.hasReturned = true;
                return;
            }
        }
        this.bytecode.addOpcode(i);
        this.hasReturned = true;
    }

    private void atThrowStmnt(Stmnt stmnt) throws CompileError {
        compileExpr(stmnt.getLeft());
        if (this.exprType != 307 || this.arrayDim > 0) {
            throw new CompileError("bad throw statement");
        }
        this.bytecode.addOpcode(191);
        this.hasReturned = true;
    }

    protected void atTryStmnt(Stmnt stmnt) throws CompileError {
        this.hasReturned = false;
    }

    private void atSyncStmnt(Stmnt stmnt) throws CompileError {
        int i;
        int listSize = getListSize(this.breakList);
        int listSize2 = getListSize(this.continueList);
        compileExpr(stmnt.head());
        if (this.exprType != 307 && this.arrayDim == 0) {
            throw new CompileError("bad type expr for synchronized block");
        }
        Bytecode bytecode = this.bytecode;
        final int maxLocals = bytecode.getMaxLocals();
        bytecode.incMaxLocals(1);
        bytecode.addOpcode(89);
        bytecode.addAstore(maxLocals);
        bytecode.addOpcode(194);
        ReturnHook returnHook = new ReturnHook(this) { // from class: javassist.compiler.CodeGen.1
            {
                CodeGen.this = this;
            }

            @Override // javassist.compiler.CodeGen.ReturnHook
            protected boolean doit(Bytecode bytecode2, int i2) {
                bytecode2.addAload(maxLocals);
                bytecode2.addOpcode(195);
                return false;
            }
        };
        int currentPc = bytecode.currentPc();
        Stmnt stmnt2 = (Stmnt) stmnt.tail();
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        int currentPc2 = bytecode.currentPc();
        if (this.hasReturned) {
            i = 0;
        } else {
            returnHook.doit(bytecode, 0);
            bytecode.addOpcode(167);
            i = bytecode.currentPc();
            bytecode.addIndex(0);
        }
        if (currentPc < currentPc2) {
            int currentPc3 = bytecode.currentPc();
            returnHook.doit(bytecode, 0);
            bytecode.addOpcode(191);
            bytecode.addExceptionHandler(currentPc, currentPc2, currentPc3, 0);
        }
        if (!this.hasReturned) {
            bytecode.write16bit(i, (bytecode.currentPc() - i) + 1);
        }
        returnHook.remove(this);
        if (getListSize(this.breakList) != listSize || getListSize(this.continueList) != listSize2) {
            throw new CompileError("sorry, cannot break/continue in synchronized block");
        }
    }

    private static int getListSize(List<Integer> list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    private static boolean isPlusPlusExpr(ASTree aSTree) {
        if (aSTree instanceof Expr) {
            int operator = ((Expr) aSTree).getOperator();
            return operator == 362 || operator == 363;
        }
        return false;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atDeclarator(Declarator declarator) throws CompileError {
        declarator.setLocalVar(getMaxLocals());
        declarator.setClassName(resolveClassName(declarator.getClassName()));
        incMaxLocals(is2word(declarator.getType(), declarator.getArrayDim()) ? 2 : 1);
        ASTree initializer = declarator.getInitializer();
        if (initializer != null) {
            doTypeCheck(initializer);
            atVariableAssign(null, 61, null, declarator, initializer, false);
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atAssignExpr(AssignExpr assignExpr) throws CompileError {
        atAssignExpr(assignExpr, true);
    }

    protected void atAssignExpr(AssignExpr assignExpr, boolean z) throws CompileError {
        int operator = assignExpr.getOperator();
        ASTree oprand1 = assignExpr.oprand1();
        ASTree oprand2 = assignExpr.oprand2();
        if (oprand1 instanceof Variable) {
            Variable variable = (Variable) oprand1;
            atVariableAssign(assignExpr, operator, variable, variable.getDeclarator(), oprand2, z);
            return;
        }
        if (oprand1 instanceof Expr) {
            Expr expr = (Expr) oprand1;
            if (expr.getOperator() == 65) {
                atArrayAssign(assignExpr, operator, expr, oprand2, z);
                return;
            }
        }
        atFieldAssign(assignExpr, operator, oprand1, oprand2, z);
    }

    protected static void badAssign(Expr expr) throws CompileError {
        throw new CompileError(expr == null ? "incompatible type for assignment" : "incompatible type for " + expr.getName());
    }

    private void atVariableAssign(Expr expr, int i, Variable variable, Declarator declarator, ASTree aSTree, boolean z) throws CompileError {
        int type = declarator.getType();
        int arrayDim = declarator.getArrayDim();
        String className = declarator.getClassName();
        int localVar = getLocalVar(declarator);
        if (i != 61) {
            atVariable(variable);
        }
        if (expr == null && (aSTree instanceof ArrayInit)) {
            atArrayVariableAssign((ArrayInit) aSTree, type, arrayDim, className);
        } else {
            atAssignCore(expr, i, aSTree, type, arrayDim, className);
        }
        if (z) {
            if (is2word(type, arrayDim)) {
                this.bytecode.addOpcode(92);
            } else {
                this.bytecode.addOpcode(89);
            }
        }
        if (arrayDim > 0) {
            this.bytecode.addAstore(localVar);
        } else if (type == 312) {
            this.bytecode.addDstore(localVar);
        } else if (type == 317) {
            this.bytecode.addFstore(localVar);
        } else if (type == 326) {
            this.bytecode.addLstore(localVar);
        } else if (isRefType(type)) {
            this.bytecode.addAstore(localVar);
        } else {
            this.bytecode.addIstore(localVar);
        }
        this.exprType = type;
        this.arrayDim = arrayDim;
        this.className = className;
    }

    private void atArrayAssign(Expr expr, int i, Expr expr2, ASTree aSTree, boolean z) throws CompileError {
        arrayAccess(expr2.oprand1(), expr2.oprand2());
        if (i != 61) {
            this.bytecode.addOpcode(92);
            this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
        }
        int i2 = this.exprType;
        int i3 = this.arrayDim;
        String str = this.className;
        atAssignCore(expr, i, aSTree, i2, i3, str);
        if (z) {
            if (is2word(i2, i3)) {
                this.bytecode.addOpcode(94);
            } else {
                this.bytecode.addOpcode(91);
            }
        }
        this.bytecode.addOpcode(getArrayWriteOp(i2, i3));
        this.exprType = i2;
        this.arrayDim = i3;
        this.className = str;
    }

    public void atAssignCore(Expr expr, int i, ASTree aSTree, int i2, int i3, String str) throws CompileError {
        if (i == 354 && i3 == 0 && i2 == 307) {
            atStringPlusEq(expr, i2, i3, str, aSTree);
        } else {
            aSTree.accept(this);
            if (invalidDim(this.exprType, this.arrayDim, this.className, i2, i3, str, false) || (i != 61 && i3 > 0)) {
                badAssign(expr);
            }
            if (i != 61) {
                int i4 = assignOps[i - 351];
                int lookupBinOp = lookupBinOp(i4);
                if (lookupBinOp < 0) {
                    fatal();
                }
                atArithBinExpr(expr, i4, lookupBinOp, i2);
            }
        }
        if (i != 61 || (i3 == 0 && !isRefType(i2))) {
            atNumCastExpr(this.exprType, i2);
        }
    }

    private void atStringPlusEq(Expr expr, int i, int i2, String str, ASTree aSTree) throws CompileError {
        if (!jvmJavaLangString.equals(str)) {
            badAssign(expr);
        }
        convToString(i, i2);
        aSTree.accept(this);
        convToString(this.exprType, this.arrayDim);
        this.bytecode.addInvokevirtual(javaLangString, "concat", "(Ljava/lang/String;)Ljava/lang/String;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = jvmJavaLangString;
    }

    private boolean invalidDim(int i, int i2, String str, int i3, int i4, String str2, boolean z) {
        if (i2 == i4 || i == 412) {
            return false;
        }
        if (i4 == 0 && i3 == 307 && jvmJavaLangObject.equals(str2)) {
            return false;
        }
        return (z && i2 == 0 && i == 307 && jvmJavaLangObject.equals(str)) ? false : true;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atCondExpr(CondExpr condExpr) throws CompileError {
        if (booleanExpr(false, condExpr.condExpr())) {
            condExpr.elseExpr().accept(this);
            return;
        }
        int currentPc = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        condExpr.thenExpr().accept(this);
        int i = this.arrayDim;
        this.bytecode.addOpcode(167);
        int currentPc2 = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        Bytecode bytecode = this.bytecode;
        bytecode.write16bit(currentPc, (bytecode.currentPc() - currentPc) + 1);
        condExpr.elseExpr().accept(this);
        if (i != this.arrayDim) {
            throw new CompileError("type mismatch in ?:");
        }
        Bytecode bytecode2 = this.bytecode;
        bytecode2.write16bit(currentPc2, (bytecode2.currentPc() - currentPc2) + 1);
    }

    public static int lookupBinOp(int i) {
        int[] iArr = binOp;
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2 += 5) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atBinExpr(BinExpr binExpr) throws CompileError {
        int operator = binExpr.getOperator();
        int lookupBinOp = lookupBinOp(operator);
        if (lookupBinOp >= 0) {
            binExpr.oprand1().accept(this);
            ASTree oprand2 = binExpr.oprand2();
            if (oprand2 == null) {
                return;
            }
            int i = this.exprType;
            int i2 = this.arrayDim;
            String str = this.className;
            oprand2.accept(this);
            if (i2 != this.arrayDim) {
                throw new CompileError("incompatible array types");
            }
            if (operator == 43 && i2 == 0 && (i == 307 || this.exprType == 307)) {
                atStringConcatExpr(binExpr, i, i2, str);
                return;
            } else {
                atArithBinExpr(binExpr, operator, lookupBinOp, i);
                return;
            }
        }
        if (!booleanExpr(true, binExpr)) {
            this.bytecode.addIndex(7);
            this.bytecode.addIconst(0);
            this.bytecode.addOpcode(167);
            this.bytecode.addIndex(4);
        }
        this.bytecode.addIconst(1);
    }

    private void atArithBinExpr(Expr expr, int i, int i2, int i3) throws CompileError {
        int i4;
        if (this.arrayDim != 0) {
            badTypes(expr);
        }
        int i5 = this.exprType;
        if (i != 364 && i != 366 && i != 370) {
            convertOprandTypes(i3, i5, expr);
        } else if (i5 == 324 || i5 == 334 || i5 == 306 || i5 == 303) {
            this.exprType = i3;
        } else {
            badTypes(expr);
        }
        int typePrecedence = typePrecedence(this.exprType);
        if (typePrecedence >= 0 && (i4 = binOp[i2 + typePrecedence + 1]) != 0) {
            if (typePrecedence == 3 && this.exprType != 301) {
                this.exprType = TokenId.INT;
            }
            this.bytecode.addOpcode(i4);
            return;
        }
        badTypes(expr);
    }

    private void atStringConcatExpr(Expr expr, int i, int i2, String str) throws CompileError {
        int i3 = this.exprType;
        int i4 = this.arrayDim;
        boolean is2word = is2word(i3, i4);
        boolean z = i3 == 307 && jvmJavaLangString.equals(this.className);
        if (is2word) {
            convToString(i3, i4);
        }
        if (is2word(i, i2)) {
            this.bytecode.addOpcode(91);
            this.bytecode.addOpcode(87);
        } else {
            this.bytecode.addOpcode(95);
        }
        convToString(i, i2);
        this.bytecode.addOpcode(95);
        if (!is2word && !z) {
            convToString(i3, i4);
        }
        this.bytecode.addInvokevirtual(javaLangString, "concat", "(Ljava/lang/String;)Ljava/lang/String;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = jvmJavaLangString;
    }

    private void convToString(int i, int i2) throws CompileError {
        if (isRefType(i) || i2 > 0) {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;");
        } else if (i == 312) {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(D)Ljava/lang/String;");
        } else if (i == 317) {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(F)Ljava/lang/String;");
        } else if (i == 326) {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(J)Ljava/lang/String;");
        } else if (i == 301) {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(Z)Ljava/lang/String;");
        } else if (i == 306) {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(C)Ljava/lang/String;");
        } else if (i == 344) {
            throw new CompileError("void type expression");
        } else {
            this.bytecode.addInvokestatic(javaLangString, "valueOf", "(I)Ljava/lang/String;");
        }
    }

    private boolean booleanExpr(boolean z, ASTree aSTree) throws CompileError {
        int compOperator = getCompOperator(aSTree);
        if (compOperator == 358) {
            BinExpr binExpr = (BinExpr) aSTree;
            compareExpr(z, binExpr.getOperator(), compileOprands(binExpr), binExpr);
        } else if (compOperator == 33) {
            return booleanExpr(!z, ((Expr) aSTree).oprand1());
        } else {
            boolean z2 = compOperator == 369;
            if (z2 || compOperator == 368) {
                BinExpr binExpr2 = (BinExpr) aSTree;
                if (booleanExpr(!z2, binExpr2.oprand1())) {
                    this.exprType = 301;
                    this.arrayDim = 0;
                    return true;
                }
                int currentPc = this.bytecode.currentPc();
                this.bytecode.addIndex(0);
                if (booleanExpr(z2, binExpr2.oprand2())) {
                    this.bytecode.addOpcode(167);
                }
                Bytecode bytecode = this.bytecode;
                bytecode.write16bit(currentPc, (bytecode.currentPc() - currentPc) + 3);
                if (z != z2) {
                    this.bytecode.addIndex(6);
                    this.bytecode.addOpcode(167);
                }
            } else if (isAlwaysBranch(aSTree, z)) {
                this.exprType = 301;
                this.arrayDim = 0;
                return true;
            } else {
                aSTree.accept(this);
                if (this.exprType != 301 || this.arrayDim != 0) {
                    throw new CompileError("boolean expr is required");
                }
                this.bytecode.addOpcode(z ? 154 : 153);
            }
        }
        this.exprType = 301;
        this.arrayDim = 0;
        return false;
    }

    private static boolean isAlwaysBranch(ASTree aSTree, boolean z) {
        if (aSTree instanceof Keyword) {
            int i = ((Keyword) aSTree).get();
            if (z) {
                if (i != 410) {
                    return false;
                }
            } else if (i != 411) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static int getCompOperator(ASTree aSTree) throws CompileError {
        if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            int operator = expr.getOperator();
            if (operator == 33) {
                return 33;
            }
            return (!(expr instanceof BinExpr) || operator == 368 || operator == 369 || operator == 38 || operator == 124) ? operator : TokenId.EQ;
        }
        return 32;
    }

    private int compileOprands(BinExpr binExpr) throws CompileError {
        binExpr.oprand1().accept(this);
        int i = this.exprType;
        int i2 = this.arrayDim;
        binExpr.oprand2().accept(this);
        if (i2 != this.arrayDim) {
            if (i != 412 && this.exprType != 412) {
                throw new CompileError("incompatible array types");
            }
            if (this.exprType == 412) {
                this.arrayDim = i2;
            }
        }
        return i == 412 ? this.exprType : i;
    }

    private void compareExpr(boolean z, int i, int i2, BinExpr binExpr) throws CompileError {
        if (this.arrayDim == 0) {
            convertOprandTypes(i2, this.exprType, binExpr);
        }
        int typePrecedence = typePrecedence(this.exprType);
        if (typePrecedence == -1 || this.arrayDim > 0) {
            if (i == 358) {
                this.bytecode.addOpcode(z ? 165 : 166);
                return;
            } else if (i == 350) {
                this.bytecode.addOpcode(z ? 166 : 165);
                return;
            } else {
                badTypes(binExpr);
                return;
            }
        }
        int i3 = 0;
        if (typePrecedence == 3) {
            int[] iArr = ifOp;
            while (i3 < iArr.length) {
                if (iArr[i3] == i) {
                    this.bytecode.addOpcode(iArr[i3 + (z ? 1 : 2)]);
                    return;
                }
                i3 += 3;
            }
            badTypes(binExpr);
            return;
        }
        if (typePrecedence == 0) {
            if (i == 60 || i == 357) {
                this.bytecode.addOpcode(152);
            } else {
                this.bytecode.addOpcode(151);
            }
        } else if (typePrecedence == 1) {
            if (i == 60 || i == 357) {
                this.bytecode.addOpcode(150);
            } else {
                this.bytecode.addOpcode(149);
            }
        } else if (typePrecedence == 2) {
            this.bytecode.addOpcode(148);
        } else {
            fatal();
        }
        int[] iArr2 = ifOp2;
        while (i3 < iArr2.length) {
            if (iArr2[i3] == i) {
                this.bytecode.addOpcode(iArr2[i3 + (z ? 1 : 2)]);
                return;
            }
            i3 += 3;
        }
        badTypes(binExpr);
    }

    protected static void badTypes(Expr expr) throws CompileError {
        throw new CompileError("invalid types for " + expr.getName());
    }

    private static int typePrecedence(int i) {
        if (i == 312) {
            return 0;
        }
        if (i == 317) {
            return 1;
        }
        if (i == 326) {
            return 2;
        }
        return (isRefType(i) || i == 344) ? -1 : 3;
    }

    public static boolean isP_INT(int i) {
        return typePrecedence(i) == 3;
    }

    public static boolean rightIsStrong(int i, int i2) {
        int typePrecedence = typePrecedence(i);
        int typePrecedence2 = typePrecedence(i2);
        return typePrecedence >= 0 && typePrecedence2 >= 0 && typePrecedence > typePrecedence2;
    }

    private void convertOprandTypes(int i, int i2, Expr expr) throws CompileError {
        int i3;
        int i4;
        boolean z;
        int typePrecedence = typePrecedence(i);
        int typePrecedence2 = typePrecedence(i2);
        if (typePrecedence2 >= 0 || typePrecedence >= 0) {
            if (typePrecedence2 < 0 || typePrecedence < 0) {
                badTypes(expr);
            }
            if (typePrecedence <= typePrecedence2) {
                this.exprType = i;
                i3 = castOp[(typePrecedence2 * 4) + typePrecedence];
                z = false;
                i4 = typePrecedence;
            } else {
                i3 = castOp[(typePrecedence * 4) + typePrecedence2];
                i4 = typePrecedence2;
                z = true;
            }
            if (!z) {
                if (i3 != 0) {
                    this.bytecode.addOpcode(i3);
                }
            } else if (i4 == 0 || i4 == 2) {
                if (typePrecedence == 0 || typePrecedence == 2) {
                    this.bytecode.addOpcode(94);
                } else {
                    this.bytecode.addOpcode(93);
                }
                this.bytecode.addOpcode(88);
                this.bytecode.addOpcode(i3);
                this.bytecode.addOpcode(94);
                this.bytecode.addOpcode(88);
            } else if (i4 == 1) {
                if (typePrecedence == 2) {
                    this.bytecode.addOpcode(91);
                    this.bytecode.addOpcode(87);
                } else {
                    this.bytecode.addOpcode(95);
                }
                this.bytecode.addOpcode(i3);
                this.bytecode.addOpcode(95);
            } else {
                fatal();
            }
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atCastExpr(CastExpr castExpr) throws CompileError {
        String resolveClassName = resolveClassName(castExpr.getClassName());
        String checkCastExpr = checkCastExpr(castExpr, resolveClassName);
        int i = this.exprType;
        this.exprType = castExpr.getType();
        this.arrayDim = castExpr.getArrayDim();
        this.className = resolveClassName;
        if (checkCastExpr == null) {
            atNumCastExpr(i, this.exprType);
        } else {
            this.bytecode.addCheckcast(checkCastExpr);
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atInstanceOfExpr(InstanceOfExpr instanceOfExpr) throws CompileError {
        this.bytecode.addInstanceof(checkCastExpr(instanceOfExpr, resolveClassName(instanceOfExpr.getClassName())));
        this.exprType = 301;
        this.arrayDim = 0;
    }

    private String checkCastExpr(CastExpr castExpr, String str) throws CompileError {
        ASTree oprand = castExpr.getOprand();
        int arrayDim = castExpr.getArrayDim();
        int type = castExpr.getType();
        oprand.accept(this);
        int i = this.exprType;
        int i2 = this.arrayDim;
        if (invalidDim(i, i2, this.className, type, arrayDim, str, true) || i == 344 || type == 344) {
            throw new CompileError("invalid cast");
        }
        if (type != 307) {
            if (arrayDim > 0) {
                return toJvmTypeName(type, arrayDim);
            }
            return null;
        } else if (!isRefType(i) && i2 == 0) {
            throw new CompileError("invalid cast");
        } else {
            return toJvmArrayName(str, arrayDim);
        }
    }

    public void atNumCastExpr(int i, int i2) throws CompileError {
        if (i == i2) {
            return;
        }
        int typePrecedence = typePrecedence(i);
        int i3 = 0;
        int i4 = (typePrecedence < 0 || typePrecedence >= 3) ? 0 : castOp[(typePrecedence * 4) + typePrecedence(i2)];
        if (i2 == 312) {
            i3 = 135;
        } else if (i2 == 317) {
            i3 = 134;
        } else if (i2 == 326) {
            i3 = 133;
        } else if (i2 == 334) {
            i3 = 147;
        } else if (i2 == 306) {
            i3 = 146;
        } else if (i2 == 303) {
            i3 = 145;
        }
        if (i4 != 0) {
            this.bytecode.addOpcode(i4);
        }
        if ((i4 == 0 || i4 == 136 || i4 == 139 || i4 == 142) && i3 != 0) {
            this.bytecode.addOpcode(i3);
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atExpr(Expr expr) throws CompileError {
        int operator = expr.getOperator();
        ASTree oprand1 = expr.oprand1();
        if (operator == 46) {
            if (((Symbol) expr.oprand2()).get().equals("class")) {
                atClassObject(expr);
            } else {
                atFieldRead(expr);
            }
        } else if (operator == 35) {
            atFieldRead(expr);
        } else if (operator == 65) {
            atArrayRead(oprand1, expr.oprand2());
        } else if (operator == 362 || operator == 363) {
            atPlusPlus(operator, oprand1, expr, true);
        } else if (operator == 33) {
            if (!booleanExpr(false, expr)) {
                this.bytecode.addIndex(7);
                this.bytecode.addIconst(1);
                this.bytecode.addOpcode(167);
                this.bytecode.addIndex(4);
            }
            this.bytecode.addIconst(0);
        } else if (operator == 67) {
            fatal();
        } else {
            expr.oprand1().accept(this);
            int typePrecedence = typePrecedence(this.exprType);
            if (this.arrayDim > 0) {
                badType(expr);
            }
            if (operator == 45) {
                if (typePrecedence == 0) {
                    this.bytecode.addOpcode(119);
                } else if (typePrecedence == 1) {
                    this.bytecode.addOpcode(118);
                } else if (typePrecedence == 2) {
                    this.bytecode.addOpcode(117);
                } else if (typePrecedence == 3) {
                    this.bytecode.addOpcode(116);
                    this.exprType = TokenId.INT;
                } else {
                    badType(expr);
                }
            } else if (operator != 126) {
                if (operator != 43) {
                    fatal();
                } else if (typePrecedence == -1) {
                    badType(expr);
                }
            } else if (typePrecedence == 3) {
                this.bytecode.addIconst(-1);
                this.bytecode.addOpcode(130);
                this.exprType = TokenId.INT;
            } else if (typePrecedence == 2) {
                this.bytecode.addLconst(-1L);
                this.bytecode.addOpcode(131);
            } else {
                badType(expr);
            }
        }
    }

    protected static void badType(Expr expr) throws CompileError {
        throw new CompileError("invalid type for " + expr.getName());
    }

    public void atClassObject(Expr expr) throws CompileError {
        ASTree oprand1 = expr.oprand1();
        if (!(oprand1 instanceof Symbol)) {
            throw new CompileError("fatal error: badly parsed .class expr");
        }
        String str = ((Symbol) oprand1).get();
        if (str.startsWith("[")) {
            int indexOf = str.indexOf("[L");
            if (indexOf >= 0) {
                String substring = str.substring(indexOf + 2, str.length() - 1);
                String resolveClassName = resolveClassName(substring);
                if (!substring.equals(resolveClassName)) {
                    String jvmToJavaName = MemberResolver.jvmToJavaName(resolveClassName);
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        int i = indexOf - 1;
                        if (indexOf < 0) {
                            break;
                        }
                        stringBuffer.append('[');
                        indexOf = i;
                    }
                    stringBuffer.append('L').append(jvmToJavaName).append(';');
                    str = stringBuffer.toString();
                }
            }
        } else {
            str = MemberResolver.jvmToJavaName(resolveClassName(MemberResolver.javaToJvmName(str)));
        }
        atClassObject2(str);
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }

    public void atClassObject2(String str) throws CompileError {
        int currentPc = this.bytecode.currentPc();
        this.bytecode.addLdc(str);
        this.bytecode.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        int currentPc2 = this.bytecode.currentPc();
        this.bytecode.addOpcode(167);
        int currentPc3 = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        Bytecode bytecode = this.bytecode;
        bytecode.addExceptionHandler(currentPc, currentPc2, bytecode.currentPc(), "java.lang.ClassNotFoundException");
        this.bytecode.growStack(1);
        this.bytecode.addInvokestatic("javassist.runtime.DotClass", "fail", "(Ljava/lang/ClassNotFoundException;)Ljava/lang/NoClassDefFoundError;");
        this.bytecode.addOpcode(191);
        Bytecode bytecode2 = this.bytecode;
        bytecode2.write16bit(currentPc3, (bytecode2.currentPc() - currentPc3) + 1);
    }

    public void atArrayRead(ASTree aSTree, ASTree aSTree2) throws CompileError {
        arrayAccess(aSTree, aSTree2);
        this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
    }

    protected void arrayAccess(ASTree aSTree, ASTree aSTree2) throws CompileError {
        aSTree.accept(this);
        int i = this.exprType;
        int i2 = this.arrayDim;
        if (i2 == 0) {
            throw new CompileError("bad array access");
        }
        String str = this.className;
        aSTree2.accept(this);
        if (typePrecedence(this.exprType) != 3 || this.arrayDim > 0) {
            throw new CompileError("bad array index");
        }
        this.exprType = i;
        this.arrayDim = i2 - 1;
        this.className = str;
    }

    private void atPlusPlus(int i, ASTree aSTree, Expr expr, boolean z) throws CompileError {
        boolean z2 = aSTree == null;
        if (z2) {
            aSTree = expr.oprand2();
        }
        ASTree aSTree2 = aSTree;
        if (aSTree2 instanceof Variable) {
            Declarator declarator = ((Variable) aSTree2).getDeclarator();
            int type = declarator.getType();
            this.exprType = type;
            this.arrayDim = declarator.getArrayDim();
            int localVar = getLocalVar(declarator);
            if (this.arrayDim > 0) {
                badType(expr);
            }
            if (type == 312) {
                this.bytecode.addDload(localVar);
                if (z && z2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addDconst(1.0d);
                this.bytecode.addOpcode(i == 362 ? 99 : 103);
                if (z && !z2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addDstore(localVar);
                return;
            } else if (type == 326) {
                this.bytecode.addLload(localVar);
                if (z && z2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addLconst(1L);
                this.bytecode.addOpcode(i == 362 ? 97 : 101);
                if (z && !z2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addLstore(localVar);
                return;
            } else if (type == 317) {
                this.bytecode.addFload(localVar);
                if (z && z2) {
                    this.bytecode.addOpcode(89);
                }
                this.bytecode.addFconst(1.0f);
                this.bytecode.addOpcode(i == 362 ? 98 : 102);
                if (z && !z2) {
                    this.bytecode.addOpcode(89);
                }
                this.bytecode.addFstore(localVar);
                return;
            } else if (type != 303 && type != 306 && type != 334 && type != 324) {
                badType(expr);
                return;
            } else {
                if (z && z2) {
                    this.bytecode.addIload(localVar);
                }
                int i2 = i != 362 ? -1 : 1;
                if (localVar > 255) {
                    this.bytecode.addOpcode(Opcode.WIDE);
                    this.bytecode.addOpcode(132);
                    this.bytecode.addIndex(localVar);
                    this.bytecode.addIndex(i2);
                } else {
                    this.bytecode.addOpcode(132);
                    this.bytecode.add(localVar);
                    this.bytecode.add(i2);
                }
                if (!z || z2) {
                    return;
                }
                this.bytecode.addIload(localVar);
                return;
            }
        }
        if (aSTree2 instanceof Expr) {
            Expr expr2 = (Expr) aSTree2;
            if (expr2.getOperator() == 65) {
                atArrayPlusPlus(i, z2, expr2, z);
                return;
            }
        }
        atFieldPlusPlus(i, z2, aSTree2, expr, z);
    }

    public void atArrayPlusPlus(int i, boolean z, Expr expr, boolean z2) throws CompileError {
        arrayAccess(expr.oprand1(), expr.oprand2());
        int i2 = this.exprType;
        int i3 = this.arrayDim;
        if (i3 > 0) {
            badType(expr);
        }
        this.bytecode.addOpcode(92);
        this.bytecode.addOpcode(getArrayReadOp(i2, this.arrayDim));
        atPlusPlusCore(is2word(i2, i3) ? 94 : 91, z2, i, z, expr);
        this.bytecode.addOpcode(getArrayWriteOp(i2, i3));
    }

    public void atPlusPlusCore(int i, boolean z, int i2, boolean z2, Expr expr) throws CompileError {
        int i3 = this.exprType;
        if (z && z2) {
            this.bytecode.addOpcode(i);
        }
        if (i3 == 324 || i3 == 303 || i3 == 306 || i3 == 334) {
            this.bytecode.addIconst(1);
            this.bytecode.addOpcode(i2 == 362 ? 96 : 100);
            this.exprType = TokenId.INT;
        } else if (i3 == 326) {
            this.bytecode.addLconst(1L);
            this.bytecode.addOpcode(i2 == 362 ? 97 : 101);
        } else if (i3 == 317) {
            this.bytecode.addFconst(1.0f);
            this.bytecode.addOpcode(i2 == 362 ? 98 : 102);
        } else if (i3 == 312) {
            this.bytecode.addDconst(1.0d);
            this.bytecode.addOpcode(i2 == 362 ? 99 : 103);
        } else {
            badType(expr);
        }
        if (!z || z2) {
            return;
        }
        this.bytecode.addOpcode(i);
    }

    @Override // javassist.compiler.ast.Visitor
    public void atVariable(Variable variable) throws CompileError {
        Declarator declarator = variable.getDeclarator();
        this.exprType = declarator.getType();
        this.arrayDim = declarator.getArrayDim();
        this.className = declarator.getClassName();
        int localVar = getLocalVar(declarator);
        if (this.arrayDim > 0) {
            this.bytecode.addAload(localVar);
            return;
        }
        int i = this.exprType;
        if (i == 307) {
            this.bytecode.addAload(localVar);
        } else if (i == 312) {
            this.bytecode.addDload(localVar);
        } else if (i == 317) {
            this.bytecode.addFload(localVar);
        } else if (i == 326) {
            this.bytecode.addLload(localVar);
        } else {
            this.bytecode.addIload(localVar);
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atKeyword(Keyword keyword) throws CompileError {
        this.arrayDim = 0;
        int i = keyword.get();
        if (i != 336 && i != 339) {
            switch (i) {
                case TokenId.TRUE /* 410 */:
                    this.bytecode.addIconst(1);
                    this.exprType = 301;
                    return;
                case TokenId.FALSE /* 411 */:
                    this.bytecode.addIconst(0);
                    this.exprType = 301;
                    return;
                case TokenId.NULL /* 412 */:
                    this.bytecode.addOpcode(1);
                    this.exprType = TokenId.NULL;
                    return;
                default:
                    fatal();
                    return;
            }
        } else if (this.inStaticMethod) {
            throw new CompileError("not-available: ".concat(i == 339 ? "this" : "super"));
        } else {
            this.bytecode.addAload(0);
            this.exprType = 307;
            if (i == 339) {
                this.className = getThisName();
            } else {
                this.className = getSuperName();
            }
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atStringL(StringL stringL) throws CompileError {
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = jvmJavaLangString;
        this.bytecode.addLdc(stringL.get());
    }

    @Override // javassist.compiler.ast.Visitor
    public void atIntConst(IntConst intConst) throws CompileError {
        this.arrayDim = 0;
        long j = intConst.get();
        int type = intConst.getType();
        if (type == 402 || type == 401) {
            this.exprType = type == 402 ? TokenId.INT : 306;
            this.bytecode.addIconst((int) j);
            return;
        }
        this.exprType = TokenId.LONG;
        this.bytecode.addLconst(j);
    }

    @Override // javassist.compiler.ast.Visitor
    public void atDoubleConst(DoubleConst doubleConst) throws CompileError {
        this.arrayDim = 0;
        if (doubleConst.getType() == 405) {
            this.exprType = 312;
            this.bytecode.addDconst(doubleConst.get());
            return;
        }
        this.exprType = 317;
        this.bytecode.addFconst((float) doubleConst.get());
    }
}
