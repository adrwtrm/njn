package dagger.hilt.android.plugin;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/* compiled from: AndroidEntryPointClassVisitor.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 %2\u00020\u0001:\u0005$%&'(B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\tH\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002JK\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\t2\b\u0010\u001b\u001a\u0004\u0018\u00010\t2\u0010\u0010\u001c\u001a\f\u0012\u0006\b\u0001\u0012\u00020\t\u0018\u00010\u001dH\u0016¢\u0006\u0002\u0010\u001eJA\u0010\u001f\u001a\u00020 2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010!\u001a\u00020\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\t2\u0010\u0010\"\u001a\f\u0012\u0006\b\u0001\u0012\u00020\t\u0018\u00010\u001dH\u0016¢\u0006\u0002\u0010#R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r¨\u0006)"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor;", "Lorg/objectweb/asm/ClassVisitor;", "apiVersion", "", "nextClassVisitor", "additionalClasses", "Ljava/io/File;", "(ILorg/objectweb/asm/ClassVisitor;Ljava/io/File;)V", "newSuperclassName", "", "getNewSuperclassName", "()Ljava/lang/String;", "setNewSuperclassName", "(Ljava/lang/String;)V", "oldSuperclassName", "getOldSuperclassName", "setOldSuperclassName", "findAdditionalClassFile", "className", "hasOnReceiveBytecodeInjectionMarker", "", "visit", "", "version", "access", AppMeasurementSdk.ConditionalUserProperty.NAME, "signature", "superName", "interfaces", "", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "visitMethod", "Lorg/objectweb/asm/MethodVisitor;", "descriptor", "exceptions", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/objectweb/asm/MethodVisitor;", "AndroidEntryPointParams", "Companion", "Factory", "InvokeSpecialAdapter", "OnReceiveAdapter", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AndroidEntryPointClassVisitor extends ClassVisitor {
    public static final String ON_RECEIVE_METHOD_DESCRIPTOR = "(Landroid/content/Context;Landroid/content/Intent;)V";
    public static final String ON_RECEIVE_METHOD_NAME = "onReceive";
    private final File additionalClasses;
    private final int apiVersion;
    public String newSuperclassName;
    public String oldSuperclassName;
    public static final Companion Companion = new Companion(null);
    private static final Set<String> ANDROID_ENTRY_POINT_ANNOTATIONS = SetsKt.setOf((Object[]) new String[]{"dagger.hilt.android.AndroidEntryPoint", "dagger.hilt.android.HiltAndroidApp"});

    /* compiled from: AndroidEntryPointClassVisitor.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor$AndroidEntryPointParams;", "Lcom/android/build/api/instrumentation/InstrumentationParameters;", "additionalClassesDir", "Lorg/gradle/api/provider/Property;", "Ljava/io/File;", "getAdditionalClassesDir", "()Lorg/gradle/api/provider/Property;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public interface AndroidEntryPointParams extends InstrumentationParameters {
        @Internal
        Property<File> getAdditionalClassesDir();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidEntryPointClassVisitor(int i, ClassVisitor nextClassVisitor, File additionalClasses) {
        super(i, nextClassVisitor);
        Intrinsics.checkNotNullParameter(nextClassVisitor, "nextClassVisitor");
        Intrinsics.checkNotNullParameter(additionalClasses, "additionalClasses");
        this.apiVersion = i;
        this.additionalClasses = additionalClasses;
    }

    /* compiled from: AndroidEntryPointClassVisitor.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor$Factory;", "Lcom/android/build/api/instrumentation/AsmClassVisitorFactory;", "Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor$AndroidEntryPointParams;", "()V", "createClassVisitor", "Lorg/objectweb/asm/ClassVisitor;", "classContext", "Lcom/android/build/api/instrumentation/ClassContext;", "nextClassVisitor", "isInstrumentable", "", "classData", "Lcom/android/build/api/instrumentation/ClassData;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static abstract class Factory implements AsmClassVisitorFactory<AndroidEntryPointParams> {
        public ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor nextClassVisitor) {
            Intrinsics.checkNotNullParameter(classContext, "classContext");
            Intrinsics.checkNotNullParameter(nextClassVisitor, "nextClassVisitor");
            Object obj = getInstrumentationContext().getApiVersion().get();
            Intrinsics.checkNotNullExpressionValue(obj, "instrumentationContext.apiVersion.get()");
            int intValue = ((Number) obj).intValue();
            Object obj2 = ((AndroidEntryPointParams) getParameters().get()).getAdditionalClassesDir().get();
            Intrinsics.checkNotNullExpressionValue(obj2, "parameters.get().additionalClassesDir.get()");
            return new AndroidEntryPointClassVisitor(intValue, nextClassVisitor, (File) obj2);
        }

        public boolean isInstrumentable(ClassData classData) {
            Intrinsics.checkNotNullParameter(classData, "classData");
            List<String> classAnnotations = classData.getClassAnnotations();
            if ((classAnnotations instanceof Collection) && classAnnotations.isEmpty()) {
                return false;
            }
            for (String str : classAnnotations) {
                if (AndroidEntryPointClassVisitor.Companion.getANDROID_ENTRY_POINT_ANNOTATIONS().contains(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public final String getNewSuperclassName() {
        String str = this.newSuperclassName;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("newSuperclassName");
        return null;
    }

    public final void setNewSuperclassName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.newSuperclassName = str;
    }

    public final String getOldSuperclassName() {
        String str = this.oldSuperclassName;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("oldSuperclassName");
        return null;
    }

    public final void setOldSuperclassName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.oldSuperclassName = str;
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visit(int i, int i2, final String name, String str, String str2, String[] strArr) {
        Intrinsics.checkNotNullParameter(name, "name");
        setNewSuperclassName(StringsKt.substringBeforeLast$default(name, '/', (String) null, 2, (Object) null) + "/Hilt_" + StringsKt.replace$default(StringsKt.substringAfterLast$default(name, '/', (String) null, 2, (Object) null), "$", "_", false, 4, (Object) null));
        if (str2 != null) {
            setOldSuperclassName(str2);
            super.visit(i, i2, name, str == null ? null : StringsKt.replaceFirst$default(str, getOldSuperclassName(), getNewSuperclassName(), false, 4, (Object) null), getNewSuperclassName(), strArr);
            return;
        }
        throw new IllegalStateException(new Function0<String>() { // from class: dagger.hilt.android.plugin.AndroidEntryPointClassVisitor$visit$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "Superclass of " + name + " is null!";
            }
        }.toString());
    }

    @Override // org.objectweb.asm.ClassVisitor
    public MethodVisitor visitMethod(int i, String name, String descriptor, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        MethodVisitor nextMethodVisitor = super.visitMethod(i, name, descriptor, str, strArr);
        int i2 = this.apiVersion;
        Intrinsics.checkNotNullExpressionValue(nextMethodVisitor, "nextMethodVisitor");
        InvokeSpecialAdapter invokeSpecialAdapter = new InvokeSpecialAdapter(this, i2, nextMethodVisitor, Intrinsics.areEqual(name, "<init>"));
        if (Intrinsics.areEqual(name, ON_RECEIVE_METHOD_NAME) && Intrinsics.areEqual(descriptor, ON_RECEIVE_METHOD_DESCRIPTOR) && hasOnReceiveBytecodeInjectionMarker()) {
            return new OnReceiveAdapter(this, this.apiVersion, invokeSpecialAdapter);
        }
        return invokeSpecialAdapter;
    }

    /* compiled from: AndroidEntryPointClassVisitor.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J0\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor$InvokeSpecialAdapter;", "Lorg/objectweb/asm/MethodVisitor;", "apiVersion", "", "nextClassVisitor", "isConstructor", "", "(Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor;ILorg/objectweb/asm/MethodVisitor;Z)V", "visitedSuperConstructorInvokeSpecial", "getAdaptedOwner", "", "methodRefName", "visitMethodInsn", "", "opcode", "owner", AppMeasurementSdk.ConditionalUserProperty.NAME, "descriptor", "isInterface", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public final class InvokeSpecialAdapter extends MethodVisitor {
        private final boolean isConstructor;
        final /* synthetic */ AndroidEntryPointClassVisitor this$0;
        private boolean visitedSuperConstructorInvokeSpecial;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InvokeSpecialAdapter(AndroidEntryPointClassVisitor this$0, int i, MethodVisitor nextClassVisitor, boolean z) {
            super(i, nextClassVisitor);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(nextClassVisitor, "nextClassVisitor");
            this.this$0 = this$0;
            this.isConstructor = z;
        }

        @Override // org.objectweb.asm.MethodVisitor
        public void visitMethodInsn(int i, String owner, String name, String descriptor, boolean z) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (i == 183 && Intrinsics.areEqual(owner, this.this$0.getOldSuperclassName())) {
                String adaptedOwner = getAdaptedOwner(name);
                super.visitMethodInsn(i, adaptedOwner == null ? owner : adaptedOwner, name, descriptor, z);
                return;
            }
            super.visitMethodInsn(i, owner, name, descriptor, z);
        }

        private final String getAdaptedOwner(String str) {
            if (Intrinsics.areEqual(str, "<init>") && this.isConstructor && !this.visitedSuperConstructorInvokeSpecial) {
                this.visitedSuperConstructorInvokeSpecial = true;
                return this.this$0.getNewSuperclassName();
            } else if (Intrinsics.areEqual(str, "<init>")) {
                return null;
            } else {
                return this.this$0.getNewSuperclassName();
            }
        }
    }

    /* compiled from: AndroidEntryPointClassVisitor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor$OnReceiveAdapter;", "Lorg/objectweb/asm/MethodVisitor;", "apiVersion", "", "nextClassVisitor", "(Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor;ILorg/objectweb/asm/MethodVisitor;)V", "visitCode", "", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public final class OnReceiveAdapter extends MethodVisitor {
        final /* synthetic */ AndroidEntryPointClassVisitor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnReceiveAdapter(AndroidEntryPointClassVisitor this$0, int i, MethodVisitor nextClassVisitor) {
            super(i, nextClassVisitor);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(nextClassVisitor, "nextClassVisitor");
            this.this$0 = this$0;
        }

        @Override // org.objectweb.asm.MethodVisitor
        public void visitCode() {
            super.visitCode();
            super.visitIntInsn(25, 0);
            super.visitIntInsn(25, 1);
            super.visitIntInsn(25, 2);
            super.visitMethodInsn(183, this.this$0.getNewSuperclassName(), AndroidEntryPointClassVisitor.ON_RECEIVE_METHOD_NAME, AndroidEntryPointClassVisitor.ON_RECEIVE_METHOD_DESCRIPTOR, false);
        }
    }

    private final boolean hasOnReceiveBytecodeInjectionMarker() {
        FileInputStream fileInputStream = new FileInputStream(findAdditionalClassFile(getNewSuperclassName()));
        try {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            new ClassReader(fileInputStream).accept(new ClassVisitor(this.apiVersion) { // from class: dagger.hilt.android.plugin.AndroidEntryPointClassVisitor$hasOnReceiveBytecodeInjectionMarker$1$1
                @Override // org.objectweb.asm.ClassVisitor
                public FieldVisitor visitField(int i, String name, String descriptor, String str, Object obj) {
                    Intrinsics.checkNotNullParameter(name, "name");
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    if (Intrinsics.areEqual(name, "onReceiveBytecodeInjectionMarker")) {
                        Ref.BooleanRef.this.element = true;
                        return null;
                    }
                    return null;
                }
            }, 7);
            boolean z = booleanRef.element;
            CloseableKt.closeFinally(fileInputStream, null);
            return z;
        } finally {
        }
    }

    private final File findAdditionalClassFile(String str) {
        return new File(this.additionalClasses, Intrinsics.stringPlus(str, ".class"));
    }

    /* compiled from: AndroidEntryPointClassVisitor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointClassVisitor$Companion;", "", "()V", "ANDROID_ENTRY_POINT_ANNOTATIONS", "", "", "getANDROID_ENTRY_POINT_ANNOTATIONS", "()Ljava/util/Set;", "ON_RECEIVE_METHOD_DESCRIPTOR", "ON_RECEIVE_METHOD_NAME", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<String> getANDROID_ENTRY_POINT_ANNOTATIONS() {
            return AndroidEntryPointClassVisitor.ANDROID_ENTRY_POINT_ANNOTATIONS;
        }
    }
}
