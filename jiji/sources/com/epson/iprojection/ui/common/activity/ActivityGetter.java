package com.epson.iprojection.ui.common.activity;

import android.app.Activity;
import android.os.Process;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.MemoryUtils;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class ActivityGetter {
    private static final ActivityGetter _inst = new ActivityGetter();
    private Activity _activity = null;
    private boolean _isAppFinished = true;

    public Activity getFrontActivity() {
        Activity activity = this._activity;
        if (activity == null) {
            return null;
        }
        return activity;
    }

    public void set(Activity activity) {
        this._activity = activity;
        this._isAppFinished = false;
        Pj.getIns().setActivity(activity);
    }

    public void killMyProcess() {
        if (this._activity != null) {
            Lg.e("OutofMemoryを検出しました。プロセスを終了します");
            MemoryUtils.show(getIns().getFrontActivity());
            Process.killProcess(Process.myPid());
        }
    }

    public void finishedApp() {
        this._isAppFinished = true;
    }

    public boolean isAppFinished() {
        return this._isAppFinished;
    }

    private ActivityGetter() {
    }

    public static ActivityGetter getIns() {
        return _inst;
    }
}
