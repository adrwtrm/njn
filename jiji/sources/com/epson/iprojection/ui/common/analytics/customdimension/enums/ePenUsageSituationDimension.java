package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum ePenUsageSituationDimension {
    usePen1("ペン1のみ使用"),
    usePen2("ペン2のみ使用"),
    both("両方使用"),
    noUsed("使用しなかった"),
    error("エラー");
    
    private final String dimension;

    ePenUsageSituationDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }
}
