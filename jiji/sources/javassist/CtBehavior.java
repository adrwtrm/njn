package javassist;

import javassist.CtField;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.LineNumberAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.LocalVariableTypeAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.StackMap;
import javassist.bytecode.StackMapTable;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;
import javassist.expr.ExprEditor;

/* loaded from: classes2.dex */
public abstract class CtBehavior extends CtMember {
    protected MethodInfo methodInfo;

    public abstract String getLongName();

    int getStartPosOfBody(CodeAttribute codeAttribute) throws CannotCompileException {
        return 0;
    }

    public abstract boolean isEmpty();

    /* JADX INFO: Access modifiers changed from: protected */
    public CtBehavior(CtClass ctClass, MethodInfo methodInfo) {
        super(ctClass);
        this.methodInfo = methodInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void copy(CtBehavior ctBehavior, boolean z, ClassMap classMap) throws CannotCompileException {
        String str;
        CtClass ctClass = this.declaringClass;
        MethodInfo methodInfo = ctBehavior.methodInfo;
        CtClass declaringClass = ctBehavior.getDeclaringClass();
        ConstPool constPool = ctClass.getClassFile2().getConstPool();
        ClassMap classMap2 = new ClassMap(classMap);
        classMap2.put(declaringClass.getName(), ctClass.getName());
        try {
            CtClass superclass = declaringClass.getSuperclass();
            CtClass superclass2 = ctClass.getSuperclass();
            boolean z2 = false;
            if (superclass == null || superclass2 == null) {
                str = null;
            } else {
                String name = superclass.getName();
                str = superclass2.getName();
                if (!name.equals(str)) {
                    if (name.equals("java.lang.Object")) {
                        z2 = true;
                    } else {
                        classMap2.putIfNone(name, str);
                    }
                }
            }
            MethodInfo methodInfo2 = new MethodInfo(constPool, methodInfo.getName(), methodInfo, classMap2);
            this.methodInfo = methodInfo2;
            if (z && z2) {
                methodInfo2.setSuperclass(str);
            }
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2);
        }
    }

    @Override // javassist.CtMember
    protected void extendToString(StringBuffer stringBuffer) {
        stringBuffer.append(' ');
        stringBuffer.append(getName());
        stringBuffer.append(' ');
        stringBuffer.append(this.methodInfo.getDescriptor());
    }

    public MethodInfo getMethodInfo() {
        this.declaringClass.checkModify();
        return this.methodInfo;
    }

    public MethodInfo getMethodInfo2() {
        return this.methodInfo;
    }

    @Override // javassist.CtMember
    public int getModifiers() {
        return AccessFlag.toModifier(this.methodInfo.getAccessFlags());
    }

    @Override // javassist.CtMember
    public void setModifiers(int i) {
        this.declaringClass.checkModify();
        this.methodInfo.setAccessFlags(AccessFlag.of(i));
    }

    @Override // javassist.CtMember
    public boolean hasAnnotation(String str) {
        MethodInfo methodInfo2 = getMethodInfo2();
        return CtClassType.hasAnnotationType(str, getDeclaringClass().getClassPool(), (AnnotationsAttribute) methodInfo2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) methodInfo2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    @Override // javassist.CtMember
    public Object getAnnotation(Class<?> cls) throws ClassNotFoundException {
        MethodInfo methodInfo2 = getMethodInfo2();
        return CtClassType.getAnnotationType(cls, getDeclaringClass().getClassPool(), (AnnotationsAttribute) methodInfo2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) methodInfo2.getAttribute(AnnotationsAttribute.visibleTag));
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
        MethodInfo methodInfo2 = getMethodInfo2();
        return CtClassType.toAnnotationType(z, getDeclaringClass().getClassPool(), (AnnotationsAttribute) methodInfo2.getAttribute(AnnotationsAttribute.invisibleTag), (AnnotationsAttribute) methodInfo2.getAttribute(AnnotationsAttribute.visibleTag));
    }

    public Object[][] getParameterAnnotations() throws ClassNotFoundException {
        return getParameterAnnotations(false);
    }

