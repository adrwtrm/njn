package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class FloatMemberValue extends MemberValue {
    int valueIndex;

    public FloatMemberValue(int i, ConstPool constPool) {
        super('F', constPool);
        this.valueIndex = i;
    }

    public FloatMemberValue(float f, ConstPool constPool) {
        super('F', constPool);
        setValue(f);
    }

    public FloatMemberValue(ConstPool constPool) {
        super('F', constPool);
        setValue(0.0f);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Float.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Float.TYPE;
    }

    public float getValue() {
        return this.cp.getFloatInfo(this.valueIndex);
    }

    public void setValue(float f) {
        this.valueIndex = this.cp.addFloatInfo(f);
    }

    public String toString() {
        return Float.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitFloatMemberValue(this);
    }
}
