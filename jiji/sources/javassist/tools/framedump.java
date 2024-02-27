package javassist.tools;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.analysis.FramePrinter;

/* loaded from: classes2.dex */
public class framedump {
    private framedump() {
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length != 1) {
            System.err.println("Usage: java javassist.tools.framedump <fully-qualified class name>");
            return;
        }
        CtClass ctClass = ClassPool.getDefault().get(strArr[0]);
        System.out.println("Frame Dump of " + ctClass.getName() + ":");
        FramePrinter.print(ctClass, System.out);
    }
}
