package com.epson.iprojection.ui.common.activity.funcs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.serenegiant.view.MessagePanelUtils;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class EndControler {
    private final Activity _activity;
    private final boolean _enabled;
    private boolean _isToastShowing = false;

    public EndControler(Activity activity, boolean z) {
        this._activity = activity;
        this._enabled = z;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this._enabled && i == 4) {
            if (this._isToastShowing) {
                Pj.getIns().onAppFinished();
                if (Pj.getIns().isWifiChanged()) {
                    showRestoreWifiManualDialog();
                } else {
                    this._activity.finish();
                }
            } else {
                if (Pj.getIns().isConnected()) {
                    ToastMgr.getIns().show(this._activity, ToastMgr.Type.ExitWhenConnecting);
                } else {
                    ToastMgr.getIns().show(this._activity, ToastMgr.Type.ExitWhenDisconnecting);
                }
                setToastShowing(true);
                new Timer(true).schedule(new Resetter(), MessagePanelUtils.MESSAGE_DURATION_SHORT);
            }
            return true;
        }
        return false;
    }

    private void showRestoreWifiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
        builder.setMessage(this._activity.getString(R.string._ChangedWiFiSettingsResetConfirmOld_));
        builder.setPositiveButton(this._activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.activity.funcs.EndControler$$ExternalSyntheticLambda2
            {
                EndControler.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EndControler.this.m197xfd7ec0f4(dialogInterface, i);
            }
        });
        builder.setNegativeButton(this._activity.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.activity.funcs.EndControler$$ExternalSyntheticLambda3
            {
                EndControler.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EndControler.this.m198xb7f46175(dialogInterface, i);
            }
        });
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    /* renamed from: lambda$showRestoreWifiDialog$0$com-epson-iprojection-ui-common-activity-funcs-EndControler */
    public /* synthetic */ void m197xfd7ec0f4(DialogInterface dialogInterface, int i) {
        Pj.getIns().restoreWifi();
        this._activity.finish();
    }

    /* renamed from: lambda$showRestoreWifiDialog$1$com-epson-iprojection-ui-common-activity-funcs-EndControler */
    public /* synthetic */ void m198xb7f46175(DialogInterface dialogInterface, int i) {
        Pj.getIns().setWifiConnecter(null);
        this._activity.finish();
    }

    private void showRestoreWifiManualDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
        builder.setMessage(this._activity.getString(R.string._ChangedWiFiSettingsResetConfirm_));
        builder.setPositiveButton(this._activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.activity.funcs.EndControler$$ExternalSyntheticLambda0
            {
                EndControler.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EndControler.this.m199xde2ac01c(dialogInterface, i);
            }
        });
        builder.setNegativeButton(this._activity.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.common.activity.funcs.EndControler$$ExternalSyntheticLambda1
            {
                EndControler.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EndControler.this.m200x98a0609d(dialogInterface, i);
            }
        });
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    /* renamed from: lambda$showRestoreWifiManualDialog$2$com-epson-iprojection-ui-common-activity-funcs-EndControler */
    public /* synthetic */ void m199xde2ac01c(DialogInterface dialogInterface, int i) {
        Pj.getIns().setWifiConnecter(null);
        Pj.getIns().removeWifiProfile();
        this._activity.finish();
    }

    /* renamed from: lambda$showRestoreWifiManualDialog$3$com-epson-iprojection-ui-common-activity-funcs-EndControler */
    public /* synthetic */ void m200x98a0609d(DialogInterface dialogInterface, int i) {
        Pj.getIns().setWifiConnecter(null);
        this._activity.finish();
    }

    public synchronized void setToastShowing(boolean z) {
        this._isToastShowing = z;
    }

    public void reset() {
        setToastShowing(false);
    }

    /* loaded from: classes.dex */
    public class Resetter extends TimerTask {
        Resetter() {
            EndControler.this = r1;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            EndControler.this.setToastShowing(false);
        }
    }
}
