package com.epson.iprojection.ui.activities.support.intro.moderator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class CustomOkCancelDialog {
    private final AlertDialog _dialog;

    public CustomOkCancelDialog(Activity activity, String str, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(str);
        builder.setPositiveButton(activity.getString(R.string._OK_), onClickListener);
        builder.setNegativeButton(activity.getString(R.string._Cancel_), onClickListener);
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        this._dialog = create;
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    public boolean isShowing() {
        AlertDialog alertDialog = this._dialog;
        return alertDialog != null && alertDialog.isShowing();
    }

    public void dismiss() {
        AlertDialog alertDialog = this._dialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this._dialog.dismiss();
    }
}
