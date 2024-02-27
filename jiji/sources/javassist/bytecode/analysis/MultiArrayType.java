package javassist.bytecode.analysis;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/* loaded from: classes2.dex */
public class MultiArrayType extends Type {
    private MultiType component;
    private int dims;

    @Override // javassist.bytecode.analysis.Type
    public int getSize() {
        return 1;
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean isArray() {
        return true;
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean isReference() {
        return true;
    }

    public MultiArrayType(MultiType multiType, int i) {
        super(null);
        this.component = multiType;
        this.dims = i;
    }

    @Override // javassist.bytecode.analysis.Type
    public CtClass getCtClass() {
        CtClass ctClass = this.component.getCtClass();
        if (ctClass == null) {
            return null;
        }
        ClassPool classPool = ctClass.getClassPool();
        if (classPool == null) {
            classPool = ClassPool.getDefault();
        }
        try {
            return classPool.get(arrayName(ctClass.getName(), this.dims));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // javassist.bytecode.analysis.Type
    boolean popChanged() {
        return this.component.popChanged();
    }

    @Override // javassist.bytecode.analysis.Type
    public int getDimensions() {
        return this.dims;
    }

    @Override // javassist.bytecode.analysis.Type
    public Type getComponent() {
        int i = this.dims;
        return i == 1 ? this.component : new MultiArrayType(this.component, i - 1);
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean isAssignableFrom(Type type) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean isAssignableTo(Type type) {
        if (eq(type.getCtClass(), Type.OBJECT.getCtClass()) || eq(type.getCtClass(), Type.CLONEABLE.getCtClass()) || eq(type.getCtClass(), Type.SERIALIZABLE.getCtClass())) {
            return true;
        }
        if (type.isArray()) {
            Type rootComponent = getRootComponent(type);
            int dimensions = type.getDimensions();
            int i = this.dims;
            if (dimensions > i) {
                return false;
            }
            if (dimensions < i) {
                return eq(rootComponent.getCtClass(), Type.OBJECT.getCtClass()) || eq(rootComponent.getCtClass(), Type.CLONEABLE.getCtClass()) || eq(rootComponent.getCtClass(), Type.SERIALIZABLE.getCtClass());
            }
            return this.component.isAssignableTo(rootComponent);
        }
        return false;
    }

    @Override // javassist.bytecode.analysis.Type
    public int hashCode() {
        return this.component.hashCode() + this.dims;
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean equals(Object obj) {
        if (obj instanceof MultiArrayType) {
            MultiArrayType multiArrayType = (MultiArrayType) obj;
            return this.component.equals(multiArrayType.component) && this.dims == multiArrayType.dims;
        }
        return false;
    }

    @Override // javassist.bytecode.analysis.Type
    public String toString() {
        return arrayName(this.component.toString(), this.dims);
    }
}
