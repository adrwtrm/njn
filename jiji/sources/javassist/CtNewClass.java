package javassist;

import java.io.DataOutputStream;
import java.io.IOException;
import javassist.bytecode.ClassFile;

/* loaded from: classes2.dex */
public class CtNewClass extends CtClassType {
    protected boolean hasConstructor;

    public CtNewClass(String str, ClassPool classPool, boolean z, CtClass ctClass) {
        super(str, classPool);
        this.wasChanged = true;
        this.classfile = new ClassFile(z, str, (z || ctClass == null) ? null : ctClass.getName());
        if (z && ctClass != null) {
            this.classfile.setInterfaces(new String[]{ctClass.getName()});
        }
        setModifiers(Modifier.setPublic(getModifiers()));
        this.hasConstructor = z;
    }

    @Override // javassist.CtClassType, javassist.CtClass
    public void extendToString(StringBuffer stringBuffer) {
        if (this.hasConstructor) {
            stringBuffer.append("hasConstructor ");
        }
        super.extendToString(stringBuffer);
    }

    @Override // javassist.CtClassType, javassist.CtClass
    public void addConstructor(CtConstructor ctConstructor) throws CannotCompileException {
        this.hasConstructor = true;
        super.addConstructor(ctConstructor);
    }

    @Override // javassist.CtClassType, javassist.CtClass
    public void toBytecode(DataOutputStream dataOutputStream) throws CannotCompileException, IOException {
        if (!this.hasConstructor) {
            try {
                inheritAllConstructors();
                this.hasConstructor = true;
            } catch (NotFoundException e) {
                throw new CannotCompileException(e);
            }
        }
        super.toBytecode(dataOutputStream);
    }

    public void inheritAllConstructors() throws CannotCompileException, NotFoundException {
        CtConstructor[] declaredConstructors;
        CtClass superclass = getSuperclass();
        int i = 0;
        for (CtConstructor ctConstructor : superclass.getDeclaredConstructors()) {
            int modifiers = ctConstructor.getModifiers();
            if (isInheritable(modifiers, superclass)) {
                CtConstructor make = CtNewConstructor.make(ctConstructor.getParameterTypes(), ctConstructor.getExceptionTypes(), this);
                make.setModifiers(modifiers & 7);
                addConstructor(make);
                i++;
            }
        }
        if (i < 1) {
            throw new CannotCompileException("no inheritable constructor in " + superclass.getName());
        }
    }

    private boolean isInheritable(int i, CtClass ctClass) {
        if (Modifier.isPrivate(i)) {
            return false;
        }
        if (Modifier.isPackage(i)) {
            String packageName = getPackageName();
            String packageName2 = ctClass.getPackageName();
            if (packageName == null) {
                return packageName2 == null;
            }
            return packageName.equals(packageName2);
        }
        return true;
    }
}
