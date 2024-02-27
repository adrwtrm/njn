package com.epson.iprojection.service.webrtc.utils;

import android.content.Context;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.common.utils.DeviceInfoUtils;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WebRTCUtils.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J&\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0016\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000f¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/service/webrtc/utils/WebRTCUtils;", "", "()V", "addCustomSDPOfCodec", "", "offerSdp", "addCustomSDPOfIPAddress", "ipAddress", "shouldUseWebRTC", "", "context", "Landroid/content/Context;", "list", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/engine_wrapper/ConnectPjInfo;", "Lkotlin/collections/ArrayList;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WebRTCUtils {
    public static final WebRTCUtils INSTANCE = new WebRTCUtils();

    private WebRTCUtils() {
    }

    public final boolean shouldUseWebRTC(Context context, ArrayList<ConnectPjInfo> list) {
        boolean z;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(list, "list");
        if (list.size() >= 2) {
            return false;
        }
        ArrayList<ConnectPjInfo> arrayList = list;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            for (ConnectPjInfo connectPjInfo : arrayList) {
                if (!connectPjInfo.getPjInfo().isSupportedWebRTC) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        return (z || ChromeOSUtils.INSTANCE.isChromeOS(context) || DeviceInfoUtils.INSTANCE.isDeviceHuawei() || !VideoCodecUtils.INSTANCE.isH264Usable()) ? false : true;
    }

    public final String addCustomSDPOfIPAddress(String offerSdp, String ipAddress) {
        Intrinsics.checkNotNullParameter(offerSdp, "offerSdp");
        Intrinsics.checkNotNullParameter(ipAddress, "ipAddress");
        return offerSdp + "ep=receiver-ip-addr:" + ipAddress + '\n';
    }

    public final String addCustomSDPOfCodec(String offerSdp) {
        Intrinsics.checkNotNullParameter(offerSdp, "offerSdp");
        return offerSdp + "ep=codec:" + (VideoCodecUtils.INSTANCE.isH264Usable() ? "H264" : "VP8") + '\n';
    }
}
