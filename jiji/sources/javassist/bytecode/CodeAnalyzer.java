package javassist.bytecode;

import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;

/* loaded from: classes2.dex */
class CodeAnalyzer implements Opcode {
    private CodeAttribute codeAttr;
    private ConstPool constPool;

    private static boolean isEnd(int i) {
        return (172 <= i && i <= 177) || i == 191;
    }

    public CodeAnalyzer(CodeAttribute codeAttribute) {
        this.codeAttr = codeAttribute;
        this.constPool = codeAttribute.getConstPool();
    }

    public int computeMaxStack() throws BadBytecode {
        int i;
        boolean z;
        CodeIterator it = this.codeAttr.iterator();
        int codeLength = it.getCodeLength();
        int[] iArr = new int[codeLength];
        this.constPool = this.codeAttr.getConstPool();
        initStack(iArr, this.codeAttr);
        do {
            z = false;
            for (int i2 = 0; i2 < codeLength; i2++) {
                if (iArr[i2] < 0) {
                    visitBytecode(it, iArr, i2);
                    z = true;
                }
            }
        } while (z);
        int i3 = 1;
        for (i = 0; i < codeLength; i++) {
            int i4 = iArr[i];
            if (i4 > i3) {
                i3 = i4;
            }
        }
        return i3 - 1;
    }

    private void initStack(int[] iArr, CodeAttribute codeAttribute) {
        iArr[0] = -1;
        ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
        if (exceptionTable != null) {
            int size = exceptionTable.size();
            for (int i = 0; i < size; i++) {
                iArr[exceptionTable.handlerPc(i)] = -2;
            }
        }
    }

    private void visitBytecode(CodeIterator codeIterator, int[] iArr, int i) throws BadBytecode {
        int length = iArr.length;
        codeIterator.move(i);
        int i2 = -iArr[i];
        int[] iArr2 = {-1};
        while (codeIterator.hasNext()) {
            int next = codeIterator.next();
            iArr[next] = i2;
            int byteAt = codeIterator.byteAt(next);
            i2 = visitInst(byteAt, codeIterator, next, i2);
            if (i2 < 1) {
                throw new BadBytecode("stack underflow at " + next);
            }
            if (processBranch(byteAt, codeIterator, next, length, iArr, i2, iArr2) || isEnd(byteAt)) {
                return;
            }
            if (byteAt == 168 || byteAt == 201) {
                i2--;
            }
        }
    }

    private boolean processBranch(int i, CodeIterator codeIterator, int i2, int i3, int[] iArr, int i4, int[] iArr2) throws BadBytecode {
        int s32bitAt;
        int i5 = 0;
        if ((153 > i || i > 166) && i != 198 && i != 199) {
            if (i == 200) {
                checkTarget(i2, i2 + codeIterator.s32bitAt(i2 + 1), i3, iArr, i4);
                return true;
            }
            if (i != 201) {
                switch (i) {
                    case 167:
                        checkTarget(i2, i2 + codeIterator.s16bitAt(i2 + 1), i3, iArr, i4);
                        return true;
                    case 169:
                        int i6 = iArr2[0];
                        if (i6 < 0) {
                            iArr2[0] = i4 + 1;
                            return false;
                        } else if (i4 + 1 == i6) {
                            return true;
                        } else {
                            throw new BadBytecode("sorry, cannot compute this data flow due to RET: " + i4 + RemotePrefUtils.SEPARATOR + iArr2[0]);
                        }
                    case 170:
                    case 171:
                        int i7 = (i2 & (-4)) + 4;
                        checkTarget(i2, i2 + codeIterator.s32bitAt(i7), i3, iArr, i4);
                        if (i == 171) {
                            int s32bitAt2 = codeIterator.s32bitAt(i7 + 4);
                            int i8 = i7 + 12;
                            while (i5 < s32bitAt2) {
                                checkTarget(i2, i2 + codeIterator.s32bitAt(i8), i3, iArr, i4);
                                i8 += 8;
                                i5++;
                            }
                        } else {
                            int s32bitAt3 = (codeIterator.s32bitAt(i7 + 8) - codeIterator.s32bitAt(i7 + 4)) + 1;
                            int i9 = i7 + 12;
                            while (i5 < s32bitAt3) {
                                checkTarget(i2, i2 + codeIterator.s32bitAt(i9), i3, iArr, i4);
                                i9 += 4;
                                i5++;
                            }
                        }
                        return true;
                }
            }
            if (i == 168) {
                s32bitAt = codeIterator.s16bitAt(i2 + 1);
            } else {
                s32bitAt = codeIterator.s32bitAt(i2 + 1);
            }
            checkTarget(i2, s32bitAt + i2, i3, iArr, i4);
            int i10 = iArr2[0];
            if (i10 < 0) {
                iArr2[0] = i4;
                return false;
            } else if (i4 == i10) {
                return false;
            } else {
                throw new BadBytecode("sorry, cannot compute this data flow due to JSR: " + i4 + RemotePrefUtils.SEPARATOR + iArr2[0]);
            }
        }
        checkTarget(i2, i2 + codeIterator.s16bitAt(i2 + 1), i3, iArr, i4);
        return false;
    }

