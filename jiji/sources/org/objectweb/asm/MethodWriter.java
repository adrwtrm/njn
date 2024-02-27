package org.objectweb.asm;

import javassist.bytecode.AnnotationDefaultAttribute;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.LineNumberAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodParametersAttribute;
import javassist.bytecode.Opcode;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.StackMap;
import javassist.bytecode.StackMapTable;
import javassist.bytecode.TypeAnnotationsAttribute;
import org.objectweb.asm.Attribute;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class MethodWriter extends MethodVisitor {
    static final int COMPUTE_ALL_FRAMES = 4;
    static final int COMPUTE_INSERTED_FRAMES = 3;
    static final int COMPUTE_MAX_STACK_AND_LOCAL = 1;
    static final int COMPUTE_MAX_STACK_AND_LOCAL_FROM_FRAMES = 2;
    static final int COMPUTE_NOTHING = 0;
    private static final int NA = 0;
    private static final int[] STACK_SIZE_DELTA = {0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, -1, -1, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -4, -3, -4, -3, -3, -3, -3, -1, -2, 1, 1, 1, 2, 2, 2, 0, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2, -1, -2, -1, -2, 0, 1, 0, 1, -1, -1, 0, 0, 1, 1, -1, 0, -1, 0, 0, 0, -3, -1, -1, -3, -3, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, 0, 1, 0, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, -1, -1, 0, 0};
    private final int accessFlags;
    private final ByteVector code;
    private final int compute;
    private Label currentBasicBlock;
    private int[] currentFrame;
    private int currentLocals;
    private ByteVector defaultValue;
    private final String descriptor;
    private final int descriptorIndex;
    private final int[] exceptionIndexTable;
    private Attribute firstAttribute;
    private Label firstBasicBlock;
    private Attribute firstCodeAttribute;
    private Handler firstHandler;
    private boolean hasAsmInstructions;
    private boolean hasSubroutines;
    private int invisibleAnnotableParameterCount;
    private Label lastBasicBlock;
    private int lastBytecodeOffset;
    private AnnotationWriter lastCodeRuntimeInvisibleTypeAnnotation;
    private AnnotationWriter lastCodeRuntimeVisibleTypeAnnotation;
    private Handler lastHandler;
    private AnnotationWriter lastRuntimeInvisibleAnnotation;
    private AnnotationWriter[] lastRuntimeInvisibleParameterAnnotations;
    private AnnotationWriter lastRuntimeInvisibleTypeAnnotation;
    private AnnotationWriter lastRuntimeVisibleAnnotation;
    private AnnotationWriter[] lastRuntimeVisibleParameterAnnotations;
    private AnnotationWriter lastRuntimeVisibleTypeAnnotation;
    private ByteVector lineNumberTable;
    private int lineNumberTableLength;
    private ByteVector localVariableTable;
    private int localVariableTableLength;
    private ByteVector localVariableTypeTable;
    private int localVariableTypeTableLength;
    private int maxLocals;
    private int maxRelativeStackSize;
    private int maxStack;
    private final String name;
    private final int nameIndex;
    private final int numberOfExceptions;
    private ByteVector parameters;
    private int parametersCount;
    private int[] previousFrame;
    private int previousFrameOffset;
    private int relativeStackSize;
    private final int signatureIndex;
    private int sourceLength;
    private int sourceOffset;
    private ByteVector stackMapTableEntries;
    private int stackMapTableNumberOfEntries;
    private final SymbolTable symbolTable;
    private int visibleAnnotableParameterCount;

    @Override // org.objectweb.asm.MethodVisitor
    public void visitCode() {
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitEnd() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MethodWriter(SymbolTable symbolTable, int i, String str, String str2, String str3, String[] strArr, int i2) {
        super(Opcodes.ASM9);
        this.code = new ByteVector();
        this.symbolTable = symbolTable;
        this.accessFlags = "<init>".equals(str) ? 262144 | i : i;
        this.nameIndex = symbolTable.addConstantUtf8(str);
        this.name = str;
        this.descriptorIndex = symbolTable.addConstantUtf8(str2);
        this.descriptor = str2;
        this.signatureIndex = str3 == null ? 0 : symbolTable.addConstantUtf8(str3);
        if (strArr != null && strArr.length > 0) {
            int length = strArr.length;
            this.numberOfExceptions = length;
            this.exceptionIndexTable = new int[length];
            for (int i3 = 0; i3 < this.numberOfExceptions; i3++) {
                this.exceptionIndexTable[i3] = symbolTable.addConstantClass(strArr[i3]).index;
            }
        } else {
            this.numberOfExceptions = 0;
            this.exceptionIndexTable = null;
        }
        this.compute = i2;
        if (i2 != 0) {
            int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str2) >> 2;
            argumentsAndReturnSizes = (i & 8) != 0 ? argumentsAndReturnSizes - 1 : argumentsAndReturnSizes;
            this.maxLocals = argumentsAndReturnSizes;
            this.currentLocals = argumentsAndReturnSizes;
            Label label = new Label();
            this.firstBasicBlock = label;
            visitLabel(label);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasFrames() {
        return this.stackMapTableNumberOfEntries > 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasAsmInstructions() {
        return this.hasAsmInstructions;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitParameter(String str, int i) {
        if (this.parameters == null) {
            this.parameters = new ByteVector();
        }
        this.parametersCount++;
        this.parameters.putShort(str == null ? 0 : this.symbolTable.addConstantUtf8(str)).putShort(i);
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        this.defaultValue = new ByteVector();
        return new AnnotationWriter(this.symbolTable, false, this.defaultValue, null);
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        if (z) {
            AnnotationWriter create = AnnotationWriter.create(this.symbolTable, str, this.lastRuntimeVisibleAnnotation);
            this.lastRuntimeVisibleAnnotation = create;
            return create;
        }
        AnnotationWriter create2 = AnnotationWriter.create(this.symbolTable, str, this.lastRuntimeInvisibleAnnotation);
        this.lastRuntimeInvisibleAnnotation = create2;
        return create2;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (z) {
            AnnotationWriter create = AnnotationWriter.create(this.symbolTable, i, typePath, str, this.lastRuntimeVisibleTypeAnnotation);
            this.lastRuntimeVisibleTypeAnnotation = create;
            return create;
        }
        AnnotationWriter create2 = AnnotationWriter.create(this.symbolTable, i, typePath, str, this.lastRuntimeInvisibleTypeAnnotation);
        this.lastRuntimeInvisibleTypeAnnotation = create2;
        return create2;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitAnnotableParameterCount(int i, boolean z) {
        if (z) {
            this.visibleAnnotableParameterCount = i;
        } else {
            this.invisibleAnnotableParameterCount = i;
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        if (z) {
            if (this.lastRuntimeVisibleParameterAnnotations == null) {
                this.lastRuntimeVisibleParameterAnnotations = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            AnnotationWriter[] annotationWriterArr = this.lastRuntimeVisibleParameterAnnotations;
            AnnotationWriter create = AnnotationWriter.create(this.symbolTable, str, annotationWriterArr[i]);
            annotationWriterArr[i] = create;
            return create;
        }
        if (this.lastRuntimeInvisibleParameterAnnotations == null) {
            this.lastRuntimeInvisibleParameterAnnotations = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
        }
        AnnotationWriter[] annotationWriterArr2 = this.lastRuntimeInvisibleParameterAnnotations;
        AnnotationWriter create2 = AnnotationWriter.create(this.symbolTable, str, annotationWriterArr2[i]);
        annotationWriterArr2[i] = create2;
        return create2;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitAttribute(Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.nextAttribute = this.firstCodeAttribute;
            this.firstCodeAttribute = attribute;
            return;
        }
        attribute.nextAttribute = this.firstAttribute;
        this.firstAttribute = attribute;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        int i4;
        int i5 = this.compute;
        if (i5 == 4) {
            return;
        }
        if (i5 == 3) {
            if (this.currentBasicBlock.frame == null) {
                this.currentBasicBlock.frame = new CurrentFrame(this.currentBasicBlock);
                this.currentBasicBlock.frame.setInputFrameFromDescriptor(this.symbolTable, this.accessFlags, this.descriptor, i2);
                this.currentBasicBlock.frame.accept(this);
            } else {
                if (i == -1) {
                    this.currentBasicBlock.frame.setInputFrameFromApiFormat(this.symbolTable, i2, objArr, i3, objArr2);
                }
                this.currentBasicBlock.frame.accept(this);
            }
        } else if (i == -1) {
            if (this.previousFrame == null) {
                Frame frame = new Frame(new Label());
                frame.setInputFrameFromDescriptor(this.symbolTable, this.accessFlags, this.descriptor, Type.getArgumentsAndReturnSizes(this.descriptor) >> 2);
                frame.accept(this);
            }
            this.currentLocals = i2;
            int visitFrameStart = visitFrameStart(this.code.length, i2, i3);
            int i6 = 0;
            while (i6 < i2) {
                this.currentFrame[visitFrameStart] = Frame.getAbstractTypeFromApiFormat(this.symbolTable, objArr[i6]);
                i6++;
                visitFrameStart++;
            }
            int i7 = 0;
            while (i7 < i3) {
                this.currentFrame[visitFrameStart] = Frame.getAbstractTypeFromApiFormat(this.symbolTable, objArr2[i7]);
                i7++;
                visitFrameStart++;
            }
            visitFrameEnd();
        } else if (this.symbolTable.getMajorVersion() < 50) {
            throw new IllegalArgumentException("Class versions V1_5 or less must use F_NEW frames.");
        } else {
            if (this.stackMapTableEntries == null) {
                this.stackMapTableEntries = new ByteVector();
                i4 = this.code.length;
            } else {
                i4 = (this.code.length - this.previousFrameOffset) - 1;
                if (i4 < 0) {
                    if (i != 3) {
                        throw new IllegalStateException();
                    }
                    return;
                }
            }
            if (i == 0) {
                this.currentLocals = i2;
                this.stackMapTableEntries.putByte(255).putShort(i4).putShort(i2);
                for (int i8 = 0; i8 < i2; i8++) {
                    putFrameType(objArr[i8]);
                }
                this.stackMapTableEntries.putShort(i3);
                for (int i9 = 0; i9 < i3; i9++) {
                    putFrameType(objArr2[i9]);
                }
            } else if (i == 1) {
                this.currentLocals += i2;
                this.stackMapTableEntries.putByte(i2 + 251).putShort(i4);
                for (int i10 = 0; i10 < i2; i10++) {
                    putFrameType(objArr[i10]);
                }
            } else if (i == 2) {
                this.currentLocals -= i2;
                this.stackMapTableEntries.putByte(251 - i2).putShort(i4);
            } else if (i != 3) {
                if (i == 4) {
                    if (i4 < 64) {
                        this.stackMapTableEntries.putByte(i4 + 64);
                    } else {
                        this.stackMapTableEntries.putByte(247).putShort(i4);
                    }
                    putFrameType(objArr2[0]);
                } else {
                    throw new IllegalArgumentException();
                }
            } else if (i4 < 64) {
                this.stackMapTableEntries.putByte(i4);
            } else {
                this.stackMapTableEntries.putByte(251).putShort(i4);
            }
            this.previousFrameOffset = this.code.length;
            this.stackMapTableNumberOfEntries++;
        }
        if (this.compute == 2) {
            this.relativeStackSize = i3;
            for (int i11 = 0; i11 < i3; i11++) {
                if (objArr2[i11] == Opcodes.LONG || objArr2[i11] == Opcodes.DOUBLE) {
                    this.relativeStackSize++;
                }
            }
            int i12 = this.relativeStackSize;
            if (i12 > this.maxRelativeStackSize) {
                this.maxRelativeStackSize = i12;
            }
        }
        this.maxStack = Math.max(this.maxStack, i3);
        this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitInsn(int i) {
        this.lastBytecodeOffset = this.code.length;
        this.code.putByte(i);
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i2 = this.compute;
            if (i2 == 4 || i2 == 3) {
                label.frame.execute(i, 0, null, null);
            } else {
                int i3 = this.relativeStackSize + STACK_SIZE_DELTA[i];
                if (i3 > this.maxRelativeStackSize) {
                    this.maxRelativeStackSize = i3;
                }
                this.relativeStackSize = i3;
            }
            if ((i < 172 || i > 177) && i != 191) {
                return;
            }
            endCurrentBasicBlockWithNoSuccessor();
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitIntInsn(int i, int i2) {
        this.lastBytecodeOffset = this.code.length;
        if (i == 17) {
            this.code.put12(i, i2);
        } else {
            this.code.put11(i, i2);
        }
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i3 = this.compute;
            if (i3 == 4 || i3 == 3) {
                label.frame.execute(i, i2, null, null);
            } else if (i != 188) {
                int i4 = this.relativeStackSize + 1;
                if (i4 > this.maxRelativeStackSize) {
                    this.maxRelativeStackSize = i4;
                }
                this.relativeStackSize = i4;
            }
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        this.lastBytecodeOffset = this.code.length;
        if (i2 < 4 && i != 169) {
            this.code.putByte((i < 54 ? ((i - 21) << 2) + 26 : ((i - 54) << 2) + 59) + i2);
        } else if (i2 >= 256) {
            this.code.putByte(Opcode.WIDE).put12(i, i2);
        } else {
            this.code.put11(i, i2);
        }
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i3 = this.compute;
            if (i3 == 4 || i3 == 3) {
                label.frame.execute(i, i2, null, null);
            } else if (i == 169) {
                label.flags = (short) (label.flags | 64);
                this.currentBasicBlock.outputStackSize = (short) this.relativeStackSize;
                endCurrentBasicBlockWithNoSuccessor();
            } else {
                int i4 = this.relativeStackSize + STACK_SIZE_DELTA[i];
                if (i4 > this.maxRelativeStackSize) {
                    this.maxRelativeStackSize = i4;
                }
                this.relativeStackSize = i4;
            }
        }
        int i5 = this.compute;
        if (i5 != 0) {
            int i6 = (i == 22 || i == 24 || i == 55 || i == 57) ? i2 + 2 : i2 + 1;
            if (i6 > this.maxLocals) {
                this.maxLocals = i6;
            }
        }
        if (i < 54 || i5 != 4 || this.firstHandler == null) {
            return;
        }
        visitLabel(new Label());
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitTypeInsn(int i, String str) {
        this.lastBytecodeOffset = this.code.length;
        Symbol addConstantClass = this.symbolTable.addConstantClass(str);
        this.code.put12(i, addConstantClass.index);
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i2 = this.compute;
            if (i2 == 4 || i2 == 3) {
                label.frame.execute(i, this.lastBytecodeOffset, addConstantClass, this.symbolTable);
            } else if (i == 187) {
                int i3 = this.relativeStackSize + 1;
                if (i3 > this.maxRelativeStackSize) {
                    this.maxRelativeStackSize = i3;
                }
                this.relativeStackSize = i3;
            }
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        int i2;
        int i3;
        this.lastBytecodeOffset = this.code.length;
        Symbol addConstantFieldref = this.symbolTable.addConstantFieldref(str, str2, str3);
        this.code.put12(i, addConstantFieldref.index);
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i4 = this.compute;
            int i5 = 0;
            if (i4 == 4 || i4 == 3) {
                label.frame.execute(i, 0, addConstantFieldref, this.symbolTable);
                return;
            }
            char charAt = str3.charAt(0);
            int i6 = 1;
            int i7 = -2;
            switch (i) {
                case 178:
                    i2 = this.relativeStackSize + ((charAt == 'D' || charAt == 'J') ? 2 : 2);
                    break;
                case 179:
                    i3 = this.relativeStackSize;
                    if (charAt != 'D' && charAt != 'J') {
                        i7 = -1;
                    }
                    i2 = i3 + i7;
                    break;
                case 180:
                    int i8 = this.relativeStackSize;
                    if (charAt == 'D' || charAt == 'J') {
                        i5 = 1;
                    }
                    i2 = i8 + i5;
                    break;
                default:
                    i3 = this.relativeStackSize;
                    if (charAt == 'D' || charAt == 'J') {
                        i7 = -3;
                    }
                    i2 = i3 + i7;
                    break;
            }
            if (i2 > this.maxRelativeStackSize) {
                this.maxRelativeStackSize = i2;
            }
            this.relativeStackSize = i2;
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        int i2;
        this.lastBytecodeOffset = this.code.length;
        Symbol addConstantMethodref = this.symbolTable.addConstantMethodref(str, str2, str3, z);
        if (i == 185) {
            this.code.put12(185, addConstantMethodref.index).put11(addConstantMethodref.getArgumentsAndReturnSizes() >> 2, 0);
        } else {
            this.code.put12(i, addConstantMethodref.index);
        }
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i3 = this.compute;
            if (i3 == 4 || i3 == 3) {
                label.frame.execute(i, 0, addConstantMethodref, this.symbolTable);
                return;
            }
            int argumentsAndReturnSizes = addConstantMethodref.getArgumentsAndReturnSizes();
            int i4 = (argumentsAndReturnSizes & 3) - (argumentsAndReturnSizes >> 2);
            if (i == 184) {
                i2 = this.relativeStackSize + i4 + 1;
            } else {
                i2 = this.relativeStackSize + i4;
            }
            if (i2 > this.maxRelativeStackSize) {
                this.maxRelativeStackSize = i2;
            }
            this.relativeStackSize = i2;
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        this.lastBytecodeOffset = this.code.length;
        Symbol addConstantInvokeDynamic = this.symbolTable.addConstantInvokeDynamic(str, str2, handle, objArr);
        this.code.put12(186, addConstantInvokeDynamic.index);
        this.code.putShort(0);
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i = this.compute;
            if (i == 4 || i == 3) {
                label.frame.execute(186, 0, addConstantInvokeDynamic, this.symbolTable);
                return;
            }
            int argumentsAndReturnSizes = addConstantInvokeDynamic.getArgumentsAndReturnSizes();
            int i2 = this.relativeStackSize + ((argumentsAndReturnSizes & 3) - (argumentsAndReturnSizes >> 2)) + 1;
            if (i2 > this.maxRelativeStackSize) {
                this.maxRelativeStackSize = i2;
            }
            this.relativeStackSize = i2;
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitJumpInsn(int i, Label label) {
        boolean z;
        this.lastBytecodeOffset = this.code.length;
        int i2 = i >= 200 ? i - 33 : i;
        if ((label.flags & 4) == 0 || label.bytecodeOffset - this.code.length >= -32768) {
            if (i2 != i) {
                this.code.putByte(i);
                ByteVector byteVector = this.code;
                label.put(byteVector, byteVector.length - 1, true);
            } else {
                this.code.putByte(i2);
                ByteVector byteVector2 = this.code;
                label.put(byteVector2, byteVector2.length - 1, false);
            }
            z = false;
        } else {
            if (i2 == 167) {
                this.code.putByte(200);
            } else if (i2 == 168) {
                this.code.putByte(Opcode.JSR_W);
            } else {
                this.code.putByte(i2 >= 198 ? i2 ^ 1 : ((i2 + 1) ^ 1) - 1);
                this.code.putShort(8);
                this.code.putByte(220);
                this.hasAsmInstructions = true;
                z = true;
                ByteVector byteVector3 = this.code;
                label.put(byteVector3, byteVector3.length - 1, true);
            }
            z = false;
            ByteVector byteVector32 = this.code;
            label.put(byteVector32, byteVector32.length - 1, true);
        }
        Label label2 = this.currentBasicBlock;
        if (label2 != null) {
            int i3 = this.compute;
            Label label3 = null;
            if (i3 == 4) {
                label2.frame.execute(i2, 0, null, null);
                Label canonicalInstance = label.getCanonicalInstance();
                canonicalInstance.flags = (short) (canonicalInstance.flags | 2);
                addSuccessorToCurrentBasicBlock(0, label);
                if (i2 != 167) {
                    label3 = new Label();
                }
            } else if (i3 == 3) {
                label2.frame.execute(i2, 0, null, null);
            } else if (i3 == 2) {
                this.relativeStackSize += STACK_SIZE_DELTA[i2];
            } else if (i2 == 168) {
                if ((label.flags & 32) == 0) {
                    label.flags = (short) (label.flags | 32);
                    this.hasSubroutines = true;
                }
                Label label4 = this.currentBasicBlock;
                label4.flags = (short) (label4.flags | 16);
                addSuccessorToCurrentBasicBlock(this.relativeStackSize + 1, label);
                label3 = new Label();
            } else {
                int i4 = this.relativeStackSize + STACK_SIZE_DELTA[i2];
                this.relativeStackSize = i4;
                addSuccessorToCurrentBasicBlock(i4, label);
            }
            if (label3 != null) {
                if (z) {
                    label3.flags = (short) (label3.flags | 2);
                }
                visitLabel(label3);
            }
            if (i2 == 167) {
                endCurrentBasicBlockWithNoSuccessor();
            }
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitLabel(Label label) {
        this.hasAsmInstructions |= label.resolve(this.code.data, this.code.length);
        if ((label.flags & 1) != 0) {
            return;
        }
        int i = this.compute;
        if (i == 4) {
            if (this.currentBasicBlock != null) {
                if (label.bytecodeOffset == this.currentBasicBlock.bytecodeOffset) {
                    Label label2 = this.currentBasicBlock;
                    label2.flags = (short) (label2.flags | (label.flags & 2));
                    label.frame = this.currentBasicBlock.frame;
                    return;
                }
                addSuccessorToCurrentBasicBlock(0, label);
            }
            if (this.lastBasicBlock != null) {
                if (label.bytecodeOffset == this.lastBasicBlock.bytecodeOffset) {
                    Label label3 = this.lastBasicBlock;
                    label3.flags = (short) (label3.flags | (label.flags & 2));
                    label.frame = this.lastBasicBlock.frame;
                    this.currentBasicBlock = this.lastBasicBlock;
                    return;
                }
                this.lastBasicBlock.nextBasicBlock = label;
            }
            this.lastBasicBlock = label;
            this.currentBasicBlock = label;
            label.frame = new Frame(label);
        } else if (i == 3) {
            Label label4 = this.currentBasicBlock;
            if (label4 == null) {
                this.currentBasicBlock = label;
            } else {
                label4.frame.owner = label;
            }
        } else if (i == 1) {
            Label label5 = this.currentBasicBlock;
            if (label5 != null) {
                label5.outputStackMax = (short) this.maxRelativeStackSize;
                addSuccessorToCurrentBasicBlock(this.relativeStackSize, label);
            }
            this.currentBasicBlock = label;
            this.relativeStackSize = 0;
            this.maxRelativeStackSize = 0;
            Label label6 = this.lastBasicBlock;
            if (label6 != null) {
                label6.nextBasicBlock = label;
            }
            this.lastBasicBlock = label;
        } else if (i == 2 && this.currentBasicBlock == null) {
            this.currentBasicBlock = label;
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitLdcInsn(Object obj) {
        char charAt;
        this.lastBytecodeOffset = this.code.length;
        Symbol addConstant = this.symbolTable.addConstant(obj);
        int i = addConstant.index;
        boolean z = addConstant.tag == 5 || addConstant.tag == 6 || (addConstant.tag == 17 && ((charAt = addConstant.value.charAt(0)) == 'J' || charAt == 'D'));
        if (z) {
            this.code.put12(20, i);
        } else if (i >= 256) {
            this.code.put12(19, i);
        } else {
            this.code.put11(18, i);
        }
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i2 = this.compute;
            if (i2 == 4 || i2 == 3) {
                label.frame.execute(18, 0, addConstant, this.symbolTable);
                return;
            }
            int i3 = this.relativeStackSize + (z ? 2 : 1);
            if (i3 > this.maxRelativeStackSize) {
                this.maxRelativeStackSize = i3;
            }
            this.relativeStackSize = i3;
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        int i3;
        int i4;
        this.lastBytecodeOffset = this.code.length;
        if (i > 255 || i2 > 127 || i2 < -128) {
            this.code.putByte(Opcode.WIDE).put12(132, i).putShort(i2);
        } else {
            this.code.putByte(132).put11(i, i2);
        }
        Label label = this.currentBasicBlock;
        if (label != null && ((i4 = this.compute) == 4 || i4 == 3)) {
            label.frame.execute(132, i, null, null);
        }
        if (this.compute == 0 || (i3 = i + 1) <= this.maxLocals) {
            return;
        }
        this.maxLocals = i3;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        this.lastBytecodeOffset = this.code.length;
        this.code.putByte(170).putByteArray(null, 0, (4 - (this.code.length % 4)) % 4);
        label.put(this.code, this.lastBytecodeOffset, true);
        this.code.putInt(i).putInt(i2);
        for (Label label2 : labelArr) {
            label2.put(this.code, this.lastBytecodeOffset, true);
        }
        visitSwitchInsn(label, labelArr);
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        this.lastBytecodeOffset = this.code.length;
        this.code.putByte(171).putByteArray(null, 0, (4 - (this.code.length % 4)) % 4);
        label.put(this.code, this.lastBytecodeOffset, true);
        this.code.putInt(labelArr.length);
        for (int i = 0; i < labelArr.length; i++) {
            this.code.putInt(iArr[i]);
            labelArr[i].put(this.code, this.lastBytecodeOffset, true);
        }
        visitSwitchInsn(label, labelArr);
    }

    private void visitSwitchInsn(Label label, Label[] labelArr) {
        Label label2 = this.currentBasicBlock;
        if (label2 != null) {
            int i = this.compute;
            if (i == 4) {
                label2.frame.execute(171, 0, null, null);
                addSuccessorToCurrentBasicBlock(0, label);
                Label canonicalInstance = label.getCanonicalInstance();
                canonicalInstance.flags = (short) (canonicalInstance.flags | 2);
                for (Label label3 : labelArr) {
                    addSuccessorToCurrentBasicBlock(0, label3);
                    Label canonicalInstance2 = label3.getCanonicalInstance();
                    canonicalInstance2.flags = (short) (canonicalInstance2.flags | 2);
                }
            } else if (i == 1) {
                int i2 = this.relativeStackSize - 1;
                this.relativeStackSize = i2;
                addSuccessorToCurrentBasicBlock(i2, label);
                for (Label label4 : labelArr) {
                    addSuccessorToCurrentBasicBlock(this.relativeStackSize, label4);
                }
            }
            endCurrentBasicBlockWithNoSuccessor();
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitMultiANewArrayInsn(String str, int i) {
        this.lastBytecodeOffset = this.code.length;
        Symbol addConstantClass = this.symbolTable.addConstantClass(str);
        this.code.put12(197, addConstantClass.index).putByte(i);
        Label label = this.currentBasicBlock;
        if (label != null) {
            int i2 = this.compute;
            if (i2 == 4 || i2 == 3) {
                label.frame.execute(197, i, addConstantClass, this.symbolTable);
            } else {
                this.relativeStackSize += 1 - i;
            }
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (z) {
            AnnotationWriter create = AnnotationWriter.create(this.symbolTable, (i & (-16776961)) | (this.lastBytecodeOffset << 8), typePath, str, this.lastCodeRuntimeVisibleTypeAnnotation);
            this.lastCodeRuntimeVisibleTypeAnnotation = create;
            return create;
        }
        AnnotationWriter create2 = AnnotationWriter.create(this.symbolTable, (i & (-16776961)) | (this.lastBytecodeOffset << 8), typePath, str, this.lastCodeRuntimeInvisibleTypeAnnotation);
        this.lastCodeRuntimeInvisibleTypeAnnotation = create2;
        return create2;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        Handler handler = new Handler(label, label2, label3, str != null ? this.symbolTable.addConstantClass(str).index : 0, str);
        if (this.firstHandler == null) {
            this.firstHandler = handler;
        } else {
            this.lastHandler.nextHandler = handler;
        }
        this.lastHandler = handler;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (z) {
            AnnotationWriter create = AnnotationWriter.create(this.symbolTable, i, typePath, str, this.lastCodeRuntimeVisibleTypeAnnotation);
            this.lastCodeRuntimeVisibleTypeAnnotation = create;
            return create;
        }
        AnnotationWriter create2 = AnnotationWriter.create(this.symbolTable, i, typePath, str, this.lastCodeRuntimeInvisibleTypeAnnotation);
        this.lastCodeRuntimeInvisibleTypeAnnotation = create2;
        return create2;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        int i2 = 1;
        if (str3 != null) {
            if (this.localVariableTypeTable == null) {
                this.localVariableTypeTable = new ByteVector();
            }
            this.localVariableTypeTableLength++;
            this.localVariableTypeTable.putShort(label.bytecodeOffset).putShort(label2.bytecodeOffset - label.bytecodeOffset).putShort(this.symbolTable.addConstantUtf8(str)).putShort(this.symbolTable.addConstantUtf8(str3)).putShort(i);
        }
        if (this.localVariableTable == null) {
            this.localVariableTable = new ByteVector();
        }
        this.localVariableTableLength++;
        this.localVariableTable.putShort(label.bytecodeOffset).putShort(label2.bytecodeOffset - label.bytecodeOffset).putShort(this.symbolTable.addConstantUtf8(str)).putShort(this.symbolTable.addConstantUtf8(str2)).putShort(i);
        if (this.compute != 0) {
            char charAt = str2.charAt(0);
            int i3 = i + ((charAt == 'J' || charAt == 'D') ? 2 : 2);
            if (i3 > this.maxLocals) {
                this.maxLocals = i3;
            }
        }
    }

    @Override // org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(i >>> 24).putShort(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            byteVector.putShort(labelArr[i2].bytecodeOffset).putShort(labelArr2[i2].bytecodeOffset - labelArr[i2].bytecodeOffset).putShort(iArr[i2]);
        }
        TypePath.put(typePath, byteVector);
        byteVector.putShort(this.symbolTable.addConstantUtf8(str)).putShort(0);
        if (z) {
            AnnotationWriter annotationWriter = new AnnotationWriter(this.symbolTable, true, byteVector, this.lastCodeRuntimeVisibleTypeAnnotation);
            this.lastCodeRuntimeVisibleTypeAnnotation = annotationWriter;
            return annotationWriter;
        }
        AnnotationWriter annotationWriter2 = new AnnotationWriter(this.symbolTable, true, byteVector, this.lastCodeRuntimeInvisibleTypeAnnotation);
        this.lastCodeRuntimeInvisibleTypeAnnotation = annotationWriter2;
        return annotationWriter2;
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitLineNumber(int i, Label label) {
        if (this.lineNumberTable == null) {
            this.lineNumberTable = new ByteVector();
        }
        this.lineNumberTableLength++;
        this.lineNumberTable.putShort(label.bytecodeOffset);
        this.lineNumberTable.putShort(i);
    }

    @Override // org.objectweb.asm.MethodVisitor
    public void visitMaxs(int i, int i2) {
        int i3 = this.compute;
        if (i3 == 4) {
            computeAllFrames();
        } else if (i3 == 1) {
            computeMaxStackAndLocal();
        } else if (i3 == 2) {
            this.maxStack = this.maxRelativeStackSize;
        } else {
            this.maxStack = i;
            this.maxLocals = i2;
        }
    }

    private void computeAllFrames() {
        Handler handler = this.firstHandler;
        while (true) {
            if (handler == null) {
                break;
            }
            int abstractTypeFromInternalName = Frame.getAbstractTypeFromInternalName(this.symbolTable, handler.catchTypeDescriptor != null ? handler.catchTypeDescriptor : "java/lang/Throwable");
            Label canonicalInstance = handler.handlerPc.getCanonicalInstance();
            canonicalInstance.flags = (short) (canonicalInstance.flags | 2);
            Label canonicalInstance2 = handler.endPc.getCanonicalInstance();
            for (Label canonicalInstance3 = handler.startPc.getCanonicalInstance(); canonicalInstance3 != canonicalInstance2; canonicalInstance3 = canonicalInstance3.nextBasicBlock) {
                canonicalInstance3.outgoingEdges = new Edge(abstractTypeFromInternalName, canonicalInstance, canonicalInstance3.outgoingEdges);
            }
            handler = handler.nextHandler;
        }
        Frame frame = this.firstBasicBlock.frame;
        frame.setInputFrameFromDescriptor(this.symbolTable, this.accessFlags, this.descriptor, this.maxLocals);
        frame.accept(this);
        Label label = this.firstBasicBlock;
        label.nextListElement = Label.EMPTY_LIST;
        int i = 0;
        while (label != Label.EMPTY_LIST) {
            Label label2 = label.nextListElement;
            label.nextListElement = null;
            label.flags = (short) (label.flags | 8);
            int inputStackSize = label.frame.getInputStackSize() + label.outputStackMax;
            if (inputStackSize > i) {
                i = inputStackSize;
            }
            for (Edge edge = label.outgoingEdges; edge != null; edge = edge.nextEdge) {
                Label canonicalInstance4 = edge.successor.getCanonicalInstance();
                if (label.frame.merge(this.symbolTable, canonicalInstance4.frame, edge.info) && canonicalInstance4.nextListElement == null) {
                    canonicalInstance4.nextListElement = label2;
                    label2 = canonicalInstance4;
                }
            }
            label = label2;
        }
        for (Label label3 = this.firstBasicBlock; label3 != null; label3 = label3.nextBasicBlock) {
            if ((label3.flags & 10) == 10) {
                label3.frame.accept(this);
            }
            if ((label3.flags & 8) == 0) {
                Label label4 = label3.nextBasicBlock;
                int i2 = label3.bytecodeOffset;
                int i3 = (label4 == null ? this.code.length : label4.bytecodeOffset) - 1;
                if (i3 >= i2) {
                    for (int i4 = i2; i4 < i3; i4++) {
                        this.code.data[i4] = 0;
                    }
                    this.code.data[i3] = -65;
                    this.currentFrame[visitFrameStart(i2, 0, 1)] = Frame.getAbstractTypeFromInternalName(this.symbolTable, "java/lang/Throwable");
                    visitFrameEnd();
                    this.firstHandler = Handler.removeRange(this.firstHandler, label3, label4);
                    i = Math.max(i, 1);
                }
            }
        }
        this.maxStack = i;
    }

    private void computeMaxStackAndLocal() {
        for (Handler handler = this.firstHandler; handler != null; handler = handler.nextHandler) {
            Label label = handler.handlerPc;
            Label label2 = handler.endPc;
            for (Label label3 = handler.startPc; label3 != label2; label3 = label3.nextBasicBlock) {
                if ((label3.flags & 16) == 0) {
                    label3.outgoingEdges = new Edge(Integer.MAX_VALUE, label, label3.outgoingEdges);
                } else {
                    label3.outgoingEdges.nextEdge.nextEdge = new Edge(Integer.MAX_VALUE, label, label3.outgoingEdges.nextEdge.nextEdge);
                }
            }
        }
        if (this.hasSubroutines) {
            this.firstBasicBlock.markSubroutine((short) 1);
            short s = 1;
            for (short s2 = 1; s2 <= s; s2 = (short) (s2 + 1)) {
                for (Label label4 = this.firstBasicBlock; label4 != null; label4 = label4.nextBasicBlock) {
                    if ((label4.flags & 16) != 0 && label4.subroutineId == s2) {
                        Label label5 = label4.outgoingEdges.nextEdge.successor;
                        if (label5.subroutineId == 0) {
                            s = (short) (s + 1);
                            label5.markSubroutine(s);
                        }
                    }
                }
            }
            for (Label label6 = this.firstBasicBlock; label6 != null; label6 = label6.nextBasicBlock) {
                if ((label6.flags & 16) != 0) {
                    label6.outgoingEdges.nextEdge.successor.addSubroutineRetSuccessors(label6);
                }
            }
        }
        Label label7 = this.firstBasicBlock;
        label7.nextListElement = Label.EMPTY_LIST;
        int i = this.maxStack;
        while (label7 != Label.EMPTY_LIST) {
            Label label8 = label7.nextListElement;
            short s3 = label7.inputStackSize;
            int i2 = label7.outputStackMax + s3;
            if (i2 > i) {
                i = i2;
            }
            Edge edge = label7.outgoingEdges;
            if ((label7.flags & 16) != 0) {
                edge = edge.nextEdge;
            }
            label7 = label8;
            while (edge != null) {
                Label label9 = edge.successor;
                if (label9.nextListElement == null) {
                    label9.inputStackSize = (short) (edge.info == Integer.MAX_VALUE ? 1 : edge.info + s3);
                    label9.nextListElement = label7;
                    label7 = label9;
                }
                edge = edge.nextEdge;
            }
        }
        this.maxStack = i;
    }

    private void addSuccessorToCurrentBasicBlock(int i, Label label) {
        this.currentBasicBlock.outgoingEdges = new Edge(i, label, this.currentBasicBlock.outgoingEdges);
    }

    private void endCurrentBasicBlockWithNoSuccessor() {
        int i = this.compute;
        if (i != 4) {
            if (i == 1) {
                this.currentBasicBlock.outputStackMax = (short) this.maxRelativeStackSize;
                this.currentBasicBlock = null;
                return;
            }
            return;
        }
        Label label = new Label();
        label.frame = new Frame(label);
        label.resolve(this.code.data, this.code.length);
        this.lastBasicBlock.nextBasicBlock = label;
        this.lastBasicBlock = label;
        this.currentBasicBlock = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int visitFrameStart(int i, int i2, int i3) {
        int i4 = i2 + 3 + i3;
        int[] iArr = this.currentFrame;
        if (iArr == null || iArr.length < i4) {
            this.currentFrame = new int[i4];
        }
        int[] iArr2 = this.currentFrame;
        iArr2[0] = i;
        iArr2[1] = i2;
        iArr2[2] = i3;
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void visitAbstractType(int i, int i2) {
        this.currentFrame[i] = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void visitFrameEnd() {
        if (this.previousFrame != null) {
            if (this.stackMapTableEntries == null) {
                this.stackMapTableEntries = new ByteVector();
            }
            putFrame();
            this.stackMapTableNumberOfEntries++;
        }
        this.previousFrame = this.currentFrame;
        this.currentFrame = null;
    }

    private void putFrame() {
        char c;
        int[] iArr = this.currentFrame;
        int i = iArr[1];
        int i2 = iArr[2];
        int i3 = 0;
        if (this.symbolTable.getMajorVersion() < 50) {
            this.stackMapTableEntries.putShort(this.currentFrame[0]).putShort(i);
            int i4 = i + 3;
            putAbstractTypes(3, i4);
            this.stackMapTableEntries.putShort(i2);
            putAbstractTypes(i4, i2 + i4);
            return;
        }
        int i5 = this.stackMapTableNumberOfEntries == 0 ? this.currentFrame[0] : (this.currentFrame[0] - this.previousFrame[0]) - 1;
        int i6 = this.previousFrame[1];
        int i7 = i - i6;
        if (i2 == 0) {
            switch (i7) {
                case -3:
                case -2:
                case -1:
                    c = 248;
                    break;
                case 0:
                    if (i5 >= 64) {
                        c = 251;
                        break;
                    } else {
                        c = 0;
                        break;
                    }
                case 1:
                case 2:
                case 3:
                    c = 252;
                    break;
                default:
                    c = 255;
                    break;
            }
        } else {
            if (i7 == 0 && i2 == 1) {
                c = i5 < 63 ? '@' : (char) 247;
            }
            c = 255;
        }
        if (c != 255) {
            int i8 = 3;
            while (true) {
                if (i3 < i6 && i3 < i) {
                    if (this.currentFrame[i8] != this.previousFrame[i8]) {
                        c = 255;
                    } else {
                        i8++;
                        i3++;
                    }
                }
            }
        }
        if (c == 0) {
            this.stackMapTableEntries.putByte(i5);
        } else if (c == '@') {
            this.stackMapTableEntries.putByte(i5 + 64);
            putAbstractTypes(i + 3, i + 4);
        } else if (c == 247) {
            this.stackMapTableEntries.putByte(247).putShort(i5);
            putAbstractTypes(i + 3, i + 4);
        } else if (c == 248) {
            this.stackMapTableEntries.putByte(i7 + 251).putShort(i5);
        } else if (c == 251) {
            this.stackMapTableEntries.putByte(251).putShort(i5);
        } else if (c == 252) {
            this.stackMapTableEntries.putByte(i7 + 251).putShort(i5);
            putAbstractTypes(i6 + 3, i + 3);
        } else {
            this.stackMapTableEntries.putByte(255).putShort(i5).putShort(i);
            int i9 = i + 3;
            putAbstractTypes(3, i9);
            this.stackMapTableEntries.putShort(i2);
            putAbstractTypes(i9, i2 + i9);
        }
    }

    private void putAbstractTypes(int i, int i2) {
        while (i < i2) {
            Frame.putAbstractType(this.symbolTable, this.currentFrame[i], this.stackMapTableEntries);
            i++;
        }
    }

    private void putFrameType(Object obj) {
        if (obj instanceof Integer) {
            this.stackMapTableEntries.putByte(((Integer) obj).intValue());
        } else if (obj instanceof String) {
            this.stackMapTableEntries.putByte(7).putShort(this.symbolTable.addConstantClass((String) obj).index);
        } else {
            this.stackMapTableEntries.putByte(8).putShort(((Label) obj).bytecodeOffset);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean canCopyMethodAttributes(ClassReader classReader, boolean z, boolean z2, int i, int i2, int i3) {
        if (classReader == this.symbolTable.getSource() && i == this.descriptorIndex && i2 == this.signatureIndex) {
            if (z2 == ((this.accessFlags & 131072) != 0)) {
                if (z != (this.symbolTable.getMajorVersion() < 49 && (this.accessFlags & 4096) != 0)) {
                    return false;
                }
                if (i3 == 0) {
                    if (this.numberOfExceptions != 0) {
                        return false;
                    }
                } else if (classReader.readUnsignedShort(i3) == this.numberOfExceptions) {
                    int i4 = i3 + 2;
                    for (int i5 = 0; i5 < this.numberOfExceptions; i5++) {
                        if (classReader.readUnsignedShort(i4) != this.exceptionIndexTable[i5]) {
                            return false;
                        }
                        i4 += 2;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setMethodAttributesSource(int i, int i2) {
        this.sourceOffset = i + 6;
        this.sourceLength = i2 - 6;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int computeMethodInfoSize() {
        int i;
        if (this.sourceOffset != 0) {
            return this.sourceLength + 6;
        }
        if (this.code.length <= 0) {
            i = 8;
        } else if (this.code.length > 65535) {
            throw new MethodTooLargeException(this.symbolTable.getClassName(), this.name, this.descriptor, this.code.length);
        } else {
            this.symbolTable.addConstantUtf8(CodeAttribute.tag);
            i = this.code.length + 16 + Handler.getExceptionTableSize(this.firstHandler) + 8;
            if (this.stackMapTableEntries != null) {
                this.symbolTable.addConstantUtf8(this.symbolTable.getMajorVersion() >= 50 ? StackMapTable.tag : StackMap.tag);
                i += this.stackMapTableEntries.length + 8;
            }
            if (this.lineNumberTable != null) {
                this.symbolTable.addConstantUtf8(LineNumberAttribute.tag);
                i += this.lineNumberTable.length + 8;
            }
            if (this.localVariableTable != null) {
                this.symbolTable.addConstantUtf8(LocalVariableAttribute.tag);
                i += this.localVariableTable.length + 8;
            }
            if (this.localVariableTypeTable != null) {
                this.symbolTable.addConstantUtf8("LocalVariableTypeTable");
                i += this.localVariableTypeTable.length + 8;
            }
            AnnotationWriter annotationWriter = this.lastCodeRuntimeVisibleTypeAnnotation;
            if (annotationWriter != null) {
                i += annotationWriter.computeAnnotationsSize(TypeAnnotationsAttribute.visibleTag);
            }
            AnnotationWriter annotationWriter2 = this.lastCodeRuntimeInvisibleTypeAnnotation;
            if (annotationWriter2 != null) {
                i += annotationWriter2.computeAnnotationsSize(TypeAnnotationsAttribute.invisibleTag);
            }
            Attribute attribute = this.firstCodeAttribute;
            if (attribute != null) {
                i += attribute.computeAttributesSize(this.symbolTable, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
        }
        if (this.numberOfExceptions > 0) {
            this.symbolTable.addConstantUtf8(ExceptionsAttribute.tag);
            i += (this.numberOfExceptions * 2) + 8;
        }
        int computeAttributesSize = i + Attribute.computeAttributesSize(this.symbolTable, this.accessFlags, this.signatureIndex) + AnnotationWriter.computeAnnotationsSize(this.lastRuntimeVisibleAnnotation, this.lastRuntimeInvisibleAnnotation, this.lastRuntimeVisibleTypeAnnotation, this.lastRuntimeInvisibleTypeAnnotation);
        AnnotationWriter[] annotationWriterArr = this.lastRuntimeVisibleParameterAnnotations;
        if (annotationWriterArr != null) {
            int i2 = this.visibleAnnotableParameterCount;
            if (i2 == 0) {
                i2 = annotationWriterArr.length;
            }
            computeAttributesSize += AnnotationWriter.computeParameterAnnotationsSize(ParameterAnnotationsAttribute.visibleTag, annotationWriterArr, i2);
        }
        AnnotationWriter[] annotationWriterArr2 = this.lastRuntimeInvisibleParameterAnnotations;
        if (annotationWriterArr2 != null) {
            int i3 = this.invisibleAnnotableParameterCount;
            if (i3 == 0) {
                i3 = annotationWriterArr2.length;
            }
            computeAttributesSize += AnnotationWriter.computeParameterAnnotationsSize(ParameterAnnotationsAttribute.invisibleTag, annotationWriterArr2, i3);
        }
        if (this.defaultValue != null) {
            this.symbolTable.addConstantUtf8(AnnotationDefaultAttribute.tag);
            computeAttributesSize += this.defaultValue.length + 6;
        }
        if (this.parameters != null) {
            this.symbolTable.addConstantUtf8(MethodParametersAttribute.tag);
            computeAttributesSize += this.parameters.length + 7;
        }
        Attribute attribute2 = this.firstAttribute;
        return attribute2 != null ? computeAttributesSize + attribute2.computeAttributesSize(this.symbolTable) : computeAttributesSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void putMethodInfo(ByteVector byteVector) {
        int i;
        boolean z = this.symbolTable.getMajorVersion() < 49;
        byteVector.putShort((~(z ? 4096 : 0)) & this.accessFlags).putShort(this.nameIndex).putShort(this.descriptorIndex);
        if (this.sourceOffset != 0) {
            byteVector.putByteArray(this.symbolTable.getSource().classFileBuffer, this.sourceOffset, this.sourceLength);
            return;
        }
        int i2 = this.code.length > 0 ? 1 : 0;
        if (this.numberOfExceptions > 0) {
            i2++;
        }
        int i3 = this.accessFlags;
        if ((i3 & 4096) != 0 && z) {
            i2++;
        }
        if (this.signatureIndex != 0) {
            i2++;
        }
        if ((131072 & i3) != 0) {
            i2++;
        }
        if (this.lastRuntimeVisibleAnnotation != null) {
            i2++;
        }
        if (this.lastRuntimeInvisibleAnnotation != null) {
            i2++;
        }
        if (this.lastRuntimeVisibleParameterAnnotations != null) {
            i2++;
        }
        if (this.lastRuntimeInvisibleParameterAnnotations != null) {
            i2++;
        }
        if (this.lastRuntimeVisibleTypeAnnotation != null) {
            i2++;
        }
        if (this.lastRuntimeInvisibleTypeAnnotation != null) {
            i2++;
        }
        if (this.defaultValue != null) {
            i2++;
        }
        if (this.parameters != null) {
            i2++;
        }
        Attribute attribute = this.firstAttribute;
        if (attribute != null) {
            i2 += attribute.getAttributeCount();
        }
        byteVector.putShort(i2);
        if (this.code.length > 0) {
            int exceptionTableSize = this.code.length + 10 + Handler.getExceptionTableSize(this.firstHandler);
            ByteVector byteVector2 = this.stackMapTableEntries;
            if (byteVector2 != null) {
                exceptionTableSize += byteVector2.length + 8;
                i = 1;
            } else {
                i = 0;
            }
            ByteVector byteVector3 = this.lineNumberTable;
            if (byteVector3 != null) {
                exceptionTableSize += byteVector3.length + 8;
                i++;
            }
            ByteVector byteVector4 = this.localVariableTable;
            if (byteVector4 != null) {
                exceptionTableSize += byteVector4.length + 8;
                i++;
            }
            ByteVector byteVector5 = this.localVariableTypeTable;
            if (byteVector5 != null) {
                exceptionTableSize += byteVector5.length + 8;
                i++;
            }
            AnnotationWriter annotationWriter = this.lastCodeRuntimeVisibleTypeAnnotation;
            if (annotationWriter != null) {
                exceptionTableSize += annotationWriter.computeAnnotationsSize(TypeAnnotationsAttribute.visibleTag);
                i++;
            }
            AnnotationWriter annotationWriter2 = this.lastCodeRuntimeInvisibleTypeAnnotation;
            if (annotationWriter2 != null) {
                exceptionTableSize += annotationWriter2.computeAnnotationsSize(TypeAnnotationsAttribute.invisibleTag);
                i++;
            }
            Attribute attribute2 = this.firstCodeAttribute;
            if (attribute2 != null) {
                exceptionTableSize += attribute2.computeAttributesSize(this.symbolTable, this.code.data, this.code.length, this.maxStack, this.maxLocals);
                i += this.firstCodeAttribute.getAttributeCount();
            }
            byteVector.putShort(this.symbolTable.addConstantUtf8(CodeAttribute.tag)).putInt(exceptionTableSize).putShort(this.maxStack).putShort(this.maxLocals).putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            Handler.putExceptionTable(this.firstHandler, byteVector);
            byteVector.putShort(i);
            if (this.stackMapTableEntries != null) {
                byteVector.putShort(this.symbolTable.addConstantUtf8(this.symbolTable.getMajorVersion() >= 50 ? StackMapTable.tag : StackMap.tag)).putInt(this.stackMapTableEntries.length + 2).putShort(this.stackMapTableNumberOfEntries).putByteArray(this.stackMapTableEntries.data, 0, this.stackMapTableEntries.length);
            }
            if (this.lineNumberTable != null) {
                byteVector.putShort(this.symbolTable.addConstantUtf8(LineNumberAttribute.tag)).putInt(this.lineNumberTable.length + 2).putShort(this.lineNumberTableLength).putByteArray(this.lineNumberTable.data, 0, this.lineNumberTable.length);
            }
            if (this.localVariableTable != null) {
                byteVector.putShort(this.symbolTable.addConstantUtf8(LocalVariableAttribute.tag)).putInt(this.localVariableTable.length + 2).putShort(this.localVariableTableLength).putByteArray(this.localVariableTable.data, 0, this.localVariableTable.length);
            }
            if (this.localVariableTypeTable != null) {
                byteVector.putShort(this.symbolTable.addConstantUtf8("LocalVariableTypeTable")).putInt(this.localVariableTypeTable.length + 2).putShort(this.localVariableTypeTableLength).putByteArray(this.localVariableTypeTable.data, 0, this.localVariableTypeTable.length);
            }
            AnnotationWriter annotationWriter3 = this.lastCodeRuntimeVisibleTypeAnnotation;
            if (annotationWriter3 != null) {
                annotationWriter3.putAnnotations(this.symbolTable.addConstantUtf8(TypeAnnotationsAttribute.visibleTag), byteVector);
            }
            AnnotationWriter annotationWriter4 = this.lastCodeRuntimeInvisibleTypeAnnotation;
            if (annotationWriter4 != null) {
                annotationWriter4.putAnnotations(this.symbolTable.addConstantUtf8(TypeAnnotationsAttribute.invisibleTag), byteVector);
            }
            Attribute attribute3 = this.firstCodeAttribute;
            if (attribute3 != null) {
                attribute3.putAttributes(this.symbolTable, this.code.data, this.code.length, this.maxStack, this.maxLocals, byteVector);
            }
        }
        if (this.numberOfExceptions > 0) {
            byteVector.putShort(this.symbolTable.addConstantUtf8(ExceptionsAttribute.tag)).putInt((this.numberOfExceptions * 2) + 2).putShort(this.numberOfExceptions);
            for (int i4 : this.exceptionIndexTable) {
                byteVector.putShort(i4);
            }
        }
        Attribute.putAttributes(this.symbolTable, this.accessFlags, this.signatureIndex, byteVector);
        AnnotationWriter.putAnnotations(this.symbolTable, this.lastRuntimeVisibleAnnotation, this.lastRuntimeInvisibleAnnotation, this.lastRuntimeVisibleTypeAnnotation, this.lastRuntimeInvisibleTypeAnnotation, byteVector);
        if (this.lastRuntimeVisibleParameterAnnotations != null) {
            int addConstantUtf8 = this.symbolTable.addConstantUtf8(ParameterAnnotationsAttribute.visibleTag);
            AnnotationWriter[] annotationWriterArr = this.lastRuntimeVisibleParameterAnnotations;
            int i5 = this.visibleAnnotableParameterCount;
            if (i5 == 0) {
                i5 = annotationWriterArr.length;
            }
            AnnotationWriter.putParameterAnnotations(addConstantUtf8, annotationWriterArr, i5, byteVector);
        }
        if (this.lastRuntimeInvisibleParameterAnnotations != null) {
            int addConstantUtf82 = this.symbolTable.addConstantUtf8(ParameterAnnotationsAttribute.invisibleTag);
            AnnotationWriter[] annotationWriterArr2 = this.lastRuntimeInvisibleParameterAnnotations;
            int i6 = this.invisibleAnnotableParameterCount;
            if (i6 == 0) {
                i6 = annotationWriterArr2.length;
            }
            AnnotationWriter.putParameterAnnotations(addConstantUtf82, annotationWriterArr2, i6, byteVector);
        }
        if (this.defaultValue != null) {
            byteVector.putShort(this.symbolTable.addConstantUtf8(AnnotationDefaultAttribute.tag)).putInt(this.defaultValue.length).putByteArray(this.defaultValue.data, 0, this.defaultValue.length);
        }
        if (this.parameters != null) {
            byteVector.putShort(this.symbolTable.addConstantUtf8(MethodParametersAttribute.tag)).putInt(this.parameters.length + 1).putByte(this.parametersCount).putByteArray(this.parameters.data, 0, this.parameters.length);
        }
        Attribute attribute4 = this.firstAttribute;
        if (attribute4 != null) {
            attribute4.putAttributes(this.symbolTable, byteVector);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void collectAttributePrototypes(Attribute.Set set) {
        set.addAttributes(this.firstAttribute);
        set.addAttributes(this.firstCodeAttribute);
    }
}
