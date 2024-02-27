package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J-\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AliasOfPropagatedDataIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "defineComponentScopes", "", "alias", "(Lcom/squareup/javapoet/ClassName;Ljava/util/List;Lcom/squareup/javapoet/ClassName;)V", "getAlias", "()Lcom/squareup/javapoet/ClassName;", "getDefineComponentScopes", "()Ljava/util/List;", "getFqName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AliasOfPropagatedDataIr {
    private final ClassName alias;
    private final List<ClassName> defineComponentScopes;
    private final ClassName fqName;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AliasOfPropagatedDataIr copy$default(AliasOfPropagatedDataIr aliasOfPropagatedDataIr, ClassName className, List list, ClassName className2, int i, Object obj) {
        if ((i & 1) != 0) {
            className = aliasOfPropagatedDataIr.fqName;
        }
        if ((i & 2) != 0) {
            list = aliasOfPropagatedDataIr.defineComponentScopes;
        }
        if ((i & 4) != 0) {
            className2 = aliasOfPropagatedDataIr.alias;
        }
        return aliasOfPropagatedDataIr.copy(className, list, className2);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final List<ClassName> component2() {
        return this.defineComponentScopes;
    }

    public final ClassName component3() {
        return this.alias;
    }

    public final AliasOfPropagatedDataIr copy(ClassName fqName, List<ClassName> defineComponentScopes, ClassName alias) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(defineComponentScopes, "defineComponentScopes");
        Intrinsics.checkNotNullParameter(alias, "alias");
        return new AliasOfPropagatedDataIr(fqName, defineComponentScopes, alias);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AliasOfPropagatedDataIr) {
            AliasOfPropagatedDataIr aliasOfPropagatedDataIr = (AliasOfPropagatedDataIr) obj;
            return Intrinsics.areEqual(this.fqName, aliasOfPropagatedDataIr.fqName) && Intrinsics.areEqual(this.defineComponentScopes, aliasOfPropagatedDataIr.defineComponentScopes) && Intrinsics.areEqual(this.alias, aliasOfPropagatedDataIr.alias);
        }
        return false;
    }

    public int hashCode() {
        return (((this.fqName.hashCode() * 31) + this.defineComponentScopes.hashCode()) * 31) + this.alias.hashCode();
    }

    public String toString() {
        return "AliasOfPropagatedDataIr(fqName=" + this.fqName + ", defineComponentScopes=" + this.defineComponentScopes + ", alias=" + this.alias + ')';
    }

    public AliasOfPropagatedDataIr(ClassName fqName, List<ClassName> defineComponentScopes, ClassName alias) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(defineComponentScopes, "defineComponentScopes");
        Intrinsics.checkNotNullParameter(alias, "alias");
        this.fqName = fqName;
        this.defineComponentScopes = defineComponentScopes;
        this.alias = alias;
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final List<ClassName> getDefineComponentScopes() {
        return this.defineComponentScopes;
    }

    public final ClassName getAlias() {
        return this.alias;
    }
}
