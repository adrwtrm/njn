package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePermissionChangeDimension;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class PermissionChangeEvent {
    private final IEventSender _eventSender;
    private ePermissionChangeDimension _permissionChangeDimension;
    private boolean isChanged = false;

    protected String getDimensionName() {
        return "";
    }

    protected String getEventName() {
        return "";
    }

    public PermissionChangeEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void set(ePermissionChangeDimension epermissionchangedimension) {
        this._permissionChangeDimension = epermissionchangedimension;
        this.isChanged = true;
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle, String str) {
        if (this.isChanged) {
            bundle.putString(getDimensionName(), str + "で" + getDimensionParam());
            this._eventSender.send(firebaseAnalytics, bundle, getEventName());
            clear();
        }
    }

    private void clear() {
        this._permissionChangeDimension = null;
        this.isChanged = false;
    }

    private String getDimensionParam() {
        ePermissionChangeDimension epermissionchangedimension = this._permissionChangeDimension;
        if (epermissionchangedimension == null) {
            return ePermissionChangeDimension.error.getString();
        }
        return epermissionchangedimension.getString();
    }
}
