package javassist;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class CtArray extends CtClass {
    private CtClass[] interfaces;
    protected ClassPool pool;

    @Override // javassist.CtClass
    public boolean isArray() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtArray(String str, ClassPool classPool) {
        super(str);
        this.interfaces = null;
        this.pool = classPool;
    }

    @Override // javassist.CtClass
    public ClassPool getClassPool() {
        return this.pool;
    }

    @Override // javassist.CtClass
    public int getModifiers() {
        try {
            return 16 | (getComponentType().getModifiers() & 7);
        } catch (NotFoundException unused) {
            return 16;
        }
    }

    @Override // javassist.CtClass
    public CtClass[] getInterfaces() throws NotFoundException {
        if (this.interfaces == null) {
            Class<?>[] interfaces = Object[].class.getInterfaces();
            this.interfaces = new CtClass[interfaces.length];
            for (int i = 0; i < interfaces.length; i++) {
                this.interfaces[i] = this.pool.get(interfaces[i].getName());
            }
        }
        return this.interfaces;
    }

    @Override // javassist.CtClass
    public boolean subtypeOf(CtClass ctClass) throws NotFoundException {
        if (super.subtypeOf(ctClass) || ctClass.getName().equals("java.lang.Object")) {
            return true;
        }
        for (CtClass ctClass2 : getInterfaces()) {
            if (ctClass2.subtypeOf(ctClass)) {
                return true;
            }
        }
        return ctClass.isArray() && getComponentType().subtypeOf(ctClass.getComponentType());
    }

    @Override // javassist.CtClass
    public CtClass getComponentType() throws NotFoundException {
        String name = getName();
        return this.pool.get(name.substring(0, name.length() - 2));
    }

    @Override // javassist.CtClass
    public CtClass getSuperclass() throws NotFoundException {
        return this.pool.get("java.lang.Object");
    }

    @Override // javassist.CtClass
    public CtMethod[] getMethods() {
        try {
            return getSuperclass().getMethods();
        } catch (NotFoundException unused) {
            return super.getMethods();
        }
    }

    @Override // javassist.CtClass
    public CtMethod getMethod(String str, String str2) throws NotFoundException {
        return getSuperclass().getMethod(str, str2);
    }

    @Override // javassist.CtClass
    public CtConstructor[] getConstructors() {
        try {
            return getSuperclass().getConstructors();
        } catch (NotFoundException unused) {
            return super.getConstructors();
        }
    }
}
