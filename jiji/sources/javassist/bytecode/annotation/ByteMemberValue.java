package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class ByteMemberValue extends MemberValue {
    int valueIndex;

    public ByteMemberValue(int i, ConstPool constPool) {
        super('B', constPool);
        this.valueIndex = i;
    }

    public ByteMemberValue(byte b, ConstPool constPool) {
        super('B', constPool);
        setValue(b);
    }

    public ByteMemberValue(ConstPool constPool) {
        super('B', constPool);
        setValue((byte) 0);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Byte.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Byte.TYPE;
    }

    public byte getValue() {
        return (byte) this.cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(byte b) {
        this.valueIndex = this.cp.addIntegerInfo(b);
    }

    public String toString() {
        return Byte.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitByteMemberValue(this);
    }
}
