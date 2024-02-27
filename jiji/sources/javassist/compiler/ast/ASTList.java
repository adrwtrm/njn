package javassist.compiler.ast;

import javassist.compiler.CompileError;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public class ASTList extends ASTree {
    private static final long serialVersionUID = 1;
    private ASTree left;
    private ASTList right;

    public ASTList(ASTree aSTree, ASTList aSTList) {
        this.left = aSTree;
        this.right = aSTList;
    }

    public ASTList(ASTree aSTree) {
        this.left = aSTree;
        this.right = null;
    }

    public static ASTList make(ASTree aSTree, ASTree aSTree2, ASTree aSTree3) {
        return new ASTList(aSTree, new ASTList(aSTree2, new ASTList(aSTree3)));
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
        this.right = (ASTList) aSTree;
    }

    public ASTree head() {
        return this.left;
    }

    public void setHead(ASTree aSTree) {
        this.left = aSTree;
    }

    public ASTList tail() {
        return this.right;
    }

    public void setTail(ASTList aSTList) {
        this.right = aSTList;
    }

    @Override // javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atASTList(this);
    }

    @Override // javassist.compiler.ast.ASTree
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("(<");
        stringBuffer.append(getTag());
        stringBuffer.append(Typography.greater);
        for (ASTList aSTList = this; aSTList != null; aSTList = aSTList.right) {
            stringBuffer.append(' ');
            ASTree aSTree = aSTList.left;
            stringBuffer.append(aSTree == null ? "<null>" : aSTree.toString());
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public int length() {
        return length(this);
    }

    public static int length(ASTList aSTList) {
        int i = 0;
        if (aSTList == null) {
            return 0;
        }
        while (aSTList != null) {
            aSTList = aSTList.right;
            i++;
        }
        return i;
    }

    public ASTList sublist(int i) {
        ASTList aSTList = this;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return aSTList;
            }
            aSTList = aSTList.right;
            i = i2;
        }
    }

    public boolean subst(ASTree aSTree, ASTree aSTree2) {
        for (ASTList aSTList = this; aSTList != null; aSTList = aSTList.right) {
            if (aSTList.left == aSTree2) {
                aSTList.left = aSTree;
                return true;
            }
        }
        return false;
    }

    public static ASTList append(ASTList aSTList, ASTree aSTree) {
        return concat(aSTList, new ASTList(aSTree));
    }

    public static ASTList concat(ASTList aSTList, ASTList aSTList2) {
        if (aSTList == null) {
            return aSTList2;
        }
        ASTList aSTList3 = aSTList;
        while (true) {
            ASTList aSTList4 = aSTList3.right;
            if (aSTList4 == null) {
                aSTList3.right = aSTList2;
                return aSTList;
            }
            aSTList3 = aSTList4;
        }
    }
}
