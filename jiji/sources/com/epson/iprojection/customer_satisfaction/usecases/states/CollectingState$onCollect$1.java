package com.epson.iprojection.customer_satisfaction.usecases.states;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CollectingState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$onCollect$1", f = "CollectingState.kt", i = {0, 1}, l = {94, 95, 97}, m = "invokeSuspend", n = {"tryCollectCount", "tryCollectCount"}, s = {"I$0", "I$0"})
/* loaded from: classes.dex */
public final class CollectingState$onCollect$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int I$0;
    int label;
    final /* synthetic */ CollectingState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectingState$onCollect$1(CollectingState collectingState, Continuation<? super CollectingState$onCollect$1> continuation) {
        super(2, continuation);
        this.this$0 = collectingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CollectingState$onCollect$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CollectingState$onCollect$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x008d A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2a
            if (r1 == r4) goto L24
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            kotlin.ResultKt.throwOnFailure(r7)
            goto L8e
        L16:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L1e:
            int r1 = r6.I$0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L7b
        L24:
            int r1 = r6.I$0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5e
        L2a:
            kotlin.ResultKt.throwOnFailure(r7)
            com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$onCollect$1$tryCollectCount$1 r7 = new com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$onCollect$1$tryCollectCount$1
            com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState r1 = r6.this$0
            r5 = 0
            r7.<init>(r1, r5)
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.runBlocking$default(r5, r7, r4, r5)
            java.lang.Number r7 = (java.lang.Number) r7
            int r7 = r7.intValue()
            com.epson.iprojection.customer_satisfaction.entities.EContractNumber r1 = com.epson.iprojection.customer_satisfaction.entities.EContractNumber.TRY_CORRECT_COUNT
            int r1 = r1.getNumber()
            if (r7 < r1) goto L7c
            com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState r1 = r6.this$0
            com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface r1 = com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState.access$getRepository$p(r1)
            r5 = r6
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r6.I$0 = r7
            r6.label = r4
            java.lang.Object r1 = r1.updateIsCollectedSatisfaction(r4, r5)
            if (r1 != r0) goto L5d
            return r0
        L5d:
            r1 = r7
        L5e:
            com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState r7 = r6.this$0
            com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface r7 = com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState.access$getRepository$p(r7)
            java.time.LocalDate r4 = java.time.LocalDate.now()
            java.lang.String r5 = "now()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r5)
            r5 = r6
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r6.I$0 = r1
            r6.label = r3
            java.lang.Object r7 = r7.updateCollectedLastDays(r4, r5)
            if (r7 != r0) goto L7b
            return r0
        L7b:
            r7 = r1
        L7c:
            com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState r1 = r6.this$0
            com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface r1 = com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState.access$getRepository$p(r1)
            r3 = r6
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r6.label = r2
            java.lang.Object r7 = r1.updateTryCollectCount(r7, r3)
            if (r7 != r0) goto L8e
            return r0
        L8e:
            java.lang.String r7 = "cs updateTryCollectCount"
            com.epson.iprojection.common.Lg.d(r7)
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$onCollect$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
