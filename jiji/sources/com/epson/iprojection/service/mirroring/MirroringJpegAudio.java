package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.projection.MediaProjection;
import androidx.core.app.ActivityCompat;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.ShortCompanionObject;

/* compiled from: MirroringJpegAudio.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\u0004H\u0002J\b\u0010\u000e\u001a\u00020\u0004H\u0002J\u0006\u0010\u000f\u001a\u00020\fJ\u0006\u0010\u0010\u001a\u00020\fJ\u0006\u0010\u0011\u001a\u00020\fJ\u0006\u0010\u0012\u001a\u00020\fJ\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0006\u0010\u0018\u001a\u00020\fJ\f\u0010\u0019\u001a\u00020\u001a*\u00020\nH\u0002J\f\u0010\u001b\u001a\u00020\u001a*\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringJpegAudio;", "", "()V", "_isAudioPaused", "", "audioCaptureThread", "Ljava/lang/Thread;", "audioRecord", "Landroid/media/AudioRecord;", "capturedAudioSamples", "", "addAudioData", "", "canSupportPjAudioFormat", "isPjAudioFormat16bit", "onClickAVMute", "onClickAVPlay", "pause", "resume", "start", "context", "Landroid/content/Context;", "mediaProjection", "Landroid/media/projection/MediaProjection;", "stop", "toByteArrayForQuantize16bit", "", "toByteArrayForQuantize8bit", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringJpegAudio {
    private static final int BUFFER_SIZE_IN_BYTES = 4410;
    private static final int BYTES_PER_SAMPLE = 2;
    public static final Companion Companion = new Companion(null);
    private static final int FREQUENCY = 22050;
    private static final int NUM_SAMPLES_PER_READ = 2205;
    private Thread audioCaptureThread;
    private AudioRecord audioRecord;
    private boolean _isAudioPaused = true;
    private final short[] capturedAudioSamples = new short[NUM_SAMPLES_PER_READ];

    public static final /* synthetic */ void access$addAudioData(MirroringJpegAudio mirroringJpegAudio) {
        mirroringJpegAudio.addAudioData();
    }

    public final void start(Context context, MediaProjection mediaProjection) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mediaProjection, "mediaProjection");
        if (canSupportPjAudioFormat()) {
            Thread thread = this.audioCaptureThread;
            if (thread != null) {
                Intrinsics.checkNotNull(thread);
                if (thread.isAlive()) {
                    return;
                }
            }
            AudioPlaybackCaptureConfiguration build = new AudioPlaybackCaptureConfiguration.Builder(mediaProjection).addMatchingUsage(1).build();
            Intrinsics.checkNotNullExpressionValue(build, "Builder(mediaProjection)…sion\n            .build()");
            AudioFormat build2 = new AudioFormat.Builder().setEncoding(2).setSampleRate(FREQUENCY).setChannelMask(16).build();
            if (ActivityCompat.checkSelfPermission(context, PermissionAudioPresenter.permission) != 0) {
                return;
            }
            AudioRecord build3 = new AudioRecord.Builder().setAudioFormat(build2).setBufferSizeInBytes(BUFFER_SIZE_IN_BYTES).setAudioPlaybackCaptureConfig(build).build();
            this.audioRecord = build3;
            if (build3 != null) {
                build3.startRecording();
            }
            this.audioCaptureThread = ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: com.epson.iprojection.service.mirroring.MirroringJpegAudio$start$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Pj.getIns().clearAudioData();
                    MirroringJpegAudio.access$addAudioData(MirroringJpegAudio.this);
                }
            }, 30, null);
            Pj.getIns().enableAudioTransfer(-1);
        }
    }

    public final void stop() {
        Thread thread = this.audioCaptureThread;
        if (thread != null) {
            thread.interrupt();
        }
        Thread thread2 = this.audioCaptureThread;
        if (thread2 != null) {
            thread2.join();
        }
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null) {
            audioRecord.stop();
        }
        AudioRecord audioRecord2 = this.audioRecord;
        if (audioRecord2 != null) {
            audioRecord2.release();
        }
        this.audioRecord = null;
    }

    public final void pause() {
        this._isAudioPaused = true;
    }

    public final void resume() {
        this._isAudioPaused = false;
    }

    public final void addAudioData() {
        while (true) {
            Thread thread = this.audioCaptureThread;
            if (thread != null) {
                Intrinsics.checkNotNull(thread);
                if (thread.isInterrupted()) {
                    return;
                }
            }
            AudioRecord audioRecord = this.audioRecord;
            if (audioRecord != null) {
                audioRecord.read(this.capturedAudioSamples, 0, NUM_SAMPLES_PER_READ);
            }
            if (!this._isAudioPaused) {
                if (isPjAudioFormat16bit()) {
                    Pj.getIns().addAudioData(toByteArrayForQuantize16bit(this.capturedAudioSamples));
                } else {
                    Pj.getIns().addAudioData(toByteArrayForQuantize8bit(this.capturedAudioSamples));
                }
            }
        }
    }

    private final byte[] toByteArrayForQuantize16bit(short[] sArr) {
        byte[] bArr = new byte[sArr.length * 2];
        int length = sArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            short s = sArr[i];
            bArr[i2] = (byte) (s & 255);
            bArr[i2 + 1] = (byte) (s >> 8);
            sArr[i] = 0;
        }
        return bArr;
    }

    private final byte[] toByteArrayForQuantize8bit(short[] sArr) {
        byte[] bArr = new byte[sArr.length];
        int length = sArr.length;
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) ((sArr[i] - ShortCompanionObject.MIN_VALUE) >> 8);
            sArr[i] = 0;
        }
        return bArr;
    }

    private final boolean isPjAudioFormat16bit() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        if (nowConnectingPJList == null) {
            return true;
        }
        return nowConnectingPJList.get(0).getPjInfo().isSupportedAudioQuantize_16bit;
    }

    private final boolean canSupportPjAudioFormat() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        if (nowConnectingPJList == null) {
            return false;
        }
        D_PjInfo pjInfo = nowConnectingPJList.get(0).getPjInfo();
        return pjInfo.isSupportedAudio && pjInfo.isSupportedAudioChannel_Monaural && pjInfo.isSupportedAudioFrequency_22050;
    }

    public final void onClickAVMute() {
        Pj.getIns().disableAudioTransfer();
    }

    public final void onClickAVPlay() {
        Pj.getIns().enableAudioTransfer(-1);
    }

    /* compiled from: MirroringJpegAudio.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringJpegAudio$Companion;", "", "()V", "BUFFER_SIZE_IN_BYTES", "", "BYTES_PER_SAMPLE", "FREQUENCY", "NUM_SAMPLES_PER_READ", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
