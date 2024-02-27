package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class LineNumberAttribute extends AttributeInfo {
    public static final String tag = "LineNumberTable";

    /* loaded from: classes2.dex */
    public static class Pc {
        public int index;
        public int line;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LineNumberAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    private LineNumberAttribute(ConstPool constPool, byte[] bArr) {
        super(constPool, tag, bArr);
    }

    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }

    public int startPc(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 2);
    }

    public int lineNumber(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 4);
    }

    public int toLineNumber(int i) {
        int tableLength = tableLength();
        int i2 = 0;
        while (true) {
            if (i2 >= tableLength) {
                break;
            } else if (i >= startPc(i2)) {
                i2++;
            } else if (i2 == 0) {
                return lineNumber(0);
            }
        }
        return lineNumber(i2 - 1);
    }

    public int toStartPc(int i) {
        int tableLength = tableLength();
        for (int i2 = 0; i2 < tableLength; i2++) {
            if (i == lineNumber(i2)) {
                return startPc(i2);
            }
        }
        return -1;
    }

    public Pc toNearPc(int i) {
        int i2;
        int tableLength = tableLength();
        int i3 = 0;
        if (tableLength > 0) {
            i2 = lineNumber(0) - i;
            i3 = startPc(0);
        } else {
            i2 = 0;
        }
        for (int i4 = 1; i4 < tableLength; i4++) {
            int lineNumber = lineNumber(i4) - i;
            if ((lineNumber < 0 && lineNumber > i2) || (lineNumber >= 0 && (lineNumber < i2 || i2 < 0))) {
                i3 = startPc(i4);
                i2 = lineNumber;
            }
        }
        Pc pc = new Pc();
        pc.index = i3;
        pc.line = i + i2;
        return pc;
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        byte[] bArr = this.info;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr2[i] = bArr[i];
        }
        return new LineNumberAttribute(constPool, bArr2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftPc(int i, int i2, boolean z) {
        int tableLength = tableLength();
        for (int i3 = 0; i3 < tableLength; i3++) {
            int i4 = (i3 * 4) + 2;
            int readU16bit = ByteArray.readU16bit(this.info, i4);
            if (readU16bit > i || (z && readU16bit == i)) {
                ByteArray.write16bit(readU16bit + i2, this.info, i4);
            }
        }
    }
}
