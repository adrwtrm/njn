package javassist.compiler;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.bytecode.Bytecode;
import javassist.bytecode.Descriptor;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.CallExpr;
import javassist.compiler.ast.CastExpr;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.Stmnt;
import javassist.compiler.ast.Symbol;

/* loaded from: classes2.dex */
public class JvstCodeGen extends MemberCodeGen {
    public static final String cflowName = "$cflow";
    public static final String clazzName = "$class";
    public static final String dollarTypeName = "$type";
    public static final String sigName = "$sig";
    public static final String wrapperCastName = "$w";
    private CtClass dollarType;
    private String param0Type;
    String paramArrayName;
    String paramListName;
    CtClass[] paramTypeList;
    private int paramVarBase;
    ProceedHandler procHandler;
    String proceedName;
    String returnCastName;
    CtClass returnType;
    private String returnVarName;
    private boolean useParam0;

    public JvstCodeGen(Bytecode bytecode, CtClass ctClass, ClassPool classPool) {
        super(bytecode, ctClass, classPool);
        this.paramArrayName = null;
        this.paramListName = null;
        this.paramTypeList = null;
        this.paramVarBase = 0;
        this.useParam0 = false;
        this.param0Type = null;
        this.dollarType = null;
        this.returnType = null;
        this.returnCastName = null;
        this.returnVarName = null;
        this.proceedName = null;
        this.procHandler = null;
        setTypeChecker(new JvstTypeChecker(ctClass, classPool, this));
    }

    private int indexOfParam1() {
        return this.paramVarBase + (this.useParam0 ? 1 : 0);
    }

    public void setProceedHandler(ProceedHandler proceedHandler, String str) {
        this.proceedName = str;
        this.procHandler = proceedHandler;
    }

    public void addNullIfVoid() {
        if (this.exprType == 344) {
            this.bytecode.addOpcode(1);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }

    @Override // javassist.compiler.MemberCodeGen, javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atMember(Member member) throws CompileError {
        String str = member.get();
        if (str.equals(this.paramArrayName)) {
            compileParameterList(this.bytecode, this.paramTypeList, indexOfParam1());
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Object";
        } else if (str.equals(sigName)) {
            this.bytecode.addLdc(Descriptor.ofMethod(this.returnType, this.paramTypeList));
            this.bytecode.addInvokestatic("javassist/runtime/Desc", "getParams", "(Ljava/lang/String;)[Ljava/lang/Class;");
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Class";
        } else if (str.equals(dollarTypeName)) {
            if (this.dollarType == null) {
                throw new CompileError("$type is not available");
            }
            this.bytecode.addLdc(Descriptor.of(this.dollarType));
            callGetType("getType");
        } else if (str.equals(clazzName)) {
            if (this.param0Type == null) {
                throw new CompileError("$class is not available");
            }
            this.bytecode.addLdc(this.param0Type);
            callGetType("getClazz");
        } else {
            super.atMember(member);
        }
    }

