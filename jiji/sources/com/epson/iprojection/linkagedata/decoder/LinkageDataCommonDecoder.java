package com.epson.iprojection.linkagedata.decoder;

import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.decoder.nfc.NFCDecoderV0;
import com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV0;
import com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV1;
import com.epson.iprojection.linkagedata.decoder.qr.QRDecoderV2;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.linkagedata.exception.LinkageDataVersionException;
import com.google.common.base.Ascii;

/* loaded from: classes.dex */
public class LinkageDataCommonDecoder {
    private static final byte QRCODE_DATA_FMT_MARKER = Byte.MIN_VALUE;
    private static final int XOR_KEY = 229;

    public static D_LinkageData decode(byte[] bArr, D_LinkageData.Mode mode) throws LinkageDataFormatException, LinkageDataVersionException {
        D_LinkageData d_LinkageData = new D_LinkageData();
        d_LinkageData.mode = mode;
        commonDataCheck(bArr);
        d_LinkageData.version = decodeVersion(bArr);
        createLinkageDataDecoder(d_LinkageData.mode, d_LinkageData.version).decode(bArr, d_LinkageData);
        return d_LinkageData;
    }

    private static void commonDataCheck(byte[] bArr) throws LinkageDataFormatException {
        int i;
        if (bArr.length <= 0) {
            throw new LinkageDataFormatException("データーが短すぎる");
        }
        int i2 = bArr[0] & 255;
        if (i2 > bArr.length) {
            throw new LinkageDataFormatException("非対応のデータ:" + i2 + " ベース:" + bArr.length);
        }
        if (bArr[i2 - 1] != Byte.MIN_VALUE) {
            throw new LinkageDataFormatException("チェックコードエラー[" + ((int) bArr[i]) + "]");
        }
    }

    private static int decodeVersion(byte[] bArr) {
        return (bArr[1] >> 4) & 15;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.linkagedata.decoder.LinkageDataCommonDecoder$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$Mode;

        static {
            int[] iArr = new int[D_LinkageData.Mode.values().length];
            $SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$Mode = iArr;
            try {
                iArr[D_LinkageData.Mode.MODE_QR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$Mode[D_LinkageData.Mode.MODE_NFC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static AbstractLinkageDataDecoder createLinkageDataDecoder(D_LinkageData.Mode mode, int i) throws LinkageDataVersionException {
        int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$linkagedata$data$D_LinkageData$Mode[mode.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                if (i == 0) {
                    return new NFCDecoderV0();
                }
                throw new LinkageDataVersionException("バージョンが不正");
            }
            throw new LinkageDataVersionException("方法が不正");
        } else if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    return new QRDecoderV2();
                }
                throw new LinkageDataVersionException("バージョンが不正");
            }
            return new QRDecoderV1();
        } else {
            return new QRDecoderV0();
        }
    }

    public static byte[] getContentData(byte[] bArr, D_LinkageData.Mode mode) {
        if (mode == D_LinkageData.Mode.MODE_QR) {
            return getContentDataQr(bArr);
        }
        return getContentData(bArr);
    }

    private static byte[] getContentData(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = (byte) (bArr[i] ^ 229);
        }
        return bArr2;
    }

    private static byte[] getContentDataQr(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length - 1];
        int i = 1;
        while (i < bArr.length - 1) {
            int i2 = i - 1;
            i++;
            bArr2[i2] = (byte) ((((bArr[i] & Ascii.SI) << 4) | ((bArr[i] >> 4) & 15)) ^ XOR_KEY);
        }
        return bArr2;
    }
}
