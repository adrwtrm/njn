package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ProjectorHeaderEvent.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/ProjectorHeaderEvent;", "", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;)V", "_eventSender", "getEventName", "", "send", "", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ProjectorHeaderEvent {
    private IEventSender _eventSender;

    public ProjectorHeaderEvent(CustomEventSender customEventSender) {
        this._eventSender = customEventSender == null ? new CustomEventSender() : customEventSender;
    }

    public final void send(FirebaseAnalytics firebaseAnalytics, Bundle params) {
        Intrinsics.checkNotNullParameter(firebaseAnalytics, "firebaseAnalytics");
        Intrinsics.checkNotNullParameter(params, "params");
        this._eventSender.send(firebaseAnalytics, params, getEventName());
    }

    private final String getEventName() {
        return eCustomEvent.PROJECTOR_HEADER.getEventName();
    }
}
