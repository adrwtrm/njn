package javassist.bytecode.analysis;

/* loaded from: classes2.dex */
public class Frame {
    private boolean jsrMerged;
    private Type[] locals;
    private boolean retMerged;
    private Type[] stack;
    private int top;

    public Frame(int i, int i2) {
        this.locals = new Type[i];
        this.stack = new Type[i2];
    }

    public Type getLocal(int i) {
        return this.locals[i];
    }

    public void setLocal(int i, Type type) {
        this.locals[i] = type;
    }

    public Type getStack(int i) {
        return this.stack[i];
    }

    public void setStack(int i, Type type) {
        this.stack[i] = type;
    }

    public void clearStack() {
        this.top = 0;
    }

    public int getTopIndex() {
        return this.top - 1;
    }

    public int localsLength() {
        return this.locals.length;
    }

    public Type peek() {
        int i = this.top;
        if (i < 1) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        return this.stack[i - 1];
    }

    public Type pop() {
        int i = this.top;
        if (i < 1) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        Type[] typeArr = this.stack;
        int i2 = i - 1;
        this.top = i2;
        return typeArr[i2];
    }

    public void push(Type type) {
        Type[] typeArr = this.stack;
        int i = this.top;
        this.top = i + 1;
        typeArr[i] = type;
    }

    public Frame copy() {
        Frame frame = new Frame(this.locals.length, this.stack.length);
        Type[] typeArr = this.locals;
        System.arraycopy(typeArr, 0, frame.locals, 0, typeArr.length);
        Type[] typeArr2 = this.stack;
        System.arraycopy(typeArr2, 0, frame.stack, 0, typeArr2.length);
        frame.top = this.top;
        return frame;
    }

    public Frame copyStack() {
        Frame frame = new Frame(this.locals.length, this.stack.length);
        Type[] typeArr = this.stack;
        System.arraycopy(typeArr, 0, frame.stack, 0, typeArr.length);
        frame.top = this.top;
        return frame;
    }

    public boolean mergeStack(Frame frame) {
        if (this.top != frame.top) {
            throw new RuntimeException("Operand stacks could not be merged, they are different sizes!");
        }
        boolean z = false;
        for (int i = 0; i < this.top; i++) {
            Type type = this.stack[i];
            if (type != null) {
                Type merge = type.merge(frame.stack[i]);
                if (merge == Type.BOGUS) {
                    throw new RuntimeException("Operand stacks could not be merged due to differing primitive types: pos = " + i);
                }
                this.stack[i] = merge;
                if (!merge.equals(type) || merge.popChanged()) {
                    z = true;
                }
            }
        }
        return z;
    }

    public boolean merge(Frame frame) {
        int i = 0;
        boolean z = false;
        while (true) {
            Type[] typeArr = this.locals;
            if (i < typeArr.length) {
                Type type = typeArr[i];
                if (type != null) {
                    Type merge = type.merge(frame.locals[i]);
                    this.locals[i] = merge;
                    if (merge.equals(type) && !merge.popChanged()) {
                    }
                    z = true;
                } else {
                    Type type2 = frame.locals[i];
                    if (type2 != null) {
                        typeArr[i] = type2;
                        z = true;
                    }
                }
                i++;
            } else {
                return mergeStack(frame) | z;
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("locals = [");
        int i = 0;
        while (true) {
            Type[] typeArr = this.locals;
            if (i >= typeArr.length) {
                break;
            }
            Type type = typeArr[i];
            stringBuffer.append(type == null ? "empty" : type.toString());
            if (i < this.locals.length - 1) {
                stringBuffer.append(", ");
            }
            i++;
        }
        stringBuffer.append("] stack = [");
        for (int i2 = 0; i2 < this.top; i2++) {
            stringBuffer.append(this.stack[i2]);
            if (i2 < this.top - 1) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isJsrMerged() {
        return this.jsrMerged;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setJsrMerged(boolean z) {
        this.jsrMerged = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isRetMerged() {
        return this.retMerged;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRetMerged(boolean z) {
        this.retMerged = z;
    }
}
