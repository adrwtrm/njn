package com.epson.iprojection.ui.common.editableList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class CustomDialog {
    private final AlertDialog.Builder _builder;
    private final D_CustomDialog _dat;

    public CustomDialog(D_CustomDialog d_CustomDialog, final int i) {
        this._dat = d_CustomDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(d_CustomDialog._activity);
        this._builder = builder;
        builder.setTitle(d_CustomDialog._title);
        builder.setItems(d_CustomDialog._itemNames, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.editableList.CustomDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CustomDialog.this.m203x8fad187f(i, dialogInterface, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-common-editableList-CustomDialog  reason: not valid java name */
    public /* synthetic */ void m203x8fad187f(int i, DialogInterface dialogInterface, int i2) {
        Lg.d("clicked which = " + i2);
        this._dat._impl.onClickDialogItem(i, i2);
    }

    public void show() {
        AlertDialog create = this._builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }
}
