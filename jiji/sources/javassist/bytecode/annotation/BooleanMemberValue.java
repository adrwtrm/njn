package javassist.bytecode.annotation;

import com.epson.iprojection.common.CommonDefine;
import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class BooleanMemberValue extends MemberValue {
    int valueIndex;

    public BooleanMemberValue(int i, ConstPool constPool) {
        super('Z', constPool);
        this.valueIndex = i;
    }

    public BooleanMemberValue(boolean z, ConstPool constPool) {
        super('Z', constPool);
        setValue(z);
    }

    public BooleanMemberValue(ConstPool constPool) {
        super('Z', constPool);
        setValue(false);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) {
        return Boolean.valueOf(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) {
        return Boolean.TYPE;
    }

    public boolean getValue() {
        return this.cp.getIntegerInfo(this.valueIndex) != 0;
    }

    public void setValue(boolean z) {
        this.valueIndex = this.cp.addIntegerInfo(z ? 1 : 0);
    }

    public String toString() {
        return getValue() ? CommonDefine.TRUE : CommonDefine.FALSE;
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitBooleanMemberValue(this);
    }
}
