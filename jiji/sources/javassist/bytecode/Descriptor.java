package javassist.bytecode;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Map;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;

/* loaded from: classes2.dex */
public class Descriptor {
    public static String toJvmName(String str) {
        return str.replace('.', '/');
    }

    public static String toJavaName(String str) {
        return str.replace('/', '.');
    }

    public static String toJvmName(CtClass ctClass) {
        if (ctClass.isArray()) {
            return of(ctClass);
        }
        return toJvmName(ctClass.getName());
    }

    public static String toClassName(String str) {
        String str2;
        int i = 0;
        char charAt = str.charAt(0);
        int i2 = 0;
        while (charAt == '[') {
            i++;
            i2++;
            charAt = str.charAt(i2);
        }
        if (charAt == 'L') {
            int i3 = i2 + 1;
            i2 = str.indexOf(59, i2);
            str2 = str.substring(i3, i2).replace('/', '.');
        } else if (charAt == 'V') {
            str2 = "void";
        } else if (charAt == 'I') {
            str2 = "int";
        } else if (charAt == 'B') {
            str2 = "byte";
        } else if (charAt == 'J') {
            str2 = "long";
        } else if (charAt == 'D') {
            str2 = "double";
        } else if (charAt == 'F') {
            str2 = TypedValues.Custom.S_FLOAT;
        } else if (charAt == 'C') {
            str2 = "char";
        } else if (charAt == 'S') {
            str2 = "short";
        } else if (charAt != 'Z') {
            throw new RuntimeException("bad descriptor: " + str);
        } else {
            str2 = TypedValues.Custom.S_BOOLEAN;
        }
        if (i2 + 1 == str.length()) {
            if (i == 0) {
                return str2;
            }
            StringBuffer stringBuffer = new StringBuffer(str2);
            do {
                stringBuffer.append("[]");
                i--;
            } while (i > 0);
            return stringBuffer.toString();
        }
        throw new RuntimeException("multiple descriptors?: " + str);
    }

    public static String of(String str) {
        return str.equals("void") ? "V" : str.equals("int") ? "I" : str.equals("byte") ? "B" : str.equals("long") ? "J" : str.equals("double") ? "D" : str.equals(TypedValues.Custom.S_FLOAT) ? "F" : str.equals("char") ? "C" : str.equals("short") ? "S" : str.equals(TypedValues.Custom.S_BOOLEAN) ? "Z" : "L" + toJvmName(str) + ";";
    }

