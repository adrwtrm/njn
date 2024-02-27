package com.serenegiant.media;

import android.content.Context;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.media.IMuxer;
import com.serenegiant.media.IRecorder;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MediaAVSplitRecorderV2 extends Recorder {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaAVSplitRecorderV2";

    @Override // com.serenegiant.media.Recorder
    public boolean check() {
        return false;
    }

    @Override // com.serenegiant.media.IRecorder
    public DocumentFile getOutputFile() {
        return null;
    }

    public MediaAVSplitRecorderV2(Context context, IRecorder.RecorderCallback recorderCallback, DocumentFile documentFile, long j) throws IOException {
        this(context, recorderCallback, null, null, null, documentFile, j);
    }

    public MediaAVSplitRecorderV2(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, IMediaQueue<RecycleMediaData> iMediaQueue, DocumentFile documentFile, long j) throws IOException {
        super(context, recorderCallback, videoConfig, iMuxerFactory);
        setMuxer(new MediaSplitMuxerV2(context, documentFile, getConfig(), getMuxerFactory(), iMediaQueue, j));
    }

    @Override // com.serenegiant.media.IRecorder
    @Deprecated
    public String getOutputPath() {
        throw new UnsupportedOperationException("");
    }
}
