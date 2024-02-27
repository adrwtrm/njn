package dagger.hilt.android.plugin;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import com.android.build.gradle.LibraryExtension;
import com.android.build.gradle.TestExtension;
import com.android.build.gradle.TestedExtension;
import com.android.build.gradle.api.AndroidBasePlugin;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.TestVariant;
import com.android.build.gradle.api.UnitTestVariant;
import com.android.build.gradle.internal.dsl.AnnotationProcessorOptions;
import com.epson.iprojection.common.CommonDefine;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.AndroidEntryPointClassVisitor;
import dagger.hilt.android.plugin.task.AggregateDepsTask;
import dagger.hilt.android.plugin.task.HiltTransformTestClassesTask;
import dagger.hilt.android.plugin.util.AggregatedPackagesTransform;
import dagger.hilt.android.plugin.util.AndroidComponentsExtensionCompat;
import dagger.hilt.android.plugin.util.ComponentCompat;
import dagger.hilt.android.plugin.util.CopyTransform;
import dagger.hilt.android.plugin.util.SimpleAGPVersion;
import dagger.hilt.android.plugin.util.StringsKt;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.gradle.api.Action;
import org.gradle.api.DomainObjectSet;
import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Transformer;
import org.gradle.api.artifacts.ArtifactView;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.component.ProjectComponentIdentifier;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.transform.TransformSpec;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.CompileOptions;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.process.CommandLineArgumentProvider;

