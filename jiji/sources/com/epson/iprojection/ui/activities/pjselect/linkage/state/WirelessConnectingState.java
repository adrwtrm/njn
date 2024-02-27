package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WirelessConnectingState.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\nH\u0014R\u0014\u0010\u0005\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/WirelessConnectingState;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ConnectingState;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;)V", "iPAddressForConnect", "", "getIPAddressForConnect", "()Ljava/lang/String;", "changeStateOrFinishWhenConnectFailed", "", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "changeStateOrFinishWhenConnected", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WirelessConnectingState extends ConnectingState {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WirelessConnectingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.ConnectingState
    protected String getIPAddressForConnect() {
        String cvtIPAddr = NetUtils.cvtIPAddr(get_contextData().getLinkageData().wiredIP);
        Intrinsics.checkNotNullExpressionValue(cvtIPAddr, "cvtIPAddr(_contextData.linkageData.wiredIP)");
        return cvtIPAddr;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.ConnectingState
    protected void changeStateOrFinishWhenConnected() {
        Lg.i("[QR] ⇨ 終了状態へ：成功");
        get_contextData().getContextListener().onFinished(Define.FinishType.Succeeded, Define.ConnectFailedReason.None);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.ConnectingState
    protected void changeStateOrFinishWhenConnectFailed(Define.ConnectFailedReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Lg.i("[QR] ⇨ 終了状態へ：connectError");
        get_contextData().getContextListener().onFinished(Define.FinishType.Failed, reason);
    }
}
