package com.epson.iprojection.common;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DataStoreRepository.kt */
@Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.common.DataStoreRepository", f = "DataStoreRepository.kt", i = {}, l = {35}, m = "getVersion", n = {}, s = {})
/* loaded from: classes.dex */
public final class DataStoreRepository$getVersion$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DataStoreRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreRepository$getVersion$1(DataStoreRepository dataStoreRepository, Continuation<? super DataStoreRepository$getVersion$1> continuation) {
        super(continuation);
        this.this$0 = dataStoreRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getVersion(this);
    }
}
