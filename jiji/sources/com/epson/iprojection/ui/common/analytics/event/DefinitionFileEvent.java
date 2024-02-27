package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eDefinitionFileDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePermissionChangeDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class DefinitionFileEvent {
    private eDefinitionFileDimension _definitionFileDimension;
    private final IEventSender _eventSender;
    public boolean isChanged = false;

    public DefinitionFileEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    private String getEventName() {
        return eCustomEvent.DEFINITION_FILE.getEventName();
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        if (this.isChanged) {
            bundle.putString(eCustomDimension.DEFINITION_FILE_READ_RESULT.getDimensionName(), getDimensionParam());
            this._eventSender.send(firebaseAnalytics, bundle, getEventName());
            clear();
        }
    }

    public void setDefinitionFileDimension(eDefinitionFileDimension edefinitionfiledimension) {
        this._definitionFileDimension = edefinitionfiledimension;
        this.isChanged = true;
    }

    private String getDimensionParam() {
        eDefinitionFileDimension edefinitionfiledimension = this._definitionFileDimension;
        if (edefinitionfiledimension == null) {
            return ePermissionChangeDimension.error.getString();
        }
        return edefinitionfiledimension.getString();
    }

    protected void clear() {
        this.isChanged = false;
    }
}
