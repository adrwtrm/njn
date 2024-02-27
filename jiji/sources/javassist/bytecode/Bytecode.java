package javassist.bytecode;

import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import javassist.CtClass;
import javassist.CtPrimitiveType;

/* loaded from: classes2.dex */
public class Bytecode extends ByteVector implements Cloneable, Opcode {
    public static final CtClass THIS = ConstPool.THIS;
    ConstPool constPool;
    int maxLocals;
    int maxStack;
    private int stackDepth;
    ExceptionTable tryblocks;

    @Override // javassist.bytecode.ByteVector
    public /* bridge */ /* synthetic */ void add(int i, int i2) {
        super.add(i, i2);
    }

    @Override // javassist.bytecode.ByteVector
    public /* bridge */ /* synthetic */ void add(int i, int i2, int i3, int i4) {
        super.add(i, i2, i3, i4);
    }

    public Bytecode(ConstPool constPool, int i, int i2) {
        this.constPool = constPool;
        this.maxStack = i;
        this.maxLocals = i2;
        this.tryblocks = new ExceptionTable(constPool);
        this.stackDepth = 0;
    }

    public Bytecode(ConstPool constPool) {
        this(constPool, 0, 0);
    }

    @Override // javassist.bytecode.ByteVector
    public Object clone() {
        try {
            Bytecode bytecode = (Bytecode) super.clone();
            bytecode.tryblocks = (ExceptionTable) this.tryblocks.clone();
            return bytecode;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public ConstPool getConstPool() {
        return this.constPool;
    }

    public ExceptionTable getExceptionTable() {
        return this.tryblocks;
    }

    public CodeAttribute toCodeAttribute() {
        return new CodeAttribute(this.constPool, this.maxStack, this.maxLocals, get(), this.tryblocks);
    }

    public int length() {
        return getSize();
    }

    public byte[] get() {
        return copy();
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public void setMaxStack(int i) {
        this.maxStack = i;
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public void setMaxLocals(int i) {
        this.maxLocals = i;
    }

    public void setMaxLocals(boolean z, CtClass[] ctClassArr, int i) {
        if (!z) {
            i++;
        }
        if (ctClassArr != null) {
            CtClass ctClass = CtClass.doubleType;
            CtClass ctClass2 = CtClass.longType;
            int length = ctClassArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                CtClass ctClass3 = ctClassArr[i2];
                i = (ctClass3 == ctClass || ctClass3 == ctClass2) ? i + 2 : i + 1;
            }
        }
        this.maxLocals = i;
    }

    public void incMaxLocals(int i) {
        this.maxLocals += i;
    }

    public void addExceptionHandler(int i, int i2, int i3, CtClass ctClass) {
        addExceptionHandler(i, i2, i3, this.constPool.addClassInfo(ctClass));
    }

    public void addExceptionHandler(int i, int i2, int i3, String str) {
        addExceptionHandler(i, i2, i3, this.constPool.addClassInfo(str));
    }

    public void addExceptionHandler(int i, int i2, int i3, int i4) {
        this.tryblocks.add(i, i2, i3, i4);
    }

    public int currentPc() {
        return getSize();
    }

    @Override // javassist.bytecode.ByteVector
    public int read(int i) {
        return super.read(i);
    }

    public int read16bit(int i) {
        return (read(i) << 8) + (read(i + 1) & 255);
    }

    public int read32bit(int i) {
        return (read16bit(i) << 16) + (read16bit(i + 2) & SupportMenu.USER_MASK);
    }

    @Override // javassist.bytecode.ByteVector
    public void write(int i, int i2) {
        super.write(i, i2);
    }

    public void write16bit(int i, int i2) {
        write(i, i2 >> 8);
        write(i + 1, i2);
    }

    public void write32bit(int i, int i2) {
        write16bit(i, i2 >> 16);
        write16bit(i + 2, i2);
    }

    @Override // javassist.bytecode.ByteVector
    public void add(int i) {
        super.add(i);
    }

    public void add32bit(int i) {
        add(i >> 24, i >> 16, i >> 8, i);
    }

    @Override // javassist.bytecode.ByteVector
    public void addGap(int i) {
        super.addGap(i);
    }

    public void addOpcode(int i) {
        add(i);
        growStack(STACK_GROW[i]);
    }

    public void growStack(int i) {
        setStackDepth(this.stackDepth + i);
    }

    public int getStackDepth() {
        return this.stackDepth;
    }

    public void setStackDepth(int i) {
        this.stackDepth = i;
        if (i > this.maxStack) {
            this.maxStack = i;
        }
    }

    public void addIndex(int i) {
        add(i >> 8, i);
    }

    public void addAload(int i) {
        if (i < 4) {
            addOpcode(i + 42);
        } else if (i < 256) {
            addOpcode(25);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(25);
            addIndex(i);
        }
    }

    public void addAstore(int i) {
        if (i < 4) {
            addOpcode(i + 75);
        } else if (i < 256) {
            addOpcode(58);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(58);
            addIndex(i);
        }
    }

    public void addIconst(int i) {
        if (i < 6 && -2 < i) {
            addOpcode(i + 3);
        } else if (i <= 127 && -128 <= i) {
            addOpcode(16);
            add(i);
        } else if (i <= 32767 && -32768 <= i) {
            addOpcode(17);
            add(i >> 8);
            add(i);
        } else {
            addLdc(this.constPool.addIntegerInfo(i));
        }
    }

    public void addConstZero(CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            if (ctClass == CtClass.longType) {
                addOpcode(9);
                return;
            } else if (ctClass == CtClass.floatType) {
                addOpcode(11);
                return;
            } else if (ctClass == CtClass.doubleType) {
                addOpcode(14);
                return;
            } else if (ctClass == CtClass.voidType) {
                throw new RuntimeException("void type?");
            } else {
                addOpcode(3);
                return;
            }
        }
        addOpcode(1);
    }

    public void addIload(int i) {
        if (i < 4) {
            addOpcode(i + 26);
        } else if (i < 256) {
            addOpcode(21);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(21);
            addIndex(i);
        }
    }

    public void addIstore(int i) {
        if (i < 4) {
            addOpcode(i + 59);
        } else if (i < 256) {
            addOpcode(54);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(54);
            addIndex(i);
        }
    }

    public void addLconst(long j) {
        if (j == 0 || j == 1) {
            addOpcode(((int) j) + 9);
        } else {
            addLdc2w(j);
        }
    }

    public void addLload(int i) {
        if (i < 4) {
            addOpcode(i + 30);
        } else if (i < 256) {
            addOpcode(22);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(22);
            addIndex(i);
        }
    }

    public void addLstore(int i) {
        if (i < 4) {
            addOpcode(i + 63);
        } else if (i < 256) {
            addOpcode(55);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(55);
            addIndex(i);
        }
    }

    public void addDconst(double d) {
        if (d == 0.0d || d == 1.0d) {
            addOpcode(((int) d) + 14);
        } else {
            addLdc2w(d);
        }
    }

    public void addDload(int i) {
        if (i < 4) {
            addOpcode(i + 38);
        } else if (i < 256) {
            addOpcode(24);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(24);
            addIndex(i);
        }
    }

    public void addDstore(int i) {
        if (i < 4) {
            addOpcode(i + 71);
        } else if (i < 256) {
            addOpcode(57);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(57);
            addIndex(i);
        }
    }

    public void addFconst(float f) {
        if (f == 0.0f || f == 1.0f || f == 2.0f) {
            addOpcode(((int) f) + 11);
        } else {
            addLdc(this.constPool.addFloatInfo(f));
        }
    }

    public void addFload(int i) {
        if (i < 4) {
            addOpcode(i + 34);
        } else if (i < 256) {
            addOpcode(23);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(23);
            addIndex(i);
        }
    }

    public void addFstore(int i) {
        if (i < 4) {
            addOpcode(i + 67);
        } else if (i < 256) {
            addOpcode(56);
            add(i);
        } else {
            addOpcode(Opcode.WIDE);
            addOpcode(56);
            addIndex(i);
        }
    }

    public int addLoad(int i, CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            if (ctClass == CtClass.booleanType || ctClass == CtClass.charType || ctClass == CtClass.byteType || ctClass == CtClass.shortType || ctClass == CtClass.intType) {
                addIload(i);
                return 1;
            } else if (ctClass == CtClass.longType) {
                addLload(i);
                return 2;
            } else if (ctClass == CtClass.floatType) {
                addFload(i);
                return 1;
            } else if (ctClass == CtClass.doubleType) {
                addDload(i);
                return 2;
            } else {
                throw new RuntimeException("void type?");
            }
        }
        addAload(i);
        return 1;
    }

    public int addStore(int i, CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            if (ctClass == CtClass.booleanType || ctClass == CtClass.charType || ctClass == CtClass.byteType || ctClass == CtClass.shortType || ctClass == CtClass.intType) {
                addIstore(i);
                return 1;
            } else if (ctClass == CtClass.longType) {
                addLstore(i);
                return 2;
            } else if (ctClass == CtClass.floatType) {
                addFstore(i);
                return 1;
            } else if (ctClass == CtClass.doubleType) {
                addDstore(i);
                return 2;
            } else {
                throw new RuntimeException("void type?");
            }
        }
        addAstore(i);
        return 1;
    }

