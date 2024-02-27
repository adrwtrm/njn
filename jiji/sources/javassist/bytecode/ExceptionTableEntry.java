package javassist.bytecode;

/* compiled from: ExceptionTable.java */
/* loaded from: classes2.dex */
class ExceptionTableEntry {
    int catchType;
    int endPc;
    int handlerPc;
    int startPc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExceptionTableEntry(int i, int i2, int i3, int i4) {
        this.startPc = i;
        this.endPc = i2;
        this.handlerPc = i3;
        this.catchType = i4;
    }
}
