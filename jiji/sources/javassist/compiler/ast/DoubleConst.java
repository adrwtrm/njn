package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.TokenId;

/* loaded from: classes2.dex */
public class DoubleConst extends ASTree {
    private static final long serialVersionUID = 1;
    protected int type;
    protected double value;

    public DoubleConst(double d, int i) {
        this.value = d;
        this.type = i;
    }

    public double get() {
        return this.value;
    }

    public void set(double d) {
        this.value = d;
    }

    public int getType() {
        return this.type;
    }

    @Override // javassist.compiler.ast.ASTree
    public String toString() {
        return Double.toString(this.value);
    }

    @Override // javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atDoubleConst(this);
    }

    public ASTree compute(int i, ASTree aSTree) {
        if (aSTree instanceof IntConst) {
            return compute0(i, (IntConst) aSTree);
        }
        if (aSTree instanceof DoubleConst) {
            return compute0(i, (DoubleConst) aSTree);
        }
        return null;
    }

    private DoubleConst compute0(int i, DoubleConst doubleConst) {
        int i2 = this.type;
        int i3 = TokenId.DoubleConstant;
        if (i2 != 405 && doubleConst.type != 405) {
            i3 = TokenId.FloatConstant;
        }
        return compute(i, this.value, doubleConst.value, i3);
    }

    private DoubleConst compute0(int i, IntConst intConst) {
        return compute(i, this.value, intConst.value, this.type);
    }

    private static DoubleConst compute(int i, double d, double d2, int i2) {
        double d3;
        if (i == 37) {
            d3 = d % d2;
        } else if (i == 45) {
            d3 = d - d2;
        } else if (i == 47) {
            d3 = d / d2;
        } else if (i == 42) {
            d3 = d * d2;
        } else if (i != 43) {
            return null;
        } else {
            d3 = d + d2;
        }
        return new DoubleConst(d3, i2);
    }
}
