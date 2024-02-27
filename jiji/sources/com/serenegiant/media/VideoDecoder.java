package com.serenegiant.media;

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
public abstract class VideoDecoder extends AbstractDecoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "VideoDecoder";
    private final Surface mOutputSurface;

    @Override // com.serenegiant.media.AbstractDecoder
    protected void internalPrepare(int i, MediaFormat mediaFormat) {
    }

    public static VideoDecoder createDecoder(Surface surface, DecoderListener decoderListener) {
        if (BuildCheck.isAPI21()) {
            return new VideoDecoderAPI21(surface, decoderListener);
        }
        return new VideoDecoderAPI16(surface, decoderListener);
    }

    private VideoDecoder(Surface surface, DecoderListener decoderListener) {
        super("video/", decoderListener);
        this.mOutputSurface = surface;
    }

    @Override // com.serenegiant.media.AbstractDecoder
    protected MediaCodec createDecoder(int i, MediaFormat mediaFormat) throws IOException {
        MediaCodec createDecoderByType;
        MediaCodec mediaCodec = null;
        if (i >= 0) {
            try {
                createDecoderByType = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
            } catch (IOException e) {
                e = e;
            }
            try {
                createDecoderByType.configure(mediaFormat, this.mOutputSurface, (MediaCrypto) null, 0);
                createDecoderByType.start();
                return createDecoderByType;
            } catch (IOException e2) {
                e = e2;
                mediaCodec = createDecoderByType;
                Log.w(TAG, e);
                return mediaCodec;
            }
        }
        return null;
    }

    /* loaded from: classes2.dex */
    private static class VideoDecoderAPI16 extends VideoDecoder {
        private static final String TAG = "VideoDecoderAPI16";
        private ByteBuffer[] mInputBuffers;

        private VideoDecoderAPI16(Surface surface, DecoderListener decoderListener) {
            super(surface, decoderListener);
        }

        @Override // com.serenegiant.media.VideoDecoder, com.serenegiant.media.AbstractDecoder
        protected MediaCodec createDecoder(int i, MediaFormat mediaFormat) throws IOException {
            MediaCodec createDecoder = super.createDecoder(i, mediaFormat);
            this.mInputBuffers = createDecoder.getInputBuffers();
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
            return new AbstractDecoder.OutputTask(i) { // from class: com.serenegiant.media.VideoDecoder.VideoDecoderAPI16.1
                @Override // com.serenegiant.media.AbstractDecoder.OutputTask
                protected void handleOutput(MediaCodec mediaCodec) {
                    int dequeueOutputBuffer;
                    while (VideoDecoderAPI16.this.isRunning() && !VideoDecoderAPI16.this.mOutputDone && (dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(VideoDecoderAPI16.this.mBufferInfo, 10000L)) != -1) {
                        if (dequeueOutputBuffer != -3 && dequeueOutputBuffer != -2) {
                            if (dequeueOutputBuffer < 0) {
                                throw new RuntimeException("unexpected result from video decoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
                            }
                            boolean z = VideoDecoderAPI16.this.mBufferInfo.size > 0;
                            if (z) {
                                VideoDecoderAPI16 videoDecoderAPI16 = VideoDecoderAPI16.this;
                                if (!videoDecoderAPI16.onFrameAvailable(videoDecoderAPI16.mBufferInfo.presentationTimeUs)) {
                                    adjustPresentationTime(VideoDecoderAPI16.this.mBufferInfo.presentationTimeUs);
                                }
                            }
                            mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, z);
                            if ((VideoDecoderAPI16.this.mBufferInfo.flags & 4) != 0) {
                                synchronized (VideoDecoderAPI16.this.mSync) {
                                    VideoDecoderAPI16.this.mOutputDone = true;
                                    VideoDecoderAPI16.this.mSync.notifyAll();
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
    private static class VideoDecoderAPI21 extends VideoDecoder {
        private static final String TAG = "VideoDecoderAPI21";

        private VideoDecoderAPI21(Surface surface, DecoderListener decoderListener) {
            super(surface, decoderListener);
        }

        @Override // com.serenegiant.media.AbstractDecoder
        public void decode(MediaExtractor mediaExtractor) {
            if (this.mDecoder != null) {
                decodeAPI21(mediaExtractor, this.mDecoder);
            }
        }

        @Override // com.serenegiant.media.AbstractDecoder
        protected AbstractDecoder.OutputTask createOutputTask(int i) {
            return new AbstractDecoder.OutputTask(i) { // from class: com.serenegiant.media.VideoDecoder.VideoDecoderAPI21.1
                @Override // com.serenegiant.media.AbstractDecoder.OutputTask
                protected void handleOutput(MediaCodec mediaCodec) {
                    int dequeueOutputBuffer;
                    while (VideoDecoderAPI21.this.isRunning() && !VideoDecoderAPI21.this.mOutputDone && (dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(VideoDecoderAPI21.this.mBufferInfo, 10000L)) != -1) {
                        if (dequeueOutputBuffer != -3 && dequeueOutputBuffer != -2) {
                            if (dequeueOutputBuffer < 0) {
                                throw new RuntimeException("unexpected result from video decoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
                            }
                            mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, adjustPresentationTime(VideoDecoderAPI21.this.mBufferInfo.presentationTimeUs));
                            if ((VideoDecoderAPI21.this.mBufferInfo.flags & 4) != 0) {
                                synchronized (VideoDecoderAPI21.this.mSync) {
                                    VideoDecoderAPI21.this.mOutputDone = true;
                                    VideoDecoderAPI21.this.mSync.notifyAll();
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
