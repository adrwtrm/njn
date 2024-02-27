package com.epson.iprojection.service.webrtc;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.util.Size;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.webrtc.core.Contract;
import com.epson.iprojection.service.webrtc.core.WebRTC;
import com.epson.iprojection.service.webrtc.utils.WebRTCUtils;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringUtils;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: WebRTCEntrance.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001%B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\b\u0010\u0017\u001a\u00020\u000eH\u0007J\u0006\u0010\u0018\u001a\u00020\u0014J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u001aJ\u0010\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0004J\u000e\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u0006J\u0010\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\fH\u0007J\b\u0010#\u001a\u00020\u000eH\u0007J\b\u0010$\u001a\u00020\u000eH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/epson/iprojection/service/webrtc/WebRTCEntrance;", "Lcom/epson/iprojection/service/webrtc/StateMachine;", "()V", "_captureIntent", "Landroid/content/Intent;", "_context", "Landroid/content/Context;", "_pjResolution", "Landroid/util/Size;", "_rtc", "Lcom/epson/iprojection/service/webrtc/core/WebRTC;", "createOfferSDP", "", "width", "", "height", "bandWidth", "ipAddress", "partition", "isAudioEnabled", "", "getMediaProjection", "Landroid/media/projection/MediaProjection;", "init", "isWebRTCProcessing", "onRotated", "", "recreateVirtualDisplay", "setAudioEnabled", "setCaptureIntent", "captureIntent", "setContext", "context", "start", "answerSdp", "stop", "uninit", "ImplMediaProjectionCallback", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WebRTCEntrance extends StateMachine {
    private static Intent _captureIntent;
    private static Context _context;
    private static WebRTC _rtc;
    public static final WebRTCEntrance INSTANCE = new WebRTCEntrance();
    private static Size _pjResolution = new Size(0, 0);

    private WebRTCEntrance() {
    }

    @JvmStatic
    public static final synchronized int init() {
        synchronized (WebRTCEntrance.class) {
            Lg.i("init");
            WebRTCEntrance webRTCEntrance = INSTANCE;
            if (webRTCEntrance.canInit() && _context != null) {
                Context context = _context;
                Intrinsics.checkNotNull(context);
                _rtc = new WebRTC(context);
                webRTCEntrance.setStatus(WebRTCStatus.initialized);
                return 0;
            }
            Lg.e("error return");
            return -1;
        }
    }

    @JvmStatic
    public static final synchronized int uninit() {
        WebRTC webRTC;
        synchronized (WebRTCEntrance.class) {
            Lg.i("uninit");
            WebRTCEntrance webRTCEntrance = INSTANCE;
            if (webRTCEntrance.canUninit() && (webRTC = _rtc) != null) {
                Intrinsics.checkNotNull(webRTC);
                if (!webRTC.isClosed()) {
                    WebRTC webRTC2 = _rtc;
                    Intrinsics.checkNotNull(webRTC2);
                    webRTC2.close();
                    _rtc = null;
                }
                webRTCEntrance.setStatus(WebRTCStatus.uninitialized);
                return 0;
            }
            Lg.e("error return");
            return -1;
        }
    }

    /* JADX WARN: Type inference failed for: r6v6, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v8, types: [T, java.lang.String] */
    @JvmStatic
    public static final synchronized String createOfferSDP(int i, int i2, int i3, String ipAddress, int i4, boolean z) {
        synchronized (WebRTCEntrance.class) {
            Intrinsics.checkNotNullParameter(ipAddress, "ipAddress");
            Lg.i("createOfferSDP w=" + i + " h=" + i2 + " bandWidth=" + i3 + " ipAddress=" + ipAddress + " partition=" + i4 + " isAudioEnabled=" + z);
            if (INSTANCE.canCreateOfferSDP() && _rtc != null && _captureIntent != null) {
                _pjResolution = new Size(i, i2);
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = "";
                final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                booleanRef.element = true;
                WebRTC webRTC = _rtc;
                Intrinsics.checkNotNull(webRTC);
                webRTC.setup(new Contract.IWebRTCEventListener() { // from class: com.epson.iprojection.service.webrtc.WebRTCEntrance$createOfferSDP$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.epson.iprojection.service.webrtc.core.Contract.IWebRTCEventListener
                    public void onLocalSdpCreated(String sdp) {
                        Intrinsics.checkNotNullParameter(sdp, "sdp");
                        Lg.d("offerSDPを生成しました");
                        objectRef.element = sdp;
                        booleanRef.element = false;
                    }
                });
                WebRTC webRTC2 = _rtc;
                Intrinsics.checkNotNull(webRTC2);
                Intent intent = _captureIntent;
                Intrinsics.checkNotNull(intent);
                webRTC2.setupLocalDisplayStream(intent, new ImplMediaProjectionCallback());
                WebRTC webRTC3 = _rtc;
                Intrinsics.checkNotNull(webRTC3);
                webRTC3.createOffer();
                int i5 = 0;
                while (booleanRef.element) {
                    Thread.sleep(1L);
                    i5++;
                    if (i5 >= 10000) {
                        break;
                    }
                }
                if (i5 >= 10000) {
                    Lg.e("offerSDP生成失敗！！");
                    return null;
                }
                Lg.i("offerSDP生成成功");
                WebRTC webRTC4 = _rtc;
                if (webRTC4 != null) {
                    webRTC4.setAudioEnabled(z);
                }
                objectRef.element = WebRTCUtils.INSTANCE.addCustomSDPOfIPAddress((String) objectRef.element, ipAddress);
                objectRef.element = WebRTCUtils.INSTANCE.addCustomSDPOfCodec((String) objectRef.element);
                INSTANCE.setStatus(WebRTCStatus.sdpcreated);
                return (String) objectRef.element;
            }
            Lg.e("error return");
            return null;
        }
    }

    @JvmStatic
    public static final synchronized int start(String answerSdp) {
        synchronized (WebRTCEntrance.class) {
            Intrinsics.checkNotNullParameter(answerSdp, "answerSdp");
            Lg.i("start");
            WebRTCEntrance webRTCEntrance = INSTANCE;
            if (webRTCEntrance.canStart() && _rtc != null && _context != null) {
                MirroringUtils.Companion companion = MirroringUtils.Companion;
                Context context = _context;
                Intrinsics.checkNotNull(context);
                Size virtualDisplayResolution = companion.getVirtualDisplayResolution(context, _pjResolution);
                Lg.d("virtualResolution w:" + virtualDisplayResolution.getWidth() + " h:" + virtualDisplayResolution.getHeight());
                WebRTC webRTC = _rtc;
                Intrinsics.checkNotNull(webRTC);
                webRTC.receiveAnswer(answerSdp);
                WebRTC webRTC2 = _rtc;
                Intrinsics.checkNotNull(webRTC2);
                webRTC2.startCapture(virtualDisplayResolution);
                webRTCEntrance.setStatus(WebRTCStatus.started);
                return 0;
            }
            Lg.e("error return");
            return -1;
        }
    }

    @JvmStatic
    public static final synchronized int stop() {
        WebRTC webRTC;
        synchronized (WebRTCEntrance.class) {
            Lg.i("stop");
            WebRTCEntrance webRTCEntrance = INSTANCE;
            if (webRTCEntrance.canStop() && (webRTC = _rtc) != null) {
                Intrinsics.checkNotNull(webRTC);
                webRTC.close();
                Pj.getIns().sendImageOfMirroringOff();
                webRTCEntrance.setStatus(WebRTCStatus.stopped);
                return 0;
            }
            Lg.e("error return");
            return -1;
        }
    }

    @JvmStatic
    public static final synchronized int setAudioEnabled(boolean z) {
        synchronized (WebRTCEntrance.class) {
            WebRTC webRTC = _rtc;
            if (webRTC == null) {
                Lg.e("error return");
                return -1;
            }
            if (webRTC != null) {
                webRTC.setAudioEnabled(z);
            }
            return 0;
        }
    }

    public final void setContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Lg.i("setContext");
        _context = context;
    }

    public final void setCaptureIntent(Intent captureIntent) {
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        Lg.i("setCaptureIntent");
        _captureIntent = captureIntent;
    }

    public final void onRotated() {
        Lg.i("onRotated");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new WebRTCEntrance$onRotated$1(null), 3, null);
    }

    public final void recreateVirtualDisplay() {
        Lg.i("recreateVirtualDisplay");
        if (_rtc == null || _context == null) {
            return;
        }
        MirroringUtils.Companion companion = MirroringUtils.Companion;
        Context context = _context;
        Intrinsics.checkNotNull(context);
        Size virtualDisplayResolution = companion.getVirtualDisplayResolution(context, _pjResolution);
        Lg.d("virtualResolution w:" + virtualDisplayResolution.getWidth() + " h:" + virtualDisplayResolution.getHeight());
        WebRTC webRTC = _rtc;
        Intrinsics.checkNotNull(webRTC);
        webRTC.changeResolution(virtualDisplayResolution.getWidth(), virtualDisplayResolution.getHeight());
    }

    public final boolean isWebRTCProcessing() {
        return get_state() == WebRTCStatus.sdpcreated || get_state() == WebRTCStatus.started;
    }

    public final MediaProjection getMediaProjection() {
        WebRTC webRTC = _rtc;
        if (webRTC == null) {
            return null;
        }
        Intrinsics.checkNotNull(webRTC);
        return webRTC.getMediaProjection();
    }

    /* compiled from: WebRTCEntrance.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/service/webrtc/WebRTCEntrance$ImplMediaProjectionCallback;", "Landroid/media/projection/MediaProjection$Callback;", "()V", "onStop", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private static final class ImplMediaProjectionCallback extends MediaProjection.Callback {
        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            super.onStop();
            Lg.w("MediaProjectionの異常終了を検出");
            WebRTCEntrance.stop();
            Context context = WebRTCEntrance._context;
            if (context != null) {
                MirroringEntrance.INSTANCE.finish(context);
            }
        }
    }
}
