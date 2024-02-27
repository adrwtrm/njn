package com.epson.iprojection.ui.activities.pjselect.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.D_ProfileItem;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class ProfileDetailDialog {
    private final Activity _activity;
    private final D_ProfileItem _pItem;

    public static /* synthetic */ void lambda$show$0(DialogInterface dialogInterface, int i) {
    }

    public ProfileDetailDialog(Activity activity, D_ProfileItem d_ProfileItem) {
        this._activity = activity;
        this._pItem = d_ProfileItem;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
        builder.setTitle(this._activity.getString(R.string._DetailInformation_));
        builder.setMessage(String.format(this._activity.getString(R.string._ProfileDetail_), this._pItem._nodeName, this._pItem._projName, this._pItem._ipAddress, this._pItem._comment));
        builder.setPositiveButton(this._activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.layouts.ProfileDetailDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProfileDetailDialog.lambda$show$0(dialogInterface, i);
            }
        });
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }
}