    public static String rename(String str, String str2, String str3) {
        int i;
        int indexOf;
        if (str.indexOf(str2) < 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i2 = 0;
        loop0: while (true) {
            i = i2;
            while (true) {
                indexOf = str.indexOf(76, i2);
                if (indexOf < 0) {
                    break loop0;
                } else if (!str.startsWith(str2, indexOf + 1) || str.charAt(str2.length() + indexOf + 1) != ';') {
                    i2 = str.indexOf(59, indexOf) + 1;
                    if (i2 < 1) {
                        break loop0;
                    }
                }
            }
            stringBuffer.append(str.substring(i, indexOf));
            stringBuffer.append('L');
            stringBuffer.append(str3);
            stringBuffer.append(';');
            i2 = indexOf + str2.length() + 2;
        }
        if (i == 0) {
            return str;
        }
        int length = str.length();
        if (i < length) {
            stringBuffer.append(str.substring(i, length));
        }
        return stringBuffer.toString();
    }

    public static String rename(String str, Map<String, String> map) {
        int indexOf;
        if (map == null) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        int i2 = 0;
        while (true) {
            int indexOf2 = str.indexOf(76, i);
            if (indexOf2 >= 0 && (indexOf = str.indexOf(59, indexOf2)) >= 0) {
                int i3 = indexOf + 1;
                String str2 = map.get(str.substring(indexOf2 + 1, indexOf));
                if (str2 != null) {
                    stringBuffer.append(str.substring(i2, indexOf2));
                    stringBuffer.append('L');
                    stringBuffer.append(str2);
                    stringBuffer.append(';');
                    i2 = i3;
                }
                i = i3;
            }
        }
        if (i2 == 0) {
            return str;
        }
        int length = str.length();
        if (i2 < length) {
            stringBuffer.append(str.substring(i2, length));
        }
        return stringBuffer.toString();
    }

    public static String of(CtClass ctClass) {
        StringBuffer stringBuffer = new StringBuffer();
        toDescriptor(stringBuffer, ctClass);
        return stringBuffer.toString();
    }

    private static void toDescriptor(StringBuffer stringBuffer, CtClass ctClass) {
        if (ctClass.isArray()) {
            stringBuffer.append('[');
            try {
                toDescriptor(stringBuffer, ctClass.getComponentType());
            } catch (NotFoundException unused) {
                stringBuffer.append('L');
                String name = ctClass.getName();
                stringBuffer.append(toJvmName(name.substring(0, name.length() - 2)));
                stringBuffer.append(';');
            }
        } else if (ctClass.isPrimitive()) {
            stringBuffer.append(((CtPrimitiveType) ctClass).getDescriptor());
        } else {
            stringBuffer.append('L');
            stringBuffer.append(ctClass.getName().replace('.', '/'));
            stringBuffer.append(';');
        }
    }

    public static String ofConstructor(CtClass[] ctClassArr) {
        return ofMethod(CtClass.voidType, ctClassArr);
    }

    public static String ofMethod(CtClass ctClass, CtClass[] ctClassArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        if (ctClassArr != null) {
            for (CtClass ctClass2 : ctClassArr) {
                toDescriptor(stringBuffer, ctClass2);
            }
        }
        stringBuffer.append(')');
        if (ctClass != null) {
            toDescriptor(stringBuffer, ctClass);
        }
        return stringBuffer.toString();
    }

    public static String ofParameters(CtClass[] ctClassArr) {
        return ofMethod(null, ctClassArr);
    }

    public static String appendParameter(String str, String str2) {
        int indexOf = str2.indexOf(41);
        if (indexOf < 0) {
            return str2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2.substring(0, indexOf));
        stringBuffer.append('L');
        stringBuffer.append(str.replace('.', '/'));
        stringBuffer.append(';');
        stringBuffer.append(str2.substring(indexOf));
        return stringBuffer.toString();
    }

    public static String insertParameter(String str, String str2) {
        return str2.charAt(0) != '(' ? str2 : "(L" + str.replace('.', '/') + ';' + str2.substring(1);
    }

    public static String appendParameter(CtClass ctClass, String str) {
        int indexOf = str.indexOf(41);
        if (indexOf < 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, indexOf));
        toDescriptor(stringBuffer, ctClass);
        stringBuffer.append(str.substring(indexOf));
        return stringBuffer.toString();
    }

    public static String insertParameter(CtClass ctClass, String str) {
        return str.charAt(0) != '(' ? str : "(" + of(ctClass) + str.substring(1);
    }

    public static String changeReturnType(String str, String str2) {
        int indexOf = str2.indexOf(41);
        if (indexOf < 0) {
            return str2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2.substring(0, indexOf + 1));
        stringBuffer.append('L');
        stringBuffer.append(str.replace('.', '/'));
        stringBuffer.append(';');
        return stringBuffer.toString();
    }

    public static CtClass[] getParameterTypes(String str, ClassPool classPool) throws NotFoundException {
        int i = 0;
        if (str.charAt(0) != '(') {
            return null;
        }
        CtClass[] ctClassArr = new CtClass[numOfParameters(str)];
        int i2 = 1;
        while (true) {
            int i3 = i + 1;
            i2 = toCtClass(classPool, str, i2, ctClassArr, i);
            if (i2 <= 0) {
                return ctClassArr;
            }
            i = i3;
        }
    }

