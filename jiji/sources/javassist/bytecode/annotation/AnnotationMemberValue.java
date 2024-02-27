package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class AnnotationMemberValue extends MemberValue {
    Annotation value;

    public AnnotationMemberValue(ConstPool constPool) {
        this(null, constPool);
    }

    public AnnotationMemberValue(Annotation annotation, ConstPool constPool) {
        super('@', constPool);
        this.value = annotation;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) throws ClassNotFoundException {
        return AnnotationImpl.make(classLoader, getType(classLoader), classPool, this.value);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader classLoader) throws ClassNotFoundException {
        Annotation annotation = this.value;
        if (annotation == null) {
            throw new ClassNotFoundException("no type specified");
        }
        return loadClass(classLoader, annotation.getTypeName());
    }

    public Annotation getValue() {
        return this.value;
    }

    public void setValue(Annotation annotation) {
        this.value = annotation;
    }

    public String toString() {
        return this.value.toString();
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.annotationValue();
        this.value.write(annotationsWriter);
    }

    @Override // javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitAnnotationMemberValue(this);
    }
}
