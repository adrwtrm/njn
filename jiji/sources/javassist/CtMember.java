package javassist;

/* loaded from: classes2.dex */
public abstract class CtMember {
    protected CtClass declaringClass;
    CtMember next = null;

    protected abstract void extendToString(StringBuffer stringBuffer);

    public abstract Object getAnnotation(Class<?> cls) throws ClassNotFoundException;

    public abstract Object[] getAnnotations() throws ClassNotFoundException;

    public abstract byte[] getAttribute(String str);

    public abstract Object[] getAvailableAnnotations();

    public abstract String getGenericSignature();

    public abstract int getModifiers();

    public abstract String getName();

    public abstract String getSignature();

    public abstract boolean hasAnnotation(String str);

    /* JADX INFO: Access modifiers changed from: package-private */
    public void nameReplaced() {
    }

    public abstract void setAttribute(String str, byte[] bArr);

    public abstract void setGenericSignature(String str);

    public abstract void setModifiers(int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Cache extends CtMember {
        private CtMember consTail;
        private CtMember fieldTail;
        private CtMember methodTail;

        @Override // javassist.CtMember
        protected void extendToString(StringBuffer stringBuffer) {
        }

        @Override // javassist.CtMember
        public Object getAnnotation(Class<?> cls) throws ClassNotFoundException {
            return null;
        }

        @Override // javassist.CtMember
        public Object[] getAnnotations() throws ClassNotFoundException {
            return null;
        }

        @Override // javassist.CtMember
        public byte[] getAttribute(String str) {
            return null;
        }

        @Override // javassist.CtMember
        public Object[] getAvailableAnnotations() {
            return null;
        }

        @Override // javassist.CtMember
        public String getGenericSignature() {
            return null;
        }

        @Override // javassist.CtMember
        public int getModifiers() {
            return 0;
        }

        @Override // javassist.CtMember
        public String getName() {
            return null;
        }

        @Override // javassist.CtMember
        public String getSignature() {
            return null;
        }

        @Override // javassist.CtMember
        public boolean hasAnnotation(String str) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CtMember methodHead() {
            return this;
        }

        @Override // javassist.CtMember
        public void setAttribute(String str, byte[] bArr) {
        }

        @Override // javassist.CtMember
        public void setGenericSignature(String str) {
        }

        @Override // javassist.CtMember
        public void setModifiers(int i) {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Cache(CtClassType ctClassType) {
            super(ctClassType);
            this.methodTail = this;
            this.consTail = this;
            this.fieldTail = this;
            this.next = this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CtMember lastMethod() {
            return this.methodTail;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CtMember consHead() {
            return this.methodTail;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CtMember lastCons() {
            return this.consTail;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CtMember fieldHead() {
            return this.consTail;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CtMember lastField() {
            return this.fieldTail;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void addMethod(CtMember ctMember) {
            ctMember.next = this.methodTail.next;
            this.methodTail.next = ctMember;
            CtMember ctMember2 = this.methodTail;
            if (ctMember2 == this.consTail) {
                this.consTail = ctMember;
                if (ctMember2 == this.fieldTail) {
                    this.fieldTail = ctMember;
                }
            }
            this.methodTail = ctMember;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void addConstructor(CtMember ctMember) {
            ctMember.next = this.consTail.next;
            this.consTail.next = ctMember;
            if (this.consTail == this.fieldTail) {
                this.fieldTail = ctMember;
            }
            this.consTail = ctMember;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void addField(CtMember ctMember) {
            ctMember.next = this;
            this.fieldTail.next = ctMember;
            this.fieldTail = ctMember;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static int count(CtMember ctMember, CtMember ctMember2) {
            int i = 0;
            while (ctMember != ctMember2) {
                i++;
                ctMember = ctMember.next;
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void remove(CtMember ctMember) {
            CtMember ctMember2 = this;
            while (true) {
                CtMember ctMember3 = ctMember2.next;
                if (ctMember3 == this) {
                    return;
                }
                if (ctMember3 == ctMember) {
                    ctMember2.next = ctMember3.next;
                    if (ctMember3 == this.methodTail) {
                        this.methodTail = ctMember2;
                    }
                    if (ctMember3 == this.consTail) {
                        this.consTail = ctMember2;
                    }
                    if (ctMember3 == this.fieldTail) {
                        this.fieldTail = ctMember2;
                        return;
                    }
                    return;
                }
                ctMember2 = ctMember2.next;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CtMember(CtClass ctClass) {
        this.declaringClass = ctClass;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final CtMember next() {
        return this.next;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(getClass().getName());
        stringBuffer.append("@");
        stringBuffer.append(Integer.toHexString(hashCode()));
        stringBuffer.append("[");
        stringBuffer.append(Modifier.toString(getModifiers()));
        extendToString(stringBuffer);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public CtClass getDeclaringClass() {
        return this.declaringClass;
    }

    public boolean visibleFrom(CtClass ctClass) {
        int modifiers = getModifiers();
        boolean z = true;
        if (Modifier.isPublic(modifiers)) {
            return true;
        }
        if (Modifier.isPrivate(modifiers)) {
            return ctClass == this.declaringClass;
        }
        String packageName = this.declaringClass.getPackageName();
        String packageName2 = ctClass.getPackageName();
        if (packageName != null) {
            z = packageName.equals(packageName2);
        } else if (packageName2 != null) {
            z = false;
        }
        return (z || !Modifier.isProtected(modifiers)) ? z : ctClass.subclassOf(this.declaringClass);
    }

    public boolean hasAnnotation(Class<?> cls) {
        return hasAnnotation(cls.getName());
    }
}
