package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MediaRawFileMuxer implements IPostMuxer {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaRawFileMuxer";
    private MediaRawFileWriter mAudioWriter;
    private final MediaFormat mConfigFormatAudio;
    private final MediaFormat mConfigFormatVideo;
    private volatile boolean mIsRunning;
    private int mLastTrackIndex;
    private final MediaRawFileWriter[] mMediaRawFileWriters;
    private final DocumentFile mOutputDoc;
    @Deprecated
    private final String mOutputPath;
    private boolean mReleased;
    private final Object mSync;
    private final String mTempName;
    private final VideoConfig mVideoConfig;
    private MediaRawFileWriter mVideoWriter;
    private final WeakReference<Context> mWeakContext;

    @Deprecated
    public MediaRawFileMuxer(Context context, VideoConfig videoConfig, String str, MediaFormat mediaFormat, MediaFormat mediaFormat2) {
        this.mSync = new Object();
        this.mLastTrackIndex = -1;
        this.mMediaRawFileWriters = new MediaRawFileWriter[2];
        this.mWeakContext = new WeakReference<>(context);
        this.mVideoConfig = videoConfig == null ? new VideoConfig() : videoConfig;
        this.mOutputPath = str;
        this.mOutputDoc = null;
        this.mTempName = FileUtils.getDateTimeString();
        this.mConfigFormatVideo = mediaFormat;
        this.mConfigFormatAudio = mediaFormat2;
    }

    public MediaRawFileMuxer(Context context, VideoConfig videoConfig, DocumentFile documentFile, MediaFormat mediaFormat, MediaFormat mediaFormat2) {
        this.mSync = new Object();
        this.mLastTrackIndex = -1;
        this.mMediaRawFileWriters = new MediaRawFileWriter[2];
        this.mWeakContext = new WeakReference<>(context);
        this.mVideoConfig = videoConfig == null ? new VideoConfig() : videoConfig;
        this.mOutputPath = null;
        this.mOutputDoc = documentFile;
        this.mTempName = FileUtils.getDateTimeString();
        this.mConfigFormatVideo = mediaFormat;
        this.mConfigFormatAudio = mediaFormat2;
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public void release() {
        synchronized (this.mSync) {
            if (!this.mReleased) {
                this.mReleased = true;
                MediaRawFileWriter mediaRawFileWriter = this.mVideoWriter;
                if (mediaRawFileWriter != null) {
                    mediaRawFileWriter.release();
                    this.mVideoWriter = null;
                }
                MediaRawFileWriter mediaRawFileWriter2 = this.mAudioWriter;
                if (mediaRawFileWriter2 != null) {
                    mediaRawFileWriter2.release();
                    this.mAudioWriter = null;
                }
                MediaRawFileWriter[] mediaRawFileWriterArr = this.mMediaRawFileWriters;
                mediaRawFileWriterArr[1] = null;
                mediaRawFileWriterArr[0] = null;
            }
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public void start() {
        synchronized (this.mSync) {
            checkReleased();
            if (this.mIsRunning) {
                throw new IllegalStateException("already started");
            }
            if (this.mLastTrackIndex < 0) {
                throw new IllegalStateException("no track added");
            }
            this.mIsRunning = true;
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public void stop() {
        synchronized (this.mSync) {
            this.mIsRunning = false;
            this.mLastTrackIndex = 0;
        }
    }

    @Override // com.serenegiant.media.IPostMuxer
    public void build() throws IOException {
        Context context = getContext();
        String tempDir = getTempDir();
        if (!TextUtils.isEmpty(this.mOutputPath)) {
            try {
                new PostMuxBuilder(this.mVideoConfig.useMediaMuxer()).buildFromRawFile(context, tempDir, this.mOutputPath);
                try {
                    MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{this.mOutputPath}, null, null);
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
            } finally {
            }
        } else if (this.mOutputDoc != null) {
            try {
                new PostMuxBuilder(this.mVideoConfig.useMediaMuxer()).buildFromRawFile(context, tempDir, this.mOutputDoc);
            } finally {
            }
        } else {
            throw new IOException("unexpected output file");
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public boolean isStarted() {
        boolean z;
        synchronized (this.mSync) {
            z = !this.mReleased && this.mIsRunning;
        }
        return z;
    }

    @Override // com.serenegiant.media.IMuxer
    public int addTrack(MediaFormat mediaFormat) throws IllegalArgumentException, IllegalStateException {
        checkReleased();
        if (this.mIsRunning) {
            throw new IllegalStateException("already started");
        }
        Context context = getContext();
        String tempDir = getTempDir();
        String string = mediaFormat.containsKey("mime") ? mediaFormat.getString("mime") : null;
        if (!TextUtils.isEmpty(string)) {
            synchronized (this.mSync) {
                int i = this.mLastTrackIndex + 1;
                if (string.startsWith("video/")) {
                    if (this.mVideoWriter == null) {
                        try {
                            MediaRawFileWriter[] mediaRawFileWriterArr = this.mMediaRawFileWriters;
                            MediaFormat mediaFormat2 = this.mConfigFormatVideo;
                            if (mediaFormat2 == null) {
                                mediaFormat2 = mediaFormat;
                            }
                            MediaRawFileWriter newInstance = MediaRawFileWriter.newInstance(context, 0, mediaFormat2, mediaFormat, tempDir);
                            this.mVideoWriter = newInstance;
                            mediaRawFileWriterArr[i] = newInstance;
                            this.mLastTrackIndex = i;
                            return i;
                        } catch (IOException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                    throw new IllegalArgumentException("Video track is already added");
                } else if (string.startsWith("audio/")) {
                    if (this.mAudioWriter == null) {
                        try {
                            MediaRawFileWriter[] mediaRawFileWriterArr2 = this.mMediaRawFileWriters;
                            MediaFormat mediaFormat3 = this.mConfigFormatAudio;
                            if (mediaFormat3 == null) {
                                mediaFormat3 = mediaFormat;
                            }
                            MediaRawFileWriter newInstance2 = MediaRawFileWriter.newInstance(context, 1, mediaFormat3, mediaFormat, tempDir);
                            this.mAudioWriter = newInstance2;
                            mediaRawFileWriterArr2[i] = newInstance2;
                            this.mLastTrackIndex = i;
                            return i;
                        } catch (IOException e2) {
                            throw new IllegalArgumentException(e2);
                        }
                    }
                    throw new IllegalArgumentException("Audio track is already added");
                } else {
                    throw new IllegalArgumentException("Unexpected mime type=" + string);
                }
            }
        }
        throw new IllegalArgumentException("Mime is null");
    }

    @Override // com.serenegiant.media.IMuxer
    public void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        MediaRawFileWriter mediaRawFileWriter;
        checkReleased();
        if (!this.mIsRunning) {
            throw new IllegalStateException("Can't write, muxer is not started");
        }
        if (i < 0 || i > this.mLastTrackIndex) {
            throw new IllegalArgumentException("Invalid trackIndex=" + i);
        }
        if (bufferInfo.size < 0 || bufferInfo.offset < 0 || bufferInfo.offset + bufferInfo.size > byteBuffer.capacity() || bufferInfo.presentationTimeUs < 0) {
            throw new IllegalArgumentException("bufferInfo must specify a valid buffer offset, size and presentation time");
        }
        synchronized (this.mSync) {
            mediaRawFileWriter = this.mMediaRawFileWriters[i];
        }
        if (mediaRawFileWriter != null) {
            try {
                mediaRawFileWriter.writeSampleData(byteBuffer, bufferInfo);
            } catch (IOException e) {
                Log.w(TAG, e);
            }
        }
    }

    protected Context getContext() {
        return this.mWeakContext.get();
    }

    private void checkReleased() throws IllegalStateException {
        synchronized (this.mSync) {
            if (this.mReleased) {
                throw new IllegalStateException("already released");
            }
        }
    }

    private String getTempDir() {
        try {
            return getContext().getDir(this.mTempName, 0).getAbsolutePath();
        } catch (Exception e) {
            Log.w(TAG, e);
            return new File(Environment.getDataDirectory(), this.mTempName).getAbsolutePath();
        }
    }

    private static final void delete(File file) {
        if (file != null) {
            try {
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    int length = listFiles != null ? listFiles.length : 0;
                    for (int i = 0; i < length; i++) {
                        delete(listFiles[i]);
                    }
                }
                file.delete();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }
}
