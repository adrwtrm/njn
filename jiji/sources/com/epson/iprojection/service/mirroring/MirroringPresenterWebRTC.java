package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.ProtocolType;
import com.epson.iprojection.service.mirroring.MirroringContract;
import com.epson.iprojection.service.webrtc.WebRTCEntrance;
import com.epson.iprojection.service.webrtc.thumbnail.ThumbnailCapturerEntrance;
import com.epson.iprojection.service.webrtc.utils.VideoCodecUtils;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringPresenterWebRTC.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016¨\u0006\u0019"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringPresenterWebRTC;", "Lcom/epson/iprojection/service/mirroring/MirroringPresenter;", "context", "Landroid/content/Context;", "view", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;", "pj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "(Landroid/content/Context;Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;Lcom/epson/iprojection/ui/engine_wrapper/IPj;)V", "finish", "", "getCodecTypeForGA", "", "getProtocolTypeForGA", "onCatchPauseCommand", "value", "onChangeAudioSettings", "shouldCaptureAudio", "", "onRotated", "newConfiguration", "Landroid/content/res/Configuration;", "start", "captureIntent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringPresenterWebRTC extends MirroringPresenter {
    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public String getProtocolTypeForGA() {
        return "WebRTC";
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onChangeAudioSettings(boolean z) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MirroringPresenterWebRTC(Context context, MirroringContract.IView view, IPj pj) {
        super(context, view, pj);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(pj, "pj");
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter, com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void start(Intent captureIntent) {
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        super.start(captureIntent);
        ThumbnailCapturerEntrance thumbnailCapturerEntrance = ThumbnailCapturerEntrance.INSTANCE;
        Context applicationContext = get_context().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "_context.applicationContext");
        thumbnailCapturerEntrance.initialize(applicationContext, captureIntent);
        WebRTCEntrance.INSTANCE.setCaptureIntent(captureIntent);
        Pj.getIns().changeProtocol(ProtocolType.WebRTC);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter, com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void finish() {
        super.finish();
        Pj.getIns().resetWaitMode();
        Pj.getIns().changeProtocol(ProtocolType.JPEG);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter, com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onRotated(Configuration newConfiguration) {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        Lg.d("onRotated : " + newConfiguration);
        super.onRotated(newConfiguration);
        WebRTCEntrance.INSTANCE.onRotated();
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public String getCodecTypeForGA() {
        return VideoCodecUtils.INSTANCE.isH264Usable() ? "H264" : "VP8";
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter
    public void onCatchPauseCommand(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (Intrinsics.areEqual(value, CommonDefine.TRUE)) {
            MirroringNotification.INSTANCE.create(get_context(), true);
            Pj.getIns().setProjectionMode(2);
            return;
        }
        MirroringNotification.INSTANCE.create(get_context(), false);
        Pj.getIns().setProjectionMode(1);
    }
}
