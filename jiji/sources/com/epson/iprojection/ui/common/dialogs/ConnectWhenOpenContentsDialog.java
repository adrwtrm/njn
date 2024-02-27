package com.epson.iprojection.ui.common.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.drawermenu.DrawerList;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class ConnectWhenOpenContentsDialog {
    public static final String ID = "ID";
    public static final String ID_CAMERA = "ID_CAMERA";
    public static final String ID_DELIVER = "ID_DELIVER";
    public static final String ID_MIRRORING = "ID_MIRRORING";
    public static final String ID_NOTHING = "ID_NOTHING";
    public static final String ID_PDF = "ID_PDF";
    public static final String ID_PHOTO = "ID_PHOTO";
    public static final String ID_WEB = "ID_WEB";
    private final Activity _activity;
    private final OkCancelDialog _dialog;
    private final String _type;

    public ConnectWhenOpenContentsDialog(Activity activity, final DrawerList drawerList, final String str) {
        this._activity = activity;
        this._type = str;
        this._dialog = new OkCancelDialog(activity, activity.getString(R.string._RequiredConnection_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.dialogs.ConnectWhenOpenContentsDialog$$ExternalSyntheticLambda0
            {
                ConnectWhenOpenContentsDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConnectWhenOpenContentsDialog.this.m202x6322df09(drawerList, str, dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$new$0$com-epson-iprojection-ui-common-dialogs-ConnectWhenOpenContentsDialog */
    public /* synthetic */ void m202x6322df09(DrawerList drawerList, String str, DialogInterface dialogInterface, int i) {
        if (i == -2) {
            drawerList.callNextActivity(DrawerList.convertTypeToInt(str));
        } else if (i != -1) {
        } else {
            if (Pj.getIns().isRegistedPjs5Over()) {
                Activity activity = this._activity;
                new OKDialog(activity, activity.getString(R.string._Exceeded4PjConnect_));
                return;
            }
            startHomeActivity();
        }
    }

    public boolean isShowing() {
        return this._dialog.isShowing();
    }

    private void startHomeActivity() {
        Activity activity = this._activity;
        if (activity instanceof Activity_PjSelect) {
            ((Activity_PjSelect) activity).startSearchAndConnect(this._type);
            return;
        }
        Intent intent = new Intent(this._activity, Activity_PjSelect.class);
        intent.putExtra(Activity_PjSelect.SEARCH_AND_CONNECT, Activity_PjSelect.TRUE);
        intent.putExtra(ID, this._type);
        this._activity.startActivity(intent);
    }
}
