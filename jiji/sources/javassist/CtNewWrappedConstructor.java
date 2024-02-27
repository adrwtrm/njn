package javassist;

import javassist.CtMethod;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class CtNewWrappedConstructor extends CtNewWrappedMethod {
    private static final int PASS_NONE = 0;
    private static final int PASS_PARAMS = 2;

    CtNewWrappedConstructor() {
    }

    public static CtConstructor wrapped(CtClass[] ctClassArr, CtClass[] ctClassArr2, int i, CtMethod ctMethod, CtMethod.ConstParameter constParameter, CtClass ctClass) throws CannotCompileException {
        try {
            CtConstructor ctConstructor = new CtConstructor(ctClassArr, ctClass);
            ctConstructor.setExceptionTypes(ctClassArr2);
            ctConstructor.getMethodInfo2().setCodeAttribute(makeBody(ctClass, ctClass.getClassFile2(), i, ctMethod, ctClassArr, constParameter).toCodeAttribute());
            return ctConstructor;
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static javassist.bytecode.Bytecode makeBody(javassist.CtClass r10, javassist.bytecode.ClassFile r11, int r12, javassist.CtMethod r13, javassist.CtClass[] r14, javassist.CtMethod.ConstParameter r15) throws javassist.CannotCompileException {
        /*
            int r0 = r11.getSuperclassId()
            javassist.bytecode.Bytecode r9 = new javassist.bytecode.Bytecode
            javassist.bytecode.ConstPool r1 = r11.getConstPool()
            r2 = 0
            r9.<init>(r1, r2, r2)
            r9.setMaxLocals(r2, r14, r2)
            r9.addAload(r2)
            java.lang.String r1 = "<init>"
            r2 = 1
            if (r12 != 0) goto L20
            java.lang.String r12 = "()V"
            r9.addInvokespecial(r0, r1, r12)
        L1e:
            r12 = r2
            goto L4a
        L20:
            r3 = 2
            if (r12 != r3) goto L30
            int r12 = r9.addLoadParameters(r14, r2)
            int r2 = r2 + r12
            java.lang.String r12 = javassist.bytecode.Descriptor.ofConstructor(r14)
            r9.addInvokespecial(r0, r1, r12)
            goto L1e
        L30:
            int r12 = compileParameterList(r9, r14, r2)
            if (r15 != 0) goto L3b
            java.lang.String r2 = javassist.CtMethod.ConstParameter.defaultConstDescriptor()
            goto L44
        L3b:
            int r2 = r15.compile(r9)
            int r3 = r3 + r2
            java.lang.String r2 = r15.constDescriptor()
        L44:
            if (r12 >= r3) goto L47
            r12 = r3
        L47:
            r9.addInvokespecial(r0, r1, r2)
        L4a:
            if (r13 != 0) goto L52
            r10 = 177(0xb1, float:2.48E-43)
            r9.add(r10)
            goto L62
        L52:
            r4 = 0
            javassist.CtClass r6 = javassist.CtClass.voidType
            r1 = r10
            r2 = r11
            r3 = r13
            r5 = r14
            r7 = r15
            r8 = r9
            int r10 = makeBody0(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r12 >= r10) goto L62
            r12 = r10
        L62:
            r9.setMaxStack(r12)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.CtNewWrappedConstructor.makeBody(javassist.CtClass, javassist.bytecode.ClassFile, int, javassist.CtMethod, javassist.CtClass[], javassist.CtMethod$ConstParameter):javassist.bytecode.Bytecode");
    }
}
