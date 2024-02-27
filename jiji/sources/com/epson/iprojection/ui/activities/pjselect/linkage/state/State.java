package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: State.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\bH&R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/State;", "", "_contextData", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;)V", "get_contextData", "()Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "onActivityResumed", "", "onActivityStopped", "run", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class State {
    private final ContextData _contextData;

    public void onActivityResumed() {
    }

    public void onActivityStopped() {
    }

    public abstract void run();

    public State(ContextData _contextData) {
        Intrinsics.checkNotNullParameter(_contextData, "_contextData");
        this._contextData = _contextData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ContextData get_contextData() {
        return this._contextData;
    }
}
