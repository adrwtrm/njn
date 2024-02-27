package dagger.hilt.android.plugin.root;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AggregatedAnnotation.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u0080\u0001\u0018\u0000 \u000f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000fB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u0010"}, d2 = {"Ldagger/hilt/android/plugin/root/AggregatedAnnotation;", "", "descriptor", "", "aggregatedPackage", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "AGGREGATED_ROOT", "PROCESSED_ROOT_SENTINEL", "DEFINE_COMPONENT", "ALIAS_OF", "AGGREGATED_DEP", "AGGREGATED_DEP_PROXY", "AGGREGATED_UNINSTALL_MODULES", "AGGREGATED_EARLY_ENTRY_POINT", "NONE", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public enum AggregatedAnnotation {
    AGGREGATED_ROOT("Ldagger/hilt/internal/aggregatedroot/AggregatedRoot;", "dagger/hilt/internal/aggregatedroot/codegen"),
    PROCESSED_ROOT_SENTINEL("Ldagger/hilt/internal/processedrootsentinel/ProcessedRootSentinel;", "dagger/hilt/internal/processedrootsentinel/codegen"),
    DEFINE_COMPONENT("Ldagger/hilt/internal/definecomponent/DefineComponentClasses;", "dagger/hilt/processor/internal/definecomponent/codegen"),
    ALIAS_OF("Ldagger/hilt/internal/aliasof/AliasOfPropagatedData;", "dagger/hilt/processor/internal/aliasof/codegen"),
    AGGREGATED_DEP("Ldagger/hilt/processor/internal/aggregateddeps/AggregatedDeps;", "hilt_aggregated_deps"),
    AGGREGATED_DEP_PROXY("Ldagger/hilt/android/internal/legacy/AggregatedElementProxy;", ""),
    AGGREGATED_UNINSTALL_MODULES("Ldagger/hilt/android/internal/uninstallmodules/AggregatedUninstallModules;", "dagger/hilt/android/internal/uninstallmodules/codegen"),
    AGGREGATED_EARLY_ENTRY_POINT("Ldagger/hilt/android/internal/earlyentrypoint/AggregatedEarlyEntryPoint;", "dagger/hilt/android/internal/earlyentrypoint/codegen"),
    NONE("", "");
    
    private static final List<String> AGGREGATED_PACKAGES;
    public static final Companion Companion = new Companion(null);
    private final String aggregatedPackage;
    private final String descriptor;

    AggregatedAnnotation(String str, String str2) {
        this.descriptor = str;
        this.aggregatedPackage = str2;
    }

    static {
        AggregatedAnnotation[] values = values();
        ArrayList arrayList = new ArrayList(values.length);
        for (AggregatedAnnotation aggregatedAnnotation : values) {
            arrayList.add(aggregatedAnnotation.aggregatedPackage);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        AGGREGATED_PACKAGES = arrayList2;
    }

    /* compiled from: AggregatedAnnotation.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Ldagger/hilt/android/plugin/root/AggregatedAnnotation$Companion;", "", "()V", "AGGREGATED_PACKAGES", "", "", "getAGGREGATED_PACKAGES", "()Ljava/util/List;", "fromString", "Ldagger/hilt/android/plugin/root/AggregatedAnnotation;", "str", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AggregatedAnnotation fromString(String str) {
            AggregatedAnnotation aggregatedAnnotation;
            Intrinsics.checkNotNullParameter(str, "str");
            AggregatedAnnotation[] values = AggregatedAnnotation.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    aggregatedAnnotation = null;
                    break;
                }
                aggregatedAnnotation = values[i];
                if (Intrinsics.areEqual(aggregatedAnnotation.descriptor, str)) {
                    break;
                }
                i++;
            }
            return aggregatedAnnotation == null ? AggregatedAnnotation.NONE : aggregatedAnnotation;
        }

        public final List<String> getAGGREGATED_PACKAGES() {
            return AggregatedAnnotation.AGGREGATED_PACKAGES;
        }
    }
}
