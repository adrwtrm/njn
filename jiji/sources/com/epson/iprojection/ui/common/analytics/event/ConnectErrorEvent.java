package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eConnectErrorDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class ConnectErrorEvent {
    private eConnectErrorDimension _connectErrorDimension;
    private final IEventSender _eventSender;

    public ConnectErrorEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.CONNECT_ERROR_REASON.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    protected void clear() {
        this._connectErrorDimension = null;
    }

    public void setConnectErrorDimension(eConnectErrorDimension econnecterrordimension) {
        this._connectErrorDimension = econnecterrordimension;
    }

    private String getEventName() {
        return eCustomEvent.CONNECT_ERROR.getEventName();
    }

    private String getDimensionParam() {
        if (this._connectErrorDimension == null) {
            return "エラー";
        }
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension[this._connectErrorDimension.ordinal()]) {
            case 1:
                return "キーワードが不正";
            case 2:
                return "ミラーリング構成が不正";
            case 3:
                return "MPPバージョンが不正";
            case 4:
                return "接続最大数を超えた";
            case 5:
                return "モバイル通信がONのため";
            case 6:
                return "その他の接続失敗";
            default:
                return "エラー";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.ConnectErrorEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension;

        static {
            int[] iArr = new int[eConnectErrorDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension = iArr;
            try {
                iArr[eConnectErrorDimension.InvalidKeyword.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension[eConnectErrorDimension.InvalidMirroringConstitution.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension[eConnectErrorDimension.InvalidMppVersion.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension[eConnectErrorDimension.OverConnectMax.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension[eConnectErrorDimension.MobileCommunicationOn.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectErrorDimension[eConnectErrorDimension.Other.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
