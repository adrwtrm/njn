package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public class EnumMemberValue extends MemberValue {
    int typeIndex;
    int valueIndex;

    public EnumMemberValue(int i, int i2, ConstPool constPool) {
        super('e', constPool);
        this.typeIndex = i;
        this.valueIndex = i2;
    }

    public EnumMemberValue(ConstPool constPool) {
        super('e', constPool);
        this.valueIndex = 0;
        this.typeIndex = 0;
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) throws ClassNotFoundException {
        try {
            return getType(classLoader).getField(getValue()).get(null);
        } catch (IllegalAccessException unused) {
            throw new ClassNotFoundException(getType() + "." + getValue());
        } catch (NoSuchFieldException unused2) {
            throw new ClassNotFoundException(getType() + "." + getValue());
        }
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) throws ClassNotFoundException {
        return loadClass(classLoader, getType());
    }

    public String getType() {
        return Descriptor.toClassName(this.cp.getUtf8Info(this.typeIndex));
    }

    public void setType(String str) {
        this.typeIndex = this.cp.addUtf8Info(Descriptor.of(str));
    }

    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }

    public void setValue(String str) {
        this.valueIndex = this.cp.addUtf8Info(str);
    }

    public String toString() {
        return getType() + "." + getValue();
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.enumConstValue(this.cp.getUtf8Info(this.typeIndex), getValue());
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitEnumMemberValue(this);
    }
}
