package javassist.bytecode.analysis;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

/* loaded from: classes2.dex */
public class Executor implements Opcode {
    private final Type CLASS_TYPE;
    private final Type STRING_TYPE;
    private final Type THROWABLE_TYPE;
    private final ClassPool classPool;
    private final ConstPool constPool;
    private int lastPos;

    public Executor(ClassPool classPool, ConstPool constPool) {
        this.constPool = constPool;
        this.classPool = classPool;
        try {
            this.STRING_TYPE = getType("java.lang.String");
            this.CLASS_TYPE = getType("java.lang.Class");
            this.THROWABLE_TYPE = getType("java.lang.Throwable");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(MethodInfo methodInfo, int i, CodeIterator codeIterator, Frame frame, Subroutine subroutine) throws BadBytecode {
        this.lastPos = i;
        int byteAt = codeIterator.byteAt(i);
        switch (byteAt) {
            case 1:
                frame.push(Type.UNINIT);
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                frame.push(Type.INTEGER);
                return;
            case 9:
            case 10:
                frame.push(Type.LONG);
                frame.push(Type.TOP);
                return;
            case 11:
            case 12:
            case 13:
                frame.push(Type.FLOAT);
                return;
            case 14:
            case 15:
                frame.push(Type.DOUBLE);
                frame.push(Type.TOP);
                return;
            case 16:
            case 17:
                frame.push(Type.INTEGER);
                return;
            case 18:
                evalLDC(codeIterator.byteAt(i + 1), frame);
                return;
            case 19:
            case 20:
                evalLDC(codeIterator.u16bitAt(i + 1), frame);
                return;
            case 21:
                evalLoad(Type.INTEGER, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 22:
                evalLoad(Type.LONG, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 23:
                evalLoad(Type.FLOAT, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 24:
                evalLoad(Type.DOUBLE, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 25:
                evalLoad(Type.OBJECT, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 26:
            case 27:
            case 28:
            case 29:
                evalLoad(Type.INTEGER, byteAt - 26, frame, subroutine);
                return;
            case 30:
            case 31:
            case 32:
            case 33:
                evalLoad(Type.LONG, byteAt - 30, frame, subroutine);
                return;
            case 34:
            case 35:
            case 36:
            case 37:
                evalLoad(Type.FLOAT, byteAt - 34, frame, subroutine);
                return;
            case 38:
            case 39:
            case 40:
            case 41:
                evalLoad(Type.DOUBLE, byteAt - 38, frame, subroutine);
                return;
            case 42:
            case 43:
            case 44:
            case 45:
                evalLoad(Type.OBJECT, byteAt - 42, frame, subroutine);
                return;
            case 46:
                evalArrayLoad(Type.INTEGER, frame);
                return;
            case 47:
                evalArrayLoad(Type.LONG, frame);
                return;
            case 48:
                evalArrayLoad(Type.FLOAT, frame);
                return;
            case 49:
                evalArrayLoad(Type.DOUBLE, frame);
                return;
            case 50:
                evalArrayLoad(Type.OBJECT, frame);
                return;
            case 51:
            case 52:
            case 53:
                evalArrayLoad(Type.INTEGER, frame);
                return;
            case 54:
                evalStore(Type.INTEGER, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 55:
                evalStore(Type.LONG, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 56:
                evalStore(Type.FLOAT, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 57:
                evalStore(Type.DOUBLE, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 58:
                evalStore(Type.OBJECT, codeIterator.byteAt(i + 1), frame, subroutine);
                return;
            case 59:
            case 60:
            case 61:
            case 62:
                evalStore(Type.INTEGER, byteAt - 59, frame, subroutine);
                return;
            case 63:
            case 64:
            case 65:
            case 66:
                evalStore(Type.LONG, byteAt - 63, frame, subroutine);
                return;
            case 67:
            case 68:
            case 69:
            case 70:
                evalStore(Type.FLOAT, byteAt - 67, frame, subroutine);
                return;
            case 71:
            case 72:
            case 73:
            case 74:
                evalStore(Type.DOUBLE, byteAt - 71, frame, subroutine);
                return;
            case 75:
            case 76:
            case 77:
            case 78:
                evalStore(Type.OBJECT, byteAt - 75, frame, subroutine);
                return;
            case 79:
                evalArrayStore(Type.INTEGER, frame);
                return;
            case 80:
                evalArrayStore(Type.LONG, frame);
                return;
            case 81:
                evalArrayStore(Type.FLOAT, frame);
                return;
            case 82:
                evalArrayStore(Type.DOUBLE, frame);
                return;
            case 83:
                evalArrayStore(Type.OBJECT, frame);
                return;
            case 84:
            case 85:
            case 86:
                evalArrayStore(Type.INTEGER, frame);
                return;
            case 87:
                if (frame.pop() == Type.TOP) {
                    throw new BadBytecode("POP can not be used with a category 2 value, pos = " + i);
                }
                return;
            case 88:
                frame.pop();
                frame.pop();
                return;
            case 89:
                if (frame.peek() == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + i);
                }
                frame.push(frame.peek());
                return;
            case 90:
            case 91:
                Type peek = frame.peek();
                if (peek == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + i);
                }
                int topIndex = frame.getTopIndex();
                int i2 = (topIndex - (byteAt - 90)) - 1;
                frame.push(peek);
                while (topIndex > i2) {
                    frame.setStack(topIndex, frame.getStack(topIndex - 1));
                    topIndex--;
                }
                frame.setStack(i2, peek);
                return;
            case 92:
                frame.push(frame.getStack(frame.getTopIndex() - 1));
                frame.push(frame.getStack(frame.getTopIndex() - 1));
                return;
            case 93:
            case 94:
                int topIndex2 = frame.getTopIndex();
                int i3 = (topIndex2 - (byteAt - 93)) - 1;
                Type stack = frame.getStack(frame.getTopIndex() - 1);
                Type peek2 = frame.peek();
                frame.push(stack);
                frame.push(peek2);
                while (topIndex2 > i3) {
                    frame.setStack(topIndex2, frame.getStack(topIndex2 - 2));
                    topIndex2--;
                }
                frame.setStack(i3, peek2);
                frame.setStack(i3 - 1, stack);
                return;
            case 95:
                Type pop = frame.pop();
                Type pop2 = frame.pop();
                if (pop.getSize() == 2 || pop2.getSize() == 2) {
                    throw new BadBytecode("Swap can not be used with category 2 values, pos = " + i);
                }
                frame.push(pop);
                frame.push(pop2);
                return;
            case 96:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 97:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 98:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 99:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 100:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 101:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 102:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 103:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 104:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 105:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 106:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 107:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 108:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 109:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 110:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 111:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 112:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 113:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 114:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 115:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 116:
                verifyAssignable(Type.INTEGER, simplePeek(frame));
                return;
            case 117:
                verifyAssignable(Type.LONG, simplePeek(frame));
                return;
            case 118:
                verifyAssignable(Type.FLOAT, simplePeek(frame));
                return;
            case 119:
                verifyAssignable(Type.DOUBLE, simplePeek(frame));
                return;
            case 120:
                evalShift(Type.INTEGER, frame);
                return;
            case 121:
                evalShift(Type.LONG, frame);
                return;
            case 122:
                evalShift(Type.INTEGER, frame);
                return;
            case 123:
                evalShift(Type.LONG, frame);
                return;
            case 124:
                evalShift(Type.INTEGER, frame);
                return;
            case 125:
                evalShift(Type.LONG, frame);
                return;
            case 126:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 127:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 128:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 129:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 130:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 131:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 132:
                int byteAt2 = codeIterator.byteAt(i + 1);
                verifyAssignable(Type.INTEGER, frame.getLocal(byteAt2));
                access(byteAt2, Type.INTEGER, subroutine);
                return;
            case 133:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                simplePush(Type.LONG, frame);
                return;
            case 134:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                simplePush(Type.FLOAT, frame);
                return;
            case 135:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                simplePush(Type.DOUBLE, frame);
                return;
            case 136:
                verifyAssignable(Type.LONG, simplePop(frame));
                simplePush(Type.INTEGER, frame);
                return;
            case 137:
                verifyAssignable(Type.LONG, simplePop(frame));
                simplePush(Type.FLOAT, frame);
                return;
            case 138:
                verifyAssignable(Type.LONG, simplePop(frame));
                simplePush(Type.DOUBLE, frame);
                return;
            case 139:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                simplePush(Type.INTEGER, frame);
                return;
            case 140:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                simplePush(Type.LONG, frame);
                return;
            case 141:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                simplePush(Type.DOUBLE, frame);
                return;
            case 142:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                simplePush(Type.INTEGER, frame);
                return;
            case 143:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                simplePush(Type.LONG, frame);
                return;
            case 144:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                simplePush(Type.FLOAT, frame);
                return;
            case 145:
            case 146:
            case 147:
                verifyAssignable(Type.INTEGER, frame.peek());
                return;
            case 148:
                verifyAssignable(Type.LONG, simplePop(frame));
                verifyAssignable(Type.LONG, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 149:
            case 150:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                verifyAssignable(Type.FLOAT, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 151:
            case 152:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                return;
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                verifyAssignable(Type.INTEGER, simplePop(frame));
                return;
            case 165:
            case 166:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                verifyAssignable(Type.OBJECT, simplePop(frame));
                return;
            case 167:
            case 177:
            case 200:
            default:
                return;
            case 168:
                frame.push(Type.RETURN_ADDRESS);
                return;
            case 169:
                verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(codeIterator.byteAt(i + 1)));
                return;
            case 170:
            case 171:
            case 172:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                return;
            case 173:
                verifyAssignable(Type.LONG, simplePop(frame));
                return;
            case 174:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                return;
            case 175:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                return;
            case 176:
                try {
                    verifyAssignable(Type.get(Descriptor.getReturnType(methodInfo.getDescriptor(), this.classPool)), simplePop(frame));
                    return;
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }
            case 178:
                evalGetField(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 179:
                evalPutField(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 180:
                evalGetField(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 181:
                evalPutField(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 182:
            case 183:
            case 184:
                evalInvokeMethod(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 185:
                evalInvokeIntfMethod(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 186:
                evalInvokeDynamic(byteAt, codeIterator.u16bitAt(i + 1), frame);
                return;
            case 187:
                frame.push(resolveClassInfo(this.constPool.getClassInfo(codeIterator.u16bitAt(i + 1))));
                return;
            case 188:
                evalNewArray(i, codeIterator, frame);
                return;
            case 189:
                evalNewObjectArray(i, codeIterator, frame);
                return;
            case 190:
                Type simplePop = simplePop(frame);
                if (!simplePop.isArray() && simplePop != Type.UNINIT) {
                    throw new BadBytecode("Array length passed a non-array [pos = " + i + "]: " + simplePop);
                }
                frame.push(Type.INTEGER);
                return;
            case 191:
                verifyAssignable(this.THROWABLE_TYPE, simplePop(frame));
                return;
            case 192:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                frame.push(typeFromDesc(this.constPool.getClassInfoByDescriptor(codeIterator.u16bitAt(i + 1))));
                return;
            case 193:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 194:
            case 195:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                return;
            case Opcode.WIDE /* 196 */:
                evalWide(i, codeIterator, frame, subroutine);
                return;
            case 197:
                evalNewObjectArray(i, codeIterator, frame);
                return;
            case 198:
            case 199:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                return;
            case Opcode.JSR_W /* 201 */:
                frame.push(Type.RETURN_ADDRESS);
                return;
        }
    }

    private Type zeroExtend(Type type) {
        return (type == Type.SHORT || type == Type.BYTE || type == Type.CHAR || type == Type.BOOLEAN) ? Type.INTEGER : type;
    }

    private void evalArrayLoad(Type type, Frame frame) throws BadBytecode {
        Type pop = frame.pop();
        Type pop2 = frame.pop();
        if (pop2 == Type.UNINIT) {
            verifyAssignable(Type.INTEGER, pop);
            if (type == Type.OBJECT) {
                simplePush(Type.UNINIT, frame);
                return;
            } else {
                simplePush(type, frame);
                return;
            }
        }
        Type component = pop2.getComponent();
        if (component == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
        }
        Type zeroExtend = zeroExtend(component);
        verifyAssignable(type, zeroExtend);
        verifyAssignable(Type.INTEGER, pop);
        simplePush(zeroExtend, frame);
    }

    private void evalArrayStore(Type type, Frame frame) throws BadBytecode {
        Type simplePop = simplePop(frame);
        Type pop = frame.pop();
        Type pop2 = frame.pop();
        if (pop2 == Type.UNINIT) {
            verifyAssignable(Type.INTEGER, pop);
            return;
        }
        Type component = pop2.getComponent();
        if (component == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
        }
        Type zeroExtend = zeroExtend(component);
        verifyAssignable(type, zeroExtend);
        verifyAssignable(Type.INTEGER, pop);
        if (type == Type.OBJECT) {
            verifyAssignable(type, simplePop);
        } else {
            verifyAssignable(zeroExtend, simplePop);
        }
    }

    private void evalBinaryMath(Type type, Frame frame) throws BadBytecode {
        Type simplePop = simplePop(frame);
        Type simplePop2 = simplePop(frame);
        verifyAssignable(type, simplePop);
        verifyAssignable(type, simplePop2);
        simplePush(simplePop2, frame);
    }

    private void evalGetField(int i, int i2, Frame frame) throws BadBytecode {
        Type zeroExtend = zeroExtend(typeFromDesc(this.constPool.getFieldrefType(i2)));
        if (i == 180) {
            verifyAssignable(resolveClassInfo(this.constPool.getFieldrefClassName(i2)), simplePop(frame));
        }
        simplePush(zeroExtend, frame);
    }

    private void evalInvokeIntfMethod(int i, int i2, Frame frame) throws BadBytecode {
        String interfaceMethodrefType = this.constPool.getInterfaceMethodrefType(i2);
        Type[] paramTypesFromDesc = paramTypesFromDesc(interfaceMethodrefType);
        int length = paramTypesFromDesc.length;
        while (length > 0) {
            length--;
            verifyAssignable(zeroExtend(paramTypesFromDesc[length]), simplePop(frame));
        }
        verifyAssignable(resolveClassInfo(this.constPool.getInterfaceMethodrefClassName(i2)), simplePop(frame));
        Type returnTypeFromDesc = returnTypeFromDesc(interfaceMethodrefType);
        if (returnTypeFromDesc != Type.VOID) {
            simplePush(zeroExtend(returnTypeFromDesc), frame);
        }
    }

    private void evalInvokeMethod(int i, int i2, Frame frame) throws BadBytecode {
        String methodrefType = this.constPool.getMethodrefType(i2);
        Type[] paramTypesFromDesc = paramTypesFromDesc(methodrefType);
        int length = paramTypesFromDesc.length;
        while (length > 0) {
            length--;
            verifyAssignable(zeroExtend(paramTypesFromDesc[length]), simplePop(frame));
        }
        if (i != 184) {
            verifyAssignable(resolveClassInfo(this.constPool.getMethodrefClassName(i2)), simplePop(frame));
        }
        Type returnTypeFromDesc = returnTypeFromDesc(methodrefType);
        if (returnTypeFromDesc != Type.VOID) {
            simplePush(zeroExtend(returnTypeFromDesc), frame);
        }
    }

    private void evalInvokeDynamic(int i, int i2, Frame frame) throws BadBytecode {
        String invokeDynamicType = this.constPool.getInvokeDynamicType(i2);
        Type[] paramTypesFromDesc = paramTypesFromDesc(invokeDynamicType);
        int length = paramTypesFromDesc.length;
        while (length > 0) {
            length--;
            verifyAssignable(zeroExtend(paramTypesFromDesc[length]), simplePop(frame));
        }
        Type returnTypeFromDesc = returnTypeFromDesc(invokeDynamicType);
        if (returnTypeFromDesc != Type.VOID) {
            simplePush(zeroExtend(returnTypeFromDesc), frame);
        }
    }

    private void evalLDC(int i, Frame frame) throws BadBytecode {
        Type type;
        int tag = this.constPool.getTag(i);
        switch (tag) {
            case 3:
                type = Type.INTEGER;
                break;
            case 4:
                type = Type.FLOAT;
                break;
            case 5:
                type = Type.LONG;
                break;
            case 6:
                type = Type.DOUBLE;
                break;
            case 7:
                type = this.CLASS_TYPE;
                break;
            case 8:
                type = this.STRING_TYPE;
                break;
            default:
                throw new BadBytecode("bad LDC [pos = " + this.lastPos + "]: " + tag);
        }
        simplePush(type, frame);
    }

    private void evalLoad(Type type, int i, Frame frame, Subroutine subroutine) throws BadBytecode {
        Type local = frame.getLocal(i);
        verifyAssignable(type, local);
        simplePush(local, frame);
        access(i, local, subroutine);
    }

    private void evalNewArray(int i, CodeIterator codeIterator, Frame frame) throws BadBytecode {
        Type type;
        verifyAssignable(Type.INTEGER, simplePop(frame));
        int byteAt = codeIterator.byteAt(i + 1);
        switch (byteAt) {
            case 4:
                type = getType("boolean[]");
                break;
            case 5:
                type = getType("char[]");
                break;
            case 6:
                type = getType("float[]");
                break;
            case 7:
                type = getType("double[]");
                break;
            case 8:
                type = getType("byte[]");
                break;
            case 9:
                type = getType("short[]");
                break;
            case 10:
                type = getType("int[]");
                break;
            case 11:
                type = getType("long[]");
                break;
            default:
                throw new BadBytecode("Invalid array type [pos = " + i + "]: " + byteAt);
        }
        frame.push(type);
    }

    private void evalNewObjectArray(int i, CodeIterator codeIterator, Frame frame) throws BadBytecode {
        int i2;
        String name = resolveClassInfo(this.constPool.getClassInfo(codeIterator.u16bitAt(i + 1))).getCtClass().getName();
        if (codeIterator.byteAt(i) == 197) {
            i2 = codeIterator.byteAt(i + 3);
        } else {
            name = name + "[]";
            i2 = 1;
        }
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                verifyAssignable(Type.INTEGER, simplePop(frame));
                i2 = i3;
            } else {
                simplePush(getType(name), frame);
                return;
            }
        }
    }

    private void evalPutField(int i, int i2, Frame frame) throws BadBytecode {
        verifyAssignable(zeroExtend(typeFromDesc(this.constPool.getFieldrefType(i2))), simplePop(frame));
        if (i == 181) {
            verifyAssignable(resolveClassInfo(this.constPool.getFieldrefClassName(i2)), simplePop(frame));
        }
    }

    private void evalShift(Type type, Frame frame) throws BadBytecode {
        Type simplePop = simplePop(frame);
        Type simplePop2 = simplePop(frame);
        verifyAssignable(Type.INTEGER, simplePop);
        verifyAssignable(type, simplePop2);
        simplePush(simplePop2, frame);
    }

    private void evalStore(Type type, int i, Frame frame, Subroutine subroutine) throws BadBytecode {
        Type simplePop = simplePop(frame);
        if (type != Type.OBJECT || simplePop != Type.RETURN_ADDRESS) {
            verifyAssignable(type, simplePop);
        }
        simpleSetLocal(i, simplePop, frame);
        access(i, simplePop, subroutine);
    }

    private void evalWide(int i, CodeIterator codeIterator, Frame frame, Subroutine subroutine) throws BadBytecode {
        int byteAt = codeIterator.byteAt(i + 1);
        int u16bitAt = codeIterator.u16bitAt(i + 2);
        if (byteAt == 132) {
            verifyAssignable(Type.INTEGER, frame.getLocal(u16bitAt));
        } else if (byteAt != 169) {
            switch (byteAt) {
                case 21:
                    evalLoad(Type.INTEGER, u16bitAt, frame, subroutine);
                    return;
                case 22:
                    evalLoad(Type.LONG, u16bitAt, frame, subroutine);
                    return;
                case 23:
                    evalLoad(Type.FLOAT, u16bitAt, frame, subroutine);
                    return;
                case 24:
                    evalLoad(Type.DOUBLE, u16bitAt, frame, subroutine);
                    return;
                case 25:
                    evalLoad(Type.OBJECT, u16bitAt, frame, subroutine);
                    return;
                default:
                    switch (byteAt) {
                        case 54:
                            evalStore(Type.INTEGER, u16bitAt, frame, subroutine);
                            return;
                        case 55:
                            evalStore(Type.LONG, u16bitAt, frame, subroutine);
                            return;
                        case 56:
                            evalStore(Type.FLOAT, u16bitAt, frame, subroutine);
                            return;
                        case 57:
                            evalStore(Type.DOUBLE, u16bitAt, frame, subroutine);
                            return;
                        case 58:
                            evalStore(Type.OBJECT, u16bitAt, frame, subroutine);
                            return;
                        default:
                            throw new BadBytecode("Invalid WIDE operand [pos = " + i + "]: " + byteAt);
                    }
            }
        } else {
            verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(u16bitAt));
        }
    }

    private Type getType(String str) throws BadBytecode {
        try {
            return Type.get(this.classPool.get(str));
        } catch (NotFoundException unused) {
            throw new BadBytecode("Could not find class [pos = " + this.lastPos + "]: " + str);
        }
    }

    private Type[] paramTypesFromDesc(String str) throws BadBytecode {
        try {
            CtClass[] parameterTypes = Descriptor.getParameterTypes(str, this.classPool);
            if (parameterTypes == null) {
                throw new BadBytecode("Could not obtain parameters for descriptor [pos = " + this.lastPos + "]: " + str);
            }
            int length = parameterTypes.length;
            Type[] typeArr = new Type[length];
            for (int i = 0; i < length; i++) {
                typeArr[i] = Type.get(parameterTypes[i]);
            }
            return typeArr;
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private Type returnTypeFromDesc(String str) throws BadBytecode {
        try {
            CtClass returnType = Descriptor.getReturnType(str, this.classPool);
            if (returnType == null) {
                throw new BadBytecode("Could not obtain return type for descriptor [pos = " + this.lastPos + "]: " + str);
            }
            return Type.get(returnType);
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private Type simplePeek(Frame frame) {
        Type peek = frame.peek();
        return peek == Type.TOP ? frame.getStack(frame.getTopIndex() - 1) : peek;
    }

    private Type simplePop(Frame frame) {
        Type pop = frame.pop();
        return pop == Type.TOP ? frame.pop() : pop;
    }

    private void simplePush(Type type, Frame frame) {
        frame.push(type);
        if (type.getSize() == 2) {
            frame.push(Type.TOP);
        }
    }

    private void access(int i, Type type, Subroutine subroutine) {
        if (subroutine == null) {
            return;
        }
        subroutine.access(i);
        if (type.getSize() == 2) {
            subroutine.access(i + 1);
        }
    }

    private void simpleSetLocal(int i, Type type, Frame frame) {
        frame.setLocal(i, type);
        if (type.getSize() == 2) {
            frame.setLocal(i + 1, Type.TOP);
        }
    }

    private Type resolveClassInfo(String str) throws BadBytecode {
        CtClass ctClass;
        try {
            if (str.charAt(0) == '[') {
                ctClass = Descriptor.toCtClass(str, this.classPool);
            } else {
                ctClass = this.classPool.get(str);
            }
            if (ctClass == null) {
                throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + str);
            }
            return Type.get(ctClass);
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private Type typeFromDesc(String str) throws BadBytecode {
        try {
            CtClass ctClass = Descriptor.toCtClass(str, this.classPool);
            if (ctClass == null) {
                throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + str);
            }
            return Type.get(ctClass);
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private void verifyAssignable(Type type, Type type2) throws BadBytecode {
        if (!type.isAssignableFrom(type2)) {
            throw new BadBytecode("Expected type: " + type + " Got: " + type2 + " [pos = " + this.lastPos + "]");
        }
    }
}
