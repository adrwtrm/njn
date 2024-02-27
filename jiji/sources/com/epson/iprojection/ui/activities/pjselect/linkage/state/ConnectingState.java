package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnMinConnectListener;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConnectingState.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH$J\b\u0010\u000e\u001a\u00020\u000bH$J\b\u0010\u000f\u001a\u00020\u000bH\u0002J\u0018\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u000bH\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X¤\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ConnectingState;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/State;", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IOnMinConnectListener;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;)V", "iPAddressForConnect", "", "getIPAddressForConnect", "()Ljava/lang/String;", "changeStateOrFinishWhenConnectFailed", "", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "changeStateOrFinishWhenConnected", "connect", "onConnectionFailed", "projID", "", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IOnConnectListener$FailReason;", "onConnectionSucceeded", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class ConnectingState extends State implements IOnMinConnectListener {
    protected abstract void changeStateOrFinishWhenConnectFailed(Define.ConnectFailedReason connectFailedReason);

    protected abstract void changeStateOrFinishWhenConnected();

    protected abstract String getIPAddressForConnect();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.State
    public void run() {
        Lg.i("[QR] 接続開始。");
        get_contextData().getConnectListener().onConnectingProgressChanged(Define.ConnectingProgress.ConnectingToPj);
        get_contextData().getEngine().setOnMinConnectListener(this);
        connect();
    }

    private final void connect() {
        if (get_contextData().getLinkageData().isWhiteboard()) {
            Pj.getIns().setWhiteboardConnect(true);
        } else {
            Pj.getIns().setWhiteboardConnect(false);
        }
        ConnectPjInfo connectPjInfo = new ConnectPjInfo(get_contextData().getFoundPjInfo(), false);
        if (connectPjInfo.getPjInfo().isSupportConnectionIdentifier) {
            connectPjInfo.setKeyword(CommonDefine.SECRET_PJ_KEYWORD_NEW);
        } else {
            connectPjInfo.setKeyword(CommonDefine.SECRET_PJ_KEYWORD);
        }
        connectPjInfo.setNoInterrupt(ConnectConfig.isModeratorModeOn(get_contextData().getContext()));
        ArrayList<ConnectPjInfo> arrayList = new ArrayList<>();
        arrayList.add(connectPjInfo);
        get_contextData().getEngine().connect(arrayList, true);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnMinConnectListener
    public void onConnectionSucceeded() {
        Lg.i("[QR] 接続完了。");
        LinkageDataInfoStacker ins = LinkageDataInfoStacker.getIns();
        D_LinkageData linkageData = get_contextData().getLinkageData();
        D_PjInfo foundPjInfo = get_contextData().getFoundPjInfo();
        Intrinsics.checkNotNull(foundPjInfo);
        ins.set(linkageData, foundPjInfo.IPAddr);
        get_contextData().getEngine().clearOnMinConnectListener();
        changeStateOrFinishWhenConnected();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnMinConnectListener
    public void onConnectionFailed(int i, IOnConnectListener.FailReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Lg.i("[QR] 接続失敗。 reason:" + reason);
        get_contextData().getEngine().clearOnMinConnectListener();
        changeStateOrFinishWhenConnectFailed(Utils.INSTANCE.convertLinkageConnectFailedReason(reason));
    }
}
