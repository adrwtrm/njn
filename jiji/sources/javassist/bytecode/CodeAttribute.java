package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class CodeAttribute extends AttributeInfo implements Opcode {
    public static final String tag = "Code";
    private List<AttributeInfo> attributes;
    private ExceptionTable exceptions;
    private int maxLocals;
    private int maxStack;

    public CodeAttribute(ConstPool constPool, int i, int i2, byte[] bArr, ExceptionTable exceptionTable) {
        super(constPool, tag);
        this.maxStack = i;
        this.maxLocals = i2;
        this.info = bArr;
        this.exceptions = exceptionTable;
        this.attributes = new ArrayList();
    }

    private CodeAttribute(ConstPool constPool, CodeAttribute codeAttribute, Map<String, String> map) throws BadBytecode {
        super(constPool, tag);
        this.maxStack = codeAttribute.getMaxStack();
        this.maxLocals = codeAttribute.getMaxLocals();
        this.exceptions = codeAttribute.getExceptionTable().copy(constPool, map);
        this.attributes = new ArrayList();
        List<AttributeInfo> attributes = codeAttribute.getAttributes();
        int size = attributes.size();
        for (int i = 0; i < size; i++) {
            this.attributes.add(attributes.get(i).copy(constPool, map));
        }
        this.info = codeAttribute.copyCode(constPool, map, this.exceptions, this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, (byte[]) null);
        dataInputStream.readInt();
        this.maxStack = dataInputStream.readUnsignedShort();
        this.maxLocals = dataInputStream.readUnsignedShort();
        this.info = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(this.info);
        this.exceptions = new ExceptionTable(constPool, dataInputStream);
        this.attributes = new ArrayList();
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        for (int i2 = 0; i2 < readUnsignedShort; i2++) {
            this.attributes.add(AttributeInfo.read(constPool, dataInputStream));
        }
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) throws RuntimeCopyException {
        try {
            return new CodeAttribute(constPool, this, map);
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

    @Override // javassist.bytecode.AttributeInfo
    public int length() {
        return this.info.length + 18 + (this.exceptions.size() * 8) + AttributeInfo.getLength(this.attributes);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.bytecode.AttributeInfo
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeInt(length() - 6);
        dataOutputStream.writeShort(this.maxStack);
        dataOutputStream.writeShort(this.maxLocals);
        dataOutputStream.writeInt(this.info.length);
        dataOutputStream.write(this.info);
        this.exceptions.write(dataOutputStream);
        dataOutputStream.writeShort(this.attributes.size());
        AttributeInfo.writeAll(this.attributes, dataOutputStream);
    }

    @Override // javassist.bytecode.AttributeInfo
    public byte[] get() {
        throw new UnsupportedOperationException("CodeAttribute.get()");
    }

    @Override // javassist.bytecode.AttributeInfo
    public void set(byte[] bArr) {
        throw new UnsupportedOperationException("CodeAttribute.set()");
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(String str, String str2) {
        AttributeInfo.renameClass(this.attributes, str, str2);
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(Map<String, String> map) {
        AttributeInfo.renameClass(this.attributes, map);
    }

    @Override // javassist.bytecode.AttributeInfo
    void getRefClasses(Map<String, String> map) {
        AttributeInfo.getRefClasses(this.attributes, map);
    }

    public String getDeclaringClass() {
        return getConstPool().getClassName();
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public void setMaxStack(int i) {
        this.maxStack = i;
    }

    public int computeMaxStack() throws BadBytecode {
        int computeMaxStack = new CodeAnalyzer(this).computeMaxStack();
        this.maxStack = computeMaxStack;
        return computeMaxStack;
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public void setMaxLocals(int i) {
        this.maxLocals = i;
    }

    public int getCodeLength() {
        return this.info.length;
    }

    public byte[] getCode() {
        return this.info;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCode(byte[] bArr) {
        super.set(bArr);
    }

    public CodeIterator iterator() {
        return new CodeIterator(this);
    }

    public ExceptionTable getExceptionTable() {
        return this.exceptions;
    }

    public List<AttributeInfo> getAttributes() {
        return this.attributes;
    }

    public AttributeInfo getAttribute(String str) {
        return AttributeInfo.lookup(this.attributes, str);
    }

    public void setAttribute(StackMapTable stackMapTable) {
        AttributeInfo.remove(this.attributes, StackMapTable.tag);
        if (stackMapTable != null) {
            this.attributes.add(stackMapTable);
        }
    }

    public void setAttribute(StackMap stackMap) {
        AttributeInfo.remove(this.attributes, StackMap.tag);
        if (stackMap != null) {
            this.attributes.add(stackMap);
        }
    }

    private byte[] copyCode(ConstPool constPool, Map<String, String> map, ExceptionTable exceptionTable, CodeAttribute codeAttribute) throws BadBytecode {
        int codeLength = getCodeLength();
        byte[] bArr = new byte[codeLength];
        codeAttribute.info = bArr;
        return LdcEntry.doit(bArr, copyCode(this.info, 0, codeLength, getConstPool(), bArr, constPool, map), exceptionTable, codeAttribute);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static LdcEntry copyCode(byte[] bArr, int i, int i2, ConstPool constPool, byte[] bArr2, ConstPool constPool2, Map<String, String> map) throws BadBytecode {
        int i3 = i;
        LdcEntry ldcEntry = null;
        while (i3 < i2) {
            int nextOpcode = CodeIterator.nextOpcode(bArr, i3);
            byte b = bArr[i3];
            bArr2[i3] = b;
            int i4 = b & 255;
            if (i4 != 189) {
                if (i4 == 197) {
                    copyConstPoolInfo(i3 + 1, bArr, constPool, bArr2, constPool2, map);
                    int i5 = i3 + 3;
                    bArr2[i5] = bArr[i5];
                } else if (i4 != 192 && i4 != 193) {
                    switch (i4) {
                        case 18:
                            int i6 = i3 + 1;
                            int copy = constPool.copy(bArr[i6] & 255, constPool2, map);
                            if (copy < 256) {
                                bArr2[i6] = (byte) copy;
                                break;
                            } else {
                                bArr2[i3] = 0;
                                bArr2[i6] = 0;
                                LdcEntry ldcEntry2 = new LdcEntry();
                                ldcEntry2.where = i3;
                                ldcEntry2.index = copy;
                                ldcEntry2.next = ldcEntry;
                                ldcEntry = ldcEntry2;
                                break;
                            }
                        default:
                            switch (i4) {
                                case 178:
                                case 179:
                                case 180:
                                case 181:
                                case 182:
                                case 183:
                                case 184:
                                case 187:
                                    break;
                                case 185:
                                    copyConstPoolInfo(i3 + 1, bArr, constPool, bArr2, constPool2, map);
                                    int i7 = i3 + 3;
                                    bArr2[i7] = bArr[i7];
                                    int i8 = i3 + 4;
                                    bArr2[i8] = bArr[i8];
                                    break;
                                case 186:
                                    copyConstPoolInfo(i3 + 1, bArr, constPool, bArr2, constPool2, map);
                                    bArr2[i3 + 3] = 0;
                                    bArr2[i3 + 4] = 0;
                                    break;
                                default:
                                    while (true) {
                                        i3++;
                                        if (i3 >= nextOpcode) {
                                            break;
                                        } else {
                                            bArr2[i3] = bArr[i3];
                                        }
                                    }
                            }
                        case 19:
                        case 20:
                            copyConstPoolInfo(i3 + 1, bArr, constPool, bArr2, constPool2, map);
                            break;
                    }
                }
                i3 = nextOpcode;
            }
            copyConstPoolInfo(i3 + 1, bArr, constPool, bArr2, constPool2, map);
            i3 = nextOpcode;
        }
        return ldcEntry;
    }

    private static void copyConstPoolInfo(int i, byte[] bArr, ConstPool constPool, byte[] bArr2, ConstPool constPool2, Map<String, String> map) {
        int i2 = i + 1;
        int copy = constPool.copy((bArr[i2] & 255) | ((bArr[i] & 255) << 8), constPool2, map);
        bArr2[i] = (byte) (copy >> 8);
        bArr2[i2] = (byte) copy;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class LdcEntry {
        int index;
        LdcEntry next;
        int where;

        LdcEntry() {
        }

        static byte[] doit(byte[] bArr, LdcEntry ldcEntry, ExceptionTable exceptionTable, CodeAttribute codeAttribute) throws BadBytecode {
            return ldcEntry != null ? CodeIterator.changeLdcToLdcW(bArr, exceptionTable, codeAttribute, ldcEntry) : bArr;
        }
    }

    public void insertLocalVar(int i, int i2) throws BadBytecode {
        CodeIterator it = iterator();
        while (it.hasNext()) {
            shiftIndex(it, i, i2);
        }
        setMaxLocals(getMaxLocals() + i2);
    }

    private static void shiftIndex(CodeIterator codeIterator, int i, int i2) throws BadBytecode {
        int i3;
        int u16bitAt;
        int next = codeIterator.next();
        int byteAt = codeIterator.byteAt(next);
        if (byteAt < 21) {
            return;
        }
        if (byteAt < 79) {
            if (byteAt < 26) {
                shiftIndex8(codeIterator, next, byteAt, i, i2);
            } else if (byteAt < 46) {
                shiftIndex0(codeIterator, next, byteAt, i, i2, 26, 21);
            } else if (byteAt < 54) {
            } else {
                if (byteAt < 59) {
                    shiftIndex8(codeIterator, next, byteAt, i, i2);
                } else {
                    shiftIndex0(codeIterator, next, byteAt, i, i2, 59, 54);
                }
            }
        } else if (byteAt != 132) {
            if (byteAt == 169) {
                shiftIndex8(codeIterator, next, byteAt, i, i2);
            } else if (byteAt != 196 || (u16bitAt = codeIterator.u16bitAt((i3 = next + 2))) < i) {
            } else {
                codeIterator.write16bit(u16bitAt + i2, i3);
            }
        } else {
            int i4 = next + 1;
            int byteAt2 = codeIterator.byteAt(i4);
            if (byteAt2 < i) {
                return;
            }
            int i5 = byteAt2 + i2;
            if (i5 < 256) {
                codeIterator.writeByte(i5, i4);
                return;
            }
            int insertExGap = codeIterator.insertExGap(3);
            codeIterator.writeByte(Opcode.WIDE, insertExGap - 3);
            codeIterator.writeByte(132, insertExGap - 2);
            codeIterator.write16bit(i5, insertExGap - 1);
            codeIterator.write16bit((byte) codeIterator.byteAt(next + 2), insertExGap + 1);
        }
    }

    private static void shiftIndex8(CodeIterator codeIterator, int i, int i2, int i3, int i4) throws BadBytecode {
        int i5 = i + 1;
        int byteAt = codeIterator.byteAt(i5);
        if (byteAt < i3) {
            return;
        }
        int i6 = byteAt + i4;
        if (i6 < 256) {
            codeIterator.writeByte(i6, i5);
            return;
        }
        int insertExGap = codeIterator.insertExGap(2);
        codeIterator.writeByte(Opcode.WIDE, insertExGap - 2);
        codeIterator.writeByte(i2, insertExGap - 1);
        codeIterator.write16bit(i6, insertExGap);
    }

    private static void shiftIndex0(CodeIterator codeIterator, int i, int i2, int i3, int i4, int i5, int i6) throws BadBytecode {
        int i7 = i2 - i5;
        int i8 = i7 % 4;
        if (i8 < i3) {
            return;
        }
        int i9 = i8 + i4;
        if (i9 < 4) {
            codeIterator.writeByte(i2 + i4, i);
            return;
        }
        int i10 = (i7 / 4) + i6;
        if (i9 < 256) {
            int insertExGap = codeIterator.insertExGap(1);
            codeIterator.writeByte(i10, insertExGap - 1);
            codeIterator.writeByte(i9, insertExGap);
            return;
        }
        int insertExGap2 = codeIterator.insertExGap(3);
        codeIterator.writeByte(Opcode.WIDE, insertExGap2 - 1);
        codeIterator.writeByte(i10, insertExGap2);
        codeIterator.write16bit(i9, insertExGap2 + 1);
    }
}
