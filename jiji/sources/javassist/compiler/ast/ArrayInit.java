package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class ArrayInit extends ASTList {
    private static final long serialVersionUID = 1;

    @Override // javassist.compiler.ast.ASTree
    public String getTag() {
        return "array";
    }

    public ArrayInit(ASTree aSTree) {
        super(aSTree);
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atArrayInit(this);
    }
}
