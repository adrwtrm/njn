package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class QueryDialog extends BaseHaveButtonDialog {

    /* loaded from: classes.dex */
    public enum MessageType {
        Connect,
        ConnectToUsingPj,
        ConnectToUsingPjs,
        Disconnect,
        AppUpdate
    }

    public QueryDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction, String[] strArr) {
        super(context, iOnDialogEventListener, null, getMsg(context, messageType, strArr), new String[]{context.getString(R.string._OK_), context.getString(R.string._Cancel_)}, resultAction);
    }

    public QueryDialog(Context context, String str, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, null, str, new String[]{context.getString(R.string._OK_), context.getString(R.string._Cancel_)}, resultAction);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType = iArr;
            try {
                iArr[MessageType.Connect.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType[MessageType.ConnectToUsingPj.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType[MessageType.ConnectToUsingPjs.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType[MessageType.Disconnect.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType[MessageType.AppUpdate.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static String getMsg(Context context, MessageType messageType, String[] strArr) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$QueryDialog$MessageType[messageType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 5) {
                            return null;
                        }
                        return context.getString(R.string._AppUpdateNow_);
                    }
                    return context.getString(R.string._ConfirmDisconnect_);
                }
                return strArr[0] + context.getString(R.string._AreUsing_) + context.getString(R.string._ContinueConnectionProcess_);
            }
            return String.format(context.getString(R.string._IsUsing_), strArr[0]) + context.getString(R.string._ContinueConnectionProcess_);
        }
        return context.getString(R.string._DoYouConnectProjector_);
    }
}
