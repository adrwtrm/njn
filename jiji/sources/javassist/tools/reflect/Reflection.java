package javassist.tools.reflect;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;

/* loaded from: classes2.dex */
public class Reflection implements Translator {
    static final String classMetaobjectClassName = "javassist.tools.reflect.ClassMetaobject";
    static final String classobjectAccessor = "_getClass";
    static final String classobjectField = "_classobject";
    static final String metaobjectClassName = "javassist.tools.reflect.Metaobject";
    static final String metaobjectField = "_metaobject";
    static final String metaobjectGetter = "_getMetaobject";
    static final String metaobjectSetter = "_setMetaobject";
    static final String readPrefix = "_r_";
    static final String writePrefix = "_w_";
    protected ClassPool classPool = null;
    protected CodeConverter converter = new CodeConverter();
    protected CtClass[] readParam;
    protected CtMethod trapMethod;
    protected CtMethod trapRead;
    protected CtMethod trapStaticMethod;
    protected CtMethod trapWrite;

    private boolean isExcluded(String str) {
        return str.startsWith("_m_") || str.equals(classobjectAccessor) || str.equals(metaobjectSetter) || str.equals(metaobjectGetter) || str.startsWith(readPrefix) || str.startsWith(writePrefix);
    }

    @Override // javassist.Translator
    public void start(ClassPool classPool) throws NotFoundException {
        this.classPool = classPool;
        try {
            CtClass ctClass = classPool.get("javassist.tools.reflect.Sample");
            rebuildClassFile(ctClass.getClassFile());
            this.trapMethod = ctClass.getDeclaredMethod("trap");
            this.trapStaticMethod = ctClass.getDeclaredMethod("trapStatic");
            this.trapRead = ctClass.getDeclaredMethod("trapRead");
            this.trapWrite = ctClass.getDeclaredMethod("trapWrite");
            this.readParam = new CtClass[]{this.classPool.get("java.lang.Object")};
        } catch (NotFoundException unused) {
            throw new RuntimeException("javassist.tools.reflect.Sample is not found or broken.");
        } catch (BadBytecode unused2) {
            throw new RuntimeException("javassist.tools.reflect.Sample is not found or broken.");
        }
    }

    @Override // javassist.Translator
    public void onLoad(ClassPool classPool, String str) throws CannotCompileException, NotFoundException {
        classPool.get(str).instrument(this.converter);
    }

    public boolean makeReflective(String str, String str2, String str3) throws CannotCompileException, NotFoundException {
        return makeReflective(this.classPool.get(str), this.classPool.get(str2), this.classPool.get(str3));
    }

    public boolean makeReflective(Class<?> cls, Class<?> cls2, Class<?> cls3) throws CannotCompileException, NotFoundException {
        return makeReflective(cls.getName(), cls2.getName(), cls3.getName());
    }

    public boolean makeReflective(CtClass ctClass, CtClass ctClass2, CtClass ctClass3) throws CannotCompileException, CannotReflectException, NotFoundException {
        if (ctClass.isInterface()) {
            throw new CannotReflectException("Cannot reflect an interface: " + ctClass.getName());
        }
        if (ctClass.subclassOf(this.classPool.get(classMetaobjectClassName))) {
            throw new CannotReflectException("Cannot reflect a subclass of ClassMetaobject: " + ctClass.getName());
        }
        if (ctClass.subclassOf(this.classPool.get(metaobjectClassName))) {
            throw new CannotReflectException("Cannot reflect a subclass of Metaobject: " + ctClass.getName());
        }
        registerReflectiveClass(ctClass);
        return modifyClassfile(ctClass, ctClass2, ctClass3);
    }

    private void registerReflectiveClass(CtClass ctClass) {
        CtField[] declaredFields;
        for (CtField ctField : ctClass.getDeclaredFields()) {
            int modifiers = ctField.getModifiers();
            if ((modifiers & 1) != 0 && (modifiers & 16) == 0) {
                String name = ctField.getName();
                this.converter.replaceFieldRead(ctField, ctClass, readPrefix + name);
                this.converter.replaceFieldWrite(ctField, ctClass, writePrefix + name);
            }
        }
    }

