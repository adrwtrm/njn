package dagger.hilt.android.plugin.task;

import com.squareup.javapoet.ClassName;
import dagger.hilt.android.plugin.root.AggregatedElementProxyGenerator;
import dagger.hilt.android.plugin.root.ComponentTreeDepsGenerator;
import dagger.hilt.android.plugin.root.ProcessedRootSentinelGenerator;
import dagger.hilt.android.plugin.task.AggregateDepsTask;
import dagger.hilt.android.plugin.task.Aggregator;
import dagger.hilt.processor.internal.root.ir.AggregatedElementProxyIr;
import dagger.hilt.processor.internal.root.ir.AggregatedRootIr;
import dagger.hilt.processor.internal.root.ir.AggregatedRootIrValidator;
import dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIr;
import dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.InputChanges;
import org.gradle.workers.WorkAction;
import org.gradle.workers.WorkParameters;
import org.gradle.workers.WorkerExecutor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: AggregateDepsTask.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b'\u0018\u00002\u00020\u0001:\u0002\u001c\u001dB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0001¢\u0006\u0002\b\u001bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068gX¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8gX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00068gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\tR\u0014\u0010\u0011\u001a\u00020\u00128gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00068gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Ldagger/hilt/android/plugin/task/AggregateDepsTask;", "Lorg/gradle/api/DefaultTask;", "workerExecutor", "Lorg/gradle/workers/WorkerExecutor;", "(Lorg/gradle/workers/WorkerExecutor;)V", "asmApiVersion", "Lorg/gradle/api/provider/Property;", "", "getAsmApiVersion", "()Lorg/gradle/api/provider/Property;", "compileClasspath", "Lorg/gradle/api/file/ConfigurableFileCollection;", "getCompileClasspath", "()Lorg/gradle/api/file/ConfigurableFileCollection;", "crossCompilationRootValidationDisabled", "", "getCrossCompilationRootValidationDisabled", "outputDir", "Lorg/gradle/api/file/DirectoryProperty;", "getOutputDir", "()Lorg/gradle/api/file/DirectoryProperty;", "testEnvironment", "getTestEnvironment", "taskAction", "", "inputs", "Lorg/gradle/work/InputChanges;", "taskAction$plugin", "Parameters", "WorkerAction", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
@CacheableTask
/* loaded from: classes2.dex */
public abstract class AggregateDepsTask extends DefaultTask {
    private final WorkerExecutor workerExecutor;

    /* compiled from: AggregateDepsTask.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b`\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0006R\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0006¨\u0006\u0014"}, d2 = {"Ldagger/hilt/android/plugin/task/AggregateDepsTask$Parameters;", "Lorg/gradle/workers/WorkParameters;", "asmApiVersion", "Lorg/gradle/api/provider/Property;", "", "getAsmApiVersion", "()Lorg/gradle/api/provider/Property;", "compileClasspath", "Lorg/gradle/api/file/ConfigurableFileCollection;", "getCompileClasspath", "()Lorg/gradle/api/file/ConfigurableFileCollection;", "crossCompilationRootValidationDisabled", "", "getCrossCompilationRootValidationDisabled", "outputDir", "Lorg/gradle/api/file/DirectoryProperty;", "getOutputDir", "()Lorg/gradle/api/file/DirectoryProperty;", "testEnvironment", "getTestEnvironment", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public interface Parameters extends WorkParameters {
        Property<Integer> getAsmApiVersion();

        ConfigurableFileCollection getCompileClasspath();

        Property<Boolean> getCrossCompilationRootValidationDisabled();

        DirectoryProperty getOutputDir();

        Property<Boolean> getTestEnvironment();
    }

    /* renamed from: $r8$lambda$EWjC8_jgPCaBMsCUYGpg-bqPFzY */
    public static /* synthetic */ void m321$r8$lambda$EWjC8_jgPCaBMsCUYGpgbqPFzY(AggregateDepsTask aggregateDepsTask, Parameters parameters) {
        m322taskAction$lambda0(aggregateDepsTask, parameters);
    }

    @Input
    @Optional
    public abstract Property<Integer> getAsmApiVersion();

    @Classpath
    public abstract ConfigurableFileCollection getCompileClasspath();

    @Input
    public abstract Property<Boolean> getCrossCompilationRootValidationDisabled();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @Input
    public abstract Property<Boolean> getTestEnvironment();

    @Inject
    public AggregateDepsTask(WorkerExecutor workerExecutor) {
        Intrinsics.checkNotNullParameter(workerExecutor, "workerExecutor");
        this.workerExecutor = workerExecutor;
    }

    @TaskAction
    public final void taskAction$plugin(InputChanges inputs) {
        Intrinsics.checkNotNullParameter(inputs, "inputs");
        this.workerExecutor.noIsolation().submit(WorkerAction.class, new Action() { // from class: dagger.hilt.android.plugin.task.AggregateDepsTask$$ExternalSyntheticLambda0
            public final void execute(Object obj) {
                AggregateDepsTask.m321$r8$lambda$EWjC8_jgPCaBMsCUYGpgbqPFzY(AggregateDepsTask.this, (AggregateDepsTask.Parameters) obj);
            }
        });
    }

