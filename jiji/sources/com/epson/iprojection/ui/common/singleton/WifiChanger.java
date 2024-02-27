package com.epson.iprojection.ui.common.singleton;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import com.epson.iprojection.common.Lg;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WifiChanger.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u0006H\u0007J\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/WifiChanger;", "", "()V", "VERSION_CODES_FOR_TIMEOUT", "", "_networkCallback", "Landroid/net/ConnectivityManager$NetworkCallback;", "changeWifi", "", "context", "Landroid/content/Context;", "ssid", "", "passwordIn", "networkCallback", "restoreWifi", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WifiChanger {
    public static final WifiChanger INSTANCE = new WifiChanger();
    private static final int VERSION_CODES_FOR_TIMEOUT = 33;
    private static ConnectivityManager.NetworkCallback _networkCallback;

    private WifiChanger() {
    }

    public final void changeWifi(Context context, String ssid, String str, ConnectivityManager.NetworkCallback networkCallback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ssid, "ssid");
        Intrinsics.checkNotNullParameter(networkCallback, "networkCallback");
        _networkCallback = networkCallback;
        if (str == null) {
            str = "";
        }
        Object systemService = context.getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
        WifiNetworkSpecifier.Builder builder = new WifiNetworkSpecifier.Builder();
        builder.setSsid(ssid);
        if (str.length() > 0) {
            builder.setWpa2Passphrase(str);
        }
        NetworkRequest.Builder builder2 = new NetworkRequest.Builder();
        builder2.addTransportType(1);
        builder2.addCapability(14);
        builder2.setNetworkSpecifier(builder.build());
        if (Build.VERSION.SDK_INT == VERSION_CODES_FOR_TIMEOUT) {
            connectivityManager.requestNetwork(builder2.build(), networkCallback, 15000);
        } else {
            connectivityManager.requestNetwork(builder2.build(), networkCallback);
        }
    }

    public final void restoreWifi(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (_networkCallback == null) {
            return;
        }
        Object systemService = context.getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
        try {
            if (connectivityManager.getBoundNetworkForProcess() != null) {
                connectivityManager.bindProcessToNetwork(null);
            }
            ConnectivityManager.NetworkCallback networkCallback = _networkCallback;
            Intrinsics.checkNotNull(networkCallback);
            connectivityManager.unregisterNetworkCallback(networkCallback);
            _networkCallback = null;
        } catch (IllegalArgumentException e) {
            Lg.e("unregisterNetworkCallback fail:" + e);
        }
    }
}
