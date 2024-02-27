package com.epson.iprojection.service.webrtc.core;

import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.webrtc.core.AudioContract;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;

/* compiled from: AudioController.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\u0002\u0018\u0000  2\u00020\u0001:\u0001 B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\u001a\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0003J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\f\u0010\u001d\u001a\u00020\r*\u00020\rH\u0002J\f\u0010\u001e\u001a\u00020\r*\u00020\u001fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioController;", "Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCapture;", "_callback", "Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCaptureCallback;", "isAudioPaused", "", "(Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCaptureCallback;Z)V", "_isAudioPaused", "audioCaptureThread", "Ljava/lang/Thread;", "audioRecord", "Landroid/media/AudioRecord;", "capturedAudioSamples", "", "addAudioData", "", "isStopped", "pauseAudio", "resumeAudio", "start", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "startAudioCapture", "mediaProjection", "Landroid/media/projection/MediaProjection;", "stop", "stopAudioCapture", "swapForAudioData", "toBytes", "", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AudioController implements AudioContract.IAudioCapture {
    private static final int AUDIOFORMAT_SIZE = 1;
    private static final int BUFFER_SIZE_IN_BYTES_FOR_WEBRTC = 1024;
    private static final int CHANNEL_FOR_WEBRTC = 2;
    private static final String CODEC = "PCM";
    public static final Companion Companion = new Companion(null);
    private static final String DATA_CHANNEL_HEADER = "EWDP";
    private static final byte DATA_CHANNEL_PROTCOL_VERSION = 1;
    private static final byte DATA_ID = 1;
    private static final int FREQUENCY_FOR_WEBRTC = 22050;
    private static final String QUANTIZE_FOR_WEBRTC = "16bit";
    private final AudioContract.IAudioCaptureCallback _callback;
    private boolean _isAudioPaused;
    private Thread audioCaptureThread;
    private AudioRecord audioRecord;
    private final byte[] capturedAudioSamples;

    public AudioController(AudioContract.IAudioCaptureCallback _callback, boolean z) {
        Intrinsics.checkNotNullParameter(_callback, "_callback");
        this._callback = _callback;
        this._isAudioPaused = z;
        this.capturedAudioSamples = new byte[1024];
    }

    public static final /* synthetic */ void access$addAudioData(AudioController audioController) {
        audioController.addAudioData();
    }

    @Override // com.epson.iprojection.service.webrtc.core.AudioContract.IAudioCapture
    public void start(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Lg.d("start()");
        if (Build.VERSION.SDK_INT >= 29) {
            Object systemService = context.getSystemService("media_projection");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
            Intrinsics.checkNotNull(intent);
            MediaProjection mediaProjection = ((MediaProjectionManager) systemService).getMediaProjection(-1, intent);
            Intrinsics.checkNotNullExpressionValue(mediaProjection, "mediaProjection");
            startAudioCapture(context, mediaProjection);
        }
    }

    @Override // com.epson.iprojection.service.webrtc.core.AudioContract.IAudioCapture
    public void stop() {
        stopAudioCapture();
    }

    @Override // com.epson.iprojection.service.webrtc.core.AudioContract.IAudioCapture
    public void resumeAudio() {
        this._isAudioPaused = false;
    }

    @Override // com.epson.iprojection.service.webrtc.core.AudioContract.IAudioCapture
    public void pauseAudio() {
        this._isAudioPaused = true;
    }

    @Override // com.epson.iprojection.service.webrtc.core.AudioContract.IAudioCapture
    public boolean isStopped() {
        return this.audioRecord == null;
    }

    private final void startAudioCapture(Context context, MediaProjection mediaProjection) {
        Thread thread = this.audioCaptureThread;
        if (thread != null) {
            Intrinsics.checkNotNull(thread);
            if (thread.isAlive()) {
                return;
            }
        }
        AudioPlaybackCaptureConfiguration build = new AudioPlaybackCaptureConfiguration.Builder(mediaProjection).addMatchingUsage(1).build();
        Intrinsics.checkNotNullExpressionValue(build, "Builder(mediaProjection)…DIA)\n            .build()");
        AudioFormat build2 = new AudioFormat.Builder().setEncoding(2).setSampleRate(FREQUENCY_FOR_WEBRTC).setChannelMask(12).build();
        if (ActivityCompat.checkSelfPermission(context, PermissionAudioPresenter.permission) != 0) {
            return;
        }
        AudioRecord build3 = new AudioRecord.Builder().setAudioFormat(build2).setBufferSizeInBytes(1024).setAudioPlaybackCaptureConfig(build).build();
        this.audioRecord = build3;
        if (build3 != null) {
            build3.startRecording();
        }
        this.audioCaptureThread = ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: com.epson.iprojection.service.webrtc.core.AudioController$startAudioCapture$1
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
                AudioController.access$addAudioData(AudioController.this);
            }
        }, 30, null);
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
                audioRecord.read(this.capturedAudioSamples, 0, 1024);
            }
            if (!this._isAudioPaused) {
                Charset UTF_8 = StandardCharsets.UTF_8;
                Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                byte[] bytes = DATA_CHANNEL_HEADER.getBytes(UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                ByteBuffer wrap = ByteBuffer.wrap(ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(bytes, (byte) 1), (byte) 1), toBytes((short) 1025)), (byte) Ascii.ESC), swapForAudioData(this.capturedAudioSamples)));
                Intrinsics.checkNotNullExpressionValue(wrap, "wrap(data)");
                this._callback.sendAudio(wrap);
            }
        }
    }

    private final byte[] toBytes(short s) {
        byte[] bArr = new byte[2];
        int i = 0;
        int i2 = s;
        while (i < 2) {
            bArr[i] = (byte) (i2 & 255);
            i++;
            i2 >>= 8;
        }
        return bArr;
    }

    private final byte[] swapForAudioData(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        IntProgression step = RangesKt.step(RangesKt.until(0, bArr.length - 1), 2);
        int first = step.getFirst();
        int last = step.getLast();
        int step2 = step.getStep();
        if ((step2 > 0 && first <= last) || (step2 < 0 && last <= first)) {
            while (true) {
                int i = first + 1;
                bArr2[first] = bArr[i];
                bArr2[i] = bArr[first];
                if (first == last) {
                    break;
                }
                first += step2;
            }
        }
        return bArr2;
    }

    private final void stopAudioCapture() {
        try {
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
        } catch (Exception unused) {
        }
    }

    /* compiled from: AudioController.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioController$Companion;", "", "()V", "AUDIOFORMAT_SIZE", "", "BUFFER_SIZE_IN_BYTES_FOR_WEBRTC", "CHANNEL_FOR_WEBRTC", "CODEC", "", "DATA_CHANNEL_HEADER", "DATA_CHANNEL_PROTCOL_VERSION", "", "DATA_ID", "FREQUENCY_FOR_WEBRTC", "QUANTIZE_FOR_WEBRTC", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
