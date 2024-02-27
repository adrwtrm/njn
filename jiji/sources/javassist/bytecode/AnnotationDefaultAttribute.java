package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.AnnotationsWriter;
import javassist.bytecode.annotation.MemberValue;

/* loaded from: classes2.dex */
public class AnnotationDefaultAttribute extends AttributeInfo {
    public static final String tag = "AnnotationDefault";

    public AnnotationDefaultAttribute(ConstPool constPool, byte[] bArr) {
        super(constPool, tag, bArr);
    }

    public AnnotationDefaultAttribute(ConstPool constPool) {
        this(constPool, new byte[]{0, 0});
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnnotationDefaultAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, constPool, map);
        try {
            copier.memberValue(0);
            return new AnnotationDefaultAttribute(constPool, copier.close());
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public MemberValue getDefaultValue() {
        try {
            return new AnnotationsAttribute.Parser(this.info, this.constPool).parseMemberValue();
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void setDefaultValue(MemberValue memberValue) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AnnotationsWriter annotationsWriter = new AnnotationsWriter(byteArrayOutputStream, this.constPool);
        try {
            memberValue.write(annotationsWriter);
            annotationsWriter.close();
            set(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return getDefaultValue().toString();
    }
}
