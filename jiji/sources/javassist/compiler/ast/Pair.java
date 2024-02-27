package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class Pair extends ASTree {
    private static final long serialVersionUID = 1;
    protected ASTree left;
    protected ASTree right;

    public Pair(ASTree aSTree, ASTree aSTree2) {
        this.left = aSTree;
        this.right = aSTree2;
    }

    @Override // javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atPair(this);
    }

    @Override // javassist.compiler.ast.ASTree
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("(<Pair> ");
        ASTree aSTree = this.left;
        stringBuffer.append(aSTree == null ? "<null>" : aSTree.toString());
        stringBuffer.append(" . ");
        ASTree aSTree2 = this.right;
        stringBuffer.append(aSTree2 != null ? aSTree2.toString() : "<null>");
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    @Override // javassist.compiler.ast.ASTree
    public ASTree getLeft() {
        return this.left;
    }

    @Override // javassist.compiler.ast.ASTree
    public ASTree getRight() {
        return this.right;
    }

    @Override // javassist.compiler.ast.ASTree
    public void setLeft(ASTree aSTree) {
        this.left = aSTree;
    }

    @Override // javassist.compiler.ast.ASTree
    public void setRight(ASTree aSTree) {
        this.right = aSTree;
    }
}
