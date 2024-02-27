package javassist.bytecode.stackmap;

import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public class BasicBlock {
    protected BasicBlock[] exit;
    protected int position;
    protected boolean stop;
    protected Catch toCatch;
    protected int length = 0;
    protected int incoming = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class JsrBytecode extends BadBytecode {
        private static final long serialVersionUID = 1;

        JsrBytecode() {
            super("JSR");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BasicBlock(int i) {
        this.position = i;
    }

    public static BasicBlock find(BasicBlock[] basicBlockArr, int i) throws BadBytecode {
        for (BasicBlock basicBlock : basicBlockArr) {
            int i2 = basicBlock.position;
            if (i2 <= i && i < i2 + basicBlock.length) {
                return basicBlock;
            }
        }
        throw new BadBytecode("no basic block at " + i);
    }

    /* loaded from: classes2.dex */
    public static class Catch {
        public BasicBlock body;
        public Catch next;
        public int typeIndex;

        Catch(BasicBlock basicBlock, int i, Catch r3) {
            this.body = basicBlock;
            this.typeIndex = i;
            this.next = r3;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String name = getClass().getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            name = name.substring(lastIndexOf + 1);
        }
        stringBuffer.append(name);
        stringBuffer.append("[");
        toString2(stringBuffer);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void toString2(StringBuffer stringBuffer) {
        stringBuffer.append("pos=").append(this.position).append(", len=").append(this.length).append(", in=").append(this.incoming).append(", exit{");
        BasicBlock[] basicBlockArr = this.exit;
        if (basicBlockArr != null) {
            for (BasicBlock basicBlock : basicBlockArr) {
                stringBuffer.append(basicBlock.position).append(RemotePrefUtils.SEPARATOR);
            }
        }
        stringBuffer.append("}, {");
        for (Catch r0 = this.toCatch; r0 != null; r0 = r0.next) {
            stringBuffer.append("(").append(r0.body.position).append(", ").append(r0.typeIndex).append("), ");
        }
        stringBuffer.append("}");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Mark implements Comparable<Mark> {
        int position;
        BasicBlock block = null;
        BasicBlock[] jump = null;
        boolean alwaysJmp = false;
        int size = 0;
        Catch catcher = null;

        Mark(int i) {
            this.position = i;
        }

        @Override // java.lang.Comparable
        public int compareTo(Mark mark) {
            if (mark == null) {
                return -1;
            }
            return this.position - mark.position;
        }

        void setJump(BasicBlock[] basicBlockArr, int i, boolean z) {
            this.jump = basicBlockArr;
            this.size = i;
            this.alwaysJmp = z;
        }
    }

    /* loaded from: classes2.dex */
    public static class Maker {
        protected BasicBlock makeBlock(int i) {
            return new BasicBlock(i);
        }

        protected BasicBlock[] makeArray(int i) {
            return new BasicBlock[i];
        }

        private BasicBlock[] makeArray(BasicBlock basicBlock) {
            BasicBlock[] makeArray = makeArray(1);
            makeArray[0] = basicBlock;
            return makeArray;
        }

        private BasicBlock[] makeArray(BasicBlock basicBlock, BasicBlock basicBlock2) {
            BasicBlock[] makeArray = makeArray(2);
            makeArray[0] = basicBlock;
            makeArray[1] = basicBlock2;
            return makeArray;
        }

        public BasicBlock[] make(MethodInfo methodInfo) throws BadBytecode {
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            if (codeAttribute == null) {
                return null;
            }
            CodeIterator it = codeAttribute.iterator();
            return make(it, 0, it.getCodeLength(), codeAttribute.getExceptionTable());
        }

        public BasicBlock[] make(CodeIterator codeIterator, int i, int i2, ExceptionTable exceptionTable) throws BadBytecode {
            BasicBlock[] makeBlocks = makeBlocks(makeMarks(codeIterator, i, i2, exceptionTable));
            addCatchers(makeBlocks, exceptionTable);
            return makeBlocks;
        }

        private Mark makeMark(Map<Integer, Mark> map, int i) {
            return makeMark0(map, i, true, true);
        }

        private Mark makeMark(Map<Integer, Mark> map, int i, BasicBlock[] basicBlockArr, int i2, boolean z) {
            Mark makeMark0 = makeMark0(map, i, false, false);
            makeMark0.setJump(basicBlockArr, i2, z);
            return makeMark0;
        }

        private Mark makeMark0(Map<Integer, Mark> map, int i, boolean z, boolean z2) {
            Integer valueOf = Integer.valueOf(i);
            Mark mark = map.get(valueOf);
            if (mark == null) {
                mark = new Mark(i);
                map.put(valueOf, mark);
            }
            if (z) {
                if (mark.block == null) {
                    mark.block = makeBlock(i);
                }
                if (z2) {
                    mark.block.incoming++;
                }
            }
            return mark;
        }

        private Map<Integer, Mark> makeMarks(CodeIterator codeIterator, int i, int i2, ExceptionTable exceptionTable) throws BadBytecode {
            int next;
            codeIterator.begin();
            codeIterator.move(i);
            HashMap hashMap = new HashMap();
            while (true) {
                int i3 = 1;
                if (codeIterator.hasNext() && (next = codeIterator.next()) < i2) {
                    int byteAt = codeIterator.byteAt(next);
                    if ((153 <= byteAt && byteAt <= 166) || byteAt == 198 || byteAt == 199) {
                        makeMark(hashMap, next, makeArray(makeMark(hashMap, codeIterator.s16bitAt(next + 1) + next).block, makeMark(hashMap, next + 3).block), 3, false);
                    } else if (167 <= byteAt && byteAt <= 171) {
                        switch (byteAt) {
                            case 167:
                                makeGoto(hashMap, next, codeIterator.s16bitAt(next + 1) + next, 3);
                                continue;
                            case 168:
                                makeJsr(hashMap, next, codeIterator.s16bitAt(next + 1) + next, 3);
                                continue;
                            case 169:
                                makeMark(hashMap, next, null, 2, true);
                                continue;
                            case 170:
                                int i4 = (next & (-4)) + 4;
                                int s32bitAt = (codeIterator.s32bitAt(i4 + 8) - codeIterator.s32bitAt(i4 + 4)) + 1;
                                BasicBlock[] makeArray = makeArray(s32bitAt + 1);
                                makeArray[0] = makeMark(hashMap, codeIterator.s32bitAt(i4) + next).block;
                                int i5 = i4 + 12;
                                int i6 = (s32bitAt * 4) + i5;
                                while (i5 < i6) {
                                    makeArray[i3] = makeMark(hashMap, codeIterator.s32bitAt(i5) + next).block;
                                    i5 += 4;
                                    i3++;
                                }
                                makeMark(hashMap, next, makeArray, i6 - next, true);
                                continue;
                            case 171:
                                int i7 = (next & (-4)) + 4;
                                int s32bitAt2 = codeIterator.s32bitAt(i7 + 4);
                                BasicBlock[] makeArray2 = makeArray(s32bitAt2 + 1);
                                makeArray2[0] = makeMark(hashMap, codeIterator.s32bitAt(i7) + next).block;
                                int i8 = i7 + 8 + 4;
                                int i9 = ((s32bitAt2 * 8) + i8) - 4;
                                while (i8 < i9) {
                                    makeArray2[i3] = makeMark(hashMap, codeIterator.s32bitAt(i8) + next).block;
                                    i8 += 8;
                                    i3++;
                                }
                                makeMark(hashMap, next, makeArray2, i9 - next, true);
                                continue;
                        }
                    } else if ((172 <= byteAt && byteAt <= 177) || byteAt == 191) {
                        makeMark(hashMap, next, null, 1, true);
                    } else if (byteAt == 200) {
                        makeGoto(hashMap, next, codeIterator.s32bitAt(next + 1) + next, 5);
                    } else if (byteAt == 201) {
                        makeJsr(hashMap, next, codeIterator.s32bitAt(next + 1) + next, 5);
                    } else if (byteAt == 196 && codeIterator.byteAt(next + 1) == 169) {
                        makeMark(hashMap, next, null, 4, true);
                    }
                }
            }
            if (exceptionTable != null) {
                int size = exceptionTable.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        makeMark0(hashMap, exceptionTable.startPc(size), true, false);
                        makeMark(hashMap, exceptionTable.handlerPc(size));
                    }
                }
            }
            return hashMap;
        }

        private void makeGoto(Map<Integer, Mark> map, int i, int i2, int i3) {
            makeMark(map, i, makeArray(makeMark(map, i2).block), i3, true);
        }

        protected void makeJsr(Map<Integer, Mark> map, int i, int i2, int i3) throws BadBytecode {
            throw new JsrBytecode();
        }

        private BasicBlock[] makeBlocks(Map<Integer, Mark> map) {
            BasicBlock makeBlock;
            Mark[] markArr = (Mark[]) map.values().toArray(new Mark[map.size()]);
            Arrays.sort(markArr);
            ArrayList arrayList = new ArrayList();
            int i = 0;
            if (markArr.length > 0 && markArr[0].position == 0 && markArr[0].block != null) {
                makeBlock = getBBlock(markArr[0]);
                i = 1;
            } else {
                makeBlock = makeBlock(0);
            }
            arrayList.add(makeBlock);
            while (i < markArr.length) {
                int i2 = i + 1;
                Mark mark = markArr[i];
                BasicBlock bBlock = getBBlock(mark);
                if (bBlock == null) {
                    if (makeBlock.length > 0) {
                        makeBlock = makeBlock(makeBlock.position + makeBlock.length);
                        arrayList.add(makeBlock);
                    }
                    makeBlock.length = (mark.position + mark.size) - makeBlock.position;
                    makeBlock.exit = mark.jump;
                    makeBlock.stop = mark.alwaysJmp;
                } else {
                    if (makeBlock.length == 0) {
                        makeBlock.length = mark.position - makeBlock.position;
                        bBlock.incoming++;
                        makeBlock.exit = makeArray(bBlock);
                    } else if (makeBlock.position + makeBlock.length < mark.position) {
                        BasicBlock makeBlock2 = makeBlock(makeBlock.position + makeBlock.length);
                        arrayList.add(makeBlock2);
                        makeBlock2.length = mark.position - makeBlock2.position;
                        makeBlock2.stop = true;
                        makeBlock2.exit = makeArray(bBlock);
                    }
                    arrayList.add(bBlock);
                    makeBlock = bBlock;
                }
                i = i2;
            }
            return (BasicBlock[]) arrayList.toArray(makeArray(arrayList.size()));
        }

        private static BasicBlock getBBlock(Mark mark) {
            BasicBlock basicBlock = mark.block;
            if (basicBlock != null && mark.size > 0) {
                basicBlock.exit = mark.jump;
                basicBlock.length = mark.size;
                basicBlock.stop = mark.alwaysJmp;
            }
            return basicBlock;
        }

        private void addCatchers(BasicBlock[] basicBlockArr, ExceptionTable exceptionTable) throws BadBytecode {
            if (exceptionTable == null) {
                return;
            }
            int size = exceptionTable.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                }
                BasicBlock find = BasicBlock.find(basicBlockArr, exceptionTable.handlerPc(size));
                int startPc = exceptionTable.startPc(size);
                int endPc = exceptionTable.endPc(size);
                int catchType = exceptionTable.catchType(size);
                find.incoming--;
                for (BasicBlock basicBlock : basicBlockArr) {
                    int i = basicBlock.position;
                    if (startPc <= i && i < endPc) {
                        basicBlock.toCatch = new Catch(find, catchType, basicBlock.toCatch);
                        find.incoming++;
                    }
                }
            }
        }
    }
}
