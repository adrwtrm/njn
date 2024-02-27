package javassist.bytecode.analysis;

import java.io.PrintStream;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.InstructionPrinter;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public final class FramePrinter {
    private final PrintStream stream;

    public FramePrinter(PrintStream printStream) {
        this.stream = printStream;
    }

    public static void print(CtClass ctClass, PrintStream printStream) {
        new FramePrinter(printStream).print(ctClass);
    }

    public void print(CtClass ctClass) {
        for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
            print(ctMethod);
        }
    }

    private String getMethodString(CtMethod ctMethod) {
        try {
            return Modifier.toString(ctMethod.getModifiers()) + " " + ctMethod.getReturnType().getName() + " " + ctMethod.getName() + Descriptor.toString(ctMethod.getSignature()) + ";";
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(CtMethod ctMethod) {
        this.stream.println("\n" + getMethodString(ctMethod));
        MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        ConstPool constPool = methodInfo2.getConstPool();
        CodeAttribute codeAttribute = methodInfo2.getCodeAttribute();
        if (codeAttribute == null) {
            return;
        }
        try {
            Frame[] analyze = new Analyzer().analyze(ctMethod.getDeclaringClass(), methodInfo2);
            int length = String.valueOf(codeAttribute.getCodeLength()).length();
            CodeIterator it = codeAttribute.iterator();
            while (it.hasNext()) {
                try {
                    int next = it.next();
                    this.stream.println(next + ": " + InstructionPrinter.instructionString(it, next, constPool));
                    int i = length + 3;
                    addSpacing(i);
                    Frame frame = analyze[next];
                    if (frame == null) {
                        this.stream.println("--DEAD CODE--");
                    } else {
                        printStack(frame);
                        addSpacing(i);
                        printLocals(frame);
                    }
                } catch (BadBytecode e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (BadBytecode e2) {
            throw new RuntimeException(e2);
        }
    }

    private void printStack(Frame frame) {
        this.stream.print("stack [");
        int topIndex = frame.getTopIndex();
        for (int i = 0; i <= topIndex; i++) {
            if (i > 0) {
                this.stream.print(", ");
            }
            this.stream.print(frame.getStack(i));
        }
        this.stream.println("]");
    }

    private void printLocals(Frame frame) {
        this.stream.print("locals [");
        int localsLength = frame.localsLength();
        for (int i = 0; i < localsLength; i++) {
            if (i > 0) {
                this.stream.print(", ");
            }
            Type local = frame.getLocal(i);
            this.stream.print(local == null ? "empty" : local.toString());
        }
        this.stream.println("]");
    }

    private void addSpacing(int i) {
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            this.stream.print(' ');
            i = i2;
        }
    }
}
