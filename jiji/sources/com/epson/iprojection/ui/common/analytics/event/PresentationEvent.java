package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePresentationContentsDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePresentationImplicitDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class PresentationEvent {
    private final IEventSender _eventSender;
    private boolean _isImplicit = false;
    private ePresentationContentsDimension _presentationContentsDimension;

    public PresentationEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public static ePresentationContentsDimension convertFileNameToEnum(String str) {
        if (FileUtils.isExpectedFile(str, new String[]{"jpg", "jpeg"})) {
            return ePresentationContentsDimension.jpg;
        }
        if (FileUtils.isExpectedFile(str, new String[]{"bmp"})) {
            return ePresentationContentsDimension.bmp;
        }
        if (FileUtils.isExpectedFile(str, new String[]{"png"})) {
            return ePresentationContentsDimension.png;
        }
        if (FileUtils.isExpectedFile(str, new String[]{"gif"})) {
            return ePresentationContentsDimension.gif;
        }
        if (FileUtils.isExpectedFile(str, new String[]{"pdf"})) {
            return ePresentationContentsDimension.pdf;
        }
        return ePresentationContentsDimension.other;
    }

    public void setPresentationContentsDimension(ePresentationContentsDimension epresentationcontentsdimension) {
        this._presentationContentsDimension = epresentationcontentsdimension;
    }

    public void setPresentationImplicitDimension(boolean z) {
        this._isImplicit = z;
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        if (this._isImplicit) {
            bundle.putString(eCustomDimension.PRESENTATION_IMPLICIT.getDimensionName(), ePresentationImplicitDimension.IMPLICIT.getParams());
        } else {
            bundle.putString(eCustomDimension.PRESENTATION_IMPLICIT.getDimensionName(), ePresentationImplicitDimension.NORMAL.getParams());
        }
        bundle.putString(eCustomDimension.PRESENTATION_CONTENTS.getDimensionName(), getContentsDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    protected String getContentsDimensionParam() {
        if (this._presentationContentsDimension == null) {
            return "エラー";
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension[this._presentationContentsDimension.ordinal()];
        return (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? this._presentationContentsDimension.name() : "エラー";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.analytics.event.PresentationEvent$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension;

        static {
            int[] iArr = new int[ePresentationContentsDimension.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension = iArr;
            try {
                iArr[ePresentationContentsDimension.jpg.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension[ePresentationContentsDimension.gif.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension[ePresentationContentsDimension.png.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension[ePresentationContentsDimension.bmp.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$analytics$customdimension$enums$ePresentationContentsDimension[ePresentationContentsDimension.pdf.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private String getEventName() {
        return eCustomEvent.PRESENTATION_SCREEN.getEventName();
    }

    private void clear() {
        this._presentationContentsDimension = null;
        this._isImplicit = false;
    }
}
