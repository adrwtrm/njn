package javassist.convert;

import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;

/* loaded from: classes2.dex */
public class TransformAfter extends TransformBefore {
    public TransformAfter(Transformer transformer, CtMethod ctMethod, CtMethod ctMethod2) throws NotFoundException {
        super(transformer, ctMethod, ctMethod2);
    }

    @Override // javassist.convert.TransformBefore
    protected int match2(int i, CodeIterator codeIterator) throws BadBytecode {
        codeIterator.move(i);
        codeIterator.insert(this.saveCode);
        codeIterator.insert(this.loadCode);
        codeIterator.setMark(codeIterator.insertGap(3));
        codeIterator.insert(this.loadCode);
        int next = codeIterator.next();
        int mark = codeIterator.getMark();
        codeIterator.writeByte(codeIterator.byteAt(next), mark);
        int i2 = next + 1;
        codeIterator.write16bit(codeIterator.u16bitAt(i2), mark + 1);
        codeIterator.writeByte(184, next);
        codeIterator.write16bit(this.newIndex, i2);
        codeIterator.move(mark);
        return codeIterator.next();
    }
}
