package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class StringMemberValue extends MemberValue {
    int valueIndex;

    public StringMemberValue(int i, ConstPool constPool) {
        super('s', constPool);
        this.valueIndex = i;
    }

    public StringMemberValue(String str, ConstPool constPool) {
        super('s', constPool);
        setValue(str);
    }

    public StringMemberValue(ConstPool constPool) {
        super('s', constPool);
        setValue("");
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return getValue();
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return String.class;
    }

    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }

    public void setValue(String str) {
        this.valueIndex = this.cp.addUtf8Info(str);
    }

    public String toString() {
        return "\"" + getValue() + "\"";
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitStringMemberValue(this);
    }
}
