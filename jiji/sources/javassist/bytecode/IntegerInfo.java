package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class IntegerInfo extends ConstInfo {
    static final int tag = 3;
    int value;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 3;
    }

    public IntegerInfo(int i, int i2) {
        super(i2);
        this.value = i;
    }

    public IntegerInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.value = dataInputStream.readInt();
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof IntegerInfo) && ((IntegerInfo) obj).value == this.value;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addIntegerInfo(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(3);
        dataOutputStream.writeInt(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Integer ");
        printWriter.println(this.value);
    }
}
