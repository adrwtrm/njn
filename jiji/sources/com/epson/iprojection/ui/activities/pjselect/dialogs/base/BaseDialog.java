package com.epson.iprojection.ui.activities.pjselect.dialogs.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public abstract class BaseDialog {
    protected ResultAction _action;
    protected Context _context;
    protected IOnDialogEventListener _impl;
    protected AlertDialog.Builder _builder = null;
    protected AlertDialog _dialog = null;

    /* loaded from: classes.dex */
    public enum ResultAction {
        NOACTION,
        CONNECT,
        DISCONNECT,
        SELFPROJECTION,
        GOTOTOP,
        DELIVERY,
        DELIVERY_WHITE,
        ONLY_CLOSE_DIALOG,
        SHOW_MESSAGE,
        SHOW_MESSAGE_ALERT,
        WAIT_MODERATOR_RESULT,
        RESTORE_WIFI,
        REMOVE_WIFIPROFILE,
        NFC_RETRY_CONNECT,
        APP_UPDATE
    }

    protected abstract void setConfig(Context context, AlertDialog.Builder builder);

    public BaseDialog(Context context, IOnDialogEventListener iOnDialogEventListener, ResultAction resultAction) {
        this._impl = iOnDialogEventListener;
        this._action = resultAction;
        this._context = context;
    }

    public void create(Context context) {
        this._context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(this._context);
        this._builder = builder;
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BaseDialog.this.m146x14755609(dialogInterface);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$create$0$com-epson-iprojection-ui-activities-pjselect-dialogs-base-BaseDialog  reason: not valid java name */
    public /* synthetic */ void m146x14755609(DialogInterface dialogInterface) {
        IOnDialogEventListener iOnDialogEventListener = this._impl;
        if (iOnDialogEventListener != null) {
            iOnDialogEventListener.onDialogCanceled();
        }
    }

    public void show() {
        try {
            Context context = this._context;
            if (context instanceof PjConnectableActivity) {
                if (!isActivityForeGround((PjConnectableActivity) context)) {
                    return;
                }
            }
            if (ActivityGetter.getIns().isAppFinished()) {
                return;
            }
            AlertDialog create = this._builder.create();
            this._dialog = create;
            create.setCancelable(false);
            this._dialog.show();
            RegisteredDialog.getIns().setDialog(this._dialog);
        } catch (ClassCastException unused) {
        }
    }

    public boolean isShowing() {
        AlertDialog alertDialog = this._dialog;
        if (alertDialog != null) {
            return alertDialog.isShowing();
        }
        return false;
    }

    public void delete() {
        AlertDialog alertDialog;
        Activity activity = (Activity) this._context;
        if (activity == null) {
            Lg.e("contextがactivityに変換できませんでした");
        } else if (ActivityGetter.getIns().isAppFinished() || activity.isFinishing() || (alertDialog = this._dialog) == null) {
        } else {
            try {
                alertDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Lg.e("IllegalArgumentException! : " + e.getMessage());
            }
        }
    }

    private boolean isActivityForeGround(PjConnectableActivity pjConnectableActivity) {
        return (pjConnectableActivity == null || pjConnectableActivity.isFinishing() || !pjConnectableActivity.isForeGroundThisActivity()) ? false : true;
    }
}
