package javassist.convert;

import javassist.CtMethod;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public class TransformCallToStatic extends TransformCall {
    public TransformCallToStatic(Transformer transformer, CtMethod ctMethod, CtMethod ctMethod2) {
        super(transformer, ctMethod, ctMethod2);
        this.methodDescriptor = ctMethod.getMethodInfo2().getDescriptor();
    }

    @Override // javassist.convert.TransformCall
    protected int match(int i, int i2, CodeIterator codeIterator, int i3, ConstPool constPool) {
        if (this.newIndex == 0) {
            this.newIndex = constPool.addMethodrefInfo(constPool.addClassInfo(this.newClassname), constPool.addNameAndTypeInfo(this.newMethodname, Descriptor.insertParameter(this.classname, this.methodDescriptor)));
            this.constPool = constPool;
        }
        codeIterator.writeByte(184, i2);
        codeIterator.write16bit(this.newIndex, i2 + 1);
        return i2;
    }
}
