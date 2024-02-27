package com.epson.iprojection.ui.activities.remote.commandsend.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.remote.commandsend.ContextData;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: U2USendingState.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/state/U2USendingState;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;)V", "findProjectorID", "", "macaddr", "", "([B)Ljava/lang/Integer;", "start", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class U2USendingState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public U2USendingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.remote.commandsend.state.State
    public void start() {
        Lg.d("[sd] U2Uコマンドでネットワークスタンバイオンを送信");
        byte[] bArr = get_contextData().getPjList().get(get_contextData().getPjIndex()).getInfo().macAddr;
        Intrinsics.checkNotNullExpressionValue(bArr, "pjInfo.macAddr");
        Integer findProjectorID = findProjectorID(bArr);
        if (findProjectorID == null) {
            Lg.e("projector IDが見つからない！");
            get_contextData().getCallback().changeState(new StartState(get_contextData()));
            return;
        }
        get_contextData().getIpj().sendU2UCommandKeyEmulation(get_contextData().getCommand(), findProjectorID.intValue());
        get_contextData().getCallback().changeState(new StartState(get_contextData()));
    }

    private final Integer findProjectorID(byte[] bArr) {
        Iterator<ConnectPjInfo> it = Pj.getIns().getNowConnectingPJList().iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (Arrays.equals(next.getPjInfo().UniqInfo, bArr)) {
                return Integer.valueOf(next.getPjInfo().ProjectorID);
            }
        }
        return null;
    }
}
