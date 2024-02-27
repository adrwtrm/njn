package com.epson.iprojection.ui.common.activitystatus;

/* loaded from: classes.dex */
public class ActivityKillStatus {
    private static final ActivityKillStatus _inst = new ActivityKillStatus();
    private boolean _isKilling = false;
    private boolean _isTillContents = false;

    public boolean isKilling() {
        return this._isKilling;
    }

    public void startKill() {
        this._isKilling = true;
    }

    public void stopKill() {
        this._isKilling = false;
    }

    public void setTillContents(boolean z) {
        this._isTillContents = z;
    }

    public boolean isTillContents() {
        return this._isTillContents;
    }

    private ActivityKillStatus() {
    }

    public static ActivityKillStatus getIns() {
        return _inst;
    }
}
