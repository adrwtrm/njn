package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eManualSearchDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class ManualSearchEvent {
    private final IEventSender _eventSender;
    private eManualSearchDimension _manualSearchDimension;

    public ManualSearchEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.MANUAL_SEARCH_RESULT.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    public void setManualSearchDimension(eManualSearchDimension emanualsearchdimension) {
        this._manualSearchDimension = emanualsearchdimension;
    }

    private String getEventName() {
        return eCustomEvent.MANUAL_SEARCH.getEventName();
    }

    private String getDimensionParam() {
        if (this._manualSearchDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eManualSearchDimension[this._manualSearchDimension.ordinal()];
        if (i == 1 || i == 2) {
            return this._manualSearchDimension.getString();
        }
        return eManualSearchDimension.error.getString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.ManualSearchEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eManualSearchDimension;

        static {
            int[] iArr = new int[eManualSearchDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eManualSearchDimension = iArr;
            try {
                iArr[eManualSearchDimension.succeeded.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eManualSearchDimension[eManualSearchDimension.failed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void clear() {
        this._manualSearchDimension = null;
    }
}
