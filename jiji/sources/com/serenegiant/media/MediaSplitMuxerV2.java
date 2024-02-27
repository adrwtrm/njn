package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.media.IMuxer;
import com.serenegiant.mediastore.MediaStoreUtils;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.PermissionUtils;
import com.serenegiant.utils.FileUtils;
import com.serenegiant.utils.UriHelper;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MediaSplitMuxerV2 implements IMuxer {
    private static final boolean DEBUG = false;
    private static final String DEFAULT_PREFIX_SEGMENT_NAME = "";
    private static final long DEFAULT_SPLIT_SIZE = 4000000000L;
    private static final String EXT_MP4 = "mp4";
    private static final int INI_POOL_NUM = 4;
    private static final long MAX_CHECK_INTERVALS_NS = 3000000000L;
    private static final int MAX_POOL_NUM = 1000;
    private static final long MIN_FREE_SPACE = 16777216;
    public static String PREFIX_SEGMENT_NAME = "";
    private static final long STORAGE_SIZE_LIMIT = 4294967296L;
    private static final String TAG = "MediaSplitMuxerV2";
    private DocumentFile mCurrent;
    private volatile boolean mIsRunning;
    private MuxTask mMuxTask;
    private IMuxer mMuxer;
    private final IMuxer.IMuxerFactory mMuxerFactory;
    private final DocumentFile mOutputDir;
    private final String mOutputDirName;
    private final IMediaQueue<RecycleMediaData> mQueue;
    private boolean mReleased;
    private volatile boolean mRequestStop;
    private final String mSegmentPrefix;
    private final long mSplitSize;
    private final VideoConfig mVideoConfig;
    private final WeakReference<Context> mWeakContext;
    private final Object mSync = new Object();
    private final MediaFormat[] mMediaFormats = new MediaFormat[2];
    private int mVideoTrackIx = -1;
    private int mAudioTrackIx = -1;
    private int mLastTrackIndex = -1;

    public MediaSplitMuxerV2(Context context, DocumentFile documentFile, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, IMediaQueue<RecycleMediaData> iMediaQueue, long j) throws IOException {
        this.mWeakContext = new WeakReference<>(context);
        this.mVideoConfig = videoConfig == null ? new VideoConfig() : videoConfig;
        this.mMuxerFactory = iMuxerFactory == null ? new IMuxer.DefaultFactory() : iMuxerFactory;
        this.mQueue = iMediaQueue == null ? new MemMediaQueue(4, 1000) : iMediaQueue;
        this.mSplitSize = j <= 0 ? DEFAULT_SPLIT_SIZE : j;
        String str = PREFIX_SEGMENT_NAME;
        this.mSegmentPrefix = str == null ? "" : str;
        String dateTimeString = FileUtils.getDateTimeString();
        this.mOutputDirName = dateTimeString;
        if (documentFile != null) {
            this.mOutputDir = documentFile;
        } else if (!BuildCheck.isAPI29() && PermissionUtils.hasWriteExternalStorage(context)) {
            File captureDir = FileUtils.getCaptureDir(context, Environment.DIRECTORY_MOVIES);
            if (captureDir != null) {
                File file = new File(captureDir, dateTimeString);
                file.mkdirs();
                this.mOutputDir = DocumentFile.fromFile(file);
            } else {
                this.mOutputDir = null;
            }
        } else {
            this.mOutputDir = null;
        }
        this.mMuxer = createMuxer(0);
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
                if (this.mIsRunning && !this.mRequestStop) {
                    stop();
                }
                this.mIsRunning = false;
                this.mQueue.clear();
            }
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
    public synchronized void start() throws IllegalStateException {
        if (!this.mReleased && !this.mIsRunning) {
            MediaFormat[] mediaFormatArr = this.mMediaFormats;
            if (mediaFormatArr[0] == null && mediaFormatArr[1] == null) {
                throw new IllegalStateException("no added track");
            }
            this.mIsRunning = true;
            this.mRequestStop = false;
            this.mMuxTask = new MuxTask();
            new Thread(this.mMuxTask, "MuxTask").start();
        } else {
            throw new IllegalStateException("already released or started");
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public synchronized void stop() {
        synchronized (this.mSync) {
            this.mRequestStop = true;
            this.mMuxTask = null;
            this.mAudioTrackIx = -1;
            this.mVideoTrackIx = -1;
            this.mLastTrackIndex = -1;
            MediaFormat[] mediaFormatArr = this.mMediaFormats;
            mediaFormatArr[1] = null;
            mediaFormatArr[0] = null;
        }
    }

    @Override // com.serenegiant.media.IMuxer
    public int addTrack(MediaFormat mediaFormat) throws IllegalArgumentException, IllegalStateException {
        int addTrack;
        int i = this.mLastTrackIndex + 1;
        if (i == 0 || i == 1) {
            if (mediaFormat.containsKey("mime")) {
                String string = mediaFormat.getString("mime");
                if (string.startsWith("video/")) {
                    addTrack = this.mMuxer.addTrack(mediaFormat);
                    this.mVideoTrackIx = addTrack;
                    this.mMediaFormats[addTrack] = mediaFormat;
                } else if (string.startsWith("audio/")) {
                    addTrack = this.mMuxer.addTrack(mediaFormat);
                    this.mAudioTrackIx = addTrack;
                    this.mMediaFormats[addTrack] = mediaFormat;
                } else {
                    throw new IllegalArgumentException("un-expected mime type");
                }
                this.mLastTrackIndex++;
                return addTrack;
            }
            throw new IllegalArgumentException("has no mime type");
        }
        throw new IllegalArgumentException();
    }

    @Override // com.serenegiant.media.IMuxer
    public void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        RecycleMediaData obtain;
        if (this.mRequestStop || i > this.mLastTrackIndex || (obtain = this.mQueue.obtain(new Object[0])) == null) {
            return;
        }
        byteBuffer.clear();
        obtain.set(i, byteBuffer, bufferInfo);
        this.mQueue.queueFrame(obtain);
    }

    protected void internalWriteSampleData(IMuxer iMuxer, int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        iMuxer.writeSampleData(i, byteBuffer, bufferInfo);
    }

    protected Context getContext() {
        return this.mWeakContext.get();
    }

    protected Context requireContext() throws IllegalStateException {
        Context context = this.mWeakContext.get();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException();
    }

    public VideoConfig getConfig() {
        return this.mVideoConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class MuxTask implements Runnable {
        private MuxTask() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x006b, code lost:
            if (r0.flags == 1) goto L37;
         */
        /* JADX WARN: Removed duplicated region for block: B:93:0x012f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 325
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.MediaSplitMuxerV2.MuxTask.run():void");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0018 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean checkFreespace() {
        /*
            r6 = this;
            androidx.documentfile.provider.DocumentFile r0 = r6.mCurrent
            if (r0 == 0) goto L15
            android.content.Context r0 = r6.requireContext()     // Catch: java.io.IOException -> Lf
            androidx.documentfile.provider.DocumentFile r1 = r6.mCurrent     // Catch: java.io.IOException -> Lf
            com.serenegiant.system.StorageInfo r0 = com.serenegiant.system.StorageUtils.getStorageInfo(r0, r1)     // Catch: java.io.IOException -> Lf
            goto L16
        Lf:
            r0 = move-exception
            java.lang.String r1 = com.serenegiant.media.MediaSplitMuxerV2.TAG
            android.util.Log.w(r1, r0)
        L15:
            r0 = 0
        L16:
            if (r0 != 0) goto L29
            android.content.Context r1 = r6.getContext()     // Catch: java.io.IOException -> L23
            java.lang.String r2 = android.os.Environment.DIRECTORY_MOVIES     // Catch: java.io.IOException -> L23
            com.serenegiant.system.StorageInfo r0 = com.serenegiant.system.StorageUtils.getStorageInfo(r1, r2)     // Catch: java.io.IOException -> L23
            goto L29
        L23:
            r1 = move-exception
            java.lang.String r2 = com.serenegiant.media.MediaSplitMuxerV2.TAG
            android.util.Log.w(r2, r1)
        L29:
            r1 = 0
            if (r0 == 0) goto L3f
            long r3 = r0.totalBytes
            int r3 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r3 <= 0) goto L3f
            long r1 = r0.freeBytes
            float r1 = (float) r1
            long r2 = r0.totalBytes
            float r2 = (float) r2
            float r1 = r1 / r2
            long r2 = r0.totalBytes
            long r4 = r0.freeBytes
            goto L43
        L3f:
            r0 = 0
            r4 = r1
            r1 = r0
            r2 = r4
        L43:
            float r0 = com.serenegiant.utils.FileUtils.FREE_RATIO
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 >= 0) goto L52
            r0 = 4294967296(0x100000000, double:2.121995791E-314)
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 < 0) goto L59
        L52:
            r0 = 16777216(0x1000000, double:8.289046E-317)
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 >= 0) goto L5b
        L59:
            r0 = 1
            goto L5c
        L5b:
            r0 = 0
        L5c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.MediaSplitMuxerV2.checkFreespace():boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IMuxer restartMuxer(IMuxer iMuxer, int i) throws IOException {
        try {
            iMuxer.stop();
            iMuxer.release();
            return setupMuxer(i);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IMuxer setupMuxer(int i) throws IOException {
        int i2;
        IMuxer createMuxer = createMuxer(i);
        synchronized (this.mSync) {
            i2 = 0;
            MediaFormat mediaFormat = this.mMediaFormats[0];
            if (mediaFormat != null) {
                createMuxer.addTrack(mediaFormat);
                i2 = 1;
            }
            MediaFormat mediaFormat2 = this.mMediaFormats[1];
            if (mediaFormat2 != null) {
                createMuxer.addTrack(mediaFormat2);
                i2++;
            }
        }
        if (i2 > 0) {
            createMuxer.start();
            return createMuxer;
        }
        throw new IOException("already released?");
    }

    private IMuxer createMuxer(int i) throws IOException {
        if (this.mCurrent != null) {
            Context requireContext = requireContext();
            if (BuildCheck.isAPI29()) {
                MediaStoreUtils.updateContentUri(requireContext, this.mCurrent);
            } else if (UriHelper.isFileUri(this.mCurrent)) {
                try {
                    MediaScannerConnection.scanFile(requireContext, new String[]{UriHelper.getPath(requireContext, this.mCurrent.getUri())}, null, null);
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
            }
        }
        this.mCurrent = createOutputDoc(i);
        return createMuxer(requireContext(), this.mCurrent);
    }

    protected DocumentFile createOutputDoc(int i) throws IOException {
        File captureDir;
        String format = String.format(Locale.US, "%s%04d.%s", this.mSegmentPrefix, Integer.valueOf(i + 1), EXT_MP4);
        Context requireContext = requireContext();
        DocumentFile documentFile = this.mOutputDir;
        if (documentFile != null) {
            if (!documentFile.isDirectory()) {
                this.mOutputDir.getParentFile();
            }
            return this.mOutputDir.createFile(null, format);
        } else if (BuildCheck.isAPI29()) {
            return MediaStoreUtils.getContentDocument(requireContext, "video/mp4", Environment.DIRECTORY_MOVIES + "/" + FileUtils.getDirName() + "/" + this.mOutputDirName, format, null);
        } else {
            if (PermissionUtils.hasWriteExternalStorage(requireContext) && (captureDir = FileUtils.getCaptureDir(requireContext, Environment.DIRECTORY_MOVIES)) != null) {
                File file = new File(captureDir, this.mOutputDirName);
                file.mkdirs();
                return DocumentFile.fromFile(file).createFile(null, format);
            }
            throw new IOException("Failed to create output DocumentFile");
        }
    }

    private IMuxer createMuxer(Context context, DocumentFile documentFile) throws IOException {
        IMuxer createMuxer = this.mMuxerFactory.createMuxer(context, getConfig().useMediaMuxer(), documentFile);
        if (createMuxer != null) {
            return createMuxer;
        }
        throw new IOException("Failed to create muxer");
    }
}
