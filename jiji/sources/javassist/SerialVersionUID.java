package javassist;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public class SerialVersionUID {
    public static void setSerialVersionUID(CtClass ctClass) throws CannotCompileException, NotFoundException {
        try {
            ctClass.getDeclaredField("serialVersionUID");
        } catch (NotFoundException unused) {
            if (isSerializable(ctClass)) {
                CtField ctField = new CtField(CtClass.longType, "serialVersionUID", ctClass);
                ctField.setModifiers(26);
                ctClass.addField(ctField, calculateDefault(ctClass) + "L");
            }
        }
    }

    private static boolean isSerializable(CtClass ctClass) throws NotFoundException {
        return ctClass.subtypeOf(ctClass.getClassPool().get("java.io.Serializable"));
    }

    public static long calculateDefault(CtClass ctClass) throws CannotCompileException {
        byte[] digest;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            ClassFile classFile = ctClass.getClassFile();
            dataOutputStream.writeUTF(javaName(ctClass));
            CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
            int modifiers = ctClass.getModifiers();
            if ((modifiers & 512) != 0) {
                modifiers = declaredMethods.length > 0 ? modifiers | 1024 : modifiers & (-1025);
            }
            dataOutputStream.writeInt(modifiers);
            String[] interfaces = classFile.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                interfaces[i] = javaName(interfaces[i]);
            }
            Arrays.sort(interfaces);
            for (String str : interfaces) {
                dataOutputStream.writeUTF(str);
            }
            CtField[] declaredFields = ctClass.getDeclaredFields();
            Arrays.sort(declaredFields, new Comparator<CtField>() { // from class: javassist.SerialVersionUID.1
                @Override // java.util.Comparator
                public int compare(CtField ctField, CtField ctField2) {
                    return ctField.getName().compareTo(ctField2.getName());
                }
            });
            for (CtField ctField : declaredFields) {
                int modifiers2 = ctField.getModifiers();
                if ((modifiers2 & 2) == 0 || (modifiers2 & 136) == 0) {
                    dataOutputStream.writeUTF(ctField.getName());
                    dataOutputStream.writeInt(modifiers2);
                    dataOutputStream.writeUTF(ctField.getFieldInfo2().getDescriptor());
                }
            }
            if (classFile.getStaticInitializer() != null) {
                dataOutputStream.writeUTF(MethodInfo.nameClinit);
                dataOutputStream.writeInt(8);
                dataOutputStream.writeUTF("()V");
            }
            CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
            Arrays.sort(declaredConstructors, new Comparator<CtConstructor>() { // from class: javassist.SerialVersionUID.2
                @Override // java.util.Comparator
                public int compare(CtConstructor ctConstructor, CtConstructor ctConstructor2) {
                    return ctConstructor.getMethodInfo2().getDescriptor().compareTo(ctConstructor2.getMethodInfo2().getDescriptor());
                }
            });
            for (CtConstructor ctConstructor : declaredConstructors) {
                int modifiers3 = ctConstructor.getModifiers();
                if ((modifiers3 & 2) == 0) {
                    dataOutputStream.writeUTF("<init>");
                    dataOutputStream.writeInt(modifiers3);
                    dataOutputStream.writeUTF(ctConstructor.getMethodInfo2().getDescriptor().replace('/', '.'));
                }
            }
            Arrays.sort(declaredMethods, new Comparator<CtMethod>() { // from class: javassist.SerialVersionUID.3
                @Override // java.util.Comparator
                public int compare(CtMethod ctMethod, CtMethod ctMethod2) {
                    int compareTo = ctMethod.getName().compareTo(ctMethod2.getName());
                    return compareTo == 0 ? ctMethod.getMethodInfo2().getDescriptor().compareTo(ctMethod2.getMethodInfo2().getDescriptor()) : compareTo;
                }
            });
            for (CtMethod ctMethod : declaredMethods) {
                int modifiers4 = ctMethod.getModifiers() & 3391;
                if ((modifiers4 & 2) == 0) {
                    dataOutputStream.writeUTF(ctMethod.getName());
                    dataOutputStream.writeInt(modifiers4);
                    dataOutputStream.writeUTF(ctMethod.getMethodInfo2().getDescriptor().replace('/', '.'));
                }
            }
            dataOutputStream.flush();
            long j = 0;
            for (int min = Math.min(MessageDigest.getInstance("SHA").digest(byteArrayOutputStream.toByteArray()).length, 8) - 1; min >= 0; min--) {
                j = (j << 8) | (digest[min] & 255);
            }
            return j;
        } catch (IOException e) {
            throw new CannotCompileException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new CannotCompileException(e2);
        }
    }

    private static String javaName(CtClass ctClass) {
        return Descriptor.toJavaName(Descriptor.toJvmName(ctClass));
    }

    private static String javaName(String str) {
        return Descriptor.toJavaName(Descriptor.toJvmName(str));
    }
}
