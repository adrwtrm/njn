package javassist.compiler.ast;

import javassist.CtField;
import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class Member extends Symbol {
    private static final long serialVersionUID = 1;
    private CtField field;

    public Member(String str) {
        super(str);
        this.field = null;
    }

    public void setField(CtField ctField) {
        this.field = ctField;
    }

    public CtField getField() {
        return this.field;
    }

    @Override // javassist.compiler.ast.Symbol, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atMember(this);
    }
}
