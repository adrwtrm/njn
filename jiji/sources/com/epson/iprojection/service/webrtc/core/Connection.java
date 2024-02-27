package com.epson.iprojection.service.webrtc.core;

import android.content.Context;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.webrtc.core.Contract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionDependencies;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SessionDescription;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

/* compiled from: Connection.kt */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u000eJ\u0006\u0010\u0012\u001a\u00020\u0010J\u0006\u0010\u0013\u001a\u00020\u000eJ\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0017J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0010H\u0016J#\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00100#H\u0016¢\u0006\u0002\u0010$J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0006H\u0016J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u001b\u0010*\u001a\u00020\u000e2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020)0#H\u0016¢\u0006\u0002\u0010,J\u0010\u0010-\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u0019H\u0016J\u0010\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0010H\u0016J\b\u00106\u001a\u00020\u000eH\u0016J\u0010\u00107\u001a\u00020\u000e2\u0006\u00108\u001a\u000209H\u0016J\u000e\u0010:\u001a\u00020\u000e2\u0006\u0010;\u001a\u00020\bJ\u000e\u0010<\u001a\u00020\u000e2\u0006\u0010=\u001a\u00020>J\u0016\u0010?\u001a\u00020\u00192\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/Connection;", "Lorg/webrtc/PeerConnection$Observer;", "_listener", "Lcom/epson/iprojection/service/webrtc/core/Contract$IWebRTCEventListener;", "(Lcom/epson/iprojection/service/webrtc/core/Contract$IWebRTCEventListener;)V", "_dataChannel", "Lorg/webrtc/DataChannel;", "_dataChannelLabel", "", "_factory", "Lorg/webrtc/PeerConnectionFactory;", "_peerConnection", "Lorg/webrtc/PeerConnection;", "addStream", "", "stream", "Lorg/webrtc/MediaStream;", "close", "createLocalMediaStream", "createOffer", "createOfferConnectionConstraints", "Lorg/webrtc/MediaConstraints;", "createVideoSource", "Lorg/webrtc/VideoSource;", "isScreencast", "", "createVideoTrack", "Lorg/webrtc/VideoTrack;", FirebaseAnalytics.Param.SOURCE, "onAddStream", "mediaStream", "onAddTrack", "rtpReceiver", "Lorg/webrtc/RtpReceiver;", "mediaStreams", "", "(Lorg/webrtc/RtpReceiver;[Lorg/webrtc/MediaStream;)V", "onDataChannel", "dataChannel", "onIceCandidate", "iceCandidate", "Lorg/webrtc/IceCandidate;", "onIceCandidatesRemoved", "iceCandidates", "([Lorg/webrtc/IceCandidate;)V", "onIceConnectionChange", "iceConnectionState", "Lorg/webrtc/PeerConnection$IceConnectionState;", "onIceConnectionReceivingChange", "b", "onIceGatheringChange", "iceGatheringState", "Lorg/webrtc/PeerConnection$IceGatheringState;", "onRemoveStream", "onRenegotiationNeeded", "onSignalingChange", "signalingState", "Lorg/webrtc/PeerConnection$SignalingState;", "receiveAnswer", "sdp", "sendAudio", "data", "Lorg/webrtc/DataChannel$Buffer;", "setup", "context", "Landroid/content/Context;", "eglContext", "Lorg/webrtc/EglBase$Context;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Connection implements PeerConnection.Observer {
    private DataChannel _dataChannel;
    private final String _dataChannelLabel;
    private PeerConnectionFactory _factory;
    private final Contract.IWebRTCEventListener _listener;
    private PeerConnection _peerConnection;

    public Connection(Contract.IWebRTCEventListener _listener) {
        Intrinsics.checkNotNullParameter(_listener, "_listener");
        this._listener = _listener;
        this._dataChannelLabel = "Datachannel";
    }

    public static final /* synthetic */ PeerConnection access$get_peerConnection$p(Connection connection) {
        return connection._peerConnection;
    }

    public final boolean setup(Context context, EglBase.Context eglContext) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(eglContext, "eglContext");
        Lg.d("setupPeerConnection");
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(context).createInitializationOptions());
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        PeerConnectionFactory createPeerConnectionFactory = PeerConnectionFactory.builder().setOptions(options).setVideoEncoderFactory(new DefaultVideoEncoderFactory(eglContext, true, false)).setVideoDecoderFactory(new DefaultVideoDecoderFactory(eglContext)).createPeerConnectionFactory();
        Intrinsics.checkNotNullExpressionValue(createPeerConnectionFactory, "builder()\n            .s…tePeerConnectionFactory()");
        this._factory = createPeerConnectionFactory;
        PeerConnectionDependencies createPeerConnectionDependencies = PeerConnectionDependencies.builder(this).createPeerConnectionDependencies();
        PeerConnection.RTCConfiguration rTCConfiguration = new PeerConnection.RTCConfiguration(new ArrayList());
        rTCConfiguration.enableDtlsSrtp = true;
        PeerConnectionFactory peerConnectionFactory = this._factory;
        PeerConnection peerConnection = null;
        if (peerConnectionFactory == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_factory");
            peerConnectionFactory = null;
        }
        PeerConnection createPeerConnection = peerConnectionFactory.createPeerConnection(rTCConfiguration, createPeerConnectionDependencies);
        if (createPeerConnection == null) {
            return false;
        }
        this._peerConnection = createPeerConnection;
        DataChannel.Init init = new DataChannel.Init();
        PeerConnection peerConnection2 = this._peerConnection;
        if (peerConnection2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
        } else {
            peerConnection = peerConnection2;
        }
        this._dataChannel = peerConnection.createDataChannel(this._dataChannelLabel, init);
        return true;
    }

    public final void createOffer() {
        Lg.d("createOffer");
        PeerConnection peerConnection = this._peerConnection;
        if (peerConnection == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
            peerConnection = null;
        }
        peerConnection.createOffer(new SkeletalSdpObserver() { // from class: com.epson.iprojection.service.webrtc.core.Connection$createOffer$1
            {
                Connection.this = this;
            }

            @Override // com.epson.iprojection.service.webrtc.core.SkeletalSdpObserver, org.webrtc.SdpObserver
            public void onCreateSuccess(SessionDescription sessionDescription) {
                Intrinsics.checkNotNullParameter(sessionDescription, "sessionDescription");
                PeerConnection access$get_peerConnection$p = Connection.access$get_peerConnection$p(Connection.this);
                if (access$get_peerConnection$p == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
                    access$get_peerConnection$p = null;
                }
                access$get_peerConnection$p.setLocalDescription(new SkeletalSdpObserver() { // from class: com.epson.iprojection.service.webrtc.core.Connection$createOffer$1$onCreateSuccess$1
                }, sessionDescription);
            }
        }, createOfferConnectionConstraints());
    }

    public final void receiveAnswer(String sdp) {
        Intrinsics.checkNotNullParameter(sdp, "sdp");
        Lg.d("receiveAnswer");
        SessionDescription sessionDescription = new SessionDescription(SessionDescription.Type.ANSWER, sdp);
        PeerConnection peerConnection = this._peerConnection;
        if (peerConnection == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
            peerConnection = null;
        }
        peerConnection.setRemoteDescription(new SkeletalSdpObserver() { // from class: com.epson.iprojection.service.webrtc.core.Connection$receiveAnswer$1
            @Override // com.epson.iprojection.service.webrtc.core.SkeletalSdpObserver, org.webrtc.SdpObserver
            public void onSetSuccess() {
                Lg.d("receiveAnswer onSetSuccess");
            }
        }, sessionDescription);
    }

    public final void close() {
        PeerConnection peerConnection = this._peerConnection;
        if (peerConnection == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
            peerConnection = null;
        }
        peerConnection.close();
    }

    public final void addStream(MediaStream stream) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        PeerConnection peerConnection = this._peerConnection;
        if (peerConnection == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
            peerConnection = null;
        }
        peerConnection.addStream(stream);
    }

    public final void sendAudio(DataChannel.Buffer data) {
        Intrinsics.checkNotNullParameter(data, "data");
        DataChannel dataChannel = this._dataChannel;
        if (dataChannel != null) {
            dataChannel.send(data);
        }
    }

    public final MediaStream createLocalMediaStream() {
        PeerConnectionFactory peerConnectionFactory = this._factory;
        if (peerConnectionFactory == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_factory");
            peerConnectionFactory = null;
        }
        MediaStream createLocalMediaStream = peerConnectionFactory.createLocalMediaStream("android_local_stream");
        Intrinsics.checkNotNullExpressionValue(createLocalMediaStream, "_factory.createLocalMedi…m(\"android_local_stream\")");
        return createLocalMediaStream;
    }

    public final VideoSource createVideoSource(boolean z) {
        PeerConnectionFactory peerConnectionFactory = this._factory;
        if (peerConnectionFactory == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_factory");
            peerConnectionFactory = null;
        }
        VideoSource createVideoSource = peerConnectionFactory.createVideoSource(z);
        Intrinsics.checkNotNullExpressionValue(createVideoSource, "_factory.createVideoSource(isScreencast)");
        return createVideoSource;
    }

    public final VideoTrack createVideoTrack(VideoSource source) {
        Intrinsics.checkNotNullParameter(source, "source");
        PeerConnectionFactory peerConnectionFactory = this._factory;
        if (peerConnectionFactory == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_factory");
            peerConnectionFactory = null;
        }
        VideoTrack createVideoTrack = peerConnectionFactory.createVideoTrack("android_local_videotrack", source);
        Intrinsics.checkNotNullExpressionValue(createVideoTrack, "_factory.createVideoTrac…ocal_videotrack\", source)");
        return createVideoTrack;
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
        Intrinsics.checkNotNullParameter(iceGatheringState, "iceGatheringState");
        Lg.d("onIceGatheringChange : " + iceGatheringState);
        if (iceGatheringState == PeerConnection.IceGatheringState.COMPLETE) {
            Lg.d("Complete");
            PeerConnection peerConnection = this._peerConnection;
            if (peerConnection == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_peerConnection");
                peerConnection = null;
            }
            SessionDescription localDescription = peerConnection.getLocalDescription();
            Contract.IWebRTCEventListener iWebRTCEventListener = this._listener;
            String str = localDescription.description;
            Intrinsics.checkNotNullExpressionValue(str, "localSdp.description");
            iWebRTCEventListener.onLocalSdpCreated(str);
        }
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onDataChannel(final DataChannel dataChannel) {
        Intrinsics.checkNotNullParameter(dataChannel, "dataChannel");
        Lg.d("onDatachannel" + dataChannel.label());
        this._dataChannel = dataChannel;
        Intrinsics.checkNotNull(dataChannel);
        dataChannel.registerObserver(new DataChannel.Observer() { // from class: com.epson.iprojection.service.webrtc.core.Connection$onDataChannel$1
            @Override // org.webrtc.DataChannel.Observer
            public void onBufferedAmountChange(long j) {
                Lg.d("BufferedAmountChange");
                Lg.d("DC onBufferedAmountChange" + j);
            }

            @Override // org.webrtc.DataChannel.Observer
            public void onStateChange() {
                Lg.d("StateChange");
                Lg.d("DC onStateChange" + dataChannel.state());
                byte[] bytes = "aaa".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                dataChannel.send(new DataChannel.Buffer(ByteBuffer.wrap(bytes), false));
            }

            @Override // org.webrtc.DataChannel.Observer
            public void onMessage(DataChannel.Buffer buffer) {
                Intrinsics.checkNotNullParameter(buffer, "buffer");
                Lg.d("Message");
                ByteBuffer byteBuffer = buffer.data;
                byte[] bArr = new byte[byteBuffer.remaining()];
                byteBuffer.get(bArr);
                try {
                    Lg.d("DC onMessage " + new JSONObject(new String(bArr, Charsets.UTF_8)).getString("data"));
                } catch (JSONException unused) {
                }
            }
        });
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onIceCandidate(IceCandidate iceCandidate) {
        Intrinsics.checkNotNullParameter(iceCandidate, "iceCandidate");
        Lg.d("onIceCandidate : " + iceCandidate);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
        Intrinsics.checkNotNullParameter(iceCandidates, "iceCandidates");
        Lg.d("onIceCandidatesRemoved : " + iceCandidates);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onAddStream(MediaStream mediaStream) {
        Intrinsics.checkNotNullParameter(mediaStream, "mediaStream");
        Lg.d("onAddStream : " + mediaStream);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onRemoveStream(MediaStream mediaStream) {
        Intrinsics.checkNotNullParameter(mediaStream, "mediaStream");
        Lg.d("onRemoveStream : " + mediaStream);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onRenegotiationNeeded() {
        Lg.d("onRenegotiationNeeded");
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
        Intrinsics.checkNotNullParameter(rtpReceiver, "rtpReceiver");
        Intrinsics.checkNotNullParameter(mediaStreams, "mediaStreams");
        Lg.d("onAddTrack : " + rtpReceiver + " : " + mediaStreams);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onSignalingChange(PeerConnection.SignalingState signalingState) {
        Intrinsics.checkNotNullParameter(signalingState, "signalingState");
        Lg.d("SignalingChange : " + signalingState);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        Intrinsics.checkNotNullParameter(iceConnectionState, "iceConnectionState");
        Lg.d("IceConnectionChange : " + iceConnectionState);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public void onIceConnectionReceivingChange(boolean z) {
        Lg.d("IceConnectionReceivingChange : " + z);
    }

    private final MediaConstraints createOfferConnectionConstraints() {
        MediaConstraints mediaConstraints = new MediaConstraints();
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", CommonDefine.TRUE));
        return mediaConstraints;
    }
}
