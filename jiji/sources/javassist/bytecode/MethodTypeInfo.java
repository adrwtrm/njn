package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class MethodTypeInfo extends ConstInfo {
    static final int tag = 16;
    int descriptor;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 16;
    }

    public MethodTypeInfo(int i, int i2) {
        super(i2);
        this.descriptor = i;
    }

    public MethodTypeInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.descriptor = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return this.descriptor;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MethodTypeInfo) && ((MethodTypeInfo) obj).descriptor == this.descriptor;
    }

    @Override // javassist.bytecode.ConstInfo
    public void renameClass(ConstPool constPool, String str, String str2, Map<ConstInfo, ConstInfo> map) {
        String utf8Info = constPool.getUtf8Info(this.descriptor);
        String rename = Descriptor.rename(utf8Info, str, str2);
        if (utf8Info != rename) {
            if (map == null) {
                this.descriptor = constPool.addUtf8Info(rename);
                return;
            }
            map.remove(this);
            this.descriptor = constPool.addUtf8Info(rename);
            map.put(this, this);
        }
    }

    @Override // javassist.bytecode.ConstInfo
    public void renameClass(ConstPool constPool, Map<String, String> map, Map<ConstInfo, ConstInfo> map2) {
        String utf8Info = constPool.getUtf8Info(this.descriptor);
        String rename = Descriptor.rename(utf8Info, map);
        if (utf8Info != rename) {
            if (map2 == null) {
                this.descriptor = constPool.addUtf8Info(rename);
                return;
            }
            map2.remove(this);
            this.descriptor = constPool.addUtf8Info(rename);
            map2.put(this, this);
        }
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addMethodTypeInfo(constPool2.addUtf8Info(Descriptor.rename(constPool.getUtf8Info(this.descriptor), map)));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(16);
        dataOutputStream.writeShort(this.descriptor);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("MethodType #");
        printWriter.println(this.descriptor);
    }
}
