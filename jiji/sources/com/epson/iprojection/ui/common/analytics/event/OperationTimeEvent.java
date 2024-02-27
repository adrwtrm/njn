package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OperationTimeEvent.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\b\u0010\u0013\u001a\u00020\u0011H\u0002J\u0006\u0010\u0014\u001a\u00020\u000fJ\u0006\u0010\u0015\u001a\u00020\u000fJ\u0006\u0010\u0016\u001a\u00020\u000fJ\u0018\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0006R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/OperationTimeEvent;", "", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;)V", "_dimensionType", "Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/eCustomDimension;", "_eventSender", "_shouldSendLaunchTimeEvent", "", "_startConnectingTime", "", "_startTimeFromDisconnectedToConnection", "_startTimeFromLaunchToConnection", "enableLaunchTimeEvent", "", "getConnectionTime", "", "getTimeFromDisconnectedToConnection", "getTimeFromLaunchToConnection", "resetConnectingTime", "resetDisconnectedTime", "resetLaunchTime", "send", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "setDimensionType", "type", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OperationTimeEvent {
    private eCustomDimension _dimensionType;
    private IEventSender _eventSender;
    private boolean _shouldSendLaunchTimeEvent;
    private long _startConnectingTime;
    private long _startTimeFromDisconnectedToConnection;
    private long _startTimeFromLaunchToConnection;

    /* compiled from: OperationTimeEvent.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[eCustomDimension.values().length];
            try {
                iArr[eCustomDimension.LAUNCH_CONNECTING_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[eCustomDimension.TIME_FROM_DISCONNECTED_TO_CONNECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[eCustomDimension.CONNECTING_TIME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public OperationTimeEvent(CustomEventSender customEventSender) {
        this._eventSender = customEventSender == null ? new CustomEventSender() : customEventSender;
    }

    public final void setDimensionType(eCustomDimension type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type == eCustomDimension.LAUNCH_CONNECTING_TIME || type == eCustomDimension.CONNECTING_TIME || type == eCustomDimension.TIME_FROM_DISCONNECTED_TO_CONNECT) {
            this._dimensionType = type;
        }
    }

    public final void resetLaunchTime() {
        this._startTimeFromLaunchToConnection = new Date().getTime();
    }

    public final void resetDisconnectedTime() {
        this._startTimeFromDisconnectedToConnection = new Date().getTime();
    }

    public final void enableLaunchTimeEvent() {
        this._shouldSendLaunchTimeEvent = true;
    }

    public final void resetConnectingTime() {
        this._startConnectingTime = new Date().getTime();
    }

    public final void send(FirebaseAnalytics firebaseAnalytics, Bundle params) {
        Intrinsics.checkNotNullParameter(params, "params");
        eCustomDimension ecustomdimension = this._dimensionType;
        if (ecustomdimension == null) {
            return;
        }
        int i = ecustomdimension == null ? -1 : WhenMappings.$EnumSwitchMapping$0[ecustomdimension.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    eCustomDimension ecustomdimension2 = this._dimensionType;
                    Intrinsics.checkNotNull(ecustomdimension2);
                    params.putString(ecustomdimension2.getDimensionName(), getConnectionTime());
                    IEventSender iEventSender = this._eventSender;
                    Intrinsics.checkNotNull(iEventSender);
                    Intrinsics.checkNotNull(firebaseAnalytics);
                    iEventSender.send(firebaseAnalytics, params, eCustomEvent.OPERATION_TIME.getEventName());
                    this._startConnectingTime = 0L;
                }
            } else if (!this._shouldSendLaunchTimeEvent) {
                eCustomDimension ecustomdimension3 = this._dimensionType;
                Intrinsics.checkNotNull(ecustomdimension3);
                params.putString(ecustomdimension3.getDimensionName(), getTimeFromDisconnectedToConnection());
                IEventSender iEventSender2 = this._eventSender;
                Intrinsics.checkNotNull(iEventSender2);
                Intrinsics.checkNotNull(firebaseAnalytics);
                iEventSender2.send(firebaseAnalytics, params, eCustomEvent.OPERATION_TIME.getEventName());
            }
        } else if (this._shouldSendLaunchTimeEvent) {
            eCustomDimension ecustomdimension4 = this._dimensionType;
            Intrinsics.checkNotNull(ecustomdimension4);
            params.putString(ecustomdimension4.getDimensionName(), getTimeFromLaunchToConnection());
            IEventSender iEventSender3 = this._eventSender;
            Intrinsics.checkNotNull(iEventSender3);
            Intrinsics.checkNotNull(firebaseAnalytics);
            iEventSender3.send(firebaseAnalytics, params, eCustomEvent.OPERATION_TIME.getEventName());
            this._shouldSendLaunchTimeEvent = false;
        }
        this._dimensionType = null;
    }

    private final String getTimeFromLaunchToConnection() {
        return this._startTimeFromLaunchToConnection != 0 ? String.valueOf((new Date().getTime() - this._startTimeFromLaunchToConnection) / 1000) : "0";
    }

    private final String getTimeFromDisconnectedToConnection() {
        return this._startTimeFromDisconnectedToConnection != 0 ? String.valueOf((new Date().getTime() - this._startTimeFromDisconnectedToConnection) / 1000) : "0";
    }

    private final String getConnectionTime() {
        return this._startConnectingTime != 0 ? String.valueOf((new Date().getTime() - this._startConnectingTime) / 1000) : "0";
    }
}
