package javassist.compiler;

import java.util.ArrayList;
import java.util.List;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CodeGen;
import javassist.compiler.MemberResolver;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.ArrayInit;
import javassist.compiler.ast.CallExpr;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.Keyword;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.MethodDecl;
import javassist.compiler.ast.NewExpr;
import javassist.compiler.ast.Pair;
import javassist.compiler.ast.Stmnt;
import javassist.compiler.ast.Symbol;

/* loaded from: classes2.dex */
public class MemberCodeGen extends CodeGen {
    protected MemberResolver resolver;
    protected boolean resultStatic;
    protected CtClass thisClass;
    protected MethodInfo thisMethod;

    public MemberCodeGen(Bytecode bytecode, CtClass ctClass, ClassPool classPool) {
        super(bytecode);
        this.resolver = new MemberResolver(classPool);
        this.thisClass = ctClass;
        this.thisMethod = null;
    }

    public int getMajorVersion() {
        ClassFile classFile2 = this.thisClass.getClassFile2();
        if (classFile2 == null) {
            return ClassFile.MAJOR_VERSION;
        }
        return classFile2.getMajorVersion();
    }

    public void setThisMethod(CtMethod ctMethod) {
        this.thisMethod = ctMethod.getMethodInfo2();
        if (this.typeChecker != null) {
            this.typeChecker.setThisMethod(this.thisMethod);
        }
    }

    public CtClass getThisClass() {
        return this.thisClass;
    }

    @Override // javassist.compiler.CodeGen
    public String getThisName() {
        return MemberResolver.javaToJvmName(this.thisClass.getName());
    }

    @Override // javassist.compiler.CodeGen
    protected String getSuperName() throws CompileError {
        return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
    }

    @Override // javassist.compiler.CodeGen
    protected void insertDefaultSuperCall() throws CompileError {
        this.bytecode.addAload(0);
        this.bytecode.addInvokespecial(MemberResolver.getSuperclass(this.thisClass), "<init>", "()V");
    }

    /* loaded from: classes2.dex */
    static class JsrHook extends CodeGen.ReturnHook {
        CodeGen cgen;
        List<int[]> jsrList;
        int var;

        JsrHook(CodeGen codeGen) {
            super(codeGen);
            this.jsrList = new ArrayList();
            this.cgen = codeGen;
            this.var = -1;
        }

        private int getVar(int i) {
            if (this.var < 0) {
                this.var = this.cgen.getMaxLocals();
                this.cgen.incMaxLocals(i);
            }
            return this.var;
        }

        private void jsrJmp(Bytecode bytecode) {
            bytecode.addOpcode(167);
            this.jsrList.add(new int[]{bytecode.currentPc(), this.var});
            bytecode.addIndex(0);
        }

