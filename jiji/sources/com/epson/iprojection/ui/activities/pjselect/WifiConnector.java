package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.StopWatch;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public class WifiConnector {
    private static final int EASY_CONNECT_WAIT_TIME = 5000;
    private static final int MAX_PRIORITY = 999999;
    private static final int SCAN_INTERVAL = 3000;
    private static final int TRY_EXIST_WAIT_TIME = 5000;
    private static final StopWatch _scanTime = new StopWatch();
    private static boolean sIsReverting = false;
    Context _context;
    private final D_LinkageData _data;
    public boolean _isSuccessfulChangeWifiProfile = true;
    private boolean _prevWifiEnabled = true;
    private String _prevSSID = null;
    private boolean _wifiChanged = false;
    private boolean _isStop = false;
    private final StopWatch _sw = new StopWatch();
    private boolean _isFirstConnect = false;
    private TryState _tryExistState = TryState.NONE;
    private int _skipTime = 0;
    private int _skipTimeEasyConnect = 0;

    /* loaded from: classes.dex */
    public enum TryState {
        NONE,
        TRY_EXIST_1,
        TRY_EXIST_2
    }

    public void enableAllNetwork() {
    }

    public WifiConnector(Context context, D_LinkageData d_LinkageData) {
        this._context = context;
        this._data = d_LinkageData;
    }

    private void initInfo(boolean z) {
        this._prevWifiEnabled = true;
        this._prevSSID = null;
        this._wifiChanged = false;
        this._isFirstConnect = false;
        this._tryExistState = TryState.NONE;
        this._skipTime = 0;
        if (z) {
            sIsReverting = false;
        }
    }

    public void setIsWifiChanged(Boolean bool) {
        this._wifiChanged = bool.booleanValue();
    }

    public synchronized boolean connect() {
        this._isSuccessfulChangeWifiProfile = true;
        Lg.i("connect");
        initInfo(false);
        if (sIsReverting) {
            Lg.i("connect遅延");
            this._isFirstConnect = true;
            return true;
        }
        return connect(false);
    }

    public synchronized boolean reconnect() {
        this._isSuccessfulChangeWifiProfile = true;
        Lg.i("reconnect");
        if (this._isFirstConnect) {
            if (sIsReverting) {
                Lg.i("connect再遅延");
                return true;
            }
            this._isFirstConnect = false;
            return connect(false);
        }
        return connect(true);
    }

    public String getTryingConnectSSID() {
        D_LinkageData d_LinkageData = this._data;
        return d_LinkageData != null ? d_LinkageData.ssid : "";
    }

    private synchronized boolean connect(boolean z) {
        WifiConfiguration wifiConfiguration;
        Lg.i("connect -- start");
        if (this._isStop) {
            return true;
        }
        WifiManager wifiManager = (WifiManager) this._context.getSystemService("wifi");
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        Lg.i("[SSID] : " + (connectionInfo.getSSID() != null ? connectionInfo.getSSID() : "null") + ", [info] : " + connectionInfo);
        if (!isSameSSID(this._data.ssid, connectionInfo.getSSID()) || isEmptyBSSID(connectionInfo)) {
            if (!enableWifi(wifiManager, z)) {
                Lg.e("Wi-Fi有効失敗");
                return false;
            }
            if (!isFindSsidInScanResult(this._data.ssid)) {
                this._isFirstConnect = true;
                if (!isEmptyScanResult()) {
                    return true;
                }
            }
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (it.hasNext()) {
                    wifiConfiguration = it.next();
                    Lg.d(new StringBuilder().append(this._data.ssid).append(" : ").append(wifiConfiguration.SSID).toString() == null ? "null" : wifiConfiguration.SSID);
                    if (isSameSSID(this._data.ssid, wifiConfiguration.SSID)) {
                        break;
                    }
                }
            }
            wifiConfiguration = null;
            if (this._data.isEasyConnect) {
                if (z) {
                    if (!isFindSsidInScanResult(this._data.ssid)) {
                        Lg.i("connectWifiLinkageData skip");
                        this._skipTime = 0;
                        return true;
                    } else if (this._skipTime == 0) {
                        this._skipTime = this._skipTimeEasyConnect;
                        this._sw.start();
                    }
                } else if (connectWifiLinkageData(wifiManager, this._data, wifiConfiguration)) {
                    this._wifiChanged = true;
                    this._skipTimeEasyConnect = 5000;
                    return true;
                } else {
                    this._skipTimeEasyConnect = 5000;
                }
            }
            if (wifiConfiguration != null) {
                if (!z) {
                    this._isSuccessfulChangeWifiProfile = false;
                    return false;
                }
                connectWifiWhenExist(wifiManager, wifiConfiguration, z);
                this._wifiChanged = true;
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean needChangeWifiPrimaryAP() {
        return (this._data.isEasyConnect && this._data.isPriorWireless && this._data.isAutoSSID) || (!this._data.isEasyConnect && this._data.isPriorWireless);
    }

    public boolean needChangeWifiSecondaryAP() {
        return (this._data.isEasyConnect && !this._data.isPriorWireless && this._data.isAutoSSID) || !(this._data.isEasyConnect || this._data.isPriorWireless);
    }

    public boolean hasSecondary() {
        return this._data.isAvailableWired && (this._data.isAvailableWireless || this._data.isAutoSSID);
    }

    public synchronized void revert() {
        WifiConfiguration wifiConfiguration;
        Lg.i("revert - start");
        if (sIsReverting) {
            Lg.d("revert重複処理ガード");
            return;
        }
        sIsReverting = true;
        this._isStop = true;
        if (!this._wifiChanged) {
            initInfo(true);
            return;
        }
        WifiManager wifiManager = (WifiManager) this._context.getSystemService("wifi");
        if (wifiManager == null) {
            initInfo(true);
        } else if (wifiManager.getWifiState() == 1) {
            initInfo(true);
        } else {
            if (this._prevSSID != null) {
                List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
                if (configuredNetworks == null) {
                    initInfo(true);
                    return;
                }
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        wifiConfiguration = null;
                        break;
                    }
                    wifiConfiguration = it.next();
                    Lg.i(new StringBuilder().append(this._prevSSID).append(" : ").append(wifiConfiguration.SSID).toString() == null ? "null" : wifiConfiguration.SSID);
                    if (isSameSSID(this._prevSSID, wifiConfiguration.SSID)) {
                        break;
                    }
                }
                if (wifiConfiguration != null) {
                    this._tryExistState = TryState.NONE;
                    this._skipTime = 0;
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    boolean z = false;
                    while (stopWatch.getDiff() < 10000 && !isEnableAP(this._context, this._prevSSID)) {
                        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                        if (!isSameSSID(this._prevSSID, connectionInfo.getSSID()) || isEmptyBSSID(connectionInfo)) {
                            connectWifiWhenExist(wifiManager, wifiConfiguration, z);
                            z = true;
                        }
                        Sleeper.sleep(200L);
                    }
                    enableAllNetwork();
                } else {
                    wifiManager.setWifiEnabled(false);
                    StopWatch stopWatch2 = new StopWatch();
                    stopWatch2.start();
                    while (stopWatch2.getDiff() < 10000 && wifiManager.getWifiState() != 1) {
                        Sleeper.sleep(100L);
                    }
                    wifiManager.setWifiEnabled(true);
                }
            }
            if (!this._prevWifiEnabled) {
                wifiManager.setWifiEnabled(false);
            }
            initInfo(true);
            Lg.i("revert - end");
        }
    }

    private boolean enableWifi(WifiManager wifiManager, boolean z) {
        String ssid;
        if (!wifiManager.isWifiEnabled()) {
            Lg.i("Wifiが有効ではありません");
            this._prevWifiEnabled = false;
            if (wifiManager.getWifiState() != 2) {
                Lg.i("Wi-FiをONにしました。");
                this._wifiChanged = true;
                wifiManager.setWifiEnabled(true);
            }
        } else if (this._prevSSID == null) {
            Lg.i("Wifiが有効です");
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && (ssid = connectionInfo.getSSID()) != null) {
                this._prevSSID = ssid.replace("\"", "");
            }
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        while (stopWatch.getDiff() < 30000) {
            if (wifiManager.getWifiState() == 3) {
                return true;
            }
            Sleeper.sleep(100L);
        }
        Lg.e("Error: Wi-Fi有効待機タイムアウト");
        return false;
    }

    private void connectWifiWhenExist(WifiManager wifiManager, WifiConfiguration wifiConfiguration, boolean z) {
        Lg.i("connectWifiWhenExist---start");
        if (this._sw.getDiff() < this._skipTime) {
            Lg.i("connectWifiWhenExist skip");
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$WifiConnector$TryState[this._tryExistState.ordinal()];
        if (i == 1 || i == 2) {
            connectWifiWhenExist1(wifiManager, wifiConfiguration);
            this._tryExistState = TryState.TRY_EXIST_1;
        } else if (i == 3) {
            connectWifiWhenExist2(wifiManager, wifiConfiguration);
            this._tryExistState = TryState.TRY_EXIST_2;
        }
        this._sw.start();
        this._skipTime = 5000;
        Lg.i("connectWifiWhenExist---end");
    }

    public static /* synthetic */ int lambda$sortByPriority$0(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.priority;
    }

    private void sortByPriority(List<WifiConfiguration> list) {
        list.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.epson.iprojection.ui.activities.pjselect.WifiConnector$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return WifiConnector.lambda$sortByPriority$0((WifiConfiguration) obj);
            }
        }));
    }

    private void connectWifiWhenExist1(WifiManager wifiManager, WifiConfiguration wifiConfiguration) {
        Lg.i("connectWifiWhenExist1");
        wifiManager.enableNetwork(wifiConfiguration.networkId, true);
    }

    private void connectWifiWhenExist2(WifiManager wifiManager, WifiConfiguration wifiConfiguration) {
        Lg.i("connectWifiWhenExist2");
        wifiManager.disconnect();
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration wifiConfiguration2 : configuredNetworks) {
                if (isSameSSID(wifiConfiguration.SSID, wifiConfiguration2.SSID)) {
                    wifiManager.enableNetwork(wifiConfiguration2.networkId, true);
                } else {
                    wifiManager.disableNetwork(wifiConfiguration2.networkId);
                }
            }
        }
    }

    private boolean connectWifiLinkageData(WifiManager wifiManager, D_LinkageData d_LinkageData, WifiConfiguration wifiConfiguration) {
        WifiConfiguration wifiConfiguration2;
        int updateNetwork;
        if (wifiConfiguration == null) {
            wifiConfiguration2 = new WifiConfiguration();
        } else if (!wifiManager.removeNetwork(wifiConfiguration.networkId)) {
            return false;
        } else {
            wifiConfiguration2 = new WifiConfiguration();
            wifiConfiguration = null;
        }
        wifiConfiguration2.SSID = "\"" + d_LinkageData.ssid + "\"";
        wifiConfiguration2.hiddenSSID = false;
        wifiConfiguration2.status = 2;
        wifiConfiguration2.allowedProtocols.set(1);
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$SecurityType[d_LinkageData.securityType.ordinal()];
        if (i == 1) {
            Lg.i("セキュリティ：なし");
            wifiConfiguration2.allowedKeyManagement.set(0);
            wifiConfiguration2.allowedAuthAlgorithms.clear();
        } else if (i == 2) {
            Lg.i("セキュリティ：WPA2_PSK_AES");
            wifiConfiguration2.preSharedKey = "\"" + d_LinkageData.password + "\"";
            wifiConfiguration2.allowedKeyManagement.set(1);
            wifiConfiguration2.allowedGroupCiphers.set(3);
            wifiConfiguration2.allowedPairwiseCiphers.set(2);
        }
        if (wifiConfiguration == null) {
            updateNetwork = wifiManager.addNetwork(wifiConfiguration2);
        } else {
            updateNetwork = wifiManager.updateNetwork(wifiConfiguration2);
        }
        if (updateNetwork == -1) {
            return false;
        }
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration wifiConfiguration3 : configuredNetworks) {
                if (!isSameSSID(wifiConfiguration2.SSID, wifiConfiguration3.SSID)) {
                    wifiManager.disableNetwork(wifiConfiguration3.networkId);
                }
            }
        }
        wifiManager.enableNetwork(updateNetwork, true);
        return true;
    }

    /* renamed from: com.epson.iprojection.ui.activities.pjselect.WifiConnector$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$SecurityType;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$WifiConnector$TryState;

        static {
            int[] iArr = new int[D_LinkageData.SecurityType.values().length];
            $SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$SecurityType = iArr;
            try {
                iArr[D_LinkageData.SecurityType.eNone.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$SecurityType[D_LinkageData.SecurityType.eWPA2_PSK_AES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[TryState.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$WifiConnector$TryState = iArr2;
            try {
                iArr2[TryState.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$WifiConnector$TryState[TryState.TRY_EXIST_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$WifiConnector$TryState[TryState.TRY_EXIST_1.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public boolean isEnableSearching() {
        NetworkInfo activeNetworkInfo;
        WifiManager wifiManager = (WifiManager) this._context.getSystemService("wifi");
        if (wifiManager == null || wifiManager.getConnectionInfo().getBSSID() == null || wifiManager.getConnectionInfo().getIpAddress() == 0 || wifiManager.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED || (activeNetworkInfo = ((ConnectivityManager) this._context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    public boolean isEnableSearchingOnSoftAP() {
        boolean isEnableAP = isEnableAP(this._context, this._data.ssid);
        if (!isEnableAP) {
            scanWiFi();
        }
        return isEnableAP;
    }

    public static boolean isEnableAP(Context context, String str) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager != null) {
            if (!isEmptyBSSID(wifiManager.getConnectionInfo()) && wifiManager.getConnectionInfo().getIpAddress() != 0 && isSameSSID(str, wifiManager.getConnectionInfo().getSSID())) {
                if (wifiManager.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED) {
                    Lg.i("scanWiFi--1");
                    return false;
                }
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    Lg.i("scanWiFi--2");
                    return false;
                } else if (activeNetworkInfo.isConnected()) {
                    return true;
                } else {
                    Lg.i("scanWiFi--3");
                    return false;
                }
            }
            Lg.i("scanWiFi--0");
        }
        return false;
    }

    private void scanWiFi() {
        WifiManager wifiManager = (WifiManager) this._context.getSystemService("wifi");
        StopWatch stopWatch = _scanTime;
        if (stopWatch.getDiff() > 3000) {
            MethodUtil.compatStartScan(wifiManager);
            stopWatch.start();
        }
    }

    private boolean isFindSsidInScanResult(String str) {
        for (ScanResult scanResult : ((WifiManager) this._context.getSystemService("wifi")).getScanResults()) {
            if (isSameSSID(str, scanResult.SSID)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmptyScanResult() {
        List<ScanResult> scanResults = ((WifiManager) this._context.getSystemService("wifi")).getScanResults();
        return scanResults == null || scanResults.isEmpty();
    }

    public boolean isWifiChanged() {
        return this._wifiChanged;
    }

    public boolean isSuccessfulChangeWifiProfile() {
        return this._isSuccessfulChangeWifiProfile;
    }

    private static boolean isSameSSID(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        return str.replace("\"", "").equals(str2.replace("\"", ""));
    }

    private static boolean isEmptyBSSID(WifiInfo wifiInfo) {
        String bssid;
        if (wifiInfo == null || (bssid = wifiInfo.getBSSID()) == null) {
            return true;
        }
        return bssid.equals("00:00:00:00:00:00");
    }
}
