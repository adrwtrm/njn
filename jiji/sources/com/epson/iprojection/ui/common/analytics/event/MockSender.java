package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MockSender.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0004H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/MockSender;", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "()V", "_eventName", "", "get_eventName", "()Ljava/lang/String;", "set_eventName", "(Ljava/lang/String;)V", "_params", "Landroid/os/Bundle;", "get_params", "()Landroid/os/Bundle;", "set_params", "(Landroid/os/Bundle;)V", "send", "", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "eventName", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MockSender implements IEventSender {
    private String _eventName = "";
    public Bundle _params;

    public final Bundle get_params() {
        Bundle bundle = this._params;
        if (bundle != null) {
            return bundle;
        }
        Intrinsics.throwUninitializedPropertyAccessException("_params");
        return null;
    }

    public final void set_params(Bundle bundle) {
        Intrinsics.checkNotNullParameter(bundle, "<set-?>");
        this._params = bundle;
    }

    public final String get_eventName() {
        return this._eventName;
    }

    public final void set_eventName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this._eventName = str;
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.IEventSender
    public void send(FirebaseAnalytics firebaseAnalytics, Bundle params, String eventName) {
        Intrinsics.checkNotNullParameter(firebaseAnalytics, "firebaseAnalytics");
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(eventName, "eventName");
        set_params(params);
        this._eventName = eventName;
    }
}
