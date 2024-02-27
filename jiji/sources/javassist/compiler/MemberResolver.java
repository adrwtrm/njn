package javassist.compiler;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.Keyword;
import javassist.compiler.ast.Symbol;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public class MemberResolver implements TokenId {
    private static final String INVALID = "<invalid>";
    private static final int NO = -1;
    private static final int YES = 0;
    private static Map<ClassPool, Reference<Map<String, String>>> invalidNamesMap = new WeakHashMap();
    private ClassPool classPool;
    private Map<String, String> invalidNames = null;

    public MemberResolver(ClassPool classPool) {
        this.classPool = classPool;
    }

    public ClassPool getClassPool() {
        return this.classPool;
    }

    private static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }

    /* loaded from: classes2.dex */
    public static class Method {
        public CtClass declaring;
        public MethodInfo info;
        public int notmatch;

        public Method(CtClass ctClass, MethodInfo methodInfo, int i) {
            this.declaring = ctClass;
            this.info = methodInfo;
            this.notmatch = i;
        }

        public boolean isStatic() {
            return (this.info.getAccessFlags() & 8) != 0;
        }
    }

    public Method lookupMethod(CtClass ctClass, CtClass ctClass2, MethodInfo methodInfo, String str, int[] iArr, int[] iArr2, String[] strArr) throws CompileError {
        Method method;
        int compareSignature;
        if (methodInfo == null || ctClass != ctClass2 || !methodInfo.getName().equals(str) || (compareSignature = compareSignature(methodInfo.getDescriptor(), iArr, iArr2, strArr)) == -1) {
            method = null;
        } else {
            method = new Method(ctClass, methodInfo, compareSignature);
            if (compareSignature == 0) {
                return method;
            }
        }
        Method lookupMethod = lookupMethod(ctClass, str, iArr, iArr2, strArr, method != null);
        return lookupMethod != null ? lookupMethod : method;
    }

    private Method lookupMethod(CtClass ctClass, String str, int[] iArr, int[] iArr2, String[] strArr, boolean z) throws CompileError {
        Method method;
        CtClass superclass;
        ClassFile classFile2 = ctClass.getClassFile2();
        Method method2 = null;
        if (classFile2 != null) {
            method = null;
            for (MethodInfo methodInfo : classFile2.getMethods()) {
                if (methodInfo.getName().equals(str) && (methodInfo.getAccessFlags() & 64) == 0) {
                    int compareSignature = compareSignature(methodInfo.getDescriptor(), iArr, iArr2, strArr);
                    if (compareSignature != -1) {
                        Method method3 = new Method(ctClass, methodInfo, compareSignature);
                        if (compareSignature == 0) {
                            return method3;
                        }
                        if (method == null || method.notmatch > compareSignature) {
                            method = method3;
                        }
                    }
                }
            }
        } else {
            method = null;
        }
        if (!z) {
            if (method != null) {
                return method;
            }
            method2 = method;
        }
        boolean isInterface = Modifier.isInterface(ctClass.getModifiers());
        if (!isInterface) {
            try {
                CtClass superclass2 = ctClass.getSuperclass();
                if (superclass2 != null) {
                    Method lookupMethod = lookupMethod(superclass2, str, iArr, iArr2, strArr, z);
                    if (lookupMethod != null) {
                        return lookupMethod;
                    }
                }
            } catch (NotFoundException unused) {
            }
        }
        try {
            for (CtClass ctClass2 : ctClass.getInterfaces()) {
                Method lookupMethod2 = lookupMethod(ctClass2, str, iArr, iArr2, strArr, z);
                if (lookupMethod2 != null) {
                    return lookupMethod2;
                }
            }
            if (isInterface && (superclass = ctClass.getSuperclass()) != null) {
                Method lookupMethod3 = lookupMethod(superclass, str, iArr, iArr2, strArr, z);
                if (lookupMethod3 != null) {
                    return lookupMethod3;
                }
            }
        } catch (NotFoundException unused2) {
        }
        return method2;
    }

    private int compareSignature(String str, int[] iArr, int[] iArr2, String[] strArr) throws CompileError {
        int i;
        int length = iArr.length;
        if (length != Descriptor.numOfParameters(str)) {
            return -1;
        }
        int length2 = str.length();
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i2 < length2) {
            int i5 = i2 + 1;
            char charAt = str.charAt(i2);
            if (charAt == ')') {
                if (i3 == length) {
                    return i4;
                }
                return -1;
            } else if (i3 >= length) {
                return -1;
            } else {
                int i6 = 0;
                while (charAt == '[') {
                    i6++;
                    int i7 = i5 + 1;
                    char charAt2 = str.charAt(i5);
                    i5 = i7;
                    charAt = charAt2;
                }
                if (iArr[i3] != 412) {
                    if (iArr2[i3] != i6) {
                        if (i6 == 0 && charAt == 'L' && str.startsWith("java/lang/Object;", i5)) {
                            i = str.indexOf(59, i5) + 1;
                            i4++;
                            if (i <= 0) {
                            }
                        }
                        return -1;
                    } else if (charAt == 'L') {
                        int indexOf = str.indexOf(59, i5);
                        if (indexOf < 0 || iArr[i3] != 307) {
                            return -1;
                        }
                        String substring = str.substring(i5, indexOf);
                        if (!substring.equals(strArr[i3])) {
                            try {
                                if (!lookupClassByJvmName(strArr[i3]).subtypeOf(lookupClassByJvmName(substring))) {
                                    return -1;
                                }
                            } catch (NotFoundException unused) {
                            }
                            i4++;
                        }
                        i = indexOf + 1;
                    } else {
                        int descToType = descToType(charAt);
                        int i8 = iArr[i3];
                        if (descToType != i8) {
                            if (descToType != 324 || (i8 != 334 && i8 != 303 && i8 != 306)) {
                                return -1;
                            }
                            i4++;
                        }
                        i2 = i5;
                    }
                    i2 = i;
                } else if (i6 == 0 && charAt != 'L') {
                    return -1;
                } else {
                    if (charAt == 'L') {
                        i = str.indexOf(59, i5) + 1;
                        i2 = i;
                    }
                    i2 = i5;
                }
                i3++;
            }
        }
        return -1;
    }

    public CtField lookupFieldByJvmName2(String str, Symbol symbol, ASTree aSTree) throws NoFieldException {
        CtClass ctClass;
        String str2 = symbol.get();
        try {
            try {
                return lookupClass(jvmToJavaName(str), true).getField(str2);
            } catch (NotFoundException unused) {
                throw new NoFieldException(javaToJvmName(ctClass.getName()) + "$" + str2, aSTree);
            }
        } catch (CompileError unused2) {
            throw new NoFieldException(str + "/" + str2, aSTree);
        }
    }

    public CtField lookupFieldByJvmName(String str, Symbol symbol) throws CompileError {
        return lookupField(jvmToJavaName(str), symbol);
    }

    public CtField lookupField(String str, Symbol symbol) throws CompileError {
        try {
            return lookupClass(str, false).getField(symbol.get());
        } catch (NotFoundException unused) {
            throw new CompileError("no such field: " + symbol.get());
        }
    }

    public CtClass lookupClassByName(ASTList aSTList) throws CompileError {
        return lookupClass(Declarator.astToClassName(aSTList, '.'), false);
    }

    public CtClass lookupClassByJvmName(String str) throws CompileError {
        return lookupClass(jvmToJavaName(str), false);
    }

    public CtClass lookupClass(Declarator declarator) throws CompileError {
        return lookupClass(declarator.getType(), declarator.getArrayDim(), declarator.getClassName());
    }

    public CtClass lookupClass(int i, int i2, String str) throws CompileError {
        String typeName;
        if (i == 307) {
            CtClass lookupClassByJvmName = lookupClassByJvmName(str);
            if (i2 <= 0) {
                return lookupClassByJvmName;
            }
            typeName = lookupClassByJvmName.getName();
        } else {
            typeName = getTypeName(i);
        }
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                typeName = typeName + "[]";
                i2 = i3;
            } else {
                return lookupClass(typeName, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getTypeName(int i) throws CompileError {
        if (i != 301) {
            if (i != 303) {
                if (i != 306) {
                    if (i != 312) {
                        if (i != 317) {
                            if (i != 324) {
                                if (i != 326) {
                                    if (i != 334) {
                                        if (i != 344) {
                                            fatal();
                                            return "";
                                        }
                                        return "void";
                                    }
                                    return "short";
                                }
                                return "long";
                            }
                            return "int";
                        }
                        return TypedValues.Custom.S_FLOAT;
                    }
                    return "double";
                }
                return "char";
            }
            return "byte";
        }
        return TypedValues.Custom.S_BOOLEAN;
    }

    public CtClass lookupClass(String str, boolean z) throws CompileError {
        CtClass searchImports;
        Map<String, String> invalidNames = getInvalidNames();
        String str2 = invalidNames.get(str);
        if (str2 == INVALID) {
            throw new CompileError("no such class: " + str);
        }
        if (str2 != null) {
            try {
                return this.classPool.get(str2);
            } catch (NotFoundException unused) {
            }
        }
        try {
            searchImports = lookupClass0(str, z);
        } catch (NotFoundException unused2) {
            searchImports = searchImports(str);
        }
        invalidNames.put(str, searchImports.getName());
        return searchImports;
    }

    public static int getInvalidMapSize() {
        return invalidNamesMap.size();
    }

    private Map<String, String> getInvalidNames() {
        Map<String, String> map = this.invalidNames;
        if (map == null) {
            synchronized (MemberResolver.class) {
                Reference<Map<String, String>> reference = invalidNamesMap.get(this.classPool);
                if (reference != null) {
                    map = reference.get();
                }
                if (map == null) {
                    map = new Hashtable<>();
                    invalidNamesMap.put(this.classPool, new WeakReference(map));
                }
            }
            this.invalidNames = map;
        }
        return map;
    }

    private CtClass searchImports(String str) throws CompileError {
        if (str.indexOf(46) < 0) {
            Iterator<String> importedPackages = this.classPool.getImportedPackages();
            while (importedPackages.hasNext()) {
                String next = importedPackages.next();
                try {
                    try {
                        return this.classPool.get(next.replaceAll("\\.$", "") + "." + str);
                    } catch (NotFoundException unused) {
                        if (next.endsWith("." + str)) {
                            return this.classPool.get(next);
                        }
                        continue;
                    }
                } catch (NotFoundException unused2) {
                }
            }
        }
        getInvalidNames().put(str, INVALID);
        throw new CompileError("no such class: " + str);
    }

    private CtClass lookupClass0(String str, boolean z) throws NotFoundException {
        CtClass ctClass = null;
        do {
            try {
                ctClass = this.classPool.get(str);
                continue;
            } catch (NotFoundException e) {
                int lastIndexOf = str.lastIndexOf(46);
                if (z || lastIndexOf < 0) {
                    throw e;
                }
                StringBuffer stringBuffer = new StringBuffer(str);
                stringBuffer.setCharAt(lastIndexOf, Typography.dollar);
                str = stringBuffer.toString();
                continue;
            }
        } while (ctClass == null);
        return ctClass;
    }

    public String resolveClassName(ASTList aSTList) throws CompileError {
        if (aSTList == null) {
            return null;
        }
        return javaToJvmName(lookupClassByName(aSTList).getName());
    }

    public String resolveJvmClassName(String str) throws CompileError {
        if (str == null) {
            return null;
        }
        return javaToJvmName(lookupClassByJvmName(str).getName());
    }

    public static CtClass getSuperclass(CtClass ctClass) throws CompileError {
        try {
            CtClass superclass = ctClass.getSuperclass();
            if (superclass != null) {
                return superclass;
            }
        } catch (NotFoundException unused) {
        }
        throw new CompileError("cannot find the super class of " + ctClass.getName());
    }

    public static CtClass getSuperInterface(CtClass ctClass, String str) throws CompileError {
        try {
            CtClass[] interfaces = ctClass.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (interfaces[i].getName().equals(str)) {
                    return interfaces[i];
                }
            }
        } catch (NotFoundException unused) {
        }
        throw new CompileError("cannot find the super interface " + str + " of " + ctClass.getName());
    }

    public static String javaToJvmName(String str) {
        return str.replace('.', '/');
    }

    public static String jvmToJavaName(String str) {
        return str.replace('/', '.');
    }

    public static int descToType(char c) throws CompileError {
        if (c != 'F') {
            if (c != 'L') {
                if (c != 'S') {
                    if (c != 'V') {
                        if (c != 'I') {
                            if (c != 'J') {
                                if (c != 'Z') {
                                    if (c != '[') {
                                        switch (c) {
                                            case 'B':
                                                return 303;
                                            case 'C':
                                                return 306;
                                            case 'D':
                                                return 312;
                                            default:
                                                fatal();
                                                return TokenId.VOID;
                                        }
                                    }
                                    return 307;
                                }
                                return 301;
                            }
                            return TokenId.LONG;
                        }
                        return TokenId.INT;
                    }
                    return TokenId.VOID;
                }
                return TokenId.SHORT;
            }
            return 307;
        }
        return 317;
    }

    public static int getModifiers(ASTList aSTList) {
        int i = 0;
        while (aSTList != null) {
            aSTList = aSTList.tail();
            int i2 = ((Keyword) aSTList.head()).get();
            if (i2 == 300) {
                i |= 1024;
            } else if (i2 == 315) {
                i |= 16;
            } else if (i2 == 335) {
                i |= 8;
            } else if (i2 == 338) {
                i |= 32;
            } else if (i2 == 342) {
                i |= 128;
            } else if (i2 == 345) {
                i |= 64;
            } else if (i2 != 347) {
                switch (i2) {
                    case TokenId.PRIVATE /* 330 */:
                        i |= 2;
                        continue;
                    case TokenId.PROTECTED /* 331 */:
                        i |= 4;
                        continue;
                    case TokenId.PUBLIC /* 332 */:
                        i |= 1;
                        continue;
                }
            } else {
                i |= 2048;
            }
        }
        return i;
    }
}
