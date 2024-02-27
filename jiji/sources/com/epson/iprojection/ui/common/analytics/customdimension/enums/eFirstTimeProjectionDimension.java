package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum eFirstTimeProjectionDimension {
    def("デフォルト"),
    photo("写真"),
    document("ドキュメント"),
    web("Web"),
    camera("カメラ"),
    mirroring("端末の画面"),
    receivedImage("受信画像"),
    error("エラー"),
    done("完了");
    
    private final String dimension;

    eFirstTimeProjectionDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }
}
