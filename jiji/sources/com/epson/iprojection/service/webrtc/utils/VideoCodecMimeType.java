package com.epson.iprojection.service.webrtc.utils;

import kotlin.Metadata;

/* compiled from: VideoCodecUtils.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0002\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/service/webrtc/utils/VideoCodecMimeType;", "", "mimeType", "", "(Ljava/lang/String;ILjava/lang/String;)V", "VP8", "VP9", "H264", "AV1", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public enum VideoCodecMimeType {
    VP8("video/x-vnd.on2.vp8"),
    VP9("video/x-vnd.on2.vp9"),
    H264("video/avc"),
    AV1("video/av01");
    
    private final String mimeType;

    VideoCodecMimeType(String str) {
        this.mimeType = str;
    }

    public final String mimeType() {
        return this.mimeType;
    }
}
