package com.epson.iprojection.ui.engine_wrapper;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.moderator.ModeratorPassInputDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.ConnectOkDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.DeliveryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.PjKeywordInputDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryConnAfterDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.SpinDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.SpoilerDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.StartModeratorDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.WhiteboardDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class DialogCtrlr implements IDialogCtrlr {
    private static final int SPIN_DLG_SELFKILL_TIME = 30000;
    private static final int SPIN_DLG_SELFKILL_TIME_DELIVERY = 35000;
    private final IOnDialogEventListener _impl;
    private SpinDialog _spinDialog = null;
    private BaseDialog _dialog = null;

    public DialogCtrlr(IOnDialogEventListener iOnDialogEventListener) {
        this._impl = iOnDialogEventListener;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createInputDialog_Keyword(Context context, String str, int i) {
        createInputDialog(context, PjKeywordInputDialog.MessageType.Input, BaseDialog.ResultAction.CONNECT, str, i);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createInputDialog_KeywordAgain(Context context, String str, int i) {
        createInputDialog(context, PjKeywordInputDialog.MessageType.InputAgain, BaseDialog.ResultAction.CONNECT, str, i);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createQueryDialog_ConnectToUsingPj(Context context, String str) {
        createQueryDialog(context, QueryDialog.MessageType.ConnectToUsingPj, BaseDialog.ResultAction.CONNECT, str);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createQueryDialog_ConnectToUsingPjs(Context context, String str) {
        createQueryDialog(context, QueryDialog.MessageType.ConnectToUsingPjs, BaseDialog.ResultAction.CONNECT, str);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createQueryDialog(Context context, QueryDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        createQueryDialog(context, messageType, iOnDialogEventListener, resultAction, null);
    }

    private void createQueryDialog(Context context, QueryDialog.MessageType messageType, BaseDialog.ResultAction resultAction, String str) {
        createQueryDialog(context, messageType, this._impl, resultAction, str);
    }

    private void createQueryDialog(Context context, QueryDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction, String str) {
        clearAlertDialog();
        QueryDialog queryDialog = new QueryDialog(context, messageType, iOnDialogEventListener, resultAction, new String[]{str});
        this._dialog = queryDialog;
        queryDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createQueryDialog_ProjectionNow(Context context) {
        createQueryConnAfterDialog(context, BaseDialog.ResultAction.SELFPROJECTION);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createMsgDialog(Context context, MessageDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        clearAlertDialog();
        MessageDialog messageDialog = new MessageDialog(context, messageType, iOnDialogEventListener, resultAction);
        this._dialog = messageDialog;
        messageDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createMsgDialog(Context context, MessageDialog.MessageType messageType) {
        clearAlertDialog();
        MessageDialog messageDialog = new MessageDialog(context, messageType, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = messageDialog;
        messageDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createMsgAlertDialog(Context context, MessageDialog.MessageType messageType) {
        clearAlertDialog();
        MessageDialog messageDialog = new MessageDialog(context, messageType, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = messageDialog;
        messageDialog.setKindAlert();
        this._dialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createMsgDialog(Context context, MessageDialog.MessageType messageType, String str) {
        clearAlertDialog();
        MessageDialog messageDialog = new MessageDialog(context, messageType, this._impl, BaseDialog.ResultAction.NOACTION, str);
        this._dialog = messageDialog;
        messageDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createMsgDialog(Context context, String str) {
        clearAlertDialog();
        MessageDialog messageDialog = new MessageDialog(context, str, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = messageDialog;
        messageDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createSpoilerDialog(Context context, SpoilerDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        clearAlertDialog();
        SpoilerDialog spoilerDialog = new SpoilerDialog(context, messageType, iOnDialogEventListener, resultAction);
        this._dialog = spoilerDialog;
        spoilerDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createMsgAlertDialog(Context context, String str) {
        clearAlertDialog();
        MessageDialog messageDialog = new MessageDialog(context, str, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = messageDialog;
        messageDialog.setKindAlert();
        this._dialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createConnectOkToast(Context context, ConnectOkDialog.MessageType messageType, BaseDialog.ResultAction resultAction) {
        clearAlertDialog();
        this._dialog = null;
        ToastMgr.getIns().show(context, ToastMgr.Type.ConnectOK);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createDisconnectDialog(Context context, MessageDialog.MessageType messageType, boolean z) {
        if (context == null) {
            return;
        }
        clearAlertDialog();
        String message = MessageDialog.getMessage(context, messageType);
        if (z) {
            this._dialog = new QueryDialog(context, message + "\n\n" + context.getString(R.string._ChangedWiFiSettingsResetConfirm_), this._impl, BaseDialog.ResultAction.REMOVE_WIFIPROFILE);
        } else if (messageType == MessageDialog.MessageType.Disconnect) {
            this._dialog = null;
            ToastMgr.getIns().show(context, ToastMgr.Type.Disconnect);
        } else {
            this._dialog = new MessageDialog(context, message, this._impl, BaseDialog.ResultAction.NOACTION);
        }
        BaseDialog baseDialog = this._dialog;
        if (baseDialog != null) {
            baseDialog.create(context);
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createWhiteboardDialog(Context context) {
        clearAlertDialog();
        WhiteboardDialog whiteboardDialog = new WhiteboardDialog(context, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = whiteboardDialog;
        whiteboardDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createDeliveryDialog(Context context) {
        clearAlertDialog();
        DeliveryDialog deliveryDialog = new DeliveryDialog(context, DeliveryDialog.MessageType.Delivery, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = deliveryDialog;
        deliveryDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createStartModeratorDialog(Context context) {
        clearAlertDialog();
        StartModeratorDialog startModeratorDialog = new StartModeratorDialog(context, this._impl, BaseDialog.ResultAction.NOACTION);
        this._dialog = startModeratorDialog;
        startModeratorDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void createModeratorPassInputDialog(Context context, ArrayList<D_PjInfo> arrayList, boolean z) {
        clearAlertDialog();
        ModeratorPassInputDialog moderatorPassInputDialog = new ModeratorPassInputDialog(context, this._impl, BaseDialog.ResultAction.NOACTION, arrayList, z);
        this._dialog = moderatorPassInputDialog;
        moderatorPassInputDialog.create(context);
    }

    private void createInputDialog(Context context, PjKeywordInputDialog.MessageType messageType, BaseDialog.ResultAction resultAction, String str, int i) {
        Pj.getIns().requestDisplayKeyword(i);
        clearAlertDialog();
        PjKeywordInputDialog pjKeywordInputDialog = new PjKeywordInputDialog(context, messageType, this._impl, resultAction, new String[]{str});
        this._dialog = pjKeywordInputDialog;
        pjKeywordInputDialog.create(context);
    }

    private void createQueryConnAfterDialog(Context context, BaseDialog.ResultAction resultAction) {
        clearAlertDialog();
        QueryConnAfterDialog queryConnAfterDialog = new QueryConnAfterDialog(context, QueryConnAfterDialog.MessageType.ConnectedAfter, this._impl, resultAction);
        this._dialog = queryConnAfterDialog;
        queryConnAfterDialog.create(context);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void show() {
        BaseDialog baseDialog = this._dialog;
        if (baseDialog == null || baseDialog.isShowing()) {
            return;
        }
        Lg.d("dialog.show()");
        this._dialog.show();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void showConnectingDialog(Context context) {
        Lg.d("showConnectingDialog().show");
        clearAlertDialog();
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog == null || !(spinDialog == null || spinDialog.getType() == SpinDialog.MessageType.Connecting)) {
            clearSpinDialog();
            SpinDialog spinDialog2 = new SpinDialog(context, SpinDialog.MessageType.Connecting, SPIN_DLG_SELFKILL_TIME);
            this._spinDialog = spinDialog2;
            spinDialog2.show();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void showDisconnectingDialog(Context context) {
        Lg.d("showDisconnectingDialog().show");
        clearAlertDialog();
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog == null || !(spinDialog == null || spinDialog.getType() == SpinDialog.MessageType.Disconnecting)) {
            clearSpinDialog();
            SpinDialog spinDialog2 = new SpinDialog(context, SpinDialog.MessageType.Disconnecting, SPIN_DLG_SELFKILL_TIME);
            this._spinDialog = spinDialog2;
            spinDialog2.show();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void showSearchingDialog(Context context) {
        Lg.d("showSearchingDialog().show");
        clearAlertDialog();
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog == null || !(spinDialog == null || spinDialog.getType() == SpinDialog.MessageType.Searching)) {
            clearSpinDialog();
            SpinDialog spinDialog2 = new SpinDialog(context, SpinDialog.MessageType.Searching, SPIN_DLG_SELFKILL_TIME);
            this._spinDialog = spinDialog2;
            spinDialog2.show();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void clearSearchingDialog() {
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog == null || spinDialog.getType() != SpinDialog.MessageType.Searching) {
            return;
        }
        clearSpinDialog();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void showDeliveringDialog(Context context) {
        Lg.d("showConnectingDialog().show");
        clearAlertDialog();
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog == null || !(spinDialog == null || spinDialog.getType() == SpinDialog.MessageType.Delivering)) {
            clearSpinDialog();
            SpinDialog spinDialog2 = new SpinDialog(context, SpinDialog.MessageType.Delivering, SPIN_DLG_SELFKILL_TIME_DELIVERY);
            this._spinDialog = spinDialog2;
            spinDialog2.show();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void clearDeliveringDialog() {
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog == null || spinDialog.getType() != SpinDialog.MessageType.Delivering) {
            return;
        }
        clearSpinDialog();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public boolean isDeliveringDialog() {
        SpinDialog spinDialog = this._spinDialog;
        return spinDialog != null && spinDialog.getType() == SpinDialog.MessageType.Delivering;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void onActivityStart(Context context) {
        BaseDialog baseDialog = this._dialog;
        if (baseDialog != null) {
            baseDialog.delete();
            this._dialog.create(context);
            this._dialog.show();
        }
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog != null) {
            spinDialog.delete();
            this._spinDialog.recreate(context);
            this._spinDialog.show();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void clearDialog() {
        Lg.d("clear all dialog");
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog != null) {
            spinDialog.delete();
            this._spinDialog = null;
        }
        BaseDialog baseDialog = this._dialog;
        if (baseDialog != null) {
            baseDialog.delete();
            this._dialog = null;
        }
    }

    private void clearAlertDialog() {
        BaseDialog baseDialog = this._dialog;
        if (baseDialog != null) {
            baseDialog.delete();
            this._dialog = null;
        }
    }

    private void clearSpinDialog() {
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog != null) {
            spinDialog.delete();
            this._spinDialog = null;
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public void onAppFinished() {
        clearDialog();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public boolean hasDialog() {
        return (this._dialog == null && this._spinDialog == null) ? false : true;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr
    public boolean isShowingSpinDialog() {
        SpinDialog spinDialog = this._spinDialog;
        if (spinDialog != null) {
            return spinDialog.isShowing();
        }
        return false;
    }
}
