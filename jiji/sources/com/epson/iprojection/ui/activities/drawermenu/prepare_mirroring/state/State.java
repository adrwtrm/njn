package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state;

import androidx.activity.result.ActivityResult;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.ContextData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: State.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\bH&R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "", "contextData", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;)V", "getContextData", "()Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;", "onActivityResult", "", "result", "Landroidx/activity/result/ActivityResult;", "onRequestPermissionResult", "isGranted", "", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class State {
    private final ContextData contextData;

    public void onActivityResult(ActivityResult result) {
        Intrinsics.checkNotNullParameter(result, "result");
    }

    public void onRequestPermissionResult(boolean z) {
    }

    public abstract void run();

    public State(ContextData contextData) {
        Intrinsics.checkNotNullParameter(contextData, "contextData");
        this.contextData = contextData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ContextData getContextData() {
        return this.contextData;
    }
}
