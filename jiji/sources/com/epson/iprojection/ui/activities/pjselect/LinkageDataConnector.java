package com.epson.iprojection.ui.activities.pjselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.StopWatch;
import com.epson.iprojection.common.utils.PermissionUtilsKt;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.decoder.LinkageDataCommonDecoder;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;
import com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.SpoilerDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.pjselect.nfc.NfcDataManager;
import com.epson.iprojection.ui.activities.pjselect.permission.wificonnection.PermissionLocationActivity;
import com.epson.iprojection.ui.activities.support.intro.wifi.Activity_IntroWifi;
import com.epson.iprojection.ui.common.AppStatus;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRegisteredDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.uiparts.ProgressDialogHasButton;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;

/* loaded from: classes.dex */
public class LinkageDataConnector implements IPjManualSearchResultListener, IOnDialogEventListener {
    private static final boolean DEBUG = false;
    public static final String FAIL_TO_CHANGE_WIFIPROFILE = "fail_to_change_wifiprofile";
    public static final String TRYING_CONNECT_SSID = "trying_connect_ssid";
    private final Activity _activity;
    private final ILinkageDataConnectorListener _impl;
    private final INextActivityCallable _implNextActivityCallable;
    private final IStartableSearch _implStartableSearch;
    private LinkageDataConnectorAndroid10AndOver _linkageDataConnectorAndroid10AndOver;
    private Dialog _progressDialog;
    private D_LinkageData _tmpLinkageDataForPermission;
    private WifiConnector _wifiConnector;
    private Thread _thread = null;
    private ConnectChecker _connectChecker = null;
    private AutoConnector _autoConnector = null;
    private D_PjInfo _findManualSearchPjInf = null;
    private boolean _wifiConnecting = false;
    private final Handler _handler = new Handler();

    /* renamed from: $r8$lambda$Z7kVZzHzjsu5fl2o7UuAzA5EK-4 */
    public static /* synthetic */ void m118$r8$lambda$Z7kVZzHzjsu5fl2o7UuAzA5EK4(LinkageDataConnector linkageDataConnector) {
        linkageDataConnector.manualSearchLinkagePj();
    }

    /* renamed from: $r8$lambda$k9etIQpTrni-L5JWGSjWTBRA5DI */
    public static /* synthetic */ void m119$r8$lambda$k9etIQpTrniL5JWGSjWTBRA5DI(LinkageDataConnector linkageDataConnector) {
        linkageDataConnector.failedConnect();
    }

    private void showDebugDialog(D_LinkageData d_LinkageData) {
    }

    public LinkageDataConnector(Activity activity, ILinkageDataConnectorListener iLinkageDataConnectorListener, INextActivityCallable iNextActivityCallable, IStartableSearch iStartableSearch) {
        this._activity = activity;
        this._impl = iLinkageDataConnectorListener;
        this._implNextActivityCallable = iNextActivityCallable;
        this._implStartableSearch = iStartableSearch;
    }

    public void connectByQrCode(byte[] bArr) {
        try {
            D_LinkageData decode = LinkageDataCommonDecoder.decode(bArr, D_LinkageData.Mode.MODE_QR);
            connectByLinkageData(decode);
            setAnalyticsEventQr(decode);
        } catch (LinkageDataFormatException unused) {
            showCodeFormatErrDialog();
        } catch (LinkageDataVersionException unused2) {
            showAppUpdaterDialog();
        }
    }

    public void connectByNfcData(boolean z) {
        try {
            try {
                try {
                    NfcDataManager.NfcAlertType nfcAlertType = NfcDataManager.NfcAlertType.ALERT_NONE;
                    if (Pj.getIns().isConnected()) {
                        nfcAlertType = NfcDataManager.NfcAlertType.ALERT_ALREADY_CONNECT;
                    } else if (z) {
                        nfcAlertType = NfcDataManager.getIns().checkPjStatus();
                    }
                    if (nfcAlertType == NfcDataManager.NfcAlertType.ALERT_NONE) {
                        D_LinkageData nfcData = NfcDataManager.getIns().getNfcData();
                        connectByLinkageData(nfcData);
                        showDebugDialog(nfcData);
                        setAnalyticsEventNfc(nfcData);
                    } else {
                        showNfcConnectErrDialog(nfcAlertType);
                    }
                } catch (LinkageDataVersionException unused) {
                    showAppUpdaterDialog();
                }
            } catch (LinkageDataFormatException unused2) {
                showCodeFormatErrDialog();
            }
        } finally {
            NfcDataManager.getIns().clearBinaryData();
        }
    }

