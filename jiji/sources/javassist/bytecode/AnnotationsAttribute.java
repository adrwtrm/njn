package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.AnnotationsWriter;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/* loaded from: classes2.dex */
public class AnnotationsAttribute extends AttributeInfo {
    public static final String invisibleTag = "RuntimeInvisibleAnnotations";
    public static final String visibleTag = "RuntimeVisibleAnnotations";

    public AnnotationsAttribute(ConstPool constPool, String str, byte[] bArr) {
        super(constPool, str, bArr);
    }

    public AnnotationsAttribute(ConstPool constPool, String str) {
        this(constPool, str, new byte[]{0, 0});
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnnotationsAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
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
            return new AnnotationsAttribute(constPool, getName(), copier.close());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Annotation getAnnotation(String str) {
        Annotation[] annotations = getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].getTypeName().equals(str)) {
                return annotations[i];
            }
        }
        return null;
    }

    public void addAnnotation(Annotation annotation) {
        String typeName = annotation.getTypeName();
        Annotation[] annotations = getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].getTypeName().equals(typeName)) {
                annotations[i] = annotation;
                setAnnotations(annotations);
                return;
            }
        }
        Annotation[] annotationArr = new Annotation[annotations.length + 1];
        System.arraycopy(annotations, 0, annotationArr, 0, annotations.length);
        annotationArr[annotations.length] = annotation;
        setAnnotations(annotationArr);
    }

    public boolean removeAnnotation(String str) {
        Annotation[] annotations = getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].getTypeName().equals(str)) {
                Annotation[] annotationArr = new Annotation[annotations.length - 1];
                System.arraycopy(annotations, 0, annotationArr, 0, i);
                if (i < annotations.length - 1) {
                    System.arraycopy(annotations, i + 1, annotationArr, i, (annotations.length - i) - 1);
                }
                setAnnotations(annotationArr);
                return true;
            }
        }
        return false;
    }

    public Annotation[] getAnnotations() {
        try {
            return new Parser(this.info, this.constPool).parseAnnotations();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAnnotations(Annotation[] annotationArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AnnotationsWriter annotationsWriter = new AnnotationsWriter(byteArrayOutputStream, this.constPool);
        try {
            annotationsWriter.numAnnotations(annotationArr.length);
            for (Annotation annotation : annotationArr) {
                annotation.write(annotationsWriter);
            }
            annotationsWriter.close();
            set(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAnnotation(Annotation annotation) {
        setAnnotations(new Annotation[]{annotation});
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

    public String toString() {
        Annotation[] annotations = getAnnotations();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < annotations.length) {
            int i2 = i + 1;
            sb.append(annotations[i].toString());
            if (i2 != annotations.length) {
                sb.append(", ");
            }
            i = i2;
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Walker {
        byte[] info;

        void classMemberValue(int i, int i2) throws Exception {
        }

        void constValueMember(int i, int i2) throws Exception {
        }

        void enumMemberValue(int i, int i2, int i3) throws Exception {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Walker(byte[] bArr) {
            this.info = bArr;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final void parameters() throws Exception {
            parameters(this.info[0] & 255, 1);
        }

        void parameters(int i, int i2) throws Exception {
            for (int i3 = 0; i3 < i; i3++) {
                i2 = annotationArray(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final void annotationArray() throws Exception {
            annotationArray(0);
        }

        final int annotationArray(int i) throws Exception {
            return annotationArray(i + 2, ByteArray.readU16bit(this.info, i));
        }

        int annotationArray(int i, int i2) throws Exception {
            for (int i3 = 0; i3 < i2; i3++) {
                i = annotation(i);
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final int annotation(int i) throws Exception {
            return annotation(i + 4, ByteArray.readU16bit(this.info, i), ByteArray.readU16bit(this.info, i + 2));
        }

        int annotation(int i, int i2, int i3) throws Exception {
            for (int i4 = 0; i4 < i3; i4++) {
                i = memberValuePair(i);
            }
            return i;
        }

        final int memberValuePair(int i) throws Exception {
            return memberValuePair(i + 2, ByteArray.readU16bit(this.info, i));
        }

        int memberValuePair(int i, int i2) throws Exception {
            return memberValue(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final int memberValue(int i) throws Exception {
            byte[] bArr = this.info;
            int i2 = bArr[i] & 255;
            if (i2 == 101) {
                enumMemberValue(i, ByteArray.readU16bit(bArr, i + 1), ByteArray.readU16bit(this.info, i + 3));
                return i + 5;
            } else if (i2 == 99) {
                classMemberValue(i, ByteArray.readU16bit(bArr, i + 1));
                return i + 3;
            } else if (i2 == 64) {
                return annotationMemberValue(i + 1);
            } else {
                if (i2 == 91) {
                    return arrayMemberValue(i + 3, ByteArray.readU16bit(bArr, i + 1));
                }
                constValueMember(i2, ByteArray.readU16bit(bArr, i + 1));
                return i + 3;
            }
        }

        int annotationMemberValue(int i) throws Exception {
            return annotation(i);
        }

        int arrayMemberValue(int i, int i2) throws Exception {
            for (int i3 = 0; i3 < i2; i3++) {
                i = memberValue(i);
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Renamer extends Walker {
        Map<String, String> classnames;
        ConstPool cpool;

        /* JADX INFO: Access modifiers changed from: package-private */
        public Renamer(byte[] bArr, ConstPool constPool, Map<String, String> map) {
            super(bArr);
            this.cpool = constPool;
            this.classnames = map;
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotation(int i, int i2, int i3) throws Exception {
            renameType(i - 4, i2);
            return super.annotation(i, i2, i3);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void enumMemberValue(int i, int i2, int i3) throws Exception {
            renameType(i + 1, i2);
            super.enumMemberValue(i, i2, i3);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void classMemberValue(int i, int i2) throws Exception {
            renameType(i + 1, i2);
            super.classMemberValue(i, i2);
        }

        private void renameType(int i, int i2) {
            String utf8Info = this.cpool.getUtf8Info(i2);
            String rename = Descriptor.rename(utf8Info, this.classnames);
            if (utf8Info.equals(rename)) {
                return;
            }
            ByteArray.write16bit(this.cpool.addUtf8Info(rename), this.info, i);
        }
    }

    /* loaded from: classes2.dex */
    static class Copier extends Walker {
        Map<String, String> classnames;
        ConstPool destPool;
        ByteArrayOutputStream output;
        ConstPool srcPool;
        AnnotationsWriter writer;

        /* JADX INFO: Access modifiers changed from: package-private */
        public Copier(byte[] bArr, ConstPool constPool, ConstPool constPool2, Map<String, String> map) {
            this(bArr, constPool, constPool2, map, true);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Copier(byte[] bArr, ConstPool constPool, ConstPool constPool2, Map<String, String> map, boolean z) {
            super(bArr);
            this.output = new ByteArrayOutputStream();
            if (z) {
                this.writer = new AnnotationsWriter(this.output, constPool2);
            }
            this.srcPool = constPool;
            this.destPool = constPool2;
            this.classnames = map;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public byte[] close() throws IOException {
            this.writer.close();
            return this.output.toByteArray();
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void parameters(int i, int i2) throws Exception {
            this.writer.numParameters(i);
            super.parameters(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotationArray(int i, int i2) throws Exception {
            this.writer.numAnnotations(i2);
            return super.annotationArray(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotation(int i, int i2, int i3) throws Exception {
            this.writer.annotation(copyType(i2), i3);
            return super.annotation(i, i2, i3);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int memberValuePair(int i, int i2) throws Exception {
            this.writer.memberValuePair(copy(i2));
            return super.memberValuePair(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void constValueMember(int i, int i2) throws Exception {
            this.writer.constValueIndex(i, copy(i2));
            super.constValueMember(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void enumMemberValue(int i, int i2, int i3) throws Exception {
            this.writer.enumConstValue(copyType(i2), copy(i3));
            super.enumMemberValue(i, i2, i3);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void classMemberValue(int i, int i2) throws Exception {
            this.writer.classInfoIndex(copyType(i2));
            super.classMemberValue(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotationMemberValue(int i) throws Exception {
            this.writer.annotationValue();
            return super.annotationMemberValue(i);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int arrayMemberValue(int i, int i2) throws Exception {
            this.writer.arrayValue(i2);
            return super.arrayMemberValue(i, i2);
        }

        int copy(int i) {
            return this.srcPool.copy(i, this.destPool, this.classnames);
        }

        int copyType(int i) {
            return this.destPool.addUtf8Info(Descriptor.rename(this.srcPool.getUtf8Info(i), this.classnames));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Parser extends Walker {
        Annotation[] allAnno;
        Annotation[][] allParams;
        Annotation currentAnno;
        MemberValue currentMember;
        ConstPool pool;

        /* JADX INFO: Access modifiers changed from: package-private */
        public Parser(byte[] bArr, ConstPool constPool) {
            super(bArr);
            this.pool = constPool;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Annotation[][] parseParameters() throws Exception {
            parameters();
            return this.allParams;
        }

        Annotation[] parseAnnotations() throws Exception {
            annotationArray();
            return this.allAnno;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public MemberValue parseMemberValue() throws Exception {
            memberValue(0);
            return this.currentMember;
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void parameters(int i, int i2) throws Exception {
            Annotation[][] annotationArr = new Annotation[i];
            for (int i3 = 0; i3 < i; i3++) {
                i2 = annotationArray(i2);
                annotationArr[i3] = this.allAnno;
            }
            this.allParams = annotationArr;
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotationArray(int i, int i2) throws Exception {
            Annotation[] annotationArr = new Annotation[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                i = annotation(i);
                annotationArr[i3] = this.currentAnno;
            }
            this.allAnno = annotationArr;
            return i;
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotation(int i, int i2, int i3) throws Exception {
            this.currentAnno = new Annotation(i2, this.pool);
            return super.annotation(i, i2, i3);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int memberValuePair(int i, int i2) throws Exception {
            int memberValuePair = super.memberValuePair(i, i2);
            this.currentAnno.addMemberValue(i2, this.currentMember);
            return memberValuePair;
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void constValueMember(int i, int i2) throws Exception {
            MemberValue floatMemberValue;
            ConstPool constPool = this.pool;
            if (i == 70) {
                floatMemberValue = new FloatMemberValue(i2, constPool);
            } else if (i == 83) {
                floatMemberValue = new ShortMemberValue(i2, constPool);
            } else if (i == 90) {
                floatMemberValue = new BooleanMemberValue(i2, constPool);
            } else if (i == 115) {
                floatMemberValue = new StringMemberValue(i2, constPool);
            } else if (i == 73) {
                floatMemberValue = new IntegerMemberValue(i2, constPool);
            } else if (i != 74) {
                switch (i) {
                    case 66:
                        floatMemberValue = new ByteMemberValue(i2, constPool);
                        break;
                    case 67:
                        floatMemberValue = new CharMemberValue(i2, constPool);
                        break;
                    case 68:
                        floatMemberValue = new DoubleMemberValue(i2, constPool);
                        break;
                    default:
                        throw new RuntimeException("unknown tag:" + i);
                }
            } else {
                floatMemberValue = new LongMemberValue(i2, constPool);
            }
            this.currentMember = floatMemberValue;
            super.constValueMember(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void enumMemberValue(int i, int i2, int i3) throws Exception {
            this.currentMember = new EnumMemberValue(i2, i3, this.pool);
            super.enumMemberValue(i, i2, i3);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        void classMemberValue(int i, int i2) throws Exception {
            this.currentMember = new ClassMemberValue(i2, this.pool);
            super.classMemberValue(i, i2);
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int annotationMemberValue(int i) throws Exception {
            Annotation annotation = this.currentAnno;
            int annotationMemberValue = super.annotationMemberValue(i);
            this.currentMember = new AnnotationMemberValue(this.currentAnno, this.pool);
            this.currentAnno = annotation;
            return annotationMemberValue;
        }

        @Override // javassist.bytecode.AnnotationsAttribute.Walker
        int arrayMemberValue(int i, int i2) throws Exception {
            ArrayMemberValue arrayMemberValue = new ArrayMemberValue(this.pool);
            MemberValue[] memberValueArr = new MemberValue[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                i = memberValue(i);
                memberValueArr[i3] = this.currentMember;
            }
            arrayMemberValue.setValue(memberValueArr);
            this.currentMember = arrayMemberValue;
            return i;
        }
    }
}
