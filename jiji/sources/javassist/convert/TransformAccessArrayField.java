package javassist.convert;

import javassist.CannotCompileException;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.analysis.Analyzer;
import javassist.bytecode.analysis.Frame;

/* loaded from: classes2.dex */
public final class TransformAccessArrayField extends Transformer {
    private Frame[] frames;
    private final String methodClassname;
    private final CodeConverter.ArrayAccessReplacementMethodNames names;
    private int offset;

    @Override // javassist.convert.Transformer
    public int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws BadBytecode {
        return i;
    }

    public TransformAccessArrayField(Transformer transformer, String str, CodeConverter.ArrayAccessReplacementMethodNames arrayAccessReplacementMethodNames) throws NotFoundException {
        super(transformer);
        this.methodClassname = str;
        this.names = arrayAccessReplacementMethodNames;
    }

    @Override // javassist.convert.Transformer
    public void initialize(ConstPool constPool, CtClass ctClass, MethodInfo methodInfo) throws CannotCompileException {
        CodeIterator it = methodInfo.getCodeAttribute().iterator();
        while (it.hasNext()) {
            try {
                int next = it.next();
                int byteAt = it.byteAt(next);
                if (byteAt == 50) {
                    initFrames(ctClass, methodInfo);
                }
                if (byteAt != 50 && byteAt != 51 && byteAt != 52 && byteAt != 49 && byteAt != 48 && byteAt != 46 && byteAt != 47 && byteAt != 53) {
                    if (byteAt == 83 || byteAt == 84 || byteAt == 85 || byteAt == 82 || byteAt == 81 || byteAt == 79 || byteAt == 80 || byteAt == 86) {
                        replace(constPool, it, next, byteAt, getStoreReplacementSignature(byteAt));
                    }
                }
                replace(constPool, it, next, byteAt, getLoadReplacementSignature(byteAt));
            } catch (Exception e) {
                throw new CannotCompileException(e);
            }
        }
    }

    @Override // javassist.convert.Transformer
    public void clean() {
        this.frames = null;
        this.offset = -1;
    }

    private Frame getFrame(int i) throws BadBytecode {
        return this.frames[i - this.offset];
    }

    private void initFrames(CtClass ctClass, MethodInfo methodInfo) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(ctClass, methodInfo);
            this.offset = 0;
        }
    }

    private int updatePos(int i, int i2) {
        int i3 = this.offset;
        if (i3 > -1) {
            this.offset = i3 + i2;
        }
        return i + i2;
    }

    private String getTopType(int i) throws BadBytecode {
        CtClass ctClass;
        Frame frame = getFrame(i);
        if (frame == null || (ctClass = frame.peek().getCtClass()) == null) {
            return null;
        }
        return Descriptor.toJvmName(ctClass);
    }

    private int replace(ConstPool constPool, CodeIterator codeIterator, int i, int i2, String str) throws BadBytecode {
        String methodName = getMethodName(i2);
        if (methodName != null) {
            String str2 = null;
            if (i2 == 50) {
                String topType = getTopType(codeIterator.lookAhead());
                if (topType == null) {
                    return i;
                }
                if (!"java/lang/Object".equals(topType)) {
                    str2 = topType;
                }
            }
            codeIterator.writeByte(0, i);
            CodeIterator.Gap insertGapAt = codeIterator.insertGapAt(i, str2 != null ? 5 : 2, false);
            int i3 = insertGapAt.position;
            int addMethodrefInfo = constPool.addMethodrefInfo(constPool.addClassInfo(this.methodClassname), methodName, str);
            codeIterator.writeByte(184, i3);
            codeIterator.write16bit(addMethodrefInfo, i3 + 1);
            if (str2 != null) {
                int addClassInfo = constPool.addClassInfo(str2);
                codeIterator.writeByte(192, i3 + 3);
                codeIterator.write16bit(addClassInfo, i3 + 4);
            }
            return updatePos(i3, insertGapAt.length);
        }
        return i;
    }

    private String getMethodName(int i) {
        String intRead;
        switch (i) {
            case 46:
                intRead = this.names.intRead();
                break;
            case 47:
                intRead = this.names.longRead();
                break;
            case 48:
                intRead = this.names.floatRead();
                break;
            case 49:
                intRead = this.names.doubleRead();
                break;
            case 50:
                intRead = this.names.objectRead();
                break;
            case 51:
                intRead = this.names.byteOrBooleanRead();
                break;
            case 52:
                intRead = this.names.charRead();
                break;
            case 53:
                intRead = this.names.shortRead();
                break;
            default:
                switch (i) {
                    case 79:
                        intRead = this.names.intWrite();
                        break;
                    case 80:
                        intRead = this.names.longWrite();
                        break;
                    case 81:
                        intRead = this.names.floatWrite();
                        break;
                    case 82:
                        intRead = this.names.doubleWrite();
                        break;
                    case 83:
                        intRead = this.names.objectWrite();
                        break;
                    case 84:
                        intRead = this.names.byteOrBooleanWrite();
                        break;
                    case 85:
                        intRead = this.names.charWrite();
                        break;
                    case 86:
                        intRead = this.names.shortWrite();
                        break;
                    default:
                        intRead = null;
                        break;
                }
        }
        if (intRead.equals("")) {
            return null;
        }
        return intRead;
    }

    private String getLoadReplacementSignature(int i) throws BadBytecode {
        switch (i) {
            case 46:
                return "(Ljava/lang/Object;I)I";
            case 47:
                return "(Ljava/lang/Object;I)J";
            case 48:
                return "(Ljava/lang/Object;I)F";
            case 49:
                return "(Ljava/lang/Object;I)D";
            case 50:
                return "(Ljava/lang/Object;I)Ljava/lang/Object;";
            case 51:
                return "(Ljava/lang/Object;I)B";
            case 52:
                return "(Ljava/lang/Object;I)C";
            case 53:
                return "(Ljava/lang/Object;I)S";
            default:
                throw new BadBytecode(i);
        }
    }

    private String getStoreReplacementSignature(int i) throws BadBytecode {
        switch (i) {
            case 79:
                return "(Ljava/lang/Object;II)V";
            case 80:
                return "(Ljava/lang/Object;IJ)V";
            case 81:
                return "(Ljava/lang/Object;IF)V";
            case 82:
                return "(Ljava/lang/Object;ID)V";
            case 83:
                return "(Ljava/lang/Object;ILjava/lang/Object;)V";
            case 84:
                return "(Ljava/lang/Object;IB)V";
            case 85:
                return "(Ljava/lang/Object;IC)V";
            case 86:
                return "(Ljava/lang/Object;IS)V";
            default:
                throw new BadBytecode(i);
        }
    }
}
