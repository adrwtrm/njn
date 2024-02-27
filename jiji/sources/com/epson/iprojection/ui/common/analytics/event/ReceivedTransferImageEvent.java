package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class ReceivedTransferImageEvent {
    private final IEventSender _eventSender;

    public ReceivedTransferImageEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
    }

    private String getEventName() {
        return eCustomEvent.RECEIVED_TRANSFER_IMAGE.getEventName();
    }
}
