package com.epson.iprojection.service.webrtc;

import com.epson.iprojection.common.Lg;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StateMachine.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\tJ\u0006\u0010\u000b\u001a\u00020\tJ\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\r\u001a\u00020\tJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0004R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/service/webrtc/StateMachine;", "", "()V", "<set-?>", "Lcom/epson/iprojection/service/webrtc/WebRTCStatus;", "_state", "get_state", "()Lcom/epson/iprojection/service/webrtc/WebRTCStatus;", "canCreateOfferSDP", "", "canInit", "canStart", "canStop", "canUninit", "setStatus", "", "state", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public class StateMachine {
    private WebRTCStatus _state = WebRTCStatus.uninitialized;

    public final WebRTCStatus get_state() {
        return this._state;
    }

    public final boolean canInit() {
        if (this._state == WebRTCStatus.uninitialized || this._state == WebRTCStatus.stopped) {
            return true;
        }
        Lg.e("initが呼ばれているのにuninitialized or stopped状態じゃない！");
        return false;
    }

    public final boolean canUninit() {
        if (this._state == WebRTCStatus.initialized || this._state == WebRTCStatus.stopped) {
            return true;
        }
        Lg.e("uninitが呼ばれているのにinitializedかstopped状態じゃない！ [でも状態遷移を許可する]");
        return true;
    }

    public final boolean canCreateOfferSDP() {
        if (this._state == WebRTCStatus.initialized || this._state == WebRTCStatus.stopped) {
            return true;
        }
        Lg.e("createOfferSDPが呼ばれているのにinitializedかstopped状態じゃない！");
        return false;
    }

    public final boolean canStart() {
        if (this._state != WebRTCStatus.sdpcreated) {
            Lg.e("startが呼ばれているのにsdpcreated状態じゃない！");
            return false;
        }
        return true;
    }

    public final boolean canStop() {
        if (this._state != WebRTCStatus.started) {
            Lg.e("stopが呼ばれているのにstarted状態じゃない！");
            return true;
        }
        return true;
    }

    public final void setStatus(WebRTCStatus state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this._state = state;
    }
}
