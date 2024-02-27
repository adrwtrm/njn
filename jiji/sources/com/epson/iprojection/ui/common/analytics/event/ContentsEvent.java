package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ContentsEvent.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001:\u0001!B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0006\u0010\u0018\u001a\u00020\u0017J\u0018\u0010\u0019\u001a\u00020\u00172\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\tH&J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/ContentsEvent;", "", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;)V", "_count", "", "_eventSender", "_eventType", "Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;", "get_eventType", "()Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;", "set_eventType", "(Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;)V", "_shouldSendEndEvent", "", "_shouldSendStartEvent", "_startTime", "", "getCount", "", "getTime", "onStart", "", "resetCount", "send", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "setEventType", "eventType", "setStartParam", "EContentsType", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class ContentsEvent {
    private int _count;
    private IEventSender _eventSender;
    private eCustomEvent _eventType;
    private boolean _shouldSendEndEvent;
    private boolean _shouldSendStartEvent = true;
    private long _startTime;

    public abstract void setEventType(eCustomEvent ecustomevent);

    public void setStartParam(Bundle params) {
        Intrinsics.checkNotNullParameter(params, "params");
    }

    public ContentsEvent(CustomEventSender customEventSender) {
        this._eventSender = customEventSender == null ? new CustomEventSender() : customEventSender;
    }

    public final eCustomEvent get_eventType() {
        return this._eventType;
    }

    public final void set_eventType(eCustomEvent ecustomevent) {
        this._eventType = ecustomevent;
    }

    public final void send(FirebaseAnalytics firebaseAnalytics, Bundle params) {
        Intrinsics.checkNotNullParameter(params, "params");
        eCustomEvent ecustomevent = this._eventType;
        if (ecustomevent == null) {
            return;
        }
        Intrinsics.checkNotNull(ecustomevent);
        if (StringsKt.contains$default((CharSequence) ecustomevent.getEventName(), (CharSequence) EContentsType.START.getTypeName(), false, 2, (Object) null) && this._shouldSendStartEvent) {
            onStart();
            setStartParam(params);
            IEventSender iEventSender = this._eventSender;
            Intrinsics.checkNotNull(iEventSender);
            Intrinsics.checkNotNull(firebaseAnalytics);
            eCustomEvent ecustomevent2 = this._eventType;
            Intrinsics.checkNotNull(ecustomevent2);
            iEventSender.send(firebaseAnalytics, params, ecustomevent2.getEventName());
            StringBuilder sb = new StringBuilder("ga send event = ");
            eCustomEvent ecustomevent3 = this._eventType;
            Intrinsics.checkNotNull(ecustomevent3);
            Lg.d(sb.append(ecustomevent3.getEventName()).toString());
            this._shouldSendStartEvent = false;
            this._shouldSendEndEvent = true;
        } else {
            eCustomEvent ecustomevent4 = this._eventType;
            Intrinsics.checkNotNull(ecustomevent4);
            if (StringsKt.contains$default((CharSequence) ecustomevent4.getEventName(), (CharSequence) EContentsType.END.getTypeName(), false, 2, (Object) null) && this._shouldSendEndEvent) {
                params.putString(eCustomDimension.DISPLAY_COUNT.getDimensionName(), getCount());
                params.putString(eCustomDimension.DISPLAY_TIME.getDimensionName(), getTime());
                IEventSender iEventSender2 = this._eventSender;
                Intrinsics.checkNotNull(iEventSender2);
                Intrinsics.checkNotNull(firebaseAnalytics);
                eCustomEvent ecustomevent5 = this._eventType;
                Intrinsics.checkNotNull(ecustomevent5);
                iEventSender2.send(firebaseAnalytics, params, ecustomevent5.getEventName());
                StringBuilder sb2 = new StringBuilder("ga send event = ");
                eCustomEvent ecustomevent6 = this._eventType;
                Intrinsics.checkNotNull(ecustomevent6);
                Lg.d(sb2.append(ecustomevent6.getEventName()).toString());
                this._startTime = 0L;
                this._shouldSendEndEvent = false;
                this._shouldSendStartEvent = true;
            }
        }
        this._eventType = null;
    }

    public final void resetCount() {
        this._count = 0;
    }

    public final String getCount() {
        return String.valueOf(this._count);
    }

    private final String getTime() {
        return this._startTime != 0 ? String.valueOf((new Date().getTime() - this._startTime) / 1000) : "0";
    }

    private final void onStart() {
        this._startTime = new Date().getTime();
        this._count++;
    }

    /* compiled from: ContentsEvent.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/ContentsEvent$EContentsType;", "", "typeName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getTypeName", "()Ljava/lang/String;", "START", "END", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum EContentsType {
        START("開始"),
        END("終了");
        
        private final String typeName;

        EContentsType(String str) {
            this.typeName = str;
        }

        public final String getTypeName() {
            return this.typeName;
        }
    }
}
