package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class LongInfo extends ConstInfo {
    static final int tag = 5;
    long value;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 5;
    }

    public LongInfo(long j, int i) {
        super(i);
        this.value = j;
    }

    public LongInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.value = dataInputStream.readLong();
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public boolean equals(Object obj) {
        return (obj instanceof LongInfo) && ((LongInfo) obj).value == this.value;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addLongInfo(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(5);
        dataOutputStream.writeLong(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Long ");
        printWriter.println(this.value);
    }
}
