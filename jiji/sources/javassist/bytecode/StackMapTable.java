package javassist.bytecode;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import javassist.CannotCompileException;

/* loaded from: classes2.dex */
public class StackMapTable extends AttributeInfo {
    public static final int DOUBLE = 3;
    public static final int FLOAT = 2;
    public static final int INTEGER = 1;
    public static final int LONG = 4;
    public static final int NULL = 5;
    public static final int OBJECT = 7;
    public static final int THIS = 6;
    public static final int TOP = 0;
    public static final int UNINIT = 8;
    public static final String tag = "StackMapTable";

    public static int typeTagOf(char c) {
        if (c != 'D') {
            if (c != 'F') {
                if (c != 'J') {
                    return (c == 'L' || c == '[') ? 7 : 1;
                }
                return 4;
            }
            return 2;
        }
        return 3;
    }

    StackMapTable(ConstPool constPool, byte[] bArr) {
        super(constPool, tag, bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StackMapTable(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) throws RuntimeCopyException {
        try {
            return new StackMapTable(constPool, new Copier(this.constPool, this.info, constPool, map).doit());
        } catch (BadBytecode unused) {
            throw new RuntimeCopyException("bad bytecode. fatal?");
        }
    }

    /* loaded from: classes2.dex */
    public static class RuntimeCopyException extends RuntimeException {
        private static final long serialVersionUID = 1;

        public RuntimeCopyException(String str) {
            super(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.bytecode.AttributeInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
    }

    /* loaded from: classes2.dex */
    public static class Walker {
        byte[] info;
        int numOfEntries;

        public void appendFrame(int i, int i2, int[] iArr, int[] iArr2) throws BadBytecode {
        }

        public void chopFrame(int i, int i2, int i3) throws BadBytecode {
        }

        public void fullFrame(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) throws BadBytecode {
        }

        public void objectOrUninitialized(int i, int i2, int i3) {
        }

        public void sameFrame(int i, int i2) throws BadBytecode {
        }

        public void sameLocals(int i, int i2, int i3, int i4) throws BadBytecode {
        }

        public Walker(StackMapTable stackMapTable) {
            this(stackMapTable.get());
        }

        public Walker(byte[] bArr) {
            this.info = bArr;
            this.numOfEntries = ByteArray.readU16bit(bArr, 0);
        }

        public final int size() {
            return this.numOfEntries;
        }

        public void parse() throws BadBytecode {
            int i = this.numOfEntries;
            int i2 = 2;
            for (int i3 = 0; i3 < i; i3++) {
                i2 = stackMapFrames(i2, i3);
            }
        }

        int stackMapFrames(int i, int i2) throws BadBytecode {
            byte[] bArr = this.info;
            int i3 = bArr[i] & 255;
            if (i3 < 64) {
                sameFrame(i, i3);
                return i + 1;
            } else if (i3 < 128) {
                return sameLocals(i, i3);
            } else {
                if (i3 >= 247) {
                    if (i3 == 247) {
                        return sameLocals(i, i3);
                    }
                    if (i3 < 251) {
                        chopFrame(i, ByteArray.readU16bit(bArr, i + 1), 251 - i3);
                    } else if (i3 != 251) {
                        if (i3 < 255) {
                            return appendFrame(i, i3);
                        }
                        return fullFrame(i);
                    } else {
                        sameFrame(i, ByteArray.readU16bit(bArr, i + 1));
                    }
                    return i + 3;
                }
                throw new BadBytecode("bad frame_type in StackMapTable");
            }
        }

        private int sameLocals(int i, int i2) throws BadBytecode {
            int readU16bit;
            int i3;
            int readU16bit2;
            if (i2 < 128) {
                readU16bit = i2 - 64;
                i3 = i;
            } else {
                readU16bit = ByteArray.readU16bit(this.info, i + 1);
                i3 = i + 2;
            }
            byte[] bArr = this.info;
            int i4 = bArr[i3 + 1] & 255;
            if (i4 == 7 || i4 == 8) {
                i3 += 2;
                readU16bit2 = ByteArray.readU16bit(bArr, i3);
                objectOrUninitialized(i4, readU16bit2, i3);
            } else {
                readU16bit2 = 0;
            }
            sameLocals(i, readU16bit, i4, readU16bit2);
            return i3 + 2;
        }

        private int appendFrame(int i, int i2) throws BadBytecode {
            int i3 = i2 - 251;
            int readU16bit = ByteArray.readU16bit(this.info, i + 1);
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int i4 = i + 3;
            for (int i5 = 0; i5 < i3; i5++) {
                byte[] bArr = this.info;
                int i6 = bArr[i4] & 255;
                iArr[i5] = i6;
                if (i6 == 7 || i6 == 8) {
                    int i7 = i4 + 1;
                    int readU16bit2 = ByteArray.readU16bit(bArr, i7);
                    iArr2[i5] = readU16bit2;
                    objectOrUninitialized(i6, readU16bit2, i7);
                    i4 += 3;
                } else {
                    iArr2[i5] = 0;
                    i4++;
                }
            }
            appendFrame(i, readU16bit, iArr, iArr2);
            return i4;
        }

        private int fullFrame(int i) throws BadBytecode {
            int readU16bit = ByteArray.readU16bit(this.info, i + 1);
            int readU16bit2 = ByteArray.readU16bit(this.info, i + 3);
            int[] iArr = new int[readU16bit2];
            int[] iArr2 = new int[readU16bit2];
            int verifyTypeInfo = verifyTypeInfo(i + 5, readU16bit2, iArr, iArr2);
            int readU16bit3 = ByteArray.readU16bit(this.info, verifyTypeInfo);
            int[] iArr3 = new int[readU16bit3];
            int[] iArr4 = new int[readU16bit3];
            int verifyTypeInfo2 = verifyTypeInfo(verifyTypeInfo + 2, readU16bit3, iArr3, iArr4);
            fullFrame(i, readU16bit, iArr, iArr2, iArr3, iArr4);
            return verifyTypeInfo2;
        }

        private int verifyTypeInfo(int i, int i2, int[] iArr, int[] iArr2) {
            for (int i3 = 0; i3 < i2; i3++) {
                byte[] bArr = this.info;
                int i4 = i + 1;
                int i5 = bArr[i] & 255;
                iArr[i3] = i5;
                if (i5 == 7 || i5 == 8) {
                    int readU16bit = ByteArray.readU16bit(bArr, i4);
                    iArr2[i3] = readU16bit;
                    objectOrUninitialized(i5, readU16bit, i4);
                    i4 += 2;
                }
                i = i4;
            }
            return i;
        }
    }

    /* loaded from: classes2.dex */
    static class SimpleCopy extends Walker {
        private Writer writer;

        protected int copyData(int i, int i2) {
            return i2;
        }

        protected int[] copyData(int[] iArr, int[] iArr2) {
            return iArr2;
        }

        public SimpleCopy(byte[] bArr) {
            super(bArr);
            this.writer = new Writer(bArr.length);
        }

        public byte[] doit() throws BadBytecode {
            parse();
            return this.writer.toByteArray();
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void sameFrame(int i, int i2) {
            this.writer.sameFrame(i2);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void sameLocals(int i, int i2, int i3, int i4) {
            this.writer.sameLocals(i2, i3, copyData(i3, i4));
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void chopFrame(int i, int i2, int i3) {
            this.writer.chopFrame(i2, i3);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void appendFrame(int i, int i2, int[] iArr, int[] iArr2) {
            this.writer.appendFrame(i2, iArr, copyData(iArr, iArr2));
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void fullFrame(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
            this.writer.fullFrame(i2, iArr, copyData(iArr, iArr2), iArr3, copyData(iArr3, iArr4));
        }
    }

    /* loaded from: classes2.dex */
    static class Copier extends SimpleCopy {
        private Map<String, String> classnames;
        private ConstPool destPool;
        private ConstPool srcPool;

        public Copier(ConstPool constPool, byte[] bArr, ConstPool constPool2, Map<String, String> map) {
            super(bArr);
            this.srcPool = constPool;
            this.destPool = constPool2;
            this.classnames = map;
        }

        @Override // javassist.bytecode.StackMapTable.SimpleCopy
        protected int copyData(int i, int i2) {
            return i == 7 ? this.srcPool.copy(i2, this.destPool, this.classnames) : i2;
        }

        @Override // javassist.bytecode.StackMapTable.SimpleCopy
        protected int[] copyData(int[] iArr, int[] iArr2) {
            int[] iArr3 = new int[iArr2.length];
            for (int i = 0; i < iArr2.length; i++) {
                if (iArr[i] == 7) {
                    iArr3[i] = this.srcPool.copy(iArr2[i], this.destPool, this.classnames);
                } else {
                    iArr3[i] = iArr2[i];
                }
            }
            return iArr3;
        }
    }

    public void insertLocal(int i, int i2, int i3) throws BadBytecode {
        set(new InsertLocal(get(), i, i2, i3).doit());
    }

    /* loaded from: classes2.dex */
    static class InsertLocal extends SimpleCopy {
        private int varData;
        private int varIndex;
        private int varTag;

        public InsertLocal(byte[] bArr, int i, int i2, int i3) {
            super(bArr);
            this.varIndex = i;
            this.varTag = i2;
            this.varData = i3;
        }

        @Override // javassist.bytecode.StackMapTable.SimpleCopy, javassist.bytecode.StackMapTable.Walker
        public void fullFrame(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
            int length = iArr.length;
            int i3 = this.varIndex;
            if (length < i3) {
                super.fullFrame(i, i2, iArr, iArr2, iArr3, iArr4);
                return;
            }
            int i4 = this.varTag;
            int i5 = (i4 == 4 || i4 == 3) ? 2 : 1;
            int i6 = length + i5;
            int[] iArr5 = new int[i6];
            int[] iArr6 = new int[i6];
            int i7 = 0;
            int i8 = 0;
            while (i7 < length) {
                if (i8 == i3) {
                    i8 += i5;
                }
                iArr5[i8] = iArr[i7];
                iArr6[i8] = iArr2[i7];
                i7++;
                i8++;
            }
            iArr5[i3] = this.varTag;
            iArr6[i3] = this.varData;
            if (i5 > 1) {
                int i9 = i3 + 1;
                iArr5[i9] = 0;
                iArr6[i9] = 0;
            }
            super.fullFrame(i, i2, iArr5, iArr6, iArr3, iArr4);
        }
    }

    /* loaded from: classes2.dex */
    public static class Writer {
        int numOfEntries;
        ByteArrayOutputStream output;

        public Writer(int i) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i);
            this.output = byteArrayOutputStream;
            this.numOfEntries = 0;
            byteArrayOutputStream.write(0);
            this.output.write(0);
        }

        public byte[] toByteArray() {
            byte[] byteArray = this.output.toByteArray();
            ByteArray.write16bit(this.numOfEntries, byteArray, 0);
            return byteArray;
        }

        public StackMapTable toStackMapTable(ConstPool constPool) {
            return new StackMapTable(constPool, toByteArray());
        }

        public void sameFrame(int i) {
            this.numOfEntries++;
            if (i < 64) {
                this.output.write(i);
                return;
            }
            this.output.write(251);
            write16(i);
        }

        public void sameLocals(int i, int i2, int i3) {
            this.numOfEntries++;
            if (i < 64) {
                this.output.write(i + 64);
            } else {
                this.output.write(247);
                write16(i);
            }
            writeTypeInfo(i2, i3);
        }

        public void chopFrame(int i, int i2) {
            this.numOfEntries++;
            this.output.write(251 - i2);
            write16(i);
        }

        public void appendFrame(int i, int[] iArr, int[] iArr2) {
            this.numOfEntries++;
            int length = iArr.length;
            this.output.write(length + 251);
            write16(i);
            for (int i2 = 0; i2 < length; i2++) {
                writeTypeInfo(iArr[i2], iArr2[i2]);
            }
        }

        public void fullFrame(int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
            this.numOfEntries++;
            this.output.write(255);
            write16(i);
            int length = iArr.length;
            write16(length);
            for (int i2 = 0; i2 < length; i2++) {
                writeTypeInfo(iArr[i2], iArr2[i2]);
            }
            int length2 = iArr3.length;
            write16(length2);
            for (int i3 = 0; i3 < length2; i3++) {
                writeTypeInfo(iArr3[i3], iArr4[i3]);
            }
        }

        private void writeTypeInfo(int i, int i2) {
            this.output.write(i);
            if (i == 7 || i == 8) {
                write16(i2);
            }
        }

        private void write16(int i) {
            this.output.write((i >>> 8) & 255);
            this.output.write(i & 255);
        }
    }

    public void println(PrintWriter printWriter) {
        Printer.print(this, printWriter);
    }

    public void println(PrintStream printStream) {
        Printer.print(this, new PrintWriter((OutputStream) printStream, true));
    }

    /* loaded from: classes2.dex */
    static class Printer extends Walker {
        private int offset;
        private PrintWriter writer;

        public static void print(StackMapTable stackMapTable, PrintWriter printWriter) {
            try {
                new Printer(stackMapTable.get(), printWriter).parse();
            } catch (BadBytecode e) {
                printWriter.println(e.getMessage());
            }
        }

        Printer(byte[] bArr, PrintWriter printWriter) {
            super(bArr);
            this.writer = printWriter;
            this.offset = -1;
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void sameFrame(int i, int i2) {
            this.offset += i2 + 1;
            this.writer.println(this.offset + " same frame: " + i2);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void sameLocals(int i, int i2, int i3, int i4) {
            this.offset += i2 + 1;
            this.writer.println(this.offset + " same locals: " + i2);
            printTypeInfo(i3, i4);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void chopFrame(int i, int i2, int i3) {
            this.offset += i2 + 1;
            this.writer.println(this.offset + " chop frame: " + i2 + ",    " + i3 + " last locals");
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void appendFrame(int i, int i2, int[] iArr, int[] iArr2) {
            this.offset += i2 + 1;
            this.writer.println(this.offset + " append frame: " + i2);
            for (int i3 = 0; i3 < iArr.length; i3++) {
                printTypeInfo(iArr[i3], iArr2[i3]);
            }
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void fullFrame(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
            this.offset += i2 + 1;
            this.writer.println(this.offset + " full frame: " + i2);
            this.writer.println("[locals]");
            for (int i3 = 0; i3 < iArr.length; i3++) {
                printTypeInfo(iArr[i3], iArr2[i3]);
            }
            this.writer.println("[stack]");
            for (int i4 = 0; i4 < iArr3.length; i4++) {
                printTypeInfo(iArr3[i4], iArr4[i4]);
            }
        }

        private void printTypeInfo(int i, int i2) {
            String str;
            switch (i) {
                case 0:
                    str = "top";
                    break;
                case 1:
                    str = TypedValues.Custom.S_INT;
                    break;
                case 2:
                    str = TypedValues.Custom.S_FLOAT;
                    break;
                case 3:
                    str = "double";
                    break;
                case 4:
                    str = "long";
                    break;
                case 5:
                    str = "null";
                    break;
                case 6:
                    str = "this";
                    break;
                case 7:
                    str = "object (cpool_index " + i2 + ")";
                    break;
                case 8:
                    str = "uninitialized (offset " + i2 + ")";
                    break;
                default:
                    str = null;
                    break;
            }
            this.writer.print("    ");
            this.writer.println(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftPc(int i, int i2, boolean z) throws BadBytecode {
        new OffsetShifter(this, i, i2).parse();
        new Shifter(this, i, i2, z).doit();
    }

    /* loaded from: classes2.dex */
    static class OffsetShifter extends Walker {
        int gap;
        int where;

        public OffsetShifter(StackMapTable stackMapTable, int i, int i2) {
            super(stackMapTable);
            this.where = i;
            this.gap = i2;
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void objectOrUninitialized(int i, int i2, int i3) {
            if (i != 8 || this.where > i2) {
                return;
            }
            ByteArray.write16bit(i2 + this.gap, this.info, i3);
        }
    }

    /* loaded from: classes2.dex */
    static class Shifter extends Walker {
        boolean exclusive;
        int gap;
        int position;
        private StackMapTable stackMap;
        byte[] updatedInfo;
        int where;

        public Shifter(StackMapTable stackMapTable, int i, int i2, boolean z) {
            super(stackMapTable);
            this.stackMap = stackMapTable;
            this.where = i;
            this.gap = i2;
            this.position = 0;
            this.updatedInfo = null;
            this.exclusive = z;
        }

        public void doit() throws BadBytecode {
            parse();
            byte[] bArr = this.updatedInfo;
            if (bArr != null) {
                this.stackMap.set(bArr);
            }
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void sameFrame(int i, int i2) {
            update(i, i2, 0, 251);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void sameLocals(int i, int i2, int i3, int i4) {
            update(i, i2, 64, 247);
        }

        void update(int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7 = this.position;
            boolean z = false;
            int i8 = i7 + i2 + (i7 == 0 ? 0 : 1);
            this.position = i8;
            if (!this.exclusive ? !(i7 > (i5 = this.where) || i5 >= i8) : !(i7 >= (i6 = this.where) || i6 > i8)) {
                z = true;
            }
            if (z) {
                int i9 = this.gap;
                int i10 = i2 + i9;
                this.position = i8 + i9;
                if (i10 < 64) {
                    this.info[i] = (byte) (i10 + i3);
                } else if (i2 < 64) {
                    byte[] insertGap = insertGap(this.info, i, 2);
                    insertGap[i] = (byte) i4;
                    ByteArray.write16bit(i10, insertGap, i + 1);
                    this.updatedInfo = insertGap;
                } else {
                    ByteArray.write16bit(i10, this.info, i + 1);
                }
            }
        }

        static byte[] insertGap(byte[] bArr, int i, int i2) {
            int length = bArr.length;
            byte[] bArr2 = new byte[length + i2];
            int i3 = 0;
            while (i3 < length) {
                bArr2[(i3 < i ? 0 : i2) + i3] = bArr[i3];
                i3++;
            }
            return bArr2;
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void chopFrame(int i, int i2, int i3) {
            update(i, i2);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void appendFrame(int i, int i2, int[] iArr, int[] iArr2) {
            update(i, i2);
        }

        @Override // javassist.bytecode.StackMapTable.Walker
        public void fullFrame(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
            update(i, i2);
        }

        void update(int i, int i2) {
            int i3;
            int i4;
            int i5 = this.position;
            boolean z = false;
            int i6 = i5 + i2 + (i5 == 0 ? 0 : 1);
            this.position = i6;
            if (!this.exclusive ? !(i5 > (i3 = this.where) || i3 >= i6) : !(i5 >= (i4 = this.where) || i4 > i6)) {
                z = true;
            }
            if (z) {
                ByteArray.write16bit(i2 + this.gap, this.info, i + 1);
                this.position += this.gap;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shiftForSwitch(int i, int i2) throws BadBytecode {
        new SwitchShifter(this, i, i2).doit();
    }

    /* loaded from: classes2.dex */
    static class SwitchShifter extends Shifter {
        SwitchShifter(StackMapTable stackMapTable, int i, int i2) {
            super(stackMapTable, i, i2, false);
        }

        @Override // javassist.bytecode.StackMapTable.Shifter
        void update(int i, int i2, int i3, int i4) {
            int i5;
            int i6 = this.position;
            this.position = i6 + i2 + (i6 == 0 ? 0 : 1);
            if (this.where == this.position) {
                i5 = i2 - this.gap;
            } else if (this.where != i6) {
                return;
            } else {
                i5 = this.gap + i2;
            }
            if (i2 >= 64) {
                if (i5 < 64) {
                    byte[] deleteGap = deleteGap(this.info, i, 2);
                    deleteGap[i] = (byte) (i5 + i3);
                    this.updatedInfo = deleteGap;
                    return;
                }
                ByteArray.write16bit(i5, this.info, i + 1);
            } else if (i5 < 64) {
                this.info[i] = (byte) (i5 + i3);
            } else {
                byte[] insertGap = insertGap(this.info, i, 2);
                insertGap[i] = (byte) i4;
                ByteArray.write16bit(i5, insertGap, i + 1);
                this.updatedInfo = insertGap;
            }
        }

        static byte[] deleteGap(byte[] bArr, int i, int i2) {
            int i3 = i + i2;
            int length = bArr.length;
            byte[] bArr2 = new byte[length - i2];
            int i4 = 0;
            while (i4 < length) {
                bArr2[i4 - (i4 < i3 ? 0 : i2)] = bArr[i4];
                i4++;
            }
            return bArr2;
        }

        @Override // javassist.bytecode.StackMapTable.Shifter
        void update(int i, int i2) {
            int i3;
            int i4 = this.position;
            this.position = i4 + i2 + (i4 == 0 ? 0 : 1);
            if (this.where == this.position) {
                i3 = i2 - this.gap;
            } else if (this.where != i4) {
                return;
            } else {
                i3 = i2 + this.gap;
            }
            ByteArray.write16bit(i3, this.info, i + 1);
        }
    }

    public void removeNew(int i) throws CannotCompileException {
        try {
            set(new NewRemover(get(), i).doit());
        } catch (BadBytecode e) {
            throw new CannotCompileException("bad stack map table", e);
        }
    }

    /* loaded from: classes2.dex */
    static class NewRemover extends SimpleCopy {
        int posOfNew;

        public NewRemover(byte[] bArr, int i) {
            super(bArr);
            this.posOfNew = i;
        }

        @Override // javassist.bytecode.StackMapTable.SimpleCopy, javassist.bytecode.StackMapTable.Walker
        public void sameLocals(int i, int i2, int i3, int i4) {
            if (i3 == 8 && i4 == this.posOfNew) {
                super.sameFrame(i, i2);
            } else {
                super.sameLocals(i, i2, i3, i4);
            }
        }

        @Override // javassist.bytecode.StackMapTable.SimpleCopy, javassist.bytecode.StackMapTable.Walker
        public void fullFrame(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
            int[] iArr5;
            int[] iArr6;
            int length = iArr3.length - 1;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    iArr5 = iArr4;
                    iArr6 = iArr3;
                    break;
                }
                if (iArr3[i4] == 8) {
                    int i5 = iArr4[i4];
                    int i6 = this.posOfNew;
                    if (i5 == i6) {
                        int i7 = i4 + 1;
                        if (iArr3[i7] == 8 && iArr4[i7] == i6) {
                            int i8 = length + 1;
                            int i9 = i8 - 2;
                            int[] iArr7 = new int[i9];
                            int[] iArr8 = new int[i9];
                            int i10 = 0;
                            while (i3 < i8) {
                                if (i3 == i4) {
                                    i3++;
                                } else {
                                    iArr7[i10] = iArr3[i3];
                                    iArr8[i10] = iArr4[i3];
                                    i10++;
                                }
                                i3++;
                            }
                            iArr5 = iArr8;
                            iArr6 = iArr7;
                        }
                    } else {
                        continue;
                    }
                }
                i4++;
            }
            super.fullFrame(i, i2, iArr, iArr2, iArr6, iArr5);
        }
    }
}
