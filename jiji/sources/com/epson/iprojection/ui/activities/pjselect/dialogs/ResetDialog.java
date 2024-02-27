package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class ResetDialog extends Dialog {
    protected ResultAction _action;
    protected IOnDialogEventListener _impl;

    /* loaded from: classes.dex */
    public enum ResultAction {
        RESET
    }

    public ResetDialog(Context context, IOnDialogEventListener iOnDialogEventListener, int i, int i2) {
        super(context, R.style.FullHeightDialog);
        this._impl = iOnDialogEventListener;
        setContentView(R.layout.dialog_reset);
        Button button = (Button) findViewById(R.id.reset_ok);
        ((TextView) findViewById(R.id.reset_title)).setText(context.getString(i));
        ((TextView) findViewById(R.id.reset_textview)).setText(context.getString(i2));
        button.setTextColor(-1);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.ResetDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ResetDialog.this.m139x1f933c41(view);
            }
        });
        ((Button) findViewById(R.id.reset_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.ResetDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ResetDialog.this.m140x89c2c460(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-activities-pjselect-dialogs-ResetDialog  reason: not valid java name */
    public /* synthetic */ void m139x1f933c41(View view) {
        IOnDialogEventListener iOnDialogEventListener = this._impl;
        if (iOnDialogEventListener != null) {
            iOnDialogEventListener.onClickDialogOK("", null);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-epson-iprojection-ui-activities-pjselect-dialogs-ResetDialog  reason: not valid java name */
    public /* synthetic */ void m140x89c2c460(View view) {
        IOnDialogEventListener iOnDialogEventListener = this._impl;
        if (iOnDialogEventListener != null) {
            iOnDialogEventListener.onClickDialogNG(null);
        }
        dismiss();
    }
}
