package com.epson.iprojection.common.utils;

import android.content.Context;
import java.io.File;

/* loaded from: classes.dex */
public final class PathGetter {
    private static final PathGetter _inst = new PathGetter();
    private String _packageName = null;
    private String _cacheDirPath = null;
    private String _userFilePath = null;
    private String _appsFilePath = null;

    public void initialize(Context context) {
        updateExternalStorageStatus(context);
        this._packageName = context.getPackageName();
        this._appsFilePath = getAppsFilesDirPath(context);
    }

    public final String getPackageName() {
        return this._packageName;
    }

    public final String getCacheDirPath() {
        return this._cacheDirPath;
    }

    public final String getUserDirPath() {
        return this._userFilePath;
    }

    public final String getAppsDirPath() {
        return this._appsFilePath;
    }

    private String getUserCacheDirPath(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            this._cacheDirPath = null;
            return null;
        }
        return externalCacheDir.toString();
    }

    private String getUserFilesDirPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir == null) {
            this._userFilePath = null;
            return null;
        }
        return externalFilesDir.toString();
    }

    private String getAppsFilesDirPath(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir == null) {
            this._appsFilePath = null;
            return null;
        }
        return filesDir.toString();
    }

    public boolean isAvailableExternalStorage(Context context) {
        updateExternalStorageStatus(context);
        return (this._packageName == null || this._cacheDirPath == null || this._userFilePath == null) ? false : true;
    }

    public void updateExternalStorageStatus(Context context) {
        this._cacheDirPath = getUserCacheDirPath(context);
        this._userFilePath = getUserFilesDirPath(context);
    }

    private PathGetter() {
    }

    public static PathGetter getIns() {
        return _inst;
    }
}
