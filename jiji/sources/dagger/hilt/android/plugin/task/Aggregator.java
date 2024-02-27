package dagger.hilt.android.plugin.task;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.squareup.javapoet.ClassName;
import dagger.hilt.android.plugin.root.AggregatedAnnotation;
import dagger.hilt.android.plugin.task.Aggregator;
import dagger.hilt.android.plugin.util.FilesKt;
import dagger.hilt.processor.internal.root.ir.AggregatedDepsIr;
import dagger.hilt.processor.internal.root.ir.AggregatedEarlyEntryPointIr;
import dagger.hilt.processor.internal.root.ir.AggregatedElementProxyIr;
import dagger.hilt.processor.internal.root.ir.AggregatedRootIr;
import dagger.hilt.processor.internal.root.ir.AggregatedUninstallModulesIr;
import dagger.hilt.processor.internal.root.ir.AliasOfPropagatedDataIr;
import dagger.hilt.processor.internal.root.ir.DefineComponentClassesIr;
import dagger.hilt.processor.internal.root.ir.ProcessedRootSentinelIr;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

/* compiled from: Aggregator.kt */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000 02\u00020\u0001:\u0002/0B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(H\u0002J\u0010\u0010*\u001a\u00020&2\u0006\u0010+\u001a\u00020,H\u0002J\u0010\u0010-\u001a\u00020&2\u0006\u0010.\u001a\u00020)H\u0002R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000bR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\b8F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000bR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\b8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\b8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\b8F¢\u0006\u0006\u001a\u0004\b!\u0010\u000bR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\b8F¢\u0006\u0006\u001a\u0004\b$\u0010\u000b¨\u00061"}, d2 = {"Ldagger/hilt/android/plugin/task/Aggregator;", "", "logger", "Lorg/slf4j/Logger;", "asmApiVersion", "", "(Lorg/slf4j/Logger;I)V", "aggregatedDepProxies", "", "Ldagger/hilt/processor/internal/root/ir/AggregatedElementProxyIr;", "getAggregatedDepProxies", "()Ljava/util/Set;", "aggregatedDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedDepsIr;", "getAggregatedDeps", "aggregatedRoots", "Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr;", "getAggregatedRoots", "aliasOfDeps", "Ldagger/hilt/processor/internal/root/ir/AliasOfPropagatedDataIr;", "getAliasOfDeps", "allAggregatedDepProxies", "getAllAggregatedDepProxies", "classVisitor", "Ldagger/hilt/android/plugin/task/Aggregator$AggregatedDepClassVisitor;", "defineComponentDeps", "Ldagger/hilt/processor/internal/root/ir/DefineComponentClassesIr;", "getDefineComponentDeps", "earlyEntryPointDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedEarlyEntryPointIr;", "getEarlyEntryPointDeps", "processedRoots", "Ldagger/hilt/processor/internal/root/ir/ProcessedRootSentinelIr;", "getProcessedRoots", "uninstallModulesDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedUninstallModulesIr;", "getUninstallModulesDeps", "process", "", "files", "", "Ljava/io/File;", "visitClass", "classFileInputStream", "Ljava/io/InputStream;", "visitFile", "file", "AggregatedDepClassVisitor", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class Aggregator {
    public static final Companion Companion = new Companion(null);
    private final int asmApiVersion;
    private final AggregatedDepClassVisitor classVisitor;
    private final Logger logger;

    public /* synthetic */ Aggregator(Logger logger, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(logger, i);
    }

    private Aggregator(Logger logger, int i) {
        this.logger = logger;
        this.asmApiVersion = i;
        this.classVisitor = new AggregatedDepClassVisitor(logger, i);
    }

    public static final /* synthetic */ void access$visitClass(Aggregator aggregator, InputStream inputStream) {
        aggregator.visitClass(inputStream);
    }

    public final Set<AggregatedRootIr> getAggregatedRoots() {
        return this.classVisitor.getAggregatedRoots();
    }

    public final Set<ProcessedRootSentinelIr> getProcessedRoots() {
        return this.classVisitor.getProcessedRoots();
    }

    public final Set<DefineComponentClassesIr> getDefineComponentDeps() {
        return this.classVisitor.getDefineComponentDeps();
    }

    public final Set<AliasOfPropagatedDataIr> getAliasOfDeps() {
        return this.classVisitor.getAliasOfDeps();
    }

    public final Set<AggregatedDepsIr> getAggregatedDeps() {
        return this.classVisitor.getAggregatedDeps();
    }

    public final Set<AggregatedElementProxyIr> getAggregatedDepProxies() {
        return this.classVisitor.getAggregatedDepProxies();
    }

    public final Set<AggregatedElementProxyIr> getAllAggregatedDepProxies() {
        return this.classVisitor.getAllAggregatedDepProxies();
    }

    public final Set<AggregatedUninstallModulesIr> getUninstallModulesDeps() {
        return this.classVisitor.getUninstallModulesDeps();
    }

    public final Set<AggregatedEarlyEntryPointIr> getEarlyEntryPointDeps() {
        return this.classVisitor.getEarlyEntryPointDeps();
    }

    /* compiled from: Aggregator.kt */
    @Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JK\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u0001032\b\u00105\u001a\u0004\u0018\u0001032\u0010\u00106\u001a\f\u0012\u0006\b\u0001\u0012\u000203\u0018\u000107H\u0016¢\u0006\u0002\u00108J\u001a\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010;\u001a\u0002032\u0006\u0010<\u001a\u00020=H\u0016J)\u0010>\u001a\u00020:2!\u0010?\u001a\u001d\u0012\u0013\u0012\u00110A¢\u0006\f\bB\u0012\b\b2\u0012\u0004\b\b(C\u0012\u0004\u0012\u00020/0@R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\r¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\r¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\r¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0010R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\r¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\r¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0010R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\r¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0010¨\u0006D"}, d2 = {"Ldagger/hilt/android/plugin/task/Aggregator$AggregatedDepClassVisitor;", "Lorg/objectweb/asm/ClassVisitor;", "logger", "Lorg/slf4j/Logger;", "asmApiVersion", "", "(Lorg/slf4j/Logger;I)V", "accessCode", "getAccessCode", "()I", "setAccessCode", "(I)V", "aggregatedDepProxies", "", "Ldagger/hilt/processor/internal/root/ir/AggregatedElementProxyIr;", "getAggregatedDepProxies", "()Ljava/util/Set;", "aggregatedDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedDepsIr;", "getAggregatedDeps", "aggregatedRoots", "Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr;", "getAggregatedRoots", "aliasOfDeps", "Ldagger/hilt/processor/internal/root/ir/AliasOfPropagatedDataIr;", "getAliasOfDeps", "allAggregatedDepProxies", "getAllAggregatedDepProxies", "annotatedClassName", "Lcom/squareup/javapoet/ClassName;", "getAnnotatedClassName", "()Lcom/squareup/javapoet/ClassName;", "setAnnotatedClassName", "(Lcom/squareup/javapoet/ClassName;)V", "defineComponentDeps", "Ldagger/hilt/processor/internal/root/ir/DefineComponentClassesIr;", "getDefineComponentDeps", "earlyEntryPointDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedEarlyEntryPointIr;", "getEarlyEntryPointDeps", "processedRoots", "Ldagger/hilt/processor/internal/root/ir/ProcessedRootSentinelIr;", "getProcessedRoots", "uninstallModulesDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedUninstallModulesIr;", "getUninstallModulesDeps", "visit", "", "version", "access", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "signature", "superName", "interfaces", "", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "visitAnnotation", "Lorg/objectweb/asm/AnnotationVisitor;", "descriptor", "visible", "", "visitValue", "block", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "value", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class AggregatedDepClassVisitor extends ClassVisitor {
        private int accessCode;
        private final Set<AggregatedElementProxyIr> aggregatedDepProxies;
        private final Set<AggregatedDepsIr> aggregatedDeps;
        private final Set<AggregatedRootIr> aggregatedRoots;
        private final Set<AliasOfPropagatedDataIr> aliasOfDeps;
        private final Set<AggregatedElementProxyIr> allAggregatedDepProxies;
        public ClassName annotatedClassName;
        private final int asmApiVersion;
        private final Set<DefineComponentClassesIr> defineComponentDeps;
        private final Set<AggregatedEarlyEntryPointIr> earlyEntryPointDeps;
        private final Logger logger;
        private final Set<ProcessedRootSentinelIr> processedRoots;
        private final Set<AggregatedUninstallModulesIr> uninstallModulesDeps;

        /* compiled from: Aggregator.kt */
        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes2.dex */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[AggregatedAnnotation.values().length];
                iArr[AggregatedAnnotation.AGGREGATED_ROOT.ordinal()] = 1;
                iArr[AggregatedAnnotation.PROCESSED_ROOT_SENTINEL.ordinal()] = 2;
                iArr[AggregatedAnnotation.DEFINE_COMPONENT.ordinal()] = 3;
                iArr[AggregatedAnnotation.ALIAS_OF.ordinal()] = 4;
                iArr[AggregatedAnnotation.AGGREGATED_DEP.ordinal()] = 5;
                iArr[AggregatedAnnotation.AGGREGATED_DEP_PROXY.ordinal()] = 6;
                iArr[AggregatedAnnotation.AGGREGATED_UNINSTALL_MODULES.ordinal()] = 7;
                iArr[AggregatedAnnotation.AGGREGATED_EARLY_ENTRY_POINT.ordinal()] = 8;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AggregatedDepClassVisitor(Logger logger, int i) {
            super(i);
            Intrinsics.checkNotNullParameter(logger, "logger");
            this.logger = logger;
            this.asmApiVersion = i;
            this.aggregatedRoots = new LinkedHashSet();
            this.processedRoots = new LinkedHashSet();
            this.defineComponentDeps = new LinkedHashSet();
            this.aliasOfDeps = new LinkedHashSet();
            this.aggregatedDeps = new LinkedHashSet();
            this.aggregatedDepProxies = new LinkedHashSet();
            this.allAggregatedDepProxies = new LinkedHashSet();
            this.uninstallModulesDeps = new LinkedHashSet();
            this.earlyEntryPointDeps = new LinkedHashSet();
            this.accessCode = 1;
        }

        public final Set<AggregatedRootIr> getAggregatedRoots() {
            return this.aggregatedRoots;
        }

        public final Set<ProcessedRootSentinelIr> getProcessedRoots() {
            return this.processedRoots;
        }

        public final Set<DefineComponentClassesIr> getDefineComponentDeps() {
            return this.defineComponentDeps;
        }

        public final Set<AliasOfPropagatedDataIr> getAliasOfDeps() {
            return this.aliasOfDeps;
        }

        public final Set<AggregatedDepsIr> getAggregatedDeps() {
            return this.aggregatedDeps;
        }

        public final Set<AggregatedElementProxyIr> getAggregatedDepProxies() {
            return this.aggregatedDepProxies;
        }

        public final Set<AggregatedElementProxyIr> getAllAggregatedDepProxies() {
            return this.allAggregatedDepProxies;
        }

        public final Set<AggregatedUninstallModulesIr> getUninstallModulesDeps() {
            return this.uninstallModulesDeps;
        }

        public final Set<AggregatedEarlyEntryPointIr> getEarlyEntryPointDeps() {
            return this.earlyEntryPointDeps;
        }

        public final int getAccessCode() {
            return this.accessCode;
        }

        public final void setAccessCode(int i) {
            this.accessCode = i;
        }

        public final ClassName getAnnotatedClassName() {
            ClassName className = this.annotatedClassName;
            if (className != null) {
                return className;
            }
            Intrinsics.throwUninitializedPropertyAccessException("annotatedClassName");
            return null;
        }

        public final void setAnnotatedClassName(ClassName className) {
            Intrinsics.checkNotNullParameter(className, "<set-?>");
            this.annotatedClassName = className;
        }

        @Override // org.objectweb.asm.ClassVisitor
        public void visit(int i, int i2, String name, String str, String str2, String[] strArr) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.accessCode = i2;
            Companion companion = Aggregator.Companion;
            Type objectType = Type.getObjectType(name);
            Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(name)");
            setAnnotatedClassName(companion.toClassName(objectType));
            super.visit(i, i2, name, str, str2, strArr);
        }

        @Override // org.objectweb.asm.ClassVisitor
        public AnnotationVisitor visitAnnotation(String descriptor, boolean z) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            AnnotationVisitor visitAnnotation = super.visitAnnotation(descriptor, z);
            AggregatedAnnotation fromString = AggregatedAnnotation.Companion.fromString(descriptor);
            if ((fromString != AggregatedAnnotation.NONE) && (this.accessCode & 1) != 1) {
                Set<AggregatedElementProxyIr> set = this.allAggregatedDepProxies;
                ClassName peerClass = getAnnotatedClassName().peerClass(Intrinsics.stringPlus("_", getAnnotatedClassName().simpleName()));
                Intrinsics.checkNotNullExpressionValue(peerClass, "annotatedClassName.peerC…edClassName.simpleName())");
                set.add(new AggregatedElementProxyIr(peerClass, getAnnotatedClassName()));
            }
            switch (WhenMappings.$EnumSwitchMapping$0[fromString.ordinal()]) {
                case 1:
                    return new Aggregator$AggregatedDepClassVisitor$visitAnnotation$1(this, visitAnnotation, this.asmApiVersion);
                case 2:
                    return new Aggregator$AggregatedDepClassVisitor$visitAnnotation$2(this, visitAnnotation, this.asmApiVersion);
                case 3:
                    return new AnnotationVisitor(visitAnnotation, this.asmApiVersion) { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$3
                        final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
                        public String componentClass;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(r3, visitAnnotation);
                            this.$nextAnnotationVisitor = visitAnnotation;
                        }

                        public final String getComponentClass() {
                            String str = this.componentClass;
                            if (str != null) {
                                return str;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("componentClass");
                            return null;
                        }

                        public final void setComponentClass(String str) {
                            Intrinsics.checkNotNullParameter(str, "<set-?>");
                            this.componentClass = str;
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visit(String name, Object obj) {
                            Intrinsics.checkNotNullParameter(name, "name");
                            if (Intrinsics.areEqual(name, "component") ? true : Intrinsics.areEqual(name, "builder")) {
                                if (obj == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                                }
                                setComponentClass((String) obj);
                            }
                            super.visit(name, obj);
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visitEnd() {
                            Aggregator.AggregatedDepClassVisitor.this.getDefineComponentDeps().add(new DefineComponentClassesIr(Aggregator.AggregatedDepClassVisitor.this.getAnnotatedClassName(), getComponentClass()));
                            super.visitEnd();
                        }
                    };
                case 4:
                    return new AnnotationVisitor(visitAnnotation, this.asmApiVersion) { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$4
                        final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
                        public Type aliasClassName;
                        private final Set<Type> defineComponentScopeClassNames;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(r3, visitAnnotation);
                            this.$nextAnnotationVisitor = visitAnnotation;
                            this.defineComponentScopeClassNames = new LinkedHashSet();
                        }

                        public final Set<Type> getDefineComponentScopeClassNames() {
                            return this.defineComponentScopeClassNames;
                        }

                        public final Type getAliasClassName() {
                            Type type = this.aliasClassName;
                            if (type != null) {
                                return type;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("aliasClassName");
                            return null;
                        }

                        public final void setAliasClassName(Type type) {
                            Intrinsics.checkNotNullParameter(type, "<set-?>");
                            this.aliasClassName = type;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:17:0x0031  */
                        /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
                        @Override // org.objectweb.asm.AnnotationVisitor
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void visit(java.lang.String r4, java.lang.Object r5) {
                            /*
                                r3 = this;
                                java.lang.String r0 = "name"
                                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
                                int r0 = r4.hashCode()
                                r1 = 92902992(0x5899650, float:1.2938634E-35)
                                java.lang.String r2 = "null cannot be cast to non-null type org.objectweb.asm.Type"
                                if (r0 == r1) goto L3e
                                r1 = 453702849(0x1b0af4c1, float:1.1494177E-22)
                                if (r0 == r1) goto L24
                                r1 = 2092845554(0x7cbe49f2, float:7.9042895E36)
                                if (r0 == r1) goto L1b
                                goto L4f
                            L1b:
                                java.lang.String r0 = "defineComponentScope"
                                boolean r0 = r4.equals(r0)
                                if (r0 != 0) goto L2d
                                goto L4f
                            L24:
                                java.lang.String r0 = "defineComponentScopes"
                                boolean r0 = r4.equals(r0)
                                if (r0 != 0) goto L2d
                                goto L4f
                            L2d:
                                java.util.Set<org.objectweb.asm.Type> r0 = r3.defineComponentScopeClassNames
                                if (r5 == 0) goto L38
                                r1 = r5
                                org.objectweb.asm.Type r1 = (org.objectweb.asm.Type) r1
                                r0.add(r1)
                                goto L4f
                            L38:
                                java.lang.NullPointerException r4 = new java.lang.NullPointerException
                                r4.<init>(r2)
                                throw r4
                            L3e:
                                java.lang.String r0 = "alias"
                                boolean r0 = r4.equals(r0)
                                if (r0 != 0) goto L47
                                goto L4f
                            L47:
                                if (r5 == 0) goto L53
                                r0 = r5
                                org.objectweb.asm.Type r0 = (org.objectweb.asm.Type) r0
                                r3.setAliasClassName(r0)
                            L4f:
                                super.visit(r4, r5)
                                return
                            L53:
                                java.lang.NullPointerException r4 = new java.lang.NullPointerException
                                r4.<init>(r2)
                                throw r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$4.visit(java.lang.String, java.lang.Object):void");
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visitEnd() {
                            Set<AliasOfPropagatedDataIr> aliasOfDeps = Aggregator.AggregatedDepClassVisitor.this.getAliasOfDeps();
                            ClassName annotatedClassName = Aggregator.AggregatedDepClassVisitor.this.getAnnotatedClassName();
                            Set<Type> set2 = this.defineComponentScopeClassNames;
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
                            for (Type type : set2) {
                                arrayList.add(Aggregator.Companion.toClassName(type));
                            }
                            aliasOfDeps.add(new AliasOfPropagatedDataIr(annotatedClassName, CollectionsKt.toList(arrayList), Aggregator.Companion.toClassName(getAliasClassName())));
                            super.visitEnd();
                        }
                    };
                case 5:
                    return new Aggregator$AggregatedDepClassVisitor$visitAnnotation$5(this, visitAnnotation, this.asmApiVersion);
                case 6:
                    return new AnnotationVisitor(visitAnnotation, this.asmApiVersion) { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$6
                        final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
                        public Type valueClassName;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(r3, visitAnnotation);
                            this.$nextAnnotationVisitor = visitAnnotation;
                        }

                        public final Type getValueClassName() {
                            Type type = this.valueClassName;
                            if (type != null) {
                                return type;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("valueClassName");
                            return null;
                        }

                        public final void setValueClassName(Type type) {
                            Intrinsics.checkNotNullParameter(type, "<set-?>");
                            this.valueClassName = type;
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visit(String name, Object obj) {
                            Intrinsics.checkNotNullParameter(name, "name");
                            if (Intrinsics.areEqual(name, "value")) {
                                if (obj == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.objectweb.asm.Type");
                                }
                                setValueClassName((Type) obj);
                            }
                            super.visit(name, obj);
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visitEnd() {
                            Aggregator.AggregatedDepClassVisitor.this.getAggregatedDepProxies().add(new AggregatedElementProxyIr(Aggregator.AggregatedDepClassVisitor.this.getAnnotatedClassName(), Aggregator.Companion.toClassName(getValueClassName())));
                            super.visitEnd();
                        }
                    };
                case 7:
                    return new Aggregator$AggregatedDepClassVisitor$visitAnnotation$7(this, visitAnnotation, this.asmApiVersion);
                case 8:
                    return new AnnotationVisitor(visitAnnotation, this.asmApiVersion) { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$8
                        final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
                        public String earlyEntryPointClass;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(r3, visitAnnotation);
                            this.$nextAnnotationVisitor = visitAnnotation;
                        }

                        public final String getEarlyEntryPointClass() {
                            String str = this.earlyEntryPointClass;
                            if (str != null) {
                                return str;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("earlyEntryPointClass");
                            return null;
                        }

                        public final void setEarlyEntryPointClass(String str) {
                            Intrinsics.checkNotNullParameter(str, "<set-?>");
                            this.earlyEntryPointClass = str;
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visit(String name, Object obj) {
                            Intrinsics.checkNotNullParameter(name, "name");
                            if (Intrinsics.areEqual(name, "earlyEntryPoint")) {
                                if (obj == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                                }
                                setEarlyEntryPointClass((String) obj);
                            }
                            super.visit(name, obj);
                        }

                        @Override // org.objectweb.asm.AnnotationVisitor
                        public void visitEnd() {
                            Aggregator.AggregatedDepClassVisitor.this.getEarlyEntryPointDeps().add(new AggregatedEarlyEntryPointIr(Aggregator.AggregatedDepClassVisitor.this.getAnnotatedClassName(), getEarlyEntryPointClass()));
                            super.visitEnd();
                        }
                    };
                default:
                    this.logger.warn(Intrinsics.stringPlus("Found an unknown annotation in Hilt aggregated packages: ", descriptor));
                    return visitAnnotation;
            }
        }

        public final AnnotationVisitor visitValue(final Function1<Object, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return new AnnotationVisitor(this.asmApiVersion) { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitValue$1
                @Override // org.objectweb.asm.AnnotationVisitor
                public void visit(String str, Object value) {
                    Intrinsics.checkNotNullParameter(value, "value");
                    block.invoke(value);
                }
            };
        }
    }

    private final void visitFile(File file) {
        if (FilesKt.isJarFile(file)) {
            FilesKt.forEachZipEntry(new ZipInputStream(new FileInputStream(file)), new Function2<InputStream, ZipEntry, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$visitFile$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(InputStream inputStream, ZipEntry zipEntry) {
                    invoke2(inputStream, zipEntry);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(InputStream inputStream, ZipEntry entry) {
                    Intrinsics.checkNotNullParameter(inputStream, "inputStream");
                    Intrinsics.checkNotNullParameter(entry, "entry");
                    if (FilesKt.isClassFile(entry)) {
                        Aggregator.access$visitClass(Aggregator.this, inputStream);
                    }
                }
            });
        } else if (!FilesKt.isClassFile(file)) {
            this.logger.debug(Intrinsics.stringPlus("Don't know how to process file: ", file));
        } else {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                visitClass(fileInputStream);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileInputStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileInputStream, th);
                    throw th2;
                }
            }
        }
    }

    public final void visitClass(InputStream inputStream) {
        new ClassReader(inputStream).accept(this.classVisitor, 0);
    }

    /* compiled from: Aggregator.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ&\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\u0006\u0010\u0012\u001a\u00020\u000fJ\n\u0010\u0013\u001a\u00020\r*\u00020\u0014¨\u0006\u0015"}, d2 = {"Ldagger/hilt/android/plugin/task/Aggregator$Companion;", "", "()V", TypedValues.TransitionType.S_FROM, "Ldagger/hilt/android/plugin/task/Aggregator;", "logger", "Lorg/slf4j/Logger;", "asmApiVersion", "", "input", "", "Ljava/io/File;", "parseClassName", "Lcom/squareup/javapoet/ClassName;", "packageName", "", "simpleNames", "", "fallbackCanonicalName", "toClassName", "Lorg/objectweb/asm/Type;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Aggregator from(Logger logger, int i, Iterable<? extends File> input) {
            Intrinsics.checkNotNullParameter(logger, "logger");
            Intrinsics.checkNotNullParameter(input, "input");
            Aggregator aggregator = new Aggregator(logger, i, null);
            aggregator.process(input);
            return aggregator;
        }

        public final ClassName toClassName(Type type) {
            String str;
            Intrinsics.checkNotNullParameter(type, "<this>");
            String binaryName = type.getClassName();
            Intrinsics.checkNotNullExpressionValue(binaryName, "binaryName");
            int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) binaryName, '.', 0, false, 6, (Object) null);
            if (lastIndexOf$default != -1) {
                str = binaryName.substring(0, lastIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            } else {
                str = "";
            }
            String substring = binaryName.substring(lastIndexOf$default + 1);
            Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.String).substring(startIndex)");
            List split$default = StringsKt.split$default((CharSequence) substring, new char[]{Typography.dollar}, false, 0, 6, (Object) null);
            String str2 = (String) CollectionsKt.first((List<? extends Object>) split$default);
            Object[] array = CollectionsKt.drop(split$default, 1).toArray(new String[0]);
            if (array != null) {
                String[] strArr = (String[]) array;
                ClassName className = ClassName.get(str, str2, (String[]) Arrays.copyOf(strArr, strArr.length));
                Intrinsics.checkNotNullExpressionValue(className, "get(packageName, shortNa…s.drop(1).toTypedArray())");
                return className;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }

        public final ClassName parseClassName(String str, List<String> simpleNames, String fallbackCanonicalName) {
            Intrinsics.checkNotNullParameter(simpleNames, "simpleNames");
            Intrinsics.checkNotNullParameter(fallbackCanonicalName, "fallbackCanonicalName");
            if (str != null) {
                if (!(simpleNames.size() > 0)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                String str2 = (String) CollectionsKt.first((List<? extends Object>) simpleNames);
                Object[] array = simpleNames.subList(1, simpleNames.size()).toArray(new String[0]);
                if (array != null) {
                    String[] strArr = (String[]) array;
                    ClassName className = ClassName.get(str, str2, (String[]) Arrays.copyOf(strArr, strArr.length));
                    Intrinsics.checkNotNullExpressionValue(className, "get(\n          packageNa….toTypedArray()\n        )");
                    return className;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            ClassName bestGuess = ClassName.bestGuess(fallbackCanonicalName);
            Intrinsics.checkNotNullExpressionValue(bestGuess, "bestGuess(fallbackCanonicalName)");
            return bestGuess;
        }
    }

    public final void process(Iterable<? extends File> iterable) {
        for (File file : iterable) {
            if (file.isFile()) {
                visitFile(file);
            } else if (!file.isDirectory()) {
                this.logger.warn(Intrinsics.stringPlus("Can't process file/directory that doesn't exist: ", file));
            } else {
                for (File file2 : SequencesKt.filter(kotlin.io.FilesKt.walkTopDown(file), new Function1<File, Boolean>() { // from class: dagger.hilt.android.plugin.task.Aggregator$process$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(File it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(it.isFile());
                    }
                })) {
                    visitFile(file2);
                }
            }
        }
    }
}
