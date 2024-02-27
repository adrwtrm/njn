package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class DoubleInfo extends ConstInfo {
    static final int tag = 6;
    double value;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 6;
    }

    public DoubleInfo(double d, int i) {
        super(i);
        this.value = d;
    }

    public DoubleInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.value = dataInputStream.readDouble();
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.value);
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }

    public boolean equals(Object obj) {
        return (obj instanceof DoubleInfo) && ((DoubleInfo) obj).value == this.value;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addDoubleInfo(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(6);
        dataOutputStream.writeDouble(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Double ");
        printWriter.println(this.value);
    }
}
