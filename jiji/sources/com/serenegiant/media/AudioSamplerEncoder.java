package com.serenegiant.media;

import com.serenegiant.media.IAudioSampler;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class AudioSamplerEncoder extends AbstractAudioEncoder {
    private int frame_count;
    private final Runnable mAudioTask;
    private final boolean mOwnSampler;
    private final IAudioSampler mSampler;
    private final IAudioSampler.SoundSamplerCallback mSoundSamplerCallback;

    static /* synthetic */ int access$008(AudioSamplerEncoder audioSamplerEncoder) {
        int i = audioSamplerEncoder.frame_count;
        audioSamplerEncoder.frame_count = i + 1;
        return i;
    }

    public AudioSamplerEncoder(IRecorder iRecorder, EncoderListener encoderListener, int i, IAudioSampler iAudioSampler) {
        super(iRecorder, encoderListener, i, iAudioSampler != null ? iAudioSampler.getChannels() : 1, iAudioSampler != null ? iAudioSampler.getSamplingFrequency() : AbstractAudioEncoder.DEFAULT_SAMPLE_RATE, AbstractAudioEncoder.DEFAULT_BIT_RATE);
        this.frame_count = 0;
        this.mSoundSamplerCallback = new IAudioSampler.SoundSamplerCallback() { // from class: com.serenegiant.media.AudioSamplerEncoder.1
            @Override // com.serenegiant.media.IAudioSampler.SoundSamplerCallback
            public void onError(Exception exc) {
            }

            @Override // com.serenegiant.media.IAudioSampler.SoundSamplerCallback
            public void onData(ByteBuffer byteBuffer, int i2, long j) {
                synchronized (AudioSamplerEncoder.this.mSync) {
                    if (AudioSamplerEncoder.this.mIsCapturing && !AudioSamplerEncoder.this.mRequestStop) {
                        if (i2 > 0) {
                            AudioSamplerEncoder.this.frameAvailableSoon();
                            AudioSamplerEncoder.this.encode(byteBuffer, i2, j);
                            AudioSamplerEncoder.access$008(AudioSamplerEncoder.this);
                        }
                    }
                }
            }
        };
        this.mAudioTask = new Runnable() { // from class: com.serenegiant.media.AudioSamplerEncoder.2
            /* JADX WARN: Removed duplicated region for block: B:17:0x0026  */
            /* JADX WARN: Removed duplicated region for block: B:34:0x0065 A[ORIG_RETURN, RETURN] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r6 = this;
                L0:
                    com.serenegiant.media.AudioSamplerEncoder r0 = com.serenegiant.media.AudioSamplerEncoder.this
                    java.lang.Object r0 = r0.mSync
                    monitor-enter(r0)
                    com.serenegiant.media.AudioSamplerEncoder r1 = com.serenegiant.media.AudioSamplerEncoder.this     // Catch: java.lang.Throwable -> L66
                    boolean r1 = r1.mIsCapturing     // Catch: java.lang.Throwable -> L66
                    if (r1 == 0) goto L1d
                    com.serenegiant.media.AudioSamplerEncoder r1 = com.serenegiant.media.AudioSamplerEncoder.this     // Catch: java.lang.Throwable -> L66
                    boolean r1 = r1.mRequestStop     // Catch: java.lang.Throwable -> L66
                    if (r1 == 0) goto L12
                    goto L1d
                L12:
                    com.serenegiant.media.AudioSamplerEncoder r1 = com.serenegiant.media.AudioSamplerEncoder.this     // Catch: java.lang.InterruptedException -> L1b java.lang.Throwable -> L66
                    java.lang.Object r1 = r1.mSync     // Catch: java.lang.InterruptedException -> L1b java.lang.Throwable -> L66
                    r1.wait()     // Catch: java.lang.InterruptedException -> L1b java.lang.Throwable -> L66
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L66
                    goto L0
                L1b:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L66
                    goto L1e
                L1d:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L66
                L1e:
                    com.serenegiant.media.AudioSamplerEncoder r0 = com.serenegiant.media.AudioSamplerEncoder.this
                    int r0 = com.serenegiant.media.AudioSamplerEncoder.access$000(r0)
                    if (r0 != 0) goto L65
                    r0 = 1024(0x400, float:1.435E-42)
                    java.nio.ByteBuffer r1 = java.nio.ByteBuffer.allocateDirect(r0)
                    java.nio.ByteOrder r2 = java.nio.ByteOrder.nativeOrder()
                    java.nio.ByteBuffer r1 = r1.order(r2)
                    r2 = 0
                L35:
                    com.serenegiant.media.AudioSamplerEncoder r3 = com.serenegiant.media.AudioSamplerEncoder.this
                    boolean r3 = r3.mIsCapturing
                    if (r3 == 0) goto L65
                    r3 = 5
                    if (r2 >= r3) goto L65
                    r1.clear()
                    r1.position(r0)
                    r1.flip()
                    com.serenegiant.media.AudioSamplerEncoder r3 = com.serenegiant.media.AudioSamplerEncoder.this
                    long r4 = r3.getInputPTSUs()
                    r3.encode(r1, r0, r4)
                    com.serenegiant.media.AudioSamplerEncoder r3 = com.serenegiant.media.AudioSamplerEncoder.this
                    r3.frameAvailableSoon()
                    monitor-enter(r6)
                    r3 = 50
                    r6.wait(r3)     // Catch: java.lang.Throwable -> L5f java.lang.InterruptedException -> L61
                    monitor-exit(r6)     // Catch: java.lang.Throwable -> L5f
                    int r2 = r2 + 1
                    goto L35
                L5f:
                    r0 = move-exception
                    goto L63
                L61:
                    monitor-exit(r6)     // Catch: java.lang.Throwable -> L5f
                    goto L65
                L63:
                    monitor-exit(r6)     // Catch: java.lang.Throwable -> L5f
                    throw r0
                L65:
                    return
                L66:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L66
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.AudioSamplerEncoder.AnonymousClass2.run():void");
            }
        };
        if (iAudioSampler != null) {
            this.mOwnSampler = false;
        } else if (i < 0 || i > 7) {
            throw new IllegalArgumentException("invalid audio source:" + i);
        } else {
            iAudioSampler = new AudioSampler(i, 1, AbstractAudioEncoder.DEFAULT_SAMPLE_RATE, 1024, 25);
            this.mOwnSampler = true;
        }
        this.mSampler = iAudioSampler;
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void start() {
        super.start();
        this.mSampler.addCallback(this.mSoundSamplerCallback);
        if (this.mOwnSampler) {
            this.mSampler.start();
        }
        new Thread(this.mAudioTask, "AudioTask").start();
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void stop() {
        this.mSampler.removeCallback(this.mSoundSamplerCallback);
        if (this.mOwnSampler) {
            this.mSampler.stop();
        }
        super.stop();
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder, com.serenegiant.glpipeline.GLPipeline
    public void release() {
        if (this.mOwnSampler) {
            this.mSampler.release();
        }
        super.release();
    }
}
