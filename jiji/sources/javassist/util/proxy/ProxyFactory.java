package javassist.util.proxy;

import com.google.common.base.Ascii;
import java.lang.invoke.MethodHandles;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import javassist.CannotCompileException;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.DuplicateMemberException;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.StackMapTable;

/* loaded from: classes2.dex */
public class ProxyFactory {
    private static final String DEFAULT_INTERCEPTOR = "default_interceptor";
    private static final String FILTER_SIGNATURE_FIELD = "_filter_signature";
    private static final String FILTER_SIGNATURE_TYPE = "[B";
    private static final String HANDLER = "handler";
    private static final String HANDLER_GETTER = "getHandler";
    private static final String HANDLER_GETTER_KEY = "getHandler:()";
    private static final String HANDLER_GETTER_TYPE;
    private static final String HANDLER_SETTER = "setHandler";
    private static final String HANDLER_SETTER_TYPE;
    private static final String HANDLER_TYPE;
    private static final String HOLDER = "_methods_";
    private static final String HOLDER_TYPE = "[Ljava/lang/reflect/Method;";
    private static final String NULL_INTERCEPTOR_HOLDER = "javassist.util.proxy.RuntimeSupport";
    private static final String SERIAL_VERSION_UID_FIELD = "serialVersionUID";
    private static final String SERIAL_VERSION_UID_TYPE = "J";
    private static final long SERIAL_VERSION_UID_VALUE = -1;
    public static boolean onlyPublicMethods = false;
    private static final String packageForJavaBase = "javassist.util.proxy.";
    private String basename;
    private String classname;
    private String superName;
    private static final Class<?> OBJECT_TYPE = Object.class;
    public static volatile boolean useCache = true;
    public static volatile boolean useWriteReplace = true;
    private static Map<ClassLoader, Map<String, ProxyDetails>> proxyCache = new WeakHashMap();
    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static ClassLoaderProvider classLoaderProvider = new ClassLoaderProvider() { // from class: javassist.util.proxy.ProxyFactory.1
        @Override // javassist.util.proxy.ProxyFactory.ClassLoaderProvider
        public ClassLoader get(ProxyFactory proxyFactory) {
            return proxyFactory.getClassLoader0();
        }
    };
    public static UniqueName nameGenerator = new UniqueName() { // from class: javassist.util.proxy.ProxyFactory.2
        private final String sep = "_$$_jvst" + Integer.toHexString(hashCode() & 4095) + "_";
        private int counter = 0;

        @Override // javassist.util.proxy.ProxyFactory.UniqueName
        public String get(String str) {
            StringBuilder append = new StringBuilder().append(str).append(this.sep);
            int i = this.counter;
            this.counter = i + 1;
            return append.append(Integer.toHexString(i)).toString();
        }
    };
    private static Comparator<Map.Entry<String, Method>> sorter = new Comparator<Map.Entry<String, Method>>() { // from class: javassist.util.proxy.ProxyFactory.3
        @Override // java.util.Comparator
        public int compare(Map.Entry<String, Method> entry, Map.Entry<String, Method> entry2) {
            return entry.getKey().compareTo(entry2.getKey());
        }
    };
    private Class<?> superClass = null;
    private Class<?>[] interfaces = null;
    private MethodFilter methodFilter = null;
    private MethodHandler handler = null;
    private byte[] signature = null;
    private List<Map.Entry<String, Method>> signatureMethods = null;
    private boolean hasGetHandler = false;
    private Class<?> thisClass = null;
    private String genericSignature = null;
    public String writeDirectory = null;
    private boolean factoryUseCache = useCache;
    private boolean factoryWriteReplace = useWriteReplace;

    /* loaded from: classes2.dex */
    public interface ClassLoaderProvider {
        ClassLoader get(ProxyFactory proxyFactory);
    }

    /* loaded from: classes2.dex */
    public interface UniqueName {
        String get(String str);
    }

    static {
        String str = "L" + MethodHandler.class.getName().replace('.', '/') + ';';
        HANDLER_TYPE = str;
        HANDLER_SETTER_TYPE = "(" + str + ")V";
        HANDLER_GETTER_TYPE = "()" + str;
    }

    public boolean isUseCache() {
        return this.factoryUseCache;
    }

    public void setUseCache(boolean z) {
        if (this.handler != null && z) {
            throw new RuntimeException("caching cannot be enabled if the factory default interceptor has been set");
        }
        this.factoryUseCache = z;
    }

    public boolean isUseWriteReplace() {
        return this.factoryWriteReplace;
    }

