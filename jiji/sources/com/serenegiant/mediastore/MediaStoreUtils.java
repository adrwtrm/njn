package com.serenegiant.mediastore;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.FileUtils;
import com.serenegiant.utils.UriHelper;

/* loaded from: classes2.dex */
public class MediaStoreUtils {
    private static final boolean DEBUG = false;
    public static final int MEDIA_ALL = 0;
    public static final int MEDIA_IMAGE = 1;
    protected static final int MEDIA_TYPE_NUM = 3;
    public static final int MEDIA_VIDEO = 2;
    protected static final int PROJ_INDEX_DATA = 3;
    protected static final int PROJ_INDEX_DISPLAY_NAME = 4;
    protected static final int PROJ_INDEX_HEIGHT = 6;
    protected static final int PROJ_INDEX_ID = 0;
    protected static final int PROJ_INDEX_MEDIA_TYPE = 7;
    protected static final int PROJ_INDEX_MIME_TYPE = 2;
    protected static final int PROJ_INDEX_TITLE = 1;
    protected static final int PROJ_INDEX_WIDTH = 5;
    public static final Uri QUERY_URI_AUDIO;
    public static final Uri QUERY_URI_FILES;
    public static final Uri QUERY_URI_IMAGES;
    public static final Uri QUERY_URI_VIDEO;
    private static final String TAG = "MediaStoreUtils";
    protected static final String[] PROJ_MEDIA = {"_id", "title", "mime_type", "_data", "_display_name", "width", "height", "media_type"};
    protected static final String[] PROJ_MEDIA_IMAGE = {"_id", "title", "mime_type", "_data", "_display_name", "width", "height"};
    protected static final String[] PROJ_MEDIA_VIDEO = {"_id", "title", "mime_type", "_data", "_display_name", "width", "height"};
    protected static final String SELECTION_MEDIA_ALL = "media_type=1 OR media_type=3";
    protected static final String SELECTION_MEDIA_IMAGE = "media_type=1";
    protected static final String SELECTION_MEDIA_VIDEO = "media_type=3";
    protected static final String[] SELECTIONS = {SELECTION_MEDIA_ALL, SELECTION_MEDIA_IMAGE, SELECTION_MEDIA_VIDEO};

    private MediaStoreUtils() {
    }

