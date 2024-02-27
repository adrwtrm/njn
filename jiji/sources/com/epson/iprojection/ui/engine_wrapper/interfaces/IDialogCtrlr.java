package com.epson.iprojection.ui.engine_wrapper.interfaces;

import android.content.Context;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.dialogs.ConnectOkDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.SpoilerDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import java.util.ArrayList;
import kotlin.Metadata;

/* compiled from: IDialogCtrlr.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J&\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH&J\u0012\u0010\r\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J \u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H&J \u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J \u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J,\u0010\u0018\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\u0019\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001b\u0018\u00010\u001a2\u0006\u0010\u001c\u001a\u00020\u0011H&J\u001c\u0010\u001d\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u000fH&J\u001c\u0010\u001d\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0014H&J\u001c\u0010\u001f\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u000fH&J0\u0010\u001f\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u000f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH&J&\u0010\u001f\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H&J\u001c\u0010\u001f\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0014H&J(\u0010\"\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020#2\u0006\u0010 \u001a\u00020!2\u0006\u0010$\u001a\u00020\fH&J\u0018\u0010%\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0018\u0010&\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010'\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J0\u0010(\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010)2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH&J\u0012\u0010*\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0012\u0010+\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\b\u0010,\u001a\u00020\u0011H&J\b\u0010-\u001a\u00020\u0011H&J\b\u0010.\u001a\u00020\u0011H&J\u0012\u0010/\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\b\u00100\u001a\u00020\u0003H&J\b\u00101\u001a\u00020\u0003H&J\u0012\u00102\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0012\u00103\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0012\u00104\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0012\u00105\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&¨\u00066"}, d2 = {"Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IDialogCtrlr;", "", "clearDeliveringDialog", "", "clearDialog", "clearSearchingDialog", "createConnectOkToast", "c", "Landroid/content/Context;", "msgType", "Lcom/epson/iprojection/ui/activities/pjselect/dialogs/ConnectOkDialog$MessageType;", "action", "Lcom/epson/iprojection/ui/activities/pjselect/dialogs/base/BaseDialog$ResultAction;", "createDeliveryDialog", "createDisconnectDialog", "Lcom/epson/iprojection/ui/activities/pjselect/dialogs/MessageDialog$MessageType;", "isWifiChange", "", "createInputDialog_Keyword", "pjName", "", "pjID", "", "createInputDialog_KeywordAgain", "createModeratorPassInputDialog", "passPjs", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "isConnect", "createMsgAlertDialog", "message", "createMsgDialog", "impl", "Lcom/epson/iprojection/ui/activities/pjselect/dialogs/base/IOnDialogEventListener;", "createQueryDialog", "Lcom/epson/iprojection/ui/activities/pjselect/dialogs/QueryDialog$MessageType;", "act", "createQueryDialog_ConnectToUsingPj", "createQueryDialog_ConnectToUsingPjs", "createQueryDialog_ProjectionNow", "createSpoilerDialog", "Lcom/epson/iprojection/ui/activities/pjselect/dialogs/SpoilerDialog$MessageType;", "createStartModeratorDialog", "createWhiteboardDialog", "hasDialog", "isDeliveringDialog", "isShowingSpinDialog", "onActivityStart", "onAppFinished", "show", "showConnectingDialog", "showDeliveringDialog", "showDisconnectingDialog", "showSearchingDialog", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface IDialogCtrlr {
    void clearDeliveringDialog();

    void clearDialog();

    void clearSearchingDialog();

    void createConnectOkToast(Context context, ConnectOkDialog.MessageType messageType, BaseDialog.ResultAction resultAction);

    void createDeliveryDialog(Context context);

    void createDisconnectDialog(Context context, MessageDialog.MessageType messageType, boolean z);

    void createInputDialog_Keyword(Context context, String str, int i);

    void createInputDialog_KeywordAgain(Context context, String str, int i);

    void createModeratorPassInputDialog(Context context, ArrayList<D_PjInfo> arrayList, boolean z);

    void createMsgAlertDialog(Context context, MessageDialog.MessageType messageType);

    void createMsgAlertDialog(Context context, String str);

    void createMsgDialog(Context context, MessageDialog.MessageType messageType);

    void createMsgDialog(Context context, MessageDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction);

    void createMsgDialog(Context context, MessageDialog.MessageType messageType, String str);

    void createMsgDialog(Context context, String str);

    void createQueryDialog(Context context, QueryDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction);

    void createQueryDialog_ConnectToUsingPj(Context context, String str);

    void createQueryDialog_ConnectToUsingPjs(Context context, String str);

    void createQueryDialog_ProjectionNow(Context context);

    void createSpoilerDialog(Context context, SpoilerDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction);

    void createStartModeratorDialog(Context context);

    void createWhiteboardDialog(Context context);

    boolean hasDialog();

    boolean isDeliveringDialog();

    boolean isShowingSpinDialog();

    void onActivityStart(Context context);

    void onAppFinished();

    void show();

    void showConnectingDialog(Context context);

    void showDeliveringDialog(Context context);

    void showDisconnectingDialog(Context context);

    void showSearchingDialog(Context context);
}
