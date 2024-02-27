package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class IntegerMemberValue extends MemberValue {
    int valueIndex;

    public IntegerMemberValue(int i, ConstPool constPool) {
        super('I', constPool);
        this.valueIndex = i;
    }

    public IntegerMemberValue(ConstPool constPool, int i) {
        super('I', constPool);
        setValue(i);
    }

    public IntegerMemberValue(ConstPool constPool) {
        super('I', constPool);
        setValue(0);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Integer.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Integer.TYPE;
    }

    public int getValue() {
        return this.cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(int i) {
        this.valueIndex = this.cp.addIntegerInfo(i);
    }

    public String toString() {
        return Integer.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitIntegerMemberValue(this);
    }
}
