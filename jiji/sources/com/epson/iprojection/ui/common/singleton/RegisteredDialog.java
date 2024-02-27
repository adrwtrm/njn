package com.epson.iprojection.ui.common.singleton;

import android.app.Dialog;

/* loaded from: classes.dex */
public class RegisteredDialog {
    private static final RegisteredDialog _inst = new RegisteredDialog();
    private Dialog _dialog;

    public void setDialog(Dialog dialog) {
        this._dialog = dialog;
    }

    public boolean isShowing() {
        Dialog dialog = this._dialog;
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }

    public void dismiss() {
        Dialog dialog = this._dialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this._dialog.dismiss();
    }

    private RegisteredDialog() {
    }

    public static RegisteredDialog getIns() {
        return _inst;
    }
}
