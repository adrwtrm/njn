package com.epson.iprojection.ui.common.singleton;

import android.app.Activity;
import android.os.Process;
import com.epson.iprojection.ui.engine_wrapper.DisconReason;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class AppKiller {
    private static final AppKiller _inst = new AppKiller();
    private eStatus _status = eStatus.None;

    /* loaded from: classes.dex */
    public enum eStatus {
        None,
        Disconnecting,
        Killing
    }

    public void start(Activity activity) {
        if (Pj.getIns().isConnected()) {
            Pj.getIns().disconnect(DisconReason.UserAction);
            this._status = eStatus.Disconnecting;
            return;
        }
        activity.finish();
        this._status = eStatus.Killing;
    }

    public boolean isKilling() {
        return this._status == eStatus.Killing;
    }

    public void onRestart(Activity activity) {
        if (this._status == eStatus.Killing) {
            activity.finish();
        }
    }

    public void onDestroy(boolean z) {
        if (this._status == eStatus.Killing && z) {
            Process.killProcess(Process.myPid());
        }
    }

    public void onDisconnect(Activity activity) {
        if (this._status == eStatus.Disconnecting) {
            start(activity);
        }
    }

    public static AppKiller getIns() {
        return _inst;
    }

    private AppKiller() {
    }
}
