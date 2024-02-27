package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AggregatedElementProxyIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "value", "(Lcom/squareup/javapoet/ClassName;Lcom/squareup/javapoet/ClassName;)V", "getFqName", "()Lcom/squareup/javapoet/ClassName;", "getValue", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AggregatedElementProxyIr {
    private final ClassName fqName;
    private final ClassName value;

    public static /* synthetic */ AggregatedElementProxyIr copy$default(AggregatedElementProxyIr aggregatedElementProxyIr, ClassName className, ClassName className2, int i, Object obj) {
        if ((i & 1) != 0) {
            className = aggregatedElementProxyIr.fqName;
        }
        if ((i & 2) != 0) {
            className2 = aggregatedElementProxyIr.value;
        }
        return aggregatedElementProxyIr.copy(className, className2);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final ClassName component2() {
        return this.value;
    }

    public final AggregatedElementProxyIr copy(ClassName fqName, ClassName value) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(value, "value");
        return new AggregatedElementProxyIr(fqName, value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AggregatedElementProxyIr) {
            AggregatedElementProxyIr aggregatedElementProxyIr = (AggregatedElementProxyIr) obj;
            return Intrinsics.areEqual(this.fqName, aggregatedElementProxyIr.fqName) && Intrinsics.areEqual(this.value, aggregatedElementProxyIr.value);
        }
        return false;
    }

    public int hashCode() {
        return (this.fqName.hashCode() * 31) + this.value.hashCode();
    }

    public String toString() {
        return "AggregatedElementProxyIr(fqName=" + this.fqName + ", value=" + this.value + ')';
    }

    public AggregatedElementProxyIr(ClassName fqName, ClassName value) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(value, "value");
        this.fqName = fqName;
        this.value = value;
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final ClassName getValue() {
        return this.value;
    }
}
