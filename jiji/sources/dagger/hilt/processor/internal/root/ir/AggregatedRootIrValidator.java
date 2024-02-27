package dagger.hilt.processor.internal.root.ir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: AggregatedRootIrValidator.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0007¨\u0006\u000b"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/AggregatedRootIrValidator;", "", "()V", "rootsToProcess", "", "Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr;", "isCrossCompilationRootValidationDisabled", "", "processedRoots", "Ldagger/hilt/processor/internal/root/ir/ProcessedRootSentinelIr;", "aggregatedRoots", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AggregatedRootIrValidator {
    public static final AggregatedRootIrValidator INSTANCE = new AggregatedRootIrValidator();

    private AggregatedRootIrValidator() {
    }

    @JvmStatic
    public static final Set<AggregatedRootIr> rootsToProcess(boolean z, Set<ProcessedRootSentinelIr> processedRoots, Set<AggregatedRootIr> aggregatedRoots) throws InvalidRootsException {
        Intrinsics.checkNotNullParameter(processedRoots, "processedRoots");
        Intrinsics.checkNotNullParameter(aggregatedRoots, "aggregatedRoots");
        ArrayList arrayList = new ArrayList();
        for (ProcessedRootSentinelIr processedRootSentinelIr : processedRoots) {
            CollectionsKt.addAll(arrayList, processedRootSentinelIr.getRoots());
        }
        Set set = CollectionsKt.toSet(arrayList);
        Set<AggregatedRootIr> set2 = aggregatedRoots;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : set2) {
            if (!set.contains(((AggregatedRootIr) obj).getRoot().canonicalName())) {
                arrayList2.add(obj);
            }
        }
        List sortedWith = CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.AggregatedRootIrValidator$rootsToProcess$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((AggregatedRootIr) t).getRoot().canonicalName(), ((AggregatedRootIr) t2).getRoot().canonicalName());
            }
        });
        List list = sortedWith;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (((AggregatedRootIr) obj2).isTestRoot()) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = arrayList3;
        List minus = CollectionsKt.minus((Iterable) list, (Iterable) arrayList4);
        if (minus.size() > 1) {
            throw new InvalidRootsException(Intrinsics.stringPlus("Cannot process multiple app roots in the same compilation unit: ", rootsToProcess$rootsToString(minus)));
        }
        ArrayList arrayList5 = arrayList4;
        if (!arrayList5.isEmpty()) {
            List list2 = minus;
            if (!list2.isEmpty()) {
                throw new InvalidRootsException(StringsKt.trimIndent("\n        Cannot process test roots and app roots in the same compilation unit:\n          App root in this compilation unit: " + rootsToProcess$rootsToString(list2) + "\n          Test roots in this compilation unit: " + rootsToProcess$rootsToString(arrayList5) + "\n        "));
            }
        }
        if (!z) {
            ArrayList arrayList6 = new ArrayList();
            Iterator<T> it = set2.iterator();
            while (true) {
                boolean z2 = false;
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                AggregatedRootIr aggregatedRootIr = (AggregatedRootIr) next;
                if (aggregatedRootIr.isTestRoot() && set.contains(aggregatedRootIr.getRoot().canonicalName())) {
                    z2 = true;
                }
                if (z2) {
                    arrayList6.add(next);
                }
            }
            ArrayList arrayList7 = arrayList6;
            if (!arrayList7.isEmpty()) {
                List list3 = sortedWith;
                if (!list3.isEmpty()) {
                    throw new InvalidRootsException(StringsKt.trimIndent("\n          Cannot process new roots when there are test roots from a previous compilation unit:\n            Test roots from previous compilation unit: " + rootsToProcess$rootsToString(arrayList7) + "\n            All roots from this compilation unit: " + rootsToProcess$rootsToString(list3) + "\n          "));
                }
            }
            ArrayList arrayList8 = new ArrayList();
            for (Object obj3 : set2) {
                AggregatedRootIr aggregatedRootIr2 = (AggregatedRootIr) obj3;
                if (!aggregatedRootIr2.isTestRoot() && set.contains(aggregatedRootIr2.getRoot().canonicalName())) {
                    arrayList8.add(obj3);
                }
            }
            ArrayList arrayList9 = arrayList8;
            if (!arrayList9.isEmpty()) {
                List list4 = minus;
                if (!list4.isEmpty()) {
                    throw new InvalidRootsException(StringsKt.trimIndent("\n          Cannot process new app roots when there are app roots from a previous compilation unit:\n            App roots in previous compilation unit: " + rootsToProcess$rootsToString(arrayList9) + "\n            App roots in this compilation unit: " + rootsToProcess$rootsToString(list4) + "\n          "));
                }
            }
        }
        return CollectionsKt.toSet(list);
    }

    private static final String rootsToProcess$rootsToString(Collection<AggregatedRootIr> collection) {
        Collection<AggregatedRootIr> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        for (AggregatedRootIr aggregatedRootIr : collection2) {
            arrayList.add(aggregatedRootIr.getRoot());
        }
        return CollectionsKt.joinToString$default(arrayList, null, null, null, 0, null, null, 63, null);
    }
}
