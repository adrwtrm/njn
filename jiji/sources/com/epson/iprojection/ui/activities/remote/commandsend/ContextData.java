package com.epson.iprojection.ui.activities.remote.commandsend;

import android.app.Activity;
import com.epson.iprojection.ui.activities.remote.commandsend.Contract;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ContextData.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001cR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0018\"\u0004\b+\u0010,¨\u0006-"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/ContextData;", "", "activity", "Landroid/app/Activity;", "pjList", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/activities/remote/commandsend/D_SendCommand;", "Lkotlin/collections/ArrayList;", "ipj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "callback", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$ICallback;", "iview", "Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IView;", "isEscvpOnly", "", "command", "", "(Landroid/app/Activity;Ljava/util/ArrayList;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$ICallback;Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IView;ZLjava/lang/String;)V", "getActivity", "()Landroid/app/Activity;", "getCallback", "()Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$ICallback;", "getCommand", "()Ljava/lang/String;", "getIpj", "()Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "isCanceled", "()Z", "setCanceled", "(Z)V", "getIview", "()Lcom/epson/iprojection/ui/activities/remote/commandsend/Contract$IView;", "pjIndex", "", "getPjIndex", "()I", "setPjIndex", "(I)V", "getPjList", "()Ljava/util/ArrayList;", "savedPassword", "getSavedPassword", "setSavedPassword", "(Ljava/lang/String;)V", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ContextData {
    private final Activity activity;
    private final Contract.ICallback callback;
    private final String command;
    private final IPj ipj;
    private boolean isCanceled;
    private final boolean isEscvpOnly;
    private final Contract.IView iview;
    private int pjIndex;
    private final ArrayList<D_SendCommand> pjList;
    private String savedPassword;

    public ContextData(Activity activity, ArrayList<D_SendCommand> pjList, IPj ipj, Contract.ICallback callback, Contract.IView iview, boolean z, String command) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(pjList, "pjList");
        Intrinsics.checkNotNullParameter(ipj, "ipj");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(iview, "iview");
        Intrinsics.checkNotNullParameter(command, "command");
        this.activity = activity;
        this.pjList = pjList;
        this.ipj = ipj;
        this.callback = callback;
        this.iview = iview;
        this.isEscvpOnly = z;
        this.command = command;
        this.savedPassword = "";
        this.pjIndex = -1;
    }

    public final Activity getActivity() {
        return this.activity;
    }

    public final ArrayList<D_SendCommand> getPjList() {
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

    public final boolean isEscvpOnly() {
        return this.isEscvpOnly;
    }

    public final String getCommand() {
        return this.command;
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