    public void setUseWriteReplace(boolean z) {
        this.factoryWriteReplace = z;
    }

    public static boolean isProxyClass(Class<?> cls) {
        return Proxy.class.isAssignableFrom(cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class ProxyDetails {
        boolean isUseWriteReplace;
        Reference<Class<?>> proxyClass;
        byte[] signature;

        ProxyDetails(byte[] bArr, Class<?> cls, boolean z) {
            this.signature = bArr;
            this.proxyClass = new WeakReference(cls);
            this.isUseWriteReplace = z;
        }
    }

    public void setSuperclass(Class<?> cls) {
        this.superClass = cls;
        this.signature = null;
    }

    public Class<?> getSuperclass() {
        return this.superClass;
    }

    public void setInterfaces(Class<?>[] clsArr) {
        this.interfaces = clsArr;
        this.signature = null;
    }

    public Class<?>[] getInterfaces() {
        return this.interfaces;
    }

    public void setFilter(MethodFilter methodFilter) {
        this.methodFilter = methodFilter;
        this.signature = null;
    }

    public void setGenericSignature(String str) {
        this.genericSignature = str;
    }

    public Class<?> createClass() {
        if (this.signature == null) {
            computeSignature(this.methodFilter);
        }
        return createClass1(null);
    }

    public Class<?> createClass(MethodFilter methodFilter) {
        computeSignature(methodFilter);
        return createClass1(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Class<?> createClass(byte[] bArr) {
        installSignature(bArr);
        return createClass1(null);
    }

    public Class<?> createClass(MethodHandles.Lookup lookup) {
        if (this.signature == null) {
            computeSignature(this.methodFilter);
        }
        return createClass1(lookup);
    }

    public Class<?> createClass(MethodHandles.Lookup lookup, MethodFilter methodFilter) {
        computeSignature(methodFilter);
        return createClass1(lookup);
    }

    Class<?> createClass(MethodHandles.Lookup lookup, byte[] bArr) {
        installSignature(bArr);
        return createClass1(lookup);
    }

    private Class<?> createClass1(MethodHandles.Lookup lookup) {
        Class<?> cls = this.thisClass;
        if (cls == null) {
            ClassLoader classLoader = getClassLoader();
            synchronized (proxyCache) {
                if (this.factoryUseCache) {
                    createClass2(classLoader, lookup);
                } else {
                    createClass3(classLoader, lookup);
                }
                cls = this.thisClass;
                this.thisClass = null;
            }
        }
        return cls;
    }

    public String getKey(Class<?> cls, Class<?>[] clsArr, byte[] bArr, boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (cls != null) {
            stringBuffer.append(cls.getName());
        }
        stringBuffer.append(":");
        for (Class<?> cls2 : clsArr) {
            stringBuffer.append(cls2.getName());
            stringBuffer.append(":");
        }
        for (byte b : bArr) {
            stringBuffer.append(hexDigits[b & Ascii.SI]);
            stringBuffer.append(hexDigits[(b >> 4) & 15]);
        }
        if (z) {
            stringBuffer.append(":w");
        }
        return stringBuffer.toString();
    }

    private void createClass2(ClassLoader classLoader, MethodHandles.Lookup lookup) {
        String key = getKey(this.superClass, this.interfaces, this.signature, this.factoryWriteReplace);
        Map<String, ProxyDetails> map = proxyCache.get(classLoader);
        if (map == null) {
            map = new HashMap<>();
            proxyCache.put(classLoader, map);
        }
        ProxyDetails proxyDetails = map.get(key);
        if (proxyDetails != null) {
            Class<?> cls = proxyDetails.proxyClass.get();
            this.thisClass = cls;
            if (cls != null) {
                return;
            }
        }
        createClass3(classLoader, lookup);
        map.put(key, new ProxyDetails(this.signature, this.thisClass, this.factoryWriteReplace));
    }

    private void createClass3(ClassLoader classLoader, MethodHandles.Lookup lookup) {
        allocateClassName();
        try {
            ClassFile make = make();
            String str = this.writeDirectory;
            if (str != null) {
                FactoryHelper.writeFile(make, str);
            }
            if (lookup == null) {
                this.thisClass = FactoryHelper.toClass(make, getClassInTheSamePackage(), classLoader, getDomain());
            } else {
                this.thisClass = FactoryHelper.toClass(make, lookup);
            }
            setField(FILTER_SIGNATURE_FIELD, this.signature);
            if (this.factoryUseCache) {
                return;
            }
            setField(DEFAULT_INTERCEPTOR, this.handler);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Class<?> getClassInTheSamePackage() {
        if (this.basename.startsWith(packageForJavaBase)) {
            return getClass();
        }
        Class<?> cls = this.superClass;
        if (cls == null || cls == OBJECT_TYPE) {
            Class<?>[] clsArr = this.interfaces;
            if (clsArr != null && clsArr.length > 0) {
                return clsArr[0];
            }
            return getClass();
        }
        return cls;
    }

    private void setField(String str, Object obj) {
        Class<?> cls = this.thisClass;
        if (cls == null || obj == null) {
            return;
        }
        try {
            Field field = cls.getField(str);
            SecurityActions.setAccessible(field, true);
            field.set(null, obj);
            SecurityActions.setAccessible(field, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] getFilterSignature(Class<?> cls) {
        return (byte[]) getField(cls, FILTER_SIGNATURE_FIELD);
    }

    private static Object getField(Class<?> cls, String str) {
        try {
            Field field = cls.getField(str);
            field.setAccessible(true);
            Object obj = field.get(null);
            field.setAccessible(false);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodHandler getHandler(Proxy proxy) {
        try {
            Field declaredField = proxy.getClass().getDeclaredField(HANDLER);
            declaredField.setAccessible(true);
            Object obj = declaredField.get(proxy);
            declaredField.setAccessible(false);
            return (MethodHandler) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ClassLoader getClassLoader() {
        return classLoaderProvider.get(this);
    }

    protected ClassLoader getClassLoader0() {
        ClassLoader classLoader;
        Class<?> cls = this.superClass;
        if (cls != null && !cls.getName().equals("java.lang.Object")) {
            classLoader = this.superClass.getClassLoader();
        } else {
            Class<?>[] clsArr = this.interfaces;
            classLoader = (clsArr == null || clsArr.length <= 0) ? null : clsArr[0].getClassLoader();
        }
        if (classLoader == null) {
            ClassLoader classLoader2 = getClass().getClassLoader();
            if (classLoader2 == null) {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                return contextClassLoader == null ? ClassLoader.getSystemClassLoader() : contextClassLoader;
            }
            return classLoader2;
        }
        return classLoader;
    }

    protected ProtectionDomain getDomain() {
        Class<?> cls;
        Class<?> cls2 = this.superClass;
        if (cls2 != null && !cls2.getName().equals("java.lang.Object")) {
            cls = this.superClass;
        } else {
            Class<?>[] clsArr = this.interfaces;
            if (clsArr != null && clsArr.length > 0) {
                cls = clsArr[0];
            } else {
                cls = getClass();
            }
        }
        return cls.getProtectionDomain();
    }

    public Object create(Class<?>[] clsArr, Object[] objArr, MethodHandler methodHandler) throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object create = create(clsArr, objArr);
        ((Proxy) create).setHandler(methodHandler);
        return create;
    }

    public Object create(Class<?>[] clsArr, Object[] objArr) throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return createClass().getConstructor(clsArr).newInstance(objArr);
    }

    @Deprecated
    public void setHandler(MethodHandler methodHandler) {
        if (this.factoryUseCache && methodHandler != null) {
            this.factoryUseCache = false;
            this.thisClass = null;
        }
        this.handler = methodHandler;
        setField(DEFAULT_INTERCEPTOR, methodHandler);
    }

    private static String makeProxyName(String str) {
        String str2;
        synchronized (nameGenerator) {
            str2 = nameGenerator.get(str);
        }
        return str2;
    }

    private ClassFile make() throws CannotCompileException {
        ClassFile classFile = new ClassFile(false, this.classname, this.superName);
        classFile.setAccessFlags(1);
        setInterfaces(classFile, this.interfaces, this.hasGetHandler ? Proxy.class : ProxyObject.class);
        ConstPool constPool = classFile.getConstPool();
        if (!this.factoryUseCache) {
            FieldInfo fieldInfo = new FieldInfo(constPool, DEFAULT_INTERCEPTOR, HANDLER_TYPE);
            fieldInfo.setAccessFlags(9);
            classFile.addField(fieldInfo);
        }
        FieldInfo fieldInfo2 = new FieldInfo(constPool, HANDLER, HANDLER_TYPE);
        fieldInfo2.setAccessFlags(2);
        classFile.addField(fieldInfo2);
        FieldInfo fieldInfo3 = new FieldInfo(constPool, FILTER_SIGNATURE_FIELD, FILTER_SIGNATURE_TYPE);
        fieldInfo3.setAccessFlags(9);
        classFile.addField(fieldInfo3);
        FieldInfo fieldInfo4 = new FieldInfo(constPool, SERIAL_VERSION_UID_FIELD, SERIAL_VERSION_UID_TYPE);
        fieldInfo4.setAccessFlags(25);
        classFile.addField(fieldInfo4);
        if (this.genericSignature != null) {
            classFile.addAttribute(new SignatureAttribute(constPool, this.genericSignature));
        }
        String str = this.classname;
        makeConstructors(str, classFile, constPool, str);
        ArrayList arrayList = new ArrayList();
        addClassInitializer(classFile, constPool, this.classname, overrideMethods(classFile, constPool, this.classname, arrayList), arrayList);
        addSetter(this.classname, classFile, constPool);
        if (!this.hasGetHandler) {
            addGetter(this.classname, classFile, constPool);
        }
        if (this.factoryWriteReplace) {
            try {
                classFile.addMethod(makeWriteReplace(constPool));
            } catch (DuplicateMemberException unused) {
            }
        }
        this.thisClass = null;
        return classFile;
    }

    private void checkClassAndSuperName() {
        if (this.interfaces == null) {
            this.interfaces = new Class[0];
        }
        Class<?> cls = this.superClass;
        if (cls == null) {
            Class<?> cls2 = OBJECT_TYPE;
            this.superClass = cls2;
            String name = cls2.getName();
            this.superName = name;
            Class<?>[] clsArr = this.interfaces;
            if (clsArr.length != 0) {
                name = clsArr[0].getName();
            }
            this.basename = name;
        } else {
            String name2 = cls.getName();
            this.superName = name2;
            this.basename = name2;
        }
        if (Modifier.isFinal(this.superClass.getModifiers())) {
            throw new RuntimeException(this.superName + " is final");
        }
        if (this.basename.startsWith("java.") || this.basename.startsWith("jdk.") || onlyPublicMethods) {
            this.basename = packageForJavaBase + this.basename.replace('.', '_');
        }
    }

    private void allocateClassName() {
        this.classname = makeProxyName(this.basename);
    }

    private void makeSortedMethodList() {
        checkClassAndSuperName();
        this.hasGetHandler = false;
        ArrayList arrayList = new ArrayList(getMethods(this.superClass, this.interfaces).entrySet());
        this.signatureMethods = arrayList;
        Collections.sort(arrayList, sorter);
    }

    private void computeSignature(MethodFilter methodFilter) {
        makeSortedMethodList();
        int size = this.signatureMethods.size();
        this.signature = new byte[(size + 7) >> 3];
        for (int i = 0; i < size; i++) {
            Method value = this.signatureMethods.get(i).getValue();
            int modifiers = value.getModifiers();
            if (!Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers) && isVisible(modifiers, this.basename, value) && (methodFilter == null || methodFilter.isHandled(value))) {
                setBit(this.signature, i);
            }
        }
    }

    private void installSignature(byte[] bArr) {
        makeSortedMethodList();
        if (bArr.length != ((this.signatureMethods.size() + 7) >> 3)) {
            throw new RuntimeException("invalid filter signature length for deserialized proxy class");
        }
        this.signature = bArr;
    }

    private boolean testBit(byte[] bArr, int i) {
        int i2 = i >> 3;
        if (i2 > bArr.length) {
            return false;
        }
        return (bArr[i2] & (1 << (i & 7))) != 0;
    }

    private void setBit(byte[] bArr, int i) {
        int i2 = i >> 3;
        if (i2 < bArr.length) {
            bArr[i2] = (byte) ((1 << (i & 7)) | bArr[i2]);
        }
    }

    private static void setInterfaces(ClassFile classFile, Class<?>[] clsArr, Class<?> cls) {
        String[] strArr;
        String name = cls.getName();
        if (clsArr == null || clsArr.length == 0) {
            strArr = new String[]{name};
        } else {
            strArr = new String[clsArr.length + 1];
            for (int i = 0; i < clsArr.length; i++) {
                strArr[i] = clsArr[i].getName();
            }
            strArr[clsArr.length] = name;
        }
        classFile.setInterfaces(strArr);
    }

    private static void addClassInitializer(ClassFile classFile, ConstPool constPool, String str, int i, List<Find2MethodsArgs> list) throws CannotCompileException {
        FieldInfo fieldInfo = new FieldInfo(constPool, HOLDER, HOLDER_TYPE);
        fieldInfo.setAccessFlags(10);
        classFile.addField(fieldInfo);
        MethodInfo methodInfo = new MethodInfo(constPool, MethodInfo.nameClinit, "()V");
        methodInfo.setAccessFlags(8);
        setThrows(methodInfo, constPool, new Class[]{ClassNotFoundException.class});
        Bytecode bytecode = new Bytecode(constPool, 0, 2);
        bytecode.addIconst(i * 2);
        bytecode.addAnewarray("java.lang.reflect.Method");
        bytecode.addAstore(0);
        bytecode.addLdc(str);
        bytecode.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        bytecode.addAstore(1);
        for (Find2MethodsArgs find2MethodsArgs : list) {
            callFind2Methods(bytecode, find2MethodsArgs.methodName, find2MethodsArgs.delegatorName, find2MethodsArgs.origIndex, find2MethodsArgs.descriptor, 1, 0);
        }
        bytecode.addAload(0);
        bytecode.addPutstatic(str, HOLDER, HOLDER_TYPE);
        bytecode.addLconst(-1L);
        bytecode.addPutstatic(str, SERIAL_VERSION_UID_FIELD, SERIAL_VERSION_UID_TYPE);
        bytecode.addOpcode(177);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
    }

    private static void callFind2Methods(Bytecode bytecode, String str, String str2, int i, String str3, int i2, int i3) {
        String name = RuntimeSupport.class.getName();
        bytecode.addAload(i2);
        bytecode.addLdc(str);
        if (str2 == null) {
            bytecode.addOpcode(1);
        } else {
            bytecode.addLdc(str2);
        }
        bytecode.addIconst(i);
        bytecode.addLdc(str3);
        bytecode.addAload(i3);
        bytecode.addInvokestatic(name, "find2Methods", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;[Ljava/lang/reflect/Method;)V");
    }

    private static void addSetter(String str, ClassFile classFile, ConstPool constPool) throws CannotCompileException {
        MethodInfo methodInfo = new MethodInfo(constPool, HANDLER_SETTER, HANDLER_SETTER_TYPE);
        methodInfo.setAccessFlags(1);
        Bytecode bytecode = new Bytecode(constPool, 2, 2);
        bytecode.addAload(0);
        bytecode.addAload(1);
        bytecode.addPutfield(str, HANDLER, HANDLER_TYPE);
        bytecode.addOpcode(177);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
    }

    private static void addGetter(String str, ClassFile classFile, ConstPool constPool) throws CannotCompileException {
        MethodInfo methodInfo = new MethodInfo(constPool, HANDLER_GETTER, HANDLER_GETTER_TYPE);
        methodInfo.setAccessFlags(1);
        Bytecode bytecode = new Bytecode(constPool, 1, 1);
        bytecode.addAload(0);
        bytecode.addGetfield(str, HANDLER, HANDLER_TYPE);
        bytecode.addOpcode(176);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
    }

    private int overrideMethods(ClassFile classFile, ConstPool constPool, String str, List<Find2MethodsArgs> list) throws CannotCompileException {
        String makeUniqueName = makeUniqueName("_d", this.signatureMethods);
        int i = 0;
        for (Map.Entry<String, Method> entry : this.signatureMethods) {
            if ((ClassFile.MAJOR_VERSION < 49 || !isBridge(entry.getValue())) && testBit(this.signature, i)) {
                override(str, entry.getValue(), makeUniqueName, i, keyToDesc(entry.getKey(), entry.getValue()), classFile, constPool, list);
            }
            i++;
        }
        return i;
    }

    private static boolean isBridge(Method method) {
        return method.isBridge();
    }

    private void override(String str, Method method, String str2, int i, String str3, ClassFile classFile, ConstPool constPool, List<Find2MethodsArgs> list) throws CannotCompileException {
        String str4;
        Class<?> declaringClass = method.getDeclaringClass();
        String str5 = str2 + i + method.getName();
        if (Modifier.isAbstract(method.getModifiers())) {
            str4 = null;
        } else {
            str4 = str5;
            MethodInfo makeDelegator = makeDelegator(method, str3, constPool, declaringClass, str4);
            makeDelegator.setAccessFlags(makeDelegator.getAccessFlags() & (-65));
            classFile.addMethod(makeDelegator);
        }
        classFile.addMethod(makeForwarder(str, method, str3, constPool, declaringClass, str4, i, list));
    }

    private void makeConstructors(String str, ClassFile classFile, ConstPool constPool, String str2) throws CannotCompileException {
        Constructor<?>[] declaredConstructors = SecurityActions.getDeclaredConstructors(this.superClass);
        boolean z = !this.factoryUseCache;
        for (Constructor<?> constructor : declaredConstructors) {
            int modifiers = constructor.getModifiers();
            if (!Modifier.isFinal(modifiers) && !Modifier.isPrivate(modifiers) && isVisible(modifiers, this.basename, constructor)) {
                classFile.addMethod(makeConstructor(str, constructor, constPool, this.superClass, z));
            }
        }
    }

    private static String makeUniqueName(String str, List<Map.Entry<String, Method>> list) {
        if (makeUniqueName0(str, list.iterator())) {
            return str;
        }
        for (int i = 100; i < 999; i++) {
            String str2 = str + i;
            if (makeUniqueName0(str2, list.iterator())) {
                return str2;
            }
        }
        throw new RuntimeException("cannot make a unique method name");
    }

    private static boolean makeUniqueName0(String str, Iterator<Map.Entry<String, Method>> it) {
        while (it.hasNext()) {
            if (it.next().getKey().startsWith(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isVisible(int i, String str, Member member) {
        if ((i & 2) != 0) {
            return false;
        }
        if ((i & 5) != 0) {
            return true;
        }
        String packageName = getPackageName(str);
        String packageName2 = getPackageName(member.getDeclaringClass().getName());
        if (packageName == null) {
            return packageName2 == null;
        }
        return packageName.equals(packageName2);
    }

    private static String getPackageName(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf < 0) {
            return null;
        }
        return str.substring(0, lastIndexOf);
    }

    private Map<String, Method> getMethods(Class<?> cls, Class<?>[] clsArr) {
        HashMap hashMap = new HashMap();
        HashSet hashSet = new HashSet();
        for (Class<?> cls2 : clsArr) {
            getMethods(hashMap, cls2, hashSet);
        }
        getMethods(hashMap, cls, hashSet);
        return hashMap;
    }

    private void getMethods(Map<String, Method> map, Class<?> cls, Set<Class<?>> set) {
        if (set.add(cls)) {
            for (Class<?> cls2 : cls.getInterfaces()) {
                getMethods(map, cls2, set);
            }
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass != null) {
                getMethods(map, superclass, set);
            }
            Method[] declaredMethods = SecurityActions.getDeclaredMethods(cls);
            for (int i = 0; i < declaredMethods.length; i++) {
                if (!Modifier.isPrivate(declaredMethods[i].getModifiers())) {
                    Method method = declaredMethods[i];
                    String str = method.getName() + ':' + RuntimeSupport.makeDescriptor(method);
                    if (str.startsWith(HANDLER_GETTER_KEY)) {
                        this.hasGetHandler = true;
                    }
                    Method put = map.put(str, method);
                    if (put != null && isBridge(method) && !Modifier.isPublic(put.getDeclaringClass().getModifiers()) && !Modifier.isAbstract(put.getModifiers()) && !isDuplicated(i, declaredMethods)) {
                        map.put(str, put);
                    }
                    if (put != null && Modifier.isPublic(put.getModifiers()) && !Modifier.isPublic(method.getModifiers())) {
                        map.put(str, put);
                    }
                }
            }
        }
    }

    private static boolean isDuplicated(int i, Method[] methodArr) {
        String name = methodArr[i].getName();
        for (int i2 = 0; i2 < methodArr.length; i2++) {
            if (i2 != i && name.equals(methodArr[i2].getName()) && areParametersSame(methodArr[i], methodArr[i2])) {
                return true;
            }
        }
        return false;
    }

    private static boolean areParametersSame(Method method, Method method2) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?>[] parameterTypes2 = method2.getParameterTypes();
        if (parameterTypes.length == parameterTypes2.length) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (!parameterTypes[i].getName().equals(parameterTypes2[i].getName())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static String keyToDesc(String str, Method method) {
        return str.substring(str.indexOf(58) + 1);
    }

    private static MethodInfo makeConstructor(String str, Constructor<?> constructor, ConstPool constPool, Class<?> cls, boolean z) {
        String makeDescriptor = RuntimeSupport.makeDescriptor(constructor.getParameterTypes(), Void.TYPE);
        MethodInfo methodInfo = new MethodInfo(constPool, "<init>", makeDescriptor);
        methodInfo.setAccessFlags(1);
        setThrows(methodInfo, constPool, constructor.getExceptionTypes());
        Bytecode bytecode = new Bytecode(constPool, 0, 0);
        if (z) {
            bytecode.addAload(0);
            String str2 = HANDLER_TYPE;
            bytecode.addGetstatic(str, DEFAULT_INTERCEPTOR, str2);
            bytecode.addPutfield(str, HANDLER, str2);
            bytecode.addGetstatic(str, DEFAULT_INTERCEPTOR, str2);
            bytecode.addOpcode(199);
            bytecode.addIndex(10);
        }
        bytecode.addAload(0);
        String str3 = HANDLER_TYPE;
        bytecode.addGetstatic(NULL_INTERCEPTOR_HOLDER, DEFAULT_INTERCEPTOR, str3);
        bytecode.addPutfield(str, HANDLER, str3);
        int currentPc = bytecode.currentPc();
        bytecode.addAload(0);
        int addLoadParameters = addLoadParameters(bytecode, constructor.getParameterTypes(), 1);
        bytecode.addInvokespecial(cls.getName(), "<init>", makeDescriptor);
        bytecode.addOpcode(177);
        bytecode.setMaxLocals(addLoadParameters + 1);
        CodeAttribute codeAttribute = bytecode.toCodeAttribute();
        methodInfo.setCodeAttribute(codeAttribute);
        StackMapTable.Writer writer = new StackMapTable.Writer(32);
        writer.sameFrame(currentPc);
        codeAttribute.setAttribute(writer.toStackMapTable(constPool));
        return methodInfo;
    }

    private MethodInfo makeDelegator(Method method, String str, ConstPool constPool, Class<?> cls, String str2) {
        MethodInfo methodInfo = new MethodInfo(constPool, str2, str);
        methodInfo.setAccessFlags((method.getModifiers() & (-1319)) | 17);
        setThrows(methodInfo, constPool, method);
        Bytecode bytecode = new Bytecode(constPool, 0, 0);
        bytecode.addAload(0);
        int addLoadParameters = addLoadParameters(bytecode, method.getParameterTypes(), 1);
        Class<?> invokespecialTarget = invokespecialTarget(cls);
        bytecode.addInvokespecial(invokespecialTarget.isInterface(), constPool.addClassInfo(invokespecialTarget.getName()), method.getName(), str);
        addReturn(bytecode, method.getReturnType());
        bytecode.setMaxLocals(addLoadParameters + 1);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return methodInfo;
    }

    private Class<?> invokespecialTarget(Class<?> cls) {
        Class<?>[] clsArr;
        if (cls.isInterface()) {
            for (Class<?> cls2 : this.interfaces) {
                if (cls.isAssignableFrom(cls2)) {
                    return cls2;
                }
            }
        }
        return this.superClass;
    }

    private static MethodInfo makeForwarder(String str, Method method, String str2, ConstPool constPool, Class<?> cls, String str3, int i, List<Find2MethodsArgs> list) {
        MethodInfo methodInfo = new MethodInfo(constPool, method.getName(), str2);
        methodInfo.setAccessFlags((method.getModifiers() & (-1313)) | 16);
        setThrows(methodInfo, constPool, method);
        int paramSize = Descriptor.paramSize(str2);
        Bytecode bytecode = new Bytecode(constPool, 0, paramSize + 2);
        int i2 = i * 2;
        int i3 = paramSize + 1;
        bytecode.addGetstatic(str, HOLDER, HOLDER_TYPE);
        bytecode.addAstore(i3);
        list.add(new Find2MethodsArgs(method.getName(), str3, str2, i2));
        bytecode.addAload(0);
        bytecode.addGetfield(str, HANDLER, HANDLER_TYPE);
        bytecode.addAload(0);
        bytecode.addAload(i3);
        bytecode.addIconst(i2);
        bytecode.addOpcode(50);
        bytecode.addAload(i3);
        bytecode.addIconst(i2 + 1);
        bytecode.addOpcode(50);
        makeParameterList(bytecode, method.getParameterTypes());
        bytecode.addInvokeinterface(MethodHandler.class.getName(), "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", 5);
        Class<?> returnType = method.getReturnType();
        addUnwrapper(bytecode, returnType);
        addReturn(bytecode, returnType);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return methodInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Find2MethodsArgs {
        String delegatorName;
        String descriptor;
        String methodName;
        int origIndex;

        Find2MethodsArgs(String str, String str2, String str3, int i) {
            this.methodName = str;
            this.delegatorName = str2;
            this.descriptor = str3;
            this.origIndex = i;
        }
    }

    private static void setThrows(MethodInfo methodInfo, ConstPool constPool, Method method) {
        setThrows(methodInfo, constPool, method.getExceptionTypes());
    }

    private static void setThrows(MethodInfo methodInfo, ConstPool constPool, Class<?>[] clsArr) {
        if (clsArr.length == 0) {
            return;
        }
        String[] strArr = new String[clsArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            strArr[i] = clsArr[i].getName();
        }
        ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute(constPool);
        exceptionsAttribute.setExceptions(strArr);
        methodInfo.setExceptionsAttribute(exceptionsAttribute);
    }

    private static int addLoadParameters(Bytecode bytecode, Class<?>[] clsArr, int i) {
        int i2 = 0;
        for (Class<?> cls : clsArr) {
            i2 += addLoad(bytecode, i2 + i, cls);
        }
        return i2;
    }

    private static int addLoad(Bytecode bytecode, int i, Class<?> cls) {
        if (cls.isPrimitive()) {
            if (cls == Long.TYPE) {
                bytecode.addLload(i);
                return 2;
            } else if (cls == Float.TYPE) {
                bytecode.addFload(i);
                return 1;
            } else if (cls == Double.TYPE) {
                bytecode.addDload(i);
                return 2;
            } else {
                bytecode.addIload(i);
                return 1;
            }
        }
        bytecode.addAload(i);
        return 1;
    }

    private static int addReturn(Bytecode bytecode, Class<?> cls) {
        if (cls.isPrimitive()) {
            if (cls == Long.TYPE) {
                bytecode.addOpcode(173);
                return 2;
            } else if (cls == Float.TYPE) {
                bytecode.addOpcode(174);
                return 1;
            } else if (cls == Double.TYPE) {
                bytecode.addOpcode(175);
                return 2;
            } else if (cls == Void.TYPE) {
                bytecode.addOpcode(177);
                return 0;
            } else {
                bytecode.addOpcode(172);
                return 1;
            }
        }
        bytecode.addOpcode(176);
        return 1;
    }

    private static void makeParameterList(Bytecode bytecode, Class<?>[] clsArr) {
        int length = clsArr.length;
        bytecode.addIconst(length);
        bytecode.addAnewarray("java/lang/Object");
        int i = 1;
        for (int i2 = 0; i2 < length; i2++) {
            bytecode.addOpcode(89);
            bytecode.addIconst(i2);
            Class<?> cls = clsArr[i2];
            if (cls.isPrimitive()) {
                i = makeWrapper(bytecode, cls, i);
            } else {
                bytecode.addAload(i);
                i++;
            }
            bytecode.addOpcode(83);
        }
    }

    private static int makeWrapper(Bytecode bytecode, Class<?> cls, int i) {
        int typeIndex = FactoryHelper.typeIndex(cls);
        String str = FactoryHelper.wrapperTypes[typeIndex];
        bytecode.addNew(str);
        bytecode.addOpcode(89);
        addLoad(bytecode, i, cls);
        bytecode.addInvokespecial(str, "<init>", FactoryHelper.wrapperDesc[typeIndex]);
        return i + FactoryHelper.dataSize[typeIndex];
    }

    private static void addUnwrapper(Bytecode bytecode, Class<?> cls) {
        if (cls.isPrimitive()) {
            if (cls == Void.TYPE) {
                bytecode.addOpcode(87);
                return;
            }
            int typeIndex = FactoryHelper.typeIndex(cls);
            String str = FactoryHelper.wrapperTypes[typeIndex];
            bytecode.addCheckcast(str);
            bytecode.addInvokevirtual(str, FactoryHelper.unwarpMethods[typeIndex], FactoryHelper.unwrapDesc[typeIndex]);
            return;
        }
        bytecode.addCheckcast(cls.getName());
    }

    private static MethodInfo makeWriteReplace(ConstPool constPool) {
        MethodInfo methodInfo = new MethodInfo(constPool, "writeReplace", "()Ljava/lang/Object;");
        ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute(constPool);
        exceptionsAttribute.setExceptions(new String[]{"java.io.ObjectStreamException"});
        methodInfo.setExceptionsAttribute(exceptionsAttribute);
        Bytecode bytecode = new Bytecode(constPool, 0, 1);
        bytecode.addAload(0);
        bytecode.addInvokestatic(NULL_INTERCEPTOR_HOLDER, "makeSerializedProxy", "(Ljava/lang/Object;)Ljavassist/util/proxy/SerializedProxy;");
        bytecode.addOpcode(176);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return methodInfo;
    }
}
