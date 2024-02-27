package javassist.bytecode.analysis;

import javassist.bytecode.CodeIterator;
import javassist.bytecode.Opcode;

/* loaded from: classes2.dex */
public class Util implements Opcode {
    public static boolean isGoto(int i) {
        return i == 167 || i == 200;
    }

    public static boolean isJsr(int i) {
        return i == 168 || i == 201;
    }

    public static boolean isJumpInstruction(int i) {
        return (i >= 153 && i <= 168) || i == 198 || i == 199 || i == 201 || i == 200;
    }

    public static boolean isReturn(int i) {
        return i >= 172 && i <= 177;
    }

    public static int getJumpTarget(int i, CodeIterator codeIterator) {
        int byteAt = codeIterator.byteAt(i);
        return i + ((byteAt == 201 || byteAt == 200) ? codeIterator.s32bitAt(i + 1) : codeIterator.s16bitAt(i + 1));
    }
}
