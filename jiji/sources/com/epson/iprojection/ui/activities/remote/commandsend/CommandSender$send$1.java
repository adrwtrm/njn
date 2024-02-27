package com.epson.iprojection.ui.activities.remote.commandsend;

import android.app.Activity;
import com.epson.iprojection.ui.activities.remote.commandsend.CommandSender;
import com.epson.iprojection.ui.activities.remote.commandsend.state.StartState;
import com.epson.iprojection.ui.activities.remote.commandsend.state.State;
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
/* compiled from: CommandSender.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.remote.commandsend.CommandSender$send$1", f = "CommandSender.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class CommandSender$send$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Activity $activity;
    final /* synthetic */ String $command;
    final /* synthetic */ IPj $ipj;
    final /* synthetic */ boolean $isEscvpOnly;
    final /* synthetic */ ArrayList<D_SendCommand> $pjList;
    int label;
    final /* synthetic */ CommandSender this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommandSender$send$1(CommandSender commandSender, Activity activity, ArrayList<D_SendCommand> arrayList, IPj iPj, boolean z, String str, Continuation<? super CommandSender$send$1> continuation) {
        super(2, continuation);
        this.this$0 = commandSender;
        this.$activity = activity;
        this.$pjList = arrayList;
        this.$ipj = iPj;
        this.$isEscvpOnly = z;
        this.$command = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CommandSender$send$1(this.this$0, this.$activity, this.$pjList, this.$ipj, this.$isEscvpOnly, this.$command, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CommandSender$send$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0._state = new StartState(new ContextData(this.$activity, this.$pjList, this.$ipj, new CommandSender.ICallbackImpl(), new CommandSender.IViewImpl(), this.$isEscvpOnly, this.$command));
        State state = this.this$0._state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_state");
            state = null;
        }
        state.start();
        return Unit.INSTANCE;
    }
}