    private void callGetType(String str) {
        this.bytecode.addInvokestatic("javassist/runtime/Desc", str, "(Ljava/lang/String;)Ljava/lang/Class;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }

    @Override // javassist.compiler.MemberCodeGen, javassist.compiler.CodeGen
    protected void atFieldAssign(Expr expr, int i, ASTree aSTree, ASTree aSTree2, boolean z) throws CompileError {
        if (!(aSTree instanceof Member) || !((Member) aSTree).get().equals(this.paramArrayName)) {
            super.atFieldAssign(expr, i, aSTree, aSTree2, z);
        } else if (i != 61) {
            throw new CompileError("bad operator for " + this.paramArrayName);
        } else {
            aSTree2.accept(this);
            if (this.arrayDim != 1 || this.exprType != 307) {
                throw new CompileError("invalid type for " + this.paramArrayName);
            }
            atAssignParamList(this.paramTypeList, this.bytecode);
            if (z) {
                return;
            }
            this.bytecode.addOpcode(87);
        }
    }

    protected void atAssignParamList(CtClass[] ctClassArr, Bytecode bytecode) throws CompileError {
        if (ctClassArr == null) {
            return;
        }
        int indexOfParam1 = indexOfParam1();
        int length = ctClassArr.length;
        for (int i = 0; i < length; i++) {
            bytecode.addOpcode(89);
            bytecode.addIconst(i);
            bytecode.addOpcode(50);
            compileUnwrapValue(ctClassArr[i], bytecode);
            bytecode.addStore(indexOfParam1, ctClassArr[i]);
            indexOfParam1 += is2word(this.exprType, this.arrayDim) ? 2 : 1;
        }
    }

    @Override // javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atCastExpr(CastExpr castExpr) throws CompileError {
        ASTList className = castExpr.getClassName();
        if (className != null && castExpr.getArrayDim() == 0) {
            ASTree head = className.head();
            if ((head instanceof Symbol) && className.tail() == null) {
                String str = ((Symbol) head).get();
                if (str.equals(this.returnCastName)) {
                    atCastToRtype(castExpr);
                    return;
                } else if (str.equals(wrapperCastName)) {
                    atCastToWrapper(castExpr);
                    return;
                }
            }
        }
        super.atCastExpr(castExpr);
    }

    protected void atCastToRtype(CastExpr castExpr) throws CompileError {
        castExpr.getOprand().accept(this);
        if (this.exprType == 344 || isRefType(this.exprType) || this.arrayDim > 0) {
            compileUnwrapValue(this.returnType, this.bytecode);
            return;
        }
        CtClass ctClass = this.returnType;
        if (ctClass instanceof CtPrimitiveType) {
            int descToType = MemberResolver.descToType(((CtPrimitiveType) ctClass).getDescriptor());
            atNumCastExpr(this.exprType, descToType);
            this.exprType = descToType;
            this.arrayDim = 0;
            this.className = null;
            return;
        }
        throw new CompileError("invalid cast");
    }

    protected void atCastToWrapper(CastExpr castExpr) throws CompileError {
        castExpr.getOprand().accept(this);
        if (isRefType(this.exprType) || this.arrayDim > 0) {
            return;
        }
        CtClass lookupClass = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
        if (lookupClass instanceof CtPrimitiveType) {
            CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) lookupClass;
            String wrapperName = ctPrimitiveType.getWrapperName();
            this.bytecode.addNew(wrapperName);
            this.bytecode.addOpcode(89);
            if (ctPrimitiveType.getDataSize() > 1) {
                this.bytecode.addOpcode(94);
            } else {
                this.bytecode.addOpcode(93);
            }
            this.bytecode.addOpcode(88);
            this.bytecode.addInvokespecial(wrapperName, "<init>", "(" + ctPrimitiveType.getDescriptor() + ")V");
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }

    @Override // javassist.compiler.MemberCodeGen, javassist.compiler.CodeGen, javassist.compiler.ast.Visitor
    public void atCallExpr(CallExpr callExpr) throws CompileError {
        ASTree oprand1 = callExpr.oprand1();
        if (oprand1 instanceof Member) {
            String str = ((Member) oprand1).get();
            if (this.procHandler != null && str.equals(this.proceedName)) {
                this.procHandler.doit(this, this.bytecode, (ASTList) callExpr.oprand2());
                return;
            } else if (str.equals(cflowName)) {
                atCflow((ASTList) callExpr.oprand2());
                return;
            }
        }
        super.atCallExpr(callExpr);
    }

    protected void atCflow(ASTList aSTList) throws CompileError {
        StringBuffer stringBuffer = new StringBuffer();
        if (aSTList == null || aSTList.tail() != null) {
            throw new CompileError("bad $cflow");
        }
        makeCflowName(stringBuffer, aSTList.head());
        String stringBuffer2 = stringBuffer.toString();
        Object[] lookupCflow = this.resolver.getClassPool().lookupCflow(stringBuffer2);
        if (lookupCflow == null) {
            throw new CompileError("no such $cflow: " + stringBuffer2);
        }
        this.bytecode.addGetstatic((String) lookupCflow[0], (String) lookupCflow[1], "Ljavassist/runtime/Cflow;");
        this.bytecode.addInvokevirtual("javassist.runtime.Cflow", "value", "()I");
        this.exprType = TokenId.INT;
        this.arrayDim = 0;
        this.className = null;
    }

    private static void makeCflowName(StringBuffer stringBuffer, ASTree aSTree) throws CompileError {
        if (aSTree instanceof Symbol) {
            stringBuffer.append(((Symbol) aSTree).get());
            return;
        }
        if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            if (expr.getOperator() == 46) {
                makeCflowName(stringBuffer, expr.oprand1());
                stringBuffer.append('.');
                makeCflowName(stringBuffer, expr.oprand2());
                return;
            }
        }
        throw new CompileError("bad $cflow");
    }

    public boolean isParamListName(ASTList aSTList) {
        if (this.paramTypeList == null || aSTList == null || aSTList.tail() != null) {
            return false;
        }
        ASTree head = aSTList.head();
        return (head instanceof Member) && ((Member) head).get().equals(this.paramListName);
    }

