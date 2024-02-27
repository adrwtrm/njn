package dagger.hilt.android.plugin;

import dagger.hilt.android.plugin.util.FilesKt;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.ByteArray;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: AndroidEntryPointClassTransformer.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 %2\u00020\u0001:\u0001%B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0006J\u000e\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0006J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0003H\u0002J \u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0003H\u0002J1\u0010\u001f\u001a\u00020\u001a*\u00020 2#\u0010!\u001a\u001f\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u001a0\"¢\u0006\u0002\b$H\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006&"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassTransformer;", "", "taskName", "", "allInputs", "", "Ljava/io/File;", "sourceRootOutputDir", "copyNonTransformed", "", "(Ljava/lang/String;Ljava/util/List;Ljava/io/File;Z)V", "classPool", "Ljavassist/ClassPool;", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "getTaskName", "()Ljava/lang/String;", "transformClass", "clazz", "Ljavassist/CtClass;", "transformClassToOutput", "transformFile", "inputFile", "transformJarContents", "transformOnReceive", "", "entryPointSuperclassName", "transformSuperMethodCalls", "oldSuperclassName", "newSuperclassName", "forEachInstruction", "Ljavassist/bytecode/CodeIterator;", "body", "Lkotlin/Function3;", "", "Lkotlin/ExtensionFunctionType;", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AndroidEntryPointClassTransformer {
    private final ClassPool classPool;
    private final boolean copyNonTransformed;
    private final Logger logger;
    private final File sourceRootOutputDir;
    private final String taskName;
    public static final Companion Companion = new Companion(null);
    private static final Set<String> ANDROID_ENTRY_POINT_ANNOTATIONS = SetsKt.setOf((Object[]) new String[]{"dagger.hilt.android.AndroidEntryPoint", "dagger.hilt.android.HiltAndroidApp"});
    private static final String ON_RECEIVE_METHOD_NAME = AndroidEntryPointClassVisitor.ON_RECEIVE_METHOD_NAME;
    private static final String ON_RECEIVE_METHOD_SIGNATURE = AndroidEntryPointClassVisitor.ON_RECEIVE_METHOD_DESCRIPTOR;

    public AndroidEntryPointClassTransformer(String taskName, List<? extends File> allInputs, File sourceRootOutputDir, boolean z) {
        Intrinsics.checkNotNullParameter(taskName, "taskName");
        Intrinsics.checkNotNullParameter(allInputs, "allInputs");
        Intrinsics.checkNotNullParameter(sourceRootOutputDir, "sourceRootOutputDir");
        this.taskName = taskName;
        this.sourceRootOutputDir = sourceRootOutputDir;
        this.copyNonTransformed = z;
        this.logger = LoggerFactory.getLogger(AndroidEntryPointClassTransformer.class);
        ClassPool classPool = new ClassPool(true);
        for (File file : allInputs) {
            classPool.appendClassPath(file.getPath());
        }
        Unit unit = Unit.INSTANCE;
        this.classPool = classPool;
        this.sourceRootOutputDir.mkdirs();
    }

    public static final /* synthetic */ Logger access$getLogger$p(AndroidEntryPointClassTransformer androidEntryPointClassTransformer) {
        return androidEntryPointClassTransformer.logger;
    }

    public final String getTaskName() {
        return this.taskName;
    }

    public final boolean transformJarContents(File inputFile) {
        Intrinsics.checkNotNullParameter(inputFile, "inputFile");
        if (!FilesKt.isJarFile(inputFile)) {
            throw new IllegalArgumentException(("Invalid file, '" + inputFile + "' is not a jar.").toString());
        }
        if (!(!this.copyNonTransformed)) {
            throw new IllegalStateException("Transforming a jar is not supported with 'copyNonTransformed'.".toString());
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputFile));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            boolean z = false;
            for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                if (FilesKt.isClassFile(nextEntry)) {
                    CtClass clazz = this.classPool.makeClass((InputStream) zipInputStream2, false);
                    Intrinsics.checkNotNullExpressionValue(clazz, "clazz");
                    if (!transformClassToOutput(clazz) && !z) {
                        z = false;
                        clazz.detach();
                    }
                    z = true;
                    clazz.detach();
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream, null);
            return z;
        } finally {
        }
    }

    public final boolean transformFile(File inputFile) {
        Intrinsics.checkNotNullParameter(inputFile, "inputFile");
        if (!FilesKt.isClassFile(inputFile)) {
            throw new IllegalStateException(("Invalid file, '" + inputFile + "' is not a class.").toString());
        }
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        try {
            CtClass clazz = this.classPool.makeClass((InputStream) fileInputStream, false);
            CloseableKt.closeFinally(fileInputStream, null);
            Intrinsics.checkNotNullExpressionValue(clazz, "clazz");
            boolean transformClassToOutput = transformClassToOutput(clazz);
            clazz.detach();
            return transformClassToOutput;
        } finally {
        }
    }

    private final boolean transformClassToOutput(CtClass ctClass) {
        boolean transformClass = transformClass(ctClass);
        if (transformClass || this.copyNonTransformed) {
            ctClass.writeFile(this.sourceRootOutputDir.getPath());
        }
        return transformClass;
    }

    private final boolean transformClass(CtClass ctClass) {
        boolean z;
        Set<String> set = ANDROID_ENTRY_POINT_ANNOTATIONS;
        boolean z2 = false;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            for (String str : set) {
                if (ctClass.hasAnnotation(str)) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        if (z) {
            return false;
        }
        String superclassName = ctClass.getClassFile().getSuperclass();
        StringBuilder append = new StringBuilder().append(ctClass.getPackageName()).append(".Hilt_");
        String simpleName = ctClass.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "clazz.simpleName");
        String sb = append.append(StringsKt.replace$default(simpleName, "$", "_", false, 4, (Object) null)).toString();
        this.logger.info("[" + this.taskName + "] Transforming " + ((Object) ctClass.getName()) + " to extend " + sb + " instead of " + ((Object) superclassName) + '.');
        CtClass ctClass2 = this.classPool.get(sb);
        ctClass.setSuperclass(ctClass2);
        Intrinsics.checkNotNullExpressionValue(superclassName, "superclassName");
        transformSuperMethodCalls(ctClass, superclassName, sb);
        CtField[] declaredFields = ctClass2.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "entryPointSuperclass.declaredFields");
        CtField[] ctFieldArr = declaredFields;
        int length = ctFieldArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (Intrinsics.areEqual(ctFieldArr[i].getName(), "onReceiveBytecodeInjectionMarker")) {
                z2 = true;
                break;
            } else {
                i++;
            }
        }
        if (z2) {
            transformOnReceive(ctClass, sb);
        }
        return true;
    }

    private final void transformSuperMethodCalls(final CtClass ctClass, final String str, final String str2) {
        CtMethod[] ctMethodArr;
        final ConstPool constPool = ctClass.getClassFile().getConstPool();
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "clazz.declaredMethods");
        ArrayList<CtMethod> arrayList = new ArrayList();
        for (CtMethod ctMethod : declaredMethods) {
            CtMethod ctMethod2 = ctMethod;
            if ((!ctMethod2.getMethodInfo().isMethod() || Modifier.isStatic(ctMethod2.getModifiers()) || Modifier.isAbstract(ctMethod2.getModifiers()) || Modifier.isNative(ctMethod2.getModifiers())) ? false : true) {
                arrayList.add(ctMethod);
            }
        }
        for (final CtMethod ctMethod3 : arrayList) {
            CodeAttribute codeAttribute = ctMethod3.getMethodInfo().getCodeAttribute();
            final byte[] code = codeAttribute.getCode();
            CodeIterator it = codeAttribute.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "codeAttr.iterator()");
            forEachInstruction(it, new Function3<CodeIterator, Integer, Integer, Unit>() { // from class: dagger.hilt.android.plugin.AndroidEntryPointClassTransformer$transformSuperMethodCalls$2$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(CodeIterator codeIterator, Integer num, Integer num2) {
                    invoke(codeIterator, num.intValue(), num2.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(CodeIterator forEachInstruction, int i, int i2) {
                    Intrinsics.checkNotNullParameter(forEachInstruction, "$this$forEachInstruction");
                    if (i2 != 183) {
                        return;
                    }
                    int i3 = i + 1;
                    int readU16bit = ByteArray.readU16bit(code, i3);
                    if (Intrinsics.areEqual(constPool.getMethodrefClassName(readU16bit), str) && !Intrinsics.areEqual(constPool.getMethodrefName(readU16bit), "<init>")) {
                        int methodrefNameAndType = constPool.getMethodrefNameAndType(readU16bit);
                        int addMethodrefInfo = constPool.addMethodrefInfo(constPool.addClassInfo(str2), methodrefNameAndType);
                        AndroidEntryPointClassTransformer.access$getLogger$p(this).info("[" + this.getTaskName() + "] Redirecting an invokespecial in " + ((Object) ctClass.getName()) + '.' + ((Object) ctMethod3.getName()) + ':' + ((Object) ctMethod3.getSignature()) + " at code index " + i + " from method ref #" + readU16bit + " to #" + addMethodrefInfo + '.');
                        ByteArray.write16bit(addMethodrefInfo, code, i3);
                    }
                }
            });
        }
    }

    private final void forEachInstruction(CodeIterator codeIterator, Function3<? super CodeIterator, ? super Integer, ? super Integer, Unit> function3) {
        while (codeIterator.hasNext()) {
            int next = codeIterator.next();
            function3.invoke(codeIterator, Integer.valueOf(next), Integer.valueOf(codeIterator.byteAt(next)));
        }
    }

    private final void transformOnReceive(CtClass ctClass, String str) {
        CtMethod[] ctMethodArr;
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "clazz.declaredMethods");
        for (CtMethod ctMethod : declaredMethods) {
            String stringPlus = Intrinsics.stringPlus(ctMethod.getName(), ctMethod.getSignature());
            String str2 = ON_RECEIVE_METHOD_NAME;
            String str3 = ON_RECEIVE_METHOD_SIGNATURE;
            if (Intrinsics.areEqual(stringPlus, Intrinsics.stringPlus(str2, str3))) {
                Bytecode bytecode = new Bytecode(ctClass.getClassFile().getConstPool());
                bytecode.addAload(0);
                bytecode.addAload(1);
                bytecode.addAload(2);
                bytecode.addInvokespecial(str, str2, str3);
                CodeAttribute codeAttribute = bytecode.toCodeAttribute();
                CodeAttribute codeAttribute2 = ctMethod.getMethodInfo().getCodeAttribute();
                codeAttribute2.setMaxStack(Math.max(codeAttribute.getMaxStack(), codeAttribute2.getMaxStack()));
                codeAttribute2.setMaxLocals(Math.max(codeAttribute.getMaxLocals(), codeAttribute2.getMaxLocals()));
                CodeIterator it = codeAttribute2.iterator();
                it.insert(codeAttribute.getExceptionTable(), it.insertEx(bytecode.get()));
                ctMethod.getMethodInfo().rebuildStackMap(ctClass.getClassPool());
                return;
            }
        }
        throw new NoSuchElementException("Array contains no element matching the predicate.");
    }

    /* compiled from: AndroidEntryPointClassTransformer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n¨\u0006\r"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassTransformer$Companion;", "", "()V", "ANDROID_ENTRY_POINT_ANNOTATIONS", "", "", "getANDROID_ENTRY_POINT_ANNOTATIONS", "()Ljava/util/Set;", "ON_RECEIVE_METHOD_NAME", "getON_RECEIVE_METHOD_NAME", "()Ljava/lang/String;", "ON_RECEIVE_METHOD_SIGNATURE", "getON_RECEIVE_METHOD_SIGNATURE", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<String> getANDROID_ENTRY_POINT_ANNOTATIONS() {
            return AndroidEntryPointClassTransformer.ANDROID_ENTRY_POINT_ANNOTATIONS;
        }

        public final String getON_RECEIVE_METHOD_NAME() {
            return AndroidEntryPointClassTransformer.ON_RECEIVE_METHOD_NAME;
        }

        public final String getON_RECEIVE_METHOD_SIGNATURE() {
            return AndroidEntryPointClassTransformer.ON_RECEIVE_METHOD_SIGNATURE;
        }
    }
}
