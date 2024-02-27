package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class WifiDialog {
    private final Activity _activity;

    public WifiDialog(Activity activity) {
        this._activity = activity;
    }

    public void show() {
        if (NetUtils.isWifiOff(this._activity)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
            builder.setIcon(17301543);
            builder.setMessage(this._activity.getString(R.string._WiFiOff2_));
            builder.setPositiveButton(this._activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.WifiDialog$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    WifiDialog.this.m145x162cc76a(dialogInterface, i);
                }
            });
            builder.setNegativeButton(this._activity.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.WifiDialog$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    WifiDialog.lambda$show$1(dialogInterface, i);
                }
            });
            builder.setCancelable(true);
            AlertDialog create = builder.create();
            create.show();
            RegisteredDialog.getIns().setDialog(create);
        }
    }

    /* renamed from: lambda$show$0$com-epson-iprojection-ui-activities-pjselect-dialogs-WifiDialog */
    public /* synthetic */ void m145x162cc76a(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
        intent.setFlags(268435456);
        if (this._activity.getPackageManager().resolveActivity(intent, 0) != null) {
            this._activity.startActivity(intent);
        }
        dialogInterface.dismiss();
    }

    public static /* synthetic */ void lambda$show$1(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
    }
}
