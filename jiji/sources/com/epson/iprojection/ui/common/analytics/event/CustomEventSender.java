package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CustomEventSender.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/CustomEventSender;", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "()V", "send", "", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "eventName", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CustomEventSender implements IEventSender {
    @Override // com.epson.iprojection.ui.common.analytics.event.IEventSender
    public void send(FirebaseAnalytics firebaseAnalytics, Bundle params, String eventName) {
        Intrinsics.checkNotNullParameter(firebaseAnalytics, "firebaseAnalytics");
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(eventName, "eventName");
        firebaseAnalytics.logEvent(eventName, params);
    }
}
