package com.epson.iprojection.ui.activities.marker.config;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class BaseDialogToolConfig {
    protected Activity _activity;
    protected IToolConfig _config;
    protected Dialog _dialog;
    protected IUpdateConfigListener _impl;
    protected LinearLayout _layout;

    protected abstract int getDialogLayoutId();

    protected abstract void setBackGround();

    public BaseDialogToolConfig(Activity activity, IToolConfig iToolConfig, IUpdateConfigListener iUpdateConfigListener) {
        this._activity = activity;
        this._config = iToolConfig;
        this._impl = iUpdateConfigListener;
        initDialog(getDialogLayoutId());
        setBackGround();
    }

    protected void initDialog(int i) {
        this._layout = (LinearLayout) ((LayoutInflater) this._activity.getSystemService("layout_inflater")).inflate(i, (ViewGroup) null);
        Dialog dialog = new Dialog(this._activity);
        this._dialog = dialog;
        dialog.requestWindowFeature(1);
        this._dialog.setContentView(this._layout);
        this._dialog.getWindow().setFlags(0, 2);
        WindowManager.LayoutParams attributes = this._dialog.getWindow().getAttributes();
        attributes.gravity = 8388659;
        Resources resources = this._activity.getResources();
        attributes.y = (int) resources.getDimension(R.dimen.ActionBarHeight);
        attributes.x = resources.getDimensionPixelSize(R.dimen.MarkerConfigDialogLeftAdjust);
        this._dialog.getWindow().setAttributes(attributes);
        this._dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.epson.iprojection.ui.activities.marker.config.BaseDialogToolConfig$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BaseDialogToolConfig.this.m93x70b3943c(dialogInterface);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$initDialog$0$com-epson-iprojection-ui-activities-marker-config-BaseDialogToolConfig  reason: not valid java name */
    public /* synthetic */ void m93x70b3943c(DialogInterface dialogInterface) {
        this._impl.onUpdateConfig();
    }

    public int getButtonId() {
        return this._config.getBtnId();
    }

    public void show() {
        if (this._dialog.isShowing()) {
            return;
        }
        this._dialog.show();
    }

    public void dismiss() {
        if (this._dialog.isShowing()) {
            this._dialog.dismiss();
        }
    }
}
