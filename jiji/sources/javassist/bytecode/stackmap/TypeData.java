package javassist.bytecode.stackmap;

import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public abstract class TypeData {
    public void constructorCalled(int i) {
    }

    public int dfs(List<TypeData> list, int i, ClassPool classPool) throws NotFoundException {
        return i;
    }

    public abstract boolean eq(TypeData typeData);

    public abstract TypeData getArrayType(int i) throws NotFoundException;

    public abstract String getName();

    public abstract int getTypeData(ConstPool constPool);

    public abstract int getTypeTag();

    public abstract boolean is2WordType();

    public abstract BasicType isBasicType();

    public boolean isNullType() {
        return false;
    }

    public boolean isUninit() {
        return false;
    }

    public abstract void setType(String str, ClassPool classPool) throws BadBytecode;

    abstract String toString2(Set<TypeData> set);

    protected TypeVar toTypeVar(int i) {
        return null;
    }

    public static TypeData[] make(int i) {
        TypeData[] typeDataArr = new TypeData[i];
        for (int i2 = 0; i2 < i; i2++) {
            typeDataArr[i2] = TypeTag.TOP;
        }
        return typeDataArr;
    }

    protected TypeData() {
    }

    private static void setType(TypeData typeData, String str, ClassPool classPool) throws BadBytecode {
        typeData.setType(str, classPool);
    }

    public TypeData join() {
        return new TypeVar(this);
    }

    public String toString() {
        return super.toString() + "(" + toString2(new HashSet()) + ")";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class BasicType extends TypeData {
        private char decodedName;
        private String name;
        private int typeTag;

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean eq(TypeData typeData) {
            return this == typeData;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return 0;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public BasicType isBasicType() {
            return this;
        }

        public BasicType(String str, int i, char c) {
            this.name = str;
            this.typeTag = i;
            this.decodedName = c;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return this.typeTag;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData join() {
            return this == TypeTag.TOP ? this : super.join();
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean is2WordType() {
            int i = this.typeTag;
            return i == 4 || i == 3;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public String getName() {
            return this.name;
        }

        public char getDecodedName() {
            return this.decodedName;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void setType(String str, ClassPool classPool) throws BadBytecode {
            throw new BadBytecode("conflict: " + this.name + " and " + str);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) throws NotFoundException {
            if (this == TypeTag.TOP) {
                return this;
            }
            if (i >= 0) {
                if (i == 0) {
                    return this;
                }
                char[] cArr = new char[i + 1];
                for (int i2 = 0; i2 < i; i2++) {
                    cArr[i2] = '[';
                }
                cArr[i] = this.decodedName;
                return new ClassName(new String(cArr));
            }
            throw new NotFoundException("no element type: " + this.name);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return this.name;
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class AbsTypeVar extends TypeData {
        @Override // javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return 7;
        }

        public abstract void merge(TypeData typeData);

        @Override // javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return constPool.addClassInfo(getName());
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean eq(TypeData typeData) {
            return getName().equals(typeData.getName());
        }
    }

    /* loaded from: classes2.dex */
    public static class TypeVar extends AbsTypeVar {
        protected String fixedType;
        private boolean is2WordType;
        private int visited = 0;
        private int smallest = 0;
        private boolean inList = false;
        private int dimension = 0;
        protected List<String> uppers = null;
        protected List<TypeData> lowers = new ArrayList(2);
        protected List<TypeData> usedBy = new ArrayList(2);

        public TypeVar(TypeData typeData) {
            merge(typeData);
            this.fixedType = null;
            this.is2WordType = typeData.is2WordType();
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public String getName() {
            String str = this.fixedType;
            return str == null ? this.lowers.get(0).getName() : str;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public BasicType isBasicType() {
            if (this.fixedType == null) {
                return this.lowers.get(0).isBasicType();
            }
            return null;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean is2WordType() {
            if (this.fixedType == null) {
                return this.is2WordType;
            }
            return false;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean isNullType() {
            if (this.fixedType == null) {
                return this.lowers.get(0).isNullType();
            }
            return false;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean isUninit() {
            if (this.fixedType == null) {
                return this.lowers.get(0).isUninit();
            }
            return false;
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar
        public void merge(TypeData typeData) {
            this.lowers.add(typeData);
            if (typeData instanceof TypeVar) {
                ((TypeVar) typeData).usedBy.add(this);
            }
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar, javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeTag();
            }
            return super.getTypeTag();
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar, javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeData(constPool);
            }
            return super.getTypeData(constPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void setType(String str, ClassPool classPool) throws BadBytecode {
            if (this.uppers == null) {
                this.uppers = new ArrayList();
            }
            this.uppers.add(str);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        protected TypeVar toTypeVar(int i) {
            this.dimension = i;
            return this;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) throws NotFoundException {
            if (i == 0) {
                return this;
            }
            BasicType isBasicType = isBasicType();
            if (isBasicType == null) {
                if (isNullType()) {
                    return new NullType();
                }
                return new ClassName(getName()).getArrayType(i);
            }
            return isBasicType.getArrayType(i);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public int dfs(List<TypeData> list, int i, ClassPool classPool) throws NotFoundException {
            TypeVar typeVar;
            if (this.visited > 0) {
                return i;
            }
            int i2 = i + 1;
            this.smallest = i2;
            this.visited = i2;
            list.add(this);
            this.inList = true;
            int size = this.lowers.size();
            for (int i3 = 0; i3 < size; i3++) {
                TypeVar typeVar2 = this.lowers.get(i3).toTypeVar(this.dimension);
                if (typeVar2 != null) {
                    int i4 = typeVar2.visited;
                    if (i4 == 0) {
                        i2 = typeVar2.dfs(list, i2, classPool);
                        int i5 = typeVar2.smallest;
                        if (i5 < this.smallest) {
                            this.smallest = i5;
                        }
                    } else if (typeVar2.inList && i4 < this.smallest) {
                        this.smallest = i4;
                    }
                }
            }
            if (this.visited == this.smallest) {
                ArrayList arrayList = new ArrayList();
                do {
                    typeVar = (TypeVar) list.remove(list.size() - 1);
                    typeVar.inList = false;
                    arrayList.add(typeVar);
                } while (typeVar != this);
                fixTypes(arrayList, classPool);
            }
            return i2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0049, code lost:
            r3 = javassist.bytecode.stackmap.TypeTag.TOP;
            r5 = true;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void fixTypes(java.util.List<javassist.bytecode.stackmap.TypeData> r14, javassist.ClassPool r15) throws javassist.NotFoundException {
            /*
                r13 = this;
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>()
                int r1 = r14.size()
                r2 = 0
                r3 = 0
                r4 = r2
                r5 = r4
            Ld:
                if (r4 >= r1) goto L62
                java.lang.Object r6 = r14.get(r4)
                javassist.bytecode.stackmap.TypeData$TypeVar r6 = (javassist.bytecode.stackmap.TypeData.TypeVar) r6
                java.util.List<javassist.bytecode.stackmap.TypeData> r7 = r6.lowers
                int r8 = r7.size()
                r9 = r2
            L1c:
                if (r9 >= r8) goto L5f
                java.lang.Object r10 = r7.get(r9)
                javassist.bytecode.stackmap.TypeData r10 = (javassist.bytecode.stackmap.TypeData) r10
                int r11 = r6.dimension
                javassist.bytecode.stackmap.TypeData r10 = r10.getArrayType(r11)
                javassist.bytecode.stackmap.TypeData$BasicType r11 = r10.isBasicType()
                r12 = 1
                if (r3 != 0) goto L41
                if (r11 != 0) goto L3e
                boolean r3 = r10.isUninit()
                r5 = r2
                if (r3 == 0) goto L3c
                r3 = r10
                goto L5f
            L3c:
                r3 = r10
                goto L4d
            L3e:
                r3 = r11
                r5 = r12
                goto L4d
            L41:
                if (r11 != 0) goto L45
                if (r5 != 0) goto L49
            L45:
                if (r11 == 0) goto L4d
                if (r3 == r11) goto L4d
            L49:
                javassist.bytecode.stackmap.TypeData$BasicType r3 = javassist.bytecode.stackmap.TypeTag.TOP
                r5 = r12
                goto L5f
            L4d:
                if (r11 != 0) goto L5c
                boolean r11 = r10.isNullType()
                if (r11 != 0) goto L5c
                java.lang.String r10 = r10.getName()
                r0.add(r10)
            L5c:
                int r9 = r9 + 1
                goto L1c
            L5f:
                int r4 = r4 + 1
                goto Ld
            L62:
                if (r5 == 0) goto L6e
                boolean r15 = r3.is2WordType()
                r13.is2WordType = r15
                r13.fixTypes1(r14, r3)
                goto L7a
            L6e:
                java.lang.String r15 = r13.fixTypes2(r14, r0, r15)
                javassist.bytecode.stackmap.TypeData$ClassName r0 = new javassist.bytecode.stackmap.TypeData$ClassName
                r0.<init>(r15)
                r13.fixTypes1(r14, r0)
            L7a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: javassist.bytecode.stackmap.TypeData.TypeVar.fixTypes(java.util.List, javassist.ClassPool):void");
        }

        private void fixTypes1(List<TypeData> list, TypeData typeData) throws NotFoundException {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TypeVar typeVar = (TypeVar) list.get(i);
                TypeData arrayType = typeData.getArrayType(-typeVar.dimension);
                if (arrayType.isBasicType() == null) {
                    typeVar.fixedType = arrayType.getName();
                } else {
                    typeVar.lowers.clear();
                    typeVar.lowers.add(arrayType);
                    typeVar.is2WordType = arrayType.is2WordType();
                }
            }
        }

        private String fixTypes2(List<TypeData> list, Set<String> set, ClassPool classPool) throws NotFoundException {
            Iterator<String> it = set.iterator();
            if (set.size() == 0) {
                return null;
            }
            if (set.size() == 1) {
                return it.next();
            }
            CtClass ctClass = classPool.get(it.next());
            while (it.hasNext()) {
                ctClass = commonSuperClassEx(ctClass, classPool.get(it.next()));
            }
            if (ctClass.getSuperclass() == null || isObjectArray(ctClass)) {
                ctClass = fixByUppers(list, classPool, new HashSet(), ctClass);
            }
            if (ctClass.isArray()) {
                return Descriptor.toJvmName(ctClass);
            }
            return ctClass.getName();
        }

        private static boolean isObjectArray(CtClass ctClass) throws NotFoundException {
            return ctClass.isArray() && ctClass.getComponentType().getSuperclass() == null;
        }

        private CtClass fixByUppers(List<TypeData> list, ClassPool classPool, Set<TypeData> set, CtClass ctClass) throws NotFoundException {
            if (list == null) {
                return ctClass;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TypeVar typeVar = (TypeVar) list.get(i);
                if (!set.add(typeVar)) {
                    return ctClass;
                }
                List<String> list2 = typeVar.uppers;
                if (list2 != null) {
                    int size2 = list2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        CtClass ctClass2 = classPool.get(typeVar.uppers.get(i2));
                        if (ctClass2.subtypeOf(ctClass)) {
                            ctClass = ctClass2;
                        }
                    }
                }
                ctClass = fixByUppers(typeVar.usedBy, classPool, set, ctClass);
            }
            return ctClass;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            TypeData typeData;
            set.add(this);
            return (this.lowers.size() <= 0 || (typeData = this.lowers.get(0)) == null || set.contains(typeData)) ? "?" : typeData.toString2(set);
        }
    }

    public static CtClass commonSuperClassEx(CtClass ctClass, CtClass ctClass2) throws NotFoundException {
        if (ctClass == ctClass2) {
            return ctClass;
        }
        if (ctClass.isArray() && ctClass2.isArray()) {
            CtClass componentType = ctClass.getComponentType();
            CtClass componentType2 = ctClass2.getComponentType();
            CtClass commonSuperClassEx = commonSuperClassEx(componentType, componentType2);
            if (commonSuperClassEx == componentType) {
                return ctClass;
            }
            if (commonSuperClassEx == componentType2) {
                return ctClass2;
            }
            return ctClass.getClassPool().get(commonSuperClassEx != null ? commonSuperClassEx.getName() + "[]" : "java.lang.Object");
        } else if (ctClass.isPrimitive() || ctClass2.isPrimitive()) {
            return null;
        } else {
            if (ctClass.isArray() || ctClass2.isArray()) {
                return ctClass.getClassPool().get("java.lang.Object");
            }
            return commonSuperClass(ctClass, ctClass2);
        }
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
    public static javassist.CtClass commonSuperClass(javassist.CtClass r5, javassist.CtClass r6) throws javassist.NotFoundException {
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
        throw new UnsupportedOperationException("Method not decompiled: javassist.bytecode.stackmap.TypeData.commonSuperClass(javassist.CtClass, javassist.CtClass):javassist.CtClass");
    }

    static boolean eq(CtClass ctClass, CtClass ctClass2) {
        return ctClass == ctClass2 || !(ctClass == null || ctClass2 == null || !ctClass.getName().equals(ctClass2.getName()));
    }

    public static void aastore(TypeData typeData, TypeData typeData2, ClassPool classPool) throws BadBytecode {
        boolean z = typeData instanceof AbsTypeVar;
        if (z && !typeData2.isNullType()) {
            ((AbsTypeVar) typeData).merge(ArrayType.make(typeData2));
        }
        if (typeData2 instanceof AbsTypeVar) {
            if (z) {
                ArrayElement.make(typeData);
            } else if (typeData instanceof ClassName) {
                if (typeData.isNullType()) {
                    return;
                }
                typeData2.setType(ArrayElement.typeName(typeData.getName()), classPool);
            } else {
                throw new BadBytecode("bad AASTORE: " + typeData);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class ArrayType extends AbsTypeVar {
        private AbsTypeVar element;

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean is2WordType() {
            return false;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public BasicType isBasicType() {
            return null;
        }

        private ArrayType(AbsTypeVar absTypeVar) {
            this.element = absTypeVar;
        }

        static TypeData make(TypeData typeData) throws BadBytecode {
            if (typeData instanceof ArrayElement) {
                return ((ArrayElement) typeData).arrayType();
            }
            if (typeData instanceof AbsTypeVar) {
                return new ArrayType((AbsTypeVar) typeData);
            }
            if ((typeData instanceof ClassName) && !typeData.isNullType()) {
                return new ClassName(typeName(typeData.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + typeData);
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar
        public void merge(TypeData typeData) {
            try {
                if (typeData.isNullType()) {
                    return;
                }
                this.element.merge(ArrayElement.make(typeData));
            } catch (BadBytecode e) {
                throw new RuntimeException("fatal: " + e);
            }
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public String getName() {
            return typeName(this.element.getName());
        }

        public AbsTypeVar elementType() {
            return this.element;
        }

        public static String typeName(String str) {
            if (str.charAt(0) == '[') {
                return "[" + str;
            }
            return "[L" + str.replace('.', '/') + ";";
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void setType(String str, ClassPool classPool) throws BadBytecode {
            this.element.setType(ArrayElement.typeName(str), classPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        protected TypeVar toTypeVar(int i) {
            return this.element.toTypeVar(i + 1);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) throws NotFoundException {
            return this.element.getArrayType(i + 1);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public int dfs(List<TypeData> list, int i, ClassPool classPool) throws NotFoundException {
            return this.element.dfs(list, i, classPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return "[" + this.element.toString2(set);
        }
    }

    /* loaded from: classes2.dex */
    public static class ArrayElement extends AbsTypeVar {
        private AbsTypeVar array;

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean is2WordType() {
            return false;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public BasicType isBasicType() {
            return null;
        }

        private ArrayElement(AbsTypeVar absTypeVar) {
            this.array = absTypeVar;
        }

        public static TypeData make(TypeData typeData) throws BadBytecode {
            if (typeData instanceof ArrayType) {
                return ((ArrayType) typeData).elementType();
            }
            if (typeData instanceof AbsTypeVar) {
                return new ArrayElement((AbsTypeVar) typeData);
            }
            if ((typeData instanceof ClassName) && !typeData.isNullType()) {
                return new ClassName(typeName(typeData.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + typeData);
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar
        public void merge(TypeData typeData) {
            try {
                if (typeData.isNullType()) {
                    return;
                }
                this.array.merge(ArrayType.make(typeData));
            } catch (BadBytecode e) {
                throw new RuntimeException("fatal: " + e);
            }
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public String getName() {
            return typeName(this.array.getName());
        }

        public AbsTypeVar arrayType() {
            return this.array;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String typeName(String str) {
            if (str.length() <= 1 || str.charAt(0) != '[') {
                return "java.lang.Object";
            }
            char charAt = str.charAt(1);
            if (charAt == 'L') {
                return str.substring(2, str.length() - 1).replace('/', '.');
            }
            return charAt == '[' ? str.substring(1) : "java.lang.Object";
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void setType(String str, ClassPool classPool) throws BadBytecode {
            this.array.setType(ArrayType.typeName(str), classPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        protected TypeVar toTypeVar(int i) {
            return this.array.toTypeVar(i - 1);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) throws NotFoundException {
            return this.array.getArrayType(i - 1);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public int dfs(List<TypeData> list, int i, ClassPool classPool) throws NotFoundException {
            return this.array.dfs(list, i, classPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return "*" + this.array.toString2(set);
        }
    }

    /* loaded from: classes2.dex */
    public static class UninitTypeVar extends AbsTypeVar {
        protected TypeData type;

        @Override // javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return "";
        }

        @Override // javassist.bytecode.stackmap.TypeData
        protected TypeVar toTypeVar(int i) {
            return null;
        }

        public UninitTypeVar(UninitData uninitData) {
            this.type = uninitData;
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar, javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return this.type.getTypeTag();
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar, javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return this.type.getTypeData(constPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public BasicType isBasicType() {
            return this.type.isBasicType();
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean is2WordType() {
            return this.type.is2WordType();
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean isUninit() {
            return this.type.isUninit();
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar, javassist.bytecode.stackmap.TypeData
        public boolean eq(TypeData typeData) {
            return this.type.eq(typeData);
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public String getName() {
            return this.type.getName();
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData join() {
            return this.type.join();
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void setType(String str, ClassPool classPool) throws BadBytecode {
            this.type.setType(str, classPool);
        }

        @Override // javassist.bytecode.stackmap.TypeData.AbsTypeVar
        public void merge(TypeData typeData) {
            if (typeData.eq(this.type)) {
                return;
            }
            this.type = TypeTag.TOP;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void constructorCalled(int i) {
            this.type.constructorCalled(i);
        }

        public int offset() {
            TypeData typeData = this.type;
            if (typeData instanceof UninitData) {
                return ((UninitData) typeData).offset;
            }
            throw new RuntimeException("not available");
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) throws NotFoundException {
            return this.type.getArrayType(i);
        }
    }

    /* loaded from: classes2.dex */
    public static class ClassName extends TypeData {
        private String name;

        @Override // javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return 7;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean is2WordType() {
            return false;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public BasicType isBasicType() {
            return null;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void setType(String str, ClassPool classPool) throws BadBytecode {
        }

        public ClassName(String str) {
            this.name = str;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public String getName() {
            return this.name;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return constPool.addClassInfo(getName());
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean eq(TypeData typeData) {
            return this.name.equals(typeData.getName());
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) throws NotFoundException {
            String str;
            if (i == 0) {
                return this;
            }
            int i2 = 0;
            if (i > 0) {
                char[] cArr = new char[i];
                for (int i3 = 0; i3 < i; i3++) {
                    cArr[i3] = '[';
                }
                String name = getName();
                if (name.charAt(0) != '[') {
                    name = "L" + name.replace('.', '/') + ";";
                }
                return new ClassName(new String(cArr) + name);
            }
            while (true) {
                int i4 = -i;
                if (i2 < i4) {
                    if (this.name.charAt(i2) != '[') {
                        throw new NotFoundException("no " + i + " dimensional array type: " + getName());
                    }
                    i2++;
                } else {
                    char charAt = this.name.charAt(i4);
                    if (charAt == '[') {
                        return new ClassName(this.name.substring(i4));
                    }
                    if (charAt != 'L') {
                        if (charAt != TypeTag.DOUBLE.decodedName) {
                            if (charAt != TypeTag.FLOAT.decodedName) {
                                if (charAt == TypeTag.LONG.decodedName) {
                                    return TypeTag.LONG;
                                }
                                return TypeTag.INTEGER;
                            }
                            return TypeTag.FLOAT;
                        }
                        return TypeTag.DOUBLE;
                    }
                    return new ClassName(this.name.substring(i4 + 1, str.length() - 1).replace('/', '.'));
                }
            }
        }

        @Override // javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return this.name;
        }
    }

    /* loaded from: classes2.dex */
    public static class NullType extends ClassName {
        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public TypeData getArrayType(int i) {
            return this;
        }

        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return 0;
        }

        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return 5;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean isNullType() {
            return true;
        }

        public NullType() {
            super("null-type");
        }
    }

    /* loaded from: classes2.dex */
    public static class UninitData extends ClassName {
        boolean initialized;
        int offset;

        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return 8;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public boolean isUninit() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public UninitData(int i, String str) {
            super(str);
            this.offset = i;
            this.initialized = false;
        }

        public UninitData copy() {
            return new UninitData(this.offset, getName());
        }

        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return this.offset;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public TypeData join() {
            if (this.initialized) {
                return new TypeVar(new ClassName(getName()));
            }
            return new UninitTypeVar(copy());
        }

        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public boolean eq(TypeData typeData) {
            if (typeData instanceof UninitData) {
                UninitData uninitData = (UninitData) typeData;
                return this.offset == uninitData.offset && getName().equals(uninitData.getName());
            }
            return false;
        }

        public int offset() {
            return this.offset;
        }

        @Override // javassist.bytecode.stackmap.TypeData
        public void constructorCalled(int i) {
            if (i == this.offset) {
                this.initialized = true;
            }
        }

        @Override // javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return getName() + RemotePrefUtils.SEPARATOR + this.offset;
        }
    }

    /* loaded from: classes2.dex */
    public static class UninitThis extends UninitData {
        @Override // javassist.bytecode.stackmap.TypeData.UninitData, javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public int getTypeData(ConstPool constPool) {
            return 0;
        }

        @Override // javassist.bytecode.stackmap.TypeData.UninitData, javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        public int getTypeTag() {
            return 6;
        }

        @Override // javassist.bytecode.stackmap.TypeData.UninitData, javassist.bytecode.stackmap.TypeData.ClassName, javassist.bytecode.stackmap.TypeData
        String toString2(Set<TypeData> set) {
            return "uninit:this";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public UninitThis(String str) {
            super(-1, str);
        }

        @Override // javassist.bytecode.stackmap.TypeData.UninitData
        public UninitData copy() {
            return new UninitThis(getName());
        }
    }
}
