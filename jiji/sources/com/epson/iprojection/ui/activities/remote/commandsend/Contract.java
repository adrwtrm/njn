package com.epson.iprojection.ui.activities.remote.commandsend;

import android.view.View;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.commandsend.state.State;
import kotlin.Metadata;

/* compiled from: Contract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract;", "", "ICallback", "IOnClickInputPasswordDialogButton", "IOnFinishedEscvpSending", "IView", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface Contract {

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$ICallback;", "", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "onFinish", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface ICallback {
        void changeState(State state);

        void onFinish();
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IOnClickInputPasswordDialogButton;", "", "onClickCancel", "", "layout", "Landroid/view/View;", "onClickOk", "pjInfo", "Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IOnClickInputPasswordDialogButton {
        void onClickCancel(View view);

        void onClickOk(View view, D_HistoryInfo d_HistoryInfo);
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IOnFinishedEscvpSending;", "", "onFinishedEscvpSending", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IOnFinishedEscvpSending {
        void onFinishedEscvpSending();
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H&¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IView;", "", "createLayoutInPasswordInputDialog", "Landroid/view/View;", "pjName", "", "isSingle", "", "createPasswordInputDialogFrom", "", "layout", "pjInfo", "Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "impl", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IOnClickInputPasswordDialogButton;", "getPasswordFrom", "isApplyAllCheckedIn", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IView {
        View createLayoutInPasswordInputDialog(String str, boolean z);

        void createPasswordInputDialogFrom(View view, D_HistoryInfo d_HistoryInfo, IOnClickInputPasswordDialogButton iOnClickInputPasswordDialogButton);

        String getPasswordFrom(View view);

        boolean isApplyAllCheckedIn(View view);
    }
}
