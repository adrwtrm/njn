package javassist.compiler.ast;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class IntConst extends ASTree {
    private static final long serialVersionUID = 1;
    protected int type;
    protected long value;

    public IntConst(long j, int i) {
        this.value = j;
        this.type = i;
    }

    public long get() {
        return this.value;
    }

    public void set(long j) {
        this.value = j;
    }

    public int getType() {
        return this.type;
    }

    @Override // javassist.compiler.ast.ASTree
    public String toString() {
        return Long.toString(this.value);
    }

    @Override // javassist.compiler.ast.ASTree
    public void accept(Visitor visitor) throws CompileError {
        visitor.atIntConst(this);
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

    private IntConst compute0(int i, IntConst intConst) {
        long j;
        int i2 = this.type;
        int i3 = intConst.type;
        int i4 = 403;
        if (i2 != 403 && i3 != 403) {
            i4 = 401;
            if (i2 != 401 || i3 != 401) {
                i4 = 402;
            }
        }
        long j2 = this.value;
        long j3 = intConst.value;
        if (i == 37) {
            j = j2 % j3;
        } else if (i == 38) {
            j = j2 & j3;
        } else if (i == 42) {
            j = j2 * j3;
        } else if (i == 43) {
            j = j2 + j3;
        } else if (i == 45) {
            j = j2 - j3;
        } else if (i == 47) {
            j = j2 / j3;
        } else if (i == 94) {
            j = j2 ^ j3;
        } else if (i != 124) {
            if (i == 364) {
                j = j2 << ((int) j3);
            } else if (i == 366) {
                j = j2 >> ((int) j3);
            } else if (i != 370) {
                return null;
            } else {
                j = j2 >>> ((int) j3);
            }
            return new IntConst(j, i2);
        } else {
            j = j2 | j3;
        }
        i2 = i4;
        return new IntConst(j, i2);
    }

    private DoubleConst compute0(int i, DoubleConst doubleConst) {
        double d;
        double d2 = this.value;
        double d3 = doubleConst.value;
        if (i == 37) {
            d = d2 % d3;
        } else if (i == 45) {
            d = d2 - d3;
        } else if (i == 47) {
            d = d2 / d3;
        } else if (i == 42) {
            d = d2 * d3;
        } else if (i != 43) {
            return null;
        } else {
            d = d2 + d3;
        }
        return new DoubleConst(d, doubleConst.type);
    }
}
