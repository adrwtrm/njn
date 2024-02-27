package javassist.bytecode.annotation;

import java.io.IOException;
import java.io.OutputStream;
import javassist.bytecode.ConstPool;

/* loaded from: classes2.dex */
public class TypeAnnotationsWriter extends AnnotationsWriter {
    public TypeAnnotationsWriter(OutputStream outputStream, ConstPool constPool) {
        super(outputStream, constPool);
    }

    @Override // javassist.bytecode.annotation.AnnotationsWriter
    public void numAnnotations(int i) throws IOException {
        super.numAnnotations(i);
    }

    public void typeParameterTarget(int i, int i2) throws IOException {
        this.output.write(i);
        this.output.write(i2);
    }

    public void supertypeTarget(int i) throws IOException {
        this.output.write(16);
        write16bit(i);
    }

    public void typeParameterBoundTarget(int i, int i2, int i3) throws IOException {
        this.output.write(i);
        this.output.write(i2);
        this.output.write(i3);
    }

    public void emptyTarget(int i) throws IOException {
        this.output.write(i);
    }

    public void formalParameterTarget(int i) throws IOException {
        this.output.write(22);
        this.output.write(i);
    }

    public void throwsTarget(int i) throws IOException {
        this.output.write(23);
        write16bit(i);
    }

    public void localVarTarget(int i, int i2) throws IOException {
        this.output.write(i);
        write16bit(i2);
    }

    public void localVarTargetTable(int i, int i2, int i3) throws IOException {
        write16bit(i);
        write16bit(i2);
        write16bit(i3);
    }

    public void catchTarget(int i) throws IOException {
        this.output.write(66);
        write16bit(i);
    }

    public void offsetTarget(int i, int i2) throws IOException {
        this.output.write(i);
        write16bit(i2);
    }

    public void typeArgumentTarget(int i, int i2, int i3) throws IOException {
        this.output.write(i);
        write16bit(i2);
        this.output.write(i3);
    }

    public void typePath(int i) throws IOException {
        this.output.write(i);
    }

    public void typePathPath(int i, int i2) throws IOException {
        this.output.write(i);
        this.output.write(i2);
    }
}
