package javassist.bytecode.analysis;

import java.util.HashMap;
import java.util.Map;
import javassist.CtClass;

/* loaded from: classes2.dex */
public class MultiType extends Type {
    private boolean changed;
    private Map<String, CtClass> interfaces;
    private MultiType mergeSource;
    private Type potentialClass;
    private Type resolved;

    @Override // javassist.bytecode.analysis.Type
    public Type getComponent() {
        return null;
    }

    @Override // javassist.bytecode.analysis.Type
    public int getSize() {
        return 1;
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean isArray() {
        return false;
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean isReference() {
        return true;
    }

    public MultiType(Map<String, CtClass> map) {
        this(map, null);
    }

    public MultiType(Map<String, CtClass> map, Type type) {
        super(null);
        this.changed = false;
        this.interfaces = map;
        this.potentialClass = type;
    }

    @Override // javassist.bytecode.analysis.Type
    public CtClass getCtClass() {
        Type type = this.resolved;
        if (type != null) {
            return type.getCtClass();
        }
        return Type.OBJECT.getCtClass();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // javassist.bytecode.analysis.Type
    public boolean popChanged() {
        boolean z = this.changed;
        this.changed = false;
        return z;
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean isAssignableFrom(Type type) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean isAssignableTo(Type type) {
        Type type2 = this.resolved;
        if (type2 != null) {
            return type.isAssignableFrom(type2);
        }
        if (Type.OBJECT.equals(type)) {
            return true;
        }
        Type type3 = this.potentialClass;
        if (type3 != null && !type.isAssignableFrom(type3)) {
            this.potentialClass = null;
        }
        Map<String, CtClass> mergeMultiAndSingle = mergeMultiAndSingle(this, type);
        if (mergeMultiAndSingle.size() == 1 && this.potentialClass == null) {
            this.resolved = Type.get(mergeMultiAndSingle.values().iterator().next());
            propogateResolved();
            return true;
        } else if (mergeMultiAndSingle.size() >= 1) {
            this.interfaces = mergeMultiAndSingle;
            propogateState();
            return true;
        } else {
            Type type4 = this.potentialClass;
            if (type4 != null) {
                this.resolved = type4;
                propogateResolved();
                return true;
            }
            return false;
        }
    }

    private void propogateState() {
        for (MultiType multiType = this.mergeSource; multiType != null; multiType = multiType.mergeSource) {
            multiType.interfaces = this.interfaces;
            multiType.potentialClass = this.potentialClass;
        }
    }

    private void propogateResolved() {
        for (MultiType multiType = this.mergeSource; multiType != null; multiType = multiType.mergeSource) {
            multiType.resolved = this.resolved;
        }
    }

    private Map<String, CtClass> getAllMultiInterfaces(MultiType multiType) {
        HashMap hashMap = new HashMap();
        for (CtClass ctClass : multiType.interfaces.values()) {
            hashMap.put(ctClass.getName(), ctClass);
            getAllInterfaces(ctClass, hashMap);
        }
        return hashMap;
    }

    private Map<String, CtClass> mergeMultiInterfaces(MultiType multiType, MultiType multiType2) {
        return findCommonInterfaces(getAllMultiInterfaces(multiType), getAllMultiInterfaces(multiType2));
    }

    private Map<String, CtClass> mergeMultiAndSingle(MultiType multiType, Type type) {
        return findCommonInterfaces(getAllMultiInterfaces(multiType), getAllInterfaces(type.getCtClass(), null));
    }

    private boolean inMergeSource(MultiType multiType) {
        while (multiType != null) {
            if (multiType == this) {
                return true;
            }
            multiType = multiType.mergeSource;
        }
        return false;
    }

    @Override // javassist.bytecode.analysis.Type
    public Type merge(Type type) {
        Map<String, CtClass> mergeMultiAndSingle;
        if (this == type || type == UNINIT) {
            return this;
        }
        if (type == BOGUS) {
            return BOGUS;
        }
        if (type == null) {
            return this;
        }
        Type type2 = this.resolved;
        if (type2 != null) {
            return type2.merge(type);
        }
        Type type3 = this.potentialClass;
        if (type3 != null) {
            Type merge = type3.merge(type);
            if (!merge.equals(this.potentialClass) || merge.popChanged()) {
                if (Type.OBJECT.equals(merge)) {
                    merge = null;
                }
                this.potentialClass = merge;
                this.changed = true;
            }
        }
        if (type instanceof MultiType) {
            MultiType multiType = (MultiType) type;
            Type type4 = multiType.resolved;
            if (type4 != null) {
                mergeMultiAndSingle = mergeMultiAndSingle(this, type4);
            } else {
                Map<String, CtClass> mergeMultiInterfaces = mergeMultiInterfaces(multiType, this);
                if (!inMergeSource(multiType)) {
                    this.mergeSource = multiType;
                }
                mergeMultiAndSingle = mergeMultiInterfaces;
            }
        } else {
            mergeMultiAndSingle = mergeMultiAndSingle(this, type);
        }
        if (mergeMultiAndSingle.size() > 1 || (mergeMultiAndSingle.size() == 1 && this.potentialClass != null)) {
            if (mergeMultiAndSingle.size() != this.interfaces.size()) {
                this.changed = true;
            } else if (!this.changed) {
                for (String str : mergeMultiAndSingle.keySet()) {
                    if (!this.interfaces.containsKey(str)) {
                        this.changed = true;
                    }
                }
            }
            this.interfaces = mergeMultiAndSingle;
            propogateState();
            return this;
        }
        if (mergeMultiAndSingle.size() == 1) {
            this.resolved = Type.get(mergeMultiAndSingle.values().iterator().next());
        } else {
            Type type5 = this.potentialClass;
            if (type5 != null) {
                this.resolved = type5;
            } else {
                this.resolved = OBJECT;
            }
        }
        propogateResolved();
        return this.resolved;
    }

    @Override // javassist.bytecode.analysis.Type
    public int hashCode() {
        Type type = this.resolved;
        if (type != null) {
            return type.hashCode();
        }
        return this.interfaces.keySet().hashCode();
    }

    @Override // javassist.bytecode.analysis.Type
    public boolean equals(Object obj) {
        if (obj instanceof MultiType) {
            MultiType multiType = (MultiType) obj;
            Type type = this.resolved;
            if (type != null) {
                return type.equals(multiType.resolved);
            }
            if (multiType.resolved != null) {
                return false;
            }
            return this.interfaces.keySet().equals(multiType.interfaces.keySet());
        }
        return false;
    }

    @Override // javassist.bytecode.analysis.Type
    public String toString() {
        Type type = this.resolved;
        if (type != null) {
            return type.toString();
        }
        StringBuffer stringBuffer = new StringBuffer("{");
        for (String str : this.interfaces.keySet()) {
            stringBuffer.append(str).append(", ");
        }
        if (this.potentialClass != null) {
            stringBuffer.append("*").append(this.potentialClass.toString());
        } else {
            stringBuffer.setLength(stringBuffer.length() - 2);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
