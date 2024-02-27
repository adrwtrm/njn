package com.epson.iprojection.customer_satisfaction.gateways;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CSRepository.kt */
@Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.gateways.CSRepository", f = "CSRepository.kt", i = {}, l = {67}, m = "updateTryCollectCount", n = {}, s = {})
/* loaded from: classes.dex */
public final class CSRepository$updateTryCollectCount$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CSRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CSRepository$updateTryCollectCount$1(CSRepository cSRepository, Continuation<? super CSRepository$updateTryCollectCount$1> continuation) {
        super(continuation);
        this.this$0 = cSRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.updateTryCollectCount(0, this);
    }
}
