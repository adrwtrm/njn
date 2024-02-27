package com.serenegiant.media;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public abstract class AbstractDecoder implements Decoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "AbstractDecoder";
    public static final int TIMEOUT_USEC = 10000;
    protected MediaCodec mDecoder;
    private MediaFormat mFormat;
    private volatile boolean mIsRunning;
    private final String mMimeType;
    protected boolean mOutputDone;
    private long mStartTimeNs;
    private volatile int mTrackIndex;
    protected final Object mSync = new Object();
    protected final MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();

    protected abstract MediaCodec createDecoder(int i, MediaFormat mediaFormat) throws IOException;

    protected abstract OutputTask createOutputTask(int i);

    public abstract void decode(MediaExtractor mediaExtractor);

    protected abstract void internalPrepare(int i, MediaFormat mediaFormat);

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean onFrameAvailable(long j) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractDecoder(String str, DecoderListener decoderListener) {
        this.mMimeType = str;
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    @Override // com.serenegiant.media.Decoder
    public void release() {
        stop();
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    @Override // com.serenegiant.media.Decoder
    public int prepare(MediaExtractor mediaExtractor) throws IllegalArgumentException {
        this.mTrackIndex = findTrack(mediaExtractor, this.mMimeType);
        if (this.mTrackIndex >= 0) {
            mediaExtractor.selectTrack(this.mTrackIndex);
            this.mFormat = mediaExtractor.getTrackFormat(this.mTrackIndex);
            internalPrepare(this.mTrackIndex, this.mFormat);
            return this.mTrackIndex;
        }
        throw new IllegalArgumentException("Track not found for " + this.mMimeType);
    }

    @Override // com.serenegiant.media.Decoder
    public void start() {
        if (this.mTrackIndex >= 0) {
            this.mFormat.getString("mime");
            try {
                this.mDecoder = createDecoder(this.mTrackIndex, this.mFormat);
                this.mOutputDone = false;
                this.mIsRunning = true;
                Thread thread = new Thread(createOutputTask(this.mTrackIndex), TAG + "-" + hashCode());
                synchronized (this.mSync) {
                    thread.start();
                    try {
                        this.mSync.wait(1000L);
                    } catch (InterruptedException unused) {
                    }
                }
            } catch (IOException e) {
                Log.w(TAG, e);
            }
        }
    }

    @Override // com.serenegiant.media.Decoder
    public void stop() {
        this.mIsRunning = false;
        synchronized (this.mSync) {
            this.mSync.notifyAll();
        }
    }

    public void signalEndOfStream() {
        while (true) {
            if (!isRunning()) {
                break;
            }
            int dequeueInputBuffer = this.mDecoder.dequeueInputBuffer(10000L);
            if (dequeueInputBuffer >= 0) {
                this.mDecoder.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                break;
            }
        }
        synchronized (this.mSync) {
            this.mSync.notifyAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void decodeAPI16(MediaExtractor mediaExtractor, MediaCodec mediaCodec, ByteBuffer[] byteBufferArr) {
        while (isRunning()) {
            int dequeueInputBuffer = mediaCodec.dequeueInputBuffer(10000L);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = mediaExtractor.readSampleData(byteBufferArr[dequeueInputBuffer], 0);
                if (readSampleData > 0) {
                    mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                    return;
                } else {
                    this.mDecoder.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void decodeAPI21(MediaExtractor mediaExtractor, MediaCodec mediaCodec) {
        while (isRunning()) {
            int dequeueInputBuffer = mediaCodec.dequeueInputBuffer(10000L);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = mediaExtractor.readSampleData(mediaCodec.getInputBuffer(dequeueInputBuffer), 0);
                if (readSampleData > 0) {
                    mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                    return;
                } else {
                    this.mDecoder.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                    return;
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    protected abstract class OutputTask implements Runnable {
        private static final long VSYNC2 = 33330000;
        private long mOffsetPtsNs = -1;
        private long mOffsetSysTimeNs = -1;
        protected final int trackIndex;

        protected abstract void handleOutput(MediaCodec mediaCodec);

        /* JADX INFO: Access modifiers changed from: protected */
        public OutputTask(int i) {
            this.trackIndex = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (AbstractDecoder.this.mSync) {
                AbstractDecoder.this.mSync.notify();
            }
            while (AbstractDecoder.this.mIsRunning && !AbstractDecoder.this.mOutputDone) {
                try {
                    if (!AbstractDecoder.this.mOutputDone) {
                        handleOutput(AbstractDecoder.this.mDecoder);
                    }
                } catch (Exception e) {
                    Log.e(AbstractDecoder.TAG, "DecodeTask:", e);
                }
            }
            synchronized (AbstractDecoder.this.mSync) {
                AbstractDecoder.this.mOutputDone = true;
                AbstractDecoder.this.mSync.notifyAll();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public long adjustPresentationTime(long j) {
            long j2 = j * 1000;
            if (this.mOffsetSysTimeNs <= 0) {
                long nanoTime = System.nanoTime();
                this.mOffsetSysTimeNs = nanoTime;
                this.mOffsetPtsNs = nanoTime - j2;
                return nanoTime + VSYNC2;
            }
            long j3 = (this.mOffsetPtsNs + j2) - VSYNC2;
            long nanoTime2 = System.nanoTime();
            while (true) {
                long j4 = j3 - nanoTime2;
                if (!AbstractDecoder.this.isRunning() || j4 <= 0) {
                    break;
                }
                if (j4 > 20000000) {
                    j4 >>= 1;
                }
                synchronized (AbstractDecoder.this.mSync) {
                    try {
                        AbstractDecoder.this.mSync.wait(j4 / 1000000, (int) (j4 % 1000000));
                    } catch (InterruptedException unused) {
                    }
                }
                nanoTime2 = System.nanoTime();
            }
            return System.nanoTime() + VSYNC2;
        }
    }

    private static final int findTrack(MediaExtractor mediaExtractor, String str) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith(str)) {
                return i;
            }
        }
        return -1;
    }
}
