package com.epson.iprojection.ui.activities.remote.commandsend;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.epson.iprojection.ui.activities.remote.commandsend.CommandSender;
import com.epson.iprojection.ui.activities.remote.commandsend.Contract;
import com.epson.iprojection.ui.activities.remote.commandsend.state.State;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: CommandSender.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002\u0018\u0019B\u0005¢\u0006\u0002\u0010\u0002JH\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0016\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u00140\u0013j\b\u0012\u0004\u0012\u00020\u0014`\u00152\u0006\u0010\u0016\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/CommandSender;", "", "()V", "_activity", "Landroid/app/Activity;", "_impl", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IOnFinishedEscvpSending;", "_isRunning", "", "_state", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "send", "", "command", "", "activity", "ipj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "pjList", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/D_SendCommand;", "Lkotlin/collections/ArrayList;", "isEscvpOnly", "impl", "ICallbackImpl", "IViewImpl", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CommandSender {
    private Activity _activity;
    private Contract.IOnFinishedEscvpSending _impl;
    private boolean _isRunning;
    private State _state;

    public final void send(String command, Activity activity, IPj ipj, ArrayList<D_SendCommand> pjList, boolean z, Contract.IOnFinishedEscvpSending iOnFinishedEscvpSending) {
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(ipj, "ipj");
        Intrinsics.checkNotNullParameter(pjList, "pjList");
        Lg.d("[sd] send");
        if (this._isRunning) {
            Lg.w("送信中のため送信命令を却下する");
            return;
        }
        this._isRunning = true;
        this._activity = activity;
        this._impl = iOnFinishedEscvpSending;
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new CommandSender$send$1(this, activity, pjList, ipj, z, command, null), 2, null);
    }

    /* compiled from: CommandSender.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/CommandSender$ICallbackImpl;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$ICallback;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/CommandSender;)V", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/remote/commandsend/state/State;", "onFinish", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ICallbackImpl implements Contract.ICallback {
        public ICallbackImpl() {
            CommandSender.this = r1;
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.ICallback
        public void onFinish() {
            Lg.d("[sd] onFinish : Stateパターンの処理が終わりました。");
            CommandSender.this._isRunning = false;
            Contract.IOnFinishedEscvpSending iOnFinishedEscvpSending = CommandSender.this._impl;
            if (iOnFinishedEscvpSending != null) {
                iOnFinishedEscvpSending.onFinishedEscvpSending();
            }
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.ICallback
        public void changeState(State state) {
            Intrinsics.checkNotNullParameter(state, "state");
            Lg.d("[sd] changeState : 次の状態へ変更する");
            CommandSender.this._state = state;
            State state2 = CommandSender.this._state;
            if (state2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_state");
                state2 = null;
            }
            state2.start();
        }
    }

    /* compiled from: CommandSender.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/CommandSender$IViewImpl;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IView;", "(Lcom/epson/iprojection/ui/activities/remote/commandsend/CommandSender;)V", "createLayoutInPasswordInputDialog", "Landroid/view/View;", "pjName", "", "isSingle", "", "createPasswordInputDialogFrom", "", "layout", "pjInfo", "Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "impl", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IOnClickInputPasswordDialogButton;", "getPasswordFrom", "isApplyAllCheckedIn", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class IViewImpl implements Contract.IView {
        /* renamed from: $r8$lambda$mCB-kgxe7LJNd9CbFnsl8-WW0ow */
        public static /* synthetic */ void m179$r8$lambda$mCBkgxe7LJNd9CbFnsl8WW0ow(Contract.IOnClickInputPasswordDialogButton iOnClickInputPasswordDialogButton, View view, D_HistoryInfo d_HistoryInfo, DialogInterface dialogInterface, int i) {
            createPasswordInputDialogFrom$lambda$0(iOnClickInputPasswordDialogButton, view, d_HistoryInfo, dialogInterface, i);
        }

        public IViewImpl() {
            CommandSender.this = r1;
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IView
        public View createLayoutInPasswordInputDialog(String pjName, boolean z) {
            Intrinsics.checkNotNullParameter(pjName, "pjName");
            Activity activity = CommandSender.this._activity;
            Activity activity2 = null;
            if (activity == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_activity");
                activity = null;
            }
            Object systemService = activity.getSystemService("layout_inflater");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
            View layout = ((LayoutInflater) systemService).inflate(R.layout.dialog_input_webcontrol_password, (ViewGroup) null);
            TextView textView = (TextView) layout.findViewById(R.id.text_pj_name);
            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.check_apply_all);
            if (z) {
                textView.setVisibility(8);
                checkBox.setVisibility(8);
            } else {
                StringBuilder sb = new StringBuilder();
                Activity activity3 = CommandSender.this._activity;
                if (activity3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_activity");
                    activity3 = null;
                }
                textView.setText(sb.append(activity3.getString(R.string._PjNameColon_)).append(pjName).toString());
                Activity activity4 = CommandSender.this._activity;
                if (activity4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_activity");
                } else {
                    activity2 = activity4;
                }
                checkBox.setChecked(PrefUtils.readBoolean(activity2, RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, true));
            }
            Intrinsics.checkNotNullExpressionValue(layout, "layout");
            return layout;
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IView
        public String getPasswordFrom(View layout) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            return ((EditText) layout.findViewById(R.id.edt_dlg_pass)).getText().toString();
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IView
        public void createPasswordInputDialogFrom(final View layout, final D_HistoryInfo pjInfo, final Contract.IOnClickInputPasswordDialogButton impl) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            Intrinsics.checkNotNullParameter(pjInfo, "pjInfo");
            Intrinsics.checkNotNullParameter(impl, "impl");
            Activity activity = CommandSender.this._activity;
            if (activity == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_activity");
                activity = null;
            }
            new OkCancelDialog(activity, layout, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.remote.commandsend.CommandSender$IViewImpl$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CommandSender.IViewImpl.m179$r8$lambda$mCBkgxe7LJNd9CbFnsl8WW0ow(Contract.IOnClickInputPasswordDialogButton.this, layout, pjInfo, dialogInterface, i);
                }
            });
        }

        public static final void createPasswordInputDialogFrom$lambda$0(Contract.IOnClickInputPasswordDialogButton impl, View layout, D_HistoryInfo pjInfo, DialogInterface dialogInterface, int i) {
            Intrinsics.checkNotNullParameter(impl, "$impl");
            Intrinsics.checkNotNullParameter(layout, "$layout");
            Intrinsics.checkNotNullParameter(pjInfo, "$pjInfo");
            if (i == -2) {
                impl.onClickCancel(layout);
            } else if (i != -1) {
            } else {
                impl.onClickOk(layout, pjInfo);
            }
        }

        @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IView
        public boolean isApplyAllCheckedIn(View layout) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.check_apply_all);
            return checkBox.getVisibility() != 8 && checkBox.isChecked();
        }
    }
}
