package com.serenegiant.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.serenegiant.media.Recorder;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/* loaded from: classes2.dex */
public class FileUtils {
    private static final boolean DEBUG = false;
    public static String DIR_NAME = null;
    private static final String TAG = "FileUtils";
    private static final SimpleDateFormat sDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
    private static final SimpleDateFormat sTimeFormat = new SimpleDateFormat("HH-mm-ss", Locale.US);
    public static float FREE_RATIO = 0.03f;
    public static float FREE_SIZE_OFFSET = 2.097152E7f;
    public static float FREE_SIZE = 3.145728E8f;
    public static float FREE_SIZE_MINUTE = 4.194304E7f;
    public static long CHECK_INTERVAL = Recorder.CHECK_INTERVAL;

    private FileUtils() {
    }

    public static String getDirName() {
        return TextUtils.isEmpty(DIR_NAME) ? "Serenegiant" : DIR_NAME;
    }

    @Deprecated
    public static final File getCaptureFile(Context context, String str, String str2, int i) {
        return getCaptureFile(context, str, null, str2, i);
    }

    public static final File getCaptureFile(Context context, String str, String str2) {
        return getCaptureFile(context, str, (String) null, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0073  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.io.File getCaptureFile(android.content.Context r2, java.lang.String r3, java.lang.String r4, java.lang.String r5, int r6) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L10
            java.lang.String r4 = getDateTimeString()
            goto L25
        L10:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r4 = r1.append(r4)
            java.lang.String r1 = getDateTimeString()
            java.lang.StringBuilder r4 = r4.append(r1)
            java.lang.String r4 = r4.toString()
        L25:
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            boolean r5 = com.serenegiant.system.BuildCheck.isAPI21()
            r0 = 0
            if (r5 == 0) goto L7d
            if (r6 <= 0) goto L7d
            boolean r5 = com.serenegiant.system.SAFUtils.hasPermission(r2, r6)
            if (r5 == 0) goto L7d
            androidx.documentfile.provider.DocumentFile r5 = com.serenegiant.system.SAFUtils.getDir(r2, r6, r0)     // Catch: java.io.IOException -> L58
            android.net.Uri r5 = r5.getUri()     // Catch: java.io.IOException -> L58
            java.lang.String r5 = com.serenegiant.utils.UriHelper.getPath(r2, r5)     // Catch: java.io.IOException -> L58
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: java.io.IOException -> L58
            if (r6 != 0) goto L5e
            java.io.File r6 = new java.io.File     // Catch: java.io.IOException -> L58
            r6.<init>(r5)     // Catch: java.io.IOException -> L58
            goto L5f
        L58:
            r5 = move-exception
            java.lang.String r6 = com.serenegiant.utils.FileUtils.TAG
            android.util.Log.w(r6, r5)
        L5e:
            r6 = r0
        L5f:
            if (r6 == 0) goto L6a
            boolean r5 = r6.canWrite()
            if (r5 != 0) goto L68
            goto L6a
        L68:
            r0 = r6
            goto L71
        L6a:
            java.lang.String r5 = com.serenegiant.utils.FileUtils.TAG
            java.lang.String r6 = "なんでか書き込めん"
            android.util.Log.w(r5, r6)
        L71:
            if (r0 == 0) goto L7d
            java.io.File r5 = new java.io.File
            java.lang.String r6 = getDirName()
            r5.<init>(r0, r6)
            r0 = r5
        L7d:
            if (r0 != 0) goto L8f
            java.io.File r2 = getCaptureDir(r2, r3)
            if (r2 == 0) goto L8f
            r2.mkdirs()
            boolean r3 = r2.canWrite()
            if (r3 == 0) goto L8f
            r0 = r2
        L8f:
            if (r0 == 0) goto L97
            java.io.File r2 = new java.io.File
            r2.<init>(r0, r4)
            r0 = r2
        L97:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.utils.FileUtils.getCaptureFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String, int):java.io.File");
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x003e, code lost:
        if (r2.canWrite() != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.io.File getCaptureFile(android.content.Context r2, java.lang.String r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L10
            java.lang.String r4 = getDateTimeString()
            goto L25
        L10:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r4 = r1.append(r4)
            java.lang.String r1 = getDateTimeString()
            java.lang.StringBuilder r4 = r4.append(r1)
            java.lang.String r4 = r4.toString()
        L25:
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.io.File r2 = getCaptureDir(r2, r3)
            if (r2 == 0) goto L41
            r2.mkdirs()
            boolean r3 = r2.canWrite()
            if (r3 == 0) goto L41
            goto L42
        L41:
            r2 = 0
        L42:
            if (r2 == 0) goto L4a
            java.io.File r3 = new java.io.File
            r3.<init>(r2, r4)
            r2 = r3
        L4a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.utils.FileUtils.getCaptureFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.io.File");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.io.File getCaptureDir(android.content.Context r2, java.lang.String r3, int r4) {
        /*
            boolean r0 = com.serenegiant.system.BuildCheck.isAPI21()
            r1 = 0
            if (r0 == 0) goto L2d
            if (r4 == 0) goto L2d
            boolean r0 = com.serenegiant.system.SAFUtils.hasPermission(r2, r4)
            if (r0 == 0) goto L2d
            androidx.documentfile.provider.DocumentFile r4 = com.serenegiant.system.SAFUtils.getDir(r2, r4, r1)     // Catch: java.io.IOException -> L27
            android.net.Uri r4 = r4.getUri()     // Catch: java.io.IOException -> L27
            java.lang.String r2 = com.serenegiant.utils.UriHelper.getPath(r2, r4)     // Catch: java.io.IOException -> L27
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch: java.io.IOException -> L27
            if (r4 != 0) goto L2d
            java.io.File r4 = new java.io.File     // Catch: java.io.IOException -> L27
            r4.<init>(r2)     // Catch: java.io.IOException -> L27
            goto L2e
        L27:
            r2 = move-exception
            java.lang.String r4 = com.serenegiant.utils.FileUtils.TAG
            android.util.Log.w(r4, r2)
        L2d:
            r4 = r1
        L2e:
            boolean r2 = com.serenegiant.utils.UriHelper.isStandardDirectory(r3)
            if (r2 == 0) goto L58
            if (r4 == 0) goto L40
            java.io.File r2 = new java.io.File
            java.lang.String r3 = getDirName()
            r2.<init>(r4, r3)
            goto L4d
        L40:
            java.io.File r2 = new java.io.File
            java.io.File r3 = android.os.Environment.getExternalStoragePublicDirectory(r3)
            java.lang.String r4 = getDirName()
            r2.<init>(r3, r4)
        L4d:
            r2.mkdirs()
            boolean r3 = r2.canWrite()
            if (r3 == 0) goto L57
            return r2
        L57:
            return r1
        L58:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r4 = " is not a standard directory name!"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.utils.FileUtils.getCaptureDir(android.content.Context, java.lang.String, int):java.io.File");
    }

    public static File getCaptureDir(Context context, String str) {
        if (!UriHelper.isStandardDirectory(str)) {
            throw new IllegalArgumentException(str + " is not a standard directory name!");
        }
        String dirName = getDirName();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(str);
        if (externalStoragePublicDirectory.canWrite()) {
            externalStoragePublicDirectory.mkdirs();
        }
        if (TextUtils.isEmpty(dirName)) {
            return externalStoragePublicDirectory;
        }
        boolean canWrite = externalStoragePublicDirectory.canWrite();
        File file = new File(externalStoragePublicDirectory, dirName);
        if (canWrite) {
            file.mkdirs();
        }
        return file;
    }

    public static final String getDateTimeString() {
        return sDateTimeFormat.format(new GregorianCalendar().getTime());
    }

    public static final String getTimeString() {
        return sTimeFormat.format(new GregorianCalendar().getTime());
    }

    @Deprecated
    public static final boolean checkFreeSpace(Context context, long j, long j2, int i) {
        float f;
        if (context == null) {
            return false;
        }
        float f2 = FREE_RATIO;
        if (j > 0) {
            f = ((((float) (j - (System.currentTimeMillis() - j2))) / 60000.0f) * FREE_SIZE_MINUTE) + FREE_SIZE_OFFSET;
        } else {
            f = FREE_SIZE;
        }
        return checkFreeSpace(context, f2, f, i);
    }

    @Deprecated
    public static final boolean checkFreeSpace(Context context, float f, float f2, int i) {
        if (context == null) {
            return false;
        }
        try {
            File captureDir = getCaptureDir(context, Environment.DIRECTORY_DCIM, i);
            if (captureDir != null) {
                float usableSpace = captureDir.canWrite() ? (float) captureDir.getUsableSpace() : 0.0f;
                if (captureDir.getTotalSpace() > 0) {
                    return usableSpace / ((float) captureDir.getTotalSpace()) > f || usableSpace > f2;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            Log.w("checkFreeSpace:", e);
            return false;
        }
    }

    @Deprecated
    public static final long getAvailableFreeSpace(Context context, String str, int i) {
        File captureDir;
        if (context == null || (captureDir = getCaptureDir(context, str, i)) == null || !captureDir.canWrite()) {
            return 0L;
        }
        return captureDir.getUsableSpace();
    }

    @Deprecated
    public static final float getFreeRatio(Context context, String str, int i) {
        File captureDir;
        if (context != null && (captureDir = getCaptureDir(context, str, i)) != null) {
            float usableSpace = captureDir.canWrite() ? (float) captureDir.getUsableSpace() : 0.0f;
            if (captureDir.getTotalSpace() > 0) {
                return usableSpace / ((float) captureDir.getTotalSpace());
            }
        }
        return 0.0f;
    }

    public static final String removeFileExtension(String str) {
        int lastIndexOf = !TextUtils.isEmpty(str) ? str.lastIndexOf(".") : -1;
        return lastIndexOf > 0 ? str.substring(0, lastIndexOf) : str;
    }

    public static final String replaceFileExtension(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf > 0) {
            return str.substring(0, lastIndexOf) + str2;
        }
        return str + str2;
    }

    public static String getExt(String str) {
        int lastIndexOf;
        return (TextUtils.isEmpty(str) || (lastIndexOf = str.lastIndexOf(".")) <= 0) ? "" : str.substring(lastIndexOf + 1);
    }

    public static boolean hasExt(String str) {
        return !TextUtils.isEmpty(getExt(str));
    }

    public static String getDisplayName(String str) {
        return removeFileExtension(new File(str).getName());
    }

    public static void deleteAll(File file) throws IOException {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                throw new IllegalArgumentException("not a directory:" + file);
            }
            if (listFiles.length > 0) {
                for (File file2 : listFiles) {
                    deleteAll(file2);
                }
            }
            if (!file.delete()) {
                throw new IOException("failed to delete directory:" + file);
            }
        } else if (!file.delete()) {
            throw new IOException("failed to delete file:" + file);
        }
    }
}
