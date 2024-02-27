package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.widget.EditText;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class PjKeywordInputDialog extends BaseDialog {
    private EditText _editText;
    private final String[] _msg;
    private final MessageType _type;

    /* loaded from: classes.dex */
    public enum MessageType {
        Input,
        InputAgain
    }

    public PjKeywordInputDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction, String[] strArr) {
        super(context, iOnDialogEventListener, resultAction);
        this._editText = null;
        this._type = messageType;
        this._msg = strArr;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void create(Context context) {
        super.create(context);
        setConfig(context, this._builder);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    protected void setConfig(Context context, AlertDialog.Builder builder) {
        builder.setMessage(getMsg(context, this._type, this._msg));
        EditText createEditText = createEditText(context);
        this._editText = createEditText;
        builder.setView(createEditText);
        builder.setPositiveButton(R.string._OK_, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.PjKeywordInputDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PjKeywordInputDialog.this.m137x77ff5a7d(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string._Cancel_, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.PjKeywordInputDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PjKeywordInputDialog.this.m138xabad853e(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-pjselect-dialogs-PjKeywordInputDialog  reason: not valid java name */
    public /* synthetic */ void m137x77ff5a7d(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            this._impl.onClickDialogOK(this._editText.getText().toString(), this._action);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$1$com-epson-iprojection-ui-activities-pjselect-dialogs-PjKeywordInputDialog  reason: not valid java name */
    public /* synthetic */ void m138xabad853e(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            this._impl.onClickDialogNG(this._action);
        }
    }

    private EditText createEditText(Context context) {
        EditText editText = new EditText(context);
        editText.setInputType(18);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        return editText;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.dialogs.PjKeywordInputDialog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$PjKeywordInputDialog$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$PjKeywordInputDialog$MessageType = iArr;
            try {
                iArr[MessageType.Input.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$PjKeywordInputDialog$MessageType[MessageType.InputAgain.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private String getMsg(Context context, MessageType messageType, String[] strArr) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$PjKeywordInputDialog$MessageType[messageType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            return context.getString(R.string._InputKeywordAgain_) + " " + strArr[0];
        }
        return context.getString(R.string._PjKeywordInput_) + " " + strArr[0];
    }
}
