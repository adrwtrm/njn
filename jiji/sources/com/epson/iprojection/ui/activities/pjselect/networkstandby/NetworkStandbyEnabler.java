package com.epson.iprojection.ui.activities.pjselect.networkstandby;

import android.content.Context;
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
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.NetworkStandbyEnabler;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.state.State;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.common.uiparts.ProgressDialogInfinityType;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: NetworkStandbyEnabler.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0013\u0014B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/NetworkStandbyEnabler;", "", "_context", "Landroid/content/Context;", "_ipj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "_impl", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnFinishedToEnableNetworkStandby;", "pjList", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/engine_wrapper/ConnectPjInfo;", "Lkotlin/collections/ArrayList;", "(Landroid/content/Context;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnFinishedToEnableNetworkStandby;Ljava/util/ArrayList;)V", "_progressDialog", "Lcom/epson/iprojection/ui/common/uiparts/ProgressDialogInfinityType;", "_state", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/State;", "enableNetworkStandby", "", "ICallbackImpl", "IViewImpl", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class NetworkStandbyEnabler {
    private final Context _context;
    private final Contract.IOnFinishedToEnableNetworkStandby _impl;
    private final IPj _ipj;
    private ProgressDialogInfinityType _progressDialog;
    private State _state;
    private final ArrayList<ConnectPjInfo> pjList;

    public NetworkStandbyEnabler(Context _context, IPj _ipj, Contract.IOnFinishedToEnableNetworkStandby _impl, ArrayList<ConnectPjInfo> pjList) {
        Intrinsics.checkNotNullParameter(_context, "_context");
        Intrinsics.checkNotNullParameter(_ipj, "_ipj");
        Intrinsics.checkNotNullParameter(_impl, "_impl");
        Intrinsics.checkNotNullParameter(pjList, "pjList");
        this._context = _context;
        this._ipj = _ipj;
        this._impl = _impl;
        this.pjList = pjList;
    }

    public final void enableNetworkStandby() {
        Lg.d("[nw] enableNetworkStandby");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new NetworkStandbyEnabler$enableNetworkStandby$1(this, null), 2, null);
    }

    /* compiled from: NetworkStandbyEnabler.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/NetworkStandbyEnabler$ICallbackImpl;", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$ICallback;", "(Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/NetworkStandbyEnabler;)V", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/State;", "onFinish", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ICallbackImpl implements Contract.ICallback {
        public ICallbackImpl() {
            NetworkStandbyEnabler.this = r1;
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.ICallback
        public void onFinish() {
            Lg.d("[nw] onFinish : Stateパターンの処理が終わりました。");
            NetworkStandbyEnabler.this._impl.onEnabledNetworkStandby();
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.ICallback
        public void changeState(State state) {
            Intrinsics.checkNotNullParameter(state, "state");
            Lg.d("[nw] changeState : 次の状態へ変更する");
            NetworkStandbyEnabler.this._state = state;
            State state2 = NetworkStandbyEnabler.this._state;
            if (state2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_state");
                state2 = null;
            }
            state2.start();
        }
    }

    /* compiled from: NetworkStandbyEnabler.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016¨\u0006\u0014"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/NetworkStandbyEnabler$IViewImpl;", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IView;", "(Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/NetworkStandbyEnabler;)V", "createLayoutInPasswordInputDialog", "Landroid/view/View;", "pjName", "", "isSingle", "", "createPasswordInputDialogFrom", "", "layout", "pjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "impl", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IOnClickInputPasswordDialogButton;", "dismissProgressDialog", "getPasswordFrom", "isApplyAllCheckedIn", "showProgressDialog", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class IViewImpl implements Contract.IView {
        public static /* synthetic */ void $r8$lambda$PkPWEi2PkfMGfCcfm09BJSm08D4(Contract.IOnClickInputPasswordDialogButton iOnClickInputPasswordDialogButton, View view, D_PjInfo d_PjInfo, DialogInterface dialogInterface, int i) {
            createPasswordInputDialogFrom$lambda$0(iOnClickInputPasswordDialogButton, view, d_PjInfo, dialogInterface, i);
        }

        public IViewImpl() {
            NetworkStandbyEnabler.this = r1;
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IView
        public void showProgressDialog() {
            NetworkStandbyEnabler.this._progressDialog = new ProgressDialogInfinityType(NetworkStandbyEnabler.this._context);
            ProgressDialogInfinityType progressDialogInfinityType = NetworkStandbyEnabler.this._progressDialog;
            ProgressDialogInfinityType progressDialogInfinityType2 = null;
            if (progressDialogInfinityType == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_progressDialog");
                progressDialogInfinityType = null;
            }
            String string = NetworkStandbyEnabler.this._context.getString(R.string._Authenticating_);
            Intrinsics.checkNotNullExpressionValue(string, "_context.getString(R.string._Authenticating_)");
            progressDialogInfinityType.setMessage(string);
            ProgressDialogInfinityType progressDialogInfinityType3 = NetworkStandbyEnabler.this._progressDialog;
            if (progressDialogInfinityType3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_progressDialog");
                progressDialogInfinityType3 = null;
            }
            progressDialogInfinityType3.setCancelable(false);
            ProgressDialogInfinityType progressDialogInfinityType4 = NetworkStandbyEnabler.this._progressDialog;
            if (progressDialogInfinityType4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_progressDialog");
            } else {
                progressDialogInfinityType2 = progressDialogInfinityType4;
            }
            progressDialogInfinityType2.show();
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IView
        public void dismissProgressDialog() {
            ProgressDialogInfinityType progressDialogInfinityType = NetworkStandbyEnabler.this._progressDialog;
            ProgressDialogInfinityType progressDialogInfinityType2 = null;
            if (progressDialogInfinityType == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_progressDialog");
                progressDialogInfinityType = null;
            }
            if (progressDialogInfinityType.isShowing()) {
                ProgressDialogInfinityType progressDialogInfinityType3 = NetworkStandbyEnabler.this._progressDialog;
                if (progressDialogInfinityType3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_progressDialog");
                } else {
                    progressDialogInfinityType2 = progressDialogInfinityType3;
                }
                progressDialogInfinityType2.dismiss();
            }
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IView
        public View createLayoutInPasswordInputDialog(String pjName, boolean z) {
            Intrinsics.checkNotNullParameter(pjName, "pjName");
            Object systemService = NetworkStandbyEnabler.this._context.getSystemService("layout_inflater");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
            View layout = ((LayoutInflater) systemService).inflate(R.layout.dialog_input_webcontrol_password, (ViewGroup) null);
            TextView textView = (TextView) layout.findViewById(R.id.text_pj_name);
            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.check_apply_all);
            if (!z) {
                textView.setText(NetworkStandbyEnabler.this._context.getString(R.string._PjNameColon_) + pjName);
                checkBox.setChecked(PrefUtils.readBoolean(NetworkStandbyEnabler.this._context, RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, true));
            } else {
                textView.setVisibility(8);
                checkBox.setVisibility(8);
            }
            Intrinsics.checkNotNullExpressionValue(layout, "layout");
            return layout;
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IView
        public String getPasswordFrom(View layout) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            return ((EditText) layout.findViewById(R.id.edt_dlg_pass)).getText().toString();
        }

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IView
        public void createPasswordInputDialogFrom(final View layout, final D_PjInfo pjInfo, final Contract.IOnClickInputPasswordDialogButton impl) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            Intrinsics.checkNotNullParameter(pjInfo, "pjInfo");
            Intrinsics.checkNotNullParameter(impl, "impl");
            new OkCancelDialog(NetworkStandbyEnabler.this._context, layout, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.networkstandby.NetworkStandbyEnabler$IViewImpl$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    NetworkStandbyEnabler.IViewImpl.$r8$lambda$PkPWEi2PkfMGfCcfm09BJSm08D4(impl, layout, pjInfo, dialogInterface, i);
                }
            });
        }

        public static final void createPasswordInputDialogFrom$lambda$0(Contract.IOnClickInputPasswordDialogButton impl, View layout, D_PjInfo pjInfo, DialogInterface dialogInterface, int i) {
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

        @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IView
        public boolean isApplyAllCheckedIn(View layout) {
            Intrinsics.checkNotNullParameter(layout, "layout");
            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.check_apply_all);
            return checkBox.getVisibility() != 8 && checkBox.isChecked();
        }
    }
}
