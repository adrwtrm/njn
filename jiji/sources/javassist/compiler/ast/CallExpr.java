package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.MemberResolver;

/* loaded from: classes2.dex */
public class CallExpr extends Expr {
    private static final long serialVersionUID = 1;
    private MemberResolver.Method method;

    private CallExpr(ASTree aSTree, ASTList aSTList) {
        super(67, aSTree, aSTList);
        this.method = null;
    }

    public void setMethod(MemberResolver.Method method) {
        this.method = method;
    }

    public MemberResolver.Method getMethod() {
        return this.method;
    }

    public static CallExpr makeCall(ASTree aSTree, ASTree aSTree2) {
        return new CallExpr(aSTree, new ASTList(aSTree2));
    }

    @Override // javassist.compiler.ast.Expr, javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atCallExpr(this);
    }
}
