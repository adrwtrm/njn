package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import kotlin.Metadata;

/* compiled from: StateContract.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;", "", "onConnectFailed", "", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "onConnected", "onConnectingProgressChanged", "progress", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectingProgress;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface IConnectListener {
    void onConnectFailed(Define.ConnectFailedReason connectFailedReason);

    void onConnected();

    void onConnectingProgressChanged(Define.ConnectingProgress connectingProgress);
}
