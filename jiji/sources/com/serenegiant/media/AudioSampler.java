package com.serenegiant.media;

import android.media.AudioRecord;

/* loaded from: classes2.dex */
public class AudioSampler extends IAudioSampler {
    private static final int AUDIO_FORMAT = 2;
    private static final String TAG = "AudioSampler";
    private final int AUDIO_SOURCE;
    private final int BUFFER_SIZE;
    private final int CHANNEL_COUNT;
    private final int SAMPLES_PER_FRAME;
    private final int SAMPLING_RATE;
    private AudioThread mAudioThread;
    private final Object mSync = new Object();

    @Override // com.serenegiant.media.IAudioSampler
    public int getBitResolution() {
        return 16;
    }

    public AudioSampler(int i, int i2, int i3, int i4, int i5) {
        this.AUDIO_SOURCE = i;
        this.CHANNEL_COUNT = i2;
        this.SAMPLING_RATE = i3;
        this.SAMPLES_PER_FRAME = i4 * i2;
        this.BUFFER_SIZE = getAudioBufferSize(i2, i3, i4, i5);
    }

    @Override // com.serenegiant.media.IAudioSampler
    public int getBufferSize() {
        return this.SAMPLES_PER_FRAME;
    }

    @Override // com.serenegiant.media.IAudioSampler
    public synchronized void start() {
        super.start();
        synchronized (this.mSync) {
            if (this.mAudioThread == null) {
                init_pool(this.SAMPLES_PER_FRAME);
                AudioThread audioThread = new AudioThread();
                this.mAudioThread = audioThread;
                audioThread.start();
            }
        }
    }

    @Override // com.serenegiant.media.IAudioSampler
    public synchronized void stop() {
        synchronized (this.mSync) {
            this.mIsCapturing = false;
            this.mAudioThread = null;
            this.mSync.notify();
        }
        super.stop();
    }

    @Override // com.serenegiant.media.IAudioSampler
    public int getAudioSource() {
        return this.AUDIO_SOURCE;
    }

    /* loaded from: classes2.dex */
    protected static final class AudioRecordRec {
        AudioRecord audioRecord;
        int bufferSize;

        protected AudioRecordRec() {
        }
    }

    public static int getAudioBufferSize(int i, int i2, int i3, int i4) {
        int minBufferSize = AudioRecord.getMinBufferSize(i2, i == 1 ? 16 : 12, 2);
        int i5 = i4 * i3;
        return i5 < minBufferSize ? ((minBufferSize / i3) + 1) * i3 * 2 * i : i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class AudioThread extends Thread {
        public AudioThread() {
            super("AudioThread");
        }

        /* JADX WARN: Removed duplicated region for block: B:108:0x018a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 438
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.AudioSampler.AudioThread.run():void");
        }
    }

    @Override // com.serenegiant.media.IAudioSampler
    public int getChannels() {
        return this.CHANNEL_COUNT;
    }

    @Override // com.serenegiant.media.IAudioSampler
    public int getSamplingFrequency() {
        return this.SAMPLING_RATE;
    }
}
