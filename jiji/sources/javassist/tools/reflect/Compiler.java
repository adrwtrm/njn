package javassist.tools.reflect;

import java.io.PrintStream;
import javassist.ClassPool;
import javassist.CtClass;

/* loaded from: classes2.dex */
public class Compiler {
    public static void main(String[] strArr) throws Exception {
        if (strArr.length == 0) {
            help(System.err);
            return;
        }
        CompiledClass[] compiledClassArr = new CompiledClass[strArr.length];
        int parse = parse(strArr, compiledClassArr);
        if (parse < 1) {
            System.err.println("bad parameter.");
        } else {
            processClasses(compiledClassArr, parse);
        }
    }

    private static void processClasses(CompiledClass[] compiledClassArr, int i) throws Exception {
        Reflection reflection = new Reflection();
        ClassPool classPool = ClassPool.getDefault();
        reflection.start(classPool);
        for (int i2 = 0; i2 < i; i2++) {
            CtClass ctClass = classPool.get(compiledClassArr[i2].classname);
            if (compiledClassArr[i2].metaobject != null || compiledClassArr[i2].classobject != null) {
                String str = compiledClassArr[i2].metaobject == null ? "javassist.tools.reflect.Metaobject" : compiledClassArr[i2].metaobject;
                String str2 = compiledClassArr[i2].classobject == null ? "javassist.tools.reflect.ClassMetaobject" : compiledClassArr[i2].classobject;
                if (!reflection.makeReflective(ctClass, classPool.get(str), classPool.get(str2))) {
                    System.err.println("Warning: " + ctClass.getName() + " is reflective.  It was not changed.");
                }
                System.err.println(ctClass.getName() + ": " + str + ", " + str2);
            } else {
                System.err.println(ctClass.getName() + ": not reflective");
            }
        }
        for (int i3 = 0; i3 < i; i3++) {
            reflection.onLoad(classPool, compiledClassArr[i3].classname);
            classPool.get(compiledClassArr[i3].classname).writeFile();
        }
    }

    private static int parse(String[] strArr, CompiledClass[] compiledClassArr) {
        int i = -1;
        int i2 = 0;
        while (i2 < strArr.length) {
            String str = strArr[i2];
            if (str.equals("-m")) {
                if (i < 0 || (i2 = i2 + 1) > strArr.length) {
                    return -1;
                }
                compiledClassArr[i].metaobject = strArr[i2];
            } else if (str.equals("-c")) {
                if (i < 0 || (i2 = i2 + 1) > strArr.length) {
                    return -1;
                }
                compiledClassArr[i].classobject = strArr[i2];
            } else if (str.charAt(0) == '-') {
                return -1;
            } else {
                CompiledClass compiledClass = new CompiledClass();
                compiledClass.classname = str;
                compiledClass.metaobject = null;
                compiledClass.classobject = null;
                i++;
                compiledClassArr[i] = compiledClass;
            }
            i2++;
        }
        return i + 1;
    }

    private static void help(PrintStream printStream) {
        printStream.println("Usage: java javassist.tools.reflect.Compiler");
        printStream.println("            (<class> [-m <metaobject>] [-c <class metaobject>])+");
    }
}
