package com.epson.iprojection.ui.activities.pjselect.control;

import com.epson.iprojection.engine.common.D_PjInfo;
import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes.dex */
public class D_HistoryInfo implements Serializable {
    private static final long serialVersionUID = 1;
    public byte[] ipAddr;
    public boolean isSmartphoneRemote;
    public boolean isSupportQR;
    public boolean isSupportSignage;
    public boolean isSupportWebRemote;
    public boolean isSupportedSecure;
    public byte[] macAddr;
    public int pjID;
    public String pjName;

    /* loaded from: classes.dex */
    public enum PasswordType {
        PASSWORD_REMOTE,
        PASSWORD_WEB_CONTROL
    }

    /* loaded from: classes.dex */
    public enum RemoteType {
        REMOTE_OLD,
        REMOTE_WEB,
        REMOTE_SMART,
        REMOTE_SIGNAGE
    }

    public static D_HistoryInfo convertFromPjInfo(D_PjInfo d_PjInfo) {
        D_HistoryInfo d_HistoryInfo = new D_HistoryInfo();
        d_HistoryInfo.pjName = d_PjInfo.PrjName;
        d_HistoryInfo.ipAddr = d_PjInfo.IPAddr;
        d_HistoryInfo.macAddr = d_PjInfo.UniqInfo;
        d_HistoryInfo.isSupportQR = d_PjInfo.bQRConnect;
        d_HistoryInfo.isSmartphoneRemote = d_PjInfo.isSmartphoneRemote;
        d_HistoryInfo.isSupportWebRemote = d_PjInfo.isSupportWebRemote;
        d_HistoryInfo.isSupportSignage = d_PjInfo.isSignageMode;
        d_HistoryInfo.isSupportedSecure = d_PjInfo.isSupportedSecuredEscvp;
        d_HistoryInfo.pjID = d_PjInfo.ProjectorID;
        return d_HistoryInfo;
    }

    public D_PjInfo convertToPjInfo() {
        D_PjInfo d_PjInfo = new D_PjInfo();
        d_PjInfo.PrjName = this.pjName;
        byte[] bArr = this.ipAddr;
        d_PjInfo.IPAddr = Arrays.copyOf(bArr, bArr.length);
        byte[] bArr2 = this.macAddr;
        d_PjInfo.UniqInfo = Arrays.copyOf(bArr2, bArr2.length);
        d_PjInfo.bQRConnect = this.isSupportQR;
        d_PjInfo.isSmartphoneRemote = this.isSmartphoneRemote;
        d_PjInfo.isSupportWebRemote = this.isSupportWebRemote;
        d_PjInfo.isSignageMode = this.isSupportSignage;
        d_PjInfo.isSupportedSecuredEscvp = this.isSupportedSecure;
        d_PjInfo.ProjectorID = 0;
        return d_PjInfo;
    }

    public RemoteType getRemoteType() {
        if (this.isSupportSignage) {
            return RemoteType.REMOTE_SIGNAGE;
        }
        if (this.isSmartphoneRemote) {
            return RemoteType.REMOTE_SMART;
        }
        if (this.isSupportWebRemote) {
            return RemoteType.REMOTE_WEB;
        }
        return RemoteType.REMOTE_OLD;
    }

    public PasswordType getPasswordType() {
        if (this.isSupportSignage) {
            return PasswordType.PASSWORD_WEB_CONTROL;
        }
        return PasswordType.PASSWORD_REMOTE;
    }

    public boolean isSupportRemote() {
        return getRemoteType() != RemoteType.REMOTE_OLD;
    }
}
