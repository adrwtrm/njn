package com.epson.iprojection.ui.common.editableList;

import android.app.Activity;

/* loaded from: classes.dex */
public class D_CustomDialog {
    public Activity _activity;
    public IOnDialogItemClickListener _impl;
    public CharSequence[] _itemNames;
    public String _title;

    public D_CustomDialog(Activity activity, String str, CharSequence[] charSequenceArr, IOnDialogItemClickListener iOnDialogItemClickListener) {
        this._activity = activity;
        this._title = str;
        this._itemNames = charSequenceArr;
        this._impl = iOnDialogItemClickListener;
    }
}
