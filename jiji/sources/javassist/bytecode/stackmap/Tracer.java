package javassist.bytecode.stackmap;

import javassist.ClassPool;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ByteArray;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.Opcode;
import javassist.bytecode.stackmap.TypeData;

/* loaded from: classes2.dex */
public abstract class Tracer implements TypeTag {
    protected ClassPool classPool;
    protected ConstPool cpool;
    protected TypeData[] localsTypes;
    protected String returnType;
    protected int stackTop;
    protected TypeData[] stackTypes;

    protected void visitBranch(int i, byte[] bArr, int i2) throws BadBytecode {
    }

    protected void visitGoto(int i, byte[] bArr, int i2) throws BadBytecode {
    }

    protected void visitJSR(int i, byte[] bArr) throws BadBytecode {
    }

    protected void visitLookupSwitch(int i, byte[] bArr, int i2, int i3, int i4) throws BadBytecode {
    }

    protected void visitRET(int i, byte[] bArr) throws BadBytecode {
    }

    protected void visitReturn(int i, byte[] bArr) throws BadBytecode {
    }

    protected void visitTableSwitch(int i, byte[] bArr, int i2, int i3, int i4) throws BadBytecode {
    }

    protected void visitThrow(int i, byte[] bArr) throws BadBytecode {
    }

    public Tracer(ClassPool classPool, ConstPool constPool, int i, int i2, String str) {
        this.classPool = classPool;
        this.cpool = constPool;
        this.returnType = str;
        this.stackTop = 0;
        this.stackTypes = TypeData.make(i);
        this.localsTypes = TypeData.make(i2);
    }

