package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum eDefinitionFileDimension {
    Success("読み込み成功"),
    Failure("読み込み失敗"),
    error("エラー");
    
    private final String dimension;

    eDefinitionFileDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }

    public static String stringOf(int i) {
        eDefinitionFileDimension[] values;
        for (eDefinitionFileDimension edefinitionfiledimension : values()) {
            if (edefinitionfiledimension.ordinal() == i) {
                return edefinitionfiledimension.getString();
            }
        }
        return null;
    }
}
