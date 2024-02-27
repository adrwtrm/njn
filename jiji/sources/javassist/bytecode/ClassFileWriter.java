package javassist.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javassist.bytecode.StackMapTable;

/* loaded from: classes2.dex */
public class ClassFileWriter {
    private ConstPoolWriter constPool;
    private FieldWriter fields;
    private MethodWriter methods;
    private ByteStream output;
    int superClass;
    int thisClass;

    /* loaded from: classes2.dex */
    public interface AttributeWriter {
        int size();

        void write(DataOutputStream dataOutputStream) throws IOException;
    }

    public ClassFileWriter(int i, int i2) {
        ByteStream byteStream = new ByteStream(512);
        this.output = byteStream;
        byteStream.writeInt(-889275714);
        this.output.writeShort(i2);
        this.output.writeShort(i);
        this.constPool = new ConstPoolWriter(this.output);
        this.fields = new FieldWriter(this.constPool);
        this.methods = new MethodWriter(this.constPool);
    }

    public ConstPoolWriter getConstPool() {
        return this.constPool;
    }

    public FieldWriter getFieldWriter() {
        return this.fields;
    }

    public MethodWriter getMethodWriter() {
        return this.methods;
    }

    public byte[] end(int i, int i2, int i3, int[] iArr, AttributeWriter attributeWriter) {
        this.constPool.end();
        this.output.writeShort(i);
        this.output.writeShort(i2);
        this.output.writeShort(i3);
        if (iArr == null) {
            this.output.writeShort(0);
        } else {
            this.output.writeShort(iArr.length);
            for (int i4 : iArr) {
                this.output.writeShort(i4);
            }
        }
        this.output.enlarge(this.fields.dataSize() + this.methods.dataSize() + 6);
        try {
            this.output.writeShort(this.fields.size());
            this.fields.write(this.output);
            this.output.writeShort(this.methods.numOfMethods());
            this.methods.write(this.output);
        } catch (IOException unused) {
        }
        writeAttribute(this.output, attributeWriter, 0);
        return this.output.toByteArray();
    }

    public void end(DataOutputStream dataOutputStream, int i, int i2, int i3, int[] iArr, AttributeWriter attributeWriter) throws IOException {
        this.constPool.end();
        this.output.writeTo(dataOutputStream);
        dataOutputStream.writeShort(i);
        dataOutputStream.writeShort(i2);
        dataOutputStream.writeShort(i3);
        if (iArr == null) {
            dataOutputStream.writeShort(0);
        } else {
            dataOutputStream.writeShort(iArr.length);
            for (int i4 : iArr) {
                dataOutputStream.writeShort(i4);
            }
        }
        dataOutputStream.writeShort(this.fields.size());
        this.fields.write(dataOutputStream);
        dataOutputStream.writeShort(this.methods.numOfMethods());
        this.methods.write(dataOutputStream);
        if (attributeWriter == null) {
            dataOutputStream.writeShort(0);
            return;
        }
        dataOutputStream.writeShort(attributeWriter.size());
        attributeWriter.write(dataOutputStream);
    }

