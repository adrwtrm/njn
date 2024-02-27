package javassist.bytecode.analysis;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/* loaded from: classes2.dex */
public class Type {
    public static final Type BOGUS;
    public static final Type BOOLEAN;
    public static final Type BYTE;
    public static final Type CHAR;
    public static final Type CLONEABLE;
    public static final Type DOUBLE;
    public static final Type FLOAT;
    public static final Type INTEGER;
    public static final Type LONG;
    public static final Type OBJECT;
    public static final Type RETURN_ADDRESS;
    public static final Type SERIALIZABLE;
    public static final Type SHORT;
    public static final Type THROWABLE;
    public static final Type TOP;
    public static final Type UNINIT;
    public static final Type VOID;
    private static final Map<CtClass, Type> prims;
    private final CtClass clazz;
    private final boolean special;

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean popChanged() {
        return false;
    }

    static {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        prims = identityHashMap;
        Type type = new Type(CtClass.doubleType);
        DOUBLE = type;
        Type type2 = new Type(CtClass.booleanType);
        BOOLEAN = type2;
        Type type3 = new Type(CtClass.longType);
        LONG = type3;
        Type type4 = new Type(CtClass.charType);
        CHAR = type4;
        Type type5 = new Type(CtClass.byteType);
        BYTE = type5;
        Type type6 = new Type(CtClass.shortType);
        SHORT = type6;
        Type type7 = new Type(CtClass.intType);
        INTEGER = type7;
        Type type8 = new Type(CtClass.floatType);
        FLOAT = type8;
        Type type9 = new Type(CtClass.voidType);
        VOID = type9;
        UNINIT = new Type(null);
        RETURN_ADDRESS = new Type(null, true);
        TOP = new Type(null, true);
        BOGUS = new Type(null, true);
        OBJECT = lookupType("java.lang.Object");
        SERIALIZABLE = lookupType("java.io.Serializable");
        CLONEABLE = lookupType("java.lang.Cloneable");
        THROWABLE = lookupType("java.lang.Throwable");
        identityHashMap.put(CtClass.doubleType, type);
        identityHashMap.put(CtClass.longType, type3);
        identityHashMap.put(CtClass.charType, type4);
        identityHashMap.put(CtClass.shortType, type6);
        identityHashMap.put(CtClass.intType, type7);
        identityHashMap.put(CtClass.floatType, type8);
        identityHashMap.put(CtClass.byteType, type5);
        identityHashMap.put(CtClass.booleanType, type2);
        identityHashMap.put(CtClass.voidType, type9);
    }

    public static Type get(CtClass ctClass) {
        Type type = prims.get(ctClass);
        return type != null ? type : new Type(ctClass);
    }

