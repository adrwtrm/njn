package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eAudioTransferDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eConnectAsModeratorDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eOpenedContentsDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eUseBandWidthDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class ConnectCompleteEvent {
    private eAudioTransferDimension _audioTransferDimension;
    private eConnectAsModeratorDimension _connectAsModeratorDimension;
    private final IEventSender _eventSender;
    private eOpenedContentsDimension _opendContentsDimension;
    private eSearchRouteDimension _searchRouteDimension;
    private eUseBandWidthDimension _useBandWidthDimension;

    public ConnectCompleteEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.SEARCH_ROUTE.getDimensionName(), getSearchRouteDimensionParam());
        bundle.putString(eCustomDimension.CONNECT_AS_MODERATOR.getDimensionName(), getModeratorDimensionParam());
        bundle.putString(eCustomDimension.OPENED_CONTENTS.getDimensionName(), getOpenedContentsDimensionParam());
        bundle.putString(eCustomDimension.USE_BAND_WIDTH.getDimensionName(), getUseBandWidthDimensionParam());
        bundle.putString(eCustomDimension.AUDIO_TRANSFER_SETTING.getDimensionName(), getAudioTransferSettingDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    public void clear() {
        this._searchRouteDimension = null;
        this._connectAsModeratorDimension = null;
        this._opendContentsDimension = null;
        this._useBandWidthDimension = null;
        this._audioTransferDimension = null;
    }

    public void setSearchRouteDimension(eSearchRouteDimension esearchroutedimension) {
        this._searchRouteDimension = esearchroutedimension;
    }

    public void setConnectAsModeratorDimension(eConnectAsModeratorDimension econnectasmoderatordimension) {
        this._connectAsModeratorDimension = econnectasmoderatordimension;
    }

    public void setOpenedContentsDimension(eOpenedContentsDimension eopenedcontentsdimension) {
        this._opendContentsDimension = eopenedcontentsdimension;
    }

    public void setUseBandWidthDimension(int i) {
        this._useBandWidthDimension = eUseBandWidthDimension.valueOf(i);
    }

    public void setAudioTransferDimension(eAudioTransferDimension eaudiotransferdimension) {
        this._audioTransferDimension = eaudiotransferdimension;
    }

    private String getEventName() {
        return eCustomEvent.CONNECT_COMPLETE.getEventName();
    }

    private String getSearchRouteDimensionParam() {
        if (this._searchRouteDimension == null) {
            return "自動検索";
        }
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

    private String getModeratorDimensionParam() {
        if (this._connectAsModeratorDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectAsModeratorDimension[this._connectAsModeratorDimension.ordinal()];
        return i != 1 ? i != 2 ? "エラー" : "オフ" : "オン";
    }

    private String getOpenedContentsDimensionParam() {
        if (this._opendContentsDimension == null) {
            return "エラー";
        }
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension[this._opendContentsDimension.ordinal()]) {
            case 1:
                return "無し";
            case 2:
                return "写真";
            case 3:
                return "ドキュメント";
            case 4:
                return "Web";
            case 5:
                return "カメラ";
            case 6:
                return "受信画像";
            default:
                return "エラー";
        }
    }

    private String getUseBandWidthDimensionParam() {
        if (this._useBandWidthDimension == null) {
            return eUseBandWidthDimension.error.getString();
        }
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[this._useBandWidthDimension.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return this._useBandWidthDimension.getString();
            default:
                return eUseBandWidthDimension.error.getString();
        }
    }

    private String getAudioTransferSettingDimensionParam() {
        if (this._audioTransferDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eAudioTransferDimension[this._audioTransferDimension.ordinal()];
        return i != 1 ? i != 2 ? "エラー" : "オフ" : "オン";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.ConnectCompleteEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eAudioTransferDimension;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectAsModeratorDimension;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension;

        static {
            int[] iArr = new int[eAudioTransferDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eAudioTransferDimension = iArr;
            try {
                iArr[eAudioTransferDimension.on.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eAudioTransferDimension[eAudioTransferDimension.off.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[eUseBandWidthDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension = iArr2;
            try {
                iArr2[eUseBandWidthDimension.noControl.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._4Mbps.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._2Mbps.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._1Mbbs.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._512Kbps.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._256Kbps.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._7Mbbs.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._15Mbps.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eUseBandWidthDimension[eUseBandWidthDimension._25Mbps.ordinal()] = 9;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr3 = new int[eOpenedContentsDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension = iArr3;
            try {
                iArr3[eOpenedContentsDimension.none.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension[eOpenedContentsDimension.photo.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension[eOpenedContentsDimension.document.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension[eOpenedContentsDimension.web.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension[eOpenedContentsDimension.camera.ordinal()] = 5;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eOpenedContentsDimension[eOpenedContentsDimension.deliver.ordinal()] = 6;
            } catch (NoSuchFieldError unused17) {
            }
            int[] iArr4 = new int[eConnectAsModeratorDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectAsModeratorDimension = iArr4;
            try {
                iArr4[eConnectAsModeratorDimension.on.ordinal()] = 1;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eConnectAsModeratorDimension[eConnectAsModeratorDimension.off.ordinal()] = 2;
            } catch (NoSuchFieldError unused19) {
            }
            int[] iArr5 = new int[eSearchRouteDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension = iArr5;
            try {
                iArr5[eSearchRouteDimension.ip.ordinal()] = 1;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.profile.ordinal()] = 2;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.history.ordinal()] = 3;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.qr_auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.qr_manual.ordinal()] = 5;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.nfc_auto.ordinal()] = 6;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.nfc_manual.ordinal()] = 7;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$eSearchRouteDimension[eSearchRouteDimension.registered.ordinal()] = 8;
            } catch (NoSuchFieldError unused27) {
            }
        }
    }
}
