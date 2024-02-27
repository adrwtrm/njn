package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConnectEvent {
    private final IEventSender _eventSender;
    private byte[] _ipAddr1;
    private byte[] _ipAddr2;
    private byte[] _macAddr;
    private eSearchRouteDimension _searchRouteDimension;

    public ConnectEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void set(eSearchRouteDimension esearchroutedimension, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr == null) {
            this._macAddr = null;
        } else {
            byte[] bArr4 = new byte[bArr.length];
            this._macAddr = bArr4;
            System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        }
        if (bArr2 == null) {
            this._ipAddr1 = null;
        } else {
            byte[] bArr5 = new byte[bArr2.length];
            this._ipAddr1 = bArr5;
            System.arraycopy(bArr2, 0, bArr5, 0, bArr2.length);
        }
        if (bArr3 == null) {
            this._ipAddr2 = null;
        } else {
            byte[] bArr6 = new byte[bArr3.length];
            this._ipAddr2 = bArr6;
            System.arraycopy(bArr3, 0, bArr6, 0, bArr3.length);
        }
        this._searchRouteDimension = esearchroutedimension;
    }

    private String getEventName() {
        return eCustomEvent.CONNECT.getEventName();
    }

    private String getDimensionParam() {
        if (this._searchRouteDimension == null) {
            return "自動検索";
        }
        if (containsThisPj() || this._searchRouteDimension == eSearchRouteDimension.registered) {
            switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[this._searchRouteDimension.ordinal()]) {
                case 1:
                    return "IP指定検索";
                case 2:
                    return "プロファイル検索";
                case 3:
                    return "履歴接続";
                case 4:
                    return "QRコード+かんたん";
                case 5:
                    return "QRコード+マニュアル";
                case 6:
                    return "NFC+かんたん";
                case 7:
                    return "NFC+マニュアル";
                case 8:
                    return "登録済";
                default:
                    return "自動検索";
            }
        }
        return "自動検索";
    }

    /* renamed from: com.epson.iprojection.ui.common.analytics.event.ConnectEvent$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension;

        static {
            int[] iArr = new int[eSearchRouteDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension = iArr;
            try {
                iArr[eSearchRouteDimension.ip.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.profile.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.history.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.qr_auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.qr_manual.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.nfc_auto.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.nfc_manual.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.registered.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        if (this._searchRouteDimension == null) {
            Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.auto);
        } else {
            switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[this._searchRouteDimension.ordinal()]) {
                case 1:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.ip);
                    break;
                case 2:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.profile);
                    break;
                case 3:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.history);
                    break;
                case 4:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.qr_auto);
                    break;
                case 5:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.qr_manual);
                    break;
                case 6:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.nfc_auto);
                    break;
                case 7:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.nfc_manual);
                    break;
                case 8:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.registered);
                    break;
                default:
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.auto);
                    break;
            }
        }
        bundle.putString(eCustomDimension.SEARCH_ROUTE.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    private void clear() {
        this._macAddr = null;
        this._ipAddr1 = null;
        this._ipAddr2 = null;
        this._searchRouteDimension = null;
    }

    private boolean containsThisPj() {
        if (this._searchRouteDimension == eSearchRouteDimension.history) {
            return true;
        }
        Iterator<ConnectPjInfo> it = Pj.getIns().getSelectedPj().iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (this._macAddr != null && Arrays.equals(next.getPjInfo().UniqInfo, this._macAddr)) {
                return true;
            }
            if (this._ipAddr1 != null && Arrays.equals(next.getPjInfo().IPAddr, this._ipAddr1)) {
                return true;
            }
            if (this._ipAddr2 != null && Arrays.equals(next.getPjInfo().IPAddr, this._ipAddr2)) {
                return true;
            }
        }
        return false;
    }
}
