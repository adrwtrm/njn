package com.epson.iprojection.ui.engine_wrapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnShutdownEventListener;

/* loaded from: classes.dex */
public class ShutdownWatcher {
    private final Context _context;
    private final IOnShutdownEventListener _impl;
    private boolean _isRegistered = false;
    private final BroadcastReceiver _receiver = new BroadcastReceiver() { // from class: com.epson.iprojection.ui.engine_wrapper.ShutdownWatcher.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
                Lg.i("Shutdown");
                ShutdownWatcher.this._impl.onShutdown();
            }
        }
    };

    public ShutdownWatcher(Context context, IOnShutdownEventListener iOnShutdownEventListener) {
        this._context = context;
        this._impl = iOnShutdownEventListener;
    }

    public void register() {
        if (this._isRegistered) {
            Lg.w("二重に登録しようとしました");
            unregister();
        }
        this._context.registerReceiver(this._receiver, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
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
