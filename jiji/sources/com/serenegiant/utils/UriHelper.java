package com.serenegiant.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.serenegiant.system.BuildCheck;
import java.io.File;
import java.util.ArrayList;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes2.dex */
public final class UriHelper {
    private static final boolean DEBUG = false;
    public static final String[] STANDARD_DIRECTORIES;
    private static final String TAG = "UriHelper";

    private UriHelper() {
    }

    public static String getAbsolutePath(ContentResolver contentResolver, Uri uri) {
        if (uri != null) {
            try {
                Cursor query = contentResolver.query(uri, new String[]{"_data"}, null, null, null);
                if (query != null) {
                    r0 = query.moveToFirst() ? query.getString(0) : null;
                    query.close();
                }
            } catch (Exception unused) {
            }
        }
        return r0;
    }

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Environment.DIRECTORY_MUSIC);
        arrayList.add(Environment.DIRECTORY_PODCASTS);
        arrayList.add(Environment.DIRECTORY_RINGTONES);
        arrayList.add(Environment.DIRECTORY_ALARMS);
        arrayList.add(Environment.DIRECTORY_NOTIFICATIONS);
        arrayList.add(Environment.DIRECTORY_PICTURES);
        arrayList.add(Environment.DIRECTORY_MOVIES);
        arrayList.add(Environment.DIRECTORY_DOWNLOADS);
        arrayList.add(Environment.DIRECTORY_DCIM);
        arrayList.add(Environment.DIRECTORY_DOCUMENTS);
        if (Build.VERSION.SDK_INT >= 29) {
            arrayList.add(Environment.DIRECTORY_AUDIOBOOKS);
        }
        STANDARD_DIRECTORIES = (String[]) arrayList.toArray(new String[0]);
    }

    public static boolean isStandardDirectory(String str) {
        try {
            for (String str2 : STANDARD_DIRECTORIES) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return false;
    }

    public static String getPath(Context context, Uri uri) {
        Uri uri2;
        String[] split;
        int length;
        boolean z;
        String str = null;
        if (BuildCheck.isKitKat() && DocumentsContract.isDocumentUri(context, uri)) {
            int i = 0;
            if (isExternalStorageDocument(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                BuildCheck.isLollipop();
                String[] split2 = documentId.split(":");
                String str2 = split2[0];
                if (str2 != null) {
                    if ("primary".equalsIgnoreCase(str2)) {
                        String str3 = Environment.getExternalStorageDirectory() + "/";
                        return split2.length > 1 ? str3 + split2[1] : str3;
                    } else if ("home".equalsIgnoreCase(str2)) {
                        if (split2.length > 1 && isStandardDirectory(split2[1])) {
                            return Environment.getExternalStoragePublicDirectory(split2[1]) + "/";
                        }
                        String str4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";
                        return split2.length > 1 ? str4 + split2[1] : str4;
                    } else {
                        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        File[] externalFilesDirs = context.getExternalFilesDirs(null);
                        int length2 = externalFilesDirs != null ? externalFilesDirs.length : 0;
                        StringBuilder sb = new StringBuilder();
                        int i2 = 0;
                        while (i2 < length2) {
                            File file = externalFilesDirs[i2];
                            if (file == null || !file.getAbsolutePath().startsWith(absolutePath)) {
                                String absolutePath2 = file != null ? file.getAbsolutePath() : str;
                                if (!TextUtils.isEmpty(absolutePath2) && (length = (split = absolutePath2.split("/")).length) > 2 && "storage".equalsIgnoreCase(split[1]) && str2.equalsIgnoreCase(split[2])) {
                                    sb.setLength(i);
                                    sb.append('/').append(split[1]);
                                    int i3 = 2;
                                    while (true) {
                                        if (i3 >= length) {
                                            z = false;
                                            break;
                                        } else if ("Android".equalsIgnoreCase(split[i3])) {
                                            z = true;
                                            break;
                                        } else {
                                            sb.append('/').append(split[i3]);
                                            i3++;
                                        }
                                    }
                                    if (z) {
                                        return new File(new File(sb.toString()), split2[1]).getAbsolutePath();
                                    }
                                }
                            }
                            i2++;
                            str = null;
                            i = 0;
                        }
                    }
                } else {
                    Log.w(TAG, "unexpectedly type is null");
                }
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(DocumentsContract.getDocumentId(uri))), null, null);
            } else {
                if (isMediaDocument(uri)) {
                    String[] split3 = DocumentsContract.getDocumentId(uri).split(":");
                    String str5 = split3[0];
                    if ("image".equals(str5)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if (MediaStreamTrack.VIDEO_TRACK_KIND.equals(str5)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else {
                        uri2 = MediaStreamTrack.AUDIO_TRACK_KIND.equals(str5) ? MediaStore.Audio.Media.EXTERNAL_CONTENT_URI : null;
                    }
                    if (uri2 != null) {
                        return getDataColumn(context, uri2, "_id=?", new String[]{split3[1]});
                    }
                }
            }
        } else if (uri != null) {
            if (isContentUri(uri)) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, null, null);
            } else if (isFileUri(uri)) {
                return uri.getPath();
            }
        }
        Log.w(TAG, "unexpectedly not found,uri=" + uri);
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isContentUri(Uri uri) {
        return uri != null && FirebaseAnalytics.Param.CONTENT.equals(uri.getScheme());
    }

    public static boolean isContentUri(DocumentFile documentFile) {
        Uri uri = documentFile != null ? documentFile.getUri() : null;
        return uri != null && FirebaseAnalytics.Param.CONTENT.equals(uri.getScheme());
    }

    public static boolean isFileUri(Uri uri) {
        return uri != null && "file".equals(uri.getScheme());
    }

    public static boolean isFileUri(DocumentFile documentFile) {
        Uri uri = documentFile != null ? documentFile.getUri() : null;
        return uri != null && "file".equals(uri.getScheme());
    }

    public static boolean isResourceUri(Uri uri) {
        return uri != null && "android.resource".equals(uri.getScheme());
    }
}
