package javassist.convert;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public final class TransformNewClass extends Transformer {
    private String classname;
    private int nested;
    private int newClassIndex;
    private String newClassName;
    private int newMethodIndex;
    private int newMethodNTIndex;

    public TransformNewClass(Transformer transformer, String str, String str2) {
        super(transformer);
        this.classname = str;
        this.newClassName = str2;
    }

    @Override // javassist.convert.Transformer
    public void initialize(ConstPool constPool, CodeAttribute codeAttribute) {
        this.nested = 0;
        this.newMethodIndex = 0;
        this.newMethodNTIndex = 0;
        this.newClassIndex = 0;
    }

    @Override // javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws CannotCompileException {
        int byteAt = codeIterator.byteAt(i);
        if (byteAt == 187) {
            int i2 = i + 1;
            if (constPool.getClassInfo(codeIterator.u16bitAt(i2)).equals(this.classname)) {
                if (codeIterator.byteAt(i + 3) != 89) {
                    throw new CannotCompileException("NEW followed by no DUP was found");
                }
                if (this.newClassIndex == 0) {
                    this.newClassIndex = constPool.addClassInfo(this.newClassName);
                }
                codeIterator.write16bit(this.newClassIndex, i2);
                this.nested++;
            }
        } else if (byteAt == 183) {
            int i3 = i + 1;
            int u16bitAt = codeIterator.u16bitAt(i3);
            if (constPool.isConstructor(this.classname, u16bitAt) != 0 && this.nested > 0) {
                int methodrefNameAndType = constPool.getMethodrefNameAndType(u16bitAt);
                if (this.newMethodNTIndex != methodrefNameAndType) {
                    this.newMethodNTIndex = methodrefNameAndType;
                    this.newMethodIndex = constPool.addMethodrefInfo(this.newClassIndex, methodrefNameAndType);
                }
                codeIterator.write16bit(this.newMethodIndex, i3);
                this.nested--;
            }
        }
        return i;
    }
}
