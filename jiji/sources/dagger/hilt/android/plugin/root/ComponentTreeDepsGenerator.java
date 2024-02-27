package dagger.hilt.android.plugin.root;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIr;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Modifier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ComponentTreeDepsGenerator.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0002\b\u0002\b\u0000\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r*\b\u0012\u0004\u0012\u00020\u00040\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Ldagger/hilt/android/plugin/root/ComponentTreeDepsGenerator;", "", "proxies", "", "Lcom/squareup/javapoet/ClassName;", "outputDir", "Ljava/io/File;", "(Ljava/util/Map;Ljava/io/File;)V", "generate", "", "componentTree", "Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIr;", "toMaybeProxies", "", "", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ComponentTreeDepsGenerator {
    private static final ClassName COMPONENT_TREE_DEPS_ANNOTATION;
    public static final Companion Companion = new Companion(null);
    private final File outputDir;
    private final Map<ClassName, ClassName> proxies;

    public ComponentTreeDepsGenerator(Map<ClassName, ClassName> proxies, File outputDir) {
        Intrinsics.checkNotNullParameter(proxies, "proxies");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        this.proxies = proxies;
        this.outputDir = outputDir;
    }

    public final void generate(ComponentTreeDepsIr componentTree) {
        Intrinsics.checkNotNullParameter(componentTree, "componentTree");
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(componentTree.getName());
        AnnotationSpec.Builder builder = AnnotationSpec.builder(COMPONENT_TREE_DEPS_ANNOTATION);
        for (ClassName className : toMaybeProxies(componentTree.getRootDeps())) {
            builder.addMember("rootDeps", "$T.class", className);
        }
        for (ClassName className2 : toMaybeProxies(componentTree.getDefineComponentDeps())) {
            builder.addMember("defineComponentDeps", "$T.class", className2);
        }
        for (ClassName className3 : toMaybeProxies(componentTree.getAliasOfDeps())) {
            builder.addMember("aliasOfDeps", "$T.class", className3);
        }
        for (ClassName className4 : toMaybeProxies(componentTree.getAggregatedDeps())) {
            builder.addMember("aggregatedDeps", "$T.class", className4);
        }
        for (ClassName className5 : toMaybeProxies(componentTree.getUninstallModulesDeps())) {
            builder.addMember("uninstallModulesDeps", "$T.class", className5);
        }
        for (ClassName className6 : toMaybeProxies(componentTree.getEarlyEntryPointDeps())) {
            builder.addMember("earlyEntryPointDeps", "$T.class", className6);
        }
        Unit unit = Unit.INSTANCE;
        JavaFile.builder(componentTree.getName().packageName(), classBuilder.addAnnotation(builder.build()).addModifiers(Modifier.PUBLIC, Modifier.FINAL).build()).build().writeTo(this.outputDir);
    }

    private final List<ClassName> toMaybeProxies(Collection<ClassName> collection) {
        List<ClassName> sorted = CollectionsKt.sorted(collection);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(sorted, 10));
        for (ClassName className : sorted) {
            ClassName className2 = this.proxies.get(className);
            if (className2 != null) {
                className = className2;
            }
            arrayList.add(className);
        }
        return arrayList;
    }

    /* compiled from: ComponentTreeDepsGenerator.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Ldagger/hilt/android/plugin/root/ComponentTreeDepsGenerator$Companion;", "", "()V", "COMPONENT_TREE_DEPS_ANNOTATION", "Lcom/squareup/javapoet/ClassName;", "getCOMPONENT_TREE_DEPS_ANNOTATION", "()Lcom/squareup/javapoet/ClassName;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClassName getCOMPONENT_TREE_DEPS_ANNOTATION() {
            return ComponentTreeDepsGenerator.COMPONENT_TREE_DEPS_ANNOTATION;
        }
    }

    static {
        ClassName className = ClassName.get("dagger.hilt.internal.componenttreedeps", "ComponentTreeDeps", new String[0]);
        Intrinsics.checkNotNullExpressionValue(className, "get(\"dagger.hilt.interna…ps\", \"ComponentTreeDeps\")");
        COMPONENT_TREE_DEPS_ANNOTATION = className;
    }
}
