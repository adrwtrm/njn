package com.epson.iprojection.ui.activities.remote;

import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import java.io.Serializable;

/* loaded from: classes.dex */
public class AuthInfo implements Serializable {
    private static final String SIGNAGE_REMOTE_PASS = "admin";
    private static final String SIGNAGE_REMOTE_URL = "/webremote/index.html";
    private static final String SIGNAGE_REMOTE_USER = "EPSONWEB";
    private static final String SMART_REMOTE_PASS = "";
    private static final String SMART_REMOTE_URL = "/mobile/index.html?EPSON=Projector";
    private static final String SMART_REMOTE_USER = "EPSONMOBILE";
    private static final String STRING_HTTP = "http://";
    private static final String WEB_REMOTE_PASS = "guest";
    private static final String WEB_REMOTE_URL = "/webremote/index.html";
    private static final String WEB_REMOTE_USER = "EPSONREMOTE";
    private static final long serialVersionUID = 1;
    private final String _defaultPassword;
    private final D_HistoryInfo _pjInfo;
    private final String _url;
    private final String _username;

    private AuthInfo(D_HistoryInfo d_HistoryInfo, String str, String str2, String str3) {
        this._pjInfo = d_HistoryInfo;
        this._url = str;
        this._username = str2;
        this._defaultPassword = str3;
    }

    public boolean isUsedWebControlPassword() {
        return this._username.equals(SIGNAGE_REMOTE_USER);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public D_HistoryInfo getPjInfo() {
        return this._pjInfo;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getUrl() {
        return this._url;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getUsername() {
        return this._username;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getDefaultPassword() {
        return this._defaultPassword;
    }

    /* renamed from: com.epson.iprojection.ui.activities.remote.AuthInfo$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$control$D_HistoryInfo$RemoteType;

        static {
            int[] iArr = new int[D_HistoryInfo.RemoteType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$control$D_HistoryInfo$RemoteType = iArr;
            try {
                iArr[D_HistoryInfo.RemoteType.REMOTE_SMART.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$control$D_HistoryInfo$RemoteType[D_HistoryInfo.RemoteType.REMOTE_WEB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$control$D_HistoryInfo$RemoteType[D_HistoryInfo.RemoteType.REMOTE_SIGNAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static AuthInfo createAuthInfo(D_HistoryInfo d_HistoryInfo) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$control$D_HistoryInfo$RemoteType[d_HistoryInfo.getRemoteType().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return null;
                }
                return createSignageRemoteAuthInfo(d_HistoryInfo);
            }
            return createWebRemoteAuthInfo(d_HistoryInfo);
        }
        return createSmartRemoteAuthInfo(d_HistoryInfo);
    }

    private static AuthInfo createWebRemoteAuthInfo(D_HistoryInfo d_HistoryInfo) {
        return new AuthInfo(d_HistoryInfo, "http://" + NetUtils.cvtIPAddr(d_HistoryInfo.ipAddr) + "/webremote/index.html", WEB_REMOTE_USER, WEB_REMOTE_PASS);
    }

    private static AuthInfo createSmartRemoteAuthInfo(D_HistoryInfo d_HistoryInfo) {
        return new AuthInfo(d_HistoryInfo, "http://" + NetUtils.cvtIPAddr(d_HistoryInfo.ipAddr) + SMART_REMOTE_URL, SMART_REMOTE_USER, "");
    }

    private static AuthInfo createSignageRemoteAuthInfo(D_HistoryInfo d_HistoryInfo) {
        return new AuthInfo(d_HistoryInfo, "http://" + NetUtils.cvtIPAddr(d_HistoryInfo.ipAddr) + "/webremote/index.html", SIGNAGE_REMOTE_USER, SIGNAGE_REMOTE_PASS);
    }
}
