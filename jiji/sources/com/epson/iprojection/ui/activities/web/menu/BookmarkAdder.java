package com.epson.iprojection.ui.activities.web.menu;

import android.app.Activity;
import com.epson.iprojection.ui.activities.web.menu.bookmark.EditBookmarkDialog;
import com.epson.iprojection.ui.activities.web.menu.bookmark.IOnClickDialogOkListener;
import com.epson.iprojection.ui.common.toast.ToastMgr;

/* loaded from: classes.dex */
public class BookmarkAdder implements IOnClickDialogOkListener {
    private final Activity _activity;

    public BookmarkAdder(Activity activity, String str, String str2) {
        this._activity = activity;
        new EditBookmarkDialog(activity, str, str2, this, EditBookmarkDialog.Mode.Add);
    }

    @Override // com.epson.iprojection.ui.activities.web.menu.bookmark.IOnClickDialogOkListener
    public void onClickDialogOk() {
        ToastMgr.getIns().show(this._activity, ToastMgr.Type.RegisteredBookmark);
    }
}
