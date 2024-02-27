package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/ProcessedRootSentinelIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "roots", "", "", "(Lcom/squareup/javapoet/ClassName;Ljava/util/List;)V", "getFqName", "()Lcom/squareup/javapoet/ClassName;", "getRoots", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ProcessedRootSentinelIr {
    private final ClassName fqName;
    private final List<String> roots;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ProcessedRootSentinelIr copy$default(ProcessedRootSentinelIr processedRootSentinelIr, ClassName className, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            className = processedRootSentinelIr.fqName;
        }
        if ((i & 2) != 0) {
            list = processedRootSentinelIr.roots;
        }
        return processedRootSentinelIr.copy(className, list);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final List<String> component2() {
        return this.roots;
    }

    public final ProcessedRootSentinelIr copy(ClassName fqName, List<String> roots) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(roots, "roots");
        return new ProcessedRootSentinelIr(fqName, roots);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ProcessedRootSentinelIr) {
            ProcessedRootSentinelIr processedRootSentinelIr = (ProcessedRootSentinelIr) obj;
            return Intrinsics.areEqual(this.fqName, processedRootSentinelIr.fqName) && Intrinsics.areEqual(this.roots, processedRootSentinelIr.roots);
        }
        return false;
    }

    public int hashCode() {
        return (this.fqName.hashCode() * 31) + this.roots.hashCode();
    }

    public String toString() {
        return "ProcessedRootSentinelIr(fqName=" + this.fqName + ", roots=" + this.roots + ')';
    }

    public ProcessedRootSentinelIr(ClassName fqName, List<String> roots) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(roots, "roots");
        this.fqName = fqName;
        this.roots = roots;
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final List<String> getRoots() {
        return this.roots;
    }
}
