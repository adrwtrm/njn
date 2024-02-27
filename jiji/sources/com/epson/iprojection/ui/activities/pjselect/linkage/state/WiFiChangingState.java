package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import com.epson.iprojection.ui.common.application.IproApplication;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.common.singleton.WifiChanger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WiFiChangingState.kt */
@Metadata(d1 = {"\u0000)\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0006\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\tH\u0016J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\tJ\b\u0010\u000f\u001a\u00020\tH\u0016R\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/WiFiChangingState;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;)V", "networkCallback", "com/epson/iprojection/ui/activities/pjselect/linkage/state/WiFiChangingState$networkCallback$1", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/WiFiChangingState$networkCallback$1;", "onActivityResumed", "", "onActivityStopped", "onFailedInChangingWiFi", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "onWiFiChanged", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WiFiChangingState extends State {
    private final WiFiChangingState$networkCallback$1 networkCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.epson.iprojection.ui.activities.pjselect.linkage.state.WiFiChangingState$networkCallback$1] */
    public WiFiChangingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
        this.networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.epson.iprojection.ui.activities.pjselect.linkage.state.WiFiChangingState$networkCallback$1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                Intrinsics.checkNotNullParameter(network, "network");
                Lg.d("[QR] WiFi onAvailable()");
                Object systemService = WiFiChangingState.this.get_contextData().getContext().getSystemService("connectivity");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
                ((ConnectivityManager) systemService).bindProcessToNetwork(network);
                LinkageDataInfoStacker.getIns().setNetwork(network);
                WiFiChangingState.this.onWiFiChanged();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                Intrinsics.checkNotNullParameter(network, "network");
                super.onLost(network);
                Lg.w("[QR] WiFi onLost()");
                WiFiChangingState.this.onFailedInChangingWiFi(Define.ConnectFailedReason.WiFiChangeFailed);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                super.onUnavailable();
                Lg.w("[QR] WiFi onUnavailable()");
                WiFiChangingState.this.onFailedInChangingWiFi(Define.ConnectFailedReason.WiFiChangeFailed);
            }
        };
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.State
    public void run() {
        Lg.i("[QR] Wi-Fi切り替え開始。");
        get_contextData().getConnectListener().onConnectingProgressChanged(Define.ConnectingProgress.ChangingWiFi);
        WifiChanger wifiChanger = WifiChanger.INSTANCE;
        Context context = get_contextData().getContext();
        String str = get_contextData().getLinkageData().ssid;
        Intrinsics.checkNotNullExpressionValue(str, "_contextData.linkageData.ssid");
        wifiChanger.changeWifi(context, str, get_contextData().getLinkageData().password, this.networkCallback);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.State
    public void onActivityResumed() {
        Context context = get_contextData().getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
        Application application = ((Activity) context).getApplication();
        Intrinsics.checkNotNull(application, "null cannot be cast to non-null type com.epson.iprojection.ui.common.application.IproApplication");
        if (((IproApplication) application).mIsNFCEventHappenedDuringQRConnect) {
            onFailedInChangingWiFi(Define.ConnectFailedReason.NfcEventHappened);
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.State
    public void onActivityStopped() {
        onFailedInChangingWiFi(Define.ConnectFailedReason.ActivityStopped);
    }

    public final void onWiFiChanged() {
        Lg.i("[QR] Wi-Fi切り替え完了。");
        Lg.i("[QR]  ⇨ 無線検索状態へ");
        get_contextData().getContextListener().changeState(new WirelessSearchingState(get_contextData()));
    }

    public final void onFailedInChangingWiFi(Define.ConnectFailedReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Lg.i("[QR] Wi-Fi切り替え失敗。");
        Lg.i("[QR]  ⇨ 終了状態へ：wifiChangeError");
        get_contextData().getContextListener().onFinished(Define.FinishType.Failed, reason);
    }
}