    public static boolean eqParamTypes(String str, String str2) {
        if (str.charAt(0) != '(') {
            return false;
        }
        int i = 0;
        while (true) {
            char charAt = str.charAt(i);
            if (charAt != str2.charAt(i)) {
                return false;
            }
            if (charAt == ')') {
                return true;
            }
            i++;
        }
    }

    public static String getParamDescriptor(String str) {
        return str.substring(0, str.indexOf(41) + 1);
    }

    public static CtClass getReturnType(String str, ClassPool classPool) throws NotFoundException {
        int indexOf = str.indexOf(41);
        if (indexOf < 0) {
            return null;
        }
        CtClass[] ctClassArr = new CtClass[1];
        toCtClass(classPool, str, indexOf + 1, ctClassArr, 0);
        return ctClassArr[0];
    }

    public static int numOfParameters(String str) {
        int i = 0;
        int i2 = 1;
        while (true) {
            char charAt = str.charAt(i2);
            if (charAt == ')') {
                return i;
            }
            while (charAt == '[') {
                i2++;
                charAt = str.charAt(i2);
            }
            if (charAt == 'L') {
                i2 = str.indexOf(59, i2) + 1;
                if (i2 <= 0) {
                    throw new IndexOutOfBoundsException("bad descriptor");
                }
            } else {
                i2++;
            }
            i++;
        }
    }

    public static CtClass toCtClass(String str, ClassPool classPool) throws NotFoundException {
        CtClass[] ctClassArr = new CtClass[1];
        return toCtClass(classPool, str, 0, ctClassArr, 0) >= 0 ? ctClassArr[0] : classPool.get(str.replace('/', '.'));
    }

