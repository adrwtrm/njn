package javassist.bytecode;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import javassist.Modifier;
import javassist.bytecode.StackMapTable;

/* loaded from: classes2.dex */
public class ClassFilePrinter {
    public static void print(ClassFile classFile) {
        print(classFile, new PrintWriter((OutputStream) System.out, true));
    }

    public static void print(ClassFile classFile, PrintWriter printWriter) {
        int modifier = AccessFlag.toModifier(classFile.getAccessFlags() & (-33));
        printWriter.println("major: " + classFile.major + ", minor: " + classFile.minor + " modifiers: " + Integer.toHexString(classFile.getAccessFlags()));
        printWriter.println(Modifier.toString(modifier) + " class " + classFile.getName() + " extends " + classFile.getSuperclass());
        String[] interfaces = classFile.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            printWriter.print("    implements ");
            printWriter.print(interfaces[0]);
            for (int i = 1; i < interfaces.length; i++) {
                printWriter.print(", " + interfaces[i]);
            }
            printWriter.println();
        }
        printWriter.println();
        for (FieldInfo fieldInfo : classFile.getFields()) {
            printWriter.println(Modifier.toString(AccessFlag.toModifier(fieldInfo.getAccessFlags())) + " " + fieldInfo.getName() + "\t" + fieldInfo.getDescriptor());
            printAttributes(fieldInfo.getAttributes(), printWriter, 'f');
        }
        printWriter.println();
        for (MethodInfo methodInfo : classFile.getMethods()) {
            printWriter.println(Modifier.toString(AccessFlag.toModifier(methodInfo.getAccessFlags())) + " " + methodInfo.getName() + "\t" + methodInfo.getDescriptor());
            printAttributes(methodInfo.getAttributes(), printWriter, 'm');
            printWriter.println();
        }
        printWriter.println();
        printAttributes(classFile.getAttributes(), printWriter, 'c');
    }

    static void printAttributes(List<AttributeInfo> list, PrintWriter printWriter, char c) {
        String classSignature;
        if (list == null) {
            return;
        }
        for (AttributeInfo attributeInfo : list) {
            if (attributeInfo instanceof CodeAttribute) {
                CodeAttribute codeAttribute = (CodeAttribute) attributeInfo;
                printWriter.println("attribute: " + attributeInfo.getName() + ": " + attributeInfo.getClass().getName());
                printWriter.println("max stack " + codeAttribute.getMaxStack() + ", max locals " + codeAttribute.getMaxLocals() + ", " + codeAttribute.getExceptionTable().size() + " catch blocks");
                printWriter.println("<code attribute begin>");
                printAttributes(codeAttribute.getAttributes(), printWriter, c);
                printWriter.println("<code attribute end>");
            } else if (attributeInfo instanceof AnnotationsAttribute) {
                printWriter.println("annnotation: " + attributeInfo.toString());
            } else if (attributeInfo instanceof ParameterAnnotationsAttribute) {
                printWriter.println("parameter annnotations: " + attributeInfo.toString());
            } else if (attributeInfo instanceof StackMapTable) {
                printWriter.println("<stack map table begin>");
                StackMapTable.Printer.print((StackMapTable) attributeInfo, printWriter);
                printWriter.println("<stack map table end>");
            } else if (attributeInfo instanceof StackMap) {
                printWriter.println("<stack map begin>");
                ((StackMap) attributeInfo).print(printWriter);
                printWriter.println("<stack map end>");
            } else if (attributeInfo instanceof SignatureAttribute) {
                String signature = ((SignatureAttribute) attributeInfo).getSignature();
                printWriter.println("signature: " + signature);
                if (c == 'c') {
                    try {
                        classSignature = SignatureAttribute.toClassSignature(signature).toString();
                    } catch (BadBytecode unused) {
                        printWriter.println("           syntax error");
                    }
                } else if (c == 'm') {
                    classSignature = SignatureAttribute.toMethodSignature(signature).toString();
                } else {
                    classSignature = SignatureAttribute.toFieldSignature(signature).toString();
                }
                printWriter.println("           " + classSignature);
            } else {
                printWriter.println("attribute: " + attributeInfo.getName() + " (" + attributeInfo.get().length + " byte): " + attributeInfo.getClass().getName());
            }
        }
    }
}
