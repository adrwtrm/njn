package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.mediastore.MediaStoreOutputStream;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.UriHelper;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MediaMuxerWrapper implements IMuxer {
    private static final String TAG = "MediaMuxerWrapper";
    public static boolean USE_MEDIASTORE_OUTPUT_STREAM = true;
    private volatile boolean mIsStarted;
    private final MediaMuxer mMuxer;
    private final String mOutputPath;
    private final OutputStream mOutputStream;
    private boolean mReleased;

    public static MediaMuxerWrapper newInstance(Context context, DocumentFile documentFile, int i) throws IOException {
        Uri uri = documentFile.getUri();
        if (BuildCheck.isAPI29() && UriHelper.isContentUri(uri)) {
            return new MediaMuxerWrapper(new MediaStoreOutputStream(context, documentFile), i);
        }
        if (BuildCheck.isAPI26()) {
            if (USE_MEDIASTORE_OUTPUT_STREAM) {
                return new MediaMuxerWrapper(new MediaStoreOutputStream(context, documentFile), i);
            }
            return new MediaMuxerWrapper(context.getContentResolver().openFileDescriptor(uri, "rw").getFileDescriptor(), i);
        }
        String path = UriHelper.getPath(context, uri);
        if (new File(path).canWrite()) {
            return new MediaMuxerWrapper(path, i);
        }
        return null;
    }

    public MediaMuxerWrapper(String str, int i) throws IOException {
        this.mMuxer = new MediaMuxer(str, i);
        this.mOutputStream = null;
        this.mOutputPath = str;
    }

    public MediaMuxerWrapper(FileDescriptor fileDescriptor, int i) throws IOException {
        this.mMuxer = new MediaMuxer(fileDescriptor, i);
        this.mOutputStream = null;
        this.mOutputPath = null;
    }

    public MediaMuxerWrapper(FileOutputStream fileOutputStream, int i) throws IOException {
        this.mMuxer = new MediaMuxer(fileOutputStream.getFD(), i);
        this.mOutputStream = fileOutputStream;
        this.mOutputPath = null;
    }

    public MediaMuxerWrapper(MediaStoreOutputStream mediaStoreOutputStream, int i) throws IOException {
        this.mMuxer = new MediaMuxer(mediaStoreOutputStream.getFd(), i);
        this.mOutputStream = mediaStoreOutputStream;
        this.mOutputPath = mediaStoreOutputStream.getOutputPath();
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    @Deprecated
    public String getOutputPath() {
        return this.mOutputPath;
    }

    @Override // com.serenegiant.media.IMuxer
    public int addTrack(MediaFormat mediaFormat) {
        return this.mMuxer.addTrack(mediaFormat);
    }

    @Override // com.serenegiant.media.IMuxer
    public void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.mReleased) {
            return;
        }
        this.mMuxer.writeSampleData(i, byteBuffer, bufferInfo);
    }

    @Override // com.serenegiant.media.IMuxer
    public void start() {
        this.mMuxer.start();
        this.mIsStarted = true;
    }

    @Override // com.serenegiant.media.IMuxer
    public void stop() {
        if (this.mIsStarted) {
            this.mIsStarted = false;
            this.mMuxer.stop();
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public void release() {
        this.mIsStarted = false;
        if (this.mReleased) {
            return;
        }
        this.mReleased = true;
        try {
            this.mMuxer.release();
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        try {
            OutputStream outputStream = this.mOutputStream;
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e2) {
            Log.w(TAG, e2);
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public boolean isStarted() {
        return this.mIsStarted && !this.mReleased;
    }
}
