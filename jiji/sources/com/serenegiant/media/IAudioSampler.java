package com.serenegiant.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.Time;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class IAudioSampler {
    public static final int AUDIO_SOURCE_UAC = 100;
    private static final int MAX_POOL_SIZE = 200;
    private static final int MAX_QUEUE_SIZE = 200;
    private CallbackThread mCallbackThread;
    protected volatile boolean mIsCapturing;
    private final String TAG = getClass().getSimpleName();
    private final Object mCallbackSync = new Object();
    private final Set<SoundSamplerCallback> mCallbacks = new CopyOnWriteArraySet();
    protected int mDefaultBufferSize = 1024;
    private long prevInputPTSUs = -1;
    private final MemMediaQueue mAudioQueue = new MemMediaQueue(200, 200, 200);

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface AudioSource {
    }

    /* loaded from: classes2.dex */
    public interface SoundSamplerCallback {
        void onData(ByteBuffer byteBuffer, int i, long j);

        void onError(Exception exc);
    }

    public abstract int getAudioSource();

    public abstract int getBitResolution();

    public abstract int getChannels();

    public abstract int getSamplingFrequency();

    public static AudioRecord createAudioRecord(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = {0, 5, 1, 0, 7, 6};
        if (i == 2) {
            iArr[0] = 5;
        } else if (i != 3) {
            iArr[0] = 1;
        } else {
            iArr[0] = 7;
        }
        AudioRecord audioRecord = null;
        for (int i6 = 0; i6 < 6; i6++) {
            int i7 = iArr[i6];
            try {
                int i8 = 16;
                if (BuildCheck.isAndroid6()) {
                    AudioRecord.Builder audioSource = new AudioRecord.Builder().setAudioSource(i7);
                    AudioFormat.Builder sampleRate = new AudioFormat.Builder().setEncoding(i4).setSampleRate(i2);
                    if (i3 != 1) {
                        i8 = 12;
                    }
                    audioRecord = audioSource.setAudioFormat(sampleRate.setChannelMask(i8).build()).setBufferSizeInBytes(i5).build();
                } else {
                    audioRecord = new AudioRecord(i7, i2, i3 == 1 ? 16 : 12, i4, i5);
                }
            } catch (Exception unused) {
            }
            if (audioRecord.getState() != 1) {
                audioRecord.release();
                audioRecord = null;
            }
            if (audioRecord != null) {
                break;
            }
        }
        return audioRecord;
    }

    public void release() {
        if (isStarted()) {
            stop();
        }
        this.mCallbacks.clear();
    }

    public synchronized void start() {
        synchronized (this.mCallbackSync) {
            if (this.mCallbackThread == null) {
                this.mIsCapturing = true;
                CallbackThread callbackThread = new CallbackThread();
                this.mCallbackThread = callbackThread;
                callbackThread.start();
            }
        }
    }

    public synchronized void stop() {
        synchronized (this.mCallbackSync) {
            boolean z = this.mIsCapturing;
            this.mIsCapturing = false;
            this.mCallbackThread = null;
            if (z) {
                try {
                    this.mCallbackSync.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public void addCallback(SoundSamplerCallback soundSamplerCallback) {
        if (soundSamplerCallback != null) {
            this.mCallbacks.add(soundSamplerCallback);
        }
    }

    public void removeCallback(SoundSamplerCallback soundSamplerCallback) {
        if (soundSamplerCallback != null) {
            this.mCallbacks.remove(soundSamplerCallback);
        }
    }

    public boolean isStarted() {
        return this.mIsCapturing;
    }

    public int getBufferSize() {
        return this.mDefaultBufferSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnData(MediaData mediaData) {
        ByteBuffer byteBuffer = mediaData.get();
        int size = mediaData.size();
        long presentationTimeUs = mediaData.presentationTimeUs();
        for (SoundSamplerCallback soundSamplerCallback : this.mCallbacks) {
            try {
                byteBuffer.clear();
                byteBuffer.position(size);
                byteBuffer.flip();
                soundSamplerCallback.onData(byteBuffer, size, presentationTimeUs);
            } catch (Exception e) {
                this.mCallbacks.remove(soundSamplerCallback);
                Log.w(this.TAG, "callOnData:", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void callOnError(Exception exc) {
        for (SoundSamplerCallback soundSamplerCallback : this.mCallbacks) {
            try {
                soundSamplerCallback.onError(exc);
            } catch (Exception e) {
                this.mCallbacks.remove(soundSamplerCallback);
                Log.w(this.TAG, "callOnError:", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init_pool(int i) {
        this.mDefaultBufferSize = i;
        this.mAudioQueue.init(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public RecycleMediaData obtain() {
        return this.mAudioQueue.obtain(Integer.valueOf(this.mDefaultBufferSize));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean addMediaData(RecycleMediaData recycleMediaData) {
        return this.mAudioQueue.queueFrame(recycleMediaData);
    }

    protected RecycleMediaData pollMediaData(long j) throws InterruptedException {
        return this.mAudioQueue.poll(j, TimeUnit.MILLISECONDS);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class CallbackThread extends Thread {
        public CallbackThread() {
            super("AudioSampler");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(-16);
            while (IAudioSampler.this.mIsCapturing) {
                try {
                    RecycleMediaData pollMediaData = IAudioSampler.this.pollMediaData(100L);
                    if (pollMediaData != null) {
                        IAudioSampler.this.callOnData(pollMediaData);
                        pollMediaData.recycle();
                    }
                } catch (InterruptedException unused) {
                }
            }
            synchronized (IAudioSampler.this.mCallbackSync) {
                IAudioSampler.this.mCallbackSync.notifyAll();
            }
        }
    }
}
