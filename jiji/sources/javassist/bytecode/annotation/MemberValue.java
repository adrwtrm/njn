package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

/* loaded from: classes2.dex */
public abstract class MemberValue {
    ConstPool cp;
    char tag;

    public abstract void accept(MemberValueVisitor memberValueVisitor);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Class<?> getType(ClassLoader classLoader) throws ClassNotFoundException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) throws ClassNotFoundException;

    public abstract void write(AnnotationsWriter annotationsWriter) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MemberValue(char c, ConstPool constPool) {
        this.cp = constPool;
        this.tag = c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Class<?> loadClass(ClassLoader classLoader, String str) throws ClassNotFoundException, NoSuchClassError {
        try {
            return Class.forName(convertFromArray(str), true, classLoader);
        } catch (LinkageError e) {
            throw new NoSuchClassError(str, e);
        }
    }

    private static String convertFromArray(String str) {
        int indexOf = str.indexOf("[]");
        if (indexOf != -1) {
            StringBuffer stringBuffer = new StringBuffer(Descriptor.of(str.substring(0, indexOf)));
            while (indexOf != -1) {
                stringBuffer.insert(0, "[");
                indexOf = str.indexOf("[]", indexOf + 1);
            }
            return stringBuffer.toString().replace('/', '.');
        }
        return str;
    }
}
