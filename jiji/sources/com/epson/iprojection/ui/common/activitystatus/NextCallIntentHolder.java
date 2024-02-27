package com.epson.iprojection.ui.common.activitystatus;

import android.content.Intent;

/* loaded from: classes.dex */
public class NextCallIntentHolder {
    private static final NextCallIntentHolder _inst = new NextCallIntentHolder();
    private Intent _intent;

    public void set(Intent intent) {
        this._intent = intent;
    }

    public Intent get() {
        return this._intent;
    }

    public void clear() {
        this._intent = null;
    }

    public boolean exists() {
        return this._intent != null;
    }

    private NextCallIntentHolder() {
    }

    public static NextCallIntentHolder getIns() {
        return _inst;
    }
}
