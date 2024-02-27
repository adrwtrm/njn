package com.serenegiant.media;

import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public class AudioEncoder extends AbstractAudioEncoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "AudioEncoder";
    private AudioThread mAudioThread;

    public AudioEncoder(IRecorder iRecorder, EncoderListener encoderListener, int i, int i2) {
        super(iRecorder, encoderListener, i, i2, AbstractAudioEncoder.DEFAULT_SAMPLE_RATE, AbstractAudioEncoder.DEFAULT_BIT_RATE);
        this.mAudioThread = null;
        if (i < 0 || i > 7) {
            throw new IllegalArgumentException("invalid audio source:" + i);
        }
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void start() {
        super.start();
        if (this.mAudioThread == null) {
            AudioThread audioThread = new AudioThread();
            this.mAudioThread = audioThread;
            audioThread.start();
        }
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void stop() {
        this.mAudioThread = null;
        super.stop();
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder, com.serenegiant.glpipeline.GLPipeline
    public void release() {
        this.mAudioThread = null;
        super.release();
    }

    /* loaded from: classes2.dex */
    private final class AudioThread extends Thread {
        public AudioThread() {
            super("AudioThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            int i;
            Process.setThreadPriority(-16);
            int audioBufferSize = AudioSampler.getAudioBufferSize(AudioEncoder.this.mChannelCount, AudioEncoder.this.mSampleRate, 1024, 25);
            AudioRecord createAudioRecord = IAudioSampler.createAudioRecord(AudioEncoder.this.mAudioSource, AudioEncoder.this.mSampleRate, AudioEncoder.this.mChannelCount, 2, audioBufferSize);
            ByteBuffer order = ByteBuffer.allocateDirect(audioBufferSize).order(ByteOrder.nativeOrder());
            if (createAudioRecord != null) {
                try {
                    try {
                    } finally {
                        createAudioRecord.release();
                    }
                } catch (Exception unused) {
                }
                if (AudioEncoder.this.mIsCapturing) {
                    try {
                        createAudioRecord.startRecording();
                    } catch (Exception unused2) {
                    }
                    try {
                        int i2 = AudioEncoder.this.mChannelCount * 1024;
                        int i3 = 0;
                        i = 0;
                        while (AudioEncoder.this.mIsCapturing && !AudioEncoder.this.mRequestStop) {
                            try {
                                int recordingState = createAudioRecord.getRecordingState();
                                if (recordingState != 3) {
                                    if (i3 == 0) {
                                        Log.e(AudioEncoder.TAG, "not a recording state," + recordingState);
                                    }
                                    i3++;
                                    if (i3 > 20) {
                                        break;
                                    }
                                    synchronized (AudioEncoder.this.mSync) {
                                        AudioEncoder.this.mSync.wait(100L);
                                    }
                                } else {
                                    order.clear();
                                    try {
                                        int read = createAudioRecord.read(order, i2);
                                        if (read > 0) {
                                            i++;
                                            order.position(read);
                                            order.flip();
                                            AudioEncoder audioEncoder = AudioEncoder.this;
                                            audioEncoder.encode(order, read, audioEncoder.getInputPTSUs());
                                            AudioEncoder.this.frameAvailableSoon();
                                            i3 = 0;
                                        } else if (read == 0) {
                                            i3 = 0;
                                        } else {
                                            if (read == -1) {
                                                if (i3 == 0) {
                                                    Log.e(AudioEncoder.TAG, "Read error ERROR");
                                                }
                                            } else if (read == -2) {
                                                if (i3 == 0) {
                                                    Log.e(AudioEncoder.TAG, "Read error ERROR_BAD_VALUE");
                                                }
                                            } else if (read == -3) {
                                                if (i3 == 0) {
                                                    Log.e(AudioEncoder.TAG, "Read error ERROR_INVALID_OPERATION");
                                                }
                                            } else if (read == -6) {
                                                if (i3 == 0) {
                                                    Log.e(AudioEncoder.TAG, "Read error ERROR_DEAD_OBJECT");
                                                }
                                            } else if (read < 0) {
                                                if (i3 == 0) {
                                                    Log.e(AudioEncoder.TAG, "Read returned unknown err " + read);
                                                }
                                            }
                                            i3++;
                                        }
                                        if (i3 > 10) {
                                            break;
                                        }
                                    } catch (Exception unused3) {
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                createAudioRecord.stop();
                                throw th;
                            }
                        }
                        if (i > 0) {
                            AudioEncoder.this.frameAvailableSoon();
                        }
                        createAudioRecord.stop();
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                i = 0;
            } else {
                i = 0;
            }
            if (i == 0) {
                for (int i4 = 0; AudioEncoder.this.mIsCapturing && i4 < 5; i4++) {
                    order.position(1024);
                    order.flip();
                    AudioEncoder audioEncoder2 = AudioEncoder.this;
                    audioEncoder2.encode(order, 1024, audioEncoder2.getInputPTSUs());
                    AudioEncoder.this.frameAvailableSoon();
                    synchronized (this) {
                        try {
                            wait(50L);
                        } catch (InterruptedException unused4) {
                        }
                    }
                }
            }
        }
    }
}
