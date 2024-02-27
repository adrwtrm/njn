package com.epson.iprojection.linkagedata.decoder.qr;

import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;

/* loaded from: classes.dex */
public class QRDecoderV0 extends AbstractLinkageDataDecoder {
    @Override // com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    public int decode(byte[] bArr, D_LinkageData d_LinkageData) throws LinkageDataFormatException, LinkageDataVersionException {
        int decodeConnectSettings = 1 + decodeConnectSettings(d_LinkageData, bArr, 1);
        int decodeWireSettings = decodeConnectSettings + decodeWireSettings(d_LinkageData, bArr, decodeConnectSettings);
        int decodeWiredIP = decodeWireSettings + decodeWiredIP(d_LinkageData, bArr, decodeWireSettings);
        int decodeWirelessIP = decodeWiredIP + decodeWirelessIP(d_LinkageData, bArr, decodeWiredIP);
        int decodePjNameOrMacAddr = decodeWirelessIP + decodePjNameOrMacAddr(d_LinkageData, bArr, decodeWirelessIP);
        int decodePassword = decodePjNameOrMacAddr + decodePassword(d_LinkageData, bArr, decodePjNameOrMacAddr);
        int decodeSSID = decodePassword + decodeSSID(d_LinkageData, bArr, decodePassword);
        setType(d_LinkageData);
        return decodeSSID;
    }

    protected int decodeConnectSettings(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataFormatException {
        d_LinkageData.isEasyConnect = (bArr[i] & 1) == 0;
        d_LinkageData.securityType = ((bArr[i] >> 1) & 1) == 0 ? D_LinkageData.SecurityType.eNone : D_LinkageData.SecurityType.eWPA2_PSK_AES;
        d_LinkageData.isAutoSSID = ((bArr[i] >> 2) & 1) == 0;
        if (((bArr[i] >> 3) & 1) == 0) {
            return 1;
        }
        throw new LinkageDataFormatException("フォーマットエラー");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int decodeWireSettings(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataVersionException {
        d_LinkageData.isAvailableWired = (bArr[i] & 1) != 0;
        d_LinkageData.isAvailableWireless = ((bArr[i] >> 1) & 1) != 0;
        d_LinkageData.isPriorWireless = ((bArr[i] >> 2) & 1) == 0;
        return 1;
    }

    protected int decodeWiredIP(D_LinkageData d_LinkageData, byte[] bArr, int i) {
        if (d_LinkageData.isAvailableWired) {
            d_LinkageData.wiredIP[0] = bArr[i + 0] & 255;
            d_LinkageData.wiredIP[1] = bArr[i + 1] & 255;
            d_LinkageData.wiredIP[2] = bArr[i + 2] & 255;
            d_LinkageData.wiredIP[3] = bArr[i + 3] & 255;
            return 4;
        }
        return 0;
    }

    protected int decodeWirelessIP(D_LinkageData d_LinkageData, byte[] bArr, int i) {
        if (d_LinkageData.isAvailableWireless) {
            d_LinkageData.wirelessIP[0] = bArr[i + 0] & 255;
            d_LinkageData.wirelessIP[1] = bArr[i + 1] & 255;
            d_LinkageData.wirelessIP[2] = bArr[i + 2] & 255;
            d_LinkageData.wirelessIP[3] = bArr[i + 3] & 255;
            return 4;
        }
        return 0;
    }

    protected boolean isWrittenMacAddr(byte[] bArr, int i) {
        return ((bArr[i] >> 7) & 1) == 1;
    }

    protected int decodePjNameOrMacAddr(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataFormatException {
        if (isWrittenMacAddr(bArr, i)) {
            return decodeMacAddr(d_LinkageData, bArr, i + 1) + 1;
        }
        return decodePjName(d_LinkageData, bArr, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int decodeMacAddr(D_LinkageData d_LinkageData, byte[] bArr, int i) {
        d_LinkageData.macAddr[0] = bArr[i + 0] & 255;
        d_LinkageData.macAddr[1] = bArr[i + 1] & 255;
        d_LinkageData.macAddr[2] = bArr[i + 2] & 255;
        d_LinkageData.macAddr[3] = bArr[i + 3] & 255;
        d_LinkageData.macAddr[4] = bArr[i + 4] & 255;
        d_LinkageData.macAddr[5] = bArr[i + 5] & 255;
        return 6;
    }

    protected int decodePjName(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataFormatException {
        int i2 = bArr[i] & Byte.MAX_VALUE;
        if (i2 <= 0 || i2 > 6) {
            throw new LinkageDataFormatException("PJ名の長さ違反");
        }
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = bArr[i + 1 + i3];
        }
        d_LinkageData.pjName = new String(bArr2);
        return i2 + 1;
    }

    protected boolean isWrittenPassword(D_LinkageData d_LinkageData) {
        return d_LinkageData.isEasyConnect && d_LinkageData.securityType == D_LinkageData.SecurityType.eWPA2_PSK_AES;
    }

    protected int decodePassword(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataFormatException {
        if (isWrittenPassword(d_LinkageData)) {
            int i2 = bArr[i];
            if (i2 < 0 || i2 > 63) {
                throw new LinkageDataFormatException("パスフレーズの長さ違反");
            }
            if (i2 != 0) {
                byte[] bArr2 = new byte[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    byte b = bArr[i + 1 + i3];
                    if (b == 0) {
                        break;
                    }
                    bArr2[i3] = b;
                }
                d_LinkageData.password = new String(bArr2);
            }
            return i2 + 1;
        }
        return 0;
    }

    protected boolean isWrittenSSID(D_LinkageData d_LinkageData) {
        return d_LinkageData.isEasyConnect;
    }

    protected int decodeSSID(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataFormatException {
        if (isWrittenSSID(d_LinkageData)) {
            int i2 = bArr[i];
            if (i2 < 0 || i2 > 32) {
                throw new LinkageDataFormatException("SSIDの長さ違反");
            }
            if (i2 != 0) {
                byte[] bArr2 = new byte[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    byte b = bArr[i + 1 + i3];
                    if (b == 0) {
                        break;
                    }
                    bArr2[i3] = b;
                }
                d_LinkageData.ssid = new String(bArr2);
            }
            return i2 + 1;
        }
        return 0;
    }

    @Override // com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    protected void setType(D_LinkageData d_LinkageData) {
        d_LinkageData.type = D_LinkageData.Type.TYPE_PJCONNECT;
    }
}
