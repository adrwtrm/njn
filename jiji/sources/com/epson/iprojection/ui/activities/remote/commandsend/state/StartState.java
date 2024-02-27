package com.epson.iprojection.ui.activities.remote.commandsend.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.commandsend.ContextData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StartState.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/state/StartState;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;)V", "start", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class StartState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StartState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.remote.commandsend.state.State
    public void start() {
        ContextData contextData = get_contextData();
        contextData.setPjIndex(contextData.getPjIndex() + 1);
        Lg.d("[sd] StartState::Start() pjIndex = " + get_contextData().getPjIndex());
        if (get_contextData().isCanceled()) {
            Lg.d("[sd] キャンセルされているので終了");
            get_contextData().getCallback().onFinish();
            return;
        }
        int pjIndex = get_contextData().getPjIndex();
        int size = get_contextData().getPjList().size();
        Lg.d("[sd] " + pjIndex + " 台目の処理");
        if (pjIndex >= size) {
            Lg.d("[sd] 全ての台数を処理し終わったので終了");
            get_contextData().getCallback().onFinish();
            return;
        }
        Lg.d("[sd] 処理対象：" + get_contextData().getPjList().get(pjIndex).getInfo().pjName);
        D_HistoryInfo info = get_contextData().getPjList().get(pjIndex).getInfo();
        if (get_contextData().isEscvpOnly()) {
            Lg.d("[sd] ESCVP送信OnlyのためESCVPで送信");
            if (info.isSupportedSecure) {
                Lg.d("[sd] セキュア対応機なので、Digest認証付きEscvpコマンドで送る。Stateを「DigestEscvpSendingState」へ変更");
                get_contextData().getCallback().changeState(new DigestEscvpSendingState(get_contextData()));
                return;
            }
            Lg.d("[sd] セキュア対応機じゃないので従来の方法で送る。Stateを「OpenEscvpSendingState」へ変更");
            get_contextData().getCallback().changeState(new OpenEscvpSendingState(get_contextData()));
        } else if (info.isSupportedSecure) {
            Lg.d("[sd] セキュア対応機");
            if (get_contextData().getIpj().isConnected()) {
                Lg.d("[sd] 接続中なのでU2Uコマンドで送る。Stateを「U2USendingState」へ変更");
                get_contextData().getCallback().changeState(new U2USendingState(get_contextData()));
                return;
            }
            Lg.d("[sd] 接続中ではないので、Digest認証付きEscvpコマンドで送る。Stateを「DigestEscvpSendingState」へ変更");
            get_contextData().getCallback().changeState(new DigestEscvpSendingState(get_contextData()));
        } else {
            Lg.d("[sd] セキュア対応機じゃないので従来の方法で送る。Stateを「OpenEscvpSendingState」へ変更");
            get_contextData().getCallback().changeState(new OpenEscvpSendingState(get_contextData()));
        }
    }
}
