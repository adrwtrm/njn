package com.serenegiant.system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.utils.FileUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class SAFUtils {
    private static final boolean DEBUG = false;
    private static final String KEY_PREFIX = "SDUtils-";
    private static final String TAG = "SAFUtils";

    /* loaded from: classes2.dex */
    public interface FileFilter {
        boolean accept(DocumentFile documentFile);
    }

    private SAFUtils() {
    }

    public static boolean hasPermission(Context context, int i) {
        if (BuildCheck.isKitKat()) {
            Uri loadUri = loadUri(context, getKey(i));
            if (loadUri != null) {
                return hasPermission(context.getContentResolver().getPersistedUriPermissions(), loadUri);
            }
            return false;
        }
        throw new UnsupportedOperationException("should be API>=19");
    }

    public static boolean hasPermission(List<UriPermission> list, Uri uri) {
        for (UriPermission uriPermission : list) {
            if (uriPermission.getUri().equals(uri)) {
                return true;
            }
        }
        return false;
    }

    public static Uri takePersistableUriPermission(Context context, int i, Uri uri) {
        return takePersistableUriPermission(context, i, uri, 3);
    }

    public static Uri takePersistableUriPermission(Context context, int i, Uri uri, int i2) {
        if (BuildCheck.isLollipop()) {
            context.getContentResolver().takePersistableUriPermission(uri, i2);
            saveUri(context, getKey(i), uri);
            return uri;
        }
        throw new UnsupportedOperationException("should be API>=19");
    }

    public static void releasePersistableUriPermission(Context context, int i) {
        if (BuildCheck.isKitKat()) {
            String key = getKey(i);
            Uri loadUri = loadUri(context, key);
            if (loadUri != null) {
                try {
                    context.getContentResolver().releasePersistableUriPermission(loadUri, 3);
                } catch (SecurityException unused) {
                }
                clearUri(context, key);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("should be API>=19");
    }

    public static DocumentFile getDir(Context context, int i, String str) throws IOException {
        if (BuildCheck.isLollipop()) {
            Uri storageUri = getStorageUri(context, i);
            if (storageUri != null) {
                return getDir(DocumentFile.fromTreeUri(context, storageUri), str);
            }
            throw new FileNotFoundException("specific dir not found");
        }
        throw new UnsupportedOperationException("should be API>=21");
    }

    public static DocumentFile getDir(DocumentFile documentFile, String str) throws IOException {
        String[] split;
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split("/")) {
                if (!TextUtils.isEmpty(str2)) {
                    if ("..".equals(str2)) {
                        documentFile = documentFile.getParentFile();
                        if (documentFile == null) {
                            throw new IOException("failed to get parent directory");
                        }
                    } else if (".".equals(str2)) {
                        continue;
                    } else {
                        DocumentFile findFile = documentFile.findFile(str2);
                        if (findFile != null && findFile.isDirectory()) {
                            documentFile = findFile;
                        } else if (findFile == null) {
                            if (documentFile.canWrite()) {
                                documentFile = documentFile.createDirectory(str2);
                            } else {
                                throw new IOException("can't create directory");
                            }
                        } else {
                            throw new IOException("can't create directory, file with same name already exists");
                        }
                    }
                }
            }
        }
        return documentFile;
    }

    public static List<DocumentFile> listFiles(DocumentFile documentFile, FileFilter fileFilter) {
        DocumentFile[] listFiles;
        ArrayList arrayList = new ArrayList();
        if (documentFile.isDirectory()) {
            for (DocumentFile documentFile2 : documentFile.listFiles()) {
                if (fileFilter == null || fileFilter.accept(documentFile2)) {
                    arrayList.add(documentFile2);
                }
            }
        }
        return arrayList;
    }

    public static DocumentFile getFile(Context context, int i, String str, String str2, String str3) throws IOException {
        if (BuildCheck.isLollipop()) {
            DocumentFile dir = getDir(context, i, str);
            if (dir != null) {
                return getFile(dir, null, str2, str3);
            }
            throw new IOException("specific dir not found");
        }
        throw new UnsupportedOperationException("should be API>=21");
    }

    public static DocumentFile getFile(DocumentFile documentFile, String str, String str2, String str3) throws IOException {
        DocumentFile dir = getDir(documentFile, str);
        DocumentFile findFile = dir.findFile(str3);
        if (findFile != null) {
            if (findFile.isFile()) {
                return findFile;
            }
            throw new IOException("directory with same name already exists");
        }
        return createFile(dir, str2, str3);
    }

    public static OutputStream getOutputStream(Context context, int i, String str, String str2, String str3) throws IOException {
        if (BuildCheck.isLollipop()) {
            DocumentFile dir = getDir(context, i, str);
            if (dir != null) {
                return getOutputStream(context, dir, (String) null, str2, str3);
            }
            throw new FileNotFoundException("specific dir not found");
        }
        throw new UnsupportedOperationException("should be API>=21");
    }

    public static OutputStream getOutputStream(Context context, DocumentFile documentFile, String str, String str2, String str3) throws IOException {
        DocumentFile dir = getDir(documentFile, str);
        DocumentFile findFile = dir.findFile(str3);
        if (findFile != null) {
            if (findFile.isFile()) {
                return context.getContentResolver().openOutputStream(findFile.getUri());
            }
            throw new IOException("directory with same name already exists");
        }
        return context.getContentResolver().openOutputStream(createFile(dir, str2, str3).getUri());
    }

    public static InputStream getInputStream(Context context, int i, String str, String str2, String str3) throws IOException {
        if (BuildCheck.isLollipop()) {
            DocumentFile dir = getDir(context, i, str);
            if (dir != null) {
                return getInputStream(context, dir, (String) null, str2, str3);
            }
            throw new FileNotFoundException("specifc dir not found");
        }
        throw new UnsupportedOperationException("should be API>=21");
    }

    public static InputStream getInputStream(Context context, DocumentFile documentFile, String str, String str2, String str3) throws IOException {
        DocumentFile findFile = getDir(documentFile, str).findFile(str3);
        if (findFile != null) {
            if (findFile.isFile()) {
                return context.getContentResolver().openInputStream(findFile.getUri());
            }
            throw new IOException("directory with same name already exists");
        }
        throw new FileNotFoundException("specific file not found");
    }

    public static ParcelFileDescriptor getFd(Context context, int i, String str, String str2, String str3) throws IOException {
        if (BuildCheck.isLollipop()) {
            DocumentFile dir = getDir(context, i, str);
            if (dir != null) {
                DocumentFile findFile = dir.findFile(str3);
                if (findFile != null) {
                    if (findFile.isFile()) {
                        return context.getContentResolver().openFileDescriptor(findFile.getUri(), "rw");
                    }
                    throw new IOException("directory with same name already exists");
                }
                return context.getContentResolver().openFileDescriptor(createFile(dir, str2, str3).getUri(), "rw");
            }
            throw new FileNotFoundException("specific dir not found");
        }
        throw new UnsupportedOperationException("should be API>=21");
    }

    public static ParcelFileDescriptor getFd(Context context, DocumentFile documentFile, String str, String str2, String str3) throws IOException {
        DocumentFile dir = getDir(documentFile, str);
        DocumentFile findFile = dir.findFile(str3);
        if (findFile != null) {
            if (findFile.isFile()) {
                return context.getContentResolver().openFileDescriptor(findFile.getUri(), "rw");
            }
            throw new IOException("directory with same name already exists");
        }
        return context.getContentResolver().openFileDescriptor(createFile(dir, str2, str3).getUri(), "rw");
    }

    public static Map<Integer, Uri> getStorageUriAll(Context context) {
        HashMap hashMap = new HashMap();
        Map<String, ?> all = context.getSharedPreferences(context.getPackageName(), 0).getAll();
        ArrayList<String> arrayList = new ArrayList();
        List<UriPermission> persistedUriPermissions = context.getContentResolver().getPersistedUriPermissions();
        for (String str : all.keySet()) {
            if (str.startsWith(KEY_PREFIX)) {
                Object obj = all.get(str);
                if (obj instanceof String) {
                    try {
                        int parseInt = Integer.parseInt(str.substring(8));
                        Uri parse = Uri.parse((String) obj);
                        if (hasPermission(persistedUriPermissions, parse)) {
                            hashMap.put(Integer.valueOf(parseInt), parse);
                        } else {
                            arrayList.add(str);
                        }
                    } catch (NumberFormatException unused) {
                        Log.d(TAG, "getStorageUriAll:unexpected key format," + str + ",value=" + obj);
                    }
                } else {
                    Log.d(TAG, "getStorageUriAll:unexpected key-value pair,key=" + str + ",value=" + obj);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            for (String str2 : arrayList) {
                clearUri(context, str2);
            }
        }
        return hashMap;
    }

    private static String getKey(int i) {
        return String.format(Locale.US, "SDUtils-%d", Integer.valueOf(i));
    }

    private static void saveUri(Context context, String str, Uri uri) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(str, uri.toString()).apply();
        }
    }

    private static Uri loadUri(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return null;
        }
        try {
            return Uri.parse(sharedPreferences.getString(str, null));
        } catch (Exception e) {
            Log.w(TAG, e);
            return null;
        }
    }

    private static void clearUri(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return;
        }
        try {
            sharedPreferences.edit().remove(str).apply();
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Uri getStorageUri(Context context, int i) throws UnsupportedOperationException {
        boolean z;
        if (BuildCheck.isKitKat()) {
            Uri loadUri = loadUri(context, getKey(i));
            if (loadUri != null) {
                Iterator<UriPermission> it = context.getContentResolver().getPersistedUriPermissions().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (it.next().getUri().equals(loadUri)) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    return loadUri;
                }
                return null;
            }
            return null;
        }
        throw new UnsupportedOperationException("should be API>=21");
    }

    private static Intent prepareStorageAccessPermission() {
        return new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
    }

    private static DocumentFile createFile(DocumentFile documentFile, String str, String str2) {
        String ext = FileUtils.getExt(str2);
        if (TextUtils.isEmpty(ext)) {
            return documentFile.createFile(str, str2);
        }
        return documentFile.createFile(str.split("/")[0] + "/" + ext, FileUtils.removeFileExtension(str2));
    }
}