    static void writeAttribute(ByteStream byteStream, AttributeWriter attributeWriter, int i) {
        if (attributeWriter == null) {
            byteStream.writeShort(i);
            return;
        }
        byteStream.writeShort(attributeWriter.size() + i);
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);
        try {
            attributeWriter.write(dataOutputStream);
            dataOutputStream.flush();
        } catch (IOException unused) {
        }
    }

    /* loaded from: classes2.dex */
    public static final class FieldWriter {
        protected ConstPoolWriter constPool;
        protected ByteStream output = new ByteStream(128);
        private int fieldCount = 0;

        FieldWriter(ConstPoolWriter constPoolWriter) {
            this.constPool = constPoolWriter;
        }

        public void add(int i, String str, String str2, AttributeWriter attributeWriter) {
            add(i, this.constPool.addUtf8Info(str), this.constPool.addUtf8Info(str2), attributeWriter);
        }

        public void add(int i, int i2, int i3, AttributeWriter attributeWriter) {
            this.fieldCount++;
            this.output.writeShort(i);
            this.output.writeShort(i2);
            this.output.writeShort(i3);
            ClassFileWriter.writeAttribute(this.output, attributeWriter, 0);
        }

        int size() {
            return this.fieldCount;
        }

        int dataSize() {
            return this.output.size();
        }

        void write(OutputStream outputStream) throws IOException {
            this.output.writeTo(outputStream);
        }
    }

    /* loaded from: classes2.dex */
    public static final class MethodWriter {
        private int catchCount;
        private int catchPos;
        protected ConstPoolWriter constPool;
        private boolean isAbstract;
        private int startPos;
        protected ByteStream output = new ByteStream(256);
        private int methodCount = 0;
        protected int codeIndex = 0;
        protected int throwsIndex = 0;
        protected int stackIndex = 0;

        MethodWriter(ConstPoolWriter constPoolWriter) {
            this.constPool = constPoolWriter;
        }

        public void begin(int i, String str, String str2, String[] strArr, AttributeWriter attributeWriter) {
            begin(i, this.constPool.addUtf8Info(str), this.constPool.addUtf8Info(str2), strArr == null ? null : this.constPool.addClassInfo(strArr), attributeWriter);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void begin(int i, int i2, int i3, int[] iArr, AttributeWriter attributeWriter) {
            this.methodCount++;
            this.output.writeShort(i);
            this.output.writeShort(i2);
            this.output.writeShort(i3);
            boolean z = (i & 1024) != 0 ? 1 : 0;
            this.isAbstract = z;
            int i4 = !z;
            if (iArr != null) {
                i4++;
            }
            ClassFileWriter.writeAttribute(this.output, attributeWriter, i4);
            if (iArr != null) {
                writeThrows(iArr);
            }
            if (!this.isAbstract) {
                if (this.codeIndex == 0) {
                    this.codeIndex = this.constPool.addUtf8Info(CodeAttribute.tag);
                }
                this.startPos = this.output.getPos();
                this.output.writeShort(this.codeIndex);
                this.output.writeBlank(12);
            }
            this.catchPos = -1;
            this.catchCount = 0;
        }

        private void writeThrows(int[] iArr) {
            if (this.throwsIndex == 0) {
                this.throwsIndex = this.constPool.addUtf8Info(ExceptionsAttribute.tag);
            }
            this.output.writeShort(this.throwsIndex);
            this.output.writeInt((iArr.length * 2) + 2);
            this.output.writeShort(iArr.length);
            for (int i : iArr) {
                this.output.writeShort(i);
            }
        }

        public void add(int i) {
            this.output.write(i);
        }

        public void add16(int i) {
            this.output.writeShort(i);
        }

        public void add32(int i) {
            this.output.writeInt(i);
        }

        public void addInvoke(int i, String str, String str2, String str3) {
            int addMethodrefInfo = this.constPool.addMethodrefInfo(this.constPool.addClassInfo(str), this.constPool.addNameAndTypeInfo(str2, str3));
            add(i);
            add16(addMethodrefInfo);
        }

        public void codeEnd(int i, int i2) {
            if (this.isAbstract) {
                return;
            }
            this.output.writeShort(this.startPos + 6, i);
            this.output.writeShort(this.startPos + 8, i2);
            ByteStream byteStream = this.output;
            byteStream.writeInt(this.startPos + 10, (byteStream.getPos() - this.startPos) - 14);
            this.catchPos = this.output.getPos();
            this.catchCount = 0;
            this.output.writeShort(0);
        }

        public void addCatch(int i, int i2, int i3, int i4) {
            this.catchCount++;
            this.output.writeShort(i);
            this.output.writeShort(i2);
            this.output.writeShort(i3);
            this.output.writeShort(i4);
        }

        public void end(StackMapTable.Writer writer, AttributeWriter attributeWriter) {
            if (this.isAbstract) {
                return;
            }
            this.output.writeShort(this.catchPos, this.catchCount);
            ClassFileWriter.writeAttribute(this.output, attributeWriter, writer == null ? 0 : 1);
            if (writer != null) {
                if (this.stackIndex == 0) {
                    this.stackIndex = this.constPool.addUtf8Info(StackMapTable.tag);
                }
                this.output.writeShort(this.stackIndex);
                byte[] byteArray = writer.toByteArray();
                this.output.writeInt(byteArray.length);
                this.output.write(byteArray);
            }
            ByteStream byteStream = this.output;
            byteStream.writeInt(this.startPos + 2, (byteStream.getPos() - this.startPos) - 6);
        }

        public int size() {
            return (this.output.getPos() - this.startPos) - 14;
        }

        int numOfMethods() {
            return this.methodCount;
        }

        int dataSize() {
            return this.output.size();
        }

        void write(OutputStream outputStream) throws IOException {
            this.output.writeTo(outputStream);
        }
    }

    /* loaded from: classes2.dex */
    public static final class ConstPoolWriter {
        protected int num = 1;
        ByteStream output;
        protected int startPos;

        ConstPoolWriter(ByteStream byteStream) {
            this.output = byteStream;
            this.startPos = byteStream.getPos();
            this.output.writeShort(1);
        }

        public int[] addClassInfo(String[] strArr) {
            int length = strArr.length;
            int[] iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = addClassInfo(strArr[i]);
            }
            return iArr;
        }

        public int addClassInfo(String str) {
            int addUtf8Info = addUtf8Info(str);
            this.output.write(7);
            this.output.writeShort(addUtf8Info);
            int i = this.num;
            this.num = i + 1;
            return i;
        }

        public int addClassInfo(int i) {
            this.output.write(7);
            this.output.writeShort(i);
            int i2 = this.num;
            this.num = i2 + 1;
            return i2;
        }

        public int addNameAndTypeInfo(String str, String str2) {
            return addNameAndTypeInfo(addUtf8Info(str), addUtf8Info(str2));
        }

        public int addNameAndTypeInfo(int i, int i2) {
            this.output.write(12);
            this.output.writeShort(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addFieldrefInfo(int i, int i2) {
            this.output.write(9);
            this.output.writeShort(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addMethodrefInfo(int i, int i2) {
            this.output.write(10);
            this.output.writeShort(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addInterfaceMethodrefInfo(int i, int i2) {
            this.output.write(11);
            this.output.writeShort(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addMethodHandleInfo(int i, int i2) {
            this.output.write(15);
            this.output.write(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addMethodTypeInfo(int i) {
            this.output.write(16);
            this.output.writeShort(i);
            int i2 = this.num;
            this.num = i2 + 1;
            return i2;
        }

        public int addInvokeDynamicInfo(int i, int i2) {
            this.output.write(18);
            this.output.writeShort(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addDynamicInfo(int i, int i2) {
            this.output.write(17);
            this.output.writeShort(i);
            this.output.writeShort(i2);
            int i3 = this.num;
            this.num = i3 + 1;
            return i3;
        }

        public int addStringInfo(String str) {
            int addUtf8Info = addUtf8Info(str);
            this.output.write(8);
            this.output.writeShort(addUtf8Info);
            int i = this.num;
            this.num = i + 1;
            return i;
        }

        public int addIntegerInfo(int i) {
            this.output.write(3);
            this.output.writeInt(i);
            int i2 = this.num;
            this.num = i2 + 1;
            return i2;
        }

        public int addFloatInfo(float f) {
            this.output.write(4);
            this.output.writeFloat(f);
            int i = this.num;
            this.num = i + 1;
            return i;
        }

        public int addLongInfo(long j) {
            this.output.write(5);
            this.output.writeLong(j);
            int i = this.num;
            this.num = i + 2;
            return i;
        }

        public int addDoubleInfo(double d) {
            this.output.write(6);
            this.output.writeDouble(d);
            int i = this.num;
            this.num = i + 2;
            return i;
        }

        public int addUtf8Info(String str) {
            this.output.write(1);
            this.output.writeUTF(str);
            int i = this.num;
            this.num = i + 1;
            return i;
        }

        void end() {
            this.output.writeShort(this.startPos, this.num);
        }
    }
}
