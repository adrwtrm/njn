package javassist.convert;

import javassist.CtClass;
import javassist.CtField;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public final class TransformWriteField extends TransformReadField {
    public TransformWriteField(Transformer transformer, CtField ctField, String str, String str2) {
        super(transformer, ctField, str, str2);
    }

    @Override // javassist.convert.TransformReadField, javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws BadBytecode {
        int byteAt = codeIterator.byteAt(i);
        if (byteAt == 181 || byteAt == 179) {
            String isField = isField(ctClass.getClassPool(), constPool, this.fieldClass, this.fieldname, this.isPrivate, codeIterator.u16bitAt(i + 1));
            if (isField != null) {
                if (byteAt == 179) {
                    CodeAttribute codeAttribute = codeIterator.get();
                    codeIterator.move(i);
                    char charAt = isField.charAt(0);
                    if (charAt == 'J' || charAt == 'D') {
                        int insertGap = codeIterator.insertGap(3);
                        codeIterator.writeByte(1, insertGap);
                        codeIterator.writeByte(91, insertGap + 1);
                        codeIterator.writeByte(87, insertGap + 2);
                        codeAttribute.setMaxStack(codeAttribute.getMaxStack() + 2);
                    } else {
                        int insertGap2 = codeIterator.insertGap(2);
                        codeIterator.writeByte(1, insertGap2);
                        codeIterator.writeByte(95, insertGap2 + 1);
                        codeAttribute.setMaxStack(codeAttribute.getMaxStack() + 1);
                    }
                    i = codeIterator.next();
                }
                int addMethodrefInfo = constPool.addMethodrefInfo(constPool.addClassInfo(this.methodClassname), this.methodName, "(Ljava/lang/Object;" + isField + ")V");
                codeIterator.writeByte(184, i);
                codeIterator.write16bit(addMethodrefInfo, i + 1);
            }
        }
        return i;
    }
}
