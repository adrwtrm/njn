package javassist.tools.rmi;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.Translator;

/* loaded from: classes2.dex */
public class StubGenerator implements Translator {
    private static final String accessorObjectId = "_getObjectId";
    private static final String fieldImporter = "importer";
    private static final String fieldObjectId = "objectId";
    private static final String sampleClass = "javassist.tools.rmi.Sample";
    private ClassPool classPool;
    private CtClass[] exceptionForProxy;
    private CtMethod forwardMethod;
    private CtMethod forwardStaticMethod;
    private CtClass[] interfacesForProxy;
    private Map<String, CtClass> proxyClasses = new Hashtable();
    private CtClass[] proxyConstructorParamTypes;

    @Override // javassist.Translator
    public void onLoad(ClassPool classPool, String str) {
    }

    @Override // javassist.Translator
    public void start(ClassPool classPool) throws NotFoundException {
        this.classPool = classPool;
        CtClass ctClass = classPool.get(sampleClass);
        this.forwardMethod = ctClass.getDeclaredMethod("forward");
        this.forwardStaticMethod = ctClass.getDeclaredMethod("forwardStatic");
        this.proxyConstructorParamTypes = classPool.get(new String[]{"javassist.tools.rmi.ObjectImporter", "int"});
        this.interfacesForProxy = classPool.get(new String[]{"java.io.Serializable", "javassist.tools.rmi.Proxy"});
        this.exceptionForProxy = new CtClass[]{classPool.get("javassist.tools.rmi.RemoteException")};
    }

    public boolean isProxyClass(String str) {
        return this.proxyClasses.get(str) != null;
    }

    public synchronized boolean makeProxyClass(Class<?> cls) throws CannotCompileException, NotFoundException {
        String name = cls.getName();
        if (this.proxyClasses.get(name) != null) {
            return false;
        }
        CtClass produceProxyClass = produceProxyClass(this.classPool.get(name), cls);
        this.proxyClasses.put(name, produceProxyClass);
        modifySuperclass(produceProxyClass);
        return true;
    }

    private CtClass produceProxyClass(CtClass ctClass, Class<?> cls) throws CannotCompileException, NotFoundException {
        int modifiers = ctClass.getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isNative(modifiers) || !Modifier.isPublic(modifiers)) {
            throw new CannotCompileException(ctClass.getName() + " must be public, non-native, and non-abstract.");
        }
        CtClass makeClass = this.classPool.makeClass(ctClass.getName(), ctClass.getSuperclass());
        makeClass.setInterfaces(this.interfacesForProxy);
        CtField ctField = new CtField(this.classPool.get("javassist.tools.rmi.ObjectImporter"), fieldImporter, makeClass);
        ctField.setModifiers(2);
        makeClass.addField(ctField, CtField.Initializer.byParameter(0));
        CtField ctField2 = new CtField(CtClass.intType, fieldObjectId, makeClass);
        ctField2.setModifiers(2);
        makeClass.addField(ctField2, CtField.Initializer.byParameter(1));
        makeClass.addMethod(CtNewMethod.getter(accessorObjectId, ctField2));
        makeClass.addConstructor(CtNewConstructor.defaultConstructor(makeClass));
        makeClass.addConstructor(CtNewConstructor.skeleton(this.proxyConstructorParamTypes, null, makeClass));
        try {
            addMethods(makeClass, cls.getMethods());
            return makeClass;
        } catch (SecurityException e) {
            throw new CannotCompileException(e);
        }
    }

    private CtClass toCtClass(Class<?> cls) throws NotFoundException {
        String stringBuffer;
        if (!cls.isArray()) {
            stringBuffer = cls.getName();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            do {
                stringBuffer2.append("[]");
                cls = cls.getComponentType();
            } while (cls.isArray());
            stringBuffer2.insert(0, cls.getName());
            stringBuffer = stringBuffer2.toString();
        }
        return this.classPool.get(stringBuffer);
    }

    private CtClass[] toCtClass(Class<?>[] clsArr) throws NotFoundException {
        int length = clsArr.length;
        CtClass[] ctClassArr = new CtClass[length];
        for (int i = 0; i < length; i++) {
            ctClassArr[i] = toCtClass(clsArr[i]);
        }
        return ctClassArr;
    }

    private void addMethods(CtClass ctClass, Method[] methodArr) throws CannotCompileException, NotFoundException {
        CtMethod ctMethod;
        for (int i = 0; i < methodArr.length; i++) {
            Method method = methodArr[i];
            int modifiers = method.getModifiers();
            if (method.getDeclaringClass() != Object.class && !Modifier.isFinal(modifiers)) {
                if (Modifier.isPublic(modifiers)) {
                    if (Modifier.isStatic(modifiers)) {
                        ctMethod = this.forwardStaticMethod;
                    } else {
                        ctMethod = this.forwardMethod;
                    }
                    CtMethod wrapped = CtNewMethod.wrapped(toCtClass(method.getReturnType()), method.getName(), toCtClass(method.getParameterTypes()), this.exceptionForProxy, ctMethod, CtMethod.ConstParameter.integer(i), ctClass);
                    wrapped.setModifiers(modifiers);
                    ctClass.addMethod(wrapped);
                } else if (!Modifier.isProtected(modifiers) && !Modifier.isPrivate(modifiers)) {
                    throw new CannotCompileException("the methods must be public, protected, or private.");
                }
            }
        }
    }

    private void modifySuperclass(CtClass ctClass) throws CannotCompileException, NotFoundException {
        while (true) {
            ctClass = ctClass.getSuperclass();
            if (ctClass == null) {
                return;
            }
            try {
                ctClass.getDeclaredConstructor(null);
                return;
            } catch (NotFoundException unused) {
                ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
            }
        }
    }
}
