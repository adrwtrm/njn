package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.TokenId;

/* loaded from: classes2.dex */
public class CastExpr extends ASTList implements TokenId {
    private static final long serialVersionUID = 1;
    protected int arrayDim;
    protected int castType;

    public CastExpr(ASTList aSTList, int i, ASTree aSTree) {
        super(aSTList, new ASTList(aSTree));
        this.castType = 307;
        this.arrayDim = i;
    }

    public CastExpr(int i, int i2, ASTree aSTree) {
        super(null, new ASTList(aSTree));
        this.castType = i;
        this.arrayDim = i2;
    }

    public int getType() {
        return this.castType;
    }

    public int getArrayDim() {
        return this.arrayDim;
    }

    public ASTList getClassName() {
        return (ASTList) getLeft();
    }

    public ASTree getOprand() {
        return getRight().getLeft();
    }

    public void setOprand(ASTree aSTree) {
        getRight().setLeft(aSTree);
    }

    @Override // javassist.compiler.ast.ASTree
    public String getTag() {
        return "cast:" + this.castType + ":" + this.arrayDim;
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atCastExpr(this);
    }
}