/* compiled from: HiltGradlePlugin.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001&B\u000f\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0016J\u0018\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00122\u0006\u0010\n\u001a\u00020\u0002H\u0002J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0002J\u0018\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0002J(\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J(\u0010\u001b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J \u0010\u001c\u001a\n \u0013*\u0004\u0018\u00010\u001d0\u001d2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0002J/\u0010!\u001a\u00020\t*\u00020\u00182!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\t0#H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006'"}, d2 = {"Ldagger/hilt/android/plugin/HiltGradlePlugin;", "Lorg/gradle/api/Plugin;", "Lorg/gradle/api/Project;", "providers", "Lorg/gradle/api/provider/ProviderFactory;", "(Lorg/gradle/api/provider/ProviderFactory;)V", "getProviders", "()Lorg/gradle/api/provider/ProviderFactory;", "apply", "", "project", "configureAggregatingTask", "hiltExtension", "Ldagger/hilt/android/plugin/HiltExtension;", "configureBytecodeTransform", "configureBytecodeTransformASM", "configureCompileClasspath", "configureDependencyTransforms", "Lorg/gradle/api/artifacts/dsl/DependencyHandler;", "kotlin.jvm.PlatformType", "configureHilt", "configureProcessorFlags", "configureVariantAggregatingTask", "androidExtension", "Lcom/android/build/gradle/BaseExtension;", "variant", "Lcom/android/build/gradle/api/BaseVariant;", "configureVariantCompileClasspath", "getAndroidJar", "Lorg/gradle/api/file/ConfigurableFileCollection;", "compileSdkVersion", "", "verifyDependencies", "forEachRootVariant", "block", "Lkotlin/Function1;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class HiltGradlePlugin implements Plugin<Project> {
    public static final String AGGREGATED_HILT_ARTIFACT_TYPE_VALUE = "aggregated-jar-for-hilt";
    public static final String DAGGER_ARTIFACT_TYPE_VALUE = "jar-for-dagger";
    public static final String LIBRARY_GROUP = "com.google.dagger";
    private final ProviderFactory providers;
    public static final Companion Companion = new Companion(null);
    private static final Attribute<String> ARTIFACT_TYPE_ATTRIBUTE = Attribute.of("artifactType", String.class);
    private static final Function1<String, String> missingDepError = new Function1<String, String>() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$Companion$missingDepError$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(String depCoordinate) {
            Intrinsics.checkNotNullParameter(depCoordinate, "depCoordinate");
            return "The Hilt Android Gradle plugin is applied but no " + depCoordinate + " dependency was found.";
        }
    };

    /* renamed from: $r8$lambda$-USgy-k1JECAdvqHplcvb_4MR6I */
    public static /* synthetic */ FileTree m292$r8$lambda$USgyk1JECAdvqHplcvb_4MR6I(AggregateDepsTask aggregateDepsTask) {
        return m310configureVariantAggregatingTask$lambda31$lambda25(aggregateDepsTask);
    }

    public static /* synthetic */ void $r8$lambda$0gGVceuwdoisFG4fOdmJKznP3Ec(TransformSpec transformSpec) {
        m304configureDependencyTransforms$lambda6$lambda5(transformSpec);
    }

    public static /* synthetic */ void $r8$lambda$41va95H2urNE7Dthv9CZzgy9GeU(TaskProvider taskProvider, BaseVariant baseVariant, BaseExtension baseExtension, ConfigurableFileCollection configurableFileCollection, Project project, Configuration configuration, JavaCompile javaCompile) {
        m309configureVariantAggregatingTask$lambda31(taskProvider, baseVariant, baseExtension, configurableFileCollection, project, configuration, javaCompile);
    }

    public static /* synthetic */ FileCollection $r8$lambda$4sRaPugNsFL9BxD9PAnz9aHPbiQ(JavaCompile javaCompile) {
        return javaCompile.getClasspath();
    }

    public static /* synthetic */ void $r8$lambda$A8IIvEl28Uu7z1juGkjAnn0lIsA(String str, ArtifactView.ViewConfiguration viewConfiguration) {
        m305x7aa582a(str, viewConfiguration);
    }

    /* renamed from: $r8$lambda$BZk2pIgDZ-iCMeCT8Gi8Gy1M_7s */
    public static /* synthetic */ void m293$r8$lambda$BZk2pIgDZiCMeCT8Gi8Gy1M_7s(Function1 function1, UnitTestVariant unitTestVariant) {
        m315forEachRootVariant$lambda11(function1, unitTestVariant);
    }

    /* renamed from: $r8$lambda$IUkLNEcGDZfonTbeJQys-a5Vo4s */
    public static /* synthetic */ void m294$r8$lambda$IUkLNEcGDZfonTbeJQysa5Vo4s(Function1 function1, ApplicationVariant applicationVariant) {
        m317forEachRootVariant$lambda7(function1, applicationVariant);
    }

    public static /* synthetic */ void $r8$lambda$JNnXF3EWoEQKQFfYRMcRv4oqaNA(Project project, HiltExtension hiltExtension, UnitTestVariant unitTestVariant) {
        m301configureBytecodeTransform$lambda16(project, hiltExtension, unitTestVariant);
    }

    public static /* synthetic */ void $r8$lambda$L0fejH2KkxtPPuMilaUjU9EfgEg(Project project, ArtifactView.ViewConfiguration viewConfiguration) {
        m312configureVariantCompileClasspath$lambda15(project, viewConfiguration);
    }

    public static /* synthetic */ void $r8$lambda$Me39Y1l8xPYfmWcCxf_H51drebI(TransformSpec transformSpec) {
        m303configureDependencyTransforms$lambda6$lambda4(transformSpec);
    }

    /* renamed from: $r8$lambda$RyaGyRxEJ-yMXxh9C_oELGq9hiI */
    public static /* synthetic */ void m295$r8$lambda$RyaGyRxEJyMXxh9C_oELGq9hiI(Project project, BaseVariant baseVariant, HiltExtension hiltExtension, Configuration configuration, AggregateDepsTask aggregateDepsTask) {
        m308configureVariantAggregatingTask$lambda24(project, baseVariant, hiltExtension, configuration, aggregateDepsTask);
    }

    public static /* synthetic */ void $r8$lambda$ZP7w4LBlFcTcTJAylKZpPaAi7Ys(Function1 function1, UnitTestVariant unitTestVariant) {
        m319forEachRootVariant$lambda9(function1, unitTestVariant);
    }

    /* renamed from: $r8$lambda$a-SLx2prtk4TGsaBQwZR3ZfY5HY */
    public static /* synthetic */ Directory m296$r8$lambda$aSLx2prtk4TGsaBQwZR3ZfY5HY(JavaCompile javaCompile) {
        return m307configureVariantAggregatingTask$lambda19(javaCompile);
    }

    public static /* synthetic */ void $r8$lambda$f4E9TYHcVhWCHTqAdIjXVLzb_DQ(Ref.BooleanRef booleanRef, HiltGradlePlugin hiltGradlePlugin, Project project, AndroidBasePlugin androidBasePlugin) {
        m299apply$lambda0(booleanRef, hiltGradlePlugin, project, androidBasePlugin);
    }

    public static /* synthetic */ void $r8$lambda$fhG3T__rR8obyanjGel3gIHdprQ(Ref.BooleanRef booleanRef, HiltGradlePlugin hiltGradlePlugin, Project project) {
        m300apply$lambda2(booleanRef, hiltGradlePlugin, project);
    }

    public static /* synthetic */ void $r8$lambda$l5cT4K3QazXUiNyLxJ7THfj16xc(Function1 function1, TestVariant testVariant) {
        m314forEachRootVariant$lambda10(function1, testVariant);
    }

    /* renamed from: $r8$lambda$oPXrmEkZxpIee_qDfH-Tvu-5SMQ */
    public static /* synthetic */ void m297$r8$lambda$oPXrmEkZxpIee_qDfHTvu5SMQ(Function1 function1, TestVariant testVariant) {
        m318forEachRootVariant$lambda8(function1, testVariant);
    }

    /* renamed from: $r8$lambda$oYCdL5p6UZWe-JLGkCGsrHzkuCo */
    public static /* synthetic */ FileCollection m298$r8$lambda$oYCdL5p6UZWeJLGkCGsrHzkuCo(Project project, JavaCompile javaCompile) {
        return m311configureVariantAggregatingTask$lambda31$lambda26(project, javaCompile);
    }

    public static /* synthetic */ void $r8$lambda$qDgn3qbbiyPRMym8BREXtVGQHz0(Function1 function1, ApplicationVariant applicationVariant) {
        m316forEachRootVariant$lambda12(function1, applicationVariant);
    }

    public static /* synthetic */ void $r8$lambda$rToIRlpUjNVoQZgMlU895TbMOPc(TransformSpec transformSpec) {
        m302configureDependencyTransforms$lambda6$lambda3(transformSpec);
    }

    public static /* synthetic */ boolean $r8$lambda$th40B0vZmVNQvoYugx7PguMJyUc(Project project, ComponentIdentifier componentIdentifier) {
        return m313configureVariantCompileClasspath$lambda15$lambda14(project, componentIdentifier);
    }

    @Inject
    public HiltGradlePlugin(ProviderFactory providers) {
        Intrinsics.checkNotNullParameter(providers, "providers");
        this.providers = providers;
    }

    public static final /* synthetic */ void access$configureBytecodeTransformASM$registerTransform(HiltExtension hiltExtension, Ref.BooleanRef booleanRef, Project project, ComponentCompat componentCompat) {
        configureBytecodeTransformASM$registerTransform(hiltExtension, booleanRef, project, componentCompat);
    }

    public static final /* synthetic */ void access$configureVariantAggregatingTask(HiltGradlePlugin hiltGradlePlugin, Project project, HiltExtension hiltExtension, BaseExtension baseExtension, BaseVariant baseVariant) {
        hiltGradlePlugin.configureVariantAggregatingTask(project, hiltExtension, baseExtension, baseVariant);
    }

    public static final /* synthetic */ void access$configureVariantCompileClasspath(HiltGradlePlugin hiltGradlePlugin, Project project, HiltExtension hiltExtension, BaseExtension baseExtension, BaseVariant baseVariant) {
        hiltGradlePlugin.configureVariantCompileClasspath(project, hiltExtension, baseExtension, baseVariant);
    }

    public final ProviderFactory getProviders() {
        return this.providers;
    }

    public void apply(final Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        project.getPlugins().withType(AndroidBasePlugin.class, new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda10
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$f4E9TYHcVhWCHTqAdIjXVLzb_DQ(booleanRef, this, project, (AndroidBasePlugin) obj);
            }
        });
        project.afterEvaluate(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda12
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$fhG3T__rR8obyanjGel3gIHdprQ(booleanRef, this, (Project) obj);
            }
        });
    }

    /* renamed from: apply$lambda-0 */
    public static final void m299apply$lambda0(Ref.BooleanRef configured, HiltGradlePlugin this$0, Project project, AndroidBasePlugin androidBasePlugin) {
        Intrinsics.checkNotNullParameter(configured, "$configured");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(project, "$project");
        configured.element = true;
        this$0.configureHilt(project);
    }

    /* renamed from: apply$lambda-2 */
    public static final void m300apply$lambda2(Ref.BooleanRef configured, HiltGradlePlugin this$0, Project it) {
        Intrinsics.checkNotNullParameter(configured, "$configured");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!configured.element) {
            throw new IllegalStateException("The Hilt Android Gradle plugin can only be applied to an Android project.".toString());
        }
        Intrinsics.checkNotNullExpressionValue(it, "it");
        this$0.verifyDependencies(it);
    }

    private final void configureHilt(Project project) {
        HiltExtension hiltExtension = (HiltExtension) project.getExtensions().create(HiltExtension.class, "hilt", HiltExtensionImpl.class, new Object[0]);
        configureDependencyTransforms(project);
        Intrinsics.checkNotNullExpressionValue(hiltExtension, "hiltExtension");
        configureCompileClasspath(project, hiltExtension);
        if (SimpleAGPVersion.Companion.getANDROID_GRADLE_PLUGIN_VERSION().compareTo(new SimpleAGPVersion(4, 2)) < 0) {
            configureBytecodeTransform(project, hiltExtension);
        } else {
            configureBytecodeTransformASM(project, hiltExtension);
        }
        configureAggregatingTask(project, hiltExtension);
        configureProcessorFlags(project, hiltExtension);
    }

    private final DependencyHandler configureDependencyTransforms(Project project) {
        DependencyHandler dependencies = project.getDependencies();
        dependencies.registerTransform(CopyTransform.class, new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda13
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$rToIRlpUjNVoQZgMlU895TbMOPc((TransformSpec) obj);
            }
        });
        dependencies.registerTransform(CopyTransform.class, new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda14
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$Me39Y1l8xPYfmWcCxf_H51drebI((TransformSpec) obj);
            }
        });
        dependencies.registerTransform(AggregatedPackagesTransform.class, new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda15
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$0gGVceuwdoisFG4fOdmJKznP3Ec((TransformSpec) obj);
            }
        });
        return dependencies;
    }

    /* renamed from: configureDependencyTransforms$lambda-6$lambda-3 */
    public static final void m302configureDependencyTransforms$lambda6$lambda3(TransformSpec transformSpec) {
        AttributeContainer from = transformSpec.getFrom();
        Attribute<String> attribute = ARTIFACT_TYPE_ATTRIBUTE;
        from.attribute(attribute, "jar");
        transformSpec.getFrom().attribute(attribute, "android-classes");
        transformSpec.getTo().attribute(attribute, DAGGER_ARTIFACT_TYPE_VALUE);
    }

    /* renamed from: configureDependencyTransforms$lambda-6$lambda-4 */
    public static final void m303configureDependencyTransforms$lambda6$lambda4(TransformSpec transformSpec) {
        AttributeContainer from = transformSpec.getFrom();
        Attribute<String> attribute = ARTIFACT_TYPE_ATTRIBUTE;
        from.attribute(attribute, "directory");
        transformSpec.getTo().attribute(attribute, DAGGER_ARTIFACT_TYPE_VALUE);
    }

    /* renamed from: configureDependencyTransforms$lambda-6$lambda-5 */
    public static final void m304configureDependencyTransforms$lambda6$lambda5(TransformSpec transformSpec) {
        AttributeContainer from = transformSpec.getFrom();
        Attribute<String> attribute = ARTIFACT_TYPE_ATTRIBUTE;
        from.attribute(attribute, DAGGER_ARTIFACT_TYPE_VALUE);
        transformSpec.getTo().attribute(attribute, AGGREGATED_HILT_ARTIFACT_TYPE_VALUE);
    }

    private final void configureCompileClasspath(final Project project, final HiltExtension hiltExtension) {
        final BaseExtension baseExtension = (BaseExtension) project.getExtensions().findByType(BaseExtension.class);
        if (baseExtension == null) {
            throw new IllegalStateException("Android BaseExtension not found.".toString());
        }
        forEachRootVariant(baseExtension, new Function1<BaseVariant, Unit>() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$configureCompileClasspath$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                HiltGradlePlugin.this = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseVariant baseVariant) {
                invoke2(baseVariant);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void invoke2(BaseVariant variant) {
                Intrinsics.checkNotNullParameter(variant, "variant");
                HiltGradlePlugin.access$configureVariantCompileClasspath(HiltGradlePlugin.this, project, hiltExtension, baseExtension, variant);
            }
        });
    }

    private final void forEachRootVariant(BaseExtension baseExtension, final Function1<? super BaseVariant, Unit> function1) {
        if (baseExtension instanceof AppExtension) {
            AppExtension appExtension = (AppExtension) baseExtension;
            appExtension.getApplicationVariants().all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda19
                public final void execute(Object obj) {
                    HiltGradlePlugin.m294$r8$lambda$IUkLNEcGDZfonTbeJQysa5Vo4s(function1, (ApplicationVariant) obj);
                }
            });
            appExtension.getTestVariants().all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda20
                public final void execute(Object obj) {
                    HiltGradlePlugin.m297$r8$lambda$oPXrmEkZxpIee_qDfHTvu5SMQ(function1, (TestVariant) obj);
                }
            });
            appExtension.getUnitTestVariants().all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda1
                public final void execute(Object obj) {
                    HiltGradlePlugin.$r8$lambda$ZP7w4LBlFcTcTJAylKZpPaAi7Ys(function1, (UnitTestVariant) obj);
                }
            });
        } else if (baseExtension instanceof LibraryExtension) {
            LibraryExtension libraryExtension = (LibraryExtension) baseExtension;
            libraryExtension.getTestVariants().all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda2
                public final void execute(Object obj) {
                    HiltGradlePlugin.$r8$lambda$l5cT4K3QazXUiNyLxJ7THfj16xc(function1, (TestVariant) obj);
                }
            });
            libraryExtension.getUnitTestVariants().all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda3
                public final void execute(Object obj) {
                    HiltGradlePlugin.m293$r8$lambda$BZk2pIgDZiCMeCT8Gi8Gy1M_7s(function1, (UnitTestVariant) obj);
                }
            });
        } else if (baseExtension instanceof TestExtension) {
            ((TestExtension) baseExtension).getApplicationVariants().all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda4
                public final void execute(Object obj) {
                    HiltGradlePlugin.$r8$lambda$qDgn3qbbiyPRMym8BREXtVGQHz0(function1, (ApplicationVariant) obj);
                }
            });
        } else {
            throw new IllegalStateException(("Hilt plugin does not know how to configure '" + baseExtension + '\'').toString());
        }
    }

    /* renamed from: forEachRootVariant$lambda-7 */
    public static final void m317forEachRootVariant$lambda7(Function1 block, ApplicationVariant it) {
        Intrinsics.checkNotNullParameter(block, "$block");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        block.invoke(it);
    }

    /* renamed from: forEachRootVariant$lambda-8 */
    public static final void m318forEachRootVariant$lambda8(Function1 block, TestVariant it) {
        Intrinsics.checkNotNullParameter(block, "$block");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        block.invoke(it);
    }

    /* renamed from: forEachRootVariant$lambda-9 */
    public static final void m319forEachRootVariant$lambda9(Function1 block, UnitTestVariant it) {
        Intrinsics.checkNotNullParameter(block, "$block");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        block.invoke(it);
    }

    /* renamed from: forEachRootVariant$lambda-10 */
    public static final void m314forEachRootVariant$lambda10(Function1 block, TestVariant it) {
        Intrinsics.checkNotNullParameter(block, "$block");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        block.invoke(it);
    }

    /* renamed from: forEachRootVariant$lambda-11 */
    public static final void m315forEachRootVariant$lambda11(Function1 block, UnitTestVariant it) {
        Intrinsics.checkNotNullParameter(block, "$block");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        block.invoke(it);
    }

    /* renamed from: forEachRootVariant$lambda-12 */
    public static final void m316forEachRootVariant$lambda12(Function1 block, ApplicationVariant it) {
        Intrinsics.checkNotNullParameter(block, "$block");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        block.invoke(it);
    }

    public final void configureVariantCompileClasspath(final Project project, HiltExtension hiltExtension, BaseExtension baseExtension, BaseVariant baseVariant) {
        Configuration runtimeConfiguration;
        String stringPlus;
        if (!hiltExtension.getEnableExperimentalClasspathAggregation() || hiltExtension.getEnableAggregatingTask()) {
            return;
        }
        boolean isCheckReleaseBuilds = baseExtension.getLintOptions().isCheckReleaseBuilds();
        boolean z = false;
        if (isCheckReleaseBuilds && SimpleAGPVersion.Companion.getANDROID_GRADLE_PLUGIN_VERSION().compareTo(new SimpleAGPVersion(7, 0)) < 0) {
            throw new IllegalStateException("Invalid Hilt plugin configuration: When 'enableExperimentalClasspathAggregation' is enabled 'android.lintOptions.checkReleaseBuilds' has to be set to false unless com.android.tools.build:gradle:7.0.0+ is used.".toString());
        }
        List listOf = CollectionsKt.listOf((Object[]) new String[]{"android.injected.build.model.only", "android.injected.build.model.only.advanced", "android.injected.build.model.only.versioned", "android.injected.build.model.feature.full.dependencies", "android.injected.build.model.v2"});
        if (!(listOf instanceof Collection) || !listOf.isEmpty()) {
            Iterator it = listOf.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (getProviders().gradleProperty((String) it.next()).forUseAtConfigurationTime().isPresent()) {
                    z = true;
                    break;
                }
            }
        }
        if (z) {
            return;
        }
        boolean z2 = baseVariant instanceof TestVariant;
        if (z2) {
            runtimeConfiguration = ((TestVariant) baseVariant).getTestedVariant().getRuntimeConfiguration();
        } else {
            runtimeConfiguration = baseVariant.getRuntimeConfiguration();
        }
        ArtifactView artifactView = runtimeConfiguration.getIncoming().artifactView(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda16
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$L0fejH2KkxtPPuMilaUjU9EfgEg(project, (ArtifactView.ViewConfiguration) obj);
            }
        });
        if (z2) {
            StringBuilder sb = new StringBuilder("androidTest");
            String name = ((TestVariant) baseVariant).getName();
            Intrinsics.checkNotNullExpressionValue(name, "variant.name");
            stringPlus = sb.append(StringsKt.capitalize$default(kotlin.text.StringsKt.substringBeforeLast$default(name, "AndroidTest", (String) null, 2, (Object) null), null, 1, null)).append("CompileOnly").toString();
        } else if (baseVariant instanceof UnitTestVariant) {
            StringBuilder sb2 = new StringBuilder("test");
            String name2 = ((UnitTestVariant) baseVariant).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "variant.name");
            stringPlus = sb2.append(StringsKt.capitalize$default(kotlin.text.StringsKt.substringBeforeLast$default(name2, "UnitTest", (String) null, 2, (Object) null), null, 1, null)).append("CompileOnly").toString();
        } else {
            stringPlus = Intrinsics.stringPlus(baseVariant.getName(), "CompileOnly");
        }
        project.getDependencies().add(stringPlus, artifactView.getFiles());
    }

    /* renamed from: configureVariantCompileClasspath$lambda-15 */
    public static final void m312configureVariantCompileClasspath$lambda15(final Project project, ArtifactView.ViewConfiguration viewConfiguration) {
        Intrinsics.checkNotNullParameter(project, "$project");
        viewConfiguration.getAttributes().attribute(ARTIFACT_TYPE_ATTRIBUTE, DAGGER_ARTIFACT_TYPE_VALUE);
        viewConfiguration.componentFilter(new Spec() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda17
            public final boolean isSatisfiedBy(Object obj) {
                return HiltGradlePlugin.$r8$lambda$th40B0vZmVNQvoYugx7PguMJyUc(project, (ComponentIdentifier) obj);
            }
        });
    }

    /* renamed from: configureVariantCompileClasspath$lambda-15$lambda-14 */
    public static final boolean m313configureVariantCompileClasspath$lambda15$lambda14(Project project, ComponentIdentifier componentIdentifier) {
        Intrinsics.checkNotNullParameter(project, "$project");
        return ((componentIdentifier instanceof ProjectComponentIdentifier) && Intrinsics.areEqual(((ProjectComponentIdentifier) componentIdentifier).getProjectName(), project.getName())) ? false : true;
    }

    private final void configureBytecodeTransformASM(final Project project, final HiltExtension hiltExtension) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        AndroidComponentsExtensionCompat.Companion.getAndroidComponentsExtension(project).onAllVariants(new Function1<ComponentCompat, Unit>() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$configureBytecodeTransformASM$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ComponentCompat componentCompat) {
                invoke2(componentCompat);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void invoke2(ComponentCompat it) {
                Intrinsics.checkNotNullParameter(it, "it");
                HiltGradlePlugin.access$configureBytecodeTransformASM$registerTransform(hiltExtension, booleanRef, project, it);
            }
        });
    }

    public static final void configureBytecodeTransformASM$registerTransform(HiltExtension hiltExtension, Ref.BooleanRef booleanRef, final Project project, final ComponentCompat componentCompat) {
        if (hiltExtension.getEnableTransformForLocalTests() && !booleanRef.element) {
            project.getLogger().warn("The Hilt configuration option 'enableTransformForLocalTests' is no longer necessary when com.android.tools.build:gradle:4.2.0+ is used.");
            booleanRef.element = true;
        }
        componentCompat.transformClassesWith(AndroidEntryPointClassVisitor.Factory.class, InstrumentationScope.PROJECT, new Function1<AndroidEntryPointClassVisitor.AndroidEntryPointParams, Unit>() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$configureBytecodeTransformASM$registerTransform$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AndroidEntryPointClassVisitor.AndroidEntryPointParams androidEntryPointParams) {
                invoke2(androidEntryPointParams);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void invoke2(AndroidEntryPointClassVisitor.AndroidEntryPointParams params) {
                Intrinsics.checkNotNullParameter(params, "params");
                params.getAdditionalClassesDir().set(new File(project.getBuildDir(), "intermediates/javac/" + componentCompat.getName() + "/classes"));
            }
        });
        componentCompat.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS);
    }

    private final void configureBytecodeTransform(final Project project, final HiltExtension hiltExtension) {
        DomainObjectSet unitTestVariants;
        BaseExtension baseExtension = (BaseExtension) project.getExtensions().findByType(BaseExtension.class);
        if (baseExtension == null) {
            throw new IllegalStateException("Android BaseExtension not found.".toString());
        }
        baseExtension.getClass().getMethod("registerTransform", Class.forName("com.android.build.api.transform.Transform"), Object[].class).invoke(baseExtension, new AndroidEntryPointTransform(), new Object[0]);
        TestedExtension testedExtension = (TestedExtension) project.getExtensions().findByType(TestedExtension.class);
        if (testedExtension == null || (unitTestVariants = testedExtension.getUnitTestVariants()) == null) {
            return;
        }
        unitTestVariants.all(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda18
            public final void execute(Object obj) {
                HiltGradlePlugin.$r8$lambda$JNnXF3EWoEQKQFfYRMcRv4oqaNA(project, hiltExtension, (UnitTestVariant) obj);
            }
        });
    }

    /* renamed from: configureBytecodeTransform$lambda-16 */
    public static final void m301configureBytecodeTransform$lambda16(Project project, HiltExtension hiltExtension, UnitTestVariant unitTestVariant) {
        Intrinsics.checkNotNullParameter(project, "$project");
        Intrinsics.checkNotNullParameter(hiltExtension, "$hiltExtension");
        HiltTransformTestClassesTask.Companion companion = HiltTransformTestClassesTask.Companion;
        Intrinsics.checkNotNullExpressionValue(unitTestVariant, "unitTestVariant");
        companion.create(project, unitTestVariant, hiltExtension);
    }

    private final void configureAggregatingTask(final Project project, final HiltExtension hiltExtension) {
        final BaseExtension baseExtension = (BaseExtension) project.getExtensions().findByType(BaseExtension.class);
        if (baseExtension == null) {
            throw new IllegalStateException("Android BaseExtension not found.".toString());
        }
        forEachRootVariant(baseExtension, new Function1<BaseVariant, Unit>() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$configureAggregatingTask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                HiltGradlePlugin.this = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseVariant baseVariant) {
                invoke2(baseVariant);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void invoke2(BaseVariant variant) {
                Intrinsics.checkNotNullParameter(variant, "variant");
                HiltGradlePlugin.access$configureVariantAggregatingTask(HiltGradlePlugin.this, project, hiltExtension, baseExtension, variant);
            }
        });
    }

    public final void configureVariantAggregatingTask(final Project project, final HiltExtension hiltExtension, final BaseExtension baseExtension, final BaseVariant baseVariant) {
        if (hiltExtension.getEnableAggregatingTask()) {
            ConfigurationContainer configurations = project.getConfigurations();
            String name = baseVariant.getName();
            Intrinsics.checkNotNullExpressionValue(name, "variant.name");
            final Configuration configuration = (Configuration) configurations.create(Intrinsics.stringPlus("hiltCompileOnly", StringsKt.capitalize$default(name, null, 1, null)));
            configuration.setCanBeConsumed(false);
            configuration.setCanBeResolved(true);
            project.getDependencies().add(configuration.getName(), project.files(new Object[]{baseVariant.getJavaCompileProvider().map(new Transformer() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda6
                public final Object transform(Object obj) {
                    return HiltGradlePlugin.$r8$lambda$4sRaPugNsFL9BxD9PAnz9aHPbiQ((JavaCompile) obj);
                }
            })}));
            project.getDependencies().add(configuration.getName(), project.files(new Object[]{baseVariant.getJavaCompileProvider().map(new Transformer() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda7
                public final Object transform(Object obj) {
                    return HiltGradlePlugin.m296$r8$lambda$aSLx2prtk4TGsaBQwZR3ZfY5HY((JavaCompile) obj);
                }
            })}));
            TaskContainer tasks = project.getTasks();
            String name2 = baseVariant.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "variant.name");
            final TaskProvider register = tasks.register(Intrinsics.stringPlus("hiltAggregateDeps", StringsKt.capitalize$default(name2, null, 1, null)), AggregateDepsTask.class, new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda8
                public final void execute(Object obj) {
                    HiltGradlePlugin.m295$r8$lambda$RyaGyRxEJyMXxh9C_oELGq9hiI(project, baseVariant, hiltExtension, configuration, (AggregateDepsTask) obj);
                }
            });
            File buildDir = project.getBuildDir();
            Intrinsics.checkNotNullExpressionValue(buildDir, "project.buildDir");
            final FileCollection files = project.files(new Object[]{FilesKt.resolve(buildDir, "intermediates/hilt/component_classes/" + ((Object) baseVariant.getName()) + '/')});
            TaskContainer tasks2 = project.getTasks();
            String name3 = baseVariant.getName();
            Intrinsics.checkNotNullExpressionValue(name3, "variant.name");
            files.builtBy(new Object[]{tasks2.register(Intrinsics.stringPlus("hiltJavaCompile", StringsKt.capitalize$default(name3, null, 1, null)), JavaCompile.class, new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda9
                public final void execute(Object obj) {
                    HiltGradlePlugin.$r8$lambda$41va95H2urNE7Dthv9CZzgy9GeU(register, baseVariant, baseExtension, files, project, configuration, (JavaCompile) obj);
                }
            })});
            baseVariant.registerPostJavacGeneratedBytecode(files);
        }
    }

    /* renamed from: configureVariantAggregatingTask$lambda-19 */
    public static final Directory m307configureVariantAggregatingTask$lambda19(JavaCompile javaCompile) {
        return (Directory) javaCompile.getDestinationDirectory().get();
    }

    private static final ConfigurableFileCollection configureVariantAggregatingTask$getInputClasspath(BaseVariant baseVariant, Configuration hiltCompileConfiguration, Project project, final String str) {
        ArrayList arrayList = new ArrayList();
        if (baseVariant instanceof TestVariant) {
            Configuration runtimeConfiguration = ((TestVariant) baseVariant).getTestedVariant().getRuntimeConfiguration();
            Intrinsics.checkNotNullExpressionValue(runtimeConfiguration, "variant.testedVariant.runtimeConfiguration");
            arrayList.add(runtimeConfiguration);
        }
        Configuration runtimeConfiguration2 = baseVariant.getRuntimeConfiguration();
        Intrinsics.checkNotNullExpressionValue(runtimeConfiguration2, "variant.runtimeConfiguration");
        arrayList.add(runtimeConfiguration2);
        Intrinsics.checkNotNullExpressionValue(hiltCompileConfiguration, "hiltCompileConfiguration");
        arrayList.add(hiltCompileConfiguration);
        ArrayList<Configuration> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (Configuration configuration : arrayList2) {
            arrayList3.add(configuration.getIncoming().artifactView(new Action() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda5
                public final void execute(Object obj) {
                    HiltGradlePlugin.$r8$lambda$A8IIvEl28Uu7z1juGkjAnn0lIsA(str, (ArtifactView.ViewConfiguration) obj);
                }
            }).getFiles());
        }
        Object[] array = arrayList3.toArray(new FileCollection[0]);
        if (array != null) {
            FileCollection[] fileCollectionArr = (FileCollection[]) array;
            return project.files(Arrays.copyOf(fileCollectionArr, fileCollectionArr.length));
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    /* renamed from: configureVariantAggregatingTask$getInputClasspath$lambda-22$lambda-21 */
    public static final void m305x7aa582a(String artifactAttributeValue, ArtifactView.ViewConfiguration viewConfiguration) {
        Intrinsics.checkNotNullParameter(artifactAttributeValue, "$artifactAttributeValue");
        viewConfiguration.getAttributes().attribute(ARTIFACT_TYPE_ATTRIBUTE, artifactAttributeValue);
    }

    /* renamed from: configureVariantAggregatingTask$lambda-24 */
    public static final void m308configureVariantAggregatingTask$lambda24(Project project, BaseVariant variant, HiltExtension hiltExtension, Configuration configuration, AggregateDepsTask aggregateDepsTask) {
        Intrinsics.checkNotNullParameter(project, "$project");
        Intrinsics.checkNotNullParameter(variant, "$variant");
        Intrinsics.checkNotNullParameter(hiltExtension, "$hiltExtension");
        aggregateDepsTask.getCompileClasspath().setFrom(configureVariantAggregatingTask$getInputClasspath(variant, configuration, project, AGGREGATED_HILT_ARTIFACT_TYPE_VALUE));
        DirectoryProperty outputDir = aggregateDepsTask.getOutputDir();
        File buildDir = project.getBuildDir();
        Intrinsics.checkNotNullExpressionValue(buildDir, "project.buildDir");
        outputDir.set(project.file(FilesKt.resolve(buildDir, "generated/hilt/component_trees/" + ((Object) variant.getName()) + '/')));
        aggregateDepsTask.getTestEnvironment().set(Boolean.valueOf((variant instanceof TestVariant) || (variant instanceof UnitTestVariant)));
        aggregateDepsTask.getCrossCompilationRootValidationDisabled().set(Boolean.valueOf(hiltExtension.getDisableCrossCompilationRootValidation()));
    }

    /* renamed from: configureVariantAggregatingTask$lambda-31 */
    public static final void m309configureVariantAggregatingTask$lambda31(TaskProvider taskProvider, BaseVariant variant, BaseExtension androidExtension, ConfigurableFileCollection configurableFileCollection, final Project project, Configuration configuration, JavaCompile javaCompile) {
        Intrinsics.checkNotNullParameter(variant, "$variant");
        Intrinsics.checkNotNullParameter(androidExtension, "$androidExtension");
        Intrinsics.checkNotNullParameter(project, "$project");
        javaCompile.setSource((FileTree) taskProvider.map(new Transformer() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda0
            public final Object transform(Object obj) {
                return HiltGradlePlugin.m292$r8$lambda$USgyk1JECAdvqHplcvb_4MR6I((AggregateDepsTask) obj);
            }
        }).get());
        FileCollection fileCollection = (FileCollection) variant.getJavaCompileProvider().map(new Transformer() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$$ExternalSyntheticLambda11
            public final Object transform(Object obj) {
                return HiltGradlePlugin.m298$r8$lambda$oYCdL5p6UZWeJLGkCGsrHzkuCo(project, (JavaCompile) obj);
            }
        }).get();
        if (JavaVersion.current().isJava9Compatible() && androidExtension.getCompileOptions().getTargetCompatibility().isJava9Compatible()) {
            javaCompile.setClasspath(configureVariantAggregatingTask$getInputClasspath(variant, configuration, project, DAGGER_ARTIFACT_TYPE_VALUE).plus(fileCollection));
            List<CommandLineArgumentProvider> compilerArgumentProviders = ((JavaCompile) variant.getJavaCompileProvider().get()).getOptions().getCompilerArgumentProviders();
            Intrinsics.checkNotNullExpressionValue(compilerArgumentProviders, "originalCompileTask.opti…compilerArgumentProviders");
            for (CommandLineArgumentProvider commandLineArgumentProvider : compilerArgumentProviders) {
                javaCompile.getOptions().getCompilerArgumentProviders().add(commandLineArgumentProvider);
            }
            javaCompile.getOptions().getCompilerArgs().add("-XDstringConcat=inline");
        } else {
            javaCompile.setClasspath(configureVariantAggregatingTask$getInputClasspath(variant, configuration, project, DAGGER_ARTIFACT_TYPE_VALUE));
            javaCompile.getOptions().setBootstrapClasspath(fileCollection);
        }
        javaCompile.getDestinationDirectory().set(configurableFileCollection.getSingleFile());
        CompileOptions options = javaCompile.getOptions();
        ConfigurationContainer configurations = project.getConfigurations();
        String name = variant.getName();
        Intrinsics.checkNotNullExpressionValue(name, "variant.name");
        Object create = configurations.create(Intrinsics.stringPlus("hiltAnnotationProcessor", StringsKt.capitalize$default(name, null, 1, null)));
        project.getDependencies().add(((Configuration) create).getName(), Intrinsics.stringPlus("com.google.dagger:hilt-compiler:", VersionKt.getHILT_VERSION()));
        Unit unit = Unit.INSTANCE;
        options.setAnnotationProcessorPath((FileCollection) create);
        DirectoryProperty generatedSourceOutputDirectory = options.getGeneratedSourceOutputDirectory();
        File buildDir = project.getBuildDir();
        Intrinsics.checkNotNullExpressionValue(buildDir, "project.buildDir");
        generatedSourceOutputDirectory.set(project.file(FilesKt.resolve(buildDir, "generated/hilt/component_sources/" + ((Object) variant.getName()) + '/')));
        if (JavaVersion.current().isJava8Compatible() && androidExtension.getCompileOptions().getTargetCompatibility().isJava8Compatible()) {
            options.getCompilerArgs().add("-parameters");
        }
        options.getCompilerArgs().add("-Adagger.fastInit=enabled");
        options.getCompilerArgs().add("-Adagger.hilt.internal.useAggregatingRootProcessor=false");
        options.getCompilerArgs().add("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true");
        options.setEncoding(androidExtension.getCompileOptions().getEncoding());
        javaCompile.setSourceCompatibility(androidExtension.getCompileOptions().getSourceCompatibility().toString());
        javaCompile.setTargetCompatibility(androidExtension.getCompileOptions().getTargetCompatibility().toString());
    }

    /* renamed from: configureVariantAggregatingTask$lambda-31$lambda-25 */
    public static final FileTree m310configureVariantAggregatingTask$lambda31$lambda25(AggregateDepsTask aggregateDepsTask) {
        return aggregateDepsTask.getOutputDir().getAsFileTree();
    }

    /* renamed from: configureVariantAggregatingTask$lambda-31$lambda-26 */
    public static final FileCollection m311configureVariantAggregatingTask$lambda31$lambda26(Project project, JavaCompile javaCompile) {
        Intrinsics.checkNotNullParameter(project, "$project");
        FileCollection bootstrapClasspath = javaCompile.getOptions().getBootstrapClasspath();
        return bootstrapClasspath == null ? project.files(new Object[0]) : bootstrapClasspath;
    }

    private final ConfigurableFileCollection getAndroidJar(Project project, String str) {
        return project.files(new Object[]{new File(dagger.hilt.android.plugin.util.FilesKt.getSdkPath(project), "platforms/" + str + "/android.jar")});
    }

    private final void configureProcessorFlags(Project project, final HiltExtension hiltExtension) {
        BaseExtension baseExtension = (BaseExtension) project.getExtensions().findByType(BaseExtension.class);
        if (baseExtension == null) {
            throw new IllegalStateException("Android BaseExtension not found.".toString());
        }
        AnnotationProcessorOptions annotationProcessorOptions = baseExtension.getDefaultConfig().getJavaCompileOptions().getAnnotationProcessorOptions();
        annotationProcessorOptions.argument("dagger.fastInit", "enabled");
        annotationProcessorOptions.argument("dagger.hilt.android.internal.disableAndroidSuperclassValidation", CommonDefine.TRUE);
        annotationProcessorOptions.compilerArgumentProvider(new CommandLineArgumentProvider() { // from class: dagger.hilt.android.plugin.HiltGradlePlugin$configureProcessorFlags$1$1
            public List<String> asArguments() {
                ArrayList arrayList = new ArrayList();
                HiltExtension hiltExtension2 = hiltExtension;
                if (hiltExtension2.getEnableAggregatingTask()) {
                    arrayList.add("-Adagger.hilt.internal.useAggregatingRootProcessor=false");
                }
                if (hiltExtension2.getDisableCrossCompilationRootValidation()) {
                    arrayList.add("-Adagger.hilt.disableCrossCompilationRootValidation=true");
                }
                return arrayList;
            }
        });
    }

    private final void verifyDependencies(Project project) {
        if (project.getState().getFailure() != null) {
            return;
        }
        Iterable<Configuration> configurations = project.getConfigurations();
        Intrinsics.checkNotNullExpressionValue(configurations, "project.configurations");
        ArrayList arrayList = new ArrayList();
        for (Configuration configuration : configurations) {
            Iterable dependencies = configuration.getDependencies();
            Intrinsics.checkNotNullExpressionValue(dependencies, "configuration.dependencies");
            Iterable<Dependency> iterable = dependencies;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
            for (Dependency dependency : iterable) {
                arrayList2.add(TuplesKt.to(dependency.getGroup(), dependency.getName()));
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        ArrayList arrayList3 = arrayList;
        if (!arrayList3.contains(TuplesKt.to(LIBRARY_GROUP, "hilt-android"))) {
            throw new IllegalStateException(missingDepError.invoke("com.google.dagger:hilt-android").toString());
        }
        if (!arrayList3.contains(TuplesKt.to(LIBRARY_GROUP, "hilt-android-compiler")) && !arrayList3.contains(TuplesKt.to(LIBRARY_GROUP, "hilt-compiler"))) {
            throw new IllegalStateException(missingDepError.invoke("com.google.dagger:hilt-compiler").toString());
        }
    }

    /* compiled from: HiltGradlePlugin.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R5\u0010\u0005\u001a&\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00040\u0004 \u0007*\u0012\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00040\u0004\u0018\u00010\u00060\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Ldagger/hilt/android/plugin/HiltGradlePlugin$Companion;", "", "()V", "AGGREGATED_HILT_ARTIFACT_TYPE_VALUE", "", "ARTIFACT_TYPE_ATTRIBUTE", "Lorg/gradle/api/attributes/Attribute;", "kotlin.jvm.PlatformType", "getARTIFACT_TYPE_ATTRIBUTE", "()Lorg/gradle/api/attributes/Attribute;", "DAGGER_ARTIFACT_TYPE_VALUE", "LIBRARY_GROUP", "missingDepError", "Lkotlin/Function1;", "getMissingDepError", "()Lkotlin/jvm/functions/Function1;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Attribute<String> getARTIFACT_TYPE_ATTRIBUTE() {
            return HiltGradlePlugin.ARTIFACT_TYPE_ATTRIBUTE;
        }

        public final Function1<String, String> getMissingDepError() {
            return HiltGradlePlugin.missingDepError;
        }
    }
}
