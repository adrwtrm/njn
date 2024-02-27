package com.epson.iprojection.ui.activities.pjselect.networkstandby.state;

import android.view.View;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.state.DigestEscvpSendingState;
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
/* compiled from: DigestEscvpSendingState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.pjselect.networkstandby.state.DigestEscvpSendingState$showInputPasswordDialog$1", f = "DigestEscvpSendingState.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class DigestEscvpSendingState$showInputPasswordDialog$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ DigestEscvpSendingState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DigestEscvpSendingState$showInputPasswordDialog$1(DigestEscvpSendingState digestEscvpSendingState, Continuation<? super DigestEscvpSendingState$showInputPasswordDialog$1> continuation) {
        super(2, continuation);
        this.this$0 = digestEscvpSendingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DigestEscvpSendingState$showInputPasswordDialog$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DigestEscvpSendingState$showInputPasswordDialog$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        D_PjInfo pjInfo = this.this$0.get_contextData().getPjList().get(this.this$0.get_contextData().getPjIndex()).getPjInfo();
        Contract.IView iview = this.this$0.get_contextData().getIview();
        String str = pjInfo.PrjName;
        Intrinsics.checkNotNullExpressionValue(str, "pjInfo.PrjName");
        View createLayoutInPasswordInputDialog = iview.createLayoutInPasswordInputDialog(str, this.this$0.get_contextData().getPjList().size() == 1);
        Contract.IView iview2 = this.this$0.get_contextData().getIview();
        Intrinsics.checkNotNullExpressionValue(pjInfo, "pjInfo");
        iview2.createPasswordInputDialogFrom(createLayoutInPasswordInputDialog, pjInfo, new DigestEscvpSendingState.ImplIOnClickDialogButton());
        return Unit.INSTANCE;
    }
}
