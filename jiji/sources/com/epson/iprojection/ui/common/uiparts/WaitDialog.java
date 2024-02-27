package com.epson.iprojection.ui.common.uiparts;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class WaitDialog {
    private final AlertDialog _dialog;

    public WaitDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setView(LayoutInflater.from(activity).inflate(R.layout.dialog_wait, (ViewGroup) null));
        this._dialog = builder.create();
    }

    public void show() {
        AlertDialog alertDialog = this._dialog;
        if (alertDialog == null || alertDialog.isShowing()) {
            return;
        }
        this._dialog.show();
        RegisteredDialog.getIns().setDialog(this._dialog);
    }

    public void dismiss() {
        AlertDialog alertDialog = this._dialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this._dialog.dismiss();
    }
}
