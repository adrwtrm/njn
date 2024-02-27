package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class CharMemberValue extends MemberValue {
    int valueIndex;

    public CharMemberValue(int i, ConstPool constPool) {
        super('C', constPool);
        this.valueIndex = i;
    }

    public CharMemberValue(char c, ConstPool constPool) {
        super('C', constPool);
        setValue(c);
    }

    public CharMemberValue(ConstPool constPool) {
        super('C', constPool);
        setValue((char) 0);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Character.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Character.TYPE;
    }

    public char getValue() {
        return (char) this.cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(char c) {
        this.valueIndex = this.cp.addIntegerInfo(c);
    }

    public String toString() {
        return Character.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitCharMemberValue(this);
    }
}
