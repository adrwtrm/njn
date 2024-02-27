package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.mirroring.MirroringContract;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringUtils;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringJpeg.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010$\u001a\u00020\rH\u0016J\b\u0010%\u001a\u00020&H\u0004J\b\u0010'\u001a\u00020&H\u0016J\b\u0010(\u001a\u00020&H\u0016J\u0010\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020&H\u0016J\b\u0010-\u001a\u00020&H\u0016J\b\u0010.\u001a\u00020&H\u0016J\u0018\u0010/\u001a\u00020&2\u0006\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u00020&H\u0016J\u0010\u00104\u001a\u00020&2\u0006\u00100\u001a\u00020\u0007H&J\b\u00105\u001a\u00020&H\u0016J\b\u00106\u001a\u00020&H\u0016J\b\u00107\u001a\u00020&H&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u001a\u0010\u0015\u001a\u00020\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u00068"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringJpeg;", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IMirroringJpeg;", "Landroid/media/projection/MediaProjection$Callback;", "()V", "_audio", "Lcom/epson/iprojection/service/mirroring/MirroringJpegAudio;", "_context", "Landroid/content/Context;", "get_context", "()Landroid/content/Context;", "set_context", "(Landroid/content/Context;)V", "_isAVMuting", "", "get_isAVMuting", "()Z", "set_isAVMuting", "(Z)V", "_isRotating", "get_isRotating", "set_isRotating", "_isStopped", "get_isStopped", "set_isStopped", "_mediaProjection", "Landroid/media/projection/MediaProjection;", "get_mediaProjection", "()Landroid/media/projection/MediaProjection;", "set_mediaProjection", "(Landroid/media/projection/MediaProjection;)V", "_virtualDisplay", "Landroid/hardware/display/VirtualDisplay;", "get_virtualDisplay", "()Landroid/hardware/display/VirtualDisplay;", "set_virtualDisplay", "(Landroid/hardware/display/VirtualDisplay;)V", "isStopped", "notifyContentRect", "", "onClickAVMute", "onClickAVPlay", "onRotated", "newConfiguration", "Landroid/content/res/Configuration;", "onStop", "pauseAudio", "resumeAudio", "start", "context", "intent", "Landroid/content/Intent;", "startAudio", "startImage", "stop", "stopAudio", "stopImage", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class MirroringJpeg extends MediaProjection.Callback implements MirroringContract.IMirroringJpeg {
    private Context _context;
    private boolean _isAVMuting;
    private boolean _isRotating;
    private MediaProjection _mediaProjection;
    private VirtualDisplay _virtualDisplay;
    private boolean _isStopped = true;
    private final MirroringJpegAudio _audio = new MirroringJpegAudio();

    public abstract void startImage(Context context);

    public abstract void stopImage();

    public final VirtualDisplay get_virtualDisplay() {
        return this._virtualDisplay;
    }

    public final void set_virtualDisplay(VirtualDisplay virtualDisplay) {
        this._virtualDisplay = virtualDisplay;
    }

    public final MediaProjection get_mediaProjection() {
        return this._mediaProjection;
    }

    protected final void set_mediaProjection(MediaProjection mediaProjection) {
        this._mediaProjection = mediaProjection;
    }

    public final boolean get_isStopped() {
        return this._isStopped;
    }

    protected final void set_isStopped(boolean z) {
        this._isStopped = z;
    }

    public final boolean get_isAVMuting() {
        return this._isAVMuting;
    }

    protected final void set_isAVMuting(boolean z) {
        this._isAVMuting = z;
    }

    public final Context get_context() {
        return this._context;
    }

    protected final void set_context(Context context) {
        this._context = context;
    }

    public final boolean get_isRotating() {
        return this._isRotating;
    }

    protected final void set_isRotating(boolean z) {
        this._isRotating = z;
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void start(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Lg.d("start()");
        this._context = context;
        this._isAVMuting = false;
        this._isStopped = false;
        Object systemService = context.getSystemService("media_projection");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
        MediaProjection mediaProjection = ((MediaProjectionManager) systemService).getMediaProjection(-1, intent);
        this._mediaProjection = mediaProjection;
        Intrinsics.checkNotNull(mediaProjection);
        mediaProjection.registerCallback(this, new Handler(Looper.getMainLooper()));
        startImage(context);
        startAudio();
    }

    public void startAudio() {
        if (Build.VERSION.SDK_INT < 29 || this._mediaProjection == null) {
            return;
        }
        MirroringJpegAudio mirroringJpegAudio = this._audio;
        Context context = this._context;
        Intrinsics.checkNotNull(context);
        MediaProjection mediaProjection = this._mediaProjection;
        Intrinsics.checkNotNull(mediaProjection);
        mirroringJpegAudio.start(context, mediaProjection);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void stop() {
        Lg.d("stop()");
        if (this._isStopped) {
            Lg.d("Stoppedなのにstopが来たので抜けました");
            return;
        }
        this._isStopped = true;
        MediaProjection mediaProjection = this._mediaProjection;
        if (mediaProjection != null) {
            Intrinsics.checkNotNull(mediaProjection);
            mediaProjection.unregisterCallback(this);
            MediaProjection mediaProjection2 = this._mediaProjection;
            Intrinsics.checkNotNull(mediaProjection2);
            mediaProjection2.stop();
            this._mediaProjection = null;
        }
        stopImage();
        stopAudio();
    }

    public void stopAudio() {
        this._audio.stop();
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void onRotated(Configuration newConfiguration) {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        Lg.d("onRotated orientation:" + newConfiguration.orientation);
        if (this._context == null || this._isStopped) {
            Lg.w("_contextがnullか、まだ開始していないのでonRotatedの処理をせずに抜ける");
            return;
        }
        this._isRotating = true;
        stopImage();
        Context context = this._context;
        Intrinsics.checkNotNull(context);
        startImage(context);
        this._isRotating = false;
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public boolean isStopped() {
        return this._isStopped;
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void pauseAudio() {
        this._audio.pause();
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void resumeAudio() {
        this._audio.resume();
    }

    public final void notifyContentRect() {
        if (this._context == null) {
            return;
        }
        MirroringUtils.Companion companion = MirroringUtils.Companion;
        Context context = this._context;
        Intrinsics.checkNotNull(context);
        ResRect screenResolution = companion.getScreenResolution(context);
        ContentRectHolder.INSTANCE.setContentRect(screenResolution.w, screenResolution.h);
    }

    @Override // android.media.projection.MediaProjection.Callback
    public void onStop() {
        super.onStop();
        Lg.w("MediaProjectionの異常終了を検出");
        Context context = this._context;
        if (context != null) {
            MirroringEntrance.INSTANCE.finish(context);
        }
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void onClickAVPlay() {
        notifyContentRect();
        this._isAVMuting = false;
        this._audio.onClickAVPlay();
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IMirroringJpeg
    public void onClickAVMute() {
        this._isAVMuting = true;
        Pj.getIns().sendWaitImage();
        this._audio.onClickAVMute();
    }
}
