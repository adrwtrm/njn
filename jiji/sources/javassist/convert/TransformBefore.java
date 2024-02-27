package javassist.convert;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public class TransformBefore extends TransformCall {
    protected byte[] loadCode;
    protected int locals;
    protected int maxLocals;
    protected CtClass[] parameterTypes;
    protected byte[] saveCode;

    public TransformBefore(Transformer transformer, CtMethod ctMethod, CtMethod ctMethod2) throws NotFoundException {
        super(transformer, ctMethod, ctMethod2);
        this.methodDescriptor = ctMethod.getMethodInfo2().getDescriptor();
        this.parameterTypes = ctMethod.getParameterTypes();
        this.locals = 0;
        this.maxLocals = 0;
        this.loadCode = null;
        this.saveCode = null;
    }

    @Override // javassist.convert.TransformCall, javassist.convert.Transformer
    public void initialize(ConstPool constPool, CodeAttribute codeAttribute) {
        super.initialize(constPool, codeAttribute);
        this.locals = 0;
        this.maxLocals = codeAttribute.getMaxLocals();
        this.loadCode = null;
        this.saveCode = null;
    }

    @Override // javassist.convert.TransformCall
    protected int match(int i, int i2, CodeIterator codeIterator, int i3, ConstPool constPool) throws BadBytecode {
        if (this.newIndex == 0) {
            this.newIndex = constPool.addMethodrefInfo(constPool.addClassInfo(this.newClassname), constPool.addNameAndTypeInfo(this.newMethodname, Descriptor.insertParameter(this.classname, Descriptor.ofParameters(this.parameterTypes) + 'V')));
            this.constPool = constPool;
        }
        if (this.saveCode == null) {
            makeCode(this.parameterTypes, constPool);
        }
        return match2(i2, codeIterator);
    }

    protected int match2(int i, CodeIterator codeIterator) throws BadBytecode {
        codeIterator.move(i);
        codeIterator.insert(this.saveCode);
        codeIterator.insert(this.loadCode);
        int insertGap = codeIterator.insertGap(3);
        codeIterator.writeByte(184, insertGap);
        codeIterator.write16bit(this.newIndex, insertGap + 1);
        codeIterator.insert(this.loadCode);
        return codeIterator.next();
    }

    @Override // javassist.convert.Transformer
    public int extraLocals() {
        return this.locals;
    }

    protected void makeCode(CtClass[] ctClassArr, ConstPool constPool) {
        Bytecode bytecode = new Bytecode(constPool, 0, 0);
        Bytecode bytecode2 = new Bytecode(constPool, 0, 0);
        int i = this.maxLocals;
        int length = ctClassArr != null ? ctClassArr.length : 0;
        bytecode2.addAload(i);
        makeCode2(bytecode, bytecode2, 0, length, ctClassArr, i + 1);
        bytecode.addAstore(i);
        this.saveCode = bytecode.get();
        this.loadCode = bytecode2.get();
    }

    private void makeCode2(Bytecode bytecode, Bytecode bytecode2, int i, int i2, CtClass[] ctClassArr, int i3) {
        if (i < i2) {
            makeCode2(bytecode, bytecode2, i + 1, i2, ctClassArr, i3 + bytecode2.addLoad(i3, ctClassArr[i]));
            bytecode.addStore(i3, ctClassArr[i]);
            return;
        }
        this.locals = i3 - this.maxLocals;
    }
}
