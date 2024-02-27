package com.serenegiant.media;

import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class AudioEncoderBuffered extends AbstractAudioEncoder {
    private static final int MAX_POOL_SIZE = 100;
    private static final int MAX_QUEUE_SIZE = 100;
    private static final String TAG = "AudioEncoderBuffered";
    private final MemMediaQueue mAudioQueue;
    private AudioThread mAudioThread;
    protected final int mBufferSize;
    private DequeueThread mDequeueThread;

    public AudioEncoderBuffered(IRecorder iRecorder, EncoderListener encoderListener, int i, int i2) {
        super(iRecorder, encoderListener, i, i2, AbstractAudioEncoder.DEFAULT_SAMPLE_RATE, AbstractAudioEncoder.DEFAULT_BIT_RATE);
        this.mAudioThread = null;
        this.mDequeueThread = null;
        this.mBufferSize = 1024;
        if (i < 0 || i > 7) {
            throw new IllegalArgumentException("invalid audio source:" + i);
        }
        this.mAudioQueue = new MemMediaQueue(100, 100, 100);
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void start() {
        super.start();
        if (this.mAudioThread == null) {
            AudioThread audioThread = new AudioThread();
            this.mAudioThread = audioThread;
            audioThread.start();
            DequeueThread dequeueThread = new DequeueThread();
            this.mDequeueThread = dequeueThread;
            dequeueThread.start();
        }
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void stop() {
        this.mAudioThread = null;
        this.mDequeueThread = null;
        super.stop();
    }

    /* loaded from: classes2.dex */
    private final class AudioThread extends Thread {
        public AudioThread() {
            super("AudioThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(-16);
            AudioRecord createAudioRecord = IAudioSampler.createAudioRecord(AudioEncoderBuffered.this.mAudioSource, AudioEncoderBuffered.this.mSampleRate, AudioEncoderBuffered.this.mChannelCount, 2, AudioSampler.getAudioBufferSize(AudioEncoderBuffered.this.mChannelCount, AudioEncoderBuffered.this.mSampleRate, 1024, 25));
            if (createAudioRecord != null) {
                try {
                    if (AudioEncoderBuffered.this.mIsCapturing) {
                        createAudioRecord.startRecording();
                        loop0: while (true) {
                            int i = 0;
                            while (true) {
                                if (!AudioEncoderBuffered.this.mIsCapturing || AudioEncoderBuffered.this.mRequestStop) {
                                    break loop0;
                                }
                                int recordingState = createAudioRecord.getRecordingState();
                                if (recordingState != 3) {
                                    if (i == 0) {
                                        Log.e(AudioEncoderBuffered.TAG, "not a recording state," + recordingState);
                                    }
                                    i++;
                                    if (i > 20) {
                                        break loop0;
                                    }
                                    synchronized (AudioEncoderBuffered.this.mSync) {
                                        AudioEncoderBuffered.this.mSync.wait(100L);
                                    }
                                } else {
                                    RecycleMediaData obtain = AudioEncoderBuffered.this.mAudioQueue.obtain(1024);
                                    ByteBuffer byteBuffer = obtain.get();
                                    byteBuffer.clear();
                                    try {
                                        int read = createAudioRecord.read(byteBuffer, 1024);
                                        if (read > 0) {
                                            obtain.presentationTimeUs(AudioEncoderBuffered.this.getInputPTSUs()).size(read);
                                            byteBuffer.position(read);
                                            byteBuffer.flip();
                                            AudioEncoderBuffered.this.mAudioQueue.queueFrame(obtain);
                                            break;
                                        } else if (read == 0) {
                                            obtain.recycle();
                                            break;
                                        } else {
                                            if (read == -1) {
                                                if (i == 0) {
                                                    Log.e(AudioEncoderBuffered.TAG, "Read error ERROR");
                                                }
                                            } else if (read == -2) {
                                                if (i == 0) {
                                                    Log.e(AudioEncoderBuffered.TAG, "Read error ERROR_BAD_VALUE");
                                                }
                                            } else if (read == -3) {
                                                if (i == 0) {
                                                    Log.e(AudioEncoderBuffered.TAG, "Read error ERROR_INVALID_OPERATION");
                                                }
                                            } else if (read == -6) {
                                                Log.e(AudioEncoderBuffered.TAG, "Read error ERROR_DEAD_OBJECT");
                                                obtain.recycle();
                                                break loop0;
                                            } else if (read < 0 && i == 0) {
                                                Log.e(AudioEncoderBuffered.TAG, "Read returned unknown err " + read);
                                            }
                                            i++;
                                            obtain.recycle();
                                            if (i > 10) {
                                                break loop0;
                                            }
                                        }
                                    } catch (Exception unused) {
                                    }
                                }
                            }
                        }
                        createAudioRecord.stop();
                    }
                } catch (Exception unused2) {
                } catch (Throwable th) {
                    createAudioRecord.release();
                    throw th;
                }
                createAudioRecord.release();
            }
        }
    }

    /* loaded from: classes2.dex */
    private final class DequeueThread extends Thread {
        public DequeueThread() {
            super("DequeueThread");
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:
            r2 = r8.this$0.mAudioQueue.poll(30L, java.util.concurrent.TimeUnit.MILLISECONDS);
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:
            if (r2 == null) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
            if (r2.size() <= 0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
            r8.this$0.encode(r2.get(), r2.size(), r2.presentationTimeUs());
            r8.this$0.frameAvailableSoon();
            r1 = r1 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0043, code lost:
            r2.recycle();
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r8 = this;
                r0 = 0
                r1 = r0
            L2:
                com.serenegiant.media.AudioEncoderBuffered r2 = com.serenegiant.media.AudioEncoderBuffered.this
                java.lang.Object r2 = r2.mSync
                monitor-enter(r2)
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this     // Catch: java.lang.Throwable -> L85
                boolean r3 = r3.mIsCapturing     // Catch: java.lang.Throwable -> L85
                if (r3 == 0) goto L47
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this     // Catch: java.lang.Throwable -> L85
                boolean r3 = r3.mRequestStop     // Catch: java.lang.Throwable -> L85
                if (r3 == 0) goto L14
                goto L47
            L14:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L85
                com.serenegiant.media.AudioEncoderBuffered r2 = com.serenegiant.media.AudioEncoderBuffered.this     // Catch: java.lang.InterruptedException -> L48
                com.serenegiant.media.MemMediaQueue r2 = com.serenegiant.media.AudioEncoderBuffered.access$100(r2)     // Catch: java.lang.InterruptedException -> L48
                java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.InterruptedException -> L48
                r4 = 30
                com.serenegiant.media.RecycleMediaData r2 = r2.poll(r4, r3)     // Catch: java.lang.InterruptedException -> L48
                if (r2 == 0) goto L2
                int r3 = r2.size()
                if (r3 <= 0) goto L43
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this
                java.nio.ByteBuffer r4 = r2.get()
                int r5 = r2.size()
                long r6 = r2.presentationTimeUs()
                r3.encode(r4, r5, r6)
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this
                r3.frameAvailableSoon()
                int r1 = r1 + 1
            L43:
                r2.recycle()
                goto L2
            L47:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L85
            L48:
                if (r1 != 0) goto L84
                r1 = 1024(0x400, float:1.435E-42)
                java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocateDirect(r1)
                java.nio.ByteOrder r3 = java.nio.ByteOrder.nativeOrder()
                java.nio.ByteBuffer r2 = r2.order(r3)
            L58:
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this
                boolean r3 = r3.mIsCapturing
                if (r3 == 0) goto L84
                r3 = 5
                if (r0 >= r3) goto L84
                r2.position(r1)
                r2.flip()
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this
                long r4 = r3.getInputPTSUs()
                r3.encode(r2, r1, r4)
                com.serenegiant.media.AudioEncoderBuffered r3 = com.serenegiant.media.AudioEncoderBuffered.this
                r3.frameAvailableSoon()
                monitor-enter(r8)
                r3 = 50
                r8.wait(r3)     // Catch: java.lang.Throwable -> L7c java.lang.InterruptedException -> L7e
                goto L7e
            L7c:
                r0 = move-exception
                goto L82
            L7e:
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L7c
                int r0 = r0 + 1
                goto L58
            L82:
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L7c
                throw r0
            L84:
                return
            L85:
                r0 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L85
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.AudioEncoderBuffered.DequeueThread.run():void");
        }
    }
}
