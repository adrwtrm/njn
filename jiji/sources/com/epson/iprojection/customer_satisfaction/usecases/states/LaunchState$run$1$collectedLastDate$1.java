package com.epson.iprojection.customer_satisfaction.usecases.states;

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
import kotlinx.coroutines.CoroutineScope;

/* compiled from: LaunchState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Ljava/time/LocalDate;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.usecases.states.LaunchState$run$1$collectedLastDate$1", f = "LaunchState.kt", i = {}, l = {40}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class LaunchState$run$1$collectedLastDate$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super LocalDate>, Object> {
    int label;
    final /* synthetic */ LaunchState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LaunchState$run$1$collectedLastDate$1(LaunchState launchState, Continuation<? super LaunchState$run$1$collectedLastDate$1> continuation) {
        super(2, continuation);
        this.this$0 = launchState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LaunchState$run$1$collectedLastDate$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super LocalDate> continuation) {
        return ((LaunchState$run$1$collectedLastDate$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
            obj = cSStoreInterface.getCollectedLastDate(this);
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