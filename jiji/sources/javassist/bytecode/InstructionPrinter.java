package javassist.bytecode;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.PrintStream;
import javassist.CtMethod;

/* loaded from: classes2.dex */
public class InstructionPrinter implements Opcode {
    private static final String[] opcodes = Mnemonic.OPCODE;
    private final PrintStream stream;

    public InstructionPrinter(PrintStream printStream) {
        this.stream = printStream;
    }

    public static void print(CtMethod ctMethod, PrintStream printStream) {
        new InstructionPrinter(printStream).print(ctMethod);
    }

    public void print(CtMethod ctMethod) {
        MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        ConstPool constPool = methodInfo2.getConstPool();
        CodeAttribute codeAttribute = methodInfo2.getCodeAttribute();
        if (codeAttribute == null) {
            return;
        }
        CodeIterator it = codeAttribute.iterator();
        while (it.hasNext()) {
            try {
                int next = it.next();
                this.stream.println(next + ": " + instructionString(it, next, constPool));
            } catch (BadBytecode e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String instructionString(CodeIterator codeIterator, int i, ConstPool constPool) {
        int byteAt = codeIterator.byteAt(i);
        String[] strArr = opcodes;
        if (byteAt > strArr.length || byteAt < 0) {
            throw new IllegalArgumentException("Invalid opcode, opcode: " + byteAt + " pos: " + i);
        }
        String str = strArr[byteAt];
        if (byteAt == 132) {
            return str + " " + codeIterator.byteAt(i + 1) + ", " + codeIterator.signedByteAt(i + 2);
        }
        if (byteAt != 192) {
            switch (byteAt) {
                case 16:
                    return str + " " + codeIterator.byteAt(i + 1);
                case 17:
                    return str + " " + codeIterator.s16bitAt(i + 1);
                case 18:
                    return str + " " + ldc(constPool, codeIterator.byteAt(i + 1));
                case 19:
                case 20:
                    return str + " " + ldc(constPool, codeIterator.u16bitAt(i + 1));
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                    break;
                default:
                    switch (byteAt) {
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                            break;
                        default:
                            switch (byteAt) {
                                case 153:
                                case 154:
                                case 155:
                                case 156:
                                case 157:
                                case 158:
                                case 159:
                                case 160:
                                case 161:
                                case 162:
                                case 163:
                                case 164:
                                case 165:
                                case 166:
                                    break;
                                case 167:
                                case 168:
                                    return str + " " + (codeIterator.s16bitAt(i + 1) + i);
                                case 169:
                                    return str + " " + codeIterator.byteAt(i + 1);
                                case 170:
                                    return tableSwitch(codeIterator, i);
                                case 171:
                                    return lookupSwitch(codeIterator, i);
                                default:
                                    switch (byteAt) {
                                        case 178:
                                        case 179:
                                        case 180:
                                        case 181:
                                            return str + " " + fieldInfo(constPool, codeIterator.u16bitAt(i + 1));
                                        case 182:
                                        case 183:
                                        case 184:
                                            return str + " " + methodInfo(constPool, codeIterator.u16bitAt(i + 1));
                                        case 185:
                                            return str + " " + interfaceMethodInfo(constPool, codeIterator.u16bitAt(i + 1));
                                        case 186:
                                            return str + " " + codeIterator.u16bitAt(i + 1);
                                        case 187:
                                            return str + " " + classInfo(constPool, codeIterator.u16bitAt(i + 1));
                                        case 188:
                                            return str + " " + arrayInfo(codeIterator.byteAt(i + 1));
                                        case 189:
                                            break;
                                        default:
                                            switch (byteAt) {
                                                case Opcode.WIDE /* 196 */:
                                                    return wide(codeIterator, i);
                                                case 197:
                                                    return str + " " + classInfo(constPool, codeIterator.u16bitAt(i + 1));
                                                case 198:
                                                case 199:
                                                    break;
                                                case 200:
                                                case Opcode.JSR_W /* 201 */:
                                                    return str + " " + (codeIterator.s32bitAt(i + 1) + i);
                                                default:
                                                    return str;
                                            }
                                    }
                            }
                            return str + " " + (codeIterator.s16bitAt(i + 1) + i);
                    }
            }
            return str + " " + codeIterator.byteAt(i + 1);
        }
        return str + " " + classInfo(constPool, codeIterator.u16bitAt(i + 1));
    }

    private static String wide(CodeIterator codeIterator, int i) {
        int byteAt = codeIterator.byteAt(i + 1);
        int u16bitAt = codeIterator.u16bitAt(i + 2);
        if (byteAt != 132 && byteAt != 169) {
            switch (byteAt) {
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                    break;
                default:
                    switch (byteAt) {
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                            break;
                        default:
                            throw new RuntimeException("Invalid WIDE operand");
                    }
            }
        }
        return opcodes[byteAt] + " " + u16bitAt;
    }

    private static String arrayInfo(int i) {
        switch (i) {
            case 4:
                return TypedValues.Custom.S_BOOLEAN;
            case 5:
                return "char";
            case 6:
                return TypedValues.Custom.S_FLOAT;
            case 7:
                return "double";
            case 8:
                return "byte";
            case 9:
                return "short";
            case 10:
                return "int";
            case 11:
                return "long";
            default:
                throw new RuntimeException("Invalid array type");
        }
    }

    private static String classInfo(ConstPool constPool, int i) {
        return "#" + i + " = Class " + constPool.getClassInfo(i);
    }

    private static String interfaceMethodInfo(ConstPool constPool, int i) {
        return "#" + i + " = Method " + constPool.getInterfaceMethodrefClassName(i) + "." + constPool.getInterfaceMethodrefName(i) + "(" + constPool.getInterfaceMethodrefType(i) + ")";
    }

    private static String methodInfo(ConstPool constPool, int i) {
        return "#" + i + " = Method " + constPool.getMethodrefClassName(i) + "." + constPool.getMethodrefName(i) + "(" + constPool.getMethodrefType(i) + ")";
    }

    private static String fieldInfo(ConstPool constPool, int i) {
        return "#" + i + " = Field " + constPool.getFieldrefClassName(i) + "." + constPool.getFieldrefName(i) + "(" + constPool.getFieldrefType(i) + ")";
    }

    private static String lookupSwitch(CodeIterator codeIterator, int i) {
        StringBuffer stringBuffer = new StringBuffer("lookupswitch {\n");
        int i2 = (i & (-4)) + 4;
        stringBuffer.append("\t\tdefault: ").append(codeIterator.s32bitAt(i2) + i).append("\n");
        int i3 = i2 + 4;
        int i4 = i3 + 4;
        int s32bitAt = (codeIterator.s32bitAt(i3) * 8) + i4;
        while (i4 < s32bitAt) {
            stringBuffer.append("\t\t").append(codeIterator.s32bitAt(i4)).append(": ").append(codeIterator.s32bitAt(i4 + 4) + i).append("\n");
            i4 += 8;
        }
        stringBuffer.setCharAt(stringBuffer.length() - 1, '}');
        return stringBuffer.toString();
    }

    private static String tableSwitch(CodeIterator codeIterator, int i) {
        StringBuffer stringBuffer = new StringBuffer("tableswitch {\n");
        int i2 = (i & (-4)) + 4;
        stringBuffer.append("\t\tdefault: ").append(codeIterator.s32bitAt(i2) + i).append("\n");
        int i3 = i2 + 4;
        int s32bitAt = codeIterator.s32bitAt(i3);
        int i4 = i3 + 4;
        int i5 = i4 + 4;
        int s32bitAt2 = (((codeIterator.s32bitAt(i4) - s32bitAt) + 1) * 4) + i5;
        while (i5 < s32bitAt2) {
            stringBuffer.append("\t\t").append(s32bitAt).append(": ").append(codeIterator.s32bitAt(i5) + i).append("\n");
            i5 += 4;
            s32bitAt++;
        }
        stringBuffer.setCharAt(stringBuffer.length() - 1, '}');
        return stringBuffer.toString();
    }

    private static String ldc(ConstPool constPool, int i) {
        int tag = constPool.getTag(i);
        switch (tag) {
            case 3:
                return "#" + i + " = int " + constPool.getIntegerInfo(i);
            case 4:
                return "#" + i + " = float " + constPool.getFloatInfo(i);
            case 5:
                return "#" + i + " = long " + constPool.getLongInfo(i);
            case 6:
                return "#" + i + " = int " + constPool.getDoubleInfo(i);
            case 7:
                return classInfo(constPool, i);
            case 8:
                return "#" + i + " = \"" + constPool.getStringInfo(i) + "\"";
            default:
                throw new RuntimeException("bad LDC: " + tag);
        }
    }
}
