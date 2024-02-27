package dagger.hilt.android.plugin.util;

import com.android.build.api.AndroidPluginVersion;
import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.ApplicationVariant;
import com.android.build.api.variant.Component;
import com.android.build.api.variant.LibraryVariant;
import com.android.build.api.variant.Variant;
import com.android.build.api.variant.VariantSelector;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat;
import dagger.hilt.android.plugin.util.ComponentCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.gradle.api.Project;

/* compiled from: AndroidGradleCompat.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \n2\u00020\u0001:\u0003\b\t\nB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0006H&\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat;", "", "()V", "onAllVariants", "", "block", "Lkotlin/Function1;", "Ldagger/hilt/android/plugin/util/ComponentCompat;", "Api42Impl", "Api70Impl", "Companion", "Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat$Api70Impl;", "Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat$Api42Impl;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public abstract class AndroidComponentsExtensionCompat {
    public static final Companion Companion = new Companion(null);

    public /* synthetic */ AndroidComponentsExtensionCompat(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void onAllVariants(Function1<? super ComponentCompat, Unit> function1);

    private AndroidComponentsExtensionCompat() {
    }

    /* compiled from: AndroidGradleCompat.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\r0\u0006H\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R)\u0010\u0005\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat$Api70Impl;", "Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat;", "actual", "Lcom/android/build/api/variant/AndroidComponentsExtension;", "(Lcom/android/build/api/variant/AndroidComponentsExtension;)V", "componentInit", "Lkotlin/Function1;", "Lcom/android/build/api/variant/Component;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "component", "Ldagger/hilt/android/plugin/util/ComponentCompat;", "onAllVariants", "", "block", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Api70Impl extends AndroidComponentsExtensionCompat {
        private final AndroidComponentsExtension<?, ?, ?> actual;
        private final Function1<Component, ComponentCompat> componentInit;

        public static final /* synthetic */ AndroidComponentsExtension access$getActual$p(Api70Impl api70Impl) {
            return api70Impl.actual;
        }

        public static final /* synthetic */ Function1 access$getComponentInit$p(Api70Impl api70Impl) {
            return api70Impl.componentInit;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Api70Impl(AndroidComponentsExtension<?, ?, ?> actual) {
            super(null);
            Intrinsics.checkNotNullParameter(actual, "actual");
            this.actual = actual;
            this.componentInit = new Function1<Component, ComponentCompat>() { // from class: dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat$Api70Impl$componentInit$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ComponentCompat invoke(Component it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (AndroidComponentsExtensionCompat.Api70Impl.access$getActual$p(AndroidComponentsExtensionCompat.Api70Impl.this).getPluginVersion().compareTo(new AndroidPluginVersion(7, 2)) < 0) {
                        return new ComponentCompat.Api70Impl(it);
                    }
                    return new ComponentCompat.Api72Impl(it);
                }
            };
        }

        @Override // dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat
        public void onAllVariants(final Function1<? super ComponentCompat, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            AndroidComponentsExtension.DefaultImpls.onVariants$default(this.actual, (VariantSelector) null, new Function1<Variant, Unit>() { // from class: dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat$Api70Impl$onAllVariants$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Variant variant) {
                    invoke2(variant);
                    return Unit.INSTANCE;
                }

                private static final Component invoke$getAndroidTest(ApplicationVariant applicationVariant) {
                    Object invoke = applicationVariant.getClass().getDeclaredMethod("getAndroidTest", new Class[0]).invoke(applicationVariant, new Object[0]);
                    if (invoke instanceof Component) {
                        return (Component) invoke;
                    }
                    return null;
                }

                /* renamed from: invoke$getAndroidTest-0  reason: not valid java name */
                private static final Component m332invoke$getAndroidTest0(LibraryVariant libraryVariant) {
                    Object invoke = libraryVariant.getClass().getDeclaredMethod("getAndroidTest", new Class[0]).invoke(libraryVariant, new Object[0]);
                    if (invoke instanceof Component) {
                        return (Component) invoke;
                    }
                    return null;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(Variant variant) {
                    Component m332invoke$getAndroidTest0;
                    Intrinsics.checkNotNullParameter(variant, "variant");
                    block.invoke(AndroidComponentsExtensionCompat.Api70Impl.access$getComponentInit$p(this).invoke(variant));
                    if (variant instanceof ApplicationVariant) {
                        m332invoke$getAndroidTest0 = invoke$getAndroidTest((ApplicationVariant) variant);
                    } else {
                        m332invoke$getAndroidTest0 = variant instanceof LibraryVariant ? m332invoke$getAndroidTest0((LibraryVariant) variant) : null;
                    }
                    if (m332invoke$getAndroidTest0 != null) {
                        block.invoke(AndroidComponentsExtensionCompat.Api70Impl.access$getComponentInit$p(this).invoke(m332invoke$getAndroidTest0));
                    }
                    Component invoke$getUnitTest = invoke$getUnitTest(variant);
                    if (invoke$getUnitTest == null) {
                        return;
                    }
                    block.invoke(AndroidComponentsExtensionCompat.Api70Impl.access$getComponentInit$p(this).invoke(invoke$getUnitTest));
                }

                private static final Component invoke$getUnitTest(Variant variant) {
                    Object invoke = variant.getClass().getDeclaredMethod("getUnitTest", new Class[0]).invoke(variant, new Object[0]);
                    if (invoke instanceof Component) {
                        return (Component) invoke;
                    }
                    return null;
                }
            }, 1, (Object) null);
        }
    }

    /* compiled from: AndroidGradleCompat.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0002\b\u0003 \u0007*\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0002\b\u0003 \u0007*\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat$Api42Impl;", "Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat;", "actual", "", "(Ljava/lang/Object;)V", "extensionClazz", "Ljava/lang/Class;", "kotlin.jvm.PlatformType", "variantSelectorClazz", "onAllVariants", "", "block", "Lkotlin/Function1;", "Ldagger/hilt/android/plugin/util/ComponentCompat;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Api42Impl extends AndroidComponentsExtensionCompat {
        private final Object actual;
        private final Class<?> extensionClazz;
        private final Class<?> variantSelectorClazz;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Api42Impl(Object actual) {
            super(null);
            Intrinsics.checkNotNullParameter(actual, "actual");
            this.actual = actual;
            this.extensionClazz = Class.forName("com.android.build.api.extension.AndroidComponentsExtension");
            this.variantSelectorClazz = Class.forName("com.android.build.api.extension.VariantSelector");
        }

        @Override // dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat
        public void onAllVariants(final Function1<? super ComponentCompat, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            Object invoke = this.variantSelectorClazz.getDeclaredMethod("all", new Class[0]).invoke(this.extensionClazz.getDeclaredMethod("selector", new Class[0]).invoke(this.actual, new Object[0]), new Object[0]);
            Function1<Object, Unit> function1 = new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat$Api42Impl$onAllVariants$wrapFunction$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                    invoke2(obj);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(Object it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    block.invoke(new ComponentCompat.Api42Impl(it));
                }
            };
            for (String str : CollectionsKt.listOf((Object[]) new String[]{"onVariants", "androidTests", "unitTests"})) {
                this.extensionClazz.getDeclaredMethod(str, this.variantSelectorClazz, Function1.class).invoke(this.actual, invoke, function1);
            }
        }
    }

    /* compiled from: AndroidGradleCompat.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat$Companion;", "", "()V", "getAndroidComponentsExtension", "Ldagger/hilt/android/plugin/util/AndroidComponentsExtensionCompat;", "project", "Lorg/gradle/api/Project;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AndroidComponentsExtensionCompat getAndroidComponentsExtension(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            if (AndroidGradleCompatKt.findClass("com.android.build.api.variant.AndroidComponentsExtension") != null) {
                AndroidComponentsExtension actualExtension = (AndroidComponentsExtension) project.getExtensions().getByType(AndroidComponentsExtension.class);
                Intrinsics.checkNotNullExpressionValue(actualExtension, "actualExtension");
                return new Api70Impl(actualExtension);
            }
            Object actualExtension2 = project.getExtensions().getByType(Class.forName("com.android.build.api.extension.AndroidComponentsExtension"));
            Intrinsics.checkNotNullExpressionValue(actualExtension2, "actualExtension");
            return new Api42Impl(actualExtension2);
        }
    }
}
