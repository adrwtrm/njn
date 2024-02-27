package javassist.bytecode.analysis;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

/* loaded from: classes2.dex */
public class Analyzer implements Opcode {
    private CtClass clazz;
    private ExceptionInfo[] exceptions;
    private Frame[] frames;
    private final SubroutineScanner scanner = new SubroutineScanner();
    private Subroutine[] subroutines;

    /* loaded from: classes2.dex */
    public static class ExceptionInfo {
        private int end;
        private int handler;
        private int start;
        private Type type;

        private ExceptionInfo(int i, int i2, int i3, Type type) {
            this.start = i;
            this.end = i2;
            this.handler = i3;
            this.type = type;
        }
    }

    public Frame[] analyze(CtClass ctClass, MethodInfo methodInfo) throws BadBytecode {
        this.clazz = ctClass;
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return null;
        }
        int maxLocals = codeAttribute.getMaxLocals();
        int maxStack = codeAttribute.getMaxStack();
        int codeLength = codeAttribute.getCodeLength();
        CodeIterator it = codeAttribute.iterator();
        IntQueue intQueue = new IntQueue();
        this.exceptions = buildExceptionInfo(methodInfo);
        this.subroutines = this.scanner.scan(methodInfo);
        Executor executor = new Executor(ctClass.getClassPool(), methodInfo.getConstPool());
        Frame[] frameArr = new Frame[codeLength];
        this.frames = frameArr;
        frameArr[it.lookAhead()] = firstFrame(methodInfo, maxLocals, maxStack);
        intQueue.add(it.next());
        while (!intQueue.isEmpty()) {
            analyzeNextEntry(methodInfo, it, intQueue, executor);
        }
        return this.frames;
    }

    public Frame[] analyze(CtMethod ctMethod) throws BadBytecode {
        return analyze(ctMethod.getDeclaringClass(), ctMethod.getMethodInfo2());
    }

    private void analyzeNextEntry(MethodInfo methodInfo, CodeIterator codeIterator, IntQueue intQueue, Executor executor) throws BadBytecode {
        int take = intQueue.take();
        codeIterator.move(take);
        codeIterator.next();
        Frame copy = this.frames[take].copy();
        Subroutine subroutine = this.subroutines[take];
        try {
            executor.execute(methodInfo, take, codeIterator, copy, subroutine);
            int byteAt = codeIterator.byteAt(take);
            if (byteAt == 170) {
                mergeTableSwitch(intQueue, take, codeIterator, copy);
            } else if (byteAt == 171) {
                mergeLookupSwitch(intQueue, take, codeIterator, copy);
            } else if (byteAt == 169) {
                mergeRet(intQueue, codeIterator, take, copy, subroutine);
            } else if (Util.isJumpInstruction(byteAt)) {
                int jumpTarget = Util.getJumpTarget(take, codeIterator);
                if (Util.isJsr(byteAt)) {
                    mergeJsr(intQueue, this.frames[take], this.subroutines[jumpTarget], take, lookAhead(codeIterator, take));
                } else if (!Util.isGoto(byteAt)) {
                    merge(intQueue, copy, lookAhead(codeIterator, take));
                }
                merge(intQueue, copy, jumpTarget);
            } else if (byteAt != 191 && !Util.isReturn(byteAt)) {
                merge(intQueue, copy, lookAhead(codeIterator, take));
            }
            mergeExceptionHandlers(intQueue, methodInfo, take, copy);
        } catch (RuntimeException e) {
            throw new BadBytecode(e.getMessage() + "[pos = " + take + "]", e);
        }
    }

    private ExceptionInfo[] buildExceptionInfo(MethodInfo methodInfo) {
        Type type;
        ConstPool constPool = methodInfo.getConstPool();
        ClassPool classPool = this.clazz.getClassPool();
        ExceptionTable exceptionTable = methodInfo.getCodeAttribute().getExceptionTable();
        ExceptionInfo[] exceptionInfoArr = new ExceptionInfo[exceptionTable.size()];
        for (int i = 0; i < exceptionTable.size(); i++) {
            int catchType = exceptionTable.catchType(i);
            if (catchType == 0) {
                try {
                    type = Type.THROWABLE;
                } catch (NotFoundException e) {
                    throw new IllegalStateException(e.getMessage());
                }
            } else {
                type = Type.get(classPool.get(constPool.getClassInfo(catchType)));
            }
            exceptionInfoArr[i] = new ExceptionInfo(exceptionTable.startPc(i), exceptionTable.endPc(i), exceptionTable.handlerPc(i), type);
        }
        return exceptionInfoArr;
    }

    private Frame firstFrame(MethodInfo methodInfo, int i, int i2) {
        int i3;
        Frame frame = new Frame(i, i2);
        if ((methodInfo.getAccessFlags() & 8) == 0) {
            frame.setLocal(0, Type.get(this.clazz));
            i3 = 1;
        } else {
            i3 = 0;
        }
        try {
            for (CtClass ctClass : Descriptor.getParameterTypes(methodInfo.getDescriptor(), this.clazz.getClassPool())) {
                Type zeroExtend = zeroExtend(Type.get(ctClass));
                int i4 = i3 + 1;
                frame.setLocal(i3, zeroExtend);
                if (zeroExtend.getSize() == 2) {
                    i3 = i4 + 1;
                    frame.setLocal(i4, Type.TOP);
                } else {
                    i3 = i4;
                }
            }
            return frame;
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int getNext(CodeIterator codeIterator, int i, int i2) throws BadBytecode {
        codeIterator.move(i);
        codeIterator.next();
        int lookAhead = codeIterator.lookAhead();
        codeIterator.move(i2);
        codeIterator.next();
        return lookAhead;
    }

    private int lookAhead(CodeIterator codeIterator, int i) throws BadBytecode {
        if (!codeIterator.hasNext()) {
            throw new BadBytecode("Execution falls off end! [pos = " + i + "]");
        }
        return codeIterator.lookAhead();
    }

    private void merge(IntQueue intQueue, Frame frame, int i) {
        boolean merge;
        Frame[] frameArr = this.frames;
        Frame frame2 = frameArr[i];
        if (frame2 == null) {
            frameArr[i] = frame.copy();
            merge = true;
        } else {
            merge = frame2.merge(frame);
        }
        if (merge) {
            intQueue.add(i);
        }
    }

    private void mergeExceptionHandlers(IntQueue intQueue, MethodInfo methodInfo, int i, Frame frame) {
        int i2 = 0;
        while (true) {
            ExceptionInfo[] exceptionInfoArr = this.exceptions;
            if (i2 >= exceptionInfoArr.length) {
                return;
            }
            ExceptionInfo exceptionInfo = exceptionInfoArr[i2];
            if (i >= exceptionInfo.start && i < exceptionInfo.end) {
                Frame copy = frame.copy();
                copy.clearStack();
                copy.push(exceptionInfo.type);
                merge(intQueue, copy, exceptionInfo.handler);
            }
            i2++;
        }
    }

    private void mergeJsr(IntQueue intQueue, Frame frame, Subroutine subroutine, int i, int i2) throws BadBytecode {
        boolean z;
        if (subroutine == null) {
            throw new BadBytecode("No subroutine at jsr target! [pos = " + i + "]");
        }
        Frame[] frameArr = this.frames;
        Frame frame2 = frameArr[i2];
        boolean z2 = true;
        if (frame2 == null) {
            frame2 = frame.copy();
            frameArr[i2] = frame2;
            z = true;
        } else {
            z = false;
            for (int i3 = 0; i3 < frame.localsLength(); i3++) {
                if (!subroutine.isAccessed(i3)) {
                    Type local = frame2.getLocal(i3);
                    Type local2 = frame.getLocal(i3);
                    if (local == null) {
                        frame2.setLocal(i3, local2);
                    } else {
                        Type merge = local.merge(local2);
                        frame2.setLocal(i3, merge);
                        if (merge.equals(local) && !merge.popChanged()) {
                        }
                    }
                    z = true;
                }
            }
        }
        if (frame2.isJsrMerged()) {
            z2 = z;
        } else {
            frame2.setJsrMerged(true);
        }
        if (z2 && frame2.isRetMerged()) {
            intQueue.add(i2);
        }
    }

    private void mergeLookupSwitch(IntQueue intQueue, int i, CodeIterator codeIterator, Frame frame) throws BadBytecode {
        int i2 = (i & (-4)) + 4;
        merge(intQueue, frame, codeIterator.s32bitAt(i2) + i);
        int i3 = i2 + 4;
        int i4 = i3 + 4;
        int s32bitAt = (codeIterator.s32bitAt(i3) * 8) + i4;
        for (int i5 = i4 + 4; i5 < s32bitAt; i5 += 8) {
            merge(intQueue, frame, codeIterator.s32bitAt(i5) + i);
        }
    }

    private void mergeRet(IntQueue intQueue, CodeIterator codeIterator, int i, Frame frame, Subroutine subroutine) throws BadBytecode {
        boolean mergeStack;
        if (subroutine == null) {
            throw new BadBytecode("Ret on no subroutine! [pos = " + i + "]");
        }
        for (Integer num : subroutine.callers()) {
            int next = getNext(codeIterator, num.intValue(), i);
            Frame[] frameArr = this.frames;
            Frame frame2 = frameArr[next];
            boolean z = true;
            if (frame2 == null) {
                frame2 = frame.copyStack();
                frameArr[next] = frame2;
                mergeStack = true;
            } else {
                mergeStack = frame2.mergeStack(frame);
            }
            for (Integer num2 : subroutine.accessed()) {
                int intValue = num2.intValue();
                Type local = frame2.getLocal(intValue);
                Type local2 = frame.getLocal(intValue);
                if (local != local2) {
                    frame2.setLocal(intValue, local2);
                    mergeStack = true;
                }
            }
            if (frame2.isRetMerged()) {
                z = mergeStack;
            } else {
                frame2.setRetMerged(true);
            }
            if (z && frame2.isJsrMerged()) {
                intQueue.add(next);
            }
        }
    }

    private void mergeTableSwitch(IntQueue intQueue, int i, CodeIterator codeIterator, Frame frame) throws BadBytecode {
        int i2 = (i & (-4)) + 4;
        merge(intQueue, frame, codeIterator.s32bitAt(i2) + i);
        int i3 = i2 + 4;
        int s32bitAt = codeIterator.s32bitAt(i3);
        int i4 = i3 + 4;
        int i5 = i4 + 4;
        int s32bitAt2 = (((codeIterator.s32bitAt(i4) - s32bitAt) + 1) * 4) + i5;
        while (i5 < s32bitAt2) {
            merge(intQueue, frame, codeIterator.s32bitAt(i5) + i);
            i5 += 4;
        }
    }

    private Type zeroExtend(Type type) {
        return (type == Type.SHORT || type == Type.BYTE || type == Type.CHAR || type == Type.BOOLEAN) ? Type.INTEGER : type;
    }
}
