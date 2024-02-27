package org.objectweb.asm;

/* loaded from: classes.dex */
final class Handler {
    final int catchType;
    final String catchTypeDescriptor;
    final Label endPc;
    final Label handlerPc;
    Handler nextHandler;
    final Label startPc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Handler(Label label, Label label2, Label label3, int i, String str) {
        this.startPc = label;
        this.endPc = label2;
        this.handlerPc = label3;
        this.catchType = i;
        this.catchTypeDescriptor = str;
    }

    Handler(Handler handler, Label label, Label label2) {
        this(label, label2, handler.handlerPc, handler.catchType, handler.catchTypeDescriptor);
        this.nextHandler = handler.nextHandler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Handler removeRange(Handler handler, Label label, Label label2) {
        if (handler == null) {
            return null;
        }
        handler.nextHandler = removeRange(handler.nextHandler, label, label2);
        int i = handler.startPc.bytecodeOffset;
        int i2 = handler.endPc.bytecodeOffset;
        int i3 = label.bytecodeOffset;
        int i4 = label2 == null ? Integer.MAX_VALUE : label2.bytecodeOffset;
        if (i3 >= i2 || i4 <= i) {
            return handler;
        }
        if (i3 <= i) {
            if (i4 >= i2) {
                return handler.nextHandler;
            }
            return new Handler(handler, label2, handler.endPc);
        } else if (i4 >= i2) {
            return new Handler(handler, handler.startPc, label);
        } else {
            handler.nextHandler = new Handler(handler, label2, handler.endPc);
            return new Handler(handler, handler.startPc, label);
        }
    }

    static int getExceptionTableLength(Handler handler) {
        int i = 0;
        while (handler != null) {
            i++;
            handler = handler.nextHandler;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getExceptionTableSize(Handler handler) {
        return (getExceptionTableLength(handler) * 8) + 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void putExceptionTable(Handler handler, ByteVector byteVector) {
        byteVector.putShort(getExceptionTableLength(handler));
        while (handler != null) {
            byteVector.putShort(handler.startPc.bytecodeOffset).putShort(handler.endPc.bytecodeOffset).putShort(handler.handlerPc.bytecodeOffset).putShort(handler.catchType);
            handler = handler.nextHandler;
        }
    }
}
