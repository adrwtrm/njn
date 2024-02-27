package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class StringInfo extends ConstInfo {
    static final int tag = 8;
    int string;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 8;
    }

    public StringInfo(int i, int i2) {
        super(i2);
        this.string = i;
    }

    public StringInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.string = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return this.string;
    }

    public boolean equals(Object obj) {
        return (obj instanceof StringInfo) && ((StringInfo) obj).string == this.string;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addStringInfo(constPool.getUtf8Info(this.string));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(8);
        dataOutputStream.writeShort(this.string);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("String #");
        printWriter.println(this.string);
    }
}
