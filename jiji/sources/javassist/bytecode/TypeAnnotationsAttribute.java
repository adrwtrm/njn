package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.TypeAnnotationsWriter;

/* loaded from: classes2.dex */
public class TypeAnnotationsAttribute extends AttributeInfo {
    public static final String invisibleTag = "RuntimeInvisibleTypeAnnotations";
    public static final String visibleTag = "RuntimeVisibleTypeAnnotations";

    public TypeAnnotationsAttribute(ConstPool constPool, String str, byte[] bArr) {
        super(constPool, str, bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TypeAnnotationsAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public int numAnnotations() {
        return ByteArray.readU16bit(this.info, 0);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        Copier copier = new Copier(this.info, this.constPool, constPool, map);
        try {
            copier.annotationArray();
            return new TypeAnnotationsAttribute(constPool, getName(), copier.close());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        renameClass(hashMap);
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(Map<String, String> map) {
        try {
            new Renamer(this.info, getConstPool(), map).annotationArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // javassist.bytecode.AttributeInfo
    void getRefClasses(Map<String, String> map) {
        renameClass(map);
    }

    /* loaded from: classes2.dex */
    static class TAWalker extends AnnotationsAttribute.Walker {
        SubWalker subWalker;

        TAWalker(byte[] bArr) {
            super(bArr);
            this.subWalker = new SubWalker(bArr);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotationArray(int i, int i2) throws Exception {
            for (int i3 = 0; i3 < i2; i3++) {
                i = annotation(this.subWalker.typePath(this.subWalker.targetInfo(i + 1, this.info[i] & 255)));
            }
            return i;
        }
    }

    /* loaded from: classes2.dex */
    static class SubWalker {
        byte[] info;

        void catchTarget(int i, int i2) throws Exception {
        }

        void emptyTarget(int i, int i2) throws Exception {
        }

        void formalParameterTarget(int i, int i2) throws Exception {
        }

        void localvarTarget(int i, int i2, int i3, int i4, int i5) throws Exception {
        }

        void offsetTarget(int i, int i2, int i3) throws Exception {
        }

        void supertypeTarget(int i, int i2) throws Exception {
        }

        void throwsTarget(int i, int i2) throws Exception {
        }

        void typeArgumentTarget(int i, int i2, int i3, int i4) throws Exception {
        }

        void typeParameterBoundTarget(int i, int i2, int i3, int i4) throws Exception {
        }

        void typeParameterTarget(int i, int i2, int i3) throws Exception {
        }

        void typePath(int i, int i2, int i3) throws Exception {
        }

        SubWalker(byte[] bArr) {
            this.info = bArr;
        }

        final int targetInfo(int i, int i2) throws Exception {
            if (i2 == 0 || i2 == 1) {
                typeParameterTarget(i, i2, this.info[i] & 255);
                return i + 1;
            }
            switch (i2) {
                case 16:
                    supertypeTarget(i, ByteArray.readU16bit(this.info, i));
                    return i + 2;
                case 17:
                case 18:
                    byte[] bArr = this.info;
                    typeParameterBoundTarget(i, i2, bArr[i] & 255, bArr[i + 1] & 255);
                    return i + 2;
                case 19:
                case 20:
                case 21:
                    emptyTarget(i, i2);
                    return i;
                case 22:
                    formalParameterTarget(i, this.info[i] & 255);
                    return i + 1;
                case 23:
                    throwsTarget(i, ByteArray.readU16bit(this.info, i));
                    return i + 2;
                default:
                    switch (i2) {
                        case 64:
                        case 65:
                            return localvarTarget(i + 2, i2, ByteArray.readU16bit(this.info, i));
                        case 66:
                            catchTarget(i, ByteArray.readU16bit(this.info, i));
                            return i + 2;
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                            offsetTarget(i, i2, ByteArray.readU16bit(this.info, i));
                            return i + 2;
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            typeArgumentTarget(i, i2, ByteArray.readU16bit(this.info, i), this.info[i + 2] & 255);
                            return i + 3;
                        default:
                            throw new RuntimeException("invalid target type: " + i2);
                    }
            }
        }

        int localvarTarget(int i, int i2, int i3) throws Exception {
            for (int i4 = 0; i4 < i3; i4++) {
                localvarTarget(i, i2, ByteArray.readU16bit(this.info, i), ByteArray.readU16bit(this.info, i + 2), ByteArray.readU16bit(this.info, i + 4));
                i += 6;
            }
            return i;
        }

        final int typePath(int i) throws Exception {
            return typePath(i + 1, this.info[i] & 255);
        }

        int typePath(int i, int i2) throws Exception {
            for (int i3 = 0; i3 < i2; i3++) {
                byte[] bArr = this.info;
                typePath(i, bArr[i] & 255, bArr[i + 1] & 255);
                i += 2;
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Renamer extends AnnotationsAttribute.Renamer {
        SubWalker sub;

        Renamer(byte[] bArr, ConstPool constPool, Map<String, String> map) {
            super(bArr, constPool, map);
            this.sub = new SubWalker(bArr);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotationArray(int i, int i2) throws Exception {
            for (int i3 = 0; i3 < i2; i3++) {
                i = annotation(this.sub.typePath(this.sub.targetInfo(i + 1, this.info[i] & 255)));
            }
            return i;
        }
    }

    /* loaded from: classes2.dex */
    static class Copier extends AnnotationsAttribute.Copier {
        SubCopier sub;

        Copier(byte[] bArr, ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
            super(bArr, constPool, constPool2, map, false);
            TypeAnnotationsWriter typeAnnotationsWriter = new TypeAnnotationsWriter(this.output, constPool2);
            this.writer = typeAnnotationsWriter;
            this.sub = new SubCopier(bArr, constPool, constPool2, map, typeAnnotationsWriter);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Copier, javassist.bytecode.AnnotationsAttribute.Walker
        int annotationArray(int i, int i2) throws Exception {
            this.writer.numAnnotations(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                i = annotation(this.sub.typePath(this.sub.targetInfo(i + 1, this.info[i] & 255)));
            }
            return i;
        }
    }

    /* loaded from: classes2.dex */
    static class SubCopier extends SubWalker {
        Map<String, String> classnames;
        ConstPool destPool;
        ConstPool srcPool;
        TypeAnnotationsWriter writer;

        SubCopier(byte[] bArr, ConstPool constPool, ConstPool constPool2, Map<String, String> map, TypeAnnotationsWriter typeAnnotationsWriter) {
            super(bArr);
            this.srcPool = constPool;
            this.destPool = constPool2;
            this.classnames = map;
            this.writer = typeAnnotationsWriter;
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void typeParameterTarget(int i, int i2, int i3) throws Exception {
            this.writer.typeParameterTarget(i2, i3);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void supertypeTarget(int i, int i2) throws Exception {
            this.writer.supertypeTarget(i2);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void typeParameterBoundTarget(int i, int i2, int i3, int i4) throws Exception {
            this.writer.typeParameterBoundTarget(i2, i3, i4);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void emptyTarget(int i, int i2) throws Exception {
            this.writer.emptyTarget(i2);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void formalParameterTarget(int i, int i2) throws Exception {
            this.writer.formalParameterTarget(i2);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void throwsTarget(int i, int i2) throws Exception {
            this.writer.throwsTarget(i2);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        int localvarTarget(int i, int i2, int i3) throws Exception {
            this.writer.localVarTarget(i2, i3);
            return super.localvarTarget(i, i2, i3);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void localvarTarget(int i, int i2, int i3, int i4, int i5) throws Exception {
            this.writer.localVarTargetTable(i3, i4, i5);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void catchTarget(int i, int i2) throws Exception {
            this.writer.catchTarget(i2);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void offsetTarget(int i, int i2, int i3) throws Exception {
            this.writer.offsetTarget(i2, i3);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void typeArgumentTarget(int i, int i2, int i3, int i4) throws Exception {
            this.writer.typeArgumentTarget(i2, i3, i4);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        int typePath(int i, int i2) throws Exception {
            this.writer.typePath(i2);
            return super.typePath(i, i2);
        }

        @Override // javassist.bytecode.TypeAnnotationsAttribute.SubWalker
        void typePath(int i, int i2, int i3) throws Exception {
            this.writer.typePathPath(i2, i3);
        }
    }
}
