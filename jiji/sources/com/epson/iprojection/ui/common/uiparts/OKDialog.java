package com.epson.iprojection.ui.common.uiparts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OKDialog.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006B#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB-\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/ui/common/uiparts/OKDialog;", "", "a", "Landroid/app/Activity;", "str", "", "(Landroid/app/Activity;Ljava/lang/String;)V", "isCancelable", "", "(Landroid/app/Activity;Ljava/lang/String;Z)V", "impl", "Landroid/content/DialogInterface$OnClickListener;", "(Landroid/app/Activity;Ljava/lang/String;ZLandroid/content/DialogInterface$OnClickListener;)V", "_dialog", "Landroid/app/AlertDialog;", "isShowing", "()Z", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OKDialog {
    private AlertDialog _dialog;

    public OKDialog(Activity activity, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(str);
        builder.setCancelable(true);
        Intrinsics.checkNotNull(activity);
        builder.setPositiveButton(activity.getString(R.string._OK_), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        Intrinsics.checkNotNullExpressionValue(create, "alertDialogBuilder.create()");
        this._dialog = create;
        create.show();
    }

    public OKDialog(Activity activity, String str, boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(str);
        builder.setCancelable(z);
        Intrinsics.checkNotNull(activity);
        builder.setPositiveButton(activity.getString(R.string._OK_), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        Intrinsics.checkNotNullExpressionValue(create, "alertDialogBuilder.create()");
        this._dialog = create;
        create.show();
    }

    public OKDialog(Activity activity, String str, boolean z, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(str);
        builder.setCancelable(z);
        Intrinsics.checkNotNull(activity);
        builder.setPositiveButton(activity.getString(R.string._OK_), onClickListener);
        AlertDialog create = builder.create();
        Intrinsics.checkNotNullExpressionValue(create, "alertDialogBuilder.create()");
        this._dialog = create;
        create.show();
    }

    public final boolean isShowing() {
        return this._dialog.isShowing();
    }
}
