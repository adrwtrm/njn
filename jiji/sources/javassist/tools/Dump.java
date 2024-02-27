package javassist.tools;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ClassFilePrinter;

/* loaded from: classes2.dex */
public class Dump {
    private Dump() {
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length != 1) {
            System.err.println("Usage: java Dump <class file name>");
            return;
        }
        ClassFile classFile = new ClassFile(new DataInputStream(new FileInputStream(strArr[0])));
        PrintWriter printWriter = new PrintWriter((OutputStream) System.out, true);
        printWriter.println("*** constant pool ***");
        classFile.getConstPool().print(printWriter);
        printWriter.println();
        printWriter.println("*** members ***");
        ClassFilePrinter.print(classFile, printWriter);
    }
}