    public void onActivityResumed() {
        if (this._linkageDataConnectorAndroid10AndOver == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this._linkageDataConnectorAndroid10AndOver.onActivityResumed();
    }

    private void connectByLinkageData(D_LinkageData d_LinkageData) {
        if (d_LinkageData.isEasyConnect && !d_LinkageData.isAutoSSID && !d_LinkageData.isAvailableWired) {
            showSsidOffDialog();
        } else if (shouldGetLocationPermission()) {
            this._tmpLinkageDataForPermission = d_LinkageData;
            this._implNextActivityCallable.startActivityForResultFragmentVer(new Intent(this._activity, PermissionLocationActivity.class), CommonDefine.REQUEST_CODE_LOCATION_PERMISSION_FOR_WIFICONNECTION);
        } else {
            connectByLinkageDataCore(d_LinkageData);
        }
    }

    private boolean shouldGetLocationPermission() {
        return PermissionUtilsKt.needGetLocationPermissionForWifi() && ContextCompat.checkSelfPermission(this._activity, "android.permission.ACCESS_FINE_LOCATION") != 0;
    }

    public void restartConnectByLinkageData() {
        if (this._tmpLinkageDataForPermission == null) {
            Lg.e("_tmpLinkageDataForPermission is null!!");
        } else if (ContextCompat.checkSelfPermission(this._activity, "android.permission.ACCESS_FINE_LOCATION") != 0) {
        } else {
            connectByLinkageDataCore(this._tmpLinkageDataForPermission);
            this._tmpLinkageDataForPermission = null;
        }
    }

    private void connectByLinkageDataCore(final D_LinkageData d_LinkageData) {
        if (Build.VERSION.SDK_INT >= 29) {
            LinkageDataConnectorAndroid10AndOver linkageDataConnectorAndroid10AndOver = new LinkageDataConnectorAndroid10AndOver(this._activity, this._impl, d_LinkageData);
            this._linkageDataConnectorAndroid10AndOver = linkageDataConnectorAndroid10AndOver;
            linkageDataConnectorAndroid10AndOver.connectByLinkageDataCore();
            return;
        }
        Pj.getIns().clearAllDialog();
        Pj.getIns().setIsConnectingByLinkageData(true);
        this._activity.getWindow().addFlags(128);
        if (d_LinkageData.needChangeWifi(this._activity)) {
            createProgressDialog(d_LinkageData.isPriorWireless, d_LinkageData.mode);
            this._wifiConnector = new WifiConnector(this._activity, d_LinkageData);
        } else {
            createProgressDialog(false, d_LinkageData.mode);
            this._wifiConnector = null;
        }
        setWifiConnecting(true);
        this._connectChecker = new ConnectChecker(this._wifiConnector, d_LinkageData.mode);
        Thread thread = new Thread(this._connectChecker);
        this._thread = thread;
        thread.start();
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda5
            {
                LinkageDataConnector.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                LinkageDataConnector.this.m120xafe75b9(d_LinkageData);
            }
        }).start();
    }

    /* renamed from: lambda$connectByLinkageDataCore$0$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector */
    public /* synthetic */ void m120xafe75b9(D_LinkageData d_LinkageData) {
        boolean z;
        WifiConnector wifiConnector = this._wifiConnector;
        if (wifiConnector == null || !wifiConnector.needChangeWifiPrimaryAP()) {
            z = false;
        } else if (!this._wifiConnector.connect()) {
            onFailedConnect();
            this._wifiConnector.revert();
            return;
        } else {
            z = true;
        }
        setWifiConnecting(false);
        startPjSearch(d_LinkageData);
        if (!z) {
            startSearchLinkagePj(d_LinkageData.isPreferredPhysicalNetworkAvailable());
        }
        if (this._connectChecker != null) {
            Pj.getIns().setLinkageDataSearchingMode(true);
        }
    }

    /* loaded from: classes.dex */
    public class ConnectChecker implements Runnable {
        private static final int WIFI_SWITCH_WAIT = 1000;
        private WifiConnector _connecter;
        private boolean _isAvailable = true;
        private final D_LinkageData.Mode _mode;
        private int _timeout;

        ConnectChecker(WifiConnector wifiConnector, D_LinkageData.Mode mode) {
            LinkageDataConnector.this = r1;
            this._connecter = wifiConnector;
            this._mode = mode;
        }

        @Override // java.lang.Runnable
        public synchronized void run() {
            int i;
            int i2;
            WifiConnector wifiConnector;
            WifiConnector wifiConnector2;
            if (this._mode == D_LinkageData.Mode.MODE_NFC) {
                i = CommonDefine.EXPIRE_NFC_CONNECT;
                i2 = 200000;
            } else {
                i = 20000;
                i2 = CommonDefine.EXPIRE_QR_CONNECT_WIFI;
            }
            WifiConnector wifiConnector3 = this._connecter;
            if (wifiConnector3 == null) {
                this._timeout = i;
            } else if (wifiConnector3.needChangeWifiPrimaryAP()) {
                this._timeout = i2;
            } else {
                this._timeout = i;
            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            long diff = stopWatch.getDiff();
            boolean z = false;
            boolean z2 = false;
            while (this._isAvailable && this._timeout > diff) {
                Sleeper.sleep(200L);
                WifiConnector wifiConnector4 = this._connecter;
                if (wifiConnector4 != null) {
                    if (!z) {
                        if (wifiConnector4.needChangeWifiPrimaryAP()) {
                            if (diff > 1000 && !z2) {
                                if (this._connecter.isEnableSearchingOnSoftAP()) {
                                    this._connecter.enableAllNetwork();
                                    LinkageDataConnector.this.startSearchLinkagePj(!z);
                                    z2 = true;
                                } else if (!this._connecter.reconnect()) {
                                    failed();
                                    return;
                                }
                            }
                        } else if (diff > 1000 && this._connecter.isEnableSearching() && !z2) {
                            LinkageDataConnector.this.startSearchLinkagePj(!z);
                            z2 = true;
                        }
                    } else if (wifiConnector4.needChangeWifiSecondaryAP()) {
                        if (diff > 1000 && !z2) {
                            if (this._connecter.isEnableSearchingOnSoftAP()) {
                                this._connecter.enableAllNetwork();
                                LinkageDataConnector.this.startSearchLinkagePj(!z);
                                z2 = true;
                            } else if (!this._connecter.reconnect()) {
                                failed();
                                return;
                            }
                        }
                    } else if (diff > 1000 && this._connecter.isEnableSearching() && !z2) {
                        LinkageDataConnector.this.startSearchLinkagePj(!z);
                        z2 = true;
                    }
                }
                if (!LinkageDataConnector.this.isQRConnecting()) {
                    break;
                }
                diff = stopWatch.getDiff();
                if (this._timeout <= diff) {
                    if (!z && (wifiConnector2 = this._connecter) != null) {
                        if (wifiConnector2.hasSecondary()) {
                            if (this._connecter.needChangeWifiSecondaryAP()) {
                                this._timeout = i2;
                                stopWatch.start();
                                diff = stopWatch.getDiff();
                                new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$ConnectChecker$$ExternalSyntheticLambda1
                                    {
                                        LinkageDataConnector.ConnectChecker.this = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LinkageDataConnector.ConnectChecker.this.m127xf5ed5892();
                                    }
                                }).start();
                                LinkageDataConnector.this.onRetryQRConnect(true);
                            } else {
                                this._timeout = i;
                                stopWatch.start();
                                diff = stopWatch.getDiff();
                                new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$ConnectChecker$$ExternalSyntheticLambda2
                                    {
                                        LinkageDataConnector.ConnectChecker.this = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LinkageDataConnector.ConnectChecker.this.m128x249ec2b1();
                                    }
                                }).start();
                                LinkageDataConnector.this.onRetryQRConnect(false);
                            }
                            z2 = false;
                            z = true;
                        } else {
                            if (this._isAvailable) {
                                failed();
                            }
                            return;
                        }
                    }
                    if (this._isAvailable) {
                        failed();
                    }
                    return;
                }
            }
            if (!this._isAvailable && (wifiConnector = this._connecter) != null) {
                wifiConnector.revert();
                this._connecter = null;
            }
        }

        /* renamed from: lambda$run$0$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector$ConnectChecker */
        public /* synthetic */ void m127xf5ed5892() {
            if (this._connecter.connect()) {
                return;
            }
            LinkageDataConnector.this.onFailedConnect();
            this._connecter.revert();
        }

        /* renamed from: lambda$run$1$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector$ConnectChecker */
        public /* synthetic */ void m128x249ec2b1() {
            this._connecter.revert();
        }

        private void failed() {
            LinkageDataConnector.this.onFailedConnect();
            new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector.ConnectChecker.1
                {
                    ConnectChecker.this = this;
                }

                @Override // java.lang.Runnable
                public synchronized void run() {
                    if (ConnectChecker.this._connecter != null) {
                        ConnectChecker.this._connecter.revert();
                        ConnectChecker.this._connecter = null;
                    }
                }
            }).start();
            LinkageDataConnector.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$ConnectChecker$$ExternalSyntheticLambda3
                {
                    LinkageDataConnector.ConnectChecker.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    LinkageDataConnector.ConnectChecker.this.m126x9be281c4();
                }
            });
        }

        /* renamed from: lambda$failed$2$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector$ConnectChecker */
        public /* synthetic */ void m126x9be281c4() {
            LinkageDataConnector.this._activity.getWindow().clearFlags(128);
        }

        public void cancel() {
            this._isAvailable = false;
            this._timeout = 0;
            failed();
        }

        public void end() {
            this._timeout = 0;
            if (Pj.getIns().isConnected()) {
                return;
            }
            LinkageDataConnector.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$ConnectChecker$$ExternalSyntheticLambda0
                {
                    LinkageDataConnector.ConnectChecker.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    LinkageDataConnector.ConnectChecker.this.m125x2160869f();
                }
            });
        }

        /* renamed from: lambda$end$3$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector$ConnectChecker */
        public /* synthetic */ void m125x2160869f() {
            LinkageDataConnector.this._activity.getWindow().clearFlags(128);
        }
    }

    public boolean isQRConnecting() {
        return (this._autoConnector != null) | this._wifiConnecting;
    }

    public void onRetryQRConnect(final boolean z) {
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda8
            {
                LinkageDataConnector.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                LinkageDataConnector.this.m124x8e8eef6d(z);
            }
        });
    }

    /* renamed from: retryQRConnect */
    public void m124x8e8eef6d(boolean z) {
        Dialog dialog = this._progressDialog;
        if (dialog != null) {
            dialog.dismiss();
            this._progressDialog = null;
        }
        createProgressDialog(z, D_LinkageData.Mode.MODE_QR);
    }

    public void onFailedConnect() {
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda4
            {
                LinkageDataConnector.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                LinkageDataConnector.m119$r8$lambda$k9etIQpTrniL5JWGSjWTBRA5DI(LinkageDataConnector.this);
            }
        });
    }

    public void failedConnect() {
        setWifiConnecting(false);
        Dialog dialog = this._progressDialog;
        if (dialog != null) {
            if (dialog.isShowing()) {
                this._progressDialog.dismiss();
            }
            this._progressDialog = null;
        }
        this._autoConnector = null;
        clearLinkageDataInfo();
        Pj.getIns().setLinkageDataSearchingMode(false);
        if (AppStatus.getIns()._isAppForeground) {
            this._impl.setResumeByLinkageDataRead(false);
            Intent intent = new Intent(this._activity, Activity_IntroWifi.class);
            WifiConnector wifiConnector = this._wifiConnector;
            if (wifiConnector != null && !wifiConnector.isSuccessfulChangeWifiProfile()) {
                intent.putExtra(FAIL_TO_CHANGE_WIFIPROFILE, true);
                intent.putExtra(TRYING_CONNECT_SSID, this._wifiConnector.getTryingConnectSSID());
            }
            this._activity.startActivityForResult(intent, 0);
        }
        Pj.getIns().setIsConnectingByLinkageData(false);
    }

    private void clearLinkageDataInfo() {
        if (Pj.getIns().isConnected()) {
            return;
        }
        LinkageDataInfoStacker.getIns().clear();
    }

    private void onCancelConnect() {
        ConnectChecker connectChecker = this._connectChecker;
        if (connectChecker != null) {
            connectChecker.cancel();
            this._connectChecker = null;
        }
        this._thread = null;
    }

    public void cancelLinkageConnect() {
        if (!NfcDataManager.getIns().isEventOccured()) {
            cancelConnect();
        }
        if (this._linkageDataConnectorAndroid10AndOver == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this._linkageDataConnectorAndroid10AndOver.onActivityStopped();
    }

    private void cancelConnect() {
        this._impl.setResumeByLinkageDataRead(false);
        Dialog dialog = this._progressDialog;
        if (dialog != null) {
            dialog.cancel();
        }
    }

    public WifiConnector getWifiConnector() {
        return this._wifiConnector;
    }

    private synchronized void setWifiConnecting(boolean z) {
        this._wifiConnecting = z;
    }

    private void startPjSearch(D_LinkageData d_LinkageData) {
        Pj.getIns().clearAllSelectingPJ();
        this._autoConnector = new AutoConnector(d_LinkageData);
    }

    public void startSearchLinkagePj(boolean z) {
        AutoConnector autoConnector = this._autoConnector;
        if (autoConnector == null) {
            return;
        }
        autoConnector.setIsPrimary(z);
        if (!this._autoConnector.isSearching()) {
            startSearchLinkagePj();
        }
        this._autoConnector.setSearching(true);
    }

    private void startSearchLinkagePj() {
        this._handler.postDelayed(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda0
            {
                LinkageDataConnector.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                LinkageDataConnector.m118$r8$lambda$Z7kVZzHzjsu5fl2o7UuAzA5EK4(LinkageDataConnector.this);
            }
        }, 500L);
    }

    public void manualSearchLinkagePj() {
        AutoConnector autoConnector = this._autoConnector;
        if (autoConnector == null) {
            return;
        }
        this._findManualSearchPjInf = null;
        String ipAddress = autoConnector.getIpAddress();
        if (ipAddress == null || Pj.getIns().manualSearchNoDialog(this, ipAddress) == -1) {
            failedConnect();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        if (z) {
            this._findManualSearchPjInf = d_PjInfo;
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        AutoConnector autoConnector = this._autoConnector;
        if (autoConnector != null) {
            if (this._findManualSearchPjInf != null) {
                autoConnector.setSearching(false);
                this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda3
                    {
                        LinkageDataConnector.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        LinkageDataConnector.this.m123x42437292();
                    }
                });
                return;
            }
            startSearchLinkagePj();
        }
    }

    /* renamed from: lambda$onEndSearchPj$2$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector */
    public /* synthetic */ void m123x42437292() {
        onPjFind(this._findManualSearchPjInf, false);
    }

    private void onPjFind(D_PjInfo d_PjInfo, boolean z) {
        AutoConnector autoConnector = this._autoConnector;
        if (autoConnector == null || !autoConnector.isConnectPj(d_PjInfo)) {
            return;
        }
        Dialog dialog = this._progressDialog;
        if (dialog != null) {
            if (dialog.isShowing()) {
                this._progressDialog.dismiss();
            }
            this._progressDialog = null;
        }
        if (Pj.getIns().isLinkageDataSearching()) {
            this._autoConnector.onPjFind(d_PjInfo, new ConnectConfig(this._activity).getInterruptSetting());
        }
        this._autoConnector = null;
    }

    private void showNfcConnectErrDialog(NfcDataManager.NfcAlertType nfcAlertType) {
        MessageDialog.MessageType messageType;
        BaseDialog.ResultAction resultAction = BaseDialog.ResultAction.NOACTION;
        int i = AnonymousClass2.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$nfc$NfcDataManager$NfcAlertType[nfcAlertType.ordinal()];
        if (i == 1) {
            messageType = MessageDialog.MessageType.Nfc_NoWirelessLanUnit;
        } else if (i == 2) {
            messageType = MessageDialog.MessageType.Nfc_WirelessModeOff;
        } else if (i == 3) {
            this._impl.setResumeByLinkageDataRead(true);
            Pj.getIns().showSpoilerDialog(SpoilerDialog.MessageType.Nfc_RetryAndPowerOn, new IOnDialogEventListener() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector.1
                {
                    LinkageDataConnector.this = this;
                }

                @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
                public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction2) {
                    LinkageDataConnector.this.onClickDialogOK(str, resultAction2);
                }

                @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
                public void onClickDialogNG(BaseDialog.ResultAction resultAction2) {
                    LinkageDataConnector.this.onClickDialogNG(resultAction2);
                    LinkageDataConnector.this._implStartableSearch.requestStartSearch();
                }

                @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
                public void onDialogCanceled() {
                    LinkageDataConnector.this.onDialogCanceled();
                    LinkageDataConnector.this._implStartableSearch.requestStartSearch();
                }
            }, BaseDialog.ResultAction.NFC_RETRY_CONNECT);
            return;
        } else if (i == 4) {
            messageType = MessageDialog.MessageType.Nfc_CheckNetworkSetting;
        } else {
            Lg.e("error!! [" + nfcAlertType + "]");
            return;
        }
        if (messageType != null) {
            Pj.getIns().showMsgDialog(messageType, this, resultAction);
        }
    }

    private void showAppUpdaterDialog() {
        Pj.getIns().showQueryDialog(QueryDialog.MessageType.AppUpdate, this, BaseDialog.ResultAction.APP_UPDATE);
    }

    private void showCodeFormatErrDialog() {
        AlertDialog create = new AlertDialog.Builder(this._activity).setMessage(this._activity.getString(R.string._QRCodeFormatError_)).setPositiveButton(this._activity.getString(R.string._OK_), (DialogInterface.OnClickListener) null).create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    private void showSsidOffDialog() {
        AlertDialog create = new AlertDialog.Builder(this._activity).setMessage(this._activity.getString(R.string._SSIDOff_)).setPositiveButton(this._activity.getString(R.string._OK_), (DialogInterface.OnClickListener) null).create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    private void createProgressDialog(boolean z, D_LinkageData.Mode mode) {
        this._impl.setResumeByLinkageDataRead(true);
        if (mode == D_LinkageData.Mode.MODE_NFC) {
            this._progressDialog = createNfcProgressDialog(z);
        } else {
            this._progressDialog = createQrProgressDialog(z);
        }
        this._progressDialog.show();
        RegisteredDialog.getIns().setDialog(this._progressDialog);
    }

    private Dialog createNfcProgressDialog(boolean z) {
        String string;
        if (z) {
            string = this._activity.getString(R.string._WiFiChanging_);
        } else {
            string = this._activity.getString(R.string._SearchingProjector_);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
        builder.setCancelable(false);
        builder.setNegativeButton(this._activity.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LinkageDataConnector.lambda$createNfcProgressDialog$3(dialogInterface, i);
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda2
            {
                LinkageDataConnector.this = this;
            }

            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                LinkageDataConnector.this.m121x7db421ba(dialogInterface);
            }
        });
        View inflate = ((LayoutInflater) this._activity.getSystemService("layout_inflater")).inflate(R.layout.dialog_progress_nfc, (ViewGroup) null);
        builder.setView(inflate);
        ((TextView) inflate.findViewById(R.id.text_main)).setText(string);
        ((TextView) inflate.findViewById(R.id.text_sub)).setText(this._activity.getString(R.string._NFCConnectFewMinutes_) + "\n" + this._activity.getString(R.string._NFCConnectingTurnOnManually_));
        return builder.create();
    }

    public static /* synthetic */ void lambda$createNfcProgressDialog$3(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
    }

    /* renamed from: lambda$createNfcProgressDialog$4$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector */
    public /* synthetic */ void m121x7db421ba(DialogInterface dialogInterface) {
        cancelProgressDialog();
    }

    private Dialog createQrProgressDialog(boolean z) {
        String string;
        if (z) {
            string = this._activity.getString(R.string._WiFiChanging_);
        } else {
            string = this._activity.getString(R.string._SearchingProjector_);
        }
        ProgressDialogHasButton progressDialogHasButton = new ProgressDialogHasButton(this._activity);
        progressDialogHasButton.setMessage(string);
        progressDialogHasButton.setCancelable(false);
        progressDialogHasButton.setButton(-2, this._activity.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LinkageDataConnector.lambda$createQrProgressDialog$5(dialogInterface, i);
            }
        });
        progressDialogHasButton.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$$ExternalSyntheticLambda7
            {
                LinkageDataConnector.this = this;
            }

            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                LinkageDataConnector.this.m122xeef9cd44(dialogInterface);
            }
        });
        return progressDialogHasButton;
    }

    public static /* synthetic */ void lambda$createQrProgressDialog$5(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
    }

    /* renamed from: lambda$createQrProgressDialog$6$com-epson-iprojection-ui-activities-pjselect-LinkageDataConnector */
    public /* synthetic */ void m122xeef9cd44(DialogInterface dialogInterface) {
        cancelProgressDialog();
    }

    /* renamed from: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector$2 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$nfc$NfcDataManager$NfcAlertType;

        static {
            int[] iArr = new int[BaseDialog.ResultAction.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction = iArr;
            try {
                iArr[BaseDialog.ResultAction.NFC_RETRY_CONNECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.APP_UPDATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[NfcDataManager.NfcAlertType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$nfc$NfcDataManager$NfcAlertType = iArr2;
            try {
                iArr2[NfcDataManager.NfcAlertType.ALERT_NO_WIRELESS_LAN_UNIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$nfc$NfcDataManager$NfcAlertType[NfcDataManager.NfcAlertType.ALERT_WIRELESS_MODE_OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$nfc$NfcDataManager$NfcAlertType[NfcDataManager.NfcAlertType.ALERT_RETRY_AND_POWER_ON.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$nfc$NfcDataManager$NfcAlertType[NfcDataManager.NfcAlertType.ALERT_CHECK_NETWORK_SETTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
        int i = AnonymousClass2.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[resultAction.ordinal()];
        if (i == 1) {
            connectByNfcData(false);
        } else if (i != 2) {
        } else {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.epson.iprojection"));
            if (this._activity.getPackageManager().resolveActivity(intent, 0) != null) {
                this._activity.startActivity(intent);
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
        Pj.getIns().clearDialog();
    }

    private void cancelProgressDialog() {
        this._autoConnector = null;
        Pj.getIns().setLinkageDataSearchingMode(false);
        Activity activity = this._activity;
        Toast.makeText(activity, activity.getString(R.string._CanceledSearching_), 1).show();
        clearLinkageDataInfo();
        onCancelConnect();
    }

    private void setAnalyticsEventQr(D_LinkageData d_LinkageData) {
        eSearchRouteDimension esearchroutedimension;
        byte[] bArr = new byte[d_LinkageData.macAddr.length];
        for (int i = 0; i < d_LinkageData.macAddr.length; i++) {
            bArr[i] = (byte) d_LinkageData.macAddr[i];
        }
        byte[] bArr2 = new byte[d_LinkageData.wiredIP.length];
        for (int i2 = 0; i2 < d_LinkageData.wiredIP.length; i2++) {
            bArr2[i2] = (byte) d_LinkageData.wiredIP[i2];
        }
        byte[] bArr3 = new byte[d_LinkageData.wirelessIP.length];
        for (int i3 = 0; i3 < d_LinkageData.wirelessIP.length; i3++) {
            bArr3[i3] = (byte) d_LinkageData.wirelessIP[i3];
        }
        if (d_LinkageData.isEasyConnect) {
            esearchroutedimension = eSearchRouteDimension.qr_auto;
        } else {
            esearchroutedimension = eSearchRouteDimension.qr_manual;
        }
        Analytics.getIns().setConnectEvent(esearchroutedimension, bArr, bArr2, bArr3);
        Analytics.getIns().setRegisteredEvent(eRegisteredDimension.qr);
    }

    private void setAnalyticsEventNfc(D_LinkageData d_LinkageData) {
        eSearchRouteDimension esearchroutedimension;
        byte[] bArr = new byte[d_LinkageData.macAddr.length];
        for (int i = 0; i < d_LinkageData.macAddr.length; i++) {
            bArr[i] = (byte) d_LinkageData.macAddr[i];
        }
        byte[] bArr2 = new byte[d_LinkageData.wiredIP.length];
        for (int i2 = 0; i2 < d_LinkageData.wiredIP.length; i2++) {
            bArr2[i2] = (byte) d_LinkageData.wiredIP[i2];
        }
        byte[] bArr3 = new byte[d_LinkageData.wirelessIP.length];
        for (int i3 = 0; i3 < d_LinkageData.wirelessIP.length; i3++) {
            bArr3[i3] = (byte) d_LinkageData.wirelessIP[i3];
        }
        if (d_LinkageData.isEasyConnect) {
            esearchroutedimension = eSearchRouteDimension.nfc_auto;
        } else {
            esearchroutedimension = eSearchRouteDimension.nfc_manual;
        }
        Analytics.getIns().setConnectEvent(esearchroutedimension, bArr, bArr2, bArr3);
        Analytics.getIns().setRegisteredEvent(eRegisteredDimension.nfc);
    }
}
