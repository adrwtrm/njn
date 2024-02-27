package org.objectweb.asm;

import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javassist.bytecode.AnnotationDefaultAttribute;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.BootstrapMethodsAttribute;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstantAttribute;
import javassist.bytecode.DeprecatedAttribute;
import javassist.bytecode.EnclosingMethodAttribute;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.InnerClassesAttribute;
import javassist.bytecode.MethodParametersAttribute;
import javassist.bytecode.NestHostAttribute;
import javassist.bytecode.NestMembersAttribute;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SourceFileAttribute;
import javassist.bytecode.SyntheticAttribute;
import javassist.bytecode.TypeAnnotationsAttribute;

/* loaded from: classes.dex */
public class ClassReader {
    static final int EXPAND_ASM_INSNS = 256;
    public static final int EXPAND_FRAMES = 8;
    private static final int INPUT_STREAM_DATA_CHUNK_SIZE = 4096;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    @Deprecated
    public final byte[] b;
    private final int[] bootstrapMethodOffsets;
    final byte[] classFileBuffer;
    private final ConstantDynamic[] constantDynamicValues;
    private final String[] constantUtf8Values;
    private final int[] cpInfoOffsets;
    public final int header;
    private final int maxStringLength;

    public ClassReader(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public ClassReader(byte[] bArr, int i, int i2) {
        this(bArr, i, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ClassReader(byte[] bArr, int i, boolean z) {
        int i2;
        this.classFileBuffer = bArr;
        this.b = bArr;
        if (z) {
            if (readShort(i + 6) > 60) {
                throw new IllegalArgumentException("Unsupported class file major version " + ((int) readShort(i2)));
            }
        }
        int readUnsignedShort = readUnsignedShort(i + 8);
        this.cpInfoOffsets = new int[readUnsignedShort];
        this.constantUtf8Values = new String[readUnsignedShort];
        int i3 = i + 10;
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = false;
        int i5 = 1;
        while (i5 < readUnsignedShort) {
            int i6 = i5 + 1;
            int i7 = i3 + 1;
            this.cpInfoOffsets[i5] = i7;
            int i8 = 3;
            switch (bArr[i3]) {
                case 1:
                    i8 = 3 + readUnsignedShort(i7);
                    if (i8 > i4) {
                        i4 = i8;
                    }
                    i5 = i6;
                    break;
                case 2:
                case 13:
                case 14:
                default:
                    throw new IllegalArgumentException();
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    i5 = i6;
                    i8 = 5;
                    break;
                case 5:
                case 6:
                    i6++;
                    i8 = 9;
                    i5 = i6;
                    break;
                case 7:
                case 8:
                case 16:
                case 19:
                case 20:
                    i5 = i6;
                    break;
                case 15:
                    i8 = 4;
                    i5 = i6;
                    break;
                case 17:
                    z2 = true;
                    z3 = true;
                    i5 = i6;
                    i8 = 5;
                    break;
                case 18:
                    z3 = true;
                    i5 = i6;
                    i8 = 5;
                    break;
            }
            i3 += i8;
        }
        this.maxStringLength = i4;
        this.header = i3;
        this.constantDynamicValues = z2 ? new ConstantDynamic[readUnsignedShort] : null;
        this.bootstrapMethodOffsets = z3 ? readBootstrapMethodsAttribute(i4) : null;
    }

    public ClassReader(InputStream inputStream) throws IOException {
        this(readStream(inputStream, false));
    }

    public ClassReader(String str) throws IOException {
        this(readStream(ClassLoader.getSystemResourceAsStream(str.replace('.', '/') + ".class"), true));
    }

    private static byte[] readStream(InputStream inputStream, boolean z) throws IOException {
        if (inputStream == null) {
            throw new IOException("Class not found");
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr, 0, 4096);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } finally {
            if (z) {
                inputStream.close();
            }
        }
    }

    public int getAccess() {
        return readUnsignedShort(this.header);
    }

    public String getClassName() {
        return readClass(this.header + 2, new char[this.maxStringLength]);
    }

    public String getSuperName() {
        return readClass(this.header + 4, new char[this.maxStringLength]);
    }

    public String[] getInterfaces() {
        int i = this.header + 6;
        int readUnsignedShort = readUnsignedShort(i);
        String[] strArr = new String[readUnsignedShort];
        if (readUnsignedShort > 0) {
            char[] cArr = new char[this.maxStringLength];
            for (int i2 = 0; i2 < readUnsignedShort; i2++) {
                i += 2;
                strArr[i2] = readClass(i, cArr);
            }
        }
        return strArr;
    }

    public void accept(ClassVisitor classVisitor, int i) {
        accept(classVisitor, new Attribute[0], i);
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attributeArr, int i) {
        int i2;
        int i3;
        int i4;
        String[] strArr;
        Context context = new Context();
        context.attributePrototypes = attributeArr;
        context.parsingOptions = i;
        context.charBuffer = new char[this.maxStringLength];
        char[] cArr = context.charBuffer;
        int i5 = this.header;
        int readUnsignedShort = readUnsignedShort(i5);
        String readClass = readClass(i5 + 2, cArr);
        String readClass2 = readClass(i5 + 4, cArr);
        int readUnsignedShort2 = readUnsignedShort(i5 + 6);
        String[] strArr2 = new String[readUnsignedShort2];
        int i6 = i5 + 8;
        for (int i7 = 0; i7 < readUnsignedShort2; i7++) {
            strArr2[i7] = readClass(i6, cArr);
            i6 += 2;
        }
        int firstAttributeOffset = getFirstAttributeOffset();
        int i8 = readUnsignedShort;
        int readUnsignedShort3 = readUnsignedShort(firstAttributeOffset - 2);
        String str = null;
        String str2 = null;
        int i9 = 0;
        String str3 = null;
        int i10 = 0;
        String str4 = null;
        String str5 = null;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        Attribute attribute = null;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        while (readUnsignedShort3 > 0) {
            String readUTF8 = readUTF8(firstAttributeOffset, cArr);
            int readInt = readInt(firstAttributeOffset + 2);
            int i20 = firstAttributeOffset + 6;
            String str6 = str;
            if (SourceFileAttribute.tag.equals(readUTF8)) {
                i2 = i20;
                str2 = readUTF8(i20, cArr);
            } else if (InnerClassesAttribute.tag.equals(readUTF8)) {
                i18 = i20;
                i2 = i18;
            } else if (EnclosingMethodAttribute.tag.equals(readUTF8)) {
                i11 = i20;
                i2 = i11;
            } else if (NestHostAttribute.tag.equals(readUTF8)) {
                i2 = i20;
                str5 = readClass(i20, cArr);
            } else if (NestMembersAttribute.tag.equals(readUTF8)) {
                i16 = i20;
                i2 = i16;
            } else if ("PermittedSubclasses".equals(readUTF8)) {
                i17 = i20;
                i2 = i17;
            } else {
                if (SignatureAttribute.tag.equals(readUTF8)) {
                    str3 = readUTF8(i20, cArr);
                } else if (AnnotationsAttribute.visibleTag.equals(readUTF8)) {
                    i12 = i20;
                    i2 = i12;
                } else if (TypeAnnotationsAttribute.visibleTag.equals(readUTF8)) {
                    i14 = i20;
                    i2 = i14;
                } else if (DeprecatedAttribute.tag.equals(readUTF8)) {
                    i8 |= 131072;
                } else if (SyntheticAttribute.tag.equals(readUTF8)) {
                    i8 |= 4096;
                } else if ("SourceDebugExtension".equals(readUTF8)) {
                    str = readUtf(i20, readInt, new char[readInt]);
                    i2 = i20;
                    i3 = i6;
                    i4 = readInt;
                    strArr = strArr2;
                    firstAttributeOffset = i2 + i4;
                    readUnsignedShort3--;
                    strArr2 = strArr;
                    i6 = i3;
                } else if (AnnotationsAttribute.invisibleTag.equals(readUTF8)) {
                    i13 = i20;
                    i2 = i13;
                } else if (TypeAnnotationsAttribute.invisibleTag.equals(readUTF8)) {
                    i15 = i20;
                    i2 = i15;
                } else if ("Record".equals(readUTF8)) {
                    i8 |= 65536;
                    i19 = i20;
                    i2 = i19;
                } else if ("Module".equals(readUTF8)) {
                    i9 = i20;
                    i2 = i9;
                } else if ("ModuleMainClass".equals(readUTF8)) {
                    str4 = readClass(i20, cArr);
                } else if ("ModulePackages".equals(readUTF8)) {
                    i10 = i20;
                    i2 = i10;
                } else {
                    if (BootstrapMethodsAttribute.tag.equals(readUTF8)) {
                        i2 = i20;
                        i3 = i6;
                        i4 = readInt;
                        strArr = strArr2;
                        str = str6;
                    } else {
                        i2 = i20;
                        i3 = i6;
                        i4 = readInt;
                        strArr = strArr2;
                        Attribute readAttribute = readAttribute(attributeArr, readUTF8, i2, readInt, cArr, -1, null);
                        readAttribute.nextAttribute = attribute;
                        attribute = readAttribute;
                        i8 = i8;
                        str = str6;
                        str2 = str2;
                    }
                    firstAttributeOffset = i2 + i4;
                    readUnsignedShort3--;
                    strArr2 = strArr;
                    i6 = i3;
                }
                i2 = i20;
            }
            i3 = i6;
            i4 = readInt;
            strArr = strArr2;
            str = str6;
            firstAttributeOffset = i2 + i4;
            readUnsignedShort3--;
            strArr2 = strArr;
            i6 = i3;
        }
        String str7 = str;
        String str8 = str2;
        int i21 = i6;
        String[] strArr3 = strArr2;
        Attribute attribute2 = attribute;
        classVisitor.visit(readInt(this.cpInfoOffsets[1] - 7), i8, readClass, str3, readClass2, strArr3);
        if ((i & 2) == 0 && (str8 != null || str7 != null)) {
            classVisitor.visitSource(str8, str7);
        }
        if (i9 != 0) {
            readModuleAttributes(classVisitor, context, i9, i10, str4);
        }
        String str9 = str5;
        if (str9 != null) {
            classVisitor.visitNestHost(str9);
        }
        int i22 = i11;
        if (i22 != 0) {
            String readClass3 = readClass(i22, cArr);
            int readUnsignedShort4 = readUnsignedShort(i22 + 2);
            classVisitor.visitOuterClass(readClass3, readUnsignedShort4 == 0 ? null : readUTF8(this.cpInfoOffsets[readUnsignedShort4], cArr), readUnsignedShort4 == 0 ? null : readUTF8(this.cpInfoOffsets[readUnsignedShort4] + 2, cArr));
        }
        int i23 = i12;
        if (i23 != 0) {
            int readUnsignedShort5 = readUnsignedShort(i23);
            int i24 = i23 + 2;
            while (true) {
                int i25 = readUnsignedShort5 - 1;
                if (readUnsignedShort5 <= 0) {
                    break;
                }
                i24 = readElementValues(classVisitor.visitAnnotation(readUTF8(i24, cArr), true), i24 + 2, true, cArr);
                readUnsignedShort5 = i25;
            }
        }
        int i26 = i13;
        if (i26 != 0) {
            int readUnsignedShort6 = readUnsignedShort(i26);
            int i27 = i26 + 2;
            while (true) {
                int i28 = readUnsignedShort6 - 1;
                if (readUnsignedShort6 <= 0) {
                    break;
                }
                i27 = readElementValues(classVisitor.visitAnnotation(readUTF8(i27, cArr), false), i27 + 2, true, cArr);
                readUnsignedShort6 = i28;
            }
        }
        int i29 = i14;
        if (i29 != 0) {
            int readUnsignedShort7 = readUnsignedShort(i29);
            int i30 = i29 + 2;
            while (true) {
                int i31 = readUnsignedShort7 - 1;
                if (readUnsignedShort7 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget = readTypeAnnotationTarget(context, i30);
                i30 = readElementValues(classVisitor.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget, cArr), true), readTypeAnnotationTarget + 2, true, cArr);
                readUnsignedShort7 = i31;
            }
        }
        int i32 = i15;
        if (i32 != 0) {
            int readUnsignedShort8 = readUnsignedShort(i32);
            int i33 = i32 + 2;
            while (true) {
                int i34 = readUnsignedShort8 - 1;
                if (readUnsignedShort8 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget2 = readTypeAnnotationTarget(context, i33);
                i33 = readElementValues(classVisitor.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget2, cArr), false), readTypeAnnotationTarget2 + 2, true, cArr);
                readUnsignedShort8 = i34;
            }
        }
        while (attribute2 != null) {
            Attribute attribute3 = attribute2.nextAttribute;
            attribute2.nextAttribute = null;
            classVisitor.visitAttribute(attribute2);
            attribute2 = attribute3;
        }
        int i35 = i16;
        if (i35 != 0) {
            int readUnsignedShort9 = readUnsignedShort(i35);
            int i36 = i35 + 2;
            while (true) {
                int i37 = readUnsignedShort9 - 1;
                if (readUnsignedShort9 <= 0) {
                    break;
                }
                classVisitor.visitNestMember(readClass(i36, cArr));
                i36 += 2;
                readUnsignedShort9 = i37;
            }
        }
        int i38 = i17;
        if (i38 != 0) {
            int readUnsignedShort10 = readUnsignedShort(i38);
            int i39 = i38 + 2;
            while (true) {
                int i40 = readUnsignedShort10 - 1;
                if (readUnsignedShort10 <= 0) {
                    break;
                }
                classVisitor.visitPermittedSubclass(readClass(i39, cArr));
                i39 += 2;
                readUnsignedShort10 = i40;
            }
        }
        int i41 = i18;
        if (i41 != 0) {
            int readUnsignedShort11 = readUnsignedShort(i41);
            int i42 = i41 + 2;
            while (true) {
                int i43 = readUnsignedShort11 - 1;
                if (readUnsignedShort11 <= 0) {
                    break;
                }
                classVisitor.visitInnerClass(readClass(i42, cArr), readClass(i42 + 2, cArr), readUTF8(i42 + 4, cArr), readUnsignedShort(i42 + 6));
                i42 += 8;
                readUnsignedShort11 = i43;
            }
        }
        int i44 = i19;
        if (i44 != 0) {
            int readUnsignedShort12 = readUnsignedShort(i44);
            int i45 = i44 + 2;
            while (true) {
                int i46 = readUnsignedShort12 - 1;
                if (readUnsignedShort12 <= 0) {
                    break;
                }
                i45 = readRecordComponent(classVisitor, context, i45);
                readUnsignedShort12 = i46;
            }
        }
        int readUnsignedShort13 = readUnsignedShort(i21);
        int i47 = i21 + 2;
        while (true) {
            int i48 = readUnsignedShort13 - 1;
            if (readUnsignedShort13 <= 0) {
                break;
            }
            i47 = readField(classVisitor, context, i47);
            readUnsignedShort13 = i48;
        }
        int readUnsignedShort14 = readUnsignedShort(i47);
        int i49 = i47 + 2;
        while (true) {
            int i50 = readUnsignedShort14 - 1;
            if (readUnsignedShort14 > 0) {
                i49 = readMethod(classVisitor, context, i49);
                readUnsignedShort14 = i50;
            } else {
                classVisitor.visitEnd();
                return;
            }
        }
    }

