package javassist.convert;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.StackMap;
import javassist.bytecode.StackMapTable;

/* loaded from: classes2.dex */
public final class TransformNew extends Transformer {
    private String classname;
    private int nested;
    private String trapClass;
    private String trapMethod;

    public TransformNew(Transformer transformer, String str, String str2, String str3) {
        super(transformer);
        this.classname = str;
        this.trapClass = str2;
        this.trapMethod = str3;
    }

    @Override // javassist.convert.Transformer
    public void initialize(ConstPool constPool, CodeAttribute codeAttribute) {
        this.nested = 0;
    }

    @Override // javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws CannotCompileException {
        int byteAt = codeIterator.byteAt(i);
        if (byteAt == 187) {
            int i2 = i + 1;
            if (constPool.getClassInfo(codeIterator.u16bitAt(i2)).equals(this.classname)) {
                int i3 = i + 3;
                if (codeIterator.byteAt(i3) != 89) {
                    throw new CannotCompileException("NEW followed by no DUP was found");
                }
                codeIterator.writeByte(0, i);
                codeIterator.writeByte(0, i2);
                codeIterator.writeByte(0, i + 2);
                codeIterator.writeByte(0, i3);
                this.nested++;
                StackMapTable stackMapTable = (StackMapTable) codeIterator.get().getAttribute(StackMapTable.tag);
                if (stackMapTable != null) {
                    stackMapTable.removeNew(i);
                }
                StackMap stackMap = (StackMap) codeIterator.get().getAttribute(StackMap.tag);
                if (stackMap != null) {
                    stackMap.removeNew(i);
                }
            }
        } else if (byteAt == 183) {
            int i4 = i + 1;
            int isConstructor = constPool.isConstructor(this.classname, codeIterator.u16bitAt(i4));
            if (isConstructor != 0 && this.nested > 0) {
                int computeMethodref = computeMethodref(isConstructor, constPool);
                codeIterator.writeByte(184, i);
                codeIterator.write16bit(computeMethodref, i4);
                this.nested--;
            }
        }
        return i;
    }

    private int computeMethodref(int i, ConstPool constPool) {
        return constPool.addMethodrefInfo(constPool.addClassInfo(this.trapClass), constPool.addNameAndTypeInfo(constPool.addUtf8Info(this.trapMethod), constPool.addUtf8Info(Descriptor.changeReturnType(this.classname, constPool.getUtf8Info(i)))));
    }
}
