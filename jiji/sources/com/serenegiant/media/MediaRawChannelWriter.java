package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;

/* loaded from: classes2.dex */
abstract class MediaRawChannelWriter extends PostMuxCommon {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaRawChannelWriter";
    private int mFrameCounts;
    private ByteChannel mOut;

    public static MediaRawChannelWriter newInstance(Context context, int i, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str) throws IOException {
        if (i != 0) {
            if (i == 1) {
                return new MediaRawAudioWriter(context, mediaFormat, mediaFormat2, str);
            }
            throw new IOException("Unexpected media type=" + i);
        }
        return new MediaRawVideoWriter(context, mediaFormat, mediaFormat2, str);
    }

    /* loaded from: classes2.dex */
    private static class MediaRawVideoWriter extends MediaRawChannelWriter {
        public MediaRawVideoWriter(Context context, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str) throws IOException {
            super(context, mediaFormat, mediaFormat2, str, "video.raw");
        }
    }

    /* loaded from: classes2.dex */
    private static class MediaRawAudioWriter extends MediaRawChannelWriter {
        public MediaRawAudioWriter(Context context, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str) throws IOException {
            super(context, mediaFormat, mediaFormat2, str, "audio.raw");
        }
    }

    private MediaRawChannelWriter(Context context, MediaFormat mediaFormat, MediaFormat mediaFormat2, String str, String str2) throws IOException {
        FileChannel channel = new FileOutputStream((str.endsWith("/") ? new StringBuilder().append(str) : new StringBuilder().append(str).append("/")).append(str2).toString(), false).getChannel();
        this.mOut = channel;
        writeFormat(channel, mediaFormat, mediaFormat2);
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public synchronized void release() {
        ByteChannel byteChannel = this.mOut;
        if (byteChannel != null) {
            try {
                byteChannel.close();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
            this.mOut = null;
        }
    }

    public synchronized void writeSampleData(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (bufferInfo.size != 0) {
            int i = this.mFrameCounts + 1;
            this.mFrameCounts = i;
            writeStream(this.mOut, 0, i, bufferInfo, byteBuffer);
        }
    }
}
