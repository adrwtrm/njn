package com.epson.iprojection.common.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class FileUtils {
    public static boolean isImageFile(String str) {
        return isExpectedFile(str, new String[]{"jpg", "jpeg", "png", "bmp", "gif"});
    }

    public static boolean isPdfFile(String str) {
        return isExpectedFile(str, new String[]{"pdf"});
    }

    public static boolean isSettingFile(String str) {
        return str.compareTo(CommonDefine.IPROJECTION_SETTINGS) == 0;
    }

    public static boolean isProfile(String str) {
        if (str.getBytes().length != str.length()) {
            return false;
        }
        return isExpectedFile(str, new String[]{"mplist"});
    }

    public static boolean isExpectedFile(String str, String[] strArr) {
        String suffix = getSuffix(str);
        if (suffix == null) {
            return false;
        }
        for (String str2 : strArr) {
            if (suffix.compareToIgnoreCase(str2) == 0) {
                return true;
            }
        }
        return false;
    }

    public static String getFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }

    public static String getFilePath(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    private static String getSuffix(String str) {
        int lastIndexOf;
        if (str == null || (lastIndexOf = str.lastIndexOf(".")) == -1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    public static String getPreffix(String str) {
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(".");
        return lastIndexOf != -1 ? str.substring(0, lastIndexOf) : str;
    }

    public static void cleanUpDirs(String str) {
        File[] listFiles = new File(str).listFiles();
        int i = 0;
        if (listFiles != null) {
            while (i < listFiles.length) {
                listFiles[i].delete();
                i++;
            }
        }
        Lg.d("Delete temporary files " + i + "items.");
    }

    public static boolean deleteFile(String str) {
        return new File(str).delete();
    }

    public static boolean mkDirectory(String str) {
        File file = new File(str);
        if (file.exists()) {
            return false;
        }
        return file.mkdirs();
    }

    public static void deleteDirectory(Context context, String str) {
        if (str.startsWith(context.getExternalFilesDir(null).getPath()) || str.startsWith(context.getExternalCacheDir().getPath())) {
            deleteDirectory(new File(str));
        }
    }

    private static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    deleteDirectory(file2);
                }
                file.delete();
            }
        }
    }

    public static void deleteCache(String str) {
        new File(PathGetter.getIns().getCacheDirPath() + "/" + str).delete();
    }

    public static void deleteListData(File[] fileArr) {
        for (File file : fileArr) {
            Lg.d("[" + file.toString() + "]を削除しました。");
            file.delete();
        }
    }

    public static void deleteAllAppData(Context context) {
        String[] fileList;
        for (String str : context.fileList()) {
            Lg.d("[" + str + "]を削除しました。");
            context.deleteFile(str);
        }
    }

    public static boolean deleteAppDataFile(Context context, String str) {
        return context.deleteFile(str);
    }

    public static boolean existsAppDataFile(Context context, String str) {
        try {
            context.openFileInput(str).close();
        } catch (FileNotFoundException unused) {
            return false;
        } catch (IOException unused2) {
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e4 A[Catch: IOException -> 0x0111, TRY_ENTER, TryCatch #4 {IOException -> 0x0111, blocks: (B:13:0x00a5, B:40:0x00e4, B:42:0x00e9, B:44:0x00ee, B:56:0x010d, B:50:0x00fe, B:52:0x0103, B:54:0x0108), top: B:74:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e9 A[Catch: IOException -> 0x0111, TryCatch #4 {IOException -> 0x0111, blocks: (B:13:0x00a5, B:40:0x00e4, B:42:0x00e9, B:44:0x00ee, B:56:0x010d, B:50:0x00fe, B:52:0x0103, B:54:0x0108), top: B:74:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ee A[Catch: IOException -> 0x0111, TRY_LEAVE, TryCatch #4 {IOException -> 0x0111, blocks: (B:13:0x00a5, B:40:0x00e4, B:42:0x00e9, B:44:0x00ee, B:56:0x010d, B:50:0x00fe, B:52:0x0103, B:54:0x0108), top: B:74:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00fe A[Catch: IOException -> 0x0111, TRY_ENTER, TryCatch #4 {IOException -> 0x0111, blocks: (B:13:0x00a5, B:40:0x00e4, B:42:0x00e9, B:44:0x00ee, B:56:0x010d, B:50:0x00fe, B:52:0x0103, B:54:0x0108), top: B:74:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0103 A[Catch: IOException -> 0x0111, TryCatch #4 {IOException -> 0x0111, blocks: (B:13:0x00a5, B:40:0x00e4, B:42:0x00e9, B:44:0x00ee, B:56:0x010d, B:50:0x00fe, B:52:0x0103, B:54:0x0108), top: B:74:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0108 A[Catch: IOException -> 0x0111, TryCatch #4 {IOException -> 0x0111, blocks: (B:13:0x00a5, B:40:0x00e4, B:42:0x00e9, B:44:0x00ee, B:56:0x010d, B:50:0x00fe, B:52:0x0103, B:54:0x0108), top: B:74:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x011d A[Catch: IOException -> 0x012b, TryCatch #6 {IOException -> 0x012b, blocks: (B:62:0x0118, B:64:0x011d, B:66:0x0122, B:68:0x0127), top: B:75:0x0118 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0122 A[Catch: IOException -> 0x012b, TryCatch #6 {IOException -> 0x012b, blocks: (B:62:0x0118, B:64:0x011d, B:66:0x0122, B:68:0x0127), top: B:75:0x0118 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0127 A[Catch: IOException -> 0x012b, TRY_LEAVE, TryCatch #6 {IOException -> 0x012b, blocks: (B:62:0x0118, B:64:0x011d, B:66:0x0122, B:68:0x0127), top: B:75:0x0118 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0118 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v25, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v26 */
    /* JADX WARN: Type inference failed for: r6v27 */
    /* JADX WARN: Type inference failed for: r6v28 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.io.SequenceInputStream] */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23, types: [java.io.SequenceInputStream] */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.io.SequenceInputStream] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.io.SequenceInputStream] */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Type inference failed for: r9v0, types: [int] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean combineFile(java.lang.String r6, int r7, java.lang.String r8, int r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.utils.FileUtils.combineFile(java.lang.String, int, java.lang.String, int, java.lang.String):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0088, code lost:
        if (r10 != 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a2, code lost:
        if (r10 != 0) goto L39;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v17 */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v22, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r10v23 */
    /* JADX WARN: Type inference failed for: r10v24 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9, types: [java.io.FileOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean replace(java.lang.String r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 198
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.utils.FileUtils.replace(java.lang.String, int, int):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.OutputStream] */
    public static void copyFile(String str, String str2) {
        ?? file = new File(PathGetter.getIns().getAppsDirPath() + "/" + str);
        File file2 = new File(PathGetter.getIns().getAppsDirPath() + "/" + str2);
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream((File) file);
                    try {
                        file = new FileOutputStream(file2);
                        try {
                            byte[] bArr = new byte[4096];
                            while (true) {
                                int read = fileInputStream2.read(bArr);
                                if (-1 == read) {
                                    break;
                                }
                                file.write(bArr, 0, read);
                            }
                            fileInputStream2.close();
                        } catch (FileNotFoundException unused) {
                            fileInputStream = fileInputStream2;
                            file = file;
                            Lg.e("input or output file not found.");
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (file == 0) {
                                return;
                            }
                            file.close();
                        } catch (IOException unused2) {
                            fileInputStream = fileInputStream2;
                            file = file;
                            Lg.e("data read or write error.");
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (file == 0) {
                                return;
                            }
                            file.close();
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream = fileInputStream2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused3) {
                                    Lg.e("stream close error.");
                                    throw th;
                                }
                            }
                            if (file != 0) {
                                file.close();
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException unused4) {
                        file = 0;
                    } catch (IOException unused5) {
                        file = 0;
                    } catch (Throwable th2) {
                        th = th2;
                        file = 0;
                    }
                } catch (FileNotFoundException unused6) {
                    file = 0;
                } catch (IOException unused7) {
                    file = 0;
                } catch (Throwable th3) {
                    th = th3;
                    file = 0;
                }
                file.close();
            } catch (IOException unused8) {
                Lg.e("stream close error.");
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static boolean writeBytes(byte[] bArr, String str) {
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(str)));
        } catch (FileNotFoundException | IOException unused) {
        }
        try {
            bufferedOutputStream.write(bArr);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return true;
        } catch (Exception unused2) {
            bufferedOutputStream.close();
            return false;
        }
    }

    public static byte[] readBytes(String str, int i) {
        BufferedInputStream bufferedInputStream;
        byte[] bArr;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(str)));
            bArr = new byte[i];
        } catch (FileNotFoundException | IOException unused) {
        }
        try {
            int read = bufferedInputStream.read(bArr);
            String str2 = null;
            for (int i2 = 0; i2 < read; i2++) {
                String format = String.format("%1$x ", Byte.valueOf(bArr[i2]));
                if (format.length() == 2) {
                    format = "0" + format;
                }
                str2 = str2 + format;
            }
            Lg.i(str2 + " ");
            bufferedInputStream.close();
            return bArr;
        } catch (Exception unused2) {
            bufferedInputStream.close();
            return null;
        }
    }

    public static boolean writeString(String str, String str2) {
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(str2))));
            try {
                printWriter.print(str);
                printWriter.close();
                return true;
            } catch (Exception unused) {
                printWriter.close();
                return false;
            }
        } catch (FileNotFoundException unused2) {
        }
    }

    public static String readString(String str) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(str))));
        } catch (FileNotFoundException | IOException unused) {
        }
        try {
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            return readLine;
        } catch (Exception unused2) {
            bufferedReader.close();
            return null;
        }
    }

    public static boolean writeObject(Object obj, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                try {
                    try {
                        objectOutputStream.writeObject(obj);
                        objectOutputStream.close();
                        fileOutputStream.close();
                        return true;
                    } catch (Exception unused) {
                        objectOutputStream.close();
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                        return false;
                    }
                } catch (IOException unused3) {
                    fileOutputStream.close();
                    return false;
                }
            } catch (IOException unused4) {
                fileOutputStream.close();
                return false;
            }
        } catch (FileNotFoundException | IOException unused5) {
        }
    }

    public static Object readObject(String str) {
        Object obj = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    obj = objectInputStream.readObject();
                } catch (Exception unused) {
                }
                try {
                    objectInputStream.close();
                } catch (IOException unused2) {
                }
                try {
                    fileInputStream.close();
                } catch (IOException unused3) {
                }
                return obj;
            } catch (Exception unused4) {
                fileInputStream.close();
                return null;
            }
        } catch (FileNotFoundException | IOException unused5) {
        }
    }

    public static void registToAndroidDB(Context context, String str) {
        if (context == null || str == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        ContentResolver contentResolver = context.getContentResolver();
        contentValues.put("mime_type", ImageIOUtilsKt.getImageMimeType(str));
        contentValues.put("_data", str);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    public static int removeByContentResolver(Context context, String str) {
        if (context == null || str == null) {
            return 0;
        }
        try {
            return context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_data=?", new String[]{str});
        } catch (Exception unused) {
            return 0;
        }
    }

    public static String getFileNameFromUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        scheme.hashCode();
        if (scheme.equals("file")) {
            return new File(uri.getPath()).getName();
        }
        if (scheme.equals(FirebaseAnalytics.Param.CONTENT)) {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
            if (query != null) {
                String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_display_name")) : null;
                query.close();
                return string;
            }
            return null;
        }
        return null;
    }

    private FileUtils() {
    }
}
