package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.GlobalScope;

/* compiled from: SearchingState.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH$J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J\b\u0010\u0013\u001a\u00020\u0010H$J\b\u0010\u0014\u001a\u00020\u0010H$J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\b\u0010\u0016\u001a\u00020\u0010H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\tX¤\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/SearchingState;", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/State;", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IPjManualSearchResultListener;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;)V", "_isFound", "", "iPAddressForSearch", "", "getIPAddressForSearch", "()Ljava/lang/String;", "equalsIpAddress", "foundPjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "onEndSearchPj", "", "onFindSearchPj", "bAllEnd", "onFound", "onNotFound", "run", FirebaseAnalytics.Event.SEARCH, "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class SearchingState extends State implements IPjManualSearchResultListener {
    private boolean _isFound;

    protected abstract boolean equalsIpAddress(D_PjInfo d_PjInfo);

    protected abstract String getIPAddressForSearch();

    protected abstract void onFound();

    protected abstract void onNotFound();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SearchingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.linkage.state.State
    public void run() {
        Lg.i("[QR] 検索します。");
        get_contextData().getConnectListener().onConnectingProgressChanged(Define.ConnectingProgress.SearchingPj);
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, null, null, new SearchingState$run$1(this, null), 3, null);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo foundPjInfo, boolean z) {
        Intrinsics.checkNotNullParameter(foundPjInfo, "foundPjInfo");
        Lg.i("[QR] OnPjFound()：" + foundPjInfo);
        if (!foundPjInfo.isShowable()) {
            Lg.i(foundPjInfo.PrjName + " は見失ったPJなのでreturn。");
        } else if (equalsIpAddress(foundPjInfo)) {
            Lg.i("[QR] 指定したPJが見つかった。：" + foundPjInfo.PrjName);
            this._isFound = true;
            get_contextData().setFoundPjInfo(foundPjInfo);
            get_contextData().getEngine().stopSearch();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        Lg.i("[QR] 検索終了。");
        if (this._isFound) {
            get_contextData().getEngine().disableManualSearchResultListener();
            Pj.getIns().selectConnPJ(new ConnectPjInfo(get_contextData().getFoundPjInfo(), new ConnectConfig(get_contextData().getContext()).getInterruptSetting()));
            onFound();
            return;
        }
        Lg.i("[QR] 検索失敗。");
        get_contextData().getEngine().disableManualSearchResultListener();
        onNotFound();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void search() {
        Lg.i("[QR] manualSearch(" + getIPAddressForSearch() + ") = " + get_contextData().getEngine().manualSearch((IPjManualSearchResultListener) this, getIPAddressForSearch(), false));
    }
}
