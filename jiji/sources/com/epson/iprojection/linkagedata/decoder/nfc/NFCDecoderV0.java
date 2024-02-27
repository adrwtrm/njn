package com.epson.iprojection.linkagedata.decoder.nfc;

import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.data.D_PjStatusData;
import com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV2;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;

/* loaded from: classes.dex */
public class NFCDecoderV0 extends QRDecoderV2 {
    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV2, com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0, com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    public int decode(byte[] bArr, D_LinkageData d_LinkageData) throws LinkageDataFormatException, LinkageDataVersionException {
        int decode = super.decode(bArr, d_LinkageData);
        return decode + decodePJStatus(d_LinkageData, bArr, decode);
    }

    protected int decodePJStatus(D_LinkageData d_LinkageData, byte[] bArr, int i) {
        d_LinkageData.pjStatus = new D_PjStatusData();
        d_LinkageData.pjStatus.isPJPowerOn = (bArr[i] & 1) != 0;
        d_LinkageData.pjStatus.isNFCAutoPowerOn = ((bArr[i] >> 1) & 1) != 0;
        d_LinkageData.pjStatus.isSetWireless = ((bArr[i] >> 2) & 1) != 0;
        d_LinkageData.pjStatus.isWirelessPowerOn = ((bArr[i] >> 3) & 1) != 0;
        d_LinkageData.pjStatus.isWirelessAuthenticFix = ((bArr[i] >> 4) & 1) != 0;
        d_LinkageData.pjStatus.isWirelessAuthenticSuccess = ((bArr[i] >> 5) & 1) != 0;
        return 1;
    }

    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV2
    protected int decodeType(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataVersionException {
        if (((bArr[i] >> 3) & 31) == 0) {
            d_LinkageData.type = D_LinkageData.Type.TYPE_PJCONNECT;
            return 1;
        }
        throw new LinkageDataVersionException("種別が不正");
    }
}
