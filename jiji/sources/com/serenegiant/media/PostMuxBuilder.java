package com.serenegiant.media;

import android.content.Context;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.media.IMuxer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* loaded from: classes2.dex */
class PostMuxBuilder extends PostMuxCommon {
    private static final boolean DEBUG = false;
    private static final long MSEC30US = 33333;
    private static final String TAG = "PostMuxBuilder";
    private volatile boolean mIsRunning;
    private final IMuxer.IMuxerFactory mMuxerFactory;
    private final boolean mUseMediaMuxer;

    public PostMuxBuilder(boolean z) {
        this(null, z);
    }

    public PostMuxBuilder(IMuxer.IMuxerFactory iMuxerFactory, boolean z) {
        this.mMuxerFactory = iMuxerFactory == null ? new IMuxer.DefaultFactory() : iMuxerFactory;
        this.mUseMediaMuxer = z;
    }

    public void cancel() {
        this.mIsRunning = false;
    }

    @Deprecated
    public void buildFromRawFile(Context context, String str, String str2) throws IOException {
        DataInputStream dataInputStream;
        File file = new File(str);
        File file2 = new File(file, "video.raw");
        File file3 = new File(file, "audio.raw");
        new File(str2);
        boolean z = file2.exists() && file2.canRead();
        boolean z2 = file3.exists() && file3.canRead();
        if (z || z2) {
            MediaMuxerWrapper mediaMuxerWrapper = new MediaMuxerWrapper(str2, 0);
            this.mIsRunning = true;
            if (z) {
                try {
                    dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file2)));
                } catch (Throwable th) {
                    this.mIsRunning = false;
                    mediaMuxerWrapper.release();
                    throw th;
                }
            } else {
                dataInputStream = null;
            }
            internalBuild(mediaMuxerWrapper, dataInputStream, z2 ? new DataInputStream(new BufferedInputStream(new FileInputStream(file3))) : null);
            this.mIsRunning = false;
            mediaMuxerWrapper.release();
        }
    }

    public void buildFromRawFile(Context context, String str, DocumentFile documentFile) throws IOException {
        DataInputStream dataInputStream;
        File file = new File(str);
        File file2 = new File(file, "video.raw");
        File file3 = new File(file, "audio.raw");
        boolean z = true;
        boolean z2 = file2.exists() && file2.canRead();
        if (!file3.exists() || !file3.canRead()) {
            z = false;
        }
        if (z2 || z) {
            IMuxer createMuxer = this.mMuxerFactory.createMuxer(context, this.mUseMediaMuxer, documentFile);
            if (createMuxer == null) {
                throw new IOException("Failed to create muxer");
            }
            if (createMuxer != null) {
                if (z2) {
                    try {
                        dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file2)));
                    } catch (Throwable th) {
                        this.mIsRunning = false;
                        createMuxer.release();
                        throw th;
                    }
                } else {
                    dataInputStream = null;
                }
                internalBuild(createMuxer, dataInputStream, z ? new DataInputStream(new BufferedInputStream(new FileInputStream(file3))) : null);
                this.mIsRunning = false;
                createMuxer.release();
                return;
            }
            throw new IOException("Failed to create muxer");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void internalBuild(com.serenegiant.media.IMuxer r28, java.io.DataInputStream r29, java.io.DataInputStream r30) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.PostMuxBuilder.internalBuild(com.serenegiant.media.IMuxer, java.io.DataInputStream, java.io.DataInputStream):void");
    }

    @Deprecated
    public void buildFromRawChannel(Context context, String str, String str2) throws IOException {
        FileChannel channel;
        File file = new File(str);
        File file2 = new File(file, "video.raw");
        File file3 = new File(file, "audio.raw");
        new File(str2);
        boolean z = file2.exists() && file2.canRead();
        boolean z2 = file3.exists() && file3.canRead();
        if (z || z2) {
            MediaMuxerWrapper mediaMuxerWrapper = new MediaMuxerWrapper(str2, 0);
            this.mIsRunning = true;
            if (z) {
                try {
                    channel = new FileInputStream(file2).getChannel();
                } catch (Throwable th) {
                    this.mIsRunning = false;
                    mediaMuxerWrapper.release();
                    throw th;
                }
            } else {
                channel = null;
            }
            internalBuild(mediaMuxerWrapper, channel, z2 ? new FileInputStream(file3).getChannel() : null);
            this.mIsRunning = false;
            mediaMuxerWrapper.release();
        }
    }

    public void buildFromRawChannel(Context context, String str, DocumentFile documentFile) throws IOException {
        FileChannel channel;
        File file = new File(str);
        File file2 = new File(file, "video.raw");
        File file3 = new File(file, "audio.raw");
        boolean z = true;
        boolean z2 = file2.exists() && file2.canRead();
        if (!file3.exists() || !file3.canRead()) {
            z = false;
        }
        if (z2 || z) {
            IMuxer createMuxer = this.mMuxerFactory.createMuxer(context, this.mUseMediaMuxer, documentFile);
            if (createMuxer == null) {
                throw new IOException("Failed to create muxer");
            }
            if (createMuxer != null) {
                if (z2) {
                    try {
                        channel = new FileInputStream(file2).getChannel();
                    } catch (Throwable th) {
                        this.mIsRunning = false;
                        createMuxer.release();
                        throw th;
                    }
                } else {
                    channel = null;
                }
                internalBuild(createMuxer, channel, z ? new FileInputStream(file3).getChannel() : null);
                this.mIsRunning = false;
                createMuxer.release();
                return;
            }
            throw new IOException("Failed to create muxer");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void internalBuild(com.serenegiant.media.IMuxer r27, java.nio.channels.ByteChannel r28, java.nio.channels.ByteChannel r29) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.PostMuxBuilder.internalBuild(com.serenegiant.media.IMuxer, java.nio.channels.ByteChannel, java.nio.channels.ByteChannel):void");
    }
}
