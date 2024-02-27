package com.epson.iprojection.customer_satisfaction.usecases.states;

import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CollectingState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState$onCollectSucceed$1", f = "CollectingState.kt", i = {}, l = {65, 66}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class CollectingState$onCollectSucceed$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CollectingState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectingState$onCollectSucceed$1(CollectingState collectingState, Continuation<? super CollectingState$onCollectSucceed$1> continuation) {
        super(2, continuation);
        this.this$0 = collectingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CollectingState$onCollectSucceed$1 collectingState$onCollectSucceed$1 = new CollectingState$onCollectSucceed$1(this.this$0, continuation);
        collectingState$onCollectSucceed$1.L$0 = obj;
        return collectingState$onCollectSucceed$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CollectingState$onCollectSucceed$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CSAssistanceInterface cSAssistanceInterface;
        CSStoreInterface cSStoreInterface;
        CSStoreInterface cSStoreInterface2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            cSAssistanceInterface = this.this$0.assistance;
            cSAssistanceInterface.feedbackAboutResponseSatisfaction(((CoroutineScope) this.L$0).getCoroutineContext());
            cSStoreInterface = this.this$0.repository;
            this.label = 1;
            if (cSStoreInterface.updateIsCollectedSatisfaction(true, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        cSStoreInterface2 = this.this$0.repository;
        LocalDate now = LocalDate.now();
        Intrinsics.checkNotNullExpressionValue(now, "now()");
        this.label = 2;
        if (cSStoreInterface2.updateCollectedLastDays(now, this) == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Unit.INSTANCE;
    }
}
