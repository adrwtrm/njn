package com.serenegiant.media;

import android.os.Bundle;
import android.util.Log;
import com.serenegiant.system.BuildCheck;

/* loaded from: classes2.dex */
public abstract class AbstractVideoEncoder extends AbstractEncoder implements IVideoEncoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "AbstractVideoEncoder";
    public static boolean supportsAdaptiveStreaming = BuildCheck.isKitKat();
    protected int mBitRate;
    protected int mFramerate;
    protected int mHeight;
    protected int mIFrameIntervals;
    protected int mWidth;

    @Override // com.serenegiant.media.Encoder
    public final boolean isAudio() {
        return false;
    }

    public AbstractVideoEncoder(String str, IRecorder iRecorder, EncoderListener encoderListener) {
        super(str, iRecorder, encoderListener);
        this.mBitRate = -1;
        this.mFramerate = -1;
        this.mIFrameIntervals = -1;
    }

    @Override // com.serenegiant.media.IVideoEncoder
    public void setVideoSize(int i, int i2) throws IllegalArgumentException, IllegalStateException {
        this.mWidth = i;
        this.mHeight = i2;
        this.mBitRate = getConfig().getBitrate(i, i2);
    }

    @Override // com.serenegiant.media.IVideoEncoder
    public void setVideoConfig(int i, int i2, int i3) {
        this.mBitRate = i;
        this.mFramerate = i2;
        this.mIFrameIntervals = i3;
    }

    @Override // com.serenegiant.media.IVideoEncoder
    public int getWidth() {
        return this.mWidth;
    }

    @Override // com.serenegiant.media.IVideoEncoder
    public int getHeight() {
        return this.mHeight;
    }

    public void adjustBitrate(int i) {
        if (supportsAdaptiveStreaming && this.mMediaCodec != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("video-bitrate", i);
            this.mMediaCodec.setParameters(bundle);
        } else if (supportsAdaptiveStreaming) {
        } else {
            Log.w(TAG, "adjustBitrate: Ignoring adjustVideoBitrate call. This functionality is only available on Android API 19+");
        }
    }
}
