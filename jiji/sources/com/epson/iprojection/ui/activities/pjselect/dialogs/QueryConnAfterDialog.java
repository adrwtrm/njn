package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class QueryConnAfterDialog extends BaseHaveButtonDialog {

    /* loaded from: classes.dex */
    public enum MessageType {
        ConnectedAfter
    }

    public QueryConnAfterDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, null, getMsg(context, messageType), new String[]{context.getString(R.string._ProjectNow_), context.getString(R.string._ProjectLater_)}, resultAction);
    }

    private static String getMsg(Context context, MessageType messageType) {
        if (messageType == MessageType.ConnectedAfter) {
            return context.getString(R.string._Connected_);
        }
        return null;
    }
}
