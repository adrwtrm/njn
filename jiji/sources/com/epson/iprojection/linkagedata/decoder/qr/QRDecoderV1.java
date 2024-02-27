package com.epson.iprojection.linkagedata.decoder.qr;

import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;

/* loaded from: classes.dex */
public class QRDecoderV1 extends QRDecoderV0 {
    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0, com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    public int decode(byte[] bArr, D_LinkageData d_LinkageData) throws LinkageDataFormatException, LinkageDataVersionException {
        return super.decode(bArr, d_LinkageData);
    }

    @Override // com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0, com.epson.iprojection.linkagedata.decoder.AbstractLinkageDataDecoder
    protected void setType(D_LinkageData d_LinkageData) {
        d_LinkageData.type = D_LinkageData.Type.TYPE_WHITEBOARD;
    }
}
