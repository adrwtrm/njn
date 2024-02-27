package com.epson.iprojection.service.webrtc.thumbnail;

import android.content.Context;
import android.content.Intent;
import com.epson.iprojection.service.mirroring.ThumbnailCapturer;
import com.epson.iprojection.service.webrtc.utils.WebRTCUtils;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: ThumbnailCapturerEntrance.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0007J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/service/webrtc/thumbnail/ThumbnailCapturerEntrance;", "", "()V", "_context", "Landroid/content/Context;", "_thumb", "Lcom/epson/iprojection/service/mirroring/ThumbnailCapturer;", "createThumbnailFromVirtualDisplayIfWebRTCMirroring", "", "width", "", "height", "initialize", "", "context", "captureIntent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ThumbnailCapturerEntrance {
    public static final ThumbnailCapturerEntrance INSTANCE = new ThumbnailCapturerEntrance();
    private static Context _context;
    private static ThumbnailCapturer _thumb;

    private ThumbnailCapturerEntrance() {
    }

    public final void initialize(Context context, Intent captureIntent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        _thumb = new ThumbnailCapturer(context, captureIntent);
        _context = context;
    }

    @JvmStatic
    public static final byte[] createThumbnailFromVirtualDisplayIfWebRTCMirroring(int i, int i2) {
        if (!MirroringEntrance.INSTANCE.isMirroringSwitchOn() || _context == null) {
            return null;
        }
        WebRTCUtils webRTCUtils = WebRTCUtils.INSTANCE;
        Context context = _context;
        Intrinsics.checkNotNull(context);
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        Intrinsics.checkNotNullExpressionValue(nowConnectingPJList, "getIns().nowConnectingPJList");
        if (webRTCUtils.shouldUseWebRTC(context, nowConnectingPJList)) {
            ThumbnailCapturer thumbnailCapturer = _thumb;
            if (thumbnailCapturer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_thumb");
                thumbnailCapturer = null;
            }
            byte[] createThumbnail = thumbnailCapturer.createThumbnail(i, i2);
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1(null), 3, null);
            return createThumbnail;
        }
        return null;
    }
}
