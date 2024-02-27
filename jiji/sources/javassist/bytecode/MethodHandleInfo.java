package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class MethodHandleInfo extends ConstInfo {
    static final int tag = 15;
    int refIndex;
    int refKind;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 15;
    }

    public MethodHandleInfo(int i, int i2, int i3) {
        super(i3);
        this.refKind = i;
        this.refIndex = i2;
    }

    public MethodHandleInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.refKind = dataInputStream.readUnsignedByte();
        this.refIndex = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return (this.refKind << 16) ^ this.refIndex;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MethodHandleInfo) {
            MethodHandleInfo methodHandleInfo = (MethodHandleInfo) obj;
            return methodHandleInfo.refKind == this.refKind && methodHandleInfo.refIndex == this.refIndex;
        }
        return false;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addMethodHandleInfo(this.refKind, constPool.getItem(this.refIndex).copy(constPool, constPool2, map));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(15);
        dataOutputStream.writeByte(this.refKind);
        dataOutputStream.writeShort(this.refIndex);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("MethodHandle #");
        printWriter.print(this.refKind);
        printWriter.print(", index #");
        printWriter.println(this.refIndex);
    }
}
