package com.epson.iprojection.ui.activities.pjselect.networkstandby;

import android.content.Context;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ContextData.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(¨\u0006)"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;", "", "context", "Landroid/content/Context;", "pjList", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/engine_wrapper/ConnectPjInfo;", "ipj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "callback", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$ICallback;", "iview", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IView;", "(Landroid/content/Context;Ljava/util/ArrayList;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$ICallback;Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IView;)V", "getCallback", "()Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$ICallback;", "getContext", "()Landroid/content/Context;", "getIpj", "()Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "isCanceled", "", "()Z", "setCanceled", "(Z)V", "getIview", "()Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/Contract$IView;", "pjIndex", "", "getPjIndex", "()I", "setPjIndex", "(I)V", "getPjList", "()Ljava/util/ArrayList;", "savedPassword", "", "getSavedPassword", "()Ljava/lang/String;", "setSavedPassword", "(Ljava/lang/String;)V", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ContextData {
    private final Contract.ICallback callback;
    private final Context context;
    private final IPj ipj;
    private boolean isCanceled;
    private final Contract.IView iview;
    private int pjIndex;
    private final ArrayList<ConnectPjInfo> pjList;
    private String savedPassword;

    public ContextData(Context context, ArrayList<ConnectPjInfo> pjList, IPj ipj, Contract.ICallback callback, Contract.IView iview) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pjList, "pjList");
        Intrinsics.checkNotNullParameter(ipj, "ipj");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(iview, "iview");
        this.context = context;
        this.pjList = pjList;
        this.ipj = ipj;
        this.callback = callback;
        this.iview = iview;
        this.savedPassword = "";
        this.pjIndex = -1;
    }

    public final Context getContext() {
        return this.context;
    }

    public final ArrayList<ConnectPjInfo> getPjList() {
        return this.pjList;
    }

    public final IPj getIpj() {
        return this.ipj;
    }

    public final Contract.ICallback getCallback() {
        return this.callback;
    }

    public final Contract.IView getIview() {
        return this.iview;
    }

    public final String getSavedPassword() {
        return this.savedPassword;
    }

    public final void setSavedPassword(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.savedPassword = str;
    }

    public final int getPjIndex() {
        return this.pjIndex;
    }

    public final void setPjIndex(int i) {
        this.pjIndex = i;
    }

    public final boolean isCanceled() {
        return this.isCanceled;
    }

    public final void setCanceled(boolean z) {
        this.isCanceled = z;
    }
}
