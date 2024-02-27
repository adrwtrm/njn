package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class DynamicInfo extends ConstInfo {
    static final int tag = 17;
    int bootstrap;
    int nameAndType;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 17;
    }

    public DynamicInfo(int i, int i2, int i3) {
        super(i3);
        this.bootstrap = i;
        this.nameAndType = i2;
    }

    public DynamicInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.bootstrap = dataInputStream.readUnsignedShort();
        this.nameAndType = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return (this.bootstrap << 16) ^ this.nameAndType;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DynamicInfo) {
            DynamicInfo dynamicInfo = (DynamicInfo) obj;
            return dynamicInfo.bootstrap == this.bootstrap && dynamicInfo.nameAndType == this.nameAndType;
        }
        return false;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addDynamicInfo(this.bootstrap, constPool.getItem(this.nameAndType).copy(constPool, constPool2, map));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(17);
        dataOutputStream.writeShort(this.bootstrap);
        dataOutputStream.writeShort(this.nameAndType);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Dynamic #");
        printWriter.print(this.bootstrap);
        printWriter.print(", name&type #");
        printWriter.println(this.nameAndType);
    }
}
