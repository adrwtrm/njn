package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class StringL extends ASTree {
    private static final long serialVersionUID = 1;
    protected String text;

    public StringL(String str) {
        this.text = str;
    }

    public String get() {
        return this.text;
    }

    @Override // javassist.compiler.ast.ASTree
    public String toString() {
        return "\"" + this.text + "\"";
    }

    @Override // javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atStringL(this);
    }
}
