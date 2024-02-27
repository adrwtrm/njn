package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class FloatInfo extends ConstInfo {
    static final int tag = 4;
    float value;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 4;
    }

    public FloatInfo(float f, int i) {
        super(i);
        this.value = f;
    }

    public FloatInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.value = dataInputStream.readFloat();
    }

    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }

    public boolean equals(Object obj) {
        return (obj instanceof FloatInfo) && ((FloatInfo) obj).value == this.value;
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addFloatInfo(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(4);
        dataOutputStream.writeFloat(this.value);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Float ");
        printWriter.println(this.value);
    }
}
