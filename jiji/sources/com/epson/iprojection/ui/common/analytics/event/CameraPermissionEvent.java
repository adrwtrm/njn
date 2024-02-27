package com.epson.iprojection.ui.common.analytics.event;

import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;

/* loaded from: classes.dex */
public class CameraPermissionEvent extends PermissionChangeEvent {
    public CameraPermissionEvent(IEventSender iEventSender) {
        super(iEventSender);
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.PermissionChangeEvent
    protected String getEventName() {
        return eCustomEvent.CAMERA_PERMISSION.getEventName();
    }

    @Override // com.epson.iprojection.ui.common.analytics.event.PermissionChangeEvent
    protected String getDimensionName() {
        return eCustomDimension.CAMERA_PERMISSION.getDimensionName();
    }
}
