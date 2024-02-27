package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class LongMemberValue extends MemberValue {
    int valueIndex;

    public LongMemberValue(int i, ConstPool constPool) {
        super('J', constPool);
        this.valueIndex = i;
    }

    public LongMemberValue(long j, ConstPool constPool) {
        super('J', constPool);
        setValue(j);
    }

    public LongMemberValue(ConstPool constPool) {
        super('J', constPool);
        setValue(0L);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Long.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Long.TYPE;
    }

    public long getValue() {
        return this.cp.getLongInfo(this.valueIndex);
    }

    public void setValue(long j) {
        this.valueIndex = this.cp.addLongInfo(j);
    }

    public String toString() {
        return Long.toString(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitLongMemberValue(this);
    }
}
