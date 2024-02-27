package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public class ClassInfo extends ConstInfo {
    static final int tag = 7;
    int name;

    @Override // javassist.bytecode.ConstInfo
    public int getTag() {
        return 7;
    }

    public ClassInfo(int i, int i2) {
        super(i2);
        this.name = i;
    }

    public ClassInfo(DataInputStream dataInputStream, int i) throws IOException {
        super(i);
        this.name = dataInputStream.readUnsignedShort();
    }

    public int hashCode() {
        return this.name;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ClassInfo) && ((ClassInfo) obj).name == this.name;
    }

    @Override // javassist.bytecode.ConstInfo
    public String getClassName(ConstPool constPool) {
        return constPool.getUtf8Info(this.name);
    }

    @Override // javassist.bytecode.ConstInfo
    public void renameClass(ConstPool constPool, String str, String str2, Map<ConstInfo, ConstInfo> map) {
        String utf8Info = constPool.getUtf8Info(this.name);
        if (!utf8Info.equals(str) && (utf8Info.charAt(0) != '[' || utf8Info == (str2 = Descriptor.rename(utf8Info, str, str2)))) {
            str2 = null;
        }
        if (str2 != null) {
            if (map == null) {
                this.name = constPool.addUtf8Info(str2);
                return;
            }
            map.remove(this);
            this.name = constPool.addUtf8Info(str2);
            map.put(this, this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v3 */
    @Override // javassist.bytecode.ConstInfo
    public void renameClass(ConstPool constPool, Map<String, String> map, Map<ConstInfo, ConstInfo> map2) {
        String str;
        String utf8Info = constPool.getUtf8Info(this.name);
        String str2 = null;
        if (utf8Info.charAt(0) != '[' ? !((str = map.get(utf8Info)) == null || str.equals(utf8Info)) : utf8Info != (str = Descriptor.rename(utf8Info, map))) {
            str2 = str;
        }
        if (str2 != null) {
            if (map2 == null) {
                this.name = constPool.addUtf8Info(str2);
                return;
            }
            map2.remove(this);
            this.name = constPool.addUtf8Info(str2);
            map2.put(this, this);
        }
    }

    @Override // javassist.bytecode.ConstInfo
    public int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
        String str;
        String utf8Info = constPool.getUtf8Info(this.name);
        if (map != null && (str = map.get(utf8Info)) != null) {
            utf8Info = str;
        }
        return constPool2.addClassInfo(utf8Info);
    }

    @Override // javassist.bytecode.ConstInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(7);
        dataOutputStream.writeShort(this.name);
    }

    @Override // javassist.bytecode.ConstInfo
    public void print(PrintWriter printWriter) {
        printWriter.print("Class #");
        printWriter.println(this.name);
    }
}
