package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state;

import android.content.Intent;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.ContextData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OverlayPermissionForOpenGLState.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/OverlayPermissionForOpenGLState;", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;)V", "onActivityResult", "", "result", "Landroidx/activity/result/ActivityResult;", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OverlayPermissionForOpenGLState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OverlayPermissionForOpenGLState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State
    public void run() {
        Lg.d("[preMirroring] オーバーレイ(forGL)権限画面呼び出し");
        getContextData().getView().startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getContextData().getView().getPackageName())));
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State
    public void onActivityResult(ActivityResult result) {
        Intrinsics.checkNotNullParameter(result, "result");
        if (getContextData().getView().canDrawOverlays()) {
            Lg.d("[preMirroring][出口] 権限画面で許可して復帰");
            getContextData().getCallback().onFinished(true);
            return;
        }
        Lg.d("[preMirroring][出口] 権限画面で拒否して復帰");
        getContextData().getView().showToast(R.string._CannotPlayMirroingWithoutOverlay_);
        getContextData().getCallback().onFinished(false);
    }
}
