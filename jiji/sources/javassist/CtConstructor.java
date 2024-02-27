package javassist;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;

/* loaded from: classes2.dex */
public final class CtConstructor extends CtBehavior {
    /* JADX INFO: Access modifiers changed from: protected */
    public CtConstructor(MethodInfo methodInfo, CtClass ctClass) {
        super(ctClass, methodInfo);
    }

    public CtConstructor(CtClass[] ctClassArr, CtClass ctClass) {
        this((MethodInfo) null, ctClass);
        this.methodInfo = new MethodInfo(ctClass.getClassFile2().getConstPool(), "<init>", Descriptor.ofConstructor(ctClassArr));
        setModifiers(1);
    }

    public CtConstructor(CtConstructor ctConstructor, CtClass ctClass, ClassMap classMap) throws CannotCompileException {
        this((MethodInfo) null, ctClass);
        copy(ctConstructor, true, classMap);
    }

    public boolean isConstructor() {
        return this.methodInfo.isConstructor();
    }

    public boolean isClassInitializer() {
        return this.methodInfo.isStaticInitializer();
    }

    @Override // javassist.CtBehavior
    public String getLongName() {
        return getDeclaringClass().getName() + (isConstructor() ? Descriptor.toString(getSignature()) : ".<clinit>()");
    }

    @Override // javassist.CtMember
    public String getName() {
        return this.methodInfo.isStaticInitializer() ? MethodInfo.nameClinit : this.declaringClass.getSimpleName();
    }

    @Override // javassist.CtBehavior
    public boolean isEmpty() {
        int isConstructor;
        CodeAttribute codeAttribute = getMethodInfo2().getCodeAttribute();
        if (codeAttribute == null) {
            return false;
        }
        ConstPool constPool = codeAttribute.getConstPool();
        CodeIterator it = codeAttribute.iterator();
        try {
            int byteAt = it.byteAt(it.next());
            if (byteAt != 177) {
                if (byteAt != 42) {
                    return false;
                }
                int next = it.next();
                if (it.byteAt(next) != 183 || (isConstructor = constPool.isConstructor(getSuperclassName(), it.u16bitAt(next + 1))) == 0 || !"()V".equals(constPool.getUtf8Info(isConstructor)) || it.byteAt(it.next()) != 177) {
                    return false;
                }
                if (it.hasNext()) {
                    return false;
                }
            }
            return true;
        } catch (BadBytecode unused) {
            return false;
        }
    }

    private String getSuperclassName() {
        return this.declaringClass.getClassFile2().getSuperclass();
    }

    public boolean callsSuper() throws CannotCompileException {
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            try {
                return codeAttribute.iterator().skipSuperConstructor() >= 0;
            } catch (BadBytecode e) {
                throw new CannotCompileException(e);
            }
        }
        return false;
    }

    @Override // javassist.CtBehavior
    public void setBody(String str) throws CannotCompileException {
        if (str == null) {
            str = isClassInitializer() ? ";" : "super();";
        }
        super.setBody(str);
    }

    public void setBody(CtConstructor ctConstructor, ClassMap classMap) throws CannotCompileException {
        setBody0(ctConstructor.declaringClass, ctConstructor.methodInfo, this.declaringClass, this.methodInfo, classMap);
    }

    public void insertBeforeBody(String str) throws CannotCompileException {
        CtClass ctClass = this.declaringClass;
        ctClass.checkModify();
        if (isClassInitializer()) {
            throw new CannotCompileException("class initializer");
        }
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        CodeIterator it = codeAttribute.iterator();
        Bytecode bytecode = new Bytecode(this.methodInfo.getConstPool(), codeAttribute.getMaxStack(), codeAttribute.getMaxLocals());
        bytecode.setStackDepth(codeAttribute.getMaxStack());
        Javac javac = new Javac(bytecode, ctClass);
        try {
            javac.recordParams(getParameterTypes(), false);
            javac.compileStmnt(str);
            codeAttribute.setMaxStack(bytecode.getMaxStack());
            codeAttribute.setMaxLocals(bytecode.getMaxLocals());
            it.skipConstructor();
            it.insert(bytecode.getExceptionTable(), it.insertEx(bytecode.get()));
            this.methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2);
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    @Override // javassist.CtBehavior
    int getStartPosOfBody(CodeAttribute codeAttribute) throws CannotCompileException {
        CodeIterator it = codeAttribute.iterator();
        try {
            it.skipConstructor();
            return it.next();
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    public CtMethod toMethod(String str, CtClass ctClass) throws CannotCompileException {
        return toMethod(str, ctClass, null);
    }

    public CtMethod toMethod(String str, CtClass ctClass, ClassMap classMap) throws CannotCompileException {
        CodeAttribute codeAttribute;
        CtMethod ctMethod = new CtMethod(null, ctClass);
        ctMethod.copy(this, false, classMap);
        if (isConstructor() && (codeAttribute = ctMethod.getMethodInfo2().getCodeAttribute()) != null) {
            removeConsCall(codeAttribute);
            try {
                this.methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
            } catch (BadBytecode e) {
                throw new CannotCompileException(e);
            }
        }
        ctMethod.setName(str);
        return ctMethod;
    }

    private static void removeConsCall(CodeAttribute codeAttribute) throws CannotCompileException {
        CodeIterator it = codeAttribute.iterator();
        try {
            int skipConstructor = it.skipConstructor();
            if (skipConstructor < 0) {
                return;
            }
            String methodrefType = codeAttribute.getConstPool().getMethodrefType(it.u16bitAt(skipConstructor + 1));
            int numOfParameters = Descriptor.numOfParameters(methodrefType) + 1;
            if (numOfParameters > 3) {
                skipConstructor = it.insertGapAt(skipConstructor, numOfParameters - 3, false).position;
            }
            int i = skipConstructor + 1;
            it.writeByte(87, skipConstructor);
            it.writeByte(0, i);
            it.writeByte(0, i + 1);
            Descriptor.Iterator iterator = new Descriptor.Iterator(methodrefType);
            while (true) {
                iterator.next();
                if (!iterator.isParameter()) {
                    return;
                }
                int i2 = i + 1;
                it.writeByte(iterator.is2byte() ? 88 : 87, i);
                i = i2;
            }
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }
}
