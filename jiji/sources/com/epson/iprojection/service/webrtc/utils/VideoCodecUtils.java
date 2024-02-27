package com.epson.iprojection.service.webrtc.utils;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import com.epson.iprojection.common.Lg;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: VideoCodecUtils.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/service/webrtc/utils/VideoCodecUtils;", "", "()V", "EXYNOS_PREFIX", "", "getEXYNOS_PREFIX", "()Ljava/lang/String;", "QCOM_PREFIX", "getQCOM_PREFIX", "isH264Usable", "", "isHardwareSupportedInCurrentSdkH264", "info", "Landroid/media/MediaCodecInfo;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class VideoCodecUtils {
    public static final VideoCodecUtils INSTANCE = new VideoCodecUtils();
    private static final String QCOM_PREFIX = "OMX.qcom.";
    private static final String EXYNOS_PREFIX = "OMX.Exynos.";

    private VideoCodecUtils() {
    }

    public final String getQCOM_PREFIX() {
        return QCOM_PREFIX;
    }

    public final String getEXYNOS_PREFIX() {
        return EXYNOS_PREFIX;
    }

    public final boolean isH264Usable() {
        MediaCodecInfo mediaCodecInfo;
        Lg.i("isH264Usable");
        try {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < codecCount; i++) {
                try {
                    mediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
                } catch (IllegalArgumentException e) {
                    Lg.e("Cannot retrieve encoder codec info : " + e);
                    mediaCodecInfo = null;
                }
                if (mediaCodecInfo != null && mediaCodecInfo.isEncoder()) {
                    Lg.i("i[" + i + "] : " + mediaCodecInfo.getName() + " isEncoder:" + mediaCodecInfo.isEncoder() + ' ');
                    if (isHardwareSupportedInCurrentSdkH264(mediaCodecInfo)) {
                        Lg.i("H264対応");
                        return true;
                    }
                }
            }
            Lg.i("対応コーデックを全て調べたけどH264非対応でした");
            return false;
        } catch (Exception unused) {
            Lg.e("isH264UsableでException発生");
            return false;
        }
    }

    private final boolean isHardwareSupportedInCurrentSdkH264(MediaCodecInfo mediaCodecInfo) {
        String[] strArr;
        strArr = VideoCodecUtilsKt.H264_HW_EXCEPTION_MODELS;
        if (ArraysKt.contains(strArr, Build.MODEL)) {
            return false;
        }
        String name = mediaCodecInfo.getName();
        Intrinsics.checkNotNullExpressionValue(name, "info.name");
        if (!StringsKt.startsWith$default(name, QCOM_PREFIX, false, 2, (Object) null)) {
            String name2 = mediaCodecInfo.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "info.name");
            if (!StringsKt.startsWith$default(name2, EXYNOS_PREFIX, false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }
}
