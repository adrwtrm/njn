package com.epson.iprojection.ui.common.activity.funcs;

/* loaded from: classes.dex */
public class IntentCalledState {
    public boolean _called = false;
    public boolean _backed = false;

    public void clear() {
        this._called = false;
        this._backed = false;
    }

    public boolean isCallable() {
        return (this._called || this._backed) ? false : true;
    }
}
