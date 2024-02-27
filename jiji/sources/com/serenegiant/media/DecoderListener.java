package com.serenegiant.media;

/* loaded from: classes2.dex */
public interface DecoderListener {
    void onDestroy(Decoder decoder);

    void onError(Throwable th);

    void onStartDecode(Decoder decoder);

    void onStopDecode(Decoder decoder);
}
