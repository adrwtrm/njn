package javassist;

import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.SignatureAttribute;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;
import javassist.compiler.SymbolTable;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.DoubleConst;
import javassist.compiler.ast.IntConst;
import javassist.compiler.ast.StringL;

/* loaded from: classes2.dex */
public class CtField extends CtMember {
    static final String javaLangString = "java.lang.String";
    protected FieldInfo fieldInfo;

    protected ASTree getInitAST() {
        return null;
    }

    public CtField(CtClass ctClass, String str, CtClass ctClass2) throws CannotCompileException {
        this(Descriptor.of(ctClass), str, ctClass2);
    }

    public CtField(CtField ctField, CtClass ctClass) throws CannotCompileException {
        this(ctField.fieldInfo.getDescriptor(), ctField.fieldInfo.getName(), ctClass);
        FieldInfo fieldInfo = this.fieldInfo;
        fieldInfo.setAccessFlags(ctField.fieldInfo.getAccessFlags());
        ConstPool constPool = fieldInfo.getConstPool();
        for (AttributeInfo attributeInfo : ctField.fieldInfo.getAttributes()) {
            fieldInfo.addAttribute(attributeInfo.copy(constPool, null));
        }
    }

    private CtField(String str, String str2, CtClass ctClass) throws CannotCompileException {
        super(ctClass);
        ClassFile classFile2 = ctClass.getClassFile2();
        if (classFile2 == null) {
            throw new CannotCompileException("bad declaring class: " + ctClass.getName());
        }
        this.fieldInfo = new FieldInfo(classFile2.getConstPool(), str2, str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtField(FieldInfo fieldInfo, CtClass ctClass) {
        super(ctClass);
        this.fieldInfo = fieldInfo;
    }

    @Override // javassist.CtMember
    public String toString() {
        return getDeclaringClass().getName() + "." + getName() + ":" + this.fieldInfo.getDescriptor();
    }

    @Override // javassist.CtMember
    protected void extendToString(StringBuffer stringBuffer) {
        stringBuffer.append(' ');
        stringBuffer.append(getName());
        stringBuffer.append(' ');
        stringBuffer.append(this.fieldInfo.getDescriptor());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Initializer getInit() {
        ASTree initAST = getInitAST();
        if (initAST == null) {
            return null;
        }
        return Initializer.byExpr(initAST);
    }

    public static CtField make(String str, CtClass ctClass) throws CannotCompileException {
        try {
            CtMember compile = new Javac(ctClass).compile(str);
            if (compile instanceof CtField) {
                return (CtField) compile;
            }
            throw new CannotCompileException("not a field");
        } catch (CompileError e) {
            throw new CannotCompileException(e);
        }
    }

    public FieldInfo getFieldInfo() {
        this.declaringClass.checkModify();
        return this.fieldInfo;
    }

    public FieldInfo getFieldInfo2() {
        return this.fieldInfo;
    }

    @Override // javassist.CtMember
    public CtClass getDeclaringClass() {
        return super.getDeclaringClass();
    }

    @Override // javassist.CtMember
    public String getName() {
        return this.fieldInfo.getName();
    }

    public void setName(String str) {
        this.declaringClass.checkModify();
        this.fieldInfo.setName(str);
    }

    @Override // javassist.CtMember
    public int getModifiers() {
        return AccessFlag.toModifier(this.fieldInfo.getAccessFlags());
    }

    @Override // javassist.CtMember
    public void setModifiers(int i) {
        this.declaringClass.checkModify();
        this.fieldInfo.setAccessFlags(AccessFlag.of(i));
    }

    @Override // javassist.CtMember
    public boolean hasAnnotation(String str) {
        FieldInfo fieldInfo2 = getFieldInfo2();
        return CtClassType.hasAnnotationType(str, getDeclaringClass().getClassPool(), (AnnotationsAttribute) fieldInfo2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) fieldInfo2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    @Override // javassist.CtMember
    public Object getAnnotation(Class<?> cls) throws ClassNotFoundException {
        FieldInfo fieldInfo2 = getFieldInfo2();
        return CtClassType.getAnnotationType(cls, getDeclaringClass().getClassPool(), (AnnotationsAttribute) fieldInfo2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) fieldInfo2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    @Override // javassist.CtMember
    public Object[] getAnnotations() throws ClassNotFoundException {
        return getAnnotations(false);
    }

    @Override // javassist.CtMember
    public Object[] getAvailableAnnotations() {
        try {
            return getAnnotations(true);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private Object[] getAnnotations(boolean z) throws ClassNotFoundException {
        FieldInfo fieldInfo2 = getFieldInfo2();
        return CtClassType.toAnnotationType(z, getDeclaringClass().getClassPool(), (AnnotationsAttribute) fieldInfo2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) fieldInfo2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    @Override // javassist.CtMember
    public String getSignature() {
        return this.fieldInfo.getDescriptor();
    }

    @Override // javassist.CtMember
    public String getGenericSignature() {
        SignatureAttribute signatureAttribute = (SignatureAttribute) this.fieldInfo.getAttribute(SignatureAttribute.tag);
        if (signatureAttribute == null) {
            return null;
        }
        return signatureAttribute.getSignature();
    }

    @Override // javassist.CtMember
    public void setGenericSignature(String str) {
        this.declaringClass.checkModify();
        this.fieldInfo.addAttribute(new SignatureAttribute(this.fieldInfo.getConstPool(), str));
    }

    public CtClass getType() throws NotFoundException {
        return Descriptor.toCtClass(this.fieldInfo.getDescriptor(), this.declaringClass.getClassPool());
    }

    public void setType(CtClass ctClass) {
        this.declaringClass.checkModify();
        this.fieldInfo.setDescriptor(Descriptor.of(ctClass));
    }

    public Object getConstantValue() {
        int constantValue = this.fieldInfo.getConstantValue();
        if (constantValue == 0) {
            return null;
        }
        ConstPool constPool = this.fieldInfo.getConstPool();
        int tag = constPool.getTag(constantValue);
        if (tag == 3) {
            int integerInfo = constPool.getIntegerInfo(constantValue);
            if ("Z".equals(this.fieldInfo.getDescriptor())) {
                return Boolean.valueOf(integerInfo != 0);
            }
            return Integer.valueOf(integerInfo);
        } else if (tag != 4) {
            if (tag != 5) {
                if (tag != 6) {
                    if (tag == 8) {
                        return constPool.getStringInfo(constantValue);
                    }
                    throw new RuntimeException("bad tag: " + constPool.getTag(constantValue) + " at " + constantValue);
                }
                return Double.valueOf(constPool.getDoubleInfo(constantValue));
            }
            return Long.valueOf(constPool.getLongInfo(constantValue));
        } else {
            return Float.valueOf(constPool.getFloatInfo(constantValue));
        }
    }

    @Override // javassist.CtMember
    public byte[] getAttribute(String str) {
        AttributeInfo attribute = this.fieldInfo.getAttribute(str);
        if (attribute == null) {
            return null;
        }
        return attribute.get();
    }

    @Override // javassist.CtMember
    public void setAttribute(String str, byte[] bArr) {
        this.declaringClass.checkModify();
        this.fieldInfo.addAttribute(new AttributeInfo(this.fieldInfo.getConstPool(), str, bArr));
    }

    /* loaded from: classes2.dex */
    public static abstract class Initializer {
        /* JADX INFO: Access modifiers changed from: package-private */
        public void check(String str) throws CannotCompileException {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException;

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException;

        /* JADX INFO: Access modifiers changed from: package-private */
        public int getConstantValue(ConstPool constPool, CtClass ctClass) {
            return 0;
        }

        public static Initializer constant(int i) {
            return new IntInitializer(i);
        }

        public static Initializer constant(boolean z) {
            return new IntInitializer(z ? 1 : 0);
        }

        public static Initializer constant(long j) {
            return new LongInitializer(j);
        }

        public static Initializer constant(float f) {
            return new FloatInitializer(f);
        }

        public static Initializer constant(double d) {
            return new DoubleInitializer(d);
        }

        public static Initializer constant(String str) {
            return new StringInitializer(str);
        }

        public static Initializer byParameter(int i) {
            ParamInitializer paramInitializer = new ParamInitializer();
            paramInitializer.nthParam = i;
            return paramInitializer;
        }

        public static Initializer byNew(CtClass ctClass) {
            NewInitializer newInitializer = new NewInitializer();
            newInitializer.objectType = ctClass;
            newInitializer.stringParams = null;
            newInitializer.withConstructorParams = false;
            return newInitializer;
        }

        public static Initializer byNew(CtClass ctClass, String[] strArr) {
            NewInitializer newInitializer = new NewInitializer();
            newInitializer.objectType = ctClass;
            newInitializer.stringParams = strArr;
            newInitializer.withConstructorParams = false;
            return newInitializer;
        }

        public static Initializer byNewWithParams(CtClass ctClass) {
            NewInitializer newInitializer = new NewInitializer();
            newInitializer.objectType = ctClass;
            newInitializer.stringParams = null;
            newInitializer.withConstructorParams = true;
            return newInitializer;
        }

        public static Initializer byNewWithParams(CtClass ctClass, String[] strArr) {
            NewInitializer newInitializer = new NewInitializer();
            newInitializer.objectType = ctClass;
            newInitializer.stringParams = strArr;
            newInitializer.withConstructorParams = true;
            return newInitializer;
        }

        public static Initializer byCall(CtClass ctClass, String str) {
            MethodInitializer methodInitializer = new MethodInitializer();
            methodInitializer.objectType = ctClass;
            methodInitializer.methodName = str;
            methodInitializer.stringParams = null;
            methodInitializer.withConstructorParams = false;
            return methodInitializer;
        }

        public static Initializer byCall(CtClass ctClass, String str, String[] strArr) {
            MethodInitializer methodInitializer = new MethodInitializer();
            methodInitializer.objectType = ctClass;
            methodInitializer.methodName = str;
            methodInitializer.stringParams = strArr;
            methodInitializer.withConstructorParams = false;
            return methodInitializer;
        }

        public static Initializer byCallWithParams(CtClass ctClass, String str) {
            MethodInitializer methodInitializer = new MethodInitializer();
            methodInitializer.objectType = ctClass;
            methodInitializer.methodName = str;
            methodInitializer.stringParams = null;
            methodInitializer.withConstructorParams = true;
            return methodInitializer;
        }

        public static Initializer byCallWithParams(CtClass ctClass, String str, String[] strArr) {
            MethodInitializer methodInitializer = new MethodInitializer();
            methodInitializer.objectType = ctClass;
            methodInitializer.methodName = str;
            methodInitializer.stringParams = strArr;
            methodInitializer.withConstructorParams = true;
            return methodInitializer;
        }

        public static Initializer byNewArray(CtClass ctClass, int i) throws NotFoundException {
            return new ArrayInitializer(ctClass.getComponentType(), i);
        }

        public static Initializer byNewArray(CtClass ctClass, int[] iArr) {
            return new MultiArrayInitializer(ctClass, iArr);
        }

        public static Initializer byExpr(String str) {
            return new CodeInitializer(str);
        }

        static Initializer byExpr(ASTree aSTree) {
            return new PtreeInitializer(aSTree);
        }
    }

    /* loaded from: classes2.dex */
    static abstract class CodeInitializer0 extends Initializer {
        abstract void compileExpr(Javac javac) throws CompileError;

        CodeInitializer0() {
        }

        @Override // javassist.CtField.Initializer
        int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            try {
                bytecode.addAload(0);
                compileExpr(javac);
                bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
                return bytecode.getMaxStack();
            } catch (CompileError e) {
                throw new CannotCompileException(e);
            }
        }

        @Override // javassist.CtField.Initializer
        int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            try {
                compileExpr(javac);
                bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
                return bytecode.getMaxStack();
            } catch (CompileError e) {
                throw new CannotCompileException(e);
            }
        }

        int getConstantValue2(ConstPool constPool, CtClass ctClass, ASTree aSTree) {
            if (ctClass.isPrimitive()) {
                if (aSTree instanceof IntConst) {
                    long j = ((IntConst) aSTree).get();
                    if (ctClass == CtClass.doubleType) {
                        return constPool.addDoubleInfo(j);
                    }
                    if (ctClass == CtClass.floatType) {
                        return constPool.addFloatInfo((float) j);
                    }
                    if (ctClass == CtClass.longType) {
                        return constPool.addLongInfo(j);
                    }
                    if (ctClass != CtClass.voidType) {
                        return constPool.addIntegerInfo((int) j);
                    }
                    return 0;
                } else if (aSTree instanceof DoubleConst) {
                    double d = ((DoubleConst) aSTree).get();
                    if (ctClass == CtClass.floatType) {
                        return constPool.addFloatInfo((float) d);
                    }
                    if (ctClass == CtClass.doubleType) {
                        return constPool.addDoubleInfo(d);
                    }
                    return 0;
                } else {
                    return 0;
                }
            } else if ((aSTree instanceof StringL) && ctClass.getName().equals(CtField.javaLangString)) {
                return constPool.addStringInfo(((StringL) aSTree).get());
            } else {
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class CodeInitializer extends CodeInitializer0 {
        private String expression;

        CodeInitializer(String str) {
            this.expression = str;
        }

        @Override // javassist.CtField.CodeInitializer0
        void compileExpr(Javac javac) throws CompileError {
            javac.compileExpr(this.expression);
        }

        @Override // javassist.CtField.Initializer
        int getConstantValue(ConstPool constPool, CtClass ctClass) {
            try {
                return getConstantValue2(constPool, ctClass, Javac.parseExpr(this.expression, new SymbolTable()));
            } catch (CompileError unused) {
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class PtreeInitializer extends CodeInitializer0 {
        private ASTree expression;

        PtreeInitializer(ASTree aSTree) {
            this.expression = aSTree;
        }

        @Override // javassist.CtField.CodeInitializer0
        void compileExpr(Javac javac) throws CompileError {
            javac.compileExpr(this.expression);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int getConstantValue(ConstPool constPool, CtClass ctClass) {
            return getConstantValue2(constPool, ctClass, this.expression);
        }
    }

    /* loaded from: classes2.dex */
    static class ParamInitializer extends Initializer {
        int nthParam;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            return 0;
        }

        ParamInitializer() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            if (ctClassArr == null || this.nthParam >= ctClassArr.length) {
                return 0;
            }
            bytecode.addAload(0);
            int addLoad = bytecode.addLoad(nthParamToLocal(this.nthParam, ctClassArr, false), ctClass) + 1;
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return addLoad;
        }

        static int nthParamToLocal(int i, CtClass[] ctClassArr, boolean z) {
            CtClass ctClass = CtClass.longType;
            CtClass ctClass2 = CtClass.doubleType;
            int i2 = !z ? 1 : 0;
            for (int i3 = 0; i3 < i; i3++) {
                CtClass ctClass3 = ctClassArr[i3];
                i2 = (ctClass3 == ctClass || ctClass3 == ctClass2) ? i2 + 2 : i2 + 1;
            }
            return i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class NewInitializer extends Initializer {
        CtClass objectType;
        String[] stringParams;
        boolean withConstructorParams;

        NewInitializer() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addNew(this.objectType);
            bytecode.add(89);
            bytecode.addAload(0);
            int compileStringParameter = this.stringParams != null ? 4 + compileStringParameter(bytecode) : 4;
            if (this.withConstructorParams) {
                compileStringParameter += CtNewWrappedMethod.compileParameterList(bytecode, ctClassArr, 1);
            }
            bytecode.addInvokespecial(this.objectType, "<init>", getDescriptor());
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return compileStringParameter;
        }

        private String getDescriptor() {
            return this.stringParams == null ? this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/Object;)V" : "(Ljava/lang/Object;)V" : this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V" : "(Ljava/lang/Object;[Ljava/lang/String;)V";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            String str2;
            bytecode.addNew(this.objectType);
            bytecode.add(89);
            int i = 2;
            if (this.stringParams == null) {
                str2 = "()V";
            } else {
                i = 2 + compileStringParameter(bytecode);
                str2 = "([Ljava/lang/String;)V";
            }
            bytecode.addInvokespecial(this.objectType, "<init>", str2);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return i;
        }

        protected final int compileStringParameter(Bytecode bytecode) throws CannotCompileException {
            int length = this.stringParams.length;
            bytecode.addIconst(length);
            bytecode.addAnewarray(CtField.javaLangString);
            for (int i = 0; i < length; i++) {
                bytecode.add(89);
                bytecode.addIconst(i);
                bytecode.addLdc(this.stringParams[i]);
                bytecode.add(83);
            }
            return 4;
        }
    }

    /* loaded from: classes2.dex */
    static class MethodInitializer extends NewInitializer {
        String methodName;

        MethodInitializer() {
        }

        @Override // javassist.CtField.NewInitializer, javassist.CtField.Initializer
        int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addAload(0);
            int compileStringParameter = this.stringParams != null ? 2 + compileStringParameter(bytecode) : 2;
            if (this.withConstructorParams) {
                compileStringParameter += CtNewWrappedMethod.compileParameterList(bytecode, ctClassArr, 1);
            }
            String of = Descriptor.of(ctClass);
            bytecode.addInvokestatic(this.objectType, this.methodName, getDescriptor() + of);
            bytecode.addPutfield(Bytecode.THIS, str, of);
            return compileStringParameter;
        }

        private String getDescriptor() {
            return this.stringParams == null ? this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/Object;)" : "(Ljava/lang/Object;)" : this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)" : "(Ljava/lang/Object;[Ljava/lang/String;)";
        }

        @Override // javassist.CtField.NewInitializer, javassist.CtField.Initializer
        int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            String str2;
            int i = 1;
            if (this.stringParams == null) {
                str2 = "()";
            } else {
                i = 1 + compileStringParameter(bytecode);
                str2 = "([Ljava/lang/String;)";
            }
            String of = Descriptor.of(ctClass);
            bytecode.addInvokestatic(this.objectType, this.methodName, str2 + of);
            bytecode.addPutstatic(Bytecode.THIS, str, of);
            return i;
        }
    }

    /* loaded from: classes2.dex */
    static class IntInitializer extends Initializer {
        int value;

        IntInitializer(int i) {
            this.value = i;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public void check(String str) throws CannotCompileException {
            char charAt = str.charAt(0);
            if (charAt != 'I' && charAt != 'S' && charAt != 'B' && charAt != 'C' && charAt != 'Z') {
                throw new CannotCompileException("type mismatch");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addIconst(this.value);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            bytecode.addIconst(this.value);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 1;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int getConstantValue(ConstPool constPool, CtClass ctClass) {
            return constPool.addIntegerInfo(this.value);
        }
    }

    /* loaded from: classes2.dex */
    static class LongInitializer extends Initializer {
        long value;

        LongInitializer(long j) {
            this.value = j;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public void check(String str) throws CannotCompileException {
            if (!str.equals("J")) {
                throw new CannotCompileException("type mismatch");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addLdc2w(this.value);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            bytecode.addLdc2w(this.value);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int getConstantValue(ConstPool constPool, CtClass ctClass) {
            if (ctClass == CtClass.longType) {
                return constPool.addLongInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: classes2.dex */
    static class FloatInitializer extends Initializer {
        float value;

        FloatInitializer(float f) {
            this.value = f;
        }

        @Override // javassist.CtField.Initializer
        void check(String str) throws CannotCompileException {
            if (!str.equals("F")) {
                throw new CannotCompileException("type mismatch");
            }
        }

        @Override // javassist.CtField.Initializer
        int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addFconst(this.value);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 3;
        }

        @Override // javassist.CtField.Initializer
        int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            bytecode.addFconst(this.value);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 2;
        }

        @Override // javassist.CtField.Initializer
        int getConstantValue(ConstPool constPool, CtClass ctClass) {
            if (ctClass == CtClass.floatType) {
                return constPool.addFloatInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: classes2.dex */
    static class DoubleInitializer extends Initializer {
        double value;

        DoubleInitializer(double d) {
            this.value = d;
        }

        @Override // javassist.CtField.Initializer
        void check(String str) throws CannotCompileException {
            if (!str.equals("D")) {
                throw new CannotCompileException("type mismatch");
            }
        }

        @Override // javassist.CtField.Initializer
        int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addLdc2w(this.value);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 3;
        }

        @Override // javassist.CtField.Initializer
        int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            bytecode.addLdc2w(this.value);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 2;
        }

        @Override // javassist.CtField.Initializer
        int getConstantValue(ConstPool constPool, CtClass ctClass) {
            if (ctClass == CtClass.doubleType) {
                return constPool.addDoubleInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: classes2.dex */
    static class StringInitializer extends Initializer {
        String value;

        StringInitializer(String str) {
            this.value = str;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            bytecode.addLdc(this.value);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            bytecode.addLdc(this.value);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 1;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int getConstantValue(ConstPool constPool, CtClass ctClass) {
            if (ctClass.getName().equals(CtField.javaLangString)) {
                return constPool.addStringInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: classes2.dex */
    static class ArrayInitializer extends Initializer {
        int size;
        CtClass type;

        ArrayInitializer(CtClass ctClass, int i) {
            this.type = ctClass;
            this.size = i;
        }

        private void addNewarray(Bytecode bytecode) {
            if (this.type.isPrimitive()) {
                bytecode.addNewarray(((CtPrimitiveType) this.type).getArrayType(), this.size);
            } else {
                bytecode.addAnewarray(this.type, this.size);
            }
        }

        @Override // javassist.CtField.Initializer
        int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            addNewarray(bytecode);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 2;
        }

        @Override // javassist.CtField.Initializer
        int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            addNewarray(bytecode);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return 1;
        }
    }

    /* loaded from: classes2.dex */
    static class MultiArrayInitializer extends Initializer {
        int[] dim;
        CtClass type;

        MultiArrayInitializer(CtClass ctClass, int[] iArr) {
            this.type = ctClass;
            this.dim = iArr;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public void check(String str) throws CannotCompileException {
            if (str.charAt(0) != '[') {
                throw new CannotCompileException("type mismatch");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException {
            bytecode.addAload(0);
            int addMultiNewarray = bytecode.addMultiNewarray(ctClass, this.dim);
            bytecode.addPutfield(Bytecode.THIS, str, Descriptor.of(ctClass));
            return addMultiNewarray + 1;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtField.Initializer
        public int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException {
            int addMultiNewarray = bytecode.addMultiNewarray(ctClass, this.dim);
            bytecode.addPutstatic(Bytecode.THIS, str, Descriptor.of(ctClass));
            return addMultiNewarray;
        }
    }
}
