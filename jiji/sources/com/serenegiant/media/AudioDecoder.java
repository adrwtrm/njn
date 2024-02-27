package com.serenegiant.media;

import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.media.AbstractDecoder;
import com.serenegiant.system.BuildCheck;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public abstract class AudioDecoder extends AbstractDecoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "AudioDecoder";
    protected int mAudioInputBufSize;
    protected AudioTrack mAudioTrack;
    private boolean mHasAudio;

    public static AudioDecoder createDecoder(DecoderListener decoderListener) {
        if (BuildCheck.isAPI21()) {
            return new AudioDecoderAPI21(decoderListener);
        }
        return new AudioDecoderAPI16(decoderListener);
    }

    private AudioDecoder(DecoderListener decoderListener) {
        super("audio/", decoderListener);
    }

    @Override // com.serenegiant.media.AbstractDecoder
    protected void internalPrepare(int i, MediaFormat mediaFormat) {
        int integer = mediaFormat.getInteger("channel-count");
        int integer2 = mediaFormat.getInteger("sample-rate");
        int minBufferSize = AudioTrack.getMinBufferSize(integer2, integer == 1 ? 4 : 12, 2);
        int integer3 = mediaFormat.getInteger("max-input-size");
        int i2 = minBufferSize > 0 ? minBufferSize * 4 : integer3;
        this.mAudioInputBufSize = i2;
        if (i2 > integer3) {
            this.mAudioInputBufSize = integer3;
        }
        int i3 = integer * 2;
        this.mAudioInputBufSize = (this.mAudioInputBufSize / i3) * i3;
        AudioTrack audioTrack = new AudioTrack(3, integer2, integer == 1 ? 4 : 12, 2, this.mAudioInputBufSize, 1);
        this.mAudioTrack = audioTrack;
        try {
            audioTrack.play();
        } catch (Exception e) {
            Log.e(TAG, "failed to start audio track playing", e);
            this.mAudioTrack.release();
            this.mAudioTrack = null;
        }
    }

    @Override // com.serenegiant.media.AbstractDecoder
    protected MediaCodec createDecoder(int i, MediaFormat mediaFormat) throws IOException {
        MediaCodec mediaCodec = null;
        if (i < 0) {
            return null;
        }
        try {
            MediaCodec createDecoderByType = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
            try {
                createDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
                createDecoderByType.start();
                return createDecoderByType;
            } catch (IOException e) {
                e = e;
                mediaCodec = createDecoderByType;
                Log.w(TAG, e);
                return mediaCodec;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    /* loaded from: classes2.dex */
    private static class AudioDecoderAPI16 extends AudioDecoder {
        private static final String TAG = "AudioDecoderAPI16";
        protected byte[] mAudioOutTempBuf;
        private ByteBuffer[] mInputBuffers;
        private ByteBuffer[] mOutputBuffers;

        private AudioDecoderAPI16(DecoderListener decoderListener) {
            super(decoderListener);
        }

        @Override // com.serenegiant.media.AudioDecoder, com.serenegiant.media.AbstractDecoder
        protected MediaCodec createDecoder(int i, MediaFormat mediaFormat) throws IOException {
            MediaCodec createDecoder = super.createDecoder(i, mediaFormat);
            this.mInputBuffers = createDecoder.getInputBuffers();
            ByteBuffer[] outputBuffers = createDecoder.getOutputBuffers();
            this.mOutputBuffers = outputBuffers;
            int capacity = outputBuffers[0].capacity();
            if (capacity <= 0) {
                capacity = this.mAudioInputBufSize;
            }
            this.mAudioOutTempBuf = new byte[capacity];
            return createDecoder;
        }

        @Override // com.serenegiant.media.AbstractDecoder
        public void decode(MediaExtractor mediaExtractor) {
            if (this.mDecoder != null) {
                decodeAPI16(mediaExtractor, this.mDecoder, this.mInputBuffers);
            }
        }

        @Override // com.serenegiant.media.AbstractDecoder
        protected AbstractDecoder.OutputTask createOutputTask(int i) {
            return new AbstractDecoder.OutputTask(i) { // from class: com.serenegiant.media.AudioDecoder.AudioDecoderAPI16.1
                @Override // com.serenegiant.media.AbstractDecoder.OutputTask
                protected void handleOutput(MediaCodec mediaCodec) {
                    int dequeueOutputBuffer;
                    while (AudioDecoderAPI16.this.isRunning() && !AudioDecoderAPI16.this.mOutputDone && (dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(AudioDecoderAPI16.this.mBufferInfo, 10000L)) != -1) {
                        if (dequeueOutputBuffer == -3) {
                            AudioDecoderAPI16.this.mOutputBuffers = mediaCodec.getOutputBuffers();
                        } else if (dequeueOutputBuffer == -2) {
                            continue;
                        } else if (dequeueOutputBuffer < 0) {
                            throw new RuntimeException("unexpected result from audio decoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
                        } else {
                            int i2 = AudioDecoderAPI16.this.mBufferInfo.size;
                            if (i2 > 0) {
                                ByteBuffer byteBuffer = AudioDecoderAPI16.this.mOutputBuffers[dequeueOutputBuffer];
                                if (AudioDecoderAPI16.this.mAudioOutTempBuf == null || AudioDecoderAPI16.this.mAudioOutTempBuf.length < i2) {
                                    AudioDecoderAPI16.this.mAudioOutTempBuf = new byte[(i2 * 3) / 2];
                                }
                                byteBuffer.position(0);
                                byteBuffer.get(AudioDecoderAPI16.this.mAudioOutTempBuf, 0, i2);
                                byteBuffer.clear();
                                if (AudioDecoderAPI16.this.mAudioTrack != null) {
                                    AudioDecoderAPI16.this.mAudioTrack.write(AudioDecoderAPI16.this.mAudioOutTempBuf, 0, i2);
                                }
                                AudioDecoderAPI16 audioDecoderAPI16 = AudioDecoderAPI16.this;
                                if (!audioDecoderAPI16.onFrameAvailable(audioDecoderAPI16.mBufferInfo.presentationTimeUs)) {
                                    adjustPresentationTime(AudioDecoderAPI16.this.mBufferInfo.presentationTimeUs);
                                }
                            }
                            mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                            if ((AudioDecoderAPI16.this.mBufferInfo.flags & 4) != 0) {
                                synchronized (AudioDecoderAPI16.this.mSync) {
                                    AudioDecoderAPI16.this.mOutputDone = true;
                                    AudioDecoderAPI16.this.mSync.notifyAll();
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            };
        }
    }

    /* loaded from: classes2.dex */
    private static class AudioDecoderAPI21 extends AudioDecoder {
        private static final String TAG = "AudioDecoderAPI21";

        private AudioDecoderAPI21(DecoderListener decoderListener) {
            super(decoderListener);
        }

        @Override // com.serenegiant.media.AbstractDecoder
        public void decode(MediaExtractor mediaExtractor) {
            if (this.mDecoder != null) {
                decodeAPI21(mediaExtractor, this.mDecoder);
            }
        }

        @Override // com.serenegiant.media.AbstractDecoder
        protected AbstractDecoder.OutputTask createOutputTask(int i) {
            return new AbstractDecoder.OutputTask(i) { // from class: com.serenegiant.media.AudioDecoder.AudioDecoderAPI21.1
                @Override // com.serenegiant.media.AbstractDecoder.OutputTask
                protected void handleOutput(MediaCodec mediaCodec) {
                    int dequeueOutputBuffer;
                    while (AudioDecoderAPI21.this.isRunning() && !AudioDecoderAPI21.this.mOutputDone && (dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(AudioDecoderAPI21.this.mBufferInfo, 10000L)) != -1) {
                        if (dequeueOutputBuffer != -3 && dequeueOutputBuffer != -2) {
                            if (dequeueOutputBuffer < 0) {
                                throw new RuntimeException("unexpected result from audio decoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
                            }
                            int i2 = AudioDecoderAPI21.this.mBufferInfo.size;
                            if (i2 > 0) {
                                ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(dequeueOutputBuffer);
                                outputBuffer.clear();
                                if (AudioDecoderAPI21.this.mAudioTrack != null) {
                                    AudioDecoderAPI21.this.mAudioTrack.write(outputBuffer, i2, 0);
                                }
                                AudioDecoderAPI21 audioDecoderAPI21 = AudioDecoderAPI21.this;
                                if (!audioDecoderAPI21.onFrameAvailable(audioDecoderAPI21.mBufferInfo.presentationTimeUs)) {
                                    adjustPresentationTime(AudioDecoderAPI21.this.mBufferInfo.presentationTimeUs);
                                }
                            }
                            mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                            if ((AudioDecoderAPI21.this.mBufferInfo.flags & 4) != 0) {
                                synchronized (AudioDecoderAPI21.this.mSync) {
                                    AudioDecoderAPI21.this.mOutputDone = true;
                                    AudioDecoderAPI21.this.mSync.notifyAll();
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            };
        }
    }
}
