package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class ConnectOkDialog extends BaseHaveButtonDialog {
    private final MessageType _type;

    /* loaded from: classes.dex */
    public enum MessageType {
        ConnectOK
    }

    public ConnectOkDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, null, getMsg(context, messageType), new String[]{context.getString(R.string._OK_)}, resultAction);
        this._type = messageType;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog, com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void create(Context context) {
        super.create(context);
        setConfig(context, this._builder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog, com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void setConfig(Context context, AlertDialog.Builder builder) {
        builder.setMessage(getMsg(context, this._type));
        builder.setTitle(this._title);
        builder.setMessage(this._message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.ConnectOkDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConnectOkDialog.this.m134xa229ea34(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-pjselect-dialogs-ConnectOkDialog  reason: not valid java name */
    public /* synthetic */ void m134xa229ea34(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            this._impl.onClickDialogOK("", this._action);
        }
    }

    private static String getMsg(Context context, MessageType messageType) {
        if (messageType == MessageType.ConnectOK) {
            return context.getString(R.string._Connected_);
        }
        return null;
    }
}