    /* renamed from: taskAction$lambda-0 */
    public static final void m322taskAction$lambda0(AggregateDepsTask this$0, Parameters parameters) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        parameters.getCompileClasspath().from(new Object[]{this$0.getCompileClasspath()});
        parameters.getAsmApiVersion().set(this$0.getAsmApiVersion());
        parameters.getOutputDir().set(this$0.getOutputDir());
        parameters.getTestEnvironment().set(this$0.getTestEnvironment());
        parameters.getCrossCompilationRootValidationDisabled().set(this$0.getCrossCompilationRootValidationDisabled());
    }

    /* compiled from: AggregateDepsTask.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Ldagger/hilt/android/plugin/task/AggregateDepsTask$WorkerAction;", "Lorg/gradle/workers/WorkAction;", "Ldagger/hilt/android/plugin/task/AggregateDepsTask$Parameters;", "()V", "execute", "", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static abstract class WorkerAction implements WorkAction<Parameters> {
        public void execute() {
            Logger logger = LoggerFactory.getLogger(AggregateDepsTask.class);
            Aggregator.Companion companion = Aggregator.Companion;
            Intrinsics.checkNotNullExpressionValue(logger, "logger");
            Integer num = (Integer) ((Parameters) getParameters()).getAsmApiVersion().getOrNull();
            if (num == null) {
                num = Integer.valueOf((int) Opcodes.ASM7);
            }
            Aggregator from = companion.from(logger, num.intValue(), (Iterable) ((Parameters) getParameters()).getCompileClasspath());
            Object obj = ((Parameters) getParameters()).getCrossCompilationRootValidationDisabled().get();
            Intrinsics.checkNotNullExpressionValue(obj, "parameters.crossCompilat…tValidationDisabled.get()");
            Set<AggregatedRootIr> rootsToProcess = AggregatedRootIrValidator.rootsToProcess(((Boolean) obj).booleanValue(), from.getProcessedRoots(), from.getAggregatedRoots());
            if (rootsToProcess.isEmpty()) {
                return;
            }
            ComponentTreeDepsIrCreator.Companion companion2 = ComponentTreeDepsIrCreator.Companion;
            Object obj2 = ((Parameters) getParameters()).getTestEnvironment().get();
            Intrinsics.checkNotNullExpressionValue(obj2, "parameters.testEnvironment.get()");
            Set<ComponentTreeDepsIr> components = companion2.components(((Boolean) obj2).booleanValue(), true, rootsToProcess, from.getDefineComponentDeps(), from.getAliasOfDeps(), from.getAggregatedDeps(), from.getUninstallModulesDeps(), from.getEarlyEntryPointDeps());
            Set<AggregatedElementProxyIr> allAggregatedDepProxies = from.getAllAggregatedDepProxies();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(allAggregatedDepProxies, 10)), 16));
            for (AggregatedElementProxyIr aggregatedElementProxyIr : allAggregatedDepProxies) {
                Pair pair = TuplesKt.to(aggregatedElementProxyIr.getValue(), aggregatedElementProxyIr.getFqName());
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            }
            File asFile = ((Directory) ((Parameters) getParameters()).getOutputDir().get()).getAsFile();
            Intrinsics.checkNotNullExpressionValue(asFile, "parameters.outputDir.get().asFile");
            ComponentTreeDepsGenerator componentTreeDepsGenerator = new ComponentTreeDepsGenerator(linkedHashMap, asFile);
            for (ComponentTreeDepsIr componentTreeDepsIr : components) {
                componentTreeDepsGenerator.generate(componentTreeDepsIr);
            }
            File asFile2 = ((Directory) ((Parameters) getParameters()).getOutputDir().get()).getAsFile();
            Intrinsics.checkNotNullExpressionValue(asFile2, "parameters.outputDir.get().asFile");
            AggregatedElementProxyGenerator aggregatedElementProxyGenerator = new AggregatedElementProxyGenerator(asFile2);
            for (AggregatedElementProxyIr aggregatedElementProxyIr2 : SetsKt.minus((Set) from.getAllAggregatedDepProxies(), (Iterable) from.getAggregatedDepProxies())) {
                aggregatedElementProxyGenerator.generate(aggregatedElementProxyIr2);
            }
            File asFile3 = ((Directory) ((Parameters) getParameters()).getOutputDir().get()).getAsFile();
            Intrinsics.checkNotNullExpressionValue(asFile3, "parameters.outputDir.get().asFile");
            ProcessedRootSentinelGenerator processedRootSentinelGenerator = new ProcessedRootSentinelGenerator(asFile3);
            Set<AggregatedRootIr> set = rootsToProcess;
            ArrayList<ClassName> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
            for (AggregatedRootIr aggregatedRootIr : set) {
                arrayList.add(aggregatedRootIr.getRoot());
            }
            for (ClassName className : arrayList) {
                processedRootSentinelGenerator.generate(className);
            }
        }
    }
}
