package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class Utf8Info extends ConstInfo {
    static final int tag = 1;
    String string;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 1;
    }

    public Utf8Info(String str, int i) {
        super(i);
        this.string = str;
    }

    public Utf8Info(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.string = dataInputStream.readUTF();
    }

    public int hashCode() {
        return this.string.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof Utf8Info) && ((Utf8Info) obj).string.equals(this.string);
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addUtf8Info(this.string);
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(1);
        dataOutputStream.writeUTF(this.string);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("UTF8 \"");
        printWriter.print(this.string);
        printWriter.println("\"");
    }
}
