package javassist.bytecode.annotation;

import java.io.IOException;
import java.io.OutputStream;
import javassist.bytecode.ByteArray;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class AnnotationsWriter {
    protected OutputStream output;
    private ConstPool pool;

    public AnnotationsWriter(OutputStream outputStream, ConstPool constPool) {
        this.output = outputStream;
        this.pool = constPool;
    }

    public ConstPool getConstPool() {
        return this.pool;
    }

    public void close() throws IOException {
        this.output.close();
    }

    public void numParameters(int i) throws IOException {
        this.output.write(i);
    }

    public void numAnnotations(int i) throws IOException {
        write16bit(i);
    }

    public void annotation(String str, int i) throws IOException {
        annotation(this.pool.addUtf8Info(str), i);
    }

    public void annotation(int i, int i2) throws IOException {
        write16bit(i);
        write16bit(i2);
    }

    public void memberValuePair(String str) throws IOException {
        memberValuePair(this.pool.addUtf8Info(str));
    }

    public void memberValuePair(int i) throws IOException {
        write16bit(i);
    }

    public void constValueIndex(boolean z) throws IOException {
        constValueIndex(90, this.pool.addIntegerInfo(z ? 1 : 0));
    }

    public void constValueIndex(byte b) throws IOException {
        constValueIndex(66, this.pool.addIntegerInfo(b));
    }

    public void constValueIndex(char c) throws IOException {
        constValueIndex(67, this.pool.addIntegerInfo(c));
    }

    public void constValueIndex(short s) throws IOException {
        constValueIndex(83, this.pool.addIntegerInfo(s));
    }

    public void constValueIndex(int i) throws IOException {
        constValueIndex(73, this.pool.addIntegerInfo(i));
    }

    public void constValueIndex(long j) throws IOException {
        constValueIndex(74, this.pool.addLongInfo(j));
    }

    public void constValueIndex(float f) throws IOException {
        constValueIndex(70, this.pool.addFloatInfo(f));
    }

    public void constValueIndex(double d) throws IOException {
        constValueIndex(68, this.pool.addDoubleInfo(d));
    }

    public void constValueIndex(String str) throws IOException {
        constValueIndex(115, this.pool.addUtf8Info(str));
    }

    public void constValueIndex(int i, int i2) throws IOException {
        this.output.write(i);
        write16bit(i2);
    }

    public void enumConstValue(String str, String str2) throws IOException {
        enumConstValue(this.pool.addUtf8Info(str), this.pool.addUtf8Info(str2));
    }

    public void enumConstValue(int i, int i2) throws IOException {
        this.output.write(101);
        write16bit(i);
        write16bit(i2);
    }

    public void classInfoIndex(String str) throws IOException {
        classInfoIndex(this.pool.addUtf8Info(str));
    }

    public void classInfoIndex(int i) throws IOException {
        this.output.write(99);
        write16bit(i);
    }

    public void annotationValue() throws IOException {
        this.output.write(64);
    }

    public void arrayValue(int i) throws IOException {
        this.output.write(91);
        write16bit(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void write16bit(int i) throws IOException {
        byte[] bArr = new byte[2];
        ByteArray.write16bit(i, bArr, 0);
        this.output.write(bArr);
    }
}
