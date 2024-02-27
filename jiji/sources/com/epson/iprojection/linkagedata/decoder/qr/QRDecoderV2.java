package com.epson.iprojection.linkagedata.decoder.qr;

import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;

/* loaded from: classes.dex */
public class QRDecoderV2 extends QRDecoderV0 {
    protected static final int TYPE_PJCONNECT = 0;
    protected static final int TYPE_WHITEBOARD = 1;

    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0
    protected boolean isWrittenSSID(D_LinkageData d_LinkageData) {
        return true;
    }

    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0, com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    protected void setType(D_LinkageData d_LinkageData) {
    }

    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0, com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    public int decode(byte[] bArr, D_LinkageData d_LinkageData) throws LinkageDataFormatException, LinkageDataVersionException {
        return super.decode(bArr, d_LinkageData);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0
    public int decodeWireSettings(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataVersionException {
        super.decodeWireSettings(d_LinkageData, bArr, i);
        decodeType(d_LinkageData, bArr, i);
        return 1;
    }

    protected int decodeType(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataVersionException {
        int i2 = (bArr[i] >> 3) & 31;
        if (i2 == 0) {
            d_LinkageData.type = D_LinkageData.Type.TYPE_PJCONNECT;
        } else if (i2 == 1) {
            d_LinkageData.type = D_LinkageData.Type.TYPE_WHITEBOARD;
        } else {
            throw new LinkageDataVersionException("種別が不正");
        }
        return 1;
    }

    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0
    protected int decodePjNameOrMacAddr(D_LinkageData d_LinkageData, byte[] bArr, int i) throws LinkageDataFormatException {
        return super.decodeMacAddr(d_LinkageData, bArr, i);
    }
}
