package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.mirroring.MirroringContract;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringUtils;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringPresenterJPEG.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0017J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringPresenterJPEG;", "Lcom/epson/iprojection/service/mirroring/MirroringPresenter;", "context", "Landroid/content/Context;", "view", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;", "pj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "(Landroid/content/Context;Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;Lcom/epson/iprojection/ui/engine_wrapper/IPj;)V", "_mirroring", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IMirroringJpeg;", "finish", "", "getCodecTypeForGA", "", "getProtocolTypeForGA", "onCatchPauseCommand", "value", "onChangeAudioSettings", "shouldCaptureAudio", "", "onRotated", "newConfiguration", "Landroid/content/res/Configuration;", "start", "captureIntent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringPresenterJPEG extends MirroringPresenter {
    private final MirroringContract.IMirroringJpeg _mirroring;

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public String getCodecTypeForGA() {
        return "JPEG";
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public String getProtocolTypeForGA() {
        return "PCON";
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MirroringPresenterJPEG(Context context, MirroringContract.IView view, IPj pj) {
        super(context, view, pj);
        MirroringJpegImageReader mirroringJpegImageReader;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(pj, "pj");
        if (MirroringUtils.Companion.shouldUseOpenGL(get_context())) {
            mirroringJpegImageReader = new MirroringJpegOpenGL();
        } else {
            mirroringJpegImageReader = new MirroringJpegImageReader();
        }
        this._mirroring = mirroringJpegImageReader;
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter, com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void start(Intent captureIntent) {
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        super.start(captureIntent);
        this._mirroring.start(get_context(), captureIntent);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter, com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void finish() {
        this._mirroring.stop();
        super.finish();
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter, com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onRotated(Configuration newConfiguration) {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        super.onRotated(newConfiguration);
        this._mirroring.onRotated(newConfiguration);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onChangeAudioSettings(boolean z) {
        Lg.d("onChangeAudioSettings");
        if (z) {
            this._mirroring.resumeAudio();
        } else {
            this._mirroring.pauseAudio();
        }
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringPresenter
    public void onCatchPauseCommand(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (this._mirroring.isStopped()) {
            return;
        }
        if (Intrinsics.areEqual(value, CommonDefine.TRUE)) {
            MirroringNotification.INSTANCE.create(get_context(), true);
            this._mirroring.onClickAVMute();
            return;
        }
        MirroringNotification.INSTANCE.create(get_context(), false);
        this._mirroring.onClickAVPlay();
    }
}
