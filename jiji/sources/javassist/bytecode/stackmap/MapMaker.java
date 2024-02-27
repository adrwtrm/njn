package javassist.bytecode.stackmap;

import java.util.ArrayList;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ByteArray;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.StackMap;
import javassist.bytecode.StackMapTable;
import javassist.bytecode.stackmap.BasicBlock;
import javassist.bytecode.stackmap.TypeData;

/* loaded from: classes2.dex */
public class MapMaker extends Tracer {
    public static StackMapTable make(ClassPool classPool, MethodInfo methodInfo) throws BadBytecode {
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return null;
        }
        try {
            TypedBlock[] makeBlocks = TypedBlock.makeBlocks(methodInfo, codeAttribute, true);
            if (makeBlocks == null) {
                return null;
            }
            MapMaker mapMaker = new MapMaker(classPool, methodInfo, codeAttribute);
            try {
                mapMaker.make(makeBlocks, codeAttribute.getCode());
                return mapMaker.toStackMap(makeBlocks);
            } catch (BadBytecode e) {
                throw new BadBytecode(methodInfo, e);
            }
        } catch (BasicBlock.JsrBytecode unused) {
            return null;
        }
    }

    public static StackMap make2(ClassPool classPool, MethodInfo methodInfo) throws BadBytecode {
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return null;
        }
        try {
            TypedBlock[] makeBlocks = TypedBlock.makeBlocks(methodInfo, codeAttribute, true);
            if (makeBlocks == null) {
                return null;
            }
            MapMaker mapMaker = new MapMaker(classPool, methodInfo, codeAttribute);
            try {
                mapMaker.make(makeBlocks, codeAttribute.getCode());
                return mapMaker.toStackMap2(methodInfo.getConstPool(), makeBlocks);
            } catch (BadBytecode e) {
                throw new BadBytecode(methodInfo, e);
            }
        } catch (BasicBlock.JsrBytecode unused) {
            return null;
        }
    }

    public MapMaker(ClassPool classPool, MethodInfo methodInfo, CodeAttribute codeAttribute) {
        super(classPool, methodInfo.getConstPool(), codeAttribute.getMaxStack(), codeAttribute.getMaxLocals(), TypedBlock.getRetType(methodInfo.getDescriptor()));
    }

    protected MapMaker(MapMaker mapMaker) {
        super(mapMaker);
    }

    void make(TypedBlock[] typedBlockArr, byte[] bArr) throws BadBytecode {
        make(bArr, typedBlockArr[0]);
        findDeadCatchers(bArr, typedBlockArr);
        try {
            fixTypes(bArr, typedBlockArr);
        } catch (NotFoundException e) {
            throw new BadBytecode("failed to resolve types", e);
        }
    }

    private void make(byte[] bArr, TypedBlock typedBlock) throws BadBytecode {
        copyTypeData(typedBlock.stackTop, typedBlock.stackTypes, this.stackTypes);
        this.stackTop = typedBlock.stackTop;
        copyTypeData(typedBlock.localsTypes.length, typedBlock.localsTypes, this.localsTypes);
        traceException(bArr, typedBlock.toCatch);
        int i = typedBlock.position;
        int i2 = typedBlock.length + i;
        while (i < i2) {
            i += doOpcode(i, bArr);
            traceException(bArr, typedBlock.toCatch);
        }
        if (typedBlock.exit != null) {
            for (int i3 = 0; i3 < typedBlock.exit.length; i3++) {
                TypedBlock typedBlock2 = (TypedBlock) typedBlock.exit[i3];
                if (typedBlock2.alreadySet()) {
                    mergeMap(typedBlock2, true);
                } else {
                    recordStackMap(typedBlock2);
                    new MapMaker(this).make(bArr, typedBlock2);
                }
            }
        }
    }

    private void traceException(byte[] bArr, BasicBlock.Catch r6) throws BadBytecode {
        while (r6 != null) {
            TypedBlock typedBlock = (TypedBlock) r6.body;
            if (typedBlock.alreadySet()) {
                mergeMap(typedBlock, false);
                if (typedBlock.stackTop < 1) {
                    throw new BadBytecode("bad catch clause: " + r6.typeIndex);
                }
                typedBlock.stackTypes[0] = merge(toExceptionType(r6.typeIndex), typedBlock.stackTypes[0]);
            } else {
                recordStackMap(typedBlock, r6.typeIndex);
                new MapMaker(this).make(bArr, typedBlock);
            }
            r6 = r6.next;
        }
    }

    private void mergeMap(TypedBlock typedBlock, boolean z) throws BadBytecode {
        int length = this.localsTypes.length;
        for (int i = 0; i < length; i++) {
            typedBlock.localsTypes[i] = merge(validateTypeData(this.localsTypes, length, i), typedBlock.localsTypes[i]);
        }
        if (z) {
            int i2 = this.stackTop;
            for (int i3 = 0; i3 < i2; i3++) {
                typedBlock.stackTypes[i3] = merge(this.stackTypes[i3], typedBlock.stackTypes[i3]);
            }
        }
    }

    private TypeData merge(TypeData typeData, TypeData typeData2) throws BadBytecode {
        if (typeData == typeData2 || (typeData2 instanceof TypeData.ClassName) || (typeData2 instanceof TypeData.BasicType)) {
            return typeData2;
        }
        if (typeData2 instanceof TypeData.AbsTypeVar) {
            ((TypeData.AbsTypeVar) typeData2).merge(typeData);
            return typeData2;
        }
        throw new RuntimeException("fatal: this should never happen");
    }

    private void recordStackMap(TypedBlock typedBlock) throws BadBytecode {
        TypeData[] make = TypeData.make(this.stackTypes.length);
        int i = this.stackTop;
        recordTypeData(i, this.stackTypes, make);
        recordStackMap0(typedBlock, i, make);
    }

    private void recordStackMap(TypedBlock typedBlock, int i) throws BadBytecode {
        TypeData[] make = TypeData.make(this.stackTypes.length);
        make[0] = toExceptionType(i).join();
        recordStackMap0(typedBlock, 1, make);
    }

    private TypeData.ClassName toExceptionType(int i) {
        return new TypeData.ClassName(i == 0 ? "java.lang.Throwable" : this.cpool.getClassInfo(i));
    }

    private void recordStackMap0(TypedBlock typedBlock, int i, TypeData[] typeDataArr) throws BadBytecode {
        int length = this.localsTypes.length;
        TypeData[] make = TypeData.make(length);
        typedBlock.setStackMap(i, typeDataArr, recordTypeData(length, this.localsTypes, make), make);
    }

    protected static int recordTypeData(int i, TypeData[] typeDataArr, TypeData[] typeDataArr2) {
        int i2 = -1;
        for (int i3 = 0; i3 < i; i3++) {
            TypeData validateTypeData = validateTypeData(typeDataArr, i, i3);
            typeDataArr2[i3] = validateTypeData.join();
            if (validateTypeData != TOP) {
                i2 = i3 + 1;
            }
        }
        return i2 + 1;
    }

    protected static void copyTypeData(int i, TypeData[] typeDataArr, TypeData[] typeDataArr2) {
        for (int i2 = 0; i2 < i; i2++) {
            typeDataArr2[i2] = typeDataArr[i2];
        }
    }

    private static TypeData validateTypeData(TypeData[] typeDataArr, int i, int i2) {
        int i3;
        TypeData typeData = typeDataArr[i2];
        return (!typeData.is2WordType() || (i3 = i2 + 1) >= i || typeDataArr[i3] == TOP) ? typeData : TOP;
    }

    private void findDeadCatchers(byte[] bArr, TypedBlock[] typedBlockArr) throws BadBytecode {
        for (TypedBlock typedBlock : typedBlockArr) {
            if (!typedBlock.alreadySet()) {
                fixDeadcode(bArr, typedBlock);
                BasicBlock.Catch r2 = typedBlock.toCatch;
                if (r2 != null) {
                    TypedBlock typedBlock2 = (TypedBlock) r2.body;
                    if (!typedBlock2.alreadySet()) {
                        recordStackMap(typedBlock2, r2.typeIndex);
                        fixDeadcode(bArr, typedBlock2);
                        typedBlock2.incoming = 1;
                    }
                }
            }
        }
    }

    private void fixDeadcode(byte[] bArr, TypedBlock typedBlock) throws BadBytecode {
        int i = typedBlock.position;
        int i2 = typedBlock.length - 3;
        if (i2 < 0) {
            if (i2 == -1) {
                bArr[i] = 0;
            }
            bArr[(i + typedBlock.length) - 1] = -65;
            typedBlock.incoming = 1;
            recordStackMap(typedBlock, 0);
            return;
        }
        typedBlock.incoming = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i + i3] = 0;
        }
        int i4 = i + i2;
        bArr[i4] = -89;
        ByteArray.write16bit(-i2, bArr, i4 + 1);
    }

    private void fixTypes(byte[] bArr, TypedBlock[] typedBlockArr) throws NotFoundException, BadBytecode {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (TypedBlock typedBlock : typedBlockArr) {
            if (typedBlock.alreadySet()) {
                int length = typedBlock.localsTypes.length;
                for (int i2 = 0; i2 < length; i2++) {
                    i = typedBlock.localsTypes[i2].dfs(arrayList, i, this.classPool);
                }
                int i3 = typedBlock.stackTop;
                for (int i4 = 0; i4 < i3; i4++) {
                    i = typedBlock.stackTypes[i4].dfs(arrayList, i, this.classPool);
                }
            }
        }
    }

    public StackMapTable toStackMap(TypedBlock[] typedBlockArr) {
        StackMapTable.Writer writer = new StackMapTable.Writer(32);
        int length = typedBlockArr.length;
        TypedBlock typedBlock = typedBlockArr[0];
        int i = typedBlock.length;
        if (typedBlock.incoming > 0) {
            writer.sameFrame(0);
            i--;
        }
        TypedBlock typedBlock2 = typedBlock;
        int i2 = i;
        for (int i3 = 1; i3 < length; i3++) {
            TypedBlock typedBlock3 = typedBlockArr[i3];
            if (isTarget(typedBlock3, typedBlockArr[i3 - 1])) {
                typedBlock3.resetNumLocals();
                toStackMapBody(writer, typedBlock3, stackMapDiff(typedBlock2.numLocals, typedBlock2.localsTypes, typedBlock3.numLocals, typedBlock3.localsTypes), i2, typedBlock2);
                i2 = typedBlock3.length - 1;
                typedBlock2 = typedBlock3;
            } else if (typedBlock3.incoming == 0) {
                writer.sameFrame(i2);
                i2 = typedBlock3.length - 1;
            } else {
                i2 += typedBlock3.length;
            }
        }
        return writer.toStackMapTable(this.cpool);
    }

    private boolean isTarget(TypedBlock typedBlock, TypedBlock typedBlock2) {
        int i = typedBlock.incoming;
        if (i > 1) {
            return true;
        }
        if (i < 1) {
            return false;
        }
        return typedBlock2.stop;
    }

    private void toStackMapBody(StackMapTable.Writer writer, TypedBlock typedBlock, int i, int i2, TypedBlock typedBlock2) {
        int i3 = typedBlock.stackTop;
        if (i3 == 0) {
            if (i == 0) {
                writer.sameFrame(i2);
                return;
            } else if (i < 0 && i >= -3) {
                writer.chopFrame(i2, -i);
                return;
            } else if (i > 0 && i <= 3) {
                int[] iArr = new int[i];
                writer.appendFrame(i2, fillStackMap(typedBlock.numLocals - typedBlock2.numLocals, typedBlock2.numLocals, iArr, typedBlock.localsTypes), iArr);
                return;
            }
        } else if (i3 == 1 && i == 0) {
            TypeData typeData = typedBlock.stackTypes[0];
            writer.sameLocals(i2, typeData.getTypeTag(), typeData.getTypeData(this.cpool));
            return;
        } else if (i3 == 2 && i == 0) {
            TypeData typeData2 = typedBlock.stackTypes[0];
            if (typeData2.is2WordType()) {
                writer.sameLocals(i2, typeData2.getTypeTag(), typeData2.getTypeData(this.cpool));
                return;
            }
        }
        int[] iArr2 = new int[i3];
        int[] fillStackMap = fillStackMap(i3, 0, iArr2, typedBlock.stackTypes);
        int[] iArr3 = new int[typedBlock.numLocals];
        writer.fullFrame(i2, fillStackMap(typedBlock.numLocals, 0, iArr3, typedBlock.localsTypes), iArr3, fillStackMap, iArr2);
    }

    private int[] fillStackMap(int i, int i2, int[] iArr, TypeData[] typeDataArr) {
        int diffSize = diffSize(typeDataArr, i2, i2 + i);
        ConstPool constPool = this.cpool;
        int[] iArr2 = new int[diffSize];
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            TypeData typeData = typeDataArr[i2 + i3];
            iArr2[i4] = typeData.getTypeTag();
            iArr[i4] = typeData.getTypeData(constPool);
            if (typeData.is2WordType()) {
                i3++;
            }
            i4++;
            i3++;
        }
        return iArr2;
    }

    private static int stackMapDiff(int i, TypeData[] typeDataArr, int i2, TypeData[] typeDataArr2) {
        int i3 = i2 - i;
        int i4 = i3 > 0 ? i : i2;
        if (stackMapEq(typeDataArr, typeDataArr2, i4)) {
            if (i3 > 0) {
                return diffSize(typeDataArr2, i4, i2);
            }
            return -diffSize(typeDataArr, i4, i);
        }
        return -100;
    }

    private static boolean stackMapEq(TypeData[] typeDataArr, TypeData[] typeDataArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!typeDataArr[i2].eq(typeDataArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    private static int diffSize(TypeData[] typeDataArr, int i, int i2) {
        int i3 = 0;
        while (i < i2) {
            int i4 = i + 1;
            i3++;
            if (typeDataArr[i].is2WordType()) {
                i4++;
            }
            i = i4;
        }
        return i3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v8 */
    public StackMap toStackMap2(ConstPool constPool, TypedBlock[] typedBlockArr) {
        StackMap.Writer writer = new StackMap.Writer();
        int length = typedBlockArr.length;
        boolean[] zArr = new boolean[length];
        int i = 1;
        boolean z = typedBlockArr[0].incoming > 0;
        zArr[0] = z;
        int i2 = z;
        while (i < length) {
            TypedBlock typedBlock = typedBlockArr[i];
            boolean isTarget = isTarget(typedBlock, typedBlockArr[i - 1]);
            zArr[i] = isTarget;
            if (isTarget) {
                typedBlock.resetNumLocals();
                i2++;
            }
            i++;
            i2 = i2;
        }
        if (i2 == 0) {
            return null;
        }
        writer.write16bit(i2);
        for (int i3 = 0; i3 < length; i3++) {
            if (zArr[i3]) {
                writeStackFrame(writer, constPool, typedBlockArr[i3].position, typedBlockArr[i3]);
            }
        }
        return writer.toStackMap(constPool);
    }

    private void writeStackFrame(StackMap.Writer writer, ConstPool constPool, int i, TypedBlock typedBlock) {
        writer.write16bit(i);
        writeVerifyTypeInfo(writer, constPool, typedBlock.localsTypes, typedBlock.numLocals);
        writeVerifyTypeInfo(writer, constPool, typedBlock.stackTypes, typedBlock.stackTop);
    }

    private void writeVerifyTypeInfo(StackMap.Writer writer, ConstPool constPool, TypeData[] typeDataArr, int i) {
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            TypeData typeData = typeDataArr[i3];
            if (typeData != null && typeData.is2WordType()) {
                i4++;
                i3++;
            }
            i3++;
        }
        writer.write16bit(i - i4);
        while (i2 < i) {
            TypeData typeData2 = typeDataArr[i2];
            writer.writeVerifyTypeInfo(typeData2.getTypeTag(), typeData2.getTypeData(constPool));
            if (typeData2.is2WordType()) {
                i2++;
            }
            i2++;
        }
    }
}
