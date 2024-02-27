package com.epson.iprojection.common;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DataStoreRepository.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\b\u0007\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0006\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\bJ!\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/common/DataStoreRepository;", "", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "(Landroidx/datastore/core/DataStore;)V", "getInstallInfo", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVersion", "updateAppVesion", "", "addVersion", "storeVersion", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInstallInfo", "installInfo", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DataStoreRepository {
    private final DataStore<Preferences> dataStore;

    @Inject
    public DataStoreRepository(DataStore<Preferences> dataStore) {
        Intrinsics.checkNotNullParameter(dataStore, "dataStore");
        this.dataStore = dataStore;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:20|21))(3:22|23|(1:25))|11|12|(1:14)(1:18)|15|16))|28|6|7|(0)(0)|11|12|(0)(0)|15|16) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006a, code lost:
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x006b, code lost:
        r6 = kotlin.Result.Companion;
        r5 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r5));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateAppVesion(java.lang.String r5, java.lang.String r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.common.DataStoreRepository$updateAppVesion$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.common.DataStoreRepository$updateAppVesion$1 r0 = (com.epson.iprojection.common.DataStoreRepository$updateAppVesion$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.common.DataStoreRepository$updateAppVesion$1 r0 = new com.epson.iprojection.common.DataStoreRepository$updateAppVesion$1
            r0.<init>(r4, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L6a
            goto L63
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r6 = r7.append(r6)
            r7 = 58
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r5 = r5.toString()
            kotlin.Result$Companion r6 = kotlin.Result.Companion
            kotlin.Result$Companion r6 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L6a
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r6 = r4.dataStore     // Catch: java.lang.Throwable -> L6a
            com.epson.iprojection.common.DataStoreRepository$updateAppVesion$2$1 r7 = new com.epson.iprojection.common.DataStoreRepository$updateAppVesion$2$1     // Catch: java.lang.Throwable -> L6a
            r2 = 0
            r7.<init>(r5, r2)     // Catch: java.lang.Throwable -> L6a
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7     // Catch: java.lang.Throwable -> L6a
            r0.label = r3     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r6, r7, r0)     // Catch: java.lang.Throwable -> L6a
            if (r7 != r1) goto L63
            return r1
        L63:
            androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r5 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L6a
            goto L75
        L6a:
            r5 = move-exception
            kotlin.Result$Companion r6 = kotlin.Result.Companion
            java.lang.Object r5 = kotlin.ResultKt.createFailure(r5)
            java.lang.Object r5 = kotlin.Result.m337constructorimpl(r5)
        L75:
            java.lang.Throwable r6 = kotlin.Result.m340exceptionOrNullimpl(r5)
            if (r6 != 0) goto L83
            androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
            java.lang.String r5 = "ds success update datastore"
            com.epson.iprojection.common.Lg.d(r5)
            goto L95
        L83:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r7 = "ds faile update datastore exeption = "
            r5.<init>(r7)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.epson.iprojection.common.Lg.d(r5)
        L95:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.DataStoreRepository.updateAppVesion(java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:22|23))(3:24|25|(1:27))|11|(1:13)|14|15|(1:20)(2:17|18)))|30|6|7|(0)(0)|11|(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:
        r0 = kotlin.Result.Companion;
        r7 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r7));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getVersion(kotlin.coroutines.Continuation<? super java.lang.String> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.common.DataStoreRepository$getVersion$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.common.DataStoreRepository$getVersion$1 r0 = (com.epson.iprojection.common.DataStoreRepository$getVersion$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.common.DataStoreRepository$getVersion$1 r0 = new com.epson.iprojection.common.DataStoreRepository$getVersion$1
            r0.<init>(r6, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            java.lang.String r3 = ""
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2c
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L68
            goto L5e
        L2c:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L34:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L68
            r7 = r6
            com.epson.iprojection.common.DataStoreRepository r7 = (com.epson.iprojection.common.DataStoreRepository) r7     // Catch: java.lang.Throwable -> L68
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r6.dataStore     // Catch: java.lang.Throwable -> L68
            kotlinx.coroutines.flow.Flow r7 = r7.getData()     // Catch: java.lang.Throwable -> L68
            com.epson.iprojection.common.DataStoreRepository$getVersion$result$1$flow$1 r2 = new com.epson.iprojection.common.DataStoreRepository$getVersion$result$1$flow$1     // Catch: java.lang.Throwable -> L68
            r5 = 0
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L68
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L68
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.m1867catch(r7, r2)     // Catch: java.lang.Throwable -> L68
            com.epson.iprojection.common.DataStoreRepository$getVersion$lambda$4$$inlined$map$1 r2 = new com.epson.iprojection.common.DataStoreRepository$getVersion$lambda$4$$inlined$map$1     // Catch: java.lang.Throwable -> L68
            r2.<init>()     // Catch: java.lang.Throwable -> L68
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L68
            r0.label = r4     // Catch: java.lang.Throwable -> L68
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L68
            if (r7 != r1) goto L5e
            return r1
        L5e:
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Throwable -> L68
            if (r7 != 0) goto L63
            r7 = r3
        L63:
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L68
            goto L73
        L68:
            r7 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)
        L73:
            boolean r0 = kotlin.Result.m343isFailureimpl(r7)
            if (r0 == 0) goto L7a
            goto L7b
        L7a:
            r3 = r7
        L7b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.DataStoreRepository.getVersion(kotlin.coroutines.Continuation):java.lang.Object");
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateInstallInfo(java.lang.String r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$1 r0 = (com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$1 r0 = new com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$1
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
            com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$2$1 r2 = new com.epson.iprojection.common.DataStoreRepository$updateInstallInfo$2$1     // Catch: java.lang.Throwable -> L53
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
            java.lang.String r6 = "ds success update datastore"
            com.epson.iprojection.common.Lg.d(r6)
            goto L7e
        L6c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "ds faile update datastore exeption = "
            r6.<init>(r0)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.epson.iprojection.common.Lg.d(r6)
        L7e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.DataStoreRepository.updateInstallInfo(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(9:5|6|7|(1:(1:10)(2:22|23))(3:24|25|(1:27))|11|(1:13)|14|15|(1:20)(2:17|18)))|30|6|7|(0)(0)|11|(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:
        r0 = kotlin.Result.Companion;
        r7 = kotlin.Result.m337constructorimpl(kotlin.ResultKt.createFailure(r7));
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getInstallInfo(kotlin.coroutines.Continuation<? super java.lang.String> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.epson.iprojection.common.DataStoreRepository$getInstallInfo$1
            if (r0 == 0) goto L14
            r0 = r7
            com.epson.iprojection.common.DataStoreRepository$getInstallInfo$1 r0 = (com.epson.iprojection.common.DataStoreRepository$getInstallInfo$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.epson.iprojection.common.DataStoreRepository$getInstallInfo$1 r0 = new com.epson.iprojection.common.DataStoreRepository$getInstallInfo$1
            r0.<init>(r6, r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            java.lang.String r3 = ""
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2c
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L68
            goto L5e
        L2c:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L34:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L68
            r7 = r6
            com.epson.iprojection.common.DataStoreRepository r7 = (com.epson.iprojection.common.DataStoreRepository) r7     // Catch: java.lang.Throwable -> L68
            androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> r7 = r6.dataStore     // Catch: java.lang.Throwable -> L68
            kotlinx.coroutines.flow.Flow r7 = r7.getData()     // Catch: java.lang.Throwable -> L68
            com.epson.iprojection.common.DataStoreRepository$getInstallInfo$result$1$flow$1 r2 = new com.epson.iprojection.common.DataStoreRepository$getInstallInfo$result$1$flow$1     // Catch: java.lang.Throwable -> L68
            r5 = 0
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L68
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch: java.lang.Throwable -> L68
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.m1867catch(r7, r2)     // Catch: java.lang.Throwable -> L68
            com.epson.iprojection.common.DataStoreRepository$getInstallInfo$lambda$9$$inlined$map$1 r2 = new com.epson.iprojection.common.DataStoreRepository$getInstallInfo$lambda$9$$inlined$map$1     // Catch: java.lang.Throwable -> L68
            r2.<init>()     // Catch: java.lang.Throwable -> L68
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2     // Catch: java.lang.Throwable -> L68
            r0.label = r4     // Catch: java.lang.Throwable -> L68
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r2, r0)     // Catch: java.lang.Throwable -> L68
            if (r7 != r1) goto L5e
            return r1
        L5e:
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Throwable -> L68
            if (r7 != 0) goto L63
            r7 = r3
        L63:
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)     // Catch: java.lang.Throwable -> L68
            goto L73
        L68:
            r7 = move-exception
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m337constructorimpl(r7)
        L73:
            boolean r0 = kotlin.Result.m343isFailureimpl(r7)
            if (r0 == 0) goto L7a
            goto L7b
        L7a:
            r3 = r7
        L7b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.DataStoreRepository.getInstallInfo(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
