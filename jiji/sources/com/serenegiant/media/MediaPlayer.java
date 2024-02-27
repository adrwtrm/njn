package com.serenegiant.media;

import android.content.res.AssetFileDescriptor;
import android.media.MediaExtractor;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.system.BuildCheck;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MediaPlayer {
    private static final boolean DEBUG = false;
    private static final int REQ_NON = 0;
    private static final int REQ_PAUSE = 5;
    private static final int REQ_PREPARE = 1;
    private static final int REQ_QUIT = 9;
    private static final int REQ_RESUME = 6;
    private static final int REQ_SEEK = 3;
    private static final int REQ_START = 2;
    private static final int REQ_STOP = 4;
    private static final int STATE_PAUSED = 3;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_STOP = 0;
    private static final String TAG = "MediaPlayer";
    private static final int TIMEOUT_USEC = 10000;
    private AudioDecoder mAudioDecoder;
    private final boolean mAudioEnabled;
    private int mAudioTrackIndex;
    private int mBitrate;
    private final IFrameCallback mCallback;
    private long mDuration;
    private final MediaExtractor mExtractor;
    private float mFrameRate;
    private volatile boolean mIsRunning;
    private final DecoderListener mListener;
    private volatile boolean mLoopEnabled;
    private final MediaMetadataRetriever mMetadata;
    private final Runnable mMoviePlayerTask;
    private final Surface mOutputSurface;
    private int mRequest;
    private long mRequestTime;
    private int mRotation;
    private Object mSource;
    private int mState;
    private final Object mSync;
    private VideoDecoder mVideoDecoder;
    private int mVideoHeight;
    private int mVideoTrackIndex;
    private int mVideoWidth;

    private void handlePause() {
    }

    private void handleResume() {
    }

    public MediaPlayer(Surface surface, IFrameCallback iFrameCallback, boolean z) throws NullPointerException, IllegalArgumentException {
        Object obj = new Object();
        this.mSync = obj;
        this.mMetadata = new MediaMetadataRetriever();
        Runnable runnable = new Runnable() { // from class: com.serenegiant.media.MediaPlayer.1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z2;
                int i;
                boolean processStop;
                try {
                    synchronized (MediaPlayer.this.mSync) {
                        z2 = MediaPlayer.this.mIsRunning = true;
                        MediaPlayer.this.mState = 0;
                        MediaPlayer.this.mRequest = 0;
                        MediaPlayer.this.mRequestTime = -1L;
                        MediaPlayer.this.mSync.notifyAll();
                    }
                    while (z2) {
                        try {
                            synchronized (MediaPlayer.this.mSync) {
                                z2 = MediaPlayer.this.mIsRunning;
                                i = MediaPlayer.this.mRequest;
                                MediaPlayer.this.mRequest = 0;
                            }
                            int i2 = MediaPlayer.this.mState;
                            if (i2 == 0) {
                                processStop = MediaPlayer.this.processStop(i);
                            } else if (i2 == 1) {
                                processStop = MediaPlayer.this.processPrepared(i);
                            } else if (i2 == 2) {
                                processStop = MediaPlayer.this.processPlaying(i);
                            } else if (i2 == 3) {
                                processStop = MediaPlayer.this.processPaused(i);
                            }
                            z2 = processStop;
                        } catch (InterruptedException unused) {
                        } catch (Exception e) {
                            Log.e(MediaPlayer.TAG, "MoviePlayerTask:", e);
                        }
                    }
                } finally {
                    MediaPlayer.this.handleStop();
                }
            }
        };
        this.mMoviePlayerTask = runnable;
        this.mListener = new DecoderListener() { // from class: com.serenegiant.media.MediaPlayer.2
            @Override // com.serenegiant.media.DecoderListener
            public void onDestroy(Decoder decoder) {
            }

            @Override // com.serenegiant.media.DecoderListener
            public void onError(Throwable th) {
            }

            @Override // com.serenegiant.media.DecoderListener
            public void onStartDecode(Decoder decoder) {
            }

            @Override // com.serenegiant.media.DecoderListener
            public void onStopDecode(Decoder decoder) {
            }
        };
        if (iFrameCallback == null) {
            throw new NullPointerException("callback should not be null");
        }
        if (surface == null && !z) {
            throw new IllegalArgumentException("Should playback at least either video or audio.");
        }
        this.mOutputSurface = surface;
        this.mCallback = iFrameCallback;
        this.mAudioEnabled = z;
        this.mExtractor = new MediaExtractor();
        new Thread(runnable, TAG).start();
        synchronized (obj) {
            try {
                if (!this.mIsRunning) {
                    obj.wait();
                }
            } catch (InterruptedException unused) {
            }
        }
    }

    public void setLoop(boolean z) {
        synchronized (this.mSync) {
            this.mLoopEnabled = z;
        }
    }

    public final int getWidth() {
        return this.mVideoWidth;
    }

    public final int getHeight() {
        return this.mVideoHeight;
    }

    public final int getBitRate() {
        return this.mBitrate;
    }

    public final float getFramerate() {
        return this.mFrameRate;
    }

    public final int getRotation() {
        return this.mRotation;
    }

    public final long getDurationUs() {
        return this.mDuration;
    }

    public boolean hasAudio() {
        return this.mAudioEnabled && this.mAudioDecoder != null;
    }

    public boolean isPlaying() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mState == 2;
        }
        return z;
    }

    public boolean isPaused() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mState == 3;
        }
        return z;
    }

    public void prepare(String str) {
        synchronized (this.mSync) {
            this.mSource = str;
            this.mRequest = 1;
            this.mSync.notifyAll();
        }
    }

    public void prepare(AssetFileDescriptor assetFileDescriptor) {
        synchronized (this.mSync) {
            this.mSource = assetFileDescriptor;
            this.mRequest = 1;
            this.mSync.notifyAll();
        }
    }

    public void play() {
        synchronized (this.mSync) {
            if (this.mState == 2) {
                return;
            }
            this.mRequest = 2;
            this.mSync.notifyAll();
        }
    }

    public void seek(long j) {
        synchronized (this.mSync) {
            this.mRequest = 3;
            this.mRequestTime = j;
            this.mSync.notifyAll();
        }
    }

    public void stop() {
        synchronized (this.mSync) {
            if (this.mState != 0) {
                this.mRequest = 4;
                this.mSync.notifyAll();
                try {
                    this.mSync.wait(50L);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public void pause() {
        synchronized (this.mSync) {
            if (this.mState == 2) {
                this.mRequest = 5;
                this.mSync.notifyAll();
            }
        }
    }

    public void resume() {
        synchronized (this.mSync) {
            if (this.mState == 3) {
                this.mRequest = 6;
                this.mSync.notifyAll();
            }
        }
    }

    public void release() {
        stop();
        synchronized (this.mSync) {
            this.mRequest = 9;
            this.mSync.notifyAll();
        }
    }

    public String extractMetadata(int i) {
        return this.mMetadata.extractMetadata(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processStop(int i) throws InterruptedException, IOException {
        boolean z;
        boolean z2 = true;
        if (i == 1) {
            handlePrepare(this.mSource);
        } else if (i == 2 || i == 5 || i == 6) {
            throw new IllegalStateException("invalid state:" + this.mState);
        } else {
            if (i != 9) {
                synchronized (this.mSync) {
                    this.mSync.wait();
                }
            } else {
                z2 = false;
            }
        }
        synchronized (this.mSync) {
            z = z2 & this.mIsRunning;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean processPrepared(int r3) throws java.lang.InterruptedException {
        /*
            r2 = this;
            r0 = 2
            if (r3 == r0) goto L3a
            r0 = 9
            if (r3 == r0) goto L38
            r0 = 4
            if (r3 == r0) goto L34
            r0 = 5
            if (r3 == r0) goto L1d
            r0 = 6
            if (r3 == r0) goto L1d
            java.lang.Object r3 = r2.mSync
            monitor-enter(r3)
            java.lang.Object r0 = r2.mSync     // Catch: java.lang.Throwable -> L1a
            r0.wait()     // Catch: java.lang.Throwable -> L1a
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1a
            goto L3d
        L1a:
            r0 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1a
            throw r0
        L1d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "invalid state:"
            r0.<init>(r1)
            int r1 = r2.mState
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r3.<init>(r0)
            throw r3
        L34:
            r2.handleStop()
            goto L3d
        L38:
            r3 = 0
            goto L3e
        L3a:
            r2.handleStart()
        L3d:
            r3 = 1
        L3e:
            java.lang.Object r0 = r2.mSync
            monitor-enter(r0)
            boolean r1 = r2.mIsRunning     // Catch: java.lang.Throwable -> L46
            r3 = r3 & r1
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L46
            return r3
        L46:
            r3 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L46
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.MediaPlayer.processPrepared(int):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processPlaying(int i) {
        boolean z;
        boolean z2;
        if (i != 9) {
            switch (i) {
                case 1:
                case 2:
                case 6:
                    throw new IllegalStateException("invalid state:" + this.mState);
                case 3:
                    handleSeek(this.mRequestTime);
                    break;
                case 4:
                    handleStop();
                    break;
                case 5:
                    handlePause();
                    break;
                default:
                    handleLoop(this.mCallback);
                    break;
            }
            z = true;
        } else {
            z = false;
        }
        synchronized (this.mSync) {
            z2 = z & this.mIsRunning;
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processPaused(int i) throws InterruptedException {
        boolean z;
        boolean z2 = true;
        if (i == 1 || i == 2) {
            throw new IllegalStateException("invalid state:" + this.mState);
        }
        if (i == 3) {
            handleSeek(this.mRequestTime);
        } else if (i == 4) {
            handleStop();
        } else if (i == 6) {
            handleResume();
        } else if (i != 9) {
            synchronized (this.mSync) {
                this.mSync.wait();
            }
        } else {
            z2 = false;
        }
        synchronized (this.mSync) {
            z = z2 & this.mIsRunning;
        }
        return z;
    }

    private void handlePrepare(Object obj) throws IOException {
        synchronized (this.mSync) {
            if (this.mState != 0) {
                throw new RuntimeException("invalid state:" + this.mState);
            }
        }
        if (obj instanceof String) {
            String str = (String) obj;
            File file = new File(str);
            if (TextUtils.isEmpty(str) || !file.canRead()) {
                throw new FileNotFoundException("Unable to read " + obj);
            }
            this.mMetadata.setDataSource(str);
            this.mExtractor.setDataSource(str);
        } else if (obj instanceof AssetFileDescriptor) {
            AssetFileDescriptor assetFileDescriptor = (AssetFileDescriptor) obj;
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            this.mMetadata.setDataSource(fileDescriptor);
            if (BuildCheck.isAndroid7()) {
                this.mExtractor.setDataSource(assetFileDescriptor);
            } else {
                this.mExtractor.setDataSource(fileDescriptor);
            }
        } else if (obj instanceof FileDescriptor) {
            FileDescriptor fileDescriptor2 = (FileDescriptor) obj;
            this.mMetadata.setDataSource(fileDescriptor2);
            this.mExtractor.setDataSource(fileDescriptor2);
        } else {
            throw new IllegalArgumentException("unknown source type:source=" + obj);
        }
        updateInfo(this.mMetadata);
        Surface surface = this.mOutputSurface;
        if (surface != null && this.mVideoDecoder == null) {
            VideoDecoder createDecoder = VideoDecoder.createDecoder(surface, this.mListener);
            this.mVideoDecoder = createDecoder;
            this.mVideoTrackIndex = createDecoder.prepare(this.mExtractor);
        } else {
            this.mVideoTrackIndex = -100;
        }
        if (this.mAudioEnabled && this.mAudioDecoder == null) {
            AudioDecoder createDecoder2 = AudioDecoder.createDecoder(this.mListener);
            this.mAudioDecoder = createDecoder2;
            this.mAudioTrackIndex = createDecoder2.prepare(this.mExtractor);
        } else {
            this.mAudioTrackIndex = -100;
        }
        synchronized (this.mSync) {
            this.mState = 1;
        }
        this.mCallback.onPrepared();
    }

    private void handleStart() {
        synchronized (this.mSync) {
            if (this.mState != 1) {
                throw new RuntimeException("invalid state:" + this.mState);
            }
            this.mState = 2;
        }
        long j = this.mRequestTime;
        if (j > 0) {
            handleSeek(j);
        }
        VideoDecoder videoDecoder = this.mVideoDecoder;
        if (videoDecoder != null) {
            videoDecoder.start();
        }
        AudioDecoder audioDecoder = this.mAudioDecoder;
        if (audioDecoder != null) {
            audioDecoder.start();
        }
    }

    private void handleSeek(long j) {
        if (j < 0) {
            return;
        }
        MediaExtractor mediaExtractor = this.mExtractor;
        if (mediaExtractor != null) {
            mediaExtractor.seekTo(j, 2);
            this.mExtractor.advance();
        }
        this.mRequestTime = -1L;
    }

    private void handleLoop(IFrameCallback iFrameCallback) {
        MediaExtractor mediaExtractor = this.mExtractor;
        if (mediaExtractor != null) {
            int sampleTrackIndex = mediaExtractor.getSampleTrackIndex();
            if (sampleTrackIndex == this.mVideoTrackIndex) {
                this.mVideoDecoder.decode(this.mExtractor);
            } else if (sampleTrackIndex == this.mAudioTrackIndex) {
                this.mAudioDecoder.decode(this.mExtractor);
            }
            if (this.mExtractor.advance()) {
                return;
            }
            if (this.mLoopEnabled) {
                handleSeek(0L);
                return;
            }
            VideoDecoder videoDecoder = this.mVideoDecoder;
            if (videoDecoder != null) {
                videoDecoder.signalEndOfStream();
            }
            AudioDecoder audioDecoder = this.mAudioDecoder;
            if (audioDecoder != null) {
                audioDecoder.signalEndOfStream();
            }
            handleStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStop() {
        boolean z;
        synchronized (this.mSync) {
            VideoDecoder videoDecoder = this.mVideoDecoder;
            if (videoDecoder != null) {
                videoDecoder.release();
                this.mVideoDecoder = null;
            }
            AudioDecoder audioDecoder = this.mAudioDecoder;
            if (audioDecoder != null) {
                audioDecoder.release();
                this.mAudioDecoder = null;
            }
        }
        synchronized (this.mSync) {
            z = this.mState != 0;
            this.mState = 0;
        }
        if (z) {
            this.mCallback.onFinished();
        }
    }

    private void updateInfo(MediaMetadataRetriever mediaMetadataRetriever) {
        this.mBitrate = 0;
        this.mRotation = 0;
        this.mVideoHeight = 0;
        this.mVideoWidth = 0;
        this.mDuration = 0L;
        this.mFrameRate = 0.0f;
        String extractMetadata = mediaMetadataRetriever.extractMetadata(18);
        if (!TextUtils.isEmpty(extractMetadata)) {
            this.mVideoWidth = Integer.parseInt(extractMetadata);
        }
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(19);
        if (!TextUtils.isEmpty(extractMetadata2)) {
            this.mVideoHeight = Integer.parseInt(extractMetadata2);
        }
        String extractMetadata3 = mediaMetadataRetriever.extractMetadata(24);
        if (!TextUtils.isEmpty(extractMetadata3)) {
            this.mRotation = Integer.parseInt(extractMetadata3);
        }
        String extractMetadata4 = mediaMetadataRetriever.extractMetadata(20);
        if (!TextUtils.isEmpty(extractMetadata4)) {
            this.mBitrate = Integer.parseInt(extractMetadata4);
        }
        String extractMetadata5 = mediaMetadataRetriever.extractMetadata(9);
        if (TextUtils.isEmpty(extractMetadata5)) {
            return;
        }
        this.mDuration = Long.parseLong(extractMetadata5) * 1000;
    }
}
