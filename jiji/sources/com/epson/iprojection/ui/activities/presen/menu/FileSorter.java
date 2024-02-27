package com.epson.iprojection.ui.activities.presen.menu;

import android.app.Activity;

/* loaded from: classes.dex */
public class FileSorter implements IOnClickDialogOkListener {
    private final Activity _activity;

    public FileSorter(Activity activity) {
        this._activity = activity;
        new SortingDialog(activity, this);
    }

    @Override // com.epson.iprojection.ui.activities.presen.menu.IOnClickDialogOkListener
    public void onClickDialogOk() {
        ((IOnClickDialogOkListener) this._activity).onClickDialogOk();
    }
}
