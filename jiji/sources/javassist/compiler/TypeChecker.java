package javassist.compiler;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;
import javassist.compiler.MemberResolver;
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
import javassist.compiler.ast.InstanceOfExpr;
import javassist.compiler.ast.IntConst;
import javassist.compiler.ast.Keyword;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.NewExpr;
import javassist.compiler.ast.StringL;
import javassist.compiler.ast.Symbol;
import javassist.compiler.ast.Variable;
import javassist.compiler.ast.Visitor;

/* loaded from: classes2.dex */
public class TypeChecker extends Visitor implements Opcode, TokenId {
    static final String javaLangObject = "java.lang.Object";
    static final String jvmJavaLangClass = "java/lang/Class";
    static final String jvmJavaLangObject = "java/lang/Object";
    static final String jvmJavaLangString = "java/lang/String";
    protected int arrayDim;
    protected String className;
    protected int exprType;
    protected MemberResolver resolver;
    protected CtClass thisClass;
    protected MethodInfo thisMethod = null;

    public TypeChecker(CtClass ctClass, ClassPool classPool) {
        this.resolver = new MemberResolver(classPool);
        this.thisClass = ctClass;
    }

    protected static String argTypesToString(int[] iArr, int[] iArr2, String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        int length = iArr.length;
        if (length > 0) {
            int i = 0;
            while (true) {
                typeToString(stringBuffer, iArr[i], iArr2[i], strArr[i]);
                i++;
                if (i >= length) {
                    break;
                }
                stringBuffer.append(',');
            }
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    protected static StringBuffer typeToString(StringBuffer stringBuffer, int i, int i2, String str) {
        String str2;
        if (i == 307) {
            str2 = MemberResolver.jvmToJavaName(str);
        } else if (i == 412) {
            str2 = "Object";
        } else {
            try {
                str2 = MemberResolver.getTypeName(i);
            } catch (CompileError unused) {
                str2 = "?";
            }
        }
        stringBuffer.append(str2);
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return stringBuffer;
            }
            stringBuffer.append("[]");
            i2 = i3;
        }
    }

    public void setThisMethod(MethodInfo methodInfo) {
        this.thisMethod = methodInfo;
    }

