package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.TokenId;

/* loaded from: classes2.dex */
public class Declarator extends ASTList implements TokenId {
    private static final long serialVersionUID = 1;
    protected int arrayDim;
    protected int localVar;
    protected String qualifiedClass;
    protected int varType;

    @Override // javassist.compiler.ast.ASTree
    public String getTag() {
        return "decl";
    }

    public Declarator(int i, int i2) {
        super(null);
        this.varType = i;
        this.arrayDim = i2;
        this.localVar = -1;
        this.qualifiedClass = null;
    }

    public Declarator(ASTList aSTList, int i) {
        super(null);
        this.varType = 307;
        this.arrayDim = i;
        this.localVar = -1;
        this.qualifiedClass = astToClassName(aSTList, '/');
    }

    public Declarator(int i, String str, int i2, int i3, Symbol symbol) {
        super(null);
        this.varType = i;
        this.arrayDim = i2;
        this.localVar = i3;
        this.qualifiedClass = str;
        setLeft(symbol);
        append(this, null);
    }

    public Declarator make(Symbol symbol, int i, ASTree aSTree) {
        Declarator declarator = new Declarator(this.varType, this.arrayDim + i);
        declarator.qualifiedClass = this.qualifiedClass;
        declarator.setLeft(symbol);
        append(declarator, aSTree);
        return declarator;
    }

    public int getType() {
        return this.varType;
    }

    public int getArrayDim() {
        return this.arrayDim;
    }

    public void addArrayDim(int i) {
        this.arrayDim += i;
    }

    public String getClassName() {
        return this.qualifiedClass;
    }

    public void setClassName(String str) {
        this.qualifiedClass = str;
    }

    public Symbol getVariable() {
        return (Symbol) getLeft();
    }

    public void setVariable(Symbol symbol) {
        setLeft(symbol);
    }

    public ASTree getInitializer() {
        ASTList tail = tail();
        if (tail != null) {
            return tail.head();
        }
        return null;
    }

    public void setLocalVar(int i) {
        this.localVar = i;
    }

    public int getLocalVar() {
        return this.localVar;
    }

    @Override // javassist.compiler.ast.ASTList, javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atDeclarator(this);
    }

    public static String astToClassName(ASTList aSTList, char c) {
        if (aSTList == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        astToClassName(stringBuffer, aSTList, c);
        return stringBuffer.toString();
    }

    private static void astToClassName(StringBuffer stringBuffer, ASTList aSTList, char c) {
        while (true) {
            ASTree head = aSTList.head();
            if (head instanceof Symbol) {
                stringBuffer.append(((Symbol) head).get());
            } else if (head instanceof ASTList) {
                astToClassName(stringBuffer, (ASTList) head, c);
            }
            aSTList = aSTList.tail();
            if (aSTList == null) {
                return;
            }
            stringBuffer.append(c);
        }
    }
}