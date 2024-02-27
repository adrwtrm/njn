package javassist.bytecode;

import java.util.ArrayList;
import java.util.List;
import javassist.bytecode.CodeAttribute;

/* loaded from: classes2.dex */
public class CodeIterator implements Opcode {
    private static final int[] opcodeLength = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 0, 0, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 5, 5, 3, 2, 3, 1, 1, 3, 3, 1, 1, 0, 4, 3, 3, 5, 5};
    protected byte[] bytecode;
    protected CodeAttribute codeAttr;
    protected int currentPos;
    protected int endPos;
    protected int mark;
    protected int mark2;

    /* loaded from: classes2.dex */
    public static class Gap {
        public int length;
        public int position;
    }

    private static int newOffset(int i, int i2, int i3, int i4, boolean z) {
        int i5 = i + i2;
        if (i < i3) {
            return (i3 < i5 || (z && i3 == i5)) ? i2 + i4 : i2;
        }
        if (i == i3) {
            if (i5 >= i3) {
                return i2;
            }
        } else if (i5 >= i3 && (z || i3 != i5)) {
            return i2;
        }
        return i2 - i4;
    }

    protected void updateCursors(int i, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CodeIterator(CodeAttribute codeAttribute) {
        this.codeAttr = codeAttribute;
        this.bytecode = codeAttribute.getCode();
        begin();
    }

    public void begin() {
        this.mark2 = 0;
        this.mark = 0;
        this.currentPos = 0;
        this.endPos = getCodeLength();
    }

    public void move(int i) {
        this.currentPos = i;
    }

    public void setMark(int i) {
        this.mark = i;
    }

    public void setMark2(int i) {
        this.mark2 = i;
    }

    public int getMark() {
        return this.mark;
    }

    public int getMark2() {
        return this.mark2;
    }

    public CodeAttribute get() {
        return this.codeAttr;
    }

    public int getCodeLength() {
        return this.bytecode.length;
    }

    public int byteAt(int i) {
        return this.bytecode[i] & 255;
    }

    public int signedByteAt(int i) {
        return this.bytecode[i];
    }

    public void writeByte(int i, int i2) {
        this.bytecode[i2] = (byte) i;
    }

    public int u16bitAt(int i) {
        return ByteArray.readU16bit(this.bytecode, i);
    }

    public int s16bitAt(int i) {
        return ByteArray.readS16bit(this.bytecode, i);
    }

    public void write16bit(int i, int i2) {
        ByteArray.write16bit(i, this.bytecode, i2);
    }

    public int s32bitAt(int i) {
        return ByteArray.read32bit(this.bytecode, i);
    }

    public void write32bit(int i, int i2) {
        ByteArray.write32bit(i, this.bytecode, i2);
    }

    public void write(byte[] bArr, int i) {
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            this.bytecode[i] = bArr[i2];
            i2++;
            i++;
        }
    }

    public boolean hasNext() {
        return this.currentPos < this.endPos;
    }

    public int next() throws BadBytecode {
        int i = this.currentPos;
        this.currentPos = nextOpcode(this.bytecode, i);
        return i;
    }

    public int lookAhead() {
        return this.currentPos;
    }

    public int skipConstructor() throws BadBytecode {
        return skipSuperConstructor0(-1);
    }

    public int skipSuperConstructor() throws BadBytecode {
        return skipSuperConstructor0(0);
    }

    public int skipThisConstructor() throws BadBytecode {
        return skipSuperConstructor0(1);
    }

    private int skipSuperConstructor0(int i) throws BadBytecode {
        begin();
        ConstPool constPool = this.codeAttr.getConstPool();
        String declaringClass = this.codeAttr.getDeclaringClass();
        int i2 = 0;
        while (true) {
            if (!hasNext()) {
                break;
            }
            int next = next();
            int byteAt = byteAt(next);
            if (byteAt == 187) {
                i2++;
            } else if (byteAt == 183) {
                int readU16bit = ByteArray.readU16bit(this.bytecode, next + 1);
                if (constPool.getMethodrefName(readU16bit).equals("<init>") && i2 - 1 < 0) {
                    if (i < 0) {
                        return next;
                    }
                    if (constPool.getMethodrefClassName(readU16bit).equals(declaringClass) == (i > 0)) {
                        return next;
                    }
                }
            } else {
                continue;
            }
        }
        begin();
        return -1;
    }

    public int insert(byte[] bArr) throws BadBytecode {
        return insert0(this.currentPos, bArr, false);
    }

    public void insert(int i, byte[] bArr) throws BadBytecode {
        insert0(i, bArr, false);
    }

    public int insertAt(int i, byte[] bArr) throws BadBytecode {
        return insert0(i, bArr, false);
    }

    public int insertEx(byte[] bArr) throws BadBytecode {
        return insert0(this.currentPos, bArr, true);
    }

    public void insertEx(int i, byte[] bArr) throws BadBytecode {
        insert0(i, bArr, true);
    }

    public int insertExAt(int i, byte[] bArr) throws BadBytecode {
        return insert0(i, bArr, true);
    }

    private int insert0(int i, byte[] bArr, boolean z) throws BadBytecode {
        int length = bArr.length;
        if (length <= 0) {
            return i;
        }
        int i2 = insertGapAt(i, length, z).position;
        int i3 = 0;
        int i4 = i2;
        while (i3 < length) {
            this.bytecode[i4] = bArr[i3];
            i3++;
            i4++;
        }
        return i2;
    }

    public int insertGap(int i) throws BadBytecode {
        return insertGapAt(this.currentPos, i, false).position;
    }

    public int insertGap(int i, int i2) throws BadBytecode {
        return insertGapAt(i, i2, false).length;
    }

    public int insertExGap(int i) throws BadBytecode {
        return insertGapAt(this.currentPos, i, true).position;
    }

    public int insertExGap(int i, int i2) throws BadBytecode {
        return insertGapAt(i, i2, true).length;
    }

    public Gap insertGapAt(int i, int i2, boolean z) throws BadBytecode {
        int i3;
        byte[] bArr;
        Gap gap = new Gap();
        if (i2 <= 0) {
            gap.position = i;
            gap.length = 0;
            return gap;
        }
        byte[] bArr2 = this.bytecode;
        if (bArr2.length + i2 > 32767) {
            bArr = insertGapCore0w(bArr2, i, i2, z, get().getExceptionTable(), this.codeAttr, gap);
            i3 = gap.position;
        } else {
            int i4 = this.currentPos;
            byte[] insertGapCore0 = insertGapCore0(bArr2, i, i2, z, get().getExceptionTable(), this.codeAttr);
            int length = insertGapCore0.length - this.bytecode.length;
            gap.position = i;
            gap.length = length;
            if (i4 >= i) {
                this.currentPos = i4 + length;
            }
            int i5 = this.mark;
            if (i5 > i || (i5 == i && z)) {
                this.mark = i5 + length;
            }
            int i6 = this.mark2;
            if (i6 > i || (i6 == i && z)) {
                this.mark2 = i6 + length;
            }
            i3 = i;
            bArr = insertGapCore0;
            i2 = length;
        }
        this.codeAttr.setCode(bArr);
        this.bytecode = bArr;
        this.endPos = getCodeLength();
        updateCursors(i3, i2);
        return gap;
    }

    public void insert(ExceptionTable exceptionTable, int i) {
        this.codeAttr.getExceptionTable().add(0, exceptionTable, i);
    }

    public int append(byte[] bArr) {
        int codeLength = getCodeLength();
        int length = bArr.length;
        if (length <= 0) {
            return codeLength;
        }
        appendGap(length);
        byte[] bArr2 = this.bytecode;
        for (int i = 0; i < length; i++) {
            bArr2[i + codeLength] = bArr[i];
        }
        return codeLength;
    }

    public void appendGap(int i) {
        byte[] bArr = this.bytecode;
        int length = bArr.length;
        int i2 = i + length;
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < length; i3++) {
            bArr2[i3] = bArr[i3];
        }
        while (length < i2) {
            bArr2[length] = 0;
            length++;
        }
        this.codeAttr.setCode(bArr2);
        this.bytecode = bArr2;
        this.endPos = getCodeLength();
    }

    public void append(ExceptionTable exceptionTable, int i) {
        ExceptionTable exceptionTable2 = this.codeAttr.getExceptionTable();
        exceptionTable2.add(exceptionTable2.size(), exceptionTable, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int nextOpcode(byte[] bArr, int i) throws BadBytecode {
        int i2;
        try {
            int i3 = bArr[i] & 255;
            try {
                i2 = opcodeLength[i3];
            } catch (IndexOutOfBoundsException unused) {
            }
            if (i2 > 0) {
                return i + i2;
            }
            if (i3 == 196) {
                return bArr[i + 1] == -124 ? i + 6 : i + 4;
            }
            int i4 = (i & (-4)) + 8;
            if (i3 == 171) {
                return i4 + (ByteArray.read32bit(bArr, i4) * 8) + 4;
            }
            if (i3 == 170) {
                return i4 + (((ByteArray.read32bit(bArr, i4 + 4) - ByteArray.read32bit(bArr, i4)) + 1) * 4) + 8;
            }
            throw new BadBytecode(i3);
        } catch (IndexOutOfBoundsException unused2) {
            throw new BadBytecode("invalid opcode address");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class AlignmentException extends Exception {
        private static final long serialVersionUID = 1;

        AlignmentException() {
        }
    }

    static byte[] insertGapCore0(byte[] bArr, int i, int i2, boolean z, ExceptionTable exceptionTable, CodeAttribute codeAttribute) throws BadBytecode {
        if (i2 <= 0) {
            return bArr;
        }
        try {
            return insertGapCore1(bArr, i, i2, z, exceptionTable, codeAttribute);
        } catch (AlignmentException unused) {
            try {
                return insertGapCore1(bArr, i, (i2 + 3) & (-4), z, exceptionTable, codeAttribute);
            } catch (AlignmentException unused2) {
                throw new RuntimeException("fatal error?");
            }
        }
    }

    private static byte[] insertGapCore1(byte[] bArr, int i, int i2, boolean z, ExceptionTable exceptionTable, CodeAttribute codeAttribute) throws BadBytecode, AlignmentException {
        int length = bArr.length;
        byte[] bArr2 = new byte[length + i2];
        insertGap2(bArr, i, i2, length, bArr2, z);
        exceptionTable.shiftPc(i, i2, z);
        LineNumberAttribute lineNumberAttribute = (LineNumberAttribute) codeAttribute.getAttribute(LineNumberAttribute.tag);
        if (lineNumberAttribute != null) {
            lineNumberAttribute.shiftPc(i, i2, z);
        }
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (localVariableAttribute != null) {
            localVariableAttribute.shiftPc(i, i2, z);
        }
        LocalVariableAttribute localVariableAttribute2 = (LocalVariableAttribute) codeAttribute.getAttribute("LocalVariableTypeTable");
        if (localVariableAttribute2 != null) {
            localVariableAttribute2.shiftPc(i, i2, z);
        }
        StackMapTable stackMapTable = (StackMapTable) codeAttribute.getAttribute(StackMapTable.tag);
        if (stackMapTable != null) {
            stackMapTable.shiftPc(i, i2, z);
        }
        StackMap stackMap = (StackMap) codeAttribute.getAttribute(StackMap.tag);
        if (stackMap != null) {
            stackMap.shiftPc(i, i2, z);
        }
        return bArr2;
    }

    private static void insertGap2(byte[] bArr, int i, int i2, int i3, byte[] bArr2, boolean z) throws BadBytecode, AlignmentException {
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            if (i4 == i) {
                int i6 = i5 + i2;
                while (i5 < i6) {
                    bArr2[i5] = 0;
                    i5++;
                }
            }
            int nextOpcode = nextOpcode(bArr, i4);
            int i7 = bArr[i4] & 255;
            if ((153 <= i7 && i7 <= 168) || i7 == 198 || i7 == 199) {
                int newOffset = newOffset(i4, (bArr[i4 + 1] << 8) | (bArr[i4 + 2] & 255), i, i2, z);
                bArr2[i5] = bArr[i4];
                ByteArray.write16bit(newOffset, bArr2, i5 + 1);
                i5 += 3;
            } else if (i7 == 200 || i7 == 201) {
                int newOffset2 = newOffset(i4, ByteArray.read32bit(bArr, i4 + 1), i, i2, z);
                int i8 = i5 + 1;
                bArr2[i5] = bArr[i4];
                ByteArray.write32bit(newOffset2, bArr2, i8);
                i5 = i8 + 4;
            } else if (i7 == 170) {
                if (i4 != i5 && (i2 & 3) != 0) {
                    throw new AlignmentException();
                }
                int i9 = (i4 & (-4)) + 4;
                int copyGapBytes = copyGapBytes(bArr2, i5, bArr, i4, i9);
                ByteArray.write32bit(newOffset(i4, ByteArray.read32bit(bArr, i9), i, i2, z), bArr2, copyGapBytes);
                int read32bit = ByteArray.read32bit(bArr, i9 + 4);
                ByteArray.write32bit(read32bit, bArr2, copyGapBytes + 4);
                int read32bit2 = ByteArray.read32bit(bArr, i9 + 8);
                ByteArray.write32bit(read32bit2, bArr2, copyGapBytes + 8);
                i5 = copyGapBytes + 12;
                int i10 = i9 + 12;
                int i11 = (((read32bit2 - read32bit) + 1) * 4) + i10;
                while (i10 < i11) {
                    ByteArray.write32bit(newOffset(i4, ByteArray.read32bit(bArr, i10), i, i2, z), bArr2, i5);
                    i5 += 4;
                    i10 += 4;
                }
            } else if (i7 != 171) {
                while (i4 < nextOpcode) {
                    bArr2[i5] = bArr[i4];
                    i5++;
                    i4++;
                }
            } else if (i4 != i5 && (i2 & 3) != 0) {
                throw new AlignmentException();
            } else {
                int i12 = (i4 & (-4)) + 4;
                int copyGapBytes2 = copyGapBytes(bArr2, i5, bArr, i4, i12);
                ByteArray.write32bit(newOffset(i4, ByteArray.read32bit(bArr, i12), i, i2, z), bArr2, copyGapBytes2);
                int read32bit3 = ByteArray.read32bit(bArr, i12 + 4);
                ByteArray.write32bit(read32bit3, bArr2, copyGapBytes2 + 4);
                i5 = copyGapBytes2 + 8;
                int i13 = i12 + 8;
                int i14 = (read32bit3 * 8) + i13;
                while (i13 < i14) {
                    ByteArray.copy32bit(bArr, i13, bArr2, i5);
                    ByteArray.write32bit(newOffset(i4, ByteArray.read32bit(bArr, i13 + 4), i, i2, z), bArr2, i5 + 4);
                    i5 += 8;
                    i13 += 8;
                }
            }
            i4 = nextOpcode;
        }
    }

    private static int copyGapBytes(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i3 - i2;
        if (i4 != 1) {
            if (i4 != 2) {
                if (i4 != 3) {
                    if (i4 != 4) {
                        return i;
                    }
                    bArr[i] = bArr2[i2];
                    i++;
                    i2++;
                }
                bArr[i] = bArr2[i2];
                i++;
                i2++;
            }
            bArr[i] = bArr2[i2];
            i++;
            i2++;
        }
        int i5 = i + 1;
        bArr[i] = bArr2[i2];
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Pointers {
        int cursor;
        ExceptionTable etable;
        LineNumberAttribute line;
        int mark;
        int mark0;
        int mark2;
        StackMapTable stack;
        StackMap stack2;
        LocalVariableAttribute types;
        LocalVariableAttribute vars;

        Pointers(int i, int i2, int i3, int i4, ExceptionTable exceptionTable, CodeAttribute codeAttribute) {
            this.cursor = i;
            this.mark = i2;
            this.mark2 = i3;
            this.mark0 = i4;
            this.etable = exceptionTable;
            this.line = (LineNumberAttribute) codeAttribute.getAttribute(LineNumberAttribute.tag);
            this.vars = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            this.types = (LocalVariableAttribute) codeAttribute.getAttribute("LocalVariableTypeTable");
            this.stack = (StackMapTable) codeAttribute.getAttribute(StackMapTable.tag);
            this.stack2 = (StackMap) codeAttribute.getAttribute(StackMap.tag);
        }

        void shiftPc(int i, int i2, boolean z) throws BadBytecode {
            int i3 = this.cursor;
            if (i < i3 || (i == i3 && z)) {
                this.cursor = i3 + i2;
            }
            int i4 = this.mark;
            if (i < i4 || (i == i4 && z)) {
                this.mark = i4 + i2;
            }
            int i5 = this.mark2;
            if (i < i5 || (i == i5 && z)) {
                this.mark2 = i5 + i2;
            }
            int i6 = this.mark0;
            if (i < i6 || (i == i6 && z)) {
                this.mark0 = i6 + i2;
            }
            this.etable.shiftPc(i, i2, z);
            LineNumberAttribute lineNumberAttribute = this.line;
            if (lineNumberAttribute != null) {
                lineNumberAttribute.shiftPc(i, i2, z);
            }
            LocalVariableAttribute localVariableAttribute = this.vars;
            if (localVariableAttribute != null) {
                localVariableAttribute.shiftPc(i, i2, z);
            }
            LocalVariableAttribute localVariableAttribute2 = this.types;
            if (localVariableAttribute2 != null) {
                localVariableAttribute2.shiftPc(i, i2, z);
            }
            StackMapTable stackMapTable = this.stack;
            if (stackMapTable != null) {
                stackMapTable.shiftPc(i, i2, z);
            }
            StackMap stackMap = this.stack2;
            if (stackMap != null) {
                stackMap.shiftPc(i, i2, z);
            }
        }

        void shiftForSwitch(int i, int i2) throws BadBytecode {
            StackMapTable stackMapTable = this.stack;
            if (stackMapTable != null) {
                stackMapTable.shiftForSwitch(i, i2);
            }
            StackMap stackMap = this.stack2;
            if (stackMap != null) {
                stackMap.shiftForSwitch(i, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] changeLdcToLdcW(byte[] bArr, ExceptionTable exceptionTable, CodeAttribute codeAttribute, CodeAttribute.LdcEntry ldcEntry) throws BadBytecode {
        Pointers pointers = new Pointers(0, 0, 0, 0, exceptionTable, codeAttribute);
        List<Branch> makeJumpList = makeJumpList(bArr, bArr.length, pointers);
        while (ldcEntry != null) {
            addLdcW(ldcEntry, makeJumpList);
            ldcEntry = ldcEntry.next;
        }
        return insertGap2w(bArr, 0, 0, false, makeJumpList, pointers);
    }

    private static void addLdcW(CodeAttribute.LdcEntry ldcEntry, List<Branch> list) {
        int i = ldcEntry.where;
        LdcW ldcW = new LdcW(i, ldcEntry.index);
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i < list.get(i2).orgPos) {
                list.add(i2, ldcW);
                return;
            }
        }
        list.add(ldcW);
    }

    private byte[] insertGapCore0w(byte[] bArr, int i, int i2, boolean z, ExceptionTable exceptionTable, CodeAttribute codeAttribute, Gap gap) throws BadBytecode {
        if (i2 <= 0) {
            return bArr;
        }
        Pointers pointers = new Pointers(this.currentPos, this.mark, this.mark2, i, exceptionTable, codeAttribute);
        byte[] insertGap2w = insertGap2w(bArr, i, i2, z, makeJumpList(bArr, bArr.length, pointers), pointers);
        this.currentPos = pointers.cursor;
        this.mark = pointers.mark;
        this.mark2 = pointers.mark2;
        int i3 = pointers.mark0;
        int i4 = this.currentPos;
        if (i3 == i4 && !z) {
            this.currentPos = i4 + i2;
        }
        if (z) {
            i3 -= i2;
        }
        gap.position = i3;
        gap.length = i2;
        return insertGap2w;
    }

    private static byte[] insertGap2w(byte[] bArr, int i, int i2, boolean z, List<Branch> list, Pointers pointers) throws BadBytecode {
        if (i2 > 0) {
            pointers.shiftPc(i, i2, z);
            for (Branch branch : list) {
                branch.shift(i, i2, z);
            }
        }
        boolean z2 = true;
        while (true) {
            if (z2) {
                boolean z3 = false;
                for (Branch branch2 : list) {
                    if (branch2.expanded()) {
                        int i3 = branch2.pos;
                        int deltaSize = branch2.deltaSize();
                        pointers.shiftPc(i3, deltaSize, false);
                        for (Branch branch3 : list) {
                            branch3.shift(i3, deltaSize, false);
                        }
                        z3 = true;
                    }
                }
                z2 = z3;
            } else {
                for (Branch branch4 : list) {
                    int gapChanged = branch4.gapChanged();
                    if (gapChanged > 0) {
                        int i4 = branch4.pos;
                        pointers.shiftPc(i4, gapChanged, false);
                        for (Branch branch5 : list) {
                            branch5.shift(i4, gapChanged, false);
                        }
                        z2 = true;
                    }
                }
                if (!z2) {
                    return makeExapndedCode(bArr, list, i, i2);
                }
            }
        }
    }

    private static List<Branch> makeJumpList(byte[] bArr, int i, Pointers pointers) throws BadBytecode {
        Branch16 jump16;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < i) {
            int nextOpcode = nextOpcode(bArr, i2);
            int i3 = bArr[i2] & 255;
            if ((153 <= i3 && i3 <= 168) || i3 == 198 || i3 == 199) {
                int i4 = (bArr[i2 + 1] << 8) | (bArr[i2 + 2] & 255);
                if (i3 == 167 || i3 == 168) {
                    jump16 = new Jump16(i2, i4);
                } else {
                    jump16 = new If16(i2, i4);
                }
                arrayList.add(jump16);
            } else if (i3 == 200 || i3 == 201) {
                arrayList.add(new Jump32(i2, ByteArray.read32bit(bArr, i2 + 1)));
            } else if (i3 == 170) {
                int i5 = (i2 & (-4)) + 4;
                int read32bit = ByteArray.read32bit(bArr, i5);
                int read32bit2 = ByteArray.read32bit(bArr, i5 + 4);
                int read32bit3 = ByteArray.read32bit(bArr, i5 + 8);
                int i6 = i5 + 12;
                int i7 = (read32bit3 - read32bit2) + 1;
                int[] iArr = new int[i7];
                for (int i8 = 0; i8 < i7; i8++) {
                    iArr[i8] = ByteArray.read32bit(bArr, i6);
                    i6 += 4;
                }
                arrayList.add(new Table(i2, read32bit, read32bit2, read32bit3, iArr, pointers));
            } else if (i3 == 171) {
                int i9 = (i2 & (-4)) + 4;
                int read32bit4 = ByteArray.read32bit(bArr, i9);
                int read32bit5 = ByteArray.read32bit(bArr, i9 + 4);
                int i10 = i9 + 8;
                int[] iArr2 = new int[read32bit5];
                int[] iArr3 = new int[read32bit5];
                for (int i11 = 0; i11 < read32bit5; i11++) {
                    iArr2[i11] = ByteArray.read32bit(bArr, i10);
                    iArr3[i11] = ByteArray.read32bit(bArr, i10 + 4);
                    i10 += 8;
                }
                arrayList.add(new Lookup(i2, read32bit4, iArr2, iArr3, pointers));
            }
            i2 = nextOpcode;
        }
        return arrayList;
    }

    private static byte[] makeExapndedCode(byte[] bArr, List<Branch> list, int i, int i2) throws BadBytecode {
        int i3;
        Branch branch;
        int size = list.size();
        int length = bArr.length + i2;
        for (Branch branch2 : list) {
            length += branch2.deltaSize();
        }
        byte[] bArr2 = new byte[length];
        int length2 = bArr.length;
        if (size > 0) {
            branch = list.get(0);
            i3 = branch.orgPos;
        } else {
            i3 = length2;
            branch = null;
        }
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < length2) {
            if (i4 == i) {
                int i7 = i5 + i2;
                while (i5 < i7) {
                    bArr2[i5] = 0;
                    i5++;
                }
            }
            if (i4 != i3) {
                bArr2[i5] = bArr[i4];
                i5++;
                i4++;
            } else {
                int write = branch.write(i4, bArr, i5, bArr2);
                i4 += write;
                i5 += write + branch.deltaSize();
                i6++;
                if (i6 < size) {
                    branch = list.get(i6);
                    i3 = branch.orgPos;
                } else {
                    i3 = length2;
                    branch = null;
                }
            }
        }
        return bArr2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class Branch {
        int orgPos;
        int pos;

        static int shiftOffset(int i, int i2, int i3, int i4, boolean z) {
            int i5 = i + i2;
            if (i >= i3) {
                if (i == i3) {
                    if (i5 >= i3 || !z) {
                        if (i3 >= i5 || z) {
                            return i2;
                        }
                    }
                } else if (i5 >= i3 && (z || i3 != i5)) {
                    return i2;
                }
                return i2 - i4;
            } else if (i3 >= i5 && (!z || i3 != i5)) {
                return i2;
            }
            return i2 + i4;
        }

        int deltaSize() {
            return 0;
        }

        boolean expanded() {
            return false;
        }

        int gapChanged() {
            return 0;
        }

        abstract int write(int i, byte[] bArr, int i2, byte[] bArr2) throws BadBytecode;

        Branch(int i) {
            this.orgPos = i;
            this.pos = i;
        }

        void shift(int i, int i2, boolean z) {
            int i3 = this.pos;
            if (i < i3 || (i == i3 && z)) {
                this.pos = i3 + i2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class LdcW extends Branch {
        int index;
        boolean state;

        @Override // javassist.bytecode.CodeIterator.Branch
        int deltaSize() {
            return 1;
        }

        LdcW(int i, int i2) {
            super(i);
            this.index = i2;
            this.state = true;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        boolean expanded() {
            if (this.state) {
                this.state = false;
                return true;
            }
            return false;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        int write(int i, byte[] bArr, int i2, byte[] bArr2) {
            bArr2[i2] = 19;
            ByteArray.write16bit(this.index, bArr2, i2 + 1);
            return 2;
        }
    }

    /* loaded from: classes2.dex */
    static abstract class Branch16 extends Branch {
        static final int BIT16 = 0;
        static final int BIT32 = 2;
        static final int EXPAND = 1;
        int offset;
        int state;

        @Override // javassist.bytecode.CodeIterator.Branch
        abstract int deltaSize();

        abstract void write32(int i, byte[] bArr, int i2, byte[] bArr2);

        Branch16(int i, int i2) {
            super(i);
            this.offset = i2;
            this.state = 0;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        void shift(int i, int i2, boolean z) {
            this.offset = shiftOffset(this.pos, this.offset, i, i2, z);
            super.shift(i, i2, z);
            if (this.state == 0) {
                int i3 = this.offset;
                if (i3 < -32768 || 32767 < i3) {
                    this.state = 1;
                }
            }
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        boolean expanded() {
            if (this.state == 1) {
                this.state = 2;
                return true;
            }
            return false;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        int write(int i, byte[] bArr, int i2, byte[] bArr2) {
            if (this.state == 2) {
                write32(i, bArr, i2, bArr2);
                return 3;
            }
            bArr2[i2] = bArr[i];
            ByteArray.write16bit(this.offset, bArr2, i2 + 1);
            return 3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Jump16 extends Branch16 {
        Jump16(int i, int i2) {
            super(i, i2);
        }

        @Override // javassist.bytecode.CodeIterator.Branch16, javassist.bytecode.CodeIterator.Branch
        int deltaSize() {
            return this.state == 2 ? 2 : 0;
        }

        @Override // javassist.bytecode.CodeIterator.Branch16
        void write32(int i, byte[] bArr, int i2, byte[] bArr2) {
            bArr2[i2] = (byte) ((bArr[i] & 255) == 167 ? 200 : Opcode.JSR_W);
            ByteArray.write32bit(this.offset, bArr2, i2 + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class If16 extends Branch16 {
        int opcode(int i) {
            if (i == 198) {
                return 199;
            }
            if (i == 199) {
                return 198;
            }
            return ((i + (-153)) & 1) == 0 ? i + 1 : i - 1;
        }

        If16(int i, int i2) {
            super(i, i2);
        }

        @Override // javassist.bytecode.CodeIterator.Branch16, javassist.bytecode.CodeIterator.Branch
        int deltaSize() {
            return this.state == 2 ? 5 : 0;
        }

        @Override // javassist.bytecode.CodeIterator.Branch16
        void write32(int i, byte[] bArr, int i2, byte[] bArr2) {
            bArr2[i2] = (byte) opcode(bArr[i] & 255);
            bArr2[i2 + 1] = 0;
            bArr2[i2 + 2] = 8;
            bArr2[i2 + 3] = -56;
            ByteArray.write32bit(this.offset - 3, bArr2, i2 + 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Jump32 extends Branch {
        int offset;

        Jump32(int i, int i2) {
            super(i);
            this.offset = i2;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        void shift(int i, int i2, boolean z) {
            this.offset = shiftOffset(this.pos, this.offset, i, i2, z);
            super.shift(i, i2, z);
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        int write(int i, byte[] bArr, int i2, byte[] bArr2) {
            bArr2[i2] = bArr[i];
            ByteArray.write32bit(this.offset, bArr2, i2 + 1);
            return 5;
        }
    }

    /* loaded from: classes2.dex */
    static abstract class Switcher extends Branch {
        int defaultByte;
        int gap;
        int[] offsets;
        Pointers pointers;

        abstract int tableSize();

        abstract int write2(int i, byte[] bArr);

        Switcher(int i, int i2, int[] iArr, Pointers pointers) {
            super(i);
            this.gap = 3 - (i & 3);
            this.defaultByte = i2;
            this.offsets = iArr;
            this.pointers = pointers;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        void shift(int i, int i2, boolean z) {
            int i3 = this.pos;
            this.defaultByte = shiftOffset(i3, this.defaultByte, i, i2, z);
            int length = this.offsets.length;
            for (int i4 = 0; i4 < length; i4++) {
                int[] iArr = this.offsets;
                iArr[i4] = shiftOffset(i3, iArr[i4], i, i2, z);
            }
            super.shift(i, i2, z);
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        int gapChanged() {
            int i = 3 - (this.pos & 3);
            int i2 = this.gap;
            if (i > i2) {
                int i3 = i - i2;
                this.gap = i;
                return i3;
            }
            return 0;
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        int deltaSize() {
            return this.gap - (3 - (this.orgPos & 3));
        }

        @Override // javassist.bytecode.CodeIterator.Branch
        int write(int i, byte[] bArr, int i2, byte[] bArr2) throws BadBytecode {
            int i3 = 3 - (this.pos & 3);
            int i4 = this.gap - i3;
            int tableSize = (3 - (this.orgPos & 3)) + 5 + tableSize();
            if (i4 > 0) {
                adjustOffsets(tableSize, i4);
            }
            int i5 = i2 + 1;
            bArr2[i2] = bArr[i];
            while (true) {
                int i6 = i3 - 1;
                if (i3 <= 0) {
                    break;
                }
                bArr2[i5] = 0;
                i3 = i6;
                i5++;
            }
            ByteArray.write32bit(this.defaultByte, bArr2, i5);
            int write2 = write2(i5 + 4, bArr2);
            int i7 = i5 + write2 + 4;
            while (true) {
                int i8 = i4 - 1;
                if (i4 > 0) {
                    bArr2[i7] = 0;
                    i4 = i8;
                    i7++;
                } else {
                    return (3 - (this.orgPos & 3)) + 5 + write2;
                }
            }
        }

        void adjustOffsets(int i, int i2) throws BadBytecode {
            this.pointers.shiftForSwitch(this.pos + i, i2);
            int i3 = this.defaultByte;
            if (i3 == i) {
                this.defaultByte = i3 - i2;
            }
            int i4 = 0;
            while (true) {
                int[] iArr = this.offsets;
                if (i4 >= iArr.length) {
                    return;
                }
                int i5 = iArr[i4];
                if (i5 == i) {
                    iArr[i4] = i5 - i2;
                }
                i4++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Table extends Switcher {
        int high;
        int low;

        Table(int i, int i2, int i3, int i4, int[] iArr, Pointers pointers) {
            super(i, i2, iArr, pointers);
            this.low = i3;
            this.high = i4;
        }

        @Override // javassist.bytecode.CodeIterator.Switcher
        int write2(int i, byte[] bArr) {
            ByteArray.write32bit(this.low, bArr, i);
            ByteArray.write32bit(this.high, bArr, i + 4);
            int length = this.offsets.length;
            int i2 = i + 8;
            for (int i3 = 0; i3 < length; i3++) {
                ByteArray.write32bit(this.offsets[i3], bArr, i2);
                i2 += 4;
            }
            return (length * 4) + 8;
        }

        @Override // javassist.bytecode.CodeIterator.Switcher
        int tableSize() {
            return (this.offsets.length * 4) + 8;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Lookup extends Switcher {
        int[] matches;

        Lookup(int i, int i2, int[] iArr, int[] iArr2, Pointers pointers) {
            super(i, i2, iArr2, pointers);
            this.matches = iArr;
        }

        @Override // javassist.bytecode.CodeIterator.Switcher
        int write2(int i, byte[] bArr) {
            int length = this.matches.length;
            ByteArray.write32bit(length, bArr, i);
            int i2 = i + 4;
            for (int i3 = 0; i3 < length; i3++) {
                ByteArray.write32bit(this.matches[i3], bArr, i2);
                ByteArray.write32bit(this.offsets[i3], bArr, i2 + 4);
                i2 += 8;
            }
            return (length * 8) + 4;
        }

        @Override // javassist.bytecode.CodeIterator.Switcher
        int tableSize() {
            return (this.matches.length * 8) + 4;
        }
    }
}
