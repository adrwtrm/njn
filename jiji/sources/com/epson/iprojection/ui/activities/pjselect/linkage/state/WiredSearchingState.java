package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WiredSearchingState.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u000eH\u0014R\u0014\u0010\u0005\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/WiredSearchingState;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/SearchingState;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;)V", "iPAddressForSearch", "", "getIPAddressForSearch", "()Ljava/lang/String;", "equalsIpAddress", "", "foundPjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "onFound", "", "onNotFound", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WiredSearchingState extends SearchingState {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WiredSearchingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.SearchingState
    protected String getIPAddressForSearch() {
        String cvtIPAddr = NetUtils.cvtIPAddr(get_contextData().getLinkageData().wiredIP);
        Intrinsics.checkNotNullExpressionValue(cvtIPAddr, "cvtIPAddr(_contextData.linkageData.wiredIP)");
        return cvtIPAddr;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.SearchingState
    protected void onFound() {
        Lg.i("[QR] ⇨ 有線接続中状態へ");
        get_contextData().getContextListener().changeState(new WiredConnectingState(get_contextData()));
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.SearchingState
    protected void onNotFound() {
        if (get_contextData().getLinkageData().isAvailableWireless) {
            if (get_contextData().getLinkageData().isEasyConnect) {
                Lg.i("[QR] ⇨ Wi-Fi切り替え中状態へ");
                get_contextData().getContextListener().changeState(new WiFiChangingState(get_contextData()));
                return;
            }
            Lg.i("[QR] ⇨ 無線検索中状態へ");
            get_contextData().getContextListener().changeState(new WirelessSearchingState(get_contextData()));
            return;
        }
        Lg.i("[QR] ⇨ 終了状態へ：searchError");
        get_contextData().getContextListener().onFinished(Define.FinishType.Failed, Define.ConnectFailedReason.SearchFailed);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.SearchingState
    protected boolean equalsIpAddress(D_PjInfo foundPjInfo) {
        Intrinsics.checkNotNullParameter(foundPjInfo, "foundPjInfo");
        Utils utils = Utils.INSTANCE;
        byte[] bArr = foundPjInfo.IPAddr;
        Intrinsics.checkNotNullExpressionValue(bArr, "foundPjInfo.IPAddr");
        int[] iArr = get_contextData().getLinkageData().wiredIP;
        Intrinsics.checkNotNullExpressionValue(iArr, "_contextData.linkageData.wiredIP");
        return utils.equals(bArr, iArr);
    }
}
