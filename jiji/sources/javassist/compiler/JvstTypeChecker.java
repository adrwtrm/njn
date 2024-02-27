package javassist.compiler;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.CallExpr;
import javassist.compiler.ast.CastExpr;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.Symbol;

/* loaded from: classes2.dex */
public class JvstTypeChecker extends TypeChecker {
    private JvstCodeGen codeGen;

    public JvstTypeChecker(CtClass ctClass, ClassPool classPool, JvstCodeGen jvstCodeGen) {
        super(ctClass, classPool);
        this.codeGen = jvstCodeGen;
    }

    public void addNullIfVoid() {
        if (this.exprType == 344) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }

    @Override // javassist.compiler.TypeChecker, javassist.compiler.ast.Visitor
    public void atMember(Member member) throws CompileError {
        String str = member.get();
        if (str.equals(this.codeGen.paramArrayName)) {
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Object";
        } else if (str.equals(JvstCodeGen.sigName)) {
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Class";
        } else if (str.equals(JvstCodeGen.dollarTypeName) || str.equals(JvstCodeGen.clazzName)) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Class";
        } else {
            super.atMember(member);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // javassist.compiler.TypeChecker
    public void atFieldAssign(Expr expr, int i, ASTree aSTree, ASTree aSTree2) throws CompileError {
        if ((aSTree instanceof Member) && ((Member) aSTree).get().equals(this.codeGen.paramArrayName)) {
            aSTree2.accept(this);
            CtClass[] ctClassArr = this.codeGen.paramTypeList;
            if (ctClassArr == null) {
                return;
            }
            for (CtClass ctClass : ctClassArr) {
                compileUnwrapValue(ctClass);
            }
            return;
        }
        super.atFieldAssign(expr, i, aSTree, aSTree2);
    }

    @Override // javassist.compiler.TypeChecker, javassist.compiler.ast.Visitor
    public void atCastExpr(CastExpr castExpr) throws CompileError {
        ASTList className = castExpr.getClassName();
        if (className != null && castExpr.getArrayDim() == 0) {
            ASTree head = className.head();
            if ((head instanceof Symbol) && className.tail() == null) {
                String str = ((Symbol) head).get();
                if (str.equals(this.codeGen.returnCastName)) {
                    atCastToRtype(castExpr);
                    return;
                } else if (str.equals(JvstCodeGen.wrapperCastName)) {
                    atCastToWrapper(castExpr);
                    return;
                }
            }
        }
        super.atCastExpr(castExpr);
    }

    protected void atCastToRtype(CastExpr castExpr) throws CompileError {
        CtClass ctClass = this.codeGen.returnType;
        castExpr.getOprand().accept(this);
        if (this.exprType == 344 || CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            compileUnwrapValue(ctClass);
        } else if (ctClass instanceof CtPrimitiveType) {
            this.exprType = MemberResolver.descToType(((CtPrimitiveType) ctClass).getDescriptor());
            this.arrayDim = 0;
            this.className = null;
        }
    }

    protected void atCastToWrapper(CastExpr castExpr) throws CompileError {
        castExpr.getOprand().accept(this);
        if (CodeGen.isRefType(this.exprType) || this.arrayDim > 0 || !(this.resolver.lookupClass(this.exprType, this.arrayDim, this.className) instanceof CtPrimitiveType)) {
            return;
        }
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Object";
    }

    @Override // javassist.compiler.TypeChecker, javassist.compiler.ast.Visitor
    public void atCallExpr(CallExpr callExpr) throws CompileError {
        ASTree oprand1 = callExpr.oprand1();
        if (oprand1 instanceof Member) {
            String str = ((Member) oprand1).get();
            if (this.codeGen.procHandler != null && str.equals(this.codeGen.proceedName)) {
                this.codeGen.procHandler.setReturnType(this, (ASTList) callExpr.oprand2());
                return;
            } else if (str.equals(JvstCodeGen.cflowName)) {
                atCflow((ASTList) callExpr.oprand2());
                return;
            }
        }
        super.atCallExpr(callExpr);
    }

    protected void atCflow(ASTList aSTList) throws CompileError {
        this.exprType = TokenId.INT;
        this.arrayDim = 0;
        this.className = null;
    }

    public boolean isParamListName(ASTList aSTList) {
        if (this.codeGen.paramTypeList == null || aSTList == null || aSTList.tail() != null) {
            return false;
        }
        ASTree head = aSTList.head();
        return (head instanceof Member) && ((Member) head).get().equals(this.codeGen.paramListName);
    }

    @Override // javassist.compiler.TypeChecker
    public int getMethodArgsLength(ASTList aSTList) {
        String str = this.codeGen.paramListName;
        int i = 0;
        while (aSTList != null) {
            ASTree head = aSTList.head();
            if (!(head instanceof Member) || !((Member) head).get().equals(str)) {
                i++;
            } else if (this.codeGen.paramTypeList != null) {
                i += this.codeGen.paramTypeList.length;
            }
            aSTList = aSTList.tail();
        }
        return i;
    }

    @Override // javassist.compiler.TypeChecker
    public void atMethodArgs(ASTList aSTList, int[] iArr, int[] iArr2, String[] strArr) throws CompileError {
        CtClass[] ctClassArr = this.codeGen.paramTypeList;
        String str = this.codeGen.paramListName;
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
                for (CtClass ctClass : ctClassArr) {
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
    public void compileInvokeSpecial(ASTree aSTree, String str, String str2, String str3, ASTList aSTList) throws CompileError {
        aSTree.accept(this);
        int methodArgsLength = getMethodArgsLength(aSTList);
        atMethodArgs(aSTList, new int[methodArgsLength], new int[methodArgsLength], new String[methodArgsLength]);
        setReturnType(str3);
        addNullIfVoid();
    }

    protected void compileUnwrapValue(CtClass ctClass) throws CompileError {
        if (ctClass == CtClass.voidType) {
            addNullIfVoid();
        } else {
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
}
