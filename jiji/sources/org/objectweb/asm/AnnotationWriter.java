package org.objectweb.asm;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.TypeAnnotationsAttribute;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AnnotationWriter extends AnnotationVisitor {
    private final ByteVector annotation;
    private AnnotationWriter nextAnnotation;
    private int numElementValuePairs;
    private final int numElementValuePairsOffset;
    private final AnnotationWriter previousAnnotation;
    private final SymbolTable symbolTable;
    private final boolean useNamedValues;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnnotationWriter(SymbolTable symbolTable, boolean z, ByteVector byteVector, AnnotationWriter annotationWriter) {
        super(Opcodes.ASM9);
        this.symbolTable = symbolTable;
        this.useNamedValues = z;
        this.annotation = byteVector;
        this.numElementValuePairsOffset = byteVector.length == 0 ? -1 : byteVector.length - 2;
        this.previousAnnotation = annotationWriter;
        if (annotationWriter != null) {
            annotationWriter.nextAnnotation = this;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AnnotationWriter create(SymbolTable symbolTable, String str, AnnotationWriter annotationWriter) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(symbolTable.addConstantUtf8(str)).putShort(0);
        return new AnnotationWriter(symbolTable, true, byteVector, annotationWriter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AnnotationWriter create(SymbolTable symbolTable, int i, TypePath typePath, String str, AnnotationWriter annotationWriter) {
        ByteVector byteVector = new ByteVector();
        TypeReference.putTarget(i, byteVector);
        TypePath.put(typePath, byteVector);
        byteVector.putShort(symbolTable.addConstantUtf8(str)).putShort(0);
        return new AnnotationWriter(symbolTable, true, byteVector, annotationWriter);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visit(String str, Object obj) {
        this.numElementValuePairs++;
        if (this.useNamedValues) {
            this.annotation.putShort(this.symbolTable.addConstantUtf8(str));
        }
        if (obj instanceof String) {
            this.annotation.put12(115, this.symbolTable.addConstantUtf8((String) obj));
        } else if (obj instanceof Byte) {
            this.annotation.put12(66, this.symbolTable.addConstantInteger(((Byte) obj).byteValue()).index);
        } else if (obj instanceof Boolean) {
            this.annotation.put12(90, this.symbolTable.addConstantInteger(((Boolean) obj).booleanValue() ? 1 : 0).index);
        } else if (obj instanceof Character) {
            this.annotation.put12(67, this.symbolTable.addConstantInteger(((Character) obj).charValue()).index);
        } else if (obj instanceof Short) {
            this.annotation.put12(83, this.symbolTable.addConstantInteger(((Short) obj).shortValue()).index);
        } else if (obj instanceof Type) {
            this.annotation.put12(99, this.symbolTable.addConstantUtf8(((Type) obj).getDescriptor()));
        } else {
            int i = 0;
            if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                this.annotation.put12(91, bArr.length);
                int length = bArr.length;
                while (i < length) {
                    this.annotation.put12(66, this.symbolTable.addConstantInteger(bArr[i]).index);
                    i++;
                }
            } else if (obj instanceof boolean[]) {
                boolean[] zArr = (boolean[]) obj;
                this.annotation.put12(91, zArr.length);
                int length2 = zArr.length;
                while (i < length2) {
                    this.annotation.put12(90, this.symbolTable.addConstantInteger(zArr[i] ? 1 : 0).index);
                    i++;
                }
            } else if (obj instanceof short[]) {
                short[] sArr = (short[]) obj;
                this.annotation.put12(91, sArr.length);
                int length3 = sArr.length;
                while (i < length3) {
                    this.annotation.put12(83, this.symbolTable.addConstantInteger(sArr[i]).index);
                    i++;
                }
            } else if (obj instanceof char[]) {
                char[] cArr = (char[]) obj;
                this.annotation.put12(91, cArr.length);
                int length4 = cArr.length;
                while (i < length4) {
                    this.annotation.put12(67, this.symbolTable.addConstantInteger(cArr[i]).index);
                    i++;
                }
            } else if (obj instanceof int[]) {
                int[] iArr = (int[]) obj;
                this.annotation.put12(91, iArr.length);
                int length5 = iArr.length;
                while (i < length5) {
                    this.annotation.put12(73, this.symbolTable.addConstantInteger(iArr[i]).index);
                    i++;
                }
            } else if (obj instanceof long[]) {
                long[] jArr = (long[]) obj;
                this.annotation.put12(91, jArr.length);
                int length6 = jArr.length;
                while (i < length6) {
                    this.annotation.put12(74, this.symbolTable.addConstantLong(jArr[i]).index);
                    i++;
                }
            } else if (obj instanceof float[]) {
                float[] fArr = (float[]) obj;
                this.annotation.put12(91, fArr.length);
                int length7 = fArr.length;
                while (i < length7) {
                    this.annotation.put12(70, this.symbolTable.addConstantFloat(fArr[i]).index);
                    i++;
                }
            } else if (obj instanceof double[]) {
                double[] dArr = (double[]) obj;
                this.annotation.put12(91, dArr.length);
                int length8 = dArr.length;
                while (i < length8) {
                    this.annotation.put12(68, this.symbolTable.addConstantDouble(dArr[i]).index);
                    i++;
                }
            } else {
                Symbol addConstant = this.symbolTable.addConstant(obj);
                this.annotation.put12(".s.IFJDCS".charAt(addConstant.tag), addConstant.index);
            }
        }
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visitEnum(String str, String str2, String str3) {
        this.numElementValuePairs++;
        if (this.useNamedValues) {
            this.annotation.putShort(this.symbolTable.addConstantUtf8(str));
        }
        this.annotation.put12(101, this.symbolTable.addConstantUtf8(str2)).putShort(this.symbolTable.addConstantUtf8(str3));
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String str, String str2) {
        this.numElementValuePairs++;
        if (this.useNamedValues) {
            this.annotation.putShort(this.symbolTable.addConstantUtf8(str));
        }
        this.annotation.put12(64, this.symbolTable.addConstantUtf8(str2)).putShort(0);
        return new AnnotationWriter(this.symbolTable, true, this.annotation, null);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String str) {
        this.numElementValuePairs++;
        if (this.useNamedValues) {
            this.annotation.putShort(this.symbolTable.addConstantUtf8(str));
        }
        this.annotation.put12(91, 0);
        return new AnnotationWriter(this.symbolTable, false, this.annotation, null);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        if (this.numElementValuePairsOffset != -1) {
            byte[] bArr = this.annotation.data;
            int i = this.numElementValuePairsOffset;
            int i2 = this.numElementValuePairs;
            bArr[i] = (byte) (i2 >>> 8);
            bArr[i + 1] = (byte) i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int computeAnnotationsSize(String str) {
        if (str != null) {
            this.symbolTable.addConstantUtf8(str);
        }
        int i = 8;
        for (AnnotationWriter annotationWriter = this; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
            i += annotationWriter.annotation.length;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int computeAnnotationsSize(AnnotationWriter annotationWriter, AnnotationWriter annotationWriter2, AnnotationWriter annotationWriter3, AnnotationWriter annotationWriter4) {
        int computeAnnotationsSize = annotationWriter != null ? 0 + annotationWriter.computeAnnotationsSize(AnnotationsAttribute.visibleTag) : 0;
        if (annotationWriter2 != null) {
            computeAnnotationsSize += annotationWriter2.computeAnnotationsSize(AnnotationsAttribute.invisibleTag);
        }
        if (annotationWriter3 != null) {
            computeAnnotationsSize += annotationWriter3.computeAnnotationsSize(TypeAnnotationsAttribute.visibleTag);
        }
        return annotationWriter4 != null ? computeAnnotationsSize + annotationWriter4.computeAnnotationsSize(TypeAnnotationsAttribute.invisibleTag) : computeAnnotationsSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void putAnnotations(int i, ByteVector byteVector) {
        int i2 = 2;
        int i3 = 0;
        AnnotationWriter annotationWriter = null;
        for (AnnotationWriter annotationWriter2 = this; annotationWriter2 != null; annotationWriter2 = annotationWriter2.previousAnnotation) {
            annotationWriter2.visitEnd();
            i2 += annotationWriter2.annotation.length;
            i3++;
            annotationWriter = annotationWriter2;
        }
        byteVector.putShort(i);
        byteVector.putInt(i2);
        byteVector.putShort(i3);
        while (annotationWriter != null) {
            byteVector.putByteArray(annotationWriter.annotation.data, 0, annotationWriter.annotation.length);
            annotationWriter = annotationWriter.nextAnnotation;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void putAnnotations(SymbolTable symbolTable, AnnotationWriter annotationWriter, AnnotationWriter annotationWriter2, AnnotationWriter annotationWriter3, AnnotationWriter annotationWriter4, ByteVector byteVector) {
        if (annotationWriter != null) {
            annotationWriter.putAnnotations(symbolTable.addConstantUtf8(AnnotationsAttribute.visibleTag), byteVector);
        }
        if (annotationWriter2 != null) {
            annotationWriter2.putAnnotations(symbolTable.addConstantUtf8(AnnotationsAttribute.invisibleTag), byteVector);
        }
        if (annotationWriter3 != null) {
            annotationWriter3.putAnnotations(symbolTable.addConstantUtf8(TypeAnnotationsAttribute.visibleTag), byteVector);
        }
        if (annotationWriter4 != null) {
            annotationWriter4.putAnnotations(symbolTable.addConstantUtf8(TypeAnnotationsAttribute.invisibleTag), byteVector);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int computeParameterAnnotationsSize(String str, AnnotationWriter[] annotationWriterArr, int i) {
        AnnotationWriter annotationWriter;
        int i2 = (i * 2) + 7;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += annotationWriterArr[i3] == null ? 0 : annotationWriter.computeAnnotationsSize(str) - 8;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void putParameterAnnotations(int i, AnnotationWriter[] annotationWriterArr, int i2, ByteVector byteVector) {
        AnnotationWriter annotationWriter;
        int i3 = (i2 * 2) + 1;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += annotationWriterArr[i4] == null ? 0 : annotationWriter.computeAnnotationsSize(null) - 8;
        }
        byteVector.putShort(i);
        byteVector.putInt(i3);
        byteVector.putByte(i2);
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = 0;
            AnnotationWriter annotationWriter2 = null;
            for (AnnotationWriter annotationWriter3 = annotationWriterArr[i5]; annotationWriter3 != null; annotationWriter3 = annotationWriter3.previousAnnotation) {
                annotationWriter3.visitEnd();
                i6++;
                annotationWriter2 = annotationWriter3;
            }
            byteVector.putShort(i6);
            while (annotationWriter2 != null) {
                byteVector.putByteArray(annotationWriter2.annotation.data, 0, annotationWriter2.annotation.length);
                annotationWriter2 = annotationWriter2.nextAnnotation;
            }
        }
    }
}
