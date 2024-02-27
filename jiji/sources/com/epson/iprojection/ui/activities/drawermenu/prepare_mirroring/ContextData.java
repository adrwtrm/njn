package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring;

import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ContextData.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/ContextData;", "", "callback", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$ICallback;", "view", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IView;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$ICallback;Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IView;)V", "getCallback", "()Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$ICallback;", "getView", "()Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IView;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ContextData {
    private final Contract.ICallback callback;
    private final Contract.IView view;

    public static /* synthetic */ ContextData copy$default(ContextData contextData, Contract.ICallback iCallback, Contract.IView iView, int i, Object obj) {
        if ((i & 1) != 0) {
            iCallback = contextData.callback;
        }
        if ((i & 2) != 0) {
            iView = contextData.view;
        }
        return contextData.copy(iCallback, iView);
    }

    public final Contract.ICallback component1() {
        return this.callback;
    }

    public final Contract.IView component2() {
        return this.view;
    }

    public final ContextData copy(Contract.ICallback callback, Contract.IView view) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(view, "view");
        return new ContextData(callback, view);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ContextData) {
            ContextData contextData = (ContextData) obj;
            return Intrinsics.areEqual(this.callback, contextData.callback) && Intrinsics.areEqual(this.view, contextData.view);
        }
        return false;
    }

    public int hashCode() {
        return (this.callback.hashCode() * 31) + this.view.hashCode();
    }

    public String toString() {
        return "ContextData(callback=" + this.callback + ", view=" + this.view + ')';
    }

    public ContextData(Contract.ICallback callback, Contract.IView view) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(view, "view");
        this.callback = callback;
        this.view = view;
    }

    public final Contract.ICallback getCallback() {
        return this.callback;
    }

    public final Contract.IView getView() {
        return this.view;
    }
}
