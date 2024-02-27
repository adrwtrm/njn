package com.epson.iprojection.ui.activities.pjselect.dialogs.base;

import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;

/* loaded from: classes.dex */
public interface IOnDialogEventListener {
    void onClickDialogNG(BaseDialog.ResultAction resultAction);

    void onClickDialogOK(String str, BaseDialog.ResultAction resultAction);

    void onDialogCanceled();
}