    private boolean modifyClassfile(CtClass ctClass, CtClass ctClass2, CtClass ctClass3) throws CannotCompileException, NotFoundException {
        if (ctClass.getAttribute("Reflective") != null) {
            return false;
        }
        ctClass.setAttribute("Reflective", new byte[0]);
        CtClass ctClass4 = this.classPool.get("javassist.tools.reflect.Metalevel");
        boolean z = !ctClass.subtypeOf(ctClass4);
        if (z) {
            ctClass.addInterface(ctClass4);
        }
        processMethods(ctClass, z);
        processFields(ctClass);
        if (z) {
            CtField ctField = new CtField(this.classPool.get(metaobjectClassName), metaobjectField, ctClass);
            ctField.setModifiers(4);
            ctClass.addField(ctField, CtField.Initializer.byNewWithParams(ctClass2));
            ctClass.addMethod(CtNewMethod.getter(metaobjectGetter, ctField));
            ctClass.addMethod(CtNewMethod.setter(metaobjectSetter, ctField));
        }
        CtField ctField2 = new CtField(this.classPool.get(classMetaobjectClassName), classobjectField, ctClass);
        ctField2.setModifiers(10);
        ctClass.addField(ctField2, CtField.Initializer.byNew(ctClass3, new String[]{ctClass.getName()}));
        ctClass.addMethod(CtNewMethod.getter(classobjectAccessor, ctField2));
        return true;
    }

    private void processMethods(CtClass ctClass, boolean z) throws CannotCompileException, NotFoundException {
        CtMethod[] methods = ctClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            CtMethod ctMethod = methods[i];
            int modifiers = ctMethod.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers)) {
                processMethods0(modifiers, ctClass, ctMethod, i, z);
            }
        }
    }

    private void processMethods0(int i, CtClass ctClass, CtMethod ctMethod, int i2, boolean z) throws CannotCompileException, NotFoundException {
        CtMethod delegator;
        CtMethod ctMethod2;
        String name = ctMethod.getName();
        if (isExcluded(name)) {
            return;
        }
        if (ctMethod.getDeclaringClass() == ctClass) {
            if (Modifier.isNative(i)) {
                return;
            }
            if (Modifier.isFinal(i)) {
                i &= -17;
                ctMethod.setModifiers(i);
            }
            delegator = ctMethod;
        } else if (Modifier.isFinal(i)) {
            return;
        } else {
            i &= -257;
            delegator = CtNewMethod.delegator(findOriginal(ctMethod, z), ctClass);
            delegator.setModifiers(i);
            ctClass.addMethod(delegator);
        }
        delegator.setName("_m_" + i2 + "_" + name);
        if (Modifier.isStatic(i)) {
            ctMethod2 = this.trapStaticMethod;
        } else {
            ctMethod2 = this.trapMethod;
        }
        CtMethod wrapped = CtNewMethod.wrapped(ctMethod.getReturnType(), name, ctMethod.getParameterTypes(), ctMethod.getExceptionTypes(), ctMethod2, CtMethod.ConstParameter.integer(i2), ctClass);
        wrapped.setModifiers(i);
        ctClass.addMethod(wrapped);
    }

    private CtMethod findOriginal(CtMethod ctMethod, boolean z) throws NotFoundException {
        if (z) {
            return ctMethod;
        }
        String name = ctMethod.getName();
        CtMethod[] declaredMethods = ctMethod.getDeclaringClass().getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            String name2 = declaredMethods[i].getName();
            if (name2.endsWith(name) && name2.startsWith("_m_") && declaredMethods[i].getSignature().equals(ctMethod.getSignature())) {
                return declaredMethods[i];
            }
        }
        return ctMethod;
    }

    private void processFields(CtClass ctClass) throws CannotCompileException, NotFoundException {
        CtField[] declaredFields;
        for (CtField ctField : ctClass.getDeclaredFields()) {
            int modifiers = ctField.getModifiers();
            if ((modifiers & 1) != 0 && (modifiers & 16) == 0) {
                int i = modifiers | 8;
                String name = ctField.getName();
                CtClass type = ctField.getType();
                CtMethod wrapped = CtNewMethod.wrapped(type, readPrefix + name, this.readParam, null, this.trapRead, CtMethod.ConstParameter.string(name), ctClass);
                wrapped.setModifiers(i);
                ctClass.addMethod(wrapped);
                CtMethod wrapped2 = CtNewMethod.wrapped(CtClass.voidType, writePrefix + name, new CtClass[]{this.classPool.get("java.lang.Object"), type}, null, this.trapWrite, CtMethod.ConstParameter.string(name), ctClass);
                wrapped2.setModifiers(i);
                ctClass.addMethod(wrapped2);
            }
        }
    }

    public void rebuildClassFile(ClassFile classFile) throws BadBytecode {
        if (ClassFile.MAJOR_VERSION < 50) {
            return;
        }
        for (MethodInfo methodInfo : classFile.getMethods()) {
            methodInfo.rebuildStackMap(this.classPool);
        }
    }
}
