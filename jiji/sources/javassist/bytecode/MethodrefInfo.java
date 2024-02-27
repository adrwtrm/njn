package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class MethodrefInfo extends MemberrefInfo {
    static final int tag = 10;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 10;
    }

    @Override // javassist.bytecode.MemberrefInfo
    public String getTagName() {
        return "Method";
    }

    public MethodrefInfo(int i, int i2, int i3) {
        super(i, i2, i3);
    }

    public MethodrefInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(dataInputStream, i);
    }

    @Override // javassist.bytecode.MemberrefInfo
    protected int copy2(ConstPool constPool, int i, int i2) {
        return constPool.addMethodrefInfo(i, i2);
    }
}