    public Tracer(Tracer tracer) {
        this.classPool = tracer.classPool;
        this.cpool = tracer.cpool;
        this.returnType = tracer.returnType;
        this.stackTop = tracer.stackTop;
        this.stackTypes = TypeData.make(tracer.stackTypes.length);
        this.localsTypes = TypeData.make(tracer.localsTypes.length);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int doOpcode(int i, byte[] bArr) throws BadBytecode {
        try {
            int i2 = bArr[i] & 255;
            if (i2 < 54) {
                return doOpcode0_53(i, bArr, i2);
            }
            if (i2 < 96) {
                return doOpcode54_95(i, bArr, i2);
            }
            if (i2 < 148) {
                return doOpcode96_147(i, bArr, i2);
            }
            return doOpcode148_201(i, bArr, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BadBytecode("inconsistent stack height " + e.getMessage(), e);
        }
    }

    private int doOpcode0_53(int i, byte[] bArr, int i2) throws BadBytecode {
        TypeData[] typeDataArr = this.stackTypes;
        switch (i2) {
            case 0:
                break;
            case 1:
                int i3 = this.stackTop;
                this.stackTop = i3 + 1;
                typeDataArr[i3] = new TypeData.NullType();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                int i4 = this.stackTop;
                this.stackTop = i4 + 1;
                typeDataArr[i4] = INTEGER;
                break;
            case 9:
            case 10:
                int i5 = this.stackTop;
                this.stackTop = i5 + 1;
                typeDataArr[i5] = LONG;
                int i6 = this.stackTop;
                this.stackTop = i6 + 1;
                typeDataArr[i6] = TOP;
                break;
            case 11:
            case 12:
            case 13:
                int i7 = this.stackTop;
                this.stackTop = i7 + 1;
                typeDataArr[i7] = FLOAT;
                break;
            case 14:
            case 15:
                int i8 = this.stackTop;
                this.stackTop = i8 + 1;
                typeDataArr[i8] = DOUBLE;
                int i9 = this.stackTop;
                this.stackTop = i9 + 1;
                typeDataArr[i9] = TOP;
                break;
            case 16:
            case 17:
                int i10 = this.stackTop;
                this.stackTop = i10 + 1;
                typeDataArr[i10] = INTEGER;
                return i2 == 17 ? 3 : 2;
            case 18:
                doLDC(bArr[i + 1] & 255);
                return 2;
            case 19:
            case 20:
                doLDC(ByteArray.readU16bit(bArr, i + 1));
                return 3;
            case 21:
                return doXLOAD(INTEGER, bArr, i);
            case 22:
                return doXLOAD(LONG, bArr, i);
            case 23:
                return doXLOAD(FLOAT, bArr, i);
            case 24:
                return doXLOAD(DOUBLE, bArr, i);
            case 25:
                return doALOAD(bArr[i + 1] & 255);
            case 26:
            case 27:
            case 28:
            case 29:
                int i11 = this.stackTop;
                this.stackTop = i11 + 1;
                typeDataArr[i11] = INTEGER;
                break;
            case 30:
            case 31:
            case 32:
            case 33:
                int i12 = this.stackTop;
                this.stackTop = i12 + 1;
                typeDataArr[i12] = LONG;
                int i13 = this.stackTop;
                this.stackTop = i13 + 1;
                typeDataArr[i13] = TOP;
                break;
            case 34:
            case 35:
            case 36:
            case 37:
                int i14 = this.stackTop;
                this.stackTop = i14 + 1;
                typeDataArr[i14] = FLOAT;
                break;
            case 38:
            case 39:
            case 40:
            case 41:
                int i15 = this.stackTop;
                this.stackTop = i15 + 1;
                typeDataArr[i15] = DOUBLE;
                int i16 = this.stackTop;
                this.stackTop = i16 + 1;
                typeDataArr[i16] = TOP;
                break;
            case 42:
            case 43:
            case 44:
            case 45:
                int i17 = this.stackTop;
                this.stackTop = i17 + 1;
                typeDataArr[i17] = this.localsTypes[i2 - 42];
                break;
            case 46:
                int i18 = this.stackTop - 1;
                this.stackTop = i18;
                typeDataArr[i18 - 1] = INTEGER;
                break;
            case 47:
                typeDataArr[this.stackTop - 2] = LONG;
                typeDataArr[this.stackTop - 1] = TOP;
                break;
            case 48:
                int i19 = this.stackTop - 1;
                this.stackTop = i19;
                typeDataArr[i19 - 1] = FLOAT;
                break;
            case 49:
                typeDataArr[this.stackTop - 2] = DOUBLE;
                typeDataArr[this.stackTop - 1] = TOP;
                break;
            case 50:
                int i20 = this.stackTop - 1;
                this.stackTop = i20;
                int i21 = i20 - 1;
                typeDataArr[i21] = TypeData.ArrayElement.make(typeDataArr[i21]);
                break;
            case 51:
            case 52:
            case 53:
                int i22 = this.stackTop - 1;
                this.stackTop = i22;
                typeDataArr[i22 - 1] = INTEGER;
                break;
            default:
                throw new RuntimeException("fatal");
        }
        return 1;
    }

    private void doLDC(int i) {
        TypeData[] typeDataArr = this.stackTypes;
        int tag = this.cpool.getTag(i);
        if (tag == 8) {
            int i2 = this.stackTop;
            this.stackTop = i2 + 1;
            typeDataArr[i2] = new TypeData.ClassName("java.lang.String");
        } else if (tag == 3) {
            int i3 = this.stackTop;
            this.stackTop = i3 + 1;
            typeDataArr[i3] = INTEGER;
        } else if (tag == 4) {
            int i4 = this.stackTop;
            this.stackTop = i4 + 1;
            typeDataArr[i4] = FLOAT;
        } else if (tag == 5) {
            int i5 = this.stackTop;
            this.stackTop = i5 + 1;
            typeDataArr[i5] = LONG;
            int i6 = this.stackTop;
            this.stackTop = i6 + 1;
            typeDataArr[i6] = TOP;
        } else if (tag != 6) {
            if (tag == 7) {
                int i7 = this.stackTop;
                this.stackTop = i7 + 1;
                typeDataArr[i7] = new TypeData.ClassName("java.lang.Class");
                return;
            }
            throw new RuntimeException("bad LDC: " + tag);
        } else {
            int i8 = this.stackTop;
            this.stackTop = i8 + 1;
            typeDataArr[i8] = DOUBLE;
            int i9 = this.stackTop;
            this.stackTop = i9 + 1;
            typeDataArr[i9] = TOP;
        }
    }

    private int doXLOAD(TypeData typeData, byte[] bArr, int i) {
        return doXLOAD(bArr[i + 1] & 255, typeData);
    }

    private int doXLOAD(int i, TypeData typeData) {
        TypeData[] typeDataArr = this.stackTypes;
        int i2 = this.stackTop;
        this.stackTop = i2 + 1;
        typeDataArr[i2] = typeData;
        if (typeData.is2WordType()) {
            TypeData[] typeDataArr2 = this.stackTypes;
            int i3 = this.stackTop;
            this.stackTop = i3 + 1;
            typeDataArr2[i3] = TOP;
            return 2;
        }
        return 2;
    }

    private int doALOAD(int i) {
        TypeData[] typeDataArr = this.stackTypes;
        int i2 = this.stackTop;
        this.stackTop = i2 + 1;
        typeDataArr[i2] = this.localsTypes[i];
        return 2;
    }

    private int doOpcode54_95(int i, byte[] bArr, int i2) throws BadBytecode {
        int i3 = 3;
        switch (i2) {
            case 54:
                return doXSTORE(i, bArr, INTEGER);
            case 55:
                return doXSTORE(i, bArr, LONG);
            case 56:
                return doXSTORE(i, bArr, FLOAT);
            case 57:
                return doXSTORE(i, bArr, DOUBLE);
            case 58:
                return doASTORE(bArr[i + 1] & 255);
            case 59:
            case 60:
            case 61:
            case 62:
                this.localsTypes[i2 - 59] = INTEGER;
                this.stackTop--;
                break;
            case 63:
            case 64:
            case 65:
            case 66:
                int i4 = i2 - 63;
                this.localsTypes[i4] = LONG;
                this.localsTypes[i4 + 1] = TOP;
                this.stackTop -= 2;
                break;
            case 67:
            case 68:
            case 69:
            case 70:
                this.localsTypes[i2 - 67] = FLOAT;
                this.stackTop--;
                break;
            case 71:
            case 72:
            case 73:
            case 74:
                int i5 = i2 - 71;
                this.localsTypes[i5] = DOUBLE;
                this.localsTypes[i5 + 1] = TOP;
                this.stackTop -= 2;
                break;
            case 75:
            case 76:
            case 77:
            case 78:
                doASTORE(i2 - 75);
                break;
            case 79:
            case 80:
            case 81:
            case 82:
                this.stackTop -= (i2 == 80 || i2 == 82) ? 4 : 4;
                break;
            case 83:
                TypeData[] typeDataArr = this.stackTypes;
                int i6 = this.stackTop;
                TypeData.ArrayElement.aastore(typeDataArr[i6 - 3], typeDataArr[i6 - 1], this.classPool);
                this.stackTop -= 3;
                break;
            case 84:
            case 85:
            case 86:
                this.stackTop -= 3;
                break;
            case 87:
                this.stackTop--;
                break;
            case 88:
                this.stackTop -= 2;
                break;
            case 89:
                int i7 = this.stackTop;
                TypeData[] typeDataArr2 = this.stackTypes;
                typeDataArr2[i7] = typeDataArr2[i7 - 1];
                this.stackTop = i7 + 1;
                break;
            case 90:
            case 91:
                int i8 = (i2 - 90) + 2;
                doDUP_XX(1, i8);
                int i9 = this.stackTop;
                TypeData[] typeDataArr3 = this.stackTypes;
                typeDataArr3[i9 - i8] = typeDataArr3[i9];
                this.stackTop = i9 + 1;
                break;
            case 92:
                doDUP_XX(2, 2);
                this.stackTop += 2;
                break;
            case 93:
            case 94:
                int i10 = (i2 - 93) + 3;
                doDUP_XX(2, i10);
                int i11 = this.stackTop;
                TypeData[] typeDataArr4 = this.stackTypes;
                int i12 = i11 - i10;
                typeDataArr4[i12] = typeDataArr4[i11];
                typeDataArr4[i12 + 1] = typeDataArr4[i11 + 1];
                this.stackTop = i11 + 2;
                break;
            case 95:
                int i13 = this.stackTop - 1;
                TypeData[] typeDataArr5 = this.stackTypes;
                TypeData typeData = typeDataArr5[i13];
                int i14 = i13 - 1;
                typeDataArr5[i13] = typeDataArr5[i14];
                typeDataArr5[i14] = typeData;
                break;
            default:
                throw new RuntimeException("fatal");
        }
        return 1;
    }

    private int doXSTORE(int i, byte[] bArr, TypeData typeData) {
        return doXSTORE(bArr[i + 1] & 255, typeData);
    }

    private int doXSTORE(int i, TypeData typeData) {
        this.stackTop--;
        this.localsTypes[i] = typeData;
        if (typeData.is2WordType()) {
            this.stackTop--;
            this.localsTypes[i + 1] = TOP;
            return 2;
        }
        return 2;
    }

    private int doASTORE(int i) {
        int i2 = this.stackTop - 1;
        this.stackTop = i2;
        this.localsTypes[i] = this.stackTypes[i2];
        return 2;
    }

    private void doDUP_XX(int i, int i2) {
        TypeData[] typeDataArr = this.stackTypes;
        int i3 = this.stackTop - 1;
        int i4 = i3 - i2;
        while (i3 > i4) {
            typeDataArr[i3 + i] = typeDataArr[i3];
            i3--;
        }
    }

    private int doOpcode96_147(int i, byte[] bArr, int i2) {
        if (i2 <= 131) {
            this.stackTop += Opcode.STACK_GROW[i2];
            return 1;
        }
        switch (i2) {
            case 132:
                return 3;
            case 133:
                this.stackTypes[this.stackTop - 1] = LONG;
                this.stackTypes[this.stackTop] = TOP;
                this.stackTop++;
                break;
            case 134:
                this.stackTypes[this.stackTop - 1] = FLOAT;
                break;
            case 135:
                this.stackTypes[this.stackTop - 1] = DOUBLE;
                this.stackTypes[this.stackTop] = TOP;
                this.stackTop++;
                break;
            case 136:
                TypeData[] typeDataArr = this.stackTypes;
                int i3 = this.stackTop - 1;
                this.stackTop = i3;
                typeDataArr[i3 - 1] = INTEGER;
                break;
            case 137:
                TypeData[] typeDataArr2 = this.stackTypes;
                int i4 = this.stackTop - 1;
                this.stackTop = i4;
                typeDataArr2[i4 - 1] = FLOAT;
                break;
            case 138:
                this.stackTypes[this.stackTop - 2] = DOUBLE;
                break;
            case 139:
                this.stackTypes[this.stackTop - 1] = INTEGER;
                break;
            case 140:
                this.stackTypes[this.stackTop - 1] = LONG;
                this.stackTypes[this.stackTop] = TOP;
                this.stackTop++;
                break;
            case 141:
                this.stackTypes[this.stackTop - 1] = DOUBLE;
                this.stackTypes[this.stackTop] = TOP;
                this.stackTop++;
                break;
            case 142:
                TypeData[] typeDataArr3 = this.stackTypes;
                int i5 = this.stackTop - 1;
                this.stackTop = i5;
                typeDataArr3[i5 - 1] = INTEGER;
                break;
            case 143:
                this.stackTypes[this.stackTop - 2] = LONG;
                break;
            case 144:
                TypeData[] typeDataArr4 = this.stackTypes;
                int i6 = this.stackTop - 1;
                this.stackTop = i6;
                typeDataArr4[i6 - 1] = FLOAT;
                break;
            case 145:
            case 146:
            case 147:
                break;
            default:
                throw new RuntimeException("fatal");
        }
        return 1;
    }

    private int doOpcode148_201(int i, byte[] bArr, int i2) throws BadBytecode {
        int i3;
        String str;
        switch (i2) {
            case 148:
                this.stackTypes[this.stackTop - 4] = INTEGER;
                this.stackTop -= 3;
                return 1;
            case 149:
            case 150:
                TypeData[] typeDataArr = this.stackTypes;
                int i4 = this.stackTop - 1;
                this.stackTop = i4;
                typeDataArr[i4 - 1] = INTEGER;
                return 1;
            case 151:
            case 152:
                this.stackTypes[this.stackTop - 4] = INTEGER;
                this.stackTop -= 3;
                return 1;
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
                this.stackTop--;
                visitBranch(i, bArr, ByteArray.readS16bit(bArr, i + 1));
                return 3;
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
                this.stackTop -= 2;
                visitBranch(i, bArr, ByteArray.readS16bit(bArr, i + 1));
                return 3;
            case 167:
                visitGoto(i, bArr, ByteArray.readS16bit(bArr, i + 1));
                return 3;
            case 168:
                visitJSR(i, bArr);
                return 3;
            case 169:
                visitRET(i, bArr);
                return 2;
            case 170:
                this.stackTop--;
                int i5 = (i & (-4)) + 8;
                int read32bit = (ByteArray.read32bit(bArr, i5 + 4) - ByteArray.read32bit(bArr, i5)) + 1;
                visitTableSwitch(i, bArr, read32bit, i5 + 8, ByteArray.read32bit(bArr, i5 - 4));
                i3 = (read32bit * 4) + 16;
                return i3 - (i & 3);
            case 171:
                this.stackTop--;
                int i6 = (i & (-4)) + 8;
                int read32bit2 = ByteArray.read32bit(bArr, i6);
                visitLookupSwitch(i, bArr, read32bit2, i6 + 4, ByteArray.read32bit(bArr, i6 - 4));
                i3 = (read32bit2 * 8) + 12;
                return i3 - (i & 3);
            case 172:
                this.stackTop--;
                visitReturn(i, bArr);
                return 1;
            case 173:
                this.stackTop -= 2;
                visitReturn(i, bArr);
                return 1;
            case 174:
                this.stackTop--;
                visitReturn(i, bArr);
                return 1;
            case 175:
                this.stackTop -= 2;
                visitReturn(i, bArr);
                return 1;
            case 176:
                TypeData[] typeDataArr2 = this.stackTypes;
                int i7 = this.stackTop - 1;
                this.stackTop = i7;
                typeDataArr2[i7].setType(this.returnType, this.classPool);
                visitReturn(i, bArr);
                return 1;
            case 177:
                visitReturn(i, bArr);
                return 1;
            case 178:
                return doGetField(i, bArr, false);
            case 179:
                return doPutField(i, bArr, false);
            case 180:
                return doGetField(i, bArr, true);
            case 181:
                return doPutField(i, bArr, true);
            case 182:
            case 183:
                return doInvokeMethod(i, bArr, true);
            case 184:
                return doInvokeMethod(i, bArr, false);
            case 185:
                return doInvokeIntfMethod(i, bArr);
            case 186:
                return doInvokeDynamic(i, bArr);
            case 187:
                int readU16bit = ByteArray.readU16bit(bArr, i + 1);
                TypeData[] typeDataArr3 = this.stackTypes;
                int i8 = this.stackTop;
                this.stackTop = i8 + 1;
                typeDataArr3[i8] = new TypeData.UninitData(i, this.cpool.getClassInfo(readU16bit));
                return 3;
            case 188:
                return doNEWARRAY(i, bArr);
            case 189:
                String replace = this.cpool.getClassInfo(ByteArray.readU16bit(bArr, i + 1)).replace('.', '/');
                if (replace.charAt(0) == '[') {
                    str = "[" + replace;
                } else {
                    str = "[L" + replace + ";";
                }
                this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(str);
                return 3;
            case 190:
                this.stackTypes[this.stackTop - 1].setType("[Ljava.lang.Object;", this.classPool);
                this.stackTypes[this.stackTop - 1] = INTEGER;
                return 1;
            case 191:
                TypeData[] typeDataArr4 = this.stackTypes;
                int i9 = this.stackTop - 1;
                this.stackTop = i9;
                typeDataArr4[i9].setType("java.lang.Throwable", this.classPool);
                visitThrow(i, bArr);
                return 1;
            case 192:
                String classInfo = this.cpool.getClassInfo(ByteArray.readU16bit(bArr, i + 1));
                if (classInfo.charAt(0) == '[') {
                    classInfo = classInfo.replace('.', '/');
                }
                this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(classInfo);
                return 3;
            case 193:
                this.stackTypes[this.stackTop - 1] = INTEGER;
                return 3;
            case 194:
            case 195:
                this.stackTop--;
                return 1;
            case Opcode.WIDE /* 196 */:
                return doWIDE(i, bArr);
            case 197:
                return doMultiANewArray(i, bArr);
            case 198:
            case 199:
                this.stackTop--;
                visitBranch(i, bArr, ByteArray.readS16bit(bArr, i + 1));
                return 3;
            case 200:
                visitGoto(i, bArr, ByteArray.read32bit(bArr, i + 1));
                return 5;
            case Opcode.JSR_W /* 201 */:
                visitJSR(i, bArr);
                return 5;
            default:
                return 1;
        }
    }

    private int doWIDE(int i, byte[] bArr) throws BadBytecode {
        int i2 = bArr[i + 1] & 255;
        if (i2 != 132) {
            if (i2 != 169) {
                switch (i2) {
                    case 21:
                        doWIDE_XLOAD(i, bArr, INTEGER);
                        return 4;
                    case 22:
                        doWIDE_XLOAD(i, bArr, LONG);
                        return 4;
                    case 23:
                        doWIDE_XLOAD(i, bArr, FLOAT);
                        return 4;
                    case 24:
                        doWIDE_XLOAD(i, bArr, DOUBLE);
                        return 4;
                    case 25:
                        doALOAD(ByteArray.readU16bit(bArr, i + 2));
                        return 4;
                    default:
                        switch (i2) {
                            case 54:
                                doWIDE_STORE(i, bArr, INTEGER);
                                return 4;
                            case 55:
                                doWIDE_STORE(i, bArr, LONG);
                                return 4;
                            case 56:
                                doWIDE_STORE(i, bArr, FLOAT);
                                return 4;
                            case 57:
                                doWIDE_STORE(i, bArr, DOUBLE);
                                return 4;
                            case 58:
                                doASTORE(ByteArray.readU16bit(bArr, i + 2));
                                return 4;
                            default:
                                throw new RuntimeException("bad WIDE instruction: " + i2);
                        }
                }
            }
            visitRET(i, bArr);
            return 4;
        }
        return 6;
    }

    private void doWIDE_XLOAD(int i, byte[] bArr, TypeData typeData) {
        doXLOAD(ByteArray.readU16bit(bArr, i + 2), typeData);
    }

    private void doWIDE_STORE(int i, byte[] bArr, TypeData typeData) {
        doXSTORE(ByteArray.readU16bit(bArr, i + 2), typeData);
    }

    private int doPutField(int i, byte[] bArr, boolean z) throws BadBytecode {
        int readU16bit = ByteArray.readU16bit(bArr, i + 1);
        String fieldrefType = this.cpool.getFieldrefType(readU16bit);
        this.stackTop -= Descriptor.dataSize(fieldrefType);
        char charAt = fieldrefType.charAt(0);
        if (charAt == 'L') {
            this.stackTypes[this.stackTop].setType(getFieldClassName(fieldrefType, 0), this.classPool);
        } else if (charAt == '[') {
            this.stackTypes[this.stackTop].setType(fieldrefType, this.classPool);
        }
        setFieldTarget(z, readU16bit);
        return 3;
    }

    private int doGetField(int i, byte[] bArr, boolean z) throws BadBytecode {
        int readU16bit = ByteArray.readU16bit(bArr, i + 1);
        setFieldTarget(z, readU16bit);
        pushMemberType(this.cpool.getFieldrefType(readU16bit));
        return 3;
    }

    private void setFieldTarget(boolean z, int i) throws BadBytecode {
        if (z) {
            String fieldrefClassName = this.cpool.getFieldrefClassName(i);
            TypeData[] typeDataArr = this.stackTypes;
            int i2 = this.stackTop - 1;
            this.stackTop = i2;
            typeDataArr[i2].setType(fieldrefClassName, this.classPool);
        }
    }

    private int doNEWARRAY(int i, byte[] bArr) {
        String str;
        int i2 = this.stackTop - 1;
        switch (bArr[i + 1] & 255) {
            case 4:
                str = "[Z";
                break;
            case 5:
                str = "[C";
                break;
            case 6:
                str = "[F";
                break;
            case 7:
                str = "[D";
                break;
            case 8:
                str = "[B";
                break;
            case 9:
                str = "[S";
                break;
            case 10:
                str = "[I";
                break;
            case 11:
                str = "[J";
                break;
            default:
                throw new RuntimeException("bad newarray");
        }
        this.stackTypes[i2] = new TypeData.ClassName(str);
        return 2;
    }

    private int doMultiANewArray(int i, byte[] bArr) {
        int readU16bit = ByteArray.readU16bit(bArr, i + 1);
        this.stackTop -= (bArr[i + 3] & 255) - 1;
        this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(this.cpool.getClassInfo(readU16bit).replace('.', '/'));
        return 4;
    }

    private int doInvokeMethod(int i, byte[] bArr, boolean z) throws BadBytecode {
        int readU16bit = ByteArray.readU16bit(bArr, i + 1);
        String methodrefType = this.cpool.getMethodrefType(readU16bit);
        checkParamTypes(methodrefType, 1);
        if (z) {
            String methodrefClassName = this.cpool.getMethodrefClassName(readU16bit);
            TypeData[] typeDataArr = this.stackTypes;
            int i2 = this.stackTop - 1;
            this.stackTop = i2;
            TypeData typeData = typeDataArr[i2];
            if ((typeData instanceof TypeData.UninitTypeVar) && typeData.isUninit()) {
                constructorCalled(typeData, ((TypeData.UninitTypeVar) typeData).offset());
            } else if (typeData instanceof TypeData.UninitData) {
                constructorCalled(typeData, ((TypeData.UninitData) typeData).offset());
            }
            typeData.setType(methodrefClassName, this.classPool);
        }
        pushMemberType(methodrefType);
        return 3;
    }

    private void constructorCalled(TypeData typeData, int i) {
        typeData.constructorCalled(i);
        int i2 = 0;
        for (int i3 = 0; i3 < this.stackTop; i3++) {
            this.stackTypes[i3].constructorCalled(i);
        }
        while (true) {
            TypeData[] typeDataArr = this.localsTypes;
            if (i2 >= typeDataArr.length) {
                return;
            }
            typeDataArr[i2].constructorCalled(i);
            i2++;
        }
    }

    private int doInvokeIntfMethod(int i, byte[] bArr) throws BadBytecode {
        int readU16bit = ByteArray.readU16bit(bArr, i + 1);
        String interfaceMethodrefType = this.cpool.getInterfaceMethodrefType(readU16bit);
        checkParamTypes(interfaceMethodrefType, 1);
        String interfaceMethodrefClassName = this.cpool.getInterfaceMethodrefClassName(readU16bit);
        TypeData[] typeDataArr = this.stackTypes;
        int i2 = this.stackTop - 1;
        this.stackTop = i2;
        typeDataArr[i2].setType(interfaceMethodrefClassName, this.classPool);
        pushMemberType(interfaceMethodrefType);
        return 5;
    }

    private int doInvokeDynamic(int i, byte[] bArr) throws BadBytecode {
        String invokeDynamicType = this.cpool.getInvokeDynamicType(ByteArray.readU16bit(bArr, i + 1));
        checkParamTypes(invokeDynamicType, 1);
        pushMemberType(invokeDynamicType);
        return 5;
    }

    private void pushMemberType(String str) {
        int i = 0;
        if (str.charAt(0) == '(' && (i = str.indexOf(41) + 1) < 1) {
            throw new IndexOutOfBoundsException("bad descriptor: " + str);
        }
        TypeData[] typeDataArr = this.stackTypes;
        int i2 = this.stackTop;
        char charAt = str.charAt(i);
        if (charAt == 'D') {
            typeDataArr[i2] = DOUBLE;
            typeDataArr[i2 + 1] = TOP;
            this.stackTop += 2;
            return;
        }
        if (charAt == 'F') {
            typeDataArr[i2] = FLOAT;
        } else if (charAt == 'J') {
            typeDataArr[i2] = LONG;
            typeDataArr[i2 + 1] = TOP;
            this.stackTop += 2;
            return;
        } else if (charAt == 'L') {
            typeDataArr[i2] = new TypeData.ClassName(getFieldClassName(str, i));
        } else if (charAt == 'V') {
            return;
        } else {
            if (charAt == '[') {
                typeDataArr[i2] = new TypeData.ClassName(str.substring(i));
            } else {
                typeDataArr[i2] = INTEGER;
            }
        }
        this.stackTop++;
    }

    private static String getFieldClassName(String str, int i) {
        return str.substring(i + 1, str.length() - 1).replace('/', '.');
    }

    private void checkParamTypes(String str, int i) throws BadBytecode {
        int i2;
        char charAt = str.charAt(i);
        if (charAt == ')') {
            return;
        }
        boolean z = false;
        int i3 = i;
        while (charAt == '[') {
            i3++;
            charAt = str.charAt(i3);
            z = true;
        }
        if (charAt == 'L') {
            i2 = str.indexOf(59, i3) + 1;
            if (i2 <= 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        } else {
            i2 = i3 + 1;
        }
        checkParamTypes(str, i2);
        if (!z && (charAt == 'J' || charAt == 'D')) {
            this.stackTop -= 2;
        } else {
            this.stackTop--;
        }
        if (z) {
            this.stackTypes[this.stackTop].setType(str.substring(i, i2), this.classPool);
        } else if (charAt == 'L') {
            this.stackTypes[this.stackTop].setType(str.substring(i + 1, i2 - 1).replace('/', '.'), this.classPool);
        }
    }
}