    private static int toCtClass(ClassPool classPool, String str, int i, CtClass[] ctClassArr, int i2) throws NotFoundException {
        int i3;
        String name;
        char charAt = str.charAt(i);
        int i4 = 0;
        while (charAt == '[') {
            i4++;
            i++;
            charAt = str.charAt(i);
        }
        if (charAt == 'L') {
            int i5 = i + 1;
            int indexOf = str.indexOf(59, i5);
            i3 = indexOf + 1;
            name = str.substring(i5, indexOf).replace('/', '.');
        } else {
            CtClass primitiveClass = toPrimitiveClass(charAt);
            if (primitiveClass == null) {
                return -1;
            }
            i3 = i + 1;
            if (i4 == 0) {
                ctClassArr[i2] = primitiveClass;
                return i3;
            }
            name = primitiveClass.getName();
        }
        if (i4 > 0) {
            StringBuffer stringBuffer = new StringBuffer(name);
            while (true) {
                int i6 = i4 - 1;
                if (i4 <= 0) {
                    break;
                }
                stringBuffer.append("[]");
                i4 = i6;
            }
            name = stringBuffer.toString();
        }
        ctClassArr[i2] = classPool.get(name);
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CtClass toPrimitiveClass(char c) {
        if (c != 'F') {
            if (c != 'S') {
                if (c != 'V') {
                    if (c != 'Z') {
                        if (c != 'I') {
                            if (c != 'J') {
                                switch (c) {
                                    case 'B':
                                        return CtClass.byteType;
                                    case 'C':
                                        return CtClass.charType;
                                    case 'D':
                                        return CtClass.doubleType;
                                    default:
                                        return null;
                                }
                            }
                            return CtClass.longType;
                        }
                        return CtClass.intType;
                    }
                    return CtClass.booleanType;
                }
                return CtClass.voidType;
            }
            return CtClass.shortType;
        }
        return CtClass.floatType;
    }

    public static int arrayDimension(String str) {
        int i = 0;
        while (str.charAt(i) == '[') {
            i++;
        }
        return i;
    }

    public static String toArrayComponent(String str, int i) {
        return str.substring(i);
    }

    public static int dataSize(String str) {
        return dataSize(str, true);
    }

    public static int paramSize(String str) {
        return -dataSize(str, false);
    }

    private static int dataSize(String str, boolean z) {
        int i = 0;
        char charAt = str.charAt(0);
        if (charAt == '(') {
            int i2 = 0;
            int i3 = 1;
            while (true) {
                char charAt2 = str.charAt(i3);
                if (charAt2 == ')') {
                    charAt = str.charAt(i3 + 1);
                    i = i2;
                    break;
                }
                boolean z2 = false;
                while (charAt2 == '[') {
                    i3++;
                    charAt2 = str.charAt(i3);
                    z2 = true;
                }
                if (charAt2 == 'L') {
                    i3 = str.indexOf(59, i3) + 1;
                    if (i3 <= 0) {
                        throw new IndexOutOfBoundsException("bad descriptor");
                    }
                } else {
                    i3++;
                }
                i2 = (z2 || !(charAt2 == 'J' || charAt2 == 'D')) ? i2 - 1 : i2 - 2;
            }
        }
        return z ? (charAt == 'J' || charAt == 'D') ? i + 2 : charAt != 'V' ? i + 1 : i : i;
    }

    public static String toString(String str) {
        return PrettyPrinter.toString(str);
    }

    /* loaded from: classes2.dex */
    static class PrettyPrinter {
        PrettyPrinter() {
        }

        static String toString(String str) {
            StringBuffer stringBuffer = new StringBuffer();
            if (str.charAt(0) == '(') {
                stringBuffer.append('(');
                int i = 1;
                while (str.charAt(i) != ')') {
                    if (i > 1) {
                        stringBuffer.append(',');
                    }
                    i = readType(stringBuffer, i, str);
                }
                stringBuffer.append(')');
            } else {
                readType(stringBuffer, 0, str);
            }
            return stringBuffer.toString();
        }

        static int readType(StringBuffer stringBuffer, int i, String str) {
            char charAt = str.charAt(i);
            int i2 = 0;
            while (charAt == '[') {
                i2++;
                i++;
                charAt = str.charAt(i);
            }
            if (charAt == 'L') {
                while (true) {
                    i++;
                    char charAt2 = str.charAt(i);
                    if (charAt2 == ';') {
                        break;
                    }
                    if (charAt2 == '/') {
                        charAt2 = '.';
                    }
                    stringBuffer.append(charAt2);
                }
            } else {
                stringBuffer.append(Descriptor.toPrimitiveClass(charAt).getName());
            }
            while (true) {
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    return i + 1;
                }
                stringBuffer.append("[]");
                i2 = i3;
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class Iterator {
        private String desc;
        private int curPos = 0;
        private int index = 0;
        private boolean param = false;

        public Iterator(String str) {
            this.desc = str;
        }

        public boolean hasNext() {
            return this.index < this.desc.length();
        }

        public boolean isParameter() {
            return this.param;
        }

        public char currentChar() {
            return this.desc.charAt(this.curPos);
        }

        public boolean is2byte() {
            char currentChar = currentChar();
            return currentChar == 'D' || currentChar == 'J';
        }

        public int next() {
            int i;
            int i2 = this.index;
            char charAt = this.desc.charAt(i2);
            if (charAt == '(') {
                this.index++;
                i2++;
                charAt = this.desc.charAt(i2);
                this.param = true;
            }
            if (charAt == ')') {
                this.index++;
                i2++;
                charAt = this.desc.charAt(i2);
                this.param = false;
            }
            while (charAt == '[') {
                i2++;
                charAt = this.desc.charAt(i2);
            }
            if (charAt == 'L') {
                i = this.desc.indexOf(59, i2) + 1;
                if (i <= 0) {
                    throw new IndexOutOfBoundsException("bad descriptor");
                }
            } else {
                i = i2 + 1;
            }
            int i3 = this.index;
            this.curPos = i3;
            this.index = i;
            return i3;
        }
    }
}
