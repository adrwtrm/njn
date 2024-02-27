package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class PackageInfo extends ConstInfo {
    static final int tag = 20;
    int name;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 20;
    }

    public PackageInfo(int i, int i2) {
        super(i2);
        this.name = i;
    }

    public PackageInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.name = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return this.name;
    }

    public boolean equals(Object obj) {
        return (obj instanceof PackageInfo) && ((PackageInfo) obj).name == this.name;
    }

    public String getPackageName(ConstPool constPool) {
        return constPool.getUtf8Info(this.name);
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addModuleInfo(constPool2.addUtf8Info(constPool.getUtf8Info(this.name)));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(20);
        dataOutputStream.writeShort(this.name);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Package #");
        printWriter.println(this.name);
    }
}
