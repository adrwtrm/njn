package com.epson.iprojection.common;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DataStoreRepository.kt */
@Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.common.DataStoreRepository", f = "DataStoreRepository.kt", i = {}, l = {42}, m = "updateInstallInfo", n = {}, s = {})
/* loaded from: classes.dex */
public final class DataStoreRepository$updateInstallInfo$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DataStoreRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreRepository$updateInstallInfo$1(DataStoreRepository dataStoreRepository, Continuation<? super DataStoreRepository$updateInstallInfo$1> continuation) {
        super(continuation);
        this.this$0 = dataStoreRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.updateInstallInfo(null, this);
    }
}