    public int addLoadParameters(CtClass[] ctClassArr, int i) {
        if (ctClassArr != null) {
            int i2 = 0;
            for (CtClass ctClass : ctClassArr) {
                i2 += addLoad(i2 + i, ctClass);
            }
            return i2;
        }
        return 0;
    }

    public void addCheckcast(CtClass ctClass) {
        addOpcode(192);
        addIndex(this.constPool.addClassInfo(ctClass));
    }

    public void addCheckcast(String str) {
        addOpcode(192);
        addIndex(this.constPool.addClassInfo(str));
    }

    public void addInstanceof(String str) {
        addOpcode(193);
        addIndex(this.constPool.addClassInfo(str));
    }

    public void addGetfield(CtClass ctClass, String str, String str2) {
        add(180);
        addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(ctClass), str, str2));
        growStack(Descriptor.dataSize(str2) - 1);
    }

    public void addGetfield(String str, String str2, String str3) {
        add(180);
        addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(str), str2, str3));
        growStack(Descriptor.dataSize(str3) - 1);
    }

    public void addGetstatic(CtClass ctClass, String str, String str2) {
        add(178);
        addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(ctClass), str, str2));
        growStack(Descriptor.dataSize(str2));
    }

    public void addGetstatic(String str, String str2, String str3) {
        add(178);
        addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(str), str2, str3));
        growStack(Descriptor.dataSize(str3));
    }

    public void addInvokespecial(CtClass ctClass, String str, CtClass ctClass2, CtClass[] ctClassArr) {
        addInvokespecial(ctClass, str, Descriptor.ofMethod(ctClass2, ctClassArr));
    }

    public void addInvokespecial(CtClass ctClass, String str, String str2) {
        addInvokespecial(ctClass == null ? false : ctClass.isInterface(), this.constPool.addClassInfo(ctClass), str, str2);
    }

    public void addInvokespecial(String str, String str2, String str3) {
        addInvokespecial(false, this.constPool.addClassInfo(str), str2, str3);
    }

    public void addInvokespecial(int i, String str, String str2) {
        addInvokespecial(false, i, str, str2);
    }

    public void addInvokespecial(boolean z, int i, String str, String str2) {
        int addMethodrefInfo;
        if (z) {
            addMethodrefInfo = this.constPool.addInterfaceMethodrefInfo(i, str, str2);
        } else {
            addMethodrefInfo = this.constPool.addMethodrefInfo(i, str, str2);
        }
        addInvokespecial(addMethodrefInfo, str2);
    }

    public void addInvokespecial(int i, String str) {
        add(183);
        addIndex(i);
        growStack(Descriptor.dataSize(str) - 1);
    }

    public void addInvokestatic(CtClass ctClass, String str, CtClass ctClass2, CtClass[] ctClassArr) {
        addInvokestatic(ctClass, str, Descriptor.ofMethod(ctClass2, ctClassArr));
    }

    public void addInvokestatic(CtClass ctClass, String str, String str2) {
        addInvokestatic(this.constPool.addClassInfo(ctClass), str, str2, ctClass == THIS ? false : ctClass.isInterface());
    }

    public void addInvokestatic(String str, String str2, String str3) {
        addInvokestatic(this.constPool.addClassInfo(str), str2, str3);
    }

    public void addInvokestatic(int i, String str, String str2) {
        addInvokestatic(i, str, str2, false);
    }

    private void addInvokestatic(int i, String str, String str2, boolean z) {
        int addMethodrefInfo;
        add(184);
        if (z) {
            addMethodrefInfo = this.constPool.addInterfaceMethodrefInfo(i, str, str2);
        } else {
            addMethodrefInfo = this.constPool.addMethodrefInfo(i, str, str2);
        }
        addIndex(addMethodrefInfo);
        growStack(Descriptor.dataSize(str2));
    }

    public void addInvokevirtual(CtClass ctClass, String str, CtClass ctClass2, CtClass[] ctClassArr) {
        addInvokevirtual(ctClass, str, Descriptor.ofMethod(ctClass2, ctClassArr));
    }

    public void addInvokevirtual(CtClass ctClass, String str, String str2) {
        addInvokevirtual(this.constPool.addClassInfo(ctClass), str, str2);
    }

    public void addInvokevirtual(String str, String str2, String str3) {
        addInvokevirtual(this.constPool.addClassInfo(str), str2, str3);
    }

    public void addInvokevirtual(int i, String str, String str2) {
        add(182);
        addIndex(this.constPool.addMethodrefInfo(i, str, str2));
        growStack(Descriptor.dataSize(str2) - 1);
    }

    public void addInvokeinterface(CtClass ctClass, String str, CtClass ctClass2, CtClass[] ctClassArr, int i) {
        addInvokeinterface(ctClass, str, Descriptor.ofMethod(ctClass2, ctClassArr), i);
    }

    public void addInvokeinterface(CtClass ctClass, String str, String str2, int i) {
        addInvokeinterface(this.constPool.addClassInfo(ctClass), str, str2, i);
    }

    public void addInvokeinterface(String str, String str2, String str3, int i) {
        addInvokeinterface(this.constPool.addClassInfo(str), str2, str3, i);
    }

    public void addInvokeinterface(int i, String str, String str2, int i2) {
        add(185);
        addIndex(this.constPool.addInterfaceMethodrefInfo(i, str, str2));
        add(i2);
        add(0);
        growStack(Descriptor.dataSize(str2) - 1);
    }

    public void addInvokedynamic(int i, String str, String str2) {
        int addInvokeDynamicInfo = this.constPool.addInvokeDynamicInfo(i, this.constPool.addNameAndTypeInfo(str, str2));
        add(186);
        addIndex(addInvokeDynamicInfo);
        add(0, 0);
        growStack(Descriptor.dataSize(str2));
    }

    public void addLdc(String str) {
        addLdc(this.constPool.addStringInfo(str));
    }

    public void addLdc(int i) {
        if (i > 255) {
            addOpcode(19);
            addIndex(i);
            return;
        }
        addOpcode(18);
        add(i);
    }

    public void addLdc2w(long j) {
        addOpcode(20);
        addIndex(this.constPool.addLongInfo(j));
    }

    public void addLdc2w(double d) {
        addOpcode(20);
        addIndex(this.constPool.addDoubleInfo(d));
    }

    public void addNew(CtClass ctClass) {
        addOpcode(187);
        addIndex(this.constPool.addClassInfo(ctClass));
    }

    public void addNew(String str) {
        addOpcode(187);
        addIndex(this.constPool.addClassInfo(str));
    }

    public void addAnewarray(String str) {
        addOpcode(189);
        addIndex(this.constPool.addClassInfo(str));
    }

    public void addAnewarray(CtClass ctClass, int i) {
        addIconst(i);
        addOpcode(189);
        addIndex(this.constPool.addClassInfo(ctClass));
    }

    public void addNewarray(int i, int i2) {
        addIconst(i2);
        addOpcode(188);
        add(i);
    }

    public int addMultiNewarray(CtClass ctClass, int[] iArr) {
        int length = iArr.length;
        for (int i : iArr) {
            addIconst(i);
        }
        growStack(length);
        return addMultiNewarray(ctClass, length);
    }

    public int addMultiNewarray(CtClass ctClass, int i) {
        add(197);
        addIndex(this.constPool.addClassInfo(ctClass));
        add(i);
        growStack(1 - i);
        return i;
    }

    public int addMultiNewarray(String str, int i) {
        add(197);
        addIndex(this.constPool.addClassInfo(str));
        add(i);
        growStack(1 - i);
        return i;
    }

    public void addPutfield(CtClass ctClass, String str, String str2) {
        addPutfield0(ctClass, null, str, str2);
    }

    public void addPutfield(String str, String str2, String str3) {
        addPutfield0(null, str, str2, str3);
    }

    private void addPutfield0(CtClass ctClass, String str, String str2, String str3) {
        int addClassInfo;
        add(181);
        if (str == null) {
            addClassInfo = this.constPool.addClassInfo(ctClass);
        } else {
            addClassInfo = this.constPool.addClassInfo(str);
        }
        addIndex(this.constPool.addFieldrefInfo(addClassInfo, str2, str3));
        growStack((-1) - Descriptor.dataSize(str3));
    }

    public void addPutstatic(CtClass ctClass, String str, String str2) {
        addPutstatic0(ctClass, null, str, str2);
    }

    public void addPutstatic(String str, String str2, String str3) {
        addPutstatic0(null, str, str2, str3);
    }

    private void addPutstatic0(CtClass ctClass, String str, String str2, String str3) {
        int addClassInfo;
        add(179);
        if (str == null) {
            addClassInfo = this.constPool.addClassInfo(ctClass);
        } else {
            addClassInfo = this.constPool.addClassInfo(str);
        }
        addIndex(this.constPool.addFieldrefInfo(addClassInfo, str2, str3));
        growStack(-Descriptor.dataSize(str3));
    }

    public void addReturn(CtClass ctClass) {
        if (ctClass == null) {
            addOpcode(177);
        } else if (ctClass.isPrimitive()) {
            addOpcode(((CtPrimitiveType) ctClass).getReturnOp());
        } else {
            addOpcode(176);
        }
    }

    public void addRet(int i) {
        if (i < 256) {
            addOpcode(169);
            add(i);
            return;
        }
        addOpcode(Opcode.WIDE);
        addOpcode(169);
        addIndex(i);
    }

    public void addPrintln(String str) {
        addGetstatic("java.lang.System", NotificationCompat.CATEGORY_ERROR, "Ljava/io/PrintStream;");
        addLdc(str);
        addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
    }
}
