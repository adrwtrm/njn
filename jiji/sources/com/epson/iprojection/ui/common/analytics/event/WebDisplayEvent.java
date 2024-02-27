package com.epson.iprojection.ui.common.analytics.event;

import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WebDisplayEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/WebDisplayEvent;", "Lcom/epson/iprojection/ui/common/analytics/event/ContentsEvent;", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;)V", "setEventType", "", "eventType", "Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WebDisplayEvent extends ContentsEvent {
    public WebDisplayEvent(IEventSender iEventSender) {
        super(iEventSender);
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.ContentsEvent
    public void setEventType(eCustomEvent eventType) {
        Intrinsics.checkNotNullParameter(eventType, "eventType");
        if (eventType == eCustomEvent.WEB_DISPLAY_START || eventType == eCustomEvent.WEB_DISPLAY_END) {
            set_eventType(eventType);
        }
    }
}
