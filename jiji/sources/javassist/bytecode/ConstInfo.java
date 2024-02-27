package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConstPool.java */
/* loaded from: classes2.dex */
public abstract class ConstInfo {
    int index;

    public abstract int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map);

    public String getClassName(ConstPool constPool) {
        return null;
    }

    public abstract int getTag();

    public abstract void print(PrintWriter printWriter);

    public void renameClass(ConstPool constPool, String str, String str2, Map<ConstInfo, ConstInfo> map) {
    }

    public void renameClass(ConstPool constPool, Map<String, String> map, Map<ConstInfo, ConstInfo> map2) {
    }

    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public ConstInfo(int i) {
        this.index = i;
    }

    public String toString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        print(new PrintWriter(byteArrayOutputStream));
        return byteArrayOutputStream.toString();
    }
}
