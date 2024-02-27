package com.epson.iprojection.service.webrtc.core;

import android.content.Context;
import android.content.Intent;
import java.nio.ByteBuffer;
import kotlin.Metadata;

/* compiled from: AudioContract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioContract;", "", "IAudioCapture", "IAudioCaptureCallback", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface AudioContract {

    /* compiled from: AudioContract.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\u001a\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\b\u0010\f\u001a\u00020\u0005H&¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCapture;", "", "isStopped", "", "pauseAudio", "", "resumeAudio", "start", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "stop", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IAudioCapture {
        boolean isStopped();

        void pauseAudio();

        void resumeAudio();

        void start(Context context, Intent intent);

        void stop();
    }

    /* compiled from: AudioContract.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCaptureCallback;", "", "sendAudio", "", "audioBuffer", "Ljava/nio/ByteBuffer;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IAudioCaptureCallback {
        void sendAudio(ByteBuffer byteBuffer);
    }
}
