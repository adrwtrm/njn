package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eWebScreenDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class WebScreenEvent {
    private final IEventSender _eventSender;
    private eWebScreenDimension _webScreenDimension = eWebScreenDimension.explicitIntents;

    public WebScreenEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.WEB_SCREEN_INTENT.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    public void setWebScreenDimension(eWebScreenDimension ewebscreendimension) {
        this._webScreenDimension = ewebscreendimension;
    }

    private String getEventName() {
        return eCustomEvent.WEB_SCREEN.getEventName();
    }

    private String getDimensionParam() {
        if (this._webScreenDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eWebScreenDimension[this._webScreenDimension.ordinal()];
        if (i == 1 || i == 2) {
            return this._webScreenDimension.getString();
        }
        return eWebScreenDimension.error.getString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.WebScreenEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eWebScreenDimension;

        static {
            int[] iArr = new int[eWebScreenDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eWebScreenDimension = iArr;
            try {
                iArr[eWebScreenDimension.explicitIntents.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eWebScreenDimension[eWebScreenDimension.implicitIntents.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void clear() {
        this._webScreenDimension = eWebScreenDimension.explicitIntents;
    }
}
