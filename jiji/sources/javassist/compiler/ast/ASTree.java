package javassist.compiler.ast;

import java.io.Serializable;
import javassist.compiler.CompileError;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public abstract class ASTree implements Serializable {
    private static final long serialVersionUID = 1;

    public abstract void accept(Visitor visitor) throws CompileError;

    public ASTree getLeft() {
        return null;
    }

    public ASTree getRight() {
        return null;
    }

    public void setLeft(ASTree aSTree) {
    }

    public void setRight(ASTree aSTree) {
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("<");
        stringBuffer.append(getTag());
        stringBuffer.append(Typography.greater);
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getTag() {
        String name = getClass().getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }
}