    @Override // javassist.compiler.MemberCodeGen
    public int getMethodArgsLength(ASTList aSTList) {
        String str = this.paramListName;
        int i = 0;
        while (aSTList != null) {
            ASTree head = aSTList.head();
            if ((head instanceof Member) && ((Member) head).get().equals(str)) {
                CtClass[] ctClassArr = this.paramTypeList;
                if (ctClassArr != null) {
                    i += ctClassArr.length;
                }
            } else {
                i++;
            }
            aSTList = aSTList.tail();
        }
        return i;
    }

    @Override // javassist.compiler.MemberCodeGen
    public void atMethodArgs(ASTList aSTList, int[] iArr, int[] iArr2, String[] strArr) throws CompileError {
        CtClass[] ctClassArr = this.paramTypeList;
        String str = this.paramListName;
        int i = 0;
        while (aSTList != null) {
            ASTree head = aSTList.head();
            if (!(head instanceof Member) || !((Member) head).get().equals(str)) {
                head.accept(this);
                iArr[i] = this.exprType;
                iArr2[i] = this.arrayDim;
                strArr[i] = this.className;
                i++;
            } else if (ctClassArr != null) {
                int indexOfParam1 = indexOfParam1();
                for (CtClass ctClass : ctClassArr) {
                    indexOfParam1 += this.bytecode.addLoad(indexOfParam1, ctClass);
                    setType(ctClass);
                    iArr[i] = this.exprType;
                    iArr2[i] = this.arrayDim;
                    strArr[i] = this.className;
                    i++;
                }
            }
            aSTList = aSTList.tail();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void compileInvokeSpecial(ASTree aSTree, int i, String str, ASTList aSTList) throws CompileError {
        aSTree.accept(this);
        int methodArgsLength = getMethodArgsLength(aSTList);
        atMethodArgs(aSTList, new int[methodArgsLength], new int[methodArgsLength], new String[methodArgsLength]);
        this.bytecode.addInvokespecial(i, str);
        setReturnType(str, false, false);
        addNullIfVoid();
    }

    @Override // javassist.compiler.CodeGen
    protected void atReturnStmnt(Stmnt stmnt) throws CompileError {
        ASTree left = stmnt.getLeft();
        if (left != null && this.returnType == CtClass.voidType) {
            compileExpr(left);
            if (is2word(this.exprType, this.arrayDim)) {
                this.bytecode.addOpcode(88);
            } else if (this.exprType != 344) {
                this.bytecode.addOpcode(87);
            }
            left = null;
        }
        atReturnStmnt2(left);
    }

    public int recordReturnType(CtClass ctClass, String str, String str2, SymbolTable symbolTable) throws CompileError {
        this.returnType = ctClass;
        this.returnCastName = str;
        this.returnVarName = str2;
        if (str2 == null) {
            return -1;
        }
        int maxLocals = getMaxLocals();
        setMaxLocals(recordVar(ctClass, str2, maxLocals, symbolTable) + maxLocals);
        return maxLocals;
    }

    public void recordType(CtClass ctClass) {
        this.dollarType = ctClass;
    }

    public int recordParams(CtClass[] ctClassArr, boolean z, String str, String str2, String str3, SymbolTable symbolTable) throws CompileError {
        return recordParams(ctClassArr, z, str, str2, str3, !z, 0, getThisName(), symbolTable);
    }

    public int recordParams(CtClass[] ctClassArr, boolean z, String str, String str2, String str3, boolean z2, int i, String str4, SymbolTable symbolTable) throws CompileError {
        this.paramTypeList = ctClassArr;
        this.paramArrayName = str2;
        this.paramListName = str3;
        this.paramVarBase = i;
        this.useParam0 = z2;
        if (str4 != null) {
            this.param0Type = MemberResolver.jvmToJavaName(str4);
        }
        this.inStaticMethod = z;
        if (z2) {
            String str5 = str + "0";
            symbolTable.append(str5, new Declarator(307, MemberResolver.javaToJvmName(str4), 0, i, new Symbol(str5)));
            i++;
        }
        int i2 = 0;
        while (i2 < ctClassArr.length) {
            CtClass ctClass = ctClassArr[i2];
            i2++;
            i += recordVar(ctClass, str + i2, i, symbolTable);
        }
        if (getMaxLocals() < i) {
            setMaxLocals(i);
        }
        return i;
    }

    public int recordVariable(CtClass ctClass, String str, SymbolTable symbolTable) throws CompileError {
        if (str == null) {
            return -1;
        }
        int maxLocals = getMaxLocals();
        setMaxLocals(recordVar(ctClass, str, maxLocals, symbolTable) + maxLocals);
        return maxLocals;
    }

    private int recordVar(CtClass ctClass, String str, int i, SymbolTable symbolTable) throws CompileError {
        if (ctClass == CtClass.voidType) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        } else {
            setType(ctClass);
        }
        symbolTable.append(str, new Declarator(this.exprType, this.className, this.arrayDim, i, new Symbol(str)));
        return is2word(this.exprType, this.arrayDim) ? 2 : 1;
    }

    public void recordVariable(String str, String str2, int i, SymbolTable symbolTable) throws CompileError {
        char charAt;
        String str3;
        int i2 = 0;
        while (true) {
            charAt = str.charAt(i2);
            if (charAt != '[') {
                break;
            }
            i2++;
        }
        int descToType = MemberResolver.descToType(charAt);
        if (descToType != 307) {
            str3 = null;
        } else if (i2 == 0) {
            str3 = str.substring(1, str.length() - 1);
        } else {
            str3 = str.substring(i2 + 1, str.length() - 1);
        }
        symbolTable.append(str2, new Declarator(descToType, str3, i2, i, new Symbol(str2)));
    }

    public static int compileParameterList(Bytecode bytecode, CtClass[] ctClassArr, int i) {
        if (ctClassArr == null) {
            bytecode.addIconst(0);
            bytecode.addAnewarray("java.lang.Object");
            return 1;
        }
        CtClass[] ctClassArr2 = new CtClass[1];
        int length = ctClassArr.length;
        bytecode.addIconst(length);
        bytecode.addAnewarray("java.lang.Object");
        for (int i2 = 0; i2 < length; i2++) {
            bytecode.addOpcode(89);
            bytecode.addIconst(i2);
            if (ctClassArr[i2].isPrimitive()) {
                CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) ctClassArr[i2];
                String wrapperName = ctPrimitiveType.getWrapperName();
                bytecode.addNew(wrapperName);
                bytecode.addOpcode(89);
                i += bytecode.addLoad(i, ctPrimitiveType);
                ctClassArr2[0] = ctPrimitiveType;
                bytecode.addInvokespecial(wrapperName, "<init>", Descriptor.ofMethod(CtClass.voidType, ctClassArr2));
            } else {
                bytecode.addAload(i);
                i++;
            }
            bytecode.addOpcode(83);
        }
        return 8;
    }

