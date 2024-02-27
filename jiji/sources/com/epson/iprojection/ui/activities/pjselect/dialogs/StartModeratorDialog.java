package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class StartModeratorDialog extends BaseHaveButtonDialog {
    public StartModeratorDialog(Context context, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, null, null, null, resultAction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog, com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void setConfig(Context context, AlertDialog.Builder builder) {
        builder.setMessage(context.getString(R.string._WantBeModerator_));
        builder.setPositiveButton(context.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.StartModeratorDialog$$ExternalSyntheticLambda0
            {
                StartModeratorDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StartModeratorDialog.this.m141xf44904c5(dialogInterface, i);
            }
        });
        builder.setNegativeButton(context.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.StartModeratorDialog$$ExternalSyntheticLambda1
            {
                StartModeratorDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StartModeratorDialog.this.m142x27f72f86(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-pjselect-dialogs-StartModeratorDialog  reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void m141xf44904c5(android.content.DialogInterface r2, int r3) {
        /*
            r1 = this;
            com.epson.iprojection.ui.common.analytics.Analytics r2 = com.epson.iprojection.ui.common.analytics.Analytics.getIns()
            com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent r3 = com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent.MODERATOR_START
            r2.sendEvent(r3)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.epson.iprojection.ui.engine_wrapper.Pj r3 = com.epson.iprojection.ui.engine_wrapper.Pj.getIns()
            boolean r2 = r3.hasMppModeratorPassword(r2)
            if (r2 == 0) goto L20
            com.epson.iprojection.ui.engine_wrapper.Pj r2 = com.epson.iprojection.ui.engine_wrapper.Pj.getIns()
            r2.createModeratorPassInputDialog()
            goto L31
        L20:
            com.epson.iprojection.ui.engine_wrapper.Pj r2 = com.epson.iprojection.ui.engine_wrapper.Pj.getIns()
            r3 = 1
            int r2 = r2.setMppControlMode(r3)
            if (r2 != 0) goto L34
            com.epson.iprojection.ui.activities.moderator.MultiProjectionDisplaySettings r2 = com.epson.iprojection.ui.activities.moderator.MultiProjectionDisplaySettings.INSTANCE
            r3 = 0
            r2.setThumb(r3)
        L31:
            java.lang.String r2 = ""
            goto L53
        L34:
            r3 = -802(0xfffffffffffffcde, float:NaN)
            if (r2 != r3) goto L46
            com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog$ResultAction r2 = com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog.ResultAction.SHOW_MESSAGE
            r1._action = r2
            android.content.Context r2 = r1._context
            r3 = 2131755175(0x7f1000a7, float:1.9141222E38)
            java.lang.String r2 = r2.getString(r3)
            goto L53
        L46:
            com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog$ResultAction r2 = com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog.ResultAction.SHOW_MESSAGE
            r1._action = r2
            android.content.Context r2 = r1._context
            r3 = 2131755051(0x7f10002b, float:1.914097E38)
            java.lang.String r2 = r2.getString(r3)
        L53:
            com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener r3 = r1._impl
            if (r3 == 0) goto L5e
            com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener r3 = r1._impl
            com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog$ResultAction r0 = r1._action
            r3.onClickDialogOK(r2, r0)
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.pjselect.dialogs.StartModeratorDialog.m141xf44904c5(android.content.DialogInterface, int):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$1$com-epson-iprojection-ui-activities-pjselect-dialogs-StartModeratorDialog  reason: not valid java name */
    public /* synthetic */ void m142x27f72f86(DialogInterface dialogInterface, int i) {
        this._action = BaseDialog.ResultAction.ONLY_CLOSE_DIALOG;
        this._impl.onClickDialogNG(this._action);
    }
}