    private void readModuleAttributes(ClassVisitor classVisitor, Context context, int i, int i2, String str) {
        String[] strArr;
        char[] cArr = context.charBuffer;
        int i3 = i + 6;
        ModuleVisitor visitModule = classVisitor.visitModule(readModule(i, cArr), readUnsignedShort(i + 2), readUTF8(i + 4, cArr));
        if (visitModule == null) {
            return;
        }
        if (str != null) {
            visitModule.visitMainClass(str);
        }
        if (i2 != 0) {
            int readUnsignedShort = readUnsignedShort(i2);
            int i4 = i2 + 2;
            while (true) {
                int i5 = readUnsignedShort - 1;
                if (readUnsignedShort <= 0) {
                    break;
                }
                visitModule.visitPackage(readPackage(i4, cArr));
                i4 += 2;
                readUnsignedShort = i5;
            }
        }
        int readUnsignedShort2 = readUnsignedShort(i3);
        int i6 = i3 + 2;
        while (true) {
            int i7 = readUnsignedShort2 - 1;
            if (readUnsignedShort2 <= 0) {
                break;
            }
            i6 += 6;
            visitModule.visitRequire(readModule(i6, cArr), readUnsignedShort(i6 + 2), readUTF8(i6 + 4, cArr));
            readUnsignedShort2 = i7;
        }
        int readUnsignedShort3 = readUnsignedShort(i6);
        int i8 = i6 + 2;
        while (true) {
            int i9 = readUnsignedShort3 - 1;
            String[] strArr2 = null;
            if (readUnsignedShort3 <= 0) {
                break;
            }
            String readPackage = readPackage(i8, cArr);
            int readUnsignedShort4 = readUnsignedShort(i8 + 2);
            int readUnsignedShort5 = readUnsignedShort(i8 + 4);
            i8 += 6;
            if (readUnsignedShort5 != 0) {
                strArr2 = new String[readUnsignedShort5];
                for (int i10 = 0; i10 < readUnsignedShort5; i10++) {
                    strArr2[i10] = readModule(i8, cArr);
                    i8 += 2;
                }
            }
            visitModule.visitExport(readPackage, readUnsignedShort4, strArr2);
            readUnsignedShort3 = i9;
        }
        int readUnsignedShort6 = readUnsignedShort(i8);
        int i11 = i8 + 2;
        while (true) {
            int i12 = readUnsignedShort6 - 1;
            if (readUnsignedShort6 <= 0) {
                break;
            }
            String readPackage2 = readPackage(i11, cArr);
            int readUnsignedShort7 = readUnsignedShort(i11 + 2);
            int readUnsignedShort8 = readUnsignedShort(i11 + 4);
            i11 += 6;
            if (readUnsignedShort8 != 0) {
                strArr = new String[readUnsignedShort8];
                for (int i13 = 0; i13 < readUnsignedShort8; i13++) {
                    strArr[i13] = readModule(i11, cArr);
                    i11 += 2;
                }
            } else {
                strArr = null;
            }
            visitModule.visitOpen(readPackage2, readUnsignedShort7, strArr);
            readUnsignedShort6 = i12;
        }
        int readUnsignedShort9 = readUnsignedShort(i11);
        int i14 = i11 + 2;
        while (true) {
            int i15 = readUnsignedShort9 - 1;
            if (readUnsignedShort9 <= 0) {
                break;
            }
            visitModule.visitUse(readClass(i14, cArr));
            i14 += 2;
            readUnsignedShort9 = i15;
        }
        int readUnsignedShort10 = readUnsignedShort(i14);
        int i16 = i14 + 2;
        while (true) {
            int i17 = readUnsignedShort10 - 1;
            if (readUnsignedShort10 > 0) {
                String readClass = readClass(i16, cArr);
                int readUnsignedShort11 = readUnsignedShort(i16 + 2);
                i16 += 4;
                String[] strArr3 = new String[readUnsignedShort11];
                for (int i18 = 0; i18 < readUnsignedShort11; i18++) {
                    strArr3[i18] = readClass(i16, cArr);
                    i16 += 2;
                }
                visitModule.visitProvide(readClass, strArr3);
                readUnsignedShort10 = i17;
            } else {
                visitModule.visitEnd();
                return;
            }
        }
    }

