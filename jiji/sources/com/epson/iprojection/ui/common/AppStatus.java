package com.epson.iprojection.ui.common;

/* loaded from: classes.dex */
public final class AppStatus {
    private static final AppStatus _inst = new AppStatus();
    public boolean isActivated = false;
    public boolean _isAppForeground = false;

    private AppStatus() {
    }

    public static AppStatus getIns() {
        return _inst;
    }
}
