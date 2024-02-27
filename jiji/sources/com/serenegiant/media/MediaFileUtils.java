package com.serenegiant.media;

import android.content.Context;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.PermissionUtils;
import com.serenegiant.system.SAFUtils;
import com.serenegiant.utils.FileUtils;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MediaFileUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaFileUtils";

    private MediaFileUtils() {
    }

    public static synchronized DocumentFile getSAFRecordingRoot(Context context, int i) {
        DocumentFile documentFile;
        synchronized (MediaFileUtils.class) {
            documentFile = null;
            if (BuildCheck.isAPI21()) {
                if (SAFUtils.hasPermission(context, i)) {
                    try {
                        DocumentFile dir = SAFUtils.getDir(context, i, FileUtils.DIR_NAME);
                        if (dir.exists() && dir.canWrite()) {
                            documentFile = dir;
                        }
                        Log.d(TAG, "path will be wrong, will already be removed," + (dir != null ? dir.getUri() : null));
                    } catch (IOException | IllegalStateException e) {
                        Log.d(TAG, "path is wrong, will already be removed.", e);
                    }
                }
                if (documentFile == null) {
                    SAFUtils.releasePersistableUriPermission(context, i);
                }
            }
        }
        return documentFile;
    }

    public static synchronized DocumentFile getRecordingRoot(Context context, String str, int i) {
        DocumentFile sAFRecordingRoot;
        File captureDir;
        synchronized (MediaFileUtils.class) {
            sAFRecordingRoot = getSAFRecordingRoot(context, i);
            if (sAFRecordingRoot == null && PermissionUtils.hasWriteExternalStorage(context) && (captureDir = FileUtils.getCaptureDir(context, str)) != null && captureDir.canWrite()) {
                sAFRecordingRoot = DocumentFile.fromFile(captureDir);
            }
        }
        return sAFRecordingRoot;
    }

    public static synchronized DocumentFile getRecordingFile(Context context, int i, String str, String str2, String str3) throws IOException {
        DocumentFile file;
        synchronized (MediaFileUtils.class) {
            DocumentFile recordingRoot = getRecordingRoot(context, str, i);
            if (recordingRoot != null) {
                file = SAFUtils.getFile(recordingRoot, null, str2, FileUtils.getDateTimeString() + str3);
            } else {
                throw new IOException("Failed to get recording root dir");
            }
        }
        return file;
    }
}
