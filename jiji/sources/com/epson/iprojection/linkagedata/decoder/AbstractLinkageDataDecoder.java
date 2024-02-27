package com.epson.iprojection.linkagedata.decoder;

import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;

/* loaded from: classes.dex */
public abstract class AbstractLinkageDataDecoder {
    public abstract int decode(byte[] bArr, D_LinkageData d_LinkageData) throws LinkageDataFormatException, LinkageDataVersionException;

    protected abstract void setType(D_LinkageData d_LinkageData);
}
