package javassist.bytecode;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class LongVector {
    static final int ABITS = 7;
    static final int ASIZE = 128;
    static final int VSIZE = 8;
    private int elements;
    private ConstInfo[][] objects;

    public LongVector() {
        this.objects = new ConstInfo[8];
        this.elements = 0;
    }

    public LongVector(int i) {
        this.objects = new ConstInfo[((i >> 7) & (-8)) + 8];
        this.elements = 0;
    }

    public int size() {
        return this.elements;
    }

    public int capacity() {
        return this.objects.length * 128;
    }

    public ConstInfo elementAt(int i) {
        if (i < 0 || this.elements <= i) {
            return null;
        }
        return this.objects[i >> 7][i & 127];
    }

    public void addElement(ConstInfo constInfo) {
        int i = this.elements;
        int i2 = i >> 7;
        int i3 = i & 127;
        ConstInfo[][] constInfoArr = this.objects;
        int length = constInfoArr.length;
        if (i2 >= length) {
            ConstInfo[][] constInfoArr2 = new ConstInfo[length + 8];
            System.arraycopy(constInfoArr, 0, constInfoArr2, 0, length);
            this.objects = constInfoArr2;
        }
        ConstInfo[][] constInfoArr3 = this.objects;
        if (constInfoArr3[i2] == null) {
            constInfoArr3[i2] = new ConstInfo[128];
        }
        constInfoArr3[i2][i3] = constInfo;
        this.elements++;
    }
}
