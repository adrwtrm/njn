package com.epson.iprojection.ui.activities.start;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.PathGetter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class IntentStreamFile {
    private static final String CONTENT_PREF = "content://";
    private static final String[] DIR_NAME;
    private static final String FILE_NAME = "catchIntentFile";
    public static final int NOT_MATCH = -1;
    private static int _useDirNo;

    static {
        String[] strArr = {"streamcache0", "streamcache1"};
        DIR_NAME = strArr;
        _useDirNo = strArr.length - 1;
    }

    public static int getIntentStreamFileDirNo(String str) {
        for (int i = 0; i < DIR_NAME.length; i++) {
            if (str.startsWith(getDirPath(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String getStreamFilePath(Activity activity, Intent intent, Uri uri) {
        Lg.d("streamとして扱う");
        if (uri != null) {
            try {
                if (uri.toString().startsWith(CONTENT_PREF)) {
                    String type = activity.getContentResolver().getType(uri);
                    String extensionFromMimeType = type != null ? MimeTypeMap.getSingleton().getExtensionFromMimeType(type) : null;
                    if (extensionFromMimeType == null) {
                        Lg.e("suffix is null");
                        return null;
                    }
                    if (extensionFromMimeType.equals("*")) {
                        extensionFromMimeType = "png";
                    }
                    createDir();
                    String str = getDirPath() + "/catchIntentFile_" + System.currentTimeMillis() + "." + extensionFromMimeType;
                    Lg.i("fileName = " + str);
                    File file = new File(str);
                    InputStream openInputStream = activity.getContentResolver().openInputStream(uri);
                    if (openInputStream == null) {
                        Lg.e("inputStreamが取得できませんでした");
                        return null;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[1024];
                    int i = 0;
                    while (true) {
                        int read = openInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                        i += read;
                    }
                    openInputStream.close();
                    fileOutputStream.close();
                    if (i == 0) {
                        return null;
                    }
                    return str;
                }
            } catch (Exception unused) {
                Lg.e("stream file でエラー発生");
                return null;
            }
        }
        Lg.i("urlがnullか、content://から始まっていません");
        return null;
    }

    private static void createDir() {
        int i = _useDirNo;
        _useDirNo = i == DIR_NAME.length + (-1) ? 0 : i + 1;
        String dirPath = getDirPath();
        if (FileUtils.mkDirectory(dirPath)) {
            return;
        }
        FileUtils.cleanUpDirs(dirPath);
    }

    public static String getDirPath() {
        return PathGetter.getIns().getCacheDirPath() + "/" + DIR_NAME[_useDirNo];
    }

    public static String getDirPath(int i) {
        return PathGetter.getIns().getCacheDirPath() + "/" + DIR_NAME[i];
    }

    public static String[] getDirPathAll() {
        String[] strArr = new String[DIR_NAME.length];
        int i = 0;
        while (true) {
            String[] strArr2 = DIR_NAME;
            if (i >= strArr2.length) {
                return strArr;
            }
            strArr[i] = PathGetter.getIns().getCacheDirPath() + "/" + strArr2[i];
            i++;
        }
    }
}
