package javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class FieldInfo {
    int accessFlags;
    List<AttributeInfo> attribute;
    String cachedName;
    String cachedType;
    ConstPool constPool;
    int descriptor;
    int name;

    private FieldInfo(ConstPool constPool) {
        this.constPool = constPool;
        this.accessFlags = 0;
        this.attribute = null;
    }

    public FieldInfo(ConstPool constPool, String str, String str2) {
        this(constPool);
        this.name = constPool.addUtf8Info(str);
        this.cachedName = str;
        this.descriptor = constPool.addUtf8Info(str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FieldInfo(ConstPool constPool, DataInputStream dataInputStream) throws IOException {
        this(constPool);
        read(dataInputStream);
    }

    public String toString() {
        return getName() + " " + getDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void compact(ConstPool constPool) {
        this.name = constPool.addUtf8Info(getName());
        this.descriptor = constPool.addUtf8Info(getDescriptor());
        this.attribute = AttributeInfo.copyAll(this.attribute, constPool);
        this.constPool = constPool;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void prune(ConstPool constPool) {
        ArrayList arrayList = new ArrayList();
        AttributeInfo attribute = getAttribute(AnnotationsAttribute.invisibleTag);
        if (attribute != null) {
            arrayList.add(attribute.copy(constPool, null));
        }
        AttributeInfo attribute2 = getAttribute(AnnotationsAttribute.visibleTag);
        if (attribute2 != null) {
            arrayList.add(attribute2.copy(constPool, null));
        }
        AttributeInfo attribute3 = getAttribute(SignatureAttribute.tag);
        if (attribute3 != null) {
            arrayList.add(attribute3.copy(constPool, null));
        }
        int constantValue = getConstantValue();
        if (constantValue != 0) {
            arrayList.add(new ConstantAttribute(constPool, this.constPool.copy(constantValue, constPool, null)));
        }
        this.attribute = arrayList;
        this.name = constPool.addUtf8Info(getName());
        this.descriptor = constPool.addUtf8Info(getDescriptor());
        this.constPool = constPool;
    }

    public ConstPool getConstPool() {
        return this.constPool;
    }

    public String getName() {
        if (this.cachedName == null) {
            this.cachedName = this.constPool.getUtf8Info(this.name);
        }
        return this.cachedName;
    }

    public void setName(String str) {
        this.name = this.constPool.addUtf8Info(str);
        this.cachedName = str;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int i) {
        this.accessFlags = i;
    }

    public String getDescriptor() {
        return this.constPool.getUtf8Info(this.descriptor);
    }

    public void setDescriptor(String str) {
        if (str.equals(getDescriptor())) {
            return;
        }
        this.descriptor = this.constPool.addUtf8Info(str);
    }

    public int getConstantValue() {
        ConstantAttribute constantAttribute;
        if ((this.accessFlags & 8) == 0 || (constantAttribute = (ConstantAttribute) getAttribute(ConstantAttribute.tag)) == null) {
            return 0;
        }
        return constantAttribute.getConstantValue();
    }

    public List<AttributeInfo> getAttributes() {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        return this.attribute;
    }

    public AttributeInfo getAttribute(String str) {
        return AttributeInfo.lookup(this.attribute, str);
    }

    public AttributeInfo removeAttribute(String str) {
        return AttributeInfo.remove(this.attribute, str);
    }

    public void addAttribute(AttributeInfo attributeInfo) {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        AttributeInfo.remove(this.attribute, attributeInfo.getName());
        this.attribute.add(attributeInfo);
    }

    private void read(DataInputStream dataInputStream) throws IOException {
        this.accessFlags = dataInputStream.readUnsignedShort();
        this.name = dataInputStream.readUnsignedShort();
        this.descriptor = dataInputStream.readUnsignedShort();
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        this.attribute = new ArrayList();
        for (int i = 0; i < readUnsignedShort; i++) {
            this.attribute.add(AttributeInfo.read(this.constPool, dataInputStream));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.accessFlags);
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeShort(this.descriptor);
        List<AttributeInfo> list = this.attribute;
        if (list == null) {
            dataOutputStream.writeShort(0);
            return;
        }
        dataOutputStream.writeShort(list.size());
        AttributeInfo.writeAll(this.attribute, dataOutputStream);
    }
}
