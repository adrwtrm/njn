package javassist.convert;

import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public final class TransformFieldAccess extends Transformer {
    private ConstPool constPool;
    private CtClass fieldClass;
    private String fieldname;
    private boolean isPrivate;
    private String newClassname;
    private String newFieldname;
    private int newIndex;

    public TransformFieldAccess(Transformer transformer, CtField ctField, String str, String str2) {
        super(transformer);
        this.fieldClass = ctField.getDeclaringClass();
        this.fieldname = ctField.getName();
        this.isPrivate = Modifier.isPrivate(ctField.getModifiers());
        this.newClassname = str;
        this.newFieldname = str2;
        this.constPool = null;
    }

    @Override // javassist.convert.Transformer
    public void initialize(ConstPool constPool, CodeAttribute codeAttribute) {
        if (this.constPool != constPool) {
            this.newIndex = 0;
        }
    }

    @Override // javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) {
        int byteAt = codeIterator.byteAt(i);
        if (byteAt == 180 || byteAt == 178 || byteAt == 181 || byteAt == 179) {
            int i2 = i + 1;
            String isField = TransformReadField.isField(ctClass.getClassPool(), constPool, this.fieldClass, this.fieldname, this.isPrivate, codeIterator.u16bitAt(i2));
            if (isField != null) {
                if (this.newIndex == 0) {
                    this.newIndex = constPool.addFieldrefInfo(constPool.addClassInfo(this.newClassname), constPool.addNameAndTypeInfo(this.newFieldname, isField));
                    this.constPool = constPool;
                }
                codeIterator.write16bit(this.newIndex, i2);
            }
        }
        return i;
    }
}
