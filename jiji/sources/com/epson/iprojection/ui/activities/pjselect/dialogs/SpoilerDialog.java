package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class SpoilerDialog extends BaseHaveButtonDialog {
    protected String _spoilerMessage;
    protected String _spoilerTitle;

    /* loaded from: classes.dex */
    public enum MessageType {
        Nfc_RetryAndPowerOn
    }

    public SpoilerDialog(Context context, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction, String str, String str2, String str3, String[] strArr) {
        super(context, iOnDialogEventListener, null, str, strArr, resultAction);
        this._spoilerMessage = str2;
        this._spoilerTitle = str3;
    }

    public SpoilerDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this(context, iOnDialogEventListener, resultAction, getMessage(context, messageType), getSpoilerMessage(context, messageType), getSpoilerTitle(context, messageType), getButtonText(context, messageType));
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog, com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void setConfig(Context context, AlertDialog.Builder builder) {
        super.setConfig(context, builder);
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.dialog_spoiler, (ViewGroup) null);
        builder.setView(inflate);
        final TextView textView = (TextView) inflate.findViewById(R.id.text_sub);
        textView.setText(this._spoilerMessage);
        textView.setVisibility(8);
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.checkbox_spoiler);
        checkBox.setText(this._spoilerTitle);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.SpoilerDialog$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SpoilerDialog.lambda$setConfig$0(textView, compoundButton, z);
            }
        });
    }

    public static /* synthetic */ void lambda$setConfig$0(TextView textView, CompoundButton compoundButton, boolean z) {
        if (z) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
    }

    private static String getMessage(Context context, MessageType messageType) {
        return messageType == MessageType.Nfc_RetryAndPowerOn ? context.getString(R.string._NFCRetryAndPowerOn_) : "";
    }

    private static String getSpoilerMessage(Context context, MessageType messageType) {
        return messageType == MessageType.Nfc_RetryAndPowerOn ? context.getString(R.string._NFCAutoPowerOnSpoiler_) : "";
    }

    private static String getSpoilerTitle(Context context, MessageType messageType) {
        return messageType == MessageType.Nfc_RetryAndPowerOn ? context.getString(R.string._UseAutoPowerOn_) : "";
    }

    private static String[] getButtonText(Context context, MessageType messageType) {
        String[] strArr = new String[2];
        if (messageType == MessageType.Nfc_RetryAndPowerOn) {
            strArr[0] = context.getString(R.string._Reconnect_);
            strArr[1] = context.getString(R.string._Cancel_);
            return strArr;
        }
        return null;
    }
}
