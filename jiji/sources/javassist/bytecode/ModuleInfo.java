package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class ModuleInfo extends ConstInfo {
    static final int tag = 19;
    int name;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 19;
    }

    public ModuleInfo(int i, int i2) {
        super(i2);
        this.name = i;
    }

    public ModuleInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.name = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return this.name;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ModuleInfo) && ((ModuleInfo) obj).name == this.name;
    }

    public String getModuleName(ConstPool constPool) {
        return constPool.getUtf8Info(this.name);
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addModuleInfo(constPool2.addUtf8Info(constPool.getUtf8Info(this.name)));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(19);
        dataOutputStream.writeShort(this.name);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Module #");
        printWriter.println(this.name);
    }
}
