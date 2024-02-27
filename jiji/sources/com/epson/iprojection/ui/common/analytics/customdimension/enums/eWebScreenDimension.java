package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum eWebScreenDimension {
    explicitIntents("明示的インテント"),
    implicitIntents("暗黙的インテント"),
    error("エラー");
    
    private final String dimensionParam;

    eWebScreenDimension(String str) {
        this.dimensionParam = str;
    }

    public String getString() {
        return this.dimensionParam;
    }
}
