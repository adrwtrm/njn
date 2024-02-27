package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SupportEvent.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0007R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/SupportEvent;", "", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;)V", "_eventSender", "_supportEventType", "Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;", "clear", "", "send", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "setSupportEventType", "eventType", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class SupportEvent {
    private IEventSender _eventSender;
    private eCustomEvent _supportEventType;

    public SupportEvent(CustomEventSender customEventSender) {
        this._eventSender = customEventSender == null ? new CustomEventSender() : customEventSender;
    }

    public final void setSupportEventType(eCustomEvent eventType) {
        Intrinsics.checkNotNullParameter(eventType, "eventType");
        if (eventType == eCustomEvent.USAGE_TIPS || eventType == eCustomEvent.MANUAL || eventType == eCustomEvent.SUPPORTED_PROJECTORS_LIST || eventType == eCustomEvent.EPSON_SETUP_NAVI || eventType == eCustomEvent.SUPPORT_SCREEN_DISPLAY_FROM_HOME || eventType == eCustomEvent.SUPPORT_SCREEN_DISPLAY_FROM_DRAWER) {
            this._supportEventType = eventType;
        }
    }

    public final void send(FirebaseAnalytics firebaseAnalytics, Bundle params) {
        Intrinsics.checkNotNullParameter(params, "params");
        if (this._supportEventType == null) {
            return;
        }
        IEventSender iEventSender = this._eventSender;
        Intrinsics.checkNotNull(iEventSender);
        Intrinsics.checkNotNull(firebaseAnalytics);
        eCustomEvent ecustomevent = this._supportEventType;
        Intrinsics.checkNotNull(ecustomevent);
        iEventSender.send(firebaseAnalytics, params, ecustomevent.getEventName());
        clear();
    }

    private final void clear() {
        this._supportEventType = null;
    }
}
