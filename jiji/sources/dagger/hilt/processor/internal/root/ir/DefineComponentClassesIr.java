package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/DefineComponentClassesIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "component", "", "(Lcom/squareup/javapoet/ClassName;Ljava/lang/String;)V", "getComponent", "()Ljava/lang/String;", "getFqName", "()Lcom/squareup/javapoet/ClassName;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class DefineComponentClassesIr {
    private final String component;
    private final ClassName fqName;

    public static /* synthetic */ DefineComponentClassesIr copy$default(DefineComponentClassesIr defineComponentClassesIr, ClassName className, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            className = defineComponentClassesIr.fqName;
        }
        if ((i & 2) != 0) {
            str = defineComponentClassesIr.component;
        }
        return defineComponentClassesIr.copy(className, str);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final String component2() {
        return this.component;
    }

    public final DefineComponentClassesIr copy(ClassName fqName, String component) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(component, "component");
        return new DefineComponentClassesIr(fqName, component);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DefineComponentClassesIr) {
            DefineComponentClassesIr defineComponentClassesIr = (DefineComponentClassesIr) obj;
            return Intrinsics.areEqual(this.fqName, defineComponentClassesIr.fqName) && Intrinsics.areEqual(this.component, defineComponentClassesIr.component);
        }
        return false;
    }

    public int hashCode() {
        return (this.fqName.hashCode() * 31) + this.component.hashCode();
    }

    public String toString() {
        return "DefineComponentClassesIr(fqName=" + this.fqName + ", component=" + this.component + ')';
    }

    public DefineComponentClassesIr(ClassName fqName, String component) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(component, "component");
        this.fqName = fqName;
        this.component = component;
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final String getComponent() {
        return this.component;
    }
}
