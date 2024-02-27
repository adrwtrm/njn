package com.epson.iprojection.common.utils;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import com.epson.iprojection.common.Lg;
import kotlin.Metadata;

/* compiled from: EncoderInfoUtils.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/common/utils/EncoderInfoUtils;", "", "()V", "show", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class EncoderInfoUtils {
    public static final EncoderInfoUtils INSTANCE = new EncoderInfoUtils();

    private EncoderInfoUtils() {
    }

    public final void show() {
        MediaCodecInfo mediaCodecInfo;
        Lg.e("** エンコーダーリスト **");
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            try {
                mediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
            } catch (IllegalArgumentException unused) {
                mediaCodecInfo = null;
            }
            if (mediaCodecInfo != null && mediaCodecInfo.isEncoder()) {
                Lg.e(mediaCodecInfo.getName());
            }
        }
        Lg.e("** エンコーダーリスト ここまで **");
    }
}
