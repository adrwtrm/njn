package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state;

import androidx.activity.result.ActivityResult;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.ContextData;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AudioPermissionState.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/AudioPermissionState;", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;)V", "needs", "", "nextState", "", "onActivityResult", "result", "Landroidx/activity/result/ActivityResult;", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AudioPermissionState extends State {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioPermissionState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State
    public void run() {
        Lg.d("[preMirroring] 音声権限画面呼び出し");
        if (needs()) {
            getContextData().getView().startActivityForResult(getContextData().getView().createIntent(PermissionAudioActivity.class));
            return;
        }
        Lg.d("[preMirroring][出口] 権限不要");
        nextState();
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State
    public void onActivityResult(ActivityResult result) {
        Intrinsics.checkNotNullParameter(result, "result");
        Lg.d("[preMirroring][出口] 権限画面から復帰");
        nextState();
    }

    private final void nextState() {
        getContextData().getCallback().changeState(new OverlayForOpenGLTutorialState(getContextData()));
    }

    private final boolean needs() {
        boolean shouldGetAudioPermission = getContextData().getView().shouldGetAudioPermission();
        Lg.d("[preMirroring] 音声権限：".concat(shouldGetAudioPermission ? "必要" : "不要"));
        return shouldGetAudioPermission;
    }
}
