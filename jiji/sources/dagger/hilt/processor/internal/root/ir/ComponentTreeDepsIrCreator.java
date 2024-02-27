package dagger.hilt.processor.internal.root.ir;

import com.squareup.javapoet.ClassName;
import dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: ComponentTreeDepsIrCreator.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001b\u001cBc\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005¢\u0006\u0002\u0010\u0011J>\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00050\u00132\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00052\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0005H\u0002J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00180\u0005H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIrCreator;", "", "isSharedTestComponentsEnabled", "", "aggregatedRoots", "", "Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr;", "defineComponentDeps", "Ldagger/hilt/processor/internal/root/ir/DefineComponentClassesIr;", "aliasOfDeps", "Ldagger/hilt/processor/internal/root/ir/AliasOfPropagatedDataIr;", "aggregatedDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedDepsIr;", "aggregatedUninstallModulesDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedUninstallModulesIr;", "aggregatedEarlyEntryPointDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedEarlyEntryPointIr;", "(ZLjava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)V", "aggregatedDepsByRoot", "", "Lcom/squareup/javapoet/ClassName;", "rootsUsingSharedComponent", "hasEarlyEntryPoints", "prodComponents", "Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIr;", "roots", "testComponents", "Companion", "ComponentTreeDepsNameGenerator", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ComponentTreeDepsIrCreator {
    public static final Companion Companion = new Companion(null);
    private static final ClassName DEFAULT_ROOT_CLASS_NAME;
    private static final ClassName SINGLETON_COMPONENT_CLASS_NAME;
    private final Set<AggregatedDepsIr> aggregatedDeps;
    private final Set<AggregatedEarlyEntryPointIr> aggregatedEarlyEntryPointDeps;
    private final Set<AggregatedRootIr> aggregatedRoots;
    private final Set<AggregatedUninstallModulesIr> aggregatedUninstallModulesDeps;
    private final Set<AliasOfPropagatedDataIr> aliasOfDeps;
    private final Set<DefineComponentClassesIr> defineComponentDeps;
    private final boolean isSharedTestComponentsEnabled;

    public /* synthetic */ ComponentTreeDepsIrCreator(boolean z, Set set, Set set2, Set set3, Set set4, Set set5, Set set6, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, set, set2, set3, set4, set5, set6);
    }

    @JvmStatic
    public static final Set<ComponentTreeDepsIr> components(boolean z, boolean z2, Set<AggregatedRootIr> set, Set<DefineComponentClassesIr> set2, Set<AliasOfPropagatedDataIr> set3, Set<AggregatedDepsIr> set4, Set<AggregatedUninstallModulesIr> set5, Set<AggregatedEarlyEntryPointIr> set6) {
        return Companion.components(z, z2, set, set2, set3, set4, set5, set6);
    }

    private ComponentTreeDepsIrCreator(boolean z, Set<AggregatedRootIr> set, Set<DefineComponentClassesIr> set2, Set<AliasOfPropagatedDataIr> set3, Set<AggregatedDepsIr> set4, Set<AggregatedUninstallModulesIr> set5, Set<AggregatedEarlyEntryPointIr> set6) {
        this.isSharedTestComponentsEnabled = z;
        this.aggregatedRoots = set;
        this.defineComponentDeps = set2;
        this.aliasOfDeps = set3;
        this.aggregatedDeps = set4;
        this.aggregatedUninstallModulesDeps = set5;
        this.aggregatedEarlyEntryPointDeps = set6;
    }

    public final Set<ComponentTreeDepsIr> prodComponents() {
        AggregatedRootIr aggregatedRootIr = (AggregatedRootIr) CollectionsKt.single(this.aggregatedRoots);
        ClassName generate = new ComponentTreeDepsNameGenerator(null, null, 3, null).generate(aggregatedRootIr.getRoot());
        Set of = SetsKt.setOf(aggregatedRootIr.getFqName());
        Set<DefineComponentClassesIr> set = this.defineComponentDeps;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        for (DefineComponentClassesIr defineComponentClassesIr : set) {
            arrayList.add(defineComponentClassesIr.getFqName());
        }
        Set set2 = CollectionsKt.toSet(arrayList);
        Set<AliasOfPropagatedDataIr> set3 = this.aliasOfDeps;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set3, 10));
        for (AliasOfPropagatedDataIr aliasOfPropagatedDataIr : set3) {
            arrayList2.add(aliasOfPropagatedDataIr.getFqName());
        }
        Set set4 = CollectionsKt.toSet(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : this.aggregatedDeps) {
            if (((AggregatedDepsIr) obj).getReplaces().isEmpty()) {
                arrayList3.add(obj);
            }
        }
        ArrayList<AggregatedDepsIr> arrayList4 = arrayList3;
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
        for (AggregatedDepsIr aggregatedDepsIr : arrayList4) {
            arrayList5.add(aggregatedDepsIr.getFqName());
        }
        return SetsKt.setOf(new ComponentTreeDepsIr(generate, of, set2, set4, CollectionsKt.toSet(arrayList5), SetsKt.emptySet(), SetsKt.emptySet()));
    }

    public final Set<ComponentTreeDepsIr> testComponents() {
        ComponentTreeDepsNameGenerator componentTreeDepsNameGenerator;
        Set of;
        Set emptySet;
        Set<ClassName> rootsUsingSharedComponent = rootsUsingSharedComponent(this.aggregatedRoots);
        Set<AggregatedRootIr> set = this.aggregatedRoots;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        for (Object obj : set) {
            linkedHashMap.put(((AggregatedRootIr) obj).getRoot(), obj);
        }
        boolean z = true;
        Map<ClassName, Set<ClassName>> aggregatedDepsByRoot = aggregatedDepsByRoot(this.aggregatedRoots, rootsUsingSharedComponent, !this.aggregatedEarlyEntryPointDeps.isEmpty());
        Set<AggregatedUninstallModulesIr> set2 = this.aggregatedUninstallModulesDeps;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set2, 10)), 16));
        for (AggregatedUninstallModulesIr aggregatedUninstallModulesIr : set2) {
            Pair pair = TuplesKt.to(aggregatedUninstallModulesIr.getTest(), aggregatedUninstallModulesIr.getFqName());
            linkedHashMap2.put(pair.getFirst(), pair.getSecond());
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ClassName className : aggregatedDepsByRoot.keySet()) {
            ClassName className2 = DEFAULT_ROOT_CLASS_NAME;
            boolean areEqual = Intrinsics.areEqual(className, className2);
            boolean z2 = (areEqual && (this.aggregatedEarlyEntryPointDeps.isEmpty() ^ z)) ? z : false;
            if (!areEqual) {
                className2 = ((AggregatedRootIr) MapsKt.getValue(linkedHashMap, className)).getOriginatingRoot();
            }
            if (this.isSharedTestComponentsEnabled) {
                componentTreeDepsNameGenerator = new ComponentTreeDepsNameGenerator("dagger.hilt.android.internal.testing.root", aggregatedDepsByRoot.keySet());
            } else {
                componentTreeDepsNameGenerator = new ComponentTreeDepsNameGenerator(null, null, 3, null);
            }
            ClassName generate = componentTreeDepsNameGenerator.generate(className2);
            if (areEqual) {
                Set<ClassName> set3 = rootsUsingSharedComponent;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set3, 10));
                for (ClassName className3 : set3) {
                    arrayList.add(((AggregatedRootIr) MapsKt.getValue(linkedHashMap, className3)).getFqName());
                }
                of = CollectionsKt.toSet(arrayList);
            } else {
                of = SetsKt.setOf(((AggregatedRootIr) MapsKt.getValue(linkedHashMap, className)).getFqName());
            }
            Set set4 = of;
            Set<DefineComponentClassesIr> set5 = this.defineComponentDeps;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set5, 10));
            for (DefineComponentClassesIr defineComponentClassesIr : set5) {
                arrayList2.add(defineComponentClassesIr.getFqName());
            }
            Set set6 = CollectionsKt.toSet(arrayList2);
            Set<AliasOfPropagatedDataIr> set7 = this.aliasOfDeps;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set7, 10));
            for (AliasOfPropagatedDataIr aliasOfPropagatedDataIr : set7) {
                arrayList3.add(aliasOfPropagatedDataIr.getFqName());
            }
            Set set8 = CollectionsKt.toSet(arrayList3);
            Set<ClassName> set9 = aggregatedDepsByRoot.get(className);
            if (set9 == null) {
                set9 = SetsKt.emptySet();
            }
            Set<ClassName> set10 = set9;
            ClassName className4 = (ClassName) linkedHashMap2.get(className.canonicalName());
            Set of2 = className4 != null ? SetsKt.setOf(className4) : null;
            Set emptySet2 = of2 == null ? SetsKt.emptySet() : of2;
            if (z2) {
                Set<AggregatedEarlyEntryPointIr> set11 = this.aggregatedEarlyEntryPointDeps;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set11, 10));
                for (AggregatedEarlyEntryPointIr aggregatedEarlyEntryPointIr : set11) {
                    arrayList4.add(aggregatedEarlyEntryPointIr.getFqName());
                }
                emptySet = CollectionsKt.toSet(arrayList4);
            } else {
                emptySet = SetsKt.emptySet();
            }
            linkedHashSet.add(new ComponentTreeDepsIr(generate, set4, set6, set8, set10, emptySet2, emptySet));
            z = true;
        }
        return linkedHashSet;
    }

    private final Set<ClassName> rootsUsingSharedComponent(Set<AggregatedRootIr> set) {
        if (this.isSharedTestComponentsEnabled) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            ArrayList<AggregatedDepsIr> arrayList = new ArrayList();
            Iterator<T> it = this.aggregatedDeps.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (((AggregatedDepsIr) next).getModule() != null) {
                    arrayList.add(next);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (AggregatedDepsIr aggregatedDepsIr : arrayList) {
                String test = aggregatedDepsIr.getTest();
                if (test != null) {
                    arrayList2.add(test);
                }
            }
            linkedHashSet.addAll(arrayList2);
            Set<AggregatedUninstallModulesIr> set2 = this.aggregatedUninstallModulesDeps;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
            for (AggregatedUninstallModulesIr aggregatedUninstallModulesIr : set2) {
                arrayList3.add(aggregatedUninstallModulesIr.getTest());
            }
            linkedHashSet.addAll(arrayList3);
            ArrayList arrayList4 = new ArrayList();
            for (Object obj : set) {
                AggregatedRootIr aggregatedRootIr = (AggregatedRootIr) obj;
                if (aggregatedRootIr.isTestRoot() && aggregatedRootIr.getAllowsSharingComponent()) {
                    arrayList4.add(obj);
                }
            }
            ArrayList<AggregatedRootIr> arrayList5 = arrayList4;
            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
            for (AggregatedRootIr aggregatedRootIr2 : arrayList5) {
                arrayList6.add(aggregatedRootIr2.getRoot());
            }
            ArrayList arrayList7 = new ArrayList();
            for (Object obj2 : arrayList6) {
                if (!linkedHashSet.contains(((ClassName) obj2).canonicalName())) {
                    arrayList7.add(obj2);
                }
            }
            return CollectionsKt.toSet(arrayList7);
        }
        return SetsKt.emptySet();
    }

    private final Map<ClassName, Set<ClassName>> aggregatedDepsByRoot(Set<AggregatedRootIr> set, Set<ClassName> set2, boolean z) {
        ArrayList<AggregatedDepsIr> arrayList = new ArrayList();
        Iterator<T> it = this.aggregatedDeps.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((AggregatedDepsIr) next).getTest() != null) {
                arrayList.add(next);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (AggregatedDepsIr aggregatedDepsIr : arrayList) {
            String test = aggregatedDepsIr.getTest();
            Object obj = linkedHashMap.get(test);
            if (obj == null) {
                obj = (List) new ArrayList();
                linkedHashMap.put(test, obj);
            }
            ((List) obj).add(aggregatedDepsIr.getFqName());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : this.aggregatedDeps) {
            AggregatedDepsIr aggregatedDepsIr2 = (AggregatedDepsIr) obj2;
            if (aggregatedDepsIr2.getTest() == null && aggregatedDepsIr2.getModule() != null) {
                arrayList2.add(obj2);
            }
        }
        ArrayList<AggregatedDepsIr> arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        for (AggregatedDepsIr aggregatedDepsIr3 : arrayList3) {
            arrayList4.add(aggregatedDepsIr3.getFqName());
        }
        ArrayList arrayList5 = arrayList4;
        ArrayList<AggregatedDepsIr> arrayList6 = new ArrayList();
        for (Object obj3 : this.aggregatedDeps) {
            AggregatedDepsIr aggregatedDepsIr4 = (AggregatedDepsIr) obj3;
            if (aggregatedDepsIr4.getTest() == null && aggregatedDepsIr4.getModule() == null) {
                arrayList6.add(obj3);
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (AggregatedDepsIr aggregatedDepsIr5 : arrayList6) {
            String test2 = aggregatedDepsIr5.getTest();
            Object obj4 = linkedHashMap2.get(test2);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap2.put(test2, obj4);
            }
            ((List) obj4).add(aggregatedDepsIr5.getFqName());
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (AggregatedRootIr aggregatedRootIr : set) {
            if (!set2.contains(aggregatedRootIr.getRoot())) {
                ClassName root = aggregatedRootIr.getRoot();
                Object obj5 = linkedHashMap3.get(root);
                if (obj5 == null) {
                    obj5 = new LinkedHashSet();
                    linkedHashMap3.put(root, obj5);
                }
                LinkedHashSet linkedHashSet = (LinkedHashSet) obj5;
                linkedHashSet.addAll(arrayList5);
                linkedHashSet.addAll(CollectionsKt.flatten(linkedHashMap2.values()));
                Object obj6 = linkedHashMap.get(aggregatedRootIr.getRoot().canonicalName());
                if (obj6 == null) {
                    obj6 = CollectionsKt.emptyList();
                }
                linkedHashSet.addAll((Collection) obj6);
                Unit unit = Unit.INSTANCE;
            }
        }
        if (!set2.isEmpty()) {
            ClassName className = DEFAULT_ROOT_CLASS_NAME;
            Object obj7 = linkedHashMap3.get(className);
            if (obj7 == null) {
                obj7 = new LinkedHashSet();
                linkedHashMap3.put(className, obj7);
            }
            LinkedHashSet linkedHashSet2 = (LinkedHashSet) obj7;
            linkedHashSet2.addAll(arrayList5);
            linkedHashSet2.addAll(CollectionsKt.flatten(linkedHashMap2.values()));
            ArrayList arrayList7 = new ArrayList();
            for (ClassName className2 : set2) {
                Object obj8 = linkedHashMap.get(className2.canonicalName());
                if (obj8 == null) {
                    obj8 = CollectionsKt.emptyList();
                }
                CollectionsKt.addAll(arrayList7, (List) obj8);
            }
            linkedHashSet2.addAll(arrayList7);
            Unit unit2 = Unit.INSTANCE;
        } else if (z) {
            ClassName className3 = DEFAULT_ROOT_CLASS_NAME;
            Object obj9 = linkedHashMap3.get(className3);
            if (obj9 == null) {
                obj9 = new LinkedHashSet();
                linkedHashMap3.put(className3, obj9);
            }
            LinkedHashSet linkedHashSet3 = (LinkedHashSet) obj9;
            linkedHashSet3.addAll(arrayList5);
            ArrayList<Map.Entry> arrayList8 = new ArrayList();
            for (Object obj10 : linkedHashMap2.entrySet()) {
                if (!Intrinsics.areEqual((String) ((Map.Entry) obj10).getKey(), SINGLETON_COMPONENT_CLASS_NAME.canonicalName())) {
                    arrayList8.add(obj10);
                }
            }
            ArrayList arrayList9 = new ArrayList();
            for (Map.Entry entry : arrayList8) {
                CollectionsKt.addAll(arrayList9, (List) entry.getValue());
            }
            linkedHashSet3.addAll(arrayList9);
            Unit unit3 = Unit.INSTANCE;
        }
        return linkedHashMap3;
    }

    /* compiled from: ComponentTreeDepsIrCreator.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B!\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006J\u001c\u0010\u0010\u001a\n \u0011*\u0004\u0018\u00010\u00060\u0006*\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0003H\u0002J\f\u0010\u0013\u001a\u00020\u0003*\u00020\u0006H\u0002R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIrCreator$ComponentTreeDepsNameGenerator;", "", "destinationPackage", "", "otherRootNames", "", "Lcom/squareup/javapoet/ClassName;", "(Ljava/lang/String;Ljava/util/Collection;)V", "simpleNameMap", "", "getSimpleNameMap", "()Ljava/util/Map;", "simpleNameMap$delegate", "Lkotlin/Lazy;", "generate", "rootName", "append", "kotlin.jvm.PlatformType", "suffix", "enclosedName", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class ComponentTreeDepsNameGenerator {
        private final String destinationPackage;
        private final Collection<ClassName> otherRootNames;
        private final Lazy simpleNameMap$delegate;

        public ComponentTreeDepsNameGenerator() {
            this(null, null, 3, null);
        }

        public ComponentTreeDepsNameGenerator(String str, Collection<ClassName> otherRootNames) {
            Intrinsics.checkNotNullParameter(otherRootNames, "otherRootNames");
            this.destinationPackage = str;
            this.otherRootNames = otherRootNames;
            this.simpleNameMap$delegate = LazyKt.lazy(new Function0<Map<ClassName, String>>() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$ComponentTreeDepsNameGenerator$simpleNameMap$2
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Map<ClassName, String> invoke() {
                    Collection collection;
                    String enclosedName;
                    String joinToString$default;
                    String enclosedName2;
                    String enclosedName3;
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    ComponentTreeDepsIrCreator.ComponentTreeDepsNameGenerator componentTreeDepsNameGenerator = ComponentTreeDepsIrCreator.ComponentTreeDepsNameGenerator.this;
                    collection = componentTreeDepsNameGenerator.otherRootNames;
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    for (Object obj : collection) {
                        enclosedName3 = componentTreeDepsNameGenerator.enclosedName((ClassName) obj);
                        Object obj2 = linkedHashMap2.get(enclosedName3);
                        if (obj2 == null) {
                            obj2 = (List) new ArrayList();
                            linkedHashMap2.put(enclosedName3, obj2);
                        }
                        ((List) obj2).add(obj);
                    }
                    for (List list : linkedHashMap2.values()) {
                        if (list.size() == 1) {
                            Object first = CollectionsKt.first((List<? extends Object>) list);
                            enclosedName = componentTreeDepsNameGenerator.enclosedName((ClassName) CollectionsKt.first((List<? extends Object>) list));
                            linkedHashMap.put(first, enclosedName);
                        } else {
                            LinkedHashSet linkedHashSet = new LinkedHashSet();
                            for (ClassName className : CollectionsKt.sorted(list)) {
                                ClassName enclosingClassName = className.enclosingClassName();
                                String enclosedName4 = enclosingClassName == null ? "" : componentTreeDepsNameGenerator.enclosedName(enclosingClassName);
                                String str2 = enclosedName4;
                                if (!(str2.length() > 0) || !Character.isUpperCase(enclosedName4.charAt(0))) {
                                    String className2 = className.toString();
                                    Intrinsics.checkNotNullExpressionValue(className2, "className.toString()");
                                    joinToString$default = CollectionsKt.joinToString$default(CollectionsKt.dropLast(StringsKt.split$default((CharSequence) className2, new char[]{'.'}, false, 0, 6, (Object) null), 1), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$ComponentTreeDepsNameGenerator$simpleNameMap$2$1$2$1$basePrefix$1$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final CharSequence invoke(String it) {
                                            Intrinsics.checkNotNullParameter(it, "it");
                                            return String.valueOf(StringsKt.first(it));
                                        }
                                    }, 30, null);
                                } else {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < str2.length(); i++) {
                                        char charAt = str2.charAt(i);
                                        if (!Character.isLowerCase(charAt)) {
                                            sb.append(charAt);
                                        }
                                    }
                                    joinToString$default = sb.toString();
                                    Intrinsics.checkNotNullExpressionValue(joinToString$default, "filterNotTo(StringBuilder(), predicate).toString()");
                                }
                                int i2 = 2;
                                String str3 = joinToString$default;
                                while (!linkedHashSet.add(str3)) {
                                    str3 = Intrinsics.stringPlus(joinToString$default, Integer.valueOf(i2));
                                    i2++;
                                }
                                StringBuilder append = new StringBuilder().append(str3).append('_');
                                enclosedName2 = componentTreeDepsNameGenerator.enclosedName(className);
                                linkedHashMap.put(className, append.append(enclosedName2).toString());
                            }
                        }
                    }
                    return linkedHashMap;
                }
            });
        }

        public /* synthetic */ ComponentTreeDepsNameGenerator(String str, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str, (i & 2) != 0 ? SetsKt.emptySet() : set);
        }

        private final Map<ClassName, String> getSimpleNameMap() {
            return (Map) this.simpleNameMap$delegate.getValue();
        }

        public final ClassName generate(ClassName rootName) {
            String str;
            Intrinsics.checkNotNullParameter(rootName, "rootName");
            String str2 = this.destinationPackage;
            if (str2 == null) {
                str2 = rootName.packageName();
            }
            if (this.otherRootNames.isEmpty()) {
                str = enclosedName(rootName);
            } else {
                str = (String) MapsKt.getValue(getSimpleNameMap(), rootName);
            }
            ClassName className = ClassName.get(str2, str, new String[0]);
            Intrinsics.checkNotNullExpressionValue(className, "get(\n          destinati…me)\n          }\n        )");
            ClassName append = append(className, "_ComponentTreeDeps");
            Intrinsics.checkNotNullExpressionValue(append, "get(\n          destinati…end(\"_ComponentTreeDeps\")");
            return append;
        }

        public final String enclosedName(ClassName className) {
            List<String> simpleNames = className.simpleNames();
            Intrinsics.checkNotNullExpressionValue(simpleNames, "simpleNames()");
            return CollectionsKt.joinToString$default(simpleNames, "_", null, null, 0, null, null, 62, null);
        }

        private final ClassName append(ClassName className, String str) {
            return className.peerClass(Intrinsics.stringPlus(className.simpleName(), str));
        }
    }

    /* compiled from: ComponentTreeDepsIrCreator.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jr\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\n2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\n2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\n2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\nH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\u001b"}, d2 = {"Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIrCreator$Companion;", "", "()V", "DEFAULT_ROOT_CLASS_NAME", "Lcom/squareup/javapoet/ClassName;", "getDEFAULT_ROOT_CLASS_NAME", "()Lcom/squareup/javapoet/ClassName;", "SINGLETON_COMPONENT_CLASS_NAME", "getSINGLETON_COMPONENT_CLASS_NAME", "components", "", "Ldagger/hilt/processor/internal/root/ir/ComponentTreeDepsIr;", "isTest", "", "isSharedTestComponentsEnabled", "aggregatedRoots", "Ldagger/hilt/processor/internal/root/ir/AggregatedRootIr;", "defineComponentDeps", "Ldagger/hilt/processor/internal/root/ir/DefineComponentClassesIr;", "aliasOfDeps", "Ldagger/hilt/processor/internal/root/ir/AliasOfPropagatedDataIr;", "aggregatedDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedDepsIr;", "aggregatedUninstallModulesDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedUninstallModulesIr;", "aggregatedEarlyEntryPointDeps", "Ldagger/hilt/processor/internal/root/ir/AggregatedEarlyEntryPointIr;", "java_dagger_hilt_processor_internal_root_ir-ir"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Set<ComponentTreeDepsIr> components(boolean z, boolean z2, Set<AggregatedRootIr> aggregatedRoots, Set<DefineComponentClassesIr> defineComponentDeps, Set<AliasOfPropagatedDataIr> aliasOfDeps, Set<AggregatedDepsIr> aggregatedDeps, Set<AggregatedUninstallModulesIr> aggregatedUninstallModulesDeps, Set<AggregatedEarlyEntryPointIr> aggregatedEarlyEntryPointDeps) {
            Intrinsics.checkNotNullParameter(aggregatedRoots, "aggregatedRoots");
            Intrinsics.checkNotNullParameter(defineComponentDeps, "defineComponentDeps");
            Intrinsics.checkNotNullParameter(aliasOfDeps, "aliasOfDeps");
            Intrinsics.checkNotNullParameter(aggregatedDeps, "aggregatedDeps");
            Intrinsics.checkNotNullParameter(aggregatedUninstallModulesDeps, "aggregatedUninstallModulesDeps");
            Intrinsics.checkNotNullParameter(aggregatedEarlyEntryPointDeps, "aggregatedEarlyEntryPointDeps");
            ComponentTreeDepsIrCreator componentTreeDepsIrCreator = new ComponentTreeDepsIrCreator(z2, CollectionsKt.toSet(CollectionsKt.sortedWith(CollectionsKt.toList(aggregatedRoots), new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$Companion$components$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((AggregatedRootIr) t).getFqName().canonicalName(), ((AggregatedRootIr) t2).getFqName().canonicalName());
                }
            })), CollectionsKt.toSet(CollectionsKt.sortedWith(CollectionsKt.toList(defineComponentDeps), new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$Companion$components$$inlined$sortedBy$2
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((DefineComponentClassesIr) t).getFqName().canonicalName(), ((DefineComponentClassesIr) t2).getFqName().canonicalName());
                }
            })), CollectionsKt.toSet(CollectionsKt.sortedWith(CollectionsKt.toList(aliasOfDeps), new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$Companion$components$$inlined$sortedBy$3
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((AliasOfPropagatedDataIr) t).getFqName().canonicalName(), ((AliasOfPropagatedDataIr) t2).getFqName().canonicalName());
                }
            })), CollectionsKt.toSet(CollectionsKt.sortedWith(CollectionsKt.toList(aggregatedDeps), new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$Companion$components$$inlined$sortedBy$4
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((AggregatedDepsIr) t).getFqName().canonicalName(), ((AggregatedDepsIr) t2).getFqName().canonicalName());
                }
            })), CollectionsKt.toSet(CollectionsKt.sortedWith(CollectionsKt.toList(aggregatedUninstallModulesDeps), new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$Companion$components$$inlined$sortedBy$5
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((AggregatedUninstallModulesIr) t).getFqName().canonicalName(), ((AggregatedUninstallModulesIr) t2).getFqName().canonicalName());
                }
            })), CollectionsKt.toSet(CollectionsKt.sortedWith(CollectionsKt.toList(aggregatedEarlyEntryPointDeps), new Comparator() { // from class: dagger.hilt.processor.internal.root.ir.ComponentTreeDepsIrCreator$Companion$components$$inlined$sortedBy$6
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((AggregatedEarlyEntryPointIr) t).getFqName().canonicalName(), ((AggregatedEarlyEntryPointIr) t2).getFqName().canonicalName());
                }
            })), null);
            return z ? componentTreeDepsIrCreator.testComponents() : componentTreeDepsIrCreator.prodComponents();
        }

        public final ClassName getDEFAULT_ROOT_CLASS_NAME() {
            return ComponentTreeDepsIrCreator.DEFAULT_ROOT_CLASS_NAME;
        }

        public final ClassName getSINGLETON_COMPONENT_CLASS_NAME() {
            return ComponentTreeDepsIrCreator.SINGLETON_COMPONENT_CLASS_NAME;
        }
    }

    static {
        ClassName className = ClassName.get("dagger.hilt.android.internal.testing.root", "Default", new String[0]);
        Intrinsics.checkNotNullExpressionValue(className, "get(\"dagger.hilt.android…testing.root\", \"Default\")");
        DEFAULT_ROOT_CLASS_NAME = className;
        ClassName className2 = ClassName.get("dagger.hilt.components", "SingletonComponent", new String[0]);
        Intrinsics.checkNotNullExpressionValue(className2, "get(\"dagger.hilt.compone…s\", \"SingletonComponent\")");
        SINGLETON_COMPONENT_CLASS_NAME = className2;
    }
}
