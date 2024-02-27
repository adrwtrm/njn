package org.objectweb.asm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ModuleWriter extends ModuleVisitor {
    private final ByteVector exports;
    private int exportsCount;
    private int mainClassIndex;
    private final int moduleFlags;
    private final int moduleNameIndex;
    private final int moduleVersionIndex;
    private final ByteVector opens;
    private int opensCount;
    private int packageCount;
    private final ByteVector packageIndex;
    private final ByteVector provides;
    private int providesCount;
    private final ByteVector requires;
    private int requiresCount;
    private final SymbolTable symbolTable;
    private int usesCount;
    private final ByteVector usesIndex;

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitEnd() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ModuleWriter(SymbolTable symbolTable, int i, int i2, int i3) {
        super(Opcodes.ASM9);
        this.symbolTable = symbolTable;
        this.moduleNameIndex = i;
        this.moduleFlags = i2;
        this.moduleVersionIndex = i3;
        this.requires = new ByteVector();
        this.exports = new ByteVector();
        this.opens = new ByteVector();
        this.usesIndex = new ByteVector();
        this.provides = new ByteVector();
        this.packageIndex = new ByteVector();
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitMainClass(String str) {
        this.mainClassIndex = this.symbolTable.addConstantClass(str).index;
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitPackage(String str) {
        this.packageIndex.putShort(this.symbolTable.addConstantPackage(str).index);
        this.packageCount++;
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitRequire(String str, int i, String str2) {
        this.requires.putShort(this.symbolTable.addConstantModule(str).index).putShort(i).putShort(str2 == null ? 0 : this.symbolTable.addConstantUtf8(str2));
        this.requiresCount++;
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitExport(String str, int i, String... strArr) {
        this.exports.putShort(this.symbolTable.addConstantPackage(str).index).putShort(i);
        if (strArr == null) {
            this.exports.putShort(0);
        } else {
            this.exports.putShort(strArr.length);
            for (String str2 : strArr) {
                this.exports.putShort(this.symbolTable.addConstantModule(str2).index);
            }
        }
        this.exportsCount++;
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitOpen(String str, int i, String... strArr) {
        this.opens.putShort(this.symbolTable.addConstantPackage(str).index).putShort(i);
        if (strArr == null) {
            this.opens.putShort(0);
        } else {
            this.opens.putShort(strArr.length);
            for (String str2 : strArr) {
                this.opens.putShort(this.symbolTable.addConstantModule(str2).index);
            }
        }
        this.opensCount++;
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitUse(String str) {
        this.usesIndex.putShort(this.symbolTable.addConstantClass(str).index);
        this.usesCount++;
    }

    @Override // org.objectweb.asm.ModuleVisitor
    public void visitProvide(String str, String... strArr) {
        this.provides.putShort(this.symbolTable.addConstantClass(str).index);
        this.provides.putShort(strArr.length);
        for (String str2 : strArr) {
            this.provides.putShort(this.symbolTable.addConstantClass(str2).index);
        }
        this.providesCount++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAttributeCount() {
        return (this.packageCount > 0 ? 1 : 0) + 1 + (this.mainClassIndex > 0 ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int computeAttributesSize() {
        this.symbolTable.addConstantUtf8("Module");
        int i = this.requires.length + 22 + this.exports.length + this.opens.length + this.usesIndex.length + this.provides.length;
        if (this.packageCount > 0) {
            this.symbolTable.addConstantUtf8("ModulePackages");
            i += this.packageIndex.length + 8;
        }
        if (this.mainClassIndex > 0) {
            this.symbolTable.addConstantUtf8("ModuleMainClass");
            return i + 8;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void putAttributes(ByteVector byteVector) {
        byteVector.putShort(this.symbolTable.addConstantUtf8("Module")).putInt(this.requires.length + 16 + this.exports.length + this.opens.length + this.usesIndex.length + this.provides.length).putShort(this.moduleNameIndex).putShort(this.moduleFlags).putShort(this.moduleVersionIndex).putShort(this.requiresCount).putByteArray(this.requires.data, 0, this.requires.length).putShort(this.exportsCount).putByteArray(this.exports.data, 0, this.exports.length).putShort(this.opensCount).putByteArray(this.opens.data, 0, this.opens.length).putShort(this.usesCount).putByteArray(this.usesIndex.data, 0, this.usesIndex.length).putShort(this.providesCount).putByteArray(this.provides.data, 0, this.provides.length);
        if (this.packageCount > 0) {
            byteVector.putShort(this.symbolTable.addConstantUtf8("ModulePackages")).putInt(this.packageIndex.length + 2).putShort(this.packageCount).putByteArray(this.packageIndex.data, 0, this.packageIndex.length);
        }
        if (this.mainClassIndex > 0) {
            byteVector.putShort(this.symbolTable.addConstantUtf8("ModuleMainClass")).putInt(2).putShort(this.mainClassIndex);
        }
    }
}
