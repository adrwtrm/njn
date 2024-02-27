package com.epson.iprojection.ui.activities.pjselect.networkstandby.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.ContextData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: StartState.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/StartState;", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;)V", "start", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class StartState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StartState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.state.State
    public void start() {
        ContextData contextData = get_contextData();
        contextData.setPjIndex(contextData.getPjIndex() + 1);
        Lg.d("[nw] StartState::Start() pjIndex = " + get_contextData().getPjIndex());
        if (get_contextData().isCanceled()) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new StartState$start$1(this, null), 2, null);
            Lg.d("[nw] キャンセルされているので終了");
            get_contextData().getCallback().onFinish();
            return;
        }
        if (get_contextData().getPjIndex() == 0) {
            Lg.d("[nw] StartStateに入るのが初回なので、プログレスダイアログを表示する");
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new StartState$start$2(this, null), 2, null);
        }
        while (true) {
            int pjIndex = get_contextData().getPjIndex();
            int size = get_contextData().getPjList().size();
            Lg.d("[nw] " + pjIndex + " 台目の処理");
            if (pjIndex >= size) {
                Lg.d("[nw] 全ての台数を処理し終わったので終了");
                BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new StartState$start$3(this, null), 2, null);
                get_contextData().getCallback().onFinish();
                return;
            }
            Lg.d("[nw] 処理対象：" + get_contextData().getPjList().get(pjIndex).getPjInfo().PrjName);
            D_PjInfo pjInfo = get_contextData().getPjList().get(pjIndex).getPjInfo();
            if (pjInfo.isStandbySetting) {
                Lg.d("[nw] 既にネットワークスタンバイオンなので何もしない");
                ContextData contextData2 = get_contextData();
                contextData2.setPjIndex(contextData2.getPjIndex() + 1);
            } else {
                Lg.d("[nw] ネットワークスタンバイオフなので、オンの処理を実行");
                if (pjInfo.isSupportedSecuredEscvp) {
                    Lg.d("[nw] セキュア対応機");
                    if (get_contextData().getIpj().isConnected()) {
                        Lg.d("[nw] 接続中なのでU2Uコマンドで送る。Stateを「U2USendingState」へ変更");
                        get_contextData().getCallback().changeState(new U2USendingState(get_contextData()));
                        return;
                    }
                    Lg.d("[nw] 接続中ではないので、Digest認証付きEscvpコマンドで送る。Stateを「DigestEscvpSendingState」へ変更");
                    get_contextData().getCallback().changeState(new DigestEscvpSendingState(get_contextData()));
                    return;
                }
                Lg.d("[nw] セキュア対応機じゃないので従来の方法で送る。Stateを「OpenEscvpSendingState」へ変更");
                get_contextData().getCallback().changeState(new OpenEscvpSendingState(get_contextData()));
                return;
            }
        }
    }
}
