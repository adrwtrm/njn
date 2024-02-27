package com.epson.iprojection.ui.activities.web;

import android.app.Activity;
import com.epson.iprojection.ui.activities.web.menu.BookmarkAdder;
import com.epson.iprojection.ui.activities.web.menu.bookmark.Bookmark;
import com.epson.iprojection.ui.activities.web.menu.bookmark.Startpage;
import com.epson.iprojection.ui.activities.web.menu.history.WebHistory;
import com.epson.iprojection.ui.common.exception.FullException;
import com.epson.iprojection.ui.common.toast.ToastMgr;

/* loaded from: classes.dex */
public class BrowserData {
    private final Activity _activity;
    private final Bookmark _bookmark;
    private final WebHistory _history;
    private final Startpage _startpage;

    public BrowserData(Activity activity) {
        this._activity = activity;
        this._bookmark = new Bookmark(null).initialize(activity, null, false);
        this._startpage = new Startpage().initialize(activity, null, false);
        this._history = new WebHistory().initialize(activity, null, false);
    }

    public void addBkmkData(String str, String str2) {
        if (!this._bookmark.isAddable()) {
            ToastMgr.getIns().show(this._activity, ToastMgr.Type.FullBookmark);
        } else {
            new BookmarkAdder(this._activity, str, str2);
        }
    }

    public void setStartpageData(String str, String str2) {
        this._startpage.set(str, str2);
    }

    public String getStartpageUrl() {
        return this._startpage.getUrl();
    }

    public void addHistoryData(String str, String str2) {
        try {
            this._history.insertFront(str, str2);
        } catch (FullException unused) {
        }
    }
}
