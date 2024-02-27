package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import com.epson.iprojection.ui.common.application.IproApplication;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.common.singleton.WifiChanger;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LinkageConnectContext.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0006\u0010\u0015\u001a\u00020\u000fJ\u0006\u0010\u0016\u001a\u00020\u000fJ\u0018\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0006\u0010\u001c\u001a\u00020\u001dR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/LinkageConnectContext;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IContextListener;", "context", "Landroid/content/Context;", "linkageData", "Lcom/epson/iprojection/linkagedata/data/D_LinkageData;", "pj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "_connectListener", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;", "(Landroid/content/Context;Lcom/epson/iprojection/linkagedata/data/D_LinkageData;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;)V", "_context", "_state", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/State;", "changeState", "", "state", "createEntryState", "qrInfo", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "onActivityResumed", "onActivityStopped", "onFinished", "type", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$FinishType;", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "startConnecting", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LinkageConnectContext implements IContextListener {
    private final IConnectListener _connectListener;
    private final Context _context;
    private State _state;

    /* compiled from: LinkageConnectContext.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Define.FinishType.values().length];
            try {
                iArr[Define.FinishType.Succeeded.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Define.FinishType.Failed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LinkageConnectContext(Context context, D_LinkageData linkageData, IPj pj, IConnectListener _connectListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(linkageData, "linkageData");
        Intrinsics.checkNotNullParameter(pj, "pj");
        Intrinsics.checkNotNullParameter(_connectListener, "_connectListener");
        this._connectListener = _connectListener;
        this._context = context;
        createEntryState(linkageData, new ContextData(context, linkageData, this, pj, _connectListener));
        LinkageDataInfoStacker.getIns().clear();
        Application application = ((Activity) context).getApplication();
        Intrinsics.checkNotNull(application, "null cannot be cast to non-null type com.epson.iprojection.ui.common.application.IproApplication");
        ((IproApplication) application).mIsNFCEventHappenedDuringQRConnect = false;
    }

    public final boolean startConnecting() {
        State state = this._state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_state");
            state = null;
        }
        state.run();
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.IContextListener
    public void changeState(State state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this._state = state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_state");
            state = null;
        }
        state.run();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.IContextListener
    public void onFinished(Define.FinishType type, Define.ConnectFailedReason reason) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(reason, "reason");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            Lg.d("onFinished 成功");
            this._connectListener.onConnected();
        } else if (i != 2) {
        } else {
            Lg.d("onFinished 失敗");
            WifiChanger.INSTANCE.restoreWifi(this._context);
            LinkageDataInfoStacker.getIns().clear();
            this._connectListener.onConnectFailed(reason);
        }
    }

    public final void onActivityResumed() {
        State state = this._state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_state");
            state = null;
        }
        state.onActivityResumed();
    }

    public final void onActivityStopped() {
        State state = this._state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_state");
            state = null;
        }
        state.onActivityStopped();
    }

    private final void createEntryState(D_LinkageData d_LinkageData, ContextData contextData) {
        WirelessSearchingState wirelessSearchingState;
        if (d_LinkageData.isAvailableWired) {
            Lg.i("[QR] → 有線検索状態へ");
            wirelessSearchingState = new WiredSearchingState(contextData);
        } else if (d_LinkageData.isEasyConnect) {
            Lg.i("[QR] → Wi-Fi切り替え状態へ");
            wirelessSearchingState = new WiFiChangingState(contextData);
        } else {
            Lg.i("[QR] → 無線検索状態へ");
            wirelessSearchingState = new WirelessSearchingState(contextData);
        }
        this._state = wirelessSearchingState;
    }
}
