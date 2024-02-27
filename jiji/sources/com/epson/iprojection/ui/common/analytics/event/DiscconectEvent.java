package com.epson.iprojection.ui.common.analytics.event;

import android.os.Bundle;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DiscconectEvent.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B?\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/DiscconectEvent;", "", "eventSender", "Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;", "photoDisplayEvent", "Lcom/epson/iprojection/ui/common/analytics/event/PhotoDisplayEvent;", "documentDisplayEvent", "Lcom/epson/iprojection/ui/common/analytics/event/DocumentDisplayEvent;", "webDisplayEvent", "Lcom/epson/iprojection/ui/common/analytics/event/WebDisplayEvent;", "cameraDisplayEvent", "Lcom/epson/iprojection/ui/common/analytics/event/CameraDisplayEvent;", "receivedImageDisplayEvent", "Lcom/epson/iprojection/ui/common/analytics/event/ReceivedImageDisplayEvent;", "mirroringEvent", "Lcom/epson/iprojection/ui/common/analytics/event/MirroringEvent;", "(Lcom/epson/iprojection/ui/common/analytics/event/IEventSender;Lcom/epson/iprojection/ui/common/analytics/event/PhotoDisplayEvent;Lcom/epson/iprojection/ui/common/analytics/event/DocumentDisplayEvent;Lcom/epson/iprojection/ui/common/analytics/event/WebDisplayEvent;Lcom/epson/iprojection/ui/common/analytics/event/CameraDisplayEvent;Lcom/epson/iprojection/ui/common/analytics/event/ReceivedImageDisplayEvent;Lcom/epson/iprojection/ui/common/analytics/event/MirroringEvent;)V", "_eventSender", "getUsedCount", "", "send", "", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "params", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DiscconectEvent {
    private IEventSender _eventSender;
    private final CameraDisplayEvent cameraDisplayEvent;
    private final DocumentDisplayEvent documentDisplayEvent;
    private final MirroringEvent mirroringEvent;
    private final PhotoDisplayEvent photoDisplayEvent;
    private final ReceivedImageDisplayEvent receivedImageDisplayEvent;
    private final WebDisplayEvent webDisplayEvent;

    public DiscconectEvent(CustomEventSender customEventSender, PhotoDisplayEvent photoDisplayEvent, DocumentDisplayEvent documentDisplayEvent, WebDisplayEvent webDisplayEvent, CameraDisplayEvent cameraDisplayEvent, ReceivedImageDisplayEvent receivedImageDisplayEvent, MirroringEvent mirroringEvent) {
        Intrinsics.checkNotNullParameter(photoDisplayEvent, "photoDisplayEvent");
        Intrinsics.checkNotNullParameter(documentDisplayEvent, "documentDisplayEvent");
        Intrinsics.checkNotNullParameter(webDisplayEvent, "webDisplayEvent");
        Intrinsics.checkNotNullParameter(cameraDisplayEvent, "cameraDisplayEvent");
        Intrinsics.checkNotNullParameter(receivedImageDisplayEvent, "receivedImageDisplayEvent");
        Intrinsics.checkNotNullParameter(mirroringEvent, "mirroringEvent");
        this.photoDisplayEvent = photoDisplayEvent;
        this.documentDisplayEvent = documentDisplayEvent;
        this.webDisplayEvent = webDisplayEvent;
        this.cameraDisplayEvent = cameraDisplayEvent;
        this.receivedImageDisplayEvent = receivedImageDisplayEvent;
        this.mirroringEvent = mirroringEvent;
        this._eventSender = customEventSender == null ? new CustomEventSender() : customEventSender;
    }

    public final void send(FirebaseAnalytics firebaseAnalytics, Bundle params) {
        Intrinsics.checkNotNullParameter(params, "params");
        params.putString(eCustomDimension.CONTENTS_USED_COUNT.getDimensionName(), getUsedCount());
        IEventSender iEventSender = this._eventSender;
        Intrinsics.checkNotNull(iEventSender);
        Intrinsics.checkNotNull(firebaseAnalytics);
        iEventSender.send(firebaseAnalytics, params, eCustomEvent.DISCONNECT.getEventName());
    }

    private final String getUsedCount() {
        return this.photoDisplayEvent.getCount() + '/' + this.documentDisplayEvent.getCount() + '/' + this.webDisplayEvent.getCount() + '/' + this.cameraDisplayEvent.getCount() + '/' + this.receivedImageDisplayEvent.getCount() + '/' + this.mirroringEvent.getCount();
    }
}
