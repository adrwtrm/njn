package javassist;

import androidx.core.view.InputDeviceCompat;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public final class CtMethod extends CtBehavior {
    protected String cachedStringRep;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtMethod(MethodInfo methodInfo, CtClass ctClass) {
        super(ctClass, methodInfo);
        this.cachedStringRep = null;
    }

    public CtMethod(CtClass ctClass, String str, CtClass[] ctClassArr, CtClass ctClass2) {
        this(null, ctClass2);
        this.methodInfo = new MethodInfo(ctClass2.getClassFile2().getConstPool(), str, Descriptor.ofMethod(ctClass, ctClassArr));
        setModifiers(InputDeviceCompat.SOURCE_GAMEPAD);
    }

    public CtMethod(CtMethod ctMethod, CtClass ctClass, ClassMap classMap) throws CannotCompileException {
        this(null, ctClass);
        copy(ctMethod, false, classMap);
    }

    public static CtMethod make(String str, CtClass ctClass) throws CannotCompileException {
        return CtNewMethod.make(str, ctClass);
    }

    public static CtMethod make(MethodInfo methodInfo, CtClass ctClass) throws CannotCompileException {
        if (ctClass.getClassFile2().getConstPool() != methodInfo.getConstPool()) {
            throw new CannotCompileException("bad declaring class");
        }
        return new CtMethod(methodInfo, ctClass);
    }

    public int hashCode() {
        return getStringRep().hashCode();
    }

    @Override // javassist.CtMember
    void nameReplaced() {
        this.cachedStringRep = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String getStringRep() {
        if (this.cachedStringRep == null) {
            this.cachedStringRep = this.methodInfo.getName() + Descriptor.getParamDescriptor(this.methodInfo.getDescriptor());
        }
        return this.cachedStringRep;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof CtMethod) && ((CtMethod) obj).getStringRep().equals(getStringRep());
    }

    @Override // javassist.CtBehavior
    public String getLongName() {
        return getDeclaringClass().getName() + "." + getName() + Descriptor.toString(getSignature());
    }

    @Override // javassist.CtMember
    public String getName() {
        return this.methodInfo.getName();
    }

    public void setName(String str) {
        this.declaringClass.checkModify();
        this.methodInfo.setName(str);
    }

    public CtClass getReturnType() throws NotFoundException {
        return getReturnType0();
    }

    @Override // javassist.CtBehavior
    public boolean isEmpty() {
        CodeAttribute codeAttribute = getMethodInfo2().getCodeAttribute();
        if (codeAttribute == null) {
            return (getModifiers() & 1024) != 0;
        }
        CodeIterator it = codeAttribute.iterator();
        try {
            if (it.hasNext() && it.byteAt(it.next()) == 177) {
                if (!it.hasNext()) {
                    return true;
                }
            }
            return false;
        } catch (BadBytecode unused) {
            return false;
        }
    }

    public void setBody(CtMethod ctMethod, ClassMap classMap) throws CannotCompileException {
        setBody0(ctMethod.declaringClass, ctMethod.methodInfo, this.declaringClass, this.methodInfo, classMap);
    }

    public void setWrappedBody(CtMethod ctMethod, ConstParameter constParameter) throws CannotCompileException {
        this.declaringClass.checkModify();
        CtClass declaringClass = getDeclaringClass();
        try {
            this.methodInfo.setCodeAttribute(CtNewWrappedMethod.makeBody(declaringClass, declaringClass.getClassFile2(), ctMethod, getParameterTypes(), getReturnType(), constParameter).toCodeAttribute());
            this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & (-1025));
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    /* loaded from: classes2.dex */
    public static class ConstParameter {
        /* JADX INFO: Access modifiers changed from: package-private */
        public static String defaultConstDescriptor() {
            return "([Ljava/lang/Object;)V";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static String defaultDescriptor() {
            return "([Ljava/lang/Object;)Ljava/lang/Object;";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int compile(Bytecode bytecode) throws CannotCompileException {
            return 0;
        }

        public static ConstParameter integer(int i) {
            return new IntConstParameter(i);
        }

        public static ConstParameter integer(long j) {
            return new LongConstParameter(j);
        }

        public static ConstParameter string(String str) {
            return new StringConstParameter(str);
        }

        ConstParameter() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String descriptor() {
            return defaultDescriptor();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String constDescriptor() {
            return defaultConstDescriptor();
        }
    }

    /* loaded from: classes2.dex */
    static class IntConstParameter extends ConstParameter {
        int param;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public String constDescriptor() {
            return "([Ljava/lang/Object;I)V";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public String descriptor() {
            return "([Ljava/lang/Object;I)Ljava/lang/Object;";
        }

        IntConstParameter(int i) {
            this.param = i;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public int compile(Bytecode bytecode) throws CannotCompileException {
            bytecode.addIconst(this.param);
            return 1;
        }
    }

    /* loaded from: classes2.dex */
    static class LongConstParameter extends ConstParameter {
        long param;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public String constDescriptor() {
            return "([Ljava/lang/Object;J)V";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public String descriptor() {
            return "([Ljava/lang/Object;J)Ljava/lang/Object;";
        }

        LongConstParameter(long j) {
            this.param = j;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public int compile(Bytecode bytecode) throws CannotCompileException {
            bytecode.addLconst(this.param);
            return 2;
        }
    }

    /* loaded from: classes2.dex */
    static class StringConstParameter extends ConstParameter {
        String param;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public String constDescriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)V";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public String descriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
        }

        StringConstParameter(String str) {
            this.param = str;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // javassist.CtMethod.ConstParameter
        public int compile(Bytecode bytecode) throws CannotCompileException {
            bytecode.addLdc(this.param);
            return 1;
        }
    }
}
