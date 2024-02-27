package com.epson.iprojection.ui.activities.pjselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.IConnectListener;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.LinkageConnectContext;
import com.epson.iprojection.ui.activities.support.intro.wifi.Activity_IntroWifi;
import com.epson.iprojection.ui.common.AppStatus;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: LinkageDataConnectorAndroid10AndOver.kt */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0012H\u0002J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\u0006\u0010\u0019\u001a\u00020\u0012J\u0006\u0010\u001a\u001a\u00020\u0012J\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0012H\u0016J\u0010\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!H\u0016J\u0012\u0010\"\u001a\u00020\u00122\b\u0010#\u001a\u0004\u0018\u00010$H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/LinkageDataConnectorAndroid10AndOver;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;", "_activity", "Landroid/app/Activity;", "_impl", "Lcom/epson/iprojection/ui/activities/pjselect/ILinkageDataConnectorListener;", "_linkageData", "Lcom/epson/iprojection/linkagedata/data/D_LinkageData;", "(Landroid/app/Activity;Lcom/epson/iprojection/ui/activities/pjselect/ILinkageDataConnectorListener;Lcom/epson/iprojection/linkagedata/data/D_LinkageData;)V", "_hanlder", "Landroid/os/Handler;", "_linkageConnect", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/LinkageConnectContext;", "_progressDialog", "Landroid/app/Dialog;", "canStartActivity", "", "connectByLinkageDataCore", "", "createProgressDialog", "isWifiChanging", "mode", "Lcom/epson/iprojection/linkagedata/data/D_LinkageData$Mode;", "dismissDialog", "finalizeConnectProcess", "onActivityResumed", "onActivityStopped", "onConnectFailed", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "onConnected", "onConnectingProgressChanged", "progress", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectingProgress;", "startWiFiTutorialActivity", "dialogString", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LinkageDataConnectorAndroid10AndOver implements IConnectListener {
    private final Activity _activity;
    private final Handler _hanlder;
    private final ILinkageDataConnectorListener _impl;
    private final LinkageConnectContext _linkageConnect;
    private final D_LinkageData _linkageData;
    private Dialog _progressDialog;

    /* compiled from: LinkageDataConnectorAndroid10AndOver.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[Define.ConnectFailedReason.values().length];
            try {
                iArr[Define.ConnectFailedReason.WiFiChangeFailed.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Define.ConnectFailedReason.SearchFailed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Define.ConnectingProgress.values().length];
            try {
                iArr2[Define.ConnectingProgress.ChangingWiFi.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[Define.ConnectingProgress.SearchingPj.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[Lifecycle.State.values().length];
            try {
                iArr3[Lifecycle.State.STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr3[Lifecycle.State.RESUMED.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    /* renamed from: $r8$lambda$fCymIV0l2-y7HZijt3HN7zzzrK4 */
    public static /* synthetic */ void m129$r8$lambda$fCymIV0l2y7HZijt3HN7zzzrK4(LinkageDataConnectorAndroid10AndOver linkageDataConnectorAndroid10AndOver) {
        onConnectFailed$lambda$0(linkageDataConnectorAndroid10AndOver);
    }

    public LinkageDataConnectorAndroid10AndOver(Activity _activity, ILinkageDataConnectorListener _impl, D_LinkageData _linkageData) {
        Intrinsics.checkNotNullParameter(_activity, "_activity");
        Intrinsics.checkNotNullParameter(_impl, "_impl");
        Intrinsics.checkNotNullParameter(_linkageData, "_linkageData");
        this._activity = _activity;
        this._impl = _impl;
        this._linkageData = _linkageData;
        Pj ins = Pj.getIns();
        Intrinsics.checkNotNullExpressionValue(ins, "getIns()");
        this._linkageConnect = new LinkageConnectContext(_activity, _linkageData, ins, this);
        this._hanlder = new Handler();
    }

    public final void connectByLinkageDataCore() {
        Pj.getIns().clearAllDialog();
        Pj.getIns().setIsConnectingByLinkageData(true);
        Pj.getIns().setLinkageDataSearchingMode(true);
        this._impl.setResumeByLinkageDataRead(true);
        this._activity.getWindow().addFlags(128);
        this._linkageConnect.startConnecting();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.IConnectListener
    public void onConnected() {
        finalizeConnectProcess();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.IConnectListener
    public void onConnectFailed(Define.ConnectFailedReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Lg.d("onConnectFailed reason:" + reason);
        finalizeConnectProcess();
        if (AppStatus.getIns()._isAppForeground) {
            this._impl.setResumeByLinkageDataRead(false);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[reason.ordinal()];
        if (i == 1) {
            startWiFiTutorialActivity(this._activity.getString(R.string._TurnOffOnWifiWhenCannotConnect_));
        } else if (i == 2) {
            startWiFiTutorialActivity(null);
        }
        this._hanlder.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.LinkageDataConnectorAndroid10AndOver$$ExternalSyntheticLambda0
            {
                LinkageDataConnectorAndroid10AndOver.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                LinkageDataConnectorAndroid10AndOver.m129$r8$lambda$fCymIV0l2y7HZijt3HN7zzzrK4(LinkageDataConnectorAndroid10AndOver.this);
            }
        });
    }

    public static final void onConnectFailed$lambda$0(LinkageDataConnectorAndroid10AndOver this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0._activity.getWindow().clearFlags(128);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.IConnectListener
    public void onConnectingProgressChanged(Define.ConnectingProgress progress) {
        Intrinsics.checkNotNullParameter(progress, "progress");
        dismissDialog();
        this._impl.setResumeByLinkageDataRead(true);
        int i = WhenMappings.$EnumSwitchMapping$1[progress.ordinal()];
        if (i == 1) {
            D_LinkageData.Mode mode = this._linkageData.mode;
            Intrinsics.checkNotNullExpressionValue(mode, "_linkageData.mode");
            createProgressDialog(true, mode);
        } else if (i != 2) {
        } else {
            D_LinkageData.Mode mode2 = this._linkageData.mode;
            Intrinsics.checkNotNullExpressionValue(mode2, "_linkageData.mode");
            createProgressDialog(false, mode2);
        }
    }

    public final void onActivityResumed() {
        this._linkageConnect.onActivityResumed();
    }

    public final void onActivityStopped() {
        this._linkageConnect.onActivityStopped();
    }

    private final void startWiFiTutorialActivity(String str) {
        if (canStartActivity()) {
            Intent intent = new Intent(this._activity, Activity_IntroWifi.class);
            if (str != null) {
                intent.putExtra(Activity_IntroWifi.INTENT_TAG_DIALOG_STRING, str);
            }
            this._activity.startActivityForResult(intent, 0);
        }
    }

    private final boolean canStartActivity() {
        int i = WhenMappings.$EnumSwitchMapping$2[ProcessLifecycleOwner.Companion.get().getLifecycle().getCurrentState().ordinal()];
        return i == 1 || i == 2;
    }

    private final void finalizeConnectProcess() {
        Pj.getIns().setIsConnectingByLinkageData(false);
        Pj.getIns().setLinkageDataSearchingMode(false);
        this._impl.setResumeByLinkageDataRead(false);
        if (!Pj.getIns().isConnected()) {
            LinkageDataInfoStacker.getIns().clear();
        }
        dismissDialog();
    }

    private final void createProgressDialog(boolean z, D_LinkageData.Mode mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);
        Object systemService = this._activity.getSystemService("layout_inflater");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
        View inflate = ((LayoutInflater) systemService).inflate(R.layout.dialog_progress_linkage, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflater.inflate(R.layou…g_progress_linkage, null)");
        builder.setView(inflate);
        builder.setCancelable(false);
        TextView textView = (TextView) inflate.findViewById(R.id.text_main);
        if (z) {
            textView.setText(this._activity.getString(R.string._WiFiChanging_));
        } else {
            textView.setText(this._activity.getString(R.string._SearchingProjector_));
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.text_sub);
        if (mode == D_LinkageData.Mode.MODE_NFC) {
            textView2.setText(StringsKt.trimIndent("\n                " + this._activity.getString(R.string._NFCConnectFewMinutes_) + "\n                " + this._activity.getString(R.string._NFCConnectingTurnOnManually_) + "\n                "));
        } else {
            textView2.setVisibility(8);
        }
        AlertDialog create = builder.create();
        this._progressDialog = create;
        if (create != null) {
            create.show();
        }
        RegisteredDialog.getIns().setDialog(this._progressDialog);
    }

    private final void dismissDialog() {
        Dialog dialog;
        Dialog dialog2 = this._progressDialog;
        if (dialog2 != null) {
            Intrinsics.checkNotNull(dialog2);
            if (!dialog2.isShowing() || (dialog = this._progressDialog) == null) {
                return;
            }
            dialog.dismiss();
        }
    }
}
