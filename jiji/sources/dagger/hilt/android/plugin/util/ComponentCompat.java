package dagger.hilt.android.plugin.util;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.Component;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AndroidGradleCompat.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0015\u0016\u0017B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&JD\u0010\u000b\u001a\u00020\b\"\b\b\u0000\u0010\f*\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\f0\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\b0\u0014H&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\u0018\u0019\u001a¨\u0006\u001b"}, d2 = {"Ldagger/hilt/android/plugin/util/ComponentCompat;", "", "()V", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "getName", "()Ljava/lang/String;", "setAsmFramesComputationMode", "", "mode", "Lcom/android/build/api/instrumentation/FramesComputationMode;", "transformClassesWith", "ParamT", "Lcom/android/build/api/instrumentation/InstrumentationParameters;", "classVisitorFactoryImplClass", "Ljava/lang/Class;", "Lcom/android/build/api/instrumentation/AsmClassVisitorFactory;", "scope", "Lcom/android/build/api/instrumentation/InstrumentationScope;", "instrumentationParamsConfig", "Lkotlin/Function1;", "Api42Impl", "Api70Impl", "Api72Impl", "Ldagger/hilt/android/plugin/util/ComponentCompat$Api72Impl;", "Ldagger/hilt/android/plugin/util/ComponentCompat$Api70Impl;", "Ldagger/hilt/android/plugin/util/ComponentCompat$Api42Impl;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public abstract class ComponentCompat {
    public /* synthetic */ ComponentCompat(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String getName();

    public abstract void setAsmFramesComputationMode(FramesComputationMode framesComputationMode);

    public abstract <ParamT extends InstrumentationParameters> void transformClassesWith(Class<? extends AsmClassVisitorFactory<ParamT>> cls, InstrumentationScope instrumentationScope, Function1<? super ParamT, Unit> function1);

    private ComponentCompat() {
    }

    /* compiled from: AndroidGradleCompat.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016JD\u0010\r\u001a\u00020\n\"\b\b\u0000\u0010\u000e*\u00020\u000f2\u0014\u0010\u0010\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u00020\n0\u0016H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0017"}, d2 = {"Ldagger/hilt/android/plugin/util/ComponentCompat$Api72Impl;", "Ldagger/hilt/android/plugin/util/ComponentCompat;", "component", "Lcom/android/build/api/variant/Component;", "(Lcom/android/build/api/variant/Component;)V", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "getName", "()Ljava/lang/String;", "setAsmFramesComputationMode", "", "mode", "Lcom/android/build/api/instrumentation/FramesComputationMode;", "transformClassesWith", "ParamT", "Lcom/android/build/api/instrumentation/InstrumentationParameters;", "classVisitorFactoryImplClass", "Ljava/lang/Class;", "Lcom/android/build/api/instrumentation/AsmClassVisitorFactory;", "scope", "Lcom/android/build/api/instrumentation/InstrumentationScope;", "instrumentationParamsConfig", "Lkotlin/Function1;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Api72Impl extends ComponentCompat {
        private final Component component;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Api72Impl(Component component) {
            super(null);
            Intrinsics.checkNotNullParameter(component, "component");
            this.component = component;
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public String getName() {
            return this.component.getName();
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public <ParamT extends InstrumentationParameters> void transformClassesWith(Class<? extends AsmClassVisitorFactory<ParamT>> classVisitorFactoryImplClass, InstrumentationScope scope, Function1<? super ParamT, Unit> instrumentationParamsConfig) {
            Intrinsics.checkNotNullParameter(classVisitorFactoryImplClass, "classVisitorFactoryImplClass");
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(instrumentationParamsConfig, "instrumentationParamsConfig");
            this.component.getInstrumentation().transformClassesWith(classVisitorFactoryImplClass, scope, instrumentationParamsConfig);
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public void setAsmFramesComputationMode(FramesComputationMode mode) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            this.component.getInstrumentation().setAsmFramesComputationMode(mode);
        }
    }

    /* compiled from: AndroidGradleCompat.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016JD\u0010\r\u001a\u00020\n\"\b\b\u0000\u0010\u000e*\u00020\u000f2\u0014\u0010\u0010\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u00020\n0\u0016H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0017"}, d2 = {"Ldagger/hilt/android/plugin/util/ComponentCompat$Api70Impl;", "Ldagger/hilt/android/plugin/util/ComponentCompat;", "component", "Lcom/android/build/api/variant/Component;", "(Lcom/android/build/api/variant/Component;)V", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "getName", "()Ljava/lang/String;", "setAsmFramesComputationMode", "", "mode", "Lcom/android/build/api/instrumentation/FramesComputationMode;", "transformClassesWith", "ParamT", "Lcom/android/build/api/instrumentation/InstrumentationParameters;", "classVisitorFactoryImplClass", "Ljava/lang/Class;", "Lcom/android/build/api/instrumentation/AsmClassVisitorFactory;", "scope", "Lcom/android/build/api/instrumentation/InstrumentationScope;", "instrumentationParamsConfig", "Lkotlin/Function1;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Api70Impl extends ComponentCompat {
        private final Component component;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Api70Impl(Component component) {
            super(null);
            Intrinsics.checkNotNullParameter(component, "component");
            this.component = component;
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public String getName() {
            return this.component.getName();
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public <ParamT extends InstrumentationParameters> void transformClassesWith(Class<? extends AsmClassVisitorFactory<ParamT>> classVisitorFactoryImplClass, InstrumentationScope scope, Function1<? super ParamT, Unit> instrumentationParamsConfig) {
            Intrinsics.checkNotNullParameter(classVisitorFactoryImplClass, "classVisitorFactoryImplClass");
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(instrumentationParamsConfig, "instrumentationParamsConfig");
            Component.class.getDeclaredMethod("transformClassesWith", Class.class, InstrumentationScope.class, Function1.class).invoke(this.component, classVisitorFactoryImplClass, scope, instrumentationParamsConfig);
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public void setAsmFramesComputationMode(FramesComputationMode mode) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            Component.class.getDeclaredMethod("setAsmFramesComputationMode", FramesComputationMode.class).invoke(this.component, mode);
        }
    }

    /* compiled from: AndroidGradleCompat.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016JD\u0010\u0010\u001a\u00020\r\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0014\u0010\u0013\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00110\u00140\u00062\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\r0\u0018H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0002\b\u0003 \u0007*\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0019"}, d2 = {"Ldagger/hilt/android/plugin/util/ComponentCompat$Api42Impl;", "Ldagger/hilt/android/plugin/util/ComponentCompat;", "actual", "", "(Ljava/lang/Object;)V", "componentClazz", "Ljava/lang/Class;", "kotlin.jvm.PlatformType", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "getName", "()Ljava/lang/String;", "setAsmFramesComputationMode", "", "mode", "Lcom/android/build/api/instrumentation/FramesComputationMode;", "transformClassesWith", "ParamT", "Lcom/android/build/api/instrumentation/InstrumentationParameters;", "classVisitorFactoryImplClass", "Lcom/android/build/api/instrumentation/AsmClassVisitorFactory;", "scope", "Lcom/android/build/api/instrumentation/InstrumentationScope;", "instrumentationParamsConfig", "Lkotlin/Function1;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Api42Impl extends ComponentCompat {
        private final Object actual;
        private final Class<?> componentClazz;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Api42Impl(Object actual) {
            super(null);
            Intrinsics.checkNotNullParameter(actual, "actual");
            this.actual = actual;
            this.componentClazz = Class.forName("com.android.build.api.component.Component");
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public String getName() {
            Object invoke = this.componentClazz.getMethod("getName", new Class[0]).invoke(this.actual, new Object[0]);
            if (invoke != null) {
                return (String) invoke;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public <ParamT extends InstrumentationParameters> void transformClassesWith(Class<? extends AsmClassVisitorFactory<ParamT>> classVisitorFactoryImplClass, InstrumentationScope scope, Function1<? super ParamT, Unit> instrumentationParamsConfig) {
            Intrinsics.checkNotNullParameter(classVisitorFactoryImplClass, "classVisitorFactoryImplClass");
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(instrumentationParamsConfig, "instrumentationParamsConfig");
            this.componentClazz.getDeclaredMethod("transformClassesWith", Class.class, InstrumentationScope.class, Function1.class).invoke(this.actual, classVisitorFactoryImplClass, scope, instrumentationParamsConfig);
        }

        @Override // dagger.hilt.android.plugin.util.ComponentCompat
        public void setAsmFramesComputationMode(FramesComputationMode mode) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            this.componentClazz.getDeclaredMethod("setAsmFramesComputationMode", FramesComputationMode.class).invoke(this.actual, mode);
        }
    }
}
