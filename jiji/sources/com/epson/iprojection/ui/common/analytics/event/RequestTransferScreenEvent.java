package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRequestTransferScreenDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class RequestTransferScreenEvent {
    private final IEventSender _eventSender;
    private eRequestTransferScreenDimension _requestTransferScreenDimension;

    public RequestTransferScreenEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.REQUEST_TRANSFER_SCREEN.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    public void setRequestTransferScreenDimension(eRequestTransferScreenDimension erequesttransferscreendimension) {
        this._requestTransferScreenDimension = erequesttransferscreendimension;
    }

    private String getEventName() {
        return eCustomEvent.REQUEST_TRANSFER_SCREEN.getEventName();
    }

    private String getDimensionParam() {
        if (this._requestTransferScreenDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRequestTransferScreenDimension[this._requestTransferScreenDimension.ordinal()];
        if (i == 1 || i == 2) {
            return this._requestTransferScreenDimension.getString();
        }
        return eRequestTransferScreenDimension.error.getString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.RequestTransferScreenEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRequestTransferScreenDimension;

        static {
            int[] iArr = new int[eRequestTransferScreenDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRequestTransferScreenDimension = iArr;
            try {
                iArr[eRequestTransferScreenDimension.projectionScreen.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eRequestTransferScreenDimension[eRequestTransferScreenDimension.white.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void clear() {
        this._requestTransferScreenDimension = null;
    }
}
