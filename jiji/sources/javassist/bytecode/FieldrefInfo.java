package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class FieldrefInfo extends MemberrefInfo {
    static final int tag = 9;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 9;
    }

    @Override // javassist.bytecode.MemberrefInfo
    public String getTagName() {
        return "Field";
    }

    public FieldrefInfo(int i, int i2, int i3) {
        super(i, i2, i3);
    }

    public FieldrefInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(dataInputStream, i);
    }

    @Override // javassist.bytecode.MemberrefInfo
    protected int copy2(ConstPool constPool, int i, int i2) {
        return constPool.addFieldrefInfo(i, i2);
    }
}
