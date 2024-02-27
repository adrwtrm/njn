package javassist.bytecode.annotation;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public class Annotation {
    Map<String, Pair> members;
    ConstPool pool;
    int typeIndex;

    /* loaded from: classes2.dex */
    public static class Pair {
        int name;
        MemberValue value;

        Pair() {
        }
    }

    public Annotation(int i, ConstPool constPool) {
        this.pool = constPool;
        this.typeIndex = i;
        this.members = null;
    }

    public Annotation(String str, ConstPool constPool) {
        this(constPool.addUtf8Info(Descriptor.of(str)), constPool);
    }

    public Annotation(ConstPool constPool, CtClass ctClass) throws NotFoundException {
        this(constPool.addUtf8Info(Descriptor.of(ctClass.getName())), constPool);
        if (!ctClass.isInterface()) {
            throw new RuntimeException("Only interfaces are allowed for Annotation creation.");
        }
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            this.members = new LinkedHashMap();
        }
        for (CtMethod ctMethod : declaredMethods) {
            addMemberValue(ctMethod.getName(), createMemberValue(constPool, ctMethod.getReturnType()));
        }
    }

    public static MemberValue createMemberValue(ConstPool constPool, CtClass ctClass) throws NotFoundException {
        if (ctClass == CtClass.booleanType) {
            return new BooleanMemberValue(constPool);
        }
        if (ctClass == CtClass.byteType) {
            return new ByteMemberValue(constPool);
        }
        if (ctClass == CtClass.charType) {
            return new CharMemberValue(constPool);
        }
        if (ctClass == CtClass.shortType) {
            return new ShortMemberValue(constPool);
        }
        if (ctClass == CtClass.intType) {
            return new IntegerMemberValue(constPool);
        }
        if (ctClass == CtClass.longType) {
            return new LongMemberValue(constPool);
        }
        if (ctClass == CtClass.floatType) {
            return new FloatMemberValue(constPool);
        }
        if (ctClass == CtClass.doubleType) {
            return new DoubleMemberValue(constPool);
        }
        if (ctClass.getName().equals("java.lang.Class")) {
            return new ClassMemberValue(constPool);
        }
        if (ctClass.getName().equals("java.lang.String")) {
            return new StringMemberValue(constPool);
        }
        if (ctClass.isArray()) {
            return new ArrayMemberValue(createMemberValue(constPool, ctClass.getComponentType()), constPool);
        }
        if (ctClass.isInterface()) {
            return new AnnotationMemberValue(new Annotation(constPool, ctClass), constPool);
        }
        EnumMemberValue enumMemberValue = new EnumMemberValue(constPool);
        enumMemberValue.setType(ctClass.getName());
        return enumMemberValue;
    }

    public void addMemberValue(int i, MemberValue memberValue) {
        Pair pair = new Pair();
        pair.name = i;
        pair.value = memberValue;
        addMemberValue(pair);
    }

    public void addMemberValue(String str, MemberValue memberValue) {
        Pair pair = new Pair();
        pair.name = this.pool.addUtf8Info(str);
        pair.value = memberValue;
        if (this.members == null) {
            this.members = new LinkedHashMap();
        }
        this.members.put(str, pair);
    }

    private void addMemberValue(Pair pair) {
        String utf8Info = this.pool.getUtf8Info(pair.name);
        if (this.members == null) {
            this.members = new LinkedHashMap();
        }
        this.members.put(utf8Info, pair);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("@");
        stringBuffer.append(getTypeName());
        if (this.members != null) {
            stringBuffer.append("(");
            for (String str : this.members.keySet()) {
                stringBuffer.append(str).append("=").append(getMemberValue(str)).append(", ");
            }
            stringBuffer.setLength(stringBuffer.length() - 2);
            stringBuffer.append(")");
        }
        return stringBuffer.toString();
    }

    public String getTypeName() {
        return Descriptor.toClassName(this.pool.getUtf8Info(this.typeIndex));
    }

    public Set<String> getMemberNames() {
        Map<String, Pair> map = this.members;
        if (map == null) {
            return null;
        }
        return map.keySet();
    }

    public MemberValue getMemberValue(String str) {
        Map<String, Pair> map = this.members;
        if (map == null || map.get(str) == null) {
            return null;
        }
        return this.members.get(str).value;
    }

    public Object toAnnotationType(ClassLoader classLoader, ClassPool classPool) throws ClassNotFoundException, NoSuchClassError {
        Class<?> loadClass = MemberValue.loadClass(classLoader, getTypeName());
        try {
            return AnnotationImpl.make(classLoader, loadClass, classPool, this);
        } catch (IllegalAccessError e) {
            throw new ClassNotFoundException(loadClass.getName(), e);
        } catch (IllegalArgumentException e2) {
            throw new ClassNotFoundException(loadClass.getName(), e2);
        }
    }

    public void write(AnnotationsWriter annotationsWriter) throws IOException {
        String utf8Info = this.pool.getUtf8Info(this.typeIndex);
        Map<String, Pair> map = this.members;
        if (map == null) {
            annotationsWriter.annotation(utf8Info, 0);
            return;
        }
        annotationsWriter.annotation(utf8Info, map.size());
        for (Pair pair : this.members.values()) {
            annotationsWriter.memberValuePair(pair.name);
            pair.value.write(annotationsWriter);
        }
    }

    public int hashCode() {
        int hashCode = getTypeName().hashCode();
        Map<String, Pair> map = this.members;
        return hashCode + (map == null ? 0 : map.hashCode());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Annotation)) {
            return false;
        }
        Annotation annotation = (Annotation) obj;
        if (getTypeName().equals(annotation.getTypeName())) {
            Map<String, Pair> map = annotation.members;
            Map<String, Pair> map2 = this.members;
            if (map2 == map) {
                return true;
            }
            if (map2 == null) {
                return map == null;
            } else if (map == null) {
                return false;
            } else {
                return map2.equals(map);
            }
        }
        return false;
    }
}
