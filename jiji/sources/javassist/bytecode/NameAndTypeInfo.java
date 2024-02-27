package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class NameAndTypeInfo extends ConstInfo {
    static final int tag = 12;
    int memberName;
    int typeDescriptor;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 12;
    }

    public NameAndTypeInfo(int i, int i2, int i3) {
        super(i3);
        this.memberName = i;
        this.typeDescriptor = i2;
    }

    public NameAndTypeInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.memberName = dataInputStream.readUnsignedShort();
        this.typeDescriptor = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return (this.memberName << 16) ^ this.typeDescriptor;
    }

    public boolean equals(Object obj) {
        if (obj instanceof NameAndTypeInfo) {
            NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo) obj;
            return nameAndTypeInfo.memberName == this.memberName && nameAndTypeInfo.typeDescriptor == this.typeDescriptor;
        }
        return false;
    }

    @Override // javassist.bytecode.ConstInfo
    public void renameClass(ConstPool constPool, String str, String str2, Map<ConstInfo, ConstInfo> map) {
        String utf8Info = constPool.getUtf8Info(this.typeDescriptor);
        String rename = Descriptor.rename(utf8Info, str, str2);
        if (utf8Info != rename) {
            if (map == null) {
                this.typeDescriptor = constPool.addUtf8Info(rename);
                return;
            }
            map.remove(this);
            this.typeDescriptor = constPool.addUtf8Info(rename);
            map.put(this, this);
        }
    }

    @Override // javassist.bytecode.ConstInfo
    public void renameClass(ConstPool constPool, Map<String, String> map, Map<ConstInfo, ConstInfo> map2) {
        String utf8Info = constPool.getUtf8Info(this.typeDescriptor);
        String rename = Descriptor.rename(utf8Info, map);
        if (utf8Info != rename) {
            if (map2 == null) {
                this.typeDescriptor = constPool.addUtf8Info(rename);
                return;
            }
            map2.remove(this);
            this.typeDescriptor = constPool.addUtf8Info(rename);
            map2.put(this, this);
        }
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        return constPool2.addNameAndTypeInfo(constPool2.addUtf8Info(constPool.getUtf8Info(this.memberName)), constPool2.addUtf8Info(Descriptor.rename(constPool.getUtf8Info(this.typeDescriptor), map)));
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(12);
        dataOutputStream.writeShort(this.memberName);
        dataOutputStream.writeShort(this.typeDescriptor);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("NameAndType #");
        printWriter.print(this.memberName);
        printWriter.print(", type #");
        printWriter.println(this.typeDescriptor);
    }
}
