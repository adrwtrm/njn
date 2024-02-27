package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ExceptionTable implements Cloneable {
    private ConstPool constPool;
    private List<ExceptionTableEntry> entries;

    private static int shiftPc(int i, int i2, int i3, boolean z) {
        return (i > i2 || (z && i == i2)) ? i + i3 : i;
    }

    public ExceptionTable(ConstPool constPool) {
        this.constPool = constPool;
        this.entries = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExceptionTable(ConstPool constPool, DataInputStream dataInputStream) throws IOException {
        this.constPool = constPool;
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        ArrayList arrayList = new ArrayList(readUnsignedShort);
        for (int i = 0; i < readUnsignedShort; i++) {
            arrayList.add(new ExceptionTableEntry(dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort()));
        }
        this.entries = arrayList;
    }

    public Object clone() throws CloneNotSupportedException {
        ExceptionTable exceptionTable = (ExceptionTable) super.clone();
        exceptionTable.entries = new ArrayList(this.entries);
        return exceptionTable;
    }

    public int size() {
        return this.entries.size();
    }

    public int startPc(int i) {
        return this.entries.get(i).startPc;
    }

    public void setStartPc(int i, int i2) {
        this.entries.get(i).startPc = i2;
    }

    public int endPc(int i) {
        return this.entries.get(i).endPc;
    }

    public void setEndPc(int i, int i2) {
        this.entries.get(i).endPc = i2;
    }

    public int handlerPc(int i) {
        return this.entries.get(i).handlerPc;
    }

    public void setHandlerPc(int i, int i2) {
        this.entries.get(i).handlerPc = i2;
    }

    public int catchType(int i) {
        return this.entries.get(i).catchType;
    }

    public void setCatchType(int i, int i2) {
        this.entries.get(i).catchType = i2;
    }

    public void add(int i, ExceptionTable exceptionTable, int i2) {
        int size = exceptionTable.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            ExceptionTableEntry exceptionTableEntry = exceptionTable.entries.get(size);
            add(i, exceptionTableEntry.startPc + i2, exceptionTableEntry.endPc + i2, exceptionTableEntry.handlerPc + i2, exceptionTableEntry.catchType);
        }
    }

    public void add(int i, int i2, int i3, int i4, int i5) {
        if (i2 < i3) {
            this.entries.add(i, new ExceptionTableEntry(i2, i3, i4, i5));
        }
    }

    public void add(int i, int i2, int i3, int i4) {
        if (i < i2) {
            this.entries.add(new ExceptionTableEntry(i, i2, i3, i4));
        }
    }

    public void remove(int i) {
        this.entries.remove(i);
    }

    public ExceptionTable copy(ConstPool constPool, Map<String, String> map) {
        ExceptionTable exceptionTable = new ExceptionTable(constPool);
        ConstPool constPool2 = this.constPool;
        for (ExceptionTableEntry exceptionTableEntry : this.entries) {
            exceptionTable.add(exceptionTableEntry.startPc, exceptionTableEntry.endPc, exceptionTableEntry.handlerPc, constPool2.copy(exceptionTableEntry.catchType, constPool, map));
        }
        return exceptionTable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftPc(int i, int i2, boolean z) {
        for (ExceptionTableEntry exceptionTableEntry : this.entries) {
            exceptionTableEntry.startPc = shiftPc(exceptionTableEntry.startPc, i, i2, z);
            exceptionTableEntry.endPc = shiftPc(exceptionTableEntry.endPc, i, i2, z);
            exceptionTableEntry.handlerPc = shiftPc(exceptionTableEntry.handlerPc, i, i2, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(size());
        for (ExceptionTableEntry exceptionTableEntry : this.entries) {
            dataOutputStream.writeShort(exceptionTableEntry.startPc);
            dataOutputStream.writeShort(exceptionTableEntry.endPc);
            dataOutputStream.writeShort(exceptionTableEntry.handlerPc);
            dataOutputStream.writeShort(exceptionTableEntry.catchType);
        }
    }
}
