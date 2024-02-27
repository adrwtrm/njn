package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class FirstTimeProjectionEvent {
    private final IEventSender _eventSender;
    private eFirstTimeProjectionDimension _firstTimeProjectionDimension = eFirstTimeProjectionDimension.def;

    public FirstTimeProjectionEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        if (this._firstTimeProjectionDimension == eFirstTimeProjectionDimension.def || this._firstTimeProjectionDimension == eFirstTimeProjectionDimension.done) {
            return;
        }
        bundle.putString(eCustomDimension.FIRST_TIME_PROJECTION_CONTENTS.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    public void setFirstTimeProjectionDimension(eFirstTimeProjectionDimension efirsttimeprojectiondimension) {
        if (this._firstTimeProjectionDimension != eFirstTimeProjectionDimension.done || efirsttimeprojectiondimension == eFirstTimeProjectionDimension.def) {
            this._firstTimeProjectionDimension = efirsttimeprojectiondimension;
        }
    }

    private String getEventName() {
        return eCustomEvent.FIRST_TIME_PROJECTION.getEventName();
    }

    private String getDimensionParam() {
        if (this._firstTimeProjectionDimension == null) {
            return "エラー";
        }
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension[this._firstTimeProjectionDimension.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return this._firstTimeProjectionDimension.getString();
            default:
                return eFirstTimeProjectionDimension.error.getString();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.FirstTimeProjectionEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension;

        static {
            int[] iArr = new int[eFirstTimeProjectionDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension = iArr;
            try {
                iArr[eFirstTimeProjectionDimension.photo.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension[eFirstTimeProjectionDimension.document.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension[eFirstTimeProjectionDimension.web.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension[eFirstTimeProjectionDimension.camera.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension[eFirstTimeProjectionDimension.mirroring.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eFirstTimeProjectionDimension[eFirstTimeProjectionDimension.receivedImage.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private void clear() {
        this._firstTimeProjectionDimension = eFirstTimeProjectionDimension.done;
    }
}
