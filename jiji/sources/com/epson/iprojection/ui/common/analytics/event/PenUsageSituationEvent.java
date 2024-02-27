package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePenUsageSituationDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class PenUsageSituationEvent {
    private final IEventSender _eventSender;
    private boolean isPen1Drawn = false;
    private boolean isPen2Drawn = false;

    public PenUsageSituationEvent(IEventSender iEventSender) {
        if (iEventSender == null) {
            this._eventSender = new CustomEventSender();
        } else {
            this._eventSender = iEventSender;
        }
    }

    public void updatePenUseState(ePenUsageSituationDimension epenusagesituationdimension) {
        if (epenusagesituationdimension == ePenUsageSituationDimension.usePen1 && !this.isPen1Drawn) {
            this.isPen1Drawn = true;
        }
        if (epenusagesituationdimension != ePenUsageSituationDimension.usePen2 || this.isPen2Drawn) {
            return;
        }
        this.isPen2Drawn = true;
    }

    public void send(FirebaseAnalytics firebaseAnalytics, Bundle bundle) {
        bundle.putString(eCustomDimension.PEN_USAGE_SITUATION.getDimensionName(), getDimensionParam());
        this._eventSender.send(firebaseAnalytics, bundle, getEventName());
        clear();
    }

    private void clear() {
        this.isPen1Drawn = false;
        this.isPen2Drawn = false;
    }

    private String getEventName() {
        return eCustomEvent.PEN_USAGE_SITUATION.getEventName();
    }

    private String getDimensionParam() {
        boolean z = this.isPen1Drawn;
        if (z && this.isPen2Drawn) {
            return ePenUsageSituationDimension.both.getString();
        }
        if (z) {
            return ePenUsageSituationDimension.usePen1.getString();
        }
        if (this.isPen2Drawn) {
            return ePenUsageSituationDimension.usePen2.getString();
        }
        return ePenUsageSituationDimension.noUsed.getString();
    }
}
