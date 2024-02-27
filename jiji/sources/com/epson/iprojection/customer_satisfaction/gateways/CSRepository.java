package com.epson.iprojection.customer_satisfaction.gateways;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CSRepository.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0015\b\u0007\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0006\u001a\u00020\u0007H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0011\u0010\u000b\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0011\u0010\f\u001a\u00020\rH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0011\u0010\u000e\u001a\u00020\rH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J\u0019\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\rH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0019J\u0019\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\rH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0019R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/gateways/CSRepository;", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "(Landroidx/datastore/core/DataStore;)V", "getCollectedLastDate", "Ljava/time/LocalDate;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getIsCollectedSatisfaction", "", "getIsCollectedStoreReview", "getTryCollectCount", "", "getUsedCount", "updateCollectedLastDays", "", "date", "(Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateIsCollectedSatisfaction", "isCollected", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateIsCollectedStoreReview", "updateTryCollectCount", "count", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUsedCount", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CSRepository implements CSStoreInterface {
    private final DataStore<Preferences> dataStore;

    @Inject
    public CSRepository(DataStore<Preferences> dataStore) {
        Intrinsics.checkNotNullParameter(dataStore, "dataStore");
        this.dataStore = dataStore;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:20|21))(3:22|23|(1:25))|11|12|(1:14)(1:18)|15|16))|28|6|7|(0)(0)|11|12|(0)(0)|15|16) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
        r7 = kotlin.Result.Companion;
        r6 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r6));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c  */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateUsedCount(int r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$1
            r0.<init>(r5, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L53
            goto L4c
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L53
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r5.dataStore     // Catch: java.lang.Throwable -> L53
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$2$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateUsedCount$2$1     // Catch: java.lang.Throwable -> L53
            r4 = 0
            r2.<init>(r6, r4)     // Catch: java.lang.Throwable -> L53
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2     // Catch: java.lang.Throwable -> L53
            r0.label = r3     // Catch: java.lang.Throwable -> L53
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r2, r0)     // Catch: java.lang.Throwable -> L53
            if (r7 != r1) goto L4c
            return r1
        L4c:
            androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7     // Catch: java.lang.Throwable -> L53
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L53
            goto L5e
        L53:
            r6 = move-exception
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r6)
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r6)
        L5e:
            java.lang.Throwable r7 = kotlin.Result.m340exceptionOrNullimpl(r6)
            if (r7 != 0) goto L6c
            androidx.datastore.preferences.core.Preferences r6 = (androidx.datastore.preferences.core.Preferences) r6
            java.lang.String r6 = "cs success update datastore"
            com.epson.iprojection.common.Lg.d(r6)
            goto L7e
        L6c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "cs faile update datastore exeption = "
            r6.<init>(r0)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.epson.iprojection.common.Lg.e(r6)
        L7e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.updateUsedCount(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:20|21))(5:22|23|(1:25)(1:29)|26|(1:28))|11|12|(1:14)(1:18)|15|16))|32|6|7|(0)(0)|11|12|(0)(0)|15|16) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0058, code lost:
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0059, code lost:
        r7 = kotlin.Result.Companion;
        r6 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r6));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateIsCollectedStoreReview(boolean r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$1
            r0.<init>(r5, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L58
            goto L51
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L58
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r5.dataStore     // Catch: java.lang.Throwable -> L58
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$2$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedStoreReview$2$1     // Catch: java.lang.Throwable -> L58
            if (r6 == 0) goto L41
            r6 = r3
            goto L42
        L41:
            r6 = 0
        L42:
            r4 = 0
            r2.<init>(r6, r4)     // Catch: java.lang.Throwable -> L58
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2     // Catch: java.lang.Throwable -> L58
            r0.label = r3     // Catch: java.lang.Throwable -> L58
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r2, r0)     // Catch: java.lang.Throwable -> L58
            if (r7 != r1) goto L51
            return r1
        L51:
            androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7     // Catch: java.lang.Throwable -> L58
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L58
            goto L63
        L58:
            r6 = move-exception
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r6)
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r6)
        L63:
            java.lang.Throwable r7 = kotlin.Result.m340exceptionOrNullimpl(r6)
            if (r7 != 0) goto L71
            androidx.datastore.preferences.core.Preferences r6 = (androidx.datastore.preferences.core.Preferences) r6
            java.lang.String r6 = "cs success update datastore"
            com.epson.iprojection.common.Lg.d(r6)
            goto L83
        L71:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "cs faile update datastore exeption = "
            r6.<init>(r0)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.epson.iprojection.common.Lg.e(r6)
        L83:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.updateIsCollectedStoreReview(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:1|(2:3|(7:5|6|7|(1:(1:10)(2:16|17))(5:18|19|(1:21)(1:25)|22|(1:24))|11|12|13))|28|6|7|(0)(0)|11|12|13) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0057, code lost:
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0058, code lost:
        r7 = kotlin.Result.Companion;
        kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r6));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateIsCollectedSatisfaction(boolean r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$1
            r0.<init>(r5, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L57
            goto L51
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L57
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r5.dataStore     // Catch: java.lang.Throwable -> L57
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$2$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateIsCollectedSatisfaction$2$1     // Catch: java.lang.Throwable -> L57
            if (r6 == 0) goto L41
            r6 = r3
            goto L42
        L41:
            r6 = 0
        L42:
            r4 = 0
            r2.<init>(r6, r4)     // Catch: java.lang.Throwable -> L57
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2     // Catch: java.lang.Throwable -> L57
            r0.label = r3     // Catch: java.lang.Throwable -> L57
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r2, r0)     // Catch: java.lang.Throwable -> L57
            if (r7 != r1) goto L51
            return r1
        L51:
            androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7     // Catch: java.lang.Throwable -> L57
            kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L57
            goto L61
        L57:
            r6 = move-exception
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r6)
            kotlin.Result.m337constructorimpl(r6)
        L61:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.updateIsCollectedSatisfaction(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:20|21))(3:22|23|(1:25))|11|12|(1:14)(1:18)|15|16))|28|6|7|(0)(0)|11|12|(0)(0)|15|16) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
        r7 = kotlin.Result.Companion;
        r6 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r6));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c  */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateTryCollectCount(int r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$1
            r0.<init>(r5, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L53
            goto L4c
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L53
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r5.dataStore     // Catch: java.lang.Throwable -> L53
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$2$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateTryCollectCount$2$1     // Catch: java.lang.Throwable -> L53
            r4 = 0
            r2.<init>(r6, r4)     // Catch: java.lang.Throwable -> L53
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2     // Catch: java.lang.Throwable -> L53
            r0.label = r3     // Catch: java.lang.Throwable -> L53
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r2, r0)     // Catch: java.lang.Throwable -> L53
            if (r7 != r1) goto L4c
            return r1
        L4c:
            androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7     // Catch: java.lang.Throwable -> L53
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L53
            goto L5e
        L53:
            r6 = move-exception
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r6)
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r6)
        L5e:
            java.lang.Throwable r7 = kotlin.Result.m340exceptionOrNullimpl(r6)
            if (r7 != 0) goto L6c
            androidx.datastore.preferences.core.Preferences r6 = (androidx.datastore.preferences.core.Preferences) r6
            java.lang.String r6 = "cs success update datastore"
            com.epson.iprojection.common.Lg.d(r6)
            goto L7e
        L6c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "cs faile update datastore exeption = "
            r6.<init>(r0)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.epson.iprojection.common.Lg.e(r6)
        L7e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.updateTryCollectCount(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:20|21))(3:22|23|(1:25))|11|12|(1:14)(1:18)|15|16))|28|6|7|(0)(0)|11|12|(0)(0)|15|16) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0062, code lost:
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
        r7 = kotlin.Result.Companion;
        r6 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r6));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateCollectedLastDays(java.time.LocalDate r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$1
            r0.<init>(r5, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L62
            goto L5b
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L62
            java.lang.String r7 = "yyyy/MM/dd"
            java.time.format.DateTimeFormatter r7 = java.time.format.DateTimeFormatter.ofPattern(r7)     // Catch: java.lang.Throwable -> L62
            java.lang.String r2 = "ofPattern(\"yyyy/MM/dd\")"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r2)     // Catch: java.lang.Throwable -> L62
            java.lang.String r6 = r6.format(r7)     // Catch: java.lang.Throwable -> L62
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r5.dataStore     // Catch: java.lang.Throwable -> L62
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$2$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$2$1     // Catch: java.lang.Throwable -> L62
            r4 = 0
            r2.<init>(r6, r4)     // Catch: java.lang.Throwable -> L62
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2     // Catch: java.lang.Throwable -> L62
            r0.label = r3     // Catch: java.lang.Throwable -> L62
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r2, r0)     // Catch: java.lang.Throwable -> L62
            if (r7 != r1) goto L5b
            return r1
        L5b:
            androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7     // Catch: java.lang.Throwable -> L62
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L62
            goto L6d
        L62:
            r6 = move-exception
            kotlin.Result$Companion r7 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r6)
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r6)
        L6d:
            java.lang.Throwable r7 = kotlin.Result.m340exceptionOrNullimpl(r6)
            if (r7 != 0) goto L7b
            androidx.datastore.preferences.core.Preferences r6 = (androidx.datastore.preferences.core.Preferences) r6
            java.lang.String r6 = "cs success update datastore"
            com.epson.iprojection.common.Lg.d(r6)
            goto L8d
        L7b:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "cs faile update datastore exeption = "
            r6.<init>(r0)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.epson.iprojection.common.Lg.e(r6)
        L8d:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.updateCollectedLastDays(java.time.LocalDate, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:23|24))(3:25|26|(1:28))|11|(1:13)(1:22)|14|15|(2:17|18)(1:20)))|31|6|7|(0)(0)|11|(0)(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0071, code lost:
        r0 = kotlin.Result.Companion;
        r7 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r7));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[Catch: all -> 0x0070, TryCatch #0 {all -> 0x0070, blocks: (B:11:0x0027, B:19:0x005d, B:21:0x0061, B:23:0x0067, B:16:0x0036), top: B:31:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getUsedCount(kotlin.coroutines.Continuation<? super java.lang.Integer> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$1
            r0.<init>(r6, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L70
            goto L5d
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L70
            r7 = r6
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository r7 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository) r7     // Catch: java.lang.Throwable -> L70
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r6.dataStore     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = r7.getData()     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$result$1$flow$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$result$1$flow$1     // Catch: java.lang.Throwable -> L70
            r5 = 0
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L70
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.m1867catch(r7, r2)     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$lambda$14$$inlined$map$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getUsedCount$lambda$14$$inlined$map$1     // Catch: java.lang.Throwable -> L70
            r2.<init>()     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L70
            r0.label = r4     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L70
            if (r7 != r1) goto L5d
            return r1
        L5d:
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch: java.lang.Throwable -> L70
            if (r7 == 0) goto L66
            int r7 = r7.intValue()     // Catch: java.lang.Throwable -> L70
            goto L67
        L66:
            r7 = r3
        L67:
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L70
            goto L7b
        L70:
            r7 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)
        L7b:
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r3)
            boolean r1 = kotlin.Result.m343isFailureimpl(r7)
            if (r1 == 0) goto L86
            r7 = r0
        L86:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.getUsedCount(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:23|24))(3:25|26|(1:28))|11|(1:13)(1:22)|14|15|(2:17|18)(1:20)))|31|6|7|(0)(0)|11|(0)(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0071, code lost:
        r0 = kotlin.Result.Companion;
        r7 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r7));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[Catch: all -> 0x0070, TryCatch #0 {all -> 0x0070, blocks: (B:11:0x0027, B:19:0x005d, B:21:0x0061, B:23:0x0067, B:16:0x0036), top: B:31:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getTryCollectCount(kotlin.coroutines.Continuation<? super java.lang.Integer> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$1
            r0.<init>(r6, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L70
            goto L5d
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L70
            r7 = r6
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository r7 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository) r7     // Catch: java.lang.Throwable -> L70
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r6.dataStore     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = r7.getData()     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$result$1$flow$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$result$1$flow$1     // Catch: java.lang.Throwable -> L70
            r5 = 0
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L70
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.m1867catch(r7, r2)     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$lambda$16$$inlined$map$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getTryCollectCount$lambda$16$$inlined$map$1     // Catch: java.lang.Throwable -> L70
            r2.<init>()     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L70
            r0.label = r4     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L70
            if (r7 != r1) goto L5d
            return r1
        L5d:
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch: java.lang.Throwable -> L70
            if (r7 == 0) goto L66
            int r7 = r7.intValue()     // Catch: java.lang.Throwable -> L70
            goto L67
        L66:
            r7 = r3
        L67:
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L70
            goto L7b
        L70:
            r7 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)
        L7b:
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r3)
            boolean r1 = kotlin.Result.m343isFailureimpl(r7)
            if (r1 == 0) goto L86
            r7 = r0
        L86:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.getTryCollectCount(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:23|24))(3:25|26|(1:28))|11|(1:13)(1:22)|14|15|(2:17|18)(1:20)))|31|6|7|(0)(0)|11|(0)(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0071, code lost:
        r0 = kotlin.Result.Companion;
        r7 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r7));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[Catch: all -> 0x0070, TryCatch #0 {all -> 0x0070, blocks: (B:11:0x0027, B:19:0x005d, B:21:0x0061, B:23:0x0067, B:16:0x0036), top: B:31:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getIsCollectedSatisfaction(kotlin.coroutines.Continuation<? super java.lang.Boolean> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$1
            r0.<init>(r6, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L70
            goto L5d
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L70
            r7 = r6
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository r7 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository) r7     // Catch: java.lang.Throwable -> L70
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r6.dataStore     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = r7.getData()     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$result$1$flow$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$result$1$flow$1     // Catch: java.lang.Throwable -> L70
            r5 = 0
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L70
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.m1867catch(r7, r2)     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$lambda$18$$inlined$map$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedSatisfaction$lambda$18$$inlined$map$1     // Catch: java.lang.Throwable -> L70
            r2.<init>()     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L70
            r0.label = r4     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L70
            if (r7 != r1) goto L5d
            return r1
        L5d:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: java.lang.Throwable -> L70
            if (r7 == 0) goto L66
            boolean r7 = r7.booleanValue()     // Catch: java.lang.Throwable -> L70
            goto L67
        L66:
            r7 = r3
        L67:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r7)     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L70
            goto L7b
        L70:
            r7 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)
        L7b:
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            boolean r1 = kotlin.Result.m343isFailureimpl(r7)
            if (r1 == 0) goto L86
            r7 = r0
        L86:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.getIsCollectedSatisfaction(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:23|24))(3:25|26|(1:28))|11|(1:13)(1:22)|14|15|(2:17|18)(1:20)))|31|6|7|(0)(0)|11|(0)(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0071, code lost:
        r0 = kotlin.Result.Companion;
        r7 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r7));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[Catch: all -> 0x0070, TryCatch #0 {all -> 0x0070, blocks: (B:11:0x0027, B:19:0x005d, B:21:0x0061, B:23:0x0067, B:16:0x0036), top: B:31:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getIsCollectedStoreReview(kotlin.coroutines.Continuation<? super java.lang.Boolean> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$1
            r0.<init>(r6, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L70
            goto L5d
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L70
            r7 = r6
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository r7 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository) r7     // Catch: java.lang.Throwable -> L70
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r6.dataStore     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = r7.getData()     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$result$1$flow$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$result$1$flow$1     // Catch: java.lang.Throwable -> L70
            r5 = 0
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L70
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.m1867catch(r7, r2)     // Catch: java.lang.Throwable -> L70
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$lambda$20$$inlined$map$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getIsCollectedStoreReview$lambda$20$$inlined$map$1     // Catch: java.lang.Throwable -> L70
            r2.<init>()     // Catch: java.lang.Throwable -> L70
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L70
            r0.label = r4     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L70
            if (r7 != r1) goto L5d
            return r1
        L5d:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: java.lang.Throwable -> L70
            if (r7 == 0) goto L66
            boolean r7 = r7.booleanValue()     // Catch: java.lang.Throwable -> L70
            goto L67
        L66:
            r7 = r3
        L67:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r7)     // Catch: java.lang.Throwable -> L70
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L70
            goto L7b
        L70:
            r7 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)
        L7b:
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            boolean r1 = kotlin.Result.m343isFailureimpl(r7)
            if (r1 == 0) goto L86
            r7 = r0
        L86:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.getIsCollectedStoreReview(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:1|(2:3|(6:5|6|7|(1:(1:10)(2:31|32))(3:33|34|(1:36))|11|(2:13|14)(8:16|17|(1:19)|20|21|(1:23)(1:27)|24|26)))|39|6|7|(0)(0)|11|(0)(0)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006d, code lost:
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006e, code lost:
        r0 = kotlin.Result.Companion;
        r6 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r6));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0060 A[Catch: all -> 0x006d, TryCatch #1 {all -> 0x006d, blocks: (B:11:0x0027, B:19:0x005c, B:21:0x0060, B:23:0x0068, B:16:0x0036), top: B:42:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068 A[Catch: all -> 0x006d, TRY_LEAVE, TryCatch #1 {all -> 0x006d, blocks: (B:11:0x0027, B:19:0x005c, B:21:0x0060, B:23:0x0068, B:16:0x0036), top: B:42:0x0023 }] */
    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getCollectedLastDate(kotlin.coroutines.Continuation<? super java.time.LocalDate> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$1
            if (r0 == 0) goto L14
            r0 = r6
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$1 r0 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$1 r0 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$1
            r0.<init>(r5, r6)
        L19:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L6d
            goto L5c
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.Result$Companion r6 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L6d
            r6 = r5
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository r6 = (com.epson.iprojection.customer_satisfaction.gateways.CSRepository) r6     // Catch: java.lang.Throwable -> L6d
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r6 = r5.dataStore     // Catch: java.lang.Throwable -> L6d
            kotlinx.coroutines.flow.Flow r6 = r6.getData()     // Catch: java.lang.Throwable -> L6d
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$result$1$flow$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$result$1$flow$1     // Catch: java.lang.Throwable -> L6d
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L6d
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L6d
            kotlinx.coroutines.flow.Flow r6 = kotlinx.coroutines.flow.FlowKt.m1867catch(r6, r2)     // Catch: java.lang.Throwable -> L6d
            com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$lambda$22$$inlined$map$1 r2 = new com.epson.iprojection.customer_satisfaction.gateways.CSRepository$getCollectedLastDate$lambda$22$$inlined$map$1     // Catch: java.lang.Throwable -> L6d
            r2.<init>()     // Catch: java.lang.Throwable -> L6d
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L6d
            r0.label = r4     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L6d
            if (r6 != r1) goto L5c
            return r1
        L5c:
            java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Throwable -> L6d
            if (r6 != 0) goto L68
            java.time.LocalDate r6 = java.time.LocalDate.MAX     // Catch: java.lang.Throwable -> L6d
            java.lang.String r0 = "MAX"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r0)     // Catch: java.lang.Throwable -> L6d
            return r6
        L68:
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r6)     // Catch: java.lang.Throwable -> L6d
            goto L78
        L6d:
            r6 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r6)
            java.lang.Object r6 = kotlin.Result.m337constructorimpl(r6)
        L78:
            boolean r0 = kotlin.Result.m343isFailureimpl(r6)
            if (r0 == 0) goto L7f
            goto L80
        L7f:
            r3 = r6
        L80:
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto L91
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch: java.lang.Exception -> L99
            java.lang.String r6 = "yyyy/MM/dd"
            java.time.format.DateTimeFormatter r6 = java.time.format.DateTimeFormatter.ofPattern(r6)     // Catch: java.lang.Exception -> L99
            java.time.LocalDate r6 = java.time.LocalDate.parse(r3, r6)     // Catch: java.lang.Exception -> L99
            goto L93
        L91:
            java.time.LocalDate r6 = java.time.LocalDate.MAX     // Catch: java.lang.Exception -> L99
        L93:
            java.lang.String r0 = "{\n            if (date !…X\n            }\n        }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r0)     // Catch: java.lang.Exception -> L99
            goto Lb3
        L99:
            r6 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "cs "
            r0.<init>(r1)
            java.lang.StringBuilder r6 = r0.append(r6)
            java.lang.String r6 = r6.toString()
            com.epson.iprojection.common.Lg.e(r6)
            java.time.LocalDate r6 = java.time.LocalDate.MAX
            java.lang.String r0 = "{\n            Lg.e(\"cs $…  LocalDate.MAX\n        }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r0)
        Lb3:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.gateways.CSRepository.getCollectedLastDate(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
