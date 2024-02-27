package com.epson.iprojection.service.webrtc.core;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

/* compiled from: SkeletalSdpObserver.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/SkeletalSdpObserver;", "Lorg/webrtc/SdpObserver;", "()V", "onCreateFailure", "", "s", "", "onCreateSuccess", "sessionDescription", "Lorg/webrtc/SessionDescription;", "onSetFailure", "onSetSuccess", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public class SkeletalSdpObserver implements SdpObserver {
    @Override // org.webrtc.SdpObserver
    public void onCreateFailure(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
    }

    @Override // org.webrtc.SdpObserver
    public void onCreateSuccess(SessionDescription sessionDescription) {
        Intrinsics.checkNotNullParameter(sessionDescription, "sessionDescription");
    }

    @Override // org.webrtc.SdpObserver
    public void onSetFailure(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
    }

    @Override // org.webrtc.SdpObserver
    public void onSetSuccess() {
    }
}
