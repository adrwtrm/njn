package com.epson.iprojection.customer_satisfaction.usecases.states;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.customer_satisfaction.entities.CSLogic;
import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
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

/* compiled from: CollectingState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$run$1", f = "CollectingState.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class CollectingState$run$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CollectingState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectingState$run$1(CollectingState collectingState, Continuation<? super CollectingState$run$1> continuation) {
        super(2, continuation);
        this.this$0 = collectingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CollectingState$run$1 collectingState$run$1 = new CollectingState$run$1(this.this$0, continuation);
        collectingState$run$1.L$0 = obj;
        return collectingState$run$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CollectingState$run$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object runBlocking$default;
        Object runBlocking$default2;
        int i;
        CSAnalyticsInterface cSAnalyticsInterface;
        SatisfactionCollectInterface satisfactionCollectInterface;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        Lg.d("cs getUsedCount");
        CollectingState collectingState = this.this$0;
        runBlocking$default = BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(this.this$0, null), 1, null);
        collectingState._usedCount = ((Number) runBlocking$default).intValue();
        runBlocking$default2 = BuildersKt__BuildersKt.runBlocking$default(null, new CollectingState$run$1$isCollectedSatisfaction$1(this.this$0, null), 1, null);
        boolean booleanValue = ((Boolean) runBlocking$default2).booleanValue();
        CSLogic cSLogic = CSLogic.INSTANCE;
        i = this.this$0._usedCount;
        cSAnalyticsInterface = this.this$0.analytics;
        if (cSLogic.shouldCollectSatisfaction(booleanValue, i, cSAnalyticsInterface.isAnalyticsEnable())) {
            satisfactionCollectInterface = this.this$0.satisfactionPresenter;
            satisfactionCollectInterface.collectSatisfaction(coroutineScope.getCoroutineContext());
            this.this$0.onCollect();
        } else {
            this.this$0.runStoreReview();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CollectingState.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
    @DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$run$1$1", f = "CollectingState.kt", i = {}, l = {41}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$run$1$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Integer>, Object> {
        int label;
        final /* synthetic */ CollectingState this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(CollectingState collectingState, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = collectingState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Integer> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CSStoreInterface cSStoreInterface;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                cSStoreInterface = this.this$0.repository;
                this.label = 1;
                obj = cSStoreInterface.getUsedCount(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            } else {
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }
}
