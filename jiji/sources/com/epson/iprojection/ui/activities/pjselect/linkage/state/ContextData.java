package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import android.content.Context;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ContextData.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/ContextData;", "", "context", "Landroid/content/Context;", "linkageData", "Lcom/epson/iprojection/linkagedata/data/D_LinkageData;", "contextListener", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IContextListener;", "engine", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "connectListener", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;", "(Landroid/content/Context;Lcom/epson/iprojection/linkagedata/data/D_LinkageData;Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IContextListener;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;)V", "getConnectListener", "()Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IConnectListener;", "getContext", "()Landroid/content/Context;", "getContextListener", "()Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IContextListener;", "getEngine", "()Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "foundPjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "getFoundPjInfo", "()Lcom/epson/iprojection/engine/common/D_PjInfo;", "setFoundPjInfo", "(Lcom/epson/iprojection/engine/common/D_PjInfo;)V", "getLinkageData", "()Lcom/epson/iprojection/linkagedata/data/D_LinkageData;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ContextData {
    private final IConnectListener connectListener;
    private final Context context;
    private final IContextListener contextListener;
    private final IPj engine;
    private D_PjInfo foundPjInfo;
    private final D_LinkageData linkageData;

    public ContextData(Context context, D_LinkageData linkageData, IContextListener contextListener, IPj engine, IConnectListener connectListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(linkageData, "linkageData");
        Intrinsics.checkNotNullParameter(contextListener, "contextListener");
        Intrinsics.checkNotNullParameter(engine, "engine");
        Intrinsics.checkNotNullParameter(connectListener, "connectListener");
        this.context = context;
        this.linkageData = linkageData;
        this.contextListener = contextListener;
        this.engine = engine;
        this.connectListener = connectListener;
    }

    public final Context getContext() {
        return this.context;
    }

    public final D_LinkageData getLinkageData() {
        return this.linkageData;
    }

    public final IContextListener getContextListener() {
        return this.contextListener;
    }

    public final IPj getEngine() {
        return this.engine;
    }

    public final IConnectListener getConnectListener() {
        return this.connectListener;
    }

    public final D_PjInfo getFoundPjInfo() {
        return this.foundPjInfo;
    }

    public final void setFoundPjInfo(D_PjInfo d_PjInfo) {
        this.foundPjInfo = d_PjInfo;
    }
}
