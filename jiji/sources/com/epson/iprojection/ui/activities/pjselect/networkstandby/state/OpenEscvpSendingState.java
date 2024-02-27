package com.epson.iprojection.ui.activities.pjselect.networkstandby.state;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.ContextData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OpenEscvpSendingState.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/OpenEscvpSendingState;", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/state/State;", "contextData", "Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;", "(Lcom/epson/iprojection/ui/activities/pjselect/networkstandby/ContextData;)V", "start", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OpenEscvpSendingState extends State {
    /* renamed from: $r8$lambda$veEJdiJH0oJ0iEyHh-kNzFSAK-o */
    public static /* synthetic */ void m150$r8$lambda$veEJdiJH0oJ0iEyHhkNzFSAKo(OpenEscvpSendingState openEscvpSendingState) {
        start$lambda$0(openEscvpSendingState);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OpenEscvpSendingState(ContextData contextData) {
        super(contextData);
        Intrinsics.checkNotNullParameter(contextData, "contextData");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.state.State
    public void start() {
        Lg.d("[nw] OpenESCVP送信");
        D_PjInfo pjInfo = get_contextData().getPjList().get(get_contextData().getPjIndex()).getPjInfo();
        get_contextData().getIpj().pjcontrol_spoweron(pjInfo);
        if (pjInfo.hasScomport) {
            get_contextData().getIpj().pjcontrol_scomport(pjInfo);
        }
        get_handler().post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.networkstandby.state.OpenEscvpSendingState$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                OpenEscvpSendingState.m150$r8$lambda$veEJdiJH0oJ0iEyHhkNzFSAKo(OpenEscvpSendingState.this);
            }
        });
    }

    public static final void start$lambda$0(OpenEscvpSendingState this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.get_contextData().getCallback().changeState(new StartState(this$0.get_contextData()));
    }
}
