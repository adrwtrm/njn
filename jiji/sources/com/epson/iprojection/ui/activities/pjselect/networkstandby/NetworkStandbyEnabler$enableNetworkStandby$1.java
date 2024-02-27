package com.epson.iprojection.ui.activities.pjselect.networkstandby;

import android.content.Context;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.NetworkStandbyEnabler;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.state.StartState;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.state.State;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import java.util.ArrayList;
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

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NetworkStandbyEnabler.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.pjselect.networkstandby.NetworkStandbyEnabler$enableNetworkStandby$1", f = "NetworkStandbyEnabler.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class NetworkStandbyEnabler$enableNetworkStandby$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ NetworkStandbyEnabler this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkStandbyEnabler$enableNetworkStandby$1(NetworkStandbyEnabler networkStandbyEnabler, Continuation<? super NetworkStandbyEnabler$enableNetworkStandby$1> continuation) {
        super(2, continuation);
        this.this$0 = networkStandbyEnabler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NetworkStandbyEnabler$enableNetworkStandby$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NetworkStandbyEnabler$enableNetworkStandby$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ArrayList arrayList;
        IPj iPj;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NetworkStandbyEnabler networkStandbyEnabler = this.this$0;
        Context context = this.this$0._context;
        arrayList = this.this$0.pjList;
        iPj = this.this$0._ipj;
        networkStandbyEnabler._state = new StartState(new ContextData(context, arrayList, iPj, new NetworkStandbyEnabler.ICallbackImpl(), new NetworkStandbyEnabler.IViewImpl()));
        State state = this.this$0._state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_state");
            state = null;
        }
        state.start();
        return Unit.INSTANCE;
    }
}
