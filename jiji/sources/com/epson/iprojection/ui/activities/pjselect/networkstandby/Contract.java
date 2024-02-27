package com.epson.iprojection.ui.activities.pjselect.networkstandby;

import android.view.View;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.state.State;
import kotlin.Metadata;

/* compiled from: Contract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract;", "", "ICallback", "IOnClickInputPasswordDialogButton", "IOnFinishedToEnableNetworkStandby", "IView", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface Contract {

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$ICallback;", "", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/State;", "onFinish", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface ICallback {
        void changeState(State state);

        void onFinish();
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnClickInputPasswordDialogButton;", "", "onClickCancel", "", "layout", "Landroid/view/View;", "onClickOk", "pjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IOnClickInputPasswordDialogButton {
        void onClickCancel(View view);

        void onClickOk(View view, D_PjInfo d_PjInfo);
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnFinishedToEnableNetworkStandby;", "", "onEnabledNetworkStandby", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IOnFinishedToEnableNetworkStandby {
        void onEnabledNetworkStandby();
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\tH&J\u0010\u0010\u0010\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H&J\b\u0010\u0012\u001a\u00020\tH&¨\u0006\u0013"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IView;", "", "createLayoutInPasswordInputDialog", "Landroid/view/View;", "pjName", "", "isSingle", "", "createPasswordInputDialogFrom", "", "layout", "pjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "impl", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnClickInputPasswordDialogButton;", "dismissProgressDialog", "getPasswordFrom", "isApplyAllCheckedIn", "showProgressDialog", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IView {
        View createLayoutInPasswordInputDialog(String str, boolean z);

        void createPasswordInputDialogFrom(View view, D_PjInfo d_PjInfo, IOnClickInputPasswordDialogButton iOnClickInputPasswordDialogButton);

        void dismissProgressDialog();

        String getPasswordFrom(View view);

        boolean isApplyAllCheckedIn(View view);

        void showProgressDialog();
    }
}
