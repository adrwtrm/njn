package javassist.bytecode.annotation;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.SignatureAttribute;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public class ClassMemberValue extends MemberValue {
    int valueIndex;

    public ClassMemberValue(int i, ConstPool constPool) {
        super('c', constPool);
        this.valueIndex = i;
    }

    public ClassMemberValue(String str, ConstPool constPool) {
        super('c', constPool);
        setValue(str);
    }

    public ClassMemberValue(ConstPool constPool) {
        super('c', constPool);
        setValue("java.lang.Class");
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) throws ClassNotFoundException {
        String value = getValue();
        if (value.equals("void")) {
            return Void.TYPE;
        }
        if (value.equals("int")) {
            return Integer.TYPE;
        }
        if (value.equals("byte")) {
            return Byte.TYPE;
        }
        if (value.equals("long")) {
            return Long.TYPE;
        }
        if (value.equals("double")) {
            return Double.TYPE;
        }
        if (value.equals(TypedValues.Custom.S_FLOAT)) {
            return Float.TYPE;
        }
        if (value.equals("char")) {
            return Character.TYPE;
        }
        if (value.equals("short")) {
            return Short.TYPE;
        }
        if (value.equals(TypedValues.Custom.S_BOOLEAN)) {
            return Boolean.TYPE;
        }
        return loadClass(classLoader, value);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) throws ClassNotFoundException {
        return loadClass(classLoader, "java.lang.Class");
    }

    public String getValue() {
        try {
            return SignatureAttribute.toTypeSignature(this.cp.getUtf8Info(this.valueIndex)).jvmTypeName();
        } catch (BadBytecode e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(String str) {
        this.valueIndex = this.cp.addUtf8Info(Descriptor.of(str));
    }

    public String toString() {
        return getValue().replace(Typography.dollar, '.') + ".class";
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.classInfoIndex(this.cp.getUtf8Info(this.valueIndex));
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitClassMemberValue(this);
    }
}
