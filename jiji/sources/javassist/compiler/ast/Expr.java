package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.TokenId;

/* loaded from: classes2.dex */
public class Expr extends ASTList implements TokenId {
    private static final long serialVersionUID = 1;
    protected int operatorId;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Expr(int i, ASTree aSTree, ASTList aSTList) {
        super(aSTree, aSTList);
        this.operatorId = i;
    }

    Expr(int i, ASTree aSTree) {
        super(aSTree);
        this.operatorId = i;
    }

    public static Expr make(int i, ASTree aSTree, ASTree aSTree2) {
        return new Expr(i, aSTree, new ASTList(aSTree2));
    }

    public static Expr make(int i, ASTree aSTree) {
        return new Expr(i, aSTree);
    }

    public int getOperator() {
        return this.operatorId;
    }

    public void setOperator(int i) {
        this.operatorId = i;
    }

    public ASTree oprand1() {
        return getLeft();
    }

    public void setOprand1(ASTree aSTree) {
        setLeft(aSTree);
    }

    public ASTree oprand2() {
        return getRight().getLeft();
    }

    public void setOprand2(ASTree aSTree) {
        getRight().setLeft(aSTree);
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atExpr(this);
    }

    public String getName() {
        int i = this.operatorId;
        if (i < 128) {
            return String.valueOf((char) i);
        }
        if (350 > i || i > 371) {
            return i == 323 ? "instanceof" : String.valueOf(i);
        }
        return opNames[i - TokenId.NEQ];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // javassist.compiler.ast.ASTree
    public String getTag() {
        return "op:" + getName();
    }
}
