package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetadataIr.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J;\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r¨\u0006\u001f"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr;", "", "fqName", "Lcom/squareup/javapoet/ClassName;", "root", "originatingRoot", "rootAnnotation", "allowsSharingComponent", "", "(Lcom/squareup/javapoet/ClassName;Lcom/squareup/javapoet/ClassName;Lcom/squareup/javapoet/ClassName;Lcom/squareup/javapoet/ClassName;Z)V", "getAllowsSharingComponent", "()Z", "getFqName", "()Lcom/squareup/javapoet/ClassName;", "isTestRoot", "getOriginatingRoot", "getRoot", "getRootAnnotation", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "Companion", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AggregatedRootIr {
    public static final Companion Companion = new Companion(null);
    private static final List<String> TEST_ROOT_ANNOTATIONS = CollectionsKt.listOf((Object[]) new String[]{"dagger.hilt.android.testing.HiltAndroidTest", "dagger.hilt.android.internal.testing.InternalTestRoot"});
    private final boolean allowsSharingComponent;
    private final ClassName fqName;
    private final boolean isTestRoot;
    private final ClassName originatingRoot;
    private final ClassName root;
    private final ClassName rootAnnotation;

    public static /* synthetic */ AggregatedRootIr copy$default(AggregatedRootIr aggregatedRootIr, ClassName className, ClassName className2, ClassName className3, ClassName className4, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            className = aggregatedRootIr.fqName;
        }
        if ((i & 2) != 0) {
            className2 = aggregatedRootIr.root;
        }
        ClassName className5 = className2;
        if ((i & 4) != 0) {
            className3 = aggregatedRootIr.originatingRoot;
        }
        ClassName className6 = className3;
        if ((i & 8) != 0) {
            className4 = aggregatedRootIr.rootAnnotation;
        }
        ClassName className7 = className4;
        if ((i & 16) != 0) {
            z = aggregatedRootIr.allowsSharingComponent;
        }
        return aggregatedRootIr.copy(className, className5, className6, className7, z);
    }

    public final ClassName component1() {
        return this.fqName;
    }

    public final ClassName component2() {
        return this.root;
    }

    public final ClassName component3() {
        return this.originatingRoot;
    }

    public final ClassName component4() {
        return this.rootAnnotation;
    }

    public final boolean component5() {
        return this.allowsSharingComponent;
    }

    public final AggregatedRootIr copy(ClassName fqName, ClassName root, ClassName originatingRoot, ClassName rootAnnotation, boolean z) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(originatingRoot, "originatingRoot");
        Intrinsics.checkNotNullParameter(rootAnnotation, "rootAnnotation");
        return new AggregatedRootIr(fqName, root, originatingRoot, rootAnnotation, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AggregatedRootIr) {
            AggregatedRootIr aggregatedRootIr = (AggregatedRootIr) obj;
            return Intrinsics.areEqual(this.fqName, aggregatedRootIr.fqName) && Intrinsics.areEqual(this.root, aggregatedRootIr.root) && Intrinsics.areEqual(this.originatingRoot, aggregatedRootIr.originatingRoot) && Intrinsics.areEqual(this.rootAnnotation, aggregatedRootIr.rootAnnotation) && this.allowsSharingComponent == aggregatedRootIr.allowsSharingComponent;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int hashCode = ((((((this.fqName.hashCode() * 31) + this.root.hashCode()) * 31) + this.originatingRoot.hashCode()) * 31) + this.rootAnnotation.hashCode()) * 31;
        boolean z = this.allowsSharingComponent;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public String toString() {
        return "AggregatedRootIr(fqName=" + this.fqName + ", root=" + this.root + ", originatingRoot=" + this.originatingRoot + ", rootAnnotation=" + this.rootAnnotation + ", allowsSharingComponent=" + this.allowsSharingComponent + ')';
    }

    public AggregatedRootIr(ClassName fqName, ClassName root, ClassName originatingRoot, ClassName rootAnnotation, boolean z) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(originatingRoot, "originatingRoot");
        Intrinsics.checkNotNullParameter(rootAnnotation, "rootAnnotation");
        this.fqName = fqName;
        this.root = root;
        this.originatingRoot = originatingRoot;
        this.rootAnnotation = rootAnnotation;
        this.allowsSharingComponent = z;
        this.isTestRoot = TEST_ROOT_ANNOTATIONS.contains(rootAnnotation.toString());
    }

    public /* synthetic */ AggregatedRootIr(ClassName className, ClassName className2, ClassName className3, ClassName className4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(className, className2, className3, className4, (i & 16) != 0 ? true : z);
    }

    public final ClassName getFqName() {
        return this.fqName;
    }

    public final ClassName getRoot() {
        return this.root;
    }

    public final ClassName getOriginatingRoot() {
        return this.originatingRoot;
    }

    public final ClassName getRootAnnotation() {
        return this.rootAnnotation;
    }

    public final boolean getAllowsSharingComponent() {
        return this.allowsSharingComponent;
    }

    public final boolean isTestRoot() {
        return this.isTestRoot;
    }

    /* compiled from: MetadataIr.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr$Companion;", "", "()V", "TEST_ROOT_ANNOTATIONS", "", "", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
