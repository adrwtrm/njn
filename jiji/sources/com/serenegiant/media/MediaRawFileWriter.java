package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
abstract class MediaRawFileWriter extends PostMuxCommon {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaRawFileWriter";
    private int mFrameCounts;
    private DataOutputStream mOut;
    private byte[] temp;

    public static MediaRawFileWriter newInstance(Context context, int i, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str) throws IOException {
        if (i != 0) {
            if (i == 1) {
                return new MediaRawAudioWriter(context, mediaFormat, mediaFormat2, str);
            }
            throw new IOException("Unexpected media type=" + i);
        }
        return new MediaRawVideoWriter(context, mediaFormat, mediaFormat2, str);
    }

    /* loaded from: classes2.dex */
    private static class MediaRawVideoWriter extends MediaRawFileWriter {
        public MediaRawVideoWriter(Context context, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str) throws IOException {
            super(context, mediaFormat, mediaFormat2, str, "video.raw");
        }
    }

    /* loaded from: classes2.dex */
    private static class MediaRawAudioWriter extends MediaRawFileWriter {
        public MediaRawAudioWriter(Context context, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str) throws IOException {
            super(context, mediaFormat, mediaFormat2, str, "audio.raw");
        }
    }

    private MediaRawFileWriter(Context context, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str, String str2) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream((str.endsWith("/") ? new StringBuilder().append(str) : new StringBuilder().append(str).append("/")).append(str2).toString(), false)));
        this.mOut = dataOutputStream;
        writeFormat(dataOutputStream, mediaFormat, mediaFormat2);
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public synchronized void release() {
        DataOutputStream dataOutputStream = this.mOut;
        if (dataOutputStream != null) {
            try {
                dataOutputStream.flush();
                this.mOut.close();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
            this.mOut = null;
        }
    }

    public synchronized void writeSampleData(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (bufferInfo.size != 0) {
            byte[] bArr = this.temp;
            if (bArr == null || bArr.length < bufferInfo.size) {
                this.temp = new byte[bufferInfo.size];
            }
            int i = this.mFrameCounts + 1;
            this.mFrameCounts = i;
            writeStream(this.mOut, 0, i, bufferInfo, byteBuffer, this.temp);
        }
    }
}
