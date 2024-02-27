package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum eRegisteredDimension {
    auto("自動検索"),
    ip("IP指定検索"),
    profile("プロファイル検索"),
    history("履歴接続"),
    qr("QRコード"),
    nfc("NFC"),
    error("エラー");
    
    private final String dimension;

    eRegisteredDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }
}
