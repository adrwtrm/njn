package com.epson.iprojection.ui.activities.pjselect.nfc;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Parcelable;
import android.util.Base64;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.decoder.LinkageDataCommonDecoder;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;

/* loaded from: classes.dex */
public class NfcDataManager {
    private static final NfcDataManager _inst = new NfcDataManager();
    private byte[] _binaryData = null;
    private D_LinkageData _nfcData = null;
    private boolean _isEventOccured = false;

    /* loaded from: classes.dex */
    public enum NfcAlertType {
        ALERT_NONE,
        ALERT_ALREADY_CONNECT,
        ALERT_NO_WIRELESS_LAN_UNIT,
        ALERT_WIRELESS_MODE_OFF,
        ALERT_RETRY_AND_POWER_ON,
        ALERT_CHECK_NETWORK_SETTING
    }

    public void setIsEventOccured(boolean z) {
        this._isEventOccured = z;
    }

    public boolean isEventOccured() {
        return this._isEventOccured;
    }

    public byte[] getBinaryData() {
        return this._binaryData;
    }

    public void setBinaryData(byte[] bArr) {
        this._binaryData = bArr;
    }

    public void clearBinaryData() {
        this._binaryData = null;
    }

    public boolean hasNoConnectData() {
        return this._binaryData != null;
    }

    public void decodeRawBytesAndStack(Intent intent) {
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
        if (parcelableArrayExtra != null) {
            NdefMessage[] ndefMessageArr = new NdefMessage[parcelableArrayExtra.length];
            for (int i = 0; i < parcelableArrayExtra.length; i++) {
                NdefMessage ndefMessage = (NdefMessage) parcelableArrayExtra[i];
                ndefMessageArr[i] = ndefMessage;
                for (NdefRecord ndefRecord : ndefMessage.getRecords()) {
                    byte[] payload = ndefRecord.getPayload();
                    if (payload != null) {
                        this._binaryData = LinkageDataCommonDecoder.getContentData(Base64.decode(new String(payload), 2), D_LinkageData.Mode.MODE_NFC);
                        return;
                    }
                }
            }
        }
    }

    public D_LinkageData getNfcData() throws LinkageDataFormatException, LinkageDataVersionException {
        byte[] bArr = this._binaryData;
        if (bArr != null) {
            this._nfcData = LinkageDataCommonDecoder.decode(bArr, D_LinkageData.Mode.MODE_NFC);
            this._binaryData = null;
        }
        return this._nfcData;
    }

    public NfcAlertType checkPjStatus() throws LinkageDataFormatException, LinkageDataVersionException {
        D_LinkageData nfcData = getNfcData();
        if (nfcData != null) {
            if (!nfcData.pjStatus.isSetWireless) {
                return NfcAlertType.ALERT_NO_WIRELESS_LAN_UNIT;
            }
            if (!nfcData.pjStatus.isWirelessPowerOn) {
                return NfcAlertType.ALERT_WIRELESS_MODE_OFF;
            }
            if (!nfcData.pjStatus.isPJPowerOn && !nfcData.pjStatus.isNFCAutoPowerOn) {
                return NfcAlertType.ALERT_RETRY_AND_POWER_ON;
            }
            if (nfcData.pjStatus.isWirelessAuthenticFix && !nfcData.pjStatus.isWirelessAuthenticSuccess) {
                return NfcAlertType.ALERT_CHECK_NETWORK_SETTING;
            }
        }
        return NfcAlertType.ALERT_NONE;
    }

    private NfcDataManager() {
    }

    public static NfcDataManager getIns() {
        return _inst;
    }
}
