package javassist.util;

import com.epson.iprojection.common.CommonDefine;
import com.sun.tools.attach.VirtualMachine;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/* loaded from: classes2.dex */
public class HotSwapAgent {
    private static Instrumentation instrumentation;

    public Instrumentation instrumentation() {
        return instrumentation;
    }

    public static void premain(String str, Instrumentation instrumentation2) throws Throwable {
        agentmain(str, instrumentation2);
    }

    public static void agentmain(String str, Instrumentation instrumentation2) throws Throwable {
        if (!instrumentation2.isRedefineClassesSupported()) {
            throw new RuntimeException("this JVM does not support redefinition of classes");
        }
        instrumentation = instrumentation2;
    }

    public static void redefine(Class<?> cls, CtClass ctClass) throws NotFoundException, IOException, CannotCompileException {
        redefine(new Class[]{cls}, new CtClass[]{ctClass});
    }

    public static void redefine(Class<?>[] clsArr, CtClass[] ctClassArr) throws NotFoundException, IOException, CannotCompileException {
        startAgent();
        ClassDefinition[] classDefinitionArr = new ClassDefinition[clsArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            classDefinitionArr[i] = new ClassDefinition(clsArr[i], ctClassArr[i].toBytecode());
        }
        try {
            instrumentation.redefineClasses(classDefinitionArr);
        } catch (UnmodifiableClassException e) {
            throw new CannotCompileException(e.getMessage(), (Throwable) e);
        } catch (ClassNotFoundException e2) {
            throw new NotFoundException(e2.getMessage(), e2);
        }
    }

    private static void startAgent() throws NotFoundException {
        if (instrumentation != null) {
            return;
        }
        try {
            File createJarFile = createJarFile();
            String name = ManagementFactory.getRuntimeMXBean().getName();
            VirtualMachine attach = VirtualMachine.attach(name.substring(0, name.indexOf(64)));
            attach.loadAgent(createJarFile.getAbsolutePath(), (String) null);
            attach.detach();
            for (int i = 0; i < 10; i++) {
                if (instrumentation != null) {
                    return;
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
            throw new NotFoundException("hotswap agent (timeout)");
        } catch (Exception e) {
            throw new NotFoundException("hotswap agent", e);
        }
    }

    public static File createAgentJarFile(String str) throws IOException, CannotCompileException, NotFoundException {
        return createJarFile(new File(str));
    }

    private static File createJarFile() throws IOException, CannotCompileException, NotFoundException {
        File createTempFile = File.createTempFile("agent", ".jar");
        createTempFile.deleteOnExit();
        return createJarFile(createTempFile);
    }

    private static File createJarFile(File file) throws IOException, CannotCompileException, NotFoundException {
        JarOutputStream jarOutputStream;
        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(new Attributes.Name("Premain-Class"), HotSwapAgent.class.getName());
        mainAttributes.put(new Attributes.Name("Agent-Class"), HotSwapAgent.class.getName());
        mainAttributes.put(new Attributes.Name("Can-Retransform-Classes"), CommonDefine.TRUE);
        mainAttributes.put(new Attributes.Name("Can-Redefine-Classes"), CommonDefine.TRUE);
        JarOutputStream jarOutputStream2 = null;
        try {
            jarOutputStream = new JarOutputStream(new FileOutputStream(file), manifest);
        } catch (Throwable th) {
            th = th;
        }
        try {
            String name = HotSwapAgent.class.getName();
            jarOutputStream.putNextEntry(new JarEntry(name.replace('.', '/') + ".class"));
            jarOutputStream.write(ClassPool.getDefault().get(name).toBytecode());
            jarOutputStream.closeEntry();
            jarOutputStream.close();
            return file;
        } catch (Throwable th2) {
            th = th2;
            jarOutputStream2 = jarOutputStream;
            if (jarOutputStream2 != null) {
                jarOutputStream2.close();
            }
            throw th;
        }
    }
}
