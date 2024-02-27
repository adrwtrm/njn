package com.epson.iprojection.ui.activities.remote.commandsend.state;

import android.content.SharedPreferences;
import android.view.View;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.epson.iprojection.ui.activities.remote.commandsend.ContextData;
import com.epson.iprojection.ui.activities.remote.commandsend.Contract;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: DigestEscvpSendingState.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J \u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u0006H\u0002J\b\u0010\u000f\u001a\u00020\u0006H\u0016¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/state/DigestEscvpSendingState;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;)V", "goNextState", "", "savePassword", "password", "", "pjInfo", "Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "isApplyAllChecked", "", "showInputPasswordDialog", "start", "ImplIOnClickDialogButton", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DigestEscvpSendingState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DigestEscvpSendingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.remote.commandsend.state.State
    public void start() {
        Lg.d("[sd] DigestESCVP送信");
        D_HistoryInfo info = get_contextData().getPjList().get(get_contextData().getPjIndex()).getInfo();
        String password = get_contextData().getPjList().get(get_contextData().getPjIndex()).getPassword();
        Lg.d("[sd] シリアル番号で送ってみる");
        StringBuffer stringBuffer = new StringBuffer();
        IPj ipj = get_contextData().getIpj();
        byte[] bArr = info.ipAddr;
        Intrinsics.checkNotNullExpressionValue(bArr, "pjInfo.ipAddr");
        if (ipj.sendAndReceiveEscvpCommandWithIp(bArr, "SNO?", stringBuffer, true) != 0) {
            Lg.e("[sd] [エラー] シリアル番号の取得に失敗したので抜ける");
            goNextState();
            return;
        }
        IPj ipj2 = get_contextData().getIpj();
        String command = get_contextData().getCommand();
        byte[] bArr2 = info.ipAddr;
        Intrinsics.checkNotNullExpressionValue(bArr2, "pjInfo.ipAddr");
        String stringBuffer2 = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringBuffer2, "serialNo.toString()");
        if (ipj2.sendDigestEscvp(command, bArr2, stringBuffer2) != -441) {
            Lg.d("[sd] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            goNextState();
            return;
        }
        if (password.length() > 0) {
            Lg.d("[sd] 指定されたパスワードで送ってみる");
            IPj ipj3 = get_contextData().getIpj();
            String command2 = get_contextData().getCommand();
            byte[] bArr3 = info.ipAddr;
            Intrinsics.checkNotNullExpressionValue(bArr3, "pjInfo.ipAddr");
            if (ipj3.sendDigestEscvp(command2, bArr3, password) != -441) {
                Lg.d("[sd] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
                goNextState();
                return;
            }
        }
        if (get_contextData().getSavedPassword().length() > 0) {
            Lg.d("[sd] 「全てに適用」で保存したパスワードで送ってみる");
            IPj ipj4 = get_contextData().getIpj();
            String command3 = get_contextData().getCommand();
            byte[] bArr4 = info.ipAddr;
            Intrinsics.checkNotNullExpressionValue(bArr4, "pjInfo.ipAddr");
            int sendDigestEscvp = ipj4.sendDigestEscvp(command3, bArr4, get_contextData().getSavedPassword());
            if (sendDigestEscvp != -441) {
                if (sendDigestEscvp == 0) {
                    savePassword(get_contextData().getSavedPassword(), info, false);
                }
                Lg.d("[sd] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
                goNextState();
                return;
            }
        }
        Lg.d("[sd] 空白で送ってみる");
        IPj ipj5 = get_contextData().getIpj();
        String command4 = get_contextData().getCommand();
        byte[] bArr5 = info.ipAddr;
        Intrinsics.checkNotNullExpressionValue(bArr5, "pjInfo.ipAddr");
        if (ipj5.sendDigestEscvp(command4, bArr5, "") != -441) {
            Lg.d("[sd] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            goNextState();
            return;
        }
        Lg.d("[sd] adminで送ってみる");
        IPj ipj6 = get_contextData().getIpj();
        String command5 = get_contextData().getCommand();
        byte[] bArr6 = info.ipAddr;
        Intrinsics.checkNotNullExpressionValue(bArr6, "pjInfo.ipAddr");
        if (ipj6.sendDigestEscvp(command5, bArr6, "admin") != -441) {
            Lg.d("[sd] パスワード違いではない(通信不可エラー含む)ことが分かったからもう次へ行く");
            goNextState();
            return;
        }
        Lg.d("[sd] 全部だめだったので、入力させる。");
        showInputPasswordDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void goNextState() {
        Lg.d("[sd] StartStateに変更");
        get_contextData().getCallback().changeState(new StartState(get_contextData()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showInputPasswordDialog() {
        if (!get_contextData().getActivity().isFinishing()) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new DigestEscvpSendingState$showInputPasswordDialog$1(this, null), 2, null);
            return;
        }
        get_contextData().setCanceled(true);
        goNextState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void savePassword(String str, D_HistoryInfo d_HistoryInfo, boolean z) {
        Lg.d("[sd] パスワードを保存");
        PrefUtils.write(get_contextData().getActivity(), RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(d_HistoryInfo.macAddr), str, (SharedPreferences.Editor) null);
        if (z) {
            Lg.d("パスワード[" + str + "]を保存した。");
            get_contextData().setSavedPassword(str);
        }
    }

    /* compiled from: DigestEscvpSendingState.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/state/DigestEscvpSendingState$ImplIOnClickDialogButton;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IOnClickInputPasswordDialogButton;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/state/DigestEscvpSendingState;)V", "onClickCancel", "", "layout", "Landroid/view/View;", "onClickOk", "pjInfo", "Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ImplIOnClickDialogButton implements Contract.IOnClickInputPasswordDialogButton {
        public ImplIOnClickDialogButton() {
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IOnClickInputPasswordDialogButton
        public void onClickOk(View layout, D_HistoryInfo pjInfo) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            Intrinsics.checkNotNullParameter(pjInfo, "pjInfo");
            Lg.d("[sd] okボタン押下");
            boolean isApplyAllCheckedIn = DigestEscvpSendingState.this.get_contextData().getIview().isApplyAllCheckedIn(layout);
            String passwordFrom = DigestEscvpSendingState.this.get_contextData().getIview().getPasswordFrom(layout);
            PrefUtils.write(DigestEscvpSendingState.this.get_contextData().getActivity(), RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, isApplyAllCheckedIn);
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new DigestEscvpSendingState$ImplIOnClickDialogButton$onClickOk$1(DigestEscvpSendingState.this, pjInfo, passwordFrom, isApplyAllCheckedIn, null), 2, null);
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IOnClickInputPasswordDialogButton
        public void onClickCancel(View layout) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            Lg.d("[sd] キャンセルボタン押下したので抜ける");
            DigestEscvpSendingState.this.get_contextData().setCanceled(true);
            DigestEscvpSendingState.this.goNextState();
        }
    }
}
