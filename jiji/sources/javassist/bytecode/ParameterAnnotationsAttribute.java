package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationsWriter;

/* loaded from: classes2.dex */
public class ParameterAnnotationsAttribute extends AttributeInfo {
    public static final String invisibleTag = "RuntimeInvisibleParameterAnnotations";
    public static final String visibleTag = "RuntimeVisibleParameterAnnotations";

    public ParameterAnnotationsAttribute(ConstPool constPool, String str, byte[] bArr) {
        super(constPool, str, bArr);
    }

    public ParameterAnnotationsAttribute(ConstPool constPool, String str) {
        this(constPool, str, new byte[]{0});
    }

    public ParameterAnnotationsAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public int numParameters() {
        return this.info[0] & 255;
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, constPool, map);
        try {
            copier.parameters();
            return new ParameterAnnotationsAttribute(constPool, getName(), copier.close());
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public Annotation[][] getAnnotations() {
        try {
            return new AnnotationsAttribute.Parser(this.info, this.constPool).parseParameters();
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void setAnnotations(Annotation[][] annotationArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AnnotationsWriter annotationsWriter = new AnnotationsWriter(byteArrayOutputStream, this.constPool);
        try {
            annotationsWriter.numParameters(annotationArr.length);
            for (Annotation[] annotationArr2 : annotationArr) {
                annotationsWriter.numAnnotations(annotationArr2.length);
                for (Annotation annotation : annotationArr2) {
                    annotation.write(annotationsWriter);
                }
            }
            annotationsWriter.close();
            set(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
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
            new AnnotationsAttribute.Renamer(this.info, getConstPool(), map).parameters();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // javassist.bytecode.AttributeInfo
    void getRefClasses(Map<String, String> map) {
        renameClass(map);
    }

    public String toString() {
        Annotation[][] annotations = getAnnotations();
        StringBuilder sb = new StringBuilder();
        for (Annotation[] annotationArr : annotations) {
            for (Annotation annotation : annotationArr) {
                sb.append(annotation.toString()).append(" ");
            }
            sb.append(", ");
        }
        return sb.toString().replaceAll(" (?=,)|, $", "");
    }
}
