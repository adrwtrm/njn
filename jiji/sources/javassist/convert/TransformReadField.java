package javassist.convert;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class TransformReadField extends Transformer {
    protected CtClass fieldClass;
    protected String fieldname;
    protected boolean isPrivate;
    protected String methodClassname;
    protected String methodName;

    public TransformReadField(Transformer transformer, CtField ctField, String str, String str2) {
        super(transformer);
        this.fieldClass = ctField.getDeclaringClass();
        this.fieldname = ctField.getName();
        this.methodClassname = str;
        this.methodName = str2;
        this.isPrivate = Modifier.isPrivate(ctField.getModifiers());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String isField(ClassPool classPool, ConstPool constPool, CtClass ctClass, String str, boolean z, int i) {
        if (constPool.getFieldrefName(i).equals(str)) {
            try {
                CtClass ctClass2 = classPool.get(constPool.getFieldrefClassName(i));
                if (ctClass2 == ctClass || (!z && isFieldInSuper(ctClass2, ctClass, str))) {
                    return constPool.getFieldrefType(i);
                }
            } catch (NotFoundException unused) {
            }
            return null;
        }
        return null;
    }

    static boolean isFieldInSuper(CtClass ctClass, CtClass ctClass2, String str) {
        if (ctClass.subclassOf(ctClass2)) {
            try {
                return ctClass.getField(str).getDeclaringClass() == ctClass2;
            } catch (NotFoundException unused) {
                return false;
            }
        }
        return false;
    }

    @Override // javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws BadBytecode {
        int byteAt = codeIterator.byteAt(i);
        if (byteAt == 180 || byteAt == 178) {
            String isField = isField(ctClass.getClassPool(), constPool, this.fieldClass, this.fieldname, this.isPrivate, codeIterator.u16bitAt(i + 1));
            if (isField != null) {
                if (byteAt == 178) {
                    codeIterator.move(i);
                    codeIterator.writeByte(1, codeIterator.insertGap(1));
                    i = codeIterator.next();
                }
                int addMethodrefInfo = constPool.addMethodrefInfo(constPool.addClassInfo(this.methodClassname), this.methodName, "(Ljava/lang/Object;)" + isField);
                codeIterator.writeByte(184, i);
                codeIterator.write16bit(addMethodrefInfo, i + 1);
            }
        }
        return i;
    }
}
