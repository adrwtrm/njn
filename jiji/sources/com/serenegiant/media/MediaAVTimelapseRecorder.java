package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.media.IMuxer;
import com.serenegiant.media.IRecorder;
import com.serenegiant.system.StorageInfo;
import com.serenegiant.system.StorageUtils;
import com.serenegiant.utils.FileUtils;
import com.serenegiant.utils.UriHelper;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MediaAVTimelapseRecorder extends Recorder {
    private static final boolean DEBUG = true;
    private static final long DEFAULT_FRAME_INTERVALS_US = 33333;
    private static final String TAG = "MediaAVTimelapseRecorder";
    private long mFrameCounts;
    private long mFrameIntervalsUs;
    private final DocumentFile mOutputFile;
    private final String mOutputPath;

    public MediaAVTimelapseRecorder(Context context, IRecorder.RecorderCallback recorderCallback, DocumentFile documentFile) throws IOException {
        this(context, recorderCallback, null, null, documentFile);
    }

    public MediaAVTimelapseRecorder(Context context, IRecorder.RecorderCallback recorderCallback, IMuxer.IMuxerFactory iMuxerFactory, DocumentFile documentFile) throws IOException {
        this(context, recorderCallback, null, iMuxerFactory, documentFile);
    }

    public MediaAVTimelapseRecorder(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, DocumentFile documentFile) throws IOException {
        super(context, recorderCallback, videoConfig, iMuxerFactory);
        this.mFrameIntervalsUs = DEFAULT_FRAME_INTERVALS_US;
        this.mOutputFile = documentFile;
        this.mOutputPath = UriHelper.getPath(context, documentFile.getUri());
        VideoConfig config = getConfig();
        if (config.getCaptureFps().asDouble() >= 0.0d) {
            this.mFrameIntervalsUs = Math.round(1000000.0d / config.getCaptureFps().asDouble());
        }
        if (this.mFrameIntervalsUs < 0) {
            this.mFrameIntervalsUs = DEFAULT_FRAME_INTERVALS_US;
        }
        setMuxer(getMuxerFactory().createMuxer(context, getConfig().useMediaMuxer(), documentFile));
    }

    @Override // com.serenegiant.media.Recorder, com.serenegiant.media.IRecorder
    public synchronized void addEncoder(Encoder encoder) throws UnsupportedOperationException {
        if ((encoder instanceof IAudioEncoder) || encoder.isAudio()) {
            throw new UnsupportedOperationException("MediaAVTimelapseRecorder only support video encoder!");
        }
        super.addEncoder(encoder);
    }

    @Override // com.serenegiant.media.IRecorder
    @Deprecated
    public String getOutputPath() {
        return this.mOutputPath;
    }

    @Override // com.serenegiant.media.IRecorder
    public DocumentFile getOutputFile() {
        return this.mOutputFile;
    }

    @Override // com.serenegiant.media.Recorder
    protected boolean check() {
        Context requireContext = requireContext();
        try {
            StorageInfo storageInfo = StorageUtils.getStorageInfo(requireContext, this.mOutputFile);
            if (storageInfo.totalBytes != 0) {
                if (((float) storageInfo.freeBytes) / ((float) storageInfo.totalBytes) >= FileUtils.FREE_RATIO) {
                    if (((float) storageInfo.freeBytes) >= FileUtils.FREE_SIZE) {
                        return false;
                    }
                }
                return true;
            }
        } catch (IOException e) {
            Log.w(TAG, e);
        }
        return requireContext == null || !FileUtils.checkFreeSpace(requireContext, getConfig().maxDuration(), this.mStartTime, 0);
    }

    @Override // com.serenegiant.media.Recorder, com.serenegiant.media.IRecorder
    public void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        bufferInfo.presentationTimeUs = getInputPTSUs(i);
        super.writeSampleData(i, byteBuffer, bufferInfo);
    }

    private long getInputPTSUs(int i) {
        if (this.mFrameCounts % 100 == 0) {
            Log.v(TAG, "getInputPTSUs:" + this.mFrameCounts);
        }
        if (this.mFrameIntervalsUs <= 0) {
            this.mFrameIntervalsUs = DEFAULT_FRAME_INTERVALS_US;
        }
        long j = this.mFrameCounts;
        this.mFrameCounts = 1 + j;
        return j * this.mFrameIntervalsUs;
    }
}
