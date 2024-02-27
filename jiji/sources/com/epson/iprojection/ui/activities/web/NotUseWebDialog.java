package com.epson.iprojection.ui.activities.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class NotUseWebDialog {
    private static final String SIMPLEAP_PREFIX = "DIRECT-";
    private final Activity _activity;

    public NotUseWebDialog(Activity activity) {
        this._activity = activity;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
        builder.setIcon(17301543);
        if (isSimpleAP()) {
            builder.setMessage(this._activity.getString(R.string._NotReachableWhileSimpleAP_));
        } else {
            builder.setMessage(this._activity.getString(R.string._NotReachableWhileQuickMode_));
        }
        builder.setCancelable(true);
        builder.setPositiveButton(this._activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.web.NotUseWebDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                NotUseWebDialog.lambda$show$0(dialogInterface, i);
            }
        });
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    public static /* synthetic */ void lambda$show$0(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }

    private boolean isSimpleAP() {
        WifiInfo connectionInfo = ((WifiManager) this._activity.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo != null) {
            return connectionInfo.getSSID().replace("\"", "").startsWith(SIMPLEAP_PREFIX);
        }
        return false;
    }
}
