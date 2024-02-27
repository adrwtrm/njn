package dagger.hilt.processor.internal.root.ir;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.squareup.javapoet.ClassName;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Ba\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003Js\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020#HÖ\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r¨\u0006$"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIr;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "Lcom/squareup/javapoet/ClassName;", "rootDeps", "", "defineComponentDeps", "aliasOfDeps", "aggregatedDeps", "uninstallModulesDeps", "earlyEntryPointDeps", "(Lcom/squareup/javapoet/ClassName;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)V", "getAggregatedDeps", "()Ljava/util/Set;", "getAliasOfDeps", "getDefineComponentDeps", "getEarlyEntryPointDeps", "getName", "()Lcom/squareup/javapoet/ClassName;", "getRootDeps", "getUninstallModulesDeps", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ComponentTreeDepsIr {
    private final Set<ClassName> aggregatedDeps;
    private final Set<ClassName> aliasOfDeps;
    private final Set<ClassName> defineComponentDeps;
    private final Set<ClassName> earlyEntryPointDeps;
    private final ClassName name;
    private final Set<ClassName> rootDeps;
    private final Set<ClassName> uninstallModulesDeps;

    public static /* synthetic */ ComponentTreeDepsIr copy$default(ComponentTreeDepsIr componentTreeDepsIr, ClassName className, Set set, Set set2, Set set3, Set set4, Set set5, Set set6, int i, Object obj) {
        if ((i & 1) != 0) {
            className = componentTreeDepsIr.name;
        }
        Set<ClassName> set7 = set;
        if ((i & 2) != 0) {
            set7 = componentTreeDepsIr.rootDeps;
        }
        Set set8 = set7;
        Set<ClassName> set9 = set2;
        if ((i & 4) != 0) {
            set9 = componentTreeDepsIr.defineComponentDeps;
        }
        Set set10 = set9;
        Set<ClassName> set11 = set3;
        if ((i & 8) != 0) {
            set11 = componentTreeDepsIr.aliasOfDeps;
        }
        Set set12 = set11;
        Set<ClassName> set13 = set4;
        if ((i & 16) != 0) {
            set13 = componentTreeDepsIr.aggregatedDeps;
        }
        Set set14 = set13;
        Set<ClassName> set15 = set5;
        if ((i & 32) != 0) {
            set15 = componentTreeDepsIr.uninstallModulesDeps;
        }
        Set set16 = set15;
        Set<ClassName> set17 = set6;
        if ((i & 64) != 0) {
            set17 = componentTreeDepsIr.earlyEntryPointDeps;
        }
        return componentTreeDepsIr.copy(className, set8, set10, set12, set14, set16, set17);
    }

    public final ClassName component1() {
        return this.name;
    }

    public final Set<ClassName> component2() {
        return this.rootDeps;
    }

    public final Set<ClassName> component3() {
        return this.defineComponentDeps;
    }

    public final Set<ClassName> component4() {
        return this.aliasOfDeps;
    }

    public final Set<ClassName> component5() {
        return this.aggregatedDeps;
    }

    public final Set<ClassName> component6() {
        return this.uninstallModulesDeps;
    }

    public final Set<ClassName> component7() {
        return this.earlyEntryPointDeps;
    }

    public final ComponentTreeDepsIr copy(ClassName name, Set<ClassName> rootDeps, Set<ClassName> defineComponentDeps, Set<ClassName> aliasOfDeps, Set<ClassName> aggregatedDeps, Set<ClassName> uninstallModulesDeps, Set<ClassName> earlyEntryPointDeps) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(rootDeps, "rootDeps");
        Intrinsics.checkNotNullParameter(defineComponentDeps, "defineComponentDeps");
        Intrinsics.checkNotNullParameter(aliasOfDeps, "aliasOfDeps");
        Intrinsics.checkNotNullParameter(aggregatedDeps, "aggregatedDeps");
        Intrinsics.checkNotNullParameter(uninstallModulesDeps, "uninstallModulesDeps");
        Intrinsics.checkNotNullParameter(earlyEntryPointDeps, "earlyEntryPointDeps");
        return new ComponentTreeDepsIr(name, rootDeps, defineComponentDeps, aliasOfDeps, aggregatedDeps, uninstallModulesDeps, earlyEntryPointDeps);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ComponentTreeDepsIr) {
            ComponentTreeDepsIr componentTreeDepsIr = (ComponentTreeDepsIr) obj;
            return Intrinsics.areEqual(this.name, componentTreeDepsIr.name) && Intrinsics.areEqual(this.rootDeps, componentTreeDepsIr.rootDeps) && Intrinsics.areEqual(this.defineComponentDeps, componentTreeDepsIr.defineComponentDeps) && Intrinsics.areEqual(this.aliasOfDeps, componentTreeDepsIr.aliasOfDeps) && Intrinsics.areEqual(this.aggregatedDeps, componentTreeDepsIr.aggregatedDeps) && Intrinsics.areEqual(this.uninstallModulesDeps, componentTreeDepsIr.uninstallModulesDeps) && Intrinsics.areEqual(this.earlyEntryPointDeps, componentTreeDepsIr.earlyEntryPointDeps);
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((this.name.hashCode() * 31) + this.rootDeps.hashCode()) * 31) + this.defineComponentDeps.hashCode()) * 31) + this.aliasOfDeps.hashCode()) * 31) + this.aggregatedDeps.hashCode()) * 31) + this.uninstallModulesDeps.hashCode()) * 31) + this.earlyEntryPointDeps.hashCode();
    }

    public String toString() {
        return "ComponentTreeDepsIr(name=" + this.name + ", rootDeps=" + this.rootDeps + ", defineComponentDeps=" + this.defineComponentDeps + ", aliasOfDeps=" + this.aliasOfDeps + ", aggregatedDeps=" + this.aggregatedDeps + ", uninstallModulesDeps=" + this.uninstallModulesDeps + ", earlyEntryPointDeps=" + this.earlyEntryPointDeps + ')';
    }

    public ComponentTreeDepsIr(ClassName name, Set<ClassName> rootDeps, Set<ClassName> defineComponentDeps, Set<ClassName> aliasOfDeps, Set<ClassName> aggregatedDeps, Set<ClassName> uninstallModulesDeps, Set<ClassName> earlyEntryPointDeps) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(rootDeps, "rootDeps");
        Intrinsics.checkNotNullParameter(defineComponentDeps, "defineComponentDeps");
        Intrinsics.checkNotNullParameter(aliasOfDeps, "aliasOfDeps");
        Intrinsics.checkNotNullParameter(aggregatedDeps, "aggregatedDeps");
        Intrinsics.checkNotNullParameter(uninstallModulesDeps, "uninstallModulesDeps");
        Intrinsics.checkNotNullParameter(earlyEntryPointDeps, "earlyEntryPointDeps");
        this.name = name;
        this.rootDeps = rootDeps;
        this.defineComponentDeps = defineComponentDeps;
        this.aliasOfDeps = aliasOfDeps;
        this.aggregatedDeps = aggregatedDeps;
        this.uninstallModulesDeps = uninstallModulesDeps;
        this.earlyEntryPointDeps = earlyEntryPointDeps;
    }

    public final ClassName getName() {
        return this.name;
    }

    public final Set<ClassName> getRootDeps() {
        return this.rootDeps;
    }

    public final Set<ClassName> getDefineComponentDeps() {
        return this.defineComponentDeps;
    }

    public final Set<ClassName> getAliasOfDeps() {
        return this.aliasOfDeps;
    }

    public final Set<ClassName> getAggregatedDeps() {
        return this.aggregatedDeps;
    }

    public final Set<ClassName> getUninstallModulesDeps() {
        return this.uninstallModulesDeps;
    }

    public final Set<ClassName> getEarlyEntryPointDeps() {
        return this.earlyEntryPointDeps;
    }
}
