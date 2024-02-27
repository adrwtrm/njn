package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javassist.CtClass;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;

/* loaded from: classes2.dex */
public class SignatureAttribute extends AttributeInfo {
    public static final String tag = "Signature";

    private static boolean isNamePart(int i) {
        return (i == 59 || i == 60) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SignatureAttribute(ConstPool constPool, int i, DataInputStream dataInputStream) throws IOException {
        super(constPool, i, dataInputStream);
    }

    public SignatureAttribute(ConstPool constPool, String str) {
        super(constPool, tag);
        int addUtf8Info = constPool.addUtf8Info(str);
        set(new byte[]{(byte) (addUtf8Info >>> 8), (byte) addUtf8Info});
    }

    public String getSignature() {
        return getConstPool().getUtf8Info(ByteArray.readU16bit(get(), 0));
    }

    public void setSignature(String str) {
        ByteArray.write16bit(getConstPool().addUtf8Info(str), this.info, 0);
    }

    @Override // javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool constPool, Map<String, String> map) {
        return new SignatureAttribute(constPool, getSignature());
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(String str, String str2) {
        setSignature(renameClass(getSignature(), str, str2));
    }

    @Override // javassist.bytecode.AttributeInfo
    void renameClass(Map<String, String> map) {
        setSignature(renameClass(getSignature(), map));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String renameClass(String str, String str2, String str3) {
        HashMap hashMap = new HashMap();
        hashMap.put(str2, str3);
        return renameClass(str, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String renameClass(String str, Map<String, String> map) {
        char charAt;
        char charAt2;
        if (map == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        while (true) {
            int indexOf = str.indexOf(76, i);
            if (indexOf < 0) {
                break;
            }
            StringBuilder sb2 = new StringBuilder();
            int i3 = indexOf;
            while (true) {
                i3++;
                try {
                    charAt = str.charAt(i3);
                    if (charAt == ';') {
                        break;
                    }
                    sb2.append(charAt);
                    if (charAt == '<') {
                        while (true) {
                            i3++;
                            charAt2 = str.charAt(i3);
                            if (charAt2 == '>') {
                                break;
                            }
                            sb2.append(charAt2);
                        }
                        sb2.append(charAt2);
                    }
                } catch (IndexOutOfBoundsException unused) {
                }
            }
            int i4 = i3 + 1;
            String str2 = map.get(sb2.toString());
            if (str2 != null) {
                sb.append(str.substring(i2, indexOf));
                sb.append('L');
                sb.append(str2);
                sb.append(charAt);
                i2 = i4;
            }
            i = i4;
        }
        if (i2 == 0) {
            return str;
        }
        int length = str.length();
        if (i2 < length) {
            sb.append(str.substring(i2, length));
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Cursor {
        int position;

        private Cursor() {
            this.position = 0;
        }

        int indexOf(String str, int i) throws BadBytecode {
            int indexOf = str.indexOf(i, this.position);
            if (indexOf < 0) {
                throw SignatureAttribute.error(str);
            }
            this.position = indexOf + 1;
            return indexOf;
        }
    }

    /* loaded from: classes2.dex */
    public static class ClassSignature {
        ClassType[] interfaces;
        TypeParameter[] params;
        ClassType superClass;

        public ClassSignature(TypeParameter[] typeParameterArr, ClassType classType, ClassType[] classTypeArr) {
            this.params = typeParameterArr == null ? new TypeParameter[0] : typeParameterArr;
            this.superClass = classType == null ? ClassType.OBJECT : classType;
            this.interfaces = classTypeArr == null ? new ClassType[0] : classTypeArr;
        }

        public ClassSignature(TypeParameter[] typeParameterArr) {
            this(typeParameterArr, null, null);
        }

        public TypeParameter[] getParameters() {
            return this.params;
        }

        public ClassType getSuperClass() {
            return this.superClass;
        }

        public ClassType[] getInterfaces() {
            return this.interfaces;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            TypeParameter.toString(stringBuffer, this.params);
            stringBuffer.append(" extends ").append(this.superClass);
            if (this.interfaces.length > 0) {
                stringBuffer.append(" implements ");
                Type.toString(stringBuffer, this.interfaces);
            }
            return stringBuffer.toString();
        }

        public String encode() {
            StringBuffer stringBuffer = new StringBuffer();
            int i = 0;
            if (this.params.length > 0) {
                stringBuffer.append(Typography.less);
                int i2 = 0;
                while (true) {
                    TypeParameter[] typeParameterArr = this.params;
                    if (i2 >= typeParameterArr.length) {
                        break;
                    }
                    typeParameterArr[i2].encode(stringBuffer);
                    i2++;
                }
                stringBuffer.append(Typography.greater);
            }
            this.superClass.encode(stringBuffer);
            while (true) {
                ClassType[] classTypeArr = this.interfaces;
                if (i < classTypeArr.length) {
                    classTypeArr[i].encode(stringBuffer);
                    i++;
                } else {
                    return stringBuffer.toString();
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class MethodSignature {
        ObjectType[] exceptions;
        Type[] params;
        Type retType;
        TypeParameter[] typeParams;

        public MethodSignature(TypeParameter[] typeParameterArr, Type[] typeArr, Type type, ObjectType[] objectTypeArr) {
            this.typeParams = typeParameterArr == null ? new TypeParameter[0] : typeParameterArr;
            this.params = typeArr == null ? new Type[0] : typeArr;
            this.retType = type == null ? new BaseType("void") : type;
            this.exceptions = objectTypeArr == null ? new ObjectType[0] : objectTypeArr;
        }

        public TypeParameter[] getTypeParameters() {
            return this.typeParams;
        }

        public Type[] getParameterTypes() {
            return this.params;
        }

        public Type getReturnType() {
            return this.retType;
        }

        public ObjectType[] getExceptionTypes() {
            return this.exceptions;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            TypeParameter.toString(stringBuffer, this.typeParams);
            stringBuffer.append(" (");
            Type.toString(stringBuffer, this.params);
            stringBuffer.append(") ");
            stringBuffer.append(this.retType);
            if (this.exceptions.length > 0) {
                stringBuffer.append(" throws ");
                Type.toString(stringBuffer, this.exceptions);
            }
            return stringBuffer.toString();
        }

        public String encode() {
            StringBuffer stringBuffer = new StringBuffer();
            if (this.typeParams.length > 0) {
                stringBuffer.append(Typography.less);
                int i = 0;
                while (true) {
                    TypeParameter[] typeParameterArr = this.typeParams;
                    if (i >= typeParameterArr.length) {
                        break;
                    }
                    typeParameterArr[i].encode(stringBuffer);
                    i++;
                }
                stringBuffer.append(Typography.greater);
            }
            stringBuffer.append('(');
            int i2 = 0;
            while (true) {
                Type[] typeArr = this.params;
                if (i2 >= typeArr.length) {
                    break;
                }
                typeArr[i2].encode(stringBuffer);
                i2++;
            }
            stringBuffer.append(')');
            this.retType.encode(stringBuffer);
            if (this.exceptions.length > 0) {
                for (int i3 = 0; i3 < this.exceptions.length; i3++) {
                    stringBuffer.append('^');
                    this.exceptions[i3].encode(stringBuffer);
                }
            }
            return stringBuffer.toString();
        }
    }

    /* loaded from: classes2.dex */
    public static class TypeParameter {
        String name;
        ObjectType superClass;
        ObjectType[] superInterfaces;

        TypeParameter(String str, int i, int i2, ObjectType objectType, ObjectType[] objectTypeArr) {
            this.name = str.substring(i, i2);
            this.superClass = objectType;
            this.superInterfaces = objectTypeArr;
        }

        public TypeParameter(String str, ObjectType objectType, ObjectType[] objectTypeArr) {
            this.name = str;
            this.superClass = objectType;
            if (objectTypeArr == null) {
                this.superInterfaces = new ObjectType[0];
            } else {
                this.superInterfaces = objectTypeArr;
            }
        }

        public TypeParameter(String str) {
            this(str, null, null);
        }

        public String getName() {
            return this.name;
        }

        public ObjectType getClassBound() {
            return this.superClass;
        }

        public ObjectType[] getInterfaceBound() {
            return this.superInterfaces;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(getName());
            if (this.superClass != null) {
                stringBuffer.append(" extends ").append(this.superClass.toString());
            }
            int length = this.superInterfaces.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    if (i > 0 || this.superClass != null) {
                        stringBuffer.append(" & ");
                    } else {
                        stringBuffer.append(" extends ");
                    }
                    stringBuffer.append(this.superInterfaces[i].toString());
                }
            }
            return stringBuffer.toString();
        }

        static void toString(StringBuffer stringBuffer, TypeParameter[] typeParameterArr) {
            stringBuffer.append(Typography.less);
            for (int i = 0; i < typeParameterArr.length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(typeParameterArr[i]);
            }
            stringBuffer.append(Typography.greater);
        }

        void encode(StringBuffer stringBuffer) {
            stringBuffer.append(this.name);
            if (this.superClass == null) {
                stringBuffer.append(":Ljava/lang/Object;");
            } else {
                stringBuffer.append(':');
                this.superClass.encode(stringBuffer);
            }
            for (int i = 0; i < this.superInterfaces.length; i++) {
                stringBuffer.append(':');
                this.superInterfaces[i].encode(stringBuffer);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class TypeArgument {
        ObjectType arg;
        char wildcard;

        TypeArgument(ObjectType objectType, char c) {
            this.arg = objectType;
            this.wildcard = c;
        }

        public TypeArgument(ObjectType objectType) {
            this(objectType, ' ');
        }

        public TypeArgument() {
            this(null, '*');
        }

        public static TypeArgument subclassOf(ObjectType objectType) {
            return new TypeArgument(objectType, SignatureVisitor.EXTENDS);
        }

        public static TypeArgument superOf(ObjectType objectType) {
            return new TypeArgument(objectType, SignatureVisitor.SUPER);
        }

        public char getKind() {
            return this.wildcard;
        }

        public boolean isWildcard() {
            return this.wildcard != ' ';
        }

        public ObjectType getType() {
            return this.arg;
        }

        public String toString() {
            if (this.wildcard == '*') {
                return "?";
            }
            String obj = this.arg.toString();
            char c = this.wildcard;
            if (c == ' ') {
                return obj;
            }
            if (c == '+') {
                return "? extends " + obj;
            }
            return "? super " + obj;
        }

        static void encode(StringBuffer stringBuffer, TypeArgument[] typeArgumentArr) {
            stringBuffer.append(Typography.less);
            for (TypeArgument typeArgument : typeArgumentArr) {
                if (typeArgument.isWildcard()) {
                    stringBuffer.append(typeArgument.wildcard);
                }
                if (typeArgument.getType() != null) {
                    typeArgument.getType().encode(stringBuffer);
                }
            }
            stringBuffer.append(Typography.greater);
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class Type {
        abstract void encode(StringBuffer stringBuffer);

        static void toString(StringBuffer stringBuffer, Type[] typeArr) {
            for (int i = 0; i < typeArr.length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(typeArr[i]);
            }
        }

        public String jvmTypeName() {
            return toString();
        }
    }

    /* loaded from: classes2.dex */
    public static class BaseType extends Type {
        char descriptor;

        BaseType(char c) {
            this.descriptor = c;
        }

        public BaseType(String str) {
            this(Descriptor.of(str).charAt(0));
        }

        public char getDescriptor() {
            return this.descriptor;
        }

        public CtClass getCtlass() {
            return Descriptor.toPrimitiveClass(this.descriptor);
        }

        public String toString() {
            return Descriptor.toClassName(Character.toString(this.descriptor));
        }

        @Override // javassist.bytecode.SignatureAttribute.Type
        void encode(StringBuffer stringBuffer) {
            stringBuffer.append(this.descriptor);
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class ObjectType extends Type {
        public String encode() {
            StringBuffer stringBuffer = new StringBuffer();
            encode(stringBuffer);
            return stringBuffer.toString();
        }
    }

    /* loaded from: classes2.dex */
    public static class ClassType extends ObjectType {
        public static ClassType OBJECT = new ClassType("java.lang.Object", null);
        TypeArgument[] arguments;
        String name;

        public ClassType getDeclaringClass() {
            return null;
        }

        static ClassType make(String str, int i, int i2, TypeArgument[] typeArgumentArr, ClassType classType) {
            if (classType == null) {
                return new ClassType(str, i, i2, typeArgumentArr);
            }
            return new NestedClassType(str, i, i2, typeArgumentArr, classType);
        }

        ClassType(String str, int i, int i2, TypeArgument[] typeArgumentArr) {
            this.name = str.substring(i, i2).replace('/', '.');
            this.arguments = typeArgumentArr;
        }

        public ClassType(String str, TypeArgument[] typeArgumentArr) {
            this.name = str;
            this.arguments = typeArgumentArr;
        }

        public ClassType(String str) {
            this(str, null);
        }

        public String getName() {
            return this.name;
        }

        public TypeArgument[] getTypeArguments() {
            return this.arguments;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            ClassType declaringClass = getDeclaringClass();
            if (declaringClass != null) {
                stringBuffer.append(declaringClass.toString()).append('.');
            }
            return toString2(stringBuffer);
        }

        private String toString2(StringBuffer stringBuffer) {
            stringBuffer.append(this.name);
            if (this.arguments != null) {
                stringBuffer.append(Typography.less);
                int length = this.arguments.length;
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append(this.arguments[i].toString());
                }
                stringBuffer.append(Typography.greater);
            }
            return stringBuffer.toString();
        }

        @Override // javassist.bytecode.SignatureAttribute.Type
        public String jvmTypeName() {
            StringBuffer stringBuffer = new StringBuffer();
            ClassType declaringClass = getDeclaringClass();
            if (declaringClass != null) {
                stringBuffer.append(declaringClass.jvmTypeName()).append(Typography.dollar);
            }
            return toString2(stringBuffer);
        }

        @Override // javassist.bytecode.SignatureAttribute.Type
        void encode(StringBuffer stringBuffer) {
            stringBuffer.append('L');
            encode2(stringBuffer);
            stringBuffer.append(';');
        }

        void encode2(StringBuffer stringBuffer) {
            ClassType declaringClass = getDeclaringClass();
            if (declaringClass != null) {
                declaringClass.encode2(stringBuffer);
                stringBuffer.append(Typography.dollar);
            }
            stringBuffer.append(this.name.replace('.', '/'));
            TypeArgument[] typeArgumentArr = this.arguments;
            if (typeArgumentArr != null) {
                TypeArgument.encode(stringBuffer, typeArgumentArr);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class NestedClassType extends ClassType {
        ClassType parent;

        NestedClassType(String str, int i, int i2, TypeArgument[] typeArgumentArr, ClassType classType) {
            super(str, i, i2, typeArgumentArr);
            this.parent = classType;
        }

        public NestedClassType(ClassType classType, String str, TypeArgument[] typeArgumentArr) {
            super(str, typeArgumentArr);
            this.parent = classType;
        }

        @Override // javassist.bytecode.SignatureAttribute.ClassType
        public ClassType getDeclaringClass() {
            return this.parent;
        }
    }

    /* loaded from: classes2.dex */
    public static class ArrayType extends ObjectType {
        Type componentType;
        int dim;

        public ArrayType(int i, Type type) {
            this.dim = i;
            this.componentType = type;
        }

        public int getDimension() {
            return this.dim;
        }

        public Type getComponentType() {
            return this.componentType;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(this.componentType.toString());
            for (int i = 0; i < this.dim; i++) {
                stringBuffer.append("[]");
            }
            return stringBuffer.toString();
        }

        @Override // javassist.bytecode.SignatureAttribute.Type
        void encode(StringBuffer stringBuffer) {
            for (int i = 0; i < this.dim; i++) {
                stringBuffer.append('[');
            }
            this.componentType.encode(stringBuffer);
        }
    }

    /* loaded from: classes2.dex */
    public static class TypeVariable extends ObjectType {
        String name;

        TypeVariable(String str, int i, int i2) {
            this.name = str.substring(i, i2);
        }

        public TypeVariable(String str) {
            this.name = str;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        @Override // javassist.bytecode.SignatureAttribute.Type
        void encode(StringBuffer stringBuffer) {
            stringBuffer.append('T').append(this.name).append(';');
        }
    }

    public static ClassSignature toClassSignature(String str) throws BadBytecode {
        try {
            return parseSig(str);
        } catch (IndexOutOfBoundsException unused) {
            throw error(str);
        }
    }

    public static MethodSignature toMethodSignature(String str) throws BadBytecode {
        try {
            return parseMethodSig(str);
        } catch (IndexOutOfBoundsException unused) {
            throw error(str);
        }
    }

    public static ObjectType toFieldSignature(String str) throws BadBytecode {
        try {
            return parseObjectType(str, new Cursor(), false);
        } catch (IndexOutOfBoundsException unused) {
            throw error(str);
        }
    }

    public static Type toTypeSignature(String str) throws BadBytecode {
        try {
            return parseType(str, new Cursor());
        } catch (IndexOutOfBoundsException unused) {
            throw error(str);
        }
    }

    private static ClassSignature parseSig(String str) throws BadBytecode, IndexOutOfBoundsException {
        Cursor cursor = new Cursor();
        TypeParameter[] parseTypeParams = parseTypeParams(str, cursor);
        ClassType parseClassType = parseClassType(str, cursor);
        int length = str.length();
        ArrayList arrayList = new ArrayList();
        while (cursor.position < length && str.charAt(cursor.position) == 'L') {
            arrayList.add(parseClassType(str, cursor));
        }
        return new ClassSignature(parseTypeParams, parseClassType, (ClassType[]) arrayList.toArray(new ClassType[arrayList.size()]));
    }

    private static MethodSignature parseMethodSig(String str) throws BadBytecode {
        Cursor cursor = new Cursor();
        TypeParameter[] parseTypeParams = parseTypeParams(str, cursor);
        int i = cursor.position;
        cursor.position = i + 1;
        if (str.charAt(i) != '(') {
            throw error(str);
        }
        ArrayList arrayList = new ArrayList();
        while (str.charAt(cursor.position) != ')') {
            arrayList.add(parseType(str, cursor));
        }
        cursor.position++;
        Type parseType = parseType(str, cursor);
        int length = str.length();
        ArrayList arrayList2 = new ArrayList();
        while (cursor.position < length && str.charAt(cursor.position) == '^') {
            cursor.position++;
            ObjectType parseObjectType = parseObjectType(str, cursor, false);
            if (parseObjectType instanceof ArrayType) {
                throw error(str);
            }
            arrayList2.add(parseObjectType);
        }
        return new MethodSignature(parseTypeParams, (Type[]) arrayList.toArray(new Type[arrayList.size()]), parseType, (ObjectType[]) arrayList2.toArray(new ObjectType[arrayList2.size()]));
    }

    private static TypeParameter[] parseTypeParams(String str, Cursor cursor) throws BadBytecode {
        ArrayList arrayList = new ArrayList();
        if (str.charAt(cursor.position) == '<') {
            cursor.position++;
            while (str.charAt(cursor.position) != '>') {
                int i = cursor.position;
                int indexOf = cursor.indexOf(str, 58);
                ObjectType parseObjectType = parseObjectType(str, cursor, true);
                ArrayList arrayList2 = new ArrayList();
                while (str.charAt(cursor.position) == ':') {
                    cursor.position++;
                    arrayList2.add(parseObjectType(str, cursor, false));
                }
                arrayList.add(new TypeParameter(str, i, indexOf, parseObjectType, (ObjectType[]) arrayList2.toArray(new ObjectType[arrayList2.size()])));
            }
            cursor.position++;
        }
        return (TypeParameter[]) arrayList.toArray(new TypeParameter[arrayList.size()]);
    }

    private static ObjectType parseObjectType(String str, Cursor cursor, boolean z) throws BadBytecode {
        int i = cursor.position;
        char charAt = str.charAt(i);
        if (charAt != 'L') {
            if (charAt == 'T') {
                return new TypeVariable(str, i + 1, cursor.indexOf(str, 59));
            } else if (charAt != '[') {
                if (z) {
                    return null;
                }
                throw error(str);
            } else {
                return parseArray(str, cursor);
            }
        }
        return parseClassType2(str, cursor, null);
    }

    private static ClassType parseClassType(String str, Cursor cursor) throws BadBytecode {
        if (str.charAt(cursor.position) == 'L') {
            return parseClassType2(str, cursor, null);
        }
        throw error(str);
    }

    private static ClassType parseClassType2(String str, Cursor cursor, ClassType classType) throws BadBytecode {
        char charAt;
        char c;
        TypeArgument[] typeArgumentArr;
        int i = cursor.position + 1;
        cursor.position = i;
        do {
            int i2 = cursor.position;
            cursor.position = i2 + 1;
            charAt = str.charAt(i2);
            if (charAt == '$' || charAt == '<') {
                break;
            }
        } while (charAt != ';');
        int i3 = cursor.position - 1;
        if (charAt == '<') {
            typeArgumentArr = parseTypeArgs(str, cursor);
            int i4 = cursor.position;
            cursor.position = i4 + 1;
            c = str.charAt(i4);
        } else {
            c = charAt;
            typeArgumentArr = null;
        }
        ClassType make = ClassType.make(str, i, i3, typeArgumentArr, classType);
        if (c == '$' || c == '.') {
            cursor.position--;
            return parseClassType2(str, cursor, make);
        }
        return make;
    }

    private static TypeArgument[] parseTypeArgs(String str, Cursor cursor) throws BadBytecode {
        TypeArgument typeArgument;
        ArrayList arrayList = new ArrayList();
        while (true) {
            int i = cursor.position;
            cursor.position = i + 1;
            char charAt = str.charAt(i);
            if (charAt != '>') {
                if (charAt == '*') {
                    typeArgument = new TypeArgument(null, '*');
                } else {
                    if (charAt != '+' && charAt != '-') {
                        cursor.position--;
                        charAt = ' ';
                    }
                    typeArgument = new TypeArgument(parseObjectType(str, cursor, false), charAt);
                }
                arrayList.add(typeArgument);
            } else {
                return (TypeArgument[]) arrayList.toArray(new TypeArgument[arrayList.size()]);
            }
        }
    }

    private static ObjectType parseArray(String str, Cursor cursor) throws BadBytecode {
        int i = 1;
        while (true) {
            int i2 = cursor.position + 1;
            cursor.position = i2;
            if (str.charAt(i2) != '[') {
                return new ArrayType(i, parseType(str, cursor));
            }
            i++;
        }
    }

    private static Type parseType(String str, Cursor cursor) throws BadBytecode {
        ObjectType parseObjectType = parseObjectType(str, cursor, true);
        if (parseObjectType == null) {
            int i = cursor.position;
            cursor.position = i + 1;
            return new BaseType(str.charAt(i));
        }
        return parseObjectType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BadBytecode error(String str) {
        return new BadBytecode("bad signature: " + str);
    }
}
