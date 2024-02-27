package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0003J-\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AggregatedUninstallModulesIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "test", "", "uninstallModules", "", "(Lcom/squareup/javapoet/ClassName;Ljava/lang/String;Ljava/util/List;)V", "getFqName", "()Lcom/squareup/javapoet/ClassName;", "getTest", "()Ljava/lang/String;", "getUninstallModules", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AggregatedUninstallModulesIr {
    private final ClassName fqName;
    private final String test;
    private final List<String> uninstallModules;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AggregatedUninstallModulesIr copy$default(AggregatedUninstallModulesIr aggregatedUninstallModulesIr, ClassName className, String str, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            className = aggregatedUninstallModulesIr.fqName;
        }
        if ((i & 2) != 0) {
            str = aggregatedUninstallModulesIr.test;
        }
        if ((i & 4) != 0) {
            list = aggregatedUninstallModulesIr.uninstallModules;
        }
        return aggregatedUninstallModulesIr.copy(className, str, list);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final String component2() {
        return this.test;
    }

    public final List<String> component3() {
        return this.uninstallModules;
    }

    public final AggregatedUninstallModulesIr copy(ClassName fqName, String test, List<String> uninstallModules) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(test, "test");
        Intrinsics.checkNotNullParameter(uninstallModules, "uninstallModules");
        return new AggregatedUninstallModulesIr(fqName, test, uninstallModules);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AggregatedUninstallModulesIr) {
            AggregatedUninstallModulesIr aggregatedUninstallModulesIr = (AggregatedUninstallModulesIr) obj;
            return Intrinsics.areEqual(this.fqName, aggregatedUninstallModulesIr.fqName) && Intrinsics.areEqual(this.test, aggregatedUninstallModulesIr.test) && Intrinsics.areEqual(this.uninstallModules, aggregatedUninstallModulesIr.uninstallModules);
        }
        return false;
    }

    public int hashCode() {
        return (((this.fqName.hashCode() * 31) + this.test.hashCode()) * 31) + this.uninstallModules.hashCode();
    }

    public String toString() {
        return "AggregatedUninstallModulesIr(fqName=" + this.fqName + ", test=" + this.test + ", uninstallModules=" + this.uninstallModules + ')';
    }

    public AggregatedUninstallModulesIr(ClassName fqName, String test, List<String> uninstallModules) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(test, "test");
        Intrinsics.checkNotNullParameter(uninstallModules, "uninstallModules");
        this.fqName = fqName;
        this.test = test;
        this.uninstallModules = uninstallModules;
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final String getTest() {
        return this.test;
    }

    public final List<String> getUninstallModules() {
        return this.uninstallModules;
    }
}
