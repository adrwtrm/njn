package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0006HÆ\u0003Jc\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020#HÖ\u0001J\t\u0010$\u001a\u00020\u0006HÖ\u0001R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e¨\u0006%"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AggregatedDepsIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "components", "", "", "test", "replaces", "module", "entryPoint", "componentEntryPoint", "(Lcom/squareup/javapoet/ClassName;Ljava/util/List;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getComponentEntryPoint", "()Ljava/lang/String;", "getComponents", "()Ljava/util/List;", "getEntryPoint", "getFqName", "()Lcom/squareup/javapoet/ClassName;", "getModule", "getReplaces", "getTest", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AggregatedDepsIr {
    private final String componentEntryPoint;
    private final List<String> components;
    private final String entryPoint;
    private final ClassName fqName;
    private final String module;
    private final List<String> replaces;
    private final String test;

    public static /* synthetic */ AggregatedDepsIr copy$default(AggregatedDepsIr aggregatedDepsIr, ClassName className, List list, String str, List list2, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            className = aggregatedDepsIr.fqName;
        }
        List<String> list3 = list;
        if ((i & 2) != 0) {
            list3 = aggregatedDepsIr.components;
        }
        List list4 = list3;
        if ((i & 4) != 0) {
            str = aggregatedDepsIr.test;
        }
        String str5 = str;
        List<String> list5 = list2;
        if ((i & 8) != 0) {
            list5 = aggregatedDepsIr.replaces;
        }
        List list6 = list5;
        if ((i & 16) != 0) {
            str2 = aggregatedDepsIr.module;
        }
        String str6 = str2;
        if ((i & 32) != 0) {
            str3 = aggregatedDepsIr.entryPoint;
        }
        String str7 = str3;
        if ((i & 64) != 0) {
            str4 = aggregatedDepsIr.componentEntryPoint;
        }
        return aggregatedDepsIr.copy(className, list4, str5, list6, str6, str7, str4);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final List<String> component2() {
        return this.components;
    }

    public final String component3() {
        return this.test;
    }

    public final List<String> component4() {
        return this.replaces;
    }

    public final String component5() {
        return this.module;
    }

    public final String component6() {
        return this.entryPoint;
    }

    public final String component7() {
        return this.componentEntryPoint;
    }

    public final AggregatedDepsIr copy(ClassName fqName, List<String> components, String str, List<String> replaces, String str2, String str3, String str4) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(replaces, "replaces");
        return new AggregatedDepsIr(fqName, components, str, replaces, str2, str3, str4);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AggregatedDepsIr) {
            AggregatedDepsIr aggregatedDepsIr = (AggregatedDepsIr) obj;
            return Intrinsics.areEqual(this.fqName, aggregatedDepsIr.fqName) && Intrinsics.areEqual(this.components, aggregatedDepsIr.components) && Intrinsics.areEqual(this.test, aggregatedDepsIr.test) && Intrinsics.areEqual(this.replaces, aggregatedDepsIr.replaces) && Intrinsics.areEqual(this.module, aggregatedDepsIr.module) && Intrinsics.areEqual(this.entryPoint, aggregatedDepsIr.entryPoint) && Intrinsics.areEqual(this.componentEntryPoint, aggregatedDepsIr.componentEntryPoint);
        }
        return false;
    }

    public int hashCode() {
        int hashCode = ((this.fqName.hashCode() * 31) + this.components.hashCode()) * 31;
        String str = this.test;
        int hashCode2 = (((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.replaces.hashCode()) * 31;
        String str2 = this.module;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.entryPoint;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.componentEntryPoint;
        return hashCode4 + (str4 != null ? str4.hashCode() : 0);
    }

    public String toString() {
        return "AggregatedDepsIr(fqName=" + this.fqName + ", components=" + this.components + ", test=" + ((Object) this.test) + ", replaces=" + this.replaces + ", module=" + ((Object) this.module) + ", entryPoint=" + ((Object) this.entryPoint) + ", componentEntryPoint=" + ((Object) this.componentEntryPoint) + ')';
    }

    public AggregatedDepsIr(ClassName fqName, List<String> components, String str, List<String> replaces, String str2, String str3, String str4) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(replaces, "replaces");
        this.fqName = fqName;
        this.components = components;
        this.test = str;
        this.replaces = replaces;
        this.module = str2;
        this.entryPoint = str3;
        this.componentEntryPoint = str4;
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final List<String> getComponents() {
        return this.components;
    }

    public final String getTest() {
        return this.test;
    }

    public final List<String> getReplaces() {
        return this.replaces;
    }

    public final String getModule() {
        return this.module;
    }

    public final String getEntryPoint() {
        return this.entryPoint;
    }

    public final String getComponentEntryPoint() {
        return this.componentEntryPoint;
    }
}