    protected static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }

    protected String getThisName() {
        return MemberResolver.javaToJvmName(this.thisClass.getName());
    }

    protected String getSuperName() throws CompileError {
        return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
    }

    protected String resolveClassName(ASTList aSTList) throws CompileError {
        return this.resolver.resolveClassName(aSTList);
    }

    protected String resolveClassName(String str) throws CompileError {
        return this.resolver.resolveJvmClassName(str);
    }

    @Override // javassist.compiler.ast.Visitor
    public void atNewExpr(NewExpr newExpr) throws CompileError {
        if (newExpr.isArray()) {
            atNewArrayExpr(newExpr);
            return;
        }
        CtClass lookupClassByName = this.resolver.lookupClassByName(newExpr.getClassName());
        String name = lookupClassByName.getName();
        atMethodCallCore(lookupClassByName, "<init>", newExpr.getArguments());
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = MemberResolver.javaToJvmName(name);
    }

    public void atNewArrayExpr(NewExpr newExpr) throws CompileError {
        int arrayType = newExpr.getArrayType();
        ASTList arraySize = newExpr.getArraySize();
        ASTList className = newExpr.getClassName();
        ArrayInit initializer = newExpr.getInitializer();
        if (initializer != null) {
            initializer.accept(this);
        }
        if (arraySize.length() > 1) {
            atMultiNewArray(arrayType, className, arraySize);
            return;
        }
        ASTree head = arraySize.head();
        if (head != null) {
            head.accept(this);
        }
        this.exprType = arrayType;
        this.arrayDim = 1;
        if (arrayType == 307) {
            this.className = resolveClassName(className);
        } else {
            this.className = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [javassist.compiler.ast.ArrayInit] */
    /* JADX WARN: Type inference failed for: r2v1, types: [javassist.compiler.ast.ASTList] */
    /* JADX WARN: Type inference failed for: r2v2, types: [javassist.compiler.ast.ASTList] */
    @Override // javassist.compiler.ast.Visitor
    public void atArrayInit(ArrayInit arrayInit) throws CompileError {
        while (arrayInit != 0) {
            ASTree head = arrayInit.head();
            arrayInit = arrayInit.tail();
            if (head != null) {
                head.accept(this);
            }
        }
    }

    protected void atMultiNewArray(int i, ASTList aSTList, ASTList aSTList2) throws CompileError {
        int length = aSTList2.length();
        while (aSTList2 != null) {
            ASTree head = aSTList2.head();
            if (head == null) {
                break;
            }
            head.accept(this);
            aSTList2 = aSTList2.tail();
        }
        this.exprType = i;
        this.arrayDim = length;
        if (i == 307) {
            this.className = resolveClassName(aSTList);
        } else {
            this.className = null;
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atAssignExpr(AssignExpr assignExpr) throws CompileError {
        int operator = assignExpr.getOperator();
        ASTree oprand1 = assignExpr.oprand1();
        ASTree oprand2 = assignExpr.oprand2();
        if (oprand1 instanceof Variable) {
            Variable variable = (Variable) oprand1;
            atVariableAssign(assignExpr, operator, variable, variable.getDeclarator(), oprand2);
            return;
        }
        if (oprand1 instanceof Expr) {
            Expr expr = (Expr) oprand1;
            if (expr.getOperator() == 65) {
                atArrayAssign(assignExpr, operator, expr, oprand2);
                return;
            }
        }
        atFieldAssign(assignExpr, operator, oprand1, oprand2);
    }

    private void atVariableAssign(Expr expr, int i, Variable variable, Declarator declarator, ASTree aSTree) throws CompileError {
        int type = declarator.getType();
        int arrayDim = declarator.getArrayDim();
        String className = declarator.getClassName();
        if (i != 61) {
            atVariable(variable);
        }
        aSTree.accept(this);
        this.exprType = type;
        this.arrayDim = arrayDim;
        this.className = className;
    }

    private void atArrayAssign(Expr expr, int i, Expr expr2, ASTree aSTree) throws CompileError {
        atArrayRead(expr2.oprand1(), expr2.oprand2());
        int i2 = this.exprType;
        int i3 = this.arrayDim;
        String str = this.className;
        aSTree.accept(this);
        this.exprType = i2;
        this.arrayDim = i3;
        this.className = str;
    }

    public void atFieldAssign(Expr expr, int i, ASTree aSTree, ASTree aSTree2) throws CompileError {
        atFieldRead(fieldAccess(aSTree));
        int i2 = this.exprType;
        int i3 = this.arrayDim;
        String str = this.className;
        aSTree2.accept(this);
        this.exprType = i2;
        this.arrayDim = i3;
        this.className = str;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atCondExpr(CondExpr condExpr) throws CompileError {
        booleanExpr(condExpr.condExpr());
        condExpr.thenExpr().accept(this);
        int i = this.exprType;
        int i2 = this.arrayDim;
        condExpr.elseExpr().accept(this);
        if (i2 == 0 && i2 == this.arrayDim) {
            if (CodeGen.rightIsStrong(i, this.exprType)) {
                condExpr.setThen(new CastExpr(this.exprType, 0, condExpr.thenExpr()));
            } else if (CodeGen.rightIsStrong(this.exprType, i)) {
                condExpr.setElse(new CastExpr(i, 0, condExpr.elseExpr()));
                this.exprType = i;
            }
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atBinExpr(BinExpr binExpr) throws CompileError {
        int operator = binExpr.getOperator();
        if (CodeGen.lookupBinOp(operator) < 0) {
            booleanExpr(binExpr);
        } else if (operator == 43) {
            Expr atPlusExpr = atPlusExpr(binExpr);
            if (atPlusExpr != null) {
                binExpr.setOprand1(CallExpr.makeCall(Expr.make(46, atPlusExpr, new Member("toString")), null));
                binExpr.setOprand2(null);
                this.className = jvmJavaLangString;
            }
        } else {
            ASTree oprand1 = binExpr.oprand1();
            ASTree oprand2 = binExpr.oprand2();
            oprand1.accept(this);
            int i = this.exprType;
            oprand2.accept(this);
            if (isConstant(binExpr, operator, oprand1, oprand2)) {
                return;
            }
            computeBinExprType(binExpr, operator, i);
        }
    }

    private Expr atPlusExpr(BinExpr binExpr) throws CompileError {
        ASTree oprand1 = binExpr.oprand1();
        ASTree oprand2 = binExpr.oprand2();
        if (oprand2 == null) {
            oprand1.accept(this);
            return null;
        }
        if (isPlusExpr(oprand1)) {
            Expr atPlusExpr = atPlusExpr((BinExpr) oprand1);
            if (atPlusExpr != null) {
                oprand2.accept(this);
                this.exprType = 307;
                this.arrayDim = 0;
                this.className = "java/lang/StringBuffer";
                return makeAppendCall(atPlusExpr, oprand2);
            }
        } else {
            oprand1.accept(this);
        }
        int i = this.exprType;
        int i2 = this.arrayDim;
        String str = this.className;
        oprand2.accept(this);
        if (isConstant(binExpr, 43, oprand1, oprand2)) {
            return null;
        }
        if ((i == 307 && i2 == 0 && jvmJavaLangString.equals(str)) || (this.exprType == 307 && this.arrayDim == 0 && jvmJavaLangString.equals(this.className))) {
            NewExpr newExpr = new NewExpr(ASTList.make(new Symbol("java"), new Symbol("lang"), new Symbol("StringBuffer")), null);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/StringBuffer";
            return makeAppendCall(makeAppendCall(newExpr, oprand1), oprand2);
        }
        computeBinExprType(binExpr, 43, i);
        return null;
    }

    private boolean isConstant(BinExpr binExpr, int i, ASTree aSTree, ASTree aSTree2) throws CompileError {
        ASTree compute;
        ASTree stripPlusExpr = stripPlusExpr(aSTree);
        ASTree stripPlusExpr2 = stripPlusExpr(aSTree2);
        if ((stripPlusExpr instanceof StringL) && (stripPlusExpr2 instanceof StringL) && i == 43) {
            compute = new StringL(((StringL) stripPlusExpr).get() + ((StringL) stripPlusExpr2).get());
        } else if (stripPlusExpr instanceof IntConst) {
            compute = ((IntConst) stripPlusExpr).compute(i, stripPlusExpr2);
        } else {
            compute = stripPlusExpr instanceof DoubleConst ? ((DoubleConst) stripPlusExpr).compute(i, stripPlusExpr2) : null;
        }
        if (compute == null) {
            return false;
        }
        binExpr.setOperator(43);
        binExpr.setOprand1(compute);
        binExpr.setOprand2(null);
        compute.accept(this);
        return true;
    }

    public static ASTree stripPlusExpr(ASTree aSTree) {
        ASTree constantFieldValue;
        if (aSTree instanceof BinExpr) {
            BinExpr binExpr = (BinExpr) aSTree;
            if (binExpr.getOperator() == 43 && binExpr.oprand2() == null) {
                return binExpr.getLeft();
            }
        } else if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            int operator = expr.getOperator();
            if (operator == 35) {
                ASTree constantFieldValue2 = getConstantFieldValue((Member) expr.oprand2());
                if (constantFieldValue2 != null) {
                    return constantFieldValue2;
                }
            } else if (operator == 43 && expr.getRight() == null) {
                return expr.getLeft();
            }
        } else if ((aSTree instanceof Member) && (constantFieldValue = getConstantFieldValue((Member) aSTree)) != null) {
            return constantFieldValue;
        }
        return aSTree;
    }

    private static ASTree getConstantFieldValue(Member member) {
        return getConstantFieldValue(member.getField());
    }

    public static ASTree getConstantFieldValue(CtField ctField) {
        Object constantValue;
        Keyword keyword = null;
        if (ctField == null || (constantValue = ctField.getConstantValue()) == null) {
            return null;
        }
        if (constantValue instanceof String) {
            return new StringL((String) constantValue);
        }
        boolean z = constantValue instanceof Double;
        if (z || (constantValue instanceof Float)) {
            return new DoubleConst(((Number) constantValue).doubleValue(), z ? TokenId.DoubleConstant : TokenId.FloatConstant);
        } else if (constantValue instanceof Number) {
            return new IntConst(((Number) constantValue).longValue(), constantValue instanceof Long ? 403 : 402);
        } else {
            if (constantValue instanceof Boolean) {
                keyword = new Keyword(((Boolean) constantValue).booleanValue() ? TokenId.TRUE : TokenId.FALSE);
            }
            return keyword;
        }
    }

    private static boolean isPlusExpr(ASTree aSTree) {
        return (aSTree instanceof BinExpr) && ((BinExpr) aSTree).getOperator() == 43;
    }

    private static Expr makeAppendCall(ASTree aSTree, ASTree aSTree2) {
        return CallExpr.makeCall(Expr.make(46, aSTree, new Member("append")), new ASTList(aSTree2));
    }

    private void computeBinExprType(BinExpr binExpr, int i, int i2) throws CompileError {
        int i3 = this.exprType;
        if (i == 364 || i == 366 || i == 370) {
            this.exprType = i2;
        } else {
            insertCast(binExpr, i2, i3);
        }
        if (!CodeGen.isP_INT(this.exprType) || this.exprType == 301) {
            return;
        }
        this.exprType = TokenId.INT;
    }

    private void booleanExpr(ASTree aSTree) throws CompileError {
        int compOperator = CodeGen.getCompOperator(aSTree);
        if (compOperator == 358) {
            BinExpr binExpr = (BinExpr) aSTree;
            binExpr.oprand1().accept(this);
            int i = this.exprType;
            int i2 = this.arrayDim;
            binExpr.oprand2().accept(this);
            if (i2 == 0 && this.arrayDim == 0) {
                insertCast(binExpr, i, this.exprType);
            }
        } else if (compOperator == 33) {
            ((Expr) aSTree).oprand1().accept(this);
        } else if (compOperator == 369 || compOperator == 368) {
            BinExpr binExpr2 = (BinExpr) aSTree;
            binExpr2.oprand1().accept(this);
            binExpr2.oprand2().accept(this);
        } else {
            aSTree.accept(this);
        }
        this.exprType = 301;
        this.arrayDim = 0;
    }

    private void insertCast(BinExpr binExpr, int i, int i2) throws CompileError {
        if (CodeGen.rightIsStrong(i, i2)) {
            binExpr.setLeft(new CastExpr(i2, 0, binExpr.oprand1()));
        } else {
            this.exprType = i;
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atCastExpr(CastExpr castExpr) throws CompileError {
        String resolveClassName = resolveClassName(castExpr.getClassName());
        castExpr.getOprand().accept(this);
        this.exprType = castExpr.getType();
        this.arrayDim = castExpr.getArrayDim();
        this.className = resolveClassName;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atInstanceOfExpr(InstanceOfExpr instanceOfExpr) throws CompileError {
        instanceOfExpr.getOprand().accept(this);
        this.exprType = 301;
        this.arrayDim = 0;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atExpr(Expr expr) throws CompileError {
        int operator = expr.getOperator();
        ASTree oprand1 = expr.oprand1();
        if (operator == 46) {
            String str = ((Symbol) expr.oprand2()).get();
            if (str.equals("length")) {
                try {
                    atArrayLength(expr);
                } catch (NoFieldException unused) {
                    atFieldRead(expr);
                }
            } else if (str.equals("class")) {
                atClassObject(expr);
            } else {
                atFieldRead(expr);
            }
        } else if (operator == 35) {
            if (((Symbol) expr.oprand2()).get().equals("class")) {
                atClassObject(expr);
            } else {
                atFieldRead(expr);
            }
        } else if (operator == 65) {
            atArrayRead(oprand1, expr.oprand2());
        } else if (operator == 362 || operator == 363) {
            atPlusPlus(operator, oprand1, expr);
        } else if (operator == 33) {
            booleanExpr(expr);
        } else if (operator == 67) {
            fatal();
        } else {
            oprand1.accept(this);
            if (isConstant(expr, operator, oprand1)) {
                return;
            }
            if ((operator == 45 || operator == 126) && CodeGen.isP_INT(this.exprType)) {
                this.exprType = TokenId.INT;
            }
        }
    }

    private boolean isConstant(Expr expr, int i, ASTree aSTree) {
        long j;
        ASTree stripPlusExpr = stripPlusExpr(aSTree);
        if (stripPlusExpr instanceof IntConst) {
            IntConst intConst = (IntConst) stripPlusExpr;
            long j2 = intConst.get();
            if (i == 45) {
                j = -j2;
            } else if (i != 126) {
                return false;
            } else {
                j = ~j2;
            }
            intConst.set(j);
        } else {
            if (stripPlusExpr instanceof DoubleConst) {
                DoubleConst doubleConst = (DoubleConst) stripPlusExpr;
                if (i == 45) {
                    doubleConst.set(-doubleConst.get());
                }
            }
            return false;
        }
        expr.setOperator(43);
        return true;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atCallExpr(CallExpr callExpr) throws CompileError {
        String str;
        CtClass ctClass;
        ASTree oprand1 = callExpr.oprand1();
        ASTList aSTList = (ASTList) callExpr.oprand2();
        if (oprand1 instanceof Member) {
            str = ((Member) oprand1).get();
            ctClass = this.thisClass;
        } else if (oprand1 instanceof Keyword) {
            if (((Keyword) oprand1).get() == 336) {
                ctClass = MemberResolver.getSuperclass(this.thisClass);
            } else {
                ctClass = this.thisClass;
            }
            str = "<init>";
        } else {
            CtClass ctClass2 = null;
            if (oprand1 instanceof Expr) {
                Expr expr = (Expr) oprand1;
                String str2 = ((Symbol) expr.oprand2()).get();
                int operator = expr.getOperator();
                if (operator == 35) {
                    ctClass2 = this.resolver.lookupClass(((Symbol) expr.oprand1()).get(), false);
                } else if (operator == 46) {
                    ASTree oprand12 = expr.oprand1();
                    String isDotSuper = isDotSuper(oprand12);
                    if (isDotSuper != null) {
                        ctClass2 = MemberResolver.getSuperInterface(this.thisClass, isDotSuper);
                    } else {
                        try {
                            oprand12.accept(this);
                        } catch (NoFieldException e) {
                            if (e.getExpr() != oprand12) {
                                throw e;
                            }
                            this.exprType = 307;
                            this.arrayDim = 0;
                            this.className = e.getField();
                            expr.setOperator(35);
                            expr.setOprand1(new Symbol(MemberResolver.jvmToJavaName(this.className)));
                        }
                        if (this.arrayDim > 0) {
                            ctClass2 = this.resolver.lookupClass(javaLangObject, true);
                        } else if (this.exprType == 307) {
                            ctClass2 = this.resolver.lookupClassByJvmName(this.className);
                        } else {
                            badMethod();
                        }
                    }
                } else {
                    badMethod();
                }
                str = str2;
                ctClass = ctClass2;
            } else {
                fatal();
                str = null;
                ctClass = null;
            }
        }
        callExpr.setMethod(atMethodCallCore(ctClass, str, aSTList));
    }

    private static void badMethod() throws CompileError {
        throw new CompileError("bad method");
    }

    public static String isDotSuper(ASTree aSTree) {
        if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            if (expr.getOperator() == 46) {
                ASTree oprand2 = expr.oprand2();
                if ((oprand2 instanceof Keyword) && ((Keyword) oprand2).get() == 336) {
                    return ((Symbol) expr.oprand1()).get();
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public MemberResolver.Method atMethodCallCore(CtClass ctClass, String str, ASTList aSTList) throws CompileError {
        String str2;
        int methodArgsLength = getMethodArgsLength(aSTList);
        int[] iArr = new int[methodArgsLength];
        int[] iArr2 = new int[methodArgsLength];
        String[] strArr = new String[methodArgsLength];
        atMethodArgs(aSTList, iArr, iArr2, strArr);
        MemberResolver.Method lookupMethod = this.resolver.lookupMethod(ctClass, this.thisClass, this.thisMethod, str, iArr, iArr2, strArr);
        if (lookupMethod == null) {
            String name = ctClass.getName();
            String argTypesToString = argTypesToString(iArr, iArr2, strArr);
            if (str.equals("<init>")) {
                str2 = "cannot find constructor " + name + argTypesToString;
            } else {
                str2 = str + argTypesToString + " not found in " + name;
            }
            throw new CompileError(str2);
        }
        setReturnType(lookupMethod.info.getDescriptor());
        return lookupMethod;
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

    public void setReturnType(String str) throws CompileError {
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
            return;
        }
        this.exprType = MemberResolver.descToType(charAt);
        this.className = null;
    }

    private void atFieldRead(ASTree aSTree) throws CompileError {
        atFieldRead(fieldAccess(aSTree));
    }

    private void atFieldRead(CtField ctField) throws CompileError {
        String descriptor = ctField.getFieldInfo2().getDescriptor();
        int i = 0;
        char charAt = descriptor.charAt(0);
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
            return;
        }
        this.className = null;
    }

    protected CtField fieldAccess(ASTree aSTree) throws CompileError {
        if (aSTree instanceof Member) {
            Member member = (Member) aSTree;
            String str = member.get();
            try {
                CtField field = this.thisClass.getField(str);
                if (Modifier.isStatic(field.getModifiers())) {
                    member.setField(field);
                }
                return field;
            } catch (NotFoundException unused) {
                throw new NoFieldException(str, aSTree);
            }
        }
        if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            int operator = expr.getOperator();
            if (operator == 35) {
                Member member2 = (Member) expr.oprand2();
                CtField lookupField = this.resolver.lookupField(((Symbol) expr.oprand1()).get(), member2);
                member2.setField(lookupField);
                return lookupField;
            } else if (operator == 46) {
                try {
                    expr.oprand1().accept(this);
                    try {
                    } catch (CompileError e) {
                        e = e;
                    }
                    if (this.exprType == 307 && this.arrayDim == 0) {
                        return this.resolver.lookupFieldByJvmName(this.className, (Symbol) expr.oprand2());
                    }
                    e = null;
                    ASTree oprand1 = expr.oprand1();
                    if (oprand1 instanceof Symbol) {
                        return fieldAccess2(expr, ((Symbol) oprand1).get());
                    }
                    if (e != null) {
                        throw e;
                    }
                } catch (NoFieldException e2) {
                    if (e2.getExpr() != expr.oprand1()) {
                        throw e2;
                    }
                    return fieldAccess2(expr, e2.getField());
                }
            }
        }
        throw new CompileError("bad filed access");
    }

    private CtField fieldAccess2(Expr expr, String str) throws CompileError {
        Member member = (Member) expr.oprand2();
        CtField lookupFieldByJvmName2 = this.resolver.lookupFieldByJvmName2(str, member, expr);
        expr.setOperator(35);
        expr.setOprand1(new Symbol(MemberResolver.jvmToJavaName(str)));
        member.setField(lookupFieldByJvmName2);
        return lookupFieldByJvmName2;
    }

    public void atClassObject(Expr expr) throws CompileError {
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = jvmJavaLangClass;
    }

    public void atArrayLength(Expr expr) throws CompileError {
        expr.oprand1().accept(this);
        if (this.arrayDim == 0) {
            throw new NoFieldException("length", expr);
        }
        this.exprType = TokenId.INT;
        this.arrayDim = 0;
    }

    public void atArrayRead(ASTree aSTree, ASTree aSTree2) throws CompileError {
        aSTree.accept(this);
        int i = this.exprType;
        int i2 = this.arrayDim;
        String str = this.className;
        aSTree2.accept(this);
        this.exprType = i;
        this.arrayDim = i2 - 1;
        this.className = str;
    }

    private void atPlusPlus(int i, ASTree aSTree, Expr expr) throws CompileError {
        if (aSTree == null) {
            aSTree = expr.oprand2();
        }
        if (aSTree instanceof Variable) {
            Declarator declarator = ((Variable) aSTree).getDeclarator();
            this.exprType = declarator.getType();
            this.arrayDim = declarator.getArrayDim();
            return;
        }
        if (aSTree instanceof Expr) {
            Expr expr2 = (Expr) aSTree;
            if (expr2.getOperator() == 65) {
                atArrayRead(expr2.oprand1(), expr2.oprand2());
                int i2 = this.exprType;
                if (i2 == 324 || i2 == 303 || i2 == 306 || i2 == 334) {
                    this.exprType = TokenId.INT;
                    return;
                }
                return;
            }
        }
        atFieldPlusPlus(aSTree);
    }

    protected void atFieldPlusPlus(ASTree aSTree) throws CompileError {
        atFieldRead(fieldAccess(aSTree));
        int i = this.exprType;
        if (i == 324 || i == 303 || i == 306 || i == 334) {
            this.exprType = TokenId.INT;
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atMember(Member member) throws CompileError {
        atFieldRead(member);
    }

    @Override // javassist.compiler.ast.Visitor
    public void atVariable(Variable variable) throws CompileError {
        Declarator declarator = variable.getDeclarator();
        this.exprType = declarator.getType();
        this.arrayDim = declarator.getArrayDim();
        this.className = declarator.getClassName();
    }

    @Override // javassist.compiler.ast.Visitor
    public void atKeyword(Keyword keyword) throws CompileError {
        this.arrayDim = 0;
        int i = keyword.get();
        if (i != 336 && i != 339) {
            switch (i) {
                case TokenId.TRUE /* 410 */:
                case TokenId.FALSE /* 411 */:
                    this.exprType = 301;
                    return;
                case TokenId.NULL /* 412 */:
                    this.exprType = TokenId.NULL;
                    return;
                default:
                    fatal();
                    return;
            }
        }
        this.exprType = 307;
        if (i == 339) {
            this.className = getThisName();
        } else {
            this.className = getSuperName();
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atStringL(StringL stringL) throws CompileError {
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = jvmJavaLangString;
    }

    @Override // javassist.compiler.ast.Visitor
    public void atIntConst(IntConst intConst) throws CompileError {
        this.arrayDim = 0;
        int type = intConst.getType();
        if (type == 402 || type == 401) {
            this.exprType = type == 402 ? TokenId.INT : 306;
        } else {
            this.exprType = TokenId.LONG;
        }
    }

    @Override // javassist.compiler.ast.Visitor
    public void atDoubleConst(DoubleConst doubleConst) throws CompileError {
        this.arrayDim = 0;
        if (doubleConst.getType() == 405) {
            this.exprType = 312;
        } else {
            this.exprType = 317;
        }
    }
}