        @Override // javassist.compiler.CodeGen.ReturnHook
        protected boolean doit(Bytecode bytecode, int i) {
            switch (i) {
                case 172:
                    bytecode.addIstore(getVar(1));
                    jsrJmp(bytecode);
                    bytecode.addIload(this.var);
                    return false;
                case 173:
                    bytecode.addLstore(getVar(2));
                    jsrJmp(bytecode);
                    bytecode.addLload(this.var);
                    return false;
                case 174:
                    bytecode.addFstore(getVar(1));
                    jsrJmp(bytecode);
                    bytecode.addFload(this.var);
                    return false;
                case 175:
                    bytecode.addDstore(getVar(2));
                    jsrJmp(bytecode);
                    bytecode.addDload(this.var);
                    return false;
                case 176:
                    bytecode.addAstore(getVar(1));
                    jsrJmp(bytecode);
                    bytecode.addAload(this.var);
                    return false;
                case 177:
                    jsrJmp(bytecode);
                    return false;
                default:
                    throw new RuntimeException("fatal");
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class JsrHook2 extends CodeGen.ReturnHook {
        int target;
        int var;

        JsrHook2(CodeGen codeGen, int[] iArr) {
            super(codeGen);
            this.target = iArr[0];
            this.var = iArr[1];
        }

        @Override // javassist.compiler.CodeGen.ReturnHook
        protected boolean doit(Bytecode bytecode, int i) {
            switch (i) {
                case 172:
                    bytecode.addIstore(this.var);
                    break;
                case 173:
                    bytecode.addLstore(this.var);
                    break;
                case 174:
                    bytecode.addFstore(this.var);
                    break;
                case 175:
                    bytecode.addDstore(this.var);
                    break;
                case 176:
                    bytecode.addAstore(this.var);
                    break;
                case 177:
                    break;
                default:
                    throw new RuntimeException("fatal");
            }
            bytecode.addOpcode(167);
            bytecode.addIndex((this.target - bytecode.currentPc()) + 3);
            return true;
        }
    }

    @Override // javassist.compiler.CodeGen
    protected void atTryStmnt(Stmnt stmnt) throws CompileError {
        Bytecode bytecode = this.bytecode;
        Stmnt stmnt2 = (Stmnt) stmnt.getLeft();
        if (stmnt2 == null) {
            return;
        }
        ASTList aSTList = (ASTList) stmnt.getRight().getLeft();
        Stmnt stmnt3 = (Stmnt) stmnt.getRight().getRight().getLeft();
        ArrayList arrayList = new ArrayList();
        JsrHook jsrHook = stmnt3 != null ? new JsrHook(this) : null;
        int currentPc = bytecode.currentPc();
        stmnt2.accept(this);
        int currentPc2 = bytecode.currentPc();
        if (currentPc == currentPc2) {
            throw new CompileError("empty try block");
        }
        boolean z = !this.hasReturned;
        if (z) {
            bytecode.addOpcode(167);
            arrayList.add(Integer.valueOf(bytecode.currentPc()));
            bytecode.addIndex(0);
        }
        int maxLocals = getMaxLocals();
        incMaxLocals(1);
        while (aSTList != null) {
            Pair pair = (Pair) aSTList.head();
            aSTList = aSTList.tail();
            Declarator declarator = (Declarator) pair.getLeft();
            Stmnt stmnt4 = (Stmnt) pair.getRight();
            declarator.setLocalVar(maxLocals);
            CtClass lookupClassByJvmName = this.resolver.lookupClassByJvmName(declarator.getClassName());
            declarator.setClassName(MemberResolver.javaToJvmName(lookupClassByJvmName.getName()));
            bytecode.addExceptionHandler(currentPc, currentPc2, bytecode.currentPc(), lookupClassByJvmName);
            bytecode.growStack(1);
            bytecode.addAstore(maxLocals);
            this.hasReturned = false;
            if (stmnt4 != null) {
                stmnt4.accept(this);
            }
            if (!this.hasReturned) {
                bytecode.addOpcode(167);
                arrayList.add(Integer.valueOf(bytecode.currentPc()));
                bytecode.addIndex(0);
                z = true;
            }
        }
        if (stmnt3 != null) {
            jsrHook.remove(this);
            int currentPc3 = bytecode.currentPc();
            bytecode.addExceptionHandler(currentPc, currentPc3, currentPc3, 0);
            bytecode.growStack(1);
            bytecode.addAstore(maxLocals);
            this.hasReturned = false;
            stmnt3.accept(this);
            if (!this.hasReturned) {
                bytecode.addAload(maxLocals);
                bytecode.addOpcode(191);
            }
            addFinally(jsrHook.jsrList, stmnt3);
        }
        patchGoto(arrayList, bytecode.currentPc());
        this.hasReturned = !z;
        if (stmnt3 == null || !z) {
            return;
        }
        stmnt3.accept(this);
    }

    private void addFinally(List<int[]> list, Stmnt stmnt) throws CompileError {
        Bytecode bytecode = this.bytecode;
        for (int[] iArr : list) {
            int i = iArr[0];
            bytecode.write16bit(i, (bytecode.currentPc() - i) + 1);
            JsrHook2 jsrHook2 = new JsrHook2(this, iArr);
            stmnt.accept(this);
            jsrHook2.remove(this);
            if (!this.hasReturned) {
                bytecode.addOpcode(167);
                bytecode.addIndex((i + 3) - bytecode.currentPc());
            }
        }
    }

    @Override // javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atNewExpr(NewExpr newExpr) throws CompileError {
        if (newExpr.isArray()) {
            atNewArrayExpr(newExpr);
            return;
        }
        CtClass lookupClassByName = this.resolver.lookupClassByName(newExpr.getClassName());
        String name = lookupClassByName.getName();
        ASTList arguments = newExpr.getArguments();
        this.bytecode.addNew(name);
        this.bytecode.addOpcode(89);
        atMethodCallCore(lookupClassByName, "<init>", arguments, false, true, -1, null);
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = MemberResolver.javaToJvmName(name);
    }

    public void atNewArrayExpr(NewExpr newExpr) throws CompileError {
        int arrayType = newExpr.getArrayType();
        ASTList arraySize = newExpr.getArraySize();
        ASTList className = newExpr.getClassName();
        ArrayInit initializer = newExpr.getInitializer();
        if (arraySize.length() <= 1) {
            atNewArrayExpr2(arrayType, arraySize.head(), Declarator.astToClassName(className, '/'), initializer);
        } else if (initializer != null) {
            throw new CompileError("sorry, multi-dimensional array initializer for new is not supported");
        } else {
            atMultiNewArray(arrayType, className, arraySize);
        }
    }

    private void atNewArrayExpr2(int i, ASTree aSTree, String str, ArrayInit arrayInit) throws CompileError {
        int i2;
        String str2;
        if (arrayInit == null) {
            if (aSTree == null) {
                throw new CompileError("no array size");
            }
            aSTree.accept(this);
        } else if (aSTree == null) {
            this.bytecode.addIconst(arrayInit.length());
        } else {
            throw new CompileError("unnecessary array size specified for new");
        }
        if (i == 307) {
            str2 = resolveClassName(str);
            this.bytecode.addAnewarray(MemberResolver.jvmToJavaName(str2));
        } else {
            if (i == 301) {
                i2 = 4;
            } else if (i == 303) {
                i2 = 8;
            } else if (i == 306) {
                i2 = 5;
            } else if (i == 312) {
                i2 = 7;
            } else if (i == 317) {
                i2 = 6;
            } else if (i == 324) {
                i2 = 10;
            } else if (i == 326) {
                i2 = 11;
            } else if (i != 334) {
                badNewExpr();
                i2 = 0;
            } else {
                i2 = 9;
            }
            this.bytecode.addOpcode(188);
            this.bytecode.add(i2);
            str2 = null;
        }
        if (arrayInit != null) {
            int length = arrayInit.length();
            int i3 = 0;
            ArrayInit arrayInit2 = arrayInit;
            while (i3 < length) {
                this.bytecode.addOpcode(89);
                this.bytecode.addIconst(i3);
                arrayInit2.head().accept(this);
                if (!isRefType(i)) {
                    atNumCastExpr(this.exprType, i);
                }
                this.bytecode.addOpcode(getArrayWriteOp(i, 0));
                i3++;
                arrayInit2 = arrayInit2.tail();
            }
        }
        this.exprType = i;
        this.arrayDim = 1;
        this.className = str2;
    }

    private static void badNewExpr() throws CompileError {
        throw new CompileError("bad new expression");
    }

    @Override // javassist.compiler.CodeGen
    protected void atArrayVariableAssign(ArrayInit arrayInit, int i, int i2, String str) throws CompileError {
        atNewArrayExpr2(i, null, str, arrayInit);
    }

    @Override // javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atArrayInit(ArrayInit arrayInit) throws CompileError {
        throw new CompileError("array initializer is not supported");
    }

