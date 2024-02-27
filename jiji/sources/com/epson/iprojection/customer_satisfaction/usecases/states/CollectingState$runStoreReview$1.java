package com.epson.iprojection.customer_satisfaction.usecases.states;

import com.epson.iprojection.customer_satisfaction.entities.CSLogic;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CollectingState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$runStoreReview$1", f = "CollectingState.kt", i = {}, l = {112}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class CollectingState$runStoreReview$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CollectingState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectingState$runStoreReview$1(CollectingState collectingState, Continuation<? super CollectingState$runStoreReview$1> continuation) {
        super(2, continuation);
        this.this$0 = collectingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CollectingState$runStoreReview$1 collectingState$runStoreReview$1 = new CollectingState$runStoreReview$1(this.this$0, continuation);
        collectingState$runStoreReview$1.L$0 = obj;
        return collectingState$runStoreReview$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CollectingState$runStoreReview$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object runBlocking$default;
        int i;
        StoreReviewCollectInterface storeReviewCollectInterface;
        CSStoreInterface cSStoreInterface;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            runBlocking$default = BuildersKt__BuildersKt.runBlocking$default(null, new CollectingState$runStoreReview$1$isCollectedStoreReview$1(this.this$0, null), 1, null);
            boolean booleanValue = ((Boolean) runBlocking$default).booleanValue();
            CSLogic cSLogic = CSLogic.INSTANCE;
            i = this.this$0._usedCount;
            if (cSLogic.shouldCollectStoreReview(booleanValue, i)) {
                storeReviewCollectInterface = this.this$0.storeReviewPresenter;
                storeReviewCollectInterface.collectStoreReviews(coroutineScope.getCoroutineContext());
                cSStoreInterface = this.this$0.repository;
                this.label = 1;
                if (cSStoreInterface.updateIsCollectedStoreReview(true, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        } else if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
