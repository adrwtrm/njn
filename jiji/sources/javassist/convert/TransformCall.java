package javassist.convert;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class TransformCall extends Transformer {
    protected String classname;
    protected ConstPool constPool;
    protected String methodDescriptor;
    protected String methodname;
    protected String newClassname;
    protected int newIndex;
    protected boolean newMethodIsPrivate;
    protected String newMethodname;

    public TransformCall(Transformer transformer, CtMethod ctMethod, CtMethod ctMethod2) {
        this(transformer, ctMethod.getName(), ctMethod2);
        this.classname = ctMethod.getDeclaringClass().getName();
    }

    public TransformCall(Transformer transformer, String str, CtMethod ctMethod) {
        super(transformer);
        this.methodname = str;
        this.methodDescriptor = ctMethod.getMethodInfo2().getDescriptor();
        String name = ctMethod.getDeclaringClass().getName();
        this.newClassname = name;
        this.classname = name;
        this.newMethodname = ctMethod.getName();
        this.constPool = null;
        this.newMethodIsPrivate = Modifier.isPrivate(ctMethod.getModifiers());
    }

    @Override // javassist.convert.Transformer
    public void initialize(ConstPool constPool, CodeAttribute codeAttribute) {
        if (this.constPool != constPool) {
            this.newIndex = 0;
        }
    }

    @Override // javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws BadBytecode {
        int u16bitAt;
        String eqMember;
        int byteAt = codeIterator.byteAt(i);
        return ((byteAt == 185 || byteAt == 183 || byteAt == 184 || byteAt == 182) && (eqMember = constPool.eqMember(this.methodname, this.methodDescriptor, (u16bitAt = codeIterator.u16bitAt(i + 1)))) != null && matchClass(eqMember, ctClass.getClassPool())) ? match(byteAt, i, codeIterator, constPool.getNameAndTypeDescriptor(constPool.getMemberNameAndType(u16bitAt)), constPool) : i;
    }

    private boolean matchClass(String str, ClassPool classPool) {
        if (this.classname.equals(str)) {
            return true;
        }
        try {
            CtClass ctClass = classPool.get(str);
            if (ctClass.subtypeOf(classPool.get(this.classname))) {
                try {
                    return ctClass.getMethod(this.methodname, this.methodDescriptor).getDeclaringClass().getName().equals(this.classname);
                } catch (NotFoundException unused) {
                    return true;
                }
            }
        } catch (NotFoundException unused2) {
        }
        return false;
    }

    protected int match(int i, int i2, CodeIterator codeIterator, int i3, ConstPool constPool) throws BadBytecode {
        if (this.newIndex == 0) {
            int addNameAndTypeInfo = constPool.addNameAndTypeInfo(constPool.addUtf8Info(this.newMethodname), i3);
            int addClassInfo = constPool.addClassInfo(this.newClassname);
            if (i == 185) {
                this.newIndex = constPool.addInterfaceMethodrefInfo(addClassInfo, addNameAndTypeInfo);
            } else {
                if (this.newMethodIsPrivate && i == 182) {
                    codeIterator.writeByte(183, i2);
                }
                this.newIndex = constPool.addMethodrefInfo(addClassInfo, addNameAndTypeInfo);
            }
            this.constPool = constPool;
        }
        codeIterator.write16bit(this.newIndex, i2 + 1);
        return i2;
    }
}
