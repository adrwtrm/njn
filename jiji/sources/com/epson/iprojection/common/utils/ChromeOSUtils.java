package com.epson.iprojection.common.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChromeOSUtils.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0007J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\bR\u0014\u0010\u0003\u001a\b\u0018\u00010\u0004R\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/common/utils/ChromeOSUtils;", "", "()V", "_multicastLock", "Landroid/net/wifi/WifiManager$MulticastLock;", "Landroid/net/wifi/WifiManager;", "_wifiManager", "isChromeOS", "", "context", "Landroid/content/Context;", "setMulticastLock", "", "isEnabled", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ChromeOSUtils {
    public static final ChromeOSUtils INSTANCE = new ChromeOSUtils();
    private static WifiManager.MulticastLock _multicastLock;
    private static WifiManager _wifiManager;

    private ChromeOSUtils() {
    }

    @JvmStatic
    public static final synchronized boolean isChromeOS() {
        boolean isChromeOS;
        synchronized (ChromeOSUtils.class) {
            ChromeOSUtils chromeOSUtils = INSTANCE;
            Context context = Pj.getIns().getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getIns().context");
            isChromeOS = chromeOSUtils.isChromeOS(context);
        }
        return isChromeOS;
    }

    public final boolean isChromeOS(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getPackageManager().hasSystemFeature("org.chromium.arc.device_management");
    }

    public final void setMulticastLock(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            if (_wifiManager == null) {
                Object systemService = context.getSystemService("wifi");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.wifi.WifiManager");
                _wifiManager = (WifiManager) systemService;
            }
            if (_multicastLock == null) {
                WifiManager wifiManager = _wifiManager;
                Intrinsics.checkNotNull(wifiManager);
                _multicastLock = wifiManager.createMulticastLock(context.getClass().getName());
            }
            if (z) {
                WifiManager.MulticastLock multicastLock = _multicastLock;
                Intrinsics.checkNotNull(multicastLock);
                if (multicastLock.isHeld()) {
                    return;
                }
                Lg.d("setMulticastLock enable");
                WifiManager.MulticastLock multicastLock2 = _multicastLock;
                Intrinsics.checkNotNull(multicastLock2);
                multicastLock2.acquire();
                return;
            }
            WifiManager.MulticastLock multicastLock3 = _multicastLock;
            Intrinsics.checkNotNull(multicastLock3);
            if (multicastLock3.isHeld()) {
                Lg.d("setMulticastLock release");
                WifiManager.MulticastLock multicastLock4 = _multicastLock;
                Intrinsics.checkNotNull(multicastLock4);
                multicastLock4.release();
            }
        } catch (Exception e) {
            Lg.e("e = " + e);
        }
    }
}
