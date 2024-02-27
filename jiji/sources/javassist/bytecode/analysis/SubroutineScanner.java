package javassist.bytecode.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

/* loaded from: classes2.dex */
public class SubroutineScanner implements Opcode {
    private Subroutine[] subroutines;
    Map<Integer, Subroutine> subTable = new HashMap();
    Set<Integer> done = new HashSet();

    public Subroutine[] scan(MethodInfo methodInfo) throws BadBytecode {
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        CodeIterator it = codeAttribute.iterator();
        this.subroutines = new Subroutine[codeAttribute.getCodeLength()];
        this.subTable.clear();
        this.done.clear();
        scan(0, it, null);
        ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
        for (int i = 0; i < exceptionTable.size(); i++) {
            scan(exceptionTable.handlerPc(i), it, this.subroutines[exceptionTable.startPc(i)]);
        }
        return this.subroutines;
    }

    private void scan(int i, CodeIterator codeIterator, Subroutine subroutine) throws BadBytecode {
        boolean z;
        if (this.done.contains(Integer.valueOf(i))) {
            return;
        }
        this.done.add(Integer.valueOf(i));
        int lookAhead = codeIterator.lookAhead();
        codeIterator.move(i);
        do {
            if (scanOp(codeIterator.next(), codeIterator, subroutine) && codeIterator.hasNext()) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
        } while (z);
        codeIterator.move(lookAhead);
    }

    private boolean scanOp(int i, CodeIterator codeIterator, Subroutine subroutine) throws BadBytecode {
        this.subroutines[i] = subroutine;
        int byteAt = codeIterator.byteAt(i);
        if (byteAt == 170) {
            scanTableSwitch(i, codeIterator, subroutine);
            return false;
        } else if (byteAt == 171) {
            scanLookupSwitch(i, codeIterator, subroutine);
            return false;
        } else if (Util.isReturn(byteAt) || byteAt == 169 || byteAt == 191) {
            return false;
        } else {
            if (Util.isJumpInstruction(byteAt)) {
                int jumpTarget = Util.getJumpTarget(i, codeIterator);
                if (byteAt == 168 || byteAt == 201) {
                    Subroutine subroutine2 = this.subTable.get(Integer.valueOf(jumpTarget));
                    if (subroutine2 == null) {
                        Subroutine subroutine3 = new Subroutine(jumpTarget, i);
                        this.subTable.put(Integer.valueOf(jumpTarget), subroutine3);
                        scan(jumpTarget, codeIterator, subroutine3);
                        return true;
                    }
                    subroutine2.addCaller(i);
                    return true;
                }
                scan(jumpTarget, codeIterator, subroutine);
                return !Util.isGoto(byteAt);
            }
            return true;
        }
    }

    private void scanLookupSwitch(int i, CodeIterator codeIterator, Subroutine subroutine) throws BadBytecode {
        int i2 = (i & (-4)) + 4;
        scan(codeIterator.s32bitAt(i2) + i, codeIterator, subroutine);
        int i3 = i2 + 4;
        int i4 = i3 + 4;
        int s32bitAt = (codeIterator.s32bitAt(i3) * 8) + i4;
        for (int i5 = i4 + 4; i5 < s32bitAt; i5 += 8) {
            scan(codeIterator.s32bitAt(i5) + i, codeIterator, subroutine);
        }
    }

    private void scanTableSwitch(int i, CodeIterator codeIterator, Subroutine subroutine) throws BadBytecode {
        int i2 = (i & (-4)) + 4;
        scan(codeIterator.s32bitAt(i2) + i, codeIterator, subroutine);
        int i3 = i2 + 4;
        int s32bitAt = codeIterator.s32bitAt(i3);
        int i4 = i3 + 4;
        int i5 = i4 + 4;
        int s32bitAt2 = (((codeIterator.s32bitAt(i4) - s32bitAt) + 1) * 4) + i5;
        while (i5 < s32bitAt2) {
            scan(codeIterator.s32bitAt(i5) + i, codeIterator, subroutine);
            i5 += 4;
        }
    }
}
