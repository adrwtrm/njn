package dagger.hilt.android.plugin.task;

import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.UnitTestVariant;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.AndroidEntryPointClassTransformer;
import dagger.hilt.android.plugin.HiltExtension;
import dagger.hilt.android.plugin.task.HiltTransformTestClassesTask;
import dagger.hilt.android.plugin.util.StringsKt;
import dagger.hilt.android.plugin.util.TasksKt;
import java.io.File;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Transformer;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.testing.Test;
import org.gradle.workers.WorkAction;
import org.gradle.workers.WorkParameters;
import org.gradle.workers.WorkerExecutor;
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper;
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile;

/* compiled from: HiltTransformTestClassesTask.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u0000 \u000f2\u00020\u0001:\u0004\u000f\u0010\u0011\u0012B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0007R\u0014\u0010\u0005\u001a\u00020\u00068gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask;", "Lorg/gradle/api/DefaultTask;", "workerExecutor", "Lorg/gradle/workers/WorkerExecutor;", "(Lorg/gradle/workers/WorkerExecutor;)V", "compiledClasses", "Lorg/gradle/api/file/ConfigurableFileCollection;", "getCompiledClasses", "()Lorg/gradle/api/file/ConfigurableFileCollection;", "outputDir", "Lorg/gradle/api/file/DirectoryProperty;", "getOutputDir", "()Lorg/gradle/api/file/DirectoryProperty;", "transformClasses", "", "Companion", "ConfigAction", "Parameters", "WorkerAction", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public abstract class HiltTransformTestClassesTask extends DefaultTask {
    public static final Companion Companion = new Companion(null);
    private static final String TASK_PREFIX = "hiltTransformFor";
    private final WorkerExecutor workerExecutor;

    /* compiled from: HiltTransformTestClassesTask.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b`\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask$Parameters;", "Lorg/gradle/workers/WorkParameters;", "compiledClasses", "Lorg/gradle/api/file/ConfigurableFileCollection;", "getCompiledClasses", "()Lorg/gradle/api/file/ConfigurableFileCollection;", AppMeasurementSdk.ConditionalUserProperty.NAME, "Lorg/gradle/api/provider/Property;", "", "getName", "()Lorg/gradle/api/provider/Property;", "outputDir", "Lorg/gradle/api/file/DirectoryProperty;", "getOutputDir", "()Lorg/gradle/api/file/DirectoryProperty;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public interface Parameters extends WorkParameters {
        ConfigurableFileCollection getCompiledClasses();

        Property<String> getName();

        DirectoryProperty getOutputDir();
    }

    /* renamed from: $r8$lambda$7kTMr-hOooh3_vN0_pLke56u4VI */
    public static /* synthetic */ void m323$r8$lambda$7kTMrhOooh3_vN0_pLke56u4VI(HiltTransformTestClassesTask hiltTransformTestClassesTask, Parameters parameters) {
        m324transformClasses$lambda0(hiltTransformTestClassesTask, parameters);
    }

    @Classpath
    public abstract ConfigurableFileCollection getCompiledClasses();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @Inject
    public HiltTransformTestClassesTask(WorkerExecutor workerExecutor) {
        Intrinsics.checkNotNullParameter(workerExecutor, "workerExecutor");
        this.workerExecutor = workerExecutor;
    }

    /* compiled from: HiltTransformTestClassesTask.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask$WorkerAction;", "Lorg/gradle/workers/WorkAction;", "Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask$Parameters;", "()V", "execute", "", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static abstract class WorkerAction implements WorkAction<Parameters> {
        public void execute() {
            File outputDir = (File) ((Parameters) getParameters()).getOutputDir().getAsFile().get();
            Intrinsics.checkNotNullExpressionValue(outputDir, "outputDir");
            FilesKt.deleteRecursively(outputDir);
            outputDir.mkdirs();
            Set files = ((Parameters) getParameters()).getCompiledClasses().getFiles();
            Intrinsics.checkNotNullExpressionValue(files, "parameters.compiledClasses.files");
            List list = CollectionsKt.toList(files);
            Object obj = ((Parameters) getParameters()).getName().get();
            Intrinsics.checkNotNullExpressionValue(obj, "parameters.name.get()");
            AndroidEntryPointClassTransformer androidEntryPointClassTransformer = new AndroidEntryPointClassTransformer((String) obj, list, outputDir, false);
            for (File it : CollectionsKt.reversed(list)) {
                if (it.isDirectory()) {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    for (File file : FilesKt.walkTopDown(it)) {
                        if (dagger.hilt.android.plugin.util.FilesKt.isClassFile(file)) {
                            androidEntryPointClassTransformer.transformFile(file);
                        }
                    }
                } else {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    if (dagger.hilt.android.plugin.util.FilesKt.isJarFile(it)) {
                        androidEntryPointClassTransformer.transformJarContents(it);
                    }
                }
            }
        }
    }

    @TaskAction
    public final void transformClasses() {
        this.workerExecutor.noIsolation().submit(WorkerAction.class, new Action() { // from class: dagger.hilt.android.plugin.task.HiltTransformTestClassesTask$$ExternalSyntheticLambda0
            {
                HiltTransformTestClassesTask.this = this;
            }

            public final void execute(Object obj) {
                HiltTransformTestClassesTask.m323$r8$lambda$7kTMrhOooh3_vN0_pLke56u4VI(HiltTransformTestClassesTask.this, (HiltTransformTestClassesTask.Parameters) obj);
            }
        });
    }

    /* renamed from: transformClasses$lambda-0 */
    public static final void m324transformClasses$lambda0(HiltTransformTestClassesTask this$0, Parameters parameters) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        parameters.getCompiledClasses().from(new Object[]{this$0.getCompiledClasses()});
        parameters.getOutputDir().set(this$0.getOutputDir());
        parameters.getName().set(this$0.getName());
    }

    /* compiled from: HiltTransformTestClassesTask.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask$ConfigAction;", "Lorg/gradle/api/Action;", "Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask;", "outputDir", "Ljava/io/File;", "inputClasspath", "Lorg/gradle/api/file/FileCollection;", "(Ljava/io/File;Lorg/gradle/api/file/FileCollection;)V", "execute", "", "transformTask", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class ConfigAction implements Action<HiltTransformTestClassesTask> {
        private final FileCollection inputClasspath;
        private final File outputDir;

        public ConfigAction(File outputDir, FileCollection inputClasspath) {
            Intrinsics.checkNotNullParameter(outputDir, "outputDir");
            Intrinsics.checkNotNullParameter(inputClasspath, "inputClasspath");
            this.outputDir = outputDir;
            this.inputClasspath = inputClasspath;
        }

        public void execute(HiltTransformTestClassesTask transformTask) {
            Intrinsics.checkNotNullParameter(transformTask, "transformTask");
            transformTask.setDescription("Transforms AndroidEntryPoint annotated classes for JUnit tests.");
            transformTask.getOutputDir().set(this.outputDir);
            transformTask.getCompiledClasses().from(new Object[]{this.inputClasspath});
        }
    }

    /* compiled from: HiltTransformTestClassesTask.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Ldagger/hilt/android/plugin/task/HiltTransformTestClassesTask$Companion;", "", "()V", "TASK_PREFIX", "", "create", "", "project", "Lorg/gradle/api/Project;", "unitTestVariant", "Lcom/android/build/gradle/api/UnitTestVariant;", "extension", "Ldagger/hilt/android/plugin/HiltExtension;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        /* renamed from: $r8$lambda$EAq-7T_dcAgXgBoSIqlv97wPMB4 */
        public static /* synthetic */ void m325$r8$lambda$EAq7T_dcAgXgBoSIqlv97wPMB4(UnitTestVariant unitTestVariant, Project project, ConfigurableFileCollection configurableFileCollection, KotlinBasePluginWrapper kotlinBasePluginWrapper) {
            m328create$lambda2(unitTestVariant, project, configurableFileCollection, kotlinBasePluginWrapper);
        }

        public static /* synthetic */ DirectoryProperty $r8$lambda$FVKrQTjMKk1qUUzPusT_70IZCe4(KotlinCompile kotlinCompile) {
            return kotlinCompile.getDestinationDirectory();
        }

        public static /* synthetic */ DirectoryProperty $r8$lambda$Rz9ZfYe0TRVFSUEFeO2mQvLGcok(HiltTransformTestClassesTask hiltTransformTestClassesTask) {
            return hiltTransformTestClassesTask.getOutputDir();
        }

        /* renamed from: $r8$lambda$WwGAkBRJEqtaNqa-Sj0ZzBGgQrw */
        public static /* synthetic */ void m326$r8$lambda$WwGAkBRJEqtaNqaSj0ZzBGgQrw(ConfigurableFileCollection configurableFileCollection, Test test) {
            m331create$lambda4(configurableFileCollection, test);
        }

        public static /* synthetic */ DirectoryProperty $r8$lambda$vAe5Klq_WaBoAglPlAonqpyI80I(JavaCompile javaCompile) {
            return javaCompile.getDestinationDirectory();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void create(final Project project, final UnitTestVariant unitTestVariant, HiltExtension extension) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(unitTestVariant, "unitTestVariant");
            Intrinsics.checkNotNullParameter(extension, "extension");
            if (extension.getEnableTransformForLocalTests()) {
                final FileCollection inputClasspath = project.files(new Object[]{unitTestVariant.getCompileClasspath((Object) null)});
                inputClasspath.from(new Object[]{unitTestVariant.getJavaCompileProvider().map(new Transformer() { // from class: dagger.hilt.android.plugin.task.HiltTransformTestClassesTask$Companion$$ExternalSyntheticLambda0
                    public final Object transform(Object obj) {
                        return HiltTransformTestClassesTask.Companion.$r8$lambda$vAe5Klq_WaBoAglPlAonqpyI80I((JavaCompile) obj);
                    }
                })});
                project.getPlugins().withType(KotlinBasePluginWrapper.class, new Action() { // from class: dagger.hilt.android.plugin.task.HiltTransformTestClassesTask$Companion$$ExternalSyntheticLambda1
                    public final void execute(Object obj) {
                        HiltTransformTestClassesTask.Companion.m325$r8$lambda$EAq7T_dcAgXgBoSIqlv97wPMB4(unitTestVariant, project, inputClasspath, (KotlinBasePluginWrapper) obj);
                    }
                });
                File buildDir = project.getBuildDir();
                Intrinsics.checkNotNullExpressionValue(buildDir, "project.buildDir");
                File resolve = FilesKt.resolve(buildDir, "intermediates/hilt/" + ((Object) unitTestVariant.getDirName()) + "Output");
                TaskContainer tasks = project.getTasks();
                String name = unitTestVariant.getName();
                Intrinsics.checkNotNullExpressionValue(name, "unitTestVariant.name");
                String stringPlus = Intrinsics.stringPlus(HiltTransformTestClassesTask.TASK_PREFIX, StringsKt.capitalize$default(name, null, 1, null));
                Intrinsics.checkNotNullExpressionValue(inputClasspath, "inputClasspath");
                final ConfigurableFileCollection files = project.files(new Object[]{tasks.register(stringPlus, HiltTransformTestClassesTask.class, new ConfigAction(resolve, inputClasspath)).map(new Transformer() { // from class: dagger.hilt.android.plugin.task.HiltTransformTestClassesTask$Companion$$ExternalSyntheticLambda2
                    public final Object transform(Object obj) {
                        return HiltTransformTestClassesTask.Companion.$r8$lambda$Rz9ZfYe0TRVFSUEFeO2mQvLGcok((HiltTransformTestClassesTask) obj);
                    }
                })});
                TaskContainer tasks2 = project.getTasks();
                String name2 = unitTestVariant.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "unitTestVariant.name");
                TaskProvider named = tasks2.named(Intrinsics.stringPlus("test", StringsKt.capitalize$default(name2, null, 1, null)));
                if (named == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.gradle.api.tasks.TaskProvider<org.gradle.api.tasks.testing.Test>");
                }
                named.configure(new Action() { // from class: dagger.hilt.android.plugin.task.HiltTransformTestClassesTask$Companion$$ExternalSyntheticLambda3
                    public final void execute(Object obj) {
                        HiltTransformTestClassesTask.Companion.m326$r8$lambda$WwGAkBRJEqtaNqaSj0ZzBGgQrw(files, (Test) obj);
                    }
                });
            }
        }

        /* renamed from: create$lambda-2 */
        public static final void m328create$lambda2(UnitTestVariant unitTestVariant, Project project, ConfigurableFileCollection configurableFileCollection, KotlinBasePluginWrapper kotlinBasePluginWrapper) {
            Intrinsics.checkNotNullParameter(unitTestVariant, "$unitTestVariant");
            Intrinsics.checkNotNullParameter(project, "$project");
            configurableFileCollection.from(new Object[]{TasksKt.getCompileKotlin((BaseVariant) unitTestVariant, project).map(new Transformer() { // from class: dagger.hilt.android.plugin.task.HiltTransformTestClassesTask$Companion$$ExternalSyntheticLambda4
                public final Object transform(Object obj) {
                    return HiltTransformTestClassesTask.Companion.$r8$lambda$FVKrQTjMKk1qUUzPusT_70IZCe4((KotlinCompile) obj);
                }
            })});
        }

        /* renamed from: create$lambda-4 */
        public static final void m331create$lambda4(ConfigurableFileCollection configurableFileCollection, Test test) {
            test.setClasspath(configurableFileCollection.plus(test.getClasspath()));
        }
    }
}
