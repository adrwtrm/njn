package com.epson.iprojection.linkagedata.data;

import android.content.Context;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.ui.activities.pjselect.WifiConnector;

/* loaded from: classes.dex */
public class D_LinkageData {
    public static final int DATA_NFC_VER_0 = 0;
    public static final int DATA_QR_VER_0 = 0;
    public static final int DATA_QR_VER_1 = 1;
    public static final int DATA_QR_VER_2 = 2;
    public boolean isEasyConnect;
    public Mode mode;
    public String password;
    public String pjName;
    public String ssid;
    public Type type;
    public int version;
    public int[] macAddr = new int[6];
    public int[] wirelessIP = new int[4];
    public int[] wiredIP = new int[4];
    public SecurityType securityType = SecurityType.eNone;
    public boolean isAutoSSID = true;
    public boolean isAvailableWireless = true;
    public boolean isAvailableWired = false;
    public boolean isPriorWireless = true;
    public D_PjStatusData pjStatus = null;

    /* loaded from: classes.dex */
    public enum Mode {
        MODE_QR,
        MODE_NFC
    }

    /* loaded from: classes.dex */
    public enum SecurityType {
        eNone,
        eWPA2_PSK_AES
    }

    /* loaded from: classes.dex */
    public enum Type {
        TYPE_PJCONNECT,
        TYPE_WHITEBOARD
    }

    public boolean isPreferredPhysicalNetworkAvailable() {
        return this.isAvailableWireless == this.isPriorWireless;
    }

    public boolean hasMacAddr() {
        for (int i : this.macAddr) {
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isMyMacAddr(byte[] bArr) {
        if (hasMacAddr()) {
            for (int i = 0; i < 6; i++) {
                if (((byte) this.macAddr[i]) != bArr[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isMyIp(byte[] bArr) {
        if (this.isAvailableWireless && isSameIp(this.wirelessIP, bArr)) {
            return true;
        }
        if (this.isAvailableWired) {
            return isSameIp(this.wiredIP, bArr);
        }
        return false;
    }

    public boolean isMyPrimaryIp(byte[] bArr) {
        if (this.isAvailableWireless && this.isPriorWireless) {
            return isSameIp(this.wirelessIP, bArr);
        }
        if (this.isAvailableWired && !this.isPriorWireless) {
            return isSameIp(this.wiredIP, bArr);
        }
        return isMyIp(bArr);
    }

    public boolean hasIp() {
        return this.isAvailableWireless | this.isAvailableWired;
    }

    private boolean isSameIp(int[] iArr, byte[] bArr) {
        for (int i = 0; i < 4; i++) {
            if (((byte) iArr[i]) != bArr[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean needChangeWifi(Context context) {
        if (!this.isEasyConnect || this.isAutoSSID) {
            if (this.mode != Mode.MODE_QR || this.version >= 2 || this.isEasyConnect) {
                return !WifiConnector.isEnableAP(context, this.ssid);
            }
            return false;
        }
        return false;
    }

    public boolean isSameWirelessIp(byte[] bArr) {
        return isSameIp(this.wirelessIP, bArr);
    }

    public boolean isWhiteboard() {
        return this.type == Type.TYPE_WHITEBOARD;
    }

    public String getPrimaryIpAddress() {
        boolean z = this.isPriorWireless;
        if (z && this.isAvailableWireless) {
            return NetUtils.cvtIPAddr(this.wirelessIP);
        }
        if (z || !this.isAvailableWired) {
            return null;
        }
        return NetUtils.cvtIPAddr(this.wiredIP);
    }

    public String getSecondaryIpAddress() {
        boolean z = this.isPriorWireless;
        if (z && this.isAvailableWired) {
            return NetUtils.cvtIPAddr(this.wiredIP);
        }
        if (z || !this.isAvailableWireless) {
            return null;
        }
        return NetUtils.cvtIPAddr(this.wirelessIP);
    }
}
