package com.epson.iprojection.service.webrtc.core;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.util.Size;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.webrtc.core.AudioContract;
import com.epson.iprojection.service.webrtc.core.Contract;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.webrtc.DataChannel;
import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.ScreenCapturerAndroid;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

/* compiled from: WebRTC.kt */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u00017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u0006\u0010!\u001a\u00020\u001dJ\u0006\u0010\"\u001a\u00020\u001dJ\b\u0010#\u001a\u0004\u0018\u00010$J\u000e\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'J\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\fJ\u000e\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020,J\u0018\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020\u00102\u0006\u0010/\u001a\u000200H\u0002J\u0016\u00101\u001a\u00020\u001d2\u0006\u00102\u001a\u00020\u00102\u0006\u0010/\u001a\u000200J\u000e\u00103\u001a\u00020\u001d2\u0006\u00104\u001a\u000205J\u0006\u00106\u001a\u00020\u001dR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u00068"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/WebRTC;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_audioController", "Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCapture;", "_connection", "Lcom/epson/iprojection/service/webrtc/core/Connection;", "_eglBase", "Lorg/webrtc/EglBase;", "_isAudioPaused", "", "_localStream", "Lorg/webrtc/MediaStream;", "_permissionResultData", "Landroid/content/Intent;", "_surfaceTextureHelper", "Lorg/webrtc/SurfaceTextureHelper;", "_videoCapturer", "Lorg/webrtc/ScreenCapturerAndroid;", "_videoSource", "Lorg/webrtc/VideoSource;", "_videoTrack", "Lorg/webrtc/VideoTrack;", "<set-?>", "isClosed", "()Z", "changeResolution", "", "width", "", "height", "close", "createOffer", "getMediaProjection", "Landroid/media/projection/MediaProjection;", "receiveAnswer", "sdp", "", "setAudioEnabled", "isEnabled", "setup", "impl", "Lcom/epson/iprojection/service/webrtc/core/Contract$IWebRTCEventListener;", "setupLocalCapture", "permissionResultData", "callback", "Landroid/media/projection/MediaProjection$Callback;", "setupLocalDisplayStream", "pemissionResultData", "startCapture", "resolution", "Landroid/util/Size;", "stopCapture", "ImplICallback", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WebRTC {
    private AudioContract.IAudioCapture _audioController;
    private Connection _connection;
    private EglBase _eglBase;
    private boolean _isAudioPaused;
    private MediaStream _localStream;
    private Intent _permissionResultData;
    private SurfaceTextureHelper _surfaceTextureHelper;
    private ScreenCapturerAndroid _videoCapturer;
    private VideoSource _videoSource;
    private VideoTrack _videoTrack;
    private final Context context;
    private boolean isClosed;

    public WebRTC(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.isClosed = true;
    }

    public final boolean isClosed() {
        return this.isClosed;
    }

    public final boolean setup(Contract.IWebRTCEventListener impl) {
        Intrinsics.checkNotNullParameter(impl, "impl");
        this.isClosed = false;
        this._eglBase = EglBase.create();
        this._connection = new Connection(impl);
        this._audioController = new AudioController(new ImplICallback(), this._isAudioPaused);
        Connection connection = this._connection;
        Intrinsics.checkNotNull(connection);
        Context context = this.context;
        EglBase eglBase = this._eglBase;
        Intrinsics.checkNotNull(eglBase);
        EglBase.Context eglBaseContext = eglBase.getEglBaseContext();
        Intrinsics.checkNotNullExpressionValue(eglBaseContext, "_eglBase!!.eglBaseContext");
        return connection.setup(context, eglBaseContext);
    }

    public final void setupLocalDisplayStream(Intent pemissionResultData, MediaProjection.Callback callback) {
        Intrinsics.checkNotNullParameter(pemissionResultData, "pemissionResultData");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this._permissionResultData = pemissionResultData;
        setupLocalCapture(pemissionResultData, callback);
        Connection connection = this._connection;
        if (connection != null) {
            MediaStream mediaStream = this._localStream;
            Intrinsics.checkNotNull(mediaStream);
            connection.addStream(mediaStream);
        }
    }

    public final void startCapture(Size resolution) {
        AudioContract.IAudioCapture iAudioCapture;
        Intrinsics.checkNotNullParameter(resolution, "resolution");
        ScreenCapturerAndroid screenCapturerAndroid = this._videoCapturer;
        if (screenCapturerAndroid != null) {
            screenCapturerAndroid.startCapture(resolution.getWidth(), resolution.getHeight(), 60);
        }
        Intent intent = this._permissionResultData;
        if (intent == null || (iAudioCapture = this._audioController) == null) {
            return;
        }
        iAudioCapture.start(this.context, intent);
    }

    public final void createOffer() {
        Connection connection = this._connection;
        if (connection != null) {
            connection.createOffer();
        }
    }

    public final void receiveAnswer(String sdp) {
        Intrinsics.checkNotNullParameter(sdp, "sdp");
        Connection connection = this._connection;
        if (connection != null) {
            connection.receiveAnswer(sdp);
        }
    }

    public final void stopCapture() {
        ScreenCapturerAndroid screenCapturerAndroid = this._videoCapturer;
        if (screenCapturerAndroid == null) {
            return;
        }
        try {
            Intrinsics.checkNotNull(screenCapturerAndroid);
            if (screenCapturerAndroid.isScreencast()) {
                ScreenCapturerAndroid screenCapturerAndroid2 = this._videoCapturer;
                Intrinsics.checkNotNull(screenCapturerAndroid2);
                screenCapturerAndroid2.stopCapture();
                AudioContract.IAudioCapture iAudioCapture = this._audioController;
                if (iAudioCapture != null) {
                    iAudioCapture.stop();
                }
            }
        } catch (Exception e) {
            Lg.e("pause InterruptException!! : " + e.getMessage());
        }
    }

    public final void close() {
        AudioContract.IAudioCapture iAudioCapture;
        stopCapture();
        MediaStream mediaStream = this._localStream;
        if (mediaStream != null) {
            mediaStream.dispose();
        }
        this._localStream = null;
        VideoSource videoSource = this._videoSource;
        if (videoSource != null) {
            videoSource.dispose();
        }
        this._videoSource = null;
        this._videoTrack = null;
        ScreenCapturerAndroid screenCapturerAndroid = this._videoCapturer;
        if (screenCapturerAndroid != null) {
            screenCapturerAndroid.dispose();
        }
        this._videoCapturer = null;
        Connection connection = this._connection;
        if (connection != null) {
            connection.close();
        }
        this._connection = null;
        EglBase eglBase = this._eglBase;
        if (eglBase != null) {
            eglBase.release();
        }
        this._eglBase = null;
        SurfaceTextureHelper surfaceTextureHelper = this._surfaceTextureHelper;
        if (surfaceTextureHelper != null) {
            surfaceTextureHelper.dispose();
        }
        this._surfaceTextureHelper = null;
        AudioContract.IAudioCapture iAudioCapture2 = this._audioController;
        if (iAudioCapture2 != null) {
            Intrinsics.checkNotNull(iAudioCapture2);
            if (!iAudioCapture2.isStopped() && (iAudioCapture = this._audioController) != null) {
                iAudioCapture.stop();
            }
        }
        this._audioController = null;
        this.isClosed = true;
    }

    public final void setAudioEnabled(boolean z) {
        this._isAudioPaused = !z;
        if (z) {
            AudioContract.IAudioCapture iAudioCapture = this._audioController;
            if (iAudioCapture != null) {
                iAudioCapture.resumeAudio();
                return;
            }
            return;
        }
        AudioContract.IAudioCapture iAudioCapture2 = this._audioController;
        if (iAudioCapture2 != null) {
            iAudioCapture2.pauseAudio();
        }
    }

    public final void changeResolution(int i, int i2) {
        ScreenCapturerAndroid screenCapturerAndroid = this._videoCapturer;
        if (screenCapturerAndroid != null) {
            screenCapturerAndroid.changeCaptureFormat(i, i2, 60);
        }
    }

    public final MediaProjection getMediaProjection() {
        ScreenCapturerAndroid screenCapturerAndroid = this._videoCapturer;
        if (screenCapturerAndroid == null) {
            return null;
        }
        Intrinsics.checkNotNull(screenCapturerAndroid);
        return screenCapturerAndroid.getMediaProjection();
    }

    private final void setupLocalCapture(Intent intent, MediaProjection.Callback callback) {
        Connection connection = this._connection;
        if (connection == null || this._eglBase == null) {
            return;
        }
        Intrinsics.checkNotNull(connection);
        this._localStream = connection.createLocalMediaStream();
        this._videoCapturer = new ScreenCapturerAndroid(intent, callback);
        EglBase eglBase = this._eglBase;
        Intrinsics.checkNotNull(eglBase);
        this._surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", eglBase.getEglBaseContext());
        Connection connection2 = this._connection;
        Intrinsics.checkNotNull(connection2);
        ScreenCapturerAndroid screenCapturerAndroid = this._videoCapturer;
        Intrinsics.checkNotNull(screenCapturerAndroid);
        this._videoSource = connection2.createVideoSource(screenCapturerAndroid.isScreencast());
        Connection connection3 = this._connection;
        Intrinsics.checkNotNull(connection3);
        VideoSource videoSource = this._videoSource;
        Intrinsics.checkNotNull(videoSource);
        this._videoTrack = connection3.createVideoTrack(videoSource);
        ScreenCapturerAndroid screenCapturerAndroid2 = this._videoCapturer;
        Intrinsics.checkNotNull(screenCapturerAndroid2);
        SurfaceTextureHelper surfaceTextureHelper = this._surfaceTextureHelper;
        Context context = this.context;
        VideoSource videoSource2 = this._videoSource;
        Intrinsics.checkNotNull(videoSource2);
        screenCapturerAndroid2.initialize(surfaceTextureHelper, context, videoSource2.getCapturerObserver());
        MediaStream mediaStream = this._localStream;
        Intrinsics.checkNotNull(mediaStream);
        mediaStream.addTrack(this._videoTrack);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: WebRTC.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/WebRTC$ImplICallback;", "Lcom/epson/iprojection/service/webrtc/core/AudioContract$IAudioCaptureCallback;", "(Lcom/epson/iprojection/service/webrtc/core/WebRTC;)V", "sendAudio", "", "audioBuffer", "Ljava/nio/ByteBuffer;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ImplICallback implements AudioContract.IAudioCaptureCallback {
        public ImplICallback() {
            WebRTC.this = r1;
        }

        @Override // com.epson.iprojection.service.webrtc.core.AudioContract.IAudioCaptureCallback
        public void sendAudio(ByteBuffer audioBuffer) {
            Intrinsics.checkNotNullParameter(audioBuffer, "audioBuffer");
            Connection connection = WebRTC.this._connection;
            if (connection != null) {
                connection.sendAudio(new DataChannel.Buffer(audioBuffer, false));
            }
        }
    }
}
