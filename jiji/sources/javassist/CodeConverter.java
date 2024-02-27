package javassist;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.convert.TransformAccessArrayField;
import javassist.convert.TransformAfter;
import javassist.convert.TransformBefore;
import javassist.convert.TransformCall;
import javassist.convert.TransformCallToStatic;
import javassist.convert.TransformFieldAccess;
import javassist.convert.TransformNew;
import javassist.convert.TransformNewClass;
import javassist.convert.TransformReadField;
import javassist.convert.TransformWriteField;
import javassist.convert.Transformer;

/* loaded from: classes2.dex */
public class CodeConverter {
    protected Transformer transformers = null;

    /* loaded from: classes2.dex */
    public interface ArrayAccessReplacementMethodNames {
        String byteOrBooleanRead();

        String byteOrBooleanWrite();

        String charRead();

        String charWrite();

        String doubleRead();

        String doubleWrite();

        String floatRead();

        String floatWrite();

        String intRead();

        String intWrite();

        String longRead();

        String longWrite();

        String objectRead();

        String objectWrite();

        String shortRead();

        String shortWrite();
    }

    /* loaded from: classes2.dex */
    public static class DefaultArrayAccessReplacementMethodNames implements ArrayAccessReplacementMethodNames {
        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String byteOrBooleanRead() {
            return "arrayReadByteOrBoolean";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String byteOrBooleanWrite() {
            return "arrayWriteByteOrBoolean";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String charRead() {
            return "arrayReadChar";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String charWrite() {
            return "arrayWriteChar";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String doubleRead() {
            return "arrayReadDouble";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String doubleWrite() {
            return "arrayWriteDouble";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String floatRead() {
            return "arrayReadFloat";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String floatWrite() {
            return "arrayWriteFloat";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String intRead() {
            return "arrayReadInt";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String intWrite() {
            return "arrayWriteInt";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String longRead() {
            return "arrayReadLong";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String longWrite() {
            return "arrayWriteLong";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String objectRead() {
            return "arrayReadObject";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String objectWrite() {
            return "arrayWriteObject";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String shortRead() {
            return "arrayReadShort";
        }

        @Override // javassist.CodeConverter.ArrayAccessReplacementMethodNames
        public String shortWrite() {
            return "arrayWriteShort";
        }
    }

    public void replaceNew(CtClass ctClass, CtClass ctClass2, String str) {
        this.transformers = new TransformNew(this.transformers, ctClass.getName(), ctClass2.getName(), str);
    }

    public void replaceNew(CtClass ctClass, CtClass ctClass2) {
        this.transformers = new TransformNewClass(this.transformers, ctClass.getName(), ctClass2.getName());
    }

    public void redirectFieldAccess(CtField ctField, CtClass ctClass, String str) {
        this.transformers = new TransformFieldAccess(this.transformers, ctField, ctClass.getName(), str);
    }

    public void replaceFieldRead(CtField ctField, CtClass ctClass, String str) {
        this.transformers = new TransformReadField(this.transformers, ctField, ctClass.getName(), str);
    }

    public void replaceFieldWrite(CtField ctField, CtClass ctClass, String str) {
        this.transformers = new TransformWriteField(this.transformers, ctField, ctClass.getName(), str);
    }

    public void replaceArrayAccess(CtClass ctClass, ArrayAccessReplacementMethodNames arrayAccessReplacementMethodNames) throws NotFoundException {
        this.transformers = new TransformAccessArrayField(this.transformers, ctClass.getName(), arrayAccessReplacementMethodNames);
    }

    public void redirectMethodCall(CtMethod ctMethod, CtMethod ctMethod2) throws CannotCompileException {
        if (!ctMethod.getMethodInfo2().getDescriptor().equals(ctMethod2.getMethodInfo2().getDescriptor())) {
            throw new CannotCompileException("signature mismatch: " + ctMethod2.getLongName());
        }
        int modifiers = ctMethod.getModifiers();
        int modifiers2 = ctMethod2.getModifiers();
        if (Modifier.isStatic(modifiers) != Modifier.isStatic(modifiers2) || ((Modifier.isPrivate(modifiers) && !Modifier.isPrivate(modifiers2)) || ctMethod.getDeclaringClass().isInterface() != ctMethod2.getDeclaringClass().isInterface())) {
            throw new CannotCompileException("invoke-type mismatch " + ctMethod2.getLongName());
        }
        this.transformers = new TransformCall(this.transformers, ctMethod, ctMethod2);
    }

    public void redirectMethodCall(String str, CtMethod ctMethod) throws CannotCompileException {
        this.transformers = new TransformCall(this.transformers, str, ctMethod);
    }

    public void redirectMethodCallToStatic(CtMethod ctMethod, CtMethod ctMethod2) {
        this.transformers = new TransformCallToStatic(this.transformers, ctMethod, ctMethod2);
    }

    public void insertBeforeMethod(CtMethod ctMethod, CtMethod ctMethod2) throws CannotCompileException {
        try {
            this.transformers = new TransformBefore(this.transformers, ctMethod, ctMethod2);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public void insertAfterMethod(CtMethod ctMethod, CtMethod ctMethod2) throws CannotCompileException {
        try {
            this.transformers = new TransformAfter(this.transformers, ctMethod, ctMethod2);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public void doit(CtClass ctClass, MethodInfo methodInfo, ConstPool constPool) throws CannotCompileException {
        Transformer transformer;
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null || (transformer = this.transformers) == null) {
            return;
        }
        for (transformer = this.transformers; transformer != null; transformer = transformer.getNext()) {
            transformer.initialize(constPool, ctClass, methodInfo);
        }
        CodeIterator it = codeAttribute.iterator();
        while (it.hasNext()) {
            try {
                int next = it.next();
                for (Transformer transformer2 = this.transformers; transformer2 != null; transformer2 = transformer2.getNext()) {
                    next = transformer2.transform(ctClass, next, it, constPool);
                }
            } catch (BadBytecode e) {
                throw new CannotCompileException(e);
            }
        }
        int i = 0;
        int i2 = 0;
        for (Transformer transformer3 = this.transformers; transformer3 != null; transformer3 = transformer3.getNext()) {
            int extraLocals = transformer3.extraLocals();
            if (extraLocals > i) {
                i = extraLocals;
            }
            int extraStack = transformer3.extraStack();
            if (extraStack > i2) {
                i2 = extraStack;
            }
        }
        for (Transformer transformer4 = this.transformers; transformer4 != null; transformer4 = transformer4.getNext()) {
            transformer4.clean();
        }
        if (i > 0) {
            codeAttribute.setMaxLocals(codeAttribute.getMaxLocals() + i);
        }
        if (i2 > 0) {
            codeAttribute.setMaxStack(codeAttribute.getMaxStack() + i2);
        }
        try {
            methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2.getMessage(), e2);
        }
    }
}
