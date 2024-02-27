package com.epson.iprojection.ui.activities.pjselect.networkstandby.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.EscvpUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: DigestEscvpSendingState.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.pjselect.networkstandby.state.DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1", f = "DigestEscvpSendingState.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $isApplyAllChecked;
    final /* synthetic */ String $password;
    final /* synthetic */ D_PjInfo $pjInfo;
    int label;
    final /* synthetic */ DigestEscvpSendingState this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1(D_PjInfo d_PjInfo, String str, DigestEscvpSendingState digestEscvpSendingState, boolean z, Continuation<? super DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1> continuation) {
        super(2, continuation);
        this.$pjInfo = d_PjInfo;
        this.$password = str;
        this.this$0 = digestEscvpSendingState;
        this.$isApplyAllChecked = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1(this.$pjInfo, this.$password, this.this$0, this.$isApplyAllChecked, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Lg.d("[nw] ダイジェスト認証付きESCVPコマンド送信");
        int sendSecureEscvpOfNetworkStandbyOnAndScomport = EscvpUtils.Companion.sendSecureEscvpOfNetworkStandbyOnAndScomport(this.$pjInfo, this.$password);
        if (sendSecureEscvpOfNetworkStandbyOnAndScomport == -441) {
            Lg.d("[nw] パスワードの認証が通らなかったので、もう一度入力させる");
            this.this$0.showInputPasswordDialog();
        } else {
            Lg.d("[nw] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            if (!this.$isApplyAllChecked) {
                Lg.d("全てに適用チェックが入っていなかったのでパスワードクリア");
                this.this$0.get_contextData().setSavedPassword("");
            }
            if (sendSecureEscvpOfNetworkStandbyOnAndScomport == 0) {
                this.this$0.savePassword(this.$password, this.$pjInfo, this.$isApplyAllChecked);
            }
            this.this$0.goNextState();
        }
        return Unit.INSTANCE;
    }
}
