package com.serenegiant.media;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.serenegiant.media.MediaReaper;

/* loaded from: classes2.dex */
public abstract class AbstractAudioEncoder extends AbstractEncoder implements IAudioEncoder {
    public static final int DEFAULT_BIT_RATE = 64000;
    public static final int DEFAULT_SAMPLE_RATE = 44100;
    public static final int FRAMES_PER_BUFFER = 25;
    public static final int SAMPLES_PER_FRAME = 1024;
    protected int mAudioSource;
    protected int mBitRate;
    protected int mChannelCount;
    protected int mSampleRate;

    @Override // com.serenegiant.media.Encoder
    public final boolean isAudio() {
        return true;
    }

    @Deprecated
    public AbstractAudioEncoder(IRecorder iRecorder, EncoderListener encoderListener, int i, int i2) {
        this(iRecorder, encoderListener, i, i2, DEFAULT_SAMPLE_RATE, DEFAULT_BIT_RATE);
    }

    public AbstractAudioEncoder(IRecorder iRecorder, EncoderListener encoderListener, int i, int i2, int i3, int i4) {
        super(MediaCodecUtils.MIME_AUDIO_AAC, iRecorder, encoderListener);
        this.mAudioSource = i;
        this.mChannelCount = i2;
        this.mSampleRate = i3;
        this.mBitRate = i4;
    }

    @Override // com.serenegiant.media.AbstractEncoder
    protected boolean internalPrepare(MediaReaper.ReaperListener reaperListener) throws Exception {
        this.mTrackIndex = -1;
        if (MediaCodecUtils.selectAudioEncoder(this.MIME_TYPE) == null) {
            return true;
        }
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(this.MIME_TYPE, this.mSampleRate, this.mChannelCount);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("channel-mask", this.mChannelCount == 1 ? 16 : 12);
        createAudioFormat.setInteger("bitrate", this.mBitRate);
        createAudioFormat.setInteger("channel-count", this.mChannelCount);
        this.mMediaCodec = MediaCodec.createEncoderByType(this.MIME_TYPE);
        this.mMediaCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mMediaCodec.start();
        this.mReaper = new MediaReaper.AudioReaper(this.mMediaCodec, reaperListener, this.mSampleRate, this.mChannelCount);
        return false;
    }
}