    public Object[][] getAvailableParameterAnnotations() {
        try {
            return getParameterAnnotations(true);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    Object[][] getParameterAnnotations(boolean z) throws ClassNotFoundException {
        MethodInfo methodInfo2 = getMethodInfo2();
        return CtClassType.toAnnotationType(z, getDeclaringClass().getClassPool(), (ParameterAnnotationsAttribute) methodInfo2.getAttribute(ParameterAnnotationsAttribute.invisibleTag), (ParameterAnnotationsAttribute) methodInfo2.getAttribute(ParameterAnnotationsAttribute.visibleTag), methodInfo2);
    }

    public CtClass[] getParameterTypes() throws NotFoundException {
        return Descriptor.getParameterTypes(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtClass getReturnType0() throws NotFoundException {
        return Descriptor.getReturnType(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
    }

    @Override // javassist.CtMember
    public String getSignature() {
        return this.methodInfo.getDescriptor();
    }

    @Override // javassist.CtMember
    public String getGenericSignature() {
        SignatureAttribute signatureAttribute = (SignatureAttribute) this.methodInfo.getAttribute(SignatureAttribute.tag);
        if (signatureAttribute == null) {
            return null;
        }
        return signatureAttribute.getSignature();
    }

    @Override // javassist.CtMember
    public void setGenericSignature(String str) {
        this.declaringClass.checkModify();
        this.methodInfo.addAttribute(new SignatureAttribute(this.methodInfo.getConstPool(), str));
    }

    public CtClass[] getExceptionTypes() throws NotFoundException {
        ExceptionsAttribute exceptionsAttribute = this.methodInfo.getExceptionsAttribute();
        return this.declaringClass.getClassPool().get(exceptionsAttribute == null ? null : exceptionsAttribute.getExceptions());
    }

    public void setExceptionTypes(CtClass[] ctClassArr) throws NotFoundException {
        this.declaringClass.checkModify();
        if (ctClassArr == null || ctClassArr.length == 0) {
            this.methodInfo.removeExceptionsAttribute();
            return;
        }
        String[] strArr = new String[ctClassArr.length];
        for (int i = 0; i < ctClassArr.length; i++) {
            strArr[i] = ctClassArr[i].getName();
        }
        ExceptionsAttribute exceptionsAttribute = this.methodInfo.getExceptionsAttribute();
        if (exceptionsAttribute == null) {
            exceptionsAttribute = new ExceptionsAttribute(this.methodInfo.getConstPool());
            this.methodInfo.setExceptionsAttribute(exceptionsAttribute);
        }
        exceptionsAttribute.setExceptions(strArr);
    }

    public void setBody(String str) throws CannotCompileException {
        setBody(str, null, null);
    }

    public void setBody(String str, String str2, String str3) throws CannotCompileException {
        CtClass ctClass = this.declaringClass;
        ctClass.checkModify();
        try {
            Javac javac = new Javac(ctClass);
            if (str3 != null) {
                javac.recordProceed(str2, str3);
            }
            this.methodInfo.setCodeAttribute(javac.compileBody(this, str).toCodeAttribute());
            MethodInfo methodInfo = this.methodInfo;
            methodInfo.setAccessFlags(methodInfo.getAccessFlags() & (-1025));
            this.methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
            this.declaringClass.rebuildClassFile();
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        } catch (CompileError e2) {
            throw new CannotCompileException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setBody0(CtClass ctClass, MethodInfo methodInfo, CtClass ctClass2, MethodInfo methodInfo2, ClassMap classMap) throws CannotCompileException {
        ctClass2.checkModify();
        ClassMap classMap2 = new ClassMap(classMap);
        classMap2.put(ctClass.getName(), ctClass2.getName());
        try {
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            if (codeAttribute != null) {
                methodInfo2.setCodeAttribute((CodeAttribute) codeAttribute.copy(methodInfo2.getConstPool(), classMap2));
            }
            methodInfo2.setAccessFlags(methodInfo2.getAccessFlags() & (-1025));
            ctClass2.rebuildClassFile();
        } catch (CodeAttribute.RuntimeCopyException e) {
            throw new CannotCompileException(e);
        }
    }

    @Override // javassist.CtMember
    public byte[] getAttribute(String str) {
        AttributeInfo attribute = this.methodInfo.getAttribute(str);
        if (attribute == null) {
            return null;
        }
        return attribute.get();
    }

    @Override // javassist.CtMember
    public void setAttribute(String str, byte[] bArr) {
        this.declaringClass.checkModify();
        this.methodInfo.addAttribute(new AttributeInfo(this.methodInfo.getConstPool(), str, bArr));
    }

    public void useCflow(String str) throws CannotCompileException {
        CtClass ctClass = this.declaringClass;
        ctClass.checkModify();
        ClassPool classPool = ctClass.getClassPool();
        int i = 0;
        while (true) {
            int i2 = i + 1;
            String str2 = "_cflow$" + i;
            try {
                ctClass.getDeclaredField(str2);
                i = i2;
            } catch (NotFoundException unused) {
                classPool.recordCflow(str, this.declaringClass.getName(), str2);
                try {
                    CtClass ctClass2 = classPool.get("javassist.runtime.Cflow");
                    CtField ctField = new CtField(ctClass2, str2, ctClass);
                    ctField.setModifiers(9);
                    ctClass.addField(ctField, CtField.Initializer.byNew(ctClass2));
                    insertBefore(str2 + ".enter();", false);
                    insertAfter(str2 + ".exit();", true);
                    return;
                } catch (NotFoundException e) {
                    throw new CannotCompileException(e);
                }
            }
        }
    }

    public void addLocalVariable(String str, CtClass ctClass) throws CannotCompileException {
        this.declaringClass.checkModify();
        ConstPool constPool = this.methodInfo.getConstPool();
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (localVariableAttribute == null) {
            localVariableAttribute = new LocalVariableAttribute(constPool);
            codeAttribute.getAttributes().add(localVariableAttribute);
        }
        LocalVariableAttribute localVariableAttribute2 = localVariableAttribute;
        int maxLocals = codeAttribute.getMaxLocals();
        String of = Descriptor.of(ctClass);
        localVariableAttribute2.addEntry(0, codeAttribute.getCodeLength(), constPool.addUtf8Info(str), constPool.addUtf8Info(of), maxLocals);
        codeAttribute.setMaxLocals(maxLocals + Descriptor.dataSize(of));
    }

    public void insertParameter(CtClass ctClass) throws CannotCompileException {
        this.declaringClass.checkModify();
        String descriptor = this.methodInfo.getDescriptor();
        String insertParameter = Descriptor.insertParameter(ctClass, descriptor);
        try {
            addParameter2(Modifier.isStatic(getModifiers()) ? 0 : 1, ctClass, descriptor);
            this.methodInfo.setDescriptor(insertParameter);
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    public void addParameter(CtClass ctClass) throws CannotCompileException {
        this.declaringClass.checkModify();
        String descriptor = this.methodInfo.getDescriptor();
        String appendParameter = Descriptor.appendParameter(ctClass, descriptor);
        try {
            addParameter2((!Modifier.isStatic(getModifiers()) ? 1 : 0) + Descriptor.paramSize(descriptor), ctClass, descriptor);
            this.methodInfo.setDescriptor(appendParameter);
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    private void addParameter2(int i, CtClass ctClass, String str) throws BadBytecode {
        int addClassInfo;
        int i2;
        char c;
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            if (ctClass.isPrimitive()) {
                CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) ctClass;
                i2 = ctPrimitiveType.getDataSize();
                c = ctPrimitiveType.getDescriptor();
                addClassInfo = 0;
            } else {
                addClassInfo = this.methodInfo.getConstPool().addClassInfo(ctClass);
                i2 = 1;
                c = 'L';
            }
            codeAttribute.insertLocalVar(i, i2);
            LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (localVariableAttribute != null) {
                localVariableAttribute.shiftIndex(i, i2);
            }
            LocalVariableTypeAttribute localVariableTypeAttribute = (LocalVariableTypeAttribute) codeAttribute.getAttribute("LocalVariableTypeTable");
            if (localVariableTypeAttribute != null) {
                localVariableTypeAttribute.shiftIndex(i, i2);
            }
            StackMapTable stackMapTable = (StackMapTable) codeAttribute.getAttribute(StackMapTable.tag);
            if (stackMapTable != null) {
                stackMapTable.insertLocal(i, StackMapTable.typeTagOf(c), addClassInfo);
            }
            StackMap stackMap = (StackMap) codeAttribute.getAttribute(StackMap.tag);
            if (stackMap != null) {
                stackMap.insertLocal(i, StackMapTable.typeTagOf(c), addClassInfo);
            }
        }
    }

    public void instrument(CodeConverter codeConverter) throws CannotCompileException {
        this.declaringClass.checkModify();
        codeConverter.doit(getDeclaringClass(), this.methodInfo, this.methodInfo.getConstPool());
    }

    public void instrument(ExprEditor exprEditor) throws CannotCompileException {
        if (this.declaringClass.isFrozen()) {
            this.declaringClass.checkModify();
        }
        if (exprEditor.doit(this.declaringClass, this.methodInfo)) {
            this.declaringClass.checkModify();
        }
    }

    public void insertBefore(String str) throws CannotCompileException {
        insertBefore(str, true);
    }

    private void insertBefore(String str, boolean z) throws CannotCompileException {
        CtClass ctClass = this.declaringClass;
        ctClass.checkModify();
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        CodeIterator it = codeAttribute.iterator();
        Javac javac = new Javac(ctClass);
        try {
            javac.recordParamNames(codeAttribute, javac.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers())));
            javac.recordLocalVariables(codeAttribute, 0);
            javac.recordReturnType(getReturnType0(), false);
            javac.compileStmnt(str);
            Bytecode bytecode = javac.getBytecode();
            int maxStack = bytecode.getMaxStack();
            int maxLocals = bytecode.getMaxLocals();
            if (maxStack > codeAttribute.getMaxStack()) {
                codeAttribute.setMaxStack(maxStack);
            }
            if (maxLocals > codeAttribute.getMaxLocals()) {
                codeAttribute.setMaxLocals(maxLocals);
            }
            it.insert(bytecode.getExceptionTable(), it.insertEx(bytecode.get()));
            if (z) {
                this.methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
            }
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2);
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    public void insertAfter(String str) throws CannotCompileException {
        insertAfter(str, false, false);
    }

    public void insertAfter(String str, boolean z) throws CannotCompileException {
        insertAfter(str, z, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ad, code lost:
        r11.setMark2(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b0, code lost:
        if (r1 == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b2, code lost:
        r22 = r12;
        r23 = r16;
        r6 = r17;
        r3 = r7;
        r17 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00bd, code lost:
        r2 = new javassist.bytecode.Bytecode(r9, 0, r14);
        r2.setStackDepth(r10.getMaxStack() + r13);
        r3 = new javassist.compiler.Javac(r2, r0);
        r3.recordParamNames(r10, r3.recordParams(getParameterTypes(), javassist.Modifier.isStatic(getModifiers())));
        r6 = r17;
        r4 = r3.recordReturnType(r6, r13);
        r3.recordLocalVariables(r10, 0);
        r17 = r1;
        r22 = r2;
        r23 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00f1, code lost:
        r24 = r6;
        r1 = insertAfterAdvice(r22, r3, r26, r9, r6, r23);
        r11.append(r22.getExceptionTable(), r11.append(r22.get()));
        insertGoto(r11, r11.getCodeLength() - r1, r7);
        r2 = r11.getMark2();
        r1 = r17;
     */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void insertAfter(java.lang.String r26, boolean r27, boolean r28) throws javassist.CannotCompileException {
        /*
            Method dump skipped, instructions count: 425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.CtBehavior.insertAfter(java.lang.String, boolean, boolean):void");
    }

    private int insertAfterAdvice(Bytecode bytecode, Javac javac, String str, ConstPool constPool, CtClass ctClass, int i) throws CompileError {
        int currentPc = bytecode.currentPc();
        if (ctClass == CtClass.voidType) {
            bytecode.addOpcode(1);
            bytecode.addAstore(i);
            javac.compileStmnt(str);
            bytecode.addOpcode(177);
            if (bytecode.getMaxLocals() < 1) {
                bytecode.setMaxLocals(1);
            }
        } else {
            bytecode.addStore(i, ctClass);
            javac.compileStmnt(str);
            bytecode.addLoad(i, ctClass);
            if (ctClass.isPrimitive()) {
                bytecode.addOpcode(((CtPrimitiveType) ctClass).getReturnOp());
            } else {
                bytecode.addOpcode(176);
            }
        }
        return bytecode.currentPc() - currentPc;
    }

    private void insertGoto(CodeIterator codeIterator, int i, int i2) throws BadBytecode {
        codeIterator.setMark(i);
        codeIterator.writeByte(0, i2);
        boolean z = (i + 2) - i2 > 32767;
        int i3 = z ? 4 : 2;
        CodeIterator.Gap insertGapAt = codeIterator.insertGapAt(i2, i3, false);
        int i4 = (insertGapAt.position + insertGapAt.length) - i3;
        int mark = codeIterator.getMark() - i4;
        if (z) {
            codeIterator.writeByte(200, i4);
            codeIterator.write32bit(mark, i4 + 1);
        } else if (mark <= 32767) {
            codeIterator.writeByte(167, i4);
            codeIterator.write16bit(mark, i4 + 1);
        } else {
            if (insertGapAt.length < 4) {
                CodeIterator.Gap insertGapAt2 = codeIterator.insertGapAt(insertGapAt.position, 2, false);
                i4 = ((insertGapAt2.position + insertGapAt2.length) + insertGapAt.length) - 4;
            }
            codeIterator.writeByte(200, i4);
            codeIterator.write32bit(codeIterator.getMark() - i4, i4 + 1);
        }
    }

    private int insertAfterHandler(boolean z, Bytecode bytecode, CtClass ctClass, int i, Javac javac, String str) throws CompileError {
        if (z) {
            int maxLocals = bytecode.getMaxLocals();
            bytecode.incMaxLocals(1);
            int currentPc = bytecode.currentPc();
            bytecode.addAstore(maxLocals);
            if (ctClass.isPrimitive()) {
                char descriptor = ((CtPrimitiveType) ctClass).getDescriptor();
                if (descriptor == 'D') {
                    bytecode.addDconst(0.0d);
                    bytecode.addDstore(i);
                } else if (descriptor == 'F') {
                    bytecode.addFconst(0.0f);
                    bytecode.addFstore(i);
                } else if (descriptor == 'J') {
                    bytecode.addLconst(0L);
                    bytecode.addLstore(i);
                } else if (descriptor == 'V') {
                    bytecode.addOpcode(1);
                    bytecode.addAstore(i);
                } else {
                    bytecode.addIconst(0);
                    bytecode.addIstore(i);
                }
            } else {
                bytecode.addOpcode(1);
                bytecode.addAstore(i);
            }
            javac.compileStmnt(str);
            bytecode.addAload(maxLocals);
            bytecode.addOpcode(191);
            return bytecode.currentPc() - currentPc;
        }
        return 0;
    }

    public void addCatch(String str, CtClass ctClass) throws CannotCompileException {
        addCatch(str, ctClass, "$e");
    }

    public void addCatch(String str, CtClass ctClass, String str2) throws CannotCompileException {
        CtClass ctClass2 = this.declaringClass;
        ctClass2.checkModify();
        ConstPool constPool = this.methodInfo.getConstPool();
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        CodeIterator it = codeAttribute.iterator();
        Bytecode bytecode = new Bytecode(constPool, codeAttribute.getMaxStack(), codeAttribute.getMaxLocals());
        bytecode.setStackDepth(1);
        Javac javac = new Javac(bytecode, ctClass2);
        try {
            javac.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers()));
            bytecode.addAstore(javac.recordVariable(ctClass, str2));
            javac.compileStmnt(str);
            int maxStack = bytecode.getMaxStack();
            int maxLocals = bytecode.getMaxLocals();
            if (maxStack > codeAttribute.getMaxStack()) {
                codeAttribute.setMaxStack(maxStack);
            }
            if (maxLocals > codeAttribute.getMaxLocals()) {
                codeAttribute.setMaxLocals(maxLocals);
            }
            int codeLength = it.getCodeLength();
            int append = it.append(bytecode.get());
            codeAttribute.getExceptionTable().add(getStartPosOfBody(codeAttribute), codeLength, codeLength, constPool.addClassInfo(ctClass));
            it.append(bytecode.getExceptionTable(), append);
            this.methodInfo.rebuildStackMapIf6(ctClass2.getClassPool(), ctClass2.getClassFile2());
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2);
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    public int insertAt(int i, String str) throws CannotCompileException {
        return insertAt(i, true, str);
    }

    public int insertAt(int i, boolean z, String str) throws CannotCompileException {
        CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        LineNumberAttribute lineNumberAttribute = (LineNumberAttribute) codeAttribute.getAttribute(LineNumberAttribute.tag);
        if (lineNumberAttribute == null) {
            throw new CannotCompileException("no line number info");
        }
        LineNumberAttribute.Pc nearPc = lineNumberAttribute.toNearPc(i);
        int i2 = nearPc.line;
        int i3 = nearPc.index;
        if (z) {
            CtClass ctClass = this.declaringClass;
            ctClass.checkModify();
            CodeIterator it = codeAttribute.iterator();
            Javac javac = new Javac(ctClass);
            try {
                javac.recordLocalVariables(codeAttribute, i3);
                javac.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers()));
                javac.setMaxLocals(codeAttribute.getMaxLocals());
                javac.compileStmnt(str);
                Bytecode bytecode = javac.getBytecode();
                int maxLocals = bytecode.getMaxLocals();
                int maxStack = bytecode.getMaxStack();
                codeAttribute.setMaxLocals(maxLocals);
                if (maxStack > codeAttribute.getMaxStack()) {
                    codeAttribute.setMaxStack(maxStack);
                }
                it.insert(bytecode.getExceptionTable(), it.insertAt(i3, bytecode.get()));
                this.methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
                return i2;
            } catch (NotFoundException e) {
                throw new CannotCompileException(e);
            } catch (BadBytecode e2) {
                throw new CannotCompileException(e2);
            } catch (CompileError e3) {
                throw new CannotCompileException(e3);
            }
        }
        return i2;
    }
}
