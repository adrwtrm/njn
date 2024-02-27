package com.epson.iprojection.ui.engine_wrapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnScreenEventListener;

/* loaded from: classes.dex */
public class ScreenStatusWatcher {
    private final Context _context;
    private final IOnScreenEventListener _impl;
    private boolean _isRegistered = false;
    private final BroadcastReceiver _receiver = new BroadcastReceiver() { // from class: com.epson.iprojection.ui.engine_wrapper.ScreenStatusWatcher.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                Lg.i("Screen OFF");
                ScreenStatusWatcher.this._impl.onScreenOff();
            }
            if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                Lg.i("Screen ON");
                ScreenStatusWatcher.this._impl.onScreenOn();
            }
        }
    };

    public ScreenStatusWatcher(Context context, IOnScreenEventListener iOnScreenEventListener) {
        this._context = context;
        this._impl = iOnScreenEventListener;
    }

    public void register() {
        if (this._isRegistered) {
            Lg.w("二重に登録しようとしました");
            unregister();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this._context.registerReceiver(this._receiver, intentFilter);
        this._isRegistered = true;
    }

    public void unregister() {
        if (!this._isRegistered) {
            Lg.i("二重に解放しようとしました。（特に問題は無）");
            return;
        }
        try {
            this._context.unregisterReceiver(this._receiver);
        } catch (Exception unused) {
            Lg.w("IllegalArgumentException");
        }
        this._isRegistered = false;
    }
}
