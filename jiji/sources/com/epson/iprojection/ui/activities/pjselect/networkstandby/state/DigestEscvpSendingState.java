package com.epson.iprojection.ui.activities.pjselect.networkstandby.state;

import android.content.SharedPreferences;
import android.view.View;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.EscvpUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.ContextData;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: DigestEscvpSendingState.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J \u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u0006H\u0002J\b\u0010\u000f\u001a\u00020\u0006H\u0016¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/DigestEscvpSendingState;", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;)V", "goNextState", "", "savePassword", "password", "", "pjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "isApplyAllChecked", "", "showInputPasswordDialog", "start", "ImplIOnClickDialogButton", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DigestEscvpSendingState extends State {
    /* renamed from: $r8$lambda$Hm80wnqA2Y2jq-AebrW89Ss82Cs */
    public static /* synthetic */ void m149$r8$lambda$Hm80wnqA2Y2jqAebrW89Ss82Cs(DigestEscvpSendingState digestEscvpSendingState) {
        goNextState$lambda$0(digestEscvpSendingState);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DigestEscvpSendingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.state.State
    public void start() {
        Lg.d("[nw] DigestESCVP送信");
        D_PjInfo pjInfo = get_contextData().getPjList().get(get_contextData().getPjIndex()).getPjInfo();
        Lg.d("[nw] シリアル番号で送ってみる");
        StringBuffer stringBuffer = new StringBuffer();
        IPj ipj = get_contextData().getIpj();
        byte[] bArr = pjInfo.IPAddr;
        Intrinsics.checkNotNullExpressionValue(bArr, "pjInfo.IPAddr");
        if (ipj.sendAndReceiveEscvpCommandWithIp(bArr, "SNO?", stringBuffer, true) != 0) {
            Lg.e("[nw] [エラー] シリアル番号の取得に失敗したので抜ける");
            goNextState();
            return;
        }
        EscvpUtils.Companion companion = EscvpUtils.Companion;
        Intrinsics.checkNotNullExpressionValue(pjInfo, "pjInfo");
        String stringBuffer2 = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringBuffer2, "serialNo.toString()");
        if (companion.sendSecureEscvpOfNetworkStandbyOnAndScomport(pjInfo, stringBuffer2) != -441) {
            Lg.d("[nw] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            goNextState();
            return;
        }
        Lg.d("[nw] オンに失敗。恐らくパスワードが違う");
        if (get_contextData().getSavedPassword().length() > 0) {
            Lg.d("[nw] 保存済みパスワードで送ってみる");
            int sendSecureEscvpOfNetworkStandbyOnAndScomport = EscvpUtils.Companion.sendSecureEscvpOfNetworkStandbyOnAndScomport(pjInfo, get_contextData().getSavedPassword());
            if (sendSecureEscvpOfNetworkStandbyOnAndScomport != -441) {
                if (sendSecureEscvpOfNetworkStandbyOnAndScomport == 0) {
                    String savedPassword = get_contextData().getSavedPassword();
                    D_PjInfo pjInfo2 = get_contextData().getPjList().get(get_contextData().getPjIndex()).getPjInfo();
                    Intrinsics.checkNotNullExpressionValue(pjInfo2, "_contextData.pjList[_contextData.pjIndex].pjInfo");
                    savePassword(savedPassword, pjInfo2, false);
                }
                goNextState();
                return;
            }
        }
        Lg.d("[nw] オンに失敗。恐らくパスワードが違う");
        Lg.d("[nw] 空文字で送ってみる");
        if (EscvpUtils.Companion.sendSecureEscvpOfNetworkStandbyOnAndScomport(pjInfo, "") != -441) {
            Lg.d("[nw] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            goNextState();
            return;
        }
        Lg.d("[nw] オンに失敗。恐らくパスワードが違う");
        Lg.d("[nw] adminで送ってみる");
        if (EscvpUtils.Companion.sendSecureEscvpOfNetworkStandbyOnAndScomport(pjInfo, "admin") != -441) {
            Lg.d("[nw] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            goNextState();
            return;
        }
        Lg.d("[nw] オンに失敗。恐らくパスワードが違う");
        Lg.d("[nw] 全部だめだったので、入力させる。");
        showInputPasswordDialog();
    }

    public final void goNextState() {
        Lg.d("[nw] StartStateに変更");
        get_handler().post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.networkstandby.state.DigestEscvpSendingState$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DigestEscvpSendingState.m149$r8$lambda$Hm80wnqA2Y2jqAebrW89Ss82Cs(DigestEscvpSendingState.this);
            }
        });
    }

    public static final void goNextState$lambda$0(DigestEscvpSendingState this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.get_contextData().getCallback().changeState(new StartState(this$0.get_contextData()));
    }

    public final void showInputPasswordDialog() {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new DigestEscvpSendingState$showInputPasswordDialog$1(this, null), 2, null);
    }

    public final void savePassword(String str, D_PjInfo d_PjInfo, boolean z) {
        Lg.d("[nw] パスワードを保存");
        PrefUtils.write(get_contextData().getContext(), RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(d_PjInfo.UniqInfo), str, (SharedPreferences.Editor) null);
        if (z) {
            get_contextData().setSavedPassword(str);
        }
    }

    /* compiled from: DigestEscvpSendingState.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/DigestEscvpSendingState$ImplIOnClickDialogButton;", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnClickInputPasswordDialogButton;", "(Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/DigestEscvpSendingState;)V", "onClickCancel", "", "layout", "Landroid/view/View;", "onClickOk", "pjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ImplIOnClickDialogButton implements Contract.IOnClickInputPasswordDialogButton {
        public ImplIOnClickDialogButton() {
            DigestEscvpSendingState.this = r1;
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IOnClickInputPasswordDialogButton
        public void onClickOk(View layout, D_PjInfo pjInfo) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            Intrinsics.checkNotNullParameter(pjInfo, "pjInfo");
            Lg.d("[nw] okボタン押下");
            boolean isApplyAllCheckedIn = DigestEscvpSendingState.this.get_contextData().getIview().isApplyAllCheckedIn(layout);
            String passwordFrom = DigestEscvpSendingState.this.get_contextData().getIview().getPasswordFrom(layout);
            PrefUtils.write(DigestEscvpSendingState.this.get_contextData().getContext(), RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, isApplyAllCheckedIn);
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1(pjInfo, passwordFrom, DigestEscvpSendingState.this, isApplyAllCheckedIn, null), 2, null);
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IOnClickInputPasswordDialogButton
        public void onClickCancel(View layout) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            Lg.d("[nw] キャンセルボタン押下したので抜ける");
            DigestEscvpSendingState.this.get_contextData().setCanceled(true);
            DigestEscvpSendingState.this.goNextState();
        }
    }
}
