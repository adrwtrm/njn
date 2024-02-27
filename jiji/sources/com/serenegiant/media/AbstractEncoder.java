package com.serenegiant.media;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import com.serenegiant.media.MediaReaper;
import com.serenegiant.system.Time;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public abstract class AbstractEncoder implements Encoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "AbstractEncoder";
    public static final int TIMEOUT_USEC = 10000;
    protected final String MIME_TYPE;
    protected volatile boolean mIsCapturing;
    private final EncoderListener mListener;
    protected MediaCodec mMediaCodec;
    protected MediaReaper mReaper;
    private IRecorder mRecorder;
    protected volatile boolean mRequestStop;
    protected int mTrackIndex;
    protected final Object mSync = new Object();
    private final MediaReaper.ReaperListener mReaperListener = new MediaReaper.ReaperListener() { // from class: com.serenegiant.media.AbstractEncoder.1
        @Override // com.serenegiant.media.MediaReaper.ReaperListener
        public void onError(MediaReaper mediaReaper, Throwable th) {
        }

        @Override // com.serenegiant.media.MediaReaper.ReaperListener
        public void writeSampleData(MediaReaper mediaReaper, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            if (!AbstractEncoder.this.mIsCapturing || AbstractEncoder.this.mRequestStop || AbstractEncoder.this.mRecorder == null) {
                return;
            }
            AbstractEncoder.this.mRecorder.writeSampleData(AbstractEncoder.this.mTrackIndex, byteBuffer, bufferInfo);
        }

        @Override // com.serenegiant.media.MediaReaper.ReaperListener
        public void onOutputFormatChanged(MediaReaper mediaReaper, MediaFormat mediaFormat) {
            if (!AbstractEncoder.this.mIsCapturing || AbstractEncoder.this.mRequestStop || AbstractEncoder.this.mRecorder == null) {
                return;
            }
            AbstractEncoder abstractEncoder = AbstractEncoder.this;
            abstractEncoder.startRecorder(abstractEncoder.mRecorder, mediaFormat);
        }

        @Override // com.serenegiant.media.MediaReaper.ReaperListener
        public void onStop(MediaReaper mediaReaper) {
            AbstractEncoder.this.mRequestStop = true;
        }
    };
    private long prevInputPTSUs = -1;

    public int getCaptureFormat() {
        return -1;
    }

    protected abstract boolean internalPrepare(MediaReaper.ReaperListener reaperListener) throws Exception;

    public AbstractEncoder(String str, IRecorder iRecorder, EncoderListener encoderListener) {
        if (encoderListener == null) {
            throw new NullPointerException("EncodeListener is null");
        }
        if (iRecorder == null) {
            throw new NullPointerException("recorder is null");
        }
        this.MIME_TYPE = str;
        this.mRecorder = iRecorder;
        this.mListener = encoderListener;
        iRecorder.addEncoder(this);
    }

    public IRecorder getRecorder() {
        return this.mRecorder;
    }

    public VideoConfig getConfig() {
        return this.mRecorder.getConfig();
    }

    @Override // com.serenegiant.media.Encoder
    @Deprecated
    public String getOutputPath() {
        IRecorder iRecorder = this.mRecorder;
        if (iRecorder != null) {
            return iRecorder.getOutputPath();
        }
        return null;
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    @Override // com.serenegiant.media.Encoder
    public final void prepare() throws Exception {
        try {
            this.mListener.onStartEncode(this, this instanceof ISurfaceEncoder ? ((ISurfaceEncoder) this).getInputSurface() : null, getCaptureFormat(), internalPrepare(this.mReaperListener));
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    protected void callOnError(Exception exc) {
        try {
            this.mListener.onError(exc);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    @Override // com.serenegiant.media.Encoder
    public void start() {
        synchronized (this.mSync) {
            this.mIsCapturing = true;
            this.mRequestStop = false;
        }
    }

    @Override // com.serenegiant.media.Encoder
    public void stop() {
        synchronized (this.mSync) {
            if (this.mRequestStop) {
                return;
            }
            MediaReaper mediaReaper = this.mReaper;
            if (mediaReaper != null) {
                mediaReaper.frameAvailableSoon();
            }
            this.mRequestStop = true;
            this.mSync.notifyAll();
        }
    }

    @Override // com.serenegiant.media.Encoder
    public void frameAvailableSoon() {
        synchronized (this.mSync) {
            if (this.mIsCapturing && !this.mRequestStop) {
                MediaReaper mediaReaper = this.mReaper;
                if (mediaReaper != null) {
                    mediaReaper.frameAvailableSoon();
                }
                this.mSync.notifyAll();
            }
        }
    }

    @Override // com.serenegiant.media.Encoder, com.serenegiant.glpipeline.GLPipeline
    public void release() {
        this.mRecorder = null;
        if (this.mIsCapturing) {
            try {
                this.mListener.onStopEncode(this);
            } catch (Exception unused) {
            }
        }
        this.mIsCapturing = false;
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            try {
                mediaCodec.stop();
                this.mMediaCodec.release();
                this.mMediaCodec = null;
            } catch (Exception unused2) {
            }
        }
        MediaReaper mediaReaper = this.mReaper;
        if (mediaReaper != null) {
            mediaReaper.release();
            this.mReaper = null;
        }
        IRecorder iRecorder = this.mRecorder;
        if (iRecorder != null) {
            try {
                iRecorder.stop(this);
            } catch (Exception unused3) {
            }
        }
        try {
            this.mListener.onDestroy(this);
        } catch (Exception unused4) {
        }
        this.mRecorder = null;
    }

    @Override // com.serenegiant.media.Encoder
    public void signalEndOfInputStream() {
        encode(null, 0, getInputPTSUs());
    }

    @Override // com.serenegiant.media.Encoder
    public boolean isCapturing() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mIsCapturing;
        }
        return z;
    }

    @Override // com.serenegiant.media.Encoder
    public void encode(ByteBuffer byteBuffer, int i, long j) {
        synchronized (this.mSync) {
            if (this.mIsCapturing && !this.mRequestStop) {
                MediaCodec mediaCodec = this.mMediaCodec;
                if (mediaCodec == null) {
                    return;
                }
                ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
                while (this.mIsCapturing) {
                    int dequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(10000L);
                    if (dequeueInputBuffer >= 0) {
                        ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
                        byteBuffer2.clear();
                        if (byteBuffer != null && i > 0) {
                            byteBuffer.clear();
                            byteBuffer.position(i);
                            byteBuffer.flip();
                            byteBuffer2.put(byteBuffer);
                        }
                        if (i <= 0) {
                            this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, 0, j, 4);
                            return;
                        } else {
                            this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, i, j, 0);
                            return;
                        }
                    } else if (dequeueInputBuffer == -1) {
                        frameAvailableSoon();
                    }
                }
            }
        }
    }

    protected boolean startRecorder(IRecorder iRecorder, MediaFormat mediaFormat) {
        if (iRecorder.getState() != 3) {
            for (int i = 0; i < 10 && iRecorder.getState() != 3; i++) {
                synchronized (this.mSync) {
                    try {
                        try {
                            this.mSync.wait(10L);
                        } catch (InterruptedException unused) {
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        if (iRecorder.getState() == 3) {
            int addTrack = iRecorder.addTrack(this, mediaFormat);
            this.mTrackIndex = addTrack;
            if (addTrack >= 0) {
                if (!iRecorder.start(this)) {
                    Log.w(TAG, "startRecorder: failed to start recorder mTrackIndex=" + this.mTrackIndex);
                }
            } else {
                iRecorder.removeEncoder(this);
            }
        }
        return iRecorder.isStarted();
    }

    protected void stopRecorder(IRecorder iRecorder) {
        this.mIsCapturing = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public long getInputPTSUs() {
        long nanoTime = Time.nanoTime() / 1000;
        long j = this.prevInputPTSUs;
        if (nanoTime <= j) {
            nanoTime = 9643 + j;
        }
        this.prevInputPTSUs = nanoTime;
        return nanoTime;
    }
}