    protected void atMultiNewArray(int i, ASTList aSTList, ASTList aSTList2) throws CompileError {
        String jvmTypeName;
        int length = aSTList2.length();
        int i2 = 0;
        while (aSTList2 != null) {
            ASTree head = aSTList2.head();
            if (head == null) {
                break;
            }
            i2++;
            head.accept(this);
            if (this.exprType == 324) {
                aSTList2 = aSTList2.tail();
            } else {
                throw new CompileError("bad type for array size");
            }
        }
        this.exprType = i;
        this.arrayDim = length;
        if (i == 307) {
            this.className = resolveClassName(aSTList);
            jvmTypeName = toJvmArrayName(this.className, length);
        } else {
            jvmTypeName = toJvmTypeName(i, length);
        }
        this.bytecode.addMultiNewarray(jvmTypeName, i2);
    }

    @Override // javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atCallExpr(CallExpr callExpr) throws CompileError {
        boolean z;
        int i;
        CtClass ctClass;
        String str;
        boolean z2;
        boolean z3;
        CtClass lookupClassByJvmName;
        ASTree oprand1 = callExpr.oprand1();
        ASTList aSTList = (ASTList) callExpr.oprand2();
        MemberResolver.Method method = callExpr.getMethod();
        boolean z4 = true;
        boolean z5 = false;
        int i2 = -1;
        if (oprand1 instanceof Member) {
            String str2 = ((Member) oprand1).get();
            CtClass ctClass2 = this.thisClass;
            if (this.inStaticMethod || (method != null && method.isStatic())) {
                str = str2;
                z2 = true;
                z = false;
                i = -1;
                ctClass = ctClass2;
            } else {
                int currentPc = this.bytecode.currentPc();
                this.bytecode.addAload(0);
                str = str2;
                ctClass = ctClass2;
                i = currentPc;
                z2 = false;
                z = false;
            }
        } else if (oprand1 instanceof Keyword) {
            CtClass ctClass3 = this.thisClass;
            if (this.inStaticMethod) {
                throw new CompileError("a constructor cannot be static");
            }
            this.bytecode.addAload(0);
            if (((Keyword) oprand1).get() == 336) {
                ctClass3 = MemberResolver.getSuperclass(ctClass3);
            }
            z = true;
            i = -1;
            str = "<init>";
            ctClass = ctClass3;
            z2 = false;
        } else {
            CtClass ctClass4 = null;
            if (oprand1 instanceof Expr) {
                Expr expr = (Expr) oprand1;
                String str3 = ((Symbol) expr.oprand2()).get();
                int operator = expr.getOperator();
                if (operator == 35) {
                    ctClass4 = this.resolver.lookupClass(((Symbol) expr.oprand1()).get(), false);
                } else if (operator == 46) {
                    ASTree oprand12 = expr.oprand1();
                    String isDotSuper = TypeChecker.isDotSuper(oprand12);
                    if (isDotSuper != null) {
                        CtClass superInterface = MemberResolver.getSuperInterface(this.thisClass, isDotSuper);
                        if (this.inStaticMethod || (method != null && method.isStatic())) {
                            ctClass4 = superInterface;
                            z3 = true;
                            z5 = z3;
                        } else {
                            i2 = this.bytecode.currentPc();
                            this.bytecode.addAload(0);
                            ctClass4 = superInterface;
                            z3 = true;
                        }
                    } else {
                        z3 = (oprand12 instanceof Keyword) && ((Keyword) oprand12).get() == 336;
                        try {
                            oprand12.accept(this);
                        } catch (NoFieldException e) {
                            if (e.getExpr() != oprand12) {
                                throw e;
                            }
                            this.exprType = 307;
                            this.arrayDim = 0;
                            this.className = e.getField();
                            z5 = true;
                        }
                        if (this.arrayDim > 0) {
                            lookupClassByJvmName = this.resolver.lookupClass("java.lang.Object", true);
                        } else if (this.exprType == 307) {
                            lookupClassByJvmName = this.resolver.lookupClassByJvmName(this.className);
                        } else {
                            badMethod();
                        }
                        ctClass4 = lookupClassByJvmName;
                    }
                    z4 = z5;
                    z5 = z3;
                } else {
                    badMethod();
                    z4 = false;
                }
                str = str3;
                z = z5;
                i = i2;
                ctClass = ctClass4;
                z2 = z4;
            } else {
                fatal();
                z = false;
                i = -1;
                ctClass = null;
                str = null;
                z2 = false;
            }
        }
        atMethodCallCore(ctClass, str, aSTList, z2, z, i, method);
    }

