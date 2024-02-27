package com.epson.iprojection.ui.activities.presen.img_filer;

import android.net.Uri;
import java.io.File;

/* loaded from: classes.dex */
public class ImageFileInfo {
    private long _lastModified;
    private final String _path;
    private Uri _uri;

    public ImageFileInfo(Uri uri, String str) {
        this._uri = uri;
        this._path = str;
    }

    public ImageFileInfo(String str, long j) {
        this._path = str;
        this._lastModified = j;
    }

    public long getLastModified() {
        return this._lastModified;
    }

    public String getPath() {
        return this._path;
    }

    public Uri getUri() {
        Uri uri = this._uri;
        return uri == null ? Uri.fromFile(new File(this._path)) : uri;
    }
}
