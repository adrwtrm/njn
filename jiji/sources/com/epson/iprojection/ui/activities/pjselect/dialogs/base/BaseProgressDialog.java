package com.epson.iprojection.ui.activities.pjselect.dialogs.base;

import android.app.Activity;
import android.content.Context;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.uiparts.ProgressDialogInfinityType;

/* loaded from: classes.dex */
public class BaseProgressDialog {
    protected Context _context;
    protected ProgressDialogInfinityType _dialog;

    public BaseProgressDialog(Context context) {
        if (context == null) {
            return;
        }
        this._context = context;
        ProgressDialogInfinityType progressDialogInfinityType = new ProgressDialogInfinityType(context);
        this._dialog = progressDialogInfinityType;
        progressDialogInfinityType.setCancelable(false);
    }

    public void show() {
        ProgressDialogInfinityType progressDialogInfinityType;
        try {
            if (!isActivityForeGround((PjConnectableActivity) this._context) || ActivityGetter.getIns().isAppFinished() || (progressDialogInfinityType = this._dialog) == null) {
                return;
            }
            progressDialogInfinityType.show();
        } catch (ClassCastException unused) {
        }
    }

    public void delete() {
        ProgressDialogInfinityType progressDialogInfinityType;
        Activity activity = (Activity) this._context;
        if (activity == null) {
            Lg.e("contextがactivityに変換できませんでした");
        } else if (ActivityGetter.getIns().isAppFinished() || activity.isFinishing() || (progressDialogInfinityType = this._dialog) == null) {
        } else {
            try {
                progressDialogInfinityType.dismiss();
            } catch (IllegalArgumentException e) {
                Lg.e("IllegalArgumentException! : " + e.getMessage());
            }
        }
    }

    private boolean isActivityForeGround(PjConnectableActivity pjConnectableActivity) {
        return (pjConnectableActivity == null || pjConnectableActivity.isFinishing() || !pjConnectableActivity.isForeGroundThisActivity()) ? false : true;
    }
}
