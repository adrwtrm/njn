package com.serenegiant.media;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.media.IMuxer;
import com.serenegiant.media.IRecorder;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.SAFUtils;
import com.serenegiant.system.StorageInfo;
import com.serenegiant.system.StorageUtils;
import com.serenegiant.utils.FileUtils;
import com.serenegiant.utils.UriHelper;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MediaAVRecorder extends Recorder {
    private static final String TAG = "MediaAVRecorder";
    protected DocumentFile mOutputFile;
    protected String mOutputPath;
    protected final int mSaveTreeId;

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, String str, int i) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, (IMuxer.IMuxerFactory) null, (String) null, str, i);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, IMuxer.IMuxerFactory iMuxerFactory, String str, int i) throws IOException {
        this(context, recorderCallback, (IMuxer.IMuxerFactory) null, (String) null, str, i);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, String str, String str2, int i) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, (IMuxer.IMuxerFactory) null, str, str2, i);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, IMuxer.IMuxerFactory iMuxerFactory, String str, String str2, int i) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, iMuxerFactory, str, str2, i);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, int i, String str, String str2) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, (IMuxer.IMuxerFactory) null, i, str, str2);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, IMuxer.IMuxerFactory iMuxerFactory, int i, String str, String str2) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, iMuxerFactory, i, str, str2);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, String str) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, (IMuxer.IMuxerFactory) null, str);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, IMuxer.IMuxerFactory iMuxerFactory, String str) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, iMuxerFactory, str);
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, String str, String str2, int i) throws IOException {
        super(context, recorderCallback, videoConfig, iMuxerFactory);
        String str3;
        this.mSaveTreeId = i;
        str2 = TextUtils.isEmpty(str2) ? ".mp4" : str2;
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            str3 = FileUtils.getDateTimeString();
        } else {
            str3 = str + FileUtils.getDateTimeString();
        }
        String sb2 = sb.append(str3).append(str2).toString();
        if (BuildCheck.isAPI21() && i != 0 && SAFUtils.hasPermission(context, i)) {
            setupMuxer(context, SAFUtils.getFile(context, i, null, "video/*", sb2));
            return;
        }
        try {
            String file = FileUtils.getCaptureFile(context, Environment.DIRECTORY_MOVIES, str, str2).toString();
            this.mOutputPath = file;
            if (file == null) {
                throw new IOException("This app has no permission of writing external storage");
            }
            setupMuxer(context, DocumentFile.fromFile(new File(this.mOutputPath)));
        } catch (Exception unused) {
            throw new IOException("This app has no permission of writing external storage");
        }
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, int i, String str, String str2) throws IOException {
        super(context, recorderCallback, videoConfig, iMuxerFactory);
        DocumentFile file;
        this.mSaveTreeId = i;
        if (i > 0 && SAFUtils.hasPermission(context, i) && (file = SAFUtils.getFile(context, i, str, "*/*", str2)) != null) {
            this.mOutputPath = UriHelper.getPath(context, file.getUri());
            setupMuxer(context, file);
        }
        throw new IOException("path not found/can't write");
    }

    @Deprecated
    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, String str) throws IOException {
        super(context, recorderCallback, videoConfig, iMuxerFactory);
        this.mSaveTreeId = 0;
        this.mOutputPath = str;
        if (TextUtils.isEmpty(str)) {
            try {
                this.mOutputPath = FileUtils.getCaptureFile(context, Environment.DIRECTORY_MOVIES, (String) null, ".mp4").toString();
            } catch (Exception unused) {
                throw new IOException("This app has no permission of writing external storage");
            }
        }
        setupMuxer(context, DocumentFile.fromFile(new File(this.mOutputPath)));
    }

    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, DocumentFile documentFile) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, (IMuxer.IMuxerFactory) null, documentFile);
    }

    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, IMuxer.IMuxerFactory iMuxerFactory, DocumentFile documentFile) throws IOException {
        this(context, recorderCallback, (VideoConfig) null, iMuxerFactory, documentFile);
    }

    public MediaAVRecorder(Context context, IRecorder.RecorderCallback recorderCallback, VideoConfig videoConfig, IMuxer.IMuxerFactory iMuxerFactory, DocumentFile documentFile) throws IOException {
        super(context, recorderCallback, videoConfig, iMuxerFactory);
        this.mSaveTreeId = 0;
        this.mOutputFile = documentFile;
        this.mOutputPath = UriHelper.getPath(context, documentFile.getUri());
        setupMuxer(context, documentFile);
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
            DocumentFile documentFile = this.mOutputFile;
            StorageInfo storageInfo = documentFile != null ? StorageUtils.getStorageInfo(requireContext, documentFile) : null;
            if (storageInfo != null && storageInfo.totalBytes != 0) {
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
        return requireContext == null || (this.mOutputFile == null && !FileUtils.checkFreeSpace(requireContext, getConfig().maxDuration(), this.mStartTime, this.mSaveTreeId));
    }

    @Deprecated
    protected void setupMuxer(int i) throws IOException {
        throw new IOException("Failed to create muxer");
    }

    @Deprecated
    protected void setupMuxer(String str) throws IOException {
        throw new IOException("Failed to create muxer");
    }

    protected void setupMuxer(Context context, DocumentFile documentFile) throws IOException {
        setMuxer(getMuxerFactory().createMuxer(context, getConfig().useMediaMuxer(), documentFile));
    }
}
