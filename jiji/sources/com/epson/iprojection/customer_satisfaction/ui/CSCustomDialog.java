package com.epson.iprojection.customer_satisfaction.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import com.epson.iprojection.customer_satisfaction.presenters.SendActionInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CSCustomDialog.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/ui/CSCustomDialog;", "Landroidx/fragment/app/DialogFragment;", "importView", "Landroid/view/View;", "callback", "Lcom/epson/iprojection/customer_satisfaction/presenters/SendActionInterface;", "message", "", "positiveButtonName", "negativeButtonName", "(Landroid/view/View;Lcom/epson/iprojection/customer_satisfaction/presenters/SendActionInterface;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "onCancel", "", "dialog", "Landroid/content/DialogInterface;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CSCustomDialog extends DialogFragment {
    private final SendActionInterface callback;
    private final View importView;
    private final String message;
    private final String negativeButtonName;
    private final String positiveButtonName;

    public static /* synthetic */ void $r8$lambda$3Q8XrGbe4tVusS8cOpQbW010wKk(CSCustomDialog cSCustomDialog, DialogInterface dialogInterface, int i) {
        onCreateDialog$lambda$1(cSCustomDialog, dialogInterface, i);
    }

    public static /* synthetic */ void $r8$lambda$R01oLZuerJ1qhz5sc4lG4zZ8ehc(CSCustomDialog cSCustomDialog, DialogInterface dialogInterface, int i) {
        onCreateDialog$lambda$0(cSCustomDialog, dialogInterface, i);
    }

    public CSCustomDialog(View importView, SendActionInterface callback, String message, String positiveButtonName, String negativeButtonName) {
        Intrinsics.checkNotNullParameter(importView, "importView");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(positiveButtonName, "positiveButtonName");
        Intrinsics.checkNotNullParameter(negativeButtonName, "negativeButtonName");
        this.importView = importView;
        this.callback = callback;
        this.message = message;
        this.positiveButtonName = positiveButtonName;
        this.negativeButtonName = negativeButtonName;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog create = new AlertDialog.Builder(requireActivity()).setMessage(this.message).setView(this.importView).setPositiveButton(this.positiveButtonName, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.customer_satisfaction.ui.CSCustomDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CSCustomDialog.$r8$lambda$R01oLZuerJ1qhz5sc4lG4zZ8ehc(CSCustomDialog.this, dialogInterface, i);
            }
        }).setNegativeButton(this.negativeButtonName, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.customer_satisfaction.ui.CSCustomDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CSCustomDialog.$r8$lambda$3Q8XrGbe4tVusS8cOpQbW010wKk(CSCustomDialog.this, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(create, "Builder(requireActivity(…  }\n            .create()");
        return create;
    }

    public static final void onCreateDialog$lambda$0(CSCustomDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.callback.onSend();
    }

    public static final void onCreateDialog$lambda$1(CSCustomDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.callback.onCancel();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            alertDialog.getButton(-1).setEnabled(false);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        super.onCancel(dialog);
        this.callback.onCancel();
    }
}