    private static Type lookupType(String str) {
        try {
            return new Type(ClassPool.getDefault().get(str));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Type(CtClass ctClass) {
        this(ctClass, false);
    }

    private Type(CtClass ctClass, boolean z) {
        this.clazz = ctClass;
        this.special = z;
    }

    public int getSize() {
        return (this.clazz == CtClass.doubleType || this.clazz == CtClass.longType || this == TOP) ? 2 : 1;
    }

    public CtClass getCtClass() {
        return this.clazz;
    }

    public boolean isReference() {
        CtClass ctClass;
        return !this.special && ((ctClass = this.clazz) == null || !ctClass.isPrimitive());
    }

    public boolean isSpecial() {
        return this.special;
    }

    public boolean isArray() {
        CtClass ctClass = this.clazz;
        return ctClass != null && ctClass.isArray();
    }

    public int getDimensions() {
        int i = 0;
        if (isArray()) {
            String name = this.clazz.getName();
            int length = name.length() - 1;
            while (name.charAt(length) == ']') {
                length -= 2;
                i++;
            }
            return i;
        }
        return 0;
    }

    public Type getComponent() {
        CtClass ctClass = this.clazz;
        if (ctClass == null || !ctClass.isArray()) {
            return null;
        }
        try {
            CtClass componentType = this.clazz.getComponentType();
            Type type = prims.get(componentType);
            return type != null ? type : new Type(componentType);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAssignableFrom(Type type) {
        if (this == type) {
            return true;
        }
        Type type2 = UNINIT;
        if ((type == type2 && isReference()) || (this == type2 && type.isReference())) {
            return true;
        }
        if (type instanceof MultiType) {
            return ((MultiType) type).isAssignableTo(this);
        }
        if (type instanceof MultiArrayType) {
            return ((MultiArrayType) type).isAssignableTo(this);
        }
        CtClass ctClass = this.clazz;
        if (ctClass == null || ctClass.isPrimitive()) {
            return false;
        }
        try {
            return type.clazz.subtypeOf(this.clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Type merge(Type type) {
        Type type2;
        if (type == this || type == null || type == (type2 = UNINIT)) {
            return this;
        }
        if (this == type2) {
            return type;
        }
        if (!type.isReference() || !isReference()) {
            return BOGUS;
        }
        if (type instanceof MultiType) {
            return type.merge(this);
        }
        if (type.isArray() && isArray()) {
            return mergeArray(type);
        }
        try {
            return mergeClasses(type);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Type getRootComponent(Type type) {
        while (type.isArray()) {
            type = type.getComponent();
        }
        return type;
    }

    private Type createArray(Type type, int i) {
        if (type instanceof MultiType) {
            return new MultiArrayType((MultiType) type, i);
        }
        try {
            return get(getClassPool(type).get(arrayName(type.clazz.getName(), i)));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String arrayName(String str, int i) {
        int length = str.length();
        int i2 = (i * 2) + length;
        char[] cArr = new char[i2];
        str.getChars(0, length, cArr, 0);
        while (length < i2) {
            int i3 = length + 1;
            cArr[length] = '[';
            length = i3 + 1;
            cArr[i3] = ']';
        }
        return new String(cArr);
    }

    private ClassPool getClassPool(Type type) {
        ClassPool classPool = type.clazz.getClassPool();
        return classPool != null ? classPool : ClassPool.getDefault();
    }

    private Type mergeArray(Type type) {
        Type rootComponent = getRootComponent(type);
        Type rootComponent2 = getRootComponent(this);
        int dimensions = type.getDimensions();
        int dimensions2 = getDimensions();
        if (dimensions == dimensions2) {
            Type merge = rootComponent2.merge(rootComponent);
            if (merge == BOGUS) {
                return OBJECT;
            }
            return createArray(merge, dimensions2);
        }
        if (dimensions >= dimensions2) {
            rootComponent = rootComponent2;
            dimensions = dimensions2;
        }
        if (eq(CLONEABLE.clazz, rootComponent.clazz) || eq(SERIALIZABLE.clazz, rootComponent.clazz)) {
            return createArray(rootComponent, dimensions);
        }
        return createArray(OBJECT, dimensions);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0020, code lost:
        r0 = r0.getSuperclass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
        if (r0 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002a, code lost:
        if (eq(r5, r6) != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002c, code lost:
        r5 = r5.getSuperclass();
        r6 = r6.getSuperclass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0035, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0036, code lost:
        r5 = r5.getSuperclass();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static javassist.CtClass findCommonSuperClass(javassist.CtClass r5, javassist.CtClass r6) throws javassist.NotFoundException {
        /*
            r0 = r5
            r1 = r6
        L2:
            boolean r2 = eq(r0, r1)
            if (r2 == 0) goto Lf
            javassist.CtClass r2 = r0.getSuperclass()
            if (r2 == 0) goto Lf
            return r0
        Lf:
            javassist.CtClass r2 = r0.getSuperclass()
            javassist.CtClass r3 = r1.getSuperclass()
            if (r3 != 0) goto L1a
            goto L20
        L1a:
            if (r2 != 0) goto L3b
            r0 = r1
            r4 = r6
            r6 = r5
            r5 = r4
        L20:
            javassist.CtClass r0 = r0.getSuperclass()
            if (r0 != 0) goto L36
        L26:
            boolean r0 = eq(r5, r6)
            if (r0 != 0) goto L35
            javassist.CtClass r5 = r5.getSuperclass()
            javassist.CtClass r6 = r6.getSuperclass()
            goto L26
        L35:
            return r5
        L36:
            javassist.CtClass r5 = r5.getSuperclass()
            goto L20
        L3b:
            r0 = r2
            r1 = r3
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.bytecode.analysis.Type.findCommonSuperClass(javassist.CtClass, javassist.CtClass):javassist.CtClass");
    }

    private Type mergeClasses(Type type) throws NotFoundException {
        CtClass findCommonSuperClass = findCommonSuperClass(this.clazz, type.clazz);
        if (findCommonSuperClass.getSuperclass() == null) {
            Map<String, CtClass> findCommonInterfaces = findCommonInterfaces(type);
            if (findCommonInterfaces.size() == 1) {
                return new Type(findCommonInterfaces.values().iterator().next());
            }
            if (findCommonInterfaces.size() > 1) {
                return new MultiType(findCommonInterfaces);
            }
            return new Type(findCommonSuperClass);
        }
        Map<String, CtClass> findExclusiveDeclaredInterfaces = findExclusiveDeclaredInterfaces(type, findCommonSuperClass);
        if (findExclusiveDeclaredInterfaces.size() > 0) {
            return new MultiType(findExclusiveDeclaredInterfaces, new Type(findCommonSuperClass));
        }
        return new Type(findCommonSuperClass);
    }

    private Map<String, CtClass> findCommonInterfaces(Type type) {
        return findCommonInterfaces(getAllInterfaces(type.clazz, null), getAllInterfaces(this.clazz, null));
    }

    private Map<String, CtClass> findExclusiveDeclaredInterfaces(Type type, CtClass ctClass) {
        Map<String, CtClass> declaredInterfaces = getDeclaredInterfaces(type.clazz, null);
        Map<String, CtClass> declaredInterfaces2 = getDeclaredInterfaces(this.clazz, null);
        for (String str : getAllInterfaces(ctClass, null).keySet()) {
            declaredInterfaces.remove(str);
            declaredInterfaces2.remove(str);
        }
        return findCommonInterfaces(declaredInterfaces, declaredInterfaces2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, CtClass> findCommonInterfaces(Map<String, CtClass> map, Map<String, CtClass> map2) {
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        if (map == null || map.isEmpty()) {
            map2.clear();
        }
        for (String str : map2.keySet()) {
            if (!map.containsKey(str)) {
                map2.remove(str);
            }
        }
        for (CtClass ctClass : map2.values()) {
            try {
                for (CtClass ctClass2 : ctClass.getInterfaces()) {
                    map2.remove(ctClass2.getName());
                }
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, CtClass> getAllInterfaces(CtClass ctClass, Map<String, CtClass> map) {
        CtClass[] interfaces;
        if (map == null) {
            map = new HashMap<>();
        }
        if (ctClass.isInterface()) {
            map.put(ctClass.getName(), ctClass);
        }
        do {
            try {
                for (CtClass ctClass2 : ctClass.getInterfaces()) {
                    map.put(ctClass2.getName(), ctClass2);
                    getAllInterfaces(ctClass2, map);
                }
                ctClass = ctClass.getSuperclass();
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        } while (ctClass != null);
        return map;
    }

    Map<String, CtClass> getDeclaredInterfaces(CtClass ctClass, Map<String, CtClass> map) {
        CtClass[] interfaces;
        if (map == null) {
            map = new HashMap<>();
        }
        if (ctClass.isInterface()) {
            map.put(ctClass.getName(), ctClass);
        }
        try {
            for (CtClass ctClass2 : ctClass.getInterfaces()) {
                map.put(ctClass2.getName(), ctClass2);
                getDeclaredInterfaces(ctClass2, map);
            }
            return map;
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int hashCode() {
        return getClass().hashCode() + this.clazz.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof Type) && obj.getClass() == getClass() && eq(this.clazz, ((Type) obj).clazz);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean eq(CtClass ctClass, CtClass ctClass2) {
        return ctClass == ctClass2 || !(ctClass == null || ctClass2 == null || !ctClass.getName().equals(ctClass2.getName()));
    }

    public String toString() {
        if (this == BOGUS) {
            return "BOGUS";
        }
        if (this == UNINIT) {
            return "UNINIT";
        }
        if (this == RETURN_ADDRESS) {
            return "RETURN ADDRESS";
        }
        if (this == TOP) {
            return "TOP";
        }
        CtClass ctClass = this.clazz;
        return ctClass == null ? "null" : ctClass.getName();
    }
}
