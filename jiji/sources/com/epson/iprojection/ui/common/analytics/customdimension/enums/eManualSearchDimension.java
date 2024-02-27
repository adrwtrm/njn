package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum eManualSearchDimension {
    succeeded("成功"),
    failed("失敗"),
    error("エラー");
    
    private final String dimension;

    eManualSearchDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }
}
