package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum eRequestTransferScreenDimension {
    projectionScreen("投写画面"),
    white("白紙"),
    error("エラー");
    
    private final String dimension;

    eRequestTransferScreenDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }
}
