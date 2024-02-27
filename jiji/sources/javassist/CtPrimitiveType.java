package javassist;

/* loaded from: classes2.dex */
public final class CtPrimitiveType extends CtClass {
    private int arrayType;
    private int dataSize;
    private char descriptor;
    private String getMethodName;
    private String mDescriptor;
    private int returnOp;
    private String wrapperName;

    @Override // javassist.CtClass
    public int getModifiers() {
        return 17;
    }

    @Override // javassist.CtClass
    public boolean isPrimitive() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CtPrimitiveType(String str, char c, String str2, String str3, String str4, int i, int i2, int i3) {
        super(str);
        this.descriptor = c;
        this.wrapperName = str2;
        this.getMethodName = str3;
        this.mDescriptor = str4;
        this.returnOp = i;
        this.arrayType = i2;
        this.dataSize = i3;
    }

    public char getDescriptor() {
        return this.descriptor;
    }

    public String getWrapperName() {
        return this.wrapperName;
    }

    public String getGetMethodName() {
        return this.getMethodName;
    }

    public String getGetMethodDescriptor() {
        return this.mDescriptor;
    }

    public int getReturnOp() {
        return this.returnOp;
    }

    public int getArrayType() {
        return this.arrayType;
    }

    public int getDataSize() {
        return this.dataSize;
    }
}