    private int readRecordComponent(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        Context context2 = context;
        char[] cArr = context2.charBuffer;
        String readUTF8 = readUTF8(i, cArr);
        String readUTF82 = readUTF8(i + 2, cArr);
        int i3 = i + 4;
        int readUnsignedShort = readUnsignedShort(i3);
        int i4 = i3 + 2;
        int i5 = 0;
        Attribute attribute = null;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        String str = null;
        while (true) {
            int i9 = readUnsignedShort - 1;
            if (readUnsignedShort <= 0) {
                break;
            }
            String readUTF83 = readUTF8(i4, cArr);
            int readInt = readInt(i4 + 2);
            int i10 = i4 + 6;
            if (SignatureAttribute.tag.equals(readUTF83)) {
                str = readUTF8(i10, cArr);
                i2 = i10;
            } else if (AnnotationsAttribute.visibleTag.equals(readUTF83)) {
                i8 = i10;
                i2 = i8;
            } else if (TypeAnnotationsAttribute.visibleTag.equals(readUTF83)) {
                i6 = i10;
                i2 = i6;
            } else if (AnnotationsAttribute.invisibleTag.equals(readUTF83)) {
                i7 = i10;
                i2 = i7;
            } else if (TypeAnnotationsAttribute.invisibleTag.equals(readUTF83)) {
                i5 = i10;
                i2 = i5;
            } else {
                i2 = i10;
                Attribute attribute2 = attribute;
                attribute = readAttribute(context2.attributePrototypes, readUTF83, i2, readInt, cArr, -1, null);
                attribute.nextAttribute = attribute2;
                i8 = i8;
                i7 = i7;
                i6 = i6;
                i5 = i5;
            }
            i4 = i2 + readInt;
            context2 = context;
            readUnsignedShort = i9;
        }
        int i11 = i5;
        Attribute attribute3 = attribute;
        int i12 = i6;
        int i13 = i7;
        int i14 = i8;
        RecordComponentVisitor visitRecordComponent = classVisitor.visitRecordComponent(readUTF8, readUTF82, str);
        if (visitRecordComponent == null) {
            return i4;
        }
        if (i14 != 0) {
            int readUnsignedShort2 = readUnsignedShort(i14);
            int i15 = i14 + 2;
            while (true) {
                int i16 = readUnsignedShort2 - 1;
                if (readUnsignedShort2 <= 0) {
                    break;
                }
                i15 = readElementValues(visitRecordComponent.visitAnnotation(readUTF8(i15, cArr), true), i15 + 2, true, cArr);
                readUnsignedShort2 = i16;
            }
        }
        if (i13 != 0) {
            int readUnsignedShort3 = readUnsignedShort(i13);
            int i17 = i13 + 2;
            while (true) {
                int i18 = readUnsignedShort3 - 1;
                if (readUnsignedShort3 <= 0) {
                    break;
                }
                i17 = readElementValues(visitRecordComponent.visitAnnotation(readUTF8(i17, cArr), false), i17 + 2, true, cArr);
                readUnsignedShort3 = i18;
            }
        }
        if (i12 != 0) {
            int readUnsignedShort4 = readUnsignedShort(i12);
            int i19 = i12 + 2;
            while (true) {
                int i20 = readUnsignedShort4 - 1;
                if (readUnsignedShort4 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget = readTypeAnnotationTarget(context, i19);
                i19 = readElementValues(visitRecordComponent.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget, cArr), true), readTypeAnnotationTarget + 2, true, cArr);
                readUnsignedShort4 = i20;
            }
        }
        if (i11 != 0) {
            int readUnsignedShort5 = readUnsignedShort(i11);
            int i21 = i11 + 2;
            while (true) {
                int i22 = readUnsignedShort5 - 1;
                if (readUnsignedShort5 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget2 = readTypeAnnotationTarget(context, i21);
                i21 = readElementValues(visitRecordComponent.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget2, cArr), false), readTypeAnnotationTarget2 + 2, true, cArr);
                readUnsignedShort5 = i22;
            }
        }
        Attribute attribute4 = attribute3;
        while (attribute4 != null) {
            Attribute attribute5 = attribute4.nextAttribute;
            attribute4.nextAttribute = null;
            visitRecordComponent.visitAttribute(attribute4);
            attribute4 = attribute5;
        }
        visitRecordComponent.visitEnd();
        return i4;
    }

    private int readField(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        int i3;
        Context context2 = context;
        char[] cArr = context2.charBuffer;
        int readUnsignedShort = readUnsignedShort(i);
        String readUTF8 = readUTF8(i + 2, cArr);
        String readUTF82 = readUTF8(i + 4, cArr);
        int i4 = i + 6;
        int readUnsignedShort2 = readUnsignedShort(i4);
        int i5 = i4 + 2;
        int i6 = readUnsignedShort;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        Attribute attribute = null;
        String str = null;
        Object obj = null;
        while (true) {
            int i11 = readUnsignedShort2 - 1;
            if (readUnsignedShort2 <= 0) {
                break;
            }
            String readUTF83 = readUTF8(i5, cArr);
            int readInt = readInt(i5 + 2);
            int i12 = i5 + 6;
            if (ConstantAttribute.tag.equals(readUTF83)) {
                int readUnsignedShort3 = readUnsignedShort(i12);
                obj = readUnsignedShort3 == 0 ? null : readConst(readUnsignedShort3, cArr);
            } else if (SignatureAttribute.tag.equals(readUTF83)) {
                str = readUTF8(i12, cArr);
            } else {
                if (DeprecatedAttribute.tag.equals(readUTF83)) {
                    i3 = 131072 | i6;
                } else if (SyntheticAttribute.tag.equals(readUTF83)) {
                    i3 = i6 | 4096;
                } else {
                    if (AnnotationsAttribute.visibleTag.equals(readUTF83)) {
                        i10 = i12;
                        i2 = i10;
                    } else if (TypeAnnotationsAttribute.visibleTag.equals(readUTF83)) {
                        i8 = i12;
                        i2 = i8;
                    } else if (AnnotationsAttribute.invisibleTag.equals(readUTF83)) {
                        i9 = i12;
                        i2 = i9;
                    } else if (TypeAnnotationsAttribute.invisibleTag.equals(readUTF83)) {
                        i7 = i12;
                        i2 = i7;
                    } else {
                        i2 = i12;
                        Attribute attribute2 = attribute;
                        attribute = readAttribute(context2.attributePrototypes, readUTF83, i2, readInt, cArr, -1, null);
                        attribute.nextAttribute = attribute2;
                        i9 = i9;
                        i10 = i10;
                        i7 = i7;
                        i8 = i8;
                    }
                    i5 = i2 + readInt;
                    context2 = context;
                    readUnsignedShort2 = i11;
                }
                i6 = i3;
            }
            i2 = i12;
            i5 = i2 + readInt;
            context2 = context;
            readUnsignedShort2 = i11;
        }
        Attribute attribute3 = attribute;
        int i13 = i7;
        int i14 = i8;
        int i15 = i9;
        int i16 = i10;
        FieldVisitor visitField = classVisitor.visitField(i6, readUTF8, readUTF82, str, obj);
        if (visitField == null) {
            return i5;
        }
        if (i16 != 0) {
            int readUnsignedShort4 = readUnsignedShort(i16);
            int i17 = i16 + 2;
            while (true) {
                int i18 = readUnsignedShort4 - 1;
                if (readUnsignedShort4 <= 0) {
                    break;
                }
                i17 = readElementValues(visitField.visitAnnotation(readUTF8(i17, cArr), true), i17 + 2, true, cArr);
                readUnsignedShort4 = i18;
            }
        }
        if (i15 != 0) {
            int readUnsignedShort5 = readUnsignedShort(i15);
            int i19 = i15 + 2;
            while (true) {
                int i20 = readUnsignedShort5 - 1;
                if (readUnsignedShort5 <= 0) {
                    break;
                }
                i19 = readElementValues(visitField.visitAnnotation(readUTF8(i19, cArr), false), i19 + 2, true, cArr);
                readUnsignedShort5 = i20;
            }
        }
        if (i14 != 0) {
            int readUnsignedShort6 = readUnsignedShort(i14);
            int i21 = i14 + 2;
            while (true) {
                int i22 = readUnsignedShort6 - 1;
                if (readUnsignedShort6 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget = readTypeAnnotationTarget(context, i21);
                i21 = readElementValues(visitField.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget, cArr), true), readTypeAnnotationTarget + 2, true, cArr);
                readUnsignedShort6 = i22;
            }
        }
        if (i13 != 0) {
            int readUnsignedShort7 = readUnsignedShort(i13);
            int i23 = i13 + 2;
            while (true) {
                int i24 = readUnsignedShort7 - 1;
                if (readUnsignedShort7 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget2 = readTypeAnnotationTarget(context, i23);
                i23 = readElementValues(visitField.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget2, cArr), false), readTypeAnnotationTarget2 + 2, true, cArr);
                readUnsignedShort7 = i24;
            }
        }
        while (true) {
            Attribute attribute4 = attribute3;
            if (attribute4 != null) {
                attribute3 = attribute4.nextAttribute;
                attribute4.nextAttribute = null;
                visitField.visitAttribute(attribute4);
            } else {
                visitField.visitEnd();
                return i5;
            }
        }
    }

    private int readMethod(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        int i3;
        int i4;
        char[] cArr = context.charBuffer;
        context.currentMethodAccessFlags = readUnsignedShort(i);
        context.currentMethodName = readUTF8(i + 2, cArr);
        int i5 = i + 4;
        context.currentMethodDescriptor = readUTF8(i5, cArr);
        int i6 = i + 6;
        int readUnsignedShort = readUnsignedShort(i6);
        int i7 = i6 + 2;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        Attribute attribute = null;
        boolean z = false;
        int i14 = 0;
        String[] strArr = null;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        while (true) {
            int i19 = readUnsignedShort - 1;
            if (readUnsignedShort <= 0) {
                break;
            }
            String readUTF8 = readUTF8(i7, cArr);
            int readInt = readInt(i7 + 2);
            int i20 = i7 + 6;
            int i21 = i8;
            if (CodeAttribute.tag.equals(readUTF8)) {
                if ((context.parsingOptions & 1) == 0) {
                    i18 = i20;
                    i8 = i21;
                    i4 = i18;
                    i7 = i4 + readInt;
                    readUnsignedShort = i19;
                } else {
                    i2 = i9;
                    i3 = i10;
                    i8 = i21;
                    i9 = i2;
                    i10 = i3;
                }
            } else {
                if (ExceptionsAttribute.tag.equals(readUTF8)) {
                    int readUnsignedShort2 = readUnsignedShort(i20);
                    String[] strArr2 = new String[readUnsignedShort2];
                    int i22 = i9;
                    int i23 = i10;
                    int i24 = i20 + 2;
                    for (int i25 = 0; i25 < readUnsignedShort2; i25++) {
                        strArr2[i25] = readClass(i24, cArr);
                        i24 += 2;
                    }
                    strArr = strArr2;
                    i14 = i20;
                    i8 = i21;
                    i9 = i22;
                    i10 = i23;
                    i4 = i14;
                } else {
                    i2 = i9;
                    i3 = i10;
                    if (SignatureAttribute.tag.equals(readUTF8)) {
                        i13 = readUnsignedShort(i20);
                    } else if (DeprecatedAttribute.tag.equals(readUTF8)) {
                        context.currentMethodAccessFlags |= 131072;
                    } else if (AnnotationsAttribute.visibleTag.equals(readUTF8)) {
                        i10 = i20;
                        i8 = i21;
                        i9 = i2;
                        i4 = i10;
                    } else if (TypeAnnotationsAttribute.visibleTag.equals(readUTF8)) {
                        i8 = i20;
                        i4 = i8;
                        i9 = i2;
                        i10 = i3;
                    } else if (AnnotationDefaultAttribute.tag.equals(readUTF8)) {
                        i11 = i20;
                        i8 = i21;
                        i9 = i2;
                        i10 = i3;
                        i4 = i11;
                    } else if (SyntheticAttribute.tag.equals(readUTF8)) {
                        context.currentMethodAccessFlags |= 4096;
                        i8 = i21;
                        i9 = i2;
                        i10 = i3;
                        z = true;
                    } else if (AnnotationsAttribute.invisibleTag.equals(readUTF8)) {
                        i9 = i20;
                        i8 = i21;
                        i10 = i3;
                        i4 = i9;
                    } else if (TypeAnnotationsAttribute.invisibleTag.equals(readUTF8)) {
                        i15 = i20;
                        i8 = i21;
                        i9 = i2;
                        i10 = i3;
                        i4 = i15;
                    } else if (ParameterAnnotationsAttribute.visibleTag.equals(readUTF8)) {
                        i16 = i20;
                        i8 = i21;
                        i9 = i2;
                        i10 = i3;
                        i4 = i16;
                    } else if (ParameterAnnotationsAttribute.invisibleTag.equals(readUTF8)) {
                        i17 = i20;
                        i8 = i21;
                        i9 = i2;
                        i10 = i3;
                        i4 = i17;
                    } else if (MethodParametersAttribute.tag.equals(readUTF8)) {
                        i12 = i20;
                        i8 = i21;
                        i9 = i2;
                        i10 = i3;
                        i4 = i12;
                    } else {
                        i4 = i20;
                        Attribute readAttribute = readAttribute(context.attributePrototypes, readUTF8, i20, readInt, cArr, -1, null);
                        readAttribute.nextAttribute = attribute;
                        attribute = readAttribute;
                        i13 = i13;
                        i9 = i2;
                        i10 = i3;
                        i8 = i21;
                        i11 = i11;
                        i12 = i12;
                    }
                    i8 = i21;
                    i9 = i2;
                    i10 = i3;
                }
                i7 = i4 + readInt;
                readUnsignedShort = i19;
            }
            i4 = i20;
            i7 = i4 + readInt;
            readUnsignedShort = i19;
        }
        int i26 = i8;
        int i27 = i9;
        int i28 = i10;
        int i29 = i11;
        int i30 = i12;
        int i31 = i13;
        MethodVisitor visitMethod = classVisitor.visitMethod(context.currentMethodAccessFlags, context.currentMethodName, context.currentMethodDescriptor, i31 == 0 ? null : readUtf(i31, cArr), strArr);
        if (visitMethod == null) {
            return i7;
        }
        if (visitMethod instanceof MethodWriter) {
            MethodWriter methodWriter = (MethodWriter) visitMethod;
            if (methodWriter.canCopyMethodAttributes(this, z, (context.currentMethodAccessFlags & 131072) != 0, readUnsignedShort(i5), i31, i14)) {
                methodWriter.setMethodAttributesSource(i, i7 - i);
                return i7;
            }
        }
        if (i30 != 0 && (context.parsingOptions & 2) == 0) {
            int readByte = readByte(i30);
            int i32 = i30 + 1;
            while (true) {
                int i33 = readByte - 1;
                if (readByte <= 0) {
                    break;
                }
                visitMethod.visitParameter(readUTF8(i32, cArr), readUnsignedShort(i32 + 2));
                i32 += 4;
                readByte = i33;
            }
        }
        if (i29 != 0) {
            AnnotationVisitor visitAnnotationDefault = visitMethod.visitAnnotationDefault();
            readElementValue(visitAnnotationDefault, i29, null, cArr);
            if (visitAnnotationDefault != null) {
                visitAnnotationDefault.visitEnd();
            }
        }
        if (i28 != 0) {
            int readUnsignedShort3 = readUnsignedShort(i28);
            int i34 = i28 + 2;
            while (true) {
                int i35 = readUnsignedShort3 - 1;
                if (readUnsignedShort3 <= 0) {
                    break;
                }
                i34 = readElementValues(visitMethod.visitAnnotation(readUTF8(i34, cArr), true), i34 + 2, true, cArr);
                readUnsignedShort3 = i35;
            }
        }
        if (i27 != 0) {
            int readUnsignedShort4 = readUnsignedShort(i27);
            int i36 = i27 + 2;
            while (true) {
                int i37 = readUnsignedShort4 - 1;
                if (readUnsignedShort4 <= 0) {
                    break;
                }
                i36 = readElementValues(visitMethod.visitAnnotation(readUTF8(i36, cArr), false), i36 + 2, true, cArr);
                readUnsignedShort4 = i37;
            }
        }
        if (i26 != 0) {
            int readUnsignedShort5 = readUnsignedShort(i26);
            int i38 = i26 + 2;
            while (true) {
                int i39 = readUnsignedShort5 - 1;
                if (readUnsignedShort5 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget = readTypeAnnotationTarget(context, i38);
                i38 = readElementValues(visitMethod.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget, cArr), true), readTypeAnnotationTarget + 2, true, cArr);
                readUnsignedShort5 = i39;
            }
        }
        int i40 = i15;
        if (i40 != 0) {
            int readUnsignedShort6 = readUnsignedShort(i40);
            int i41 = i40 + 2;
            while (true) {
                int i42 = readUnsignedShort6 - 1;
                if (readUnsignedShort6 <= 0) {
                    break;
                }
                int readTypeAnnotationTarget2 = readTypeAnnotationTarget(context, i41);
                i41 = readElementValues(visitMethod.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(readTypeAnnotationTarget2, cArr), false), readTypeAnnotationTarget2 + 2, true, cArr);
                readUnsignedShort6 = i42;
            }
        }
        int i43 = i16;
        if (i43 != 0) {
            readParameterAnnotations(visitMethod, context, i43, true);
        }
        int i44 = i17;
        if (i44 != 0) {
            readParameterAnnotations(visitMethod, context, i44, false);
        }
        while (attribute != null) {
            Attribute attribute2 = attribute.nextAttribute;
            attribute.nextAttribute = null;
            visitMethod.visitAttribute(attribute);
            attribute = attribute2;
        }
        int i45 = i18;
        if (i45 != 0) {
            visitMethod.visitCode();
            readCode(visitMethod, context, i45);
        }
        visitMethod.visitEnd();
        return i7;
    }

    /* JADX WARN: Removed duplicated region for block: B:232:0x079a  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x07ca  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x07d1  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x07f1  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x07c2 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readCode(org.objectweb.asm.MethodVisitor r40, org.objectweb.asm.Context r41, int r42) {
        /*
            Method dump skipped, instructions count: 3360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.readCode(org.objectweb.asm.MethodVisitor, org.objectweb.asm.Context, int):void");
    }

    protected Label readLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            labelArr[i] = new Label();
        }
        return labelArr[i];
    }

    private Label createLabel(int i, Label[] labelArr) {
        Label readLabel = readLabel(i, labelArr);
        readLabel.flags = (short) (readLabel.flags & (-2));
        return readLabel;
    }

    private void createDebugLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            Label readLabel = readLabel(i, labelArr);
            readLabel.flags = (short) (readLabel.flags | 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int[] readTypeAnnotations(org.objectweb.asm.MethodVisitor r11, org.objectweb.asm.Context r12, int r13, boolean r14) {
        /*
            r10 = this;
            char[] r0 = r12.charBuffer
            int r1 = r10.readUnsignedShort(r13)
            int[] r2 = new int[r1]
            int r13 = r13 + 2
            r3 = 0
        Lb:
            if (r3 >= r1) goto L84
            r2[r3] = r13
            int r4 = r10.readInt(r13)
            int r5 = r4 >>> 24
            r6 = 23
            if (r5 == r6) goto L4d
            switch(r5) {
                case 16: goto L4d;
                case 17: goto L4d;
                case 18: goto L4d;
                default: goto L1c;
            }
        L1c:
            switch(r5) {
                case 64: goto L28;
                case 65: goto L28;
                case 66: goto L4d;
                case 67: goto L4d;
                case 68: goto L4d;
                case 69: goto L4d;
                case 70: goto L4d;
                case 71: goto L25;
                case 72: goto L25;
                case 73: goto L25;
                case 74: goto L25;
                case 75: goto L25;
                default: goto L1f;
            }
        L1f:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            r11.<init>()
            throw r11
        L25:
            int r13 = r13 + 4
            goto L4f
        L28:
            int r6 = r13 + 1
            int r6 = r10.readUnsignedShort(r6)
            int r13 = r13 + 3
        L30:
            int r7 = r6 + (-1)
            if (r6 <= 0) goto L4f
            int r6 = r10.readUnsignedShort(r13)
            int r8 = r13 + 2
            int r8 = r10.readUnsignedShort(r8)
            int r13 = r13 + 6
            org.objectweb.asm.Label[] r9 = r12.currentMethodLabels
            r10.createLabel(r6, r9)
            int r6 = r6 + r8
            org.objectweb.asm.Label[] r8 = r12.currentMethodLabels
            r10.createLabel(r6, r8)
            r6 = r7
            goto L30
        L4d:
            int r13 = r13 + 3
        L4f:
            int r6 = r10.readByte(r13)
            r7 = 66
            r8 = 0
            r9 = 1
            if (r5 != r7) goto L78
            if (r6 != 0) goto L5c
            goto L63
        L5c:
            org.objectweb.asm.TypePath r8 = new org.objectweb.asm.TypePath
            byte[] r5 = r10.classFileBuffer
            r8.<init>(r5, r13)
        L63:
            int r6 = r6 * 2
            int r6 = r6 + r9
            int r13 = r13 + r6
            java.lang.String r5 = r10.readUTF8(r13, r0)
            int r13 = r13 + 2
            r4 = r4 & (-256(0xffffffffffffff00, float:NaN))
            org.objectweb.asm.AnnotationVisitor r4 = r11.visitTryCatchAnnotation(r4, r8, r5, r14)
            int r13 = r10.readElementValues(r4, r13, r9, r0)
            goto L81
        L78:
            int r6 = r6 * 2
            int r6 = r6 + 3
            int r13 = r13 + r6
            int r13 = r10.readElementValues(r8, r13, r9, r0)
        L81:
            int r3 = r3 + 1
            goto Lb
        L84:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.readTypeAnnotations(org.objectweb.asm.MethodVisitor, org.objectweb.asm.Context, int, boolean):int[]");
    }

    private int getTypeAnnotationBytecodeOffset(int[] iArr, int i) {
        if (iArr == null || i >= iArr.length || readByte(iArr[i]) < 67) {
            return -1;
        }
        return readUnsignedShort(iArr[i] + 1);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readTypeAnnotationTarget(org.objectweb.asm.Context r10, int r11) {
        /*
            r9 = this;
            int r0 = r9.readInt(r11)
            int r1 = r0 >>> 24
            r2 = 1
            if (r1 == 0) goto L70
            if (r1 == r2) goto L70
            r3 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            switch(r1) {
                case 16: goto L6b;
                case 17: goto L6b;
                case 18: goto L6b;
                case 19: goto L68;
                case 20: goto L68;
                case 21: goto L68;
                case 22: goto L70;
                case 23: goto L6b;
                default: goto L10;
            }
        L10:
            switch(r1) {
                case 64: goto L22;
                case 65: goto L22;
                case 66: goto L6b;
                case 67: goto L20;
                case 68: goto L20;
                case 69: goto L20;
                case 70: goto L20;
                case 71: goto L19;
                case 72: goto L19;
                case 73: goto L19;
                case 74: goto L19;
                case 75: goto L19;
                default: goto L13;
            }
        L13:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            r10.<init>()
            throw r10
        L19:
            r1 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            r0 = r0 & r1
            int r11 = r11 + 4
            goto L75
        L20:
            r0 = r0 & r3
            goto L6d
        L22:
            r0 = r0 & r3
            int r1 = r11 + 1
            int r1 = r9.readUnsignedShort(r1)
            int r11 = r11 + 3
            org.objectweb.asm.Label[] r3 = new org.objectweb.asm.Label[r1]
            r10.currentLocalVariableAnnotationRangeStarts = r3
            org.objectweb.asm.Label[] r3 = new org.objectweb.asm.Label[r1]
            r10.currentLocalVariableAnnotationRangeEnds = r3
            int[] r3 = new int[r1]
            r10.currentLocalVariableAnnotationRangeIndices = r3
            r3 = 0
        L38:
            if (r3 >= r1) goto L75
            int r4 = r9.readUnsignedShort(r11)
            int r5 = r11 + 2
            int r5 = r9.readUnsignedShort(r5)
            int r6 = r11 + 4
            int r6 = r9.readUnsignedShort(r6)
            int r11 = r11 + 6
            org.objectweb.asm.Label[] r7 = r10.currentLocalVariableAnnotationRangeStarts
            org.objectweb.asm.Label[] r8 = r10.currentMethodLabels
            org.objectweb.asm.Label r8 = r9.createLabel(r4, r8)
            r7[r3] = r8
            org.objectweb.asm.Label[] r7 = r10.currentLocalVariableAnnotationRangeEnds
            int r4 = r4 + r5
            org.objectweb.asm.Label[] r5 = r10.currentMethodLabels
            org.objectweb.asm.Label r4 = r9.createLabel(r4, r5)
            r7[r3] = r4
            int[] r4 = r10.currentLocalVariableAnnotationRangeIndices
            r4[r3] = r6
            int r3 = r3 + 1
            goto L38
        L68:
            r0 = r0 & r3
            int r11 = r11 + r2
            goto L75
        L6b:
            r0 = r0 & (-256(0xffffffffffffff00, float:NaN))
        L6d:
            int r11 = r11 + 3
            goto L75
        L70:
            r1 = -65536(0xffffffffffff0000, float:NaN)
            r0 = r0 & r1
            int r11 = r11 + 2
        L75:
            r10.currentTypeAnnotationTarget = r0
            int r0 = r9.readByte(r11)
            if (r0 != 0) goto L7f
            r1 = 0
            goto L86
        L7f:
            org.objectweb.asm.TypePath r1 = new org.objectweb.asm.TypePath
            byte[] r3 = r9.classFileBuffer
            r1.<init>(r3, r11)
        L86:
            r10.currentTypeAnnotationTargetPath = r1
            int r11 = r11 + r2
            int r0 = r0 * 2
            int r11 = r11 + r0
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.readTypeAnnotationTarget(org.objectweb.asm.Context, int):int");
    }

    private void readParameterAnnotations(MethodVisitor methodVisitor, Context context, int i, boolean z) {
        int i2 = i + 1;
        int i3 = this.classFileBuffer[i] & 255;
        methodVisitor.visitAnnotableParameterCount(i3, z);
        char[] cArr = context.charBuffer;
        for (int i4 = 0; i4 < i3; i4++) {
            int readUnsignedShort = readUnsignedShort(i2);
            i2 += 2;
            while (true) {
                int i5 = readUnsignedShort - 1;
                if (readUnsignedShort > 0) {
                    i2 = readElementValues(methodVisitor.visitParameterAnnotation(i4, readUTF8(i2, cArr), z), i2 + 2, true, cArr);
                    readUnsignedShort = i5;
                }
            }
        }
    }

    private int readElementValues(AnnotationVisitor annotationVisitor, int i, boolean z, char[] cArr) {
        int readUnsignedShort = readUnsignedShort(i);
        int i2 = i + 2;
        if (!z) {
            while (true) {
                int i3 = readUnsignedShort - 1;
                if (readUnsignedShort <= 0) {
                    break;
                }
                i2 = readElementValue(annotationVisitor, i2, null, cArr);
                readUnsignedShort = i3;
            }
        } else {
            while (true) {
                int i4 = readUnsignedShort - 1;
                if (readUnsignedShort <= 0) {
                    break;
                }
                i2 = readElementValue(annotationVisitor, i2 + 2, readUTF8(i2, cArr), cArr);
                readUnsignedShort = i4;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return i2;
    }

    private int readElementValue(AnnotationVisitor annotationVisitor, int i, String str, char[] cArr) {
        int i2 = 0;
        if (annotationVisitor == null) {
            int i3 = this.classFileBuffer[i] & 255;
            if (i3 != 64) {
                if (i3 != 91) {
                    return i3 != 101 ? i + 3 : i + 5;
                }
                return readElementValues(null, i + 1, false, cArr);
            }
            return readElementValues(null, i + 3, true, cArr);
        }
        int i4 = i + 1;
        int i5 = this.classFileBuffer[i] & 255;
        if (i5 != 64) {
            if (i5 != 70) {
                if (i5 == 83) {
                    annotationVisitor.visit(str, Short.valueOf((short) readInt(this.cpInfoOffsets[readUnsignedShort(i4)])));
                } else if (i5 == 99) {
                    annotationVisitor.visit(str, Type.getType(readUTF8(i4, cArr)));
                } else if (i5 == 101) {
                    annotationVisitor.visitEnum(str, readUTF8(i4, cArr), readUTF8(i4 + 2, cArr));
                    return i4 + 4;
                } else if (i5 == 115) {
                    annotationVisitor.visit(str, readUTF8(i4, cArr));
                } else if (i5 != 73 && i5 != 74) {
                    if (i5 == 90) {
                        annotationVisitor.visit(str, readInt(this.cpInfoOffsets[readUnsignedShort(i4)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
                    } else if (i5 != 91) {
                        switch (i5) {
                            case 66:
                                annotationVisitor.visit(str, Byte.valueOf((byte) readInt(this.cpInfoOffsets[readUnsignedShort(i4)])));
                                break;
                            case 67:
                                annotationVisitor.visit(str, Character.valueOf((char) readInt(this.cpInfoOffsets[readUnsignedShort(i4)])));
                                break;
                            case 68:
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    } else {
                        int readUnsignedShort = readUnsignedShort(i4);
                        int i6 = i4 + 2;
                        if (readUnsignedShort == 0) {
                            return readElementValues(annotationVisitor.visitArray(str), i6 - 2, false, cArr);
                        }
                        int i7 = this.classFileBuffer[i6] & 255;
                        if (i7 == 70) {
                            float[] fArr = new float[readUnsignedShort];
                            while (i2 < readUnsignedShort) {
                                fArr[i2] = Float.intBitsToFloat(readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]));
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, fArr);
                            return i6;
                        } else if (i7 == 83) {
                            short[] sArr = new short[readUnsignedShort];
                            while (i2 < readUnsignedShort) {
                                sArr[i2] = (short) readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, sArr);
                            return i6;
                        } else if (i7 == 90) {
                            boolean[] zArr = new boolean[readUnsignedShort];
                            for (int i8 = 0; i8 < readUnsignedShort; i8++) {
                                zArr[i8] = readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]) != 0;
                                i6 += 3;
                            }
                            annotationVisitor.visit(str, zArr);
                            return i6;
                        } else if (i7 == 73) {
                            int[] iArr = new int[readUnsignedShort];
                            while (i2 < readUnsignedShort) {
                                iArr[i2] = readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, iArr);
                            return i6;
                        } else if (i7 != 74) {
                            switch (i7) {
                                case 66:
                                    byte[] bArr = new byte[readUnsignedShort];
                                    while (i2 < readUnsignedShort) {
                                        bArr[i2] = (byte) readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                        i6 += 3;
                                        i2++;
                                    }
                                    annotationVisitor.visit(str, bArr);
                                    return i6;
                                case 67:
                                    char[] cArr2 = new char[readUnsignedShort];
                                    while (i2 < readUnsignedShort) {
                                        cArr2[i2] = (char) readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                        i6 += 3;
                                        i2++;
                                    }
                                    annotationVisitor.visit(str, cArr2);
                                    return i6;
                                case 68:
                                    double[] dArr = new double[readUnsignedShort];
                                    while (i2 < readUnsignedShort) {
                                        dArr[i2] = Double.longBitsToDouble(readLong(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]));
                                        i6 += 3;
                                        i2++;
                                    }
                                    annotationVisitor.visit(str, dArr);
                                    return i6;
                                default:
                                    return readElementValues(annotationVisitor.visitArray(str), i6 - 2, false, cArr);
                            }
                        } else {
                            long[] jArr = new long[readUnsignedShort];
                            while (i2 < readUnsignedShort) {
                                jArr[i2] = readLong(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, jArr);
                            return i6;
                        }
                    }
                }
                return i4 + 2;
            }
            annotationVisitor.visit(str, readConst(readUnsignedShort(i4), cArr));
            return i4 + 2;
        }
        return readElementValues(annotationVisitor.visitAnnotation(str, readUTF8(i4, cArr)), i4 + 2, true, cArr);
    }

    private void computeImplicitFrame(Context context) {
        int i;
        String str = context.currentMethodDescriptor;
        Object[] objArr = context.currentFrameLocalTypes;
        int i2 = 0;
        if ((context.currentMethodAccessFlags & 8) == 0) {
            if ("<init>".equals(context.currentMethodName)) {
                objArr[0] = Opcodes.UNINITIALIZED_THIS;
            } else {
                objArr[0] = readClass(this.header + 2, context.charBuffer);
            }
            i2 = 1;
        }
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char charAt = str.charAt(i3);
            if (charAt == 'F') {
                i = i2 + 1;
                objArr[i2] = Opcodes.FLOAT;
            } else if (charAt != 'L') {
                if (charAt != 'S' && charAt != 'I') {
                    if (charAt == 'J') {
                        i = i2 + 1;
                        objArr[i2] = Opcodes.LONG;
                    } else if (charAt != 'Z') {
                        if (charAt != '[') {
                            switch (charAt) {
                                case 'B':
                                case 'C':
                                    break;
                                case 'D':
                                    i = i2 + 1;
                                    objArr[i2] = Opcodes.DOUBLE;
                                    break;
                                default:
                                    context.currentFrameLocalCount = i2;
                                    return;
                            }
                        } else {
                            while (str.charAt(i4) == '[') {
                                i4++;
                            }
                            if (str.charAt(i4) == 'L') {
                                do {
                                    i4++;
                                } while (str.charAt(i4) != ';');
                                int i5 = i4 + 1;
                                objArr[i2] = str.substring(i3, i5);
                                i3 = i5;
                                i2++;
                            } else {
                                int i52 = i4 + 1;
                                objArr[i2] = str.substring(i3, i52);
                                i3 = i52;
                                i2++;
                            }
                        }
                    }
                }
                i = i2 + 1;
                objArr[i2] = Opcodes.INTEGER;
            } else {
                int i6 = i4;
                while (str.charAt(i6) != ';') {
                    i6++;
                }
                objArr[i2] = str.substring(i4, i6);
                i2++;
                i3 = i6 + 1;
            }
            i2 = i;
            i3 = i4;
        }
    }

    private int readStackMapFrame(int i, boolean z, boolean z2, Context context) {
        int i2;
        int i3;
        char[] cArr = context.charBuffer;
        Label[] labelArr = context.currentMethodLabels;
        if (z) {
            i2 = i + 1;
            i3 = this.classFileBuffer[i] & 255;
        } else {
            context.currentFrameOffset = -1;
            i2 = i;
            i3 = 255;
        }
        context.currentFrameLocalCountDelta = 0;
        if (i3 < 64) {
            context.currentFrameType = 3;
            context.currentFrameStackCount = 0;
        } else if (i3 < 128) {
            i3 -= 64;
            i2 = readVerificationTypeInfo(i2, context.currentFrameStackTypes, 0, cArr, labelArr);
            context.currentFrameType = 4;
            context.currentFrameStackCount = 1;
        } else if (i3 >= 247) {
            int readUnsignedShort = readUnsignedShort(i2);
            i2 += 2;
            if (i3 == 247) {
                i2 = readVerificationTypeInfo(i2, context.currentFrameStackTypes, 0, cArr, labelArr);
                context.currentFrameType = 4;
                context.currentFrameStackCount = 1;
            } else if (i3 >= 248 && i3 < 251) {
                context.currentFrameType = 2;
                context.currentFrameLocalCountDelta = 251 - i3;
                context.currentFrameLocalCount -= context.currentFrameLocalCountDelta;
                context.currentFrameStackCount = 0;
            } else if (i3 == 251) {
                context.currentFrameType = 3;
                context.currentFrameStackCount = 0;
            } else if (i3 < 255) {
                int i4 = i3 - 251;
                int i5 = z2 ? context.currentFrameLocalCount : 0;
                int i6 = i4;
                while (i6 > 0) {
                    i2 = readVerificationTypeInfo(i2, context.currentFrameLocalTypes, i5, cArr, labelArr);
                    i6--;
                    i5++;
                }
                context.currentFrameType = 1;
                context.currentFrameLocalCountDelta = i4;
                context.currentFrameLocalCount += context.currentFrameLocalCountDelta;
                context.currentFrameStackCount = 0;
            } else {
                int readUnsignedShort2 = readUnsignedShort(i2);
                int i7 = i2 + 2;
                context.currentFrameType = 0;
                context.currentFrameLocalCountDelta = readUnsignedShort2;
                context.currentFrameLocalCount = readUnsignedShort2;
                for (int i8 = 0; i8 < readUnsignedShort2; i8++) {
                    i7 = readVerificationTypeInfo(i7, context.currentFrameLocalTypes, i8, cArr, labelArr);
                }
                int readUnsignedShort3 = readUnsignedShort(i7);
                i2 = i7 + 2;
                context.currentFrameStackCount = readUnsignedShort3;
                for (int i9 = 0; i9 < readUnsignedShort3; i9++) {
                    i2 = readVerificationTypeInfo(i2, context.currentFrameStackTypes, i9, cArr, labelArr);
                }
            }
            i3 = readUnsignedShort;
        } else {
            throw new IllegalArgumentException();
        }
        context.currentFrameOffset += i3 + 1;
        createLabel(context.currentFrameOffset, labelArr);
        return i2;
    }

    private int readVerificationTypeInfo(int i, Object[] objArr, int i2, char[] cArr, Label[] labelArr) {
        int i3 = i + 1;
        switch (this.classFileBuffer[i] & 255) {
            case 0:
                objArr[i2] = Opcodes.TOP;
                return i3;
            case 1:
                objArr[i2] = Opcodes.INTEGER;
                return i3;
            case 2:
                objArr[i2] = Opcodes.FLOAT;
                return i3;
            case 3:
                objArr[i2] = Opcodes.DOUBLE;
                return i3;
            case 4:
                objArr[i2] = Opcodes.LONG;
                return i3;
            case 5:
                objArr[i2] = Opcodes.NULL;
                return i3;
            case 6:
                objArr[i2] = Opcodes.UNINITIALIZED_THIS;
                return i3;
            case 7:
                objArr[i2] = readClass(i3, cArr);
                break;
            case 8:
                objArr[i2] = createLabel(readUnsignedShort(i3), labelArr);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return i3 + 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int getFirstAttributeOffset() {
        int i = this.header;
        int readUnsignedShort = i + 8 + (readUnsignedShort(i + 6) * 2);
        int readUnsignedShort2 = readUnsignedShort(readUnsignedShort);
        int i2 = readUnsignedShort + 2;
        while (true) {
            int i3 = readUnsignedShort2 - 1;
            if (readUnsignedShort2 <= 0) {
                break;
            }
            int readUnsignedShort3 = readUnsignedShort(i2 + 6);
            i2 += 8;
            while (true) {
                int i4 = readUnsignedShort3 - 1;
                if (readUnsignedShort3 > 0) {
                    i2 += readInt(i2 + 2) + 6;
                    readUnsignedShort3 = i4;
                }
            }
            readUnsignedShort2 = i3;
        }
        int readUnsignedShort4 = readUnsignedShort(i2);
        int i5 = i2 + 2;
        while (true) {
            int i6 = readUnsignedShort4 - 1;
            if (readUnsignedShort4 <= 0) {
                return i5 + 2;
            }
            int readUnsignedShort5 = readUnsignedShort(i5 + 6);
            i5 += 8;
            while (true) {
                int i7 = readUnsignedShort5 - 1;
                if (readUnsignedShort5 > 0) {
                    i5 += readInt(i5 + 2) + 6;
                    readUnsignedShort5 = i7;
                }
            }
            readUnsignedShort4 = i6;
        }
    }

    private int[] readBootstrapMethodsAttribute(int i) {
        char[] cArr = new char[i];
        int firstAttributeOffset = getFirstAttributeOffset();
        for (int readUnsignedShort = readUnsignedShort(firstAttributeOffset - 2); readUnsignedShort > 0; readUnsignedShort--) {
            String readUTF8 = readUTF8(firstAttributeOffset, cArr);
            int readInt = readInt(firstAttributeOffset + 2);
            int i2 = firstAttributeOffset + 6;
            if (BootstrapMethodsAttribute.tag.equals(readUTF8)) {
                int readUnsignedShort2 = readUnsignedShort(i2);
                int[] iArr = new int[readUnsignedShort2];
                int i3 = i2 + 2;
                for (int i4 = 0; i4 < readUnsignedShort2; i4++) {
                    iArr[i4] = i3;
                    i3 += (readUnsignedShort(i3 + 2) * 2) + 4;
                }
                return iArr;
            }
            firstAttributeOffset = i2 + readInt;
        }
        throw new IllegalArgumentException();
    }

    private Attribute readAttribute(Attribute[] attributeArr, String str, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        for (Attribute attribute : attributeArr) {
            if (attribute.type.equals(str)) {
                return attribute.read(this, i, i2, cArr, i3, labelArr);
            }
        }
        return new Attribute(str).read(this, i, i2, null, -1, null);
    }

    public int getItemCount() {
        return this.cpInfoOffsets.length;
    }

    public int getItem(int i) {
        return this.cpInfoOffsets[i];
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public int readByte(int i) {
        return this.classFileBuffer[i] & 255;
    }

    public int readUnsignedShort(int i) {
        byte[] bArr = this.classFileBuffer;
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    public short readShort(int i) {
        byte[] bArr = this.classFileBuffer;
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public int readInt(int i) {
        byte[] bArr = this.classFileBuffer;
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public long readLong(int i) {
        return (readInt(i) << 32) | (readInt(i + 4) & 4294967295L);
    }

    public String readUTF8(int i, char[] cArr) {
        int readUnsignedShort = readUnsignedShort(i);
        if (i == 0 || readUnsignedShort == 0) {
            return null;
        }
        return readUtf(readUnsignedShort, cArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String readUtf(int i, char[] cArr) {
        String[] strArr = this.constantUtf8Values;
        String str = strArr[i];
        if (str != null) {
            return str;
        }
        int i2 = this.cpInfoOffsets[i];
        String readUtf = readUtf(i2 + 2, readUnsignedShort(i2), cArr);
        strArr[i] = readUtf;
        return readUtf;
    }

    private String readUtf(int i, int i2, char[] cArr) {
        int i3;
        int i4 = i2 + i;
        byte[] bArr = this.classFileBuffer;
        int i5 = 0;
        while (i < i4) {
            int i6 = i + 1;
            byte b = bArr[i];
            if ((b & 128) == 0) {
                i3 = i5 + 1;
                cArr[i5] = (char) (b & Byte.MAX_VALUE);
            } else if ((b & 224) == 192) {
                cArr[i5] = (char) (((b & Ascii.US) << 6) + (bArr[i6] & 63));
                i5++;
                i = i6 + 1;
            } else {
                i3 = i5 + 1;
                int i7 = i6 + 1;
                int i8 = ((b & Ascii.SI) << 12) + ((bArr[i6] & 63) << 6);
                i6 = i7 + 1;
                cArr[i5] = (char) (i8 + (bArr[i7] & 63));
            }
            i = i6;
            i5 = i3;
        }
        return new String(cArr, 0, i5);
    }

    private String readStringish(int i, char[] cArr) {
        return readUTF8(this.cpInfoOffsets[readUnsignedShort(i)], cArr);
    }

    public String readClass(int i, char[] cArr) {
        return readStringish(i, cArr);
    }

    public String readModule(int i, char[] cArr) {
        return readStringish(i, cArr);
    }

    public String readPackage(int i, char[] cArr) {
        return readStringish(i, cArr);
    }

    private ConstantDynamic readConstantDynamic(int i, char[] cArr) {
        ConstantDynamic constantDynamic = this.constantDynamicValues[i];
        if (constantDynamic != null) {
            return constantDynamic;
        }
        int[] iArr = this.cpInfoOffsets;
        int i2 = iArr[i];
        int i3 = iArr[readUnsignedShort(i2 + 2)];
        String readUTF8 = readUTF8(i3, cArr);
        String readUTF82 = readUTF8(i3 + 2, cArr);
        int i4 = this.bootstrapMethodOffsets[readUnsignedShort(i2)];
        Handle handle = (Handle) readConst(readUnsignedShort(i4), cArr);
        int readUnsignedShort = readUnsignedShort(i4 + 2);
        Object[] objArr = new Object[readUnsignedShort];
        int i5 = i4 + 4;
        for (int i6 = 0; i6 < readUnsignedShort; i6++) {
            objArr[i6] = readConst(readUnsignedShort(i5), cArr);
            i5 += 2;
        }
        ConstantDynamic[] constantDynamicArr = this.constantDynamicValues;
        ConstantDynamic constantDynamic2 = new ConstantDynamic(readUTF8, readUTF82, handle, objArr);
        constantDynamicArr[i] = constantDynamic2;
        return constantDynamic2;
    }

    public Object readConst(int i, char[] cArr) {
        int i2 = this.cpInfoOffsets[i];
        byte b = this.classFileBuffer[i2 - 1];
        switch (b) {
            case 3:
                return Integer.valueOf(readInt(i2));
            case 4:
                return Float.valueOf(Float.intBitsToFloat(readInt(i2)));
            case 5:
                return Long.valueOf(readLong(i2));
            case 6:
                return Double.valueOf(Double.longBitsToDouble(readLong(i2)));
            case 7:
                return Type.getObjectType(readUTF8(i2, cArr));
            case 8:
                return readUTF8(i2, cArr);
            default:
                switch (b) {
                    case 15:
                        int readByte = readByte(i2);
                        int i3 = this.cpInfoOffsets[readUnsignedShort(i2 + 1)];
                        int i4 = this.cpInfoOffsets[readUnsignedShort(i3 + 2)];
                        return new Handle(readByte, readClass(i3, cArr), readUTF8(i4, cArr), readUTF8(i4 + 2, cArr), this.classFileBuffer[i3 - 1] == 11);
                    case 16:
                        return Type.getMethodType(readUTF8(i2, cArr));
                    case 17:
                        return readConstantDynamic(i, cArr);
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }
}
