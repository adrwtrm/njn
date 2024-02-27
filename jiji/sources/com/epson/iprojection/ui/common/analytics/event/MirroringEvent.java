package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;

/* loaded from: classes.dex */
public class MirroringEvent extends ContentsEvent {
    private String _codec;
    private String _protocol;

    public MirroringEvent(IEventSender iEventSender) {
        super(iEventSender);
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.ContentsEvent
    public void setStartParam(Bundle bundle) {
        bundle.putString(eCustomDimension.PROTOCOL_AND_CODEC.getDimensionName(), getProtocolAndCodec());
    }

    public void setProtocolAndCodec(String str, String str2) {
        this._protocol = str;
        this._codec = str2;
    }

    private String getProtocolAndCodec() {
        if (this._protocol == null) {
            this._protocol = "0";
        }
        if (this._codec == null) {
            this._codec = "0";
        }
        return this._protocol + " + " + this._codec;
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.ContentsEvent
    public void setEventType(eCustomEvent ecustomevent) {
        if (ecustomevent == eCustomEvent.MIRRORING_START || ecustomevent == eCustomEvent.MIRRORING_END) {
            set_eventType(ecustomevent);
        }
    }
}