    private static void badMethod() throws CompileError {
        throw new CompileError("bad method");
    }

    public void atMethodCallCore(CtClass ctClass, String str, ASTList aSTList, boolean z, boolean z2, int i, MemberResolver.Method method) throws CompileError {
        boolean z3;
        int methodArgsLength = getMethodArgsLength(aSTList);
        int[] iArr = new int[methodArgsLength];
        int[] iArr2 = new int[methodArgsLength];
        String[] strArr = new String[methodArgsLength];
        if (z || method == null || !method.isStatic()) {
            z3 = z;
        } else {
            this.bytecode.addOpcode(87);
            z3 = true;
        }
        this.bytecode.getStackDepth();
        atMethodArgs(aSTList, iArr, iArr2, strArr);
        MemberResolver.Method lookupMethod = method == null ? this.resolver.lookupMethod(ctClass, this.thisClass, this.thisMethod, str, iArr, iArr2, strArr) : method;
        if (lookupMethod == null) {
            throw new CompileError(str.equals("<init>") ? "constructor not found" : "Method " + str + " not found in " + ctClass.getName());
        }
        atMethodCallCore2(ctClass, str, z3, z2, i, lookupMethod);
    }

    private boolean isFromSameDeclaringClass(CtClass ctClass, CtClass ctClass2) {
        while (ctClass != null) {
            try {
                if (isEnclosing(ctClass, ctClass2)) {
                    return true;
                }
                ctClass = ctClass.getDeclaringClass();
            } catch (NotFoundException unused) {
                return false;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void atMethodCallCore2(javassist.CtClass r10, java.lang.String r11, boolean r12, boolean r13, int r14, javassist.compiler.MemberResolver.Method r15) throws javassist.compiler.CompileError {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.compiler.MemberCodeGen.atMethodCallCore2(javassist.CtClass, java.lang.String, boolean, boolean, int, javassist.compiler.MemberResolver$Method):void");
    }

    protected String getAccessiblePrivate(String str, String str2, String str3, MethodInfo methodInfo, CtClass ctClass) throws CompileError {
        AccessorMaker accessorMaker;
        if (isEnclosing(ctClass, this.thisClass) && (accessorMaker = ctClass.getAccessorMaker()) != null) {
            return accessorMaker.getMethodAccessor(str, str2, str3, methodInfo);
        }
        throw new CompileError("Method " + str + " is private");
    }

    protected String getAccessibleConstructor(String str, CtClass ctClass, MethodInfo methodInfo) throws CompileError {
        AccessorMaker accessorMaker;
        if (isEnclosing(ctClass, this.thisClass) && (accessorMaker = ctClass.getAccessorMaker()) != null) {
            return accessorMaker.getConstructor(ctClass, str, methodInfo);
        }
        throw new CompileError("the called constructor is private in " + ctClass.getName());
    }

    private boolean isEnclosing(CtClass ctClass, CtClass ctClass2) {
        while (ctClass2 != null) {
            try {
                ctClass2 = ctClass2.getDeclaringClass();
                if (ctClass2 == ctClass) {
                    return true;
                }
            } catch (NotFoundException unused) {
                return false;
            }
        }
        return false;
    }

    public int getMethodArgsLength(ASTList aSTList) {
        return ASTList.length(aSTList);
    }

    public void atMethodArgs(ASTList aSTList, int[] iArr, int[] iArr2, String[] strArr) throws CompileError {
        int i = 0;
        while (aSTList != null) {
            aSTList.head().accept(this);
            iArr[i] = this.exprType;
            iArr2[i] = this.arrayDim;
            strArr[i] = this.className;
            i++;
            aSTList = aSTList.tail();
        }
    }

    public void setReturnType(String str, boolean z, boolean z2) throws CompileError {
        int indexOf = str.indexOf(41);
        if (indexOf < 0) {
            badMethod();
        }
        int i = indexOf + 1;
        char charAt = str.charAt(i);
        int i2 = 0;
        while (charAt == '[') {
            i2++;
            i++;
            charAt = str.charAt(i);
        }
        this.arrayDim = i2;
        if (charAt == 'L') {
            int i3 = i + 1;
            int indexOf2 = str.indexOf(59, i3);
            if (indexOf2 < 0) {
                badMethod();
            }
            this.exprType = 307;
            this.className = str.substring(i3, indexOf2);
        } else {
            this.exprType = MemberResolver.descToType(charAt);
            this.className = null;
        }
        int i4 = this.exprType;
        if (z && z2) {
            if (is2word(i4, i2)) {
                this.bytecode.addOpcode(93);
                this.bytecode.addOpcode(88);
                this.bytecode.addOpcode(87);
            } else if (i4 == 344) {
                this.bytecode.addOpcode(87);
            } else {
                this.bytecode.addOpcode(95);
                this.bytecode.addOpcode(87);
            }
        }
    }

    @Override // javassist.compiler.CodeGen
    public void atFieldAssign(Expr expr, int i, ASTree aSTree, ASTree aSTree2, boolean z) throws CompileError {
        int i2 = 0;
        CtField fieldAccess = fieldAccess(aSTree, false);
        boolean z2 = this.resultStatic;
        int i3 = 89;
        if (i != 61 && !z2) {
            this.bytecode.addOpcode(89);
        }
        if (i == 61) {
            FieldInfo fieldInfo2 = fieldAccess.getFieldInfo2();
            setFieldType(fieldInfo2);
            if (isAccessibleField(fieldAccess, fieldInfo2) == null) {
                i2 = addFieldrefInfo(fieldAccess, fieldInfo2);
            }
        } else {
            i2 = atFieldRead(fieldAccess, z2);
        }
        int i4 = i2;
        int i5 = this.exprType;
        int i6 = this.arrayDim;
        String str = this.className;
        atAssignCore(expr, i, aSTree2, i5, i6, str);
        boolean is2word = is2word(i5, i6);
        if (z) {
            if (!z2) {
                i3 = is2word ? 93 : 90;
            } else if (is2word) {
                i3 = 92;
            }
            this.bytecode.addOpcode(i3);
        }
        atFieldAssignCore(fieldAccess, z2, i4, is2word);
        this.exprType = i5;
        this.arrayDim = i6;
        this.className = str;
    }

    private void atFieldAssignCore(CtField ctField, boolean z, int i, boolean z2) throws CompileError {
        if (i != 0) {
            if (z) {
                this.bytecode.add(179);
                this.bytecode.growStack(z2 ? -2 : -1);
            } else {
                this.bytecode.add(181);
                this.bytecode.growStack(z2 ? -3 : -2);
            }
            this.bytecode.addIndex(i);
            return;
        }
        CtClass declaringClass = ctField.getDeclaringClass();
        MethodInfo fieldSetter = declaringClass.getAccessorMaker().getFieldSetter(ctField.getFieldInfo2(), z);
        this.bytecode.addInvokestatic(declaringClass, fieldSetter.getName(), fieldSetter.getDescriptor());
    }

    @Override // javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atMember(Member member) throws CompileError {
        atFieldRead(member);
    }

    @Override // javassist.compiler.CodeGen
    protected void atFieldRead(ASTree aSTree) throws CompileError {
        CtField fieldAccess = fieldAccess(aSTree, true);
        if (fieldAccess == null) {
            atArrayLength(aSTree);
            return;
        }
        boolean z = this.resultStatic;
        ASTree constantFieldValue = TypeChecker.getConstantFieldValue(fieldAccess);
        if (constantFieldValue == null) {
            atFieldRead(fieldAccess, z);
            return;
        }
        constantFieldValue.accept(this);
        setFieldType(fieldAccess.getFieldInfo2());
    }

    private void atArrayLength(ASTree aSTree) throws CompileError {
        if (this.arrayDim == 0) {
            throw new CompileError(".length applied to a non array");
        }
        this.bytecode.addOpcode(190);
        this.exprType = TokenId.INT;
        this.arrayDim = 0;
    }

    private int atFieldRead(CtField ctField, boolean z) throws CompileError {
        FieldInfo fieldInfo2 = ctField.getFieldInfo2();
        boolean fieldType = setFieldType(fieldInfo2);
        AccessorMaker isAccessibleField = isAccessibleField(ctField, fieldInfo2);
        if (isAccessibleField != null) {
            MethodInfo fieldGetter = isAccessibleField.getFieldGetter(fieldInfo2, z);
            this.bytecode.addInvokestatic(ctField.getDeclaringClass(), fieldGetter.getName(), fieldGetter.getDescriptor());
            return 0;
        }
        int addFieldrefInfo = addFieldrefInfo(ctField, fieldInfo2);
        if (z) {
            this.bytecode.add(178);
            this.bytecode.growStack(fieldType ? 2 : 1);
        } else {
            this.bytecode.add(180);
            this.bytecode.growStack(fieldType ? 1 : 0);
        }
        this.bytecode.addIndex(addFieldrefInfo);
        return addFieldrefInfo;
    }

    private AccessorMaker isAccessibleField(CtField ctField, FieldInfo fieldInfo) throws CompileError {
        AccessorMaker accessorMaker;
        if (!AccessFlag.isPrivate(fieldInfo.getAccessFlags()) || ctField.getDeclaringClass() == this.thisClass) {
            return null;
        }
        CtClass declaringClass = ctField.getDeclaringClass();
        if (!isEnclosing(declaringClass, this.thisClass) || (accessorMaker = declaringClass.getAccessorMaker()) == null) {
            throw new CompileError("Field " + ctField.getName() + " in " + declaringClass.getName() + " is private.");
        }
        return accessorMaker;
    }

    private boolean setFieldType(FieldInfo fieldInfo) throws CompileError {
        String descriptor = fieldInfo.getDescriptor();
        char charAt = descriptor.charAt(0);
        int i = 0;
        int i2 = 0;
        while (charAt == '[') {
            i++;
            i2++;
            charAt = descriptor.charAt(i2);
        }
        this.arrayDim = i;
        this.exprType = MemberResolver.descToType(charAt);
        if (charAt == 'L') {
            int i3 = i2 + 1;
            this.className = descriptor.substring(i3, descriptor.indexOf(59, i3));
        } else {
            this.className = null;
        }
        if (i == 0) {
            return charAt == 'J' || charAt == 'D';
        }
        return false;
    }

    private int addFieldrefInfo(CtField ctField, FieldInfo fieldInfo) {
        ConstPool constPool = this.bytecode.getConstPool();
        return constPool.addFieldrefInfo(constPool.addClassInfo(ctField.getDeclaringClass().getName()), fieldInfo.getName(), fieldInfo.getDescriptor());
    }

    @Override // javassist.compiler.CodeGen
    public void atClassObject2(String str) throws CompileError {
        if (getMajorVersion() < 49) {
            super.atClassObject2(str);
        } else {
            this.bytecode.addLdc(this.bytecode.getConstPool().addClassInfo(str));
        }
    }

    @Override // javassist.compiler.CodeGen
    protected void atFieldPlusPlus(int i, boolean z, ASTree aSTree, Expr expr, boolean z2) throws CompileError {
        CtField fieldAccess = fieldAccess(aSTree, false);
        boolean z3 = this.resultStatic;
        int i2 = 89;
        if (!z3) {
            this.bytecode.addOpcode(89);
        }
        int atFieldRead = atFieldRead(fieldAccess, z3);
        boolean is2word = is2word(this.exprType, this.arrayDim);
        if (!z3) {
            i2 = is2word ? 93 : 90;
        } else if (is2word) {
            i2 = 92;
        }
        atPlusPlusCore(i2, z2, i, z, expr);
        atFieldAssignCore(fieldAccess, z3, atFieldRead, is2word);
    }

    protected CtField fieldAccess(ASTree aSTree, boolean z) throws CompileError {
        if (aSTree instanceof Member) {
            String str = ((Member) aSTree).get();
            try {
                CtField field = this.thisClass.getField(str);
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (!isStatic) {
                    if (this.inStaticMethod) {
                        throw new CompileError("not available in a static method: " + str);
                    }
                    this.bytecode.addAload(0);
                }
                this.resultStatic = isStatic;
                return field;
            } catch (NotFoundException unused) {
                throw new NoFieldException(str, aSTree);
            }
        }
        CtField ctField = null;
        if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            int operator = expr.getOperator();
            if (operator == 35) {
                CtField lookupField = this.resolver.lookupField(((Symbol) expr.oprand1()).get(), (Symbol) expr.oprand2());
                this.resultStatic = true;
                return lookupField;
            } else if (operator == 46) {
                try {
                    expr.oprand1().accept(this);
                    if (this.exprType == 307 && this.arrayDim == 0) {
                        ctField = this.resolver.lookupFieldByJvmName(this.className, (Symbol) expr.oprand2());
                    } else if (z && this.arrayDim > 0 && ((Symbol) expr.oprand2()).get().equals("length")) {
                        return null;
                    } else {
                        badLvalue();
                    }
                    boolean isStatic2 = Modifier.isStatic(ctField.getModifiers());
                    if (isStatic2) {
                        this.bytecode.addOpcode(87);
                    }
                    this.resultStatic = isStatic2;
                    return ctField;
                } catch (NoFieldException e) {
                    if (e.getExpr() != expr.oprand1()) {
                        throw e;
                    }
                    String field2 = e.getField();
                    CtField lookupFieldByJvmName2 = this.resolver.lookupFieldByJvmName2(field2, (Symbol) expr.oprand2(), aSTree);
                    this.resultStatic = true;
                    return lookupFieldByJvmName2;
                }
            } else {
                badLvalue();
            }
        } else {
            badLvalue();
        }
        this.resultStatic = false;
        return null;
    }

    private static void badLvalue() throws CompileError {
        throw new CompileError("bad l-value");
    }

    public CtClass[] makeParamList(MethodDecl methodDecl) throws CompileError {
        ASTList params = methodDecl.getParams();
        int i = 0;
        if (params == null) {
            return new CtClass[0];
        }
        CtClass[] ctClassArr = new CtClass[params.length()];
        while (params != null) {
            ctClassArr[i] = this.resolver.lookupClass((Declarator) params.head());
            params = params.tail();
            i++;
        }
        return ctClassArr;
    }

    public CtClass[] makeThrowsList(MethodDecl methodDecl) throws CompileError {
        ASTList aSTList = methodDecl.getThrows();
        if (aSTList == null) {
            return null;
        }
        CtClass[] ctClassArr = new CtClass[aSTList.length()];
        int i = 0;
        while (aSTList != null) {
            ctClassArr[i] = this.resolver.lookupClassByName((ASTList) aSTList.head());
            aSTList = aSTList.tail();
            i++;
        }
        return ctClassArr;
    }

    @Override // javassist.compiler.CodeGen
    protected String resolveClassName(ASTList aSTList) throws CompileError {
        return this.resolver.resolveClassName(aSTList);
    }

    @Override // javassist.compiler.CodeGen
    protected String resolveClassName(String str) throws CompileError {
        return this.resolver.resolveJvmClassName(str);
    }
}