    protected void compileUnwrapValue(CtClass ctClass, Bytecode bytecode) throws CompileError {
        if (ctClass == CtClass.voidType) {
            addNullIfVoid();
        } else if (this.exprType == 344) {
            throw new CompileError("invalid type for " + this.returnCastName);
        } else {
            if (ctClass instanceof CtPrimitiveType) {
                CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) ctClass;
                String wrapperName = ctPrimitiveType.getWrapperName();
                bytecode.addCheckcast(wrapperName);
                bytecode.addInvokevirtual(wrapperName, ctPrimitiveType.getGetMethodName(), ctPrimitiveType.getGetMethodDescriptor());
                setType(ctClass);
                return;
            }
            bytecode.addCheckcast(ctClass);
            setType(ctClass);
        }
    }

    public void setType(CtClass ctClass) throws CompileError {
        setType(ctClass, 0);
    }

    private void setType(CtClass ctClass, int i) throws CompileError {
        if (ctClass.isPrimitive()) {
            this.exprType = MemberResolver.descToType(((CtPrimitiveType) ctClass).getDescriptor());
            this.arrayDim = i;
            this.className = null;
        } else if (ctClass.isArray()) {
            try {
                setType(ctClass.getComponentType(), i + 1);
            } catch (NotFoundException unused) {
                throw new CompileError("undefined type: " + ctClass.getName());
            }
        } else {
            this.exprType = 307;
            this.arrayDim = i;
            this.className = MemberResolver.javaToJvmName(ctClass.getName());
        }
    }

    public void doNumCast(CtClass ctClass) throws CompileError {
        if (this.arrayDim != 0 || isRefType(this.exprType)) {
            return;
        }
        if (ctClass instanceof CtPrimitiveType) {
            atNumCastExpr(this.exprType, MemberResolver.descToType(((CtPrimitiveType) ctClass).getDescriptor()));
            return;
        }
        throw new CompileError("type mismatch");
    }
}
