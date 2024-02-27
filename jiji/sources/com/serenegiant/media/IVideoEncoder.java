package com.serenegiant.media;

/* loaded from: classes2.dex */
public interface IVideoEncoder extends Encoder {
    int getHeight();

    int getWidth();

    void setVideoConfig(int i, int i2, int i3);

    void setVideoSize(int i, int i2) throws IllegalArgumentException, IllegalStateException;
}
