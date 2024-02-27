package com.epson.iprojection.ui.common.analytics.customdimension.enums;

/* loaded from: classes.dex */
public enum ePermissionChangeDimension {
    Allow("許可した"),
    Deny("許可しなかった"),
    error("エラー");
    
    private final String dimension;

    ePermissionChangeDimension(String str) {
        this.dimension = str;
    }

    public String getString() {
        return this.dimension;
    }

    public static String stringOf(int i) {
        ePermissionChangeDimension[] values;
        for (ePermissionChangeDimension epermissionchangedimension : values()) {
            if (epermissionchangedimension.ordinal() == i) {
                return epermissionchangedimension.getString();
            }
        }
        return null;
    }
}
