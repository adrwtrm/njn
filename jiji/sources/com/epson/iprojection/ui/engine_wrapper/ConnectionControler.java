package com.epson.iprojection.ui.engine_wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetworkInfoUtilsKt;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.engine.common.D_DeliveryError;
import com.epson.iprojection.engine.common.D_ImageProcTime;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.engine.common.D_ThumbnailError;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.moderator.MultiProjectionDisplaySettings;
import com.epson.iprojection.ui.activities.pjselect.dialogs.ConnectOkDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.SpoilerDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardActivity;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardUtils;
import com.epson.iprojection.ui.common.AppStatus;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eConnectErrorDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.epson.iprojection.ui.common.uiparts.RestoreWifiNotification;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IDialogCtrlr;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnMinConnectListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConnectionControler implements IOnDialogEventListener {
    private Context _context;
    private String _deliveredImagePath;
    private ArrayList<ConnectPjInfo> _conPjInfo = null;
    private boolean _bCheckedUsing = false;
    private int _nKeyCheckPJ = -1;
    private boolean _bCheckedSomething = false;
    private ConnectPjInfo _stKeywordAgain = null;
    private IOnConnectListener.DisconedReason _disconedReason = IOnConnectListener.DisconedReason.Nothing;
    private IOnConnectListener _implConnect = null;
    private IOnMinConnectListener _implOnMinConnectListener = null;
    private final HashSet<StackDialog> _stackDialog = new HashSet<>();
    private boolean _stackIsConnect = true;
    private final IDialogCtrlr _dlgCtrlr = new DialogCtrlr(this);

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum StackDialog {
        None,
        ModeratorPassword,
        AlreadyModerator,
        WhiteboardDialog
    }

    public ConnectionControler(Context context) {
        this._context = context;
    }

    public void setContext(Context context) {
        this._context = context;
    }

    public void tryConnect(ArrayList<ConnectPjInfo> arrayList, boolean z) {
        this._conPjInfo = arrayList;
        this._nKeyCheckPJ = -1;
        this._bCheckedUsing = false;
        this._bCheckedSomething = false;
        this._stKeywordAgain = null;
        tryConnectSub(z);
    }

    private void tryConnectSub(boolean z) {
        if (Pj.getIns().isLinkageDataSearching()) {
            Iterator<ConnectPjInfo> it = this._conPjInfo.iterator();
            while (it.hasNext()) {
                ConnectPjInfo next = it.next();
                if (next.getPjInfo().isSupportConnectionIdentifier) {
                    next.setKeyword(CommonDefine.SECRET_PJ_KEYWORD_NEW);
                } else {
                    next.setKeyword(CommonDefine.SECRET_PJ_KEYWORD);
                }
            }
            Pj.getIns().setLinkageDataSearchingMode(false);
            callConnect();
        } else if ((this._bCheckedUsing || !isUsing(this._conPjInfo)) && !isNeedKeyword(this._conPjInfo)) {
            if (this._bCheckedSomething) {
                callConnect();
            } else if (z) {
                callConnect();
            } else {
                this._nKeyCheckPJ = this._conPjInfo.size() - 1;
                callConnect();
            }
        }
    }

    public void tryRegister(ArrayList<ConnectPjInfo> arrayList) {
        Pj.getIns().addRegisteredPjInf(arrayList);
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onRegisterSucceed();
        }
    }

    private boolean isUsing(ArrayList<ConnectPjInfo> arrayList) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < this._conPjInfo.size(); i2++) {
            ConnectPjInfo connectPjInfo = arrayList.get(i2);
            if (connectPjInfo.getPjInfo().Status == 2) {
                Lg.d("【ダイアログ】使用中PJに接続");
                if (i != 0) {
                    sb.append("\n");
                }
                sb.append(connectPjInfo.getPjInfo().PrjName);
                i++;
            }
        }
        if (1 < i) {
            sb.append("\n");
        }
        if (i > 0) {
            if (1 == i) {
                this._dlgCtrlr.createQueryDialog_ConnectToUsingPj(this._context, sb.toString());
            } else {
                this._dlgCtrlr.createQueryDialog_ConnectToUsingPjs(this._context, sb.toString());
            }
            this._dlgCtrlr.show();
            this._bCheckedSomething = true;
            return true;
        }
        this._bCheckedUsing = true;
        return false;
    }

    private boolean isNeedKeyword(ArrayList<ConnectPjInfo> arrayList) {
        if (this._nKeyCheckPJ == arrayList.size()) {
            return false;
        }
        for (int i = this._nKeyCheckPJ + 1; i < arrayList.size(); i++) {
            ConnectPjInfo connectPjInfo = arrayList.get(i);
            if (connectPjInfo.getPjInfo().isNeededPjKeyword) {
                Lg.d("【ダイアログ】キーワード入力");
                this._dlgCtrlr.createInputDialog_Keyword(this._context, connectPjInfo.getPjInfo().PrjName, connectPjInfo.getPjInfo().ProjectorID);
                this._nKeyCheckPJ = i;
                this._dlgCtrlr.show();
                this._bCheckedSomething = true;
                return true;
            }
        }
        this._nKeyCheckPJ = arrayList.size();
        return false;
    }

    public void onConnectSucceed(boolean z, boolean z2, boolean z3) {
        Lg.d("【ダイアログ】接続OK");
        if (z2) {
            this._stackDialog.add(StackDialog.WhiteboardDialog);
        }
        this._dlgCtrlr.clearDialog();
        if (!z) {
            this._dlgCtrlr.createConnectOkToast(this._context, ConnectOkDialog.MessageType.ConnectOK, BaseDialog.ResultAction.NOACTION);
            showStackDialog();
        } else if (z2) {
            this._dlgCtrlr.createConnectOkToast(this._context, ConnectOkDialog.MessageType.ConnectOK, BaseDialog.ResultAction.NOACTION);
            showStackDialog();
        } else if (z3) {
            this._dlgCtrlr.createQueryDialog_ProjectionNow(this._context);
        }
        this._dlgCtrlr.show();
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onConnectSucceed();
        }
        IOnMinConnectListener iOnMinConnectListener = this._implOnMinConnectListener;
        if (iOnMinConnectListener != null) {
            iOnMinConnectListener.onConnectionSucceeded();
        }
    }

    public void onRegistered() {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onRegisterSucceed();
        }
    }

    public void showQueryDialog(QueryDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this._dlgCtrlr.createQueryDialog(this._context, messageType, iOnDialogEventListener, resultAction);
        this._dlgCtrlr.show();
    }

    public void showMsgDialog(MessageDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this._dlgCtrlr.createMsgDialog(this._context, messageType, iOnDialogEventListener, resultAction);
        this._dlgCtrlr.show();
    }

    public void showSpoilerDialog(SpoilerDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this._dlgCtrlr.createSpoilerDialog(this._context, messageType, iOnDialogEventListener, resultAction);
        this._dlgCtrlr.show();
    }

    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
        this._dlgCtrlr.clearDialog();
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[failReason.ordinal()]) {
            case 1:
                if (this._stKeywordAgain != null) {
                    Lg.d("既に他のプロジェクターで間違って再入力中。");
                    return;
                }
                for (int i2 = 0; i2 < this._conPjInfo.size(); i2++) {
                    ConnectPjInfo connectPjInfo = this._conPjInfo.get(i2);
                    if (connectPjInfo != null && connectPjInfo.getPjInfo().ProjectorID == i) {
                        Lg.d("【ダイアログ】キーワードが違います。再入力");
                        this._stKeywordAgain = connectPjInfo;
                        return;
                    }
                }
                break;
                break;
            case 2:
                Lg.d("【ダイアログ】利用できないNPのバージョンです");
                this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.NpVersionErr);
                break;
            case 3:
                Lg.d("【ダイアログ】MPPミラーリング構成が違います");
                this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.DiffCombiPJ);
                break;
            case 4:
                Lg.d("【ダイアログ】MPPで既に最大数参加中です");
                this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.MppMaxUser);
                break;
            case 5:
                Lg.d("【ダイアログ】使用不可能なプロジェクターが含まれる");
                this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.IncludeUnavailable);
                break;
            case 6:
                Lg.d("【ダイアログ】プロジェクターの電源がオフ");
                this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.Standby);
                break;
            case 7:
                Lg.d("【ダイアログ】接続NG");
                this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.ConnectNG);
                break;
        }
        this._dlgCtrlr.show();
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onConnectFail(i, failReason);
        }
        IOnMinConnectListener iOnMinConnectListener = this._implOnMinConnectListener;
        if (iOnMinConnectListener != null) {
            iOnMinConnectListener.onConnectionFailed(i, failReason);
        }
    }

    public void onConnectFailForGA(IOnConnectListener.FailReason failReason) {
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[failReason.ordinal()]) {
            case 1:
                Analytics.getIns().setConnectErrorEvent(eConnectErrorDimension.InvalidKeyword);
                Analytics.getIns().sendEvent(eCustomEvent.CONNECT_ERROR);
                break;
            case 2:
                Analytics.getIns().setConnectErrorEvent(eConnectErrorDimension.InvalidMppVersion);
                Analytics.getIns().sendEvent(eCustomEvent.CONNECT_ERROR);
                break;
            case 3:
                Analytics.getIns().setConnectErrorEvent(eConnectErrorDimension.InvalidMirroringConstitution);
                Analytics.getIns().sendEvent(eCustomEvent.CONNECT_ERROR);
                break;
            case 4:
                Analytics.getIns().setConnectErrorEvent(eConnectErrorDimension.OverConnectMax);
                Analytics.getIns().sendEvent(eCustomEvent.CONNECT_ERROR);
                break;
            case 5:
            case 6:
            case 7:
                if (NetworkInfoUtilsKt.isActiveNetworkMobile(this._context)) {
                    Analytics.getIns().setConnectErrorEvent(eConnectErrorDimension.MobileCommunicationOn);
                } else {
                    Analytics.getIns().setConnectErrorEvent(eConnectErrorDimension.Other);
                }
                Analytics.getIns().sendEvent(eCustomEvent.CONNECT_ERROR);
                break;
        }
        Analytics.getIns().incrementConnectionErrorCount();
    }

    public void onChangeMppControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onChangeMPPControlMode(mppControlMode);
        }
    }

    public void onUpdateMppUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onUpdateMPPUserList(arrayList, arrayList2);
        }
    }

    public void onChangeMppLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onChangeMPPLayout(arrayList);
        }
    }

    public void onChangeScreenLockStatus(boolean z) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onChangeScreenLockStatus(z);
        }
    }

    public void onChangedScreenLockByMe(boolean z) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onChangedScreenLockByMe(z);
        }
    }

    public void clearDeliveredImagePath() {
        this._deliveredImagePath = null;
    }

    public String getDeliveredImagePath() {
        return this._deliveredImagePath;
    }

    public void onDeliverImage(String str, D_DeliveryPermission d_DeliveryPermission) {
        this._dlgCtrlr.clearDeliveringDialog();
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onDeliverImage(str, d_DeliveryPermission);
        }
        this._deliveredImagePath = str;
        clearStackDialog();
        if (Pj.getIns().isModerator()) {
            ToastMgr.getIns().show(this._context, ToastMgr.Type.Delivered);
            return;
        }
        ToastMgr.getIns().show(this._context, ToastMgr.Type.Received);
        Analytics.getIns().sendEvent(eCustomEvent.RECEIVED_TRANSFER_IMAGE);
    }

    public void clearDeliveringDialog() {
        this._dlgCtrlr.clearDeliveringDialog();
    }

    public void onDeliveryError(D_DeliveryError d_DeliveryError) {
        boolean isDeliveringDialog = this._dlgCtrlr.isDeliveringDialog();
        this._dlgCtrlr.clearDeliveringDialog();
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onDeliveryError(d_DeliveryError);
        }
        if (Pj.getIns().isModerator()) {
            Context context = this._context;
            Toast.makeText(context, context.getString(R.string._CannotShare_), 1).show();
        }
        if (isDeliveringDialog) {
            if (d_DeliveryError.errorKind == 0) {
                switch (d_DeliveryError.errorCode) {
                    case 0:
                    case 4:
                        Context context2 = this._context;
                        new OKDialog((Activity) context2, context2.getString(R.string._SharingNGNowProcessing_));
                        return;
                    case 1:
                        Context context3 = this._context;
                        new OKDialog((Activity) context3, context3.getString(R.string._SharingNGCauseImages_));
                        return;
                    case 2:
                    case 3:
                        Context context4 = this._context;
                        new OKDialog((Activity) context4, context4.getString(R.string._DeliverNGDeliverImage_));
                        return;
                    case 5:
                        Context context5 = this._context;
                        new OKDialog((Activity) context5, context5.getString(R.string._DeliverNGDeliveryProcessing_));
                        return;
                    case 6:
                        Context context6 = this._context;
                        new OKDialog((Activity) context6, context6.getString(R.string._SharingNGCauseNetworkError_));
                        return;
                    default:
                        Context context7 = this._context;
                        new OKDialog((Activity) context7, context7.getString(R.string._CannotShare_));
                        return;
                }
            } else if (d_DeliveryError.errorKind == 0) {
                switch (d_DeliveryError.errorCode) {
                    case 0:
                    case 4:
                        Context context8 = this._context;
                        new OKDialog((Activity) context8, context8.getString(R.string._SharingNGNowProcessing_));
                        return;
                    case 1:
                        Context context9 = this._context;
                        new OKDialog((Activity) context9, context9.getString(R.string._SharingNGCauseImages_));
                        return;
                    case 2:
                    case 3:
                        Context context10 = this._context;
                        new OKDialog((Activity) context10, context10.getString(R.string._DeliverNGDeliverImage_));
                        return;
                    case 5:
                        Context context11 = this._context;
                        new OKDialog((Activity) context11, context11.getString(R.string._DeliverNGDeliveryProcessing_));
                        return;
                    case 6:
                        Context context12 = this._context;
                        new OKDialog((Activity) context12, context12.getString(R.string._SharingNGCauseNetworkError_));
                        return;
                    default:
                        Context context13 = this._context;
                        new OKDialog((Activity) context13, context13.getString(R.string._CannotShare_));
                        return;
                }
            }
        }
    }

    public void onStartDelivery() {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onStartDelivery();
        }
    }

    public void onEndDelivery() {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onEndDelivery();
        }
    }

    public void onChangeDeliveryPermission(boolean z, boolean z2, boolean z3) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onChangeDeliveryPermission(z, z2, z3);
        }
    }

    public void onFinishDelivery() {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onFinishDelivery();
        }
    }

    public void onThumbnailInfo(D_ThumbnailInfo d_ThumbnailInfo) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onThumbnailInfo(d_ThumbnailInfo);
        }
    }

    public void onThumbnailError(D_ThumbnailError d_ThumbnailError) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onThumbnailError(d_ThumbnailError);
        }
    }

    public void onNotifyImageProcTime(D_ImageProcTime d_ImageProcTime) {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onNotifyImageProcTime(d_ImageProcTime);
        }
    }

    public void showKeywordAgainDialog() {
        ConnectPjInfo connectPjInfo = this._stKeywordAgain;
        if (connectPjInfo == null) {
            Lg.w("キーワード再入力対象のPJが設定されていません");
            return;
        }
        this._dlgCtrlr.createInputDialog_KeywordAgain(this._context, connectPjInfo.getPjInfo().PrjName, this._stKeywordAgain.getPjInfo().ProjectorID);
        this._dlgCtrlr.show();
    }

    private void callConnect() {
        Pj.getIns().stopCyclicSearchForRegisted();
        showConnectingDialog();
        Pj.getIns().resetReconnect();
        Pj.getIns().connect(this._conPjInfo);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        this._dlgCtrlr.clearDialog();
        boolean z = false;
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[resultAction.ordinal()]) {
            case 1:
                Lg.d("Dialog ConnectOK → connect");
                if (str.equals(CommonDefine.SECRET_PJ_KEYWORD)) {
                    str = "";
                }
                ConnectPjInfo connectPjInfo = this._stKeywordAgain;
                if (connectPjInfo != null) {
                    connectPjInfo.setKeyword(str);
                    this._stKeywordAgain = null;
                    callConnect();
                } else if (!this._bCheckedUsing) {
                    this._bCheckedUsing = true;
                    tryConnectSub(false);
                    return;
                } else {
                    if (this._nKeyCheckPJ < this._conPjInfo.size()) {
                        this._conPjInfo.get(this._nKeyCheckPJ).setKeyword(str);
                    }
                    if (this._nKeyCheckPJ + 1 >= this._conPjInfo.size()) {
                        callConnect();
                    } else {
                        tryConnectSub(false);
                    }
                }
                z = true;
                break;
            case 2:
                Lg.d("Dialog ConnectOK → disconnect");
                Pj.getIns().disconnect(DisconReason.Default);
                z = true;
                break;
            case 3:
                Lg.d("Dialog ConnectOK → no action");
                z = true;
                break;
            case 4:
                Lg.d("Dialog ConnectOK → self projection");
                Pj.getIns().projectionMyself();
                z = true;
                break;
            case 5:
                Pj.getIns().requestDelivery(false, false, false, false, true);
                z = true;
                break;
            case 6:
                Pj.getIns().requestDeliveryWhitePaper();
                z = true;
                break;
            case 7:
                Lg.d("Dialog ConnectOK → show message dialog");
                showMsgDialog(str);
                break;
            case 8:
                Lg.d("Dialog ConnectOK → show message alert dialog");
                showMsgAlertDialog(str);
                break;
            case 9:
                Lg.d("Dialog ConnectOK → wait moderator result");
                break;
            case 10:
                Lg.d("Dialog ConnectOK → restore wifi");
                Pj.getIns().restoreWifi();
                z = true;
                break;
            case 11:
                Pj.getIns().removeWifiProfile();
                Pj.getIns().setWifiConnecter(null);
                z = true;
                break;
            default:
                z = true;
                break;
        }
        if (z) {
            showStackDialog();
        }
    }

    private void showMsgDialog(String str) {
        this._dlgCtrlr.createMsgDialog(this._context, str);
        this._dlgCtrlr.show();
    }

    private void showMsgAlertDialog(String str) {
        this._dlgCtrlr.createMsgAlertDialog(this._context, str);
        this._dlgCtrlr.show();
    }

    public void showConnectingDialog() {
        Lg.d("【prgダイアログ】接続中");
        this._dlgCtrlr.showConnectingDialog(this._context);
        this._disconedReason = IOnConnectListener.DisconedReason.Nothing;
    }

    public void showDisconnectingDialog(DisconReason disconReason) {
        Lg.d("【prgダイアログ】切断中");
        if (disconReason == DisconReason.Interrupt || disconReason == DisconReason.Error || disconReason == DisconReason.IllegalKeyword || disconReason == DisconReason.ConnectFailed || disconReason == DisconReason.NpVersionError || disconReason == DisconReason.MppMaxUser || disconReason == DisconReason.DiffCombiPj || disconReason == DisconReason.DisconnOther) {
            return;
        }
        this._dlgCtrlr.showDisconnectingDialog(this._context);
    }

    public void showSearchingDialog() {
        Lg.d("【prgダイアログ】指定検索中");
        this._dlgCtrlr.showSearchingDialog(this._context);
    }

    public void clearSearchingDialog() {
        this._dlgCtrlr.clearSearchingDialog();
    }

    public void onActivityStart(Context context) {
        this._dlgCtrlr.onActivityStart(context);
    }

    public void connect(ArrayList<ConnectPjInfo> arrayList) {
        Lg.d("set _conPjInfo");
        this._conPjInfo = arrayList;
        callConnect();
    }

    public void disconnect(DisconReason disconReason) {
        Pj.getIns().disconnect(disconReason);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
        if (Pj.getIns().isRegistered()) {
            Pj.getIns().startCyclicSeachForRegisted();
        }
        this._dlgCtrlr.clearDialog();
        showStackDialog();
        if (resultAction == null) {
            Lg.d("actionのnullは許容.");
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[resultAction.ordinal()];
        if (i == 1) {
            connectCancel();
        } else if (i != 10) {
            return;
        }
        Pj.getIns().setWifiConnecter(null);
    }

    private void connectCancel() {
        this._conPjInfo = null;
        this._nKeyCheckPJ = -1;
        this._bCheckedUsing = false;
        this._bCheckedSomething = false;
        this._stKeywordAgain = null;
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onConnectCanceled();
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
        this._dlgCtrlr.clearDialog();
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onConnectCanceled();
        }
    }

    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z, boolean z2) {
        if (!z) {
            try {
                showDisconnectedDialog(disconedReason);
                if (!AppStatus.getIns()._isAppForeground && Pj.getIns().isWifiChanged()) {
                    RestoreWifiNotification.getIns().show(this._context);
                }
            } catch (Exception unused) {
                return;
            }
        }
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onDisconnect(i, disconedReason, z2);
        }
        MirroringEntrance.INSTANCE.onDisconnect(this._context);
    }

    public void onUnregistered() {
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onUnregistered();
        }
    }

    public void onErrorDisconnect(D_PjInfo d_PjInfo) {
        this._dlgCtrlr.clearDialog();
        this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.DisconnectOnlyOne, d_PjInfo.PrjName);
        this._dlgCtrlr.show();
        IOnConnectListener iOnConnectListener = this._implConnect;
        if (iOnConnectListener != null) {
            iOnConnectListener.onDisconnectOne(d_PjInfo.ProjectorID, IOnConnectListener.DisconedReason.Default);
        }
    }

    public void showDisconnectedDialog(IOnConnectListener.DisconedReason disconedReason) {
        if (shouldSkip(disconedReason)) {
            return;
        }
        this._disconedReason = disconedReason;
        boolean isWifiChanged = Pj.getIns().isWifiChanged();
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason[disconedReason.ordinal()]) {
            case 1:
                Lg.d("【ダイアログ】切断");
                this._dlgCtrlr.clearDialog();
                this._dlgCtrlr.createDisconnectDialog(this._context, MessageDialog.MessageType.Disconnect, isWifiChanged);
                break;
            case 2:
                Lg.d("【ダイアログ】Pjから切断");
                this._dlgCtrlr.clearDialog();
                this._dlgCtrlr.createDisconnectDialog(this._context, MessageDialog.MessageType.DisconnectFromPj, isWifiChanged);
                break;
            case 3:
                Lg.d("【ダイアログ】割り込み");
                this._dlgCtrlr.clearDialog();
                this._dlgCtrlr.createDisconnectDialog(this._context, MessageDialog.MessageType.Interrupted, isWifiChanged);
                break;
            case 4:
                Lg.d("【ダイアログ】ネットワークエラー");
                this._dlgCtrlr.clearDialog();
                this._dlgCtrlr.createDisconnectDialog(this._context, MessageDialog.MessageType.NetworkErr, isWifiChanged);
                break;
            case 5:
                Lg.d("【ダイアログ】他ユーザーからの切断");
                this._dlgCtrlr.clearDialog();
                this._dlgCtrlr.createDisconnectDialog(this._context, MessageDialog.MessageType.DisconnectOther, isWifiChanged);
                break;
            case 6:
                Lg.d("【ダイアログ】管理者からの切断");
                this._dlgCtrlr.clearDialog();
                this._dlgCtrlr.createDisconnectDialog(this._context, MessageDialog.MessageType.DisconnectAdmin, isWifiChanged);
                break;
        }
        this._dlgCtrlr.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.engine_wrapper.ConnectionControler$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason;

        static {
            int[] iArr = new int[IOnConnectListener.DisconedReason.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason = iArr;
            try {
                iArr[IOnConnectListener.DisconedReason.Default.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason[IOnConnectListener.DisconedReason.PjPowerOff.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason[IOnConnectListener.DisconedReason.Interrupt.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason[IOnConnectListener.DisconedReason.NetworkErr.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason[IOnConnectListener.DisconedReason.DisconnOther.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$DisconedReason[IOnConnectListener.DisconedReason.DisconnAdmin.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[BaseDialog.ResultAction.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction = iArr2;
            try {
                iArr2[BaseDialog.ResultAction.CONNECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.DISCONNECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.NOACTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.SELFPROJECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.DELIVERY.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.DELIVERY_WHITE.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.SHOW_MESSAGE.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.SHOW_MESSAGE_ALERT.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.WAIT_MODERATOR_RESULT.ordinal()] = 9;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.RESTORE_WIFI.ordinal()] = 10;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.REMOVE_WIFIPROFILE.ordinal()] = 11;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseDialog$ResultAction[BaseDialog.ResultAction.ONLY_CLOSE_DIALOG.ordinal()] = 12;
            } catch (NoSuchFieldError unused18) {
            }
            int[] iArr3 = new int[IOnConnectListener.FailReason.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason = iArr3;
            try {
                iArr3[IOnConnectListener.FailReason.IlligalKeyword.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[IOnConnectListener.FailReason.NpVersionError.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[IOnConnectListener.FailReason.DiffCombiPj.ordinal()] = 3;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[IOnConnectListener.FailReason.MppMaxUser.ordinal()] = 4;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[IOnConnectListener.FailReason.IncludeUnavailable.ordinal()] = 5;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[IOnConnectListener.FailReason.Standby.ordinal()] = 6;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$FailReason[IOnConnectListener.FailReason.Default.ordinal()] = 7;
            } catch (NoSuchFieldError unused25) {
            }
        }
    }

    private boolean shouldSkip(IOnConnectListener.DisconedReason disconedReason) {
        return (this._disconedReason == IOnConnectListener.DisconedReason.Interrupt && disconedReason == IOnConnectListener.DisconedReason.Default) || (this._disconedReason == IOnConnectListener.DisconedReason.NetworkErr && disconedReason == IOnConnectListener.DisconedReason.Default) || ((this._disconedReason == IOnConnectListener.DisconedReason.PjPowerOff && disconedReason == IOnConnectListener.DisconedReason.Default) || ((this._disconedReason == IOnConnectListener.DisconedReason.FailedToConnect && disconedReason == IOnConnectListener.DisconedReason.Default) || ((this._disconedReason == IOnConnectListener.DisconedReason.DisconnOther && disconedReason == IOnConnectListener.DisconedReason.Default) || (this._disconedReason == IOnConnectListener.DisconedReason.DisconnAdmin && disconedReason == IOnConnectListener.DisconedReason.Default))));
    }

    public void setOnConnectListener(IOnConnectListener iOnConnectListener) {
        this._implConnect = iOnConnectListener;
    }

    public void disableOnConnectListener() {
        this._implConnect = null;
    }

    public void setOnMinConnectListener(IOnMinConnectListener iOnMinConnectListener) {
        this._implOnMinConnectListener = iOnMinConnectListener;
    }

    public void clearOnMinConnectListener() {
        this._implOnMinConnectListener = null;
    }

    public void onAppFinished() {
        this._context = null;
        this._stackDialog.clear();
        this._dlgCtrlr.onAppFinished();
    }

    public boolean hasAgainKeyword() {
        return this._stKeywordAgain != null;
    }

    public void createDeliveryDialog() {
        this._dlgCtrlr.createDeliveryDialog(this._context);
        this._dlgCtrlr.show();
    }

    public void createDeliveringDialog() {
        this._dlgCtrlr.showDeliveringDialog(this._context);
        this._dlgCtrlr.show();
    }

    public void clearDialog() {
        this._dlgCtrlr.clearDialog();
    }

    public void clearAllDialog() {
        this._dlgCtrlr.clearDialog();
    }

    public void createStartModeratorDialog() {
        MessageDialog.setIsModeratorMenu(true);
        this._dlgCtrlr.createStartModeratorDialog(this._context);
        this._dlgCtrlr.show();
    }

    public void createModeratorPassInputDialog(boolean z) {
        if (this._dlgCtrlr.hasDialog()) {
            this._stackDialog.add(StackDialog.ModeratorPassword);
            this._stackIsConnect = z;
            return;
        }
        this._stackDialog.remove(StackDialog.ModeratorPassword);
        int needModeratorPassInputDialog = needModeratorPassInputDialog();
        if (needModeratorPassInputDialog == 0) {
            showStackDialog();
        } else if (needModeratorPassInputDialog == -802) {
            ArrayList<D_PjInfo> arrayList = new ArrayList<>();
            Pj.getIns().hasMppModeratorPassword(arrayList);
            this._dlgCtrlr.createModeratorPassInputDialog(this._context, arrayList, z);
            this._dlgCtrlr.show();
        } else {
            createAlreadyModeratorDialog();
        }
    }

    public void createAlreadyModeratorDialog() {
        if (this._dlgCtrlr.hasDialog()) {
            this._stackDialog.add(StackDialog.AlreadyModerator);
            return;
        }
        if (MessageDialog.isModeratorMenu()) {
            this._dlgCtrlr.createMsgDialog(this._context, MessageDialog.MessageType.AlreadyModerator);
        } else {
            this._dlgCtrlr.createMsgAlertDialog(this._context, MessageDialog.MessageType.AlreadyModerator);
        }
        this._dlgCtrlr.show();
        this._stackDialog.remove(StackDialog.AlreadyModerator);
        MessageDialog.setIsModeratorMenu(false);
    }

    private void createWhiteboardDialog() {
        this._dlgCtrlr.createWhiteboardDialog(this._context);
        this._dlgCtrlr.show();
        this._stackDialog.remove(StackDialog.WhiteboardDialog);
    }

    public void showStackDialog() {
        if (!Pj.getIns().isConnected()) {
            this._stackDialog.clear();
        } else if (this._dlgCtrlr.hasDialog()) {
        } else {
            if (this._stackDialog.contains(StackDialog.ModeratorPassword)) {
                createModeratorPassInputDialog(this._stackIsConnect);
            } else if (this._stackDialog.contains(StackDialog.AlreadyModerator)) {
                createAlreadyModeratorDialog();
            } else if (this._stackDialog.contains(StackDialog.WhiteboardDialog)) {
                new Handler().post(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.ConnectionControler$$ExternalSyntheticLambda0
                    {
                        ConnectionControler.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        ConnectionControler.this.m205xad621da5();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$showStackDialog$0$com-epson-iprojection-ui-engine_wrapper-ConnectionControler  reason: not valid java name */
    public /* synthetic */ void m205xad621da5() {
        if (WhiteboardUtils.Companion.shouldOpenInMyApp()) {
            Activity frontActivity = ActivityGetter.getIns().getFrontActivity();
            Intent intent = new Intent(frontActivity, WhiteboardActivity.class);
            intent.putExtra(WhiteboardActivity.INTENT_INCLUDE_PINCODE, "emptymessage");
            frontActivity.startActivity(intent);
            this._stackDialog.remove(StackDialog.WhiteboardDialog);
            return;
        }
        createWhiteboardDialog();
    }

    private void clearStackDialog() {
        if (PrefUtils.readInt(this._context, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1) {
            this._stackDialog.clear();
        }
    }

    private int needModeratorPassInputDialog() {
        int mppControlMode = Pj.getIns().setMppControlMode(1, null);
        if (mppControlMode == 0) {
            MultiProjectionDisplaySettings.INSTANCE.setThumb(false);
        }
        return mppControlMode;
    }

    public boolean isShowingSpinDialog() {
        return this._dlgCtrlr.isShowingSpinDialog();
    }
}
