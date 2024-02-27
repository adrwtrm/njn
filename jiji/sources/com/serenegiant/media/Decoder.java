package com.serenegiant.media;

import android.media.MediaExtractor;

/* loaded from: classes2.dex */
public interface Decoder {
    int prepare(MediaExtractor mediaExtractor) throws IllegalArgumentException;

    void release();

    void start();

    void stop();
}
