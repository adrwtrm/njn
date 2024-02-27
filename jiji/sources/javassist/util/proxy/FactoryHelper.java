package javassist.util.proxy;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.ProtectionDomain;
import javassist.CannotCompileException;
import javassist.bytecode.ClassFile;

/* loaded from: classes2.dex */
public class FactoryHelper {
    public static final Class<?>[] primitiveTypes = {Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
    public static final String[] wrapperTypes = {"java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Void"};
    public static final String[] wrapperDesc = {"(Z)V", "(B)V", "(C)V", "(S)V", "(I)V", "(J)V", "(F)V", "(D)V"};
    public static final String[] unwarpMethods = {"booleanValue", "byteValue", "charValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue"};
    public static final String[] unwrapDesc = {"()Z", "()B", "()C", "()S", "()I", "()J", "()F", "()D"};
    public static final int[] dataSize = {1, 1, 1, 1, 1, 2, 1, 2};

    public static final int typeIndex(Class<?> cls) {
        int i = 0;
        while (true) {
            Class<?>[] clsArr = primitiveTypes;
            if (i < clsArr.length) {
                if (clsArr[i] == cls) {
                    return i;
                }
                i++;
            } else {
                throw new RuntimeException("bad type:" + cls.getName());
            }
        }
    }

    public static Class<?> toClass(ClassFile classFile, ClassLoader classLoader) throws CannotCompileException {
        return toClass(classFile, null, classLoader, null);
    }

    public static Class<?> toClass(ClassFile classFile, ClassLoader classLoader, ProtectionDomain protectionDomain) throws CannotCompileException {
        return toClass(classFile, null, classLoader, protectionDomain);
    }

    public static Class<?> toClass(ClassFile classFile, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws CannotCompileException {
        try {
            byte[] bytecode = toBytecode(classFile);
            if (ProxyFactory.onlyPublicMethods) {
                return DefineClassHelper.toPublicClass(classFile.getName(), bytecode);
            }
            return DefineClassHelper.toClass(classFile.getName(), cls, classLoader, protectionDomain, bytecode);
        } catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    public static Class<?> toClass(ClassFile classFile, MethodHandles.Lookup lookup) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(lookup, toBytecode(classFile));
        } catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    private static byte[] toBytecode(ClassFile classFile) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            classFile.write(dataOutputStream);
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            dataOutputStream.close();
            throw th;
        }
    }

    public static void writeFile(ClassFile classFile, String str) throws CannotCompileException {
        try {
            writeFile0(classFile, str);
        } catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    private static void writeFile0(ClassFile classFile, String str) throws CannotCompileException, IOException {
        String str2 = str + File.separatorChar + classFile.getName().replace('.', File.separatorChar) + ".class";
        int lastIndexOf = str2.lastIndexOf(File.separatorChar);
        if (lastIndexOf > 0) {
            String substring = str2.substring(0, lastIndexOf);
            if (!substring.equals(".")) {
                new File(substring).mkdirs();
            }
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(str2)));
        try {
            try {
                classFile.write(dataOutputStream);
            } catch (IOException e) {
                throw e;
            }
        } finally {
            dataOutputStream.close();
        }
    }
}
