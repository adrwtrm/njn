package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class DoubleMemberValue extends MemberValue {
    int valueIndex;

    public DoubleMemberValue(int i, ConstPool constPool) {
        super('D', constPool);
        this.valueIndex = i;
    }

    public DoubleMemberValue(double d, ConstPool constPool) {
        super('D', constPool);
        setValue(d);
    }

    public DoubleMemberValue(ConstPool constPool) {
        super('D', constPool);
        setValue(0.0d);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Double.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Double.TYPE;
    }

    public double getValue() {
        return this.cp.getDoubleInfo(this.valueIndex);
    }

    public void setValue(double d) {
        this.valueIndex = this.cp.addDoubleInfo(d);
    }

    public String toString() {
        return Double.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitDoubleMemberValue(this);
    }
}
