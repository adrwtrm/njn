package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class ShortMemberValue extends MemberValue {
    int valueIndex;

    public ShortMemberValue(int i, ConstPool constPool) {
        super('S', constPool);
        this.valueIndex = i;
    }

    public ShortMemberValue(short s, ConstPool constPool) {
        super('S', constPool);
        setValue(s);
    }

    public ShortMemberValue(ConstPool constPool) {
        super('S', constPool);
        setValue((short) 0);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Short.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Short.TYPE;
    }

    public short getValue() {
        return (short) this.cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(short s) {
        this.valueIndex = this.cp.addIntegerInfo(s);
    }

    public String toString() {
        return Short.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitShortMemberValue(this);
    }
}
