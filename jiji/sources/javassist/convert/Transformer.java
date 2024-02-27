package javassist.convert;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

/* loaded from: classes2.dex */
public abstract class Transformer implements Opcode {
    private Transformer next;

    public void clean() {
    }

    public int extraLocals() {
        return 0;
    }

    public int extraStack() {
        return 0;
    }

    public void initialize(ConstPool constPool, CodeAttribute codeAttribute) {
    }

    public abstract int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws CannotCompileException, BadBytecode;

    public Transformer(Transformer transformer) {
        this.next = transformer;
    }

    public Transformer getNext() {
        return this.next;
    }

    public void initialize(ConstPool constPool, CtClass ctClass, MethodInfo methodInfo) throws CannotCompileException {
        initialize(constPool, methodInfo.getCodeAttribute());
    }
}
