package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRegisteredDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class RegisteredEvent {
    private final IEventSender _eventSender;
    private eRegisteredDimension _registeredDimension = eRegisteredDimension.auto;

    public RegisteredEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.SEARCH_ROUTE_REGISTERED.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    public void setRegisteredDimension(eRegisteredDimension eregistereddimension) {
        this._registeredDimension = eregistereddimension;
    }

    private String getEventName() {
        return eCustomEvent.REGISTERED.getEventName();
    }

    private String getDimensionParam() {
        if (this._registeredDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension[this._registeredDimension.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            return this._registeredDimension.getString();
        }
        return eRegisteredDimension.auto.getString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.RegisteredEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension;

        static {
            int[] iArr = new int[eRegisteredDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension = iArr;
            try {
                iArr[eRegisteredDimension.ip.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension[eRegisteredDimension.profile.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension[eRegisteredDimension.history.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension[eRegisteredDimension.qr.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRegisteredDimension[eRegisteredDimension.nfc.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private void clear() {
        this._registeredDimension = eRegisteredDimension.auto;
    }
}