    static {
        Uri contentUri;
        Uri uri;
        Uri uri2;
        Uri uri3;
        if (Build.VERSION.SDK_INT >= 29) {
            contentUri = MediaStore.Files.getContentUri("external_primary");
        } else {
            contentUri = MediaStore.Files.getContentUri("external");
        }
        QUERY_URI_FILES = contentUri;
        if (Build.VERSION.SDK_INT >= 29) {
            uri = MediaStore.Images.Media.getContentUri("external_primary");
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        QUERY_URI_IMAGES = uri;
        if (Build.VERSION.SDK_INT >= 29) {
            uri2 = MediaStore.Video.Media.getContentUri("external_primary");
        } else {
            uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        QUERY_URI_VIDEO = uri2;
        if (Build.VERSION.SDK_INT >= 29) {
            uri3 = MediaStore.Audio.Media.getContentUri("external_primary");
        } else {
            uri3 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        QUERY_URI_AUDIO = uri3;
    }

    public static DocumentFile getContentDocument(Context context, String str, String str2, String str3, String str4) {
        return DocumentFile.fromSingleUri(context, getContentUri(context, str, str2, str3, str4));
    }

    public static Uri getContentUri(Context context, String str, String str2, String str3, String str4) {
        return getContentUri(context.getContentResolver(), str, str2, str3, str4);
    }

    public static Uri getContentUri(ContentResolver contentResolver, String str, String str2, String str3, String str4) {
        Uri uri;
        ContentValues contentValues = new ContentValues();
        String lowerCase = str != null ? str.toLowerCase() : "";
        String lowerCase2 = FileUtils.getExt(str3).toLowerCase();
        if (lowerCase.startsWith("*/") && str4 != null) {
            contentValues.put("_display_name", str3);
            contentValues.put("mime_type", lowerCase);
            uri = QUERY_URI_FILES;
        } else if (lowerCase.startsWith("image/") || lowerCase2.equalsIgnoreCase("png") || lowerCase2.equalsIgnoreCase("jpg") || lowerCase2.equalsIgnoreCase("jpeg") || lowerCase2.equalsIgnoreCase("webp")) {
            if (TextUtils.isEmpty(lowerCase)) {
                StringBuilder sb = new StringBuilder("image/");
                if (TextUtils.isEmpty(lowerCase2)) {
                    lowerCase2 = "*";
                }
                lowerCase = sb.append(lowerCase2).toString();
            }
            contentValues.put("_display_name", str3);
            contentValues.put("mime_type", lowerCase);
            uri = QUERY_URI_IMAGES;
        } else if (lowerCase.startsWith("video/") || lowerCase2.equalsIgnoreCase("mp4") || lowerCase2.equalsIgnoreCase("3gp") || lowerCase2.equalsIgnoreCase("h264") || lowerCase2.equalsIgnoreCase("mjpeg")) {
            if (TextUtils.isEmpty(lowerCase)) {
                StringBuilder sb2 = new StringBuilder("video/");
                if (TextUtils.isEmpty(lowerCase2)) {
                    lowerCase2 = "*";
                }
                lowerCase = sb2.append(lowerCase2).toString();
            }
            contentValues.put("_display_name", str3);
            contentValues.put("mime_type", lowerCase);
            uri = QUERY_URI_VIDEO;
        } else if (lowerCase.startsWith("audio/") || lowerCase2.equalsIgnoreCase("m4a")) {
            if (TextUtils.isEmpty(lowerCase)) {
                StringBuilder sb3 = new StringBuilder("audio/");
                if (TextUtils.isEmpty(lowerCase2)) {
                    lowerCase2 = "*";
                }
                lowerCase = sb3.append(lowerCase2).toString();
            }
            contentValues.put("_display_name", str3);
            contentValues.put("mime_type", lowerCase);
            uri = QUERY_URI_AUDIO;
        } else {
            throw new IllegalArgumentException("unknown mimeType/file type," + str + ",name=" + str3);
        }
        if (str4 != null) {
            contentValues.put("_data", str4);
        }
        contentValues.put("title", FileUtils.removeFileExtension(str3));
        if (Build.VERSION.SDK_INT >= 29) {
            if (!TextUtils.isEmpty(str2)) {
                contentValues.put("relative_path", str2);
            }
            contentValues.put("is_pending", (Integer) 1);
        }
        return contentResolver.insert(uri, contentValues);
    }

    public static void updateContentUri(Context context, DocumentFile documentFile) {
        if (documentFile.isDirectory() || !BuildCheck.isAPI29()) {
            return;
        }
        updateContentUri(context.getContentResolver(), documentFile.getUri());
    }

    public static void updateContentUri(Context context, Uri uri) {
        updateContentUri(context.getContentResolver(), uri);
    }

    public static void updateContentUri(ContentResolver contentResolver, Uri uri) {
        if (BuildCheck.isAPI29() && UriHelper.isContentUri(uri)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_pending", (Integer) 0);
            try {
                contentResolver.update(uri, contentValues, null, null);
            } catch (Exception e) {
                Log.d(TAG, "updateContentUri:", e);
            }
        }
    }

    public static Uri getUri(int i, long j) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return null;
                    }
                    return ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, j);
                }
                return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, j);
            }
            return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, j);
        }
        return ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), j);
    }

    @Deprecated
    public static Uri registerImage(Context context, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mime_type", str);
        contentValues.put("_data", str2);
        return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    @Deprecated
    public static Uri registerVideo(Context context, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mime_type", str);
        contentValues.put("_data", str2);
        return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
    }
}
