package com.epson.iprojection.ui.common.analytics.event;

import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;

/* loaded from: classes.dex */
public class LocationPermissionEvent extends PermissionChangeEvent {
    public LocationPermissionEvent(IEventSender iEventSender) {
        super(iEventSender);
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.PermissionChangeEvent
    protected String getEventName() {
        return eCustomEvent.LOCATION_PERMISSION.getEventName();
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.PermissionChangeEvent
    protected String getDimensionName() {
        return eCustomDimension.LOCATION_PERMISSION.getDimensionName();
    }
}
