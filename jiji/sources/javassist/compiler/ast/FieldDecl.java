package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class FieldDecl extends ASTList {
    private static final long serialVersionUID = 1;

    public FieldDecl(ASTree aSTree, ASTList aSTList) {
        super(aSTree, aSTList);
    }

    public ASTList getModifiers() {
        return (ASTList) getLeft();
    }

    public Declarator getDeclarator() {
        return (Declarator) tail().head();
    }

    public ASTree getInit() {
        return sublist(2).head();
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atFieldDecl(this);
    }
}
