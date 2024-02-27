package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.media.IMuxer;
import com.serenegiant.media.IRecorder;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public abstract class Recorder implements IRecorder {
    public static final long CHECK_INTERVAL = 45000;
    private static final String TAG = "Recorder";
    protected Encoder mAudioEncoder;
    private volatile boolean mAudioStarted;
    private final IRecorder.RecorderCallback mCallback;
    private volatile int mEncoderCount;
    private EosHandler mEosHandler;
    protected IMuxer mMuxer;
    private final IMuxer.IMuxerFactory mMuxerFactory;
    private volatile boolean mReleased;
    protected long mStartTime;
    private volatile int mStartedCount;
    private int mState;
    private final VideoConfig mVideoConfig;
    protected Encoder mVideoEncoder;
    private volatile boolean mVideoStarted;
    private final WeakReference<Context> mWeakContext;

    protected abstract boolean check();

    public Recorder(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory) {
        this.mWeakContext = new WeakReference<>(context);
        this.mCallback = recorderCallback;
        this.mVideoConfig = videoConfig == null ? new VideoConfig() : videoConfig;
        this.mMuxerFactory = iMuxerFactory == null ? new IMuxer.DefaultFactory() : iMuxerFactory;
        synchronized (this) {
            this.mState = 0;
        }
    }

    @Override // com.serenegiant.media.IRecorder
    public VideoConfig getConfig() {
        return this.mVideoConfig;
    }

    @Override // com.serenegiant.media.IRecorder
    public void setMuxer(IMuxer iMuxer) {
        if (this.mReleased) {
            return;
        }
        this.mMuxer = iMuxer;
        this.mStartedCount = 0;
        this.mEncoderCount = 0;
        synchronized (this) {
            this.mState = 1;
        }
    }

    protected Context getContext() {
        return this.mWeakContext.get();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Context requireContext() throws IllegalStateException {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public IMuxer.IMuxerFactory getMuxerFactory() {
        return this.mMuxerFactory;
    }

    @Override // com.serenegiant.media.IRecorder
    public void prepare() {
        synchronized (this) {
            if (this.mReleased) {
                throw new IllegalStateException("already released");
            }
            if (this.mState != 1) {
                throw new IllegalStateException("prepare:state=" + this.mState);
            }
        }
        try {
            Encoder encoder = this.mVideoEncoder;
            if (encoder != null) {
                encoder.prepare();
            }
            Encoder encoder2 = this.mAudioEncoder;
            if (encoder2 != null) {
                encoder2.prepare();
            }
            synchronized (this) {
                this.mState = 2;
            }
            callOnPrepared();
        } catch (Exception e) {
            callOnError(e);
        }
    }

    @Override // com.serenegiant.media.IRecorder
    public void startRecording() throws IllegalStateException {
        synchronized (this) {
            if (this.mReleased) {
                throw new IllegalStateException("already released");
            }
            if (this.mState != 2) {
                throw new IllegalStateException("start:not prepared");
            }
            this.mState = 3;
        }
        this.mStartTime = System.currentTimeMillis();
        Encoder encoder = this.mVideoEncoder;
        if (encoder != null) {
            encoder.start();
        }
        Encoder encoder2 = this.mAudioEncoder;
        if (encoder2 != null) {
            encoder2.start();
        }
        if (this.mEosHandler == null) {
            this.mEosHandler = EosHandler.createHandler(this);
        }
        this.mEosHandler.startCheckFreeSpace();
    }

    @Override // com.serenegiant.media.IRecorder
    public void stopRecording() {
        EosHandler eosHandler = this.mEosHandler;
        if (eosHandler != null) {
            eosHandler.terminate();
            this.mEosHandler = null;
        }
        synchronized (this) {
            int i = this.mState;
            if (i != 0 && i != 1 && i != 5) {
                this.mState = 5;
                Encoder encoder = this.mAudioEncoder;
                if (encoder != null) {
                    encoder.stop();
                }
                Encoder encoder2 = this.mVideoEncoder;
                if (encoder2 != null) {
                    encoder2.stop();
                }
                callOnStopped();
            }
        }
    }

    @Override // com.serenegiant.media.IRecorder
    public Surface getInputSurface() {
        Encoder encoder = this.mVideoEncoder;
        if (encoder instanceof ISurfaceEncoder) {
            return ((ISurfaceEncoder) encoder).getInputSurface();
        }
        return null;
    }

    @Override // com.serenegiant.media.IRecorder
    public Encoder getVideoEncoder() {
        return this.mVideoEncoder;
    }

    @Override // com.serenegiant.media.IRecorder
    public Encoder getAudioEncoder() {
        return this.mAudioEncoder;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized boolean isStarted() {
        boolean z;
        if (!this.mReleased) {
            z = this.mState == 4;
        }
        return z;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized boolean isReady() {
        boolean z;
        if (!this.mReleased) {
            int i = this.mState;
            z = i == 4 || i == 2;
        }
        return z;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized boolean isStopping() {
        return this.mState == 5;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized boolean isStopped() {
        return this.mState <= 1;
    }

    public boolean isReleased() {
        return this.mReleased;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized int getState() {
        return this.mState;
    }

    @Override // com.serenegiant.media.IRecorder
    public IMuxer getMuxer() {
        return this.mMuxer;
    }

    @Override // com.serenegiant.media.IRecorder
    public void frameAvailableSoon() {
        Encoder encoder = this.mVideoEncoder;
        if (encoder != null) {
            encoder.frameAvailableSoon();
        }
    }

    @Override // com.serenegiant.media.IRecorder
    public void release() {
        if (!this.mReleased) {
            this.mReleased = true;
            Encoder encoder = this.mAudioEncoder;
            if (encoder != null) {
                encoder.release();
            }
            Encoder encoder2 = this.mVideoEncoder;
            if (encoder2 != null) {
                encoder2.release();
            }
            IMuxer iMuxer = this.mMuxer;
            if (iMuxer != null) {
                iMuxer.release();
            }
        }
        this.mAudioEncoder = null;
        this.mVideoEncoder = null;
        this.mMuxer = null;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized void addEncoder(Encoder encoder) {
        int i;
        synchronized (this) {
            if (this.mReleased) {
                throw new IllegalStateException("already released");
            }
            i = 1;
            if (this.mState > 1) {
                throw new IllegalStateException("addEncoder already prepared/started");
            }
        }
        if (encoder instanceof IAudioEncoder) {
            if (this.mAudioEncoder != null) {
                throw new IllegalArgumentException("Audio encoder already added.");
            }
            this.mAudioEncoder = encoder;
        }
        if (encoder instanceof IVideoEncoder) {
            if (this.mVideoEncoder != null) {
                throw new IllegalArgumentException("Video encoder already added.");
            }
            this.mVideoEncoder = encoder;
        }
        int i2 = this.mVideoEncoder != null ? 1 : 0;
        if (this.mAudioEncoder == null) {
            i = 0;
        }
        this.mEncoderCount = i2 + i;
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized void removeEncoder(Encoder encoder) {
        if (encoder instanceof IVideoEncoder) {
            this.mVideoEncoder = null;
            this.mVideoStarted = false;
        }
        if (encoder instanceof IAudioEncoder) {
            this.mAudioEncoder = null;
            this.mAudioStarted = false;
        }
        this.mEncoderCount = (this.mVideoEncoder != null ? 1 : 0) + (this.mAudioEncoder != null ? 1 : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0045 A[SYNTHETIC] */
    @Override // com.serenegiant.media.IRecorder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean start(com.serenegiant.media.Encoder r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.mReleased     // Catch: java.lang.Throwable -> L7e
            if (r0 != 0) goto L76
            int r0 = r6.mState     // Catch: java.lang.Throwable -> L7e
            r1 = 3
            if (r0 != r1) goto L6e
            com.serenegiant.media.Encoder r0 = r6.mVideoEncoder     // Catch: java.lang.Throwable -> L7e
            boolean r0 = r7.equals(r0)     // Catch: java.lang.Throwable -> L7e
            r2 = 1
            if (r0 == 0) goto L16
            r6.mVideoStarted = r2     // Catch: java.lang.Throwable -> L7e
            goto L20
        L16:
            com.serenegiant.media.Encoder r0 = r6.mAudioEncoder     // Catch: java.lang.Throwable -> L7e
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Throwable -> L7e
            if (r7 == 0) goto L20
            r6.mAudioStarted = r2     // Catch: java.lang.Throwable -> L7e
        L20:
            int r7 = r6.mStartedCount     // Catch: java.lang.Throwable -> L7e
            int r7 = r7 + r2
            r6.mStartedCount = r7     // Catch: java.lang.Throwable -> L7e
        L25:
            int r7 = r6.mState     // Catch: java.lang.Throwable -> L7e
            r0 = 0
            r3 = 4
            if (r7 != r1) goto L66
            int r7 = r6.mEncoderCount     // Catch: java.lang.Throwable -> L7e
            if (r7 <= 0) goto L66
            com.serenegiant.media.Encoder r7 = r6.mVideoEncoder     // Catch: java.lang.Throwable -> L7e
            if (r7 == 0) goto L37
            boolean r7 = r6.mVideoStarted     // Catch: java.lang.Throwable -> L7e
            if (r7 == 0) goto L40
        L37:
            com.serenegiant.media.Encoder r7 = r6.mAudioEncoder     // Catch: java.lang.Throwable -> L7e
            if (r7 == 0) goto L42
            boolean r7 = r6.mAudioStarted     // Catch: java.lang.Throwable -> L7e
            if (r7 == 0) goto L40
            goto L42
        L40:
            r7 = r0
            goto L43
        L42:
            r7 = r2
        L43:
            if (r7 == 0) goto L60
            com.serenegiant.media.IMuxer r7 = r6.mMuxer     // Catch: java.lang.Throwable -> L7e
            r7.start()     // Catch: java.lang.Throwable -> L7e
            r6.mState = r3     // Catch: java.lang.Throwable -> L7e
            r6.notifyAll()     // Catch: java.lang.Throwable -> L7e
            r6.callOnStarted()     // Catch: java.lang.Throwable -> L7e
            com.serenegiant.media.Recorder$EosHandler r7 = r6.mEosHandler     // Catch: java.lang.Throwable -> L7e
            if (r7 == 0) goto L66
            com.serenegiant.media.VideoConfig r1 = r6.mVideoConfig     // Catch: java.lang.Throwable -> L7e
            long r4 = r1.maxDuration()     // Catch: java.lang.Throwable -> L7e
            r7.setDuration(r4)     // Catch: java.lang.Throwable -> L7e
            goto L66
        L60:
            r4 = 100
            r6.wait(r4)     // Catch: java.lang.InterruptedException -> L66 java.lang.Throwable -> L7e
            goto L25
        L66:
            int r7 = r6.mState     // Catch: java.lang.Throwable -> L7e
            if (r7 != r3) goto L6b
            goto L6c
        L6b:
            r2 = r0
        L6c:
            monitor-exit(r6)
            return r2
        L6e:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L7e
            java.lang.String r0 = "muxer has not prepared:state="
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L7e
            throw r7     // Catch: java.lang.Throwable -> L7e
        L76:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L7e
            java.lang.String r0 = "already released"
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L7e
            throw r7     // Catch: java.lang.Throwable -> L7e
        L7e:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.Recorder.start(com.serenegiant.media.Encoder):boolean");
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized void stop(Encoder encoder) {
        if (encoder.equals(this.mVideoEncoder)) {
            if (this.mVideoStarted) {
                this.mVideoStarted = false;
                this.mStartedCount--;
            }
        } else if (encoder.equals(this.mAudioEncoder) && this.mAudioStarted) {
            this.mAudioStarted = false;
            this.mStartedCount--;
        }
        if (this.mEncoderCount > 0 && this.mStartedCount <= 0) {
            if (this.mState == 5) {
                this.mMuxer.stop();
            }
            this.mState = 1;
            this.mVideoEncoder = null;
            this.mAudioEncoder = null;
        }
    }

    @Override // com.serenegiant.media.IRecorder
    public synchronized int addTrack(Encoder encoder, MediaFormat mediaFormat) {
        int i;
        try {
        } catch (Exception e) {
            Log.w(TAG, "addTrack:", e);
            removeEncoder(encoder);
            i = -1;
        }
        if (this.mReleased) {
            throw new IllegalStateException("already released");
        }
        if (this.mState != 3) {
            throw new IllegalStateException("muxer not ready:state=" + this.mState);
        }
        i = this.mMuxer.addTrack(mediaFormat);
        return i;
    }

    @Override // com.serenegiant.media.IRecorder
    public void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        try {
            if (this.mReleased || this.mStartedCount <= 0) {
                return;
            }
            this.mMuxer.writeSampleData(i, byteBuffer, bufferInfo);
        } catch (Exception e) {
            callOnError(e);
        }
    }

    protected void callOnPrepared() {
        IRecorder.RecorderCallback recorderCallback = this.mCallback;
        if (recorderCallback != null) {
            try {
                recorderCallback.onPrepared(this);
            } catch (Exception e) {
                Log.e(TAG, "onPrepared:", e);
            }
        }
    }

    protected void callOnStarted() {
        IRecorder.RecorderCallback recorderCallback = this.mCallback;
        if (recorderCallback != null) {
            try {
                recorderCallback.onStarted(this);
            } catch (Exception e) {
                Log.e(TAG, "onStarted:", e);
            }
        }
    }

    protected void callOnStopped() {
        IRecorder.RecorderCallback recorderCallback = this.mCallback;
        if (recorderCallback != null) {
            try {
                recorderCallback.onStopped(this);
            } catch (Exception e) {
                Log.e(TAG, "onStopped:", e);
            }
        }
    }

    protected void callOnError(Exception exc) {
        IRecorder.RecorderCallback recorderCallback;
        if (this.mReleased || (recorderCallback = this.mCallback) == null) {
            return;
        }
        try {
            recorderCallback.onError(exc);
        } catch (Exception unused) {
            Log.e(TAG, "onError:", exc);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class EosHandler extends Handler {
        private static final int MSG_CHECK_FREESPACE = 5;
        private static final int MSG_SEND_EOS = 8;
        private static final int MSG_SEND_QUIT = 9;
        private final EosThread mThread;

        private EosHandler(EosThread eosThread) {
            this.mThread = eosThread;
        }

        public static final EosHandler createHandler(Recorder recorder) {
            EosThread eosThread = new EosThread(recorder);
            eosThread.start();
            return eosThread.getHandler();
        }

        public final void setDuration(long j) {
            removeMessages(8);
            if (j > 0) {
                sendEmptyMessageDelayed(8, j);
            }
        }

        public final void startCheckFreeSpace() {
            removeMessages(5);
            sendEmptyMessageDelayed(5, Recorder.CHECK_INTERVAL);
        }

        public final void terminate() {
            removeMessages(8);
            removeMessages(5);
            sendEmptyMessage(9);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Recorder recorder = (Recorder) this.mThread.mWeakRecorder.get();
            if (recorder == null) {
                try {
                    Looper.myLooper().quit();
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            int i = message.what;
            if (i == 5) {
                if (!this.mThread.check(recorder)) {
                    sendEmptyMessageDelayed(5, Recorder.CHECK_INTERVAL);
                } else {
                    recorder.stopRecording();
                }
            } else if (i == 8) {
                recorder.stopRecording();
            } else if (i == 9) {
                try {
                    Looper.myLooper().quit();
                } catch (Exception unused2) {
                }
            } else {
                super.handleMessage(message);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class EosThread extends Thread {
            private EosHandler mHandler;
            private boolean mIsReady;
            private final Object mSync;
            private final WeakReference<Recorder> mWeakRecorder;

            public EosThread(Recorder recorder) {
                super("EosThread");
                this.mSync = new Object();
                this.mIsReady = false;
                this.mWeakRecorder = new WeakReference<>(recorder);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final EosHandler getHandler() {
                synchronized (this.mSync) {
                    while (!this.mIsReady) {
                        try {
                            this.mSync.wait(300L);
                        } catch (InterruptedException unused) {
                        }
                    }
                }
                return this.mHandler;
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                Looper.prepare();
                synchronized (this.mSync) {
                    this.mHandler = new EosHandler(this);
                    this.mIsReady = true;
                    this.mSync.notify();
                }
                Looper.loop();
                synchronized (this.mSync) {
                    this.mIsReady = false;
                    this.mHandler = null;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean check(Recorder recorder) {
                return recorder.check();
            }
        }
    }
}
