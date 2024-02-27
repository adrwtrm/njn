package javassist.compiler;

/* loaded from: classes2.dex */
public class SyntaxError extends CompileError {
    private static final long serialVersionUID = 1;

    public SyntaxError(Lex lex) {
        super("syntax error near \"" + lex.getTextAround() + "\"", lex);
    }
}
