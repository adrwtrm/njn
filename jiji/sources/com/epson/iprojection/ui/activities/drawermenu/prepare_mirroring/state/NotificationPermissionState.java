package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state;

import android.os.Build;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.ContextData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationPermissionState.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0006H\u0016J\b\u0010\u000b\u001a\u00020\bH\u0017¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/NotificationPermissionState;", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;)V", "needs", "", "nextState", "", "onRequestPermissionResult", "isGranted", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class NotificationPermissionState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationPermissionState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State
    public void run() {
        Lg.d("[preMirroring] POST_NOTIFICATION権限画面呼び出し");
        if (needs()) {
            getContextData().getView().requestPermission("android.permission.POST_NOTIFICATIONS");
            return;
        }
        Lg.d("[preMirroring][出口] 権限不要");
        nextState();
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State
    public void onRequestPermissionResult(boolean z) {
        Lg.d("[preMirroring][出口] 権限画面から復帰");
        nextState();
    }

    private final void nextState() {
        getContextData().getCallback().changeState(new CapturePermissionState(getContextData()));
    }

    private final boolean needs() {
        if (Build.VERSION.SDK_INT < 33) {
            Lg.d("[preMirroring] 通知権限：不要。Android14未満の場合Notificationに権限が必要ない");
            return false;
        } else if (getContextData().getView().checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0) {
            Lg.d("[preMirroring] 通知権限：不要。既に許可済み");
            return false;
        } else if (getContextData().getView().shouldShowRequestPermissionRationale("android.permission.POST_NOTIFICATIONS")) {
            Lg.d("[preMirroring] 通知権限：不要。まだ許可していない。「今後表示しない」チェックあり");
            return false;
        } else {
            Lg.d("[preMirroring] 通知権限：必要。まだ許可していない。「今後表示しない」チェックなし");
            return true;
        }
    }
}
