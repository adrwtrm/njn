package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SatisfactionEvent.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0007J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\tR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/SatisfactionEvent;", "", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;)V", "_eventSender", "_satisfactionEventType", "Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;", "_satisfactionResult", "", "clear", "", "send", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "setSatisfactionEventType", "eventType", "setSatisfactionResult", "result", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class SatisfactionEvent {
    private IEventSender _eventSender;
    private eCustomEvent _satisfactionEventType;
    private int _satisfactionResult;

    public SatisfactionEvent(CustomEventSender customEventSender) {
        this._eventSender = customEventSender == null ? new CustomEventSender() : customEventSender;
    }

    public final void setSatisfactionEventType(eCustomEvent eventType) {
        Intrinsics.checkNotNullParameter(eventType, "eventType");
        if (eventType == eCustomEvent.SATISFACTION_UI_DISPLAY_LAUNCH || eventType == eCustomEvent.SATISFACTION_UI_DISPLAY_OPERATION_END || eventType == eCustomEvent.SATISFACTION_UI_SEND_LAUNCH || eventType == eCustomEvent.SATISFACTION_UI_SEND_OPERATION_END || eventType == eCustomEvent.SATISFACTION_UI_CANCEL_LAUNCH || eventType == eCustomEvent.SATISFACTION_UI_CANCEL_OPERATION_END) {
            this._satisfactionEventType = eventType;
        }
    }

    public final void setSatisfactionResult(int i) {
        this._satisfactionResult = i;
    }

    public final void send(FirebaseAnalytics firebaseAnalytics, Bundle params) {
        Intrinsics.checkNotNullParameter(params, "params");
        eCustomEvent ecustomevent = this._satisfactionEventType;
        if (ecustomevent == null) {
            return;
        }
        if (ecustomevent == eCustomEvent.SATISFACTION_UI_SEND_LAUNCH || this._satisfactionEventType == eCustomEvent.SATISFACTION_UI_SEND_OPERATION_END) {
            params.putString(eCustomDimension.SEARCH_RESULT.getDimensionName(), String.valueOf(this._satisfactionResult));
        }
        IEventSender iEventSender = this._eventSender;
        Intrinsics.checkNotNull(iEventSender);
        Intrinsics.checkNotNull(firebaseAnalytics);
        eCustomEvent ecustomevent2 = this._satisfactionEventType;
        Intrinsics.checkNotNull(ecustomevent2);
        iEventSender.send(firebaseAnalytics, params, ecustomevent2.getEventName());
        clear();
    }

    private final void clear() {
        this._satisfactionEventType = null;
        this._satisfactionResult = 0;
    }
}