    private void checkTarget(int i, int i2, int i3, int[] iArr, int i4) throws BadBytecode {
        if (i2 < 0 || i3 <= i2) {
            throw new BadBytecode("bad branch offset at " + i);
        }
        int i5 = iArr[i2];
        if (i5 == 0) {
            iArr[i2] = -i4;
        } else if (i5 != i4 && i5 != (-i4)) {
            throw new BadBytecode("verification error (" + i4 + RemotePrefUtils.SEPARATOR + i5 + ") at " + i);
        }
    }

    private int visitInst(int i, CodeIterator codeIterator, int i2, int i3) throws BadBytecode {
        int i4;
        int fieldSize;
        int fieldSize2;
        if (i != 191) {
            if (i != 196) {
                if (i != 197) {
                    switch (i) {
                        case 178:
                            i4 = getFieldSize(codeIterator, i2);
                            return i3 + i4;
                        case 179:
                            fieldSize = getFieldSize(codeIterator, i2);
                            return i3 - fieldSize;
                        case 180:
                            fieldSize2 = getFieldSize(codeIterator, i2);
                            i4 = fieldSize2 - 1;
                            return i3 + i4;
                        case 181:
                            fieldSize = getFieldSize(codeIterator, i2) + 1;
                            return i3 - fieldSize;
                        case 182:
                        case 183:
                            fieldSize2 = Descriptor.dataSize(this.constPool.getMethodrefType(codeIterator.u16bitAt(i2 + 1)));
                            i4 = fieldSize2 - 1;
                            return i3 + i4;
                        case 184:
                            i4 = Descriptor.dataSize(this.constPool.getMethodrefType(codeIterator.u16bitAt(i2 + 1)));
                            return i3 + i4;
                        case 185:
                            fieldSize2 = Descriptor.dataSize(this.constPool.getInterfaceMethodrefType(codeIterator.u16bitAt(i2 + 1)));
                            i4 = fieldSize2 - 1;
                            return i3 + i4;
                        case 186:
                            i4 = Descriptor.dataSize(this.constPool.getInvokeDynamicType(codeIterator.u16bitAt(i2 + 1)));
                            return i3 + i4;
                    }
                }
                return (1 - codeIterator.byteAt(i2 + 3)) + i3;
            }
            i = codeIterator.byteAt(i2 + 1);
            i4 = STACK_GROW[i];
            return i3 + i4;
        }
        return 1;
    }

    private int getFieldSize(CodeIterator codeIterator, int i) {
        return Descriptor.dataSize(this.constPool.getFieldrefType(codeIterator.u16bitAt(i + 1)));
    }
}
