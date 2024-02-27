package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class ArrayMemberValue extends MemberValue {
    MemberValue type;
    MemberValue[] values;

    public ArrayMemberValue(ConstPool constPool) {
        super('[', constPool);
        this.type = null;
        this.values = null;
    }

    public ArrayMemberValue(MemberValue memberValue, ConstPool constPool) {
        super('[', constPool);
        this.type = memberValue;
        this.values = null;
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) throws ClassNotFoundException {
        Class<?> type;
        MemberValue[] memberValueArr = this.values;
        if (memberValueArr == null) {
            throw new ClassNotFoundException("no array elements found: " + method.getName());
        }
        int length = memberValueArr.length;
        MemberValue memberValue = this.type;
        if (memberValue == null) {
            type = method.getReturnType().getComponentType();
            if (type == null || length > 0) {
                throw new ClassNotFoundException("broken array type: " + method.getName());
            }
        } else {
            type = memberValue.getType(classLoader);
        }
        Object newInstance = Array.newInstance(type, length);
        for (int i = 0; i < length; i++) {
            Array.set(newInstance, i, this.values[i].getValue(classLoader, classPool, method));
        }
        return newInstance;
    }

    @Override // javassist.bytecode.annotation.MemberValue
    Class<?> getType(ClassLoader classLoader) throws ClassNotFoundException {
        MemberValue memberValue = this.type;
        if (memberValue == null) {
            throw new ClassNotFoundException("no array type specified");
        }
        return Array.newInstance(memberValue.getType(classLoader), 0).getClass();
    }

    public MemberValue getType() {
        return this.type;
    }

    public MemberValue[] getValue() {
        return this.values;
    }

    public void setValue(MemberValue[] memberValueArr) {
        this.values = memberValueArr;
        if (memberValueArr == null || memberValueArr.length <= 0) {
            return;
        }
        this.type = memberValueArr[0];
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        if (this.values != null) {
            int i = 0;
            while (true) {
                MemberValue[] memberValueArr = this.values;
                if (i >= memberValueArr.length) {
                    break;
                }
                stringBuffer.append(memberValueArr[i].toString());
                i++;
                if (i < this.values.length) {
                    stringBuffer.append(", ");
                }
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        MemberValue[] memberValueArr = this.values;
        int length = memberValueArr == null ? 0 : memberValueArr.length;
        annotationsWriter.arrayValue(length);
        for (int i = 0; i < length; i++) {
            this.values[i].write(annotationsWriter);
        }
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitArrayMemberValue(this);
    }
}
