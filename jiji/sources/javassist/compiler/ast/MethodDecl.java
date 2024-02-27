package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class MethodDecl extends ASTList {
    public static final String initName = "<init>";
    private static final long serialVersionUID = 1;

    public MethodDecl(ASTree aSTree, ASTList aSTList) {
        super(aSTree, aSTList);
    }

    public boolean isConstructor() {
        Symbol variable = getReturn().getVariable();
        return variable != null && "<init>".equals(variable.get());
    }

    public ASTList getModifiers() {
        return (ASTList) getLeft();
    }

    public Declarator getReturn() {
        return (Declarator) tail().head();
    }

    public ASTList getParams() {
        return (ASTList) sublist(2).head();
    }

    public ASTList getThrows() {
        return (ASTList) sublist(3).head();
    }

    public Stmnt getBody() {
        return (Stmnt) sublist(4).head();
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atMethodDecl(this);
    }
}
