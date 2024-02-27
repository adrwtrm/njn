package com.epson.iprojection.ui.activities.pjselect.qrcode;

import kotlin.Metadata;

/* compiled from: QRDataHolder.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/qrcode/QRDataHolder;", "", "()V", "qrRowData", "", "getQrRowData", "()[B", "setQrRowData", "([B)V", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class QRDataHolder {
    public static final QRDataHolder INSTANCE = new QRDataHolder();
    private static byte[] qrRowData;

    private QRDataHolder() {
    }

    public final byte[] getQrRowData() {
        return qrRowData;
    }

    public final void setQrRowData(byte[] bArr) {
        qrRowData = bArr;
    }
}
