package com.serenegiant.system;

import android.content.Context;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.utils.FileUtils;
import com.serenegiant.utils.UriHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class StorageUtils {
    private StorageUtils() {
    }

    public static String getExternalMounts() {
        String[] split;
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
            String str2 = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                } else if (!readLine.contains("secure") && !readLine.contains("asec")) {
                    if (readLine.contains("fat")) {
                        String[] split2 = readLine.split(" ");
                        if (split2 != null && split2.length > 1 && !TextUtils.isEmpty(split2[1])) {
                            str = split2[1];
                            if (!str.endsWith("/")) {
                                str = str + "/";
                            }
                        }
                    } else if (readLine.contains("fuse") && (split = readLine.split(" ")) != null && split.length > 1) {
                        str2 = str2.concat("[" + split[1] + "]");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Deprecated
    public static StorageInfo getStorageInfo(Context context, String str, int i) throws IOException {
        try {
            File captureDir = FileUtils.getCaptureDir(context, str, i);
            if (captureDir != null) {
                return new StorageInfo(captureDir.getTotalSpace(), captureDir.canWrite() ? captureDir.getUsableSpace() : 0L);
            }
        } catch (Exception e) {
            Log.w("getStorageInfo:", e);
        }
        throw new IOException();
    }

    public static StorageInfo getStorageInfo(Context context, String str) throws IOException {
        try {
            File captureDir = FileUtils.getCaptureDir(context, str);
            if (captureDir != null) {
                return new StorageInfo(captureDir.getTotalSpace(), captureDir.canWrite() ? captureDir.getUsableSpace() : 0L);
            }
        } catch (Exception e) {
            Log.w("getStorageInfo:", e);
        }
        throw new IOException();
    }

    public static StorageInfo getStorageInfo(Context context, DocumentFile documentFile) throws IOException {
        try {
            String path = UriHelper.getPath(context, documentFile.getUri());
            if (path != null) {
                File file = new File(path);
                if (file.isDirectory() && file.canRead()) {
                    long totalSpace = file.getTotalSpace();
                    long freeSpace = file.getFreeSpace();
                    if (freeSpace < file.getUsableSpace()) {
                        freeSpace = file.getUsableSpace();
                    }
                    return new StorageInfo(totalSpace, freeSpace);
                }
            }
        } catch (Exception unused) {
        }
        if (BuildCheck.isJellyBeanMR2()) {
            try {
                StatFs statFs = new StatFs(UriHelper.getPath(context, documentFile.getUri()));
                return new StorageInfo(statFs.getTotalBytes(), statFs.getAvailableBytes());
            } catch (Exception unused2) {
            }
        }
        throw new IOException();
    }
}
