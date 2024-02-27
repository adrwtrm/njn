package com.epson.iprojection.ui.activities.remote.commandsend.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.commandsend.ContextData;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OpenEscvpSendingState.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/state/OpenEscvpSendingState;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;)V", "start", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OpenEscvpSendingState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OpenEscvpSendingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.remote.commandsend.state.State
    public void start() {
        Lg.d("[sd] OpenESCVP送信");
        D_HistoryInfo info = get_contextData().getPjList().get(get_contextData().getPjIndex()).getInfo();
        IPj ipj = get_contextData().getIpj();
        String command = get_contextData().getCommand();
        byte[] bArr = info.ipAddr;
        Intrinsics.checkNotNullExpressionValue(bArr, "pjInfo.ipAddr");
        ipj.sendOpenEscvp(command, bArr);
        get_contextData().getCallback().changeState(new StartState(get_contextData()));
    }
}
