package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class CondExpr extends ASTList {
    private static final long serialVersionUID = 1;

    @Override // javassist.compiler.ast.ASTree
    public String getTag() {
        return "?:";
    }

    public CondExpr(ASTree aSTree, ASTree aSTree2, ASTree aSTree3) {
        super(aSTree, new ASTList(aSTree2, new ASTList(aSTree3)));
    }

    public ASTree condExpr() {
        return head();
    }

    public void setCond(ASTree aSTree) {
        setHead(aSTree);
    }

    public ASTree thenExpr() {
        return tail().head();
    }

    public void setThen(ASTree aSTree) {
        tail().setHead(aSTree);
    }

    public ASTree elseExpr() {
        return tail().tail().head();
    }

    public void setElse(ASTree aSTree) {
        tail().tail().setHead(aSTree);
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atCondExpr(this);
    }
}
