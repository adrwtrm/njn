package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public abstract class MemberrefInfo extends ConstInfo {
    int classIndex;
    int nameAndTypeIndex;

    protected abstract int copy2(ConstPool constPool, int i, int i2);

    public abstract String getTagName();

    public MemberrefInfo(int i, int i2, int i3) {
        super(i3);
        this.classIndex = i;
        this.nameAndTypeIndex = i2;
    }

    public MemberrefInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.classIndex = dataInputStream.readUnsignedShort();
        this.nameAndTypeIndex = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return (this.classIndex << 16) ^ this.nameAndTypeIndex;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MemberrefInfo) {
            MemberrefInfo memberrefInfo = (MemberrefInfo) obj;
            return memberrefInfo.classIndex == this.classIndex && memberrefInfo.nameAndTypeIndex == this.nameAndTypeIndex && memberrefInfo.getClass() == getClass();
        }
        return false;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return copy2(constPool2, constPool.getItem(this.classIndex).copy(constPool, constPool2, map), constPool.getItem(this.nameAndTypeIndex).copy(constPool, constPool2, map));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(getTag());
        dataOutputStream.writeShort(this.classIndex);
        dataOutputStream.writeShort(this.nameAndTypeIndex);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print(getTagName() + " #");
        printWriter.print(this.classIndex);
        printWriter.print(", name&type #");
        printWriter.println(this.nameAndTypeIndex);
    }
}
