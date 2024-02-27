package com.epson.iprojection.ui.activities.moderator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.epson.iprojection.R;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ModeratorPassInputDialog extends BaseDialog {
    private EditText _editText;
    private final boolean _isConnect;
    private final ArrayList<D_PjInfo> _passPjs;

    public ModeratorPassInputDialog(Context context, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction, ArrayList<D_PjInfo> arrayList, boolean z) {
        super(context, iOnDialogEventListener, resultAction);
        this._isConnect = z;
        this._passPjs = arrayList;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void create(Context context) {
        super.create(context);
        setConfig(context, this._builder);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    protected void setConfig(Context context, AlertDialog.Builder builder) {
        ArrayList<D_PjInfo> arrayList = this._passPjs;
        if (arrayList != null && arrayList.size() > 0) {
            StringBuilder sb = new StringBuilder(context.getString(R.string._ModeratorPassExist_) + "\n");
            Iterator<D_PjInfo> it = this._passPjs.iterator();
            while (it.hasNext()) {
                sb.append(it.next().PrjName).append("\n");
            }
            sb.append("\n");
            if (this._passPjs.size() == 1) {
                sb.append(context.getString(R.string._ModeratorPassAsk_));
            } else {
                sb.append(context.getString(R.string._ModeratorPassAnyOf_));
            }
            builder.setMessage(sb);
        } else {
            builder.setMessage(context.getString(R.string._ModeratorPassAsk_));
        }
        EditText createEditText = createEditText(context);
        this._editText = createEditText;
        builder.setView(createEditText);
        builder.setPositiveButton(R.string._OK_, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.ModeratorPassInputDialog$$ExternalSyntheticLambda0
            {
                ModeratorPassInputDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ModeratorPassInputDialog.this.m102x1ece5943(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string._Cancel_, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.ModeratorPassInputDialog$$ExternalSyntheticLambda1
            {
                ModeratorPassInputDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ModeratorPassInputDialog.this.m103xa1190e22(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-moderator-ModeratorPassInputDialog  reason: not valid java name */
    public /* synthetic */ void m102x1ece5943(DialogInterface dialogInterface, int i) {
        String string;
        hideSoftKeyboard();
        int mppControlMode = Pj.getIns().setMppControlMode(1, this._editText.getText().toString().getBytes());
        if (mppControlMode == 0) {
            MultiProjectionDisplaySettings.INSTANCE.setThumb(false);
            this._action = BaseDialog.ResultAction.WAIT_MODERATOR_RESULT;
            string = "";
        } else if (mppControlMode == -802) {
            if (this._isConnect) {
                string = this._context.getString(R.string._IncorrectModeratorPassword_);
                this._action = BaseDialog.ResultAction.SHOW_MESSAGE_ALERT;
            } else {
                string = this._context.getString(R.string._ModeratorPassIncorrect_);
                this._action = BaseDialog.ResultAction.SHOW_MESSAGE;
            }
        } else if (Pj.getIns().existsOtherModerator()) {
            string = MessageDialog.getMessage(this._context, MessageDialog.MessageType.AlreadyModerator);
            if (MessageDialog.isModeratorMenu()) {
                this._action = BaseDialog.ResultAction.SHOW_MESSAGE;
            } else {
                this._action = BaseDialog.ResultAction.SHOW_MESSAGE_ALERT;
            }
        } else {
            string = this._context.getString(R.string._CannotBecomeModerator_);
            this._action = BaseDialog.ResultAction.SHOW_MESSAGE;
        }
        if (this._impl != null) {
            this._impl.onClickDialogOK(string, this._action);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$1$com-epson-iprojection-ui-activities-moderator-ModeratorPassInputDialog  reason: not valid java name */
    public /* synthetic */ void m103xa1190e22(DialogInterface dialogInterface, int i) {
        hideSoftKeyboard();
        if (this._impl != null) {
            this._impl.onClickDialogNG(this._action);
        }
    }

    private void hideSoftKeyboard() {
        ((InputMethodManager) this._context.getSystemService("input_method")).hideSoftInputFromWindow(this._editText.getWindowToken(), 2);
    }

    private EditText createEditText(Context context) {
        EditText editText = new EditText(context);
        editText.setInputType(2);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        editText.setInputType(129);
        return editText;
    }
}
