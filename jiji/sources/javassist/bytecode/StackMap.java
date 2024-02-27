package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javassist.CannotCompileException;

/* loaded from: classes2.dex */
public class StackMap extends AttributeInfo {
    public static final int DOUBLE = 3;
    public static final int FLOAT = 2;
    public static final int INTEGER = 1;
    public static final int LONG = 4;
    public static final int NULL = 5;
    public static final int OBJECT = 7;
    public static final int THIS = 6;
    public static final int TOP = 0;
    public static final int UNINIT = 8;
    public static final String tag = "StackMap";

    StackMap(ConstPool constPool, byte[] bArr) {
        super(constPool, tag, bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StackMap(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public int numOfEntries() {
        return ByteArray.readU16bit(this.info, 0);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        Copier copier = new Copier(this, constPool, map);
        copier.visit();
        return copier.getStackMap();
    }

    /* loaded from: classes2.dex */
    public static class Walker {
        byte[] info;

        public void objectVariable(int i, int i2) {
        }

        public void typeInfo(int i, byte b) {
        }

        public void uninitialized(int i, int i2) {
        }

        public Walker(StackMap stackMap) {
            this.info = stackMap.get();
        }

        public void visit() {
            int readU16bit = ByteArray.readU16bit(this.info, 0);
            int i = 2;
            for (int i2 = 0; i2 < readU16bit; i2++) {
                int readU16bit2 = ByteArray.readU16bit(this.info, i);
                int locals = locals(i + 4, readU16bit2, ByteArray.readU16bit(this.info, i + 2));
                i = stack(locals + 2, readU16bit2, ByteArray.readU16bit(this.info, locals));
            }
        }

        public int locals(int i, int i2, int i3) {
            return typeInfoArray(i, i2, i3, true);
        }

        public int stack(int i, int i2, int i3) {
            return typeInfoArray(i, i2, i3, false);
        }

        public int typeInfoArray(int i, int i2, int i3, boolean z) {
            for (int i4 = 0; i4 < i3; i4++) {
                i = typeInfoArray2(i4, i);
            }
            return i;
        }

        int typeInfoArray2(int i, int i2) {
            byte[] bArr = this.info;
            byte b = bArr[i2];
            if (b == 7) {
                objectVariable(i2, ByteArray.readU16bit(bArr, i2 + 1));
            } else if (b == 8) {
                uninitialized(i2, ByteArray.readU16bit(bArr, i2 + 1));
            } else {
                typeInfo(i2, b);
                return i2 + 1;
            }
            return i2 + 3;
        }
    }

    /* loaded from: classes2.dex */
    static class Copier extends Walker {
        Map<String, String> classnames;
        byte[] dest;
        ConstPool destCp;
        ConstPool srcCp;

        Copier(StackMap stackMap, ConstPool constPool, Map<String, String> map) {
            super(stackMap);
            this.srcCp = stackMap.getConstPool();
            this.dest = new byte[this.info.length];
            this.destCp = constPool;
            this.classnames = map;
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void visit() {
            ByteArray.write16bit(ByteArray.readU16bit(this.info, 0), this.dest, 0);
            super.visit();
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int locals(int i, int i2, int i3) {
            ByteArray.write16bit(i2, this.dest, i - 4);
            return super.locals(i, i2, i3);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int typeInfoArray(int i, int i2, int i3, boolean z) {
            ByteArray.write16bit(i3, this.dest, i - 2);
            return super.typeInfoArray(i, i2, i3, z);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void typeInfo(int i, byte b) {
            this.dest[i] = b;
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void objectVariable(int i, int i2) {
            this.dest[i] = 7;
            ByteArray.write16bit(this.srcCp.copy(i2, this.destCp, this.classnames), this.dest, i + 1);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void uninitialized(int i, int i2) {
            byte[] bArr = this.dest;
            bArr[i] = 8;
            ByteArray.write16bit(i2, bArr, i + 1);
        }

        public StackMap getStackMap() {
            return new StackMap(this.destCp, this.dest);
        }
    }

    public void insertLocal(int i, int i2, int i3) throws BadBytecode {
        set(new InsertLocal(this, i, i2, i3).doit());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class SimpleCopy extends Walker {
        Writer writer;

        SimpleCopy(StackMap stackMap) {
            super(stackMap);
            this.writer = new Writer();
        }

        byte[] doit() {
            visit();
            return this.writer.toByteArray();
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void visit() {
            this.writer.write16bit(ByteArray.readU16bit(this.info, 0));
            super.visit();
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int locals(int i, int i2, int i3) {
            this.writer.write16bit(i2);
            return super.locals(i, i2, i3);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int typeInfoArray(int i, int i2, int i3, boolean z) {
            this.writer.write16bit(i3);
            return super.typeInfoArray(i, i2, i3, z);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void typeInfo(int i, byte b) {
            this.writer.writeVerifyTypeInfo(b, 0);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void objectVariable(int i, int i2) {
            this.writer.writeVerifyTypeInfo(7, i2);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void uninitialized(int i, int i2) {
            this.writer.writeVerifyTypeInfo(8, i2);
        }
    }

    /* loaded from: classes2.dex */
    static class InsertLocal extends SimpleCopy {
        private int varData;
        private int varIndex;
        private int varTag;

        InsertLocal(StackMap stackMap, int i, int i2, int i3) {
            super(stackMap);
            this.varIndex = i;
            this.varTag = i2;
            this.varData = i3;
        }

        @Override // javassist.bytecode.StackMap.SimpleCopy, javassist.bytecode.StackMap.Walker
        public int typeInfoArray(int i, int i2, int i3, boolean z) {
            if (!z || i3 < this.varIndex) {
                return super.typeInfoArray(i, i2, i3, z);
            }
            this.writer.write16bit(i3 + 1);
            for (int i4 = 0; i4 < i3; i4++) {
                if (i4 == this.varIndex) {
                    writeVarTypeInfo();
                }
                i = typeInfoArray2(i4, i);
            }
            if (i3 == this.varIndex) {
                writeVarTypeInfo();
            }
            return i;
        }

        private void writeVarTypeInfo() {
            int i = this.varTag;
            if (i == 7) {
                this.writer.writeVerifyTypeInfo(7, this.varData);
            } else if (i == 8) {
                this.writer.writeVerifyTypeInfo(8, this.varData);
            } else {
                this.writer.writeVerifyTypeInfo(this.varTag, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftPc(int i, int i2, boolean z) throws BadBytecode {
        new Shifter(this, i, i2, z).visit();
    }

    /* loaded from: classes2.dex */
    static class Shifter extends Walker {
        private boolean exclusive;
        private int gap;
        private int where;

        public Shifter(StackMap stackMap, int i, int i2, boolean z) {
            super(stackMap);
            this.where = i;
            this.gap = i2;
            this.exclusive = z;
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int locals(int i, int i2, int i3) {
            if (!this.exclusive ? this.where < i2 : this.where <= i2) {
                ByteArray.write16bit(this.gap + i2, this.info, i - 4);
            }
            return super.locals(i, i2, i3);
        }

        @Override // javassist.bytecode.StackMap.Walker
        public void uninitialized(int i, int i2) {
            if (this.where <= i2) {
                ByteArray.write16bit(i2 + this.gap, this.info, i + 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftForSwitch(int i, int i2) throws BadBytecode {
        new SwitchShifter(this, i, i2).visit();
    }

    /* loaded from: classes2.dex */
    static class SwitchShifter extends Walker {
        private int gap;
        private int where;

        public SwitchShifter(StackMap stackMap, int i, int i2) {
            super(stackMap);
            this.where = i;
            this.gap = i2;
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int locals(int i, int i2, int i3) {
            int i4 = this.where;
            if (i4 == i + i2) {
                ByteArray.write16bit(i2 - this.gap, this.info, i - 4);
            } else if (i4 == i) {
                ByteArray.write16bit(this.gap + i2, this.info, i - 4);
            }
            return super.locals(i, i2, i3);
        }
    }

    public void removeNew(int i) throws CannotCompileException {
        set(new NewRemover(this, i).doit());
    }

    /* loaded from: classes2.dex */
    static class NewRemover extends SimpleCopy {
        int posOfNew;

        NewRemover(StackMap stackMap, int i) {
            super(stackMap);
            this.posOfNew = i;
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int stack(int i, int i2, int i3) {
            return stackTypeInfoArray(i, i2, i3);
        }

        private int stackTypeInfoArray(int i, int i2, int i3) {
            int i4 = i;
            int i5 = 0;
            for (int i6 = 0; i6 < i3; i6++) {
                byte b = this.info[i4];
                if (b != 7) {
                    if (b != 8) {
                        i4++;
                    } else if (ByteArray.readU16bit(this.info, i4 + 1) == this.posOfNew) {
                        i5++;
                    }
                }
                i4 += 3;
            }
            this.writer.write16bit(i3 - i5);
            for (int i7 = 0; i7 < i3; i7++) {
                byte b2 = this.info[i];
                if (b2 == 7) {
                    objectVariable(i, ByteArray.readU16bit(this.info, i + 1));
                } else if (b2 == 8) {
                    int readU16bit = ByteArray.readU16bit(this.info, i + 1);
                    if (readU16bit != this.posOfNew) {
                        uninitialized(i, readU16bit);
                    }
                } else {
                    typeInfo(i, b2);
                    i++;
                }
                i += 3;
            }
            return i;
        }
    }

    public void print(PrintWriter printWriter) {
        new Printer(this, printWriter).print();
    }

    /* loaded from: classes2.dex */
    static class Printer extends Walker {
        private PrintWriter writer;

        public Printer(StackMap stackMap, PrintWriter printWriter) {
            super(stackMap);
            this.writer = printWriter;
        }

        public void print() {
            this.writer.println(ByteArray.readU16bit(this.info, 0) + " entries");
            visit();
        }

        @Override // javassist.bytecode.StackMap.Walker
        public int locals(int i, int i2, int i3) {
            this.writer.println("  * offset " + i2);
            return super.locals(i, i2, i3);
        }
    }

    /* loaded from: classes2.dex */
    public static class Writer {
        private ByteArrayOutputStream output = new ByteArrayOutputStream();

        public byte[] toByteArray() {
            return this.output.toByteArray();
        }

        public StackMap toStackMap(ConstPool constPool) {
            return new StackMap(constPool, this.output.toByteArray());
        }

        public void writeVerifyTypeInfo(int i, int i2) {
            this.output.write(i);
            if (i == 7 || i == 8) {
                write16bit(i2);
            }
        }

        public void write16bit(int i) {
            this.output.write((i >>> 8) & 255);
            this.output.write(i & 255);
        }
    }
}
