package com.epson.iprojection.ui.common.uiparts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class OkCancelDialog {
    private final AlertDialog _dialog;

    public OkCancelDialog(Context context, String str, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setPositiveButton(context.getString(R.string._OK_), onClickListener);
        builder.setNegativeButton(context.getString(R.string._Cancel_), onClickListener);
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        this._dialog = create;
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    public OkCancelDialog(Context context, View view, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton(context.getString(R.string._OK_), onClickListener);
        builder.setNegativeButton(context.getString(R.string._Cancel_), onClickListener);
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        this._dialog = create;
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    public boolean isShowing() {
        return this._dialog.isShowing();
    }

    public void dismiss() {
        this._dialog.dismiss();
    }
}
