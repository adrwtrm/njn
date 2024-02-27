package javassist.bytecode.stackmap;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.stackmap.BasicBlock;
import javassist.bytecode.stackmap.TypeData;

/* loaded from: classes2.dex */
public class TypedBlock extends BasicBlock {
    public TypeData[] localsTypes;
    public int numLocals;
    public int stackTop;
    public TypeData[] stackTypes;

    public static TypedBlock[] makeBlocks(MethodInfo methodInfo, CodeAttribute codeAttribute, boolean z) throws BadBytecode {
        TypedBlock[] typedBlockArr = (TypedBlock[]) new Maker().make(methodInfo);
        if (z && typedBlockArr.length < 2 && (typedBlockArr.length == 0 || typedBlockArr[0].incoming == 0)) {
            return null;
        }
        typedBlockArr[0].initFirstBlock(codeAttribute.getMaxStack(), codeAttribute.getMaxLocals(), methodInfo.getConstPool().getClassName(), methodInfo.getDescriptor(), (methodInfo.getAccessFlags() & 8) != 0, methodInfo.isConstructor());
        return typedBlockArr;
    }

    protected TypedBlock(int i) {
        super(i);
        this.localsTypes = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // javassist.bytecode.stackmap.BasicBlock
    public void toString2(StringBuffer stringBuffer) {
        super.toString2(stringBuffer);
        stringBuffer.append(",\n stack={");
        printTypes(stringBuffer, this.stackTop, this.stackTypes);
        stringBuffer.append("}, locals={");
        printTypes(stringBuffer, this.numLocals, this.localsTypes);
        stringBuffer.append('}');
    }

    private void printTypes(StringBuffer stringBuffer, int i, TypeData[] typeDataArr) {
        if (typeDataArr == null) {
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                stringBuffer.append(", ");
            }
            TypeData typeData = typeDataArr[i2];
            stringBuffer.append(typeData == null ? "<>" : typeData.toString());
        }
    }

    public boolean alreadySet() {
        return this.localsTypes != null;
    }

    public void setStackMap(int i, TypeData[] typeDataArr, int i2, TypeData[] typeDataArr2) throws BadBytecode {
        this.stackTop = i;
        this.stackTypes = typeDataArr;
        this.numLocals = i2;
        this.localsTypes = typeDataArr2;
    }

    public void resetNumLocals() {
        TypeData[] typeDataArr = this.localsTypes;
        if (typeDataArr != null) {
            int length = typeDataArr.length;
            while (length > 0 && this.localsTypes[length - 1].isBasicType() == TypeTag.TOP && (length <= 1 || !this.localsTypes[length - 2].is2WordType())) {
                length--;
            }
            this.numLocals = length;
        }
    }

    /* loaded from: classes2.dex */
    public static class Maker extends BasicBlock.Maker {
        @Override // javassist.bytecode.stackmap.BasicBlock.Maker
        protected BasicBlock makeBlock(int i) {
            return new TypedBlock(i);
        }

        @Override // javassist.bytecode.stackmap.BasicBlock.Maker
        protected BasicBlock[] makeArray(int i) {
            return new TypedBlock[i];
        }
    }

    void initFirstBlock(int i, int i2, String str, String str2, boolean z, boolean z2) throws BadBytecode {
        if (str2.charAt(0) != '(') {
            throw new BadBytecode("no method descriptor: " + str2);
        }
        this.stackTop = 0;
        this.stackTypes = TypeData.make(i);
        TypeData[] make = TypeData.make(i2);
        if (z2) {
            make[0] = new TypeData.UninitThis(str);
        } else if (!z) {
            make[0] = new TypeData.ClassName(str);
        }
        int i3 = z ? -1 : 0;
        int i4 = 1;
        while (true) {
            i3++;
            try {
                i4 = descToTag(str2, i4, i3, make);
                if (i4 > 0) {
                    if (make[i3].is2WordType()) {
                        i3++;
                        make[i3] = TypeTag.TOP;
                    }
                } else {
                    this.numLocals = i3;
                    this.localsTypes = make;
                    return;
                }
            } catch (StringIndexOutOfBoundsException unused) {
                throw new BadBytecode("bad method descriptor: " + str2);
            }
        }
    }

    private static int descToTag(String str, int i, int i2, TypeData[] typeDataArr) throws BadBytecode {
        char charAt = str.charAt(i);
        int i3 = 0;
        if (charAt == ')') {
            return 0;
        }
        int i4 = i;
        while (charAt == '[') {
            i3++;
            i4++;
            charAt = str.charAt(i4);
        }
        if (charAt == 'L') {
            int indexOf = str.indexOf(59, i4 + 1);
            if (i3 > 0) {
                int i5 = indexOf + 1;
                typeDataArr[i2] = new TypeData.ClassName(str.substring(i, i5));
                return i5;
            }
            int i6 = indexOf + 1;
            typeDataArr[i2] = new TypeData.ClassName(str.substring(i + 1, i6 - 1).replace('/', '.'));
            return i6;
        } else if (i3 > 0) {
            int i7 = i4 + 1;
            typeDataArr[i2] = new TypeData.ClassName(str.substring(i, i7));
            return i7;
        } else {
            TypeData primitiveTag = toPrimitiveTag(charAt);
            if (primitiveTag == null) {
                throw new BadBytecode("bad method descriptor: " + str);
            }
            typeDataArr[i2] = primitiveTag;
            return i4 + 1;
        }
    }

    private static TypeData toPrimitiveTag(char c) {
        if (c != 'F') {
            if (c != 'S' && c != 'Z' && c != 'I') {
                if (c == 'J') {
                    return TypeTag.LONG;
                }
                switch (c) {
                    case 'B':
                    case 'C':
                        break;
                    case 'D':
                        return TypeTag.DOUBLE;
                    default:
                        return null;
                }
            }
            return TypeTag.INTEGER;
        }
        return TypeTag.FLOAT;
    }

    public static String getRetType(String str) {
        int indexOf = str.indexOf(41);
        if (indexOf < 0) {
            return "java.lang.Object";
        }
        int i = indexOf + 1;
        char charAt = str.charAt(i);
        if (charAt == '[') {
            return str.substring(i);
        }
        return charAt == 'L' ? str.substring(indexOf + 2, str.length() - 1).replace('/', '.') : "java.lang.Object";
    }
}
