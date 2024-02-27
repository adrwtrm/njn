package javassist.bytecode.stackmap;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import javassist.bytecode.stackmap.TypeData;

/* loaded from: classes2.dex */
public interface TypeTag {
    public static final String TOP_TYPE = "*top*";
    public static final TypeData.BasicType TOP = new TypeData.BasicType(TOP_TYPE, 0, ' ');
    public static final TypeData.BasicType INTEGER = new TypeData.BasicType("int", 1, 'I');
    public static final TypeData.BasicType FLOAT = new TypeData.BasicType(TypedValues.Custom.S_FLOAT, 2, 'F');
    public static final TypeData.BasicType DOUBLE = new TypeData.BasicType("double", 3, 'D');
    public static final TypeData.BasicType LONG = new TypeData.BasicType("long", 4, 'J');
}
