package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class InterfaceMethodrefInfo extends MemberrefInfo {
    static final int tag = 11;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 11;
    }

    @Override // javassist.bytecode.MemberrefInfo
    public String getTagName() {
        return "Interface";
    }

    public InterfaceMethodrefInfo(int i, int i2, int i3) {
        super(i, i2, i3);
    }

    public InterfaceMethodrefInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(dataInputStream, i);
    }

    @Override // javassist.bytecode.MemberrefInfo
    protected int copy2(ConstPool constPool, int i, int i2) {
        return constPool.addInterfaceMethodrefInfo(i, i2);
    }
}
