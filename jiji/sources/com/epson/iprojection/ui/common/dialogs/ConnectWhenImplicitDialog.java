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
public class ConnectWhenImplicitDialog {
    private final Activity _activity;

    public ConnectWhenImplicitDialog(Activity activity, DrawerList drawerList) {
        this._activity = activity;
        new OkCancelDialog(activity, activity.getString(R.string._RequiredConnection_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.dialogs.ConnectWhenImplicitDialog$$ExternalSyntheticLambda0
            {
                ConnectWhenImplicitDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConnectWhenImplicitDialog.this.m201x95c37b2a(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-common-dialogs-ConnectWhenImplicitDialog  reason: not valid java name */
    public /* synthetic */ void m201x95c37b2a(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            if (Pj.getIns().isRegistedPjs5Over()) {
                Activity activity = this._activity;
                new OKDialog(activity, activity.getString(R.string._Exceeded4PjConnect_));
                return;
            }
            startHomeActivity();
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this._activity, Activity_PjSelect.class);
        intent.putExtra(Activity_PjSelect.SEARCH_AND_CONNECT, Activity_PjSelect.TRUE);
        intent.putExtra(Activity_PjSelect.TAG_KILL_SELF_WHEN_CONNECTED, Activity_PjSelect.TRUE);
        this._activity.startActivity(intent);
    }
}
